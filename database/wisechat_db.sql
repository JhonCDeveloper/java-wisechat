-- ============================================
-- Script de creación de la BD: wisechat_db
-- Proyecto: Wisechat
-- ============================================

CREATE DATABASE IF NOT EXISTS wisechat_db
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE wisechat_db;

-- --- Tabla USER ---
CREATE TABLE IF NOT EXISTS USER (
    id_user    INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100)  NOT NULL,
    email      VARCHAR(150)  NOT NULL UNIQUE,
    password   VARCHAR(255)  NOT NULL,
    created_at TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

-- --- Tabla BUSINESS ---
CREATE TABLE IF NOT EXISTS BUSINESS (
    id_business INT AUTO_INCREMENT PRIMARY KEY,
    id_user     INT          NOT NULL,
    name        VARCHAR(150) NOT NULL,
    industry    VARCHAR(100),
    description TEXT,
    CONSTRAINT fk_business_user FOREIGN KEY (id_user) REFERENCES USER(id_user) ON DELETE CASCADE
);

-- --- Tabla FORM ---
CREATE TABLE IF NOT EXISTS FORM (
    id_form     INT AUTO_INCREMENT PRIMARY KEY,
    id_business INT          NOT NULL,
    title       VARCHAR(200) NOT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_form_business FOREIGN KEY (id_business) REFERENCES BUSINESS(id_business) ON DELETE CASCADE
);

-- --- Tabla QUESTIONS ---
CREATE TABLE IF NOT EXISTS QUESTIONS (
    id_question   INT AUTO_INCREMENT PRIMARY KEY,
    id_form       INT          NOT NULL,
    question_text TEXT         NOT NULL,
    type          ENUM('text','multiple_choice','boolean') NOT NULL DEFAULT 'text',
    CONSTRAINT fk_question_form FOREIGN KEY (id_form) REFERENCES FORM(id_form) ON DELETE CASCADE
);

-- --- Tabla RESPONSES ---
CREATE TABLE IF NOT EXISTS RESPONSES (
    id_response INT AUTO_INCREMENT PRIMARY KEY,
    id_form     INT       NOT NULL,
    id_question INT       NOT NULL,
    id_user     INT       NOT NULL,
    answer      TEXT      NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_response_form     FOREIGN KEY (id_form)     REFERENCES FORM(id_form)         ON DELETE CASCADE,
    CONSTRAINT fk_response_question FOREIGN KEY (id_question) REFERENCES QUESTIONS(id_question) ON DELETE CASCADE,
    CONSTRAINT fk_response_user     FOREIGN KEY (id_user)     REFERENCES USER(id_user)          ON DELETE CASCADE
);

-- --- Tabla ACTIONS_PLAN ---
CREATE TABLE IF NOT EXISTS ACTIONS_PLAN (
    id_action   INT AUTO_INCREMENT PRIMARY KEY,
    id_business INT    NOT NULL,
    description TEXT,
    status      ENUM('pending','completed') NOT NULL DEFAULT 'pending',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_action_business FOREIGN KEY (id_business) REFERENCES BUSINESS(id_business) ON DELETE CASCADE
);

-- --- Tabla MESSAGES ---
CREATE TABLE IF NOT EXISTS MESSAGES (
    id_message   INT AUTO_INCREMENT PRIMARY KEY,
    id_user      INT  NOT NULL,
    message_text TEXT NOT NULL,
    sender       ENUM('user','system') NOT NULL DEFAULT 'user',
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_message_user FOREIGN KEY (id_user) REFERENCES USER(id_user) ON DELETE CASCADE
);
