DROP DATABASE IF EXISTS matchdb;

CREATE DATABASE matchdb;
USE matchdb;

CREATE TABLE users (
	userid INTEGER PRIMARY KEY NOT NULL,
	username varchar(30) NOT NULL,
	userdisplayname varchar(40) NOT NULL,
	userpassword varchar(50) NOT NULL,
	userhobbylist varchar(5000) NOT NULL,
	usermaxtraveldistance INTEGER NOT NULL,
	userlatitude varchar(30) NOT NULL,
	userlongitude varchar(30) NOT NULL
);

CREATE TABLE hobbylist (
	hid INTEGER PRIMARY KEY NOT NULL,
	husername varchar(30) NOT NULL,
	hdisplayname varchar(40) NOT NULL,
	havailablehobbies varchar(50) NOT NULL
);

CREATE TABLE commoninterests (
	cid INTEGER PRIMARY KEY NOT NULL,
	cusername varchar(50),
	chobby varchar(50)
);

INSERT INTO users VALUES(1,'test1','James Smith','jamessmith','swimming, biking, hiking','10','40','30');
INSERT INTO users VALUES(2,'test2','Jenny Goldstein','jennygoldstein','swimming, reading, cooking','20','25','30');
INSERT INTO users VALUES(3,'test3','Rachel Berry','rachelberry','singing, eating, skateboarding','25','15','35');
INSERT INTO users VALUES(4,'test4','Will Schuester','willschuester','singing, dancing, fishing','15','35','45');
INSERT INTO users VALUES(5,'test5','Quinn Fabray','quinnfabray','singing, cheerlleading, baking','5','16','35');
INSERT INTO users VALUES(6,'test6','Mike Chang','mikechang','football, singing, running','10','35','35');
INSERT INTO users VALUES(7,'test7','Ryujin Kang','ryujinkang','dancing, singing, hiking','15','20','20');
INSERT INTO users VALUES(8,'test8','Sue Sylvester','suesylvester','coaching, dancing, eating','5','10','80');
INSERT INTO users VALUES(9,'test9','Becky Jackson','beckyjackson','jumproping, cheerleading, dancing','10','25','25');
INSERT INTO users VALUES(10,'test10','Tom Holland','tomholland','acting, golf, skating','30','79','75');

INSERT INTO hobbylist VALUES(1,'test1','James Smith','swimming, biking, hiking');
INSERT INTO hobbylist VALUES(2,'test2','Jenny Goldstein','swimming, reading, cooking');
INSERT INTO hobbylist VALUES(3,'test3','Rachel Berry','singing, eating, skateboarding');
INSERT INTO hobbylist VALUES(4,'test4','Will Schuester','singing, dancing, fishing');
INSERT INTO hobbylist VALUES(5,'test5','Quinn Fabray','singing, cheerleading, baking');
INSERT INTO hobbylist VALUES(6,'test6','Mike Chang','football, singing, running');
INSERT INTO hobbylist VALUES(7,'test7','Ryujin Kang','dancing, singing, hiking');
INSERT INTO hobbylist VALUES(8,'test8','Sue Sylvester','coaching, dancing, eating');
INSERT INTO hobbylist VALUES(9,'test9','Becky Jackson','jumproping, cheerleading, dancing');
INSERT INTO hobbylist VALUES(10,'test10','Tom Holland','acting, golf, skating');

INSERT INTO commoninterests VALUES(1,'jamessmith','swimming');
INSERT INTO commoninterests VALUES(2,'jamessmith','biking');
INSERT INTO commoninterests VALUES(3,'jamessmith','hiking');
INSERT INTO commoninterests VALUES(4,'jennygoldstein','swimming');
INSERT INTO commoninterests VALUES(5,'jennygoldstein','reading');
INSERT INTO commoninterests VALUES(6,'jennygoldstein','cooking');
INSERT INTO commoninterests VALUES(7,'rachelberry','singing');
INSERT INTO commoninterests VALUES(8,'rachelberry','eating');
INSERT INTO commoninterests VALUES(9,'rachelberry','skateboarding');
INSERT INTO commoninterests VALUES(10,'willschuester','singing');
INSERT INTO commoninterests VALUES(11,'willschuester','dancing');
INSERT INTO commoninterests VALUES(12,'willschuester','fishing');
INSERT INTO commoninterests VALUES(13,'quinnfabray','singing');
INSERT INTO commoninterests VALUES(14,'quinnfabray','cheerleading');
INSERT INTO commoninterests VALUES(15,'quinnfabray','baking');
INSERT INTO commoninterests VALUES(16,'mikechang','football');
INSERT INTO commoninterests VALUES(17,'mikechang','singing');
INSERT INTO commoninterests VALUES(18,'mikechang','running');
INSERT INTO commoninterests VALUES(19,'ryujinkang','dancing');
INSERT INTO commoninterests VALUES(20,'ryujinkang','singing');
INSERT INTO commoninterests VALUES(21,'ryujinkang','hiking');
INSERT INTO commoninterests VALUES(22,'suesylvester','coaching');
INSERT INTO commoninterests VALUES(23,'suesylvester','dancing');
INSERT INTO commoninterests VALUES(24,'suesylvester','eating');
INSERT INTO commoninterests VALUES(25,'beckyjackson','jumproping');
INSERT INTO commoninterests VALUES(26,'beckyjackson','cheerleading');
INSERT INTO commoninterests VALUES(27,'beckyjackson','dancing');
INSERT INTO commoninterests VALUES(28,'tomholland','acting');
INSERT INTO commoninterests VALUES(29,'tomholland','golf');
INSERT INTO commoninterests VALUES(30,'tomholland','skating');

