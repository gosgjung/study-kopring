-- DROP TABLE IF EXISTS payment;
CREATE SCHEMA IF NOT EXISTS collector;
DROP TABLE IF EXISTS collector.book;
--
-- -- CREATE TABLE payment
create table collector.book
(
    book_id    bigint auto_increment
        primary key,
    author_id    bigint      null,
    price        bigint      null
);

commit;
