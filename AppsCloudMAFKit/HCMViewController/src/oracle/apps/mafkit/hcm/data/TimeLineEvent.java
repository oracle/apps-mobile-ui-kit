/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.hcm.data;

import java.util.Date;

public class TimeLineEvent {
    private String _title;
    private String _description;
    private Date _startDate;
    private Date _endDate;
    
    public TimeLineEvent(){
        super();
    }//constructor    
    
    public TimeLineEvent(String title, String description, Date startDate, Date endDate){
        _title = title;
        _description = description;
        _startDate = startDate;
        _endDate = endDate;
    }//constructor
    
    //Accessors
    public void setTitle(String s) { _title = s; }
    public String getTitle() { return _title; }
    public void setDescription(String s) { _description = s; }
    public String getDescription() { return _description; }
    public void setStartDate(Date d) { _startDate = d; }
    public Date getStartDate() { return _startDate; }
    public void setEndDate(Date d) { _endDate = d; }
    public Date getEndDate() { return _endDate; }
}//TimeLineEvent
