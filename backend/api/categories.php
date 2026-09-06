<?php
/**
 * promptly Cloud Backend - Categories REST API
 */
require_once __DIR__ . '/../includes/auth.php';

$method = $_SERVER['REQUEST_METHOD'];
if ($method !== 'GET') {
    validateApiKey();
}

$db = getDbConnection();

switch ($method) {
    case 'GET':
        $stmt = $db->query("SELECT * FROM categories WHERE is_active = 1 ORDER BY sort_order ASC, display_name ASC");
        $categories = $stmt->fetchAll();
        jsonResponse(['success' => true, 'categories' => $categories]);
        break;

    case 'POST':
        $input = json_decode(file_get_contents('php://input'), true) ?: $_POST;
        $id = $input['id'] ?? ('cat_' . uniqid());
        $displayName = trim($input['displayName'] ?? $input['display_name'] ?? '');
        
        if (empty($displayName)) {
            jsonResponse(['success' => false, 'error' => 'Display name is required'], 400);
        }

        $stmt = $db->prepare("
            INSERT INTO categories (id, name, display_name, description, badge_tag, color_hex, accent_hex, banner_image_url, sort_order, is_active)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1)
            ON DUPLICATE KEY UPDATE display_name = VALUES(display_name), description = VALUES(description), color_hex = VALUES(color_hex)
        ");
        $stmt->execute([
            $id,
            $input['name'] ?? $id,
            $displayName,
            $input['description'] ?? '',
            strtoupper($input['badgeTag'] ?? $input['badge_tag'] ?? 'NEW'),
            $input['colorHex'] ?? $input['color_hex'] ?? '#FF6B00',
            $input['accentHex'] ?? $input['accent_hex'] ?? '#00F0FF',
            $input['bannerImageUrl'] ?? $input['banner_image_url'] ?? '',
            (int)($input['sortOrder'] ?? $input['sort_order'] ?? 0)
        ]);

        jsonResponse(['success' => true, 'id' => $id, 'message' => 'Category saved successfully']);
        break;

    case 'DELETE':
        $id = $_GET['id'] ?? null;
        if (!$id) {
            jsonResponse(['success' => false, 'error' => 'Category ID is required'], 400);
        }
        $stmt = $db->prepare("DELETE FROM categories WHERE id = ?");
        $stmt->execute([$id]);
        jsonResponse(['success' => true, 'message' => 'Category deleted']);
        break;

    default:
        jsonResponse(['success' => false, 'error' => 'Method not allowed'], 405);
        break;
}
