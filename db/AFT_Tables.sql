
CREATE TABLE USER (
	USER_ID BIGINT NOT NULL AUTO_INCREMENT,
	USER_USERNAME VARCHAR(50) UNIQUE NOT NULL,
	USER_PASSWORD VARCHAR(60) NOT NULL,
	USER_EMAIL VARCHAR(100) NOT NULL,
	USER_ACTIVE BIT NOT NULL DEFAULT 1,
	USER_LOCKED BIT NOT NULL DEFAULT 0,
	USER_EXP_DATE TIMESTAMP NULL DEFAULT NULL,
	USER_PASSWORD_EXP_DATE TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY (USER_ID)
);

CREATE TABLE USER_ROLE (
	USER_ROLE VARCHAR(50) NOT NULL,
	PRIMARY KEY (USER_ROLE)
);

CREATE TABLE USER_ROLE_ASSIGNMENT (
	USER_ROLE_ASSIGNMENT_ID BIGINT NOT NULL AUTO_INCREMENT,
	USER_ID BIGINT NOT NULL,
	USER_ROLE VARCHAR (50) NOT NULL,
	PRIMARY KEY (USER_ROLE_ASSIGNMENT_ID),
	FOREIGN KEY (USER_ID) REFERENCES USER(USER_ID),
	FOREIGN KEY (USER_ROLE) REFERENCES USER_ROLE(USER_ROLE)
);

INSERT INTO USER_ROLE (USER_ROLE)
VALUES ('ROLE_ADMIN'), ('ROLE_USER');
	

CREATE TABLE DEVICE (
	DEV_ID BIGINT NOT NULL AUTO_INCREMENT,
	DEV_UID VARCHAR(25) NOT NULL,
	DEV_REG_ID VARCHAR(4000),
	DEV_UPLOAD_KEY VARCHAR(500),
	DEV_NAME VARCHAR(250),
	DEV_ACT BOOL,
	DEV_OWNER BIGINT NOT NULL,
	PRIMARY KEY (DEV_ID),
	FOREIGN KEY (DEV_OWNER) REFERENCES USER(USER_ID)
);

CREATE TABLE REQUEST_STATUS (
	REQ_STAT_VAL VARCHAR(16) NOT NULL,
	PRIMARY KEY (REQ_STAT_VAL)
);

CREATE TABLE REQUEST_BASE_DIR (
	REQ_BASE_DIR VARCHAR(16) NOT NULL,
	PRIMARY KEY (REQ_BASE_DIR)
);

CREATE TABLE REQUEST (
	REQ_ID BIGINT NOT NULL AUTO_INCREMENT,
	REQ_NAME VARCHAR(250),
	REQ_FILE_LOC VARCHAR(250),
	REQ_FILE_BASE_DIR VARCHAR(16), 
	REQ_STAT VARCHAR(16),
	REQ_STAT_MSG VARCHAR(500) DEFAULT '',
	REQ_UPD TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	REQ_DEV_ID BIGINT,
	REQ_OWNER BIGINT NOT NULL,
	PRIMARY KEY (REQ_ID),
	FOREIGN KEY (REQ_DEV_ID) REFERENCES DEVICE(DEV_ID),
	FOREIGN KEY (REQ_STAT) REFERENCES REQUEST_STATUS(REQ_STAT_VAL),
	FOREIGN KEY (REQ_FILE_BASE_DIR) REFERENCES REQUEST_BASE_DIR(REQ_BASE_DIR),
	FOREIGN KEY (REQ_OWNER) REFERENCES USER(USER_ID)
);

CREATE TABLE FILE (
	FILE_ID BIGINT NOT NULL AUTO_INCREMENT,
	REQ_ID BIGINT NULL,
	FILE_VAL MEDIUMBLOB,
	FILE_NAME VARCHAR(250),
	FILE_OWNER BIGINT NOT NULL,
	PRIMARY KEY (FILE_ID),
	FOREIGN KEY (REQ_ID) REFERENCES REQUEST(REQ_ID),
	FOREIGN KEY (FILE_OWNER) REFERENCES USER (USER_ID)
);

INSERT INTO REQUEST_STATUS (REQ_STAT_VAL)
VALUES ('NEW'),
	  ('IN_PROGRESS'),
	  ('FAILED'),
	  ('COMPLETED'),
	  ('CANCELLED');


INSERT INTO REQUEST_BASE_DIR (REQ_BASE_DIR)
VALUES ('ROOT'),
	  ('DOCUMENTS'),
	  ('DOWNLOADS'),
	  ('MOVIES'),
	  ('MUSIC'),
	  ('PICTURES');
	  
	  
