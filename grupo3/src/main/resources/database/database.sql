DROP DATABASE IF EXISTS MessageApp;
CREATE DATABASE MessageApp;
USE MessageApp;

DROP TABLE IF EXISTS Messages;
CREATE TABLE message(
	sender VARCHAR(64) NOT NULL,
    recipient VARCHAR(64) NOT NULL,
    content TEXT NOT NULL,
    language VARCHAR(5),
    creation_date DATETIME NOT NULL,
    reception_date DATETIME,
    read_date DATETIME,
    
    CONSTRAINT PK_message 
		PRIMARY KEY (sender,recipient,creation_date),
    CONSTRAINT FK_sender 
		FOREIGN KEY (sender) REFERENCES MyUsers(username),
	CONSTRAINT FK_recipient 
		FOREIGN KEY (recipient) REFERENCES MyUsers(username)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS Messenger;
CREATE TABLE Messenger(
	id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT
)ENGINE=InnoDB;

CREATE TABLE UserGroups(
	id BIGINT UNSIGNED NOT NULL,   
	name VARCHAR(64) NOT NULL,
    description TEXT,
    
    CONSTRAINT PK_group PRIMARY KEY(id),
    CONSTRAINT FK_id 
		FOREIGN KEY (id) REFERENCES Messenger(id)
);

DROP TABLE IF EXISTS MyUsers;
CREATE TABLE my_user(
	id BIGINT UNSIGNED NOT NULL,
	username VARCHAR(64) NOT NULL UNIQUE,
    mail VARCHAR(256) NOT NULL UNIQUE,
    first_name VARCHAR(64) NOT NULL,
	last_name VARCHAR(64) NOT NULL,
    language VARCHAR(5) NOT NULL,
    birth_date DATE NOT NULL,
    profile_image VARCHAR(256) NOT NULL,
    
    CONSTRAINT PK_user PRIMARY KEY(id),
    CONSTRAINT FK_id 
		FOREIGN KEY (id) REFERENCES Messenger(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS Contacts;
CREATE TABLE Contacts(
	contact_owner VARCHAR(64) NOT NULL,
    contacted VARCHAR(64) NOT NULL,
    
    CONSTRAINT PK_contact PRIMARY KEY(contact_owner,contacted),
    CONSTRAINT FK_contact_owner 
		FOREIGN KEY (contact_owner) REFERENCES MyUsers(username),
	CONSTRAINT FK_contacted
		FOREIGN KEY (contacted) REFERENCES MyUsers(username)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS Blocks;
CREATE TABLE Blocks(
	block_owner VARCHAR(64) NOT NULL,
    blocked VARCHAR(64) NOT NULL,
    
    CONSTRAINT PK_block PRIMARY KEY(block_owner,blocked),
    CONSTRAINT FK_block_owner 
		FOREIGN KEY (block_owner) REFERENCES MyUsers(username),
	CONSTRAINT FK_blocked
		FOREIGN KEY (blocked) REFERENCES MyUsers(username)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS GroupMembers;
CREATE TABLE GroupMembers(
	group_id VARCHAR(64) NOT NULL,
    group_member VARCHAR(64) NOT NULL,
    
    CONSTRAINT PK_contact PRIMARY KEY(group_id,group_member),
    CONSTRAINT FK_group_id
		FOREIGN KEY (group_id) REFERENCES MyUsers(username),
	CONSTRAINT FK_group_member
		FOREIGN KEY (group_member) REFERENCES MyUsers(username)
)ENGINE=InnoDB;






DROP TABLE IF EXISTS UserAutentications;
CREATE TABLE UserAutentications(
	username VARCHAR(64) NOT NULL,
	password VARCHAR(256) NOT NULL,
    role TINYINT UNSIGNED NOT NULL,
    
    CONSTRAINT PK_userAutentication PRIMARY KEY(username),
    
    CONSTRAINT FK_username
		FOREIGN KEY (username) REFERENCES MyUsers(username),
	CONSTRAINT FK_role 
		FOREIGN KEY (role) REFERENCES Roles(id)
)ENGINE=InnoDB;

DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles(
	id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL,
    
    CONSTRAINT PK_roles PRIMARY KEY(id)
)ENGINE=InnoDB;
