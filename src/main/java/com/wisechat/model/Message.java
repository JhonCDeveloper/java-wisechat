package com.wisechat.model;

import java.sql.Timestamp;

/**
 * POJO que representa la entidad MESSAGES de la base de datos.
 * Remitentes válidos: "user", "system"
 */
public class Message {

    private int idMessage;
    private int idUser;
    private String messageText;
    private String sender; // user | system
    private Timestamp createdAt;

    public Message() {}

    public Message(int idMessage, int idUser, String messageText, String sender, Timestamp createdAt) {
        this.idMessage   = idMessage;
        this.idUser      = idUser;
        this.messageText = messageText;
        this.sender      = sender;
        this.createdAt   = createdAt;
    }

    // --- Getters y Setters ---
    public int getIdMessage()                    { return idMessage; }
    public void setIdMessage(int idMessage)      { this.idMessage = idMessage; }

    public int getIdUser()                       { return idUser; }
    public void setIdUser(int idUser)            { this.idUser = idUser; }

    public String getMessageText()               { return messageText; }
    public void setMessageText(String text)      { this.messageText = text; }

    public String getSender()                    { return sender; }
    public void setSender(String sender)         { this.sender = sender; }

    public Timestamp getCreatedAt()              { return createdAt; }
    public void setCreatedAt(Timestamp createdAt){ this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Message{idMessage=" + idMessage + ", sender='" + sender + "', text='" + messageText + "'}";
    }
}
