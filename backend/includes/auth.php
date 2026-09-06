<?php
require_once __DIR__ . '/db.php';

// Start session if not started
if (session_status() === PHP_SESSION_NONE) {
    session_name(SESSION_NAME);
    session_start();
}

/**
 * Validates API key from Header (X-API-KEY or Authorization) or query param 'api_key'
 */
function validateApiKey() {
    // Handle CORS preflight OPTIONS request
    if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
        header('Access-Control-Allow-Origin: *');
        header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
        header('Access-Control-Allow-Headers: Content-Type, Authorization, X-API-KEY');
        http_response_code(200);
        exit;
    }

    $apiKey = null;
    if (isset($_SERVER['HTTP_X_API_KEY'])) {
        $apiKey = trim($_SERVER['HTTP_X_API_KEY']);
    } elseif (isset($_SERVER['HTTP_AUTHORIZATION'])) {
        $auth = trim($_SERVER['HTTP_AUTHORIZATION']);
        if (preg_match('/Bearer\s(\S+)/', $auth, $matches)) {
            $apiKey = $matches[1];
        }
    } elseif (isset($_GET['api_key'])) {
        $apiKey = trim($_GET['api_key']);
    } elseif (isset($_POST['api_key'])) {
        $apiKey = trim($_POST['api_key']);
    }

    $validKey = getSetting('api_secret_key', API_SECRET_KEY);

    // If key is empty or mismatch
    if (empty($apiKey) || $apiKey !== $validKey) {
        jsonResponse([
            'success' => false,
            'error' => 'Unauthorized. Invalid or missing X-API-KEY.',
            'code' => 401
        ], 401);
    }
}

/**
 * Checks if admin is logged into the web portal
 */
function requireAdminLogin() {
    if (empty($_SESSION['admin_logged_in']) || $_SESSION['admin_logged_in'] !== true) {
        header('Location: login.php');
        exit;
    }
}
