CREATE SCHEMA IF NOT EXISTS collector;
DROP TABLE IF EXISTS book;

CREATE TABLE book
(
    book_id    bigint auto_increment
        primary key,
    author_id    bigint      null,
    price        bigint      null,
    bookname    varchar(200) not null
);

commit;