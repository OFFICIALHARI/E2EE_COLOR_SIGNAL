package com.colorsignal.backend_springboot.model;

import java.util.Map;

public class RegisterRequest {

    private String roomId;
    private String userId;
    private Map<String, Object> publicKeyJwk;

    public RegisterRequest() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Object> getPublicKeyJwk() {
        return publicKeyJwk;
    }

    public void setPublicKeyJwk(Map<String, Object> publicKeyJwk) {
        this.publicKeyJwk = publicKeyJwk;
    }
}
