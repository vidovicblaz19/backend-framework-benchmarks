const { Client } = require('pg')
const express = require('express')
const app = express()
const port = 7222
const cluster = require('cluster');

function stressGarbageCollectorSort(arr) {
    if (arr.length <= 1) {
        return arr;
    }

    const pivot = arr[Math.floor(arr.length / 2)];
    const less = [];
    const equal = [];
    const greater = [];

    for (const element of arr) {
        if (element < pivot) {
            less.push(element);
        } else if (element > pivot) {
            greater.push(element);
        } else {
            equal.push(element);
        }
    }

    return stressGarbageCollectorSort(less)
        .concat(equal)
        .concat(stressGarbageCollectorSort(greater));
}

if (cluster.isPrimary) {
    // Create a worker for each CPU
    for (var i = 0; i < 2; i += 1) {
        cluster.fork();
    }
} else {
    const client = new Client({
        user: 'admin',
        password: 'admin',
        host: 'postgres',
        database: 'dev-db',
        port: 5432,
    })

    client.connect()
        .then(() => {
            app.get('/', async (req, res) => {
                const query = await client.query('SELECT * FROM \"orders\" LIMIT 10');

                const input = [
                    9, 13, 7, 25, 18, 3, 11, 30, 14, 6,
                    22, 17, 4, 28, 12, 19, 8, 15, 1, 10,
                    27, 5, 21, 16, 2, 24, 20, 23, 29, 26,
                    35, 39, 33, 51, 44, 31, 47, 36, 43, 32,
                    48, 38, 45, 34, 50, 37, 42, 40, 49, 41
                ];
                const sorted = stressGarbageCollectorSort(input);

                const out = {
                    query: query.rows,
                    sorted
                }

                res.send(out);
            })

            app.listen(port, () => {
                console.log(`Example app listening at http://localhost:${port}`)
            })

            process.on('SIGTERM', () => {
                client.end();
                server.close(() => {
                    console.log('Server closed');
                    process.exit(0);
                });
            });
        })
        .catch((error) => {
            console.error('Failed to connect to the database:', error);
        });

}

