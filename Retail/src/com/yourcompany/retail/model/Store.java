package com.yourcompany.retail.model;
 
import javax.persistence.*;

import lombok.*;
 
@Entity @Getter @Setter
public class Store extends Identifiable {
  
    @Column(length=50)
    String description;
 
}