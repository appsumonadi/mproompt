<?php
/**
 * promptly AI Prompt Hub - Central Configuration
 * Edit this file with your MySQL database credentials.
 */

// Database Credentials
define('DB_HOST', 'localhost');
define('DB_PORT', '3306');
define('DB_NAME', 'promptly_db');
define('DB_USER', 'root');
define('DB_PASS', '');

// Security & API Settings
// This secret key must match the one entered in the Android App Admin Panel
define('API_SECRET_KEY', 'promptly_secret_key_2026');

// Session Settings
define('SESSION_NAME', 'promptly_admin_session');
define('ADMIN_TITLE', 'promptly AI // MASTER CLOUD ADMIN');

// App Info
define('APP_VERSION', '2.0.0');

// Environment (production / development)
define('APP_ENV', 'development');

if (APP_ENV === 'development') {
    ini_set('display_errors', 1);
    error_reporting(E_ALL);
} else {
    ini_set('display_errors', 0);
    error_reporting(0);
}
