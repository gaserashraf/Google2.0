CREATE TABLE words (
word VARCHAR(15) ,
df INT , 
PRIMARY KEY(word)
);

CREATE TABLE wordDocs (
word VARCHAR(15) , 
docIndex INT ,
tf INT,
type VARCHAR(15) , 
PRIMARY KEY(word, docIndex) ,
FOREIGN KEY (word) REFERENCES words (word),
FOREIGN KEY (docIndex) REFERENCES docLink (docIndex)
);

CREATE TABLE docLink (
link VARCHAR(500), 
docIndex INT, 
PRIMARY KEY(docIndex)
);
ALTER TABLE doclink 
ADD title VARCHAR(100),
ADD description VARCHAR(500);

CREATE TABLE searchWords (
word VARCHAR(500), 
PRIMARY KEY(word)
);

