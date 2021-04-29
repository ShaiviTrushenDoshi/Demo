package com.yourcompany.retail.model;
 
import java.time.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.apache.commons.beanutils.*;
import org.openxava.annotations.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import com.yourcompany.retail.actions.*;

import lombok.*;
 
@Entity @Getter @Setter
@View(extendsView="super.DEFAULT", 
	members=
	    "estimatedDeliveryDays, delivered," + 
	    "invoice { invoice }" 
)
@View( name="NoCustomerNoInvoice", 
	members=                       
	    "year, number, date;" +    
	    "details;" +
	    "remarks"
) 
@Tab(baseCondition = "deleted = false")
@Tab(name="Deleted", baseCondition = "deleted = true")
public class Order extends CommercialDocument {
 
    @ManyToOne
    @ReferenceView("NoCustomerNoOrders") 
    @OnChange(ShowHideCreateInvoiceAction.class)
    Invoice invoice;
    
    @Column(columnDefinition="INTEGER DEFAULT 1")
    int deliveryDays;
    
    @Depends("date")
    public int getEstimatedDeliveryDays() {
        if (getDate().getDayOfYear() < 15) {
            return 20 - getDate().getDayOfYear(); 
        }
        if (getDate().getDayOfWeek() == DayOfWeek.SUNDAY) return 2;
        if (getDate().getDayOfWeek() == DayOfWeek.SATURDAY) return 3;
        return 1;
    }
    
    @Column(columnDefinition="BOOLEAN DEFAULT FALSE")
    @OnChange(ShowHideCreateInvoiceAction.class)
    boolean delivered;
    
    public void setDeleted(boolean deleted) {
        if (deleted) validateOnRemove(); 
        super.setDeleted(deleted);
    }
    
    @PrePersist @PreUpdate 
    private void recalculateDeliveryDays() {
        setDeliveryDays(getEstimatedDeliveryDays());
    }
    
    @AssertTrue(message="order_must_be_delivered")
	private boolean isDeliveredToBeInInvoice() {
	    return invoice == null || isDelivered(); 
	}
    
    @PreRemove 
    private void validateOnRemove() {
        if (invoice != null) { 
            throw new javax.validation.ValidationException( 
                XavaResources.getString( 
                    "cannot_delete_order_with_invoice"));
        }
    }
    
    public void createInvoice()
	    throws CreateInvoiceException 
	{
	    if (this.invoice != null) { 
	        throw new CreateInvoiceException( 
	            "order_already_has_invoice"); 
	    }
	    if (!isDelivered()) {
	        throw new CreateInvoiceException("order_is_not_delivered");
	    }
	    try {
	        Invoice invoice = new Invoice(); 
	        BeanUtils.copyProperties(invoice, this); 
	        invoice.setOid(null); 
	        invoice.setDate(LocalDate.now()); 
	        invoice.setDetails(new ArrayList<>(getDetails())); 
	        XPersistence.getManager().persist(invoice);
	        this.invoice = invoice; 
	    }
	    catch (Exception ex) { 
	        throw new SystemException( 
	            "impossible_create_invoice", ex);
	    }
	}
    
}
