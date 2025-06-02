DROP DATABASE akihabara_db;
CREATE DATABASE akihabara_db;
USE akihabara_db;

	##En categoria es ("figura", "Manga", "póster", "Llavero", "Ropa")
CREATE TABLE producto(
id INT PRIMARY KEY auto_increment,
nombre VARCHAR (255) NOT NULL,
categoria VARCHAR (100),
precio DECIMAL (10,2),
stock int
);
