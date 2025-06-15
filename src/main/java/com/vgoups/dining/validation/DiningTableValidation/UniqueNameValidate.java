package com.vgoups.dining.validation.DiningTableValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DiningTableUniqueValidation.class)
@Documented
public @interface UniqueNameValidate {
    String message() default "name already exists";

    String fieldName();

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
