package rw.leavemanagement.auth.security;

import com.google.common.base.Joiner;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        // PasswordValidator with a set of rules
        PasswordValidator validator = new PasswordValidator(
                Arrays.asList(new LengthRule(8, 30), // Rule: Password length between 8 and 30 characters
                        new UppercaseCharacterRule(1), // Rule: At least 1 uppercase character
                        new LowercaseCharacterRule(1), // Rule: At least 1 lowercase character
                        new SpecialCharacterRule(1), // Rule: At least 1 special character
                        new DigitCharacterRule(1))); // Rule: At least 1 digit character

        // Validate the password against the rules
        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            // If the password is valid, return true
            return true;
        }

        // If the password is invalid, disable the default constraint violation and add
        // a custom violation message
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result)))
                .addConstraintViolation();

        // Return false to indicate that the password is invalid
        return false;
    }
}
