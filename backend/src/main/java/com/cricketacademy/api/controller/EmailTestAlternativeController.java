package com.cricketacademy.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

/**
 * Alternative Email Test Controller
 * Tests email sending with direct SMTP connection to diagnose issues
 */
@RestController
@RequestMapping("/api/email-test-alt")
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:3000" })
@Slf4j
public class EmailTestAlternativeController {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    /**
     * Test email sending with direct SMTP connection and detailed error reporting
     */
    @PostMapping("/send-direct")
    public ResponseEntity<Map<String, Object>> sendDirectEmail(@RequestParam String to) {
        try {
            log.info("Testing direct SMTP email to: {}", to);
            log.info("Using SMTP config - Host: {}, Port: {}, Username: {}", host, port, username);

            // Create properties
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.ssl.trust", host);

            // Create authenticator
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };

            // Create session
            Session session = Session.getInstance(props, authenticator);
            session.setDebug(true);

            // Create message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("DIRECT SMTP TEST - Cricket Academy - " + LocalDateTime.now());
            message.setText("This is a direct SMTP test email.\n\n" +
                    "If you receive this email, the SMTP configuration is working correctly.\n\n" +
                    "Sent at: " + LocalDateTime.now() + "\n" +
                    "From: " + username + "\n" +
                    "Via: " + host + ":" + port + "\n\n" +
                    "This email was sent using direct SMTP connection to diagnose email delivery issues.");

            // Send message
            log.info("Attempting to send email via direct SMTP...");
            Transport.send(message);
            log.info("Email sent successfully via direct SMTP");

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Email sent successfully via direct SMTP",
                    "to", to,
                    "from", username,
                    "host", host,
                    "port", port,
                    "timestamp", LocalDateTime.now()));

        } catch (AuthenticationFailedException e) {
            log.error("SMTP Authentication failed - App password is likely incorrect", e);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "SMTP Authentication failed - Gmail App Password is incorrect or expired",
                    "error", "AuthenticationFailedException",
                    "solution", "Generate a new Gmail App Password",
                    "timestamp", LocalDateTime.now()));

        } catch (MessagingException e) {
            log.error("SMTP Messaging error", e);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "SMTP error: " + e.getMessage(),
                    "error", e.getClass().getSimpleName(),
                    "timestamp", LocalDateTime.now()));

        } catch (Exception e) {
            log.error("Unexpected error sending email", e);
            return ResponseEntity.internalServerError().body(Map.of(
                    "success", false,
                    "message", "Unexpected error: " + e.getMessage(),
                    "error", e.getClass().getSimpleName(),
                    "timestamp", LocalDateTime.now()));
        }
    }

    /**
     * Test SMTP connection without sending email
     */
    @GetMapping("/test-connection")
    public ResponseEntity<Map<String, Object>> testConnection() {
        try {
            log.info("Testing SMTP connection to {}:{}", host, port);

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", port);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");

            Session session = Session.getInstance(props);
            Transport transport = session.getTransport("smtp");

            // Test connection and authentication
            transport.connect(host, port, username, password);
            transport.close();

            log.info("SMTP connection and authentication successful");
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "SMTP connection and authentication successful",
                    "host", host,
                    "port", port,
                    "username", username,
                    "timestamp", LocalDateTime.now()));

        } catch (AuthenticationFailedException e) {
            log.error("SMTP Authentication failed", e);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "SMTP Authentication failed - Gmail App Password is incorrect",
                    "error", "AuthenticationFailedException",
                    "solution", "Generate a new Gmail App Password at https://myaccount.google.com/security",
                    "timestamp", LocalDateTime.now()));

        } catch (Exception e) {
            log.error("SMTP connection failed", e);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "SMTP connection failed: " + e.getMessage(),
                    "error", e.getClass().getSimpleName(),
                    "timestamp", LocalDateTime.now()));
        }
    }
}