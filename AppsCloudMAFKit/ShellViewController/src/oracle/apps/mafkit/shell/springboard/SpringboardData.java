/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.springboard;

import java.util.ArrayList;
import java.util.List;
import oracle.adfmf.framework.FeatureInformation;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;

public class SpringboardData {
    private List<SpringboardItem> _springboardCommonHeaderList = new ArrayList<SpringboardItem>();
    private List<SpringboardItem> _springboardCommonFooterList = new ArrayList<SpringboardItem>();
    private List<SpringboardItem> _springboardFeaturesList = new ArrayList<SpringboardItem>();
    private boolean _multipleVendors = true;

    public SpringboardData() {
        super();
        _buildSpringboardData();
    } //constructor

    private void _buildSpringboardData() {
        List<SpringboardItem> registeredFeatures = new ArrayList<SpringboardItem>();
        FeatureInformation[] features = AdfmfContainerUtilities.getFeatures();
        for (FeatureInformation fi : features) {
            SpringboardItem springboardFeature = new SpringboardItem(fi);
            registeredFeatures.add(springboardFeature);
        }//loop
        List<SpringboardItem> headerFeatures = new ArrayList<SpringboardItem>();
        List<SpringboardItem> footerFeatures = new ArrayList<SpringboardItem>();
        for (SpringboardItem item : registeredFeatures) {
            if (item.getDividerText().equalsIgnoreCase("COM_HEADER"))
                headerFeatures.add(item);
            else if (item.getDividerText().equalsIgnoreCase("COM_FOOTER"))
                footerFeatures.add(item);
            else
                _springboardFeaturesList.add(item);
        }//loop
        _injectAdditionalHeaderFeatures(headerFeatures);
        _injectAdditionalFooterFeatures(footerFeatures);
        _injectAdditionalFeaturesAndProcess();
    } //_buildSpringboardData

    private void _injectAdditionalHeaderFeatures(List<SpringboardItem> headerFeatures) {
        try {
            //TODO Additional header features go here if required

            //Add Capture
            SpringboardItem capture = new SpringboardItem();
            capture.setSpringboardItemType(SpringboardItem.SPRINGBOARD_ITEM_TYPE.TYPE_CAPTURE);
            capture.setName("Capture");
            capture.setImage("/resources/images/spli_captureactive_24_achr.png");
            headerFeatures.add(capture);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (headerFeatures.size() > 0)
                _springboardCommonHeaderList.addAll(headerFeatures);
        }//try-catch
    } //_injectAdditionalHeaderFeatures

    private void _injectAdditionalFooterFeatures(List<SpringboardItem> footerFeatures) {
        try {
            //Add Settings
            SpringboardItem settings = new SpringboardItem();
            settings.setName("Settings");
            settings.setImage("/resources/images/spli_gears_24_achr.png");
            settings.setId("oracle.apps.mafkit.shell.settings");
            footerFeatures.add(settings);
            //Add About
            SpringboardItem about = new SpringboardItem();
            about.setName("About");
            about.setImage("/resources/images/spli_information_24_achr.png");
            about.setId("oracle.apps.mafkit.shell.about");
            footerFeatures.add(about);
            //Add Sign Out
            SpringboardItem signout = new SpringboardItem();
            signout.setName("Sign Out");
            signout.setImage("/resources/images/spli_exit_24_achr.png");
            signout.setId("oracle.apps.mafkit.shell.login");
            footerFeatures.add(signout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (footerFeatures.size() > 0)
                _springboardCommonFooterList.addAll(footerFeatures);
        }//try-catch
    } //_injectAdditionalFooterFeatures

    private void _injectAdditionalFeaturesAndProcess() {
        //TODO Addional features go here if required
        
        //Check for multiple vendors
        String firstVendor = _springboardFeaturesList.get(0).getDividerText();
        for (SpringboardItem item : _springboardFeaturesList) {
            if (! firstVendor.equals(item.getDividerText())) {
                _multipleVendors = true;
                break;
            }//if
        }//loop
    }//_injectAdditionalFeaturesAndProcess

    //Accessors
    public void setSpringboardCommonHeaderList(List<SpringboardItem> l) { _springboardCommonHeaderList = l; }
    public List<SpringboardItem> getSpringboardCommonHeaderList() { return _springboardCommonHeaderList; }
    public void setSpringboardCommonFooterList(List<SpringboardItem> l) { _springboardCommonFooterList = l; } 
    public List<SpringboardItem> getSpringboardCommonFooterList() { return _springboardCommonFooterList; } 
    public void setSpringboardFeaturesList(List<SpringboardItem> l) { _springboardFeaturesList = l; }
    public List<SpringboardItem> getSpringboardFeaturesList() { return _springboardFeaturesList; }
    public void setMultipleVendors(boolean b) { _multipleVendors = b; }
    public boolean isMultipleVendors() { return _multipleVendors; }
}//SpringboardData
