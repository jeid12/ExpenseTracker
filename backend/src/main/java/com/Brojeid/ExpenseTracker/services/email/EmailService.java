package com.Brojeid.ExpenseTracker.services.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public void sendPasswordResetEmail(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Reset Request - Expense Tracker");
        
        String resetLink = frontendUrl + "/reset-password?token=" + token;
        String emailBody = String.format(
                "Hello,\n\n" +
                "You have requested to reset your password for Expense Tracker.\n\n" +
                "Please click the link below to reset your password:\n%s\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "If you did not request this password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Expense Tracker Team",
                resetLink
        );
        
        message.setText(emailBody);
        mailSender.send(message);
    }

    public void sendWelcomeEmail(String toEmail, String firstName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to Expense Tracker");
        
        String emailBody = String.format(
                "Hello %s,\n\n" +
                "Welcome to Expense Tracker!\n\n" +
                "Your account has been successfully created. You can now start tracking your expenses and income.\n\n" +
                "Best regards,\n" +
                "Expense Tracker Team",
                firstName
        );
        
        message.setText(emailBody);
        mailSender.send(message);
    }
}
