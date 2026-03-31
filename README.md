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
private static final String USUARIO  = "root";      // Tu usuario MySQL
private static final String PASSWORD = "";           // Tu contraseña MySQL
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
│                   └── main/    # App.java (Ejecutable de prueba)
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

Para generar el entregable final en formato ZIP:

```bash
# Generar ZIP usando PowerShell
Compress-Archive -Path . -DestinationPath "../NOMBREAPELLIDO_AA2_EV01.zip"
```

---

## 🔗 Tecnologías

- **Java 17** — Lenguaje principal
- **JDBC Nativo** — Persistencia con `PreparedStatement` (Seguro contra SQL Injection)
- **MySQL 8** — Motor de base de datos
- **Maven** — Gestión de dependencias y build
- **Patrón DAO** — Separación de lógica de acceso a datos
- **Patrón Singleton** — Gestión única de conexión
