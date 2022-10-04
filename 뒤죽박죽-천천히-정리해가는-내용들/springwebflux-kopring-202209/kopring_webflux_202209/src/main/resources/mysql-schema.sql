-- https://stackoverflow.com/questions/13357760/mysql-create-user-if-not-exists
GRANT ALL ON `collector`.* TO 'collector'@'localhost' IDENTIFIED BY '1111'
-- GRANT ALL ON `collector`.* TO 'collector'@'%' IDENTIFIED BY '1111'

-- CREATE DATABASE IF NOT EXISTS collector character set utf8mb4 collate utf8mb4_general_ci;
-- CREATE DATABASE IF NOT EXISTS shop character set utf8mb4 collate utf8mb4_general_ci;
CREATE DATABASE IF NOT EXISTS collector character set utf8mb4 collate utf8mb4_general_ci;

use collector;

DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS book_category;

CREATE TABLE IF NOT EXISTS book
(
    id     varchar(80) not null
        primary key,
    name   varchar(60) null,
    price  bigint      null,
    detail text        null
);


CREATE TABLE IF NOT EXISTS book_category
(
    code varchar(10) not null
        primary key,
    name varchar(50) null
);