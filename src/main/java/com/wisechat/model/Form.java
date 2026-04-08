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

    /**
     * Constructor sin ID para insertar nuevos registros (el ID lo genera AUTO_INCREMENT).
     *
     * @param idBusiness ID del negocio al que pertenece el formulario.
     * @param title      Título descriptivo del formulario.
     */
    public Form(int idBusiness, String title) {
        this.idBusiness = idBusiness;
        this.title      = title;
    }

    /**
     * Constructor completo para mapear registros desde la base de datos.
     *
     * @param idForm     ID del formulario (PK generado por JDBC).
     * @param idBusiness ID del negocio asociado (FK).
     * @param title      Título del formulario.
     * @param createdAt  Timestamp de creación.
     */
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
