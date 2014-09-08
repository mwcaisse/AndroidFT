
CREATE TABLE DEVICE (
	DEV_ID BIGINT NOT NULL AUTO_INCREMENT,
	DEV_UID VARCHAR(25) NOT NULL,
	DEV_REG_ID VARCHAR(4000),
	DEV_NAME VARCHAR(250),
	DEV_ACT BOOL,
	PRIMARY KEY (DEV_ID)
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
	PRIMARY KEY (REQ_ID),
	FOREIGN KEY (REQ_DEV_ID) REFERENCES DEVICE(DEV_ID),
	FOREIGN KEY (REQ_STAT) REFERENCES REQUEST_STATUS(REQ_STAT_VAL),
	FOREIGN KEY (REQ_FILE_BASE_DIR) REFERENCES REQUEST_BASE_DIR(REQ_BASE_DIR)
);

CREATE TABLE REQUEST_STATUS (
	REQ_STAT_VAL VARCHAR(16) NOT NULL,
	PRIMARY KEY (REQ_STAT_VAL)
);

CREATE TABLE REQUEST_BASE_DIR (
	REQ_BASE_DIR VARCHAR(16) NOT NULL,
	PRIMARY KEY (REQ_BASE_DIR)
);

CREATE TABLE FILE (
	FILE_ID BIGINT NOT NULL AUTO_INCREMENT,
	REQ_ID BIGINT NULL,
	FILE_VAL MEDIUMBLOB,
	FILE_NAME VARCHAR(250),
	PRIMARY KEY (FILE_ID),
	FOREIGN KEY (REQ_ID) REFERENCES REQUEST(REQ_ID)
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