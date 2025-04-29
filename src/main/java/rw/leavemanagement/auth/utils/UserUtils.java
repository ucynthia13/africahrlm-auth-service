package rw.leavemanagement.auth.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import rw.leavemanagement.auth.repository.IUserRepository;
import rw.leavemanagement.auth.security.UserPrincipal;

/**
 * This class provides utility methods for working with user-related operations.
 */
public class UserUtils {
    private static IUserRepository userRepository;

    /**
     * Retrieves the currently logged in user.
     *
     * @return The UserPrincipal object representing the currently logged in user,
     *         or null if no user is logged in.
     */
    public static UserPrincipal getLoggedInUser() {
        // Retrieve the currently authenticated user from the SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return (UserPrincipal) authentication.getPrincipal();
        }
        return null;
    }
}
