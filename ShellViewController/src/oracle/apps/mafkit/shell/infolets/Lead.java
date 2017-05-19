/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.infolets;

public class Lead {
    private Double _leadAmount;
    private String _quarter;

    public Lead() {
        super();
    }//constructor
    
    public Lead(Double leadAmount, String quarter) {
        super();
        _leadAmount = leadAmount;
        _quarter = quarter;
    }//constructor

    //Accessors
    public void setLeadAmount(Double d) { _leadAmount = d; }
    public Double getLeadAmount() { return _leadAmount; }
    public void setQuarter(String s) { _quarter = s; }
    public String getQuarter() { return _quarter; }
}//Lead
