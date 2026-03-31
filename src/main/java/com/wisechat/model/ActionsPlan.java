package com.wisechat.model;

import java.sql.Timestamp;

/**
 * POJO que representa la entidad ACTIONS_PLAN de la base de datos.
 * Estados válidos: "pending", "completed"
 */
public class ActionsPlan {

    private int idAction;
    private int idBusiness;
    private String description;
    private String status; // pending | completed
    private Timestamp createdAt;

    public ActionsPlan() {}

    public ActionsPlan(int idAction, int idBusiness, String description, String status, Timestamp createdAt) {
        this.idAction    = idAction;
        this.idBusiness  = idBusiness;
        this.description = description;
        this.status      = status;
        this.createdAt   = createdAt;
    }

    // --- Getters y Setters ---
    public int getIdAction()                     { return idAction; }
    public void setIdAction(int idAction)        { this.idAction = idAction; }

    public int getIdBusiness()                   { return idBusiness; }
    public void setIdBusiness(int idBusiness)    { this.idBusiness = idBusiness; }

    public String getDescription()               { return description; }
    public void setDescription(String desc)      { this.description = desc; }

    public String getStatus()                    { return status; }
    public void setStatus(String status)         { this.status = status; }

    public Timestamp getCreatedAt()              { return createdAt; }
    public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ActionsPlan{idAction=" + idAction + ", status='" + status + "', description='" + description + "'}";
    }
}
