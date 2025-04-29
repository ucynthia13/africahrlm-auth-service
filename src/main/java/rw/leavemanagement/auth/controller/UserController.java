package rw.leavemanagement.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.leavemanagement.auth.dto.response.ApiResponse;
import rw.leavemanagement.auth.dto.user.CreatedUserResponseDTO;
import rw.leavemanagement.auth.dto.user.UpdateUserDTO;
import rw.leavemanagement.auth.dto.user.UserResponseDTO;
import rw.leavemanagement.auth.dto.user.UsersResponseDTO;
import rw.leavemanagement.auth.services.interfaces.UserService;
import rw.leavemanagement.auth.utils.Constants;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-users")
    public ResponseEntity<ApiResponse<UsersResponseDTO>> getUsers(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit
    ) {
        // Create a Pageable object for pagination
        Pageable pageable = (Pageable) PageRequest.of(page, limit, Sort.Direction.ASC, "createdAt");
        return userService.getUsers(pageable);
    }

    // Endpoint to get a user by their ID
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    // Endpoint to update a user by their ID
    @PatchMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable String userId, @RequestBody @Valid UpdateUserDTO updateUserDTO) {
        return userService.updateUser(userId, updateUserDTO);
    }

    // Endpoint to delete a user by their ID
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse<Object>> deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }


}
