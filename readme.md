# ms-studentHelp
Student support platform <br />
Class Diagram : ![](https://lucid.app/publicSegments/view/9de2afd8-5cb7-414e-8c9d-8eb506c31ad0/image.png)

## Database

Uses a MySQL/MariaDB database.

The schema is available in [`ms_studenthelp/ms_studenthelp.sql`](ms_studenthelp/ms_studenthelp.sql)

The database credentials are curently hardcoded in ms_studenthelp/src/main/java/be/ecam/ms_studenthelp/Database/mysql/MySqlDatabase.java

- server on `localhost`
- database named `ms_studenthelp`
- username `dummy` with password `1234`

## API

Our OpenAPI schema is in [`schema.yaml`](schema.yaml).
It also has a generated doc available at https://beta.bachelay.eu/ms-studentHelp
