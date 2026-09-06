<?php
/**
 * Prompteg Cloud Backend - Ping & Health Check Endpoint
 */
require_once __DIR__ . '/../includes/db.php';

header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type, Authorization, X-API-KEY');

if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit;
}

try {
    $db = getDbConnection();
    $stmt = $db->query("SELECT COUNT(*) as count FROM prompts WHERE status = 'active'");
    $promptsCount = $stmt->fetch()['count'];

    $catStmt = $db->query("SELECT COUNT(*) as count FROM categories WHERE is_active = 1");
    $categoriesCount = $catStmt->fetch()['count'];

    jsonResponse([
        'success' => true,
        'status' => 'ONLINE',
        'server_time' => time(),
        'server_datetime' => date('Y-m-d H:i:s'),
        'version' => APP_VERSION,
        'message' => 'Prompteg Master Cloud Backend is operational and reachable!',
        'stats' => [
            'active_prompts' => (int)$promptsCount,
            'active_categories' => (int)$categoriesCount
        ]
    ]);
} catch (Exception $e) {
    jsonResponse([
        'success' => false,
        'status' => 'ERROR',
        'error' => $e->getMessage()
    ], 500);
}
