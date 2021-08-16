package com.ot6.mercadolivre.shared.validation;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistingIdValidator implements ConstraintValidator<ExistingId, Long> {

    private String field;
    private Class<?> theClass;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void initialize(ExistingId constraintAnnotation) {
        field = constraintAnnotation.field();
        theClass = constraintAnnotation.theClass();
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true;

        Query query = entityManager.createQuery(
                "select 1 from " + theClass.getName() + " where " + field + " = :pValue"
        );
        query.setParameter("pValue", value);
        List<?> list = query.getResultList();

        Assert.state(
                list.size() <= 1,
                "múltiplos atributos, do tipo " + field + ", da classe, " + theClass.getName() + ", foram encontrados"
        );

        return list.size() == 1;
    }
}
