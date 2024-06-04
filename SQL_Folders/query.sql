CREATE SCHEMA project;
USE project;

CREATE TABLE userinfo (
	userID int,
    username varchar(20),
    password varchar(20),
    PRIMARY KEY (userID)
);
CREATE TABLE visits (
	visitID int,
    username varchar(20),
    country_name varchar(23),
    city_name varchar(23),
    season varchar(10),
    feature varchar(25),
    comment varchar(50),
    rating int,
	PRIMARY KEY (visitID)
);
CREATE TABLE sharedvisits (
	shared_visitID int,
    username varchar(20),
    friend_username varchar(20)
);

INSERT INTO userinfo (userID,username,password) VALUES (0,"root","password");
INSERT INTO userinfo (userID,username,password) VALUES (1,"John","7894");
INSERT INTO userinfo (userID,username,password) VALUES (2,"Can","1234");
INSERT INTO userinfo (userID,username,password) VALUES (3,"Sheldon","gold");

insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(0,"Sheldon","Turkey","Izmir",2004,"Summer","Food","Great City!!",5);
 insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(1,"Sheldon","America","Los Angles",2007,"Winter","History","Great City!!",4);
 insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(2,"Sheldon","French","Paris",2008,"Summer","Food","Great City!!",8);
 insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(3,"Sheldon","Italy","Rome",2003,"Spring","Food","Great City!!",7);
  insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(4,"Can","Italy","Rome",2003,"Spring","Food","Great City!!",10);
 insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(5,"Can","Turkey","Istanbul",2004,"Summer","Food","Great City!!",8);
 insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(6,"Can","Japan","Kyoto",2007,"Spring","History","Great City!!",4);
 insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(7,"Can","America","Japan",2008,"Summer","Food","Great City!!",8);
 insert into visits(visitID,username,country_name,city_name,year_visited,season,feature,comment,rating)
 values(8,"Can","French","Paris",2003,"Spring","Food","Great City!!",7);

insert into sharedvisits(shared_visitID,username,friend_username) values(1,"Sheldon","Can");
insert into sharedvisits(shared_visitID,username,friend_username) values(2,"Sheldon","Can");
insert into sharedvisits(shared_visitID,username,friend_username) values(5,"Sheldon","Can");
insert into sharedvisits(shared_visitID,username,friend_username) values(6,"Sheldon","Can");
insert into sharedvisits(shared_visitID,username,friend_username) values(0,"Sheldon","Can");
 





