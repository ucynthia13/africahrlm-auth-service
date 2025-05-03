package rw.leavemanagement.auth.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import rw.leavemanagement.auth.dto.authentication.*;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.entity.User;
import rw.leavemanagement.auth.enumerations.EUserRole;
import rw.leavemanagement.auth.exceptions.CustomException;
import rw.leavemanagement.auth.repository.IUserRepository;
import rw.leavemanagement.auth.security.JwtTokenProvider;
import rw.leavemanagement.auth.security.UserPrincipal;
import rw.leavemanagement.auth.services.interfaces.AuthenticationService;
import rw.leavemanagement.auth.utils.HashUtil;
import rw.leavemanagement.auth.utils.UserUtils;


import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final IUserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationProvider authenticationProvider;

    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> register(SignupDTO signupDTO) {
        try {
            Optional<User> existingUser = userRepository.findUserByEmail(signupDTO.getEmail());
            if (existingUser.isPresent()) {
                return ApiResponse.error("Email already registered", HttpStatus.CONFLICT, null);
            }

            String hashedPassword = HashUtil.hashPassword(signupDTO.getPassword());

            User newUser = new User();
            newUser.setEmail(signupDTO.getEmail());
            newUser.setPassword(hashedPassword);
            newUser.setFirstname(signupDTO.getFirstName());
            newUser.setLastname(signupDTO.getLastName());
            newUser.setDepartment(signupDTO.getDepartment());
            newUser.setRole(EUserRole.valueOf(signupDTO.getRole()));
            newUser.setPhoneNumber(signupDTO.getPhoneNumber());

            userRepository.save(newUser);

            // Auto-login after registration (optional)
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(signupDTO.getEmail());
            loginDTO.setPassword(signupDTO.getPassword());
            loginDTO.setRememberMe(false);

            Authentication authentication = authenticateUser(loginDTO);
            AuthResponse response = generateJwtAuthenticationResponse(authentication, false);

            return ApiResponse.success("Successfully registered", HttpStatus.CREATED, response);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public User findOrCreateGoogleUser(String email, String firstName, String lastName) {
        return userRepository.findUserByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setFirstname(firstName);
            newUser.setLastname(lastName);

            // Set safe defaults
            newUser.setDepartment("Unknown");
            newUser.setPhoneNumber("N/A");
            newUser.setRole(EUserRole.USER);
            newUser.setPassword(UUID.randomUUID().toString());

            return userRepository.save(newUser);
        });
    }

    @Override
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordDTO changePasswordDTO) {
        try {

        UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
        System.out.println("Current logged in user: " +userPrincipal);
        User user = userRepository.findUserById(userPrincipal.getId()).orElseThrow(() -> new CustomException("User not found"));

        // check if old and new password are the same
        if (changePasswordDTO.getOldPassword().equals(changePasswordDTO.getNewPassword())) {
            return ApiResponse.error("Old and new password can't be the same", HttpStatus.BAD_REQUEST, null);
        }

        if (!HashUtil.verifyPassword(changePasswordDTO.getOldPassword(), user.getPassword())) {
            return ApiResponse.error("Old password is incorrect", HttpStatus.BAD_REQUEST, null);
        }

        String hashedPassword = HashUtil.hashPassword(changePasswordDTO.getNewPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
        return ApiResponse.success("Password changed successfully", HttpStatus.OK, null);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }


    /**
     * Login a user.
     *
     * @param loginDTO The DTO containing user login information.
     * @return ResponseEntity containing the API response and authentication
     *         details.
     */
    @Override
    public ResponseEntity<ApiResponse<AuthResponse>> login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticateUser(loginDTO);
            AuthResponse response = generateJwtAuthenticationResponse(authentication, loginDTO.isRememberMe());
            return ApiResponse.success("Successfully logged in", HttpStatus.OK, response);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
    /**
     * Authenticates a user using the provided login information.
     *
     * @param loginDTO The DTO containing user login information.
     * @return The authenticated user.
     */
    private Authentication authenticateUser(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                loginDTO.getPassword());
        Authentication authentication = authenticationProvider.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Override
    public ResponseEntity<ApiResponse<SignupDTO>> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ApiResponse.error("Unauthorized", HttpStatus.UNAUTHORIZED, null);
        }

        User user = (User) authentication.getPrincipal(); // Assumes you're using UserDetails
        SignupDTO signupDTO = new SignupDTO(user); // Create a lightweight DTO to avoid exposing password, etc.

        return ApiResponse.success("Current user fetched successfully", HttpStatus.OK, signupDTO);
    }
    /**
     * Generates the JWT authentication response.
     *
     * @param authentication The authenticated user.
     * @return The authentication response containing the JWT token and user
     *         details.
     */
    private AuthResponse generateJwtAuthenticationResponse(Authentication authentication, Boolean rememberMe) {
        System.out.println(authentication.getCredentials());
        JWTTokenCreation jwtTokenCreation = jwtTokenProvider.generateAccessToken(authentication, rememberMe);
        UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
        assert userPrincipal != null;
        Optional<User> foundUser = userRepository.findUserById(userPrincipal.getId());
        User user = foundUser.orElseThrow(() -> new CustomException("User not found"));
        return new AuthResponse(jwtTokenCreation.getToken(), user);
    }
}
