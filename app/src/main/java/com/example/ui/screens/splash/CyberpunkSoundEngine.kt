package com.example.ui.screens.splash

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.tanh

/**
 * High-tech procedural synthesizer for the Cyberpunk splash screen loading sound effect.
 *
 * Synthesizes an authentic AAA sci-fi audio landscape:
 * - Sub-bass power grid surge & analog warmth (0.0s - 1.3s)
 * - Circuit telemetry & neural data chirps (0.7s, 0.9s, 1.6s, 2.0s)
 * - Accelerating cyberpunk hazard loading charging frequency sweep (2.4s - 4.8s)
 * - System ready confirmation chime & sub-bass punch (4.8s - 5.4s)
 *
 * Runs on background coroutines with zero latency and clean lifecycle disposal.
 */
object CyberpunkSoundEngine {
  private const val TAG = "CyberpunkSoundEngine"
  private const val SAMPLE_RATE = 44100
  private const val DURATION_SECONDS = 5.4

  // Cached PCM audio buffer so subsequent replays have 0ms latency
  private var cachedAudioData: ShortArray? = null
  private val isGenerating = AtomicBoolean(false)

  private var activeTrack: AudioTrack? = null
  private var playbackJob: Job? = null
  private val isMutedState = AtomicBoolean(false)

  /**
   * Pre-generates the audio buffer in the background if not already cached.
   */
  fun prewarm() {
    if (cachedAudioData == null && !isGenerating.get()) {
      CoroutineScope(Dispatchers.Default).launch {
        generateAudioBuffer()
      }
    }
  }

