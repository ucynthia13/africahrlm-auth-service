package rw.leavemanagement.auth.utils;

public class GeneratePasswordUtil {
    public static String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;:,.<>?";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = (int) (chars.length() * Math.random());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}
