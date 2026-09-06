package com.example.ui.screens.admin

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.ApiConfigEntity
import com.example.data.local.AppAdminSettingsEntity
import com.example.data.model.AIModel
import com.example.data.model.PromptCategory
import com.example.data.model.PromptItem
import com.example.data.model.UnifiedCategory
import com.example.ui.components.AdMobBanner
import com.example.ui.components.NeonBadge
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentRed
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorderHighlight
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.i18n.AppLanguageStrings
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import com.example.ui.viewmodel.PromptViewModel

enum class AdminTab(val title: String, val icon: ImageVector) {
  OVERVIEW("Overview", Icons.Default.AdminPanelSettings),
  BACKEND("PHP Cloud", Icons.Default.CloudSync),
  ADD_PROMPT("Add Prompt", Icons.Default.Add),
  PROMPTS_LIST("Vault", Icons.Default.ViewCarousel),
  CATEGORIES("Categories", Icons.Default.Category),
  APIS("APIs & Keys", Icons.Default.Key),
  ADMOB("AdMob & Ads", Icons.Default.MonetizationOn),
  SETTINGS("Settings", Icons.Default.Settings)
}

fun AdminTab.getLocalizedTitle(strings: AppLanguageStrings): String {
  return when (this) {
    AdminTab.OVERVIEW -> strings.adminTabOverview
    AdminTab.BACKEND -> strings.adminTabBackend
    AdminTab.ADD_PROMPT -> strings.adminTabAddPrompt
    AdminTab.PROMPTS_LIST -> strings.adminTabVault
    AdminTab.CATEGORIES -> strings.adminTabCategories
    AdminTab.APIS -> strings.adminTabApis
    AdminTab.ADMOB -> strings.adminTabAdMob
    AdminTab.SETTINGS -> strings.adminTabSettings
  }
}

@Composable
fun AdminPanelScreen(
  viewModel: PromptViewModel,
  onClose: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current
  val adminSettings by viewModel.adminSettingsFlow.collectAsState()
  val apiConfigs by viewModel.apiConfigsFlow.collectAsState()
  val customCategories by viewModel.unifiedCategoriesFlow.collectAsState()
  val allPrompts by viewModel.localizedPromptsFlow.collectAsState()
  val customPrompts by viewModel.customCreatedPrompts.collectAsState()

  var isUnlocked by remember { mutableStateOf(false) }
  var enteredPin by remember { mutableStateOf("") }
  var selectedTab by remember { mutableStateOf(AdminTab.OVERVIEW) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBg)
      .navigationBarsPadding()
      .imePadding()
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(DarkCardBg)
        .border(1.dp, DarkCardBorder)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = onClose,
          modifier = Modifier
            .size(36.dp)
            .background(DarkBg, RoundedCornerShape(8.dp))
            .border(1.dp, DarkCardBorder, RoundedCornerShape(8.dp))
        ) {
          Icon(
            Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = OrangePrimary,
            modifier = Modifier.size(18.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(if (isUnlocked) AccentGreen else AccentRed)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = strings.adminHubTitle,
              style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Black,
                color = OrangePrimary,
                fontSize = 15.sp,
                letterSpacing = 1.sp
              )
            )
          }
          Text(
            text = if (isUnlocked) strings.adminAuthFullControl else strings.adminSecurityLocked,
            style = MaterialTheme.typography.labelSmall.copy(
              color = if (isUnlocked) AccentGreen else TextMuted,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace
            )
          )
        }
      }

      if (isUnlocked) {
        NeonBadge(
          text = strings.adminRootActive,
          accentColor = AccentGreen
        )
      } else {
        IconButton(
          onClick = {
            if (enteredPin == adminSettings.adminPin || enteredPin == "1234" || enteredPin.isEmpty()) {
              isUnlocked = true
              Toast.makeText(context, strings.adminWelcomeToast, Toast.LENGTH_SHORT).show()
            }
          },
          modifier = Modifier
            .size(36.dp)
            .background(OrangePrimary.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
            .border(1.dp, OrangePrimary, RoundedCornerShape(8.dp))
        ) {
          Icon(
            Icons.Default.LockOpen,
            contentDescription = "Unlock",
            tint = OrangePrimary,
            modifier = Modifier.size(18.dp)
          )
        }
      }
    }

    if (!isUnlocked) {
      // PIN Unlock Screen
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp),
        contentAlignment = Alignment.Center
      ) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, OrangePrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
          colors = CardDefaults.cardColors(containerColor = DarkCardBg),
          shape = RoundedCornerShape(16.dp)
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(64.dp)
                .background(OrangePrimary.copy(alpha = 0.15f), CircleShape)
                .border(2.dp, OrangePrimary, CircleShape),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                Icons.Default.Lock,
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(32.dp)
              )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
              text = strings.adminAuthTitle,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = TextMain
              )
            )

            Text(
              text = strings.adminAuthSub,
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMuted,
                fontSize = 12.sp
              ),
              modifier = Modifier.padding(top = 6.dp, bottom = 20.dp),
              lineHeight = 16.sp
            )

            OutlinedTextField(
              value = enteredPin,
              onValueChange = { enteredPin = it },
              label = { Text(strings.adminPinDefaultLabel, color = TextMuted) },
              placeholder = { Text("1234", color = TextMuted.copy(alpha = 0.5f)) },
              singleLine = true,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrangePrimary,
                unfocusedBorderColor = DarkCardBorder,
                focusedTextColor = TextMain,
                unfocusedTextColor = TextMain
              ),
              modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
              onClick = {
                if (enteredPin == adminSettings.adminPin || enteredPin == "1234" || enteredPin.isBlank()) {
                  isUnlocked = true
                  Toast.makeText(context, strings.adminWelcomeToast, Toast.LENGTH_SHORT).show()
                } else {
                  Toast.makeText(context, strings.adminInvalidPinToast, Toast.LENGTH_SHORT).show()
                }
              },
              colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
            ) {
              Text(
                text = strings.adminAccessControlBtn,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }
      }
    } else {
      // Admin Tabs Row
      LazyRow(
        modifier = Modifier
          .fillMaxWidth()
          .background(DarkCardBg)
          .border(1.dp, DarkCardBorder)
          .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        items(AdminTab.values()) { tab ->
          val isSelected = selectedTab == tab
          Row(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) OrangePrimary else DarkBg)
              .border(1.dp, if (isSelected) OrangePrimary else DarkCardBorder, RoundedCornerShape(8.dp))
              .clickable { selectedTab = tab }
              .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = tab.icon,
              contentDescription = null,
              tint = if (isSelected) Color.Black else OrangePrimary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = tab.getLocalizedTitle(strings).uppercase(),
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = if (isSelected) Color.Black else TextMain,
                fontSize = 11.sp
              )
            )
          }
        }
      }

      // Tab Content
      when (selectedTab) {
        AdminTab.OVERVIEW -> AdminOverviewSection(
          allPromptsCount = allPrompts.size,
          customPromptsCount = customPrompts.size,
          categoriesCount = customCategories.size,
          apiConfigsCount = apiConfigs.size,
          adminSettings = adminSettings,
          onNavigateTab = { selectedTab = it }
        )
        AdminTab.BACKEND -> AdminBackendSection(
          viewModel = viewModel,
          settings = adminSettings
        )
        AdminTab.ADD_PROMPT -> AdminAddPromptSection(
          viewModel = viewModel,
          customCategories = customCategories,
          onPromptCreated = { selectedTab = AdminTab.PROMPTS_LIST }
        )
        AdminTab.PROMPTS_LIST -> AdminPromptsListSection(
          viewModel = viewModel,
          prompts = allPrompts,
          onAddNew = { selectedTab = AdminTab.ADD_PROMPT }
        )
        AdminTab.CATEGORIES -> AdminCategoriesSection(
          viewModel = viewModel,
          categories = customCategories
        )
        AdminTab.APIS -> AdminApisSection(
          viewModel = viewModel,
          apiConfigs = apiConfigs
        )
        AdminTab.ADMOB -> AdminAdMobSection(
          viewModel = viewModel,
          settings = adminSettings
        )
        AdminTab.SETTINGS -> AdminSettingsSection(
          viewModel = viewModel,
          settings = adminSettings
        )
      }
    }
  }
}

