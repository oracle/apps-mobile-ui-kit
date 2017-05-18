/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.springboard;

import oracle.adfmf.framework.FeatureInformation;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class SpringboardItem implements Comparable {
    public static enum SPRINGBOARD_ITEM_TYPE {
        TYPE_GOLINK, //used for punching out to a diferent app with query strings
        TYPE_FEATURELINK, //default value for feature navigation
        TYPE_CAPTURE, ; //switch for enabling functionality like capture
    }//SPRINGBOARD_ITEM_TYPE
    private SPRINGBOARD_ITEM_TYPE _springboardItemType = SPRINGBOARD_ITEM_TYPE.TYPE_FEATURELINK;
    private String _id;
    private String _name;
    private String _image;
    private String _dividerText;

    public SpringboardItem() {
        super();
    } //constructor

    public SpringboardItem(FeatureInformation fi) {
        super();
        _image = fi.getImage();
        _dividerText = fi.getVendor();
        _name = fi.getName();
        _id = fi.getId();
    } //constructor

    @Override
    public int compareTo(Object o) {
        //Override compareTo for sorting
        if (o instanceof SpringboardItem)
            return this.getDividerText().compareTo(((SpringboardItem) o).getDividerText()) * (-1);
        return 0;
    }//compareTo

    public void processItemSelection() {
        //Handle FEATURELINKS
        if (_springboardItemType.equals(SPRINGBOARD_ITEM_TYPE.TYPE_FEATURELINK))
            AdfmfContainerUtilities.resetFeature(getId(), true);
        //Handle capture selection
        if (_springboardItemType.equals(SPRINGBOARD_ITEM_TYPE.TYPE_CAPTURE)) {
            boolean currentCaptureValue = ((Boolean) AdfmfJavaUtilities.getELValue("#{applicationScope.ApplicationStateBean.captureEnabled}")).booleanValue();
            AdfmfJavaUtilities.setELValue("#{applicationScope.ApplicationStateBean.captureEnabled}", ! currentCaptureValue);
        }//check catpture selection
        //TODO Handle puchout app logic if required
    }//processItemSelection

    //Accessors
    public void setSpringboardItemType(SPRINGBOARD_ITEM_TYPE sit) { _springboardItemType = sit; }
    public String getSpringboardItemType() { return _springboardItemType.toString(); }
    public void setId(String s) { _id = s; }
    public String getId() { return _id; }
    public void setName(String s) { _name = s; }
    public String getName() { return _name; }
    public void setImage(String s) { _image = s; }
    public String getImage() { return _image; }
    public void setDividerText(String s) { _dividerText = s; }
    public String getDividerText() { return _dividerText; }    
}//SpringboardItem
