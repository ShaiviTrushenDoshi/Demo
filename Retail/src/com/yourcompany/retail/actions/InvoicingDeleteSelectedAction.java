package com.yourcompany.retail.actions;
 
import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;
import org.openxava.model.meta.*;

import lombok.*;
 
public class InvoicingDeleteSelectedAction
    extends TabBaseAction 
    implements IChainActionWithArgv { 
 
    private String nextAction = null; 
    
    @Getter @Setter
    boolean restore; 
 
    public void execute() throws Exception {
        if (!getMetaModel().containsMetaProperty("deleted")) {
            nextAction="CRUD.deleteSelected"; 
            return;    
        }
        markSelectedEntitiesAsDeleted(); 
    }
 
    private MetaModel getMetaModel() {
        return MetaModel.get(getTab().getModelName());
    }
 
    public String getNextAction() 
        throws Exception
    {
        return nextAction; 
    }
 
    public String getNextActionArgv() throws Exception {
        return "row=" + getRow();
    }
 
    private void markSelectedEntitiesAsDeleted() throws Exception {
        Map<String, Object> values = new HashMap<>(); 
        values.put("deleted", !isRestore()); 
        Map<String, Object>[] selectedOnes = getSelectedKeys(); 
        if (selectedOnes != null) {
            for (int i = 0; i < selectedOnes.length; i++) { 
                Map<String, Object> key = selectedOnes[i]; 
                try {
                    MapFacade.setValues(  
                        getTab().getModelName(),
                        key,
                        values);
                }
                catch (javax.validation.ValidationException ex) { 
                    addError("no_delete_row", i, key);
                    addError("remove_error", getTab().getModelName(), ex.getMessage()); 
                }
                catch (Exception ex) { 
                    addError("no_delete_row", i, key); 
                }
            }
        }
        getTab().deselectAll(); 
        resetDescriptionsCache(); 
    }
 
}

