package com.wisechat.model;

/**
 * POJO que representa la entidad QUESTIONS de la base de datos.
 * Tipos válidos: "text", "multiple_choice", "boolean"
 */
public class Question {

    private int idQuestion;
    private int idForm;
    private String questionText;
    private String type; // text | multiple_choice | boolean

    public Question() {}

    public Question(int idQuestion, int idForm, String questionText, String type) {
        this.idQuestion  = idQuestion;
        this.idForm      = idForm;
        this.questionText = questionText;
        this.type        = type;
    }

    // --- Getters y Setters ---
    public int getIdQuestion()                       { return idQuestion; }
    public void setIdQuestion(int idQuestion)        { this.idQuestion = idQuestion; }

    public int getIdForm()                           { return idForm; }
    public void setIdForm(int idForm)                { this.idForm = idForm; }

    public String getQuestionText()                  { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getType()                          { return type; }
    public void setType(String type)                 { this.type = type; }

    @Override
    public String toString() {
        return "Question{idQuestion=" + idQuestion + ", type='" + type + "', text='" + questionText + "'}";
    }
}
