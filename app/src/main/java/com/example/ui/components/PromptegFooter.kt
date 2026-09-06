package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle

@Composable
fun PromptegFooter(
  onNavigateToGallery: () -> Unit,
  onNavigateToFavorites: () -> Unit,
  onOpenWatermarkRemover: () -> Unit,
  onOpenLegalTopic: (String) -> Unit,
  onSocialClick: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 24.dp)
      .testTag("prompteg_footer_section")
  ) {
    // 1. Prompteg Brand Title
    Text(
      text = "Prompteg",
      style = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Black,
        fontSize = 24.sp,
        letterSpacing = (-0.5).sp
      ),
      color = TextMain
    )

    Spacer(modifier = Modifier.height(10.dp))

    // 2. Platform Description
    Text(
      text = strings.footerPlatformDesc,
      style = MaterialTheme.typography.bodyMedium,
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 20.sp
    )

    Spacer(modifier = Modifier.height(18.dp))

    // 3. Gemini Watermark Remover Button (Dark outline pill with orange accent)
    Surface(
      onClick = onOpenWatermarkRemover,
      shape = RoundedCornerShape(10.dp),
      color = DarkCardBg,
      border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.6f)),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Icon(
          imageVector = Icons.Filled.AutoAwesome,
          contentDescription = null,
          tint = OrangePrimary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = strings.footerWatermarkBtn,
          color = OrangePrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 13.5.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowForward,
          contentDescription = null,
          tint = OrangePrimary,
          modifier = Modifier.size(15.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // 4. Social Icons Row (Telegram, Instagram, Threads, Pinterest, Facebook)
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      SocialIconButton(
        icon = Icons.AutoMirrored.Filled.Send,
        label = "Telegram",
        onClick = { onSocialClick("Telegram") }
      )
      SocialIconButton(
        icon = Icons.Filled.CameraAlt,
        label = "Instagram",
        onClick = { onSocialClick("Instagram") }
      )
      SocialIconButton(
        icon = Icons.Filled.AlternateEmail,
        label = "Threads",
        onClick = { onSocialClick("Threads") }
      )
      SocialIconButton(
        icon = Icons.Filled.PushPin,
        label = "Pinterest",
        onClick = { onSocialClick("Pinterest") }
      )
      SocialIconButton(
        icon = Icons.Filled.Public,
        label = "Facebook",
        onClick = { onSocialClick("Facebook") }
      )
    }

    Spacer(modifier = Modifier.height(28.dp))

    // 5. NAVIGATION Section Header
    Text(
      text = strings.footerNavHeader,
      color = OrangePrimary,
      fontWeight = FontWeight.ExtraBold,
      fontSize = 12.sp,
      letterSpacing = 1.sp
    )

    Spacer(modifier = Modifier.height(12.dp))

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      FooterLinkItem(label = strings.footerNavGallery, onClick = onNavigateToGallery)
      FooterLinkItem(label = strings.footerNavFavorites, onClick = onNavigateToFavorites)
      FooterLinkItem(label = strings.footerNavWatermark, onClick = onOpenWatermarkRemover)
      FooterLinkItem(label = strings.footerNavAbout, onClick = { onOpenLegalTopic("About Us") })
    }

    Spacer(modifier = Modifier.height(28.dp))

    // 6. TRUST & LEGAL Section Header
    Text(
      text = strings.footerTrustHeader,
      color = OrangePrimary,
      fontWeight = FontWeight.ExtraBold,
      fontSize = 12.sp,
      letterSpacing = 1.sp
    )

    Spacer(modifier = Modifier.height(12.dp))

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
      FooterLinkItem(label = strings.footerTrustPlatform, onClick = { onOpenLegalTopic("About Platform") })
      FooterLinkItem(label = strings.footerTrustContact, onClick = { onOpenLegalTopic("Contact & Support") })
      FooterLinkItem(label = strings.footerTrustPrivacy, onClick = { onOpenLegalTopic("Privacy Policy") })
      FooterLinkItem(label = strings.footerTrustTerms, onClick = { onOpenLegalTopic("Terms of Service") })
      FooterLinkItem(label = strings.footerTrustDmca, onClick = { onOpenLegalTopic("DMCA & Disclaimer") })
    }

    Spacer(modifier = Modifier.height(28.dp))

    HorizontalDivider(
      color = DarkCardBorder,
      thickness = 1.dp
    )

    Spacer(modifier = Modifier.height(18.dp))

    // 7. Copyright text
    Text(
      text = strings.footerCopyright,
      color = TextSubtle,
      fontSize = 12.sp,
      lineHeight = 18.sp
    )

    Spacer(modifier = Modifier.height(12.dp))

    // 8. Footer Inline Sub-links
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = strings.footerNavAbout,
        color = TextMuted,
        fontSize = 12.sp,
        modifier = Modifier.clickable { onOpenLegalTopic("About Us") }
      )
      Text(text = "•", color = TextSubtle, fontSize = 12.sp)
      Text(
        text = strings.contactSupportLink,
        color = TextMuted,
        fontSize = 12.sp,
        modifier = Modifier.clickable { onOpenLegalTopic("Contact & Support") }
      )
      Text(text = "•", color = TextSubtle, fontSize = 12.sp)
      Text(
        text = "DMCA",
        color = TextMuted,
        fontSize = 12.sp,
        modifier = Modifier.clickable { onOpenLegalTopic("DMCA & Disclaimer") }
      )
      Text(text = "•", color = TextSubtle, fontSize = 12.sp)
      Text(
        text = strings.privacyPolicyLink,
        color = TextMuted,
        fontSize = 12.sp,
        modifier = Modifier.clickable { onOpenLegalTopic("Privacy Policy") }
      )
    }

    Spacer(modifier = Modifier.height(32.dp))
  }
}

@Composable
private fun SocialIconButton(
  icon: ImageVector,
  label: String,
  onClick: () -> Unit
) {
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(8.dp),
    color = DarkCardElevated,
    border = BorderStroke(1.dp, DarkCardBorder),
    modifier = Modifier.size(40.dp)
  ) {
    Box(
      modifier = Modifier.padding(8.dp),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = TextMuted,
        modifier = Modifier.size(20.dp)
      )
    }
  }
}

@Composable
private fun FooterLinkItem(
  label: String,
  onClick: () -> Unit
) {
  Text(
    text = label,
    color = TextMuted,
    fontSize = 13.5.sp,
    fontWeight = FontWeight.Medium,
    modifier = Modifier
      .clickable(onClick = onClick)
      .padding(vertical = 2.dp)
  )
}
