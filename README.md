# Desarrollo de una API REST con Spring Boot

Proyecto del curso de [OpenWebinars](https://openwebinars.net). API REST para la gestion de tareas (To-Do) desarrollada con Spring Boot 4 y Java 17.

## Tecnologias

- **Spring Boot 4.0.2**
- **Spring Security** (Basic Auth)
- **Spring Data JPA**
- **H2 Database** (base de datos embebida)
- **Lombok**
- **Springdoc OpenAPI 3.0.1** (Swagger UI)

## Requisitos

- Java 17+
- Maven

## Ejecutar el proyecto

```bash
./mvnw spring-boot:run
```

La aplicacion se levanta en `http://localhost:8080`.

## Endpoints

### Auth

| Metodo | Ruta              | Descripcion               | Auth |
|--------|--------------------|---------------------------|------|
| POST   | `/auth/register`   | Registrar un nuevo usuario | No   |

### Tareas

Todos los endpoints de tareas requieren **Basic Auth**.

| Metodo | Ruta          | Descripcion                          |
|--------|---------------|--------------------------------------|
| GET    | `/task/`      | Obtener todas las tareas del usuario |
| GET    | `/task/{id}`  | Obtener una tarea por ID             |
| POST   | `/task/`      | Crear una nueva tarea                |
| PUT    | `/task/{id}`  | Editar una tarea                     |
| DELETE | `/task/{id}`  | Eliminar una tarea                   |

### Ejemplo de registro

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "pepe",
    "email": "pepe@example.com",
    "password": "1234"
  }'
```

### Ejemplo de crear tarea

```bash
curl -X POST http://localhost:8080/task/ \
  -u pepe:1234 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Aprender Spring Boot",
    "description": "Hacer todos los cursos de Spring Boot en Openwebinars.net",
    "deadline": "2026-12-31T23:59:59"
  }'
```

## Seguridad

- Los endpoints de tareas estan protegidos con **HTTP Basic Auth**.
- Cada usuario solo puede ver, editar y eliminar sus propias tareas (`@PreAuthorize` / `@PostAuthorize`).

## Documentacion API (Swagger)

Con la aplicacion en ejecucion, accede a:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

![Swagger UI](docs/swagger.png)

## Base de datos

Utiliza **H2** como base de datos embebida. La consola H2 esta disponible en:

- http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:./db/database`
- Usuario: `sa`
- Password: _(vacio)_
