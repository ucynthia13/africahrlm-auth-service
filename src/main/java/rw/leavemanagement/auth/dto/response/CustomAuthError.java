package rw.leavemanagement.auth.dto.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class represents a custom authentication error handler.
 * It implements the AuthenticationEntryPoint interface, which is used to handle authentication failures.
 */
@Component
public class CustomAuthError implements AuthenticationEntryPoint {

    /**
     * This method is called when an authentication failure occurs.
     * It sets the response status to UNAUTHORIZED (401), sets the content type to JSON,
     * and writes an error message to the response body.
     *
     * @param request        the HttpServletRequest object representing the current request
     * @param response       the HttpServletResponse object representing the current response
     * @param authException  the AuthenticationException object representing the authentication failure
     * @throws IOException      if an I/O error occurs while writing the response
     * @throws ServletException  if a servlet-specific error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{ \"error\": \"Unauthorized\" }");
    }
}
