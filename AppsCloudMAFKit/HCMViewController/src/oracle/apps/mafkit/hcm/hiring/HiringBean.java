/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.hcm.hiring;

import javax.el.MethodExpression;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import oracle.adf.model.datacontrols.device.DeviceManagerFactory;
import oracle.adfmf.amx.event.ActionEvent;
import oracle.adfmf.amx.event.ValueChangeEvent;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.util.Utility;

public class HiringBean {
    private PropertyChangeSupport _propertyChangeSupport = new PropertyChangeSupport(this);
    private String _contextSwitcher; //'Requisitions' or 'Candidates'
    private String _docToShow; //'Resume' or 'CoverLetter'
    private String _samplePdfResumeUrl = "http://www.1st-writer.com/ResumeExample3.pdf";
    private String _samplePdfCoverLetterUrl = "http://wvact.net/docs/Sample%20Cover%20Letter.pdf";
    private String _stageSwitcher; //'All' or 'New' or 'Review' or 'Evaluation' or 'Selection'
    private String _viewedRequisitionId;
    private String _sectionSwitcher; //'Summary' or 'Messages' or 'Update' or '360View'
    private long _selectedCandidateId;
    private boolean _pageNavigation;
    private String _searchString;

    public void addPropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.addPropertyChangeListener(l); }
    public void removePropertyChangeListener(PropertyChangeListener l) { _propertyChangeSupport.removePropertyChangeListener(l); }

    public HiringBean(){
        setContextSwitcher("Requisitions");
        _docToShow = "Resume";
        setStageSwitcher("All");
        setSectionSwitcher("Summary");
        setPageNavigation(false);
        _searchString = "";
    }//constructor
    
    public void handleReturnFromCandidateDetail(ActionEvent actionEvent){
        if (getContextSwitcher().equals("Requisitions")){
            try {
                MethodExpression mex = AdfmfJavaUtilities.getMethodExpression("#{bindings.filterRequisitionCandidates.execute}", 
                                                                              Object.class, new Class[] { });
                mex.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
            } catch (Exception e) {
                e.printStackTrace();
            }//try-catch
        }//check if returning to 'Requisition Detail'
    }//handleReturnFromCandidateDetail
    
    //Handle custom search value change
    public void filterHiringList(ValueChangeEvent valueChangeEvent){
        String methodStr = "";
        //Check list to filter
        if (getContextSwitcher().toUpperCase().equals("REQUISITIONS"))
            methodStr = "#{bindings.filterRequisitionsList.execute}";
        else
            methodStr = "#{bindings.filterCandidatesList.execute}";
        //Filter list
        try {
            MethodExpression mex = AdfmfJavaUtilities.getMethodExpression(methodStr, Object.class, new Class[] { });
            mex.invoke(AdfmfJavaUtilities.getELContext(), new Object[] { });
        } catch (Exception e) {
            e.printStackTrace();
        }//try-catch
    }//filterHiringList
    
    public void showDocument(ActionEvent actionEvent){
        if (_docToShow.equalsIgnoreCase("Resume"))
            _documentViewer(_samplePdfResumeUrl);
        else
            _documentViewer(_samplePdfCoverLetterUrl);
    }//documentViewer

    @SuppressWarnings("oracle.jdeveloper.java.nested-assignment")
    private void _documentViewer(String fileUrl) {
        try {
            URL remoteFileUrl = new URL(fileUrl);
            URLConnection connection = remoteFileUrl.openConnection();
            InputStream is = new BufferedInputStream(connection.getInputStream());
            //Saving the file locally as 'previewTempFile.<extension>'
            String fileExt = fileUrl.substring(fileUrl.lastIndexOf('.'), fileUrl.length());
            String tempFile = "/previewTempFile" + fileExt;
            // Save the file in the DownloadDirectory location
            File localFile =
                new File(AdfmfJavaUtilities.getDirectoryPathRoot(AdfmfJavaUtilities.DownloadDirectory) + tempFile);
            if (localFile.exists())
                localFile.delete();
            //Download the file.
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] data = new byte[1024];
            int read = 0;
            while ((read = is.read(data)) != -1)
                fos.write(data, 0, read);
            is.close();
            fos.close();
            //DisplayFile takes a URL string which has to be encoded on iOS. iOS does not handle "+" as an encoding 
            //for space (" ") but expects "%20" instead. Also, the leading slash MUST NOT be encoded to "%2F". 
            //We will revert it to a slash after the URLEncoder converts it to "%2F".
            StringBuffer buffer = new StringBuffer();
            String path = URLEncoder.encode(localFile.getPath(), "UTF-8");
            //Replace "+" with "%20"
            String replacedString = "+";
            String replacement = "%20";
            int index = 0, previousIndex = 0;
            index = path.indexOf(replacedString, index);
            while (index != -1) {
                buffer.append(path.substring(previousIndex, index)).append(replacement);
                previousIndex = index + 1;
                index = path.indexOf(replacedString, index + replacedString.length());
            }//loop
            buffer.append(path.substring(previousIndex, path.length()));
            //Revert the leading encoded slash ("%2F") to a literal slash ("/").
            if (buffer.indexOf("%2F") == 0)
                buffer.replace(0, 3, "/");
            //Create URL and invoke displayFile with its String representation.
            URL localURL = null;
            if (Utility.getOSFamily() == Utility.OSFAMILY_ANDROID)
                localURL = new URL("file", "localhost", localFile.getAbsolutePath());
            else if (Utility.getOSFamily() == Utility.OSFAMILY_IOS)
                localURL = new URL("file", "localhost", buffer.toString());
            DeviceManagerFactory.getDeviceManager().displayFile(localURL.toString(), "remotefile");
        } catch (Throwable t) {
            t.printStackTrace();
        }//try-catch
    }//_documentViewer
    
    //Accessors
    public void setContextSwitcher(String s) {
        String oldValue = _contextSwitcher;
        _contextSwitcher = s;
        _propertyChangeSupport.firePropertyChange("contextSwitcher", oldValue, _contextSwitcher);
    }//setContextSwitcher
    public String getContextSwitcher() { return _contextSwitcher; }
    //
    public void setDocToShow(String s) {
        String oldValue = _docToShow;
        _docToShow = s;
        _propertyChangeSupport.firePropertyChange("docToShow", oldValue, _docToShow);
    }//setDocToShow
    public String getDocToShow() { return _docToShow; }
    //
    public void setStageSwitcher(String s) {
        String oldValue = _stageSwitcher;
        _stageSwitcher = s;
        _propertyChangeSupport.firePropertyChange("stageSwitcher", oldValue, _stageSwitcher);
    }//setStageSwitcher
    public String getStageSwitcher() { return _stageSwitcher; }
    //
    public void setViewedRequisitionId(String s) {
        String oldValue = _viewedRequisitionId;
        _viewedRequisitionId = s;
        _propertyChangeSupport.firePropertyChange("viewedRequisitionId", oldValue, _viewedRequisitionId);
    }//setViewedRequisitionId
    public String getViewedRequisitionId() { return _viewedRequisitionId; }
    //
    public void setSectionSwitcher(String s) {
        String oldValue = _sectionSwitcher;
        _sectionSwitcher = s;
        _propertyChangeSupport.firePropertyChange("sectionSwitcher", oldValue, _sectionSwitcher);
    }//setSectionSwitcher
    public String getSectionSwitcher() { return _sectionSwitcher; }
    //
    public void setSelectedCandidateId(long l) {
        long oldValue = _selectedCandidateId;
        _selectedCandidateId = l;
        _propertyChangeSupport.firePropertyChange("selectedCandidateId", oldValue, _selectedCandidateId);
    }//setSelectedCandidateId
    public long getSelectedCandidateId() { return _selectedCandidateId; }
    //
    public void setPageNavigation(boolean b) {
        boolean oldValue = _pageNavigation;
        _pageNavigation = b;
        _propertyChangeSupport.firePropertyChange("pageNavigation", oldValue, _pageNavigation);
    }//setPageNavigation
    public boolean isPageNavigation() { return _pageNavigation; }
    //
    public void setSearchString(String s) { 
        String oldValue = _searchString;
        _searchString = s;
        _propertyChangeSupport.firePropertyChange("searchString", oldValue, _searchString);
    }//setSearchString
    public String getSearchString() { return _searchString; }
}//HiringBean
