package rw.leavemanagement.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Boolean success; // Indicates whether the API response is successful or not

    private String message; // Message associated with the API response

    private HttpStatus status; // HTTP status code of the API response

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload; // Payload data of the API response (if any)

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error = null; // Error object associated with the API response (if any)

    private final String timestamp = LocalDateTime.now().toString(); // Timestamp of when the API response was created

    public ApiResponse(boolean success, String message, HttpStatus status, T payload, boolean isPayload) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.payload = payload;
        this.error = null;
    }

    public ApiResponse(boolean success, String message, HttpStatus status, Object error) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.payload = null;
        this.error = error;
    }

    public ApiResponse(Boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }

    // Creates a successful API response with the provided message, status, and payload
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, HttpStatus status, T payload) {
        ApiResponse<T> response = new ApiResponse<>(true, message, status, payload, true);
        return new ResponseEntity<>(response, status);
    }
    // Creates a successful API response with the provided message, status, and payload
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>(true, message, status);
        return new ResponseEntity<>(response, status);
    }

    // Creates an error API response with the provided message, status, and error object
    public static <T> ResponseEntity<ApiResponse<T>> error(String message, HttpStatus status, Object error) {
        ApiResponse<T> response = new ApiResponse<>(false, message, status, error);
        return new ResponseEntity<>(response, status);
    }

    // Creates a successful API response with payload and additional errors
    public static <T> ResponseEntity<ApiResponse<T>> successWithErrors(
            String message,
            HttpStatus status,
            T payload,
            List<String> errors
    ) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setStatus(status);
        response.setPayload(payload);
        response.setError(errors);
        return new ResponseEntity<>(response, status);
    }
}
