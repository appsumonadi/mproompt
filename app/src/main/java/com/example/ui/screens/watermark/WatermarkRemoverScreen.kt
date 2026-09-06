package com.example.ui.screens.watermark

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.ui.components.NeonBadge
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkCardElevated
import com.example.ui.theme.OrangeDim
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSubtle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

data class WatermarkSample(
  val title: String,
  val category: String,
  val originalUrl: String,
  val watermarkType: String,
  val prompt: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatermarkRemoverScreen(
  onBack: () -> Unit,
  onShowToast: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val strings = LocalAppStrings.current
  val samples = remember {
    listOf(
      WatermarkSample(
        title = "Gemini Retro Avatar",
        category = "3D Character",
        originalUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1000&auto=format&fit=crop&q=80",
        watermarkType = "Gemini Sparkle Symbol",
        prompt = "Vibrant 3D stylized character floating pose, glossy clay textures, isometric viewpoint"
      ),
      WatermarkSample(
        title = "Editorial Portrait",
        category = "Realistic",
        originalUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=1000&auto=format&fit=crop&q=80",
        watermarkType = "Imagen Bottom-Right Watermark",
        prompt = "Cinematic 8k close-up high-fashion portrait, natural skin pores, 85mm f/1.4 lens"
      ),
      WatermarkSample(
        title = "Neon Cyberpunk Samurai",
        category = "Cinematic",
        originalUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1000&auto=format&fit=crop&q=80",
        watermarkType = "AI Model Signature",
        prompt = "Full-body cinematic portrait on rainy Neo-Tokyo rooftop, reflective wet pavement"
      ),
      WatermarkSample(
        title = "Concept Hypercar",
        category = "Vehicles",
        originalUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=1000&auto=format&fit=crop&q=80",
        watermarkType = "Gemini Sparkle Corner",
        prompt = "Aerodynamic concept hypercar speeding on coastal highway, glowing active aero spoilers"
      )
    )
  }

  var selectedSampleIndex by remember { mutableIntStateOf(0) }
  var customImageUri by remember { mutableStateOf<String?>(null) }
  var isProcessing by remember { mutableStateOf(false) }
  var isCleaned by remember { mutableStateOf(false) }
  var sliderPosition by remember { mutableFloatStateOf(0.5f) }
  var brushRadius by remember { mutableFloatStateOf(35f) }
  var selectedAlgorithm by remember { mutableStateOf("Smart AI Inpainting") }

  val coroutineScope = rememberCoroutineScope()

  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri ->
    if (uri != null) {
      customImageUri = uri.toString()
      isCleaned = false
      onShowToast(strings.watermarkLoadedToast)
    }
  }

  val currentImageUrl = customImageUri ?: samples[selectedSampleIndex].originalUrl
  val currentPrompt = if (customImageUri != null) "Uploaded custom photo" else samples[selectedSampleIndex].prompt

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBg)
      .statusBarsPadding()
      .testTag("watermark_remover_screen"),
    contentPadding = PaddingValues(bottom = 80.dp)
  ) {
    // 1. Top Bar with back button & badge
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          IconButton(
            onClick = onBack,
            modifier = Modifier
              .size(38.dp)
              .clip(CircleShape)
              .background(DarkCardElevated)
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = TextMain,
              modifier = Modifier.size(20.dp)
            )
          }

          Column {
            Text(
              text = strings.watermarkTitle,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Black,
                fontSize = 18.sp
              ),
              color = TextMain
            )
            Text(
              text = strings.watermarkSub,
              style = MaterialTheme.typography.labelSmall,
              color = OrangePrimary,
              fontWeight = FontWeight.Bold
            )
          }
        }

        NeonBadge(
          text = strings.watermarkFreeBadge,
          accentColor = OrangePrimary,
          icon = Icons.Filled.AutoAwesome
        )
      }
    }

    // 2. Interactive Preview Canvas with Before / After Split Slider
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 8.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = DarkCardBg,
          border = BorderStroke(1.dp, if (isCleaned) OrangePrimary else DarkCardBorder),
          modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
        ) {
          BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val containerWidth = maxWidth
            val containerHeight = maxHeight

            // Render Base Image
            SubcomposeAsyncImage(
              model = currentImageUrl,
              contentDescription = "Target Image",
              contentScale = ContentScale.Crop,
              modifier = Modifier.fillMaxSize(),
              loading = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                  CircularProgressIndicator(color = OrangePrimary, strokeWidth = 2.dp)
                }
              }
            )

            // If not cleaned yet, draw Gemini Watermark overlay in bottom right corner
            if (!isCleaned) {
              Box(
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .padding(16.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(Color.Black.copy(alpha = 0.65f))
                  .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)), RoundedCornerShape(8.dp))
                  .padding(horizontal = 10.dp, vertical = 5.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                  Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                  )
                  Text(
                    text = "Gemini",
                    color = Color.White,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }

            // If Cleaned, show interactive Before / After Comparison split
            if (isCleaned) {
              // Simulated cleaned image (clean corner overlay healing the watermark)
              Box(
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .padding(16.dp)
                  .clip(RoundedCornerShape(8.dp))
                  .background(OrangePrimary.copy(alpha = 0.2f))
                  .border(BorderStroke(1.dp, OrangePrimary), RoundedCornerShape(8.dp))
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = OrangePrimary,
                    modifier = Modifier.size(13.dp)
                  )
                  Text(
                    text = "WATERMARK REMOVED",
                    color = OrangePrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold
                  )
                }
              }
            }

            // Top Status Label Pill
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = Color.Black.copy(alpha = 0.75f),
              border = BorderStroke(1.dp, Color.White.copy(alpha = 0.15f)),
              modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Box(
                  modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(if (isCleaned) OrangePrimary else Color(0xFFFF5252))
                )
                Text(
                  text = if (isCleaned) "CLEANED (AI RECONSTRUCTED)" else "ORIGINAL (WATERMARKED)",
                  color = Color.White,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 0.5.sp
                )
              }
            }

            // Processing Overlay Animation
            if (isProcessing) {
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
              ) {
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                  CircularProgressIndicator(
                    color = OrangePrimary,
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(48.dp)
                  )
                  Text(
                    text = "Inpainting & Erasing Watermark...",
                    color = TextMain,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                  )
                }
              }
            }
          }
        }
      }
    }

    // 3. Primary Action Buttons
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 10.dp)
      ) {
        if (!isCleaned) {
          // Erase Button
          Surface(
            onClick = {
              isProcessing = true
              coroutineScope.launch {
                delay(1200)
                isProcessing = false
                isCleaned = true
                onShowToast("Watermark cleanly erased with AI inpainting!")
              }
            },
            shape = RoundedCornerShape(14.dp),
            color = OrangePrimary,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier.padding(vertical = 14.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Filled.AutoAwesome,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "CLEAN WATERMARK NOW",
                color = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp
              )
            }
          }
        } else {
          // Download and Copy row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Surface(
              onClick = {
                onShowToast("Cleaned HD image saved to device gallery!")
              },
              shape = RoundedCornerShape(12.dp),
              color = OrangePrimary,
              modifier = Modifier.weight(1.3f)
            ) {
              Row(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Filled.Download,
                  contentDescription = null,
                  tint = Color.Black,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Download Clean HD",
                  color = Color.Black,
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp
                )
              }
            }

            Surface(
              onClick = {
                isCleaned = false
                onShowToast("Reset to original image")
              },
              shape = RoundedCornerShape(12.dp),
              color = DarkCardElevated,
              border = BorderStroke(1.dp, DarkCardBorder),
              modifier = Modifier.weight(0.7f)
            ) {
              Row(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Filled.Refresh,
                  contentDescription = null,
                  tint = TextMain,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "Reset",
                  color = TextMain,
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 13.sp
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Upload custom image button
        Surface(
          onClick = { photoPickerLauncher.launch("image/*") },
          shape = RoundedCornerShape(12.dp),
          color = DarkCardElevated,
          border = BorderStroke(1.dp, DarkCardBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Filled.AddPhotoAlternate,
              contentDescription = null,
              tint = OrangePrimary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Upload My Own Photo / Gemini Image",
              color = TextMain,
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp
            )
          }
        }
      }
    }

    // 4. Sample Presets Carousel
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 16.dp)
      ) {
        Text(
          text = "Try with Sample Watermarked Images:",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
          color = TextMain,
          modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
          contentPadding = PaddingValues(horizontal = 20.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          itemsIndexed(samples) { index, sample ->
            val isSelected = selectedSampleIndex == index && customImageUri == null
            Surface(
              onClick = {
                selectedSampleIndex = index
                customImageUri = null
                isCleaned = false
              },
              shape = RoundedCornerShape(14.dp),
              color = if (isSelected) OrangeDim else DarkCardBg,
              border = BorderStroke(1.5.dp, if (isSelected) OrangePrimary else DarkCardBorder),
              modifier = Modifier.width(130.dp)
            ) {
              Column(modifier = Modifier.padding(8.dp)) {
                SubcomposeAsyncImage(
                  model = sample.originalUrl,
                  contentDescription = sample.title,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = sample.title,
                  color = if (isSelected) OrangePrimary else TextMain,
                  fontSize = 11.5.sp,
                  fontWeight = FontWeight.Bold,
                  maxLines = 1
                )
                Text(
                  text = sample.category,
                  color = TextSubtle,
                  fontSize = 10.sp
                )
              }
            }
          }
        }
      }
    }

    // 5. Inpainting Engine Settings Card
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 20.dp)
      ) {
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = DarkCardBg,
          border = BorderStroke(1.dp, DarkCardBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(
                imageVector = Icons.Filled.Tune,
                contentDescription = null,
                tint = OrangePrimary,
                modifier = Modifier.size(18.dp)
              )
              Text(
                text = "Eraser Engine Settings",
                color = TextMain,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
              )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Algorithm selection
            Text(
              text = "Detection Mode:",
              color = TextMuted,
              fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              listOf("Smart AI Inpainting", "Gemini Sparkle Corner", "Edge Reconstruction").forEach { algo ->
                val isSelected = selectedAlgorithm == algo
                Surface(
                  onClick = { selectedAlgorithm = algo },
                  shape = RoundedCornerShape(8.dp),
                  color = if (isSelected) OrangeDim else DarkCardElevated,
                  border = BorderStroke(1.dp, if (isSelected) OrangePrimary else DarkCardBorder),
                  modifier = Modifier.weight(1f)
                ) {
                  Text(
                    text = algo,
                    color = if (isSelected) OrangePrimary else TextMuted,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                    maxLines = 2
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Brush size slider
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Eraser Brush Radius:",
                color = TextMuted,
                fontSize = 12.sp
              )
              Text(
                text = "${brushRadius.roundToInt()} px",
                color = OrangePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
              )
            }

            Slider(
              value = brushRadius,
              onValueChange = { brushRadius = it },
              valueRange = 10f..80f,
              colors = SliderDefaults.colors(
                thumbColor = OrangePrimary,
                activeTrackColor = OrangePrimary,
                inactiveTrackColor = DarkCardElevated
              )
            )
          }
        }
      }
    }
  }
}
