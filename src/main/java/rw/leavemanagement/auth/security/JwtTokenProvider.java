package rw.leavemanagement.auth.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import rw.leavemanagement.auth.dto.authentication.JWTTokenCreation;
import rw.leavemanagement.auth.exceptions.CustomException;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.rememberExpires}")
    private int jwtRememberExpirationInMs;

    @Value("${jwt.notRememberExpires}")
    private int jwtExpirationInMs;

    /**
     * Generates an access token for the authenticated user.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated access token.
     */
    public JWTTokenCreation generateAccessToken(Authentication authentication, Boolean rememberMe) {
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            Date now = new Date();
            long expirationInMs = rememberMe ? jwtRememberExpirationInMs : jwtExpirationInMs;
            Date expiryDate = new Date(now.getTime() + expirationInMs);
            logger.info("Token Expiration Time: {}", expiryDate);
            String token=  Jwts.builder()
                    .setId(userPrincipal.getId() + "")
                    .setSubject(userPrincipal.getId() + "")
                    .claim("user", userPrincipal)
                    .claim("roles", userPrincipal.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(expiryDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
            long ttlSeconds = expirationInMs / 1000;
            return new JWTTokenCreation(token, ttlSeconds);
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    /**
     * Retrieves the user ID from the provided token.
     *
     * @param token The JWT token.
     * @return The user ID extracted from the token.
     */
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * Validates the provided JWT token.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String authToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken)
                    .getBody();

            // Example: You can log roles or integrate them into SecurityContext if needed
            List<String> roles = claims.get("roles", List.class);
            logger.info("Roles from token: {}", roles);

            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token", ex);
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token" + ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token" + ex);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty" + ex);
        }
        return false;
    }
}
