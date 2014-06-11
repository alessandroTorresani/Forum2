CREATE TABLE USERS (
    USER_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    USERNAME VARCHAR(255),
    PASSWORD VARCHAR(255),
    EMAIL VARCHAR(255),
    LAST_LOGIN TIMESTAMP,
    IS_MODERATOR BOOLEAN,
    CONSTRAINT primary_key PRIMARY KEY (USER_ID) 
);

CREATE TABLE GROUPS (
    GROUP_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    ADMINISTRATOR_ID INTEGER NOT NULL,
    GROUPNAME VARCHAR(255),
    CREATION_DATE TIMESTAMP,
    IS_CLOSED BOOLEAN,
    IS_PRIVATE BOOLEAN,
    CONSTRAINT PRIMARY_KEY1 PRIMARY KEY (GROUP_ID,ADMINISTRATOR_ID) 
);

CREATE TABLE USERS_GROUPS (
    USER_ID  INT NOT NULL,
    GROUP_ID INT NOT NULL,
    IS_ADMINISTRATOR BOOLEAN,
    CONSTRAINT PRIMARY_KEY2 PRIMARY KEY(USER_ID,GROUP_ID)
);

CREATE TABLE BIDS (
    BID_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    USER_ID INT NOT NULL,
    GROUP_ID INT NOT NULL,
    ADMINISTRATOR_ID INT NOT NULL,
    CONSTRAINT PRIMARY_KEY3 PRIMARY KEY(BID_ID)
);

CREATE TABLE POSTS (
    POST_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    USER_ID INT NOT NULL,
    GROUP_ID INT NOT NULL,
    MESSAGE VARCHAR (4000),
    CREATION_DATE TIMESTAMP,
    CONSTRAINT PRIMARY_KEY4 PRIMARY KEY(POST_ID)
);

CREATE TABLE POST_FILES (
    FILE_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    POST_ID INT NOT NULL,
    FILENAME VARCHAR(255),
    CONSTRAINT PRIMARY_KEY5 PRIMARY KEY(FILE_ID,POST_ID)
);

CREATE TABLE FORGOTTEN_PASSWORDS (
    REQUEST_ID VARCHAR(64) NOT NULL,
    USER_ID INT NOT NULL,
    REQUEST_TIME TIMESTAMP,
    TEMP_PASSWORD VARCHAR(50),
    CONSTRAINT PRIMARY_KEY6 PRIMARY KEY (REQUEST_ID,USER_ID)
);


INSERT INTO users (username,password,email, is_moderator) VALUES ('Alessandro','alessandrop','harwinrene@gmail.com', true);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente1','utente1p','utente1@gmail.com', true);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente2','utente2p','utente2@gmail.com', true);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente3','utente3p','utente3@gmail.com', false);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente4','utente4p','utente4@gmail.com', false);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente5','utente5p','utente5@gmail.com', false);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente6','utente6p','utente6@gmail.com', false);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente7','utente7p','utente7@gmail.com', false);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente8','utente8p','utente8@gmail.com', false);
INSERT INTO users (username,password,email, is_moderator) VALUES ('utente9','utente9p','utente9@gmail.com', false);

INSERT INTO groups (administrator_id, groupname, creation_date, is_closed, is_private) VALUES (1,'gruppo1','2014-06-11 15:10:10', false, false);
INSERT INTO groups (administrator_id, groupname, creation_date, is_closed, is_private) VALUES (1,'gruppo2','2014-06-11 15:11:10', false, true);
INSERT INTO groups (administrator_id, groupname, creation_date, is_closed, is_private) VALUES (3,'gruppo3','2014-06-11 15:12:10', false, false);
INSERT INTO groups (administrator_id, groupname, creation_date, is_closed, is_private) VALUES (4,'gruppo4','2014-06-11 15:13:10', false, true);
INSERT INTO groups (administrator_id, groupname, creation_date, is_closed, is_private) VALUES (5,'gruppo5','2014-06-11 15:14:10', true, false);

INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (1,1,true);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (1,2,true);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (3,3,true);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (4,4,true);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (5,5,true);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (6,1,false);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (7,1,false);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (8,3,false);
INSERT INTO users_groups (user_id, group_id, is_administrator) VALUES (9,4,false);

INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post1','2014-06-11 15:15:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post2','2014-06-11 15:16:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post3','2014-06-11 15:17:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post4','2014-06-11 15:18:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post5','2014-06-11 15:19:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post6','2014-06-11 15:20:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post7','2014-06-11 15:21:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (1,1,'post8','2014-06-11 15:22:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (6,1,'post9','2014-06-11 15:23:10');
INSERT INTO posts(user_id, group_id, message, creation_date) VALUES (7,1,'post10','2014-06-11 15:24:10');

INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post1','2014-06-11 15:25:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post2','2014-06-11 15:26:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post3','2014-06-11 15:27:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post4','2014-06-11 15:28:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post5','2014-06-11 15:29:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post6','2014-06-11 15:30:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post7','2014-06-11 15:31:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post8','2014-06-11 15:32:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post9','2014-06-11 15:33:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (1,2,'post10','2014-06-11 15:34:10');

INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post1','2014-06-11 15:35:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post2','2014-06-11 15:36:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post3','2014-06-11 15:37:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post4','2014-06-11 15:38:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post5','2014-06-11 15:39:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post6','2014-06-11 15:40:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post7','2014-06-11 15:41:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post8','2014-06-11 15:42:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (3,3,'post9','2014-06-11 15:43:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (8,3,'post10','2014-06-11 15:44:10');

INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post1','2014-06-11 15:45:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post2','2014-06-11 15:46:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post3','2014-06-11 15:47:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post4','2014-06-11 15:48:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post5','2014-06-11 15:49:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post6','2014-06-11 15:50:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post7','2014-06-11 15:51:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post8','2014-06-11 15:52:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (4,4,'post9','2014-06-11 15:53:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (9,4,'post10','2014-06-11 15:54:10');

INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post1','2014-06-11 15:55:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post2','2014-06-11 15:56:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post3','2014-06-11 15:57:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post4','2014-06-11 15:58:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post5','2014-06-11 15:59:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post6','2014-06-11 16:00:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post7','2014-06-11 16:01:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post8','2014-06-11 16:02:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post9','2014-06-11 16:03:10');
INSERT INTO posts(user_id, group_id,message, creation_date) VALUES (5,5,'post10','2014-06-11 16:04:10');

INSERT INTO post_files(post_id,filename) VALUES (7,'1.txt');
INSERT INTO post_files(post_id,filename) VALUES (8,'2.txt');
INSERT INTO post_files(post_id,filename) VALUES (9,'3.txt');
INSERT INTO post_files(post_id,filename) VALUES (10,'4.txt');
INSERT INTO post_files(post_id,filename) VALUES (17,'5.txt');
INSERT INTO post_files(post_id,filename) VALUES (18,'6.txt');
INSERT INTO post_files(post_id,filename) VALUES (19,'7.txt');
INSERT INTO post_files(post_id,filename) VALUES (20,'8.txt');
INSERT INTO post_files(post_id,filename) VALUES (27,'9.txt');
INSERT INTO post_files(post_id,filename) VALUES (28,'10.txt');
INSERT INTO post_files(post_id,filename) VALUES (29,'11.txt');
INSERT INTO post_files(post_id,filename) VALUES (30,'12.txt');
INSERT INTO post_files(post_id,filename) VALUES (37,'13.txt');
INSERT INTO post_files(post_id,filename) VALUES (38,'14.txt');
INSERT INTO post_files(post_id,filename) VALUES (39,'15.txt');
INSERT INTO post_files(post_id,filename) VALUES (40,'16.txt');
INSERT INTO post_files(post_id,filename) VALUES (47,'17.txt');
INSERT INTO post_files(post_id,filename) VALUES (48,'18.txt');
INSERT INTO post_files(post_id,filename) VALUES (49,'19.txt');
INSERT INTO post_files(post_id,filename) VALUES (50,'20.txt');