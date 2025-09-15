# Catalog API

Una API REST para la gestión de catálogos de productos construida con Spring Boot, MongoDB y Redis. Implementa una arquitectura hexagonal limpia con separación de responsabilidades y soporte para cache eficiente.

## 🏗️ Arquitectura

Esta aplicación sigue los principios de la arquitectura hexagonal (puertos y adaptadores), separando claramente las capas de dominio, aplicación e infraestructura.

### Diagrama de Arquitectura Hexagonal

```
                    ┌─────────────────────────────────────┐
                    │         EXTERNAL ACTORS             │
                    │  ┌─────────────────────────────────┐ │
                    │  │   REST API CLIENTS (HTTP)       │ │
                    │  │   Postman, Frontend Apps, etc.  │ │
                    │  └─────────────────────────────────┘ │
                    └─────────────────────────────────────┘
                                      │
                                      │ HTTP
                                      ▼
                    ┌─────────────────────────────────────┐
                    │     ADAPTERS LAYER (DRIVING)       │
                    │  ┌─────────────────────────────────┐ │
                    │  │   REST CONTROLLERS              │ │
                    │  │  - ProductController            │ │
                    │  │  - Request/Response DTOs       │ │
                    │  └─────────────────────────────────┘ │
                    └─────────────────────────────────────┘
                                      │
                                      │ Application Services
                                      ▼
                    ┌─────────────────────────────────────┐
                    │       APPLICATION LAYER            │
                    │  ┌─────────────────────────────────┐ │
                    │  │   APPLICATION SERVICES          │ │
                    │  │  - ProductService               │ │
                    │  │  - Business Logic & Validation │ │
                    │  └─────────────────────────────────┘ │
                    └─────────────────────────────────────┘
                                      │
                                      │ Domain Interfaces
                                      ▼
                    ┌─────────────────────────────────────┐
                    │         DOMAIN LAYER               │
                    │  ┌─────────────────────────────────┐ │
                    │  │   DOMAIN ENTITIES               │ │
                    │  │  - Product (Business Rules)    │ │
                    │  │  - Value Objects               │ │
                    │  │                                 │ │
                    │  │   DOMAIN SERVICES               │ │
                    │  │  - Business Logic               │ │
                    │  │                                 │ │
                    │  │   DOMAIN REPOSITORIES           │ │
                    │  │  - ProductRepository Interface  │ │
                    │  └─────────────────────────────────┘ │
                    └─────────────────────────────────────┘
                                      │
                                      │ Implementations
                    ┌─────────────────────────────────────┐
                    │   ADAPTERS LAYER (DRIVEN)          │
                    │  ┌─────────────────────────────────┐ │
                    │  │   PERSISTENCE ADAPTERS          │ │
                    │  │  - MongoProductRepository       │ │
                    │  │  - ProductDocument (MongoDB)    │ │
                    │  │                                 │ │
                    │  │   CACHE ADAPTERS                │ │
                    │  │  - Redis Cache Service          │ │
                    │  │                                 │ │
                    │  │   EXTERNAL SYSTEMS              │ │
                    │  │  - MongoDB Database             │ │
                    │  │  - Redis Cache Server           │ │
                    │  └─────────────────────────────────┘ │
                    └─────────────────────────────────────┘
```

### Flujo de Datos

```
1. HTTP Request → 2. Controller → 3. Application Service → 4. Domain Entity/Service → 5. Repository Interface
                                                                 ↓
6. Repository Implementation → 7. MongoDB/Redis → 8. Response DTO → 9. HTTP Response
```

### Tecnologías por Capa

```
┌─────────────────┬─────────────────────────────────────┬─────────────────────────────┐
│     Capa        │           Tecnología               │       Responsabilidad       │
├─────────────────┼─────────────────────────────────────┼─────────────────────────────┤
│ Presentation    │ Spring Web MVC                     │ HTTP Controllers, DTOs      │
│ Application     │ Spring Services                    │ Use Cases, Validation       │
│ Domain          │ Java POJOs + Business Logic        │ Business Rules, Entities    │
│ Infrastructure  │ Spring Data MongoDB                │ Data Persistence            │
│ Infrastructure  │ Spring Data Redis                  │ Caching Layer               │
│ Infrastructure  │ Docker                             │ Containerization            │
└─────────────────┴─────────────────────────────────────┴─────────────────────────────┘
```

## 🛠️ Tecnologías Utilizadas

