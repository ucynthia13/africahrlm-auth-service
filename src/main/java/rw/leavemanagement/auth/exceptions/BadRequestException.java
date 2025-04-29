package rw.leavemanagement.auth.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BadRequestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    // Constructor with a message parameter
    public BadRequestException(String message) {
        super(message);
    }

    // Constructor with both message and cause parameters
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}