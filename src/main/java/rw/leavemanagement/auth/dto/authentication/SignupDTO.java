package rw.leavemanagement.auth.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import rw.leavemanagement.auth.entity.User;
import rw.leavemanagement.auth.enumerations.EUserRole;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Schema(example = "example@gmail.com")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Schema(example = "password@123")
    @NotBlank(message = "Email is required")
    private String password;


    public SignupDTO(User user) {
        this.firstName = user.getFirstname();
        this.lastName = user.getLastname();
        this.email = user.getEmail();
        this.department = user.getDepartment();
        this.role = String.valueOf(user.getRole());
        this.phoneNumber = user.getPhoneNumber();
    }
}
