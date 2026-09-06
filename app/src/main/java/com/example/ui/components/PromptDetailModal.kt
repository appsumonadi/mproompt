package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PromptDetailModal(
  prompt: PromptItem,
  onDismiss: () -> Unit,
  onCopyPrompt: (String) -> Unit,
  onBookmarkClick: () -> Unit,
  onLikeClick: () -> Unit,
  onRemixInStudio: (PromptItem, Map<String, String>) -> Unit,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val variableValues = remember(prompt.id) {
    mutableStateMapOf<String, String>().apply {
      prompt.variables.forEach { v -> put(v.key, v.defaultValue) }
    }
  }

  val compiledPrompt = remember(prompt.promptTemplate, variableValues.toMap()) {
    prompt.compilePrompt(variableValues)
  }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = DarkBg,
    tonalElevation = 8.dp,
    dragHandle = null,
    modifier = modifier.testTag("prompt_detail_modal")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .navigationBarsPadding()
        .imePadding()
        .padding(bottom = 24.dp)
    ) {
      // Top Hero Image Banner
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .aspectRatio(1.4f)
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
              CircularProgressIndicator(color = OrangePrimary, strokeWidth = 2.dp)
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
                  Color(0x90000000),
                  Color.Transparent,
                  Color(0xDD08090C),
                  DarkBg
                )
              )
            )
        )

        // Close Button & Quick Actions Header
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Close button
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(Color(0xCC121318))
              .border(BorderStroke(1.dp, DarkCardBorder), CircleShape)
              .clickable { onDismiss() },
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Filled.Close,
              contentDescription = "Close",
              tint = TextMain,
              modifier = Modifier.size(20.dp)
            )
          }

          // Top Action Row (Bookmark & Like)
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Like Button
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xCC121318))
                .border(BorderStroke(1.dp, DarkCardBorder), CircleShape)
                .clickable { onLikeClick() },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (prompt.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Like",
                tint = if (prompt.isLiked) AccentRed else TextMuted,
                modifier = Modifier.size(18.dp)
              )
            }

            // Bookmark Button
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xCC121318))
                .border(BorderStroke(1.dp, if (prompt.isBookmarked) OrangeBorder else DarkCardBorder), CircleShape)
                .clickable { onBookmarkClick() },
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = if (prompt.isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = if (prompt.isBookmarked) OrangePrimary else TextMuted,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }

        // Title and Category in Bottom Hero
        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            NeonBadge(
              text = prompt.model.displayName,
              accentColor = prompt.model.tagColor
            )
            NeonBadge(
              text = strings.getCategoryTitle(prompt.category),
              accentColor = OrangePrimary
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = prompt.title,
            style = MaterialTheme.typography.titleLarge,
            color = TextMain,
            fontWeight = FontWeight.Black
          )
        }
      }

      // Body Details
      Column(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        Text(
          text = prompt.description,
          style = MaterialTheme.typography.bodyMedium,
          color = TextMuted
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Variable Tuner Section
        if (prompt.variables.isNotEmpty()) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Filled.Tune,
              contentDescription = null,
              tint = OrangePrimary,
              modifier = Modifier.size(16.dp)
            )
            Text(
              text = strings.variableTuner,
              style = MaterialTheme.typography.labelSmall,
              color = OrangePrimary,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          prompt.variables.forEach { variable ->
            val currentValue = variableValues[variable.key] ?: variable.defaultValue

            GlowingCard(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              backgroundColor = DarkCardBg,
              borderColor = DarkCardBorder
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Text(
                  text = variable.label.uppercase(),
                  style = MaterialTheme.typography.labelSmall,
                  color = TextMuted,
                  fontSize = 10.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                  value = currentValue,
                  onValueChange = { variableValues[variable.key] = it },
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("var_input_${variable.key}"),
                  textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextMain),
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = OrangePrimary,
                    unfocusedBorderColor = DarkBorderHighlight,
                    focusedContainerColor = DarkCardElevated,
                    unfocusedContainerColor = DarkCardElevated,
                    cursorColor = OrangePrimary
                  ),
                  shape = RoundedCornerShape(10.dp),
                  singleLine = false,
                  maxLines = 2
                )

                if (variable.suggestedOptions.isNotEmpty()) {
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                    text = strings.suggestionsLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSubtle,
                    fontSize = 10.sp
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                  ) {
                    variable.suggestedOptions.forEach { opt ->
                      val isSelected = currentValue == opt
                      Surface(
                        onClick = { variableValues[variable.key] = opt },
                        shape = RoundedCornerShape(6.dp),
                        color = if (isSelected) OrangeDim else DarkCardElevated,
                        border = BorderStroke(
                          1.dp,
                          if (isSelected) OrangePrimary else DarkBorderHighlight
                        )
                      ) {
                        Text(
                          text = opt,
                          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                          style = MaterialTheme.typography.bodySmall,
                          color = if (isSelected) OrangePrimary else TextMuted,
                          fontSize = 11.sp
                        )
                      }
                    }
                  }
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))
        }

        // Live Compiled Prompt Display
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = strings.compiledPrompt,
            style = MaterialTheme.typography.labelSmall,
            color = OrangePrimary,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )

          Text(
            text = "${compiledPrompt.length} ${strings.charsCount}",
            style = MaterialTheme.typography.labelSmall,
            color = TextSubtle,
            fontSize = 10.sp
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        GlowingCard(
          modifier = Modifier.fillMaxWidth(),
          backgroundColor = DarkCardBg,
          borderColor = OrangeBorder,
          glowColor = OrangeDim
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = compiledPrompt,
              style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontSize = 12.5.sp,
                lineHeight = 19.sp,
                color = TextMain
              )
            )
          }
        }

        // Negative Prompt (if present)
        if (prompt.negativePrompt.isNotBlank()) {
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = strings.negativePromptLabel,
            style = MaterialTheme.typography.labelSmall,
            color = AccentRed,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )

          Spacer(modifier = Modifier.height(6.dp))

          GlowingCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = DarkCardBg,
            borderColor = AccentRed.copy(alpha = 0.4f)
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = prompt.negativePrompt,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontFamily = FontFamily.Monospace,
                  fontSize = 11.sp,
                  color = TextMuted
                )
              )
            }
          }
        }

        // Model Parameters Breakdown
        if (prompt.parameters.isNotEmpty()) {
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = strings.parametersLabel,
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
            prompt.parameters.forEach { (param, value) ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = DarkCardElevated,
                border = BorderStroke(1.dp, DarkBorderHighlight)
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Text(
                    text = param,
                    style = MaterialTheme.typography.labelSmall,
                    color = OrangePrimary,
                    fontWeight = FontWeight.Bold
                  )
                  Text(
                    text = value,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMain
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons: Copy Prompt & Remix in Studio
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          NeonButton(
            text = strings.copyPrompt,
            onClick = { onCopyPrompt(compiledPrompt) },
            icon = Icons.Filled.ContentCopy,
            isPrimary = true,
            accentColor = OrangePrimary,
            modifier = Modifier.weight(1f),
            testTag = "detail_copy_button"
          )

          NeonButton(
            text = strings.remixPrompt,
            onClick = { onRemixInStudio(prompt, variableValues.toMap()) },
            icon = Icons.Filled.AutoAwesome,
            isPrimary = false,
            accentColor = OrangePrimary,
            modifier = Modifier.weight(1f),
            testTag = "detail_remix_button"
          )
        }
      }
    }
  }
}

