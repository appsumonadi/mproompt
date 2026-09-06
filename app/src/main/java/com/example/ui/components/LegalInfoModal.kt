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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegalInfoModal(
  topicTitle: String,
  onDismiss: () -> Unit,
  onSubmitContactForm: (String, String) -> Unit,
  modifier: Modifier = Modifier
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = DarkBg,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    dragHandle = {
      Box(
        modifier = Modifier
          .padding(vertical = 12.dp)
          .size(width = 44.dp, height = 4.dp)
          .clip(CircleShape)
          .background(DarkCardBorder)
      )
    },
    modifier = modifier.testTag("legal_info_modal")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .padding(bottom = 36.dp)
        .navigationBarsPadding()
        .imePadding()
        .verticalScroll(rememberScrollState())
    ) {
      // Header with close button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = OrangeDim,
            border = BorderStroke(1.dp, OrangePrimary.copy(alpha = 0.4f)),
            modifier = Modifier.size(36.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = when {
                  topicTitle.contains("Contact", ignoreCase = true) -> Icons.Filled.Email
                  topicTitle.contains("Privacy", ignoreCase = true) -> Icons.Filled.Security
                  topicTitle.contains("DMCA", ignoreCase = true) -> Icons.Filled.Policy
                  else -> Icons.Filled.Info
                },
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Text(
            text = topicTitle,
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Black,
              fontSize = 20.sp
            ),
            color = TextMain
          )
        }

        IconButton(
          onClick = onDismiss,
          modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(DarkCardElevated)
        ) {
          Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            tint = TextMuted,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      when {
        topicTitle.contains("Contact", ignoreCase = true) -> {
          ContactAndSupportView(onSubmit = onSubmitContactForm)
        }
        topicTitle.contains("Privacy", ignoreCase = true) -> {
          PrivacyPolicyView()
        }
        topicTitle.contains("Terms", ignoreCase = true) -> {
          TermsOfServiceView()
        }
        topicTitle.contains("DMCA", ignoreCase = true) -> {
          DmcaDisclaimerView()
        }
        else -> {
          AboutPlatformView()
        }
      }
    }
  }
}

@Composable
private fun AboutPlatformView() {
  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      text = "Welcome to Prompteg",
      color = OrangePrimary,
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp
    )
    Text(
      text = "Prompteg is a next-generation AI prompt engineering platform and creative utility for designers, creators, and developers working with Midjourney, Gemini, ChatGPT-4o, Flux.1, and SDXL.",
      color = TextMuted,
      fontSize = 13.5.sp,
      lineHeight = 20.sp
    )
    Text(
      text = "Our mission is to eliminate prompt trial-and-error by providing tested, production-ready prompts with dynamic variables, high-res visual references, and in-app AI utility tools like the Gemini Watermark Remover.",
      color = TextMuted,
      fontSize = 13.5.sp,
      lineHeight = 20.sp
    )

    Spacer(modifier = Modifier.height(6.dp))

    Surface(
      shape = RoundedCornerShape(12.dp),
      color = DarkCardBg,
      border = BorderStroke(1.dp, DarkCardBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(text = "⚡ Key Highlights", color = TextMain, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        Text(text = "• 100% Curated and Tested Prompts across 10+ Categories", color = TextSubtle, fontSize = 12.sp)
        Text(text = "• Dynamic Variable Remixer & One-Tap Copy Engine", color = TextSubtle, fontSize = 12.sp)
        Text(text = "• Integrated Gemini & AI Watermark Eraser", color = TextSubtle, fontSize = 12.sp)
        Text(text = "• Offline-First Local Storage via Room Database", color = TextSubtle, fontSize = 12.sp)
      }
    }
  }
}

@Composable
private fun ContactAndSupportView(
  onSubmit: (String, String) -> Unit
) {
  var emailInput by remember { mutableStateOf("") }
  var messageInput by remember { mutableStateOf("") }
  var isSubmitted by remember { mutableStateOf(false) }

  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Text(
      text = "We're here to help!",
      color = OrangePrimary,
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp
    )
    Text(
      text = "Have questions about prompt engineering, need API support, or want to report an issue? Send us a message or email support@prompteg.ai.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )

    if (isSubmitted) {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = OrangeDim,
        border = BorderStroke(1.dp, OrangePrimary),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
          Text(text = "✓ Message Received!", color = OrangePrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "Our team will respond to $emailInput within 24 hours.", color = TextMain, fontSize = 12.sp)
        }
      }
    } else {
      OutlinedTextField(
        value = emailInput,
        onValueChange = { emailInput = it },
        label = { Text("Your Email Address") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = OrangePrimary,
          unfocusedBorderColor = DarkCardBorder,
          focusedContainerColor = DarkCardBg,
          unfocusedContainerColor = DarkCardBg,
          cursorColor = OrangePrimary
        )
      )

      OutlinedTextField(
        value = messageInput,
        onValueChange = { messageInput = it },
        label = { Text("How can we help?") },
        modifier = Modifier
          .fillMaxWidth()
          .height(110.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = OrangePrimary,
          unfocusedBorderColor = DarkCardBorder,
          focusedContainerColor = DarkCardBg,
          unfocusedContainerColor = DarkCardBg,
          cursorColor = OrangePrimary
        )
      )

      Surface(
        onClick = {
          if (emailInput.isNotBlank() && messageInput.isNotBlank()) {
            onSubmit(emailInput, messageInput)
            isSubmitted = true
          }
        },
        enabled = emailInput.isNotBlank() && messageInput.isNotBlank(),
        shape = RoundedCornerShape(10.dp),
        color = OrangePrimary,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(vertical = 12.dp),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(imageVector = Icons.Filled.Send, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = "Send Message", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
        }
      }
    }
  }
}

@Composable
private fun PrivacyPolicyView() {
  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(text = "Last updated: 2026", color = TextSubtle, fontSize = 11.5.sp)
    Text(
      text = "Your Privacy Comes First",
      color = OrangePrimary,
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp
    )
    Text(
      text = "1. Data Collection: Prompteg operates under an offline-first privacy model. Your custom created prompts, favorites, and history logs are stored securely on your local device.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )
    Text(
      text = "2. No Image Storage: Images processed in the Gemini Watermark Remover are rendered client-side on your device and are never transmitted to third-party tracking servers.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )
    Text(
      text = "3. Zero Ads / Zero Profiling: We do not sell user data, fingerprint devices, or serve behavioral advertisements.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )
  }
}

@Composable
private fun TermsOfServiceView() {
  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(text = "Last updated: 2026", color = TextSubtle, fontSize = 11.5.sp)
    Text(
      text = "Terms of Service",
      color = OrangePrimary,
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp
    )
    Text(
      text = "1. Prompt Usage: Prompts provided in the Prompteg library are free for both personal and commercial creative use.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )
    Text(
      text = "2. AI Service Compliance: When executing prompts on external AI engines (Midjourney, OpenAI ChatGPT, Google Gemini), users are responsible for complying with the respective engine's terms and content policies.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )
  }
}

@Composable
private fun DmcaDisclaimerView() {
  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    Text(
      text = "DMCA & Disclaimer Notice",
      color = OrangePrimary,
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp
    )
    Text(
      text = "Prompteg is a prompt engineering library and reference index. Prompt text snippets and model templates are community-shared parameters for generative synthesis.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )
    Text(
      text = "To submit a copyright or trademark inquiry, please email dmca@prompteg.ai with the relevant prompt ID and documentation for immediate review.",
      color = TextMuted,
      fontSize = 13.sp,
      lineHeight = 19.sp
    )
  }
}
