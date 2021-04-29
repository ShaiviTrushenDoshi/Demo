package com.yourcompany.retail.model;

import java.math.*;
import java.time.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;

/**
 * This is an example of an entity.
 * 
 * Feel free feel to rename, modify or remove at your taste.
 */

@Embeddable @Entity @Getter @Setter
public class YourFirstEntity extends Identifiable {
	
	@Column(length=50) @Required
	String description;
	
	LocalDate date;
	
	BigDecimal amount;

}
