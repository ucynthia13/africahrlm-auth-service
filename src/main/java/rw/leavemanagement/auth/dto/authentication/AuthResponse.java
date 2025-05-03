package rw.leavemanagement.auth.dto.authentication;

import lombok.Data;
import rw.leavemanagement.auth.entity.User;

@Data
public class AuthResponse {
    private String token;
    private User user;

    public AuthResponse(String accessToken, User user) {
        this.token = accessToken;
        this.user = user;
    }
}