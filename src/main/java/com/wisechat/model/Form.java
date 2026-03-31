package com.wisechat.model;

import java.sql.Timestamp;

/**
 * POJO que representa la entidad FORM de la base de datos.
 */
public class Form {

    private int idForm;
    private int idBusiness;
    private String title;
    private Timestamp createdAt;

    public Form() {}

    public Form(int idForm, int idBusiness, String title, Timestamp createdAt) {
        this.idForm      = idForm;
        this.idBusiness  = idBusiness;
        this.title       = title;
        this.createdAt   = createdAt;
    }

    // --- Getters y Setters ---
    public int getIdForm()                       { return idForm; }
    public void setIdForm(int idForm)            { this.idForm = idForm; }

    public int getIdBusiness()                   { return idBusiness; }
    public void setIdBusiness(int idBusiness)    { this.idBusiness = idBusiness; }

    public String getTitle()                     { return title; }
    public void setTitle(String title)           { this.title = title; }

    public Timestamp getCreatedAt()              { return createdAt; }
    public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Form{idForm=" + idForm + ", title='" + title + "', idBusiness=" + idBusiness + "}";
    }
}
