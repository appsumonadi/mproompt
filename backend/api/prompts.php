<?php
/**
 * promptly Cloud Backend - Prompts REST API (GET, POST, PUT, DELETE)
 */
require_once __DIR__ . '/../includes/auth.php';

$method = $_SERVER['REQUEST_METHOD'];

// Public GET can be allowed without key, or with key for secured access
if ($method !== 'GET') {
    validateApiKey();
}

$db = getDbConnection();

switch ($method) {
    case 'GET':
        // Handle single prompt or filtered list
        if (isset($_GET['id'])) {
            $stmt = $db->prepare("SELECT * FROM prompts WHERE id = ?");
            $stmt->execute([$_GET['id']]);
            $prompt = $stmt->fetch();
            if (!$prompt) {
                jsonResponse(['success' => false, 'error' => 'Prompt not found'], 404);
            }
            $prompt['parameters'] = json_decode($prompt['parameters_json'], true) ?: [];
            $prompt['variables'] = json_decode($prompt['variables_json'], true) ?: [];
            jsonResponse(['success' => true, 'prompt' => $prompt]);
        }

        // List with search and filtering
        $query = "SELECT * FROM prompts WHERE status = 'active'";
        $params = [];

        if (!empty($_GET['category'])) {
            $query .= " AND category_name = ?";
            $params[] = $_GET['category'];
        }

        if (!empty($_GET['model'])) {
            $query .= " AND model_name = ?";
            $params[] = $_GET['model'];
        }

        if (!empty($_GET['trending']) && $_GET['trending'] === '1') {
            $query .= " AND is_trending = 1";
        }

        if (!empty($_GET['search'])) {
            $query .= " AND (title LIKE ? OR description LIKE ? OR prompt_template LIKE ?)";
            $term = '%' . trim($_GET['search']) . '%';
            $params[] = $term;
            $params[] = $term;
            $params[] = $term;
        }

        $query .= " ORDER BY is_trending DESC, created_at DESC";

        $limit = isset($_GET['limit']) ? max(1, min(100, (int)$_GET['limit'])) : 50;
        $offset = isset($_GET['offset']) ? max(0, (int)$_GET['offset']) : 0;
        $query .= " LIMIT $limit OFFSET $offset";

        $stmt = $db->prepare($query);
        $stmt->execute($params);
        $rows = $stmt->fetchAll();

        $prompts = [];
        foreach ($rows as $p) {
            $p['parameters'] = json_decode($p['parameters_json'], true) ?: [];
            $p['variables'] = json_decode($p['variables_json'], true) ?: [];
            $prompts[] = $p;
        }

        jsonResponse([
            'success' => true,
            'count' => count($prompts),
            'prompts' => $prompts
        ]);
        break;

    case 'POST':
        // Create prompt
        $input = json_decode(file_get_contents('php://input'), true) ?: $_POST;
        
        $title = trim($input['title'] ?? '');
        $promptTemplate = trim($input['promptTemplate'] ?? $input['prompt_template'] ?? '');
        
        if (empty($title) || empty($promptTemplate)) {
            jsonResponse(['success' => false, 'error' => 'Title and prompt template are required'], 400);
        }

        $id = $input['id'] ?? ('prompt_' . uniqid());
        $desc = trim($input['description'] ?? '');
        $neg = trim($input['negativePrompt'] ?? $input['negative_prompt'] ?? '');
        $model = strtoupper(trim($input['model'] ?? $input['model_name'] ?? 'MIDJOURNEY'));
        $cat = strtoupper(trim($input['category'] ?? $input['category_name'] ?? 'SCI_FI'));
        $customCat = trim($input['customCategoryName'] ?? $input['custom_category_name'] ?? '');
        $img = trim($input['imageUrl'] ?? $input['image_url'] ?? 'https://images.unsplash.com/photo-1578632767115-351597cf2477?w=900&auto=format&fit=crop&q=80');
        
        $paramsJson = is_array($input['parameters'] ?? null) ? json_encode($input['parameters']) : ($input['parameters_json'] ?? '{}');
        $varsJson = is_array($input['variables'] ?? null) ? json_encode($input['variables']) : ($input['variables_json'] ?? '[]');

        $stmt = $db->prepare("
            INSERT INTO prompts (id, title, description, prompt_template, negative_prompt, model_name, category_name, custom_category_name, image_url, parameters_json, variables_json, likes_count, copy_count, is_featured, is_trending, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        ");
        $stmt->execute([
            $id, $title, $desc, $promptTemplate, $neg, $model, $cat, $customCat, $img, $paramsJson, $varsJson,
            (int)($input['likesCount'] ?? 100),
            (int)($input['copyCount'] ?? 200),
            !empty($input['isFeatured']) ? 1 : 0,
            !empty($input['isTrending']) ? 1 : 0,
            $input['status'] ?? 'active'
        ]);

        jsonResponse(['success' => true, 'id' => $id, 'message' => 'Prompt created successfully'], 201);
        break;

    case 'PUT':
    case 'PATCH':
        // Update prompt
        $input = json_decode(file_get_contents('php://input'), true);
        $id = $_GET['id'] ?? ($input['id'] ?? null);

        if (!$id) {
            jsonResponse(['success' => false, 'error' => 'Prompt ID is required for update'], 400);
        }

        $fields = [];
        $params = [];

        if (isset($input['title'])) { $fields[] = "title = ?"; $params[] = $input['title']; }
        if (isset($input['description'])) { $fields[] = "description = ?"; $params[] = $input['description']; }
        if (isset($input['promptTemplate']) || isset($input['prompt_template'])) {
            $fields[] = "prompt_template = ?";
            $params[] = $input['promptTemplate'] ?? $input['prompt_template'];
        }
        if (isset($input['negativePrompt']) || isset($input['negative_prompt'])) {
            $fields[] = "negative_prompt = ?";
            $params[] = $input['negativePrompt'] ?? $input['negative_prompt'];
        }
        if (isset($input['model']) || isset($input['model_name'])) {
            $fields[] = "model_name = ?";
            $params[] = strtoupper($input['model'] ?? $input['model_name']);
        }
        if (isset($input['category']) || isset($input['category_name'])) {
            $fields[] = "category_name = ?";
            $params[] = strtoupper($input['category'] ?? $input['category_name']);
        }
        if (isset($input['imageUrl']) || isset($input['image_url'])) {
            $fields[] = "image_url = ?";
            $params[] = $input['imageUrl'] ?? $input['image_url'];
        }
        if (isset($input['isFeatured'])) { $fields[] = "is_featured = ?"; $params[] = $input['isFeatured'] ? 1 : 0; }
        if (isset($input['isTrending'])) { $fields[] = "is_trending = ?"; $params[] = $input['isTrending'] ? 1 : 0; }
        if (isset($input['status'])) { $fields[] = "status = ?"; $params[] = $input['status']; }

        if (empty($fields)) {
            jsonResponse(['success' => false, 'error' => 'No fields provided to update'], 400);
        }

        $params[] = $id;
        $sql = "UPDATE prompts SET " . implode(", ", $fields) . " WHERE id = ?";
        $stmt = $db->prepare($sql);
        $stmt->execute($params);

        jsonResponse(['success' => true, 'message' => 'Prompt updated successfully']);
        break;

    case 'DELETE':
        $id = $_GET['id'] ?? null;
        if (!$id) {
            jsonResponse(['success' => false, 'error' => 'Prompt ID is required for deletion'], 400);
        }
        $stmt = $db->prepare("DELETE FROM prompts WHERE id = ?");
        $stmt->execute([$id]);
        jsonResponse(['success' => true, 'message' => 'Prompt deleted successfully']);
        break;

    default:
        jsonResponse(['success' => false, 'error' => 'Method not supported'], 405);
        break;
}
