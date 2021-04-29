package com.yourcompany.retail.actions;
 
import java.util.*;

import javax.ejb.*;

import org.openxava.actions.*;
 
public class SearchExcludingDeletedAction
    extends SearchExecutingOnChangeAction { 
 
    private boolean isDeletable() { 
        return getView().getMetaModel()
            .containsMetaProperty("deleted");
    }
 
    protected Map getValuesFromView() 
        throws Exception 
    {
        if (!isDeletable()) { 
            return super.getValuesFromView();
        }
        Map<String, Object> values = super.getValuesFromView();
        values.put("deleted", false); 
        return values;
    }
 
    protected Map getMemberNames() 
        throws Exception
    {
        if (!isDeletable()) { 
            return super.getMemberNames();
        }
        Map<String, Object> members = super.getMemberNames();
        members.put("deleted", null);  
        return members; 
    }
 
    protected void setValuesToView(Map values) 
        throws Exception 
    {
        if (isDeletable() && 
            (Boolean) values.get("deleted")) { 
                throw new ObjectNotFoundException(); 
        } 
        else {
            super.setValuesToView(values); 
        }
    }
}
