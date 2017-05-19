/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.data;

public class Product {
    private long _id;
    private String _name;
    private Double _amount;
    private boolean _recurring = false;
    
    public Product() {
        super();
    }//constructor

    public Product(long id, String name, Double amount) {
        _id = id;
        _name = name;
        _amount = amount;
    }//constructor

    public Product(long id, String name, Double amount, boolean recurring) {
        _id = id;
        _name = name;
        _amount = amount;
        _recurring = recurring;
    }//constructor

    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setAmount(Double d) { _amount = d; }
    public Double getAmount() { return _amount; }
    public void setRecurring(boolean b) { _recurring = b; }
    public boolean isRecurring() { return _recurring; }
}//Product
