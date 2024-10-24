use mydb;

create table orders
(
    id          bigint auto_increment
        primary key,
    user_id     varchar(50) default 'defaultUser'     not null,
    product_id  varchar(20)                           not null,
    order_id    varchar(50)                           not null,
    qty         int         default 0                 null,
    unit_price  int         default 0                 null,
    total_price int         default 0                 null,
    created_at  datetime    default CURRENT_TIMESTAMP null
);

