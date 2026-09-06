package com.example.data.repository

import androidx.compose.ui.graphics.Color
import com.example.data.local.ApiConfigEntity
import com.example.data.local.AppAdminSettingsEntity
import com.example.data.local.CustomCategoryEntity
import com.example.data.local.CustomPromptEntity
import com.example.data.local.PromptDao
import com.example.data.local.PromptHistoryEntity
import com.example.data.local.SavedPromptEntity
import com.example.data.model.AIModel
import com.example.data.model.PromptCategory
import com.example.data.model.PromptItem
import com.example.data.model.PromptVariable
import com.example.data.model.UnifiedCategory
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentPink
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentRed
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.flow.Flow
import com.example.data.remote.RemoteBackendService
import com.example.data.remote.SyncResult
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

class PromptRepository(
  private val promptDao: PromptDao,
  private val remoteService: RemoteBackendService = RemoteBackendService()
) {

  private val memoryLikes = mutableMapOf<String, Boolean>()
  private val memoryCopyCounts = mutableMapOf<String, Int>()

  fun getPromptsFlow(): Flow<List<PromptItem>> {
    return combine(
      promptDao.getAllCustomPrompts(),
      promptDao.getAllSavedPromptIds()
    ) { customEntities, savedIds ->
      val savedIdSet = savedIds.map { it.promptId }.toSet()

      val customPrompts = customEntities.map { entity ->
        entityToPromptItem(entity, savedIdSet.contains(entity.id))
      }

      val curatedPrompts = CuratedPromptsData.defaultPrompts.map { item ->
        val isLiked = memoryLikes[item.id] ?: false
        val extraCopies = memoryCopyCounts[item.id] ?: 0
        item.copy(
          isBookmarked = savedIdSet.contains(item.id),
          isLiked = isLiked,
          likesCount = if (isLiked) item.likesCount + 1 else item.likesCount,
          copyCount = item.copyCount + extraCopies
        )
      }

      customPrompts + curatedPrompts
    }
  }

  fun getUnifiedCategoriesFlow(): Flow<List<UnifiedCategory>> {
    return promptDao.getAllCustomCategories().map { customEntities ->
      val defaultList = PromptCategory.values().map { cat ->
        UnifiedCategory(
          id = cat.name,
          title = cat.title,
          subtitle = cat.subtitle,
          chipLabel = cat.chipLabel,
          dotColor = cat.dotColor,
          coverImageUrl = cat.coverImageUrl,
          accentColor = cat.accentColor,
          isCustom = false
        )
      }

      val customList = customEntities.map { entity ->
        val dotColor = parseHexColor(entity.colorHex, OrangePrimary)
        val accentColor = parseHexColor(entity.accentHex, AccentCyan)
        UnifiedCategory(
          id = entity.id,
          title = entity.displayName,
          subtitle = entity.description,
          chipLabel = entity.badgeTag,
          dotColor = dotColor,
          coverImageUrl = entity.bannerImageUrl.ifBlank {
            "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=900&auto=format&fit=crop&q=80"
          },
          accentColor = accentColor,
          isCustom = true
        )
      }

      defaultList + customList
    }
  }

  fun getApiConfigsFlow(): Flow<List<ApiConfigEntity>> {
    return promptDao.getAllApiConfigs().map { configs ->
      if (configs.isEmpty()) {
        // Return default placeholder configurations
        listOf(
          ApiConfigEntity(
            id = "gemini",
            serviceName = "Google Gemini 2.0 / 1.5 Flash",
            apiKey = "",
            endpointUrl = "https://generativelanguage.googleapis.com/v1beta",
            modelIdentifier = "gemini-2.0-flash",
            isEnabled = false,
            lastTestedStatus = "Ready to configure"
          ),
          ApiConfigEntity(
            id = "openai",
            serviceName = "OpenAI DALL·E 3 / GPT-4o",
            apiKey = "",
            endpointUrl = "https://api.openai.com/v1",
            modelIdentifier = "dall-e-3",
            isEnabled = false,
            lastTestedStatus = "Ready to configure"
          ),
          ApiConfigEntity(
            id = "midjourney",
            serviceName = "Midjourney Proxy / Imagine API",
            apiKey = "",
            endpointUrl = "https://api.midjourney.com/v2",
            modelIdentifier = "midjourney-v6",
            isEnabled = false,
            lastTestedStatus = "Ready to configure"
          ),
          ApiConfigEntity(
            id = "stability",
            serviceName = "Stability AI (SDXL / Ultra)",
            apiKey = "",
            endpointUrl = "https://api.stability.ai/v1",
            modelIdentifier = "stable-diffusion-xl-1024-v1-0",
            isEnabled = false,
            lastTestedStatus = "Ready to configure"
          )
        )
      } else {
        configs
      }
    }
  }

  fun getAdminSettingsFlow(): Flow<AppAdminSettingsEntity> {
    return promptDao.getAdminSettings().map { settings ->
      settings ?: AppAdminSettingsEntity()
    }
  }

  suspend fun saveAdminSettings(settings: AppAdminSettingsEntity) {
    promptDao.saveAdminSettings(settings)
  }

  suspend fun testBackendConnection(url: String, apiKey: String): SyncResult {
    return remoteService.testBackendConnection(url, apiKey)
  }

  suspend fun syncFromRemoteBackend(url: String, apiKey: String): SyncResult {
    val result = remoteService.fetchSyncData(url, apiKey)
    if (result.success) {
      // 1. Insert remote prompts into Room custom prompts
      result.prompts.forEach { promptEntity ->
        promptDao.insertCustomPrompt(promptEntity)
      }

      // 2. Insert remote categories into Room
      result.categories.forEach { categoryEntity ->
        promptDao.insertCustomCategory(categoryEntity)
      }

      // 3. Update Admin Settings if remote settings received
      val currentSettings = promptDao.getAdminSettings().firstOrNull() ?: AppAdminSettingsEntity()
      val updatedSettings = if (result.remoteSettings != null) {
        currentSettings.copy(
          backendApiUrl = url,
          backendApiKey = apiKey,
          adsEnabled = result.remoteSettings.adsEnabled,
          bannerAdUnitId = result.remoteSettings.bannerAdUnitId,
          interstitialAdUnitId = result.remoteSettings.interstitialAdUnitId,
          interstitialFrequency = result.remoteSettings.interstitialFrequency,
          announcementEnabled = result.remoteSettings.announcementEnabled,
          announcementText = result.remoteSettings.announcementText,
          lastSyncTimestamp = System.currentTimeMillis(),
          lastSyncStatus = "Synced ${result.prompts.size} Prompts & ${result.categories.size} Categories",
          lastSyncCount = result.prompts.size,
          updatedAt = System.currentTimeMillis()
        )
      } else {
        currentSettings.copy(
          backendApiUrl = url,
          backendApiKey = apiKey,
          lastSyncTimestamp = System.currentTimeMillis(),
          lastSyncStatus = "Synced ${result.prompts.size} Prompts",
          lastSyncCount = result.prompts.size,
          updatedAt = System.currentTimeMillis()
        )
      }
      promptDao.saveAdminSettings(updatedSettings)
    } else {
      // Update error status
      val currentSettings = promptDao.getAdminSettings().firstOrNull() ?: AppAdminSettingsEntity()
      promptDao.saveAdminSettings(
        currentSettings.copy(
          lastSyncStatus = "Failed: ${result.message.take(40)}",
          updatedAt = System.currentTimeMillis()
        )
      )
    }
    return result
  }

  suspend fun saveApiConfig(config: ApiConfigEntity) {
    promptDao.insertApiConfig(config)
  }

  suspend fun deleteApiConfig(id: String) {
    promptDao.deleteApiConfig(id)
  }

  suspend fun saveCustomCategory(
    displayName: String,
    description: String,
    badgeTag: String,
    colorHex: String,
    bannerImageUrl: String,
    accentHex: String
  ): CustomCategoryEntity {
    val id = "cat_" + UUID.randomUUID().toString().take(8)
    val entity = CustomCategoryEntity(
      id = id,
      displayName = displayName,
      description = description,
      badgeTag = badgeTag.uppercase().trim(),
      colorHex = colorHex,
      bannerImageUrl = bannerImageUrl.ifBlank { "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=900&auto=format&fit=crop&q=80" },
      accentHex = accentHex,
      createdAt = System.currentTimeMillis()
    )
    promptDao.insertCustomCategory(entity)
    return entity
  }

  suspend fun deleteCustomCategory(id: String) {
    promptDao.deleteCustomCategory(id)
  }

  fun getRecentHistoryFlow(): Flow<List<PromptHistoryEntity>> {
    return promptDao.getRecentHistory()
  }

  suspend fun toggleBookmark(promptId: String, currentSaved: Boolean) {
    if (currentSaved) {
      promptDao.unsavePromptId(promptId)
    } else {
      promptDao.savePromptId(SavedPromptEntity(promptId = promptId))
    }
  }

  fun toggleLike(promptId: String): Boolean {
    val current = memoryLikes[promptId] ?: false
    val newState = !current
    memoryLikes[promptId] = newState
    return newState
  }

  suspend fun recordCopy(promptId: String, title: String, compiledPrompt: String, model: String) {
    memoryCopyCounts[promptId] = (memoryCopyCounts[promptId] ?: 0) + 1
    promptDao.insertHistory(
      PromptHistoryEntity(
        promptId = promptId,
        promptTitle = title,
        compiledPrompt = compiledPrompt,
        modelName = model
      )
    )
  }

  suspend fun saveCustomPrompt(
    title: String,
    description: String,
    template: String,
    negativePrompt: String,
    model: AIModel,
    category: PromptCategory,
    customCategoryName: String? = null,
    imageUrl: String,
    parameters: Map<String, String>,
    variables: List<PromptVariable>,
    existingId: String? = null
  ): PromptItem {
    val id = existingId ?: ("custom_" + UUID.randomUUID().toString().take(8))

    val paramsObj = JSONObject()
    parameters.forEach { (k, v) -> paramsObj.put(k, v) }

    val varsArray = JSONArray()
    variables.forEach { variable ->
      val varObj = JSONObject()
      varObj.put("key", variable.key)
      varObj.put("label", variable.label)
      varObj.put("defaultValue", variable.defaultValue)
      val optionsArray = JSONArray()
      variable.suggestedOptions.forEach { optionsArray.put(it) }
      varObj.put("options", optionsArray)
      varsArray.put(varObj)
    }

    val catName = if (!customCategoryName.isNullOrBlank()) {
      customCategoryName
    } else {
      category.name
    }

    val entity = CustomPromptEntity(
      id = id,
      title = title,
      description = description,
      promptTemplate = template,
      negativePrompt = negativePrompt,
      modelName = model.name,
      categoryName = catName,
      imageUrl = imageUrl.ifBlank { "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=900&auto=format&fit=crop&q=80" },
      parametersJson = paramsObj.toString(),
      variablesJson = varsArray.toString(),
      likesCount = 1,
      copyCount = 0,
      createdAt = System.currentTimeMillis()
    )

    promptDao.insertCustomPrompt(entity)
    return entityToPromptItem(entity, isBookmarked = false)
  }

  suspend fun deleteCustomPrompt(id: String) {
    promptDao.deleteCustomPrompt(id)
    promptDao.unsavePromptId(id)
  }

  suspend fun clearHistory() {
    promptDao.clearHistory()
  }

  suspend fun exportAllDataAsJson(): String {
    val root = JSONObject()
    root.put("version", 2)
    root.put("exportedAt", System.currentTimeMillis())

    // Prompts array
    val promptsArray = JSONArray()
    CuratedPromptsData.defaultPrompts.forEach { p ->
      val pObj = JSONObject()
      pObj.put("id", p.id)
      pObj.put("title", p.title)
      pObj.put("description", p.description)
      pObj.put("promptTemplate", p.promptTemplate)
      pObj.put("negativePrompt", p.negativePrompt)
      pObj.put("model", p.model.name)
      pObj.put("category", p.category.name)
      pObj.put("customCategoryName", p.customCategoryName ?: "")
      pObj.put("imageUrl", p.imageUrl)
      promptsArray.put(pObj)
    }
    root.put("prompts", promptsArray)
    return root.toString(2)
  }

  private fun parseHexColor(hexString: String, fallback: Color): Color {
    return try {
      val cleanHex = hexString.replace("#", "").trim()
      val colorLong = cleanHex.toLong(16)
      if (cleanHex.length == 6) {
        Color(0xFF000000 or colorLong)
      } else {
        Color(colorLong)
      }
    } catch (_: Exception) {
      fallback
    }
  }

  private fun entityToPromptItem(entity: CustomPromptEntity, isBookmarked: Boolean): PromptItem {
    val model = try {
      AIModel.valueOf(entity.modelName)
    } catch (_: Exception) {
      AIModel.MIDJOURNEY
    }

    var customCat: String? = null
    val category = try {
      PromptCategory.valueOf(entity.categoryName)
    } catch (_: Exception) {
      customCat = entity.categoryName
      PromptCategory.REALISTIC
    }

    val params = mutableMapOf<String, String>()
    try {
      val json = JSONObject(entity.parametersJson)
      val keys = json.keys()
      while (keys.hasNext()) {
        val key = keys.next()
        params[key] = json.getString(key)
      }
    } catch (_: Exception) {}

    val variables = mutableListOf<PromptVariable>()
    try {
      val array = JSONArray(entity.variablesJson)
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        val opts = mutableListOf<String>()
        val optsArray = obj.optJSONArray("options")
        if (optsArray != null) {
          for (j in 0 until optsArray.length()) {
            opts.add(optsArray.getString(j))
          }
        }
        variables.add(
          PromptVariable(
            key = obj.optString("key", "var$i"),
            label = obj.optString("label", "Variable ${i + 1}"),
            defaultValue = obj.optString("defaultValue", ""),
            suggestedOptions = opts
          )
        )
      }
    } catch (_: Exception) {}

    val isLiked = memoryLikes[entity.id] ?: false
    val extraCopies = memoryCopyCounts[entity.id] ?: 0

    return PromptItem(
      id = entity.id,
      title = entity.title,
      description = entity.description,
      promptTemplate = entity.promptTemplate,
      negativePrompt = entity.negativePrompt,
      model = model,
      category = category,
      customCategoryName = customCat,
      imageUrl = entity.imageUrl,
      parameters = params,
      variables = variables,
      likesCount = if (isLiked) entity.likesCount + 1 else entity.likesCount,
      copyCount = entity.copyCount + extraCopies,
      isBookmarked = isBookmarked,
      isLiked = isLiked,
      isCustom = true,
      createdAt = entity.createdAt
    )
  }
}

