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

## 🌐 Despliegue Web en Tomcat 10

Dado que la aplicación ha evolucionado a una versión Web empaquetada como `<packaging>war</packaging>`, necesitas desplegarla en un entorno **Apache Tomcat 10.x** usando **Jakarta EE 10**.

1. **Compilar/Empaquetar:** Asegúrate de ejecutar `mvn clean compile` o verificar que tu carpeta `target/classes` está actualizada con las últimas versiones de tus Servlets y DAOs.
2. **Despliegue de Recursos:** El directorio `src/main/webapp/` (que contiene tus `.jsp` y `WEB-INF`) debe estar mapeado o copiado de forma directa en tu directorio de despliegue de Tomcat (ej. `webapps/TU_APP`).
3. **Clases Compiladas:** El contenido de `target/classes/` debe proveerse en `WEB-INF/classes/` en el servidor Tomcat.
4. **Controlador JDBC (`mysql-connector-j`):** Tomcat exige que el driver MySQL esté presente localmente. Copia el `.jar` del conector de MySQL directo a la carpeta de librerías de tu webapp: `WEB-INF/lib/`.
5. **Acceso Web:** Enciende Tomcat (`startup.bat`) y visita `http://localhost:8080/TU_APP/` para navegar por la aplicación utilizando la interfaz gráfica construida con Tailwind CSS.

---

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
│       └── webapp/                 # Archivos Root Web
│           ├── WEB-INF/
│           │   └── web.xml         # Descriptor de Despliegue (EE 10)
│           ├── index.jsp           # Vista Principal (Landing)
│           ├── registro.jsp        # Formulario POST de Registro
│           └── resultado.jsp       # Vista de Respuesta (Éxito/Error)
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

- **Java 17** — Lenguaje principal
- **Jakarta EE 10** — Implementación de Servlets 6.0 y JSP 3.1
- **Apache Tomcat 10** — Servidor Web / Contenedor de Servlets
- **MySQL 8** — Motor de base de datos
- **JDBC Nativo** — Persistencia con `PreparedStatement` (Seguro contra SQL Injection)
- **Tailwind CSS** — Framework CSS para interfaces modernas y responsivas
- **Maven** — Gestión de dependencias (`pom.xml` -> `<packaging>war</packaging>`)
- **Patrón MVC** — Arquitectura web separando Model, View, Controller
- **Patrón DAO & Singleton** — Separación de conexión DB y lógica de entidades
