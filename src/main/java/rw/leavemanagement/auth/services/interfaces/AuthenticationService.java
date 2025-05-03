package rw.leavemanagement.auth.services.interfaces;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import rw.leavemanagement.auth.dto.authentication.AuthResponse;
import rw.leavemanagement.auth.dto.authentication.ChangePasswordDTO;
import rw.leavemanagement.auth.dto.authentication.LoginDTO;
import rw.leavemanagement.auth.dto.authentication.SignupDTO;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.entity.User;

public interface AuthenticationService {
    //Method for signup
    ResponseEntity<ApiResponse<AuthResponse>> register(SignupDTO signupDTO);

    //For google Auth
    User findOrCreateGoogleUser(String email, String firstName, String lastName);

    // Method for user login
     ResponseEntity<ApiResponse<AuthResponse>> login(LoginDTO signInDTO);

    // Method for changing user password
     ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordDTO changePasswordDTO);

    ResponseEntity<ApiResponse<SignupDTO>> getCurrentUser(Authentication authentication);

}
