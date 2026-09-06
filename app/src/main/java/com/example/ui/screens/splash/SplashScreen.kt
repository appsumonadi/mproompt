package com.example.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.i18n.LocalAppStrings
import com.example.ui.theme.DarkBg
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMain
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

/**
 * High-octane cyberpunk splash screen animation replicating the video:
 * - Stage 1: Dark PCB circuit board with electric orange branching traces radiating outward.
 * - Stage 2: Central mechanical squircle emblem flares with neon orange plasma energy.
 * - Stage 3: Glowing typography: "Promptly" & "Elevate Your Prompting".
 * - Stage 4: "COPY PASTE" Industrial hazard stripe progress bar with technical boot sequence.
 * - Stage 5: Cinematic zoom into the main app.
 */
@Composable
fun SplashScreen(
  onAnimationFinished: () -> Unit
) {
  val strings = LocalAppStrings.current
  var isMuted by remember { mutableStateOf(false) }

  // Start procedural cyberpunk loading soundscape on launch, cleanly stop on dispose
  DisposableEffect(Unit) {
    CyberpunkSoundEngine.play(isMuted = false)
    onDispose {
      CyberpunkSoundEngine.stop()
    }
  }

  // Animation progress drivers
  val circuitGrowth = remember { Animatable(0f) }
  val coreFlare = remember { Animatable(0f) }
  val textEntrance = remember { Animatable(0f) }
  val hazardProgress = remember { Animatable(0f) }
  val zoomExit = remember { Animatable(1f) }
  val exitAlpha = remember { Animatable(1f) }

  var statusText by remember { mutableStateOf("INITIALIZING PROMPTLY NEURAL CORE...") }

  // Infinite pulsing transitions for electric glow & hazard stripes
  val infiniteTransition = rememberInfiniteTransition(label = "electric_pulse")
  val pulseGlow by infiniteTransition.animateFloat(
    initialValue = 0.6f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "glow_pulse"
  )

  val hazardStripeShift by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 40f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "hazard_stripe_shift"
  )

  // Master sequential director coroutine
  LaunchedEffect(Unit) {
    // 0.0s - 1.2s: Electrical circuits shoot outward from the central badge
    launch {
      circuitGrowth.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1300, easing = FastOutSlowInEasing)
      )
    }

    delay(700)
    statusText = "POWERING HIGH-FREQUENCY AI ENGINES..."

    // 1.2s - 2.5s: Central emblem flares with fiery neon orange energy
    launch {
      coreFlare.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
      )
    }

    delay(900)
    statusText = "SYNCHRONIZING REPOSITORY BLUEPRINTS..."

    // 2.2s - 3.4s: Text enters (Promptly + Elevate Your Prompting)
    launch {
      textEntrance.animateTo(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 900, easing = FastOutSlowInEasing)
      )
    }

    delay(400)
    statusText = "CALIBRATING PARAMETER MATRIX..."

    // 2.8s - 5.5s: Hazard stripe "COPY PASTE" bar fills up to 100%
    hazardProgress.animateTo(
      targetValue = 1f,
      animationSpec = tween(durationMillis = 2400, easing = LinearEasing)
    )

    statusText = "SYS.READY // ELEVATE YOUR PROMPTING"
    delay(400)

    // Cinematic exit zoom
    launch {
      zoomExit.animateTo(
        targetValue = 1.25f,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
      )
    }
    launch {
      exitAlpha.animateTo(
        targetValue = 0f,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing)
      )
    }

    delay(500)
    CyberpunkSoundEngine.stop()
    onAnimationFinished()
  }

  // Fullscreen Container
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(DarkBg)
      .alpha(exitAlpha.value)
      .scale(zoomExit.value)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
      ) {
        // Tap anywhere to immediately skip into app
        CyberpunkSoundEngine.stop()
        onAnimationFinished()
      },
    contentAlignment = Alignment.Center
  ) {
    // 1. Background Cityscape Backdrop Image
    Image(
      painter = painterResource(id = R.drawable.promptly_splash_bg_1788462241373),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxSize()
        .alpha(0.35f)
    )

    // 2. Ambient Gradient Vignette & Cyber Dust
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(
          Brush.radialGradient(
            colors = listOf(
              Color(0x33FF6B00),
              Color(0x880C0D10),
              Color(0xF50C0D10)
            ),
            radius = 900f
          )
        )
    )

    // 3. Dynamic Animated Branching Electric Circuit Traces Canvas
    Canvas(
      modifier = Modifier
        .fillMaxSize()
    ) {
      drawElectricCircuitTraces(
        growth = circuitGrowth.value,
        pulse = pulseGlow
      )
    }

    // 4. Central Content Column (Emblem, Glow Aura, Typography, Hazard Bar)
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
    ) {
      // Central Emblem Wrapper with Neon Energy Halos
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(240.dp)
      ) {
        // Outer pulsing energy ring
        if (coreFlare.value > 0.1f) {
          Box(
            modifier = Modifier
              .size((160 + (40 * coreFlare.value * pulseGlow)).dp)
              .clip(RoundedCornerShape(44.dp))
              .background(Color(0x22FF6A00))
              .border(
                width = 2.dp,
                color = OrangePrimary.copy(alpha = 0.5f * coreFlare.value * pulseGlow),
                shape = RoundedCornerShape(44.dp)
              )
          )

          // Glowing neon backplate (using radial gradient to ensure 100% hardware & software compatibility across all renderers)
          Box(
            modifier = Modifier
              .size(160.dp)
              .background(
                Brush.radialGradient(
                  colors = listOf(
                    OrangePrimary.copy(alpha = 0.75f * coreFlare.value * pulseGlow),
                    OrangePrimary.copy(alpha = 0.35f * coreFlare.value * pulseGlow),
                    Color.Transparent
                  )
                )
              )
          )
        }

        // Secondary tight neon border
        Box(
          modifier = Modifier
            .size(134.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0x66FF6A00))
            .border(
              width = 2.dp,
              brush = Brush.linearGradient(
                colors = listOf(
                  OrangeLight,
                  OrangePrimary,
                  Color(0xFFFFB300),
                  OrangePrimary
                )
              ),
              shape = RoundedCornerShape(32.dp)
            )
        )

        // Core App Icon Image
        Image(
          painter = painterResource(id = R.drawable.promptly_app_icon_1788462188931),
          contentDescription = "Promptly Icon",
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(126.dp)
            .clip(RoundedCornerShape(28.dp))
            .shadow(16.dp, RoundedCornerShape(28.dp), spotColor = OrangePrimary)
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 5. Typography Entrance: "Promptly" & "Elevate Your Prompting"
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .alpha(textEntrance.value)
          .offset(y = ((1f - textEntrance.value) * 20).dp)
      ) {
        // Brand Title with Neon Glow
        Text(
          text = "Promptly",
          style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.SansSerif,
            letterSpacing = 1.sp,
            color = Color(0xFFFF8400)
          ),
          modifier = Modifier.shadow(12.dp, spotColor = OrangePrimary)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Tagline
        Text(
          text = "Elevate Your Prompting",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = Color(0xFFF3F4F6)
          )
        )
      }

      Spacer(modifier = Modifier.height(34.dp))

      // 6. Industrial Tech "COPY PASTE" Hazard Loading Bar
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 24.dp)
          .alpha(circuitGrowth.value)
      ) {
        // Hazard Label: [ COPY PASTE ]
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.padding(bottom = 6.dp)
        ) {
          Text(
            text = "COPY PASTE",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Black,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 3.sp,
              color = OrangeLight
            )
          )
        }

        // Industrial Hazard Progress Container
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF101217))
            .border(
              width = 1.5.dp,
              brush = Brush.horizontalGradient(
                listOf(
                  OrangePrimary.copy(alpha = 0.4f),
                  OrangeLight,
                  OrangePrimary.copy(alpha = 0.4f)
                )
              ),
              shape = RoundedCornerShape(6.dp)
            )
        ) {
          // Animated Hazard Stripes Filling Across the Progress Track
          Canvas(
            modifier = Modifier
              .fillMaxWidth(fraction = hazardProgress.value.coerceIn(0.01f, 1f))
              .height(24.dp)
          ) {
            val width = size.width
            val height = size.height
            val stripeWidth = 14f
            val spacing = 10f
            val shift = hazardStripeShift

            // Base glow background
            drawRect(
              brush = Brush.horizontalGradient(
                colors = listOf(
                  Color(0xFFCC5500),
                  Color(0xFFFF7700),
                  Color(0xFFFFA000)
                )
              )
            )

            // Draw diagonal hazard lines
            val totalSpan = width + height + 50f
            var x = -height + (shift % (stripeWidth + spacing))
            while (x < totalSpan) {
              val path = Path().apply {
                moveTo(x, height)
                lineTo(x + stripeWidth, height)
                lineTo(x + stripeWidth + height, 0f)
                lineTo(x + height, 0f)
                close()
              }
              drawPath(
                path = path,
                color = Color(0x33000000)
              )
              x += stripeWidth + spacing
            }
          }

          // Inner percentage text overlay
          Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
          ) {
            val percent = (hazardProgress.value * 100).toInt()
            Text(
              text = "$percent%",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                color = if (hazardProgress.value > 0.5f) Color.Black else OrangeLight
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Telemetry status text
        Text(
          text = statusText,
          style = MaterialTheme.typography.bodySmall.copy(
            fontFamily = FontFamily.Monospace,
            fontSize = 10.sp,
            color = TextMuted,
            letterSpacing = 1.sp
          )
        )
      }
    }

    // 7. Top Bar with Cyberpunk Sound Toggle & Skip Button
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 44.dp, start = 20.dp, end = 20.dp)
        .align(Alignment.TopCenter),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Tech build identifier
      Text(
        text = "PROMPTEG // v2.6.4",
        style = MaterialTheme.typography.labelSmall.copy(
          fontFamily = FontFamily.Monospace,
          fontSize = 10.sp,
          color = TextMuted
        )
      )

      Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Cyberpunk SFX Sound Toggle Pill
        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x33101217))
            .border(
              width = 1.dp,
              color = if (!isMuted) OrangePrimary.copy(alpha = 0.65f) else Color(0x33FFFFFF),
              shape = RoundedCornerShape(12.dp)
            )
            .clickable {
              isMuted = !isMuted
              CyberpunkSoundEngine.setMuted(isMuted)
            }
            .padding(horizontal = 9.dp, vertical = 6.dp)
            .testTag("splash_sound_toggle"),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
          Icon(
            imageVector = if (!isMuted) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
            contentDescription = if (!isMuted) strings.splashSoundOn else strings.splashSoundOff,
            tint = if (!isMuted) OrangeLight else TextMuted,
            modifier = Modifier.size(14.dp)
          )

          // Animated cyberpunk equalizer bars while unmuted
          if (!isMuted) {
            Row(
              horizontalArrangement = Arrangement.spacedBy(2.dp),
              verticalAlignment = Alignment.Bottom,
              modifier = Modifier.height(11.dp)
            ) {
              Box(
                modifier = Modifier
                  .width(2.dp)
                  .height((4 + (5 * pulseGlow)).dp)
                  .background(OrangePrimary, RoundedCornerShape(1.dp))
              )
              Box(
                modifier = Modifier
                  .width(2.dp)
                  .height((9 - (4 * pulseGlow)).dp)
                  .background(OrangeLight, RoundedCornerShape(1.dp))
              )
              Box(
                modifier = Modifier
                  .width(2.dp)
                  .height((3 + (6 * pulseGlow)).dp)
                  .background(OrangePrimary, RoundedCornerShape(1.dp))
              )
            }
          }

          Text(
            text = if (!isMuted) strings.splashSoundOn else strings.splashSoundOff,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = if (!isMuted) OrangeLight else TextMuted,
              fontSize = 9.sp
            )
          )
        }

        // Skip Pill
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0x33FF6B00))
            .border(1.dp, OrangePrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .clickable {
              CyberpunkSoundEngine.stop()
              onAnimationFinished()
            }
            .padding(horizontal = 11.dp, vertical = 6.dp)
            .testTag("splash_skip_button")
        ) {
          Text(
            text = "SKIP >>",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = OrangeLight,
              fontSize = 10.sp
            )
          )
        }
      }
    }

    // 8. Subtle Bottom Skip Prompt
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .padding(bottom = 28.dp)
        .alpha(0.55f)
    ) {
      Text(
        text = strings.splashSkipHint,
        style = MaterialTheme.typography.labelSmall.copy(
          fontFamily = FontFamily.Monospace,
          fontSize = 9.sp,
          letterSpacing = 2.sp,
          color = TextMuted
        )
      )
    }
  }
}

