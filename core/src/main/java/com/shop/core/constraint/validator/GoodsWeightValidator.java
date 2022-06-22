package com.shop.core.constraint.validator;

import com.shop.core.constraint.annotation.GoodsWeight;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GoodsWeightValidator implements ConstraintValidator<GoodsWeight, Double> {

    @Override
    public boolean isValid(Double weight, ConstraintValidatorContext constraintValidatorContext) {
        return weight < 100;
    }
}
