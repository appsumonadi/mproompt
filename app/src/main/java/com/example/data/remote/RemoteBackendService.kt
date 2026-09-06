package com.example.data.remote

import com.example.data.local.AppAdminSettingsEntity
import com.example.data.local.CustomCategoryEntity
import com.example.data.local.CustomPromptEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

data class SyncResult(
  val success: Boolean,
  val promptsCount: Int = 0,
  val categoriesCount: Int = 0,
  val message: String = "",
  val latencyMs: Long = 0,
  val prompts: List<CustomPromptEntity> = emptyList(),
  val categories: List<CustomCategoryEntity> = emptyList(),
  val remoteSettings: RemoteSettingsData? = null
)

data class RemoteSettingsData(
  val adsEnabled: Boolean,
  val bannerAdUnitId: String,
  val interstitialAdUnitId: String,
  val interstitialFrequency: Int,
  val announcementEnabled: Boolean,
  val announcementText: String
)

class RemoteBackendService {

  private val client: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .build()

  /**
   * Tests connectivity to a given PHP Backend URL
   */
  suspend fun testBackendConnection(rawUrl: String, apiKey: String): SyncResult = withContext(Dispatchers.IO) {
    val startTime = System.currentTimeMillis()
    val cleanUrl = sanitizeUrl(rawUrl)

    if (cleanUrl.isBlank()) {
      return@withContext SyncResult(
        success = false,
        message = "Backend URL cannot be empty."
      )
    }

    try {
      // If URL points to folder, try test.php or sync.php
      val targetUrl = if (cleanUrl.endsWith(".php")) {
        cleanUrl
      } else {
        "${cleanUrl.trimEnd('/')}/api/sync.php"
      }

      val request = Request.Builder()
        .url(targetUrl)
        .addHeader("X-API-KEY", apiKey.trim())
        .addHeader("Accept", "application/json")
        .get()
        .build()

      val response = client.newCall(request).execute()
      val latency = System.currentTimeMillis() - startTime
      val body = response.body?.string() ?: ""

      if (!response.isSuccessful) {
        return@withContext SyncResult(
          success = false,
          latencyMs = latency,
          message = "Server returned HTTP ${response.code}: ${response.message.ifBlank { "Error" }}"
        )
      }

      val json = JSONObject(body)
      val isSuccess = json.optBoolean("success", false)
      if (isSuccess) {
        val promptsCount = json.optJSONObject("counts")?.optInt("prompts")
          ?: json.optJSONArray("prompts")?.length() ?: 0
        val catsCount = json.optJSONObject("counts")?.optInt("categories")
          ?: json.optJSONArray("categories")?.length() ?: 0

        SyncResult(
          success = true,
          latencyMs = latency,
          promptsCount = promptsCount,
          categoriesCount = catsCount,
          message = "Connected successfully! (${latency}ms) - $promptsCount prompts, $catsCount categories ready."
        )
      } else {
        val error = json.optString("error", "Unknown server error")
        SyncResult(
          success = false,
          latencyMs = latency,
          message = "Backend rejected request: $error"
        )
      }
    } catch (e: IOException) {
      val latency = System.currentTimeMillis() - startTime
      SyncResult(
        success = false,
        latencyMs = latency,
        message = "Network error: ${e.localizedMessage ?: "Unable to reach server"}"
      )
    } catch (e: Exception) {
      val latency = System.currentTimeMillis() - startTime
      SyncResult(
        success = false,
        latencyMs = latency,
        message = "Connection error: ${e.localizedMessage ?: "Invalid response"}"
      )
    }
  }

