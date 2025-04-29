package rw.leavemanagement.auth.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rw.leavemanagement.auth.dto.profile.ChangePasswordRequestDTO;
import rw.leavemanagement.auth.dto.profile.ProfileResponseDTO;
import rw.leavemanagement.auth.dto.profile.UpdateProfileRequestDTO;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.dto.user.UserResponseDTO;
import rw.leavemanagement.auth.entity.User;
import rw.leavemanagement.auth.exceptions.CustomException;
import rw.leavemanagement.auth.repository.IUserRepository;
import rw.leavemanagement.auth.security.UserPrincipal;
import rw.leavemanagement.auth.services.interfaces.ProfileService;
import rw.leavemanagement.auth.utils.HashUtil;
import rw.leavemanagement.auth.utils.UserUtils;


@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final IUserRepository userRepository;

    // Get the profile of the logged-in user
    @Override
    public ResponseEntity<ApiResponse<ProfileResponseDTO>> getProfile() {
        try {
            UserPrincipal userPrincipal = UserUtils.getLoggedInUser();

            User user = userRepository.findUserById(userPrincipal.getId()).orElseThrow(() -> new CustomException("User not found"));

            return ApiResponse.success(
                    "Profile fetched successfully",
                    HttpStatus.OK,
                    new ProfileResponseDTO(user)
            );
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    // Update the profile of the logged-in user
    @Override
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(UpdateProfileRequestDTO updateProfileRequestDTO) {
        try {
            UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new CustomException("User not found"));
            if (updateProfileRequestDTO.getFirstname() != null) user.setFirstname(updateProfileRequestDTO.getFirstname());
            if (updateProfileRequestDTO.getLastname() != null) user.setEmail(updateProfileRequestDTO.getLastname());
            userRepository.save(user);
            return ApiResponse.success("Profile updated successfully", HttpStatus.OK, new UserResponseDTO(user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    // Change the password of the logged-in user
    @Override
    public ResponseEntity<ApiResponse<UserResponseDTO>> changePassword(ChangePasswordRequestDTO changePasswordRequestDTO) {
        try {
            UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
            String oldPassword = changePasswordRequestDTO.getOldPassword();
            String newPassword = changePasswordRequestDTO.getNewPassword();
            String confirmPassword = changePasswordRequestDTO.getConfirmPassword();
            if (!newPassword.equals(confirmPassword)) {
                return ApiResponse.error("Passwords do not match", HttpStatus.BAD_REQUEST, null);
            }
            if (!HashUtil.verifyPassword(oldPassword, userPrincipal.getPassword())) {
                return ApiResponse.error("Old password is incorrect", HttpStatus.BAD_REQUEST, null);
            }
            if (HashUtil.verifyPassword(newPassword, userPrincipal.getPassword())) {
                return ApiResponse.error("New password cannot be the same as the old password", HttpStatus.BAD_REQUEST, null);
            }
            User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new CustomException("User not found"));
            user.setPassword(HashUtil.hashPassword(newPassword));
            userRepository.save(user);
            return ApiResponse.success(
                    "Password changed successfully",
                    HttpStatus.OK,
                    new UserResponseDTO(user)
            );
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }
}
