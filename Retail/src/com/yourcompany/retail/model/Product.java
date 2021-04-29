package com.yourcompany.retail.model;
 
import java.math.*;

import javax.persistence.*;

import org.hibernate.validator.constraints.*;
import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
@View(members=
"number, description;" +
"Details { author, category, isbn, price;"+
		" manufacture, store, storeID}" +
"Remarks { photos; remarks; }"
)
//@Tab(properties="description, number")
public class Product {
 
    @Id @Column(length=9)
    int number;
 
    @Column(length=50) @Required
    String description;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Author author;
    
    @ManyToOne( 
        fetch=FetchType.LAZY, 
        optional=true) 
    @DescriptionsList 
    Category category;
    
    @Column(length=13) @ISBN
    String isbn;
    
    @Stereotype("MONEY") 
    BigDecimal price;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList
    Manufacture manufacture;
    
    @ManyToOne( 
        fetch=FetchType.LAZY, 
        optional=true) 
    @DescriptionsList 
    Store store;
    
    @Column(length=50) @Required
    String storeID;
     
    @Stereotype("IMAGES_GALLERY") 
    @Column(length=32) 
    String photos;
     
    @Stereotype("MEMO")
    String remarks;
 
}