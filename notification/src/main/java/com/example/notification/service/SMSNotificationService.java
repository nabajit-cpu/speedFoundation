package com.example.notification.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.notification.model.NotificationInterface;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SMSNotificationService implements NotificationInterface {

    // List of SMS recipients
    private final List<String> smsRecipients = Arrays.asList("6361516942");

    // Twilio credentials (inject from properties file)
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    // Initialize Twilio when the service starts
    public void setTwilio() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public String getType() {
        return "sms";
    }

    @Override
    public Boolean sendNotification(String message) {
        try {
            setTwilio();
            for (String recipient : smsRecipients) {
                // Send the SMS
                Message sms = Message.creator(
                        new com.twilio.type.PhoneNumber(recipient), // To
                        new com.twilio.type.PhoneNumber(fromPhoneNumber), // From
                        message // Message Body
                ).create();

                System.out.println("SMS sent to: " + recipient);
                System.out.println("Message SID: " + sms.getSid());
            }
            return true; // All messages sent successfully
        } catch (Exception e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            return false; // Return false if any error occurs
        }
    }
}
