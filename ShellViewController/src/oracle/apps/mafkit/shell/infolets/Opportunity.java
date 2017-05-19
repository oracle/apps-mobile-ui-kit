/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.infolets;

public class Opportunity {
    private Double _opportunityAmount;
    private String _quarter;
    
    public Opportunity() {
        super();
    }//constructor
    
    public Opportunity(Double opportunityAmount, String quarter) {
        super();
        _opportunityAmount = opportunityAmount;
        _quarter = quarter;
    }//constructor

    //Accessors
    public void setOpportunityAmount(Double d) { _opportunityAmount = d; }
    public Double getOpportunityAmount() { return _opportunityAmount; }
    public void setQuarter(String s) { _quarter = s; }
    public String getQuarter() { return _quarter; }
}//Opportunity
