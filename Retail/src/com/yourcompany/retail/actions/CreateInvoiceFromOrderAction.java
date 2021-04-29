package com.yourcompany.retail.actions; // In 'actions' package

import org.openxava.actions.*;

import com.yourcompany.retail.model.*;

public class CreateInvoiceFromOrderAction
    extends ViewBaseAction { 

    public void execute() throws Exception {
        if (getView().getValue("oid") == null) { 
            addError("impossible_create_invoice_order_not_exist");
            return;
        }
        Order order = (Order) getView().getEntity();     
        order.createInvoice(); 
        getView().refresh(); 
        addMessage("invoice_created_from_order", order.getInvoice());
        removeActions("Order.createInvoice");
    }
}

