create table accounts
(
   id bigint auto_increment,
   username varchar(255) not null,
   password varchar(255) not null,
   name varchar(255) not null,
   lastname varchar(255) not null,
   money decimal(9999,4) not null,
   primary key(username)
);

create table account_stock
(
   id bigint auto_increment,
   acc_id integer not null,
   stock_id varchar(255) not null,
   stock_count integer not null,
   primary key(id),
   foreign key (acc_id) REFERENCES accounts(id)
);

create table stock_archive
(
   id integer not null auto_increment,
   times_tamp timestamp not null,
   stock_id varchar(255) not null,
   price decimal(7,4) not null,
   primary key(id)
);