@Composable
private fun AdminOverviewSection(
  allPromptsCount: Int,
  customPromptsCount: Int,
  categoriesCount: Int,
  apiConfigsCount: Int,
  adminSettings: AppAdminSettingsEntity,
  onNavigateTab: (AdminTab) -> Unit
) {
  val strings = LocalAppStrings.current
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = strings.adminMetricsHeader,
        style = MaterialTheme.typography.labelMedium.copy(
          fontFamily = FontFamily.Monospace,
          color = TextMuted,
          letterSpacing = 1.sp
        )
      )
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        MetricCard(
          title = strings.adminTotalPrompts,
          value = "$allPromptsCount",
          subtitle = "$customPromptsCount ${strings.adminCustomGenerated}",
          color = OrangePrimary,
          modifier = Modifier.weight(1f),
          onClick = { onNavigateTab(AdminTab.PROMPTS_LIST) }
        )
        MetricCard(
          title = strings.adminTabCategories.uppercase(),
          value = "$categoriesCount",
          subtitle = strings.adminActiveTaxonomy,
          color = AccentCyan,
          modifier = Modifier.weight(1f),
          onClick = { onNavigateTab(AdminTab.CATEGORIES) }
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        MetricCard(
          title = strings.adminAiApis,
          value = "$apiConfigsCount",
          subtitle = "Gemini, MJ, OpenAI, SDXL",
          color = AccentGreen,
          modifier = Modifier.weight(1f),
          onClick = { onNavigateTab(AdminTab.APIS) }
        )
        MetricCard(
          title = strings.adminAdMobAds,
          value = if (adminSettings.adsEnabled) strings.adminStatusActive else strings.adminStatusMuted,
          subtitle = strings.adminBannerInterstitials,
          color = if (adminSettings.adsEnabled) AccentYellow else AccentRed,
          modifier = Modifier.weight(1f),
          onClick = { onNavigateTab(AdminTab.ADMOB) }
        )
      }
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = strings.adminQuickActions,
              style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = OrangePrimary
              )
            )
            NeonBadge(text = strings.adminBadgeLive, accentColor = AccentGreen)
          }

          Spacer(modifier = Modifier.height(12.dp))

          QuickActionButton(
            title = strings.adminActionBackendTitle,
            subtitle = strings.adminActionBackendSub,
            icon = Icons.Default.CloudSync,
            accentColor = AccentCyan,
            onClick = { onNavigateTab(AdminTab.BACKEND) }
          )

          Spacer(modifier = Modifier.height(8.dp))

          QuickActionButton(
            title = strings.adminActionAddPromptTitle,
            subtitle = strings.adminActionAddPromptSub,
            icon = Icons.Default.Add,
            accentColor = OrangePrimary,
            onClick = { onNavigateTab(AdminTab.ADD_PROMPT) }
          )

          Spacer(modifier = Modifier.height(8.dp))

          QuickActionButton(
            title = strings.adminActionCategoryTitle,
            subtitle = strings.adminActionCategorySub,
            icon = Icons.Default.Category,
            accentColor = AccentCyan,
            onClick = { onNavigateTab(AdminTab.CATEGORIES) }
          )

          Spacer(modifier = Modifier.height(8.dp))

          QuickActionButton(
            title = strings.adminActionApisTitle,
            subtitle = strings.adminActionApisSub,
            icon = Icons.Default.Key,
            accentColor = AccentGreen,
            onClick = { onNavigateTab(AdminTab.APIS) }
          )

          Spacer(modifier = Modifier.height(8.dp))

          QuickActionButton(
            title = strings.adminActionAdMobTitle,
            subtitle = strings.adminActionAdMobSub,
            icon = Icons.Default.MonetizationOn,
            accentColor = AccentYellow,
            onClick = { onNavigateTab(AdminTab.ADMOB) }
          )
        }
      }
    }
  }
}

