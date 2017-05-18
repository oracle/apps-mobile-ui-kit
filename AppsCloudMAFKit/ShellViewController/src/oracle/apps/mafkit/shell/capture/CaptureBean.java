/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.capture;

import oracle.adf.model.datacontrols.device.DeviceManager;
import oracle.adf.model.datacontrols.device.DeviceManagerFactory;
import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.apps.mafkit.application.ApplicationStateBean;
import oracle.apps.mafkit.application.Notification;

public class CaptureBean {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private String _capturedImage;
    private boolean _captureSuccess;
    private Notification _newNotification = new Notification();
    private String _confirmationMessage;

    public CaptureBean() {
        super();
    }//constructor

    public void addPropertyChangeListener(PropertyChangeListener l) { 
        _propertyChangeSupport.addPropertyChangeListener(l);
    }//addPropertyChangeListener
    
    public void removePropertyChangeListener(PropertyChangeListener l) { 
        _propertyChangeSupport.removePropertyChangeListener(l); 
    }//removePropertyChangeListener

    public void initiateImageCapture() {
        try {
            if (DeviceManagerFactory.getDeviceManager().hasCamera()) {
                _capturedImage =
                    DeviceManagerFactory.getDeviceManager().getPicture(100, 1, 1, true,
                                                                       DeviceManager.CAMERA_ENCODINGTYPE_PNG, 0, 0);
            } else {
                _capturedImage =
                    DeviceManagerFactory.getDeviceManager().getPicture(100, 1, 0, true,
                                                                       DeviceManager.CAMERA_ENCODINGTYPE_PNG, 0, 0);
            }//check if simulator
            if (null != _capturedImage && !("".equals(_capturedImage))) {
                _captureSuccess = true;
            } else {
                _captureSuccess = false;
            }//check success
        } catch (Exception e) {
            e.printStackTrace();
            _captureSuccess = false;
        }//try-catch
    } //initiateImageCapture

    public void navigateToCallingFeature() {
        String navigateToFeatureId = null;
        if (null != AdfmfJavaUtilities.getELValue("#{applicationScope.ApplicationStateBean.navigateFromFeatureId}")) {
            navigateToFeatureId =
                AdfmfJavaUtilities.getELValue("#{applicationScope.ApplicationStateBean.navigateFromFeatureId}").toString();
        } else {
            navigateToFeatureId = "oracle.apps.mafkit.shell.home";
        }//check from feature
        AdfmfContainerUtilities.resetFeature(navigateToFeatureId, true);
    } //navigateToCallingFeature

    public void createNotification(ActionEvent actionEvent){
        Object obj = AdfmfJavaUtilities.getELValue("#{applicationScope.ApplicationStateBean}");
        ApplicationStateBean applicationStateBean = null;
        if (obj instanceof ApplicationStateBean && null != obj) {
            applicationStateBean = (ApplicationStateBean) obj;
            //Set the new notification id
            long nextNotificationId = applicationStateBean.getNextNotificationId();
            _newNotification.setId(nextNotificationId++);
            applicationStateBean.setNextNotificationId(nextNotificationId++);
            //TODO
            //Set details for the new notification
            if (applicationStateBean.getCaptureNavigationParam().equals("CAMERA")) {
                _newNotification.setSubject("New Image" + (_newNotification.getSubject() == null ? "" : ": " + _newNotification.getSubject()));
                _newNotification.setType("CAPTURED_IMAGE");
                _newNotification.setImage(_capturedImage);
                //Set confirmation message
                setConfirmationMessage("Image Saved");
            } else if(applicationStateBean.getCaptureNavigationParam().equals("NOTE")) {
                _newNotification.setSubject("New Note"+ (_newNotification.getSubject() == null ? "" : ": " + _newNotification.getSubject()));
                _newNotification.setType("CAPTURED_NOTE");
                //Note body saved directly into _newNotification
                //Set confirmation message
                setConfirmationMessage("Note Saved");
            }//check notification type
            applicationStateBean.getNotificationsList().add(0, _newNotification);
            applicationStateBean.setNewNotification(true);
            //Show confirmation
            AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureId(), "notifyAndNavigate", new Object[] { });
        }//null check
    }//createNotification

    //Accessors
    public void setCapturedImage(String s) { _capturedImage = s; }
    public String getCapturedImage() { return _capturedImage; }
    public void setCaptureSuccess(boolean b) { _captureSuccess = b; }
    public boolean isCaptureSuccess() { return _captureSuccess; }
    public void setNewNotification(Notification n) { _newNotification = n; }
    public Notification getNewNotification() { return _newNotification; } 
    public void setConfirmationMessage(String s) { 
        String oldValue = _confirmationMessage;
        _confirmationMessage = s;
        _propertyChangeSupport.firePropertyChange("confirmationMessage", oldValue, _confirmationMessage);
    }//setConfirmationMessage
    public String getConfirmationMessage() { return _confirmationMessage; }
}//CaptureBean
