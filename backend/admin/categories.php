<?php
require_once __DIR__ . '/../includes/header.php';

$db = getDbConnection();
$message = '';
$error = '';

// Handle Delete
if (isset($_GET['delete_id'])) {
    $stmt = $db->prepare("DELETE FROM categories WHERE id = ?");
    $stmt->execute([$_GET['delete_id']]);
    $message = "Category removed from taxonomy.";
}

// Handle Add / Edit
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = strtoupper(trim($_POST['id'] ?? ''));
    $displayName = trim($_POST['display_name'] ?? '');
    $description = trim($_POST['description'] ?? '');
    $badgeTag = strtoupper(trim($_POST['badge_tag'] ?? 'NEW'));
    $colorHex = trim($_POST['color_hex'] ?? '#FF6B00');
    $accentHex = trim($_POST['accent_hex'] ?? '#00F0FF');
    $bannerUrl = trim($_POST['banner_image_url'] ?? '');
    $sortOrder = (int)($_POST['sort_order'] ?? 0);

    if (empty($id) || empty($displayName)) {
        $error = "ID and Display Name are required.";
    } else {
        $stmt = $db->prepare("
            INSERT INTO categories (id, name, display_name, description, badge_tag, color_hex, accent_hex, banner_image_url, sort_order, is_active)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1)
            ON DUPLICATE KEY UPDATE display_name = VALUES(display_name), description = VALUES(description), badge_tag = VALUES(badge_tag), color_hex = VALUES(color_hex), accent_hex = VALUES(accent_hex), banner_image_url = VALUES(banner_image_url), sort_order = VALUES(sort_order)
        ");
        $stmt->execute([$id, $id, $displayName, $description, $badgeTag, $colorHex, $accentHex, $bannerUrl, $sortOrder]);
        $message = "Category saved successfully!";
    }
}

$categories = $db->query("SELECT * FROM categories ORDER BY sort_order ASC, display_name ASC")->fetchAll();
?>

