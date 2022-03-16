DROP TABLE IF EXISTS client;
DROP SEQUENCE IF EXISTS client_seq;

CREATE TABLE IF NOT EXISTS client (
    id				SERIAL,
    full_name		VARCHAR(255)	        NOT NULL,
    email			VARCHAR(255)	        NOT NULL,
    username		VARCHAR(20) 	        NOT NULL,
    passwd			VARCHAR(60)		        NOT NULL,
    roles			VARCHAR(20)		        NOT NULL DEFAULT 'USER',

    CONSTRAINT      client_primary_key      PRIMARY KEY (id),
    CONSTRAINT      client_email_unique     UNIQUE      (email),
    CONSTRAINT      client_username_unique  UNIQUE      (username)
);