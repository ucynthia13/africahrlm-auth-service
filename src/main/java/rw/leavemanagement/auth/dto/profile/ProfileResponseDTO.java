package rw.leavemanagement.auth.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import rw.leavemanagement.auth.entity.User;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private User user;
}
