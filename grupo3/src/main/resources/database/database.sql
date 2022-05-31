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
	id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL,
    
    CONSTRAINT PK_roles PRIMARY KEY(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS user_autentications;
CREATE TABLE user_autentications(
	username VARCHAR(64) NOT NULL,
	password VARCHAR(256) NOT NULL,
    role INTEGER UNSIGNED NOT NULL,
    
    CONSTRAINT PK_userAutentication PRIMARY KEY(username),
    
    CONSTRAINT FK_username
		FOREIGN KEY (username) REFERENCES my_users(username),
	CONSTRAINT FK_role 
		FOREIGN KEY (role) REFERENCES roles(id)
)ENGINE=InnoDB;

#------------------------------------------------------------------------------------------------
# roles




#-------------------------------------------------------------------------------------------------
# TRIGGERS

DELIMITER $
DROP TRIGGER IF EXISTS group_members_insert$
CREATE TRIGGER group_members_insert BEFORE INSERT ON group_members FOR EACH ROW 
BEGIN
	DECLARE members integer;
    SET members := (SELECT COUNT(*) FROM group_members WHERE group_id = NEW.group_id);
    IF members = 0 AND NEW.is_admin = false THEN
		SIGNAL SQLSTATE '99999' 
			SET MESSAGE_TEXT = 'the first member of the group must be an admin';
	END IF;
END$

DROP TRIGGER IF EXISTS group_members_update$
CREATE TRIGGER group_members_update BEFORE UPDATE ON group_members FOR EACH ROW 
BEGIN
	DECLARE members integer;
    IF OLD.is_admin = TRUE AND NEW.is_admin = FALSE THEN
		SET members := (SELECT COUNT(*) 
							FROM group_members 
							WHERE group_id = OLD.group_id 
								AND group_member != OLD.group_member 
								AND is_admin = true);
		IF members = 0 THEN
			SIGNAL SQLSTATE '99998' 
				SET MESSAGE_TEXT = 'the group must have another admin before deletion';
		END IF;
	END IF;
END$

-- DROP TRIGGER IF EXISTS group_members_delete$
-- CREATE TRIGGER group_members_delete BEFORE DELETE ON group_members FOR EACH ROW 
-- BEGIN
-- 	DECLARE members integer;
--     IF OLD.is_admin=true THEN
-- 		SET members := (SELECT COUNT(*) 
-- 							FROM group_members 
-- 							WHERE group_id = OLD.group_id 
-- 								AND group_member != OLD.group_member 
-- 								AND is_admin = true);
-- 		IF members = 0 THEN
-- 			SIGNAL SQLSTATE '99998' 
-- 				SET MESSAGE_TEXT = 'the group must have another admin before deletion';
-- 		END IF;
-- 	END IF;
-- END$

DROP TRIGGER IF EXISTS messages_create$
CREATE TRIGGER messages_Create BEFORE INSERT ON messages FOR EACH ROW 
BEGIN
	IF NEW.sender = NEW.receiver THEN
		SIGNAL SQLSTATE '99997' 
			SET MESSAGE_TEXT = 'The sender and the receiver must be different';
	END IF;
	IF NEW.creation_date != NOW() THEN
		SET NEW.creation_date = NOW();
	END IF;
END$

DROP TRIGGER IF EXISTS my_user_delete$
CREATE TRIGGER my_user_delete BEFORE DELETE ON my_users FOR EACH ROW 
BEGIN
	DECLARE admins integer;
	SET admins := (SELECT COUNT(*) 
						FROM group_members 
						WHERE group_member = OLD.id 
							AND is_admin = true);
	IF admins > 0 THEN
		SIGNAL SQLSTATE '99996' 
			SET MESSAGE_TEXT = 'The user must not be admin in any group for deletion';
	END IF;
	DELETE FROM group_members WHERE group_member = OLD.id;
    DELETE FROM contacts WHERE contact_owner = OLD.id OR contacted = OLD.id;
    DELETE FROM blocks WHERE block_owner = OLD.id OR blocked = OLD.id;
    DELETE FROM messages WHERE sender = OLD.id OR receiver = OLD.id;
    DELETE FROM user_autentications WHERE username = OLD.username;
END$

DROP TRIGGER IF EXISTS user_group_delete$
CREATE TRIGGER user_group_delete BEFORE DELETE ON user_groups FOR EACH ROW 
BEGIN
	DELETE FROM group_members WHERE group_id = OLD.id;
    DELETE FROM messages WHERE sender = OLD.id OR receiver = OLD.id;
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


DROP PROCEDURE IF EXISTS change_admin_status$
CREATE PROCEDURE change_admin_status(IN var_group BIGINT UNSIGNED, IN var_member BIGINT UNSIGNED)
BEGIN
	DECLARE var_is_admin BOOLEAN;
    SET var_is_admin := (SELECT is_admin FROM group_members WHERE group_id = var_group AND group_member = var_member);
	UPDATE group_members 
		SET is_admin = NOT var_is_admin
		WHERE group_id = var_group AND group_member = var_member;
END$

-- DROP TRIGGER IF EXISTS group_members_delete$
-- CREATE TRIGGER group_members_delete BEFORE DELETE ON group_members FOR EACH ROW 
-- BEGIN
-- 	DECLARE members integer;
--     IF OLD.is_admin=true THEN
-- 		SET members := (SELECT COUNT(*) 
-- 							FROM group_members 
-- 							WHERE group_id = OLD.group_id 
-- 								AND group_member != OLD.group_member 
-- 								AND is_admin = true);
-- 		IF members = 0 THEN
-- 			SIGNAL SQLSTATE '99998' 
-- 				SET MESSAGE_TEXT = 'the group must have another admin before deletion';
-- 		END IF;
-- 	END IF;
-- END$

DROP PROCEDURE IF EXISTS delete_group_member$
CREATE PROCEDURE delete_group_member(IN var_group BIGINT UNSIGNED, IN var_member BIGINT UNSIGNED)
BEGIN
	DECLARE var_is_admin BOOLEAN;
    DECLARE admins integer;
    SET var_is_admin := (SELECT is_admin FROM group_members WHERE group_id = var_group AND group_member = var_member);
	IF var_is_admin=true THEN
		SET admins := (SELECT COUNT(*) 
							FROM group_members 
							WHERE group_id = var_group
								AND group_member != var_member
								AND is_admin = true);
		IF admins = 0 THEN
				SIGNAL SQLSTATE '99998' 
					SET MESSAGE_TEXT = 'the group must have another admin before deletion';
		END IF;
	END IF;
	DELETE FROM group_members WHERE group_id = var_group AND group_member = var_member;

END$

DROP PROCEDURE IF EXISTS change_password$
CREATE PROCEDURE change_password(IN var_username VARCHAR(64), 
									IN var_old_password VARCHAR(256), 
                                    IN var_new_password VARCHAR(256))
BEGIN
	DECLARE old_password VARCHAR(256);
	SET old_password := (SELECT password FROM user_autentications WHERE username = var_username);
    IF old_password = var_new_password THEN
		UPDATE user_authentications
			SET password = var_new_password
            WHERE username = var_username;
	END IF;
END$

DELIMITER ;

INSERT INTO roles(name) VALUES ("ADMIN");
INSERT INTO roles(name) VALUES ("USER");


