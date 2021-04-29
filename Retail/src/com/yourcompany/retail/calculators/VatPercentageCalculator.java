package com.yourcompany.retail.calculators; 

import org.openxava.calculators.*;

import com.yourcompany.retail.util.*; 
 
public class VatPercentageCalculator implements ICalculator {
 
    public Object calculate() throws Exception {
        return InvoicingPreferences.getDefaultVatPercentage();
    }
}
