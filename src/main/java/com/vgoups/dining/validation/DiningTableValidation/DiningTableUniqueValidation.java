package com.vgoups.dining.validation.DiningTableValidation;

import com.vgoups.dining.repository.DiningTableRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;

public class DiningTableUniqueValidation implements ConstraintValidator<UniqueNameValidate, Object> {

    @Autowired
    private DiningTableRepository diningTableRepository;

    private String fieldName;

    @Override
    public void initialize(UniqueNameValidate uniqueNameValidate) {
        fieldName = uniqueNameValidate.fieldName();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            Field field = value.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            Object fieldValue = field.get(value);
            if (fieldValue == null) return true;

            Boolean exists = diningTableRepository.existsByName(fieldValue.toString());
            if(exists) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(fieldName + " already exists!")
                        .addPropertyNode(fieldName)
                        .addConstraintViolation();
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
