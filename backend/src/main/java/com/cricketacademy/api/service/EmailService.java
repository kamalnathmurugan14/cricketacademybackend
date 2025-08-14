package com.cricketacademy.api.service;

import com.cricketacademy.api.dto.EmailRequest;
import com.cricketacademy.api.dto.EmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;

/**
 * Service for handling email operations with Gmail SMTP
 */
@Service
@Primary
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    /**
     * Send a simple text email
     * 
     * @param emailRequest the email request containing to, subject, and body
     * @return EmailResponse indicating success or failure
     */
    public EmailResponse sendSimpleEmail(EmailRequest emailRequest) {
        try {
            log.info("Attempting to send email to: {} with subject: {}", emailRequest.getTo(),
                    emailRequest.getSubject());
            log.debug("From email configured as: {}", fromEmail);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(emailRequest.getTo());
            message.setSubject(emailRequest.getSubject());
            message.setText(emailRequest.getBody());

            log.debug("Sending email via JavaMailSender...");
            mailSender.send(message);
            log.info("Email sent successfully to: {}", emailRequest.getTo());

            return EmailResponse.builder()
                    .success(true)
                    .message("Email sent successfully")
                    .timestamp(LocalDateTime.now())
                    .recipient(emailRequest.getTo())
                    .subject(emailRequest.getSubject())
                    .build();
        } catch (Exception e) {
            log.error("Failed to send email to: {}", emailRequest.getTo(), e);
            return EmailResponse.builder()
                    .success(false)
                    .message("Failed to send email: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .recipient(emailRequest.getTo())
                    .subject(emailRequest.getSubject())
                    .build();
        }
    }

    /**
     * Send HTML email
     * 
     * @param emailRequest the email request containing to, subject, and body
     * @return EmailResponse indicating success or failure
     */
    public EmailResponse sendHtmlEmail(EmailRequest emailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(emailRequest.getTo());
            helper.setSubject(emailRequest.getSubject());
            helper.setText(emailRequest.getBody(), true);

            mailSender.send(message);
            log.info("HTML email sent successfully to: {}", emailRequest.getTo());

            return EmailResponse.builder()
                    .success(true)
                    .message("HTML email sent successfully")
                    .timestamp(LocalDateTime.now())
                    .recipient(emailRequest.getTo())
                    .subject(emailRequest.getSubject())
                    .build();
        } catch (MessagingException e) {
            log.error("Failed to send HTML email to: {}", emailRequest.getTo(), e);
            return EmailResponse.builder()
                    .success(false)
                    .message("Failed to send HTML email: " + e.getMessage())
                    .timestamp(LocalDateTime.now())
                    .recipient(emailRequest.getTo())
                    .subject(emailRequest.getSubject())
                    .build();
        }
    }

    /**
     * Send password reset email
     * 
     * @param to    recipient email address
     * @param token password reset token
     * @return EmailResponse indicating success or failure
     */
    public EmailResponse sendPasswordResetEmail(String to, String token) {
        String resetUrl = baseUrl + "/reset-password?token=" + token;
        String subject = "Password Reset Request";
        String body = "Click the link below to reset your password:\n\n" + resetUrl + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.";

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject(subject);
        emailRequest.setBody(body);

        return sendSimpleEmail(emailRequest);
    }

    /**
     * Send OTP verification email
     * 
     * @param to  recipient email address
     * @param otp the OTP code
     * @return EmailResponse indicating success or failure
     */
    public EmailResponse sendOtpEmail(String to, String otp) {
        String subject = "Your OTP Verification Code - Cricket Academy";
        String body = "Dear User,\n\n" +
                "Your One-Time Password (OTP) for verification is: " + otp + "\n\n" +
                "This OTP is valid for 5 minutes.\n\n" +
                "Please enter this code to complete your verification.\n\n" +
                "If you did not request this verification, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Cricket Academy Team";

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject(subject);
        emailRequest.setBody(body);

        return sendSimpleEmail(emailRequest);
    }

    /**
     * Send simple password reset email (fallback method)
     * 
     * @param to    recipient email address
     * @param token password reset token
     * @return EmailResponse indicating success or failure
     */
    public EmailResponse sendSimplePasswordResetEmail(String to, String token) {
        String resetUrl = baseUrl + "/reset-password?token=" + token;
        String subject = "Password Reset Request - Simple Version";
        String body = "Click the link below to reset your password:\n\n" + resetUrl + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you didn't request this, please ignore this email.";

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(to);
        emailRequest.setSubject(subject);
        emailRequest.setBody(body);

        return sendSimpleEmail(emailRequest);
    }
}
