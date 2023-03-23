# Covid Statistics

This is a demo application that retrieves data from Covid Statistics API : "https://api.covid19api.com/summary" and save it into database.
Have only one REST endpoint that shows data by given country code (two length String, ISO-3166).

Technologies: Build and compiled with Java 11, Spring Boot, Maven, MariaDB.

There is provided SQL script to create the Covid Statistics Schema and Table.
From [application.properties](src/main/resources/application.properties) and in [HibernateConfiguration](src/main/java/com/example/demo/configurations/HibernateConfiguration.java)
is provided configurations for MariaDB. If you want to use your own database, you need to change the used database driver.
Also provide your username and password for database connection.

To run this application, build with Java version 11 and above. Run [DemoApplication](src/main/java/com/example/demo/DemoApplication.java)