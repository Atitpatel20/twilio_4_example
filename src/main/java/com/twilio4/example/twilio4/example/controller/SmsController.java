package com.twilio4.example.twilio4.example.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio4.example.twilio4.example.payload.SmsRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @PostMapping("/send-sms")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest request) {
        try {
            Twilio.init(twilioAccountSid, twilioAuthToken);
            Message message = Message.creator(
                            new PhoneNumber(request.getTo()),
                            new PhoneNumber(twilioPhoneNumber),
                            request.getMessage())
                    .create();
            return ResponseEntity.ok("SMS sent successfully. SID: " + message.getSid());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send SMS: " + e.getMessage());
        }
    }
}
