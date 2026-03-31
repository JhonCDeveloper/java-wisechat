# Wisechat — Sistema de Gestión con JDBC

Proyecto Java con persistencia JDBC nativa siguiendo arquitectura DAO, desarrollado para el programa SENA.

---

## 📋 Requisitos Previos

| Herramienta | Versión mínima |
|-------------|----------------|
| Java JDK    | 17+            |
| Maven       | 3.8+           |
| MySQL Server| 8.0+           |
| Git         | 2.x            |

---

## ⚙️ Configuración de la Base de Datos

### 1. Crear la base de datos

Ejecuta el script SQL incluido en el proyecto:

```bash
mysql -u root -p < database/wisechat_db.sql
```

Esto creará la base de datos `wisechat_db` con todas las tablas y relaciones.

### 2. Configurar credenciales

Edita el archivo `src/main/java/com/wisechat/util/ConexionDB.java` y ajusta:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/wisechat_db";
private static final String USUARIO  = "root";      // Tu usuario MySQL
private static final String PASSWORD = "";           // Tu contraseña MySQL
```

---

## 🚀 Compilar y Ejecutar

```bash
# Compilar el proyecto
mvn compile

# Ejecutar la clase de prueba
mvn exec:java -Dexec.mainClass="com.wisechat.main.App"

# Empaquetar
mvn package
```

---

## 🏗️ Estructura del Proyecto

```
wisechat-db/
├── database/
│   └── wisechat_db.sql          # Script de creación de la BD
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── wisechat/
│                   ├── dao/     # Interfaces e implementaciones DAO
│                   ├── model/   # POJOs / Entidades
│                   ├── util/    # ConexionDB (Singleton JDBC)
│                   └── main/    # App.java (clase de prueba)
├── pom.xml
└── README.md
```

---

## 🗂️ Entidades del Sistema

| Entidad        | Tabla         | Descripción                        |
|----------------|---------------|------------------------------------|
| `User`         | `USER`        | Usuarios del sistema               |
| `Business`     | `BUSINESS`    | Empresas administradas por usuarios|
| `Form`         | `FORM`        | Formularios de cada empresa        |
| `Question`     | `QUESTIONS`   | Preguntas de cada formulario       |
| `Response`     | `RESPONSES`   | Respuestas a preguntas             |
| `ActionsPlan`  | `ACTIONS_PLAN`| Planes de acción por empresa       |
| `Message`      | `MESSAGES`    | Mensajes del sistema/usuario       |

---

## 📦 Empaquetado (Protocolo SENA)

```bash
# Generar ZIP para entrega
Compress-Archive -Path . -DestinationPath "../NOMBREAPELLIDO_AA2_EV01.zip"
```

---

## 🔗 Tecnologías

- **Java 17** — Lenguaje principal
- **JDBC Nativo** — Persistencia con `PreparedStatement`
- **MySQL 8** — Motor de base de datos
- **Maven** — Gestión de dependencias y build
- **Patrón DAO** — Separación de lógica de acceso a datos
- **Patrón Singleton** — Gestión única de conexión
