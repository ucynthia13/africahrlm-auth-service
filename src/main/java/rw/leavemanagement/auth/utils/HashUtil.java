package rw.leavemanagement.auth.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The HashUtil class provides utility methods for hashing and verifying passwords using BCryptPasswordEncoder.
 */
public class HashUtil {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Hashes the given password using BCryptPasswordEncoder.
     *
     * @param password the password to be hashed
     * @return the hashed password
     */
    public static String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    /**
     * Verifies the raw password against the hashed password using BCryptPasswordEncoder.
     *
     * @param rawPassword    the raw password to be verified
     * @param hashedPassword the hashed password to be compared against
     * @return true if the raw password matches the hashed password, false otherwise
     */
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
    }
}
