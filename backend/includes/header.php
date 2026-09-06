<?php
require_once __DIR__ . '/auth.php';
requireAdminLogin();

$currentPage = basename($_SERVER['PHP_SELF']);
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><?= ADMIN_TITLE ?></title>
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@400;600;800&family=Plus+Jakarta+Sans:wght@400;600;800&display=swap" rel="stylesheet">
  <style>
    body { font-family: 'Plus Jakarta Sans', sans-serif; }
    .mono { font-family: 'JetBrains Mono', monospace; }
    /* Universal Button & Badge Protection Rules */
    .badge-lock {
      flex-shrink: 0 !important;
      white-space: nowrap !important;
    }
    .btn-lock {
      flex-shrink: 0 !important;
      white-space: nowrap !important;
    }
    .text-truncate-safe {
      min-width: 0 !important;
      overflow: hidden !important;
      text-overflow: ellipsis !important;
      white-space: nowrap !important;
    }
  </style>
</head>
<body class="bg-gray-950 text-gray-100 min-h-screen flex flex-col">
  <!-- Top Navigation Bar -->
  <header class="bg-gray-900 border-b border-gray-800 sticky top-0 z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between gap-4">
      <div class="flex items-center space-x-3 shrink-0">
        <a href="index.php" class="flex items-center space-x-3 shrink-0">
          <div class="w-9 h-9 rounded-xl bg-orange-600 flex items-center justify-center text-black font-black text-lg shadow-lg shadow-orange-600/30 shrink-0">P</div>
          <div class="shrink-0">
            <div class="flex items-center space-x-2">
              <span class="text-base font-extrabold text-orange-500 tracking-wider mono shrink-0 whitespace-nowrap">promptly</span>
              <span class="text-[10px] font-extrabold bg-emerald-500/20 text-emerald-400 border border-emerald-500/30 px-1.5 py-0.5 rounded mono shrink-0 whitespace-nowrap">ADMIN</span>
            </div>
            <p class="text-[10px] text-gray-400 mono shrink-0 whitespace-nowrap">CLOUD MASTER HUB v<?= APP_VERSION ?></p>
          </div>
        </a>
      </div>

      <!-- Navigation Links -->
      <nav class="hidden md:flex items-center space-x-1 mono text-xs shrink-0">
        <a href="index.php" class="shrink-0 whitespace-nowrap px-3 py-2 rounded-lg <?= $currentPage === 'index.php' ? 'bg-orange-500/20 text-orange-400 border border-orange-500/40 font-bold' : 'text-gray-400 hover:text-gray-200 hover:bg-gray-800' ?>">
          📊 DASHBOARD
        </a>
        <a href="prompts.php" class="shrink-0 whitespace-nowrap px-3 py-2 rounded-lg <?= $currentPage === 'prompts.php' || $currentPage === 'prompt_edit.php' ? 'bg-orange-500/20 text-orange-400 border border-orange-500/40 font-bold' : 'text-gray-400 hover:text-gray-200 hover:bg-gray-800' ?>">
          ⚡ PROMPTS VAULT
        </a>
        <a href="categories.php" class="shrink-0 whitespace-nowrap px-3 py-2 rounded-lg <?= $currentPage === 'categories.php' ? 'bg-orange-500/20 text-orange-400 border border-orange-500/40 font-bold' : 'text-gray-400 hover:text-gray-200 hover:bg-gray-800' ?>">
          🏷️ CATEGORIES
        </a>
        <a href="settings.php" class="shrink-0 whitespace-nowrap px-3 py-2 rounded-lg <?= $currentPage === 'settings.php' ? 'bg-orange-500/20 text-orange-400 border border-orange-500/40 font-bold' : 'text-gray-400 hover:text-gray-200 hover:bg-gray-800' ?>">
          ⚙️ SETTINGS & ADMOB
        </a>
      </nav>

      <!-- User & Logout -->
      <div class="flex items-center space-x-3 shrink-0">
        <span class="text-xs text-gray-400 mono hidden sm:inline shrink-0 whitespace-nowrap">User: <strong class="text-gray-200"><?= htmlspecialchars($_SESSION['admin_user'] ?? 'admin') ?></strong></span>
        <a href="logout.php" class="shrink-0 whitespace-nowrap bg-rose-950/60 hover:bg-rose-900 border border-rose-600/40 text-rose-400 text-xs font-bold px-3 py-1.5 rounded-lg transition mono">
          LOGOUT
        </a>
      </div>
    </div>
  </header>

  <!-- Main Body Content -->
  <main class="flex-1 max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
