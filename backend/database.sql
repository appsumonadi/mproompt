-- Prompteg AI Prompt Hub - MySQL Database Schema
-- Version: 2.0.0
-- Compatible with MySQL 5.7+ / MariaDB 10.3+ / PHP 7.4 - 8.3+

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- --------------------------------------------------------
-- Table: admin_users
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `admin_users` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(64) NOT NULL UNIQUE,
  `password_hash` VARCHAR(255) NOT NULL,
  `email` VARCHAR(128) DEFAULT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Default admin user: username: admin | password: password123 (Change in admin settings!)
INSERT INTO `admin_users` (`id`, `username`, `password_hash`, `email`) 
VALUES (1, 'admin', '$2y$10$eA8gN2V01WwY6Zk6hE8Eru53kXpYf0aQj.lGlnN27O3EwS4f5Vf6q', 'admin@prompteg.ai')
ON DUPLICATE KEY UPDATE `username`=`username`;

-- --------------------------------------------------------
-- Table: categories
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `categories` (
  `id` VARCHAR(64) PRIMARY KEY,
  `name` VARCHAR(64) NOT NULL,
  `display_name` VARCHAR(128) NOT NULL,
  `description` TEXT DEFAULT NULL,
  `badge_tag` VARCHAR(32) NOT NULL DEFAULT 'NEW',
  `color_hex` VARCHAR(16) NOT NULL DEFAULT '#FF6B00',
  `accent_hex` VARCHAR(16) NOT NULL DEFAULT '#00F0FF',
  `banner_image_url` VARCHAR(512) DEFAULT NULL,
  `sort_order` INT NOT NULL DEFAULT 0,
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `categories` (`id`, `name`, `display_name`, `description`, `badge_tag`, `color_hex`, `accent_hex`, `banner_image_url`, `sort_order`, `is_active`) VALUES
('SCI_FI', 'SCI_FI', 'Sci-Fi & Cyberpunk', 'Futuristic megacities, cybernetics, holographic interfaces & neon noir', 'SCI-FI', '#FF6B00', '#00F0FF', 'https://images.unsplash.com/photo-1578632767115-351597cf2477?w=900&auto=format&fit=crop&q=80', 1, 1),
('REALISTIC', 'REALISTIC', 'Photorealistic & Raw Textures', 'Hyperrealistic 8K portraits, studio optics & Hasselblad color reproduction', '8K PHOTO', '#00F0FF', '#7000FF', 'https://images.unsplash.com/photo-1534447677768-be436bb09401?w=900&auto=format&fit=crop&q=80', 2, 1),
('ANIME', 'ANIME', 'Anime & Stylized 2D', 'Makoto Shinkai skies, Studio Ghibli watercolors & vibrant manga', 'ANIME', '#FF0055', '#FFD700', 'https://images.unsplash.com/photo-1563089145-599997674d42?w=900&auto=format&fit=crop&q=80', 3, 1),
('THREE_D', 'THREE_D', '3D Isometric & Product Renders', 'Octane clay renders, C4D glossy aesthetics & commercial packshots', '3D CLAY', '#00FF66', '#00F0FF', 'https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=900&auto=format&fit=crop&q=80', 4, 1),
('CINEMATIC', 'CINEMATIC', 'Cinematic & Film Portraits', 'Panavision 35mm anamorphic flares, Rembrandt lighting & movie scenes', 'FILM', '#FFD700', '#FF6B00', 'https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=900&auto=format&fit=crop&q=80', 5, 1),
('ART', 'ART', 'Digital Art & Surrealism', 'Matte paintings, dreamscapes, fluid dynamics & abstract concept art', 'ART', '#7000FF', '#FF0055', 'https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=900&auto=format&fit=crop&q=80', 6, 1)
ON DUPLICATE KEY UPDATE `display_name`=VALUES(`display_name`);

-- --------------------------------------------------------
-- Table: prompts
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `prompts` (
  `id` VARCHAR(64) PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT NOT NULL,
  `prompt_template` TEXT NOT NULL,
  `negative_prompt` TEXT DEFAULT NULL,
  `model_name` VARCHAR(64) NOT NULL DEFAULT 'MIDJOURNEY',
  `category_name` VARCHAR(64) NOT NULL DEFAULT 'SCI_FI',
  `custom_category_name` VARCHAR(128) DEFAULT NULL,
  `image_url` VARCHAR(512) NOT NULL,
  `parameters_json` TEXT DEFAULT NULL,
  `variables_json` TEXT DEFAULT NULL,
  `likes_count` INT NOT NULL DEFAULT 120,
  `copy_count` INT NOT NULL DEFAULT 450,
  `is_featured` TINYINT(1) NOT NULL DEFAULT 0,
  `is_trending` TINYINT(1) NOT NULL DEFAULT 0,
  `status` ENUM('active', 'draft', 'archived') NOT NULL DEFAULT 'active',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX idx_model (`model_name`),
  INDEX idx_category (`category_name`),
  INDEX idx_status (`status`),
  INDEX idx_trending (`is_trending`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Sample Cloud Prompts
INSERT INTO `prompts` (`id`, `title`, `description`, `prompt_template`, `negative_prompt`, `model_name`, `category_name`, `image_url`, `parameters_json`, `variables_json`, `likes_count`, `copy_count`, `is_featured`, `is_trending`) VALUES
('cloud_001', 'Cyberpunk Neon Ronin Infiltrator', 'Masterpiece of a robotic samurai operative moving stealthily across rain-slicked Tokyo rooftops.', 'Cinematic 8k photograph of a futuristic {character} wearing {armor_style}, standing on a rainy skyscraper rooftop overlooking Neo-Shinjuku, {lighting_mood}, volumetric neon reflections, shot on ARRI Alexa 35mm --ar 16:9 --v 6.0 --s 750', 'blurry, low resolution, bad hands, deformed anatomy, watermark, text', 'MIDJOURNEY', 'SCI_FI', 'https://images.unsplash.com/photo-1578632767115-351597cf2477?w=900&auto=format&fit=crop&q=80', '{\"--ar\":\"16:9\",\"--v\":\"6.0\",\"--s\":\"750\"}', '[{\"key\":\"character\",\"label\":\"Character\",\"defaultValue\":\"cybernetic ninja ronin\",\"options\":[\"cybernetic ninja ronin\",\"armored female netrunner\",\"covert android assassin\"]},{\"key\":\"armor_style\",\"label\":\"Armor Style\",\"defaultValue\":\"carbon-fiber matte armor with glowing turquoise conduits\",\"options\":[\"carbon-fiber matte armor with glowing turquoise conduits\",\"weathered titanium exoskeleton\",\"holographic reactive stealth cloak\"]},{\"key\":\"lighting_mood\",\"label\":\"Lighting Mood\",\"defaultValue\":\"neon magenta and cobalt rim lighting\",\"options\":[\"neon magenta and cobalt rim lighting\",\"golden twilight sun flare\",\"flickering amber street lamps\"]}]', 1420, 3890, 1, 1),
('cloud_002', 'Hyperrealistic Studio Optical Portrait', 'Ultra-detailed 85mm lens portrait highlighting intricate iris textures and soft Rembrandt illumination.', 'Extreme close-up commercial studio portrait of a {subject} with intricate eye details, natural skin texture with visible pores, {lighting_setup}, shallow depth of field, Hasselblad H6D-100c, 8k resolution, raw photo --ar 4:5 --v 6.0', 'cartoon, 3d render, plastic skin, airbrushed, oversaturated, deformed eyes', 'FLUX', 'REALISTIC', 'https://images.unsplash.com/photo-1534447677768-be436bb09401?w=900&auto=format&fit=crop&q=80', '{\"--ar\":\"4:5\",\"--guidance\":\"3.5\",\"--steps\":\"30\"}', '[{\"key\":\"subject\",\"label\":\"Subject\",\"defaultValue\":\"cybernetic fashion model with silver hair\",\"options\":[\"cybernetic fashion model with silver hair\",\"elderly weathered philosopher\",\"nordic warrior with subtle scars\"]},{\"key\":\"lighting_setup\",\"label\":\"Lighting Setup\",\"defaultValue\":\"soft octabox key light with warm edge glow\",\"options\":[\"soft octabox key light with warm edge glow\",\"dramatic high-contrast Rembrandt chiaroscuro\",\"dual cyan and amber gel lights\"]}]', 980, 2410, 1, 1),
('cloud_003', 'Makoto Shinkai Anime Cloudscape', 'Breathtaking nostalgic anime scenery with towering cumulonimbus clouds and golden hour skies.', 'Makoto Shinkai and CoMix Wave studio style anime landscape of {location}, massive sunlit cumulonimbus thunderhead clouds, shimmering turquoise lake, {atmosphere_elements}, rich pastel color palette, cinematic lighting, wallpaper 4k', 'photograph, real life, low quality, sketch, signature, cropped', 'MIDJOURNEY', 'ANIME', 'https://images.unsplash.com/photo-1563089145-599997674d42?w=900&auto=format&fit=crop&q=80', '{\"--ar\":\"16:9\",\"--niji\":\"6\"}', '[{\"key\":\"location\",\"label\":\"Location\",\"defaultValue\":\"a quiet rural railway station on a coastal hillside\",\"options\":[\"a quiet rural railway station on a coastal hillside\",\"ancient shrine floating above the clouds\",\"windy summer hill with sakura blossoms\"]},{\"key\":\"atmosphere_elements\",\"label\":\"Atmospheric Elements\",\"defaultValue\":\"golden sunbeams piercing through clouds, floating petals\",\"options\":[\"golden sunbeams piercing through clouds, floating petals\",\"gentle rainfall with colorful umbrellas\",\"glowing paper lanterns in evening dusk\"]}]', 2150, 5600, 1, 1)
ON DUPLICATE KEY UPDATE `title`=VALUES(`title`);

-- --------------------------------------------------------
-- Table: settings
-- --------------------------------------------------------
CREATE TABLE IF NOT EXISTS `settings` (
  `key_name` VARCHAR(64) PRIMARY KEY,
  `value_text` TEXT NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `settings` (`key_name`, `value_text`, `description`) VALUES
('api_secret_key', 'prompteg_secret_key_2026', 'API authentication secret key for mobile sync'),
('ads_enabled', '1', 'Enable AdMob ads globally in app (1 = enabled, 0 = disabled)'),
('banner_ad_unit_id', 'ca-app-pub-3940256099942544/6300978111', 'AdMob Banner Unit ID'),
('interstitial_ad_unit_id', 'ca-app-pub-3940256099942544/1033173712', 'AdMob Interstitial Unit ID'),
('interstitial_frequency', '3', 'Show interstitial every X copies'),
('announcement_enabled', '1', 'Show announcement banner across the app'),
('announcement_text', '🚀 Prompteg 2026 Cloud Sync Active! 500+ New Midjourney v6 & FLUX Blueprints available.', 'Global announcement broadcast message'),
('app_version', '2.0.0', 'Current cloud database revision version'),
('server_name', 'Prompteg Master Backend', 'Server label')
ON DUPLICATE KEY UPDATE `value_text`=VALUES(`value_text`);

SET FOREIGN_KEY_CHECKS = 1;
