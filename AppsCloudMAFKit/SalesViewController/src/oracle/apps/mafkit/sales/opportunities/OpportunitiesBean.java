/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.sales.opportunities;

import java.util.Date;
import javax.el.MethodExpression;
import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.amx.event.SelectionEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;

public class OpportunitiesBean {
    private String _opportunityName;
    private Double _opportunityAmount;
    private String _opportunityStage;
    private Double _winProbability;
    private Date _closeDate;
    
    public OpportunitiesBean() {
        super();
    }//constructor
    
    public void handleMapSelection(SelectionEvent selectionEvent) {
        try {
            String[] selectedRowKeys = selectionEvent.getSelectedRowKeys();
            Integer selecetdKey =0;
            if (selectedRowKeys != null)
                selecetdKey = new Integer(selectedRowKeys[0]);
            AdfmfJavaUtilities.setELValue("#{viewScope.selectedKey}", selecetdKey);
            try {
                MethodExpression me =
                    AdfmfJavaUtilities.getMethodExpression("#{bindings.setCurrentOpportunity.execute}", 
                                                           Object.class, new Class[] { });
                me.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
                //setSelectAll(Boolean.FALSE);
            } catch (Exception e) {
                e.printStackTrace();
            }//try-catch
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch        
        AdfmfJavaUtilities.setELValue("#{viewScope.mapPointSelected}", true);
    }//handleMapSelection

    public void handleDecreaseWinProbability(ActionEvent actionEvent) {
        Object obj = AdfmfJavaUtilities.getELValue("#{bindings.winProbability.inputValue}");
        Double dbl = new Double(obj.toString());
        dbl = dbl - 5;
        AdfmfJavaUtilities.setELValue("#{bindings.winProbability.inputValue}", dbl);
    }//handleDecreaseWinProbability

    public void handleIncreaseWinProbability(ActionEvent actionEvent) {
        Object obj = AdfmfJavaUtilities.getELValue("#{bindings.winProbability.inputValue}");
        Double dbl = new Double(obj.toString());
        dbl = dbl + 5;
        AdfmfJavaUtilities.setELValue("#{bindings.winProbability.inputValue}", dbl);
    }//handleIncreaseWinProbability

    //Invoked on navigation to Edit Opportunity
    public void cachePreEditValues(ActionEvent actionEvent) {
        Object obj;
        //Cache Opportunity Name
        _opportunityName = (String)AdfmfJavaUtilities.getELValue("#{bindings.name.inputValue}");
        //Cache Opportunity Amount
        obj = AdfmfJavaUtilities.getELValue("#{bindings.amount.inputValue}");
        _opportunityAmount = new Double(obj.toString());
        //Cache Opportunity Stage
        _opportunityStage = (String)AdfmfJavaUtilities.getELValue("#{bindings.opportunityStage.inputValue}");
        //Cache Win Probability
        obj = AdfmfJavaUtilities.getELValue("#{bindings.winProbability.inputValue}");
        _winProbability = new Double(obj.toString());
        //Cache Close Date
        _closeDate = (Date)AdfmfJavaUtilities.getELValue("#{bindings.closeDate.inputValue}");
    }//cachePreEditValues

    //Invoked on Edit Opportunity Cancel
    public void restorePreEditValues(ActionEvent actionEvent) {
        AdfmfJavaUtilities.setELValue("#{bindings.name.inputValue}", _opportunityName);
        AdfmfJavaUtilities.setELValue("#{bindings.amount.inputValue}", _opportunityAmount);
        AdfmfJavaUtilities.setELValue("#{bindings.opportunityStage.inputValue}", _opportunityStage);
        AdfmfJavaUtilities.setELValue("#{bindings.winProbability.inputValue}", _winProbability);
        AdfmfJavaUtilities.setELValue("#{bindings.closeDate.inputValue}", _closeDate);
    }//restorePreEditValues

    //Invoked on Edit Opportunity Save
    public void saveEditChanges(ActionEvent actionEvent) {
        String optyName = (String) AdfmfJavaUtilities.getELValue("#{bindings.name.inputValue}");
        String oldAmount = _opportunityAmount.toString();
        Object obj = AdfmfJavaUtilities.getELValue("#{bindings.amount.inputValue}");
        String newAmount = obj.toString();
    }//saveEditChanges

}//OpportunitiesBean
