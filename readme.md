# ms-studentHelp
Student support platform <br />

## Installation

### Windows
- make sur to have wamp installed (or a mysql server)
- install the java jdk
- you may need to install mysql connectorJ

### Linux
- install mysql
- - you may want to install phpmyadmin
- install java jdk
- install mysql java connector


### common
- Copy the repo
- Create a database:
- - Name : `ms_studenthelp`
- - Import : `ms_studenthelp.sql`

- Create a mysql user named `dummy` with password `1234` , grant him data acces on `ms_studenthelp`

## API

Our OpenAPI schema is in [`schema.yaml`](schema.yaml).
It also has a generated doc available at https://beta.bachelay.eu/ms-studentHelp

## Database

Uses a MySQL/MariaDB database.

The schema is available in [`ms_studenthelp.sql`](ms_studenthelp.sql)

The database credentials are curently hardcoded in [`ms_studenthelp/src/main/java/be/ecam/ms_studenthelp/Database/mysql/MySqlDatabase.java`](ms_studenthelp/src/main/java/be/ecam/ms_studenthelp/Database/mysql/MySqlDatabase.java)

- server on `localhost`
- database named `ms_studenthelp`
- username `dummy` with password `1234`

## Diagram
### Database
![](ms_studenthelp.png)


### Class
![](https://lucid.app/publicSegments/view/9de2afd8-5cb7-414e-8c9d-8eb506c31ad0/image.png)
