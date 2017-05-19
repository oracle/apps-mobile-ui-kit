/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class ApplicationStateBean {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    //Notifications related
    private List<Notification> _notificationsList = new ArrayList<Notification>();
    private long _nextNotificationId = 1;
    private long _selectedNotificationId = 0;
    //Capture related
    private boolean _captureEnabled = true;
    private String _captureNavigationParam;
    private String _navigateFromFeatureId;
    private boolean _newNotification = false;

    public ApplicationStateBean(){
        super();
        _buildNotificationsData();
    }//constructor

    public int getNotificationsCount(){
        return _notificationsList.size();
    }//getNotificationsCount
    
    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }//addPropertyChangeListener

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }//removePropertyChangeListener

    //Accessors
    public void setPropertyChangeSupport(PropertyChangeSupport s) { _propertyChangeSupport = s; }
    public PropertyChangeSupport getPropertyChangeSupport() { return _propertyChangeSupport; }
    public void setNotificationsList(List<Notification> l) { _notificationsList = l; }
    public List<Notification> getNotificationsList() { return _notificationsList; }
    public void setNextNotificationId(long l) { _nextNotificationId = l; }
    public long getNextNotificationId() { return _nextNotificationId; } 
    public void setSelectedNotificationId(long l) { _selectedNotificationId = l; }
    public long getSelectedNotificationId() { return _selectedNotificationId; }
    public void setCaptureEnabled(boolean b) {
        boolean oldCaptureEnabled = _captureEnabled;
        _captureEnabled = b;
        _propertyChangeSupport.firePropertyChange("captureEnabled", oldCaptureEnabled, _captureEnabled);
    }//setCaptureEnabled
    public boolean isCaptureEnabled() { return _captureEnabled; }
    public void setNavigateFromFeatureId(String s) { _navigateFromFeatureId = s; }
    public String getNavigateFromFeatureId() { return _navigateFromFeatureId; }
    public void setCaptureNavigationParam(String s) { _captureNavigationParam = s; }
    public String getCaptureNavigationParam() { return _captureNavigationParam; }
    public void setNewNotification(boolean b) {
        boolean oldValue = _newNotification;
        _newNotification = b;
        _propertyChangeSupport.firePropertyChange("newNotification", oldValue, _newNotification);
    }//setNewNotification
    public boolean isNewNotification() { return _newNotification; }

    private void _buildNotificationsData(){
        _notificationsList.add(new Notification(_nextNotificationId++, "Appraisal: Mid-Year Review", "Bob Boyle",
                                                Notification.NOTIFICATION_TYPE.FYI, _getNow()));
        _notificationsList.add(new Notification(_nextNotificationId++, "Expense Report: Lunch ($60)", "Bob Boyle",
                                                Notification.NOTIFICATION_TYPE.APPROVAL, _getNowPlusMinutes(-30)));
        _notificationsList.add(new Notification(_nextNotificationId++, "Task: Deploy Check Deposit", "Past finish date",
                                                Notification.NOTIFICATION_TYPE.FYI, _getNowPlusMinutes(-60)));
        _notificationsList.add(new Notification(_nextNotificationId++, "Budget: Conference ($50,000)", "Jane Donald",
                                                Notification.NOTIFICATION_TYPE.FYI, _getNowPlusMinutes(-90)));
    }//_buildNotificationsData

    private Date _getNow(){
        return Calendar.getInstance().getTime();
    }//_getNow
    
    private Date _getNowPlusMinutes(int minutes){
        Calendar nowPlusMinutes = Calendar.getInstance();
        nowPlusMinutes.add(Calendar.MINUTE, minutes);
        return nowPlusMinutes.getTime();
    }//_getNowPlusMinutes

}//ApplicationStateBean
