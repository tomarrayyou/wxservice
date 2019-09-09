package com.pingan.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: shouwangqingzhong
 * @date: 2019/9/6 14:54
 */
public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private static Logger logger = LoggerFactory.getLogger(EnumValidator.class);

    // 枚举校验注解
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {

        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        Class<?> cls = annotation.target();
        boolean ignoreEmpty = annotation.ignoreEmpty();

        // target为枚举，并且value有值，或者不忽视空值，才进行校验
        if (cls.isEnum() && ((null != value && value.length() > 0) || !ignoreEmpty)) {

            Object[] objects = cls.getEnumConstants();
            try {
                Method method = cls.getMethod("name");
                for (Object obj : objects) {
                    Object code = method.invoke(obj);
                    if (value.equals(code.toString())) {
                        result = true;
                        break;
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                logger.warn("EnumValidator call isValid() method exception.");
                result = false;
            }
        } else {
            result = true;
        }
        return result;
    }
}
