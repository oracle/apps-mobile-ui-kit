/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.hcm.data;

import java.util.Date;

public class Submission {
    private String _reqId;
    private String _reqName;
    private Date _reqPostingDate;
    private String _reqCity;
    private String _reqState;
    private String _reqCountry;
    private String _candidateStatus;
    
    public Submission(){
        super();
    }//constructor
    
    public Submission(String reqId, String reqName, Date reqPostingDate, 
                      String reqCity, String reqState, String reqCountry, String candidateStatus){
        _reqId = reqId;
        _reqName = reqName;
        _reqPostingDate = reqPostingDate;
        _reqCity = reqCity;
        _reqState = reqState;
        _reqCountry = reqCountry;
        _candidateStatus = candidateStatus;
    }//constructor
    
    //Accessors
    public void setReqId(String s) { _reqId = s; }
    public String getReqId() { return _reqId; }
    public void setReqName(String s) { _reqName = s; }
    public String getReqName() { return _reqName; }
    public void setReqPostingDate(Date d) { _reqPostingDate = d; }
    public Date getReqPostingDate() { return _reqPostingDate; }
    public void setReqCity(String s) { _reqCity = s; }
    public String getReqCity() { return _reqCity; }
    public void setReqState(String s) { _reqState = s; }
    public String getReqState() { return _reqState; }
    public void setReqCountry(String s) { _reqCountry = s; }
    public String getReqCountry() { return _reqCountry; }
    public void setCandidateStatus(String s) { _candidateStatus = s; }
    public String getCandidateStatus() { return _candidateStatus; }
}//Submission
