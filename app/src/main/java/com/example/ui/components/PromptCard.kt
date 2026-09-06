package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.data.model.PromptItem
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.AccentRed
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorderHighlight
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle

internal fun formatCompactCount(count: Int): String {
  return when {
    count >= 1_000_000 -> {
      val m = count / 1_000_000.0
      val formatted = String.format(java.util.Locale.US, "%.1fM", m)
      formatted.replace(".0M", "M")
    }
    count >= 10_000 -> "${count / 1_000}k"
    count >= 1_000 -> {
      val k = count / 1_000.0
      val formatted = String.format(java.util.Locale.US, "%.1fk", k)
      formatted.replace(".0k", "k")
    }
    else -> count.toString()
  }
}

@Composable
fun TrendingPromptCard(
  prompt: PromptItem,
  rank: Int,
  onCardClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val shape = RoundedCornerShape(20.dp)

  Surface(
    modifier = modifier
      .width(260.dp)
      .height(340.dp)
      .testTag("trending_card_${prompt.id}")
      .clip(shape)
      .clickable { onCardClick() },
    shape = shape,
    color = DarkCardBg,
    border = BorderStroke(1.dp, if (prompt.isBookmarked) OrangePrimary else DarkCardBorder)
  ) {
    Box(modifier = Modifier.fillMaxSize()) {
      // High-res visual image
      SubcomposeAsyncImage(
        model = prompt.imageUrl,
        contentDescription = prompt.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
        loading = {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(DarkCardElevated),
            contentAlignment = Alignment.Center
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(24.dp),
              color = OrangePrimary,
              strokeWidth = 2.dp
            )
          }
        }
      )

      // Gradient overlay for high-contrast reading
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.verticalGradient(
              colors = listOf(
                Color(0x77000000),
                Color.Transparent,
                Color(0xCC0C0D10),
                Color(0xF50C0D10)
              ),
              startY = 0f,
              endY = 800f
            )
          )
      )

      // Top Rank Badge & Bookmark Button
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Rank pill (e.g., #1, #2)
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(0xDD121318),
          border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.5f))
        ) {
          Text(
            text = "#$rank",
            color = OrangePrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontFamily = FontFamily.Monospace
          )
        }

        // Top bookmark icon
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(Color(0xBB141519))
            .border(BorderStroke(1.dp, if (prompt.isBookmarked) OrangePrimary else Color(0x44FFFFFF)), CircleShape)
            .clickable { onBookmarkClick() },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (prompt.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = "Bookmark",
            tint = if (prompt.isBookmarked) OrangePrimary else TextMain,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      // Bottom Content Overlay
      Column(
        modifier = Modifier
          .align(Alignment.BottomStart)
          .fillMaxWidth()
          .padding(16.dp)
      ) {
        Text(
          text = prompt.title,
          style = MaterialTheme.typography.titleMedium,
          color = TextMain,
          fontWeight = FontWeight.Bold,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          val strings = LocalAppStrings.current
          // Category tag in orange
          Text(
            text = strings.getCategoryTitle(prompt.category),
            color = OrangePrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 0.8.sp
          )

          // View / Copy Count
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Icon(
              imageVector = Icons.Filled.Visibility,
              contentDescription = null,
              tint = TextMuted,
              modifier = Modifier.size(13.dp)
            )
            Text(
              text = formatCompactCount(prompt.copyCount),
              color = TextMuted,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }
      }
    }
  }
}

@Composable
fun PromptCard(
  prompt: PromptItem,
  onCardClick: () -> Unit,
  onCopyClick: () -> Unit,
  onBookmarkClick: () -> Unit,
  onLikeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val shape = RoundedCornerShape(16.dp)

  Surface(
    modifier = modifier
      .testTag("prompt_card_${prompt.id}")
      .fillMaxWidth()
      .clip(shape)
      .clickable { onCardClick() },
    shape = shape,
    color = DarkCardBg,
    border = BorderStroke(1.dp, if (prompt.isBookmarked) OrangeBorder else DarkCardBorder)
  ) {
    Column {
      // Visual Preview Header
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1.3f)
          .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
      ) {
        SubcomposeAsyncImage(
          model = prompt.imageUrl,
          contentDescription = prompt.title,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize(),
          loading = {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(DarkCardElevated),
              contentAlignment = Alignment.Center
            ) {
              CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = OrangePrimary,
                strokeWidth = 2.dp
              )
            }
          },
          error = {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(DarkCardElevated),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "IMAGE UNAVAILABLE",
                style = MaterialTheme.typography.labelSmall,
                color = TextMuted
              )
            }
          }
        )

        // Gradient overlay
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Transparent,
                  Color(0x40000000),
                  Color(0xEE0C0D10)
                ),
                startY = 60f
              )
            )
        )

        // Top Badges (Model + Bookmark)
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          NeonBadge(
            text = prompt.model.shortBadge,
            accentColor = prompt.model.tagColor
          )

          // Bookmark Icon Button
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Color(0x990C0D10))
              .clickable { onBookmarkClick() },
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (prompt.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
              contentDescription = "Bookmark",
              tint = if (prompt.isBookmarked) OrangePrimary else TextMuted,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        // Category Tag in Bottom-Left of Image
        Box(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(10.dp)
        ) {
          val strings = LocalAppStrings.current
          Text(
            text = strings.getCategoryTitle(prompt.category),
            style = MaterialTheme.typography.labelSmall,
            color = OrangeLight,
            fontSize = 9.5.sp,
            letterSpacing = 0.8.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // Card Content
      Column(
        modifier = Modifier.padding(10.dp)
      ) {
        Text(
          text = prompt.title,
          style = MaterialTheme.typography.titleSmall,
          color = TextMain,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = prompt.description,
          style = MaterialTheme.typography.bodySmall,
          color = TextMuted,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Card Footer (Likes, Copy Count, One-Tap Copy Action)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Stats Row: weighted with fill=false so Copy Action button takes intrinsic space first
          Row(
            modifier = Modifier.weight(1f, fill = false),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Likes
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable { onLikeClick() }
                .padding(vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
              Icon(
                imageVector = if (prompt.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Likes",
                tint = if (prompt.isLiked) AccentRed else TextSubtle,
                modifier = Modifier.size(13.dp)
              )
              Text(
                text = formatCompactCount(prompt.likesCount),
                style = MaterialTheme.typography.bodySmall,
                color = if (prompt.isLiked) AccentRed else TextSubtle,
                fontSize = 10.5.sp,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
              )
            }

            // Copy Count
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
              Icon(
                imageVector = Icons.Outlined.ContentCopy,
                contentDescription = null,
                tint = TextSubtle,
                modifier = Modifier.size(11.dp)
              )
              Text(
                text = formatCompactCount(prompt.copyCount),
                style = MaterialTheme.typography.bodySmall,
                color = TextSubtle,
                fontSize = 10.5.sp,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
              )
            }
          }

          Spacer(modifier = Modifier.width(6.dp))

          // Quick Copy Action Button (Guaranteed horizontal layout, no vertical wrapping)
          Surface(
            onClick = onCopyClick,
            shape = RoundedCornerShape(8.dp),
            color = OrangeDim,
            border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.4f)),
            modifier = Modifier.testTag("copy_button_${prompt.id}")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
              Icon(
                imageVector = Icons.Filled.ContentCopy,
                contentDescription = "Copy Prompt",
                tint = OrangePrimary,
                modifier = Modifier.size(11.dp)
              )
              Text(
                text = strings.copyPrompt.uppercase(),
                color = OrangePrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                maxLines = 1,
                softWrap = false
              )
            }
          }
        }
      }
    }
  }
}

