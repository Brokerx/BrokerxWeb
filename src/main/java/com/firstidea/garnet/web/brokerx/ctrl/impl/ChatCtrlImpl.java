/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl.impl;

import com.firstidea.garnet.web.brokerx.auth.Authentication;
import com.firstidea.garnet.web.brokerx.ctrl.ChatCtrl;
import com.firstidea.garnet.web.brokerx.service.ChatService;
import com.firstidea.garnet.web.brokerx.service.JndiService;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;

/**
 *
 * @author Govind
 */
public class ChatCtrlImpl extends Authentication implements ChatCtrl {

    ChatService chatService = JndiService.getChatService();
    @Override
    public String sendMsg(Integer fromUserID, String fromUserName, Integer toUserID, Integer leadID, 
            String type, String message, String fromUserType, String itemName) {
        return JsonConverter.createJson(chatService.sendMsg(fromUserID, fromUserName, toUserID, leadID, type, message, fromUserType, itemName));
    }

    @Override
    public String getChats(Integer fromUserID, Integer toUserID, Integer leadID) {
        return JsonConverter.createJson(chatService.getChats(fromUserID, toUserID, leadID));
    }
    
    @Override
    public String getNotifications(Integer userID) {
        return JsonConverter.createJson(chatService.getNotifications(userID));
    }
    
}
