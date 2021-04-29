package com.yourcompany.retail.validators; 

import javax.validation.*;
import javax.ws.rs.client.*; // To use JAX-RS

import org.apache.commons.logging.*; // To use Log
import org.openxava.util.*;

import com.yourcompany.retail.annotations.*;
 
public class ISBNValidator
    implements ConstraintValidator<ISBN, Object> {
	
    private static Log log = LogFactory.getLog(ISBNValidator.class); 
 
    private static org.apache.commons.validator.routines.ISBNValidator
        validator = 
            new org.apache.commons.validator.routines.ISBNValidator();
    
    private boolean search; 
 
    public void initialize(ISBN isbn) {
    	this.search = isbn.search();
    }
 
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (Is.empty(value)) return true;
        if (!validator.isValid(value.toString())) return false;
        return search ? isbnExists(value) : true; 
    }
    
    private boolean isbnExists(Object isbn) {
        try {
            String response = ClientBuilder.newClient()
                .target("http://openlibrary.org/") 
                .path("/api/books") 
                .queryParam("jscmd", "data") 
                .queryParam("format", "json")
                .queryParam("bibkeys", "ISBN:" + isbn) 
                .request()
                .get(String.class); 
            return !response.equals("{}"); 
        }
        catch (Exception ex) {
            log.warn("Impossible to connect to openlibrary.org " +
                "to validate the ISBN. Validation fails", ex);
            return false; 
        }
    }
    
}

