package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PromptDao {
  // Custom Prompts
  @Query("SELECT * FROM custom_prompts ORDER BY createdAt DESC")
  fun getAllCustomPrompts(): Flow<List<CustomPromptEntity>>

  @Query("SELECT * FROM custom_prompts WHERE id = :id")
  suspend fun getCustomPromptById(id: String): CustomPromptEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCustomPrompt(prompt: CustomPromptEntity)

  @Query("DELETE FROM custom_prompts WHERE id = :id")
  suspend fun deleteCustomPrompt(id: String)

  @Query("DELETE FROM custom_prompts")
  suspend fun clearAllCustomPrompts()

  // Custom Categories
  @Query("SELECT * FROM custom_categories ORDER BY createdAt ASC")
  fun getAllCustomCategories(): Flow<List<CustomCategoryEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertCustomCategory(category: CustomCategoryEntity)

  @Query("DELETE FROM custom_categories WHERE id = :id")
  suspend fun deleteCustomCategory(id: String)

  @Query("DELETE FROM custom_categories")
  suspend fun clearAllCustomCategories()

  // API Configs
  @Query("SELECT * FROM api_configs ORDER BY serviceName ASC")
  fun getAllApiConfigs(): Flow<List<ApiConfigEntity>>

  @Query("SELECT * FROM api_configs WHERE id = :id")
  suspend fun getApiConfigById(id: String): ApiConfigEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertApiConfig(config: ApiConfigEntity)

  @Query("DELETE FROM api_configs WHERE id = :id")
  suspend fun deleteApiConfig(id: String)

  // App & Admin Settings
  @Query("SELECT * FROM admin_settings WHERE id = 'global_settings' LIMIT 1")
  fun getAdminSettings(): Flow<AppAdminSettingsEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveAdminSettings(settings: AppAdminSettingsEntity)

  // Saved Prompts (Bookmarks)
  @Query("SELECT * FROM saved_prompt_ids ORDER BY savedAt DESC")
  fun getAllSavedPromptIds(): Flow<List<SavedPromptEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun savePromptId(saved: SavedPromptEntity)

  @Query("DELETE FROM saved_prompt_ids WHERE promptId = :promptId")
  suspend fun unsavePromptId(promptId: String)

  @Query("SELECT EXISTS(SELECT 1 FROM saved_prompt_ids WHERE promptId = :promptId)")
  suspend fun isPromptSaved(promptId: String): Boolean

  // Prompt Copy History
  @Query("SELECT * FROM prompt_history ORDER BY copiedAt DESC LIMIT 50")
  fun getRecentHistory(): Flow<List<PromptHistoryEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertHistory(history: PromptHistoryEntity)

  @Query("DELETE FROM prompt_history")
  suspend fun clearHistory()
}
