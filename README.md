# InAdvance
Este microservicio maneja el registro de usuarios. Valida el correo electrónico y la contraseña del usuario, y devuelve un objeto de usuario con información adicional si el registro es exitoso.

### Versiones de librerias implementadas

- JDK : 17
- Maven : 3.9.2
- Spring Boot : 3.2.5
- JJWT : 0.12.5
- Lombok : 1.18.32
- Mapstruct : 1.5.5.Final
- Springdoc-openapi : 2.5.0
- H2 : 2.2.224

### Objetivo
- CRUD de registro de usuarios
- Habiliar la aunteticacion en la aplicacion
- Generar un Token JWT
- Validar el Token recibido

## Endpoints

### Crear Usuario
**POST** `/api/v1/user`

#### Cuerpo de la Solicitud
- Objeto `UserDto` que representa al usuario con los nuevos datos.

```json
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "countrycode": "57"
        }
    ]
}
```

### Obtener Usuario por Correo Electrónico
**GET** `/api/v1/user`

#### Parámetros de la Solicitud
- `email`: Correo electrónico del usuario a buscar.

### Obtener Todos los Usuarios
**GET** `/api/v1/user/all`

### Actualizar Usuario
**PUT** `/api/v1/user`

#### Cuerpo de la Solicitud
- Objeto `UserDto` que representa al usuario a crear.

### Eliminar Usuario
**DELETE** `/api/v1/user`

#### Parámetros de la Solicitud
- `email`: Correo electrónico del usuario a eliminar.

## Reglas de Validación Adicionales

- **Correo Electrónico:** Debe seguir una expresión regular para validar su formato correcto (`aaaaaaa@dominio.cl`).
- **Clave:** Debe seguir una expresión regular configurada para validar su formato correcto, 
debe contener solo letras y números, y siempre terminar en un número.

> **Nota:** La expresión regular de validación se encuentra en el archivo .properties y puede ser modificada según sea necesario.

## Base de Datos H2 en Memoria

El microservicio utiliza una base de datos H2 en memoria para almacenar los datos de manera temporal durante el tiempo de ejecución. A continuación se muestra la configuración relacionada:

- **URL de la Base de Datos:** `jdbc:h2:mem:testdb`
- **Usuario:** `inadvance`
- **Contraseña:** `idadvance`
- **Consola H2:** La consola de H2 está habilitada y se puede acceder mediante el siguiente enlace: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Ten en cuenta que al tratarse de una base de datos en memoria, los datos se perderán una vez que la aplicación se detenga o reinicie.

## Autenticación

### Uso del Proyecto

En el proyecto se encuentra un archivo de Postman en la carpeta ***/doc*** con los requests a ejecutar. Se recomienda seguir el siguiente flujo:

1. **Login:** Ejecuta primero el request de login para obtener el token JWT necesario para las solicitudes siguientes. 
2. **Solicitudes Siguientes:** Después de obtener el token JWT, puedes ejecutar los demás requests del Postman para probar los diferentes endpoints del microservicio.

> **Nota:** No se esta realizando la validacion real del usuario, solo se usa para generar el token. El token JWT se agrega automaticamente en el encabezado de autorización (Bearer token) en las solicitudes posteriores para autenticarlas correctamente.

### Swagger

Ejecuta la aplicación y dirígete a [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) en el navegador.
