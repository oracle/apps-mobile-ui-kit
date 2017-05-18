/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.hcm.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.java.beans.ProviderChangeListener;
import oracle.adfmf.java.beans.ProviderChangeSupport;

import oracle.apps.mafkit.hcm.common.HcmUtils;

public class HiringData {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private ProviderChangeSupport _providerChangeSupport = new ProviderChangeSupport(this);
    private HcmUtils _utils = new HcmUtils();
    //
    private List<Requisition> _requisitionsList = new ArrayList<Requisition>();
    private List<Requisition> _filteredRequisitionsList = new ArrayList<Requisition>();
    private List<Candidate> _candidatesList = new ArrayList<Candidate>();
    private List<Candidate> _filteredCandidatesList = new ArrayList<Candidate>();
    private List<TimeLineEvent> _educationData = new ArrayList<TimeLineEvent>();
    private List<TimeLineEvent> _workExperienceData = new ArrayList<TimeLineEvent>();

    public void addPropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.removePropertyChangeListener(l); }
    public void addProviderChangeListener(ProviderChangeListener l) { _providerChangeSupport.addProviderChangeListener(l); }
    public void removeProviderChangeListener(ProviderChangeListener l) { _providerChangeSupport.removeProviderChangeListener(l); }

    public HiringData(){
        super();
        _buildRequisitionsList();
        setFilteredRequisitionsList(getRequisitionsList());
        _buildCandidatesList();
        setFilteredCandidatesList(getCandidatesList());
        _updateCandidateSubmissions();
        _buildTimeLineData();
    }//constructor
    
    //Filter Candidates List
    public void filterCandidatesList(String searchString){
        List<Candidate> newList = new ArrayList<Candidate>();
        if (searchString != null && searchString.length() > 0){
            //Loop through candidates
            for (Candidate c : _candidatesList){
                //Look for a pattern match in name, job, city, state, country
                if (c.getName().toUpperCase().contains(searchString.toUpperCase()) ||
                    c.getJob().toUpperCase().contains(searchString.toUpperCase()) ||
                    c.getCity().toUpperCase().contains(searchString.toUpperCase()) ||
                    c.getState().toUpperCase().contains(searchString.toUpperCase()) ||
                    c.getCountry().toUpperCase().contains(searchString.toUpperCase())){
                    newList.add(c);
                }//check for pattern
            }//loop through candidates
        } else {
            for (Candidate c : _candidatesList)
                newList.add(c);
        }//check pattern supplied
        setFilteredCandidatesList(newList);
        _providerChangeSupport.fireProviderRefresh("filteredCandidatesList");
    }//filterCandidatesList
    
    //Filter Requisitions List
    public void filterRequisitionsList(String searchString){
        List<Requisition> newList = new ArrayList<Requisition>();
        if (searchString != null && searchString.length() > 0){
            //Loop through requisitions
            for (Requisition r : _requisitionsList){
                //Look for a pattern match in name, city, state, country
                if (r.getName().toUpperCase().contains(searchString.toUpperCase())){
                    newList.add(r);
                }//check for pattern
            }//loop through requisitions
        } else {
            for (Requisition r : _requisitionsList)
                newList.add(r);
        }//check pattern supplied
        setFilteredRequisitionsList(newList);
        _providerChangeSupport.fireProviderRefresh("filteredRequisitionsList");
    }//filterRequisitionsList
    
    //Called while navigating 'Requisition Detail' to 'Candidate Detail'
    public void fetchCandidate(long candidateId){
        List<Candidate> newList = new ArrayList<Candidate>();
        for (Candidate c : _candidatesList){
            if (c.getId() == candidateId){
                newList.add(c);
                break;
            }//check candidate id
        }//loop through candidates
        setFilteredCandidatesList(newList);
    }//fetchCandidate
    
    //Called while navigating 'Requisitions Landing' to 'Requisition Detail' and 
    //'Candidate Detail' to 'Requisition Detail'
    public void filterRequisitionCandidates(String requisitionId, String stage, boolean pageNavigation){
        List<Candidate> newList = new ArrayList<Candidate>();
        //Get the selected requisition
        for (Requisition r : _requisitionsList){
            if (r.getId().toUpperCase().equals(requisitionId.toUpperCase())){
                //
                //Check if NEW candidates required
                if (stage.toUpperCase().equals("ALL") || stage.toUpperCase().equals("NEW")){
                    //Get NEW candidates Ids
                    for (Long id : r.getNewList()){
                        //Get corresponding candidate
                        for (Candidate c : _candidatesList){
                            if (c.getId() == id){
                                c.setSubmissionName(r.getName());
                                c.setSubmissionStatus("New Candidate");
                                newList.add(c);
                                break;
                            }//check candidate id
                        }//loop through candidates
                    }//loop through NEW candidate Ids
                }//check if NEW candidates required
                //
                //Check if REVIEW candidates required
                if (stage.toUpperCase().equals("ALL") || stage.toUpperCase().equals("REVIEW")){
                    //Get REVIEW candidates
                    for (Long id : r.getReviewList()){
                        //Get corresponding candidate
                        for (Candidate c : _candidatesList){
                            if (c.getId() == id){
                                c.setSubmissionName(r.getName());
                                c.setSubmissionStatus("Under Review");
                                newList.add(c);
                                break;
                            }//check candidate id
                        }//loop through candidates
                    }//loop through REVIEW candidate Ids
                }//check if REVIEW candidates required
                //
                //Check if EVALUATION candidates required
                if (stage.toUpperCase().equals("ALL") || stage.toUpperCase().equals("EVALUATION")){
                    //Get EVALUATION candidates
                    for (Long id : r.getEvaluationList()){
                        //Get corresponding candidate
                        for (Candidate c : _candidatesList){
                            if (c.getId() == id){
                                c.setSubmissionName(r.getName());
                                c.setSubmissionStatus("Under Evaluation");
                                newList.add(c);
                                break;
                            }//check candidate id
                        }//loop through candidates
                    }//loop through EVALUATION candidate Ids
                }//Check if EVALUATION candidates required
                //
                //Check if SELECTION candidates required
                if (stage.toUpperCase().equals("ALL") || stage.toUpperCase().equals("SELECTION")){
                    //Get SELECTION candidates
                    for (Long id : r.getSelectionList()){
                        //Get corresponding candidate
                        for (Candidate c : _candidatesList){
                            if (c.getId() == id){
                                c.setSubmissionName(r.getName());
                                c.setSubmissionStatus("Under Selection");
                                newList.add(c);
                                break;
                            }//check candidate id
                        }//loop through candidates
                    }//loop through SELECTION candidate Ids
                }//Check if SELECTION candidates required
                //
                break;
            }//requisition id check
        }//loop through requisitions
        setFilteredCandidatesList(newList);
        if (!pageNavigation)
            _providerChangeSupport.fireProviderRefresh("filteredCandidatesList");
    }//filterRequisitionCandidates

    //Called while navigating 'Requisition Detail' to 'Requisitions Landing'
    public void resetCandidateFilters(){
        setFilteredCandidatesList(getCandidatesList());
    }//resetCandidateFilters
    
    private void _buildRequisitionsList(){
        List<Long> newList;
        List<Long> reviewList;
        List<Long> evaluationList;
        List<Long> selectionList;
        //
        newList = new ArrayList<Long>();
        reviewList = new ArrayList<Long>();
        evaluationList = new ArrayList<Long>();
        selectionList = new ArrayList<Long>();
        newList.add(new Long(111));
        newList.add(new Long(115));
        newList.add(new Long(101));
        reviewList.add(new Long(105));
        evaluationList.add(new Long(116));
        _requisitionsList.add(new Requisition("IC01925", "Design Verification Engineer", _utils.addDaysToToday(-10),
                                              newList, reviewList, evaluationList, selectionList,
                                              "San Francisco", "CA", "USA"));
        //
        newList = new ArrayList<Long>();
        reviewList = new ArrayList<Long>();
        evaluationList = new ArrayList<Long>();
        selectionList = new ArrayList<Long>();
        newList.add(new Long(102));
        reviewList.add(new Long(106));
        evaluationList.add(new Long(110));
        evaluationList.add(new Long(112));
        _requisitionsList.add(new Requisition("MG14095", "IT Account Manager", _utils.addDaysToToday(-15),
                                              newList, reviewList, evaluationList, selectionList,
                                              "Dallas", "TX", "USA"));
        //
        newList = new ArrayList<Long>();
        reviewList = new ArrayList<Long>();
        evaluationList = new ArrayList<Long>();
        selectionList = new ArrayList<Long>();
        reviewList.add(new Long(104));
        reviewList.add(new Long(108));
        evaluationList.add(new Long(110));
        evaluationList.add(new Long(112));
        evaluationList.add(new Long(114));
        _requisitionsList.add(new Requisition("IC32067", "System Administrator", _utils.addDaysToToday(-20),
                                              newList, reviewList, evaluationList, selectionList,
                                              "Redwood City", "CA", "USA"));
        //
        newList = new ArrayList<Long>();
        reviewList = new ArrayList<Long>();
        evaluationList = new ArrayList<Long>();
        selectionList = new ArrayList<Long>();
        reviewList.add(new Long(102));
        evaluationList.add(new Long(103));
        evaluationList.add(new Long(106));
        evaluationList.add(new Long(107));
        selectionList.add(new Long(112));
        _requisitionsList.add(new Requisition("MG36797", "Sales Manager", _utils.addDaysToToday(-25),
                                              newList, reviewList, evaluationList, selectionList,
                                              "Austin", "TX", "USA"));
        //
        newList = new ArrayList<Long>();
        reviewList = new ArrayList<Long>();
        evaluationList = new ArrayList<Long>();
        selectionList = new ArrayList<Long>();
        reviewList.add(new Long(103));
        evaluationList.add(new Long(107));
        evaluationList.add(new Long(110));
        selectionList.add(new Long(113));
        selectionList.add(new Long(114));
        _requisitionsList.add(new Requisition("IC75864", "Sales Representative", _utils.addDaysToToday(-30),
                                              newList, reviewList, evaluationList, selectionList,
                                              "Belmont", "CA", "USA"));
        //
        newList = new ArrayList<Long>();
        reviewList = new ArrayList<Long>();
        evaluationList = new ArrayList<Long>();
        selectionList = new ArrayList<Long>();
        newList.add(new Long(103));
        evaluationList.add(new Long(107));
        selectionList.add(new Long(109));
        selectionList.add(new Long(113));
        selectionList.add(new Long(116));
        _requisitionsList.add(new Requisition("IC84850", "Telesales Engineer", _utils.addDaysToToday(-35),
                                              newList, reviewList, evaluationList, selectionList,
                                              "Frisco", "TX", "USA"));
    }//_buildRequisitionsList
    
    private void _buildCandidatesList(){
        _candidatesList.add(new Candidate(101, "Leslie Holloway", "/resources/images/headshot01.png", "Software Engineer", "San Francisco", "CA", "USA", 70));
        _candidatesList.add(new Candidate(102, "Christopher Lemontage", "/resources/images/headshot09.png", "Project Manager", "Dallas", "TX", "USA", 75));
        _candidatesList.add(new Candidate(103, "Jason Depaso", "/resources/images/headshot10.png", "Sales Consultant", "Redwood City", "CA", "USA", 80));
        _candidatesList.add(new Candidate(104, "Jose Eduado", "/resources/images/headshot11.png", "System Administrator", "Austin", "TX", "USA", 85));
        _candidatesList.add(new Candidate(105, "John Sabastian", "/resources/images/headshot12.png", "Software Engineer", "Redwood Shores", "CA", "USA", 90));
        _candidatesList.add(new Candidate(106, "Emily Mansel", "/resources/images/headshot03.png", "Project Manager", "Belmont", "CA", "USA", 95));
        _candidatesList.add(new Candidate(107, "Micheal Smith", "/resources/images/headshot13.png", "Sales Consultant", "Frisco", "TX", "USA", 73));
        _candidatesList.add(new Candidate(108, "Maria John", "/resources/images/headshot06.png", "Database Administrator", "Redwood City", "CA", "USA", 78));
        _candidatesList.add(new Candidate(109, "Dave Givenchi", "/resources/images/headshot15.png", "Software Engineer", "San Francisco", "CA", "USA", 83));
        _candidatesList.add(new Candidate(110, "Mary Adams", "/resources/images/headshot07.png", "Project Manager", "Austin", "TX", "USA", 88));
        _candidatesList.add(new Candidate(111, "Anna Sanchez", "/resources/images/headshot08.png", "Quality Assurance Engineer", "Belmont", "CA", "USA", 93));
        _candidatesList.add(new Candidate(112, "Umi Travez", "/resources/images/headshot14.png", "Project Manager", "Redwood City", "CA", "USA", 65));
        _candidatesList.add(new Candidate(113, "Zeplin Smith", "/resources/images/headshot16.png", "Software Engineer", "Dallas", "TX", "USA", 72));
        _candidatesList.add(new Candidate(114, "Mandy Mansel", "/resources/images/headshot17.png", "Project Manager", "San Francisco", "CA", "USA", 81));
        _candidatesList.add(new Candidate(115, "Jeff Johnson", "/resources/images/headshot18.png", "Quality Assurance Engineer", "Austin", "TX", "USA", 90));
        _candidatesList.add(new Candidate(116, "Kyle Trafford", "/resources/images/headshot19.png", "Software Engineer", "Dallas", "TX", "USA", 85));
    }//_buildCandidatesList
    
    private void _updateCandidateSubmissions(){
        String _new = "New Submission";
        String _review = "Submission under Review";
        String _evaluation = "Submission under Evaluation";
        String _selection = "Submission at Selection stage";
        for (Candidate c : _candidatesList){
            for (Requisition r : _requisitionsList){
                //Check through New stage
                for (Long l : r.getNewList()){
                    if (c.getId() == l){
                        c.getSubmissions().add(new Submission(r.getId(), r.getName(), r.getPostingDate(), 
                                                              r.getCity(), r.getState(), r.getCountry(), _new));
                    }//check ids
                }//loop through applicants
                //Check through Review stage 
                for (Long l : r.getReviewList()){
                    if (c.getId() == l)
                        c.getSubmissions().add(new Submission(r.getId(), r.getName(), r.getPostingDate(), 
                                                              r.getCity(), r.getState(), r.getCountry(), _review));
                }//loop through applicants
                //Check through Evaluation stage 
                for (Long l : r.getEvaluationList()){
                    if (c.getId() == l)
                        c.getSubmissions().add(new Submission(r.getId(), r.getName(), r.getPostingDate(), 
                                                              r.getCity(), r.getState(), r.getCountry(), _evaluation));
                }//loop through applicants
                //Check through Selection stage 
                for (Long l : r.getSelectionList()){
                    if (c.getId() == l)
                        c.getSubmissions().add(new Submission(r.getId(), r.getName(), r.getPostingDate(), 
                                                              r.getCity(), r.getState(), r.getCountry(), _selection));
                }//loop through applicants
            }//loop through requisitions
        }//loop through candidates
    }//_updateCandidateSubmissions
    
    private void _buildTimeLineData(){
        _educationData.add(new TimeLineEvent("Masters in Computer Science", "Brooklyn University", 
                                             _utils.setDate(2014, 1, 1), _utils.setDate(2015, 12, 31)));
        _educationData.add(new TimeLineEvent("Certificate in Scripting Languages", "Oxford University", 
                                             _utils.setDate(2016, 1, 1), _utils.setDate(2016, 3, 31)));
        _educationData.add(new TimeLineEvent("Agile Software Development", "Oracle University", 
                                             _utils.setDate(2016, 4, 1), _utils.setDate(2016, 5, 31)));
        _workExperienceData.add(new TimeLineEvent("Consulting Engineer", "Lexus Software Systems", 
                                                  _utils.setDate(2015, 1, 1), _utils.setDate(2015, 6, 30)));
        _workExperienceData.add(new TimeLineEvent("Systems Integrator", "Mosfet USA Inc", 
                                                  _utils.setDate(2015, 7, 1), _utils.setDate(2015, 12, 31)));
        _workExperienceData.add(new TimeLineEvent("Systems Implementation Specialist", "Cry No More Software Systems", 
                                                  _utils.setDate(2016, 1, 1), _utils.setDate(2016, 6, 30)));
    }//_buildTimeLineData
    
    //Accessors
    public void setRequisitionsList(List<Requisition> l) {
        List<Requisition> oldValue = _requisitionsList;
        _requisitionsList = l;
        _propertyChangeSupport.firePropertyChange("requisitionsList", oldValue, _requisitionsList);
    }//setRequisitionsList
    public List<Requisition> getRequisitionsList() { return _requisitionsList; }
    //
    public void setFilteredRequisitionsList(List<Requisition> l) { 
        List<Requisition> oldValue = _filteredRequisitionsList;
        _filteredRequisitionsList = l;
        _propertyChangeSupport.firePropertyChange("filteredRequisitionsList", oldValue, _filteredRequisitionsList);
    }//setFilteredRequisitionsList
    public List<Requisition> getFilteredRequisitionsList() { return _filteredRequisitionsList; }
    //
    public void setCandidatesList(List<Candidate> l) {
        List<Candidate> oldValue = _candidatesList;
        _candidatesList = l;
        _propertyChangeSupport.firePropertyChange("candidatesList", oldValue, _candidatesList);
    }//setCandidatesList
    public List<Candidate> getCandidatesList() { return _candidatesList; }
    //
    public void setFilteredCandidatesList(List<Candidate> l) {
        List<Candidate> oldValue = _filteredCandidatesList;
        _filteredCandidatesList = l;
        _propertyChangeSupport.firePropertyChange("filteredCandidatesList", oldValue, _filteredCandidatesList);
    }//setFilteredCandidatesList
    public List<Candidate> getFilteredCandidatesList() { return _filteredCandidatesList; }
    //
    public void setEducationData(List<TimeLineEvent> l) { _educationData = l; }
    public List<TimeLineEvent> getEducationData() { return _educationData; }
    public void setWorkExperienceData(List<TimeLineEvent> l) { _workExperienceData = l; }
    public List<TimeLineEvent> getWorkExperienceData() { return _workExperienceData; }
}//HiringData
