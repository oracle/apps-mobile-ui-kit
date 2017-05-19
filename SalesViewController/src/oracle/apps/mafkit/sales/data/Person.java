/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.data;

public class Person {
    private long _id;
    private String _name;
    private String _image;
    private String _designation;
    private String _oraganization;

    public Person() {
        super();
    }//constructor

    public Person(long id, String name, String image, String designation) {
        _id = id;
        _name = name;
        _image = image;
        _designation = designation;
    }//constructor

    public Person(long id, String name, String image, String designation, String oraganization) {
        _id = id;
        _name = name;
        _image = image;
        _designation = designation;
        _oraganization = oraganization;
    }//constructor

    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setImage(String s) { _image = s; }
    public String getImage() { return _image; }
    public void setDesignation(String s) { _designation = s; }
    public String getDesignation() { return _designation; }
    public void setOraganization(String s) { _oraganization = s; }
    public String getOraganization() { return _oraganization; }
}//Person
