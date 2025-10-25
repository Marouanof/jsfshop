-- Désactiver les contraintes FK
SET FOREIGN_KEY_CHECKS = 0;

-- Vider les tables dans l'ordre enfant -> parent
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM cart_items;
DELETE FROM carts;
DELETE FROM products;
DELETE FROM categories;
DELETE FROM users;

-- Réactiver les contraintes
SET FOREIGN_KEY_CHECKS = 1;

-- Réinitialiser les auto-increments
ALTER TABLE products AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;

-- Insérer les catégories
INSERT INTO categories (nom) VALUES ('Smartphones');
INSERT INTO categories (nom) VALUES ('Ordinateurs');
INSERT INTO categories (nom) VALUES ('Tablettes');
INSERT INTO categories (nom) VALUES ('Accessoires');
INSERT INTO categories (nom) VALUES ('Écouteurs');

-- Insérer les produits (UN SEUL INSERT par produit)
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('iPhone 15 Pro', 'Smartphone Apple avec écran Super Retina XDR', 1299.99, 25, 1);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('Samsung Galaxy S24', 'Smartphone Android avec appareil photo 200MP', 999.99, 30, 1);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('Google Pixel 8', 'Smartphone Google avec AI intégrée', 799.99, 20, 1);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('MacBook Pro 14"', 'Ordinateur portable Apple M3 Pro', 2399.99, 15, 2);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('Dell XPS 13', 'Ultrabook Windows performant', 1199.99, 18, 2);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('HP Spectre x360', 'PC convertible haut de gamme', 1349.99, 12, 2);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('iPad Air', 'Tablette Apple avec puce M1', 749.99, 22, 3);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('Samsung Galaxy Tab S9', 'Tablette Android avec S-Pen', 899.99, 16, 3);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('AirPods Pro', 'Écouteurs sans fil avec réduction de bruit', 279.99, 50, 5);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('Sony WH-1000XM5', 'Casque audio avec ANC', 399.99, 35, 5);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('Chargeur USB-C 30W', 'Chargeur rapide compatible universel', 29.99, 100, 4);
INSERT INTO products (nom, description, prix, stock, category_id) VALUES ('Coque iPhone 15', 'Coque de protection en silicone', 19.99, 80, 4);

-- Insérer les utilisateurs
INSERT INTO users (nom, email, password) VALUES ('Administrateur', 'admin@ecommerce.com', 'admin123');
INSERT INTO users (nom, email, password) VALUES ('Client Test', 'client@test.com', 'client123');