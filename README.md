# Java Spring Boot Blog
A simple blog application example project made with Spring Boot for Codeup students.

- Implementing Materialize CSS Framework
- Spring Boot 2.1.x
- Hibernate ORM + JPA
- Thymeleaf
- MySQL
 
### Libraries and Utilities Applied

- FullCalendar.js [link](https://fullcalendar.io/)
- Moments.js [link](https://momentjs.com/)
- jQuery [link](https://jquery.com/)
- WebSockets
- Joda Time [link](https://www.joda.org/joda-time/)
- User Icon Generator [link](https://github.com/fmendozaro/user-icon-generator)

### First steps

1. Clone this repo locally.
1. Create an `application.properties` file with valid credentials for a MySQL connection, use the `application.properties.example` as a template.
1. Add any API keys needed to test the full functionality.
1. If you want to use the seeder file leave the `seed.db=true` property, change it to: `seed.db=false` or any value that makes sense to you when you don't want to seed the database.

### Some General Examples

In this project you will find some examples for:
- A complex JPA `joinTable` relationship where users can send FriendRequests between them and each one has a status to check if it's been sent, approved or rejected. See JPA model [here](https://github.com/fmendozaro/spring-blog/blob/master/src/main/java/com/fer_mendoza/blog/models/FriendList.java)
- A small `Enum` class that contains a list of friendship statues. See model [here](https://github.com/fmendozaro/spring-blog/blob/master/src/main/java/com/fer_mendoza/blog/models/FriendStatus.java) 
