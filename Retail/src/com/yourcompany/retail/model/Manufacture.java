package com.yourcompany.retail.model;
 
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Manufacture extends Identifiable {
 
    @Column(length=50) @Required
    String name;
    
    @OneToMany(mappedBy="author")
    @ListProperties("number, description, price")
    Collection<Product> products;
 
}