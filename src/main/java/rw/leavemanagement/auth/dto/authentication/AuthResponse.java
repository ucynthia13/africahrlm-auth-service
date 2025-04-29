package rw.leavemanagement.auth.dto.authentication;

import lombok.Data;
import rw.leavemanagement.auth.entity.User;

@Data
public class AuthResponse {
    private String token;
    private String email;

    public AuthResponse(String accessToken, User user) {
        this.token = accessToken;
        this.email = user.getEmail();
    }
}
