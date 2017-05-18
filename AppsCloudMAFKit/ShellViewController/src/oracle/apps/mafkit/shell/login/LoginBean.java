/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.login;

import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class LoginBean {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private String _username;
    private String _password;
    private String _rememberUsername;
    private String _rememberPassword;
    
    public LoginBean(){
        super();
        _rememberUsername = "true";
        _rememberPassword = "true";
    }//constructor

    public void addPropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.addPropertyChangeListener(l);
    }//addPropertyChangeListener

    public void removePropertyChangeListener(PropertyChangeListener l) {
        _propertyChangeSupport.removePropertyChangeListener(l);
    }//removePropertyChangeListener

    public void setUsername(String username) {
        String oldUsername = _username;
        _username = username;
        _propertyChangeSupport.firePropertyChange("username", oldUsername, username);
    }//setUsername

    public String getUsername() {
        if (getRememberUsername().equals("true"))
            _username = "lisa.jones";
        return _username;
    }//getUsername

    public void setPassword(String password) {
        String oldPassword = _password;
        _password = password;
        _propertyChangeSupport.firePropertyChange("password", oldPassword, password);
    }//setPassword

    public String getPassword() {
        if (getRememberPassword().equals("true"))
            _password = "oracle";
        return _password;
    }//getPassword

    public void setRememberUsername(String rememberUsername) {
        String oldRememberUsername = _rememberUsername;
        _rememberUsername = rememberUsername;
        _propertyChangeSupport.firePropertyChange("rememberUsername", oldRememberUsername, rememberUsername);
    }//setRememberUsername

    public String getRememberUsername() {
        return _rememberUsername;
    }//getRememberUsername

    public void setRememberPassword(String rememberPassword) {
        String oldRememberPassword = _rememberPassword;
        _rememberPassword = rememberPassword;
        _propertyChangeSupport.firePropertyChange("rememberPassword", oldRememberPassword, rememberPassword);
    }//setRememberPassword

    public String getRememberPassword() {
        return _rememberPassword;
    }//getRememberPassword
    
    public void validateCredentials(ActionEvent actionEvent){
        String username = getUsername();
        String password = getPassword();
        if (("lisa.jones".equalsIgnoreCase(username)) 
            && "oracle".equalsIgnoreCase(password)
           ) {
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.validCredentials}", null); 
            AdfmfContainerUtilities.resetFeature("oracle.apps.mafkit.shell.home", true); 
        } else {
            AdfmfJavaUtilities.setELValue("#{pageFlowScope.validCredentials}", "Enter valid user name and password"); 
        }//credential values check
    }//validateCredentials
    
    public void handleRememberUserNameChange(ValueChangeEvent valueChangeEvent) {
        if (getRememberUsername().equals("false"))
            setUsername("");
        else
            setUsername("lisa.jones");
    }//handleRememberUserNameChange

    public void handleRememberPasswordChange(ValueChangeEvent valueChangeEvent) {
        if (getRememberPassword().equals("false"))
            setPassword("");
        else
            setPassword("oracle");
    }//handleRememberPasswordChange

}//LoginBean
