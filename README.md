# java-test-liverpool

Proyecto de evaluación técnica con arquitectura de microservicios en Spring Boot.

Incluye:

- `customer-service`: CRUD de clientes.
- `order-service`: CRUD de órdenes con validación contra `customer-service`.
- `api-gateway`: entrada única para enrutar requests hacia customers y orders.
- PostgreSQL como base de datos.
- Docker y Docker Compose para ejecución local.
- Despliegue en Render.

## Tecnologías

- Java 17
- Spring Boot 3.5.x
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Cloud Gateway
- PostgreSQL
- Docker
- Docker Compose
- Render

## Arquitectura

```text
Client / Postman / Browser
        |
        v
api-gateway :8080
   |              |
   v              v
customer-service  order-service
:8081             :8082
   |              |
   v              v
PostgreSQL        PostgreSQL
```

## Base de Datos

En local se usan dos bases de datos separadas mediante Docker Compose:

- `customer_db`
- `order_db`

En Render se puede usar una sola base compartida por limitaciones del plan gratuito. En ese caso, ambos servicios crean sus tablas dentro de la misma base:

- `customers`
- `orders`

Ambas bases son PostgreSQL.

## Servicios

### customer-service

Servicio responsable de la gestión de clientes.

Endpoints principales:

| Método | Endpoint |
|---|---|
| POST | `/api/v1/customers` |
| GET | `/api/v1/customers` |
| GET | `/api/v1/customers/{id}` |
| PUT | `/api/v1/customers/{id}` |
| DELETE | `/api/v1/customers/{id}` |

Reglas principales:

- Valida campos requeridos.
- Valida formato de email.
- No permite emails duplicados.
- Devuelve `409 Conflict` si el email ya existe.
- Devuelve `404 Not Found` si el customer no existe.

### order-service

Servicio responsable de la gestión de órdenes.

Endpoints principales:

| Método | Endpoint |
|---|---|
| POST | `/api/v1/orders` |
| GET | `/api/v1/orders` |
| GET | `/api/v1/orders/{id}` |
| PUT | `/api/v1/orders/{id}` |
| DELETE | `/api/v1/orders/{id}` |

Reglas principales:

- Valida campos requeridos.
- Valida que `quantity >= 1`.
- Valida que `price >= 0.01`.
- Antes de crear o actualizar una orden, valida que el `customerId` exista en `customer-service`.
- Devuelve `404 Not Found` si el customer no existe.

### api-gateway

Servicio de entrada única para consumir los microservicios.

Rutas:

| Ruta | Servicio destino |
|---|---|
| `/api/v1/customers/**` | `customer-service` |
| `/api/v1/orders/**` | `order-service` |

El gateway no contiene lógica de negocio. Su responsabilidad principal es enrutar las peticiones hacia el microservicio correspondiente.

## Ejecución Local

Para facilitar el despliegue local, el proyecto cuenta con un archivo `docker-compose.yml` en la raíz del proyecto.

Desde la raíz del proyecto, ejecutar:

```bash
docker compose up --build
```

Para apagar los servicios:

```bash
docker compose down
```

Si también quieres eliminar los volúmenes de base de datos:

```bash
docker compose down -v
```

Si desea eliminar todo (volumenes e imagenes)
```bash
docker compose down --rmi local -v
```

## URLs Locales

Usando API Gateway:

```text
http://localhost:8080/api/v1/customers
http://localhost:8080/api/v1/orders
```

Servicios directos:

```text
http://localhost:8081/swagger-ui/index.html
http://localhost:8082/swagger-ui/index.html
```

Health checks:

```text
http://localhost:8080/actuator/health
http://localhost:8081/actuator/health
http://localhost:8082/actuator/health
```

## Flujo de Prueba Local

### 1. Crear customer

```bash
curl -X POST http://localhost:8080/api/v1/customers \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Emmanuel",
    "middleName": "David",
    "paternalLastName": "Martinez",
    "maternalLastName": "Vidal",
    "email": "emmanuel@example.com",
    "shippingAddress": "Nicaragua 39"
  }'
```

Respuesta esperada:

```text
201 Created
```

Guardar el `customerId` recibido para crear órdenes asociadas.

### 2. Listar customers

```bash
curl http://localhost:8080/api/v1/customers
```

### 3. Crear order con customer existente

```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productCode": "123",
    "customerId": 1,
    "quantity": 1,
    "price": 0.01
  }'
```

Respuesta esperada:

```text
201 Created
```

### 4. Listar orders

```bash
curl http://localhost:8080/api/v1/orders
```

### 5. Probar customer inexistente en order

```bash
curl -X POST http://localhost:8080/api/v1/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productCode": "123",
    "customerId": 99999,
    "quantity": 1,
    "price": 0.01
  }'
```

Respuesta esperada:

```text
404 Not Found
```

### 6. Probar email duplicado

