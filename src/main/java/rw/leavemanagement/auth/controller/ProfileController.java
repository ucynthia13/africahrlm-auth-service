package rw.leavemanagement.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.leavemanagement.auth.dto.profile.ChangePasswordRequestDTO;
import rw.leavemanagement.auth.dto.profile.ProfileResponseDTO;
import rw.leavemanagement.auth.dto.profile.UpdateProfileRequestDTO;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.dto.user.UserResponseDTO;
import rw.leavemanagement.auth.services.interfaces.ProfileService;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    /**
     * Retrieves the profile information.
     *
     * @return ResponseEntity containing the ApiResponse with the ProfileResponseDTO
     */
    @GetMapping("/get-profile")
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getProfile() {
        return profileService.getProfile();
    }

    /**
     * Updates the profile information.
     *
     * @param updateProfileRequestDTO The request body containing the updated profile information
     * @return ResponseEntity containing the ApiResponse with the updated ProfileResponseDTO
     */
    @PatchMapping("/update-profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(@Valid @RequestBody UpdateProfileRequestDTO updateProfileRequestDTO) {
        return profileService.updateProfile(updateProfileRequestDTO);
    }

    /**
     * Changes the user's password.
     *
     * @param changePasswordRequestDTO The request body containing the old and new passwords
     * @return ResponseEntity containing the ApiResponse with the updated ProfileResponseDTO
     */
    @PatchMapping("/change-password")
    public ResponseEntity<ApiResponse<UserResponseDTO>> changePassword(@Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        return profileService.changePassword(changePasswordRequestDTO);
    }
}
