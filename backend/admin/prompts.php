<?php
require_once __DIR__ . '/../includes/header.php';

$db = getDbConnection();
$message = '';
$error = '';

// Handle Delete
if (isset($_GET['delete_id'])) {
    $stmt = $db->prepare("DELETE FROM prompts WHERE id = ?");
    $stmt->execute([$_GET['delete_id']]);
    $message = "Prompt deleted successfully.";
}

// Handle Quick Toggle Trending / Featured
if (isset($_GET['toggle_trending'])) {
    $stmt = $db->prepare("UPDATE prompts SET is_trending = NOT is_trending WHERE id = ?");
    $stmt->execute([$_GET['toggle_trending']]);
    header('Location: prompts.php');
    exit;
}

// Search and Filter
$search = trim($_GET['search'] ?? '');
$filterCategory = trim($_GET['category'] ?? '');
$filterModel = trim($_GET['model'] ?? '');

$sql = "SELECT * FROM prompts WHERE 1=1";
$params = [];

if ($search !== '') {
    $sql .= " AND (title LIKE ? OR description LIKE ? OR prompt_template LIKE ?)";
    $term = "%$search%";
    $params[] = $term;
    $params[] = $term;
    $params[] = $term;
}

if ($filterCategory !== '') {
    $sql .= " AND category_name = ?";
    $params[] = $filterCategory;
}

if ($filterModel !== '') {
    $sql .= " AND model_name = ?";
    $params[] = $filterModel;
}

$sql .= " ORDER BY created_at DESC";
$stmt = $db->prepare($sql);
$stmt->execute($params);
$prompts = $stmt->fetchAll();

// Fetch Categories for dropdown
$categories = $db->query("SELECT * FROM categories ORDER BY display_name ASC")->fetchAll();
?>

