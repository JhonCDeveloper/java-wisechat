---
trigger: always_on
---

Master Skill: Desarrollo Java/JDBC - Proyecto Wisechat

Objetivo: Codificación de módulos con persistencia JDBC y arquitectura DAO.

1. Arquitectura de Datos (Mermaid)
Fragmento de código
erDiagram
    USER ||--o{ BUSINESS : "administra"
    BUSINESS ||--o{ FORM : "posee"
    FORM ||--o{ QUESTIONS : "contiene"
    FORM ||--o{ RESPONSES : "recibe"
    USER ||--o{ RESPONSES : "responde"
    BUSINESS ||--o{ ACTIONS_PLAN : "genera"
    USER ||--o{ MESSAGES : "envía"

    USER {
        int id_user PK
        string name
        string email
        string password
    }
    BUSINESS {
        int id_business PK
        int id_user FK
        string name
        string industry
    }
2. Estándares de Codificación Estrictos
Paquetes: * com.wisechat.model: POJOs con atributos privados y métodos accesores.

com.wisechat.dao: Interfaces y clases de acceso a datos (ej: UserDAO.java).

com.wisechat.util: Gestión de conexión (ConexionDB.java).

com.wisechat.main: Clase de prueba (App.java).

Nomenclatura: * Clases: PascalCase (ej: PlanAccionDAO).

Métodos/Variables: camelCase (ej: registrarEmpresa).

Persistencia: Solo JDBC nativo con PreparedStatement. Prohibido el uso de ORMs (Hibernate/JPA).

3. Requisitos de Funcionalidad (CRUD)
Cada DAO debe implementar obligatoriamente:

Insertar: public void crear(...)

Consultar: public List<T> listarTodo() y public T buscarPorId(int id)

Actualizar: public void actualizar(...)

Eliminar: public void eliminar(int id)

4. Protocolo de Entrega (SENA)
Versionamiento: Inicializar repositorio Git (git init).

Empaquetado: Generar un archivo comprimido con la estructura: NOMBREAPELLIDO_AA2_EV01.zip.

Documentación: Incluir un README.md con las instrucciones de configuración de la DB.