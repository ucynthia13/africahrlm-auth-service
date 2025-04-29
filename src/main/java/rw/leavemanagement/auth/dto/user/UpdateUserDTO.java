package rw.leavemanagement.auth.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDTO extends UserInformationDTO {
    @Schema(example = "https://example.com/profile.jpg")
    private String profilePicture;
}

