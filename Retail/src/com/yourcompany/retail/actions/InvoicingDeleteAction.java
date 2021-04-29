package com.yourcompany.retail.actions;
 
import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
 
public class InvoicingDeleteAction extends ViewBaseAction {

    public void execute() throws Exception {
        if (!getView().getMetaModel().containsMetaProperty("deleted")) {
            executeAction("CRUD.delete"); 
            return;                       
        }
   	
        Map<String, Object> values = new HashMap<>(); 
        values.put("deleted", true);
        MapFacade.setValues(getModelName(), getView().getKeyValues(), values); 
        resetDescriptionsCache(); 
        addMessage("object_deleted", getModelName());
        getView().clear();
        getView().setKeyEditable(true);
        getView().setEditable(false); 
    }
}

