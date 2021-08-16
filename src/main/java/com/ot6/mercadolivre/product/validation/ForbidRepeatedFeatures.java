package com.ot6.mercadolivre.product.validation;

import com.ot6.mercadolivre.product.dtos.NewProductRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class ForbidRepeatedFeatures implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return NewProductRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        NewProductRequest productRequest = (NewProductRequest) obj;
        Set<String> repeatedFeaturesNames = productRequest.getRepeatedFeaturesNames();

        if(repeatedFeaturesNames.size() > 0) {
            errors.rejectValue(
                    "features",
                    null,
                    "Características repetidas (" + repeatedFeaturesNames + ")");
        }
    }
}
