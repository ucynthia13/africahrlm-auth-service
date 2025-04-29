package rw.leavemanagement.auth.services.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.dto.user.UpdateUserDTO;
import rw.leavemanagement.auth.dto.user.UserResponseDTO;
import rw.leavemanagement.auth.dto.user.UsersResponseDTO;
import rw.leavemanagement.auth.entity.User;
import rw.leavemanagement.auth.enumerations.EStatus;
import rw.leavemanagement.auth.exceptions.CustomException;
import rw.leavemanagement.auth.exceptions.InternalServerErrorException;
import rw.leavemanagement.auth.exceptions.NotFoundException;
import rw.leavemanagement.auth.repository.IUserRepository;
import rw.leavemanagement.auth.services.interfaces.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final IUserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponse<UsersResponseDTO>> getUsers(Pageable pageable) {
        try {
            Page<User> users = userRepository.findAll(pageable);

            return ApiResponse.success("Successfully fetched users", HttpStatus.OK, new UsersResponseDTO(users));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(String uuid) {
        try {
            User user = findUserById(uuid);
            return ApiResponse.success("Successfully fetched user", HttpStatus.OK, new UserResponseDTO(user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public User findUserById(String userId) {
        return userRepository.findUserById(userId)
                        .orElseThrow(() -> new NotFoundException("The Resource was not found"));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(String userId, UpdateUserDTO updateUserDTO) {
        try {
            User user = findUserById(userId);
            if (updateUserDTO.getEmail() != null)
                user.setEmail(updateUserDTO.getEmail());
            if (updateUserDTO.getFirstname() != null)
                user.setFirstname(updateUserDTO.getFirstname());
            if (updateUserDTO.getLastname() != null)
                user.setLastname(updateUserDTO.getLastname());
            if (updateUserDTO.getProfilePicture() != null)
                user.setProfilePicture(updateUserDTO.getProfilePicture());

            return ApiResponse.success("Successfully updated the user", HttpStatus.OK, new UserResponseDTO(user));
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Object>> deleteUser(String userId) {
        try {
            User user = findUserById(userId);
            user.setStatus(EStatus.INACTIVE);
            userRepository.save(user);
            return ApiResponse.success("Successfully deleted the user", HttpStatus.OK, null);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
