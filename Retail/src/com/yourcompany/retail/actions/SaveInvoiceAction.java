package com.yourcompany.retail.actions;

import org.openxava.actions.*;

public class SaveInvoiceAction
    extends SaveAction { 
	 
    public void execute() throws Exception {
        super.execute(); 
        closeDialog(); 
    }
}

