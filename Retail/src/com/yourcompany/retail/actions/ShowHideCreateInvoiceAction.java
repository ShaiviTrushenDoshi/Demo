package com.yourcompany.retail.actions; 

import org.openxava.actions.*; 

public class ShowHideCreateInvoiceAction
    extends OnChangePropertyBaseAction { 

    public void execute() throws Exception {
        if (isOrderCreated() && isDelivered() && !hasInvoice()) { 
            addActions("Order.createInvoice");
        }
        else {
            removeActions("Order.createInvoice");
        }
    }
	
    private boolean isOrderCreated() {
        return getView().getValue("oid") != null; 
    }
	
    private boolean isDelivered() {
        Boolean delivered = (Boolean) getView().getValue("delivered"); 
        return delivered == null?false:delivered;
    }

    private boolean hasInvoice() {
        return getView().getValue("invoice.oid") != null; 
    } 	
}

