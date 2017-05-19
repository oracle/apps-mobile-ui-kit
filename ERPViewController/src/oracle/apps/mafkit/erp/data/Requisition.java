/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Requisition {
    private String _number;
    private String _description;
    private Date _date;
    private List<RequisitionLine> _lines = new ArrayList<RequisitionLine>();
    private double _amount = 0;
    private double _discount = 0;
    private double _finalAmount = 0;
    private Person _requestor;
    private String _status;
    private String _image;
    
    public Requisition() {
        super();
    }//constrcutor
    
    public Requisition(String number, Date date, List<RequisitionLine> lines, double discount, Person requestor) {
        _number = number;
        if (lines.size() > 0)
            _description = lines.get(0).getProduct().getName();
        else
            _description = "Procurement Requisition";
        _date = date;
        _lines = lines;
        _amount = 0;
        if (lines.size() > 0)
            for (RequisitionLine reqLine : lines)
                _amount += reqLine.getLineValue();
        _discount = discount;
        _finalAmount = _amount - _discount;
        _requestor = requestor;
        _status = "Pending Approval";
        if (lines.size() > 0)
            _image = lines.get(0).getProduct().getImage();
        else
            _image = "/resources/images/shopping_cart_160.png";
    }//constrcutor
    
    //Accessors
    public void setNumber(String s) { _number = s; }
    public String getNumber() { return _number; }
    public void setDescription(String s) { _description = s; }
    public String getDescription() { return _description; }
    public void setDate(Date d) { _date = d; }
    public Date getDate() { return _date; }
    public void setLines(List<RequisitionLine> l) { _lines = l; }
    public List<RequisitionLine> getLines() { return _lines; }
    public void setAmount(double d) { _amount = d; }
    public double getAmount() { return _amount; }
    public void setDiscount(double d) { _discount = d; }
    public double getDiscount() { return _discount; }
    public void setFinalAmount(double d) { _finalAmount = d; }
    public double getFinalAmount() { return _finalAmount; }
    public void setRequestor(Person p) { _requestor = p; }
    public Person getRequestor() { return _requestor; }
    public void setStatus(String s) { _status = s; }
    public String getStatus() { return _status; }
    public void setImage(String i) { _image = i; }
    public String getImage() { return _image; }
}//Requisition
