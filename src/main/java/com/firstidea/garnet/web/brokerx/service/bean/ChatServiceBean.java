/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service.bean;

import com.firstidea.garnet.web.brokerx.constants.QueryConstants;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.Chat;
import com.firstidea.garnet.web.brokerx.entity.ChatSummary;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.entity.Notification;
import com.firstidea.garnet.web.brokerx.entity.User;
import com.firstidea.garnet.web.brokerx.service.ChatService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.GCMUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            Integer leadID, String type, String message, String fromUserType, String itemName) {
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

            Query updateChatSummary = em.createNativeQuery(QueryConstants.UPDATE_CHAT_SUMMARY_BY_USERS_N_LEAD)
                    .setParameter("msg", message)
                    .setParameter("msgType", type)
                    .setParameter("leadID", leadID)
                    .setParameter("fromUserID", fromUserID)
                    .setParameter("toUserID", toUserID);
            int updateCount = updateChatSummary.executeUpdate();
            if (updateCount <= 0) {
                createChatSummary(chat);
            }
            GCMUtils.sendNotification(toUser.getGcmKey(), msgJSON, GCMUtils.TYPE_USER_COMMUNICATION,"");
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

    private void createChatSummary(Chat chat) {
        Lead lead = em.find(Lead.class, chat.getLeadID());
        ChatSummary chatSummary = new ChatSummary();
        chatSummary.setLeadID(chat.getLeadID());
        chatSummary.setFromUserID(chat.getFromUserID());
        chatSummary.setToUserID(chat.getToUserID());
        chatSummary.setLastMsgType(chat.getType());
        chatSummary.setLastMsg(chat.getMessage());
        chatSummary.setItemName(lead.getItemName());
        chatSummary.setLastMsgDateTime(ApptDateUtils.getCurrentDateAndTime());
        if (lead.getType().equals("B")) {
            if (chat.getFromUserID() == lead.getCreatedUserID()) {
                chatSummary.setFromUserType("B");
            } else if (chat.getFromUserID() == lead.getBrokerID()) {
                chatSummary.setFromUserType("R");
            } else {
                chatSummary.setFromUserType("S");
            }

            if (chat.getToUserID() == lead.getCreatedUserID()) {
                chatSummary.setToUserType("B");
            } else if (chat.getToUserID() == lead.getBrokerID()) {
                chatSummary.setToUserType("R");
            } else {
                chatSummary.setToUserType("S");
            }
        } else {
            if (chat.getFromUserID() == lead.getCreatedUserID()) {
                chatSummary.setFromUserType("S");
            } else if (chat.getFromUserID() == lead.getBrokerID()) {
                chatSummary.setFromUserType("R");
            } else {
                chatSummary.setFromUserType("B");
            }

            if (chat.getToUserID() == lead.getCreatedUserID()) {
                chatSummary.setToUserType("S");
            } else if (chat.getToUserID() == lead.getBrokerID()) {
                chatSummary.setToUserType("R");
            } else {
                chatSummary.setToUserType("B");
            }
        }

        em.persist(chatSummary);
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

    @Override
    public MessageDTO getNotifications(Integer userID) {
        MessageDTO messageDTO = null;
        try {
            Query query = em.createQuery(QueryConstants.GET_NOTIFICATIONS_BY_USERID)
                    .setParameter("userID", userID);
            List<Notification> notifications = query.getResultList();
            List<Integer> userIDs = new ArrayList<Integer>();
            for (Notification notification : notifications) {
                userIDs.add(notification.getFromUserID());
            }
            Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                    .setParameter("userIDs", userIDs);
            List<User> users = userQuery.getResultList();
            Map<Integer, User> usersMap = new HashMap();
            for (User user : users) {
                usersMap.put(user.getUserID(), user);
            }
            for (Notification notification : notifications) {
                notification.setFromUser(usersMap.get(notification.getFromUserID()));
            }
            messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(notifications);
            Query updateQuery = em.createNativeQuery(QueryConstants.UPDATE_NOTIFICATION_READ_FLAG_BY_USERID)
                    .setParameter("userID", userID);
            int rowsUpdated = updateQuery.executeUpdate();
        } catch (Exception e) {
            logger.error(" ERROR : getNotifications() ERROR : userID " + userID);
            messageDTO = MessageDTO.getFailureDTO();
        }

        return messageDTO;
    }

    @Override
    public MessageDTO getUnreadNotificationCount(Integer userID) {
        MessageDTO messageDTO = null;
        try {
            Query query = em.createNativeQuery(QueryConstants.GET_UNREAD_NOTIFICATION_COUNT_BY_USERID)
                    .setParameter("userID", userID);
            BigInteger count = (BigInteger) query.getSingleResult();
            messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(count);
        } catch (Exception e) {
            logger.error(" ERROR : getUnreadNotificationCount() ERROR : userID " + userID);
            messageDTO = MessageDTO.getFailureDTO();
        }

        return messageDTO;
    }

    @Override
    public MessageDTO getChatSummary(Integer userID) {
        MessageDTO messageDTO = null;
        try {
            Query query = em.createQuery(QueryConstants.GET_CHAT_SUMMARY_BY_USERS_ID)
                    .setParameter("userID", userID);
            List<ChatSummary> chatSummarys = query.getResultList();
            List<Integer> userIDs = new ArrayList<Integer>();
            for (ChatSummary chatSummary : chatSummarys) {
                if (chatSummary.getToUserID().equals(userID)) {
                    userIDs.add(chatSummary.getFromUserID());
                } else {
                    userIDs.add(chatSummary.getToUserID());
                }
            }
            Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                    .setParameter("userIDs", userIDs);
            List<User> users = userQuery.getResultList();
            Map<Integer, User> usersMap = new HashMap();
            for (User user : users) {
                usersMap.put(user.getUserID(), user);
            }
            for (ChatSummary chatSummary : chatSummarys) {
                if (chatSummary.getToUserID().equals(userID)) {
                    chatSummary.setToUser(usersMap.get(chatSummary.getFromUserID()));
                } else {
                    chatSummary.setToUser(usersMap.get(chatSummary.getToUserID()));
                }
            }
            messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(chatSummarys);
        } catch (Exception e) {
            logger.error(" ERROR : getChatSummary() ERROR : userID " + userID);
            messageDTO = MessageDTO.getFailureDTO();
        }

        return messageDTO;
    }
}
