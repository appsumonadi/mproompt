package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBgElevated
import com.example.ui.theme.DarkBorderHighlight
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.GlassBg
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangeGlow
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle

@Composable
fun CyberpunkBackground(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBg)
      .drawBehind {
        // Subtle ambient orange radial glows
        drawCircle(
          brush = Brush.radialGradient(
            colors = listOf(OrangePrimary.copy(alpha = 0.09f), Color.Transparent),
            center = Offset(size.width * 0.95f, size.height * 0.08f),
            radius = size.width * 0.75f
          )
        )
        drawCircle(
          brush = Brush.radialGradient(
            colors = listOf(OrangeLight.copy(alpha = 0.05f), Color.Transparent),
            center = Offset(size.width * 0.05f, size.height * 0.55f),
            radius = size.width * 0.85f
          )
        )
      }
  ) {
    content()
  }
}

@Composable
fun GlowingCard(
  modifier: Modifier = Modifier,
  borderColor: Color = DarkCardBorder,
  backgroundColor: Color = DarkCardBg,
  glowColor: Color = Color.Transparent,
  shapeRadius: Dp = 16.dp,
  onClick: (() -> Unit)? = null,
  content: @Composable () -> Unit
) {
  val shape = RoundedCornerShape(shapeRadius)
  val baseModifier = modifier
    .then(
      if (glowColor != Color.Transparent) {
        Modifier.shadow(
          elevation = 12.dp,
          shape = shape,
          ambientColor = glowColor,
          spotColor = glowColor
        )
      } else Modifier
    )
    .clip(shape)
    .background(backgroundColor)
    .border(BorderStroke(1.dp, borderColor), shape)
    .then(
      if (onClick != null) {
        Modifier.clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          onClick = onClick
        )
      } else Modifier
    )

  Box(modifier = baseModifier) {
    content()
  }
}

@Composable
fun NeonButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null,
  isPrimary: Boolean = true,
  accentColor: Color = OrangePrimary,
  enabled: Boolean = true,
  testTag: String = "neon_button"
) {
  val shape = RoundedCornerShape(12.dp)
  val bgBrush = if (isPrimary) {
    Brush.horizontalGradient(
      colors = listOf(accentColor, OrangeLight)
    )
  } else {
    Brush.horizontalGradient(
      colors = listOf(DarkCardElevated, DarkCardBg)
    )
  }

  val borderStroke = if (isPrimary) {
    BorderStroke(1.dp, accentColor)
  } else {
    BorderStroke(1.dp, DarkCardBorder)
  }

  Surface(
    onClick = onClick,
    enabled = enabled,
    modifier = modifier
      .testTag(testTag)
      .clip(shape)
      .then(
        if (isPrimary && enabled) {
          Modifier.shadow(8.dp, shape, ambientColor = accentColor, spotColor = accentColor)
        } else Modifier
      ),
    shape = shape,
    color = Color.Transparent,
    border = borderStroke
  ) {
    Box(
      modifier = Modifier
        .background(if (enabled) bgBrush else Brush.linearGradient(listOf(DarkCardBg, DarkCardBg)))
        .padding(horizontal = 16.dp, vertical = 12.dp),
      contentAlignment = Alignment.Center
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        if (icon != null) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isPrimary) DarkBg else accentColor,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
          text = text,
          color = if (isPrimary) DarkBg else TextMain,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp,
          letterSpacing = 0.4.sp
        )
      }
    }
  }
}

@Composable
fun NeonBadge(
  text: String,
  accentColor: Color,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(6.dp),
    color = accentColor.copy(alpha = 0.12f),
    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.4f))
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(12.dp)
        )
      }
      Text(
        text = text,
        color = accentColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        fontFamily = FontFamily.Monospace
      )
    }
  }
}

@Composable
fun CategoryFilterPill(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  dotColor: Color? = null,
  modifier: Modifier = Modifier
) {
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(20.dp),
    color = if (isSelected) OrangePrimary else DarkCardElevated,
    border = BorderStroke(
      1.dp,
      if (isSelected) OrangePrimary else DarkCardBorder
    ),
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      if (dotColor != null) {
        Box(
          modifier = Modifier
            .size(7.dp)
            .clip(CircleShape)
            .background(if (isSelected) DarkBg else dotColor)
        )
      }
      Text(
        text = label,
        color = if (isSelected) DarkBg else TextMain,
        fontSize = 12.sp,
        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
        letterSpacing = 0.4.sp
      )
    }
  }
}

@Composable
fun FrostedGlassBottomBar(
  currentTab: String,
  onTabSelected: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val tabs = listOf(
    NavTabItem("discover", strings.navDiscover, Icons.Filled.Home, Icons.Outlined.Home),
    NavTabItem("categories", strings.navCategories, Icons.Filled.Explore, Icons.Outlined.Explore),
    NavTabItem("profile", strings.navFavorites, Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder),
    NavTabItem("languages", strings.navLanguages, Icons.Filled.Language, Icons.Outlined.Language)
  )

  Surface(
    modifier = modifier
      .fillMaxWidth()
      .navigationBarsPadding()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    shape = RoundedCornerShape(24.dp),
    color = GlassBg,
    border = BorderStroke(1.dp, DarkBorderHighlight),
    shadowElevation = 16.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      tabs.forEach { tab ->
        val isSelected = currentTab == tab.id
        val scale = animateFloatAsState(
          targetValue = if (isSelected) 1.05f else 1.0f,
          animationSpec = tween(durationMillis = 200),
          label = "tab_scale"
        )

        val itemBgModifier = if (isSelected) {
          Modifier.background(OrangeDim)
        } else Modifier

        Box(
          modifier = Modifier
            .testTag("nav_tab_${tab.id}")
            .scale(scale.value)
            .clip(RoundedCornerShape(16.dp))
            .then(itemBgModifier)
            .then(
              if (isSelected) Modifier.border(BorderStroke(1.dp, OrangeBorder), RoundedCornerShape(16.dp)) else Modifier
            )
            .clickable { onTabSelected(tab.id) }
            .padding(horizontal = 14.dp, vertical = 8.dp),
          contentAlignment = Alignment.Center
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = if (isSelected) tab.activeIcon else tab.inactiveIcon,
              contentDescription = tab.label,
              tint = if (isSelected) OrangePrimary else TextMuted,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = tab.label,
              color = if (isSelected) OrangePrimary else TextSubtle,
              fontSize = 10.5.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              letterSpacing = 0.2.sp
            )
          }
        }
      }
    }
  }
}

private data class NavTabItem(
  val id: String,
  val label: String,
  val activeIcon: ImageVector,
  val inactiveIcon: ImageVector
)

@Composable
fun CyberpunkToast(
  message: String,
  isVisible: Boolean,
  modifier: Modifier = Modifier
) {
  AnimatedVisibility(
    visible = isVisible,
    enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn(),
    exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut(),
    modifier = modifier
  ) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = DarkCardElevated,
      border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.8f)),
      shadowElevation = 14.dp,
      modifier = Modifier
        .testTag("cyber_toast")
        .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Box(
          modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(OrangeDim),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = OrangePrimary,
            modifier = Modifier.size(18.dp)
          )
        }
        Column {
          Text(
            text = "PRMTLY NOTIFICATION",
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontSize = 9.sp,
            letterSpacing = 1.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = TextMain,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }
  }
}

