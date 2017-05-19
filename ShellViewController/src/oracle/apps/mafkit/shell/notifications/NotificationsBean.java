/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.notifications;

import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.apps.mafkit.application.ApplicationStateBean;
import oracle.apps.mafkit.application.Notification;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class NotificationsBean {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private Notification _selectedNotification = new Notification();

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }//addPropertyChangeListener

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }//removePropertyChangeListener

    //Accessors
    public void setSelectedNotification(Notification n) { _selectedNotification = n; }
    public Notification getSelectedNotification() { return _selectedNotification; }

    //Called from Notifications Flow
    public void initializeNotificationDetails(){
        //Initialize selected notification
        _selectedNotification = new Notification();
        Object obj = AdfmfJavaUtilities.getELValue("#{applicationScope.ApplicationStateBean}");
        ApplicationStateBean applicationStateBean = null;
        if (obj instanceof ApplicationStateBean && null != obj) {
            applicationStateBean = (ApplicationStateBean) obj;
            //Grab the selected notificaftion Id
            _selectedNotification.setId(applicationStateBean.getSelectedNotificationId());
            //Grab other details
            for (Notification n : applicationStateBean.getNotificationsList()){
                if (n.getId() == _selectedNotification.getId()) {
                    _selectedNotification.setSubject(n.getSubject());
                    _selectedNotification.setDescription(n.getDescription());
                    _selectedNotification.setCreationDate(n.getCreationDate());
                    _selectedNotification.setType(n.getType());
                    _selectedNotification.setImage(n.getImage());
                }//match
            }//loop
        } else {
            _selectedNotification.setId(0);
            _selectedNotification.setSubject("Unknown");
            _selectedNotification.setType("FYI");
        }//null check
        _propertyChangeSupport.firePropertyChange("selectedNotification", null, _selectedNotification);
    }//initializeNotificationDetails
    
}//NotificationsBean
