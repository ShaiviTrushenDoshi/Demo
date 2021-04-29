package com.yourcompany.retail.actions;

import java.util.*;

import javax.ejb.*;

import org.openxava.actions.*;
import org.openxava.model.*;

import com.yourcompany.retail.model.*;

public class CreateInvoiceFromSelectedOrdersAction  extends TabBaseAction { 

    public void execute() throws Exception {
        Collection<Order> orders = getSelectedOrders(); 
        Invoice invoice = Invoice.createFromOrders(orders); 
        addMessage("invoice_created_from_orders", invoice, orders); 
        
        showDialog(); 
        
        getView().setModel(invoice); 
        getView().setKeyEditable(false); 
        setControllers("InvoiceEdition"); 
    }

    private Collection<Order> getSelectedOrders() throws FinderException {
        Collection<Order> result = new ArrayList<>();
        for (Map key: getTab().getSelectedKeys()) { 
            Order order = (Order) MapFacade.findEntity("Order", key); 
            result.add(order);
        }
        return result;
    }
}
