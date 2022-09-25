CREATE SCHEMA IF NOT EXISTS collector;
DROP TABLE IF EXISTS book;

CREATE TABLE book
(
    book_id    bigint auto_increment
        primary key,
    price        bigint      null,
    name    varchar(200) not null
);

commit;