const express = require('express');
var mysql = require('mysql');
//const bodyParser = require("body-parser");
const app = express();
app.use(express.json());
var cors = require('cors');
app.use(cors())
app.listen(5000, () => {
    console.log('Server initiated succesfully at port 5000');
});

let myConnention = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "gaser011100",
    database: "searchindex",
    multipleStatements: true
});

myConnention.connect(err => {
    if (!err) {
        console.log("Database is connected!");
    } else {
        console.log("Database connection failed!");
    }
})
app.get("/", async(req, res) => {
    let sql = ` SELECT word FROM searchwords `;
    myConnention.query(sql, async(error, results, fields) => {
        if (error) res.send(error);
        if (!results[0]) {
            res.json("Not Found");
        } else {
            res.json(results);
        }
    });
})
app.get("/:word", async(req, res) => {
    // Check if the word already exists to avoid key constraint
    let sql = ` SELECT word FROM searchwords WHERE word='${req.params.word}'; `
    myConnention.query(sql, async(error, results, fields) => {
        if (error) res.send(error);
        // If not exit in the old search then insert it
        if (!results[0]) {
            sql = ` INSERT INTO searchwords VALUES ('${req.params.word}'); `
            myConnention.query(sql, async(error) => {
                if (error) res.send(error);
            });
        }
    });

    sql = `SELECT link , title, description
    FROM docLink
    JOIN wordDocs
    ON docLink.docIndex=wordDocs.docIndex
    WHERE word='${req.params.word}'
    ;`
    myConnention.query(sql, async(error, results, fields) => {
        if (error) res.send(error);
        if (!results[0]) {
            res.json("Not Found");
        } else {
            res.json(results);
        }
    });
})