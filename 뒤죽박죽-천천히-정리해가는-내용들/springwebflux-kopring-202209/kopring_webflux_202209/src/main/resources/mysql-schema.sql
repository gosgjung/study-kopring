-- DROP TABLE IF EXISTS payment;
CREATE SCHEMA IF NOT EXISTS testprj;
DROP TABLE IF EXISTS testprj.book;
commit;

-- -- CREATE TABLE payment
create table testprj.book
(
    book_id    bigint auto_increment
        primary key,
    name        varchar(30)      null,
    price        bigint      null
);

commit;