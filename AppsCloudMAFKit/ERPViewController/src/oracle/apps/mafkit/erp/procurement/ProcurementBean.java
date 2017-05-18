/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.erp.procurement;

import javax.el.MethodExpression;
import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class ProcurementBean {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private boolean _searchPerformed;
    private String _searchString;
    private String _resultsViewMode; //'card'or 'list'
    private long _selectedProductId;
    private int _orderQuantity;
    private String _confirmationMessage;
    
    public ProcurementBean() {
        _searchPerformed = false;
        _searchString = "";
        _resultsViewMode = "list";
        _selectedProductId = -1;
        _orderQuantity = 1;
        _confirmationMessage = "Action completed";
    }//constructor

    public void addPropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.removePropertyChangeListener(l); }

    //Accessors
    public void setSearchPerformed(boolean b) { 
        boolean oldValue = _searchPerformed;
        _searchPerformed = b; 
        _propertyChangeSupport.firePropertyChange("searchPerformed", oldValue, _searchPerformed);
    }//setSearchPerformed
    public boolean isSearchPerformed() { return _searchPerformed; }
    public void setSearchString(String s) { 
        String oldValue = _searchString;
        _searchString = s; 
        _propertyChangeSupport.firePropertyChange("searchString", oldValue, _searchString);
    }//setSearchString
    public String getSearchString() { return _searchString; }
    public void setResultsViewMode(String s) { 
        String oldValue = _resultsViewMode;
        _resultsViewMode = s; 
        _propertyChangeSupport.firePropertyChange("resultsViewMode", oldValue, _resultsViewMode);
    }//isSearchPerformed
    public String getResultsViewMode() { return _resultsViewMode; }
    public void setSelectedProductId(long l) {
        long oldValue = _selectedProductId;
        _selectedProductId = l;
        _propertyChangeSupport.firePropertyChange("selectedProductId", oldValue, _selectedProductId);
    }//setSelectedProductId
    public long getSelectedProductId() { return _selectedProductId; }
    public void setOrderQuantity(int i) {
        int oldValue = _orderQuantity;
        _orderQuantity = i;
        _propertyChangeSupport.firePropertyChange("orderQuantity", oldValue, _orderQuantity);
    }//setOrderQuantity
    public int getOrderQuantity() { return _orderQuantity; }
    public void setConfirmationMessage(String s) { 
        String oldValue = _confirmationMessage;
        _confirmationMessage = s;
        _propertyChangeSupport.firePropertyChange("confirmationMessage", oldValue, _confirmationMessage);
    }//setConfirmationMessage
    public String getConfirmationMessage() { return _confirmationMessage; }

    //Handle back button from product search
    public void resetProductFilters(ActionEvent actionEvent) {
        setSearchPerformed(false);
        setSearchString("");
    }//resetProductFilters
    
    //Handle custom search value change
    public void filterProductList(ValueChangeEvent valueChangeEvent){
        try {
            MethodExpression mex = AdfmfJavaUtilities.getMethodExpression("#{bindings.filterProductList.execute}", 
                                                                          Object.class, new Class[] { });
            mex.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
            setSearchPerformed(true);
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch
    }//filterProductList
    
    //Handle Add to Cart
    public void addProductToCart(ActionEvent actionEvent){
        try {
            MethodExpression mex = AdfmfJavaUtilities.getMethodExpression("#{bindings.addToCart.execute}", 
                                                                          Object.class, new Class[] { });
            mex.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch
        //Trigger popup display using JavaScript with auto-dismiss
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureId(), "notifierPopup",
                                                                  new Object[] { });
    }//addProductToCart

    //Handle remove from cart
    public void removeProductFromCart(ActionEvent actionEvent){
        try {
            MethodExpression mex = AdfmfJavaUtilities.getMethodExpression("#{bindings.removeFromCart.execute}", 
                                                                          Object.class, new Class[] { });
            mex.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch
        setConfirmationMessage("Removed from Cart");
        //Trigger popup display using JavaScript with auto-dismiss
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureId(), "notifierPopup",
                                                                  new Object[] { });
    }//removeProductFromCart
    
    //Handle quantity change in cart
    public void recalculateCart(ActionEvent actionEvent){
        //Update cart total
        try {
            MethodExpression mex = AdfmfJavaUtilities.getMethodExpression("#{bindings.calculateCartAmount.execute}", 
                                                                          Object.class, new Class[] { });
            mex.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch
    }//recalculateCart
    
    //Handle Submit Cart
    public void submitCart(ActionEvent actionEvent){
        setConfirmationMessage("Submitted for Approval");
        //Submit cart for approval
        try {
            MethodExpression mex = AdfmfJavaUtilities.getMethodExpression("#{bindings.submitCartForApproval.execute}", 
                                                                          Object.class, new Class[] { });
            mex.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch
        //Trigger popup display using JavaScript with auto-dismiss and navigate
        AdfmfContainerUtilities.invokeContainerJavaScriptFunction(AdfmfJavaUtilities.getFeatureId(), "notifyAndNavigate",
                                                                  new Object[] { });
        //Cleanup
        resetProductFilters(actionEvent);
    }//submitCart

}//ProcurementBean
