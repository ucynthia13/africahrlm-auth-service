package rw.leavemanagement.auth.controller;

import com.google.api.client.auth.oauth2.TokenRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rw.leavemanagement.auth.dto.authentication.AuthResponse;
import rw.leavemanagement.auth.dto.authentication.ChangePasswordDTO;
import rw.leavemanagement.auth.dto.authentication.LoginDTO;
import rw.leavemanagement.auth.dto.authentication.SignupDTO;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.entity.User;
import rw.leavemanagement.auth.security.JwtTokenProvider;
import rw.leavemanagement.auth.services.implementations.GoogleOAuthService;
import rw.leavemanagement.auth.services.interfaces.AuthenticationService;
import rw.leavemanagement.auth.services.interfaces.UserService;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final GoogleOAuthService googleOAuthService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody SignupDTO signupDTO) {
        return authenticationService.register(signupDTO);
    }

    /**
     * Handles the change password request.
     *
     * @param changePasswordDTO The information required to change the password.
     * @return The response entity containing the response message.
     */
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        return authenticationService.changePassword(changePasswordDTO);
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginDTO loginDTO){
        return authenticationService.login(loginDTO);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<ApiResponse<SignupDTO>> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ApiResponse.error("Unauthorized", HttpStatus.UNAUTHORIZED, null);
        }

        User user = (User) authentication.getPrincipal(); // Assumes you're using UserDetails
        SignupDTO signupDTO = new SignupDTO(user); // Create a lightweight DTO to avoid exposing password, etc.

        return ApiResponse.success("Current user fetched successfully", HttpStatus.OK, signupDTO);
    }

    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@RequestBody TokenRequest request) {
        try {
            var payload = googleOAuthService.verifyToken(request.getToken());
            String email = payload.getEmail();
            String name = (String) payload.get("name");

            // Split full name into first and last name (fallback-safe)
            String[] names = name != null ? name.split(" ", 2) : new String[]{"Google", "User"};
            String firstName = names[0];
            String lastName = names.length > 1 ? names[1] : "";

            // Find or create the user
            User user = authenticationService.findOrCreateGoogleUser(email, firstName, lastName);

            // Generate JWT token
            String appToken = jwtTokenProvider.generateToken(user);

            // Respond with token and user details
            return ResponseEntity.ok(new GoogleOAuthResponse(appToken, user));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid Google token: " + e.getMessage());
        }
    }


    public static class TokenRequest {
        private String token;
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
    }

    public static class GoogleOAuthResponse {
        private String token;
        private User user;
        public GoogleOAuthResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }
        public String getToken() { return token; }
        public User getUser() { return user; }
    }

}