/**
 * Custom Canvas drawing rendering branching electronic PCB copper traces
 * radiating from the central squircle out across the screen with solder pads
 * and illuminated neon energy packets.
 */
private fun DrawScope.drawElectricCircuitTraces(
  growth: Float,
  pulse: Float
) {
  val centerX = size.width / 2f
  val centerY = size.height / 2f - 40f // Aligned to central emblem
  val halfBadge = 70f // Radius of badge boundary

  val copperDark = Color(0x33442200)
  val electricGlow = Color(0xFFFF7A00).copy(alpha = 0.7f * pulse)
  val coreOrange = Color(0xFFFFAB40).copy(alpha = 0.95f * pulse)
  val nodeColor = Color(0xFFFFD54F)

  // Defines a branching PCB trace path relative to badge center
  data class Trace(
    val startOffsetX: Float,
    val startOffsetY: Float,
    val points: List<Offset>
  )

  val traces = listOf(
    // Top-left branching traces
    Trace(-40f, -halfBadge, listOf(Offset(-40f, -halfBadge - 50f), Offset(-120f, -halfBadge - 130f), Offset(-120f, -halfBadge - 260f))),
    Trace(-halfBadge, -30f, listOf(Offset(-halfBadge - 60f, -30f), Offset(-halfBadge - 140f, -90f), Offset(-halfBadge - 220f, -90f))),
    
    // Top center traces
    Trace(-15f, -halfBadge, listOf(Offset(-15f, -halfBadge - 70f), Offset(-50f, -halfBadge - 140f), Offset(-50f, -halfBadge - 280f))),
    Trace(20f, -halfBadge, listOf(Offset(20f, -halfBadge - 80f), Offset(60f, -halfBadge - 160f), Offset(60f, -halfBadge - 300f))),

    // Top-right branching traces
    Trace(halfBadge, -30f, listOf(Offset(halfBadge + 50f, -30f), Offset(halfBadge + 130f, -100f), Offset(halfBadge + 210f, -100f))),
    Trace(40f, -halfBadge, listOf(Offset(40f, -halfBadge - 60f), Offset(130f, -halfBadge - 140f), Offset(130f, -halfBadge - 270f))),

    // Lateral Left traces
    Trace(-halfBadge, 10f, listOf(Offset(-halfBadge - 70f, 10f), Offset(-halfBadge - 120f, 60f), Offset(-halfBadge - 200f, 60f))),
    Trace(-halfBadge, 40f, listOf(Offset(-halfBadge - 50f, 40f), Offset(-halfBadge - 100f, 120f), Offset(-halfBadge - 170f, 120f))),

    // Lateral Right traces
    Trace(halfBadge, 10f, listOf(Offset(halfBadge + 60f, 10f), Offset(halfBadge + 110f, 60f), Offset(halfBadge + 190f, 60f))),
    Trace(halfBadge, 40f, listOf(Offset(halfBadge + 50f, 40f), Offset(halfBadge + 90f, 110f), Offset(halfBadge + 160f, 110f))),

    // Bottom branching traces
    Trace(-30f, halfBadge, listOf(Offset(-30f, halfBadge + 40f), Offset(-80f, halfBadge + 90f), Offset(-80f, halfBadge + 180f))),
    Trace(0f, halfBadge, listOf(Offset(0f, halfBadge + 50f), Offset(20f, halfBadge + 100f), Offset(20f, halfBadge + 200f))),
    Trace(35f, halfBadge, listOf(Offset(35f, halfBadge + 40f), Offset(90f, halfBadge + 100f), Offset(90f, halfBadge + 190f)))
  )

  traces.forEach { trace ->
    val startX = centerX + trace.startOffsetX
    val startY = centerY + trace.startOffsetY

    // Build the full path
    val fullPath = Path().apply {
      moveTo(startX, startY)
      trace.points.forEach { pt ->
        lineTo(centerX + pt.x, centerY + pt.y)
      }
    }

    // 1. Draw inactive copper background trace
    drawPath(
      path = fullPath,
      color = copperDark,
      style = Stroke(width = 4f, cap = StrokeCap.Round)
    )

    // 2. Draw animated electric glow trace
    if (growth > 0.05f) {
      // Glow blur stroke
      drawPath(
        path = fullPath,
        color = electricGlow,
        style = Stroke(
          width = 8f * pulse,
          cap = StrokeCap.Round,
          pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(800f * growth, 1000f),
            phase = 0f
          )
        )
      )

      // Inner electric core stroke
      drawPath(
        path = fullPath,
        color = coreOrange,
        style = Stroke(
          width = 3.5f,
          cap = StrokeCap.Round,
          pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(800f * growth, 1000f),
            phase = 0f
          )
        )
      )

      // End solder pad / node dot
      val endPoint = trace.points.last()
      if (growth >= 0.8f) {
        val nodeX = centerX + endPoint.x
        val nodeY = centerY + endPoint.y

        drawCircle(
          color = electricGlow,
          radius = 6f * pulse,
          center = Offset(nodeX, nodeY)
        )
        drawCircle(
          color = nodeColor,
          radius = 3f,
          center = Offset(nodeX, nodeY)
        )
      }
    }
  }
}
