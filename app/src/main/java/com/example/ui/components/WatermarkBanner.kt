package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted

@Composable
fun WatermarkBanner(
  onOpenWatermarkTool: () -> Unit,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .testTag("watermark_banner_card"),
    shape = RoundedCornerShape(20.dp),
    color = DarkCardBg,
    border = BorderStroke(1.dp, DarkCardBorder)
  ) {
    Box(
      modifier = Modifier
        .background(
          Brush.verticalGradient(
            colors = listOf(
              OrangeDim.copy(alpha = 0.15f),
              DarkCardBg
            )
          )
        )
        .padding(24.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // 1. Badge Pill: ★ FREE AI WATERMARK TOOL
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = OrangeDim,
          border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.5f))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Filled.AutoAwesome,
              contentDescription = null,
              tint = OrangePrimary,
              modifier = Modifier.size(13.dp)
            )
            Text(
              text = strings.watermarkBadgeText,
              color = OrangePrimary,
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.8.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Headline
        Text(
          text = strings.watermarkBannerTitle,
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            lineHeight = 27.sp
          ),
          color = TextMain,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Subtitle
        Text(
          text = strings.watermarkBannerSub,
          style = MaterialTheme.typography.bodySmall,
          color = TextMuted,
          textAlign = TextAlign.Center,
          fontSize = 13.sp,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 4. Solid Bright Orange CTA Button
        Surface(
          onClick = onOpenWatermarkTool,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("use_watermark_remover_button"),
          shape = RoundedCornerShape(12.dp),
          color = OrangePrimary
        ) {
          Row(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Filled.AutoAwesome,
              contentDescription = null,
              tint = Color.Black,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = strings.watermarkLaunchBtn,
              color = Color.Black,
              fontWeight = FontWeight.Black,
              fontSize = 13.5.sp,
              letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = Color.Black,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 5. Trust Badge Footer
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = null,
            tint = OrangePrimary,
            modifier = Modifier.size(15.dp)
          )
          Text(
            text = strings.watermarkFeat2,
            color = TextMain,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }
  }
}
