package com.projekt.projekt.Validation;

import com.projekt.projekt.Controllers.RestController;
import com.projekt.projekt.Notes.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class DictionaryValidator implements ConstraintValidator<Dictionary, String> {

    @Override
    public boolean isValid(String categoryName, ConstraintValidatorContext constraintValidatorContext)
    {
        boolean result;

        if(categoryName != null)
        {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String[]> response =
                    restTemplate.getForEntity(
                            "http://localhost:8080/category/categoryList",
                            String[].class);
            String[] slownik = response.getBody();
           result = (Arrays.asList(slownik).contains(categoryName));
            System.out.println(categoryName);

        }
        else {
            result = false;
        }
        return result;
    }
}
