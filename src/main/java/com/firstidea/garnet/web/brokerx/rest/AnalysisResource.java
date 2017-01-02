/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.ctrl.AnalysisCtrl;
import com.firstidea.garnet.web.brokerx.ctrl.CtrlCollection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author Govind
 */
@Path("analysis")
public class AnalysisResource  extends AppResource{

    @Context
    private UriInfo context;

    AnalysisCtrl analysisCtrl = CtrlCollection.ANALYSIS_CTRL;
    /**
     * Creates a new instance of AnalysisResource
     */
    public AnalysisResource() {
    }
    
    @POST
    @Path("/getBrokerTopHighestPayingLeads")
    @Produces("application/json")
    public String getBrokerTopHighestPayingLeads(
            @FormParam("brokerID") Integer brokerID,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        analysisCtrl.setUserRequest(request);
        String response = analysisCtrl.getBrokerTopHighestPayingLeads(brokerID, startDate, endDate);
        return response;
    }
    
    @POST
    @Path("/getBrokerTopHighestPayingUsers")
    @Produces("application/json")
    public String getBrokerTopHighestPayingUsers(
            @FormParam("brokerID") Integer brokerID,
            @FormParam("type")String type,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        analysisCtrl.setUserRequest(request);
        String response = analysisCtrl.getBrokerTopHighestPayingUsers(brokerID, type, startDate, endDate);
        return response;
    }
    
    @POST
    @Path("/getBrokerDesparity")
    @Produces("application/json")
    public String getBrokerDesparity(
            @FormParam("brokerID") Integer brokerID,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        analysisCtrl.setUserRequest(request);
        String response = analysisCtrl.getBrokerDesparity(brokerID, startDate, endDate);
        return response;
    }
    
    @POST
    @Path("/getTopBrokers")
    @Produces("application/json")
    public String getTopBrokers(
            @FormParam("userID") Integer userID,
            @FormParam("type")String type,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        analysisCtrl.setUserRequest(request);
        String response = analysisCtrl.getTopBrokers(userID, type, startDate, endDate);
        return response;
    }
    
}