<div class="space-y-6">
  <!-- Top Action Header -->
  <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
    <div>
      <h1 class="text-2xl font-black text-white mono">PROMPTS VAULT MANAGER</h1>
      <p class="text-xs text-gray-400 mt-0.5">Manage, add, and publish prompts delivered to the Android mobile app.</p>
    </div>
    <a href="prompt_edit.php" class="bg-orange-500 hover:bg-orange-600 text-black font-extrabold px-4 py-2.5 rounded-xl text-xs transition mono flex items-center justify-center space-x-1.5 shadow-lg shadow-orange-500/20">
      <span>+</span>
      <span>ADD NEW PROMPT</span>
    </a>
  </div>

  <?php if ($message): ?>
    <div class="bg-emerald-950/80 border border-emerald-500/50 text-emerald-300 text-xs p-3.5 rounded-xl">
      <?= htmlspecialchars($message) ?>
    </div>
  <?php endif; ?>

  <!-- Search & Filter Bar -->
  <div class="bg-gray-900 border border-gray-800 p-4 rounded-2xl flex flex-wrap items-center gap-3">
    <form method="GET" class="flex flex-wrap items-center gap-3 w-full">
      <div class="flex-1 min-w-[200px]">
        <input type="text" name="search" value="<?= htmlspecialchars($search) ?>" placeholder="Search prompts by title, description or formula..." class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3.5 py-2 text-xs text-gray-100 outline-none">
      </div>

      <div>
        <select name="category" class="bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2 text-xs text-gray-300 outline-none">
          <option value="">All Categories</option>
          <?php foreach ($categories as $c): ?>
            <option value="<?= htmlspecialchars($c['name']) ?>" <?= $filterCategory === $c['name'] ? 'selected' : '' ?>>
              <?= htmlspecialchars($c['display_name']) ?>
            </option>
          <?php endforeach; ?>
        </select>
      </div>

      <div>
        <select name="model" class="bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-3 py-2 text-xs text-gray-300 outline-none">
          <option value="">All AI Engines</option>
          <option value="MIDJOURNEY" <?= $filterModel === 'MIDJOURNEY' ? 'selected' : '' ?>>Midjourney</option>
          <option value="FLUX" <?= $filterModel === 'FLUX' ? 'selected' : '' ?>>FLUX.1</option>
          <option value="STABLE_DIFFUSION" <?= $filterModel === 'STABLE_DIFFUSION' ? 'selected' : '' ?>>SDXL</option>
          <option value="DALL_E_3" <?= $filterModel === 'DALL_E_3' ? 'selected' : '' ?>>DALL-E 3</option>
          <option value="CHATGPT_4O" <?= $filterModel === 'CHATGPT_4O' ? 'selected' : '' ?>>ChatGPT-4o</option>
          <option value="CLAUDE_3_5" <?= $filterModel === 'CLAUDE_3_5' ? 'selected' : '' ?>>Claude 3.5</option>
        </select>
      </div>

      <button type="submit" class="bg-gray-800 hover:bg-gray-700 text-orange-400 border border-orange-500/30 px-4 py-2 rounded-xl text-xs font-bold mono">
        FILTER
      </button>

      <?php if ($search || $filterCategory || $filterModel): ?>
        <a href="prompts.php" class="text-xs text-gray-400 hover:text-gray-200 mono underline ml-2">Clear</a>
      <?php endif; ?>
    </form>
  </div>

  <!-- View Toggle & Header Count -->
  <div class="flex items-center justify-between gap-4">
    <div class="text-xs text-gray-400 mono shrink-0 whitespace-nowrap">
      SHOWING <span class="text-orange-400 font-bold"><?= count($prompts) ?></span> PROMPTS IN VAULT
    </div>
    <div class="flex items-center space-x-2 shrink-0">
      <button type="button" onclick="setVaultView('cards')" id="btnViewCards" class="shrink-0 whitespace-nowrap px-3 py-1.5 rounded-lg text-xs mono font-bold bg-orange-500 text-black transition">
        📱 CARD GRID
      </button>
      <button type="button" onclick="setVaultView('table')" id="btnViewTable" class="shrink-0 whitespace-nowrap px-3 py-1.5 rounded-lg text-xs mono font-bold bg-gray-900 text-gray-400 border border-gray-800 hover:text-white transition">
        📄 TABLE
      </button>
    </div>
  </div>

  <!-- Prompts Card Grid (Enforces Locked Badges, min-w-0 Truncation & Fixed COPY Button) -->
  <div id="vaultCardsView" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
    <?php if (empty($prompts)): ?>
      <div class="col-span-full bg-gray-900 border border-gray-800 rounded-2xl p-12 text-center text-gray-500 mono">
        No prompts match your search criteria.
      </div>
    <?php else: ?>
      <?php foreach ($prompts as $p): ?>
        <div class="bg-gray-900 border border-gray-800 hover:border-gray-700 rounded-2xl overflow-hidden shadow-xl flex flex-col transition">
          <!-- Card Image Header -->
          <div class="relative h-44 w-full bg-gray-950 overflow-hidden shrink-0">
            <img src="<?= htmlspecialchars($p['image_url']) ?>" alt="<?= htmlspecialchars($p['title']) ?>" class="w-full h-full object-cover" onerror="this.src='https://images.unsplash.com/photo-1578632767115-351597cf2477?w=600'">
            <div class="absolute inset-0 bg-gradient-to-t from-gray-900 via-transparent to-black/60"></div>
            
            <!-- Top Badges Row (Locked Dimensions & No Shrink) -->
            <div class="absolute top-3 inset-x-3 flex items-center justify-between gap-2">
              <span class="shrink-0 whitespace-nowrap inline-flex items-center px-2 py-0.5 rounded text-[10px] font-extrabold mono bg-black/80 text-orange-400 border border-orange-500/40 shadow">
                [<?= htmlspecialchars($p['model_name']) ?>]
              </span>
              <span class="shrink-0 whitespace-nowrap inline-flex items-center px-2 py-0.5 rounded text-[10px] font-extrabold mono bg-black/80 text-cyan-400 border border-cyan-500/40 shadow">
                [<?= htmlspecialchars($p['category_name']) ?>]
              </span>
            </div>

            <!-- Category / Tag Sub-badge -->
            <?php if (!empty($p['custom_category_name'])): ?>
              <div class="absolute bottom-2 left-3 shrink-0 whitespace-nowrap">
                <span class="shrink-0 whitespace-nowrap text-[9px] font-bold mono bg-cyan-950/80 text-cyan-300 border border-cyan-500/40 px-1.5 py-0.5 rounded">
                  <?= htmlspecialchars($p['custom_category_name']) ?>
                </span>
              </div>
            <?php endif; ?>
          </div>

          <!-- Card Content Body (Flex Column) -->
          <div class="p-4 flex-1 flex flex-col justify-between">
            <!-- Header Row with Title & Flags -->
            <div>
              <div class="flex items-start justify-between gap-2">
                <!-- Title Container with min-w-0 for Ellipsis Truncation -->
                <div class="min-w-0 flex-1">
                  <h3 class="font-bold text-gray-100 text-sm truncate" title="<?= htmlspecialchars($p['title']) ?>">
                    <?= htmlspecialchars($p['title']) ?>
                  </h3>
                  <p class="text-gray-400 text-xs mt-1 line-clamp-2" title="<?= htmlspecialchars($p['description']) ?>">
                    <?= htmlspecialchars($p['description']) ?>
                  </p>
                </div>

                <!-- Trending / Featured Status Badge (Locked Dimensions) -->
                <div class="shrink-0 whitespace-nowrap flex flex-col items-end gap-1">
                  <?php if ($p['is_trending']): ?>
                    <span class="shrink-0 whitespace-nowrap px-1.5 py-0.5 rounded text-[9px] font-extrabold mono bg-amber-950/80 text-amber-300 border border-amber-500/40">
                      🔥 TRENDING
                    </span>
                  <?php endif; ?>
                </div>
              </div>

              <!-- Template Formula Box (Truncated Line Clamp) -->
              <div class="mt-3 p-2.5 bg-gray-950/90 rounded-xl border border-gray-800 text-[11px] text-gray-300 font-mono line-clamp-2 select-all">
                <?= htmlspecialchars($p['prompt_template']) ?>
              </div>
            </div>

            <!-- Card Footer: Stats on Left & Fixed "COPY" Action Button at Bottom Right -->
            <div class="mt-4 pt-3 border-t border-gray-800/80 flex items-center justify-between gap-3">
              <!-- Stats Row (Flex Item with min-w-0) -->
              <div class="min-w-0 flex items-center space-x-3 text-xs text-gray-400 mono shrink-0 whitespace-nowrap">
                <span class="shrink-0 whitespace-nowrap">❤️ <?= number_format($p['likes_count']) ?></span>
                <span class="shrink-0 whitespace-nowrap">📋 <?= number_format($p['copy_count']) ?></span>
              </div>

              <!-- Action Buttons (Locked Fixed Dimensions, Aligned Bottom Right) -->
              <div class="shrink-0 whitespace-nowrap flex items-center space-x-1.5">
                <!-- Quick Copy Action Button (Fixed Width/Padding, Never Shrinks, Never Wraps) -->
                <button type="button" 
                  onclick="copyFormula(this, <?= htmlspecialchars(json_encode($p['prompt_template'])) ?>)" 
                  class="shrink-0 whitespace-nowrap inline-flex items-center justify-center px-3 py-1.5 rounded-lg text-xs font-black mono bg-orange-500/20 hover:bg-orange-500 text-orange-400 hover:text-black border border-orange-500/40 transition">
                  <span class="copy-label shrink-0 whitespace-nowrap">COPY</span>
                </button>

                <a href="prompt_edit.php?id=<?= urlencode($p['id']) ?>" 
                   class="shrink-0 whitespace-nowrap inline-flex items-center justify-center px-2.5 py-1.5 rounded-lg text-xs font-bold mono bg-cyan-950/50 hover:bg-cyan-900 text-cyan-400 border border-cyan-500/30 transition">
                  EDIT
                </a>
                
                <a href="prompts.php?delete_id=<?= urlencode($p['id']) ?>" 
                   onclick="return confirm('Are you sure you want to delete this prompt?')" 
                   class="shrink-0 whitespace-nowrap inline-flex items-center justify-center px-2 py-1.5 rounded-lg text-xs font-bold mono bg-rose-950/50 hover:bg-rose-900 text-rose-400 border border-rose-500/30 transition">
                  DEL
                </a>
              </div>
            </div>
          </div>
        </div>
      <?php endforeach; ?>
    <?php endif; ?>
  </div>

  <!-- Prompts Data Table (Alternative View) -->
  <div id="vaultTableView" class="hidden bg-gray-900 border border-gray-800 rounded-2xl overflow-hidden shadow-xl">
    <div class="overflow-x-auto">
      <table class="w-full text-left border-collapse text-xs">
        <thead>
          <tr class="bg-gray-950/60 border-b border-gray-800 text-gray-400 uppercase mono text-[10px]">
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Thumb</th>
            <th class="py-3 px-4 min-w-0">Title & Formula Template</th>
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">AI Engine</th>
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Category</th>
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Flags</th>
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Stats</th>
            <th class="py-3 px-4 text-right shrink-0 whitespace-nowrap">Actions</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-800">
          <?php if (empty($prompts)): ?>
            <tr>
              <td colspan="7" class="text-center py-12 text-gray-500 mono">No prompts match your search criteria.</td>
            </tr>
          <?php else: ?>
            <?php foreach ($prompts as $p): ?>
              <tr class="hover:bg-gray-800/40 transition">
                <td class="py-3 px-4 shrink-0 whitespace-nowrap">
                  <img src="<?= htmlspecialchars($p['image_url']) ?>" alt="Cover" class="w-14 h-14 rounded-xl object-cover border border-gray-800 shadow-md shrink-0" onerror="this.src='https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300'">
                </td>
                <td class="py-3 px-4 max-w-md min-w-0">
                  <div class="font-bold text-gray-100 text-sm truncate"><?= htmlspecialchars($p['title']) ?></div>
                  <div class="text-gray-400 text-xs mt-0.5 truncate"><?= htmlspecialchars($p['description']) ?></div>
                  <div class="mt-1 bg-gray-950/80 p-2 rounded-lg border border-gray-800/80 text-[11px] text-gray-300 mono line-clamp-2">
                    <?= htmlspecialchars($p['prompt_template']) ?>
                  </div>
                </td>
                <td class="py-3 px-4 shrink-0 whitespace-nowrap">
                  <span class="shrink-0 whitespace-nowrap inline-flex items-center bg-orange-950/60 text-orange-400 border border-orange-500/30 px-2 py-0.5 rounded text-[10px] font-bold mono">
                    [<?= htmlspecialchars($p['model_name']) ?>]
                  </span>
                </td>
                <td class="py-3 px-4 shrink-0 whitespace-nowrap mono text-[11px] text-gray-300">
                  <span class="shrink-0 whitespace-nowrap font-bold">[<?= htmlspecialchars($p['category_name']) ?>]</span>
                  <?php if (!empty($p['custom_category_name'])): ?>
                    <span class="block text-[9px] text-cyan-400 shrink-0 whitespace-nowrap">(<?= htmlspecialchars($p['custom_category_name']) ?>)</span>
                  <?php endif; ?>
                </td>
                <td class="py-3 px-4 shrink-0 whitespace-nowrap">
                  <div class="flex flex-col gap-1 shrink-0">
                    <a href="prompts.php?toggle_trending=<?= urlencode($p['id']) ?>" class="shrink-0 whitespace-nowrap <?= $p['is_trending'] ? 'bg-amber-950/80 text-amber-300 border-amber-500/40' : 'bg-gray-950 text-gray-500 border-gray-800' ?> border text-[9px] px-1.5 py-0.5 rounded font-bold mono text-center">
                      <?= $p['is_trending'] ? '🔥 TRENDING' : 'TRENDING: OFF' ?>
                    </a>
                    <?php if ($p['is_featured']): ?>
                      <span class="shrink-0 whitespace-nowrap bg-cyan-950/80 text-cyan-300 border border-cyan-500/40 text-[9px] px-1.5 py-0.5 rounded font-bold mono text-center">FEATURED</span>
                    <?php endif; ?>
                  </div>
                </td>
                <td class="py-3 px-4 shrink-0 whitespace-nowrap mono text-[11px] text-gray-300">
                  <div class="shrink-0 whitespace-nowrap">❤️ <?= number_format($p['likes_count']) ?></div>
                  <div class="shrink-0 whitespace-nowrap">📋 <?= number_format($p['copy_count']) ?></div>
                </td>
                <td class="py-3 px-4 text-right shrink-0 whitespace-nowrap mono space-x-1">
                  <!-- COPY Button in Table -->
                  <button type="button" 
                    onclick="copyFormula(this, <?= htmlspecialchars(json_encode($p['prompt_template'])) ?>)" 
                    class="shrink-0 whitespace-nowrap inline-flex items-center justify-center px-2.5 py-1.5 bg-orange-950/60 hover:bg-orange-500 text-orange-400 hover:text-black font-bold rounded-lg border border-orange-500/40 transition">
                    <span class="copy-label shrink-0 whitespace-nowrap">COPY</span>
                  </button>
                  <a href="prompt_edit.php?id=<?= urlencode($p['id']) ?>" class="shrink-0 whitespace-nowrap inline-flex items-center justify-center text-cyan-400 hover:text-cyan-300 font-bold px-2.5 py-1.5 bg-cyan-950/50 rounded-lg border border-cyan-500/30 transition">
                    EDIT
                  </a>
                  <a href="prompts.php?delete_id=<?= urlencode($p['id']) ?>" onclick="return confirm('Are you sure you want to delete this prompt?')" class="shrink-0 whitespace-nowrap inline-flex items-center justify-center text-rose-400 hover:text-rose-300 font-bold px-2.5 py-1.5 bg-rose-950/50 rounded-lg border border-rose-500/30 transition">
                    DEL
                  </a>
                </td>
              </tr>
            <?php endforeach; ?>
          <?php endif; ?>
        </tbody>
      </table>
    </div>
  </div>
