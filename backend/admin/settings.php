<?php
require_once __DIR__ . '/../includes/header.php';

$db = getDbConnection();
$message = '';
$error = '';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $action = $_POST['action'] ?? 'settings';

    if ($action === 'settings') {
        setSetting('ads_enabled', !empty($_POST['ads_enabled']) ? '1' : '0');
        setSetting('banner_ad_unit_id', trim($_POST['banner_ad_unit_id'] ?? ''));
        setSetting('interstitial_ad_unit_id', trim($_POST['interstitial_ad_unit_id'] ?? ''));
        setSetting('interstitial_frequency', (int)($_POST['interstitial_frequency'] ?? 3));
        setSetting('announcement_enabled', !empty($_POST['announcement_enabled']) ? '1' : '0');
        setSetting('announcement_text', trim($_POST['announcement_text'] ?? ''));
        setSetting('api_secret_key', trim($_POST['api_secret_key'] ?? ''));
        $message = "Cloud and AdMob settings updated successfully!";
    } elseif ($action === 'password') {
        $oldPass = trim($_POST['old_password'] ?? '');
        $newPass = trim($_POST['new_password'] ?? '');
        $confirmPass = trim($_POST['confirm_password'] ?? '');

        if (empty($newPass) || strlen($newPass) < 6) {
            $error = "New password must be at least 6 characters.";
        } elseif ($newPass !== $confirmPass) {
            $error = "Passwords do not match.";
        } else {
            $userId = $_SESSION['admin_user_id'] ?? 1;
            $stmt = $db->prepare("SELECT password_hash FROM admin_users WHERE id = ?");
            $stmt->execute([$userId]);
            $currentHash = $stmt->fetchColumn();

            if (password_verify($oldPass, $currentHash)) {
                $newHash = password_hash($newPass, PASSWORD_BCRYPT);
                $updateStmt = $db->prepare("UPDATE admin_users SET password_hash = ? WHERE id = ?");
                $updateStmt->execute([$newHash, $userId]);
                $message = "Admin password updated successfully!";
            } else {
                $error = "Current password is incorrect.";
            }
        }
    }
}

$adsEnabled = getSetting('ads_enabled', '1') === '1';
$bannerAdUnitId = getSetting('banner_ad_unit_id', 'ca-app-pub-3940256099942544/6300978111');
$interstitialAdUnitId = getSetting('interstitial_ad_unit_id', 'ca-app-pub-3940256099942544/1033173712');
$interstitialFreq = (int)getSetting('interstitial_frequency', 3);
$announcementEnabled = getSetting('announcement_enabled', '1') === '1';
$announcementText = getSetting('announcement_text', '🚀 promptly 2026 Cloud Sync Active! 500+ New Midjourney v6 & FLUX Blueprints available.');
$apiKey = getSetting('api_secret_key', API_SECRET_KEY);
?>

<div class="max-w-4xl mx-auto space-y-6">
  <div>
    <h1 class="text-2xl font-black text-white mono">APP SETTINGS & ADMOB MONETIZATION</h1>
    <p class="text-xs text-gray-400 mt-0.5">Control global application configuration, remote broadcast banners, and AdMob unit IDs.</p>
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

  <!-- Settings Form -->
  <form method="POST" class="bg-gray-900 border border-gray-800 rounded-2xl p-6 space-y-6 shadow-xl">
    <input type="hidden" name="action" value="settings">

    <!-- API Secret Key -->
    <div>
      <h2 class="text-sm font-black text-orange-400 mono uppercase mb-3">1. MOBILE APP AUTHENTICATION KEY</h2>
      <label class="block text-xs font-semibold text-gray-400 mb-1 mono">API Secret Key (Must match your Android App Admin Panel)</label>
      <input type="text" name="api_secret_key" value="<?= htmlspecialchars($apiKey) ?>" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-xs text-amber-300 outline-none font-mono" required>
    </div>

    <!-- AdMob Monetization -->
    <div class="pt-4 border-t border-gray-800">
      <h2 class="text-sm font-black text-orange-400 mono uppercase mb-3">2. GOOGLE ADMOB MONETIZATION</h2>
      
      <div class="flex items-center space-x-3 mb-4">
        <input type="checkbox" name="ads_enabled" id="ads_enabled" value="1" <?= $adsEnabled ? 'checked' : '' ?> class="w-5 h-5 accent-orange-500">
        <label for="ads_enabled" class="text-xs text-gray-200 font-bold mono">Enable Mobile Ads (AdMob Banner + Interstitial)</label>
      </div>

      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-xs font-semibold text-gray-400 mb-1 mono">AdMob Banner Unit ID</label>
          <input type="text" name="banner_ad_unit_id" value="<?= htmlspecialchars($bannerAdUnitId) ?>" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-xs text-gray-200 font-mono">
        </div>

        <div>
          <label class="block text-xs font-semibold text-gray-400 mb-1 mono">AdMob Interstitial Unit ID</label>
          <input type="text" name="interstitial_ad_unit_id" value="<?= htmlspecialchars($interstitialAdUnitId) ?>" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2.5 text-xs text-gray-200 font-mono">
        </div>
      </div>

      <div class="mt-4">
        <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Interstitial Ad Frequency (Show every X copies)</label>
        <input type="number" name="interstitial_frequency" value="<?= $interstitialFreq ?>" min="1" max="20" class="w-full max-w-xs bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-2 text-xs text-gray-200 font-mono">
      </div>
    </div>

    <!-- Announcement Banner -->
    <div class="pt-4 border-t border-gray-800">
      <h2 class="text-sm font-black text-orange-400 mono uppercase mb-3">3. GLOBAL ANNOUNCEMENT BROADCAST</h2>

      <div class="flex items-center space-x-3 mb-3">
        <input type="checkbox" name="announcement_enabled" id="announcement_enabled" value="1" <?= $announcementEnabled ? 'checked' : '' ?> class="w-5 h-5 accent-cyan-500">
        <label for="announcement_enabled" class="text-xs text-cyan-300 font-bold mono">Display Announcement Banner in App Feed</label>
      </div>

      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Announcement Message</label>
        <textarea name="announcement_text" rows="2" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl p-3 text-xs text-gray-200 font-mono"><?= htmlspecialchars($announcementText) ?></textarea>
      </div>
    </div>

    <button type="submit" class="w-full bg-orange-500 hover:bg-orange-600 text-black font-black py-3.5 rounded-xl text-xs transition tracking-wider mono shadow-lg shadow-orange-500/20">
      SAVE CLOUD CONFIGURATION
    </button>
  </form>

  <!-- Change Password Form -->
  <form method="POST" class="bg-gray-900 border border-gray-800 rounded-2xl p-6 space-y-4 shadow-xl">
    <input type="hidden" name="action" value="password">
    <h2 class="text-sm font-black text-orange-400 mono uppercase">4. CHANGE ADMIN PASSWORD</h2>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-3">
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Current Password</label>
        <input type="password" name="old_password" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none" required>
      </div>
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1 mono">New Password</label>
        <input type="password" name="new_password" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none" required>
      </div>
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1 mono">Confirm New Password</label>
        <input type="password" name="confirm_password" class="w-full bg-gray-950 border border-gray-800 rounded-xl px-3 py-2 text-xs text-gray-100 outline-none" required>
      </div>
    </div>

    <button type="submit" class="bg-gray-800 hover:bg-gray-700 text-cyan-400 border border-cyan-500/30 font-bold px-4 py-2.5 rounded-xl text-xs transition mono">
      UPDATE PASSWORD
    </button>
  </form>
</div>

</main>
</body>
</html>