Ejecutar dos veces el mismo request de creación de customer con el mismo email.

Respuesta esperada en el segundo intento:

```text
409 Conflict
```

## Variables de Entorno

### customer-service

```env
PORT=8081
SPRING_DATASOURCE_URL=jdbc:postgresql://customer-db:5432/customer_db
SPRING_DATASOURCE_USERNAME=app_user
SPRING_DATASOURCE_PASSWORD=user
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_FORMAT_SQL=true
```

### order-service

```env
PORT=8082
SPRING_DATASOURCE_URL=jdbc:postgresql://order-db:5432/order_db
SPRING_DATASOURCE_USERNAME=app_user
SPRING_DATASOURCE_PASSWORD=user
CUSTOMER_SERVICE_URL=http://customer-service:8081
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_FORMAT_SQL=true
```

### api-gateway

```env
PORT=8080
CUSTOMER_SERVICE_URL=http://customer-service:8081
ORDER_SERVICE_URL=http://order-service:8082
```

## URLs en Render

Servicios directos:

```text
customer-service: https://java-test-liverpool.onrender.com
order-service:    https://java-test-liverpool-1.onrender.com
```

Gateway:

```text
api-gateway: https://liverpool-gateway.onrender.com
```

Consumo mediante gateway:

```text
https://liverpool-gateway.onrender.com/api/v1/customers
https://liverpool-gateway.onrender.com/api/v1/orders
```

## Variables de Entorno en Render

### customer-service

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<database>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_FORMAT_SQL=true
```

### order-service

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/<database>
SPRING_DATASOURCE_USERNAME=<username>
SPRING_DATASOURCE_PASSWORD=<password>
CUSTOMER_SERVICE_URL=https://java-test-liverpool.onrender.com
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_JPA_SHOW_SQL=false
SPRING_JPA_FORMAT_SQL=true
```

### api-gateway

```env
CUSTOMER_SERVICE_URL=https://java-test-liverpool.onrender.com
ORDER_SERVICE_URL=https://java-test-liverpool-1.onrender.com
```

> Nota: En Render no es necesario declarar manualmente `PORT`, ya que Render lo inyecta automáticamente.

## Docker Compose

El archivo `docker-compose.yml` levanta los siguientes servicios:

- `customer-db`
- `order-db`
- `customer-service`
- `order-service`
- `api-gateway`

También crea una red interna para que los servicios puedan comunicarse usando sus nombres de contenedor:

```text
customer-service
order-service
customer-db
order-db
api-gateway
```

Por ejemplo, `order-service` consume `customer-service` usando:

```text
http://customer-service:8081
```

Y `api-gateway` enruta hacia:

```text
http://customer-service:8081
http://order-service:8082
```

## Swagger

Swagger está disponible directamente en cada microservicio:

```text
customer-service:
http://localhost:8081/swagger-ui/index.html

order-service:
http://localhost:8082/swagger-ui/index.html
```

En Render:

```text
customer-service:
https://java-test-liverpool.onrender.com/swagger-ui/index.html

order-service:
https://java-test-liverpool-1.onrender.com/swagger-ui/index.html
```

## Postman

La colección de Postman se encuentra en la carpeta `postman/`.

Archivos incluidos:

- `Liverpool-exam.postman_collection.json`
- `local.postman_environment.json`
- `render.postman_environment.json`

Para probar el proyecto:

1. Importar la colección en Postman.
2. Importar el environment `local` o `render`.
3. Seleccionar el environment correspondiente.
4. Ejecutar los requests desde la colección.

Pruebas recomendadas:

- Crear customer.
- Listar customers.
- Obtener customer por id.
- Actualizar customer.
- Eliminar customer.
- Validar email duplicado.
- Crear order con customer existente.
- Crear order con customer inexistente.
- Listar orders.
- Obtener order por id.
- Actualizar order.
- Eliminar order.

## Códigos de Respuesta Esperados

| Caso | Código esperado |
|---|---|
| Crear customer correctamente | `201 Created` |
| Crear order correctamente | `201 Created` |
| Listar recursos | `200 OK` |
| Actualizar recurso | `200 OK` |
| Eliminar recurso | `204 No Content` |
| Customer no encontrado | `404 Not Found` |
| Order no encontrada | `404 Not Found` |
| Customer inexistente al crear order | `404 Not Found` |
| Email duplicado | `409 Conflict` |
| Datos inválidos | `400 Bad Request` |

## Notas

- En local, cada microservicio tiene su propia base de datos.
- En Render, por limitaciones del plan gratuito, se puede compartir una misma base PostgreSQL entre servicios.
- `api-gateway` solo enruta requests; no contiene lógica de negocio.
- `order-service` depende de `customer-service` para validar que el customer exista antes de crear o actualizar una orden.
- Hibernate crea o actualiza las tablas automáticamente usando `SPRING_JPA_HIBERNATE_DDL_AUTO=update`.