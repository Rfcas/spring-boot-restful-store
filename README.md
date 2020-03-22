## Spring boot restful Store application. Using H2 database + liquibase

This REST application contains a Spring boot application that that is able to process products and orders.
For further information on endpoints please check API section.


#### API
Swagger is configured on the application, you can view the API using the following url:
`http://localhost:8080/swagger-ui.html`

There are two controllers, Product and Order

##### Product Controller:
1. Create product
2. List products
3. Update product
4. Delete product

##### Order Controller:
1. Create order
2. List orders

#### Building the application

In order to build the application just do a maven clean install
`mvn clean install`

To run the application execute the following command:
`mvn spring-boot:run -Dspring-boot.run.profiles=dev`


#### Optional - Using Docker and Docker Composer

It is also possible to build and run the application using Docker. In order to do that you can follow these steps:

`mvn clean install`

`mvn package -Pdocker`

`cd /src/main/resources/docker`

`docker-compose up`

Application is starting on port 8080.

In order to stop you can use

`docker-compose down`


#### Optional - Using only Docker

`mvn clean install`

`mvn package -Pdocker`

`docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 8080:8080 -t store:0.0.1-SNAPSHOT`

