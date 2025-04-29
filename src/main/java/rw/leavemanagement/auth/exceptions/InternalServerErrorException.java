package rw.leavemanagement.auth.exceptions;

import java.io.Serial;

/**
 * This exception is thrown when an internal server error occurs.
 * It is a subclass of RuntimeException.
 */
public class InternalServerErrorException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new InternalServerErrorException with the specified detail message.
     *
     * @param message the detail message
     */
    public InternalServerErrorException(String message) {
        super(message);
    }

    /**
     * Constructs a new InternalServerErrorException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}