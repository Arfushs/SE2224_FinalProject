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
insert into visits(visitID,username,country_name,city_name,season,feature,comment,rating)
 values(0,"Sheldon","Turkey","Izmir","Summer","Food","Great City!!",5);
 insert into visits(visitID,username,country_name,city_name,season,feature,comment,rating)
 values(1,"Sheldon","Turkey","Manisa","Winter","History","Great City!!",4);
 insert into visits(visitID,username,country_name,city_name,season,feature,comment,rating)
 values(2,"Sheldon","Turkey","Istanbul","Summer","Food","Great City!!",5);
 insert into visits(visitID,username,country_name,city_name,season,feature,comment,rating)
 values(3,"Sheldon","Turkey","Edirne","Spring","Food","Great City!!",5);
 
select * from visits
s



