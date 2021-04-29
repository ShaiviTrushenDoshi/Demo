package com.yourcompany.retail.model;
 
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import lombok.*;
 
@Entity @Getter @Setter
@View(extendsView="super.DEFAULT", 
	members="orders { orders }" 
)
@View( name="NoCustomerNoOrders", 
	members=                      
	    "year, number, date;" +   
	    "details;" +
	    "remarks"
)
@Tab(baseCondition = "deleted = false")
@Tab(name="Deleted", baseCondition = "deleted = true") 
public class Invoice extends CommercialDocument {
 
    @OneToMany(mappedBy="invoice")
    @CollectionView("NoCustomerNoInvoice")
    Collection<Order> orders; 
    
    public static Invoice createFromOrders(Collection<Order> orders)
        throws CreateInvoiceException
    {
        Invoice invoice = null;
        for (Order order: orders) {
            if (invoice == null) { 
                order.createInvoice();                                        
                invoice = order.getInvoice(); 
            }
            else { 
                order.setInvoice(invoice); 
                invoice.getDetails().addAll(order.getDetails()); 
                invoice.setVat(invoice.getVat().add(order.getVat())); 
                invoice.setTotalAmount(invoice.getTotalAmount().add(order.getTotalAmount()));
            } 
        } 
        if (invoice == null) { 
            throw new CreateInvoiceException(
                "orders_not_specified");
        }
        return invoice;
    }
    
}

