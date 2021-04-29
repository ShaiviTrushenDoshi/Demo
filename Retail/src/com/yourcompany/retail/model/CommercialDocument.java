package com.yourcompany.retail.model;
 
import java.math.*;
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;

import com.yourcompany.retail.calculators.*;

import lombok.*;
 
@Embeddable @Entity @Getter @Setter
@View(members=
	"year, number, date," + 
	"data {" + 
	    "customer;" +
	    "details;" +
	    "remarks" +
	"}"
)
abstract public class CommercialDocument extends Deletable {

    @Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) 
    int year;
 
    @Column(length=6)
    @ReadOnly
    int number;
 
    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) 
    LocalDate date;
    
    @ManyToOne(fetch=FetchType.LAZY, optional=false) 
    @ReferenceView("Simple") 
    Customer customer;
    
    @ElementCollection
    @ListProperties(
        "product.number, product.description, quantity, pricePerUnit, " +
        "amount+[" + 
        	"commercialDocument.vatPercentage," +
        	"commercialDocument.vat," +
        	"commercialDocument.totalAmount" +
        "]" 
    )
    Collection<Detail> details;
    
    @Digits(integer=2, fraction=0) 
    @DefaultValueCalculator(VatPercentageCalculator.class)
    BigDecimal vatPercentage;
       
    @ReadOnly
    @Stereotype("MONEY")
    @Calculation("sum(details.amount) * vatPercentage / 100")
    BigDecimal vat;

    @ReadOnly
    @Stereotype("MONEY")
    @Calculation("sum(details.amount) + vat")    
    BigDecimal totalAmount;   
    
    @org.hibernate.annotations.Formula("TOTALAMOUNT * 0.10") 
    @Setter(AccessLevel.NONE) 
    @Stereotype("MONEY")
    BigDecimal estimatedProfit; 
 
    @Stereotype("MEMO")
    String remarks;
    
    @PrePersist 
    private void calculateNumber() throws Exception {
        Query query = XPersistence.getManager()
            .createQuery("select max(i.number) from " +
            getClass().getSimpleName() + 
            " i where i.year = :year");
        query.setParameter("year", year);
        Integer lastNumber = (Integer) query.getSingleResult();
        this.number = lastNumber == null ? 1 : lastNumber + 1;
    }
    
    public String toString() {
        return year + "/" + number;
    }
 
}