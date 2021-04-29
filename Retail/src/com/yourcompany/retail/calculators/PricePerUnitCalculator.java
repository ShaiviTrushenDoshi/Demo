package com.yourcompany.retail.calculators; 

import static org.openxava.jpa.XPersistence.getManager;

import org.openxava.calculators.*;

import com.yourcompany.retail.model.*;

import lombok.*;
 
public class PricePerUnitCalculator implements ICalculator {
 
    @Getter @Setter
    int productNumber; 
 
    public Object calculate() throws Exception {
        Product product = getManager() 
            .find(Product.class, productNumber); 
        return product.getPrice(); 
    }
 
}

