/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * Licensed under the Universal Permissive License v 1.0 as shown at http://oss.oracle.com/licenses/upl.
 */
package oracle.apps.mafkit.shell.infolets;

public class OpenPipeline {
    private Double _pipelineValue;
    private String _pipelineGroup;
    private String _pipelineSeries;

    public OpenPipeline() {
        super();
    }//constructor

    public OpenPipeline(Double pipelineValue, String pipelineGroup, String pipelineSeries) {
        super();
        _pipelineValue = pipelineValue;
        _pipelineGroup = pipelineGroup;
        _pipelineSeries = pipelineSeries;
    }//constructor

    //Accessors
    public void setPipelineValue(Double d) { _pipelineValue = d; }
    public Double getPipelineValue() { return _pipelineValue; }
    public void setPipelineGroup(String s) { _pipelineGroup = s; }
    public String getPipelineGroup() { return _pipelineGroup; } 
    public void setPipelineSeries(String s) { _pipelineSeries = s; }
    public String getPipelineSeries() { return _pipelineSeries; }
}//OpenPipeline
