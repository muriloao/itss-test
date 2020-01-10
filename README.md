# ItssTest
A implementation of the ITSS test


## Tecnologies
- Eureka Netflix Server/Client
- Spring Boot microservices
- Zuul Gateway
- Postgres
- Docker
- Docker-compose
- Angular app

How to run this example :

```sh
## build docker images
mvn clean install

##should display three freshly built docker images
docker images

## (OPTIONAL) config your database credentials in .env

##start up all instances
docker-compose up
```

Access Frontend app
http://localhost:4200
USER: admin
PASSWORD: 123456

Access Eureka Server
http://localhost:8761

Access Zuul Gateway
http://localhost:8080/routes

Access Swagger Documentation
http://localhost:8080/swagger-ui.html

