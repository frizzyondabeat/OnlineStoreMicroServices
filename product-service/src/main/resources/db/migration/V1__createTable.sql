create table if not exists inventory (
    id bigint not null auto_increment ,
    sku_code varchar(100) not null ,
    quantity integer not null ,
    primary key (id),
    unique key sku_code_unique (sku_code)
) engine = InnoDB default charset = utf8;