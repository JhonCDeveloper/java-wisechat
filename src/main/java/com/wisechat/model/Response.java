package com.wisechat.model;

import java.sql.Timestamp;

/**
 * POJO que representa la entidad RESPONSES de la base de datos.
 */
public class Response {

    private int idResponse;
    private int idForm;
    private int idQuestion;
    private int idUser;
    private String answer;
    private Timestamp createdAt;

    public Response() {}

    /**
     * Constructor sin ID para insertar nuevas respuestas del formulario de onboarding.
     * El ID y el timestamp los genera la base de datos.
     *
     * @param idForm      ID del formulario al que pertenece esta respuesta.
     * @param idQuestion  ID de la pregunta correspondiente (puede ser 0 si no aplica).
     * @param idUser      ID del usuario que responde.
     * @param answer      Texto de la respuesta.
     */
    public Response(int idForm, int idQuestion, int idUser, String answer) {
        this.idForm     = idForm;
        this.idQuestion = idQuestion;
        this.idUser     = idUser;
        this.answer     = answer;
    }

    /**
     * Constructor completo para mapear registros de la base de datos.
     *
     * @param idResponse  ID de la respuesta (PK).
     * @param idForm      ID del formulario (FK).
     * @param idQuestion  ID de la pregunta (FK).
     * @param idUser      ID del usuario (FK).
     * @param answer      Texto de la respuesta.
     * @param createdAt   Timestamp de creación.
     */
    public Response(int idResponse, int idForm, int idQuestion, int idUser, String answer, Timestamp createdAt) {
        this.idResponse  = idResponse;
        this.idForm      = idForm;
        this.idQuestion  = idQuestion;
        this.idUser      = idUser;
        this.answer      = answer;
        this.createdAt   = createdAt;
    }

    // --- Getters y Setters ---
    public int getIdResponse()                   { return idResponse; }
    public void setIdResponse(int idResponse)    { this.idResponse = idResponse; }

    public int getIdForm()                       { return idForm; }
    public void setIdForm(int idForm)            { this.idForm = idForm; }

    public int getIdQuestion()                   { return idQuestion; }
    public void setIdQuestion(int idQuestion)    { this.idQuestion = idQuestion; }

    public int getIdUser()                       { return idUser; }
    public void setIdUser(int idUser)            { this.idUser = idUser; }

    public String getAnswer()                    { return answer; }
    public void setAnswer(String answer)         { this.answer = answer; }

    public Timestamp getCreatedAt()              { return createdAt; }
    public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Response{idResponse=" + idResponse + ", idQuestion=" + idQuestion + ", answer='" + answer + "'}";
    }
}
