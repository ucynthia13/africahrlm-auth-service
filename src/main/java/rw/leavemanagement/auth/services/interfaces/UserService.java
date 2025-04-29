package rw.leavemanagement.auth.services.interfaces;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.dto.user.UpdateUserDTO;
import rw.leavemanagement.auth.dto.user.UserResponseDTO;
import rw.leavemanagement.auth.dto.user.UsersResponseDTO;
import rw.leavemanagement.auth.entity.User;


import java.util.List;

public interface UserService {

    // Find a user by their ID
    public User findUserById(String userId);

    // Get a list of users with pagination support
    public ResponseEntity<ApiResponse<UsersResponseDTO>> getUsers(Pageable pageable);

    // Get a user by their ID and return the response
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(String id);

    // Update a user with the provided ID and UpdateUserDTO, and return the response
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(String userId, UpdateUserDTO updateUserDTO);

    // Delete a user with the provided ID and return the response
    public ResponseEntity<ApiResponse<Object>> deleteUser(String userId);

}
