# Sincro - Modulo de clientes con Spring Boot

Modulo web del proyecto Sincro desarrollado con el framework Spring Boot,
usando Spring MVC para los controladores, Spring Data JPA para el acceso
a datos y Thymeleaf para las vistas.

Evidencia GA7-220501096-AA3-EV01
Julian Andres Albarracin Martinez - Ficha 3336228

## Estructura

```
src/main/java/co/edu/sena/sincro/
├── SincroApplication.java              clase principal
├── modelo/Cliente.java                 entidad de la tabla t_cliente
├── repositorio/ClienteRepositorio.java acceso a datos
├── servicio/ClienteServicio.java       reglas del negocio
└── controlador/ClienteControlador.java atiende las peticiones

src/main/resources/
├── application.properties              configuracion y datos de conexion
├── templates/lista.html                tabla de clientes
├── templates/formulario.html           registro y edicion
└── static/css/estilos.css              hoja de estilos
```

## Direcciones de la aplicacion

| Metodo | Direccion | Que hace |
|---|---|---|
| GET | `/clientes` | lista los clientes |
| GET | `/clientes/nuevo` | formulario vacio |
| GET | `/clientes/editar/{id}` | formulario con los datos del cliente |
| POST | `/clientes/guardar` | registra o actualiza |
| POST | `/clientes/eliminar/{id}` | elimina |

## Que aporta el framework

| Antes (JDBC y servlets) | Ahora (Spring Boot) |
|---|---|
| Clase de conexion escrita a mano | `application.properties` |
| Sentencias SQL escritas una por una | consultas generadas por Spring Data JPA |
| Servlet con `doGet` y `doPost` | controlador con `@GetMapping` y `@PostMapping` |
| Validaciones revisadas a mano | anotaciones en la entidad |
| Servidor configurado aparte | servidor embebido en el arranque |

## Validaciones aplicadas

| Campo | Regla |
|---|---|
| Nombre | obligatorio, entre 3 y 100 caracteres, solo letras y espacios |
| Telefono | obligatorio, entre 7 y 20 digitos, sin letras ni caracteres especiales |
| Correo | obligatorio, formato valido, maximo 100 caracteres, no repetido |

## Requisitos

- JDK 21
- Maven (incluido en IntelliJ)
- MySQL 8.0 con la base de datos `sincro`

## Ejecucion

Ejecutar la clase `SincroApplication` y abrir `http://localhost:8080/clientes`.

## Estandares de codificacion aplicados

- Clases en PascalCase: `Cliente`, `ClienteServicio`, `ClienteControlador`
- Metodos y variables en camelCase: `buscarPorId`, `nombreCompleto`
- Paquetes en minuscula: `co.edu.sena.sincro.repositorio`
- Separacion en capas: modelo, repositorio, servicio y controlador
- Todas las clases y metodos documentados con comentarios
