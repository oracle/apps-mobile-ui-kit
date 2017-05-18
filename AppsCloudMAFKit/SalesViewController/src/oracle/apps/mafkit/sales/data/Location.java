/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.data;

public class Location {
    private double _pointX;
    private double _pointY;
    private String _type;
    private String _shortDesc;
    private String _address;

    public Location() {
        super();
    }//constructor
    
    public Location(double pointX, double pointY, String type, String address) {
        _pointX = pointX;
        _pointY = pointY;
        _type = type;
        _address = address;
    }//constructor

    public Location(double pointX, double pointY, String type, String shortDesc, String address) {
        _pointX = pointX;
        _pointY = pointY;
        _type = type;
        _shortDesc = shortDesc;
        _address = address;
    }//constructor

    //Accessors
    public void setPointX(double d) { _pointX = d; }
    public double getPointX() { return _pointX; }
    public void setPointY(double d) { _pointY = d; }
    public double getPointY() { return _pointY; }
    public void setType(String s) { _type = s; }
    public String getType() { return _type; }
    public void setShortDesc(String s) { _shortDesc = s; }
    public String getShortDesc() { return _shortDesc; }
    public void setAddress(String s) { _address = s; }
    public String getAddress() { return _address; }
}//Location
