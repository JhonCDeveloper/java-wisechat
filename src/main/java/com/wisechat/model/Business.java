package com.wisechat.model;

/**
 * POJO que representa la entidad BUSINESS de la base de datos.
 */
public class Business {

    private int idBusiness;
    private int idUser;
    private String name;
    private String industry;
    private String description;

    public Business() {}

    public Business(int idBusiness, int idUser, String name, String industry, String description) {
        this.idBusiness  = idBusiness;
        this.idUser      = idUser;
        this.name        = name;
        this.industry    = industry;
        this.description = description;
    }

    // --- Getters y Setters ---
    public int getIdBusiness()                   { return idBusiness; }
    public void setIdBusiness(int idBusiness)    { this.idBusiness = idBusiness; }

    public int getIdUser()                       { return idUser; }
    public void setIdUser(int idUser)            { this.idUser = idUser; }

    public String getName()                      { return name; }
    public void setName(String name)             { this.name = name; }

    public String getIndustry()                  { return industry; }
    public void setIndustry(String industry)     { this.industry = industry; }

    public String getDescription()               { return description; }
    public void setDescription(String desc)      { this.description = desc; }

    @Override
    public String toString() {
        return "Business{idBusiness=" + idBusiness + ", name='" + name + "', industry='" + industry + "'}";
    }
}
