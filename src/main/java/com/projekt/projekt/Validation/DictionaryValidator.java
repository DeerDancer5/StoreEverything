package com.projekt.projekt.Validation;

import com.projekt.projekt.Controllers.RestController;
import com.projekt.projekt.Notes.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validator;

public class DictionaryValidator implements ConstraintValidator<Dictionary, String> {
    private String categoryPrefix;

    @Override
    public void initialize(Dictionary dictionary){
        categoryPrefix = dictionary.value();
    }
    @Override
    public boolean isValid(String dict, ConstraintValidatorContext constraintValidatorContext)
    {
        boolean result;

        if(dict != null)
        {
            result = dict.contains(categoryPrefix);
        }
        else {
            result = true;
        }
        return result;
    }
}
