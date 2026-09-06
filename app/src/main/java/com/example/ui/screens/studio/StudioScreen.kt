package com.example.ui.screens.studio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AIModel
import com.example.data.model.PromptCategory
import com.example.ui.components.GlowingCard
import com.example.ui.components.NeonBadge
import com.example.ui.components.NeonButton
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
import com.example.ui.viewmodel.PromptViewModel
import com.example.ui.viewmodel.StudioMode

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StudioScreen(
  viewModel: PromptViewModel,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val mode by viewModel.studioMode.collectAsState()
  val synthModel by viewModel.synthModel.collectAsState()
  val synthSubject by viewModel.synthSubject.collectAsState()
  val synthStylePreset by viewModel.synthStylePreset.collectAsState()
  val synthLighting by viewModel.synthLighting.collectAsState()
  val synthCameraAngle by viewModel.synthCameraAngle.collectAsState()
  val synthAspectRatio by viewModel.synthAspectRatio.collectAsState()

  val customTitle by viewModel.customTitle.collectAsState()
  val customDesc by viewModel.customDescription.collectAsState()
  val customTemplate by viewModel.customTemplate.collectAsState()
  val customNeg by viewModel.customNegativePrompt.collectAsState()
  val customModel by viewModel.customModel.collectAsState()
  val customCategory by viewModel.customCategory.collectAsState()
  val customImgUrl by viewModel.customImageUrl.collectAsState()

  val compiledSynth = viewModel.compileSynthesizerPrompt()

  Column(
    modifier = modifier
      .fillMaxSize()
      .testTag("studio_screen")
      .verticalScroll(rememberScrollState())
      .navigationBarsPadding()
      .imePadding()
      .padding(bottom = 110.dp)
  ) {
    // Top Studio Title
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = Icons.Filled.AutoAwesome,
          contentDescription = null,
          tint = OrangePrimary,
          modifier = Modifier.size(24.dp)
        )
        Text(
          text = strings.studioTitle,
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
        text = strings.studioSubtitle,
        style = MaterialTheme.typography.bodyMedium,
        color = TextMuted
      )

      Spacer(modifier = Modifier.height(14.dp))

      // Studio Mode Switcher Tabs
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(DarkCardBg)
          .border(BorderStroke(1.dp, DarkCardBorder), RoundedCornerShape(12.dp))
          .padding(4.dp)
      ) {
        val isSynth = mode == StudioMode.SYNTHESIZER
        Surface(
          onClick = { viewModel.studioMode.value = StudioMode.SYNTHESIZER },
          shape = RoundedCornerShape(8.dp),
          color = if (isSynth) OrangePrimary else Color.Transparent,
          modifier = Modifier.weight(1f)
        ) {
          Row(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = strings.crafterTab,
              color = if (isSynth) DarkBg else TextMuted,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold
            )
          }
        }

        val isCustom = mode == StudioMode.CUSTOM_CREATOR
        Surface(
          onClick = { viewModel.studioMode.value = StudioMode.CUSTOM_CREATOR },
          shape = RoundedCornerShape(8.dp),
          color = if (isCustom) OrangePrimary else Color.Transparent,
          modifier = Modifier.weight(1f)
        ) {
          Row(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = strings.customTab,
              color = if (isCustom) DarkBg else TextMuted,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }

    if (mode == StudioMode.SYNTHESIZER) {
      // SYNTHESIZER MODE
      Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // 1. Target Engine
        Column {
          Text(
            text = strings.targetEngine,
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(AIModel.values()) { m ->
              val isSel = synthModel == m
              Surface(
                onClick = { viewModel.synthModel.value = m },
                shape = RoundedCornerShape(10.dp),
                color = if (isSel) OrangeDim else DarkCardBg,
                border = BorderStroke(1.dp, if (isSel) OrangePrimary else DarkCardBorder)
              ) {
                Text(
                  text = m.displayName,
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                  color = if (isSel) OrangePrimary else TextMuted,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }

        // 2. Subject / Core Topic
        Column {
          Text(
            text = strings.subjectLabel,
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = synthSubject,
            onValueChange = { viewModel.synthSubject.value = it },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("synth_subject_input"),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextMain),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedContainerColor = DarkCardBg,
              unfocusedContainerColor = DarkCardBg,
              cursorColor = OrangePrimary
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text(strings.subjectPlaceholder, color = TextSubtle) }
          )
        }

        // 3. Style Presets
        val styles = listOf(
          "Warm Golden Lighting",
          "Unreal Engine 5 Render",
          "Minimalist Clean Vector",
          "Cinematic 35mm Arri Film",
          "Cyberpunk Neon & Chrome",
          "Hyperrealistic 8k Macro",
          "Moody Chiaroscuro",
          "Isometric 3D Minimal"
        )
        Column {
          Text(
            text = "AESTHETIC & RENDERING STYLE",
            style = MaterialTheme.typography.labelSmall,
            color = OrangeLight,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            styles.forEach { style ->
              val isSel = synthStylePreset == style
              Surface(
                onClick = { viewModel.synthStylePreset.value = style },
                shape = RoundedCornerShape(8.dp),
                color = if (isSel) OrangeDim else DarkCardElevated,
                border = BorderStroke(1.dp, if (isSel) OrangePrimary else DarkCardBorder)
              ) {
                Text(
                  text = style,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  color = if (isSel) OrangePrimary else TextMuted,
                  style = MaterialTheme.typography.bodySmall,
                  fontSize = 11.5.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                )
              }
            }
          }
        }

        // 4. Lighting Atmosphere
        val lightings = listOf(
          "Warm golden hour mist & sunset rays",
          "Soft cinematic studio rim light",
          "Diffused high-fashion softbox",
          "Moody high-contrast chiaroscuro",
          "Volumetric atmospheric glow",
          "Clean bright daylight ambient"
        )
        Column {
          Text(
            text = "ATMOSPHERIC LIGHTING",
            style = MaterialTheme.typography.labelSmall,
            color = OrangeLight,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            lightings.forEach { light ->
              val isSel = synthLighting == light
              Surface(
                onClick = { viewModel.synthLighting.value = light },
                shape = RoundedCornerShape(8.dp),
                color = if (isSel) OrangeDim else DarkCardElevated,
                border = BorderStroke(1.dp, if (isSel) OrangePrimary else DarkCardBorder)
              ) {
                Text(
                  text = light,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  color = if (isSel) OrangePrimary else TextMuted,
                  style = MaterialTheme.typography.bodySmall,
                  fontSize = 11.5.sp,
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                )
              }
            }
          }
        }

        // 5. Aspect Ratio (for image engines)
        val aspectRatios = listOf("16:9", "4:5", "1:1", "9:16", "21:9")
        Column {
          Text(
            text = strings.aspectRatioLabel,
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            aspectRatios.forEach { ar ->
              val isSel = synthAspectRatio == ar
              Surface(
                onClick = { viewModel.synthAspectRatio.value = ar },
                shape = RoundedCornerShape(8.dp),
                color = if (isSel) OrangeDim else DarkCardElevated,
                border = BorderStroke(1.dp, if (isSel) OrangePrimary else DarkCardBorder)
              ) {
                Text(
                  text = ar,
                  modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                  color = if (isSel) OrangePrimary else TextMuted,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }

        // 6. Live Synthesized Prompt Output
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = strings.liveSynthesizedPrompt,
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.2.sp
          )

          Text(
            text = "${compiledSynth.length} ${strings.charsCount}",
            style = MaterialTheme.typography.labelSmall,
            color = TextSubtle,
            fontSize = 10.sp
          )
        }

        GlowingCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = DarkCardBg,
          borderColor = OrangeBorder,
          glowColor = OrangeDim
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = compiledSynth,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                color = TextMain
              )
            )

            Spacer(modifier = Modifier.height(16.dp))

            NeonButton(
              text = strings.copySynthPrompt,
              onClick = { viewModel.copyRawText(compiledSynth, "Synthesized: $synthSubject") },
              icon = Icons.Filled.ContentCopy,
              isPrimary = true,
              accentColor = OrangePrimary,
              modifier = Modifier.fillMaxWidth(),
              testTag = "copy_synth_prompt_button"
            )
          }
        }
      }
    } else {
      // CUSTOM CREATOR / REMIX MODE
      Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Title
        Column {
          Text(
            text = strings.promptTitleLabel,
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = customTitle,
            onValueChange = { viewModel.customTitle.value = it },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("custom_title_input"),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextMain),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedContainerColor = DarkCardBg,
              unfocusedContainerColor = DarkCardBg,
              cursorColor = OrangePrimary
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("e.g. Cyber Samurai in Neo-Tokyo", color = TextSubtle) }
          )
        }

        // Description
        Column {
          Text(
            text = strings.shortDescLabel,
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = customDesc,
            onValueChange = { viewModel.customDescription.value = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextMain),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedContainerColor = DarkCardBg,
              unfocusedContainerColor = DarkCardBg,
              cursorColor = OrangePrimary
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Briefly explain what this prompt produces...", color = TextSubtle) }
          )
        }

        // Template with [variables]
        Column {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = strings.promptTemplateLabel,
              style = MaterialTheme.typography.labelSmall,
              color = OrangePrimary,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Text(
              text = "Use {variable} for tuners",
              style = MaterialTheme.typography.labelSmall,
              color = OrangePrimary,
              fontSize = 10.sp
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = customTemplate,
            onValueChange = { viewModel.customTemplate.value = it },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("custom_template_input"),
            minLines = 4,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
              fontFamily = FontFamily.Monospace,
              color = TextMain
            ),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedContainerColor = DarkCardBg,
              unfocusedContainerColor = DarkCardBg,
              cursorColor = OrangePrimary
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text(strings.promptTemplatePlaceholder, color = TextSubtle) }
          )
        }

        // Negative Prompt
        Column {
          Text(
            text = strings.negativePromptLabel,
            style = MaterialTheme.typography.labelSmall,
            color = AccentRed,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = customNeg,
            onValueChange = { viewModel.customNegativePrompt.value = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
              fontFamily = FontFamily.Monospace,
              color = TextMain
            ),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = AccentRed,
              unfocusedBorderColor = DarkCardBorder,
              focusedContainerColor = DarkCardBg,
              unfocusedContainerColor = DarkCardBg,
              cursorColor = AccentRed
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("blurry, low quality, deformed, artifacts...", color = TextSubtle) }
          )
        }

        // Model & Category Pickers
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = strings.targetEngine,
              style = MaterialTheme.typography.labelSmall,
              color = OrangePrimary,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              items(AIModel.values()) { m ->
                val isSel = customModel == m
                Surface(
                  onClick = { viewModel.customModel.value = m },
                  shape = RoundedCornerShape(8.dp),
                  color = if (isSel) OrangeDim else DarkCardElevated,
                  border = BorderStroke(1.dp, if (isSel) OrangePrimary else DarkCardBorder)
                ) {
                  Text(
                    text = m.shortBadge,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    color = if (isSel) OrangePrimary else TextMuted,
                    style = MaterialTheme.typography.labelSmall
                  )
                }
              }
            }
          }

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = strings.filterByCategory,
              style = MaterialTheme.typography.labelSmall,
              color = OrangeLight,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              items(PromptCategory.values()) { cat ->
                val isSel = customCategory == cat
                Surface(
                  onClick = { viewModel.customCategory.value = cat },
                  shape = RoundedCornerShape(8.dp),
                  color = if (isSel) OrangeDim else DarkCardElevated,
                  border = BorderStroke(1.dp, if (isSel) OrangePrimary else DarkCardBorder)
                ) {
                  Text(
                    text = strings.getCategoryTitle(cat).take(10) + "..",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    color = if (isSel) OrangePrimary else TextMuted,
                    style = MaterialTheme.typography.labelSmall
                  )
                }
              }
            }
          }
        }

        // Image URL
        Column {
          Text(
            text = "COVER IMAGE URL (OPTIONAL)",
            style = MaterialTheme.typography.labelSmall,
            color = TextSubtle,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(6.dp))
          OutlinedTextField(
            value = customImgUrl,
            onValueChange = { viewModel.customImageUrl.value = it },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextMain),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = OrangePrimary,
              unfocusedBorderColor = DarkCardBorder,
              focusedContainerColor = DarkCardBg,
              unfocusedContainerColor = DarkCardBg,
              cursorColor = OrangePrimary
            ),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("https://images.unsplash.com/...", color = TextSubtle) }
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Publish Button
        NeonButton(
          text = strings.publishToVault,
          onClick = { viewModel.saveCustomPrompt() },
          icon = Icons.Filled.Save,
          isPrimary = true,
          accentColor = OrangePrimary,
          modifier = Modifier.fillMaxWidth(),
          testTag = "save_custom_prompt_button"
        )
      }
    }
  }
}

