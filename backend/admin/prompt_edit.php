<?php
require_once __DIR__ . '/../includes/header.php';

$db = getDbConnection();
$error = '';
$message = '';

$id = $_GET['id'] ?? '';
$isEdit = !empty($id);

$prompt = [
    'id' => 'prompt_' . uniqid(),
    'title' => '',
    'description' => '',
    'prompt_template' => '',
    'negative_prompt' => '',
    'model_name' => 'MIDJOURNEY',
    'category_name' => 'SCI_FI',
    'custom_category_name' => '',
    'image_url' => 'https://images.unsplash.com/photo-1578632767115-351597cf2477?w=900&auto=format&fit=crop&q=80',
    'parameters_json' => '{"--ar":"16:9","--v":"6.0","--s":"750"}',
    'variables_json' => '[]',
    'likes_count' => 150,
    'copy_count' => 300,
    'is_featured' => 0,
    'is_trending' => 1,
    'status' => 'active'
];

if ($isEdit) {
    $stmt = $db->prepare("SELECT * FROM prompts WHERE id = ?");
    $stmt->execute([$id]);
    $existing = $stmt->fetch();
    if ($existing) {
        $prompt = $existing;
    } else {
        $error = "Prompt not found.";
    }
}

// Handle Form Submission
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $title = trim($_POST['title'] ?? '');
    $promptTemplate = trim($_POST['prompt_template'] ?? '');
    
    if (empty($title) || empty($promptTemplate)) {
        $error = "Title and Prompt Template are required.";
    } else {
        $pId = $isEdit ? $id : trim($_POST['id'] ?: ('prompt_' . uniqid()));
        $desc = trim($_POST['description'] ?? '');
        $negativePrompt = trim($_POST['negative_prompt'] ?? '');
        $modelName = strtoupper(trim($_POST['model_name'] ?? 'MIDJOURNEY'));
        $categoryName = strtoupper(trim($_POST['category_name'] ?? 'SCI_FI'));
        $customCatName = trim($_POST['custom_category_name'] ?? '');
        $imageUrl = trim($_POST['image_url'] ?? '');
        $likesCount = (int)($_POST['likes_count'] ?? 100);
        $copyCount = (int)($_POST['copy_count'] ?? 200);
        $isFeatured = !empty($_POST['is_featured']) ? 1 : 0;
        $isTrending = !empty($_POST['is_trending']) ? 1 : 0;
        $status = $_POST['status'] ?? 'active';

        // Auto extract variables from template if variables_json is empty
        $varsJson = trim($_POST['variables_json'] ?? '');
        if (empty($varsJson) || $varsJson === '[]') {
            preg_match_all('/[\{\[]([a-zA-Z0-9_-]+)[\}\]]/', $promptTemplate, $matches);
            $extracted = [];
            if (!empty($matches[1])) {
                foreach (array_unique($matches[1]) as $key) {
                    $extracted[] = [
                        'key' => $key,
                        'label' => ucwords(str_replace(['_', '-'], ' ', $key)),
                        'defaultValue' => $key,
                        'options' => [$key]
                    ];
                }
            }
            $varsJson = json_encode($extracted);
        }

        $paramsJson = trim($_POST['parameters_json'] ?? '{}');

        try {
            if ($isEdit) {
                $stmt = $db->prepare("
                    UPDATE prompts SET 
                    title = ?, description = ?, prompt_template = ?, negative_prompt = ?, 
                    model_name = ?, category_name = ?, custom_category_name = ?, image_url = ?, 
                    parameters_json = ?, variables_json = ?, likes_count = ?, copy_count = ?, 
                    is_featured = ?, is_trending = ?, status = ? 
                    WHERE id = ?
                ");
                $stmt->execute([
                    $title, $desc, $promptTemplate, $negativePrompt,
                    $modelName, $categoryName, $customCatName, $imageUrl,
                    $paramsJson, $varsJson, $likesCount, $copyCount,
                    $isFeatured, $isTrending, $status, $pId
                ]);
                $message = "Prompt updated successfully!";
            } else {
                $stmt = $db->prepare("
                    INSERT INTO prompts (id, title, description, prompt_template, negative_prompt, model_name, category_name, custom_category_name, image_url, parameters_json, variables_json, likes_count, copy_count, is_featured, is_trending, status)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ");
                $stmt->execute([
                    $pId, $title, $desc, $promptTemplate, $negativePrompt,
                    $modelName, $categoryName, $customCatName, $imageUrl,
                    $paramsJson, $varsJson, $likesCount, $copyCount,
                    $isFeatured, $isTrending, $status
                ]);
                $message = "Prompt created successfully!";
                header("Location: prompt_edit.php?id=" . urlencode($pId) . "&msg=created");
                exit;
            }

            // Reload data
            $stmt = $db->prepare("SELECT * FROM prompts WHERE id = ?");
            $stmt->execute([$pId]);
            $prompt = $stmt->fetch();
        } catch (Exception $e) {
            $error = "Save failed: " . $e->getMessage();
        }
    }
}

$categories = $db->query("SELECT * FROM categories ORDER BY display_name ASC")->fetchAll();
?>

<div class="max-w-4xl mx-auto space-y-6">
  <div class="flex items-center justify-between gap-4">
    <div class="min-w-0 flex-1">
      <a href="prompts.php" class="text-xs text-gray-400 hover:text-orange-400 mono font-bold shrink-0 whitespace-nowrap inline-block">&larr; BACK TO PROMPT LIST</a>
      <h1 class="text-2xl font-black text-white mono mt-1 truncate">
        <?= $isEdit ? 'EDIT PROMPT BLUEPRINT' : 'CREATE NEW PROMPT BLUEPRINT' ?>
      </h1>
    </div>
    <?php if ($isEdit): ?>
      <span class="bg-gray-800 text-cyan-400 border border-cyan-500/30 px-3 py-1.5 rounded-lg mono text-xs shrink-0 whitespace-nowrap">
        ID: <?= htmlspecialchars($prompt['id']) ?>
      </span>
    <?php endif; ?>
  </div>

  <?php if ($message || isset($_GET['msg'])): ?>
    <div class="bg-emerald-950/80 border border-emerald-500/50 text-emerald-300 text-xs p-3.5 rounded-xl">
      <?= htmlspecialchars($message ?: 'Prompt saved successfully in cloud database!') ?>
    </div>
  <?php endif; ?>

  <?php if ($error): ?>
    <div class="bg-rose-950/80 border border-rose-500/50 text-rose-300 text-xs p-3.5 rounded-xl">
      <?= htmlspecialchars($error) ?>
    </div>
  <?php endif; ?>

  <form method="POST" class="bg-gray-900 border border-gray-800 rounded-2xl p-6 space-y-6 shadow-xl">
    <!-- Basic Info -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Prompt Title *</label>
        <input type="text" name="title" value="<?= htmlspecialchars($prompt['title']) ?>" placeholder="e.g. Cyberpunk Neon Ronin Infiltrator" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-sm text-gray-100 outline-none" required>
      </div>

      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Unique ID (Alphanumeric)</label>
        <input type="text" name="id" value="<?= htmlspecialchars($prompt['id']) ?>" <?= $isEdit ? 'readonly' : '' ?> class="w-full bg-gray-950 border border-gray-800 text-gray-400 rounded-xl px-4 py-2.5 text-sm outline-none mono">
      </div>
    </div>

    <div>
      <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Description</label>
      <input type="text" name="description" value="<?= htmlspecialchars($prompt['description']) ?>" placeholder="Short summary of the visual aesthetic..." class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-sm text-gray-100 outline-none">
    </div>

    <!-- Formula & Negatives -->
    <div>
      <div class="flex items-center justify-between mb-1.5">
        <label class="block text-xs font-semibold text-gray-400 uppercase mono">Prompt Formula (Use {variables} for dynamic tokens) *</label>
        <span class="text-[10px] text-orange-400 mono">Tip: Use {subject}, {lighting}, {style}</span>
      </div>
      <textarea name="prompt_template" rows="4" placeholder="Cinematic 8k composition of a {subject} with {lighting_style}, shot on 35mm lens --ar 16:9 --v 6.0" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl p-3.5 text-sm text-amber-200 outline-none font-mono" required><?= htmlspecialchars($prompt['prompt_template']) ?></textarea>
    </div>

    <div>
      <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Negative Prompt (Optional for SDXL / Flux)</label>
      <input type="text" name="negative_prompt" value="<?= htmlspecialchars($prompt['negative_prompt'] ?? '') ?>" placeholder="blurry, low resolution, bad anatomy, watermark, text" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-sm text-gray-300 outline-none font-mono">
    </div>

    <!-- AI Model, Category & Flags -->
    <div class="grid grid-cols-1 sm:grid-cols-3 gap-4">
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">AI Model Engine</label>
        <select name="model_name" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2.5 text-sm text-gray-200 outline-none">
          <option value="MIDJOURNEY" <?= $prompt['model_name'] === 'MIDJOURNEY' ? 'selected' : '' ?>>Midjourney v6</option>
          <option value="FLUX" <?= $prompt['model_name'] === 'FLUX' ? 'selected' : '' ?>>FLUX.1 Schnell/Dev</option>
          <option value="STABLE_DIFFUSION" <?= $prompt['model_name'] === 'STABLE_DIFFUSION' ? 'selected' : '' ?>>SDXL / Ultra</option>
          <option value="DALL_E_3" <?= $prompt['model_name'] === 'DALL_E_3' ? 'selected' : '' ?>>DALL-E 3</option>
          <option value="CHATGPT_4O" <?= $prompt['model_name'] === 'CHATGPT_4O' ? 'selected' : '' ?>>ChatGPT-4o Prompt</option>
          <option value="CLAUDE_3_5" <?= $prompt['model_name'] === 'CLAUDE_3_5' ? 'selected' : '' ?>>Claude 3.5 Sonnet</option>
        </select>
      </div>

      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Category</label>
        <select name="category_name" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2.5 text-sm text-gray-200 outline-none">
          <?php foreach ($categories as $c): ?>
            <option value="<?= htmlspecialchars($c['name']) ?>" <?= $prompt['category_name'] === $c['name'] ? 'selected' : '' ?>>
              <?= htmlspecialchars($c['display_name']) ?>
            </option>
          <?php endforeach; ?>
        </select>
      </div>

      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Custom Sub-Category</label>
        <input type="text" name="custom_category_name" value="<?= htmlspecialchars($prompt['custom_category_name'] ?? '') ?>" placeholder="e.g. Retro Cyberpunk" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-sm text-gray-200 outline-none">
      </div>
    </div>

    <!-- Image Preview & URL -->
    <div class="grid grid-cols-1 md:grid-cols-4 gap-4 items-start">
      <div class="md:col-span-3">
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Cover Image URL (Direct Web Link)</label>
        <input type="url" name="image_url" id="imageUrlInput" value="<?= htmlspecialchars($prompt['image_url']) ?>" placeholder="https://images.unsplash.com/photo-..." class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-sm text-gray-200 outline-none font-mono" onchange="document.getElementById('previewImg').src = this.value">
      </div>
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Preview</label>
        <img id="previewImg" src="<?= htmlspecialchars($prompt['image_url']) ?>" alt="Preview" class="w-full h-24 rounded-xl object-cover border border-gray-800 bg-gray-950 shadow-md" onerror="this.src='https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300'">
      </div>
    </div>

    <!-- Parameters & Variables JSON -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Parameters JSON</label>
        <textarea name="parameters_json" rows="3" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl p-3 text-xs text-gray-300 font-mono outline-none"><?= htmlspecialchars($prompt['parameters_json'] ?? '{}') ?></textarea>
      </div>
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase mono">Variables JSON (Leave empty to auto-extract)</label>
        <textarea name="variables_json" rows="3" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl p-3 text-xs text-gray-300 font-mono outline-none"><?= htmlspecialchars($prompt['variables_json'] ?? '[]') ?></textarea>
      </div>
    </div>

    <!-- Stats and Checkboxes -->
    <div class="grid grid-cols-2 sm:grid-cols-4 gap-4 pt-2 border-t border-gray-800">
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Likes Count</label>
        <input type="number" name="likes_count" value="<?= (int)$prompt['likes_count'] ?>" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-sm text-gray-200 outline-none">
      </div>
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Copy Count</label>
        <input type="number" name="copy_count" value="<?= (int)$prompt['copy_count'] ?>" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-sm text-gray-200 outline-none">
      </div>
      <div class="flex items-center space-x-2 pt-6">
        <input type="checkbox" name="is_trending" id="is_trending" value="1" <?= !empty($prompt['is_trending']) ? 'checked' : '' ?> class="w-4 h-4 accent-orange-500">
        <label for="is_trending" class="text-xs text-amber-300 font-bold mono">Trending Hero</label>
      </div>
      <div class="flex items-center space-x-2 pt-6">
        <input type="checkbox" name="is_featured" id="is_featured" value="1" <?= !empty($prompt['is_featured']) ? 'checked' : '' ?> class="w-4 h-4 accent-cyan-500">
        <label for="is_featured" class="text-xs text-cyan-300 font-bold mono">Featured Tag</label>
      </div>
    </div>

    <div class="pt-4 border-t border-gray-800 flex gap-3">
      <button type="submit" class="flex-1 bg-orange-500 hover:bg-orange-600 text-black font-black py-3.5 rounded-xl text-sm transition tracking-wider mono shadow-lg shadow-orange-500/20">
        <?= $isEdit ? 'SAVE & DEPLOY CHANGES' : 'PUBLISH PROMPT TO CLOUD' ?>
      </button>
      <a href="prompts.php" class="px-6 py-3.5 bg-gray-800 hover:bg-gray-700 text-gray-300 font-bold rounded-xl text-sm transition mono">
        CANCEL
      </a>
    </div>
  </form>
</div>

</main>
</body>
</html>
