<?php
/**
 * promptly Cloud Backend - Settings REST API
 */
require_once __DIR__ . '/../includes/auth.php';

$method = $_SERVER['REQUEST_METHOD'];
if ($method !== 'GET') {
    validateApiKey();
}

$db = getDbConnection();

switch ($method) {
    case 'GET':
        $stmt = $db->query("SELECT key_name, value_text, description FROM settings");
        $rows = $stmt->fetchAll();
        $settings = [];
        foreach ($rows as $r) {
            $settings[$r['key_name']] = $r['value_text'];
        }
        jsonResponse(['success' => true, 'settings' => $settings]);
        break;

    case 'POST':
    case 'PUT':
        $input = json_decode(file_get_contents('php://input'), true) ?: $_POST;
        foreach ($input as $key => $val) {
            if ($key !== 'api_key') {
                setSetting($key, is_bool($val) ? ($val ? '1' : '0') : (string)$val);
            }
        }
        jsonResponse(['success' => true, 'message' => 'Settings updated successfully']);
        break;

    default:
        jsonResponse(['success' => false, 'error' => 'Method not allowed'], 405);
        break;
}
