package rw.leavemanagement.auth.exceptions;

import java.io.Serial;

/**
 * Exception thrown when a resource is not found.
 */
public class NotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new NotFoundException with the specified detail message.
     *
     * @param message the detail message
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotFoundException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
