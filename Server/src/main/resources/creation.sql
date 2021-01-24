DROP DATABASE chinese_checkers_db;
CREATE DATABASE chinese_checkers_db;
USE chinese_checkers_db;

CREATE TABLE games (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       players INT,
                       start   DATETIME
);

CREATE TABLE moves (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       game_id INT NOT NULL,
                       player_id INT,
                       pawn_id INT,
                       dest_x INT,
                       dest_y INT,
                       dest_z INT,
                       FOREIGN KEY(game_id) REFERENCES games(id) ON DELETE CASCADE
);