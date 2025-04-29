package rw.leavemanagement.auth.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    @Schema(example = "password@123")
    @NotBlank(message = "New password is required")
    private String oldPassword;

    @Schema(example = "password@123")
    @NotBlank(message = "New password is required")
    private String newPassword;
}
