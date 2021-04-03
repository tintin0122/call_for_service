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

##### Call For Service
The service will responsible for search events based on time range or responder. 

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
| call-for-service  | 8080          |
| postgre           | 5434          |
| sonar             | 9000          |



![alt text](https://github.com/tintin0122/call_for_service/blob/b202faa4803ad1d63f79cb3887298cc15e77862e/images/project_structure.jpg)
##### Entities Layer (domain module)
The layer that describes the universal behavior of a thing that can be used across all applications. This can be as simple as a data structure, to a class with methods and behavior - as long as that behavior is the same regardless of what application it is injected in.

##### Use Cases Layer(usecase module)
The layer that contains application specific business rules, where you can define how your application interacts with the entities layer. This defines business processes and workflows. Depends on entities layer, but also defines various contracts that will be implemented by external layers.

##### Interface Adapter Layer (Rest, Repository module)
The layer which implements various interfaces define in the use case layer. As we outer layers, we start to move towards more high level systems, utilizing frameworks to implement a lot of the heavy lifting for our functionality, without tying us into a specific solution. This is great that we separate out this into its own layer because in the event we change the structure of our data, it won't have a large affect on the structure of the application itself.
- rest: handles incoming HTTP requests, invoke approriate use-case and send response back to the caller.
- repository: contains configuration and business logic to manipulate with database.
- lookup: make request to external resource for getting data.

##### Configuration Layer (configuration module)
The layer that brings all of the code components together and exposes them for usage. This is where you'll be asserting how the system should work from a technical point of view. This would be the layer in which you would apply your dependency injection to wire up your code.


### ERD Diagram
![alt text](https://github.com/tintin0122/call_for_service/blob/69ed2992168e5ccf524e2f6e74c5136dc1332a4c/images/database.jpg)

*Note: For this assignment, I simplify the schema to one table(Product table) in product-service.


### How to run the application
- Install [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- Install [Docker](https://www.docker.com/products/docker-desktop)
- Install [Maven](https://maven.apache.org/download.cgi?Preferred=ftp://mirror.reverse.net/pub/apache/)
- Clone the call_for_service repository
- Open terminal, navigate into the root directory of this project and run command: "docker-compose up"
- Sonarqube Dashboard:
  - Go to: http://localhost:9000 and login with account: admin/admin
  ![alt text](https://github.com/tintin0122/call_for_service/blob/b202faa4803ad1d63f79cb3887298cc15e77862e/images/sonar_login.jpg)
  - Overall project
  ![alt text](https://github.com/tintin0122/call_for_service/blob/b202faa4803ad1d63f79cb3887298cc15e77862e/images/sonar_projects.jpg)
  - Detail of Call For Service project:
  ![alt text](https://github.com/tintin0122/call_for_service/blob/b202faa4803ad1d63f79cb3887298cc15e77862e/images/sonar_issue.jpg)

- Generate coverage report:
  - mvn clean install sonar:sonar
- Run services:
  - call-for-service: mvn clean install && java -jar configuration/target/configuration-1.0.0.jar


### API Document
- customerID: 1001,1002,1003
- responderCode: OFFICER_001,OFFICER_002,OFFICER_003
- Get events by time range: (iphone, vinfast, shoe) http://localhost:8080/customers/{customer-id}/events?startTime={time}&endTime={time}&page={pageNumber}&size={sizeNubmer}&sort={field,direction}
  - example: curl --location --request GET 'localhost:8080/api/v1/customers/1002/events?startTime=2021-02-20%2017:15:50.000&endTime=2021-04-20%2017:15:50.000&sort=eventTime,asc' \
--header 'x-user: 1002'
- Get events by responder code: http://localhost:8080/customers/{customer-id}/events?responder={responderCode}&page={pageNumber}&size={sizeNubmer}&sort={field,direction}
  - example: curl --location --request GET 'localhost:8080/api/v1/customers/1002/events?responder=OFFICER_001&sort=eventTime,asc' \
--header 'x-user: 1002'
- Create event: 'http://localhost:8080/customers/{customer-id}/events
  - example: curl --location --request POST 'localhost:8080/api/v1/customers/1002/events' \
--header 'x-user: 1002' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=F644C23EF48242C368B64527373192F6' \
--data-raw '{
    "event_number": "66002",
    "event_type_code": "CMO",
    "event_time": "2021-01-20 17:15:50.000",
    "dispatch_time": "2021-01-22 17:15:02.000",
    "responder": "OFFICER_002"
}'
