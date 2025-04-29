package rw.leavemanagement.auth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import rw.leavemanagement.auth.entity.User;


@Data
@AllArgsConstructor
public class UsersResponseDTO {
    Page<User> users;
}
