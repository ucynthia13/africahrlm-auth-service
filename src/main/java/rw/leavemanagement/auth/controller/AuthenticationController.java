package rw.leavemanagement.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.leavemanagement.auth.dto.authentication.AuthResponse;
import rw.leavemanagement.auth.dto.authentication.ChangePasswordDTO;
import rw.leavemanagement.auth.dto.authentication.LoginDTO;
import rw.leavemanagement.auth.dto.authentication.SignupDTO;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.services.interfaces.AuthenticationService;



@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
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
}
