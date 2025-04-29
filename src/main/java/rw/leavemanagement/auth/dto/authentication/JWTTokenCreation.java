package rw.leavemanagement.auth.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class JWTTokenCreation {
    private String token;
    private long ttlSeconds;

}
