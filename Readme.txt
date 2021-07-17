1) Must be installed:
	- MySQL 8.0.18
	- TomCat 9.0.29
	- Java 8

2) Must be created environment variables for Java and TomCat:
	- CATALINA_HOME
	- JAVA_HOME

3) Add information to environment variable path about Java and MySql
	Examples:
		C:\Program Files\Java\jdk1.8.0_201\bin;
		C:\Program Files\MySQL\MySQL Server 8.0\bin

4) Change if you need user and password parametrs for database in:
	a) ..\dao\src\main\resources\application.properties
	b) run.bat
	(default user=root, password=password)
									
5) Execute run.bat