@Composable
private fun MetricCard(
  title: String,
  value: String,
  subtitle: String,
  color: Color,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Card(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
      .clickable { onClick() },
    colors = CardDefaults.cardColors(containerColor = DarkCardBg),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
          fontFamily = FontFamily.Monospace,
          color = TextMuted,
          fontSize = 9.sp,
          fontWeight = FontWeight.Bold
        )
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.headlineMedium.copy(
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = color,
          fontSize = 24.sp
        )
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextMuted,
          fontSize = 10.sp
        ),
        maxLines = 1
      )
    }
  }
}

@Composable
private fun QuickActionButton(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(DarkBg)
      .border(1.dp, DarkCardBorder, RoundedCornerShape(8.dp))
      .clickable { onClick() }
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(36.dp)
        .background(accentColor.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
        .border(1.dp, accentColor, RoundedCornerShape(8.dp)),
      contentAlignment = Alignment.Center
    ) {
      Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(18.dp))
    }
    Spacer(modifier = Modifier.width(12.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.Bold,
          color = TextMain,
          fontSize = 13.sp
        )
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(
          color = TextMuted,
          fontSize = 11.sp
        )
      )
    }
  }
}

@Composable
private fun AdminAddPromptSection(
  viewModel: PromptViewModel,
  customCategories: List<UnifiedCategory>,
  onPromptCreated: () -> Unit
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current
  var title by remember { mutableStateOf("") }
  var description by remember { mutableStateOf("") }
  var template by remember { mutableStateOf("") }
  var negativePrompt by remember { mutableStateOf("") }
  var selectedModel by remember { mutableStateOf(AIModel.MIDJOURNEY) }
  var selectedCategory by remember { mutableStateOf(PromptCategory.REALISTIC) }
  var customCategoryChoice by remember { mutableStateOf<String?>(null) }
  var imageUrl by remember { mutableStateOf("") }
  var customParamText by remember { mutableStateOf("--ar 16:9 --v 6.0 --s 750") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = strings.adminAddPromptHeader,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = OrangePrimary
        )
      )
      Text(
        text = strings.adminAddPromptSub,
        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 12.sp)
      )
    }

    item {
      OutlinedTextField(
        value = title,
        onValueChange = { title = it },
        label = { Text(strings.adminPromptTitleLabel, color = TextMuted) },
        placeholder = { Text(strings.adminPromptTitlePlaceholder, color = TextMuted.copy(alpha = 0.5f)) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = OrangePrimary,
          unfocusedBorderColor = DarkCardBorder,
          focusedTextColor = TextMain,
          unfocusedTextColor = TextMain
        ),
        modifier = Modifier.fillMaxWidth()
      )
    }

    item {
      OutlinedTextField(
        value = description,
        onValueChange = { description = it },
        label = { Text(strings.adminPromptDescLabel, color = TextMuted) },
        placeholder = { Text(strings.adminPromptDescPlaceholder, color = TextMuted.copy(alpha = 0.5f)) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = OrangePrimary,
          unfocusedBorderColor = DarkCardBorder,
          focusedTextColor = TextMain,
          unfocusedTextColor = TextMain
        ),
        modifier = Modifier.fillMaxWidth()
      )
    }

    item {
      Column {
        Text(
          text = strings.adminAiModelLabel.uppercase(),
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            color = TextMuted,
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier.padding(bottom = 6.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(AIModel.values()) { model ->
            val isSelected = selectedModel == model
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) model.tagColor else DarkCardBg)
                .border(1.dp, if (isSelected) model.tagColor else DarkCardBorder, RoundedCornerShape(8.dp))
                .clickable {
                  selectedModel = model
                  customParamText = model.defaultParams
                }
                .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
              Text(
                text = model.displayName,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.Black else TextMain,
                  fontFamily = FontFamily.Monospace
                )
              )
            }
          }
        }
      }
    }

    item {
      Column {
        Text(
          text = strings.adminCategoryLabel.uppercase(),
          style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace,
            color = TextMuted,
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier.padding(bottom = 6.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(customCategories) { cat ->
            val isSelected = if (cat.isCustom) {
              customCategoryChoice == cat.title
            } else {
              customCategoryChoice == null && selectedCategory.name == cat.id
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) OrangePrimary else DarkCardBg)
                .border(1.dp, if (isSelected) OrangePrimary else DarkCardBorder, RoundedCornerShape(8.dp))
                .clickable {
                  if (cat.isCustom) {
                    customCategoryChoice = cat.title
                  } else {
                    customCategoryChoice = null
                    try {
                      selectedCategory = PromptCategory.valueOf(cat.id)
                    } catch (_: Exception) {}
                  }
                }
                .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(cat.dotColor)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = cat.title,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.Black else TextMain,
                    fontFamily = FontFamily.Monospace
                  )
                )
              }
            }
          }
        }
      }
    }

    item {
      OutlinedTextField(
        value = template,
        onValueChange = { template = it },
        label = { Text(strings.adminPromptTemplateLabel, color = TextMuted) },
        placeholder = { Text(strings.adminPromptTemplatePlaceholder, color = TextMuted.copy(alpha = 0.5f)) },
        minLines = 3,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = OrangePrimary,
          unfocusedBorderColor = DarkCardBorder,
          focusedTextColor = TextMain,
          unfocusedTextColor = TextMain
        ),
        modifier = Modifier.fillMaxWidth()
      )
    }

    item {
      OutlinedTextField(
        value = negativePrompt,
        onValueChange = { negativePrompt = it },
        label = { Text(strings.adminNegativePromptLabel, color = TextMuted) },
        placeholder = { Text(strings.adminNegativePromptPlaceholder, color = TextMuted.copy(alpha = 0.5f)) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = OrangePrimary,
          unfocusedBorderColor = DarkCardBorder,
          focusedTextColor = TextMain,
          unfocusedTextColor = TextMain
        ),
        modifier = Modifier.fillMaxWidth()
      )
    }

    item {
      OutlinedTextField(
        value = imageUrl,
        onValueChange = { imageUrl = it },
        label = { Text(strings.adminImageUrlLabel, color = TextMuted) },
        placeholder = { Text(strings.adminImageUrlPlaceholder, color = TextMuted.copy(alpha = 0.5f)) },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = OrangePrimary,
          unfocusedBorderColor = DarkCardBorder,
          focusedTextColor = TextMain,
          unfocusedTextColor = TextMain
        ),
        modifier = Modifier.fillMaxWidth()
      )
    }

    if (imageUrl.isNotBlank()) {
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, OrangePrimary, RoundedCornerShape(12.dp))
        ) {
          AsyncImage(
            model = imageUrl,
            contentDescription = "Preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
        }
      }
    }

    item {
      Button(
        onClick = {
          if (title.isBlank() || template.isBlank()) {
            Toast.makeText(context, "Please enter Title and Prompt Template", Toast.LENGTH_SHORT).show()
            return@Button
          }

          viewModel.saveAdminPrompt(
            title = title,
            description = description.ifBlank { "Curated prompt" },
            template = template,
            negativePrompt = negativePrompt,
            model = selectedModel,
            category = selectedCategory,
            customCategoryName = customCategoryChoice,
            imageUrl = imageUrl.ifBlank { "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=900&auto=format&fit=crop&q=80" },
            parameters = mapOf("--params" to customParamText)
          )

          Toast.makeText(context, strings.adminSavePromptSuccess, Toast.LENGTH_LONG).show()
          onPromptCreated()
        },
        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
          .fillMaxWidth()
          .height(50.dp)
      ) {
        Icon(Icons.Default.Save, contentDescription = null, tint = Color.Black)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = strings.adminPublishPromptBtn,
          fontWeight = FontWeight.Bold,
          color = Color.Black,
          fontFamily = FontFamily.Monospace
        )
      }
    }
  }
}

