

## Configuration
Before running the application, configure the database connection in the application.properties file:
```properties
spring.datasource.url=jdbc:sqlserver://;serverName=localhost;databaseName=booksdb;encrypt=true;trustServerCertificate=true;
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.username=SA
spring.datasource.password=yourStrong(!)Password
spring.jpa.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

"# prueba-tecnica-sermaluc" 
