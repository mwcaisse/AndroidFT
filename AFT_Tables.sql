
CREATE TABLE DEVICE (
	DEV_ID BIGINT NOT NULL AUTO_INCREMENT,
	DEV_UID VARCHAR(25) NOT NULL,
	DEV_REG_ID VARCHAR(4000),
	DEV_NAME VARCHAR(255),
	DEV_ACT BOOL,
	PRIMARY KEY (DEV_ID)
);

CREATE TABLE REQUEST (
	REQ_ID BIGINT NOT NULL AUTO_INCREMENT,
	REQ_NAME VARCHAR(255),
	REQ_FILE_LOC VARCHAR(255),
	REQ_FILE_BASE_DIR VARCHAR(255), 
	REQ_FILE_ID BIGINT,
	REQ_STAT VARCHAR(255),
	REQ_UPD TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	REQ_DEV_ID BIGINT,
	PRIMARY KEY (REQ_ID),
	FOREIGN KEY (REQ_DEV_ID) REFERENCES DEVICE(DEV_ID),
	FOREIGN KEY (REQ_FILE_ID) REFERENCES FILE(FILE_ID),
	FOREIGN KEY (REQ_STAT) REFERENCES REQUEST_STATUS(REQ_STAT_VAL),
	FOREIGN KEY (REQ_FILE_BASE_DIR) REFERENCES REQUEST_BASE_DIR(REQ_BASE_DIR)
)

CREATE TABLE REQUEST_STATUS (
	REQ_STAT_VAL VARCHAR(255) NOT NULL,
	PRIMARY KEY (REQ_STAT_VAL)
);

CREATE TABLE REQUEST_BASE_DIR (
	REQ_BASE_DIR VARCHAR(255) NOT NULL,
	PRIMARY KEY (REQ_BASE_DIR)
);

CREATE TABLE FILE (
	FILE_ID BIGINT NOT NULL AUTO_INCREMENT,
	FILE_VAL MEDIUMBLOB,
	FILE_NAME VARCHAR(255),
	PRIMARY KEY (FILE_ID)
);