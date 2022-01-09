package org.sadness.transaction.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/7 0:49
 */
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JSONConstraintValidator.class)
public @interface JsonFormat {

    String message() default "JSON格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
