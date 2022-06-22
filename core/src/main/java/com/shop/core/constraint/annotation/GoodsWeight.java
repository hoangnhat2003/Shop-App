package com.shop.core.constraint.annotation;

import com.shop.core.constraint.validator.GoodsWeightValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GoodsWeightValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GoodsWeight {

    String message() default "Goods weight must small less than 100kg";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
