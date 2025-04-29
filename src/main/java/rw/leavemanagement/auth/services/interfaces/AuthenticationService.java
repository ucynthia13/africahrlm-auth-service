package rw.leavemanagement.auth.services.interfaces;


import org.springframework.http.ResponseEntity;
import rw.leavemanagement.auth.dto.authentication.AuthResponse;
import rw.leavemanagement.auth.dto.authentication.ChangePasswordDTO;
import rw.leavemanagement.auth.dto.authentication.LoginDTO;
import rw.leavemanagement.auth.dto.authentication.SignupDTO;
import rw.leavemanagement.auth.dto.response.ApiResponse;

public interface AuthenticationService {
    //Method for signup
    ResponseEntity<ApiResponse<AuthResponse>> register(SignupDTO signupDTO);

    // Method for user login
    public ResponseEntity<ApiResponse<AuthResponse>> login(LoginDTO signInDTO);

    // Method for changing user password
    public ResponseEntity<ApiResponse<String>> changePassword(ChangePasswordDTO changePasswordDTO);
}