  /**
   * Fetches full catalog data from PHP sync.php endpoint
   */
  suspend fun fetchSyncData(rawUrl: String, apiKey: String): SyncResult = withContext(Dispatchers.IO) {
    val startTime = System.currentTimeMillis()
    val cleanUrl = sanitizeUrl(rawUrl)

    if (cleanUrl.isBlank()) {
      return@withContext SyncResult(success = false, message = "Backend URL is empty.")
    }

    try {
      val targetUrl = if (cleanUrl.endsWith(".php")) {
        cleanUrl
      } else {
        "${cleanUrl.trimEnd('/')}/api/sync.php"
      }

      val request = Request.Builder()
        .url(targetUrl)
        .addHeader("X-API-KEY", apiKey.trim())
        .addHeader("Accept", "application/json")
        .get()
        .build()

      val response = client.newCall(request).execute()
      val latency = System.currentTimeMillis() - startTime
      val body = response.body?.string() ?: ""

      if (!response.isSuccessful) {
        return@withContext SyncResult(
          success = false,
          latencyMs = latency,
          message = "HTTP ${response.code}: ${response.message}"
        )
      }

      val json = JSONObject(body)
      if (!json.optBoolean("success", false)) {
        return@withContext SyncResult(
          success = false,
          latencyMs = latency,
          message = json.optString("error", "Sync failed on server")
        )
      }

      // Parse Prompts
      val promptsList = mutableListOf<CustomPromptEntity>()
      val promptsArray = json.optJSONArray("prompts") ?: JSONArray()
      for (i in 0 until promptsArray.length()) {
        val p = promptsArray.getJSONObject(i)
        val id = p.optString("id", "cloud_$i")
        val title = p.optString("title", "Untitled Prompt")
        val description = p.optString("description", "")
        val template = p.optString("promptTemplate", p.optString("prompt_template", ""))
        val negative = p.optString("negativePrompt", p.optString("negative_prompt", ""))
        val model = p.optString("model", p.optString("model_name", "MIDJOURNEY"))
        val category = p.optString("category", p.optString("category_name", "SCI_FI"))
        val customCat = p.optString("customCategoryName", p.optString("custom_category_name", ""))
        val imageUrl = p.optString("imageUrl", p.optString("image_url", ""))
        val likes = p.optInt("likesCount", p.optInt("likes_count", 100))
        val copies = p.optInt("copyCount", p.optInt("copy_count", 250))
        val createdAt = p.optLong("createdAt", System.currentTimeMillis())

        val paramsObj = p.optJSONObject("parameters") ?: JSONObject()
        val varsArray = p.optJSONArray("variables") ?: JSONArray()

        promptsList.add(
          CustomPromptEntity(
            id = id,
            title = title,
            description = description,
            promptTemplate = template,
            negativePrompt = negative,
            modelName = model,
            categoryName = if (customCat.isNotBlank()) customCat else category,
            imageUrl = imageUrl.ifBlank { "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=900&auto=format&fit=crop&q=80" },
            parametersJson = paramsObj.toString(),
            variablesJson = varsArray.toString(),
            likesCount = likes,
            copyCount = copies,
            createdAt = createdAt
          )
        )
      }

      // Parse Categories
      val categoriesList = mutableListOf<CustomCategoryEntity>()
      val catsArray = json.optJSONArray("categories") ?: JSONArray()
      for (i in 0 until catsArray.length()) {
        val c = catsArray.getJSONObject(i)
        val id = c.optString("id", "cat_$i")
        val displayName = c.optString("display_name", c.optString("displayName", id))
        val desc = c.optString("description", "")
        val badge = c.optString("badge_tag", c.optString("badgeTag", "TAG"))
        val color = c.optString("color_hex", c.optString("colorHex", "#FF6B00"))
        val accent = c.optString("accent_hex", c.optString("accentHex", "#00F0FF"))
        val banner = c.optString("banner_image_url", c.optString("bannerImageUrl", ""))

        categoriesList.add(
          CustomCategoryEntity(
            id = id,
            displayName = displayName,
            description = desc,
            badgeTag = badge,
            colorHex = color,
            bannerImageUrl = banner,
            accentHex = accent,
            createdAt = System.currentTimeMillis()
          )
        )
      }

      // Parse Settings
      var remoteSettings: RemoteSettingsData? = null
      val settingsObj = json.optJSONObject("settings")
      if (settingsObj != null) {
        remoteSettings = RemoteSettingsData(
          adsEnabled = settingsObj.optBoolean("adsEnabled", true),
          bannerAdUnitId = settingsObj.optString("bannerAdUnitId", "ca-app-pub-3940256099942544/6300978111"),
          interstitialAdUnitId = settingsObj.optString("interstitialAdUnitId", "ca-app-pub-3940256099942544/1033173712"),
          interstitialFrequency = settingsObj.optInt("interstitialFrequency", 3),
          announcementEnabled = settingsObj.optBoolean("announcementEnabled", false),
          announcementText = settingsObj.optString("announcementText", "")
        )
      }

      SyncResult(
        success = true,
        latencyMs = latency,
        promptsCount = promptsList.size,
        categoriesCount = categoriesList.size,
        message = "Synced ${promptsList.size} prompts and ${categoriesList.size} categories from PHP backend!",
        prompts = promptsList,
        categories = categoriesList,
        remoteSettings = remoteSettings
      )
    } catch (e: Exception) {
      val latency = System.currentTimeMillis() - startTime
      SyncResult(
        success = false,
        latencyMs = latency,
        message = "Sync failed: ${e.localizedMessage ?: "Unknown network exception"}"
      )
    }
  }

  private fun sanitizeUrl(url: String): String {
    var trimmed = url.trim()
    if (trimmed.isNotBlank() && !trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
      trimmed = "https://$trimmed"
    }
    return trimmed
  }
}
