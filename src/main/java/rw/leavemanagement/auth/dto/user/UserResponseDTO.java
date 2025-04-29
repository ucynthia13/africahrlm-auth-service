package rw.leavemanagement.auth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import rw.leavemanagement.auth.entity.User;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private User user;
}
