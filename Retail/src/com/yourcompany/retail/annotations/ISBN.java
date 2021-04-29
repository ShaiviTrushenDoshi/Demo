package com.yourcompany.retail.annotations; 

import java.lang.annotation.*;

import javax.validation.*;
 
@Constraint(validatedBy = com.yourcompany.retail.validators.ISBNValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN { 
 
	boolean search() default true; 
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
    String message() default "isbn_invalid"; 
}

