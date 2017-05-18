/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.data;

public class RequisitionLine {
    private Product _product;
    private int _numberOfItems;
    private double _lineValue;
    
    public RequisitionLine() {
        super();
    }//constructor
    
    public RequisitionLine(Product product, int numberOfItem) {
        _product = product;
        _numberOfItems = numberOfItem;
        _lineValue = _numberOfItems * product.getUnitPrice();
    }//constructor
    
    //Accessors
    public void setProduct(Product p) { _product = p; }
    public Product getProduct() { return _product; }
    public void setNumberOfItems(int i) { _numberOfItems = i; }
    public int getNumberOfItems() { return _numberOfItems; }
    public void setLineValue(double d) { _lineValue = d; }
    public double getLineValue() { return _lineValue; }
}//RequisitionLine
