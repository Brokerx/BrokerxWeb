/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.ctrl.CtrlCollection;
import com.firstidea.garnet.web.brokerx.ctrl.LeadCtrl;
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
@Path("lead")
public class LeadResource extends AppResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LeadResource
     */
    public LeadResource() {
    }

    @POST
    @Path("/saveLead")
    @Produces("application/json")
    public String saveLead(String leadJSON) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        String response = leadCtrl.saveLead(leadJSON);
        return response;
    }

    @POST
    @Path("/getLeads")
    @Produces("application/json")
    public String getLeads(@FormParam("userID") Integer userID,
            @FormParam("type") String type,
            @FormParam("status") String status,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);

        String response = leadCtrl.getLeads(userID, type, status, startDate, endDate);
        return response;
    }

    @POST
    @Path("/getBrokerLeads")
    @Produces("application/json")
    public String getBrokerLeads(@FormParam("brokerID") Integer brokerID,
            @FormParam("type") String type,
            @FormParam("status") String status,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);

        String response = leadCtrl.getLeadsByBroker(brokerID, type, status, startDate, endDate);
        return response;
    }

    @POST
    @Path("/getLeadHistory")
    @Produces("application/json")
    public String getLeadHistory(@FormParam("leadID") Integer leadID) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.getLeadHistory(leadID);
    }

    @POST
    @Path("/getActiveLeads")
    @Produces("application/json")
    public String getActiveLeads(@FormParam("userID") Integer userID,
            @FormParam("type") String type) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.getActiveLeads(userID, type);
    }

    @POST
    @Path("/getHistory")
    @Produces("application/json")
    public String getHistory(@FormParam("userID") Integer userID,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.getHistory(userID, startDate, endDate);
    }
    
    @POST
    @Path("/dealDone")
    @Produces("application/json")
    public String dealDone(@FormParam("leadID")  Integer leadID) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.dealDone(leadID);
    }
}
