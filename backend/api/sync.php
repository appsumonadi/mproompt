<?php
/**
 * promptly Cloud Backend - Full Mobile App Sync Endpoint
 * Returns all active categories, prompts, and global settings for the mobile app.
 */
require_once __DIR__ . '/../includes/auth.php';

// Validate API Key if enabled
validateApiKey();

try {
    $db = getDbConnection();

    // 1. Fetch Categories
    $catStmt = $db->query("SELECT * FROM categories WHERE is_active = 1 ORDER BY sort_order ASC, created_at ASC");
    $categories = $catStmt->fetchAll();

    // 2. Fetch Active Prompts
    $promptStmt = $db->query("SELECT * FROM prompts WHERE status = 'active' ORDER BY is_trending DESC, is_featured DESC, created_at DESC");
    $promptsRaw = $promptStmt->fetchAll();

    $prompts = [];
    foreach ($promptsRaw as $p) {
        $params = json_decode($p['parameters_json'], true) ?: [];
        $variables = json_decode($p['variables_json'], true) ?: [];

        $prompts[] = [
            'id' => $p['id'],
            'title' => $p['title'],
            'description' => $p['description'],
            'promptTemplate' => $p['prompt_template'],
            'negativePrompt' => $p['negative_prompt'] ?: '',
            'model' => $p['model_name'],
            'category' => $p['category_name'],
            'customCategoryName' => $p['custom_category_name'] ?: '',
            'imageUrl' => $p['image_url'],
            'parameters' => $params,
            'variables' => $variables,
            'likesCount' => (int)$p['likes_count'],
            'copyCount' => (int)$p['copy_count'],
            'isFeatured' => (bool)$p['is_featured'],
            'isTrending' => (bool)$p['is_trending'],
            'createdAt' => strtotime($p['created_at']) * 1000
        ];
    }

    // 3. Fetch Settings
    $settingsStmt = $db->query("SELECT key_name, value_text FROM settings");
    $settingsRows = $settingsStmt->fetchAll();
    $settings = [];
    foreach ($settingsRows as $s) {
        $settings[$s['key_name']] = $s['value_text'];
    }

    jsonResponse([
        'success' => true,
        'syncTimestamp' => time() * 1000,
        'version' => APP_VERSION,
        'counts' => [
            'categories' => count($categories),
            'prompts' => count($prompts)
        ],
        'settings' => [
            'adsEnabled' => ($settings['ads_enabled'] ?? '1') === '1',
            'bannerAdUnitId' => $settings['banner_ad_unit_id'] ?? 'ca-app-pub-3940256099942544/6300978111',
            'interstitialAdUnitId' => $settings['interstitial_ad_unit_id'] ?? 'ca-app-pub-3940256099942544/1033173712',
            'interstitialFrequency' => (int)($settings['interstitial_frequency'] ?? 3),
            'announcementEnabled' => ($settings['announcement_enabled'] ?? '0') === '1',
            'announcementText' => $settings['announcement_text'] ?? ''
        ],
        'categories' => $categories,
        'prompts' => $prompts
    ]);
} catch (Exception $e) {
    jsonResponse([
        'success' => false,
        'error' => $e->getMessage()
    ], 500);
}
