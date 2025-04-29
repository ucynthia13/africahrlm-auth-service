package rw.leavemanagement.auth.security;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class) // Specifies the validator class for this annotation
@Target({ TYPE, FIELD, ANNOTATION_TYPE }) // Specifies where this annotation can be applied (type, field, or annotation)
@Retention(RUNTIME) // Specifies that this annotation will be retained at runtime
public @interface ValidPassword {

    String message() default "Invalid Password"; // Specifies the default error message for validation failure

    Class<?>[] groups() default {}; // Specifies the validation groups to which this constraint belongs

    Class<? extends Payload>[] payload() default {}; // Specifies the payload associated with this constraint
}
