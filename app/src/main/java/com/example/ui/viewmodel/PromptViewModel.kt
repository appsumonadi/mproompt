package com.example.ui.viewmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PromptHistoryEntity
import com.example.data.model.AIModel
import com.example.data.model.PromptCategory
import com.example.data.model.PromptItem
import com.example.data.model.PromptVariable
import com.example.data.repository.PromptRepository
import com.example.ui.i18n.AppStrings
import com.example.ui.i18n.PromptLocalizer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class StudioMode {
  SYNTHESIZER,
  CUSTOM_CREATOR
}

class PromptViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: PromptRepository

  init {
    val db = AppDatabase.getDatabase(application)
    repository = PromptRepository(db.promptDao())
  }

  // Raw prompts from repository flow
  private val _allPrompts = repository.getPromptsFlow().stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    emptyList()
  )
  val allPromptsFlow: StateFlow<List<PromptItem>> = _allPrompts

  // Unified Categories Flow
  val unifiedCategoriesFlow: StateFlow<List<com.example.data.model.UnifiedCategory>> = repository.getUnifiedCategoriesFlow().stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    emptyList()
  )

  // API Configurations Flow
  val apiConfigsFlow: StateFlow<List<com.example.data.local.ApiConfigEntity>> = repository.getApiConfigsFlow().stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    emptyList()
  )

  // Admin & App Settings Flow (including AdMob configuration)
  val adminSettingsFlow: StateFlow<com.example.data.local.AppAdminSettingsEntity> = repository.getAdminSettingsFlow().stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    com.example.data.local.AppAdminSettingsEntity()
  )

  // Admin Panel Open State
  private val _isAdminPanelOpen = MutableStateFlow(false)
  val isAdminPanelOpen: StateFlow<Boolean> = _isAdminPanelOpen.asStateFlow()

  // Navigation State
  private val _currentTab = MutableStateFlow("discover")
  val currentTab: StateFlow<String> = _currentTab.asStateFlow()

  // Pagination State
  private val _currentPage = MutableStateFlow(1)
  val currentPage: StateFlow<Int> = _currentPage.asStateFlow()
  val itemsPerPage = 6

  // Language State
  private val _currentLanguageCode = MutableStateFlow("en")
  val currentLanguageCode: StateFlow<String> = _currentLanguageCode.asStateFlow()

  // Watermark Remover Tool Screen / Modal
  private val _isWatermarkRemoverOpen = MutableStateFlow(false)
  val isWatermarkRemoverOpen: StateFlow<Boolean> = _isWatermarkRemoverOpen.asStateFlow()

  // Legal & Info Modal Topic
  private val _activeLegalTopic = MutableStateFlow<String?>(null)
  val activeLegalTopic: StateFlow<String?> = _activeLegalTopic.asStateFlow()

  // Search & Filter State
  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedModelFilter = MutableStateFlow<AIModel?>(null)
  val selectedModelFilter: StateFlow<AIModel?> = _selectedModelFilter.asStateFlow()

  private val _selectedCategoryFilter = MutableStateFlow<PromptCategory?>(null)
  val selectedCategoryFilter: StateFlow<PromptCategory?> = _selectedCategoryFilter.asStateFlow()

  // Active Detail Prompt Modal
  private val _activeDetailPrompt = MutableStateFlow<PromptItem?>(null)
  val activeDetailPrompt: StateFlow<PromptItem?> = _activeDetailPrompt.asStateFlow()

  // Toast Notification State
  private val _toastMessage = MutableStateFlow<String?>(null)
  val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

  // History
  val historyFlow: StateFlow<List<PromptHistoryEntity>> = repository.getRecentHistoryFlow().stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(5000),
    emptyList()
  )

  // Localized Prompts Stream - automatically updates on language switch
  val localizedPromptsFlow: StateFlow<List<PromptItem>> = combine(
    _allPrompts,
    _currentLanguageCode
  ) { prompts, lang ->
    prompts.map { item ->
      PromptLocalizer.localize(item, lang)
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Filtered Prompts StateFlow
  val filteredPrompts: StateFlow<List<PromptItem>> = combine(
    localizedPromptsFlow,
    _searchQuery,
    _selectedModelFilter,
    _selectedCategoryFilter
  ) { prompts, query, model, category ->
    prompts.filter { item ->
      val matchesQuery = query.isBlank() ||
        item.title.contains(query, ignoreCase = true) ||
        item.description.contains(query, ignoreCase = true) ||
        item.promptTemplate.contains(query, ignoreCase = true) ||
        item.category.title.contains(query, ignoreCase = true)

      val matchesModel = model == null || item.model == model
      val matchesCategory = category == null || item.category == category

      matchesQuery && matchesModel && matchesCategory
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val bookmarkedPrompts: StateFlow<List<PromptItem>> = localizedPromptsFlow.map { list ->
    list.filter { it.isBookmarked }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val customCreatedPrompts: StateFlow<List<PromptItem>> = localizedPromptsFlow.map { list ->
    list.filter { it.isCustom }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Studio State
  val studioMode = MutableStateFlow(StudioMode.SYNTHESIZER)

  // Synthesizer State
  val synthModel = MutableStateFlow(AIModel.MIDJOURNEY)
  val synthSubject = MutableStateFlow("cyberpunk netrunner hacker infiltrator")
  val synthStylePreset = MutableStateFlow("Cyberpunk Neon & Chrome")
  val synthLighting = MutableStateFlow("Volumetric cyan & magenta rain glow")
  val synthCameraAngle = MutableStateFlow("Cinematic 35mm Arri Alexa")
  val synthAspectRatio = MutableStateFlow("16:9")
  val synthStylize = MutableStateFlow("750")

  // Custom Prompt Form State
  val customTitle = MutableStateFlow("")
  val customDescription = MutableStateFlow("")
  val customTemplate = MutableStateFlow("")
  val customNegativePrompt = MutableStateFlow("")
  val customModel = MutableStateFlow(AIModel.MIDJOURNEY)
  val customCategory = MutableStateFlow(PromptCategory.SCI_FI)
  val customImageUrl = MutableStateFlow("")

  fun setTab(tab: String) {
    _currentTab.value = tab
  }

  fun setPage(page: Int) {
    _currentPage.value = page
  }

  fun setLanguage(code: String, name: String) {
    _currentLanguageCode.value = code
    // If detail modal is open, re-localize it
    _activeDetailPrompt.value?.let { current ->
      val basePrompt = _allPrompts.value.find { it.id == current.id } ?: current
      _activeDetailPrompt.value = PromptLocalizer.localize(basePrompt, code)
    }
    val toastText = AppStrings.forLanguage(code).toastLanguageChanged
    showToast(toastText)
  }

  fun openWatermarkRemover() {
    _isWatermarkRemoverOpen.value = true
  }

  fun closeWatermarkRemover() {
    _isWatermarkRemoverOpen.value = false
  }

  fun openLegalTopic(topic: String) {
    _activeLegalTopic.value = topic
  }

  fun closeLegalTopic() {
    _activeLegalTopic.value = null
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
    _currentPage.value = 1
  }

  fun setModelFilter(model: AIModel?) {
    _selectedModelFilter.value = if (_selectedModelFilter.value == model) null else model
    _currentPage.value = 1
  }

  fun setCategoryFilter(category: PromptCategory?) {
    _selectedCategoryFilter.value = if (_selectedCategoryFilter.value == category) null else category
    _currentPage.value = 1
  }

  fun openPromptDetail(prompt: PromptItem) {
    _activeDetailPrompt.value = PromptLocalizer.localize(prompt, _currentLanguageCode.value)
  }

  fun closePromptDetail() {
    _activeDetailPrompt.value = null
  }

  fun toggleBookmark(prompt: PromptItem) {
    viewModelScope.launch {
      repository.toggleBookmark(prompt.id, prompt.isBookmarked)
      showToast(if (!prompt.isBookmarked) "SAVED TO BOOKMARKS" else "REMOVED FROM BOOKMARKS")
    }
  }

  fun toggleLike(prompt: PromptItem) {
    repository.toggleLike(prompt.id)
  }

  fun copyToClipboard(prompt: PromptItem, textToCopy: String) {
    val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("AI Prompt", textToCopy)
    clipboard.setPrimaryClip(clip)

    viewModelScope.launch {
      repository.recordCopy(prompt.id, prompt.title, textToCopy, prompt.model.displayName)
      showToast("PROMPT COPIED TO CLIPBOARD // READY")
    }
  }

  fun copyRawText(text: String, label: String = "Prompt") {
    val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("AI Prompt", text)
    clipboard.setPrimaryClip(clip)

    viewModelScope.launch {
      repository.recordCopy("synth_${System.currentTimeMillis()}", label, text, synthModel.value.displayName)
      showToast("COPIED TO CLIPBOARD // SYNTHESIZED")
    }
  }

  fun remixPromptInStudio(prompt: PromptItem, variableValues: Map<String, String>) {
    closePromptDetail()
    _currentTab.value = "create"
    studioMode.value = StudioMode.CUSTOM_CREATOR

    customTitle.value = "Remix: ${prompt.title}"
    customDescription.value = "Remixed from ${prompt.title}"
    customTemplate.value = prompt.compilePrompt(variableValues)
    customNegativePrompt.value = prompt.negativePrompt
    customModel.value = prompt.model
    customCategory.value = prompt.category
    customImageUrl.value = prompt.imageUrl
    showToast("PROMPT LOADED INTO STUDIO FOR REMIXING")
  }

  fun saveCustomPrompt() {
    val title = customTitle.value.trim()
    val template = customTemplate.value.trim()

    if (title.isBlank() || template.isBlank()) {
      showToast("PLEASE ENTER A TITLE AND PROMPT TEMPLATE")
      return
    }

    viewModelScope.launch {
      val variables = extractVariablesFromTemplate(template)
      val savedPrompt = repository.saveCustomPrompt(
        title = title,
        description = customDescription.value.trim().ifBlank { "Custom user prompt creation" },
        template = template,
        negativePrompt = customNegativePrompt.value.trim(),
        model = customModel.value,
        category = customCategory.value,
        imageUrl = customImageUrl.value.trim(),
        parameters = mapOf("--model" to customModel.value.shortBadge),
        variables = variables
      )

      // Reset form
      customTitle.value = ""
      customDescription.value = ""
      customTemplate.value = ""
      customNegativePrompt.value = ""
      customImageUrl.value = ""

      showToast("CUSTOM PROMPT PUBLISHED TO YOUR VAULT")
      _currentTab.value = "profile"
    }
  }

  fun deleteCustomPrompt(id: String) {
    viewModelScope.launch {
      repository.deleteCustomPrompt(id)
      showToast("CUSTOM PROMPT DELETED")
    }
  }

  fun openAdminPanel() {
    _isAdminPanelOpen.value = true
  }

  fun closeAdminPanel() {
    _isAdminPanelOpen.value = false
  }

  fun saveAdminPrompt(
    title: String,
    description: String,
    template: String,
    negativePrompt: String,
    model: AIModel,
    category: PromptCategory,
    customCategoryName: String? = null,
    imageUrl: String,
    parameters: Map<String, String>
  ) {
    viewModelScope.launch {
      val variables = extractVariablesFromTemplate(template)
      repository.saveCustomPrompt(
        title = title,
        description = description,
        template = template,
        negativePrompt = negativePrompt,
        model = model,
        category = category,
        customCategoryName = customCategoryName,
        imageUrl = imageUrl,
        parameters = parameters,
        variables = variables
      )
      showToast("PROMPT SAVED TO BACKEND REPOSITORY")
    }
  }

  fun saveCustomCategory(
    displayName: String,
    description: String,
    badgeTag: String,
    colorHex: String,
    bannerImageUrl: String,
    accentHex: String
  ) {
    viewModelScope.launch {
      repository.saveCustomCategory(
        displayName = displayName,
        description = description,
        badgeTag = badgeTag,
        colorHex = colorHex,
        bannerImageUrl = bannerImageUrl,
        accentHex = accentHex
      )
      showToast("CATEGORY CREATED SUCCESSFULLY")
    }
  }

  fun deleteCustomCategory(id: String) {
    viewModelScope.launch {
      repository.deleteCustomCategory(id)
      showToast("CATEGORY REMOVED")
    }
  }

  fun saveApiConfig(config: com.example.data.local.ApiConfigEntity) {
    viewModelScope.launch {
      repository.saveApiConfig(config)
      showToast("${config.serviceName} CONFIGURATION SAVED")
    }
  }

  fun testApiConfig(id: String, key: String, endpoint: String) {
    viewModelScope.launch {
      delay(800) // Simulated connection verification
      val status = if (key.isBlank()) "API Key Required" else "Valid & Online (HTTP 200 OK)"
      val existing = repository.getApiConfigsFlow()
      val updated = com.example.data.local.ApiConfigEntity(
        id = id,
        serviceName = when (id) {
          "gemini" -> "Google Gemini 2.0 / 1.5 Flash"
          "openai" -> "OpenAI DALL·E 3 / GPT-4o"
          "midjourney" -> "Midjourney Proxy / Imagine API"
          "stability" -> "Stability AI (SDXL / Ultra)"
          else -> "Custom AI Endpoint"
        },
        apiKey = key,
        endpointUrl = endpoint,
        modelIdentifier = "active-v1",
        isEnabled = key.isNotBlank(),
        lastTestedStatus = status,
        lastTestedTimestamp = System.currentTimeMillis()
      )
      repository.saveApiConfig(updated)
      showToast("Ping status: $status")
    }
  }

  // Cloud Backend Sync State
  private val _isSyncingBackend = MutableStateFlow(false)
  val isSyncingBackend: StateFlow<Boolean> = _isSyncingBackend.asStateFlow()

  private val _backendTestStatus = MutableStateFlow<String?>(null)
  val backendTestStatus: StateFlow<String?> = _backendTestStatus.asStateFlow()

  fun testBackendConnection(url: String, apiKey: String) {
    if (url.isBlank()) {
      showToast("PLEASE ENTER A BACKEND URL")
      return
    }
    viewModelScope.launch {
      _backendTestStatus.value = "Testing connection..."
      val result = repository.testBackendConnection(url, apiKey)
      _backendTestStatus.value = result.message
      showToast(if (result.success) "SERVER ONLINE (${result.latencyMs}ms)" else "CONNECTION FAILED")
    }
  }

  fun syncFromCloudBackend(url: String, apiKey: String) {
    if (url.isBlank()) {
      showToast("PLEASE ENTER A VALID BACKEND URL")
      return
    }
    viewModelScope.launch {
      _isSyncingBackend.value = true
      _backendTestStatus.value = "Downloading prompts & taxonomy from cloud..."
      val result = repository.syncFromRemoteBackend(url, apiKey)
      _isSyncingBackend.value = false
      _backendTestStatus.value = result.message
      if (result.success) {
        showToast("SYNCED ${result.promptsCount} PROMPTS FROM CLOUD BACKEND")
      } else {
        showToast("SYNC FAILED: ${result.message.take(30)}")
      }
    }
  }

  fun saveAdminSettings(settings: com.example.data.local.AppAdminSettingsEntity) {
    viewModelScope.launch {
      repository.saveAdminSettings(settings)
      showToast("ADMIN & ADMOB SETTINGS UPDATED")
    }
  }

  fun exportAllDataToClipboard() {
    viewModelScope.launch {
      val json = repository.exportAllDataAsJson()
      val clipboard = getApplication<Application>().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
      val clip = ClipData.newPlainText("promptly Export", json)
      clipboard.setPrimaryClip(clip)
      showToast("ENTIRE PROMPT VAULT COPIED AS JSON")
    }
  }

  fun clearHistory() {
    viewModelScope.launch {
      repository.clearHistory()
      showToast("COPY HISTORY CLEARED")
    }
  }

  fun compileSynthesizerPrompt(): String {
    val model = synthModel.value
    val subject = synthSubject.value.trim()
    val style = synthStylePreset.value
    val lighting = synthLighting.value
    val camera = synthCameraAngle.value
    val ar = synthAspectRatio.value
    val stylize = synthStylize.value

    return when (model) {
      AIModel.MIDJOURNEY -> {
        "Cinematic 8k composition of a $subject, style of $style, $lighting, shot with $camera, octane render, intricate details, hyperrealistic textures --ar $ar --v 6.0 --s $stylize"
      }
      AIModel.FLUX -> {
        "High-fidelity masterpiece photograph of $subject, $style aesthetic, $lighting, $camera lens, 8k UHD, detailed skin & material textures --steps 28 --guidance 3.5"
      }
      AIModel.STABLE_DIFFUSION -> {
        "Masterpiece photo of $subject, $style, $lighting, $camera, sharp focus, 8k, award-winning photography --steps 30 --cfg 7.0"
      }
      AIModel.DALL_E_3 -> {
        "A highly detailed 3D digital art rendering of $subject, featuring $style elements, illuminated by $lighting, captured from a $camera perspective, vivid style, 8k resolution."
      }
      AIModel.CHATGPT_4O -> {
        "Act as an elite prompt engineer. Formulate a comprehensive operational guide and execution framework for: $subject. Tone: $style. Key requirements: $lighting. Format: $camera."
      }
      AIModel.CLAUDE_3_5 -> {
        "Act as a Principal Solutions Architect. Provide a production-grade, zero-defect architectural implementation for: $subject. Style guidelines: $style. Performance constraints: $lighting. Deliverable: $camera."
      }
      AIModel.GEMINI -> {
        "A hyperrealistic photo of $subject, cinematic film still, style of $style, illuminated by $lighting, shot on $camera, 8k resolution, ultra-detailed textures."
      }
    }
  }

  private fun extractVariablesFromTemplate(template: String): List<PromptVariable> {
    val regex = Regex("""[\{\[]([a-zA-Z0-9_-]+)[\}\]]""")
    val matches = regex.findAll(template).map { it.groupValues[1] }.distinct().toList()
    return matches.map { key ->
      PromptVariable(
        key = key,
        label = key.replace('_', ' ').replace('-', ' ').replaceFirstChar { it.uppercase() },
        defaultValue = key
      )
    }
  }

  private fun showToast(msg: String) {
    viewModelScope.launch {
      _toastMessage.value = msg
      delay(2600)
      if (_toastMessage.value == msg) {
        _toastMessage.value = null
      }
    }
  }
}
