package org.sadness.transaction.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/7 1:23
 */
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HttpMethodConstraintValidator.class)
public @interface HttpMethod {

    String message() default "HTTP协议方法错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
