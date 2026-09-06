package com.example.data.repository

import com.example.data.model.AIModel
import com.example.data.model.PromptCategory
import com.example.data.model.PromptItem
import com.example.data.model.PromptVariable

object CuratedPromptsData {
  val defaultPrompts: List<PromptItem> = listOf(
    PromptItem(
      id = "prmt_soccer_throne",
      title = "Photo to Soccer Throne Sports Poster AI Prompt",
      description = "Transform uploaded photos into an epic soccer throne luxury sports character poster with dramatic arena lighting and trophy majesty.",
      promptTemplate = "Cinematic sports poster of {subject}, seated majestically upon an ornate championship soccer throne in a packed floodlit stadium, {lighting}, hyper-detailed athlete anatomy, gold trophy accents, floating stadium confetti dust, 8k commercial sports editorial --ar 3:4 --v 6.0",
      negativePrompt = "blurry, low quality, deformed hands, cartoon, bad anatomy",
      model = AIModel.GEMINI,
      category = PromptCategory.CINEMATIC,
      imageUrl = "https://images.unsplash.com/photo-1579952363873-27f3bade9f55?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf("--ar" to "3:4", "model" to "Gemini 1.5 Pro"),
      variables = listOf(
        PromptVariable("subject", "Athlete Pose", "legendary soccer superstar with focused championship gaze holding a golden soccer ball", listOf("legendary soccer superstar with focused championship gaze holding a golden soccer ball", "striker in royal gold and black kit seated on the throne", "young prodigy football champion under crown light")),
        PromptVariable("lighting", "Stadium Atmosphere", "dramatic arena spotlights cutting through misty evening haze with golden pyro sparks", listOf("dramatic arena spotlights cutting through misty evening haze with golden pyro sparks", "electric blue floodlights with rim halo glow", "warm victory sunset arena flare"))
      ),
      likesCount = 8940,
      copyCount = 14200
    ),

    PromptItem(
      id = "prmt_shutter_motion_blur",
      title = "Photo to Orange & Blue Shutter Motion Blur Portrait AI Prompt",
      description = "Transform uploaded portraits into high-fashion dual-tone shutter drag portraits with luminous orange and blue light trails.",
      promptTemplate = "Slow-shutter dynamic fashion editorial portrait of {subject}, luminous orange and electric cyan motion blur light trails, {lighting}, double-exposure shutter streak effect, high-fashion moody expression, 35mm film grain, avant-garde magazine spread --ar 3:4 --v 6.0",
      negativePrompt = "ugly, overexposed, static, flat lighting, amateur",
      model = AIModel.GEMINI,
      category = PromptCategory.PORTRAITS,
      imageUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf("--ar" to "3:4", "model" to "Gemini 1.5 Pro"),
      variables = listOf(
        PromptVariable("subject", "Fashion Model Subject", "stylish model turning head with dramatic flowing hair and tailored trench coat", listOf("stylish model turning head with dramatic flowing hair and tailored trench coat", "cyberpunk street dancer caught in mid-spin streak", "enigmatic musician holding vintage microphone with light streaks")),
        PromptVariable("lighting", "Light Trail Grading", "neon warm amber and intense cobalt blue streaks across black velvet backdrop", listOf("neon warm amber and intense cobalt blue streaks across black velvet backdrop", "magenta and turquoise trailing neon ribbons", "warm sunset halogen streaks with soft fill"))
      ),
      likesCount = 7650,
      copyCount = 11830
    ),

    PromptItem(
      id = "prmt_ultra_realistic_portrait",
      title = "Ultra-Realistic Editorial Portrait",
      description = "Cinematic 8k close-up high-fashion editorial portrait with natural skin texture and soft ambient light.",
      promptTemplate = "Cinematic 8k close-up high-fashion portrait of an elegant {subject}, {lighting}, natural skin pores, 85mm f/1.4 lens, Vogue editorial aesthetic, extreme sharpness, 8k UHD --ar 3:4 --v 6.0 --s 250",
      negativePrompt = "airbrushed, plastic skin, CGI look, blurry eyes, overexposed, low quality",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.REALISTIC,
      imageUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "--ar" to "3:4",
        "--v" to "6.0",
        "--s" to "250"
      ),
      variables = listOf(
        PromptVariable("subject", "Model Persona", "woman with piercing hazel eyes and subtle freckles", listOf("woman with piercing hazel eyes and subtle freckles", "stylish man with tailored linen collar in golden hour", "haute-couture model with avant-garde gold accents")),
        PromptVariable("lighting", "Studio Light Setup", "dramatic rim light with diffused softbox key", listOf("dramatic rim light with diffused softbox key", "moody chiaroscuro with warm golden backlight", "high-key fashion strobe with crisp reflections"))
      ),
      likesCount = 4279,
      copyCount = 7675
    ),

