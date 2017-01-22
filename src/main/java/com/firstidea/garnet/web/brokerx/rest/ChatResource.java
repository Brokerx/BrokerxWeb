/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.ctrl.ChatCtrl;
import com.firstidea.garnet.web.brokerx.ctrl.CtrlCollection;
import com.firstidea.garnet.web.brokerx.ctrl.LeadCtrl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author Govind
 */
@Path("chat")
public class ChatResource extends AppResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ChatResource
     */
    public ChatResource() {
    }

    @POST
    @Path("/getChats")
    @Produces("application/json")
    public String getChats(@FormParam("fromUserID") Integer fromUserID,
            @FormParam("toUserID") Integer toUserID,
            @FormParam("leadID") Integer leadID) {
        ChatCtrl chatCtrl = CtrlCollection.CHAT_CTRL;
        chatCtrl.setUserRequest(request);

        String response = chatCtrl.getChats(fromUserID, toUserID, leadID);
        return response;
    }

    @POST
    @Path("/sendMsg")
    @Produces("application/json")
    public String sendMsg(@FormParam("fromUserID") Integer fromUserID,
            @FormParam("fromUserName") String fromUserName,
            @FormParam("toUserID") Integer toUserID,
            @FormParam("advertisementID") Integer advertisementID,
            @FormParam("type") String type,
            @FormParam("message") String message,
            @FormParam("fromUserType") String fromUserType,
            @FormParam("itemName") String itemName) {
        ChatCtrl chatCtrl = CtrlCollection.CHAT_CTRL;
        chatCtrl.setUserRequest(request);
        String response = chatCtrl.sendMsg(fromUserID, fromUserName, toUserID, advertisementID, type, message, fromUserType, itemName);
        return response;
    }
    
    @POST
    @Path("/getNotifications")
    @Produces("application/json")
    public String getNotifications(@FormParam("userID") Integer userID) {
        ChatCtrl chatCtrl = CtrlCollection.CHAT_CTRL;
        chatCtrl.setUserRequest(request);
        String response = chatCtrl.getNotifications(userID);
        return response;
    }
}
