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
