/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class Opportunity {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.removePropertyChangeListener(l); }

    public static enum OPPORTUNITY_STAGE {
        QUALIFICATION("01-Qualification"),
        DISCOVERY("02-Discovery"),
        BUILDING("03-Building"),
        PRESENTSTION("04-Presentation"),
        AGREEMENT("05-Agreement"),
        NEGOTIATIONS("06-Negotiations"),
        CLOSED("07-Closed");

        public final String value;

        private OPPORTUNITY_STAGE(String s) {
            value = s;
        }

        public String toString() {
            return this.value;
        }
    }//OPPORTUNITY_STAGE

    private long _id;
    private String _name;
    private Account _account;
    private String _organization;
    private Double _opportunityAmount;
    private Person _primaryContact;
    private String _status;
    private String _forcastCriteria;
    private Person _owner;
    private String _primaryCompetitor;
    private List<Product> _productList = new ArrayList<Product>();
    private Location _address;
    private Double _winProbability;
    private Date _closeDate;
    private OPPORTUNITY_STAGE _opportunityStage = OPPORTUNITY_STAGE.QUALIFICATION;    
    private String _quarter;
    private Double _amount;

    public Opportunity() {
        super();
    }//constructor
    
    public Opportunity(long id, String name) {
        _id = id;
        _name = name;
    }//constructor

    public Opportunity(Double opportunityAmount, String quarter) {
        _opportunityAmount = opportunityAmount;
        _quarter = quarter;
    }//constructor

    public Opportunity(long id, String name, Double opportunityAmount, String quarter) {
        _id = id;
        _name = name;
        _opportunityAmount = opportunityAmount;
        _quarter = quarter;
    }//constructor

    public Opportunity(long id, String name, Double opportunityAmount, String quarter, Double winProbability,
                       Date closeDate, Opportunity.OPPORTUNITY_STAGE opportunityStage) {
        _id = id;
        _name = name;
        _opportunityAmount = opportunityAmount;
        _quarter = quarter;
        _winProbability = winProbability;
        _closeDate = closeDate;
        _opportunityStage = opportunityStage;
    }//constructor

    public Opportunity(long id, String name, Account account, String organization, Double amount,
                       Person primaryContact, String status, String forcastCriteria, Person owner,
                       String primaryCompetitor, List<Product> productList, Location address,
                       Double winProbability, Date closeDate, Opportunity.OPPORTUNITY_STAGE opportunityStage) {
        _id = id;
        _name = name;
        _account = account;
        _organization = organization;
        _amount = amount;
        _primaryContact = primaryContact;
        _status = status;
        _forcastCriteria = forcastCriteria;
        _owner = owner;
        _primaryCompetitor = primaryCompetitor;
        _productList = productList;
        _address = address;
        _winProbability = winProbability;
        _closeDate = closeDate;
        _opportunityStage = opportunityStage;
    }//constructor

    public List<OrderedStage> getSalesStageItems() {
        List<OrderedStage> salesStageItems = new ArrayList<OrderedStage>();
        int i = 1;
        for (OPPORTUNITY_STAGE stage : OPPORTUNITY_STAGE.values())
            salesStageItems.add(new OrderedStage(i, stage.value));
        return salesStageItems;
    }//getSalesStageItems

    //Accessors
    public void setId(long l) { _id = l; }
    public long getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setAccount(Account a) { _account = a; }
    public Account getAccount() { return _account; }
    public void setOrganization(String s) { _organization = s; }
    public String getOrganization() { return _organization; }
    public void setOpportunityAmount(Double d) { _opportunityAmount = d; }
    public Double getOpportunityAmount() { return _opportunityAmount; }
    public void setPrimaryContact(Person p) { _primaryContact = p; }
    public Person getPrimaryContact() { return _primaryContact; }
    public void setStatus(String s) { _status = s; }
    public String getStatus() { return _status; }
    public void setForcastCriteria(String s) { _forcastCriteria = s; }
    public String getForcastCriteria() { return _forcastCriteria; }
    public void setOwner(Person p) { _owner = p; }
    public Person getOwner() { return _owner; }
    public void setPrimaryCompetitor(String s) { _primaryCompetitor = s; }
    public String getPrimaryCompetitor() { return _primaryCompetitor; }
    public void setProductList(List<Product> l) { _productList = l; }
    public List<Product> getProductList() { return _productList; }
    public void setAddress(Location l) { _address = l; }
    public Location getAddress() { return _address; }
    public void setWinProbability(Double d) {
        Double oldWinProbability = _winProbability;
        _winProbability = d;
        _propertyChangeSupport.firePropertyChange("winProbability", oldWinProbability, d);
    }//setWinProbability
    public Double getWinProbability() { return _winProbability; }
    public void setCloseDate(Date d) { _closeDate = d; }
    public Date getCloseDate() { return _closeDate; }
    public void setOpportunityStage(Opportunity.OPPORTUNITY_STAGE s) { _opportunityStage = s; }
    public void setOpportunityStage(String s) {
        for (OPPORTUNITY_STAGE stage : OPPORTUNITY_STAGE.values()) {
            if(stage.toString().equalsIgnoreCase(s)){
                _opportunityStage = stage;
            }//match
        }//loop
    }//setOpportunityStage
    public String getOpportunityStage() { return _opportunityStage.toString(); }
    public void setQuarter(String s) { _quarter = s; }
    public String getQuarter() { return _quarter; }
    public void setAmount(Double d) { _amount = d; }
    public Double getAmount() { return _amount; }
}//Opportunity
