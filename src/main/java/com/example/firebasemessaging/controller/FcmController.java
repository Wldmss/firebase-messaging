package com.example.firebasemessaging.controller;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.example.firebasemessaging.service.FcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/fcm")
public class FcmController {

    @Autowired
    FcmService fcmService;

    @GetMapping("/send")
    public ResponseEntity<?> sendMessage() throws FirebaseMessagingException, IOException {
        BatchResponse response = fcmService.sendMessage();

        return ResponseEntity.ok(true);
    }

    @GetMapping("/send/topic")
    public ResponseEntity<?> sendTopicMessage() throws FirebaseMessagingException {
        BatchResponse response = fcmService.sendTopicMessage();

        return ResponseEntity.ok(true);
    }

}
