/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.data;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private long _id;
    private String _name;
    private String _description;
    private String _category;
    private String _image;
    private String _supplier;
    private String _supplierItemCode;
    private String _manufacturer;
    private String _manufacturerPartNumber;
    private double _unitPrice;
    private List<PriceBreak> _priceBreaks;
    private int _quantity; //denormalised for shopping basket
    private double _price; //denormalised for shopping basket
    
    public Product(){
        super();
        _priceBreaks = new ArrayList<PriceBreak>();
    }//constructor
    
    public Product(long id, String name, String description, String category, String image, String supplier,
                   String supplierItemCode, String manufacturer, String manufacturerPartNumber, double unitPrice,
                   List<PriceBreak> priceBreaks){
        _priceBreaks = new ArrayList<PriceBreak>();
        _id = id;
        _name = name;
        _description = description;
        _category = category;
        _image = image;
        _supplier = supplier;
        _supplierItemCode = supplierItemCode;
        _manufacturer = manufacturer;
        _manufacturerPartNumber = manufacturerPartNumber;
        _unitPrice = unitPrice;
        _priceBreaks = priceBreaks;
        _quantity = 0;
        _price = 0;
    }//constructor

    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setDescription(String s) { _description = s; }
    public String getDescription() { return _description; }
    public void setCategory(String s) { _category = s; }
    public String getCategory() { return _category; }
    public void setImage(String s) { _image = s; }
    public String getImage() { return _image; }
    public void setSupplier(String s) { _supplier = s; }
    public String getSupplier() { return _supplier; }
    public void setSupplierItemCode(String s) { _supplierItemCode = s; }
    public String getSupplierItemCode() { return _supplierItemCode; }
    public void setManufacturer(String s) { _manufacturer = s; }
    public String getManufacturer() { return _manufacturer; }
    public void setManufacturerPartNumber(String s) { _manufacturerPartNumber = s; }
    public String getManufacturerPartNumber() { return _manufacturerPartNumber; }
    public void setUnitPrice(double d) { _unitPrice = d; }
    public double getUnitPrice() { return _unitPrice; }
    public void setPriceBreaks(List<PriceBreak> l) { _priceBreaks = l; }
    public List<PriceBreak> getPriceBreaks() { return _priceBreaks; }
    public void setQuantity(int i) { 
        _quantity = i; 
        //Check for price breaks
        _price = _quantity * _unitPrice;
        if (_priceBreaks.size() > 0) {
            for (PriceBreak pb : _priceBreaks){
                if (_quantity >= pb.getQuantity())
                    _price = _quantity * pb.getNewUnitPrice();
            }//loop through price breaks
        }//check for price breaks
        //Round off to 2 digits
        _price = (double) Math.round(_price * 100) / 100;
    }//setQuantity
    public int getQuantity() { return _quantity; }
    public void setPrice(double d) { _price = d; }
    public double getPrice() { return _price; }
}//Product
