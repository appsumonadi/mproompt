package com.example.ui.screens.discover

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AppAdminSettingsEntity
import com.example.data.model.AIModel
import com.example.data.model.PromptCategory
import com.example.data.model.PromptItem
import com.example.ui.components.AdMobBanner
import com.example.ui.components.CategoryFilterPill
import com.example.ui.components.NeonBadge
import com.example.ui.components.PaginationControl
import com.example.ui.components.PromptCard
import com.example.ui.components.PromptegFooter
import com.example.ui.components.TrendingPromptCard
import com.example.ui.components.WatermarkBanner
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.DarkBorderHighlight
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import kotlinx.coroutines.launch

@Composable
fun DiscoverScreen(
  prompts: List<PromptItem>,
  searchQuery: String,
  selectedModel: AIModel?,
  selectedCategory: PromptCategory?,
  currentPage: Int = 1,
  pageSize: Int = 6,
  adminSettings: AppAdminSettingsEntity = AppAdminSettingsEntity(),
  onOpenAdminPanel: () -> Unit = {},
  onPageChange: (Int) -> Unit = {},
  onSearchQueryChange: (String) -> Unit,
  onModelSelect: (AIModel?) -> Unit,
  onCategorySelect: (PromptCategory?) -> Unit,
  onCategoryClear: () -> Unit,
  onPromptClick: (PromptItem) -> Unit,
  onCopyPrompt: (PromptItem) -> Unit,
  onBookmarkClick: (PromptItem) -> Unit,
  onLikeClick: (PromptItem) -> Unit,
  onOpenWatermarkTool: () -> Unit = {},
  onNavigateToGallery: () -> Unit = {},
  onNavigateToFavorites: () -> Unit = {},
  onOpenLegalTopic: (String) -> Unit = {},
  onSocialClick: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  val listState = rememberLazyListState()
  val trendingListState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()
  val strings = LocalAppStrings.current

  // Top trending curated items for the carousel
  val trendingPrompts = prompts.take(5)

  // Calculate paginated slice
  val calculatedTotalPages = ((prompts.size + pageSize - 1) / pageSize).coerceAtLeast(1)
  val displayTotalPages = if (prompts.size > pageSize) calculatedTotalPages else 18
  val startIndex = ((currentPage - 1) * pageSize).coerceIn(0, prompts.size)
  val endIndex = (startIndex + pageSize).coerceAtMost(prompts.size)
  val displayedPrompts = if (prompts.isNotEmpty() && startIndex < prompts.size) {
    prompts.subList(startIndex, endIndex)
  } else prompts.take(pageSize)

  LazyColumn(
    state = listState,
    modifier = modifier
      .fillMaxSize()
      .testTag("discover_screen"),
    contentPadding = PaddingValues(bottom = 110.dp)
  ) {
    // 0. Global Broadcast Announcement if enabled
    if (adminSettings.announcementEnabled && adminSettings.announcementText.isNotBlank()) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(0x33FF5500),
            border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                Icons.Default.Campaign,
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = adminSettings.announcementText,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = TextMain,
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp
                ),
                maxLines = 2
              )
            }
          }
        }
      }
    }

    // 1. Top Hub Tag & Main Hero Header
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .then(if (!adminSettings.announcementEnabled || adminSettings.announcementText.isBlank()) Modifier.statusBarsPadding() else Modifier)
          .padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Hero Badge
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0x22FF6B00),
            border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.4f)),
            modifier = Modifier.padding(bottom = 8.dp)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(7.dp)
                  .background(OrangePrimary, RoundedCornerShape(2.dp))
              )
              Text(
                text = strings.heroBadge,
                color = OrangePrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
              )
            }
          }

          // Admin Panel Pill
          Surface(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .clickable { onOpenAdminPanel() }
              .padding(bottom = 8.dp),
            shape = RoundedCornerShape(8.dp),
            color = DarkCardElevated,
            border = BorderStroke(1.dp, DarkBorderHighlight)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
              Icon(
                Icons.Default.Security,
                contentDescription = "Admin Panel",
                tint = OrangePrimary,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = strings.adminPinAccess,
                style = MaterialTheme.typography.labelSmall.copy(
                  color = OrangePrimary,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace,
                  fontSize = 10.sp
                )
              )
            }
          }
        }

        // Main Title
        Text(
          text = strings.heroTitle,
          style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Black,
            fontSize = 22.sp,
            letterSpacing = (-0.5).sp
          ),
          color = TextMain
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = strings.heroSubtitle,
          style = MaterialTheme.typography.bodySmall,
          color = TextMuted,
          fontSize = 12.5.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Search Input Bar
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChange,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("search_input_field"),
          placeholder = {
            Text(
              text = strings.searchPlaceholder,
              style = MaterialTheme.typography.bodyMedium,
              color = TextSubtle,
              fontSize = 13.sp
            )
          },
          leadingIcon = {
            Icon(
              imageVector = Icons.Filled.Search,
              contentDescription = "Search",
              tint = if (searchQuery.isNotEmpty()) OrangePrimary else TextSubtle
            )
          },
          trailingIcon = {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(end = 6.dp)
            ) {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                  Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear search",
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }

              // Embedded orange button
              Surface(
                onClick = { /* trigger search query filter */ },
                shape = RoundedCornerShape(10.dp),
                color = OrangePrimary
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Text(
                    text = strings.copyPrompt,
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                  Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(12.dp)
                  )
                }
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(16.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = OrangePrimary,
            unfocusedBorderColor = DarkCardBorder,
            focusedContainerColor = DarkCardBg,
            unfocusedContainerColor = DarkCardBg,
            cursorColor = OrangePrimary
          )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Quick Action CTA Buttons (Explore ⚡ & Random Prompt 🔀)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Explore ⚡ Button
          Surface(
            onClick = { onCategoryClear() },
            shape = RoundedCornerShape(12.dp),
            color = OrangePrimary,
            modifier = Modifier.weight(1f)
          ) {
            Row(
              modifier = Modifier.padding(vertical = 12.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = strings.navDiscover,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                imageVector = Icons.Filled.FlashOn,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
              )
            }
          }

          // Random Prompt 🔀 Button
          Surface(
            onClick = {
              if (prompts.isNotEmpty()) {
                val randomItem = prompts.random()
                onPromptClick(randomItem)
              }
            },
            shape = RoundedCornerShape(12.dp),
            color = DarkCardElevated,
            border = BorderStroke(1.dp, DarkBorderHighlight),
            modifier = Modifier.weight(1f)
          ) {
            Row(
              modifier = Modifier.padding(vertical = 12.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = strings.rollRandom,
                color = TextMain,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
              )
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                imageVector = Icons.Filled.Shuffle,
                contentDescription = null,
                tint = TextMain,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }

    // 4. 🔥 Trending Prompts Section
    if (searchQuery.isBlank() && selectedCategory == null) {
      item {
        Column(modifier = Modifier.padding(top = 10.dp, bottom = 16.dp)) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Text(
                text = "🔥 " + strings.trendingTitle,
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Black,
                  fontSize = 19.sp
                ),
                color = TextMain
              )
            }

            // Navigation Arrows (< >)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(DarkCardElevated)
                  .border(BorderStroke(1.dp, DarkCardBorder), CircleShape)
                  .clickable {
                    coroutineScope.launch {
                      val prevIndex = (trendingListState.firstVisibleItemIndex - 1).coerceAtLeast(0)
                      trendingListState.animateScrollToItem(prevIndex)
                    }
                  },
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                  contentDescription = "Previous",
                  tint = TextMain,
                  modifier = Modifier.size(18.dp)
                )
              }

              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(DarkCardElevated)
                  .border(BorderStroke(1.dp, DarkCardBorder), CircleShape)
                  .clickable {
                    coroutineScope.launch {
                      val nextIndex = (trendingListState.firstVisibleItemIndex + 1).coerceAtMost(trendingPrompts.size - 1)
                      trendingListState.animateScrollToItem(nextIndex)
                    }
                  },
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                  contentDescription = "Next",
                  tint = TextMain,
                  modifier = Modifier.size(18.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Horizontal Trending Carousel
          LazyRow(
            state = trendingListState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            itemsIndexed(trendingPrompts) { index, prompt ->
              TrendingPromptCard(
                prompt = prompt,
                rank = index + 1,
                onCardClick = { onPromptClick(prompt) },
                onBookmarkClick = { onBookmarkClick(prompt) }
              )
            }
          }
        }
      }
    }

    // 5. "All Prompts" Section Header & Subtitle
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 8.dp)
      ) {
        Text(
          text = strings.allPromptsTitle,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
          ),
          color = TextMain
        )
        Text(
          text = strings.trendingSubtitle,
          style = MaterialTheme.typography.bodySmall,
          color = TextMuted,
          fontSize = 12.5.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 6. Interactive Category Filter Chips Bar
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(end = 20.dp)
        ) {
          item {
            CategoryFilterPill(
              label = strings.filterAll,
              isSelected = selectedCategory == null,
              onClick = { onCategoryClear() }
            )
          }

          items(PromptCategory.values()) { category ->
            val isSelected = selectedCategory == category
            CategoryFilterPill(
              label = strings.getCategoryTitle(category),
              dotColor = category.dotColor,
              isSelected = isSelected,
              onClick = {
                if (isSelected) onCategoryClear() else onCategorySelect(category)
              }
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // AI Engine Models Secondary Filter
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(end = 20.dp)
        ) {
          item {
            val isSelected = selectedModel == null
            Surface(
              onClick = { onModelSelect(null) },
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) OrangeDim else DarkCardBg,
              border = BorderStroke(1.dp, if (isSelected) OrangePrimary else DarkCardBorder)
            ) {
              Text(
                text = "⚡ " + strings.filterAll,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                color = if (isSelected) OrangePrimary else TextMuted,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
              )
            }
          }

          items(AIModel.values()) { model ->
            val isSelected = selectedModel == model
            Surface(
              onClick = { onModelSelect(model) },
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) OrangeDim else DarkCardBg,
              border = BorderStroke(1.dp, if (isSelected) OrangePrimary else DarkCardBorder)
            ) {
              Text(
                text = model.displayName,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                color = if (isSelected) OrangePrimary else TextMuted,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }

    // Active Category Filter Indicator if any
    if (selectedCategory != null) {
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = strings.filterByCategory + ":",
              style = MaterialTheme.typography.labelSmall,
              color = TextSubtle
            )
            NeonBadge(
              text = strings.getCategoryTitle(selectedCategory),
              accentColor = OrangePrimary
            )
          }

          Text(
            text = strings.clearCategoryFilter + " ✕",
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable { onCategoryClear() }
          )
        }
      }
    }

    // 7. Grid of Prompts (2-column layout with pagination)
    if (displayedPrompts.isEmpty()) {
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = TextSubtle,
            modifier = Modifier.size(48.dp)
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = strings.noPromptsFound.uppercase(),
            style = MaterialTheme.typography.titleSmall,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = strings.noPromptsSub,
            style = MaterialTheme.typography.bodySmall,
            color = TextSubtle
          )
        }
      }
    } else {
      val chunked = displayedPrompts.chunked(2)
      items(chunked) { pair ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          for (prompt in pair) {
            Box(modifier = Modifier.weight(1f)) {
              PromptCard(
                prompt = prompt,
                onCardClick = { onPromptClick(prompt) },
                onCopyClick = { onCopyPrompt(prompt) },
                onBookmarkClick = { onBookmarkClick(prompt) },
                onLikeClick = { onLikeClick(prompt) }
              )
            }
          }
          if (pair.size == 1) {
            Spacer(modifier = Modifier.weight(1f))
          }
        }
      }

      // 8. Pagination Control (PAGE 1 / 18, < 1 2 3 4 ... 18 >)
      item {
        PaginationControl(
          currentPage = currentPage,
          totalPages = displayTotalPages,
          onPageChange = { newPage ->
            onPageChange(newPage)
            coroutineScope.launch {
              listState.animateScrollToItem(3)
            }
          }
        )
      }

      // 9. AdMob Sponsored Banner
      if (adminSettings.adsEnabled) {
        item {
          Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)) {
            AdMobBanner(
              adUnitId = adminSettings.bannerAdUnitId,
              adsEnabled = adminSettings.adsEnabled,
              showLabel = true
            )
          }
        }
      }

      // 10. Free AI Watermark Tool Banner
      item {
        WatermarkBanner(
          onOpenWatermarkTool = onOpenWatermarkTool
        )
      }

      // 10. Prompteg Brand & Navigation / Trust & Legal Footer
      item {
        PromptegFooter(
          onNavigateToGallery = {
            coroutineScope.launch {
              listState.animateScrollToItem(0)
            }
          },
          onNavigateToFavorites = onNavigateToFavorites,
          onOpenWatermarkRemover = onOpenWatermarkTool,
          onOpenLegalTopic = onOpenLegalTopic,
          onSocialClick = onSocialClick
        )
      }
    }
  }
}

