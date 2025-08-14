package com.cricketacademy.api.util;

import com.cricketacademy.api.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Standalone utility to reset a user's password.
 * Usage: java -cp target/classes com.cricketacademy.api.util.PasswordResetUtil
 * userEmail newPassword
 */
public class PasswordResetUtil {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java PasswordResetUtil <userEmail> <newPassword>");
            System.exit(1);
        }

        String email = args[0];
        String newPassword = args[1];

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.cricketacademy.api");
        UserService userService = context.getBean(UserService.class);

        try {
            userService.resetUserPassword(email, newPassword);
            System.out.println("Password reset successfully for user: " + email);
        } catch (Exception e) {
            System.err.println("Failed to reset password: " + e.getMessage());
            e.printStackTrace();
        } finally {
            context.close();
        }
    }
}
