package rw.leavemanagement.auth.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserInformationDTO {
    @Schema(example = "example@aos.rw")
    @Email(message = "The email must be valid")
    @NotEmpty(message = "The 'email' field must not be empty")
    private String email;

    @Schema(example = "John")
    @NotEmpty(message = "The 'firstname' field must not be empty")
    private String firstname;

    @Schema(example = "Doe")
    @NotEmpty(message = "The 'lastname' field must not be empty")
    private String lastname;

    @Schema(example = "0788112233")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

}
