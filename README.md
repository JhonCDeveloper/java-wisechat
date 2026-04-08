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

Ejecuta el script SQL incluido en el proyecto para crear la base de datos `wisechat_db` con todas las tablas y relaciones:

```bash
mysql -u root -p < database/wisechat_db.sql
```

### 2. Configurar credenciales

Abre el archivo `src/main/java/com/wisechat/util/ConexionDB.java` y ajusta las credenciales de tu servidor de base de datos local:

```java
private static final String URL      = "jdbc:mysql://localhost:3306/wisechat_db";
private static final String USUARIO  = "root";      // Usuario MySQL
private static final String PASSWORD = "";           // Contraseña MySQL
```

---

## 🚀 Uso y Prueba de los Métodos (App.java)

El proyecto incluye la clase `com.wisechat.main.App` que sirve como punto de prueba automatizada para confirmar que las operaciones de Base de Datos (CRUD) funcionan correctamente, respetando las Foreign Keys (FK).

### Flujo de la prueba automatizada
Al ejecutar `App.java`, el sistema procesará los siguientes pasos:
1. **Crear Usuario:** Inserta un User (`"Laura Gómez"`) recuperando su **ID auto-generado**.
2. **Crear Empresa:** Con el ID devuelto en el paso anterior, inserta un nuevo Business.
3. **Consulta General:** Revisa la BD y lista en consola todos los usuarios y empresas.
4. **Actualización:** Busca la empresa insertada, y modifica su nombre y descripción en la Base de Datos.
5. **Eliminación (Cascada / Controlada):** Finalmente, elimina el registro **Business** y posteriormente el **User** para dejar la BD limpia, demostrando la integridad referencial.

### ¿Cómo correr la prueba?

Asegúrate de haber configurado tu base de datos y tus credenciales. En una terminal ubicada en la raíz del proyecto, instala las dependencias y compila:

```bash
mvn compile
```

Luego, corre la clase principal con este comando:

```bash
mvn exec:java -Dexec.mainClass="com.wisechat.main.App"
```

Verás una salida en consola con reportes técnicos paso a paso.

---

## 🚀 Cómo Iniciar el Proyecto Completo (Backend + Frontend)

Este proyecto funciona bajo una arquitectura **Cliente-Servidor**. El backend (Java) actúa como una API REST que provee datos al frontend construído en React.

### 1. Iniciar el Backend (Java REST API)

El backend está construido con **Jakarta Servlet 6.0** y configurado para desplegarse como un `.war`. Requiere **Apache Tomcat 10+**.

**Opción A: Usando IDE (Recomendado)**
1. Abre el proyecto `wisechat-db` en **IntelliJ IDEA**, **Eclipse** o **VS Code (con Tomcat for Java extension)**.
2. Configura un servidor local Tomcat 10 en tu IDE.
3. Despliega la aplicación (suele bastar con darle a "Run" o "Debug" apuntando al servidor).
4. El backend quedará expuesto por defecto en: `http://localhost:8080/wisechat-db/api/...`

**Opción B: Despliegue Manual en Tomcat**
1. Ejecuta `mvn clean package` para generar el archivo `target/wisechat-db-1.0-SNAPSHOT.war`.
2. Renombra el archivo a `wisechat-db.war` y cópialo dentro de la carpeta `webapps/` de tu instalación de Tomcat 10.
3. Inicia Tomcat (ej. `bin/startup.bat` o `bin/startup.sh`).

### 2. Iniciar el Frontend (React)

El frontend es una aplicación construida con **Vite + React**.

1. Abre una nueva terminal y navega a la carpeta del frontend (si está al nivel del workspace, debe llamarse `react-wisechat` o similar).
   ```bash
   cd react-wisechat
   ```
2. Instala las dependencias (solo la primera vez):
   ```bash
   npm install
   ```
3. Inicia el servidor de desarrollo:
   ```bash
   npm run dev
   ```
4. Abre la URL que te proporciona Vite en tu navegador (usualmente `http://localhost:5173`).

¡Listo! Tu frontend React ahora debería comunicarse de forma local con la base de datos MySQL a través de la API Java de Tomcat.

## 🏗️ Estructura del Proyecto

```
wisechat-db/
├── database/
│   └── wisechat_db.sql          # Script de creación de la BD
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── wisechat/
│       │           ├── controller/ # Servlets (Controladores MVC Jakarta EE)
│       │           ├── dao/        # Interfaces e implementaciones DAO
│       │           ├── model/      # POJOs / Entidades
│       │           ├── util/       # ConexionDB (Singleton JDBC)
│       │           └── main/       # App.java (Ejecutable de prueba CLI)
│        └── webapp/                 # Archivos Root Web
            ├── WEB-INF/
            │   └── web.xml         # Descriptor de Despliegue (EE 10)
            └── (Configuración CORS) # Filtros CORS para permitir que React acceda
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

---

## 🔗 Tecnologías

- **Java 17** — Lenguaje principal del backend
- **Jakarta EE 10** — Implementación de Servlets 6.0 para API REST
- **Apache Tomcat 10** — Servidor Web / Contenedor de Servlets
- **MySQL 8** — Motor de base de datos relacional
- **JDBC Nativo** — Persistencia con `PreparedStatement` seguro contra inyecciones SQL
- **Gson (Google)** — Serialización y deserialización de JSON entre React y Java
- **React 18 + Vite** — Framework frontend SPA web interactivo
- **Tailwind CSS** — Framework CSS usado en React para interfaces modernas
- **Maven** — Gestión de dependencias Java
- **Patrón DAO & Singleton** — Separación de conexión DB y lógica de BD en Java
