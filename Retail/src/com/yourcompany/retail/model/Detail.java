package com.yourcompany.retail.model;
 
import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.yourcompany.retail.calculators.*;

import lombok.*;
 
@Embeddable @Getter @Setter
public class Detail {
 
    int quantity;
 
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    Product product;
    
    @DefaultValueCalculator(
	    value=PricePerUnitCalculator.class, // This class calculates the initial value
	    properties=@PropertyValue(
	        name="productNumber", // The productNumber property of the calculator...
	        from="product.number") // ...is filled from product.number of the detail
	)
	@Stereotype("MONEY")
	BigDecimal pricePerUnit; // A regular persistent property
     
    @Stereotype("MONEY")
    @Depends("pricePerUnit, quantity") // pricePerUnit instead of product.number
    public BigDecimal getAmount() {
        if (pricePerUnit == null) return BigDecimal.ZERO; // pricePerUnit instead of product and product.getPrice()
        return new BigDecimal(quantity).multiply(pricePerUnit); // pricePerUnit instead of product.getPrice()
    }
 
}