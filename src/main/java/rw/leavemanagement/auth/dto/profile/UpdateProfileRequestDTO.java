package rw.leavemanagement.auth.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProfileRequestDTO {
    @Schema(example = "John")
    private String firstname;

    @Schema(example = "Doe")
    private String lastname;

}
