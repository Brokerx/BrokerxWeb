/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Govind
 */
@Path("login")
public class LoginResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginResource
     */
    public LoginResource() {
    }

    @POST
    @Produces("application/json")
    @Path("/admin")
    public String adminLogin(@FormParam("userName")String userName,
                            @FormParam("password")String password) {
        if(userName.equals("admin@brokerx.in") && 
                password.equals("BrokerxAdmin321")) {
            return JsonConverter.createJson(MessageDTO.getSuccessDTO());
        }
        return JsonConverter.createJson(MessageDTO.getFailureDTO());
    }
}
