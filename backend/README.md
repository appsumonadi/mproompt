# Prompteg AI Prompt Hub - PHP Backend & Web Admin Panel

This folder contains the complete, production-ready PHP Backend and Admin Panel for the Prompteg AI mobile application.

---

## 🚀 Quick Deployment Guide

### Option 1: 1-Click Auto-Installer (Recommended)
1. Upload the entire `backend/` folder to your web hosting (e.g. `public_html/backend` or `public_html/api`).
2. Open your browser and navigate to:
   ```
   https://yourdomain.com/backend/install.php
   ```
3. Enter your MySQL database connection details (host, database name, username, password).
4. Enter your desired Admin credentials (default `admin` / `admin1234`) and your Mobile API Secret Key.
5. Click **INSTALL & INITIALIZE PROMPTEG BACKEND**. That's it!

---

### Option 2: Manual MySQL Setup (phpMyAdmin)
1. Open **phpMyAdmin** on your cPanel / web hosting.
2. Create a new MySQL database named `prompteg_db` (or any name you prefer).
3. Click **Import** and upload `backend/database.sql`.
4. Edit `backend/config.php` with your database credentials:
   ```php
   define('DB_HOST', 'localhost');
   define('DB_NAME', 'prompteg_db');
   define('DB_USER', 'your_db_username');
   define('DB_PASS', 'your_db_password');
   define('API_SECRET_KEY', 'prompteg_secret_key_2026');
   ```

---

## 📱 Connecting with the Android App

1. Open the **Prompteg** Android App.
2. Tap the top-right **Admin Security Lock** icon (or access via Settings).
3. Enter your Admin PIN (default: `1234`).
4. Switch to the **CLOUD BACKEND** tab.
5. Enter your Sync URL:
   ```
   https://yourdomain.com/backend/api/sync.php
   ```
6. Enter your **API Secret Key**:
   ```
   prompteg_secret_key_2026
   ```
7. Tap **TEST CONNECTION**. Once verified, tap **SYNC NOW FROM CLOUD BACKEND**.
8. All prompts, categories, AdMob unit IDs, and announcements from your PHP website will immediately sync to the mobile app!

---

## 🌐 Web Admin URL & Features
- **Admin Dashboard**: `https://yourdomain.com/backend/admin/index.php`
- **Prompts Vault**: Add, edit, delete prompts with dynamic `{variable}` tokens, cover images, and tags.
- **Taxonomy Categories**: Create custom prompt categories with custom color tags and banner artwork.
- **Monetization & AdMob**: Configure AdMob Banner IDs, Interstitial IDs, and frequency remotely without rebuilding the APK.
- **Announcement Bar**: Broadcast real-time announcements to all app users.
