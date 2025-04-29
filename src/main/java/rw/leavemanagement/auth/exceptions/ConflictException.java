package rw.leavemanagement.auth.exceptions;

import lombok.Getter;

import java.io.Serial;

/**
 * This exception is thrown when a conflict occurs in the application.
 * It is a subclass of the RuntimeException class.
 */
@Getter
public class ConflictException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new ConflictException with the specified detail message.
     *
     * @param message the detail message
     */
    public ConflictException(String message) {
        super(message);
    }

    /**
     * Constructs a new ConflictException with the specified detail message and
     * cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}