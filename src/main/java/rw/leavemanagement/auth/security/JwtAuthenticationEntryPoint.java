package rw.leavemanagement.auth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import rw.leavemanagement.auth.dto.response.ApiResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        // Set the response status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Set the response content type to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Get the token from the request header
        String token = request.getHeader("Authorization");

        // Create an ApiResponse object with appropriate error message
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                false,
                token == null ? "Missing Token" : "Invalid Token",
                HttpStatus.UNAUTHORIZED,
                null);

        // Get the output stream from the response
        ServletOutputStream out = response.getOutputStream();
        // Write the ApiResponse object as JSON to the output stream
        new ObjectMapper().writeValue(out, apiResponse);
        // Flush the output stream
        out.flush();
    }
}
