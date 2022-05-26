DROP DATABASE IF EXISTS MessageApp;
CREATE DATABASE MessageApp;
USE MessageApp;

DROP TABLE IF EXISTS messengers;
CREATE TABLE messengers(
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    
    CONSTRAINT PK_messenger PRIMARY KEY(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS messages;
CREATE TABLE messages(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	sender BIGINT UNSIGNED NOT NULL,
    receiver BIGINT UNSIGNED NOT NULL,
    content TEXT NOT NULL,
    language VARCHAR(5),
    creation_date DATETIME DEFAULT NOW(),
    reception_date DATETIME,
    read_date DATETIME,

    CONSTRAINT PK_message 
		PRIMARY KEY (id),
    CONSTRAINT FK_sender 
		FOREIGN KEY (sender) REFERENCES messengers(id),
	CONSTRAINT FK_recipient 
		FOREIGN KEY (receiver) REFERENCES messengers(id)
)ENGINE=InnoDB;


DROP TABLE IF EXISTS user_groups;
CREATE TABLE user_groups(
	id BIGINT UNSIGNED NOT NULL,   
	name VARCHAR(64) NOT NULL,
    description TEXT,
    active BOOLEAN DEFAULT TRUE,
    
    CONSTRAINT PK_group PRIMARY KEY(id),
    CONSTRAINT FK_id_group
		FOREIGN KEY (id) REFERENCES messengers(id)
);

DROP TABLE IF EXISTS my_users;
CREATE TABLE my_users(
	id BIGINT UNSIGNED NOT NULL,
	username VARCHAR(64) NOT NULL UNIQUE,
    mail VARCHAR(256) NOT NULL UNIQUE,
    first_name VARCHAR(64) NOT NULL,
	last_name VARCHAR(64) NOT NULL,
    language VARCHAR(5) NOT NULL,
    birth_date DATE NOT NULL,
    profile_image VARCHAR(256) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    
    CONSTRAINT PK_user PRIMARY KEY(id),
    CONSTRAINT FK_id_user 
		FOREIGN KEY (id) REFERENCES messengers(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS contacts;
CREATE TABLE contacts(
	contact_owner BIGINT UNSIGNED NOT NULL,
    contacted BIGINT UNSIGNED NOT NULL,
    
    CONSTRAINT PK_contact PRIMARY KEY(contact_owner,contacted),
    CONSTRAINT FK_contact_owner 
		FOREIGN KEY (contact_owner) REFERENCES my_users(id),
	CONSTRAINT FK_contacted
		FOREIGN KEY (contacted) REFERENCES my_users(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS blocks;
CREATE TABLE blocks(
	block_owner BIGINT UNSIGNED NOT NULL,
    blocked BIGINT UNSIGNED NOT NULL,
    
    CONSTRAINT PK_block PRIMARY KEY(block_owner,blocked),
    CONSTRAINT FK_block_owner 
		FOREIGN KEY (block_owner) REFERENCES my_users(id),
	CONSTRAINT FK_blocked
		FOREIGN KEY (blocked) REFERENCES my_users(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS group_members;
CREATE TABLE group_members(
	group_id BIGINT UNSIGNED NOT NULL,
    group_member BIGINT UNSIGNED NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE,
    
    CONSTRAINT PK_contact PRIMARY KEY(group_id,group_member),
    CONSTRAINT FK_group_id
		FOREIGN KEY (group_id) REFERENCES user_groups(id),
	CONSTRAINT FK_group_member
		FOREIGN KEY (group_member) REFERENCES my_users(id)
)ENGINE=InnoDB;




DROP TABLE IF EXISTS roles;
CREATE TABLE roles(
	id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL,
    
    CONSTRAINT PK_roles PRIMARY KEY(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS user_autentications;
CREATE TABLE user_autentications(
	username VARCHAR(64) NOT NULL,
	password VARCHAR(256) NOT NULL,
    role TINYINT UNSIGNED NOT NULL,
    
    CONSTRAINT PK_userAutentication PRIMARY KEY(username),
    
    CONSTRAINT FK_username
		FOREIGN KEY (username) REFERENCES my_users(username),
	CONSTRAINT FK_role 
		FOREIGN KEY (role) REFERENCES roles(id)
)ENGINE=InnoDB;

DELIMITER $
DROP TRIGGER IF EXISTS messages_Create$
CREATE TRIGGER messages_Create BEFORE INSERT ON messages FOR EACH ROW 
BEGIN
	IF NEW.sender = NEW.receiver THEN
		SIGNAL SQLSTATE '99999' 
			SET MESSAGE_TEXT = 'The sender and the recipient must be different';
	END IF;
	IF NEW.creation_date != NOW() THEN
		SET NEW.creation_date = NOW();
	END IF;
END$
DROP PROCEDURE IF EXISTS message_received$
CREATE PROCEDURE message_received(IN var_message BIGINT UNSIGNED)
BEGIN
	UPDATE messages
		SET reception_date = NOW()
        WHERE id = var_message;
END$

DROP PROCEDURE IF EXISTS message_read$
CREATE PROCEDURE message_read(IN var_message BIGINT UNSIGNED)
BEGIN
	UPDATE messages
		SET read_date = NOW()
		WHERE id = var_message;
END$
DELIMITER ;

