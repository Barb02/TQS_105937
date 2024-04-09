## How to run application

Run db first:

`docker run --name mysql5tqs -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=tqsdemo -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo -p 33000:3306 -d mysql/mysql-server:5.7`

Then run spring-boot:

`mvn spring-boot:run`

## Access

Website: `localhost:8080/`

Swagger: `http://localhost:8080/swagger-ui/index.html#/`

API: `localhost:8080/api/v1/<endpoint>`
