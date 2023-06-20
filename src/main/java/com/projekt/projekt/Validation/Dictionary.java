package com.projekt.projekt.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Constraint(validatedBy = DictionaryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dictionary {
    public String value() default "tak";
    public String message() default "Must be a word from a dictionary";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
