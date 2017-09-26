/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.application;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.FeatureInformation;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class ThemesBean {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private String _skinFamily = "";
    private String _selectedTheme = "";
    
    public ThemesBean(){
        super();
        //Set default theme
        _skinFamily = "mafkit-alta-brightBlue";
        _selectedTheme = _skinFamily;
    }//constructor

    public void applyThemeChange(ActionEvent ae){
        setSkinFamily(getSelectedTheme());
        //
        //Set the phone header content colour
        if ("mafkit-alta-darkGrey".equalsIgnoreCase(getSelectedTheme()))
            AdfmfContainerUtilities.setStatusBarStyle(AdfmfContainerUtilities.STATUS_BAR_STYLE.LIGHT);
        else
            AdfmfContainerUtilities.setStatusBarStyle(AdfmfContainerUtilities.STATUS_BAR_STYLE.DARK);
        //
        //Reset features individually so they pick up the new theme
        FeatureInformation[] appFeatures = AdfmfContainerUtilities.getFeatures();
        for (int i = 0; i < appFeatures.length; i++)
            AdfmfContainerUtilities.resetFeature(appFeatures[i].getId());
    }//applyThemeChange
    
    public void setSkinFamily(String s) {
        String oldValue = _skinFamily;
        _skinFamily = s;
        _propertyChangeSupport.firePropertyChange("skinFamily", oldValue, _skinFamily);
    }//setSkinFamily
    public String getSkinFamily() { return _skinFamily; }
    public void setSelectedTheme(String s) {
        String oldValue = _selectedTheme;
        _selectedTheme = s;
        _propertyChangeSupport.firePropertyChange("selectedTheme", oldValue, _selectedTheme);
    }//setSelectedTheme
    public String getSelectedTheme() { return _selectedTheme; }

    public void addPropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.addPropertyChangeListener(l); }    
    public void removePropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.removePropertyChangeListener(l); }
}//ThemesBean