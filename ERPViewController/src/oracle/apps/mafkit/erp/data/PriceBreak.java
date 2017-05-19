/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.data;

public class PriceBreak {
    private long _quantity;
    private double _newUnitPrice;
    private double _unitPrice;
    private double _percentDiscount;
    
    public PriceBreak() {
        super();
    }//constructor
    
    public PriceBreak(long quantity, double newUnitPrice, double unitPrice) {
        _quantity = quantity;
        _newUnitPrice = newUnitPrice;
        _unitPrice = unitPrice;
        _percentDiscount = 100 * (_unitPrice - _newUnitPrice) / _unitPrice;
        _percentDiscount = (double) Math.round(_percentDiscount * 100) / 100;
    }//constructor
    
    //Accessors
    public void setQuantity(long l) { _quantity = l; }
    public long getQuantity() { return _quantity; }
    public void setNewUnitPrice(double d) { _newUnitPrice = d; }
    public double getNewUnitPrice() { return _newUnitPrice; }
    public void setUnitPrice(double d) { _unitPrice = d; }
    public double getUnitPrice() { return _unitPrice; }
    public void setPercentDiscount(double d) { _percentDiscount = d; }
    public double getPercentDiscount() { return _percentDiscount; }
}//PriceBreak
