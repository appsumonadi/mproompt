package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_prompts")
data class CustomPromptEntity(
  @PrimaryKey val id: String,
  val title: String,
  val description: String,
  val promptTemplate: String,
  val negativePrompt: String,
  val modelName: String,
  val categoryName: String,
  val imageUrl: String,
  val parametersJson: String,
  val variablesJson: String,
  val likesCount: Int,
  val copyCount: Int,
  val createdAt: Long
)

@Entity(tableName = "saved_prompt_ids")
data class SavedPromptEntity(
  @PrimaryKey val promptId: String,
  val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "prompt_history")
data class PromptHistoryEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val promptId: String,
  val promptTitle: String,
  val compiledPrompt: String,
  val modelName: String,
  val copiedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_categories")
data class CustomCategoryEntity(
  @PrimaryKey val id: String,
  val displayName: String,
  val description: String,
  val badgeTag: String,
  val colorHex: String,
  val bannerImageUrl: String,
  val accentHex: String,
  val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "api_configs")
data class ApiConfigEntity(
  @PrimaryKey val id: String, // e.g. "gemini", "openai", "midjourney", "stability", "replicate", "custom"
  val serviceName: String,
  val apiKey: String,
  val endpointUrl: String,
  val modelIdentifier: String,
  val isEnabled: Boolean,
  val lastTestedStatus: String = "Not Tested",
  val lastTestedTimestamp: Long = 0L,
  val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_settings")
data class AppAdminSettingsEntity(
  @PrimaryKey val id: String = "global_settings",
  val adsEnabled: Boolean = true,
  val bannerAdUnitId: String = "ca-app-pub-3940256099942544/6300978111", // Google sample test banner
  val interstitialAdUnitId: String = "ca-app-pub-3940256099942544/1033173712", // Google sample test interstitial
  val interstitialFrequency: Int = 3, // Show interstitial every 3 copies
  val adminPin: String = "1234",
  val announcementEnabled: Boolean = false,
  val announcementText: String = "",
  val primarySiteUrl: String = "https://prompteg.ai",
  val supportEmail: String = "support@prompteg.ai",
  val backendApiUrl: String = "", // e.g. "https://yourdomain.com/backend/api/sync.php"
  val backendApiKey: String = "prompteg_secret_key_2026",
  val isRemoteSyncEnabled: Boolean = false,
  val lastSyncTimestamp: Long = 0L,
  val lastSyncStatus: String = "Ready to connect",
  val lastSyncCount: Int = 0,
  val updatedAt: Long = System.currentTimeMillis()
)

