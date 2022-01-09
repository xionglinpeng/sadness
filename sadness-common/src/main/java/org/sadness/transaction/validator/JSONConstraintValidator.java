package org.sadness.transaction.validator;

import com.alibaba.fastjson.JSONObject;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/7 0:41
 */
public class JSONConstraintValidator implements ConstraintValidator<JsonFormat, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        try {
            return JSONObject.parse(value) instanceof JSONObject;
        } catch (Exception e) {
            return false;
        }
    }

}
