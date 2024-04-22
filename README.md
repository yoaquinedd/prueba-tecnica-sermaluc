

## Configuración

Despues de clonar el proyecto, se debe configurar el archivo application.properties con las credenciales de la base de datos en SQL server.

En "databaseName=" se debe proporcionar el nombre de la base de datos existente en SQL server
En "spring.datasource.username=" Se debe proporcionar el nombre del usuario para acceder a la base de datos
En "spring.datasource.password=" Se debe proporcionar la contraseña del usuario para acceder a la base de datos

```properties
spring.datasource.url=jdbc:sqlserver://;serverName=localhost;databaseName=;encrypt=true;trustServerCertificate=true;
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.dialect=org.hibernate.dialect.SQLServerDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=create-update
```

#  Ejecutar la aplicación

Para ejecutar la aplicacion debemos ejecutar utilizando el comando de Maven

```properties
    mvn spring-boot:run
```
O buscar la ventana lateral derecha del plugin de Maven y hacer doble click en spring-boot:run

#  Probar la aplicación

Si queremos probar las funcionalidades de la aplicacioón podemos realiazrlo desde la herramienta Postman. La aplicacion cuenta con 2 endpoints:

``` 
http://localhost:8080/generar-resultados-por-id?acoIdAsociacionComuna=
```

Al pasar el parametro **acoIdAsociacionComuna** obtendremos un excel con los calculos de las formulas asociadas a ese acoIdAsociacionComuna



Mientras que el siguiente endpoint genera todos los resultados de las formulas para todos los **acoIdAsociacionComuna**
``` 
http://localhost:8080/generar-resultados
```

### EN AMBOS CASOS LOS ARCHIVOS EXCEL SE GUARDAN EN LA CARPETA DOWNLOAD DEL USUARIO CON EL NOMBRE **_resultados.xlsx_**

## prueba-tecnica-sermaluc
