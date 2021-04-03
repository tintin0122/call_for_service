# CALL FOR SERVICE
A website that can compare the price of a product from different resources (Tiki, Lazada, Shopee...)

- System Design
- Software Principle
- Project structure & application configuration
- ERD Diagram
- How to run application
- API Document

### System Design

![alt text](https://github.com/tintin0122/call_for_service/blob/c7c79eef7ac8bc35d9c8fa43b01a9655b1f8a456/images/call4service.jpg)

##### Discovery Service
The service is a database populated with information on how to dispatch requests to microservice instances.

##### Gateway Service
The service encapsulates the internal system architecture and provides an API that is tailored to each client. 

##### Product Service
This is core business of Smart Choice system. It call to Crawler service when client search for product by using name and store these searching informations.
When client get detail information of specific product, this service will retrieve from Redis database to avoid make a request to Crawler Service.

##### Crawler Service
This service will responsible for searching product on external resources(Tiki,Lazada,Shopee,...) and store these product in database.

##### Audit Service
logging all client activities.

##### Identity Provider
An identity provider is a service that stores and verifies user identity. (e.g: Keycloak)




### Software Principle
##### CLEAN Architecture
Clean architecture is a software design philosophy that separates the elements of a design into ring levels. An important goal of clean architecture is to provide developers with a way to organize code in such a way that it encapsulates the business logic but keeps it separate from the delivery mechanism. 

The main rule of clean architecture is that code dependencies can only move from the outer levels inward. Code on the inner layers can have no knowledge of functions on the outer layers. The variables, functions and classes (any entities) that exist in the outer layers can not be mentioned in the more inward levels. It is recommended that data formats also stay separate between levels.

![alt text](https://github.com/tintin0122/call_for_service/blob/69ed2992168e5ccf524e2f6e74c5136dc1332a4c/images/call4service_architecture.jpg)

![alt text](https://github.com/tintin0122/call_for_service/blob/69ed2992168e5ccf524e2f6e74c5136dc1332a4c/images/call4service_architecture_2.jpg)

##### Separation of concerns
Separation of concerns is a principle used in programming to separate an application into units, with minimal overlapping between the functions of the individual units.

##### KISS: Keep It Simple, Stupid
Keep it simple, stupid (KISS) is a design principle which states that designs and/or systems should be as simple as possible. Wherever possible, unnecessary complexity should be avoided in a system.

##### DRY: Don't Repeat Yourself
This is a principle of software development that aims at reducing the repetition of patterns and code duplication in favor of abstractions and avoiding redundancy.

##### SOLID
The SOLID Principles are five principles of Object-Oriented class design. They are a set of rules and best practices to follow while designing a class structure

* The Single Responsibility Principle (S): A class should do one thing and therefore it should have only a single reason to change.
* The Open-Closed Principle (O): Classes should be open for extension and closed to modification.
* The Liskov Substitution Principle (L): Subclasses should be substitutable for their base classes.
* The Interface Segregation Principle (I): Keeping things separated, and the Interface Segregation Principle is about separating the interfaces.
* The Dependency Inversion Principle (D): Our classes should depend upon interfaces or abstract classes instead of concrete classes and functions.




### Project structure & application configuration
| application       | Port          |
| ------------------| ------------- |
| audit-service     | 8082          |
| crawler-service   | 8083          |
| discovery-service | 8761          |
| gateway-service   | 8080          |
| product-service   | 8081          |
| sandbox-service   | 8180          |
| postgre           | 5434          |
| mongo             | 27017         |
| redis             | 6379          |
| rabbitmq          | 15672         |



![alt text](https://github.com/tintin0122/call_for_service/blob/69ed2992168e5ccf524e2f6e74c5136dc1332a4c/images/database.jpg)
##### Entities Layer (domain module)
The layer that describes the universal behavior of a thing that can be used across all applications. This can be as simple as a data structure, to a class with methods and behavior - as long as that behavior is the same regardless of what application it is injected in.

##### Use Cases Layer(usecase module)
The layer that contains application specific business rules, where you can define how your application interacts with the entities layer. This defines business processes and workflows. Depends on entities layer, but also defines various contracts that will be implemented by external layers.

##### Interface Adapter Layer (Rest, Lookup, Repository module)
The layer which implements various interfaces define in the use case layer. As we outer layers, we start to move towards more high level systems, utilizing frameworks to implement a lot of the heavy lifting for our functionality, without tying us into a specific solution. This is great that we separate out this into its own layer because in the event we change the structure of our data, it won't have a large affect on the structure of the application itself.
- rest: handles incoming HTTP requests, invoke approriate use-case and send response back to the caller.
- repository: contains configuration and business logic to manipulate with database.
- lookup: make request to external resource for getting data.

##### Configuration Layer (configuration module)
The layer that brings all of the code components together and exposes them for usage. This is where you'll be asserting how the system should work from a technical point of view. This would be the layer in which you would apply your dependency injection to wire up your code.


### ERD Diagram
![alt text](https://github.com/tintin0122/smart_choice/blob/main/images/smart_choice_class.jpg?raw=true)

*Note: For this assignment, I simplify the schema to one table(Product table) in product-service.


### How to run the application
- Install [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- Install [Docker](https://www.docker.com/products/docker-desktop)
- Install [Maven](https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/)
- Clone the smart_choice repository
- Open terminal, navigate into the root directory of this project and run command: "docker-compose up"
- Configuration RabbitMQ
  - Go to: http://localhost:15672/ and login with account: rabbitmq/rabbitmq
  ![alt text](https://github.com/tintin0122/smart_choice/blob/main/images/rabbitmq_login.jpg?raw=true)
  - Add a new Queue
  ![alt text](https://github.com/tintin0122/smart_choice/blob/main/images/rabbitmq_queue.jpg?raw=true)
  - Add a new Exchange
  ![alt text](https://github.com/tintin0122/smart_choice/blob/main/images/rabbitmq_exchange.jpg?raw=true)
  - Add binding to queue
  ![alt text](https://github.com/tintin0122/smart_choice/blob/main/images/rabbitmq_binding.jpg?raw=true)

- Run services: (note: make sure starting discovery-service at first)
  - discovery-service, audit-service, gateway-service, sandbox-service: mvn clean install && java -jar target/<service-name>-1.0.0.jar
  - product-service, crawler-service: mvn clean install && java -jar configuration/target/configuration-1.0.0.jar

### API Document
- Searching product by name: (iphone, vinfast, shoe) http://localhost:8080/customers/{customer-id}/products/search?product-name={search-keyword}
  - example: curl --location --request GET 'http://localhost:8080/customers/user001/products/search?product-name=iphone' \
--header 'x-user: user001'
- Go to product detail: http://localhost:8080/customers/{customer-id}/products/{product-id}
  - example: curl --location --request GET 'http://localhost:8080/customers/user001/products/1202' \
--header 'x-user: user001' 
- Get user activities:
  - curl --location --request GET 'http://localhost:8080/audit/customer-activity'
