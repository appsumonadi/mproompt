package com.example.ui.i18n



import androidx.compose.runtime.Composable

import androidx.compose.runtime.compositionLocalOf

import com.example.data.model.PromptCategory



/**

 * Complete UI Translations Data Structure for Prompteg AI Prompt Hub

 */

open class AppLanguageStrings(
  open val languageCode: String = "en",
  open val languageName: String = "English"
) {


  // Navigation Tabs

  open val navDiscover: String = "Discover"
  open val navCategories: String = "Categories"
  open val navFavorites: String = "Favorites"
  open val navLanguages: String = "Languages"


  // Discover Screen - Hero & Headers

  open val heroBadge: String = "AI PROMPT VAULT 2026"
  open val heroTitle: String = "Curated AI Prompts for Midjourney, FLUX & DALL·E 3"
  open val heroSubtitle: String = "Search, copy, and customize thousands of production-grade neural prompts."
  open val searchPlaceholder: String = "Search prompts by keyword, subject, style, or camera..."
  open val adminPinAccess: String = "ADMIN HUB"
  open val rollRandom: String = "Roll Random"


  // Quick Stats

  open val statsPrompts: String = "Prompts"
  open val statsCopies: String = "Copies"
  open val statsStyles: String = "Styles"
  open val statsModels: String = "Models"


  // Trending & Feed

  open val trendingBadge: String = "HOT THIS WEEK"
  open val trendingTitle: String = "Trending Prompts"
  open val trendingSubtitle: String = "Most popular prompt formulas copied by creators across the globe."
  open val allPromptsBadge: String = "INDEXED FEED"
  open val allPromptsTitle: String = "All Neural Prompts"
  open val noPromptsFound: String = "No prompts found matching your criteria"
  open val noPromptsSub: String = "Try searching a different keyword or clearing active filters."
  open val resetFilters: String = "Reset Filters"
  open val pageIndicator: String = "Page"


  // Filters & Models

  open val filterAll: String = "All Models"
  open val filterByCategory: String = "Category"
  open val clearCategoryFilter: String = "Clear Filter"


  // Prompt Card Actions

  open val copyPrompt: String = "Copy"
  open val copiedBadge: String = "Copied"
  open val bookmarkTooltip: String = "Bookmark"
  open val likeTooltip: String = "Like"
  open val viewBlueprint: String = "Blueprint"
  open val remixAction: String = "Remix"


  // Categories Screen

  open val categoriesBadge: String = "TAXONOMY & STYLES"
  open val categoriesTitle: String = "Categories"
  open val categoriesSubtitle: String = "Explore curated aesthetics, art styles, cinematic compositions, and technical workflows."
  open val searchCategoriesPlaceholder: String = "Search categories and styles..."
  open val exploreCategoryBtn: String = "Explore Prompts"
  open val promptsCountSuffix: String = "Prompts"
  open val customTag: String = "CUSTOM"


  // Localized Category Titles & Subtitles

  open val catSciFiTitle: String = "Sci-Fi & Cyberpunk"
  open val catSciFiSub: String = "Futuristic tech, neon noir & spaceships"
  open val catHyperrealismTitle: String = "Hyperrealism"
  open val catHyperrealismSub: String = "Photorealistic 8K portraits & raw textures"
  open val catAnimeTitle: String = "Anime & Manga"
  open val catAnimeSub: String = "Makoto Shinkai & Studio Ghibli aesthetics"
  open val catArchitectureTitle: String = "Architecture & Interior"
  open val catArchitectureSub: String = "Zaha Hadid brutalism & luxury villa spaces"
  open val catDigitalArtTitle: String = "Digital Art & Illustration"
  open val catDigitalArtSub: String = "Concept art, octane render & stylized vectors"
  open val catPortraitsTitle: String = "Cinematic Portraits"
  open val catPortraitsSub: String = "Dramatic lighting & high-fashion studio bokeh"
  open val catFantasyTitle: String = "Fantasy & Mythic"
  open val catFantasySub: String = "Ethereal deities, dragons & arcane spells"
  open val catProduct3DTitle: String = "Product & 3D Render"
  open val catProduct3DSub: String = "Commercial mockups & studio clay lighting"
  open val catSurrealismTitle: String = "Surrealism & Abstract"
  open val catSurrealismSub: String = "Dreamscapes & dimensional geometry"
  open val catMinimalistTitle: String = "Minimalist & Vectors"
  open val catMinimalistSub: String = "Clean typography, line art & flat iconography"
  open val catDarkFantasyTitle: String = "Dark Fantasy & Gothic"
  open val catDarkFantasySub: String = "Eldritch horrors, obsidian armor & grim castles"
  open val catStreetPhotoTitle: String = "Street Photography"
  open val catStreetPhotoSub: String = "Candid urban moments & 35mm film grain"
  open val catNatureWildlifeTitle: String = "Nature & Wildlife"
  open val catNatureWildlifeSub: String = "National Geographic macros & landscapes"
  open val catFashionEditorialTitle: String = "Fashion & Editorial"
  open val catFashionEditorialSub: String = "Haute couture runway & magazine shoots"
  open val catPixelRetroTitle: String = "Pixel & Retro 80s"
  open val catPixelRetroSub: String = "16-bit sprites, synthwave grids & VHS vibes"


  // Favorites / Vault Screen

  open val vaultBadge: String = "NEURAL REPOSITORY"
  open val vaultTitle: String = "Saved Prompts & Vault"
  open val vaultSubtitle: String = "Your bookmarked creations, custom engineered prompts, and recent clipboard history."
  open val tabBookmarks: String = "Bookmarks"
  open val tabCustomPrompts: String = "My Prompts"
  open val tabCopyHistory: String = "History"
  open val emptyBookmarksTitle: String = "No bookmarked prompts yet"
  open val emptyBookmarksSub: String = "Tap the bookmark icon on any prompt in Discover to save it here."
  open val emptyCustomTitle: String = "No custom prompts created yet"
  open val emptyCustomSub: String = "Create your own custom prompts in Studio or Admin panel."
  open val emptyHistoryTitle: String = "Clipboard history is empty"
  open val emptyHistorySub: String = "Copied prompts will appear here for instant re-use."
  open val clearHistoryBtn: String = "Clear History"
  open val createPromptBtn: String = "Create Prompt"
  open val adminControlBtn: String = "Admin Panel"
  open val totalBookmarksLabel: String = "Bookmarked"
  open val totalCustomLabel: String = "Custom Built"
  open val totalHistoryLabel: String = "Copied History"


  // Profile Screen specifics

  open val profileTitle: String = "Prompt Vault"
  open val statSaved: String = "Saved"
  open val statCustom: String = "Custom"
  open val statCopies: String = "Copies"
  open val adminPanelTitle: String = "Master Database & Monetization"
  open val adminPanelSubtitle: String = "Configure AdMob units, update remote prompt catalogs, and manage tags"
  open val manage: String = "MANAGE"
  open val tabSaved: String = "SAVED"
  open val tabVault: String = "MY VAULT"
  open val tabHistory: String = "HISTORY"
  open val noSavedBookmarks: String = "No Bookmarks Yet"
  open val noSavedBookmarksSub: String = "Bookmark prompts from the Discover feed to easily reference and remix them anytime."
  open val vaultEmpty: String = "Vault is Empty"
  open val vaultEmptySub: String = "Synthesize blueprints in the Studio or craft custom prompts to save them here."
  open val openDetail: String = "DETAIL"
  open val noHistory: String = "No Copy History"
  open val noHistorySub: String = "When you copy prompt formulas from Prompteg, they will be logged here for quick retrieval."
  open val recentCopied: String = "RECENTLY COPIED PROMPTS"
  open val clearHistory: String = "CLEAR HISTORY"
  open val copyAgain: String = "COPY AGAIN"


  // Studio Screen

  open val studioTitle: String = "Prompt Studio"
  open val studioSubtitle: String = "Craft, tweak, and synthesize high-octane prompt blueprints"
  open val crafterTab: String = "AI CRAFTER"
  open val customTab: String = "CUSTOM VAULT"
  open val targetEngine: String = "TARGET ENGINE"
  open val subjectLabel: String = "SUBJECT / CORE SCENE"
  open val subjectPlaceholder: String = "Describe the hero subject, entity, or topic..."
  open val aspectRatioLabel: String = "ASPECT RATIO"
  open val liveSynthesizedPrompt: String = "LIVE SYNTHESIZED BLUEPRINT"
  open val charsCount: String = "chars"
  open val copySynthPrompt: String = "COPY SYNTHESIZED PROMPT"
  open val promptTitleLabel: String = "PROMPT TITLE"
  open val shortDescLabel: String = "SHORT DESCRIPTION"
  open val promptTemplateLabel: String = "PROMPT TEMPLATE"
  open val promptTemplatePlaceholder: String = "Cinematic 8k of {subject}, {lighting}, hyperrealistic --ar 16:9 --v 6.0"
  open val negativePromptLabel: String = "NEGATIVE PROMPT (OPTIONAL)"
  open val publishToVault: String = "PUBLISH TO MY VAULT"


  // Detail Modal specifics

  open val variableTuner: String = "INTERACTIVE VARIABLE TUNER"
  open val suggestionsLabel: String = "Suggestions:"
  open val compiledPrompt: String = "COMPILED PROMPT"
  open val parametersLabel: String = "ENGINE PARAMETERS"
  open val remixPrompt: String = "REMIX IN STUDIO"


  // Languages Screen

  open val languagesBadge: String = "LOCALIZATION & REGION"
  open val languagesTitle: String = "Languages"
  open val languagesSubtitle: String = "Choose your preferred language for prompts, UI labels, and AI generation parameters."
  open val searchLanguagesPlaceholder: String = "Search language name or country..."
  open val activeLanguageLabel: String = "Active"
  open val selectedLanguageLabel: String = "Selected"


  // Prompt Detail Modal

  open val modalBlueprintTitle: String = "Prompt Blueprint"
  open val modalBadge: String = "NEURAL SPEC"
  open val copyPromptBtn: String = "Copy Prompt"
  open val remixInStudioBtn: String = "Remix in Studio"
  open val variablesSectionTitle: String = "Prompt Variables"
  open val negativePromptTitle: String = "Negative Prompt"
  open val parametersTitle: String = "Engine Parameters"
  open val targetModelTitle: String = "Target Model"
  open val categoryTitle: String = "Category"
  open val aspectRatioTitle: String = "Aspect Ratio"
  open val closeModalBtn: String = "Close"


  // Watermark Remover Screen

  open val watermarkBadge: String = "IMAGE PROCESSING ENGINE"
  open val watermarkTitle: String = "AI Watermark Remover"
  open val watermarkSubtitle: String = "Clean, sharpen, and restore AI generated images using neural inpainting."
  open val uploadImageHint: String = "Select or drop image to clean"
  open val removeWatermarkBtn: String = "Remove Watermark"
  open val processingNeural: String = "Processing neural layers..."
  open val downloadCleanBtn: String = "Download Clean Image"
  open val sliderLabel: String = "Drag to Compare"
  open val sampleLibraryTitle: String = "Demo Benchmark Library"
  open val backToDiscoverBtn: String = "Back to Discover"
  open val watermarkCleanNow: String = "CLEAN WATERMARK NOW"
  open val watermarkDownloadClean: String = "Download Clean HD"
  open val watermarkReset: String = "Reset"
  open val watermarkUploadCustom: String = "Upload My Own Photo / Gemini Image"
  open val watermarkTrySample: String = "Try with Sample Watermarked Images:"
  open val watermarkProcessingLabel: String = "Inpainting & Erasing Watermark..."
  open val watermarkOriginalBadge: String = "ORIGINAL (WATERMARKED)"
  open val watermarkCleanedBadge: String = "CLEANED WITH AI"
  open val watermarkFreeBadge: String = "100% FREE"


  // Admin Panel Hub & Auth

  open val adminHubTitle: String = "PROMPTEG ADMIN HUB"
  open val adminAuthFullControl: String = "AUTHENTICATED // FULL CONTROL"
  open val adminSecurityLocked: String = "SECURITY LOCKED // ENTER PIN"
  open val adminRootActive: String = "ROOT ACTIVE"
  open val adminAuthTitle: String = "ADMIN AUTHENTICATION"
  open val adminAuthSub: String = "Enter your security PIN to manage Prompts, Categories, API Keys, and AdMob settings."
  open val adminPinDefaultLabel: String = "Admin PIN (Default: 1234)"
  open val adminAccessControlBtn: String = "ACCESS CONTROL PANEL"
  open val adminWelcomeToast: String = "Welcome Admin"
  open val adminInvalidPinToast: String = "Invalid PIN"


  // Admin Navigation Tabs

  open val adminTabOverview: String = "Overview"
  open val adminTabBackend: String = "PHP Cloud"
  open val adminTabAddPrompt: String = "Add Prompt"
  open val adminTabVault: String = "Vault"
  open val adminTabCategories: String = "Categories"
  open val adminTabApis: String = "APIs & Keys"
  open val adminTabAdMob: String = "AdMob & Ads"
  open val adminTabSettings: String = "Settings"


  // Admin Metrics & Overview

  open val adminMetricsHeader: String = "SYSTEM METRICS & DATABASE SUMMARY"
  open val adminTotalPrompts: String = "TOTAL PROMPTS"
  open val adminCustomGenerated: String = "custom generated"
  open val adminActiveTaxonomy: String = "Active taxonomy groups"
  open val adminAiApis: String = "AI APIS"
  open val adminAdMobAds: String = "ADMOB ADS"
  open val adminStatusActive: String = "ACTIVE"
  open val adminStatusMuted: String = "MUTED"
  open val adminBannerInterstitials: String = "Banner & Interstitials"
  open val adminQuickActions: String = "QUICK ADMINISTRATIVE ACTIONS"
  open val adminBadgeLive: String = "LIVE"


  // Admin Quick Actions

  open val adminActionBackendTitle: String = "PHP Cloud Backend & Remote Database Sync"
  open val adminActionBackendSub: String = "Connect to your external website admin, sync prompts & settings"
  open val adminActionAddPromptTitle: String = "Add New AI Prompt with Live Parameters"
  open val adminActionAddPromptSub: String = "Custom tags, negative prompt, aspect ratio & variables"
  open val adminActionCategoryTitle: String = "Create New Prompt Category"
  open val adminActionCategorySub: String = "Add custom badge tags, cover art & theme colors"
  open val adminActionApisTitle: String = "Configure AI Model Endpoints & API Keys"
  open val adminActionApisSub: String = "Google Gemini 2.0, Midjourney Proxy, DALL-E & Stability"
  open val adminActionAdMobTitle: String = "AdMob Monetization & Ad Frequency"
  open val adminActionAdMobSub: String = "Banner Ad IDs, Interstitial triggers & live test simulator"


  // Admin Add Prompt Section

  open val adminAddPromptHeader: String = "ADD NEW AI PROMPT // BACKEND BUILDER"
  open val adminAddPromptSub: String = "Add prompts that will immediately appear in the library with variable controls and copy features."
  open val adminPromptTitleLabel: String = "Prompt Title *"
  open val adminPromptTitlePlaceholder: String = "e.g. Cyber Samurai in Neo-Tokyo"
  open val adminPromptDescLabel: String = "Short Description / Style Summary"
  open val adminPromptDescPlaceholder: String = "e.g. Neon reflections, rain-slicked pavement & katana glow"
  open val adminAiModelLabel: String = "AI MODEL"
  open val adminCategoryLabel: String = "PROMPT CATEGORY"
  open val adminPromptTemplateLabel: String = "Prompt Text / Template (use {variable} for custom inputs) *"
  open val adminPromptTemplatePlaceholder: String = "Cinematic 8k portrait of {subject} wearing {attire}, {lighting} --ar 16:9"
  open val adminNegativePromptLabel: String = "Negative Prompt (Optional)"
  open val adminNegativePromptPlaceholder: String = "blurry, low quality, distorted anatomy, extra limbs"
  open val adminImageUrlLabel: String = "Cover Image URL"
  open val adminImageUrlPlaceholder: String = "https://images.unsplash.com/photo-..."
  open val adminParametersLabel: String = "Engine Parameters"
  open val adminPublishPromptBtn: String = "PUBLISH PROMPT TO BACKEND"
  open val adminSavePromptSuccess: String = "Prompt Published to Vault!"


  // Admin Prompts List Section

  open val adminPromptsListHeader: String = "PROMPT REPOSITORY"
  open val adminPromptsListSub: String = "Inspect, edit and delete prompts"
  open val adminAddNewPromptBtn: String = "Add New"
  open val adminDeletePromptBtn: String = "Delete"


  // Admin Categories Section

  open val adminCategoriesHeader: String = "CATEGORY ARCHITECTURE"
  open val adminCategoriesSub: String = "Add custom taxonomy groups to organize prompts across the app."
  open val adminAddCategoryHeader: String = "CREATE NEW CATEGORY"
  open val adminCategoryNameLabel: String = "Category Name *"
  open val adminCategoryNamePlaceholder: String = "e.g. Anime & Manga Art"
  open val adminCategoryDescLabel: String = "Description / Scope"
  open val adminCategoryDescPlaceholder: String = "Shonen anime characters, cel-shading & vibrant scenes"
  open val adminCategoryBadgeLabel: String = "Short Badge Tag (e.g. ANIME)"
  open val adminCategoryBannerLabel: String = "Banner Image URL (Optional)"
  open val adminCategoryAccentColor: String = "ACCENT COLOR THEME"
  open val adminSaveCategoryBtn: String = "SAVE CATEGORY"


  // Admin APIs Section

  open val adminApisHeader: String = "AI MODEL ENDPOINTS & API KEYS"
  open val adminApisSub: String = "Configure server-side proxy routes and API keys for AI image generation."
  open val adminApiKeyLabel: String = "API Key"
  open val adminBaseUrlLabel: String = "Endpoint URL"
  open val adminSaveApiBtn: String = "SAVE CONFIGURATION"


  // Admin AdMob Section

  open val adminAdMobHeader: String = "ADMOB MONETIZATION CONFIGURATION"
  open val adminAdMobSub: String = "Manage interstitial frequency, banner units, and native ad placements."
  open val adminAdsEnabledLabel: String = "Enable AdMob Monetization"
  open val adminBannerAdUnitLabel: String = "Banner Ad Unit ID"
  open val adminInterstitialAdUnitLabel: String = "Interstitial Ad Unit ID"
  open val adminSaveAdMobBtn: String = "SAVE ADMOB CONFIG"


  // Admin Settings Section

  open val adminSettingsHeader: String = "APP ADMINISTRATION & GLOBAL SETTINGS"
  open val adminSettingsSub: String = "Manage admin PIN, announcements, remote sync, and database resets."
  open val adminChangePinLabel: String = "Master Security PIN"
  open val adminAnnouncementLabel: String = "Global Announcement Banner"
  open val adminSaveSettingsBtn: String = "SAVE ALL SETTINGS"


  // Admin Backend Section

  open val adminBackendHeader: String = "PHP CLOUD BACKEND & SYNC"
  open val adminBackendSub: String = "Connect to your external website admin, sync prompts & settings in real-time."
  open val adminBackendUrlLabel: String = "PHP Admin API URL"
  open val adminBackendApiKeyLabel: String = "Secret Sync API Key"
  open val adminTestConnectionBtn: String = "TEST CONNECTION"
  open val adminSyncNowBtn: String = "SYNC PROMPTS NOW"


  open val adminSecurityTitle: String = "Admin Security Verification"
  open val adminSecuritySub: String = "Default PIN is 1234. Manage app repository, categories, monetization, and API endpoints."
  open val enterPinPlaceholder: String = "Enter 4-Digit Admin PIN..."
  open val unlockAdminBtn: String = "Unlock Admin Hub"
  open val invalidPinMsg: String = "Invalid PIN code. Access denied."
  open val adminTabNewPrompt: String = "New Prompt"
  open val adminTabModels: String = "AI Models"
  open val adminTabMonetization: String = "Monetization"
  open val saveSettingsBtn: String = "Save Changes"
  open val exportJsonBtn: String = "Export Vault JSON"
  open val closeAdminBtn: String = "Close Admin"


  // Footer & Legal

  open val footerSlogan: String = "The world's premier generative AI prompt database & creative hub."
  open val privacyPolicyLink: String = "Privacy Policy"
  open val termsOfServiceLink: String = "Terms of Service"
  open val communityGuidelinesLink: String = "Community Guidelines"
  open val apiAccessLink: String = "API Documentation"
  open val contactSupportLink: String = "Contact Support"
  open val watermarkToolLink: String = "Watermark Remover"
  open val copyrightText: String = "© 2026 Prompteg AI. All rights reserved."
  open val contactSubmitBtn: String = "Send Message"
  open val emailPlaceholder: String = "Your email address"
  open val messagePlaceholder: String = "Describe your question or feedback..."


  // Toast Notifications

  open val toastCopied: String = "PROMPT COPIED TO CLIPBOARD // READY"
  open val toastBookmarkAdded: String = "SAVED TO BOOKMARKS"
  open val toastBookmarkRemoved: String = "REMOVED FROM BOOKMARKS"
  open val toastHistoryCleared: String = "COPY HISTORY CLEARED"
  open val toastPromptSaved: String = "PROMPT SAVED TO VAULT"
  open val toastLanguageChanged: String = "Language updated successfully"


  // Footer Extended

  open val footerPlatformDesc: String = "AI photo editing prompts library featuring premium, tested prompts for ChatGPT, Gemini, and AI image editors. Create realistic, cinematic, and professional photo edits faster."
  open val footerWatermarkBtn: String = "Gemini Watermark Remover"
  open val footerNavHeader: String = "NAVIGATION"
  open val footerNavGallery: String = "Gallery"
  open val footerNavFavorites: String = "Favorites"
  open val footerNavWatermark: String = "Watermark Eraser Engine"
  open val footerNavAbout: String = "About Us"
  open val footerTrustHeader: String = "TRUST & LEGAL"
  open val footerTrustPlatform: String = "About Platform"
  open val footerTrustContact: String = "Contact & Support"
  open val footerTrustPrivacy: String = "Privacy Policy"
  open val footerTrustTerms: String = "Terms of Service"
  open val footerTrustDmca: String = "DMCA & Disclaimer"
  open val footerCopyright: String = "© 2026 Prompteg - AI Photo Editing Prompts Library. All rights reserved."


  // Watermark Banner Component & Tool

  open val watermarkBadgeText: String = "FREE AI WATERMARK TOOL"
  open val watermarkBannerTitle: String = "Gemini Watermark Remover"
  open val watermarkBannerSub: String = "Effortlessly remove watermarks from Gemini, Midjourney, and AI editor generated images."
  open val watermarkSub: String = "AI-Powered Inpainting Engine"
  open val watermarkFeat1: String = "Instant Neural Inpainting"
  open val watermarkFeat2: String = "100% Free & Unlimited"
  open val watermarkFeat3: String = "High-Res Export Quality"
  open val watermarkLaunchBtn: String = "Launch Watermark Tool"
  open val watermarkLoadedToast: String = "Custom image loaded into Watermark Eraser"


  // Extended Admin Controls

  open val adminEnableAds: String = "ENABLE ADMOB ADS"
  open val adminAdsActive: String = "Ads active across application"
  open val adminAdsDisabled: String = "All ads globally disabled"
  open val adminBannerIdLabel: String = "Banner Ad Unit ID"
  open val adminInterstitialIdLabel: String = "Interstitial Ad Unit ID"
  open val adminInterstitialFreqLabel: String = "Interstitial Frequency (Show every X copies)"
  open val adminResetTestIdsBtn: String = "Reset Test IDs"
  open val adminLiveBannerPreview: String = "LIVE ADMOB BANNER PREVIEW"
  open val adminSecurityPinHeader: String = "ADMIN SECURITY PIN"
  open val adminPinLabel: String = "Security PIN"
  open val adminAnnouncementHeader: String = "GLOBAL ANNOUNCEMENT BANNER"
  open val adminAnnouncementSub: String = "Broadcast alert at the top of Discover screen"
  open val adminSaveConfigBtn: String = "SAVE CONFIGURATION"
  open val adminExportBackupHeader: String = "DATABASE EXPORT & BACKUP"
  open val adminExportBackupSub: String = "Export the entire prompt vault and category taxonomy as a formatted JSON document."
  open val adminExportVaultBtn: String = "EXPORT VAULT TO JSON CLIPBOARD"
  open val adminBackendCoords: String = "REMOTE BACKEND COORDINATES"
  open val adminConfigured: String = "CONFIGURED"
  open val adminOffline: String = "OFFLINE"
  open val adminSaveBackendBtn: String = "SAVE BACKEND CONFIGURATION"
  open val adminCloudSyncHeader: String = "CLOUD SYNC OPERATIONS"
  open val adminCloudSyncSub: String = "Pull all active prompts, categories, announcements and AdMob configurations from your PHP backend database."
  open val adminTestPingBtn: String = "TEST PING"
  open val adminSyncingBtn: String = "SYNCING..."
  open val adminSyncVaultBtn: String = "SYNC CLOUD VAULT"
  open val adminLastStatus: String = "Last Status"


  // Splash Screen Intro Replay in Profile & Cyberpunk Sound

  open val splashIntroBtnTitle: String = "CINEMATIC INTRO ANIMATION"
  open val splashIntroBtnSub: String = "Replay the high-voltage cyberpunk splash screen"
  open val splashIntroPlay: String = "PLAY"
  open val splashSoundOn: String = "SFX ON"
  open val splashSoundOff: String = "SFX MUTED"
  open val splashSkipHint: String = "TAP ANYWHERE TO SKIP"
  open val profileTierBadge: String = "PROMPTEG PRO // CREATOR TIER"


  // Card & Common

  open val cardExploreAction: String = "Explore"
  open val imageUnavailable: String = "IMAGE UNAVAILABLE"
  open val pageOf: String = "of"
  open fun getCategoryTitle(category: PromptCategory): String {
    return when (category) {

      PromptCategory.REALISTIC -> catHyperrealismTitle

      PromptCategory.THREE_D -> catProduct3DTitle

      PromptCategory.CINEMATIC -> catPortraitsTitle

      PromptCategory.CAR -> catStreetPhotoTitle

      PromptCategory.ANIMALS -> catNatureWildlifeTitle

      PromptCategory.ANIME -> catAnimeTitle

      PromptCategory.ART -> catDigitalArtTitle

      PromptCategory.PORTRAITS -> catFashionEditorialTitle

      PromptCategory.DRAWING -> catMinimalistTitle

      PromptCategory.PRODUCTS -> catProduct3DTitle

      PromptCategory.VIDEO -> catFantasyTitle

      PromptCategory.SCI_FI -> catSciFiTitle

      PromptCategory.MARKETING -> catDarkFantasyTitle

      PromptCategory.CODING -> catPixelRetroTitle

      PromptCategory.DESIGN -> catArchitectureTitle

    }

  }



  open fun getCategorySubtitle(category: PromptCategory): String {
    return when (category) {

      PromptCategory.REALISTIC -> catHyperrealismSub

      PromptCategory.THREE_D -> catProduct3DSub

      PromptCategory.CINEMATIC -> catPortraitsSub

      PromptCategory.CAR -> catStreetPhotoSub

      PromptCategory.ANIMALS -> catNatureWildlifeSub

      PromptCategory.ANIME -> catAnimeSub

      PromptCategory.ART -> catDigitalArtSub

      PromptCategory.PORTRAITS -> catFashionEditorialSub

      PromptCategory.DRAWING -> catMinimalistSub

      PromptCategory.PRODUCTS -> catProduct3DSub

      PromptCategory.VIDEO -> catFantasySub

      PromptCategory.SCI_FI -> catSciFiSub

      PromptCategory.MARKETING -> catDarkFantasySub

      PromptCategory.CODING -> catPixelRetroSub

      PromptCategory.DESIGN -> catArchitectureSub

    }

  }

}



val LocalAppStrings = compositionLocalOf<AppLanguageStrings> {

  AppStrings.English

}