</div>

<script>
function copyFormula(btn, text) {
  if (navigator.clipboard && window.isSecureContext) {
    navigator.clipboard.writeText(text);
  } else {
    const ta = document.createElement('textarea');
    ta.value = text;
    document.body.appendChild(ta);
    ta.select();
    document.execCommand('copy');
    document.body.removeChild(ta);
  }
  const label = btn.querySelector('.copy-label');
  if (label) {
    const orig = label.innerText;
    label.innerText = 'COPIED!';
    btn.classList.add('bg-orange-500', 'text-black');
    setTimeout(() => {
      label.innerText = orig;
      btn.classList.remove('bg-orange-500', 'text-black');
    }, 1500);
  }
}

function setVaultView(view) {
  const cards = document.getElementById('vaultCardsView');
  const table = document.getElementById('vaultTableView');
  const btnCards = document.getElementById('btnViewCards');
  const btnTable = document.getElementById('btnViewTable');

  if (view === 'cards') {
    cards.classList.remove('hidden');
    table.classList.add('hidden');
    btnCards.className = 'shrink-0 whitespace-nowrap px-3 py-1.5 rounded-lg text-xs mono font-bold bg-orange-500 text-black transition';
    btnTable.className = 'shrink-0 whitespace-nowrap px-3 py-1.5 rounded-lg text-xs mono font-bold bg-gray-900 text-gray-400 border border-gray-800 hover:text-white transition';
  } else {
    cards.classList.add('hidden');
    table.classList.remove('hidden');
    btnTable.className = 'shrink-0 whitespace-nowrap px-3 py-1.5 rounded-lg text-xs mono font-bold bg-orange-500 text-black transition';
    btnCards.className = 'shrink-0 whitespace-nowrap px-3 py-1.5 rounded-lg text-xs mono font-bold bg-gray-900 text-gray-400 border border-gray-800 hover:text-white transition';
  }
}
</script>
</div>

</main>
</body>
</html>
