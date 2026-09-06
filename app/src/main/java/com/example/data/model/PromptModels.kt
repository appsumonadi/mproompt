package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.AccentBlue
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.AccentPink
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentRed
import com.example.ui.theme.AccentYellow
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextMuted

enum class AIModel(
  val displayName: String,
  val shortBadge: String,
  val tagColor: Color,
  val defaultParams: String
) {
  MIDJOURNEY("Midjourney v6.0", "MJ v6", OrangePrimary, "--ar 16:9 --v 6.0 --s 750 --c 10"),
  FLUX("Flux.1 Dev", "Flux.1", AccentCyan, "--steps 28 --guidance 3.5"),
  STABLE_DIFFUSION("SDXL Turbo", "SDXL", AccentBlue, "--steps 30 --cfg 7.0"),
  DALL_E_3("DALL·E 3", "DALL-E 3", AccentGreen, "vivid style, hd quality"),
  CHATGPT_4O("ChatGPT-4o", "GPT-4o", AccentGreen, "temp: 0.7, top_p: 0.9"),
  CLAUDE_3_5("Claude 3.5 Sonnet", "Claude 3.5", AccentYellow, "temperature: 0.5"),
  GEMINI("Gemini 1.5 Pro", "Gemini", OrangePrimary, "aspect_ratio: 16:9, style: cinematic")
}

enum class PromptCategory(
  val title: String,
  val subtitle: String,
  val chipLabel: String,
  val dotColor: Color,
  val coverImageUrl: String,
  val accentColor: Color
) {
  REALISTIC(
    "Photorealistic & Editorial",
    "Ultra-realistic cinematic lighting, 8k skin textures & studio gaze",
    "REALISTIC",
    AccentCyan,
    "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=900&auto=format&fit=crop&q=80",
    OrangePrimary
  ),
  THREE_D(
    "3D Isometric & Game Art",
    "Blender 3D, voxel scenes & Unreal Engine 5 environments",
    "3D",
    AccentGreen,
    "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=900&auto=format&fit=crop&q=80",
    AccentGreen
  ),
  CINEMATIC(
    "Cinematic & Action",
    "GTA 6-style heists, dynamic action shots & anamorphic frames",
    "CINEMATIC",
    AccentRed,
    "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=900&auto=format&fit=crop&q=80",
    AccentRed
  ),
  CAR(
    "Vehicles & Concept Cars",
    "Supercars, hypercars, aerodynamic prototypes & drift chases",
    "CAR",
    AccentBlue,
    "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=900&auto=format&fit=crop&q=80",
    AccentBlue
  ),
  ANIMALS(
    "Wildlife & Cyber Fauna",
    "Hyper-detailed wildlife, mythical beasts & cyber companions",
    "ANIMALS",
    AccentGreen,
    "https://images.unsplash.com/photo-1563089145-599997674d42?w=900&auto=format&fit=crop&q=80",
    AccentGreen
  ),
  ANIME(
    "Anime & Manga Art",
    "Studio Ghibli aesthetic, Makoto Shinkai skies & mecha",
    "ANIME",
    AccentGreen,
    "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=900&auto=format&fit=crop&q=80",
    AccentPink
  ),
  ART(
    "Abstract & Fine Art",
    "Surrealism, chromatic iridescence, oil paintings & sacred geometry",
    "ART",
    AccentPurple,
    "https://images.unsplash.com/photo-1550684848-fac1c5b4e853?w=900&auto=format&fit=crop&q=80",
    AccentPurple
  ),
  PORTRAITS(
    "Fashion & Portraits",
    "High-fashion Vogue editorial, augmented gaze & beauty lighting",
    "PORTRAITS",
    AccentPurple,
    "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=900&auto=format&fit=crop&q=80",
    AccentPurple
  ),
  DRAWING(
    "Concept Art & Drawing",
    "Charcoal sketches, watercolor illustrations & ink lines",
    "DRAWING",
    AccentYellow,
    "https://images.unsplash.com/photo-1513364776144-60967b0f800f?w=900&auto=format&fit=crop&q=80",
    AccentYellow
  ),
  PRODUCTS(
    "Commercial & Products",
    "Sleek product mockups, luxury packaging & studio renders",
    "PRODUCTS",
    AccentGreen,
    "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=900&auto=format&fit=crop&q=80",
    AccentGreen
  ),
  VIDEO(
    "Video & Motion Cues",
    "Cinematic camera cues for Sora, Runway Gen-3 & Kling",
    "VIDEO",
    AccentGreen,
    "https://images.unsplash.com/photo-1536240478700-b869070f9279?w=900&auto=format&fit=crop&q=80",
    AccentGreen
  ),
  SCI_FI(
    "Sci-Fi & Cyberpunk",
    "Futuristic mechs, neon alleyways & cyberpunk avatars",
    "SCI-FI",
    OrangePrimary,
    "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=900&auto=format&fit=crop&q=80",
    OrangePrimary
  ),
  MARKETING(
    "Marketing & Copy",
    "Viral ad hooks, brand positioning & sales frameworks",
    "TOOLS",
    TextMuted,
    "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=900&auto=format&fit=crop&q=80",
    OrangePrimary
  ),
  CODING(
    "Coding & Architecture",
    "Full-stack scaffolds, refactoring & architectural design",
    "TOOLS",
    TextMuted,
    "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=900&auto=format&fit=crop&q=80",
    AccentCyan
  ),
  DESIGN(
    "Graphic Design & Logos",
    "Minimalist vector marks, Swiss design icons & brand emblems",
    "DESIGN",
    AccentCyan,
    "https://images.unsplash.com/photo-1626785774573-4b799315345d?w=900&auto=format&fit=crop&q=80",
    OrangePrimary
  )
}

data class PromptVariable(
  val key: String,
  val label: String,
  val defaultValue: String,
  val suggestedOptions: List<String> = emptyList()
)

data class PromptItem(
  val id: String,
  val title: String,
  val description: String,
  val promptTemplate: String,
  val negativePrompt: String = "",
  val model: AIModel,
  val category: PromptCategory,
  val customCategoryName: String? = null,
  val imageUrl: String,
  val parameters: Map<String, String> = emptyMap(),
  val variables: List<PromptVariable> = emptyList(),
  val likesCount: Int = 0,
  val copyCount: Int = 0,
  val isBookmarked: Boolean = false,
  val isLiked: Boolean = false,
  val isCustom: Boolean = false,
  val createdAt: Long = System.currentTimeMillis()
) {
  val categoryDisplayName: String
    get() = customCategoryName?.ifBlank { null } ?: category.title

  val categoryChipLabel: String
    get() = customCategoryName?.take(10)?.uppercase() ?: category.chipLabel

  fun compilePrompt(variableValues: Map<String, String>): String {
    var result = promptTemplate
    for (variable in variables) {
      val value = variableValues[variable.key] ?: variable.defaultValue
      result = result.replace("{${variable.key}}", value)
      result = result.replace("[${variable.key}]", value)
    }
    return result
  }
}

data class UnifiedCategory(
  val id: String,
  val title: String,
  val subtitle: String,
  val chipLabel: String,
  val dotColor: Color,
  val coverImageUrl: String,
  val accentColor: Color,
  val isCustom: Boolean = false
)

