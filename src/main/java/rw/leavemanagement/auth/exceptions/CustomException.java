package rw.leavemanagement.auth.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;

@Getter
@AllArgsConstructor
public class CustomException extends AbstractThrowableProblem {

    private final String message;
    private final Exception exception;

    // Constructor that takes an Exception object
    public CustomException(Exception exception) {
        this.exception = exception;
        this.message = exception.getMessage();
    }

    // Constructor that takes a message string
    public CustomException(String message) {
        this.exception = null;
        this.message = message;
    }
}
