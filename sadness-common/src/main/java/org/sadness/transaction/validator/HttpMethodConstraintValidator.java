package org.sadness.transaction.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/7 0:41
 */
public class HttpMethodConstraintValidator implements ConstraintValidator<HttpMethod, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return org.springframework.http.HttpMethod.resolve(value.toUpperCase()) != null;
    }

}
