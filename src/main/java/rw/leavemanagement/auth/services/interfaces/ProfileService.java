package rw.leavemanagement.auth.services.interfaces;

import org.springframework.http.ResponseEntity;
import rw.leavemanagement.auth.dto.profile.ChangePasswordRequestDTO;
import rw.leavemanagement.auth.dto.profile.ProfileResponseDTO;
import rw.leavemanagement.auth.dto.profile.UpdateProfileRequestDTO;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.dto.user.UserResponseDTO;

/**
 * The ProfileService interface provides methods for managing user profiles.
 */
public interface ProfileService {

    /**
     * Retrieves the profile of the currently authenticated user.
     *
     * @return ResponseEntity containing the ApiResponse with the profile response DTO.
     */
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getProfile();

    /**
     * Updates the profile of the currently authenticated user.
     *
     * @param updateProfileRequestDTO The DTO containing the updated profile information.
     * @return ResponseEntity containing the ApiResponse with the updated profile response DTO.
     */
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(UpdateProfileRequestDTO updateProfileRequestDTO);

    /**
     * Changes the password of the currently authenticated user.
     *
     * @param changePasswordRequestDTO The DTO containing the new password information.
     * @return ResponseEntity containing the ApiResponse with the updated profile response DTO.
     */
    public ResponseEntity<ApiResponse<UserResponseDTO>> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO);
}
