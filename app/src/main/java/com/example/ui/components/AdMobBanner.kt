package com.example.ui.components

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.DarkCardBg
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.TextMuted
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun AdMobBanner(
  modifier: Modifier = Modifier,
  adUnitId: String = "ca-app-pub-3940256099942544/6300978111",
  adsEnabled: Boolean = true,
  showLabel: Boolean = true
) {
  if (!adsEnabled) return

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if (showLabel) {
      Text(
        text = "ADVERTISEMENT // SPONSORED",
        style = MaterialTheme.typography.labelSmall.copy(
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          color = TextMuted
        ),
        modifier = Modifier.padding(bottom = 4.dp)
      )
    }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(8.dp))
        .background(DarkCardBg)
        .border(1.dp, DarkCardBorder, RoundedCornerShape(8.dp))
        .padding(vertical = 4.dp),
      contentAlignment = Alignment.Center
    ) {
      AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
          try {
            AdView(context).apply {
              setAdSize(AdSize.BANNER)
              this.adUnitId = adUnitId.ifBlank { "ca-app-pub-3940256099942544/6300978111" }
              try {
                loadAd(AdRequest.Builder().build())
              } catch (_: Throwable) {}
            }
          } catch (_: Throwable) {
            // Safe fallback view if AdMob is unavailable on current device/emulator
            android.widget.TextView(context).apply {
              text = "PROMPTEG AI AD NETWORK // LIVE"
              setTextColor(android.graphics.Color.parseColor("#71717A"))
              textSize = 11f
              gravity = android.view.Gravity.CENTER
              setPadding(0, 32, 0, 32)
            }
          }
        }
      )
    }
  }
}

object AdMobInterstitialHelper {
  private var interstitialAd: InterstitialAd? = null
  private var copyCounter = 0

  fun loadInterstitial(context: Context, adUnitId: String) {
    try {
      val adRequest = AdRequest.Builder().build()
      val unitId = adUnitId.ifBlank { "ca-app-pub-3940256099942544/1033173712" }
      InterstitialAd.load(
        context,
        unitId,
        adRequest,
        object : InterstitialAdLoadCallback() {
          override fun onAdLoaded(ad: InterstitialAd) {
            interstitialAd = ad
          }
          override fun onAdFailedToLoad(error: LoadAdError) {
            interstitialAd = null
          }
        }
      )
    } catch (_: Exception) {}
  }

  fun onPromptCopied(activity: Activity, adUnitId: String, frequency: Int, adsEnabled: Boolean) {
    if (!adsEnabled || frequency <= 0) return
    copyCounter++
    if (copyCounter % frequency == 0) {
      interstitialAd?.show(activity)
      // Prepare next interstitial
      loadInterstitial(activity, adUnitId)
    }
  }
}
