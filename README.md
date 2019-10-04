# Java Spring-Blog
A simple blog Java example project made with Spring boot / Hibernate for Codeup students.

- Implementing Materialize CSS Framework
- Spring Boot
- Hibernate ORM + JPA
- Thymeleaf
- MySQL
 
### Libraries and Utilities Applied

- FullCalendar.js
- Moments.js
- jQuery
- WebSockets
- Joda Time
- User Icon Generator [link](https://github.com/fmendozaro/user-icon-generator)

### First steps

1. Create an `application.properties` file with valid credentials for a MySQL connection, use the `application.properties.example` as a template.
1. If you want to use the seeder file leave the `app.env=dev` property, change it to: `app.env=prod` or any value that makes sense to you when you don't want to seed the database.

### Some General Examples

In this project you will find some examples for:
- A complex JPA `joinTable` relationship where users can send FriendRequests between them and each one has a status to check if it's been sent, approved or rejected. See JPA model [here](https://github.com/fmendozaro/spring-blog/blob/master/src/main/java/com/fer_mendoza/blog/models/FriendList.java)
- A small `Enum` class that contains a list of friendship statues. See model [here](https://github.com/fmendozaro/spring-blog/blob/master/src/main/java/com/fer_mendoza/blog/models/FriendStatus.java) 