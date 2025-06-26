-- V1__create_products_and_categories_tables.sql

CREATE SCHEMA IF NOT EXISTS "springboot_fullstack_challenge";

CREATE TABLE "springboot_fullstack_challenge"."categories" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent INTEGER,
    CONSTRAINT fk_parent FOREIGN KEY(parent) REFERENCES "springboot_fullstack_challenge"."categories"(id)
);

CREATE TABLE "springboot_fullstack_challenge"."products" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    category INTEGER,
    available BOOLEAN NOT NULL,
    stock INTEGER DEFAULT 0,
    image_url VARCHAR(255),
    CONSTRAINT fk_category FOREIGN KEY(category) REFERENCES "springboot_fullstack_challenge"."categories"(id)
);

insert into "springboot_fullstack_challenge"."categories" (name, parent) values
('Electronics', null),
('Computers', 1),
('Laptops', 2),
('Desktops', 2),
('Smartphones', 1),
('Tablets', 1);

insert into "springboot_fullstack_challenge"."products" (name, description, price, category, available, image_url, stock) values
('Dell XPS 13', 'A high-performance laptop with a sleek design.', 999.99, 3, true, 'http://localhost:8080/images/GenericLaptop.png', 150),
('MacBook Pro', 'Apple''s premium laptop with M1 chip.', 1299.99, 3, true, 'http://localhost:8080/images/GenericLaptop.png', 250),
('HP Spectre x360', 'A versatile 2-in-1 laptop.', 1099.99, 3, true, 'http://localhost:8080/images/GenericLaptop.png', 350),
('Lenovo ThinkPad X1 Carbon', 'A business-class laptop with a durable design.', 1399.99, 3, true, 'http://localhost:8080/images/GenericLaptop.png', 10),
('iPhone 13', 'Apple''s latest smartphone with advanced features.', 799.99, 5, true, 'http://localhost:8080/images/GenericPhone.png', 105),
('Samsung Galaxy S21', 'A flagship smartphone with a stunning display.', 799.99, 5, true, 'http://localhost:8080/images/GenericPhone.png', 15),
('iPad Pro', 'Apple''s powerful tablet for professionals.', 999.99, 6, true, 'http://localhost:8080/images/GenericPhone.png', 56),
('Samsung Galaxy Tab S7', 'A high-performance Android tablet.', 649.99, 6, true, 'http://localhost:8080/images/GenericPhone.png', 23);