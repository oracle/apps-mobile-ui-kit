/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.data;

public class Person {
    private long _id;
    private String _name;
    private String _image;
    private String _email;
    private String _phone;
    private String _addressLine1;
    private String _addressLine2;
    private String _city;
    private String _state;
    private String _postcode;

    public Person() {
        super();
    }//constructor

    public Person(long id, String name, String image, String email, String phone, String addressLine1, 
                  String addressLine2, String city, String state, String postcode) {
        _id = id;
        _name = name;
        _image = image;
        _email = email;
        _phone = phone;
        _addressLine1 = addressLine1;
        _addressLine2 = addressLine2;
        _city = city;
        _state = state;
        _postcode = postcode;
    }//constructor

    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setImage(String s) { _image = s; }
    public String getImage() { return _image; }
    public void setEmail(String s) { _email = s; }
    public String getEmail() { return _email; }
    public void setPhone(String s) { _phone = s; }
    public String getPhone() { return _phone; }
    public void setAddressLine1(String s) { _addressLine1 = s; }
    public String getAddressLine1() { return _addressLine1; }
    public void setAddressLine2(String s) { _addressLine2 = s; }
    public String getAddressLine2() { return _addressLine2; }
    public void setCity(String s) { _city = s; }
    public String getCity() { return _city; }
    public void setState(String s) { _state = s; }
    public String getState() { return _state; }
    public void setPostcode(String s) { _postcode = s; }
    public String getPostcode() { return _postcode; } 
}//Person
