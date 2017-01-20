/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service.bean;

import com.firstidea.garnet.web.brokerx.constants.QueryConstants;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.Chat;
import com.firstidea.garnet.web.brokerx.entity.User;
import com.firstidea.garnet.web.brokerx.service.ChatService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.GCMUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Govind
 */
@Stateless
public class ChatServiceBean implements ChatService {

    static final Logger logger = LoggerFactory.getLogger(ChatServiceBean.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public MessageDTO sendMsg(Integer fromUserID, String fromUserName, Integer toUserID, 
            Integer leadID, String type, String message, String fromUserType, String itemName){
        MessageDTO messageDTO = null;
        try {
            Chat chat = new Chat();
            chat.setFromUserID(fromUserID);
            chat.setToUserID(toUserID);
            chat.setLeadID(leadID);
            chat.setType(type);
            chat.setMessage(message);
            chat.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
            em.persist(chat);
            User toUser = em.find(User.class, toUserID);
            chat.setFromUserName(fromUserName);
            chat.setFromUserType(fromUserType);
            chat.setItemName(itemName);
            String msgJSON = JsonConverter.createJson(chat);

            GCMUtils.sendNotification(toUser.getGcmKey(), msgJSON, GCMUtils.TYPE_USER_COMMUNICATION);
            messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(chat);
        } catch (Exception e) {
            logger.error(" ERROR : sendMsg() Start : fromUserID " + fromUserID + " toUserID " + toUserID + " advertisementID " + leadID
                    + " type " + type
                    + " message " + message);
            messageDTO = MessageDTO.getFailureDTO();
        }

        return messageDTO;
    }

    @Override
    public MessageDTO getChats(Integer fromUserID, Integer toUserID, Integer leadID) {
        MessageDTO messageDTO = null;
        try {
            messageDTO = MessageDTO.getSuccessDTO();
            List<Integer> userIDs = new ArrayList<Integer>(2);
            userIDs.add(fromUserID);
            userIDs.add(toUserID);
            Query query = em.createQuery(QueryConstants.GET_USER_CHATS)
                    .setParameter("fromUserID", userIDs)
                    .setParameter("toUserID", userIDs)
                    .setParameter("leadID", leadID);
            List<Chat> communications = query.getResultList();
            if (communications != null && !communications.isEmpty()) {
                messageDTO.setData(communications);
            }
        } catch (Exception e) {
            logger.error(" ERROR : getChats() Start : fromUserID " + fromUserID + " toUserID " + toUserID + " leadID " + leadID);
            messageDTO = MessageDTO.getFailureDTO();
        }

        return messageDTO;
    }

}
