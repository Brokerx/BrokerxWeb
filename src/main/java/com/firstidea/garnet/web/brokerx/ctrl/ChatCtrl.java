/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl;

/**
 *
 * @author Govind
 */
public interface ChatCtrl extends AuthenticationCtrl{
    public String sendMsg(Integer fromUserID, String fromUserName, Integer toUserID, Integer leadID, String type, String message, String fromUserType, String itemName);
    public String getChats(Integer fromUserID, Integer toUserID, Integer leadID);
    public String getNotifications(Integer userID);
}