  /**
   * Generates the multi-layer 5.4-second cyberpunk splash sound effect.
   */
  private fun generateAudioBuffer(): ShortArray {
    if (cachedAudioData != null) return cachedAudioData!!
    isGenerating.set(true)

    val totalSamples = (DURATION_SECONDS * SAMPLE_RATE).toInt()
    val buffer = ShortArray(totalSamples)

    // Phase accumulators for continuous glitch-free frequency integration
    var phaseBass = 0.0
    var phaseCharge = 0.0
    var phaseChargeHarmonic = 0.0
    var phasePulse = 0.0
    var phaseLfo = 0.0
    var phaseChirp = 0.0

    val twoPi = 2.0 * PI
    val dt = 1.0 / SAMPLE_RATE

    for (i in 0 until totalSamples) {
      val t = i.toDouble() / SAMPLE_RATE

      // -------------------------------------------------------------
      // Layer 1: Sub-bass Power Grid Surge (t: 0.0s -> 5.4s)
      // -------------------------------------------------------------
      val bootProgress = (t / 1.3).coerceIn(0.0, 1.0)
      val bassFreq = 55.0 + 55.0 * (bootProgress * bootProgress)
      phaseBass += twoPi * bassFreq * dt

      val bassWave = sin(phaseBass) + 0.35 * sin(2.0 * phaseBass) + 0.15 * sin(3.0 * phaseBass)

      // Bass volume envelope: attack (0 to 150ms), sustain peak, then settle to steady cyber hum
      val bassAmp = when {
        t < 0.15 -> (t / 0.15) * 0.65
        t < 1.3 -> 0.65 - 0.35 * ((t - 0.15) / 1.15)
        t < 4.8 -> 0.30
        else -> 0.30 * exp(-3.0 * (t - 4.8))
      }

      // -------------------------------------------------------------
      // Layer 2: Circuit & Neural Data Telemetry Chirps
      // -------------------------------------------------------------
      var chirpAmp = 0.0
      var chirpFreq = 0.0

      when {
        // Milestone 1: 0.72s - 0.80s (Circuit trace branching)
        t in 0.72..0.80 -> {
          val ct = (t - 0.72) / 0.08
          chirpFreq = 880.0 + 600.0 * ct
          chirpAmp = 0.25 * sin(PI * ct)
        }
        // Milestone 2: 0.90s - 0.98s (Neural core flare)
        t in 0.90..0.98 -> {
          val ct = (t - 0.90) / 0.08
          chirpFreq = 1400.0 - 500.0 * ct + 120.0 * sin(ct * 40.0)
          chirpAmp = 0.30 * sin(PI * ct)
        }
        // Milestone 3: 1.65s - 1.76s (Blueprint sync)
        t in 1.65..1.76 -> {
          val ct = (t - 1.65) / 0.11
          chirpFreq = 1320.0 + 300.0 * sin(ct * 60.0)
          chirpAmp = 0.22 * sin(PI * ct)
        }
        // Milestone 4: 2.05s - 2.16s (Parameter matrix calibration)
        t in 2.05..2.16 -> {
          val ct = (t - 2.05) / 0.11
          chirpFreq = if (ct < 0.5) 1760.0 else 2200.0
          chirpAmp = 0.20 * sin(PI * (ct % 0.5) / 0.5)
        }
      }

      if (chirpFreq > 0.0) {
        phaseChirp += twoPi * chirpFreq * dt
      }
      val chirpWave = if (chirpAmp > 0.0) sin(phaseChirp) else 0.0

      // -------------------------------------------------------------
      // Layer 3: Cyberpunk Hazard Loading Sound Effect (2.4s -> 4.8s)
      // -------------------------------------------------------------
      var loadAmp = 0.0
      var loadWave = 0.0

      if (t in 2.35..4.85) {
        val loadProgress = ((t - 2.4) / 2.4).coerceIn(0.0, 1.0)

        // Accelerating charging pitch: 220Hz (A3) sweeps to 740Hz (F#5)
        val baseChargeFreq = 220.0 * 2.0.pow(1.75 * loadProgress)

        // LFO rate accelerates from 4Hz to 14Hz
        val lfoRate = 4.0 + 10.0 * (loadProgress * loadProgress)
        phaseLfo += twoPi * lfoRate * dt
        val lfoPitchOffset = sin(phaseLfo) * (8.0 + 12.0 * loadProgress)

        val activeChargeFreq = baseChargeFreq + lfoPitchOffset
        phaseCharge += twoPi * activeChargeFreq * dt
        phaseChargeHarmonic += twoPi * (activeChargeFreq * 2.0) * dt

        // Rhythmic pulsing cyber engine: pulses accelerate as bar fills
        val pulseRate = 3.5 + 11.5 * (loadProgress * loadProgress)
        phasePulse += twoPi * pulseRate * dt
        val pulseMod = (0.5 + 0.5 * sin(phasePulse)).pow(3.0)

        // Waveform: Rich analog pulse wave
        val waveCore = sin(phaseCharge) + 0.4 * sin(phaseChargeHarmonic) + 0.2 * sin(3.0 * phaseCharge)

        // High frequency cyber shimmer above 60% progress
        val shimmer = if (loadProgress > 0.6) {
          val shimProgress = (loadProgress - 0.6) / 0.4
          0.25 * shimProgress * sin(phaseCharge * 3.5)
        } else 0.0

        val entranceFade = ((t - 2.35) / 0.15).coerceIn(0.0, 1.0)
        val exitFade = ((4.85 - t) / 0.10).coerceIn(0.0, 1.0)

        loadAmp = (0.35 + 0.25 * pulseMod) * entranceFade * exitFade
        loadWave = waveCore + shimmer
      }

      // -------------------------------------------------------------
      // Layer 4: System Ready Completion Chime & Sub Boom (4.8s -> 5.4s)
      // -------------------------------------------------------------
      var chimeAmp = 0.0
      var chimeWave = 0.0

      if (t >= 4.8) {
        val chimeTime = t - 4.8

        // Cyber confirmation chord: C5 (523.25Hz), E5 (659.25Hz), G5 (783.99Hz), C6 (1046.5Hz)
        val chordWave = sin(twoPi * 523.25 * chimeTime) +
            0.75 * sin(twoPi * 659.25 * chimeTime) +
            0.60 * sin(twoPi * 783.99 * chimeTime) +
            0.45 * sin(twoPi * 1046.50 * chimeTime)

        val chordDecay = exp(-4.2 * chimeTime)

        // Tactile sub-bass punch
        val boomFreq = 85.0 - 25.0 * (chimeTime / 0.6).coerceIn(0.0, 1.0)
        val boomWave = sin(twoPi * boomFreq * chimeTime)
        val boomDecay = exp(-8.0 * chimeTime)

        chimeAmp = (0.45 * chordDecay + 0.35 * boomDecay).coerceIn(0.0, 1.0)
        chimeWave = chordWave * 0.6 + boomWave * 0.4
      }

      // -------------------------------------------------------------
      // Master Mix & Analog Soft-Clipping Saturation
      // -------------------------------------------------------------
      val mixed = (bassAmp * bassWave) +
          (chirpAmp * chirpWave) +
          (loadAmp * loadWave) +
          (chimeAmp * chimeWave)

      // Master fade-out at the very end to ensure silence
      val masterFade = if (t > 5.3) ((5.4 - t) / 0.1).coerceIn(0.0, 1.0) else 1.0

      // Warm analog saturation via tanh
      val saturated = tanh(mixed * 1.35) * masterFade * 31800.0
      buffer[i] = saturated.toInt().coerceIn(-32767, 32767).toShort()
    }

    cachedAudioData = buffer
    isGenerating.set(false)
    return buffer
  }

