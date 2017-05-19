/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.data;

import java.util.ArrayList;
import java.util.List;
import oracle.apps.mafkit.sales.common.SalesUtils;

public class OpportunitiesData {
    private List<Opportunity> _opportunityList = new ArrayList<Opportunity>();
    private long _nextId = 1;
    private SalesUtils _utils = new SalesUtils();
    
    public OpportunitiesData() {
        super();
        _buildOpportunityData();
    }//constructor

    private void _buildOpportunityData() {
        List<Product> productList = _buildProductList();
        //
        _opportunityList.add(new Opportunity(_nextId, "Data Center Green Server",
                                             new Account(_nextId++, "Landmark, Inc."), 
                                             "Landmark, Inc.", 1200000d,
                                             new Person(_nextId++, "Misha Bell", "/resources/images/headshot14.png",
                                                        "UX Desiger"), 
                                             "Open", "When matches forecast criteria",
                                             new Person(_nextId++, "Bob Boyle", "/resources/images/headshot09.png",
                                                        "UX Designer"), 
                                             "Superior Servers", productList,
                                             new Location(37.787642, -122.406913, "pointXY",
                                                          "333 Post St, San Francisco, CA 94108, USA"),
                                             90d,
                                             _utils.convertDateInFormat(_utils.getStandardDateFormat(),
                                                                        "02-11-2016 00:00"),
                                             Opportunity.OPPORTUNITY_STAGE.AGREEMENT));
        //
        _opportunityList.add(new Opportunity(_nextId, "Big Data Statistical Server",
                                             new Account(_nextId++, "Ross Stores Inc"), 
                                             "Ross Stores Inc", 135762000d,
                                             new Person(_nextId++, "Misha Bell", "/resources/images/headshot14.png",
                                                        "UX Desiger"), 
                                             "Open", "When matches forecast criteria",
                                             new Person(_nextId++, "Bob Boyle", "/resources/images/headshot09.png",
                                                        "UX Designer"), 
                                             "Superior Servers", productList,
                                             new Location(37.7923578, -122.4067327, "pointXY",
                                                          "655 Montgomery St Suite 400, San Francisco, CA 94111, United States"),
                                             80d,
                                             _utils.convertDateInFormat(_utils.getStandardDateFormat(),
                                                                        "22-06-2016 00:00"),
                                             Opportunity.OPPORTUNITY_STAGE.DISCOVERY));
        //
        _opportunityList.add(new Opportunity(_nextId, "Data Center Green Server",
                                             new Account(_nextId++, "Pinnacle Technologies"),
                                             "Pinnacle Technologies", 500000000d,
                                             new Person(_nextId++, "Misha Bell", "/resources/images/headshot14.png",
                                                        "UX Desiger"), 
                                             "Open", "When matches forecast criteria",
                                             new Person(_nextId++, "Bob Boyle", "/resources/images/headshot09.png",
                                                        "UX Designer"), 
                                             "Superior Servers", productList,
                                             new Location(37.7739101, -122.42823, "pointXY",
                                                          "California Academy of Sciences, Music Concourse Drive, San Francisco, CA, United States"),
                                             60d,
                                             _utils.convertDateInFormat(_utils.getStandardDateFormat(),
                                                                       "03-06-2016 00:00"),
                                             Opportunity.OPPORTUNITY_STAGE.AGREEMENT));
        //
        _opportunityList.add(new Opportunity(_nextId, "Big Data Statistical Server",
                                             new Account(_nextId++, "Tech Data Corporation"),
                                             "Tech Data Corporation", 63849000d,
                                             new Person(_nextId++, "Misha Bell", "/resources/images/headshot14.png",
                                                        "UX Desiger"), 
                                             "Open", "When matches forecast criteria",
                                             new Person(_nextId++, "Bob Boyle", "/resources/images/headshot09.png",
                                                        "UX Designer"),
                                             "Superior Servers", productList,
                                             new Location(37.7739101, -122.42823, "pointXY",
                                                          "155 Johnstone Dr, San Francisco, CA 94131, United States"), 
                                             40d,
                                             _utils.convertDateInFormat(_utils.getStandardDateFormat(),
                                                                        "25-06-2016 00:00"),
                                             Opportunity.OPPORTUNITY_STAGE.AGREEMENT));
        //
        _opportunityList.add(new Opportunity(_nextId, "Management Server",
                                             new Account(_nextId++, "Pinnacle Technologies"),
                                             "Pinnacle Technologies", 9234600d,
                                             new Person(_nextId++, "Misha Bell", "/resources/images/headshot14.png",
                                                        "UX Desiger"), 
                                             "Open", "When matches forecast criteria",
                                             new Person(_nextId++, "Bob Boyle", "/resources/images/headshot09.png",
                                                        "UX Designer"), 
                                             "Superior Servers", productList,
                                             new Location(37.7739101, -122.42823, "pointXY",
                                                          "300 Page St, San Francisco, CA 94102, United States"), 
                                             30d,
                                             _utils.convertDateInFormat(_utils.getStandardDateFormat(),
                                                                        "20-06-2016 00:00"),
                                             Opportunity.OPPORTUNITY_STAGE.BUILDING));
    }//_buildOpportunityData
    
    private List<Product> _buildProductList() {
        List<Product> productList = new ArrayList<Product>();
        productList.add(new Product(_nextId++, "Green Server", 300d, true));
        productList.add(new Product(_nextId++, "2 Years Service", 7200d));
        return productList;
    }//_buildProductList

    //Accessors
    public void setOpportunityList(List<Opportunity> l) { _opportunityList = l; }
    public List<Opportunity> getOpportunityList() { return _opportunityList; }    
}//OpportunitiesData
