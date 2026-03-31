---
trigger: always_on
---

erDiagram
    %% --- Entidades Principales ---
    USER ||--o{ BUSINESS : "crea y administra"
    USER ||--o{ MESSAGES : "envía/recibe"
    
    BUSINESS ||--o{ FORM : "posee"
    BUSINESS ||--o{ ACTIONS_PLAN : "ejecuta"
    
    FORM ||--o{ QUESTIONS : "contiene"
    FORM ||--o{ RESPONSES : "agrupa"
    
    QUESTIONS ||--o{ RESPONSES : "clasifica"
    USER ||--o{ RESPONSES : "responde (Mejora PRO)"

    %% --- Atributos y Tipos de Datos ---
    USER {
        int id_user PK
        string name "Not Null"
        string email "Unique, Not Null"
        string password "Hashed"
        timestamp created_at
    }

    BUSINESS {
        int id_business PK
        int id_user FK "Relación con USER"
        string name "Not Null"
        string industry
        string description
    }

    FORM {
        int id_form PK
        int id_business FK "Relación con BUSINESS"
        string title "Not Null"
        timestamp created_at
    }

    QUESTIONS {
        int id_question PK
        int id_form FK "Relación con FORM"
        string question_text "Not Null"
        string type "text/multiple_choice/boolean"
    }

    RESPONSES {
        int id_response PK
        int id_form FK "Contexto del formulario"
        int id_question FK "Pregunta específica"
        int id_user FK "Usuario que responde"
        string answer "Not Null"
        timestamp created_at
    }

    ACTIONS_PLAN {
        int id_action PK
        int id_business FK "Relación con BUSINESS"
        string description "Detalle de la acción"
        string status "pending/completed"
        timestamp created_at
    }

    MESSAGES {
        int id_message PK
        int id_user FK "Remitente"
        string message_text "Contenido"
        string sender "user/system"
        timestamp created_at
    }