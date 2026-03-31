package com.wisechat.model;

import java.sql.Timestamp;

/**
 * POJO que representa la entidad USER de la base de datos.
 *
 * Atributos:
 *   - idUser    : identificador único (PK)
 *   - nombre    : nombre completo del usuario (Not Null)
 *   - email     : correo electrónico único (Not Null)
 *   - password  : contraseña hasheada
 *   - creadoEn  : marca de tiempo de creación
 */
public class User {

    // ─── Atributos privados ───────────────────────────────────────────────────
    private int       idUser;
    private String    nombre;
    private String    email;
    private String    password;
    private Timestamp creadoEn;

    // ─── Constructor vacío (requerido por JDBC al mapear ResultSet) ───────────
    public User() {}

    // ─── Constructor completo ─────────────────────────────────────────────────
    /**
     * @param idUser    ID del usuario (generado por AUTO_INCREMENT)
     * @param nombre    Nombre completo
     * @param email     Correo electrónico único
     * @param password  Contraseña (debe almacenarse hasheada)
     * @param creadoEn  Timestamp de creación
     */
    public User(int idUser, String nombre, String email, String password, Timestamp creadoEn) {
        this.idUser   = idUser;
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
        this.creadoEn = creadoEn;
    }

    // ─── Constructor sin ID (para insertar nuevos registros) ─────────────────
    /**
     * @param nombre   Nombre completo
     * @param email    Correo electrónico
     * @param password Contraseña
     */
    public User(String nombre, String email, String password) {
        this.nombre   = nombre;
        this.email    = email;
        this.password = password;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────
    public int getIdUser()         { return idUser; }
    public String getNombre()      { return nombre; }
    public String getEmail()       { return email; }
    public String getPassword()    { return password; }
    public Timestamp getCreadoEn() { return creadoEn; }

    // ─── Setters ──────────────────────────────────────────────────────────────
    public void setIdUser(int idUser)           { this.idUser   = idUser; }
    public void setNombre(String nombre)        { this.nombre   = nombre; }
    public void setEmail(String email)          { this.email    = email; }
    public void setPassword(String password)    { this.password = password; }
    public void setCreadoEn(Timestamp creadoEn) { this.creadoEn = creadoEn; }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "User{"
            + "idUser="    + idUser
            + ", nombre='" + nombre   + '\''
            + ", email='"  + email    + '\''
            + ", creado="  + creadoEn
            + '}';
    }
}
