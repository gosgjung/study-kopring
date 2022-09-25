-- DROP TABLE IF EXISTS payment;
CREATE SCHEMA IF NOT EXISTS collector;
DROP TABLE IF EXISTS collector.book;
--
-- -- CREATE TABLE payment
create table collector.book
(
    book_id    bigint auto_increment
        primary key,
    name        varchar(30)      null,
    price        bigint      null
);

commit;

INSERT INTO collector.book (book_id, price, name) VALUES (1, 1000, '스포츠');
commit;
