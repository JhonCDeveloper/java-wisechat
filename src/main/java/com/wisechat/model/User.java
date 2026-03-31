package com.wisechat.model;

import java.sql.Timestamp;

/**
 * POJO que representa la entidad USER de la base de datos.
 */
public class User {

    private int idUser;
    private String name;
    private String email;
    private String password;
    private Timestamp createdAt;

    public User() {}

    public User(int idUser, String name, String email, String password, Timestamp createdAt) {
        this.idUser    = idUser;
        this.name      = name;
        this.email     = email;
        this.password  = password;
        this.createdAt = createdAt;
    }

    // --- Getters y Setters ---
    public int getIdUser()               { return idUser; }
    public void setIdUser(int idUser)    { this.idUser = idUser; }

    public String getName()              { return name; }
    public void setName(String name)     { this.name = name; }

    public String getEmail()             { return email; }
    public void setEmail(String email)   { this.email = email; }

    public String getPassword()                  { return password; }
    public void setPassword(String password)     { this.password = password; }

    public Timestamp getCreatedAt()              { return createdAt; }
    public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{idUser=" + idUser + ", name='" + name + "', email='" + email + "'}";
    }
}
