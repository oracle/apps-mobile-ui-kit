/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.hcm.data;

import java.util.ArrayList;
import java.util.List;

public class Candidate {
    private long _id;
    private String _name;
    private String _photo;
    private String _job;
    private String _city;
    private String _state;
    private String _country;
    private int _matchPercent;
    private List<Submission> _submissions = new ArrayList<Submission>();
    private String _submissionName;   //denormalised for selected requisition
    private String _submissionStatus; //denormalised for selected requisition
    
    public Candidate() {
        super();
    }//constructor
    
    public Candidate (long id, String name, String photo, String job, String city, String state, String country,
                      int matchPercent){
        _id = id;
        _name = name;
        _photo = photo;
        _job = job;
        _city = city;
        _state = state;
        _country = country;
        _matchPercent = matchPercent;
    }//constructor
    
    public Candidate (long id, String name, String photo, String job, String city, String state, String country,
                      int matchPercent, List<Submission> submissions){
        _id = id;
        _name = name;
        _photo = photo;
        _job = job;
        _city = city;
        _state = state;
        _country = country;
        _matchPercent = matchPercent;
        _submissions = submissions;
    }//constructor
    
    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setPhoto(String s) { _photo = s; }
    public String getPhoto() { return _photo; }
    public void setJob(String s) { _job = s; }
    public String getJob() { return _job; }
    public void setCity(String s) { _city = s; }
    public String getCity() { return _city; }
    public void setState(String s) { _state = s; }
    public String getState() { return _state; }
    public void setCountry(String s) { _country = s; }
    public String getCountry() { return _country; }
    public void setMatchPercent(int i) { _matchPercent = i; }
    public int getMatchPercent() { return _matchPercent; }
    public void setSubmissions(List<Submission> l) { _submissions = l; }
    public List<Submission> getSubmissions() { return _submissions; }
    public void setSubmissionName(String s) { _submissionName = s; }
    public String getSubmissionName() { return _submissionName; }
    public void setSubmissionStatus(String s) { _submissionStatus = s; }
    public String getSubmissionStatus() { return _submissionStatus; }
}//Candidate