- **Framework**: Spring Boot 3.5.5
- **Lenguaje**: Java 17
- **Base de Datos**: MongoDB
- **Cache**: Redis
- **Build Tool**: Gradle
- **Contenedor**: Docker & Docker Compose
- **Librerías**:
  - Spring Data MongoDB
  - Spring Data Redis
  - Spring Web
  - Project Lombok
  - Lettuce (Redis client)

## 📋 Características

### Entidad Product
- **Campos principales**: id, name, description, price, category, stock
- **Campos de auditoría**: createdAt, updatedAt, active
- **Métodos de negocio**:
  - `isAvailable()`: Verifica si el producto está disponible
  - `reduceStock(int quantity)`: Reduce el stock disponible
  - `increaseStock(int quantity)`: Incrementa el stock

### Repositorio ProductRepository
- Operaciones CRUD completas
- Búsqueda paginada
- Filtros por:
  - Estado activo/inactivo
  - Categoría
  - Nombre (búsqueda insensible a mayúsculas)
  - Combinación de categoría y nombre

### Infraestructura
- **MongoDB**: Configuración personalizada con índices en campos de búsqueda
- **Redis**: Configuración con serialización JSON para objetos complejos
- **Docker**: Entorno de desarrollo completo con compose

## 🚀 Instalación y Ejecución

### Prerrequisitos
- Java 17 o superior
- Docker y Docker Compose
- Gradle (opcional, incluido wrapper)

### Ejecutar con Docker Compose

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd catalog-api
   ```

2. **Iniciar servicios de infraestructura**
   ```bash
   docker-compose up -d
   ```

   Esto iniciará:
   - MongoDB en `localhost:27017`
   - Redis en `localhost:6379`

3. **Ejecutar la aplicación**
   ```bash
   ./gradlew bootRun
   ```

   O usando Java directamente:
   ```bash
   ./gradlew build
   java -jar build/libs/catalog-api-0.0.1-SNAPSHOT.jar
   ```

### Ejecutar sin Docker

1. **Instalar MongoDB y Redis localmente**
   - MongoDB: https://docs.mongodb.com/manual/installation/
   - Redis: https://redis.io/download

2. **Actualizar configuración** (si es necesario)
   - Modificar `src/main/resources/application.properties`

3. **Ejecutar**
   ```bash
   ./gradlew bootRun
   ```

## 📡 API Endpoints

*(Los endpoints específicos se implementarán en controladores REST futuros)*

Basado en la interfaz `ProductRepository`, la API soportará:

### Gestión de Productos
- `POST /api/products` - Crear producto
- `GET /api/products/{id}` - Obtener producto por ID
- `GET /api/products` - Listar productos (paginated)
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

### Búsqueda y Filtrado
- `GET /api/products/active` - Productos activos
- `GET /api/products?category={category}` - Por categoría
- `GET /api/products?search={name}` - Búsqueda por nombre
- `GET /api/products?category={category}&search={name}` - Combinada

### Parámetros de Paginación
- `page` - Número de página (0-based)
- `size` - Tamaño de página
- `sort` - Campo de ordenamiento

## 🗃️ Base de Datos

### MongoDB
- **Base de datos**: `mydatabase`
- **Colección**: `products`
- **Índices**:
  - `name` (para búsquedas por nombre)
  - `category` (para filtrado por categoría)
  - `active` (para filtrado por estado)

### Redis
- **Uso**: Cache para mejorar rendimiento
- **Configuración**: JSON serialization para objetos complejos
- **Timeout**: 2000ms

## 🔧 Configuración

### Variables de Entorno (application.properties)
```properties
spring.application.name=catalog-api

# MongoDB
spring.data.mongodb.uri=mongodb://root:secret@localhost:27017/mydatabase?authSource=admin
spring.data.mongodb.database=mydatabase

# Redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.timeout=2000ms
```

## 🏃‍♂️ Desarrollo

### Estructura del Proyecto
```
src/main/java/com/jeisson/catalog_api/
├── application/          # Capa de aplicación
│   ├── dto/             # Data Transfer Objects
│   └── service/         # Servicios de aplicación
├── domain/              # Capa de dominio
│   ├── entity/          # Entidades de negocio
│   ├── repository/      # Interfaces de repositorio
│   └── service/         # Servicios de dominio
│       └── usecase/     # Casos de uso
├── infrastructure/      # Capa de infraestructura
│   ├── config/          # Configuraciones
│   └── entity/          # Entidades de infraestructura
└── shared/              # Utilidades compartidas
```

### Testing
```bash
./gradlew test
```

### Build
```bash
./gradlew build
```

## 🤝 Contribución

1. Fork el proyecto
2. Crear rama para feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

**Desarrollado con ❤️ usando Spring Boot**
