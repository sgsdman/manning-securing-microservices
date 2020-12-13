DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS authorities;

DROP TABLE IF EXISTS clients;

CREATE TABLE users ( 
   id INTEGER auto_increment primary key, 
   username VARCHAR(50) NOT NULL, 
   password VARCHAR(100) NOT NULL, 
   authority varchar(100)
);

CREATE TABLE authorities ( 
   id INTEGER auto_increment primary key, 
   username VARCHAR(50) NOT NULL, 
   authority VARCHAR(100) NOT NULL
);


create table clients (
	id integer auto_increment primary key,
	client VARCHAR(256) not null,
	resources VARCHAR(256),
	secret VARCHAR(256),
	scope VARCHAR(256),
	grant_types VARCHAR(256),
	redirect_uri VARCHAR(256),
	authorities VARCHAR(256),
    access_token_validity INTEGER default 60,
    refresh_token_validity INTEGER default 60,
	additional_info VARCHAR(4096),
	auto_approve VARCHAR(256) default 'Y'
		);

-- $2a$10$nTtTPgNrHtzP101YSISLIuxyhzgXwWWUsR8sXwylcJ8yT/b2FcsCe is "12345" Bcrypt encoded. This is for unit test
INSERT INTO users  (id, username, password, authority) VALUES (3,'john', '$2a$10$nTtTPgNrHtzP101YSISLIuxyhzgXwWWUsR8sXwylcJ8yT/b2FcsCe','x');

-- $2a$08$G2nubJ9FQLVNJddwchKbfO/8Xn59BHjU8iBSLAXGgDjLF5g0d5sDC is "secret" Bcrypt encoded. This is for unit test
INSERT INTO clients (id,client, authorities, secret, grant_types,redirect_uri,scope,additional_info) VALUES (2,'client','read','$2a$08$G2nubJ9FQLVNJddwchKbfO/8Xn59BHjU8iBSLAXGgDjLF5g0d5sDC','authorization_code,password,refresh_token','http://localhost:9090/home','read','TWE' );