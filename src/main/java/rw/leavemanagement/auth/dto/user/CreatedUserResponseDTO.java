package rw.leavemanagement.auth.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CreatedUserResponseDTO {
    private String email;
    private String password;
    private String phoneNumber;
    private String firstname;
    private String lastname;
}
