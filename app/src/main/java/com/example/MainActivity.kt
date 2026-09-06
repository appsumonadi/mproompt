package com.example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.PromptCategory
import com.example.ui.components.AdMobInterstitialHelper
import com.example.ui.components.CyberpunkBackground
import com.example.ui.components.CyberpunkToast
import com.example.ui.components.FrostedGlassBottomBar
import com.example.ui.components.LegalInfoModal
import com.example.ui.components.PromptDetailModal
import com.example.ui.i18n.AppStrings
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.screens.admin.AdminPanelScreen
import com.example.ui.screens.categories.CategoriesScreen
import com.example.ui.screens.discover.DiscoverScreen
import com.example.ui.screens.languages.LanguagesScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.splash.CyberpunkSoundEngine
import com.example.ui.screens.splash.SplashScreen
import com.example.ui.screens.studio.StudioScreen
import com.example.ui.screens.watermark.WatermarkRemoverScreen
import com.example.ui.theme.DarkBg
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.PromptViewModel
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // Prewarm cyberpunk audio synthesizer
    CyberpunkSoundEngine.prewarm()

    // Initialize Google Mobile Ads SDK
    try {
      MobileAds.initialize(this) {}
      AdMobInterstitialHelper.loadInterstitial(this, "ca-app-pub-3940256099942544/1033173712")
    } catch (_: Exception) {}

    setContent {
      MyApplicationTheme {
        val viewModel: PromptViewModel = viewModel()
        PrmtlyApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun PrmtlyApp(viewModel: PromptViewModel) {
  val context = LocalContext.current
  val activity = context as? Activity

  val currentTab by viewModel.currentTab.collectAsState()
  val currentPage by viewModel.currentPage.collectAsState()
  val currentLanguageCode by viewModel.currentLanguageCode.collectAsState()
  val isWatermarkRemoverOpen by viewModel.isWatermarkRemoverOpen.collectAsState()
  val isAdminPanelOpen by viewModel.isAdminPanelOpen.collectAsState()
  val activeLegalTopic by viewModel.activeLegalTopic.collectAsState()

  val filteredPrompts by viewModel.filteredPrompts.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()
  val selectedModelFilter by viewModel.selectedModelFilter.collectAsState()
  val selectedCategoryFilter by viewModel.selectedCategoryFilter.collectAsState()
  val activeDetailPrompt by viewModel.activeDetailPrompt.collectAsState()
  val toastMessage by viewModel.toastMessage.collectAsState()
  val adminSettings by viewModel.adminSettingsFlow.collectAsState()
  val unifiedCategories by viewModel.unifiedCategoriesFlow.collectAsState()

  val handleCopyAction: (com.example.data.model.PromptItem, String) -> Unit = { prompt, compiled ->
    viewModel.copyToClipboard(prompt, compiled)
    activity?.let { act ->
      AdMobInterstitialHelper.onPromptCopied(
        activity = act,
        adUnitId = adminSettings.interstitialAdUnitId,
        frequency = adminSettings.interstitialFrequency,
        adsEnabled = adminSettings.adsEnabled
      )
    }
  }

  val appStrings = remember(currentLanguageCode) {
    AppStrings.forLanguage(currentLanguageCode)
  }

  var isSplashVisible by remember { mutableStateOf(true) }
  var showAdminPasswordDialog by remember { mutableStateOf(false) }
  var adminPasswordInput by remember { mutableStateOf("") }
  var adminPasswordError by remember { mutableStateOf(false) }

  CompositionLocalProvider(LocalAppStrings provides appStrings) {
    if (isSplashVisible) {
      SplashScreen(
        onAnimationFinished = { isSplashVisible = false }
      )
    } else {
      CyberpunkBackground {
        Scaffold(
        containerColor = DarkBg,
        bottomBar = {
          if (!isWatermarkRemoverOpen && !isAdminPanelOpen) {
            FrostedGlassBottomBar(
              currentTab = currentTab,
              onTabSelected = { tabId -> viewModel.setTab(tabId) }
            )
          }
        }
      ) { innerPadding ->
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
          if (isAdminPanelOpen) {
            AdminPanelScreen(
              viewModel = viewModel,
              onClose = { viewModel.closeAdminPanel() }
            )
          } else if (isWatermarkRemoverOpen) {
            WatermarkRemoverScreen(
              onBack = { viewModel.closeWatermarkRemover() },
              onShowToast = { msg -> viewModel.copyRawText(msg, "Watermark Action") }
            )
          } else {
            Crossfade(targetState = currentTab, label = "tab_crossfade") { tab ->
              when (tab) {
                "discover" -> {
                  DiscoverScreen(
                    prompts = filteredPrompts,
                    searchQuery = searchQuery,
                    selectedModel = selectedModelFilter,
                    selectedCategory = selectedCategoryFilter,
                    currentPage = currentPage,
                    pageSize = viewModel.itemsPerPage,
                    adminSettings = adminSettings,
                    onOpenAdminPanel = { showAdminPasswordDialog = true },
                    onPageChange = { page -> viewModel.setPage(page) },
                    onSearchQueryChange = { query -> viewModel.setSearchQuery(query) },
                    onModelSelect = { model -> viewModel.setModelFilter(model) },
                    onCategorySelect = { category -> viewModel.setCategoryFilter(category) },
                    onCategoryClear = { viewModel.setCategoryFilter(null) },
                    onPromptClick = { prompt -> viewModel.openPromptDetail(prompt) },
                    onCopyPrompt = { prompt -> handleCopyAction(prompt, prompt.compilePrompt(emptyMap())) },
                    onBookmarkClick = { prompt -> viewModel.toggleBookmark(prompt) },
                    onLikeClick = { prompt -> viewModel.toggleLike(prompt) },
                    onOpenWatermarkTool = { viewModel.openWatermarkRemover() },
                    onNavigateToGallery = { viewModel.setTab("discover") },
                    onNavigateToFavorites = { viewModel.setTab("profile") },
                    onOpenLegalTopic = { topic -> viewModel.openLegalTopic(topic) },
                    onSocialClick = { network -> viewModel.copyRawText("https://t.me/promptly", "$network Link") }
                  )
                }
                "categories" -> {
                  CategoriesScreen(
                    categories = unifiedCategories,
                    onCategorySelect = { category ->
                      viewModel.setCategoryFilter(category)
                      viewModel.setTab("discover")
                    },
                    onCustomCategorySelect = { customName ->
                      viewModel.setSearchQuery(customName)
                      viewModel.setTab("discover")
                    }
                  )
                }
                "profile" -> {
                  ProfileScreen(
                    viewModel = viewModel,
                    onPromptClick = { prompt -> viewModel.openPromptDetail(prompt) },
                    onCopyPrompt = { prompt -> handleCopyAction(prompt, prompt.compilePrompt(emptyMap())) },
                    onBookmarkClick = { prompt -> viewModel.toggleBookmark(prompt) },
                    onLikeClick = { prompt -> viewModel.toggleLike(prompt) },
                    onReplaySplash = { isSplashVisible = true }
                  )
                }
                "languages" -> {
                  LanguagesScreen(
                    currentLanguageCode = currentLanguageCode,
                    onLanguageSelected = { lang ->
                      viewModel.setLanguage(lang.code, lang.nativeName)
                    }
                  )
                }
              }
            }
          }

          // Active Prompt Detail Bottom Sheet Modal
          activeDetailPrompt?.let { prompt ->
            PromptDetailModal(
              prompt = prompt,
              onDismiss = { viewModel.closePromptDetail() },
              onCopyPrompt = { compiledText -> handleCopyAction(prompt, compiledText) },
              onBookmarkClick = { viewModel.toggleBookmark(prompt) },
              onLikeClick = { viewModel.toggleLike(prompt) },
              onRemixInStudio = { item, vars -> viewModel.remixPromptInStudio(item, vars) }
            )
          }

          // Legal / Trust Information Bottom Sheet Modal
          activeLegalTopic?.let { topic ->
            LegalInfoModal(
              topicTitle = topic,
              onDismiss = { viewModel.closeLegalTopic() },
              onSubmitContactForm = { email, msg ->
                viewModel.closeLegalTopic()
                viewModel.copyRawText("Message from $email: $msg", "Support Ticket")
              }
            )
          }

          // Floating Cyberpunk System Toast
          CyberpunkToast(
            message = toastMessage ?: "",
            isVisible = toastMessage != null,
            modifier = Modifier.align(Alignment.BottomCenter)
          )

          if (showAdminPasswordDialog) {
            androidx.compose.material3.AlertDialog(
              onDismissRequest = {
                showAdminPasswordDialog = false
                adminPasswordInput = ""
                adminPasswordError = false
              },
              title = {
                Text("Admin Access Verification", color = com.example.ui.theme.TextMain, fontWeight = FontWeight.Bold)
              },
              text = {
                androidx.compose.foundation.layout.Column(verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)) {
                  Text("Enter admin password to unlock master dashboard:", color = com.example.ui.theme.TextMuted, fontSize = 13.sp)
                  androidx.compose.material3.OutlinedTextField(
                    value = adminPasswordInput,
                    onValueChange = {
                      adminPasswordInput = it
                      adminPasswordError = false
                    },
                    label = { Text("Admin Password", color = com.example.ui.theme.TextMuted) },
                    singleLine = true,
                    isError = adminPasswordError,
                    visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                      focusedBorderColor = com.example.ui.theme.OrangePrimary,
                      unfocusedBorderColor = com.example.ui.theme.DarkCardBorder,
                      focusedTextColor = com.example.ui.theme.TextMain,
                      unfocusedTextColor = com.example.ui.theme.TextMain
                    ),
                    modifier = Modifier.fillMaxWidth()
                  )
                  if (adminPasswordError) {
                    Text("Incorrect password. Please try again.", color = Color.Red, fontSize = 12.sp)
                  }
                }
              },
              confirmButton = {
                androidx.compose.material3.Button(
                  onClick = {
                    if (adminPasswordInput == adminSettings.adminPin) {
                      showAdminPasswordDialog = false
                      adminPasswordInput = ""
                      adminPasswordError = false
                      viewModel.openAdminPanel()
                    } else {
                      adminPasswordError = true
                    }
                  },
                  colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = com.example.ui.theme.OrangePrimary)
                ) {
                  Text("Unlock", color = Color.Black, fontWeight = FontWeight.Bold)
                }
              },
              dismissButton = {
                androidx.compose.material3.TextButton(
                  onClick = {
                    showAdminPasswordDialog = false
                    adminPasswordInput = ""
                    adminPasswordError = false
                  }
                ) {
                  Text("Cancel", color = com.example.ui.theme.TextMuted)
                }
              },
              containerColor = com.example.ui.theme.DarkCardBg,
              shape = RoundedCornerShape(16.dp)
            )
          }
        }
      }
    }
  }
}
}