    PromptItem(
      id = "prmt_gta6_style_heist",
      title = "GTA 6-Style Masked Heist",
      description = "Vibrant high-contrast action shot inspired by GTA 6 neon Miami sunset with dynamic perspective.",
      promptTemplate = "Action cinematic photography of a {subject} standing beside a muscle car in Vice City during sunset, {lighting}, hyperrealistic textures, motion blur background, 35mm Arri film grain, saturated pastel and orange grading, GTA 6 key art aesthetic --ar 3:4 --v 6.0",
      negativePrompt = "cartoon, flat lighting, low resolution, deformed fingers, blurry",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.CINEMATIC,
      imageUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "--ar" to "3:4",
        "--v" to "6.0",
        "--s" to "650"
      ),
      variables = listOf(
        PromptVariable("subject", "Character & Outfit", "masked street crew member holding a duffel bag", listOf("masked street crew member holding a duffel bag", "coastal getaway driver leaning against hood", "undercover detective in vibrant tropical shirt")),
        PromptVariable("lighting", "Sun & Neon Grading", "intense orange and purple sunset glow with humid atmospheric haze", listOf("intense orange and purple sunset glow with humid atmospheric haze", "neon palm tree night glow with wet asphalt reflections", "harsh midday Florida sun casting sharp shadows"))
      ),
      likesCount = 3892,
      copyCount = 6840
    ),

    PromptItem(
      id = "prmt_hypercar_concept",
      title = "High-Realism Concept Hypercar",
      description = "Aggressive carbon-fiber prototype hypercar speeding across coastal highway at golden hour.",
      promptTemplate = "Studio and track photography of a sleek aerodynamic {subject} speeding on a coastal highway, {lighting}, glowing active aero spoilers, carbon-weave body panels, road spray, motion blur wheels, Top Gear magazine cover shot, 8k resolution --steps 28 --guidance 3.5",
      negativePrompt = "distorted wheels, asymmetrical car body, low poly, toy car look, cartoon",
      model = AIModel.FLUX,
      category = PromptCategory.CAR,
      imageUrl = "https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "--steps" to "28",
        "--guidance" to "3.5",
        "--sampler" to "dpm_2m"
      ),
      variables = listOf(
        PromptVariable("subject", "Vehicle Model", "matte black and electric orange hybrid hypercar", listOf("matte black and electric orange hybrid hypercar", "futuristic titanium speeder with active aero", "widebody track edition coupe with carbon diffuser")),
        PromptVariable("lighting", "Atmosphere & Reflections", "dramatic golden hour sunset casting warm specular reflections", listOf("dramatic golden hour sunset casting warm specular reflections", "neon tunnel speed streak lights", "rain-drenched asphalt with glowing tail light trails"))
      ),
      likesCount = 3120,
      copyCount = 5980
    ),

    PromptItem(
      id = "prmt_3d_isometric_diorama",
      title = "3D Isometric Floating World",
      description = "Stylized 3D voxel diorama featuring a Japanese courtyard with glowing cherry blossom lanterns.",
      promptTemplate = "3D isometric diorama of a {subject}, intricate miniature details, miniature glowing lanterns, {lighting}, Blender 3D cycles render, vibrant color grading, clay and glossy materials, tilt-shift lens effect, 8k resolution --ar 1:1 --v 6.0",
      negativePrompt = "orthographic distortion, dull colors, flat textures, 2D vector, noisy render",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.THREE_D,
      imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "--ar" to "1:1",
        "--v" to "6.0",
        "--stylize" to "500"
      ),
      variables = listOf(
        PromptVariable("subject", "Diorama Scene", "floating zen courtyard with koi pond and bonsai tree", listOf("floating zen courtyard with koi pond and bonsai tree", "cyberpunk ramen shop with neon signage", "cozy treehouse sanctuary with glowing fireflies", "futuristic greenhouse pod with exotic flora")),
        PromptVariable("lighting", "Diorama Lighting", "sunset golden glow with warm amber lanterns", listOf("sunset golden glow with warm amber lanterns", "ambient dark blue night with glowing pink neon", "clean studio white backdrop with soft pastel lighting"))
      ),
      likesCount = 2890,
      copyCount = 5410
    ),

    PromptItem(
      id = "prmt_cyber_samurai",
      title = "Cyber Samurai in Neo-Tokyo",
      description = "Hyper-detailed cinematic portrait of a robotic ronin bathed in warm orange & blue rain reflections.",
      promptTemplate = "Cinematic 8k close-up portrait of a cybernetic {subject} standing on a wet asphalt alleyway in Neo-Tokyo, {lighting}, hyper-detailed carbon fiber armor, intricate glowing circuitry cables, shot on 35mm Arri Alexa LF, anamorphic lens flare, masterwork composition --ar 16:9 --v 6.0 --s 750 --c 10",
      negativePrompt = "blurry, low quality, flat lighting, watermark, cartoonish, oversaturated, deformed hands",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.SCI_FI,
      imageUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "--ar" to "16:9",
        "--v" to "6.0",
        "--s" to "750",
        "--c" to "10"
      ),
      variables = listOf(
        PromptVariable("subject", "Subject", "cyberpunk ronin samurai warrior with glowing blade", listOf("cyberpunk ronin samurai warrior with glowing blade", "stealth android operative", "augmented mech pilot", "cybernetic geisha hacker")),
        PromptVariable("lighting", "Atmospheric Lighting", "volumetric orange and cyan backlights with damp rain reflections", listOf("volumetric orange and cyan backlights with damp rain reflections", "golden hour haze piercing toxic fog", "dark high-contrast strobes", "holographic billboards shimmer"))
      ),
      likesCount = 2428,
      copyCount = 4892
    ),

    PromptItem(
      id = "prmt_dalle3_cyber_pet",
      title = "Bioluminescent Wildlife Companion",
      description = "3D character render of an adorable fox companion with glowing fur sitting on an ethereal rock.",
      promptTemplate = "A 3D character render of an adorable {subject}, featuring translucent fiber-optic fur that glows with vibrant {lighting}, sitting on a mossy rock, cute expressive robotic eyes, high detail, studio lighting, Octane render quality, 8k resolution, vivid style.",
      negativePrompt = "",
      model = AIModel.DALL_E_3,
      category = PromptCategory.ANIMALS,
      imageUrl = "https://images.unsplash.com/photo-1563089145-599997674d42?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "style" to "vivid",
        "quality" to "hd",
        "size" to "1024x1024"
      ),
      variables = listOf(
        PromptVariable("subject", "Companion Creature", "cybernetic arctic fox cub with glowing orange paws", listOf("cybernetic arctic fox cub with glowing orange paws", "tiny mech red panda with glowing radar ears", "futuristic dragon kit with fiber-optic wings", "floating owl robot with crystalline eyes")),
        PromptVariable("lighting", "Glow Palette", "warm amber and gold bioluminescence", listOf("warm amber and gold bioluminescence", "cyan and electric purple neon accents", "emerald matrix laser highlights"))
      ),
      likesCount = 2760,
      copyCount = 5120
    ),

    PromptItem(
      id = "prmt_product_watch_luxury",
      title = "Luxury Timepiece Studio Render",
      description = "High-end commercial product photography of an automatic skeleton chronograph on dark obsidian.",
      promptTemplate = "Commercial product photography of a luxury {subject}, resting on dark textured slate, {lighting}, water micro-droplets, razor-sharp focus on tourbillon gears, 100mm macro lens, Hasselblad 100MP, award-winning advertising quality --ar 1:1 --v 6.0",
      negativePrompt = "blurry, dust, fingerprints, cheap materials, reflections distorted",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.PRODUCTS,
      imageUrl = "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "--ar" to "1:1",
        "--v" to "6.0",
        "--s" to "250"
      ),
      variables = listOf(
        PromptVariable("subject", "Luxury Product", "matte black and rose gold skeleton chronograph watch", listOf("matte black and rose gold skeleton chronograph watch", "minimalist titanium wireless earphone case", "artisanal perfume glass bottle with amber liquid")),
        PromptVariable("lighting", "Studio Light Rig", "precision spot lighting with crisp metallic highlights and subtle rim gradient", listOf("precision spot lighting with crisp metallic highlights and subtle rim gradient", "soft diffused light with moody shadows", "dramatic beam light through water mist"))
      ),
      likesCount = 1950,
      copyCount = 4120
    ),

    PromptItem(
      id = "prmt_sdxl_surreal_dreamscape",
      title = "Surreal Chrono-Spire in Floating Desert",
      description = "SDXL master prompt creating an impossible surreal landscape with chrome spheres and levitating geometry.",
      promptTemplate = "Masterpiece award-winning surrealist photograph of an ancient {subject} floating above crimson sand dunes under an eclipse sun, {lighting}, Salvador Dali meets Moebius aesthetic, octane render 8k, golden ratio composition --steps 30 --cfg 7.0",
      negativePrompt = "poorly rendered, ugly, distorted, jpeg artifacts, text, watermarks",
      model = AIModel.STABLE_DIFFUSION,
      category = PromptCategory.ART,
      imageUrl = "https://images.unsplash.com/photo-1550684848-fac1c5b4e853?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "--steps" to "30",
        "--cfg" to "7.0",
        "sampler" to "DPM++ 2M Karras"
      ),
      variables = listOf(
        PromptVariable("subject", "Surreal Element", "infinite mirrored chrome clock tower dissolving into luminous butterflies", listOf("infinite mirrored chrome clock tower dissolving into luminous butterflies", "levitating basalt obelisk with glowing molten gold veins", "giant crystalline skull reflecting alien constellations")),
        PromptVariable("lighting", "Atmospheric Aura", "warm sunset orange horizon with glowing sands", listOf("warm sunset orange horizon with glowing sands", "ethereal violet twilight with bioluminescent sands", "surreal eclipse corona with indigo halo"))
      ),
      likesCount = 1890,
      copyCount = 3980
    ),

    PromptItem(
      id = "prmt_gpt4o_saas_copywriter",
      title = "High-Converting Viral Landing Hook",
      description = "Prompt framework for generating viral headlines, subheaders, and pain-point objection breakers for AI startups.",
      promptTemplate = "Act as an elite conversion copywriter for a venture-backed {subject} tech company. Generate 5 high-converting headline variations using the PAS (Problem-Agitate-Solution) framework. Target audience: {lighting}. Tone: {mood}. Include 3 high-urgency call-to-action buttons, 3 trust-building micro-testimonials, and 1 compelling value proposition summary.",
      negativePrompt = "",
      model = AIModel.CHATGPT_4O,
      category = PromptCategory.MARKETING,
      imageUrl = "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "temperature" to "0.7",
        "top_p" to "0.9"
      ),
      variables = listOf(
        PromptVariable("subject", "Product Domain", "AI-powered automated prompt engineering tool", listOf("AI-powered automated prompt engineering tool", "Fintech B2B developer tool", "Next-gen creative design suite", "Growth marketing automation engine")),
        PromptVariable("lighting", "Target Audience", "Senior Founders, Marketers and Indie Hackers", listOf("Senior Founders, Marketers and Indie Hackers", "Designers and Prompt Engineers", "Enterprise AI Strategists")),
        PromptVariable("mood", "Brand Tone", "Punchy, authoritative, modern, and outcome-obsessed", listOf("Punchy, authoritative, modern, and outcome-obsessed", "Minimalist, elegant, high-status", "Energetic, visionary"))
      ),
      likesCount = 3120,
      copyCount = 7890
    ),

    PromptItem(
      id = "prmt_claude_clean_architecture",
      title = "Clean Architecture Compose Architect",
      description = "Full-stack Android Kotlin prompt for generating complete production-grade MVI/MVVM architectures with Room and Flow.",
      promptTemplate = "Act as a Principal Android Staff Architect. Write a modular, production-ready Kotlin Jetpack Compose implementation for a {subject}. Follow Clean Architecture with strict separation of domain, data, and presentation layers. Implement: 1. Immutable UI State sealed interfaces, 2. StateFlow-driven ViewModel with structured error handling, 3. Room DAO with Flow streams, 4. Modern M3 Composable with smooth animated transitions and edge-to-edge support. Constraint: {lighting}.",
      negativePrompt = "",
      model = AIModel.CLAUDE_3_5,
      category = PromptCategory.CODING,
      imageUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf(
        "temperature" to "0.3",
        "max_tokens" to "4096"
      ),
      variables = listOf(
        PromptVariable("subject", "Module / Feature", "real-time offline-first sync cache engine", listOf("real-time offline-first sync cache engine", "dynamic orange dashboard with canvas charts", "biometric authentication flow with cryptographic storage", "streaming token LLM client with markdown rendering")),
        PromptVariable("lighting", "Architectural Constraint", "Zero boilerplate, strictly adhere to Kotlin 2.0 Coroutines best practices and compose performance rules", listOf("Zero boilerplate, strictly adhere to Kotlin 2.0 Coroutines best practices and compose performance rules", "Provide comprehensive unit test suite using MockK and Turbine", "Optimize for low-latency memory footprint"))
      ),
      likesCount = 4520,
      copyCount = 9240
    ),

    PromptItem(
      id = "prmt_floating_3d_avatar",
      title = "Retro-Pop Floating 3D Avatar",
      description = "Create a retro-pop floating 3D avatar with vibrant plastic textures and studio lighting.",
      promptTemplate = "A vibrant 3D stylized character {subject}, floating pose, glossy clay and plastic textures, {lighting}, isometric viewpoint, clean solid background, soft ambient occlusion, Pixar and Pop-Mart art style, 8k render, octane render --ar 1:1 --v 6.0",
      negativePrompt = "photorealistic human, uncanny valley, ugly, low poly, noisy, grainy",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.THREE_D,
      imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf("--ar" to "1:1", "--v" to "6.0", "--s" to "300"),
      variables = listOf(
        PromptVariable("subject", "Character Type", "cheerful young designer with oversized headphones and orange sunglasses", listOf("cheerful young designer with oversized headphones and orange sunglasses", "cyber cat with robotic goggles and hoodie", "retro astronaut with holographic bubble helmet")),
        PromptVariable("lighting", "Studio Mood", "warm pastel gradient background with punchy rim light", listOf("warm pastel gradient background with punchy rim light", "clean studio white backdrop with soft pink bounce", "vibrant cyberpunk glow with neon backlight"))
      ),
      likesCount = 3410,
      copyCount = 5820
    ),

    PromptItem(
      id = "prmt_creature_costume_photo",
      title = "2D Creature Costume Portrait",
      description = "Turn a photo into a bold 2D creature-costume illustration with streetwear vibes.",
      promptTemplate = "Turn portrait into stylized 2D bold character art of {subject} wearing an oversized fuzzy creature hoodie, {lighting}, Japanese anime streetwear aesthetic, thick ink outlines, flat vibrant color fills, high energy pose, trending on ArtStation --ar 3:4 --v 6.0",
      negativePrompt = "dull colors, low res, sketch, messy lines, realistic skin",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.ANIME,
      imageUrl = "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf("--ar" to "3:4", "--v" to "6.0"),
      variables = listOf(
        PromptVariable("subject", "Costume & Persona", "girl with split-dyed orange hair in a black-and-gold mythical dragon hoodie", listOf("girl with split-dyed orange hair in a black-and-gold mythical dragon hoodie", "boy with headphones in a cyber-wolf parka", "character wearing an oversized kawaii monster onesie")),
        PromptVariable("lighting", "Color Tone", "electric neon accents against dark obsidian backdrop", listOf("electric neon accents against dark obsidian backdrop", "bright pastel sunset gradient", "vibrant comic book halftone print"))
      ),
      likesCount = 4130,
      copyCount = 7210
    ),

    PromptItem(
      id = "prmt_cyber_samurai_tokyo",
      title = "Cyber Samurai in Neo-Tokyo",
      description = "Master prompt for high-contrast neon rain reflections with traditional carbon-fiber katana armor.",
      promptTemplate = "Full-body cinematic portrait of a {subject} standing on a rainy rooftop overlooking Neo-Tokyo, {lighting}, reflective wet pavement, holographic billboard reflections, volumetric steam, 8k resolution, Unreal Engine 5 render aesthetic --ar 16:9 --v 6.0",
      negativePrompt = "blurry, low quality, artifacts, watermark, washed out colors",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.CINEMATIC,
      imageUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf("--ar" to "16:9", "--v" to "6.0", "--s" to "750"),
      variables = listOf(
        PromptVariable("subject", "Warrior Armor", "futuristic samurai wearing matte black armor with glowing orange trim and dual katanas", listOf("futuristic samurai wearing matte black armor with glowing orange trim and dual katanas", "ronin assassin with holographic kabuto helmet", "cyber-ninja in carbon stealth cloak")),
        PromptVariable("lighting", "Environment Light", "warm orange and cyan neon rain reflections with heavy god rays", listOf("warm orange and cyan neon rain reflections with heavy god rays", "dramatic lightning strike in misty night", "golden sunset breaking through skyscraper smog"))
      ),
      likesCount = 5120,
      copyCount = 9810
    ),

    PromptItem(
      id = "prmt_minimalist_vector_badge",
      title = "Clean Minimalist Brand Mark",
      description = "Precision geometric vector icon prompt with perfect negative space and balance.",
      promptTemplate = "Minimalist modern flat vector logo of {subject}, {lighting}, Swiss design principles, golden ratio geometry, clean silhouette, single line weight, high-status tech brand aesthetic, SVG vector style on pure dark obsidian background --v 6.0",
      negativePrompt = "gradient noise, 3d render, skeuomorphic, complex details, text, blurry",
      model = AIModel.MIDJOURNEY,
      category = PromptCategory.DESIGN,
      imageUrl = "https://images.unsplash.com/photo-1626785774573-4b799315345d?w=1000&auto=format&fit=crop&q=80",
      parameters = mapOf("--ar" to "1:1", "--v" to "6.0"),
      variables = listOf(
        PromptVariable("subject", "Emblem Concept", "geometric falcon fused with an infinity loop and lightning spark", listOf("geometric falcon fused with an infinity loop and lightning spark", "origami fox head with sharp polygonal facets", "abstract radiant sunburst with interlocking prisms")),
        PromptVariable("lighting", "Color Palette", "radiant electric orange and monochrome obsidian white", listOf("radiant electric orange and monochrome obsidian white", "monochrome gold on matte black", "clean duo-tone cyan and slate"))
      ),
      likesCount = 2890,
      copyCount = 4920
    )
  )
}

