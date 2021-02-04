-------------------------------------------------------------------
--  To Change the DEV1 password
--
--  ALTER USER SIT1 identified by "password1" replace "password1" ;
--

-- If password expired
-- SQL*Plus: Release 19.0.0.0.0 - Production on Sun Jan 17 23:53:46 2021
-- Version 19.3.0.0.0
-- Copyright (c) 1982, 2019, Oracle.  All rights reserved.
-- Enter user-name: sys as sysdba
-- Enter password: sys

-------------------------------------------------------------------

-- CREATE TABLE for USERS table

DROP TABLE  DEV1.USERS;
DROP TABLE  DEV1.AUTHORITIES;


CREATE TABLE DEV1.USERS (
    ID NUMBER(10) NOT NULL,
    USERNAME VARCHAR2(10) NOT NULL,
    PASSWORD VARCHAR2(10) NOT NULL,
    ENABLED VARCHAR2(1) NOT NULL,
   PRIMARY KEY (ID),
   UNIQUE (USERNAME)
);

CREATE TABLE DEV1.AUTHORITIES (
    ID NUMBER(10) NOT NULL,
    USERNAME VARCHAR2(10) NOT NULL,
    AUTHORITY VARCHAR2(10) NOT NULL,
   PRIMARY KEY (ID),
   FOREIGN KEY (USERNAME) REFERENCES USERS(username)
);


INSERT INTO DEV1.USERS VALUES (1001, 'ADMIN', 'ADMIN', 'Y');
INSERT INTO DEV1.USERS VALUES (1002, 'DBA', 'DBA', 'Y');
INSERT INTO DEV1.USERS VALUES (1003, 'USER', 'USER', 'Y');

INSERT INTO DEV1.AUTHORITIES VALUES (2001, 'ADMIN', 'ADMIN');
INSERT INTO DEV1.AUTHORITIES VALUES (2002, 'DBA', 'DBA');
INSERT INTO DEV1.AUTHORITIES VALUES (2003, 'USER', 'USER');