DROP DATABASE IF EXISTS matchdb;

CREATE DATABASE matchdb;
USE matchdb;

CREATE TABLE users (
	userid INTEGER PRIMARY KEY NOT NULL,
	username varchar(30) NOT NULL,
	userdisplayname varchar(40) NOT NULL,
	userpassword varchar(50) NOT NULL,
	usermaxtraveldistance INTEGER NOT NULL,
	userlatitude varchar(30) NOT NULL,
	userlongitude varchar(30) NOT NULL,
    useraboutMe varchar(1000) NOT NULL
);

CREATE TABLE hobbies (
    hid INTEGER PRIMARY KEY NOT NULL,
    hinterests varchar(100)
);

CREATE TABLE userHobbies (
	uhiid INTEGER NOT NULL,
	uhhid INTEGER NOT NULL
);

CREATE TABLE friendsList(
    fid1 INTEGER NOT NULL,
    fid2 INTEGER NOT NULL
);

INSERT INTO users VALUES(1,'test1','James Smith','jamessmith','10','40','30','I like to party');
INSERT INTO users VALUES(2,'test2','Jenny Goldstein','jennygoldstein','20','25','30','I am sporty');
INSERT INTO users VALUES(3,'test3','Rachel Berry','rachelberry','25','15','35','I am destined to be a star');
INSERT INTO users VALUES(4,'test4','Will Schuester','willschuester','15','35','45','I am a high school teacher');
INSERT INTO users VALUES(5,'test5','Quinn Fabray','quinnfabray','5','16','35','I am head of the Cheerios cheerleading squad');
INSERT INTO users VALUES(6,'test6','Mike Chang','mikechang','10','35','35','I am the quarterback of the football team');
INSERT INTO users VALUES(7,'test7','Ryujin Kang','ryujinkang','15','20','20','I am the main dancer in ITZY');
INSERT INTO users VALUES(8,'test8','Sue Sylvester','suesylvester','5','10','80','I am a cheerleading coach');
INSERT INTO users VALUES(9,'test9','Becky Jackson','beckyjackson','10','25','25','I am a cheerleader');
INSERT INTO users VALUES(10,'test10','Tom Holland','tomholland','30','79','75','I am Spiderman');

INSERT INTO hobbies VALUES(1,'swimming');
INSERT INTO hobbies VALUES(2,'reading');
INSERT INTO hobbies VALUES(3,'bicycling');
INSERT INTO hobbies VALUES(4,'hiking');
INSERT INTO hobbies VALUES(5,'camping');
INSERT INTO hobbies VALUES(6,'dancing');
INSERT INTO hobbies VALUES(7,'running');
INSERT INTO hobbies VALUES(8,'bowling');
INSERT INTO hobbies VALUES(9,'video games');
INSERT INTO hobbies VALUES(10,'programming');
INSERT INTO hobbies VALUES(11,'watching TV');
INSERT INTO hobbies VALUES(12,'singing');
INSERT INTO hobbies VALUES(13,'going to the movies');
INSERT INTO hobbies VALUES(14,'basketball');
INSERT INTO hobbies VALUES(15,'football');

INSERT INTO userHobbies VALUES(1,1);
INSERT INTO userHobbies VALUES(1,3);
INSERT INTO userHobbies VALUES(1,4);
INSERT INTO userHobbies VALUES(2,1);
INSERT INTO userHobbies VALUES(2,2);
INSERT INTO userHobbies VALUES(2,5);
INSERT INTO userHobbies VALUES(3,12);
INSERT INTO userHobbies VALUES(3,2);
INSERT INTO userHobbies VALUES(3,8);
INSERT INTO userHobbies VALUES(4,12);
INSERT INTO userHobbies VALUES(4,6);
INSERT INTO userHobbies VALUES(4,13);
INSERT INTO userHobbies VALUES(5,12);
INSERT INTO userHobbies VALUES(5,8);
INSERT INTO userHobbies VALUES(5,6);
INSERT INTO userHobbies VALUES(6,15);
INSERT INTO userHobbies VALUES(6,12);
INSERT INTO userHobbies VALUES(6,7);
INSERT INTO userHobbies VALUES(7,6);
INSERT INTO userHobbies VALUES(7,12);
INSERT INTO userHobbies VALUES(7,4);
INSERT INTO userHobbies VALUES(8,10);
INSERT INTO userHobbies VALUES(8,6);
INSERT INTO userHobbies VALUES(8,9);
INSERT INTO userHobbies VALUES(9,11);
INSERT INTO userHobbies VALUES(9,5);
INSERT INTO userHobbies VALUES(9,6);
INSERT INTO userHobbies VALUES(10,2);
INSERT INTO userHobbies VALUES(10,8);
INSERT INTO userHobbies VALUES(10,6);

INSERT INTO friendsList VALUES(1,2);
INSERT INTO friendsList VALUES(3,4);
INSERT INTO friendsList VALUES(5,6);
INSERT INTO friendsList VALUES(7,8);
INSERT INTO friendsList VALUES(9,10);




