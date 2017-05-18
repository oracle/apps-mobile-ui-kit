/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.home;

public class HomeBean {
    private String[] _barChartColors = { "rgb(255, 176, 96)", "rgb(255, 117, 95)", "rgb(130, 200, 121)", 
                                         "rgb(83, 167, 215", "rgb(123, 123, 181)", "rgb(212, 116, 149)" };
    
    public HomeBean(){
        super();
    }//constructor

    //Accessors
    public void setBarChartColors(String[] barChartColors) { _barChartColors = barChartColors; }
    public String[] getBarChartColors() { return _barChartColors; }
    
}//HomeBean
