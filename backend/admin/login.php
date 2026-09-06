<?php
require_once __DIR__ . '/../includes/auth.php';

$error = '';

if (!empty($_SESSION['admin_logged_in'])) {
    header('Location: index.php');
    exit;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = trim($_POST['username'] ?? '');
    $password = trim($_POST['password'] ?? '');

    try {
        $db = getDbConnection();
        $stmt = $db->prepare("SELECT * FROM admin_users WHERE username = ? LIMIT 1");
        $stmt->execute([$username]);
        $user = $stmt->fetch();

        if ($user && password_verify($password, $user['password_hash'])) {
            $_SESSION['admin_logged_in'] = true;
            $_SESSION['admin_user_id'] = $user['id'];
            $_SESSION['admin_user'] = $user['username'];
            header('Location: index.php');
            exit;
        } else {
            $error = 'Invalid username or password. Please try again.';
        }
    } catch (Exception $e) {
        $error = 'Authentication error: ' . $e->getMessage();
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login // promptly Admin Hub</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;600;800&display=swap" rel="stylesheet">
  <style>body { font-family: 'JetBrains Mono', monospace; }</style>
</head>
<body class="bg-gray-950 text-gray-100 min-h-screen flex items-center justify-center p-4">
  <div class="max-w-md w-full bg-gray-900 border border-orange-500/40 rounded-2xl p-8 shadow-2xl shadow-orange-950/40">
    <div class="text-center mb-8">
      <div class="w-14 h-14 rounded-2xl bg-orange-600 flex items-center justify-center text-black font-black text-2xl mx-auto mb-3 shadow-lg shadow-orange-600/40">P</div>
      <h1 class="text-xl font-black text-orange-500 tracking-wider">promptly ADMIN</h1>
      <p class="text-xs text-gray-400 mt-1">Master Backend & Cloud Prompt Controller</p>
    </div>

    <?php if ($error): ?>
      <div class="bg-rose-950/80 border border-rose-500/50 text-rose-300 text-xs p-3.5 rounded-xl mb-6">
        <?= htmlspecialchars($error) ?>
      </div>
    <?php endif; ?>

    <form method="POST" class="space-y-4">
      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase">Admin Username</label>
        <input type="text" name="username" value="admin" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-3 text-sm text-gray-100 outline-none transition" required autofocus>
      </div>

      <div>
        <label class="block text-xs font-semibold text-gray-400 mb-1.5 uppercase">Admin Password</label>
        <input type="password" name="password" placeholder="••••••••" class="w-full bg-gray-950 border border-gray-800 focus:border-orange-500 rounded-xl px-4 py-3 text-sm text-gray-100 outline-none transition" required>
      </div>

      <button type="submit" class="w-full bg-orange-500 hover:bg-orange-600 text-black font-extrabold py-3.5 rounded-xl text-sm transition tracking-wider mt-2">
        AUTHENTICATE & ENTER
      </button>
    </form>

    <div class="mt-8 pt-4 border-t border-gray-800 text-center">
      <p class="text-[11px] text-gray-500">Need first time setup? <a href="../install.php" class="text-cyan-400 hover:underline">Run 1-Click Installer</a></p>
    </div>
  </div>
</body>
</html>