<div class="space-y-6">
  <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
    <div>
      <h1 class="text-2xl font-black text-white mono">TAXONOMY & CATEGORIES</h1>
      <p class="text-xs text-gray-400 mt-0.5">Control category filters and landing tabs rendered in the Android application.</p>
    </div>
  </div>

  <?php if ($message): ?>
    <div class="bg-emerald-950/80 border border-emerald-500/50 text-emerald-300 text-xs p-3.5 rounded-xl">
      <?= htmlspecialchars($message) ?>
    </div>
  <?php endif; ?>

  <?php if ($error): ?>
    <div class="bg-rose-950/80 border border-rose-500/50 text-rose-300 text-xs p-3.5 rounded-xl">
      <?= htmlspecialchars($error) ?>
    </div>
  <?php endif; ?>

  <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
    <!-- Add/Edit Category Form -->
    <div class="bg-gray-900 border border-gray-800 rounded-2xl p-5 shadow-xl">
      <h2 class="text-sm font-black text-orange-400 mono mb-4 uppercase">CREATE / UPDATE CATEGORY</h2>
      <form method="POST" class="space-y-3.5">
        <div>
          <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Category ID (e.g. SCI_FI, 3D, FASHION)</label>
          <input type="text" name="id" placeholder="CINEMATIC" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none uppercase font-mono" required>
        </div>

        <div>
          <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Display Name</label>
          <input type="text" name="display_name" placeholder="Cinematic & Film Portraits" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none" required>
        </div>

        <div>
          <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Description</label>
          <input type="text" name="description" placeholder="Panavision 35mm flares and Rembrandt portraits" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none">
        </div>

        <div class="grid grid-cols-2 gap-2">
          <div>
            <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Badge Tag</label>
            <input type="text" name="badge_tag" value="NEW" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none uppercase font-mono">
          </div>
          <div>
            <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Sort Order</label>
            <input type="number" name="sort_order" value="1" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none">
          </div>
        </div>

        <div class="grid grid-cols-2 gap-2">
          <div>
            <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Color Hex</label>
            <input type="text" name="color_hex" value="#FF6B00" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-xs text-orange-400 font-mono">
          </div>
          <div>
            <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Accent Hex</label>
            <input type="text" name="accent_hex" value="#00F0FF" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-xs text-cyan-400 font-mono">
          </div>
        </div>

        <div>
          <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Banner Image URL</label>
          <input type="url" name="banner_image_url" placeholder="https://images.unsplash.com/..." class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none font-mono">
        </div>

        <button type="submit" class="w-full bg-orange-500 hover:bg-orange-600 text-black font-black py-2.5 rounded-xl text-xs transition tracking-wider mono mt-2">
          SAVE CATEGORY
        </button>
      </form>
    </div>

    <!-- Existing Categories List -->
    <div class="lg:col-span-2 bg-gray-900 border border-gray-800 rounded-2xl overflow-hidden shadow-xl">
      <div class="p-4 border-b border-gray-800 flex items-center justify-between gap-4">
        <h2 class="text-sm font-black text-white mono uppercase shrink-0 whitespace-nowrap">ACTIVE TAXONOMY CATEGORIES</h2>
        <span class="text-[11px] text-gray-400 mono shrink-0 whitespace-nowrap"><?= count($categories) ?> CATEGORIES REGISTERED</span>
      </div>
      
      <!-- Category List Rows (Flex Layout with Locked Badges & Truncated Titles) -->
      <div class="p-4 space-y-2.5">
        <?php foreach ($categories as $cat): ?>
          <div class="flex items-center justify-between gap-3 p-3 bg-gray-950/70 border border-gray-800/80 hover:border-gray-700 rounded-xl transition">
            <!-- Badge Container (Locked Dimensions, No Shrink, No Multi-line Wrapping) -->
            <div class="shrink-0 whitespace-nowrap flex items-center">
              <span class="shrink-0 whitespace-nowrap inline-flex items-center justify-center px-2.5 py-1 rounded-md text-[10px] font-extrabold mono tracking-wider" style="background-color: <?= htmlspecialchars($cat['color_hex']) ?>22; color: <?= htmlspecialchars($cat['color_hex']) ?>; border: 1px solid <?= htmlspecialchars($cat['color_hex']) ?>66;">
                [<?= htmlspecialchars($cat['badge_tag']) ?>]
              </span>
            </div>

            <!-- Title & Subtitle Container (Takes Remaining Space Cleanly with Truncation) -->
            <div class="min-w-0 flex-1">
              <div class="font-bold text-gray-100 text-xs truncate" title="<?= htmlspecialchars($cat['display_name']) ?>">
                <?= htmlspecialchars($cat['display_name']) ?>
              </div>
              <div class="text-[10px] text-gray-400 mono truncate mt-0.5">
                ID: <span class="text-gray-300"><?= htmlspecialchars($cat['id']) ?></span> &bull; <span class="text-gray-500"><?= htmlspecialchars($cat['description'] ?: 'No description provided') ?></span>
              </div>
            </div>

            <!-- Sort Order Pill (Locked Dimensions) -->
            <div class="shrink-0 whitespace-nowrap hidden sm:flex items-center text-[10px] mono text-gray-400 bg-gray-900 border border-gray-800 px-2 py-0.5 rounded">
              Order: #<?= (int)$cat['sort_order'] ?>
            </div>

            <!-- Actions (Locked Dimensions) -->
            <div class="shrink-0 whitespace-nowrap flex items-center space-x-1.5">
              <a href="categories.php?delete_id=<?= urlencode($cat['id']) ?>" onclick="return confirm('Delete category?')" class="shrink-0 whitespace-nowrap inline-flex items-center justify-center text-rose-400 hover:text-rose-300 font-bold px-2.5 py-1 bg-rose-950/50 rounded-lg border border-rose-500/30 text-xs mono transition">
                DEL
              </a>
            </div>
          </div>
        <?php endforeach; ?>
      </div>
    </div>
  </div>
</div>

</main>
</body>
</html>
