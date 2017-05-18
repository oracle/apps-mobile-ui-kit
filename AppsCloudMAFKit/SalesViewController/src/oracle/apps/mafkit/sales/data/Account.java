/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.data;

public class Account {
    private long _id;
    private String _name;

    public Account() {
        super();
    }//constructor

    public Account(long id, String name) {
        _id = id;
        _name = name;
    }//constructor
    
    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
}//Account
