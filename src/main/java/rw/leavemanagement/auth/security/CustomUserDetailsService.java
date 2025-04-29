package rw.leavemanagement.auth.security;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rw.leavemanagement.auth.entity.User;
import rw.leavemanagement.auth.enumerations.EStatus;
import rw.leavemanagement.auth.exceptions.BadRequestException;
import rw.leavemanagement.auth.repository.IUserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    /**
     * Constructs a new CustomUserDetailsService with the specified IUserRepository.
     *
     * @param userRepository the repository used to retrieve user information
     */

    public CustomUserDetailsService(@Lazy IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by their user ID.
     *
     * @param id the user ID
     * @return the UserDetails object representing the user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Transactional
    public UserDetails loadByUserId(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        return UserPrincipal.create(user);
    }

    /**
     * Loads a user by their username or email.
     *
     * @param s the username or email
     * @return the UserDetails object representing the user
     * @throws BadRequestException if the user is not found or is not active
     */
    @Transactional
    public UserDetails loadUserByUsername(String s) throws BadRequestException {
        User user = userRepository.findUserByEmail(s).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + s));

        if (!user.getStatus().equals(EStatus.ACTIVE)) {
            throw new BadRequestException("User is not active");
        }
        return UserPrincipal.create(user);
    }
}