  /**
   * Starts playback of the cyberpunk loading sound.
   * If already playing, restarts cleanly.
   */
  fun play(isMuted: Boolean = false) {
    isMutedState.set(isMuted)
    stop()

    playbackJob = CoroutineScope(Dispatchers.Default).launch {
      try {
        val audioData = cachedAudioData ?: generateAudioBuffer()

        val bufferSize = AudioTrack.getMinBufferSize(
          SAMPLE_RATE,
          AudioFormat.CHANNEL_OUT_MONO,
          AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(audioData.size * 2)

        val track = AudioTrack.Builder()
          .setAudioAttributes(
            AudioAttributes.Builder()
              .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
              .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
              .build()
          )
          .setAudioFormat(
            AudioFormat.Builder()
              .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
              .setSampleRate(SAMPLE_RATE)
              .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
              .build()
          )
          .setBufferSizeInBytes(bufferSize)
          .setTransferMode(AudioTrack.MODE_STATIC)
          .build()

        activeTrack = track

        track.write(audioData, 0, audioData.size)

        if (isMutedState.get()) {
          track.setVolume(0.0f)
        } else {
          track.setVolume(1.0f)
        }

        if (isActive) {
          track.play()
        }
      } catch (e: Exception) {
        Log.w(TAG, "AudioTrack playback error or audio focus unavailable", e)
      }
    }
  }

  /**
   * Updates mute state in real-time.
   */
  fun setMuted(muted: Boolean) {
    isMutedState.set(muted)
    try {
      activeTrack?.setVolume(if (muted) 0.0f else 1.0f)
    } catch (e: Exception) {
      Log.w(TAG, "Failed to adjust volume", e)
    }
  }

  fun isMuted(): Boolean = isMutedState.get()

  /**
   * Gracefully stops and releases the AudioTrack immediately with zero click noise.
   */
  fun stop() {
    playbackJob?.cancel()
    playbackJob = null

    try {
      activeTrack?.let { track ->
        if (track.state == AudioTrack.STATE_INITIALIZED) {
          try {
            track.setVolume(0f)
            track.pause()
            track.flush()
            track.stop()
          } catch (_: Exception) {}
        }
        track.release()
      }
    } catch (e: Exception) {
      Log.w(TAG, "Error stopping AudioTrack", e)
    } finally {
      activeTrack = null
    }
  }
}
