package com.example.ui.screens.categories

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.data.model.PromptCategory
import com.example.data.model.UnifiedCategory
import com.example.ui.components.GlowingCard
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted

@Composable
fun CategoriesScreen(
  categories: List<UnifiedCategory> = emptyList(),
  onCategorySelect: (PromptCategory?) -> Unit,
  onCustomCategorySelect: (String) -> Unit = {},
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val displayCategories = if (categories.isNotEmpty()) {
    categories
  } else {
    PromptCategory.values().map {
      UnifiedCategory(
        id = it.name,
        title = it.title,
        subtitle = it.subtitle,
        chipLabel = it.chipLabel,
        dotColor = it.dotColor,
        coverImageUrl = it.coverImageUrl,
        accentColor = it.accentColor,
        isCustom = false
      )
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("categories_screen"),
    contentPadding = PaddingValues(bottom = 110.dp)
  ) {
    // Header
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .statusBarsPadding()
          .padding(horizontal = 20.dp, vertical = 16.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.Layers,
            contentDescription = null,
            tint = OrangePrimary,
            modifier = Modifier.size(24.dp)
          )
          Text(
            text = strings.categoriesTitle,
            style = MaterialTheme.typography.displayMedium.copy(
              fontSize = 24.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = (-0.5).sp
            ),
            color = TextMain
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = strings.categoriesSubtitle,
          style = MaterialTheme.typography.bodyMedium,
          color = TextMuted
        )
      }
    }

    // Categories List of High-Resolution Visual Cards
    items(displayCategories, key = { it.id }) { category ->
      val matchedPromptCategory = try {
        PromptCategory.valueOf(category.id)
      } catch (_: Exception) {
        null
      }
      val displayTitle = if (matchedPromptCategory != null) {
        strings.getCategoryTitle(matchedPromptCategory)
      } else {
        category.title
      }
      val displaySubtitle = if (matchedPromptCategory != null) {
        strings.getCategorySubtitle(matchedPromptCategory)
      } else {
        category.subtitle
      }

      Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
        GlowingCard(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("category_card_${category.id}"),
          borderColor = DarkCardBorder,
          backgroundColor = DarkCardBg,
          shapeRadius = 18.dp,
          onClick = {
            if (category.isCustom) {
              onCustomCategorySelect(category.title)
            } else {
              onCategorySelect(matchedPromptCategory)
            }
          }
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(125.dp)
          ) {
            // Full color Unsplash high-res image
            SubcomposeAsyncImage(
              model = category.coverImageUrl,
              contentDescription = displayTitle,
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize(),
              loading = {
                Box(
                  modifier = Modifier
                    .fillMaxSize()
                    .background(DarkCardElevated),
                  contentAlignment = Alignment.Center
                ) {
                  CircularProgressIndicator(color = OrangePrimary, strokeWidth = 2.dp)
                }
              }
            )

            // Cinematic Gradient Overlay
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(
                  Brush.horizontalGradient(
                    colors = listOf(
                      Color(0xF80C0D10),
                      Color(0xDD0C0D10),
                      Color(0x550C0D10)
                    )
                  )
                )
            )

            // Content on Top of Image
            Row(
              modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Box(
                    modifier = Modifier
                      .size(8.dp)
                      .background(category.dotColor, CircleShape)
                  )
                  Text(
                    text = displayTitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextMain,
                    fontWeight = FontWeight.Bold
                  )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                  text = displaySubtitle,
                  style = MaterialTheme.typography.bodySmall,
                  color = TextMuted,
                  maxLines = 2
                )
              }

              // Glowing Action Pill
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = OrangeDim,
                border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.4f))
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Text(
                    text = strings.exploreCategoryBtn.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = OrangePrimary,
                    fontWeight = FontWeight.ExtraBold
                  )
                  Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier.size(14.dp)
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}


