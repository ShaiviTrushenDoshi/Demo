package com.yourcompany.retail.model;
 
import javax.persistence.*;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;

import lombok.*;
 
@MappedSuperclass 
@Getter @Setter
public class Identifiable {
 
    @Id @GeneratedValue(generator="system-uuid") @Hidden
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(length=32)
    String oid; 
 
}

