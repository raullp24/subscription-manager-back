# 📋 Subscription Manager — Backend

API REST para gestionar suscripciones de usuario con autenticación JWT, base de datos NoSQL y notificaciones automáticas por correo electrónico.

Construido con **Spring Boot 3.4** y **Java 21**.

---

## 🚀 Tech Stack

| Capa | Tecnología |
|------|------------|
| Lenguaje | ![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white) |
| Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-6DB33F?logo=springboot&logoColor=white) |
| Seguridad | ![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white) |
| Base de datos | ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?logo=mongodb&logoColor=white) |
| Autenticación | ![JWT](https://img.shields.io/badge/JWT-jjwt%200.11.5-000000?logo=jsonwebtokens&logoColor=white) |
| Email | Spring Mail (SMTP) |
| Build | ![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white) |
| Utilidades | ![Lombok](https://img.shields.io/badge/Lombok-FF5722?logo=lombok&logoColor=white) |

---

## ✨ Características

- 🔐 **Autenticación JWT** con registro, login y filtros personalizados
- 👤 **Gestión de usuarios** con contraseñas encriptadas (BCrypt)
- 📦 **CRUD de suscripciones** con periodicidad mensual/anual y auto-renovación
- 📊 **Cálculo automático** de fechas de expiración según periodicidad
- ❌ **Cancelación inteligente** de suscripciones con renovación automática
- 📧 **Notificaciones por email** programadas (cron diario) 5 días antes del vencimiento
- 🛡️ **Manejo global de excepciones** con respuestas JSON estructuradas
- 🌐 **CORS** configurado para el frontend Angular
- 🔑 **Variables de entorno** con soporte para `.env` (java-dotenv)

---

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas (Layered Architecture):

```
com.app.subscription_manager
├── 📁 config/          # Seguridad, JWT, CORS
├── 📁 controller/      # Endpoints REST
├── 📁 service/         # Lógica de negocio
│   ├── web/            # ⬆️ renombrado a web (controllers)
├── 📁 repository/      # Acceso a datos (MongoDB)
├── 📁 model/           # Entidades / Documentos
├── 📁 dtos/            # Objetos de transferencia
└── 📁 exception/       # Excepciones personalizadas
```

### 📂 Estructura del proyecto

```
subscription-manager-back/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
└── src/
    ├── main/
    │   ├── java/com/app/subscription_manager/
    │   └── resources/
    │       └── application.properties
    └── test/
```

---

## 🗃️ Modelo de datos

### 👤 User (colección `users`)

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | String (ObjectId) | Identificador único |
| `name` | String | Nombre |
| `surname` | String | Apellido |
| `email` | String | Email (único) |
| `password` | String | Hash BCrypt |

### 📦 Subscription (colección `subscriptions`)

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | String (ObjectId) | Identificador único |
| `userId` | String | Referencia al usuario |
| `name` | String | Nombre de la suscripción |
| `description` | String | Descripción |
| `status` | String | `active` / `cancelled` |
| `startDate` | LocalDate | Fecha de inicio |
| `endDate` | LocalDate | Fecha de fin (calculada) |
| `periodicity` | Enum | `MONTHLY` / `YEARLY` |
| `autoRenewal` | Boolean | Renovación automática |
| `price` | Double | Precio (> 0) |

---

## 🌐 API Endpoints

### 🔐 Autenticación (`/api/auth`)

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| `POST` | `/api/auth/register` | ❌ | Registrar nuevo usuario |
| `POST` | `/api/auth/login` | ❌ | Iniciar sesión y obtener JWT |
| `GET`  | `/api/auth/test` | ✅ | Probar token válido |

### 📦 Suscripciones (`/api/subscriptions`)

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| `POST` | `/api/subscriptions` | ✅ | Crear suscripción |
| `GET`  | `/api/subscriptions` | ✅ | Listar todas |
| `GET`  | `/api/subscriptions/user/{userId}` | ✅ | Listar por usuario |
| `GET`  | `/api/subscriptions/{id}` | ✅ | Obtener una por ID |
| `PUT`  | `/api/subscriptions/{id}` | ✅ | Actualizar suscripción |
| `POST` | `/api/subscriptions/{id}/cancel` | ✅ | Cancelar suscripción |

### 👤 Usuarios (`/api/users`)

| Método | Endpoint | Auth | Descripción |
|--------|----------|------|-------------|
| `PUT`  | `/api/users/{id}` | ✅ | Actualizar usuario |
| `POST` | `/api/users/change-password/{userId}` | ✅ | Cambiar contraseña |

---

## ⚙️ Configuración

### 📋 Requisitos previos

- ☕ **Java 21** o superior
- 🐘 **Gradle** (incluido el wrapper `gradlew`)
- 🍃 **MongoDB** corriendo en local o en la nube
- 📬 **Servidor SMTP** (por defecto se usa `localhost:1025`, ideal para [MailHog](https://github.com/mailhog/MailHog) o [MailDev](https://github.com/maildev/maildev))

### 🔐 Variables de entorno

Crea un archivo `.env` en la raíz del proyecto:

```env
MONGO_URI=mongodb://localhost:27017/proyecto
JWT_SECRET=tu-clave-secreta-muy-larga-y-segura-aqui
JWT_EXPIRATION=86400000
```

> Las variables se cargan automáticamente al iniciar la aplicación mediante `java-dotenv`.

### 📧 Configuración de email (desarrollo)

`application.properties` viene preconfigurado para SMTP local en el puerto `1025`:

```properties
spring.mail.host=localhost
spring.mail.port=1025
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=false
```

Para producción, modifica estos valores con un proveedor real (Gmail, SendGrid, etc.).

---

## ▶️ Ejecución

### 🏃 Modo desarrollo

```bash
# Clonar el repositorio
git clone https://github.com/raullp24/subscription-manager-back.git
cd subscription-manager-back

# Ejecutar con Gradle wrapper
./gradlew bootRun
```

> En Windows: `gradlew.bat bootRun`

La API quedará disponible en `http://localhost:8080`.

### 🏗️ Compilar para producción

```bash
./gradlew clean build
java -jar build/libs/subscription-manager-0.0.1-SNAPSHOT.jar
```

### 🧪 Ejecutar tests

```bash
./gradlew test
```

---

## 📧 Notificaciones automáticas

El servicio `SubscriptionNotificationService` ejecuta una tarea programada **todos los días a las 09:10 AM** que:

1. 🔎 Busca suscripciones que vencen **en 5 días**.
2. 📩 Envía email de **aviso de renovación** si tiene `autoRenewal = true` y periodicidad mensual.
3. ⚠️ Envía email de **aviso de expiración** si NO tiene renovación automática.

Configurable en: `src/main/java/com/app/subscription_manager/service/SubscriptionNotificationService.java:33`

```java
@Scheduled(cron = "0 10 9 * * ?")
```

---

## 🛡️ Manejo de errores

Todas las excepciones se gestionan con `GlobalExceptionHandler` y devuelven `ErrorResponseDTO`:

```json
{
  "status": 404,
  "message": "User not found: user@example.com",
  "timestamp": "2025-01-15T10:30:00"
}
```

Excepciones personalizadas:
- `UserNotFoundException`
- `UserAlreadyExistsException`
- `InvalidCredentialsException`
- `SubscriptionNotFoundException`
- `CustomException` (genérica)

---

## 🤝 Proyecto relacionado

> 🅰️ **Frontend** de esta aplicación: [subscription-manager-front](https://github.com/raullp24/subscription-manager-front)
> Aplicación Angular 21 con gráficos de Chart.js y Bootstrap 5.

---

## 📄 Licencia

Este proyecto es de uso personal. Siéntete libre de revisarlo y aprender de él.

---

<p align="center">Hecho con ☕ y Java</p>
