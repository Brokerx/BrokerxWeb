/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import javax.ejb.Local;

/**
 *
 * @author Govind
 */
@Local
public interface ChatService {
    public MessageDTO sendMsg(Integer fromUserID, String fromUserName, Integer toUserID, Integer leadID, String type, String message, String fromUserType, String itemName);
    public MessageDTO getChats(Integer fromUserID, Integer toUserID, Integer leadID);
    public MessageDTO getNotifications(Integer userID);
    public MessageDTO getUnreadNotificationCount(Integer userID);
    public MessageDTO getChatSummary(Integer userID);
}
