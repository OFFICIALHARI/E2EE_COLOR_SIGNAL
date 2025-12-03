package com.colorsignal.backend_springboot.controller;

import com.colorsignal.backend_springboot.model.Message;
import com.colorsignal.backend_springboot.model.RegisterRequest;
import com.colorsignal.backend_springboot.model.SendMessageRequest;
import com.colorsignal.backend_springboot.service.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")  // allow frontend from any origin
@RequestMapping("/api")      // base path: /api/...
public class ApiController {

    private final RoomService roomService;

    public ApiController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        roomService.registerKey(req.getRoomId(), req.getUserId(), req.getPublicKeyJwk());
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");
        return res;
    }

    @GetMapping("/partnerKey")
    public Map<String, Object> partnerKey(
            @RequestParam String roomId,
            @RequestParam String userId
    ) {
        String partnerId = roomService.getPartnerId(roomId, userId);
        Map<String, Object> partnerKey = roomService.getPartnerKey(roomId, userId);

        Map<String, Object> res = new HashMap<>();
        res.put("partnerId", partnerId);
        res.put("partnerKeyJwk", partnerKey);
        return res;
    }

    @PostMapping("/send")
    public Map<String, Object> send(@RequestBody SendMessageRequest req) {
        int msgId = roomService.addMessage(req.getRoomId(), req.getSenderId(), req.getCiphertext());
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");
        res.put("messageId", msgId);
        return res;
    }

    @GetMapping("/messages")
    public Map<String, Object> getMessages(
            @RequestParam String roomId,
            @RequestParam String userId,
            @RequestParam(defaultValue = "0") int sinceId
    ) {
        List<Message> list = roomService.getMessages(roomId, userId, sinceId);
        Map<String, Object> res = new HashMap<>();
        res.put("messages", list);
        return res;
    }
}
