-- Database: UrlShortener
-- Подготовка пользователей
INSERT INTO users (email, password, role, username)
SELECT 'admin@gmail.com', '1', 'ROLE_ADMIN', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM users);

INSERT INTO users (email, password, role, username)
SELECT 'first@gmail.com', '1', 'ROLE_REGISTERED', 'first'
WHERE NOT EXISTS (SELECT 1 FROM users);

-- Подготовка цены
INSERT INTO prices (creation_date, price) VALUES (CURRENT_DATE, 10.0);
