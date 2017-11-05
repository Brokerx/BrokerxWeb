/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.ctrl.CtrlCollection;
import com.firstidea.garnet.web.brokerx.ctrl.DashBoardCtrl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Govind
 */
@Path("dashboard")
public class DashboardResource extends AppResource{

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DashboardResource
     */
    public DashboardResource() {
    }

    @POST
    @Path("/getDashboard")
    @Produces("application/json")
    public String getDashboard() {
        DashBoardCtrl dashBoardCtrl = CtrlCollection.DASHBOARD_CTRL;
        setHeaders();
        dashBoardCtrl.setUserRequest(request);
        return dashBoardCtrl.getDashboard();
    }
}
