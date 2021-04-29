package com.yourcompany.retail.model; 

import org.openxava.util.*;

public class CreateInvoiceException extends Exception { 

    public CreateInvoiceException(String message) {
        super(XavaResources.getString(message)); 
    }
	
}
