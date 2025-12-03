package com.colorsignal.backend_springboot.model;

public class Message {

    private int id;
    private String senderId;
    private String ciphertext;
    private long timestamp;

    public Message() {
    }

    public Message(int id, String senderId, String ciphertext, long timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.ciphertext = ciphertext;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setCiphertext(String ciphertext) {
        this.ciphertext = ciphertext;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
