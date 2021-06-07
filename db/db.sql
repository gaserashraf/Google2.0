CREATE DATABASE `SearchIndex`;
USE `SearchIndex`;
CREATE TABLE words (
word VARCHAR(500) ,
df INT , 
PRIMARY KEY(word)
);

CREATE TABLE docLink (
link VARCHAR(500), 
docIndex INT, 
title VARCHAR(100),
description VARCHAR(500),
PRIMARY KEY(docIndex)
);

CREATE TABLE wordDocs (
word VARCHAR(500) , 
docIndex INT ,
tf INT,
type VARCHAR(15) , 
PRIMARY KEY(word, docIndex) ,
FOREIGN KEY (word) REFERENCES words (word),
FOREIGN KEY (docIndex) REFERENCES docLink (docIndex)
);


CREATE TABLE searchWords (
word VARCHAR(500), 
PRIMARY KEY(word)
);

