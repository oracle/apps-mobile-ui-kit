/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.infolets;

import java.util.ArrayList;
import java.util.List;

public class InfoletsData {
    private Double _remainingOpportunityAmount;
    private Double _wonOpportunityAmount;
    private List<Lead> _openLeadsList;
    private List<Opportunity> _openOpportunitiesList;
    private List<OpenPipeline> _openPipelineList;


    public InfoletsData() {
        super();
        _buildActualVsQuota();
        _buildOpenLeadsList();
        _buildOpenOpportunitiesList();
        _buildOpenPipelineList();
    }//constructor
    
    private void _buildActualVsQuota() {
        _remainingOpportunityAmount = 1.5;
        _wonOpportunityAmount = 7.8;
    }//_buildActualVsQuota

    private void _buildOpenLeadsList() {
        _openLeadsList = new ArrayList<Lead>();
        for (int i = 1; i <= 5; i++)
            _openLeadsList.add(new Lead(((new Double(Math.floor(Math.random() * 500000) + 5000000))), "Q" + i));
    }//_buildOpenLeadsList
    
    private void _buildOpenOpportunitiesList() {
        _openOpportunitiesList = new ArrayList<Opportunity>();
        for (int i = 1; i <= 5; i++)
            _openOpportunitiesList.add(new Opportunity(((new Double(Math.floor(Math.random() * 500000) + 500000))), 
                                                       "Q" + i));
    }//_buildOpenOpportunitiesList
    
    private void _buildOpenPipelineList() {
        _openPipelineList = new ArrayList<OpenPipeline>();
        for (int i = 1; i <= 6; i++)
            _openPipelineList.add(new OpenPipeline(((new Double(Math.floor(Math.random() * 50) + 50))), "A" + i, 
                                                   i + ""));
    }//_buildOpenPipelineList
    
    //Accessors
    public void setRemainingOpportunityAmount(Double d) { _remainingOpportunityAmount = d; }
    public Double getRemainingOpportunityAmount() { return _remainingOpportunityAmount; }
    public void setWonOpportunityAmount(Double d) { _wonOpportunityAmount = d; }
    public Double getWonOpportunityAmount() { return _wonOpportunityAmount; }
    public void setOpenLeadsList(List<Lead> l) { _openLeadsList = l; }
    public List<Lead> getOpenLeadsList() { return _openLeadsList; }
    public void setOpenOpportunitiesList(List<Opportunity> l) { _openOpportunitiesList = l; }
    public List<Opportunity> getOpenOpportunitiesList() { return _openOpportunitiesList; }
    public void setOpenPipelineList(List<OpenPipeline> l) { _openPipelineList = l; }
    public List<OpenPipeline> getOpenPipelineList() { return _openPipelineList; }
}//InfoletsData
