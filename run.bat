set user=root
set password=password

mysql --user=%user% --password=%password% < createDB.sql
mysql --user=%user% --password=%password% prices < data.sql
call gradlew build
XCopy "%~dp0build\libs\shops.war" "%CATALINA_HOME%\webapps"
call "%CATALINA_HOME%\bin\startup.bat"

pause