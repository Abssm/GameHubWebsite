-- Insert default admin user
INSERT INTO users (email, password, full_name, is_admin, is_banned, total_spent, join_date) 
VALUES ('admin@gamehub.com', '$2a$10$YourHashedPasswordHere', 'Admin User', true, false, 0.0, NOW());

-- Insert default demo user
INSERT INTO users (email, password, full_name, is_admin, is_banned, total_spent, join_date) 
VALUES ('demo@gamehub.com', '$2a$10$YourHashedPasswordHere', 'Demo User', false, false, 0.0, NOW());

-- Insert sample games
INSERT INTO games (title, category, price_usd, price_sar, image_url, description, developer, publisher, rating, featured) 
VALUES 
('Counter-Strike 2', 'FPS', 0.00, 0.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/730/header.jpg', 
 'Counter-Strike 2 is the largest technical leap forward...', 'Valve', 'Valve', '9/10', true),
 
('Call of Duty: Modern Warfare II', 'FPS', 69.99, 262.46, 'https://cdn.cloudflare.steamstatic.com/steam/apps/1938090/header.jpg', 
 'Call of Duty: Modern Warfare II drops players into an unprecedented global conflict...', 'Infinity Ward', 'Activision', '8/10', true),
 
('Grand Theft Auto V', 'Action-Adventure', 59.99, 225.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/271590/header.jpg', 
 'Get the complete Grand Theft Auto V experience...', 'Rockstar North', 'Rockstar Games', '10/10', true),
 
('Red Dead Redemption 2', 'Action-Adventure', 59.99, 225.00, 'https://cdn.cloudflare.steamstatic.com/steam/apps/1174180/header.jpg', 
 'Winner of over 175 Game of the Year Awards...', 'Rockstar Studios', 'Rockstar Games', '10/10', false);