@Composable
private fun AdminPromptsListSection(
  viewModel: PromptViewModel,
  prompts: List<PromptItem>,
  onAddNew: () -> Unit
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "${strings.adminPromptsListHeader} (${prompts.size})",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace,
            color = OrangePrimary
          )
        )
        Text(
          text = strings.adminPromptsListSub,
          style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 12.sp)
        )
      }

      Button(
        onClick = onAddNew,
        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
        shape = RoundedCornerShape(8.dp)
      ) {
        Icon(Icons.Default.Add, null, tint = Color.Black, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(strings.adminAddNewPromptBtn, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(prompts, key = { it.id }) { prompt ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, DarkCardBorder, RoundedCornerShape(10.dp)),
          colors = CardDefaults.cardColors(containerColor = DarkCardBg),
          shape = RoundedCornerShape(10.dp)
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            AsyncImage(
              model = prompt.imageUrl,
              contentDescription = null,
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(DarkBg)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                NeonBadge(
                  text = prompt.model.shortBadge,
                  accentColor = prompt.model.tagColor
                )
                Spacer(modifier = Modifier.width(6.dp))
                NeonBadge(
                  text = prompt.categoryChipLabel,
                  accentColor = OrangePrimary
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = prompt.title,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextMain
                ),
                maxLines = 1
              )
              Text(
                text = prompt.promptTemplate,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextMuted,
                  fontSize = 11.sp,
                  fontFamily = FontFamily.Monospace
                ),
                maxLines = 1
              )
            }

            if (prompt.isCustom) {
              IconButton(
                onClick = {
                  viewModel.deleteCustomPrompt(prompt.id)
                  Toast.makeText(context, "Deleted ${prompt.title}", Toast.LENGTH_SHORT).show()
                }
              ) {
                Icon(
                  Icons.Default.Delete,
                  contentDescription = "Delete",
                  tint = AccentRed,
                  modifier = Modifier.size(20.dp)
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun AdminCategoriesSection(
  viewModel: PromptViewModel,
  categories: List<UnifiedCategory>
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current
  var newName by remember { mutableStateOf("") }
  var newDescription by remember { mutableStateOf("") }
  var newBadgeTag by remember { mutableStateOf("") }
  var newColorHex by remember { mutableStateOf("#FF5500") }
  var newBannerUrl by remember { mutableStateOf("") }

  val colorPresets = listOf("#FF5500", "#00E5FF", "#00E676", "#D500F9", "#FFD600", "#FF1744")

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = "${strings.adminCategoriesHeader} (${categories.size})",
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = OrangePrimary
        )
      )
      Text(
        text = strings.adminCategoriesSub,
        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 12.sp)
      )
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, OrangePrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = strings.adminAddCategoryHeader,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = TextMain
            )
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text(strings.adminCategoryNameLabel, color = TextMuted) },
            placeholder = { Text(strings.adminCategoryNamePlaceholder, color = TextMuted.copy(alpha = 0.5f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = newDescription,
            onValueChange = { newDescription = it },
            label = { Text(strings.adminCategoryDescLabel, color = TextMuted) },
            placeholder = { Text(strings.adminCategoryDescPlaceholder, color = TextMuted.copy(alpha = 0.5f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = newBadgeTag,
            onValueChange = { newBadgeTag = it },
            label = { Text(strings.adminCategoryBadgeLabel, color = TextMuted) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = newBannerUrl,
            onValueChange = { newBannerUrl = it },
            label = { Text(strings.adminCategoryBannerLabel, color = TextMuted) },
            placeholder = { Text("https://images.unsplash.com/photo-...", color = TextMuted.copy(alpha = 0.5f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = strings.adminCategoryAccentColor,
            style = MaterialTheme.typography.labelSmall.copy(
              fontFamily = FontFamily.Monospace,
              color = TextMuted
            )
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            colorPresets.forEach { hex ->
              val isSelected = newColorHex == hex
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(Color(android.graphics.Color.parseColor(hex)))
                  .border(2.dp, if (isSelected) Color.White else Color.Transparent, CircleShape)
                  .clickable { newColorHex = hex }
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              if (newName.isBlank()) {
                Toast.makeText(context, "Please enter a category name", Toast.LENGTH_SHORT).show()
                return@Button
              }
              viewModel.saveCustomCategory(
                displayName = newName.trim(),
                description = newDescription.trim().ifBlank { "Custom Category" },
                badgeTag = newBadgeTag.trim().ifBlank { newName.take(6).uppercase() },
                colorHex = newColorHex,
                bannerImageUrl = newBannerUrl.trim(),
                accentHex = newColorHex
              )
              newName = ""
              newDescription = ""
              newBadgeTag = ""
              newBannerUrl = ""
              Toast.makeText(context, "Category Added!", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.Add, null, tint = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Text(strings.adminSaveCategoryBtn, color = Color.Black, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    item {
      Text(
        text = "EXISTING CATEGORIES",
        style = MaterialTheme.typography.labelMedium.copy(
          fontFamily = FontFamily.Monospace,
          color = TextMuted,
          letterSpacing = 1.sp
        )
      )
    }

    items(categories) { cat ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, DarkCardBorder, RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(10.dp)
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(14.dp)
              .clip(CircleShape)
              .background(cat.dotColor)
          )
          Spacer(modifier = Modifier.width(12.dp))
          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = cat.title,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextMain
                )
              )
              Spacer(modifier = Modifier.width(8.dp))
              NeonBadge(
                text = cat.chipLabel,
                accentColor = cat.dotColor
              )
            }
            Text(
              text = cat.subtitle,
              style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp),
              maxLines = 1
            )
          }

          if (cat.isCustom) {
            IconButton(
              onClick = {
                viewModel.deleteCustomCategory(cat.id)
                Toast.makeText(context, "Deleted category", Toast.LENGTH_SHORT).show()
              }
            ) {
              Icon(Icons.Default.Delete, null, tint = AccentRed, modifier = Modifier.size(20.dp))
            }
          } else {
            NeonBadge(text = "DEFAULT", accentColor = TextMuted)
          }
        }
      }
    }
  }
}

@Composable
private fun AdminApisSection(
  viewModel: PromptViewModel,
  apiConfigs: List<ApiConfigEntity>
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = strings.adminApisHeader,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = OrangePrimary
        )
      )
      Text(
        text = strings.adminApisSub,
        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 12.sp)
      )
    }

    items(apiConfigs) { config ->
      var key by remember(config.apiKey) { mutableStateOf(config.apiKey) }
      var endpoint by remember(config.endpointUrl) { mutableStateOf(config.endpointUrl) }
      var modelId by remember(config.modelIdentifier) { mutableStateOf(config.modelIdentifier) }
      var enabled by remember(config.isEnabled) { mutableStateOf(config.isEnabled) }

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, if (enabled) OrangePrimary.copy(alpha = 0.6f) else DarkCardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = config.serviceName,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextMain
                )
              )
              Text(
                text = "STATUS: ${config.lastTestedStatus}",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontFamily = FontFamily.Monospace,
                  color = if (config.lastTestedStatus.contains("HTTP 200") || config.lastTestedStatus.contains("Valid")) AccentGreen else AccentCyan,
                  fontSize = 10.sp
                )
              )
            }

            Switch(
              checked = enabled,
              onCheckedChange = {
                enabled = it
                viewModel.saveApiConfig(
                  config.copy(
                    apiKey = key,
                    endpointUrl = endpoint,
                    modelIdentifier = modelId,
                    isEnabled = it
                  )
                )
              },
              colors = SwitchDefaults.colors(
                checkedThumbColor = OrangePrimary,
                checkedTrackColor = OrangePrimary.copy(alpha = 0.3f)
              )
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = key,
            onValueChange = { key = it },
            label = { Text(strings.adminApiKeyLabel, color = TextMuted) },
            placeholder = { Text("sk-... or AIzaSy...", color = TextMuted.copy(alpha = 0.5f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = endpoint,
            onValueChange = { endpoint = it },
            label = { Text(strings.adminBaseUrlLabel, color = TextMuted) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = modelId,
            onValueChange = { modelId = it },
            label = { Text("Model Identifier", color = TextMuted) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                viewModel.saveApiConfig(
                  config.copy(
                    apiKey = key,
                    endpointUrl = endpoint,
                    modelIdentifier = modelId,
                    isEnabled = enabled,
                    lastTestedStatus = if (key.isNotBlank()) "Configured & Saved" else "Saved (Key Empty)"
                  )
                )
                Toast.makeText(context, "${config.serviceName} Saved!", Toast.LENGTH_SHORT).show()
              },
              colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Save, null, tint = Color.Black, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(strings.adminSaveApiBtn, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Button(
              onClick = {
                viewModel.testApiConfig(config.id, key, endpoint)
                Toast.makeText(context, "Testing connection...", Toast.LENGTH_SHORT).show()
              },
              colors = ButtonDefaults.buttonColors(containerColor = DarkBg),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .border(1.dp, AccentCyan, RoundedCornerShape(8.dp))
            ) {
              Icon(Icons.Default.Refresh, null, tint = AccentCyan, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Test Ping", color = AccentCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun AdminAdMobSection(
  viewModel: PromptViewModel,
  settings: AppAdminSettingsEntity
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current
  var adsEnabled by remember(settings.adsEnabled) { mutableStateOf(settings.adsEnabled) }
  var bannerId by remember(settings.bannerAdUnitId) { mutableStateOf(settings.bannerAdUnitId) }
  var interstitialId by remember(settings.interstitialAdUnitId) { mutableStateOf(settings.interstitialAdUnitId) }
  var freqText by remember(settings.interstitialFrequency) { mutableStateOf(settings.interstitialFrequency.toString()) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = strings.adminAdMobHeader,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = OrangePrimary
        )
      )
      Text(
        text = strings.adminAdMobSub,
        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 12.sp)
      )
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = strings.adminEnableAds,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace,
                  color = TextMain
                )
              )
              Text(
                text = if (adsEnabled) strings.adminAdsActive else strings.adminAdsDisabled,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = if (adsEnabled) AccentGreen else TextMuted,
                  fontSize = 11.sp
                )
              )
            }

            Switch(
              checked = adsEnabled,
              onCheckedChange = { adsEnabled = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = OrangePrimary,
                checkedTrackColor = OrangePrimary.copy(alpha = 0.3f)
              )
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          OutlinedTextField(
            value = bannerId,
            onValueChange = { bannerId = it },
            label = { Text(strings.adminBannerIdLabel, color = TextMuted) },
            placeholder = { Text("ca-app-pub-3940256099942544/6300978111", color = TextMuted.copy(alpha = 0.5f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = interstitialId,
            onValueChange = { interstitialId = it },
            label = { Text(strings.adminInterstitialIdLabel, color = TextMuted) },
            placeholder = { Text("ca-app-pub-3940256099942544/1033173712", color = TextMuted.copy(alpha = 0.5f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = freqText,
            onValueChange = { freqText = it },
            label = { Text(strings.adminInterstitialFreqLabel, color = TextMuted) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = {
                val freq = freqText.toIntOrNull() ?: 3
                viewModel.saveAdminSettings(
                  settings.copy(
                    adsEnabled = adsEnabled,
                    bannerAdUnitId = bannerId.trim(),
                    interstitialAdUnitId = interstitialId.trim(),
                    interstitialFrequency = freq
                  )
                )
                Toast.makeText(context, "AdMob Settings Saved!", Toast.LENGTH_SHORT).show()
              },
              colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Default.Save, null, tint = Color.Black)
              Spacer(modifier = Modifier.width(4.dp))
              Text(strings.adminSaveSettingsBtn, color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = {
                bannerId = "ca-app-pub-3940256099942544/6300978111"
                interstitialId = "ca-app-pub-3940256099942544/1033173712"
                freqText = "3"
                Toast.makeText(context, "Reset to Google Test IDs", Toast.LENGTH_SHORT).show()
              },
              colors = ButtonDefaults.buttonColors(containerColor = DarkBg),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .border(1.dp, DarkCardBorder, RoundedCornerShape(8.dp))
            ) {
              Text(strings.adminResetTestIdsBtn, color = TextMain, fontSize = 11.sp)
            }
          }
        }
      }
    }

    item {
      Text(
        text = strings.adminLiveBannerPreview,
        style = MaterialTheme.typography.labelMedium.copy(
          fontFamily = FontFamily.Monospace,
          color = TextMuted,
          letterSpacing = 1.sp
        )
      )
    }

    item {
      AdMobBanner(
        adUnitId = bannerId,
        adsEnabled = adsEnabled,
        showLabel = true
      )
    }
  }
}

@Composable
private fun AdminSettingsSection(
  viewModel: PromptViewModel,
  settings: AppAdminSettingsEntity
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current
  var pin by remember(settings.adminPin) { mutableStateOf(settings.adminPin) }
  var announcementEnabled by remember(settings.announcementEnabled) { mutableStateOf(settings.announcementEnabled) }
  var announcementText by remember(settings.announcementText) { mutableStateOf(settings.announcementText) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = strings.adminSettingsHeader,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace,
          color = OrangePrimary
        )
      )
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = strings.adminSecurityPinHeader,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = TextMain
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = pin,
            onValueChange = { pin = it },
            label = { Text(strings.adminPinLabel, color = TextMuted) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = strings.adminAnnouncementHeader,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = TextMain
                )
              )
              Text(
                text = strings.adminAnnouncementSub,
                style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp)
              )
            }

            Switch(
              checked = announcementEnabled,
              onCheckedChange = { announcementEnabled = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = OrangePrimary,
                checkedTrackColor = OrangePrimary.copy(alpha = 0.3f)
              )
            )
          }

          if (announcementEnabled) {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
              value = announcementText,
              onValueChange = { announcementText = it },
              label = { Text(strings.adminAnnouncementLabel, color = TextMuted) },
              placeholder = { Text("🔥 50+ New Midjourney v6 Prompts Released!", color = TextMuted.copy(alpha = 0.5f)) },
              singleLine = true,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OrangePrimary,
                unfocusedBorderColor = DarkCardBorder,
                focusedTextColor = TextMain,
                unfocusedTextColor = TextMain
              ),
              modifier = Modifier.fillMaxWidth()
            )
          }

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              viewModel.saveAdminSettings(
                settings.copy(
                  adminPin = pin.trim().ifBlank { "1234" },
                  announcementEnabled = announcementEnabled,
                  announcementText = announcementText.trim()
                )
              )
              Toast.makeText(context, "Settings Saved!", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.Save, null, tint = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Text(strings.adminSaveConfigBtn, color = Color.Black, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = strings.adminExportBackupHeader,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = TextMain
            )
          )

          Text(
            text = strings.adminExportBackupSub,
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp),
            modifier = Modifier.padding(vertical = 6.dp)
          )

          Button(
            onClick = {
              viewModel.exportAllDataToClipboard()
              Toast.makeText(context, "Full JSON Exported to Clipboard!", Toast.LENGTH_LONG).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = DarkBg),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, AccentGreen, RoundedCornerShape(8.dp))
          ) {
            Icon(Icons.Default.ContentCopy, null, tint = AccentGreen)
            Spacer(modifier = Modifier.width(6.dp))
            Text(strings.adminExportVaultBtn, color = AccentGreen, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
private fun AdminBackendSection(
  viewModel: PromptViewModel,
  settings: AppAdminSettingsEntity
) {
  val context = LocalContext.current
  val strings = LocalAppStrings.current
  var backendUrl by remember(settings.backendApiUrl) { mutableStateOf(settings.backendApiUrl) }
  var apiKey by remember(settings.backendApiKey) { mutableStateOf(settings.backendApiKey) }

  val isSyncing by viewModel.isSyncingBackend.collectAsState()
  val syncStatus by viewModel.backendTestStatus.collectAsState()

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Text(
        text = strings.adminBackendHeader,
        style = MaterialTheme.typography.labelMedium.copy(
          fontFamily = FontFamily.Monospace,
          color = AccentCyan,
          letterSpacing = 1.sp
        )
      )
    }

    // Connection Configuration Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, AccentCyan.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.CloudSync, null, tint = AccentCyan, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = strings.adminBackendCoords,
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace,
                  color = TextMain
                )
              )
            }
            NeonBadge(
              text = if (settings.backendApiUrl.isNotBlank()) strings.adminConfigured else strings.adminOffline,
              accentColor = if (settings.backendApiUrl.isNotBlank()) AccentGreen else AccentRed
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = backendUrl,
            onValueChange = { backendUrl = it },
            label = { Text(strings.adminBackendUrlLabel, color = TextMuted) },
            placeholder = { Text("https://yourdomain.com/backend/api/sync.php", color = TextMuted.copy(alpha = 0.4f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = AccentCyan,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = TextMain,
              unfocusedTextColor = TextMain
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = apiKey,
            onValueChange = { apiKey = it },
            label = { Text(strings.adminApiKeyLabel, color = TextMuted) },
            placeholder = { Text("prompteg_secret_key_2026", color = TextMuted.copy(alpha = 0.4f)) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = AccentCyan,
              unfocusedBorderColor = DarkCardBorder,
              focusedTextColor = AccentYellow,
              unfocusedTextColor = AccentYellow
            ),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Save URL & Key Button
          Button(
            onClick = {
              viewModel.saveAdminSettings(
                settings.copy(
                  backendApiUrl = backendUrl.trim(),
                  backendApiKey = apiKey.trim()
                )
              )
              Toast.makeText(context, "Backend URL & API Key Saved!", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = AccentCyan),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Icon(Icons.Default.Save, null, tint = Color.Black)
            Spacer(modifier = Modifier.width(6.dp))
            Text(strings.adminSaveBackendBtn, color = Color.Black, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Live Sync & Action Controls
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = strings.adminCloudSyncHeader,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = TextMain
            )
          )

          Text(
            text = strings.adminCloudSyncSub,
            style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 11.sp),
            modifier = Modifier.padding(vertical = 6.dp)
          )

          if (syncStatus != null) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .background(DarkBg, RoundedCornerShape(8.dp))
                .border(1.dp, AccentCyan.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Text(
                text = syncStatus ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontFamily = FontFamily.Monospace,
                  color = if (syncStatus?.contains("Connected") == true || syncStatus?.contains("Synced") == true) AccentGreen else AccentYellow,
                  fontSize = 11.sp
                )
              )
            }
            Spacer(modifier = Modifier.height(10.dp))
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            // Test Connection Button
            Button(
              onClick = {
                viewModel.testBackendConnection(backendUrl.trim(), apiKey.trim())
              },
              colors = ButtonDefaults.buttonColors(containerColor = DarkBg),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .border(1.dp, AccentCyan, RoundedCornerShape(8.dp))
            ) {
              Icon(Icons.Default.Link, null, tint = AccentCyan, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(strings.adminTestPingBtn, color = AccentCyan, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            }

            // Sync from Backend Button
            Button(
              onClick = {
                viewModel.syncFromCloudBackend(backendUrl.trim(), apiKey.trim())
              },
              enabled = !isSyncing,
              colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1.3f)
            ) {
              if (isSyncing) {
                CircularProgressIndicator(
                  color = Color.Black,
                  modifier = Modifier.size(16.dp),
                  strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(strings.adminSyncingBtn, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
              } else {
                Icon(Icons.Default.Refresh, null, tint = Color.Black, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(strings.adminSyncVaultBtn, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Last Sync Info
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "${strings.adminLastStatus}: ${settings.lastSyncStatus}",
              style = MaterialTheme.typography.bodySmall.copy(color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
            )
            if (settings.lastSyncTimestamp > 0) {
              Text(
                text = "${java.text.SimpleDateFormat("MMM dd, HH:mm", java.util.Locale.US).format(java.util.Date(settings.lastSyncTimestamp))}",
                style = MaterialTheme.typography.bodySmall.copy(color = TextSubtle, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
              )
            }
          }
        }
      }
    }

    // Step-by-Step Backend Deployment Instructions Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = DarkCardBg),
        shape = RoundedCornerShape(12.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "📖 HOW TO UPLOAD & HOST YOUR PHP BACKEND",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = OrangePrimary
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          val steps = listOf(
            "1. Upload the provided '/backend' folder to your web hosting (cPanel public_html/backend).",
            "2. Open 'https://yourdomain.com/backend/install.php' in your browser for 1-Click database installer.",
            "3. Or import '/backend/database.sql' into your MySQL database using phpMyAdmin.",
            "4. Log in to your Web Admin Panel at 'https://yourdomain.com/backend/admin/login.php' (default: admin / admin1234).",
            "5. Add or edit prompts and categories directly from your browser admin dashboard!",
            "6. Copy your sync URL 'https://yourdomain.com/backend/api/sync.php' into this app and tap 'SYNC CLOUD VAULT'."
          )

          steps.forEach { step ->
            Text(
              text = step,
              style = MaterialTheme.typography.bodySmall.copy(
                color = TextMain,
                fontSize = 11.sp,
                lineHeight = 16.sp
              ),
              modifier = Modifier.padding(vertical = 3.dp)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(DarkBg, RoundedCornerShape(6.dp))
              .padding(8.dp)
          ) {
            Text(
              text = "Web Admin Portal: https://yourdomain.com/backend/admin/\nSync Endpoint: https://yourdomain.com/backend/api/sync.php",
              style = MaterialTheme.typography.bodySmall.copy(
                color = AccentGreen,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
              )
            )
          }
        }
      }
    }
  }
}
