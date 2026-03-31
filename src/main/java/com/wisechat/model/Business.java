package com.wisechat.model;

/**
 * POJO que representa la entidad BUSINESS de la base de datos.
 *
 * Atributos:
 *   - idBusiness  : identificador único (PK)
 *   - idUser      : FK hacia la tabla USER
 *   - nombre      : nombre de la empresa (Not Null)
 *   - industria   : sector o industria de la empresa
 *   - descripcion : descripción de la empresa
 */
public class Business {

    // ─── Atributos privados ───────────────────────────────────────────────────
    private int    idBusiness;
    private int    idUser;
    private String nombre;
    private String industria;
    private String descripcion;

    // ─── Constructor vacío ────────────────────────────────────────────────────
    public Business() {}

    // ─── Constructor completo ─────────────────────────────────────────────────
    /**
     * @param idBusiness  ID de la empresa (generado por AUTO_INCREMENT)
     * @param idUser      ID del usuario propietario (FK)
     * @param nombre      Nombre de la empresa
     * @param industria   Sector al que pertenece
     * @param descripcion Descripción adicional
     */
    public Business(int idBusiness, int idUser, String nombre, String industria, String descripcion) {
        this.idBusiness  = idBusiness;
        this.idUser      = idUser;
        this.nombre      = nombre;
        this.industria   = industria;
        this.descripcion = descripcion;
    }

    // ─── Constructor sin ID (para insertar nuevos registros) ─────────────────
    /**
     * @param idUser      ID del usuario propietario
     * @param nombre      Nombre de la empresa
     * @param industria   Sector
     * @param descripcion Descripción
     */
    public Business(int idUser, String nombre, String industria, String descripcion) {
        this.idUser      = idUser;
        this.nombre      = nombre;
        this.industria   = industria;
        this.descripcion = descripcion;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────
    public int    getIdBusiness()  { return idBusiness; }
    public int    getIdUser()      { return idUser; }
    public String getNombre()      { return nombre; }
    public String getIndustria()   { return industria; }
    public String getDescripcion() { return descripcion; }

    // ─── Setters ──────────────────────────────────────────────────────────────
    public void setIdBusiness(int idBusiness)     { this.idBusiness  = idBusiness; }
    public void setIdUser(int idUser)             { this.idUser      = idUser; }
    public void setNombre(String nombre)          { this.nombre      = nombre; }
    public void setIndustria(String industria)    { this.industria   = industria; }
    public void setDescripcion(String descripcion){ this.descripcion = descripcion; }

    // ─── toString ─────────────────────────────────────────────────────────────
    @Override
    public String toString() {
        return "Business{"
            + "idBusiness="    + idBusiness
            + ", idUser="      + idUser
            + ", nombre='"     + nombre      + '\''
            + ", industria='"  + industria   + '\''
            + ", descripcion='"+ descripcion + '\''
            + '}';
    }
}
