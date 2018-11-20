# Spring-boot batch insert examples    

Example results are from second run after database start. Better results can 
be seen as more and more similar operations are executed against a database.

#### DB2

Using DB2 express-c docker container with default configuration. 

     docker run --name db2 -p 50000:50000 -e DB2INST1_PASSWORD=changeit -e LICENSE=accept -d ibmcom/db2express-c db2start

Result

    {
        "rows": 10000,
        "duration": 1055,
        "msPerRow": 0.106,
        "rowsPerSecond": 9479
    }

#### Postgresql

Using official Postgresql docker container.

    docker run --name postgres -e POSTGRES_PASSWORD=changeit -d -p 5432:5432 postgres

Result

    {
        "rows": 10000,
        "duration": 512,
        "msPerRow": 0.051,
        "rowsPerSecond": 19531
    }

#### MySQL

Using official docker container.

    docker run --name mysql -e MYSQL_ROOT_PASSWORD=changeit -d -p 3306:3306 mysql

Result 

    {
        "rows": 10000,
        "duration": 618,
        "msPerRow": 0.062,
        "rowsPerSecond": 16181
    }
