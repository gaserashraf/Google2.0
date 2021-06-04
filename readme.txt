First setting the database
We shall use MySQL, open MySQL workbench then Create database, call it "searchIndex" and run the db.sql in the db directory.
Secondly you need to download and include jsoup and mysql-connector-java libraries
To run the crawler, ..................
Then to run the indexer go to the Indexer & SearchIndex directory and update the passwords in the InvertedIndex.java file then run it
using the command "java .\InvertedIndex.java".
Now you searchIndex is filled.
go to the Backend directory then run the server after installing the dependencies using the command npm start
To run the interface go to the Frontend directory then install the dependencies then run it using the command npm start.