<?php
require_once __DIR__ . '/../config.php';

/**
 * Returns a singleton PDO instance with UTF-8 support
 */
function getDbConnection() {
    static $pdo = null;
    if ($pdo === null) {
        $dsn = "mysql:host=" . DB_HOST . ";port=" . DB_PORT . ";dbname=" . DB_NAME . ";charset=utf8mb4";
        $options = [
            PDO::ATTR_ERRMODE            => PDO::ERRMODE_EXCEPTION,
            PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
            PDO::ATTR_EMULATE_PREPARES   => false,
        ];
        try {
            $pdo = new PDO($dsn, DB_USER, DB_PASS, $options);
        } catch (PDOException $e) {
            http_response_code(500);
            header('Content-Type: application/json; charset=utf-8');
            echo json_encode([
                'success' => false,
                'error' => 'Database connection failed: ' . $e->getMessage()
            ]);
            exit;
        }
    }
    return $pdo;
}

/**
 * JSON Response helper
 */
function jsonResponse($data, $statusCode = 200) {
    http_response_code($statusCode);
    header('Content-Type: application/json; charset=utf-8');
    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
    header('Access-Control-Allow-Headers: Content-Type, Authorization, X-API-KEY');
    echo json_encode($data, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
    exit;
}

/**
 * Retrieve setting value by key
 */
function getSetting($key, $default = null) {
    $db = getDbConnection();
    $stmt = $db->prepare("SELECT value_text FROM settings WHERE key_name = ? LIMIT 1");
    $stmt->execute([$key]);
    $row = $stmt->fetch();
    return $row ? $row['value_text'] : $default;
}

/**
 * Update or insert setting value
 */
function setSetting($key, $value, $description = null) {
    $db = getDbConnection();
    $stmt = $db->prepare("
        INSERT INTO settings (key_name, value_text, description, updated_at) 
        VALUES (?, ?, ?, NOW()) 
        ON DUPLICATE KEY UPDATE value_text = VALUES(value_text), updated_at = NOW()
    ");
    return $stmt->execute([$key, $value, $description]);
}
