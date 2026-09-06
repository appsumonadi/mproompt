<?php
require_once __DIR__ . '/../includes/header.php';

$db = getDbConnection();

// Fetch statistics
$promptsCount = $db->query("SELECT COUNT(*) FROM prompts WHERE status = 'active'")->fetchColumn();
$categoriesCount = $db->query("SELECT COUNT(*) FROM categories WHERE is_active = 1")->fetchColumn();
$trendingCount = $db->query("SELECT COUNT(*) FROM prompts WHERE is_trending = 1 AND status = 'active'")->fetchColumn();
$featuredCount = $db->query("SELECT COUNT(*) FROM prompts WHERE is_featured = 1 AND status = 'active'")->fetchColumn();
$totalCopies = $db->query("SELECT SUM(copy_count) FROM prompts")->fetchColumn() ?: 0;

// Fetch latest prompts
$recentPrompts = $db->query("SELECT * FROM prompts ORDER BY created_at DESC LIMIT 6")->fetchAll();

$serverHost = (isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on' ? "https" : "http") . "://" . $_SERVER['HTTP_HOST'] . dirname(dirname($_SERVER['REQUEST_URI']));
$syncUrl = rtrim($serverHost, '/') . '/api/sync.php';
$apiKey = getSetting('api_secret_key', API_SECRET_KEY);
?>

<div class="space-y-8">
  <!-- Top Greeting & Live API Endpoint Banner -->
  <div class="bg-gradient-to-r from-gray-900 via-gray-900 to-orange-950/40 border border-orange-500/30 rounded-2xl p-6 shadow-xl">
    <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between gap-4">
      <div>
        <div class="flex items-center space-x-2 mb-1">
          <span class="w-2.5 h-2.5 rounded-full bg-emerald-400 animate-ping"></span>
          <span class="text-xs font-bold text-emerald-400 mono">CLOUD SERVER OPERATIONAL</span>
        </div>
        <h1 class="text-2xl font-black text-white">Prompteg Master Admin Dashboard</h1>
        <p class="text-xs text-gray-400 mt-1">Directly control prompts, taxonomy categories, and AdMob configs rendered in the Android application.</p>
      </div>
      <div class="flex flex-wrap gap-2">
        <a href="prompts.php?action=new" class="bg-orange-500 hover:bg-orange-600 text-black font-extrabold px-4 py-2.5 rounded-xl text-xs transition mono flex items-center space-x-1.5">
          <span>+</span>
          <span>CREATE NEW PROMPT</span>
        </a>
        <a href="../api/sync.php?api_key=<?= urlencode($apiKey) ?>" target="_blank" class="bg-gray-800 hover:bg-gray-700 text-cyan-400 border border-cyan-500/30 font-bold px-4 py-2.5 rounded-xl text-xs transition mono">
          PREVIEW JSON SYNC ↗
        </a>
      </div>
    </div>

    <!-- Live Mobile Connection Coordinates -->
    <div class="mt-6 pt-5 border-t border-gray-800 grid grid-cols-1 md:grid-cols-2 gap-4 mono text-xs">
      <div class="bg-gray-950/80 p-3.5 rounded-xl border border-gray-800">
        <span class="text-gray-400 block text-[10px] uppercase font-bold">Android App Sync Backend URL:</span>
        <code class="text-emerald-400 select-all font-semibold break-all text-xs"><?= htmlspecialchars($syncUrl) ?></code>
      </div>
      <div class="bg-gray-950/80 p-3.5 rounded-xl border border-gray-800">
        <span class="text-gray-400 block text-[10px] uppercase font-bold">Mobile API Secret Key:</span>
        <code class="text-amber-400 select-all font-semibold break-all text-xs"><?= htmlspecialchars($apiKey) ?></code>
      </div>
    </div>
  </div>

  <!-- Metric Statistics Cards -->
  <div class="grid grid-cols-2 sm:grid-cols-2 lg:grid-cols-4 gap-4">
    <div class="bg-gray-900 border border-gray-800 p-5 rounded-2xl hover:border-orange-500/40 transition">
      <div class="flex items-center justify-between text-xs text-gray-400 mono">
        <span>TOTAL PROMPTS</span>
        <span class="text-orange-500">⚡</span>
      </div>
      <div class="text-3xl font-black text-white mt-2 mono"><?= number_format($promptsCount) ?></div>
      <p class="text-[11px] text-gray-500 mt-1">Live active templates</p>
    </div>

    <div class="bg-gray-900 border border-gray-800 p-5 rounded-2xl hover:border-cyan-500/40 transition">
      <div class="flex items-center justify-between text-xs text-gray-400 mono">
        <span>CATEGORIES</span>
        <span class="text-cyan-400">🏷️</span>
      </div>
      <div class="text-3xl font-black text-white mt-2 mono"><?= number_format($categoriesCount) ?></div>
      <p class="text-[11px] text-gray-500 mt-1">Taxonomy collections</p>
    </div>

    <div class="bg-gray-900 border border-gray-800 p-5 rounded-2xl hover:border-emerald-500/40 transition">
      <div class="flex items-center justify-between text-xs text-gray-400 mono">
        <span>TRENDING SPOTLIGHT</span>
        <span class="text-emerald-400">🔥</span>
      </div>
      <div class="text-3xl font-black text-white mt-2 mono"><?= number_format($trendingCount) ?></div>
      <p class="text-[11px] text-gray-500 mt-1">Featured on app home</p>
    </div>

    <div class="bg-gray-900 border border-gray-800 p-5 rounded-2xl hover:border-purple-500/40 transition">
      <div class="flex items-center justify-between text-xs text-gray-400 mono">
        <span>TOTAL COPIES</span>
        <span class="text-purple-400">📋</span>
      </div>
      <div class="text-3xl font-black text-white mt-2 mono"><?= number_format($totalCopies) ?></div>
      <p class="text-[11px] text-gray-500 mt-1">User executions</p>
    </div>
  </div>

  <!-- Recent Prompts Table -->
  <div class="bg-gray-900 border border-gray-800 rounded-2xl overflow-hidden shadow-xl">
    <div class="p-5 border-b border-gray-800 flex items-center justify-between gap-4">
      <div class="min-w-0 flex-1">
        <h2 class="text-base font-black text-white mono truncate">RECENT PROMPTS IN CLOUD REPOSITORY</h2>
        <p class="text-xs text-gray-400 truncate">Latest prompt blueprints synced across mobile devices</p>
      </div>
      <a href="prompts.php" class="shrink-0 whitespace-nowrap text-orange-400 hover:underline text-xs mono font-bold">VIEW ALL PROMPTS &rarr;</a>
    </div>

    <div class="overflow-x-auto">
      <table class="w-full text-left border-collapse text-xs">
        <thead>
          <tr class="bg-gray-950/60 border-b border-gray-800 text-gray-400 uppercase mono text-[10px]">
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Preview</th>
            <th class="py-3 px-4 min-w-0">Title & Description</th>
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Model & Category</th>
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Stats</th>
            <th class="py-3 px-4 shrink-0 whitespace-nowrap">Status</th>
            <th class="py-3 px-4 text-right shrink-0 whitespace-nowrap">Actions</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-800">
          <?php if (empty($recentPrompts)): ?>
            <tr>
              <td colspan="6" class="text-center py-8 text-gray-500 mono">No prompts found in database yet. Click "CREATE NEW PROMPT" to add one!</td>
            </tr>
          <?php else: ?>
            <?php foreach ($recentPrompts as $p): ?>
              <tr class="hover:bg-gray-800/40 transition">
                <td class="py-3 px-4 shrink-0 whitespace-nowrap">
                  <img src="<?= htmlspecialchars($p['image_url']) ?>" alt="Preview" class="w-12 h-12 rounded-lg object-cover border border-gray-800 shrink-0" onerror="this.src='https://images.unsplash.com/photo-1578632767115-351597cf2477?w=300'">
                </td>
                <td class="py-3 px-4 max-w-xs min-w-0">
                  <div class="font-bold text-gray-100 text-sm truncate" title="<?= htmlspecialchars($p['title']) ?>"><?= htmlspecialchars($p['title']) ?></div>
                  <div class="text-gray-400 text-[11px] truncate mt-0.5" title="<?= htmlspecialchars($p['description']) ?>"><?= htmlspecialchars($p['description']) ?></div>
                </td>
                <td class="py-3 px-4 shrink-0 whitespace-nowrap">
                  <span class="shrink-0 whitespace-nowrap inline-flex items-center bg-orange-950/60 text-orange-400 border border-orange-500/30 px-2 py-0.5 rounded text-[10px] font-bold mono">
                    [<?= htmlspecialchars($p['model_name']) ?>]
                  </span>
                  <div class="text-[10px] text-gray-400 mt-1 mono shrink-0 whitespace-nowrap font-bold">[<?= htmlspecialchars($p['category_name']) ?>]</div>
                </td>
                <td class="py-3 px-4 shrink-0 whitespace-nowrap mono text-[11px] text-gray-300">
                  <div class="shrink-0 whitespace-nowrap">❤️ <?= number_format($p['likes_count']) ?></div>
                  <div class="shrink-0 whitespace-nowrap">📋 <?= number_format($p['copy_count']) ?></div>
                </td>
                <td class="py-3 px-4 shrink-0 whitespace-nowrap">
                  <?php if ($p['is_trending']): ?>
                    <span class="shrink-0 whitespace-nowrap inline-flex items-center bg-amber-950/80 text-amber-300 border border-amber-500/40 text-[9px] px-2 py-0.5 rounded font-bold mono">🔥 TRENDING</span>
                  <?php else: ?>
                    <span class="shrink-0 whitespace-nowrap inline-flex items-center bg-gray-800 text-gray-400 text-[9px] px-2 py-0.5 rounded font-bold mono">ACTIVE</span>
                  <?php endif; ?>
                </td>
                <td class="py-3 px-4 text-right shrink-0 whitespace-nowrap mono space-x-1">
                  <!-- Fixed COPY button -->
                  <button type="button" 
                    onclick="copyFormula(this, <?= htmlspecialchars(json_encode($p['prompt_template'])) ?>)" 
                    class="shrink-0 whitespace-nowrap inline-flex items-center justify-center px-2.5 py-1 bg-orange-950/60 hover:bg-orange-500 text-orange-400 hover:text-black font-bold rounded-lg border border-orange-500/40 transition text-xs">
                    <span class="copy-label shrink-0 whitespace-nowrap">COPY</span>
                  </button>
                  <a href="prompt_edit.php?id=<?= urlencode($p['id']) ?>" class="shrink-0 whitespace-nowrap inline-flex items-center justify-center text-cyan-400 hover:text-cyan-300 font-bold px-2.5 py-1 bg-cyan-950/50 rounded-lg border border-cyan-500/30 text-xs transition">
                    EDIT
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
</script>
</div>

</main>
</body>
</html>
