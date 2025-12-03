package com.colorsignal.backend_springboot.service;

import com.colorsignal.backend_springboot.model.Message;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    // roomId → Room
    private final Map<String, Room> rooms = new HashMap<>();
    private int globalMessageId = 1;

    public static class Room {
        // userId → publicKey (JWK as Map)
        public Map<String, Object> users = new HashMap<>();
        public List<Message> messages = new ArrayList<>();
    }

    public synchronized void registerKey(String roomId, String userId, Map<String, Object> jwk) {
        rooms.putIfAbsent(roomId, new Room());
        rooms.get(roomId).users.put(userId, jwk);
    }

    public String getPartnerId(String roomId, String userId) {
        Room room = rooms.get(roomId);
        if (room == null) return null;

        return room.users.keySet().stream()
                .filter(id -> !id.equals(userId))
                .findFirst()
                .orElse(null);
    }

    public Map<String, Object> getPartnerKey(String roomId, String userId) {
        Room room = rooms.get(roomId);
        if (room == null) return null;

        return room.users.entrySet().stream()
                .filter(e -> !e.getKey().equals(userId))
                .map(Map.Entry::getValue)
                .map(v -> (Map<String, Object>) v)
                .findFirst()
                .orElse(null);
    }

    public synchronized int addMessage(String roomId, String senderId, String ciphertext) {
        rooms.putIfAbsent(roomId, new Room());
        Room room = rooms.get(roomId);

        int id = globalMessageId++;
        Message m = new Message(id, senderId, ciphertext, System.currentTimeMillis());
        room.messages.add(m);

        return id;
    }

    public List<Message> getMessages(String roomId, String userId, int sinceId) {
        Room room = rooms.get(roomId);
        if (room == null) return Collections.emptyList();

        List<Message> result = new ArrayList<>();
        for (Message m : room.messages) {
            if (m.getId() > sinceId && !m.getSenderId().equals(userId)) {
                result.add(m);
            }
        }
        return result;
    }
}
