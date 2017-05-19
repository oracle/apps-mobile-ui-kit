/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.data;

public class ProductCategory {
    private String _name;
    private String _image;

    public ProductCategory() {
        super();
    }//constructor

    public ProductCategory(String name, String image) {
        _name = name;
        _image = image;
    }//constructor

    //Accessors
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setImage(String s) { _image = s; }
    public String getImage() { return _image; }
}//ProductCategory
