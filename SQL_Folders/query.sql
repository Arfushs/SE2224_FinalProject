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