drop table if exists oauth_client_details;
create table oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

drop table if exists oauth_client_token;
create table oauth_client_token (
   token_id VARCHAR(255),
   token LONGVARBINARY,
   authentication_id VARCHAR(255) PRIMARY KEY,
   user_name VARCHAR(255),
   client_id VARCHAR(255)
 );

 
 drop table if exists oauth_access_token;
 create table oauth_access_token (
   token_id VARCHAR(255),
   token LONG VARBINARY,
   authentication_id VARCHAR(255) PRIMARY KEY,
   user_name VARCHAR(255),
   client_id VARCHAR(255),
   authentication LONG VARBINARY,
   refresh_token VARCHAR(255)
 );
  
 drop table if exists oauth_refresh_token;
 create table oauth_refresh_token (
   token_id VARCHAR(255),
   token LONG VARBINARY,
   authentication LONG VARBINARY
 );
  
 drop table if exists oauth_code;
 create table oauth_code (
   code VARCHAR(255), authentication LONG VARBINARY
 );
  
 drop table if exists oauth_approvals;
 create table oauth_approvals (
     userId VARCHAR(255),
     clientId VARCHAR(255),
     scope VARCHAR(255),
     status VARCHAR(10),
     expiresAt TIMESTAMP,
     lastModifiedAt TIMESTAMP
 );




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