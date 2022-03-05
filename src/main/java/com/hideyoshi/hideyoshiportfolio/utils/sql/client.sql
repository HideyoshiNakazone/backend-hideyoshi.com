
CREATE SEQUENCE IF NOT EXISTS client_seq
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	cache 1;

DROP TABLE IF EXISTS client;
CREATE TABLE IF NOT EXISTS client (
    id				BIGINT			        NOT NULL DEFAULT NEXTVAL('client_seq') ,
    full_name		VARCHAR(255)	        NOT NULL,
    email			VARCHAR(255)	        NOT NULL,
    username		VARCHAR(20) 	        NOT NULL,
    passwd			VARCHAR(60)		        NOT NULL,
    roles			VARCHAR(20)		        NOT NULL DEFAULT 'USER',

    CONSTRAINT      client_primary_key      PRIMARY KEY (email),
    CONSTRAINT      client_username_unique  UNIQUE      (username)
);