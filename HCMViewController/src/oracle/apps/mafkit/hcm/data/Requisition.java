/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.hcm.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Requisition {
    private String _id;
    private String _name;
    private Date _postingDate;
    private List<Long> _newList = new ArrayList<Long>();
    private List<Long> _reviewList = new ArrayList<Long>();
    private List<Long> _evaluationList = new ArrayList<Long>();
    private List<Long> _selectionList = new ArrayList<Long>();
    private int _newCount;
    private int _reviewCount;
    private int _evaluationCount;
    private int _selectionCount;
    private int _totalCandidateCount;
    private String _city;
    private String _state;
    private String _country;
    
    public Requisition(){
        super();
    }//constructor
    
    public Requisition(String id, String name, Date postingDate, List<Long> newList, List<Long> reviewList, 
                       List<Long> evaluationList, List<Long> selectionList, String city, String state, String country){
        _id = id;
        _name = name;
        _postingDate = postingDate;
        _newList = newList;
        _reviewList = reviewList;
        _evaluationList = evaluationList;
        _selectionList = selectionList;
        _newCount = _newList.size();
        _reviewCount = _reviewList.size();
        _evaluationCount = _evaluationList.size();
        _selectionCount = _selectionList.size();
        _totalCandidateCount = _newCount + _reviewCount + _evaluationCount + _selectionCount;
        _city = city;
        _state = state;
        _country = country;
    }//constructor
    
    //Accessors
    public void setId(String s) { _id = s; }
    public String getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setPostingDate(Date d) { _postingDate = d; }
    public Date getPostingDate() { return _postingDate; }
    public void setNewList(List<Long> l) { _newList = l; }
    public List<Long> getNewList() { return _newList; }
    public void setReviewList(List<Long> l) { _reviewList = l; }
    public List<Long> getReviewList() { return _reviewList; }
    public void setEvaluationList(List<Long> l) { _evaluationList = l; }
    public List<Long> getEvaluationList() { return _evaluationList; }
    public void setSelectionList(List<Long> l) { _selectionList = l; }
    public List<Long> getSelectionList() { return _selectionList; }
    public void setNewCount(int i) { _newCount = i; }
    public int getNewCount() { return _newCount; }
    public void setReviewCount(int i) { _reviewCount = i; }
    public int getReviewCount() { return _reviewCount; }
    public void setEvaluationCount(int i) { _evaluationCount = i; }
    public int getEvaluationCount() { return _evaluationCount; }
    public void setSelectionCount(int i) { _selectionCount = i; }
    public int getSelectionCount() { return _selectionCount; }
    public void setTotalCandidateCount(int i) { _totalCandidateCount = i; }
    public int getTotalCandidateCount() { return _totalCandidateCount; }
    public void setCity(String s) { _city = s; }
    public String getCity() { return _city; }
    public void setState(String s) { _state = s; }
    public String getState() { return _state; }
    public void setCountry(String s) { _country = s; }
    public String getCountry() { return _country; }
}//Requisition
