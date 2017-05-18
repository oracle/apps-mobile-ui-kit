/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.data;

public class OrderedStage {
    private int _id;
    private String _name;

    public OrderedStage() {
        super();
    }//constructor
    
    public OrderedStage(String name, int id) { 
        _id = id;
        _name = name;
    }//constructor

    public OrderedStage(int id, String name) {
        _id = id;
        _name = name;
    }//constructor
    
    //Accessors
    public void setId(int i) { _id = i; }
    public int getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
}//OrderedStage
