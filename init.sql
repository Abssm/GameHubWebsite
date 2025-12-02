-- Create Database
CREATE DATABASE IF NOT EXISTS gamehub;
USE gamehub;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_admin BOOLEAN DEFAULT FALSE,
    is_banned BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- User Wishlist Table
CREATE TABLE IF NOT EXISTS user_wishlist (
    user_id BIGINT NOT NULL,
    game_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, game_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Games Table
CREATE TABLE IF NOT EXISTS games (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT,
    category VARCHAR(100) NOT NULL,
    price_usd DOUBLE NOT NULL,
    price_sar DOUBLE,
    image_url VARCHAR(500),
    release_date VARCHAR(50),
    developer VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    rating VARCHAR(10) DEFAULT '0/10',
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX (category),
    INDEX (is_available)
);

-- Game Platforms Table
CREATE TABLE IF NOT EXISTS game_platforms (
    game_id BIGINT NOT NULL,
    platform VARCHAR(100),
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);

-- Game Tags Table
CREATE TABLE IF NOT EXISTS game_tags (
    game_id BIGINT NOT NULL,
    tag VARCHAR(100),
    FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);

-- Orders Table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id VARCHAR(100) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL,
    total_amount DOUBLE NOT NULL,
    tax_amount DOUBLE,
    subtotal_amount DOUBLE,
    status VARCHAR(50) DEFAULT 'COMPLETED',
    payment_method VARCHAR(100),
    game_ids LONGTEXT,
    game_keys LONGTEXT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX (order_id),
    INDEX (user_id)
);

-- Digital Codes Table
CREATE TABLE IF NOT EXISTS digital_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT,
    category VARCHAR(100) NOT NULL,
    price_usd DOUBLE NOT NULL,
    price_sar DOUBLE,
    image_url VARCHAR(500),
    code_type VARCHAR(100),
    duration_days INT,
    platform VARCHAR(100),
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert Sample Data
-- Password: password (for both admin@gamehub.com and demo@gamehub.com)
-- BCrypt Hash: $2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/KFm
INSERT INTO users (email, password, name, is_admin) VALUES 
('admin@gamehub.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/KFm', 'Admin User', TRUE),
('demo@gamehub.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcg7b3XeKeUxWdeS86E36P4/KFm', 'Demo User', FALSE);

-- Insert Sample Games
INSERT INTO games (title, description, category, price_usd, price_sar, image_url, release_date, developer, publisher, rating, is_available) VALUES
('Counter-Strike 2', 'Counter-Strike 2 is the largest technical leap forward in Counter-Strikes history, ensuring new features and updates for years to come.', 'FPS', 0.00, 0.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/730/header.jpg', 'August 21, 2012', 'Valve', 'Valve', '9/10', TRUE),
('Call of Duty: Modern Warfare II', 'Call of Duty: Modern Warfare II drops players into an unprecedented global conflict.', 'FPS', 69.99, 262.46, 'https://cdn.cloudflare.steamstatic.com/steam/apps/1938090/header.jpg', 'October 28, 2022', 'Infinity Ward', 'Activision', '8/10', TRUE),
('Grand Theft Auto V', 'Grand Theft Auto V - the most anticipated game of the year.', 'Action-Adventure', 59.99, 225.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg', 'April 20, 2018', 'Rockstar North', 'Rockstar Games', '10/10', TRUE),
('Red Dead Redemption 2', 'Winner of over 175 Game of the Year Awards and recipient of over 250 perfect scores.', 'Action-Adventure', 59.99, 225.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/1174180/header.jpg', 'November 5, 2019', 'Rockstar Studios', 'Rockstar Games', '10/10', TRUE),
('The Elder Scrolls V: Skyrim', 'The Elder Scrolls V: Skyrim is an epic fantasy open world RPG.', 'RPG', 59.99, 225.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/72850/header.jpg', 'November 11, 2011', 'Bethesda Game Studios', 'Bethesda Softworks', '9/10', TRUE),
('Elden Ring', 'Rise, Tarnished, and let grace guide you in your new adventure in the Lands Between.', 'RPG', 59.99, 225.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/570940/header.jpg', 'February 25, 2022', 'FromSoftware', 'Bandai Namco Entertainment', '9/10', TRUE),
('Fortnite', 'A battle royale game where 100 players fight to be the last one standing.', 'Battle Royale', 0.00, 0.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/1286410/header.jpg', 'July 25, 2018', 'Epic Games', 'Epic Games', '8/10', TRUE),
('Valorant', 'A team-based tactical shooter set in the future.', 'FPS', 0.00, 0.00, 'https://cdn2.unrealengine.com/Unreal+Engine%2Fblog%2Fvacc-and-changes-to-how-we-tackle-cheating-in-valorant-and-league-of-legends%2FHubSpot_Blog_Feature_Image-1920x1080-1920x1080-432451661.jpg', 'June 2, 2020', 'Riot Games', 'Riot Games', '8/10', TRUE);
