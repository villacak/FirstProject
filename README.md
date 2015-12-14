# FirstProject

This is a very simple project, that has four services that are
Two for products and two for prices.
For for each type we have one that search by a single ID and other that search from an id list.

E.g. for URL's

Price

POST

*/rest/v1/price/detailsById/1000*


GET

*/rest/v1/price/listDetailsById?ids="1000"&ids="1002"*


Product

POST

*/FirstProject/rest/v1/product/detailsById/1000*

GET

*/FirstProject/rest/v1/product/listDetailsById?ids="1000"&ids="1002"*



This is a Maven project, that uses Jersey 2.22.1, Jackson, Jettison and JPA 2.6
For tests is JUnit and EasyMock.

If you be running the project from Eclipse the URL should start with
*http://localhost:8080/FirstProject*

If from the mvn install, mean from the .war file then the URL should start with
*http://localhost:8080/FirstProject-1*

As per Jersey 2.0+ (JAX-RS 2.0), most of REST configuration is made on the Application class instead web.xml.
The application server used is the Tomcat 8.0.30, the database used is the MySQL.
The config files for Tomcat are into the *files/TomcatConfig* folder within the project, those files should be copied to the <CATALINA_HOME>/conf directory.
To generate the DB Schema is just run the target_test.sql file that's into the *files/MySQL* folder within the projec.
