/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service.bean;

import com.firstidea.garnet.web.brokerx.constants.QueryConstants;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.entity.LeadDocument;
import com.firstidea.garnet.web.brokerx.entity.LeadHistory;
import com.firstidea.garnet.web.brokerx.entity.LeadStatusHistory;
import com.firstidea.garnet.web.brokerx.entity.Notification;
import com.firstidea.garnet.web.brokerx.entity.User;
import com.firstidea.garnet.web.brokerx.enums.LeadCurrentStatus;
import com.firstidea.garnet.web.brokerx.enums.NotificationType;
import com.firstidea.garnet.web.brokerx.filehandling.FileUploadHelper;
import com.firstidea.garnet.web.brokerx.service.LeadService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.GCMUtils;
import com.firstidea.garnet.web.brokerx.util.GarnetStringUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Govind
 */
@Stateless
public class LeadServiceBean implements LeadService {

    static final Logger logger = LoggerFactory.getLogger(LeadServiceBean.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public MessageDTO saveLead(Lead lead) {
        try {
            Lead prevLead = null;
            String fieldsAltered = null;
            lead.setLastUpdDateTime(ApptDateUtils.getCurrentDateAndTime());
            if (lead.getLeadID() != null) {
                prevLead = em.find(Lead.class, lead.getLeadID());
                fieldsAltered = getFieldsAltered(prevLead, lead);
                lead = em.merge(lead);
            } else {
                lead.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
                if (lead.getParentLeadID() != null) {
                    Lead parentLead = em.find(Lead.class, lead.getParentLeadID());
                    if (parentLead.getType().equals("B")) {
                        parentLead.setBuyerStatus(LeadCurrentStatus.Accepted.getStatus());
                    } else {
                        parentLead.setSellerStatus(LeadCurrentStatus.Accepted.getStatus());
                    }
                    parentLead.setBrokerStatus(LeadCurrentStatus.Accepted.getStatus());
                    parentLead.setAssignedToUserID(lead.getCreatedUserID());
                    em.merge(parentLead);
                }
                em.persist(lead);
            }
            LeadHistory leadHistory = mapLeadToLeadHistory(fieldsAltered, lead);
            em.persist(leadHistory);
            List<Integer> userIDs = new ArrayList<Integer>();
            userIDs.add(lead.getCreatedUserID());
            userIDs.add(lead.getBrokerID());
            if (lead.getAssignedToUserID() != null) {
                userIDs.add(lead.getAssignedToUserID());
            }
            Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                    .setParameter("userIDs", userIDs);
            List<User> users = userQuery.getResultList();
            Map<Integer, User> usersMap = new HashMap();
            for (User user : users) {
                usersMap.put(user.getUserID(), user);
            }
            lead.setBroker(usersMap.get(lead.getBrokerID()));
            lead.setAssignedToUser(usersMap.get(lead.getAssignedToUserID()));
            lead.setCreatedUser(usersMap.get(lead.getCreatedUserID()));

            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(lead);
            //sendGCMNotification(prevLead, lead);
            sendLeadHistoryNotification(leadHistory, lead);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " saveLead() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    
    private void sendLeadHistoryNotification(LeadHistory leadHistory, Lead lead) {
        Notification notification = new Notification();
        notification.setFromUserID(lead.getLastUpdUserID());
        String createdUsername = "";
        String gcmKey = "";
        if (leadHistory.getCreatedUserID() == lead.getCreatedUserID()) {
            notification.setToUserID(lead.getBrokerID());
            gcmKey = lead.getBroker().getGcmKey();
            createdUsername = lead.getCreatedUser().getFullName();
        } else {
            notification.setToUserID(lead.getCreatedUserID());
            createdUsername = lead.getBroker().getFullName();
            gcmKey = lead.getCreatedUser().getGcmKey();
        }
        notification.setLeadID(lead.getLeadID());
        notification.setItemName(lead.getItemName());
        if (lead.getIsMoveToPending() != null && !lead.getIsMoveToPending()) {
            notification.setType(NotificationType.MOVED_TO_PENDING_LEAD.getNotificationType());
            notification.setMessage("Moved to pending deals");
        } else if (leadHistory.getFieldsAltered().equals("Changed Status")) {
            notification.setMessage(leadHistory.getFieldsAltered());
            notification.setType(NotificationType.LEAD_STATUS_CHANGED.getNotificationType());
        } else if (leadHistory.getFieldsAltered().equals("Lead Created")) {
            notification.setMessage("Lead Created");
            notification.setType(NotificationType.LEAD_CREATED.getNotificationType());
        } else {
            notification.setMessage("Suggested Some Changes");
            notification.setType(NotificationType.LEAD_REVERTED.getNotificationType());
        }
        notification.setIsRead(false);
        notification.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
        em.persist(notification);
        
        sendPushNotification(gcmKey, createdUsername, notification);

    }

    private void sendGCMNotification(Lead prevLead, Lead lead) {
        User broker = em.find(User.class, lead.getBrokerID());
        User createdUser = em.find(User.class, lead.getCreatedUserID());
        String gcmKey = "", userName = "";
        Notification notification = new Notification();
        notification.setLeadID(lead.getLeadID());
        String type = GCMUtils.TYPE_NEW_LEAD_ADDED;
        notification.setType(NotificationType.LEAD_CREATED.getNotificationType());
        notification.setMessage("Lead Created");
        if (lead.getIsMoveToPending() != null && !lead.getIsMoveToPending()) {
            type = GCMUtils.TYPE_NEW_NOTIFICATION;
            notification.setType(NotificationType.MOVED_TO_PENDING_LEAD.getNotificationType());
            notification.setMessage("Moved to pending deals");
        } else if (prevLead != null) {
            type = GCMUtils.TYPE_LEAD_REVERTED;
            notification.setType(NotificationType.LEAD_REVERTED.getNotificationType());
            notification.setMessage("Suggested Some Changes");
        }
        notification.setItemName(lead.getItemName());
        notification.setIsRead(false);
        notification.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
        if (lead.getLastUpdUserID().equals(createdUser.getUserID())) {
            gcmKey = broker.getGcmKey();
            userName = createdUser.getFullName();
            notification.setFromUserID(createdUser.getUserID());
            notification.setToUserID(broker.getUserID());
        } else {
            gcmKey = createdUser.getGcmKey();
            userName = broker.getFullName();
            notification.setFromUserID(broker.getUserID());
            notification.setToUserID(createdUser.getUserID());
        }

        em.persist(notification);
        if (StringUtils.isNotBlank(gcmKey)) {
            sendPushNotification(gcmKey, userName, notification);
        }
    }

    @Override
    public MessageDTO getActiveLeads(Integer userID, String type) {
        try {
            StringBuilder queryString = new StringBuilder(QueryConstants.GET_ALL_LEADS);
            Map<String, Object> queryParams = new HashMap<String, Object>();
            if (userID != null) {
                queryString.append(" AND l.createdUserID= :createdUserID");
                queryParams.put("createdUserID", userID);
            }
            String statusColumnName = type.equals("B") ? "l.buyerStatus" : "l.sellerStatus";
            String includeStatus = ""+ LeadCurrentStatus.Rejected.getStatus() + "," + LeadCurrentStatus.Accepted.getStatus() + "," + LeadCurrentStatus.Waiting.getStatus() + "," + LeadCurrentStatus.Reverted.getStatus();
            List<String> includeStatusList = GarnetStringUtils.getListOfComaValues(includeStatus);
            queryString.append(" AND " + statusColumnName + " IN (:includeStatus) ");
//                    + " AND l.assignedToUserID Is Null");
            queryParams.put("includeStatus", includeStatusList);
            Query query = em.createQuery(queryString.toString());
            for (String param : queryParams.keySet()) {
                query.setParameter(param, queryParams.get(param));
            }
            List<Lead> leads = query.getResultList();
            if (leads != null && !leads.isEmpty()) {
                for (Lead lead : leads) {
                    User broker = em.find(User.class, lead.getBrokerID());
                    lead.setBroker(broker);
                    if (lead.getAssignedToUserID() != null) {
                        User assignedTouser = em.find(User.class, lead.getAssignedToUserID());
                        lead.setAssignedToUser(assignedTouser);
                    }
                }
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leads);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " saveLead() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO dealDone(Integer leadID) {
        try {
            Lead childLead = em.find(Lead.class, leadID);
            Lead parentLead = em.find(Lead.class, childLead.getParentLeadID());
            parentLead.setBuyerStatus(LeadCurrentStatus.Deleted.getStatus());
            parentLead.setSellerStatus(LeadCurrentStatus.Deleted.getStatus());
            parentLead.setBrokerStatus(LeadCurrentStatus.Deleted.getStatus());

            childLead.setBuyerStatus(LeadCurrentStatus.Done.getStatus());
            childLead.setSellerStatus(LeadCurrentStatus.Done.getStatus());
            childLead.setBrokerStatus(LeadCurrentStatus.Done.getStatus());
            childLead.setAssignedToUserID(childLead.getCreatedUserID());
            childLead.setCreatedUserID(parentLead.getCreatedUserID());
            childLead.setType(parentLead.getType());

            LeadStatusHistory leadStatusHistory = new LeadStatusHistory();
            leadStatusHistory.setLeadID(childLead.getLeadID());
            leadStatusHistory.setCurrentStatus(1);
            leadStatusHistory.setDealDoneDateTime(ApptDateUtils.getCurrentDateAndTime());
            em.merge(leadStatusHistory);

            if (parentLead.getType().equals("B")) {
                childLead.setBuyerBrokerage(parentLead.getBrokerageAmt());
                childLead.setSellerBrokerage(childLead.getBrokerageAmt());
            } else {
                childLead.setBuyerBrokerage(childLead.getBrokerageAmt());
                childLead.setSellerBrokerage(parentLead.getBrokerageAmt());
            }

            em.merge(parentLead);
            Query query = em.createNativeQuery(QueryConstants.UPDATE_LEAD_ID_IN_CHAT_ON_DEAL_DONE)
                    .setParameter("newLeadID", childLead.getLeadID())
                    .setParameter("oldLeadID", parentLead.getLeadID());
                    
            int chatsUpdCount = query.executeUpdate();
            
            Query chatSummaryUpdatequery = em.createNativeQuery(QueryConstants.UPDATE_LEAD_ID_IN_CHAT_SUMMARY_ON_DEAL_DONE)
                    .setParameter("newLeadID", childLead.getLeadID())
                    .setParameter("oldLeadID", parentLead.getLeadID());
                    
            int chatsSummaryUpdateCount = chatSummaryUpdatequery.executeUpdate();
            
            childLead = em.merge(childLead);
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(childLead);
            sendDealDoneNotification(childLead);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " dealDone() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO saveLeadStatusHistory(LeadStatusHistory leadStatusHistory) {
        try {
            em.merge(leadStatusHistory);
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leadStatusHistory);
            sendLeadStatusHisoryNotification(leadStatusHistory.getLeadID(), false);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " saveLeadStatusHistory() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    private void sendLeadStatusHisoryNotification(Integer leadID, boolean isDocumentUploaded) {
        Lead lead = em.find(Lead.class, leadID);
        List<Integer> userIDs = new ArrayList<Integer>();
        Integer buyerID,sellerID;
        if(lead.getType().equals("B")){
            buyerID = lead.getCreatedUserID();
            sellerID = lead.getAssignedToUserID();
        } else {
            buyerID = lead.getAssignedToUserID();
            sellerID = lead.getCreatedUserID();
        }
        userIDs.add(buyerID);
        userIDs.add(sellerID);
        userIDs.add(lead.getBrokerID());
        Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                .setParameter("userIDs", userIDs);
        List<User> users = userQuery.getResultList();
        Map<Integer, User> usersMap = new HashMap();
        for (User user : users) {
            usersMap.put(user.getUserID(), user);
        }
        String message = isDocumentUploaded ? "Document Uploaded" : "Lead Stage Updated";
        String type = isDocumentUploaded ? NotificationType.ANALYSIS_DOCUMENT_UPLOADED.getNotificationType() : NotificationType.ANALYSIS_STATUS_CHANGED.getNotificationType();
        Notification buyerNotification = new Notification();
        buyerNotification.setFromUserID(sellerID);
        String createdUsername = usersMap.get(sellerID).getFullName();
        String brokerName = usersMap.get(lead.getBrokerID()).getFullName();
        buyerNotification.setToUserID(buyerID);
        buyerNotification.setLeadID(lead.getLeadID());
        buyerNotification.setItemName(lead.getItemName());
        buyerNotification.setMessage(message);
        buyerNotification.setType(type);
        buyerNotification.setIsRead(false);
        buyerNotification.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
        em.persist(buyerNotification);

        // For Broker
        Notification brokerNotification = new Notification();
        brokerNotification.setFromUserID(sellerID);
        brokerNotification.setToUserID(lead.getBrokerID());
        brokerNotification.setLeadID(lead.getLeadID());
        brokerNotification.setItemName(lead.getItemName());
        brokerNotification.setMessage(message);
        brokerNotification.setType(type);
        brokerNotification.setIsRead(false);
        brokerNotification.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
        em.persist(brokerNotification);

        sendPushNotification(usersMap.get(buyerID).getGcmKey(), createdUsername, buyerNotification);
        sendPushNotification(usersMap.get(lead.getBrokerID()).getGcmKey(), brokerName, brokerNotification);
    }

    @Override
    public MessageDTO getLeadStatusHistory(Integer leadID) {
        try {
            LeadStatusHistory leadStatusHistory = em.find(LeadStatusHistory.class, leadID);
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leadStatusHistory);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " getLeadStatusHistory() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getHistory(Integer userID, Date startDate, Date endDate) {
        try {
            StringBuilder queryString = new StringBuilder(QueryConstants.GET_ALL_LEADS);
            Map<String, Object> queryParams = new HashMap<String, Object>();
            if (userID != null) {
                queryString.append(" AND (CreatedUserID= :createdUserID OR BrokerID=:createdUserID OR AssignedToUserID=:createdUserID)");
                queryParams.put("createdUserID", userID);
            }
            if (startDate != null && endDate != null) {
                queryString.append(" AND l.createdDttm BETWEEN :startDate AND :endDate");
                queryParams.put("startDate", startDate);
                queryParams.put("endDate", endDate);
            }
            String status = LeadCurrentStatus.Done.getStatus();
            queryString.append(" AND BuyerStatus= :status AND SellerStatus=:status AND BrokerStatus=:status AND SellerStatus=:status  "
                    + " AND ParentLeadID Is NOT Null ORDER BY LastUpdDateTime DESC");
            queryParams.put("status", status);
            Query query = em.createQuery(queryString.toString());
            for (String param : queryParams.keySet()) {
                query.setParameter(param, queryParams.get(param));
            }
            List<Lead> leads = query.getResultList();
            if (leads != null && !leads.isEmpty()) {
                List<Integer> userIDs = new ArrayList<Integer>();
                for (Lead lead : leads) {
                    userIDs.add(lead.getCreatedUserID());
                    userIDs.add(lead.getBrokerID());
                    userIDs.add(lead.getAssignedToUserID());
                }
                Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                        .setParameter("userIDs", userIDs);
                List<User> users = userQuery.getResultList();
                Map<Integer, User> usersMap = new HashMap();
                for (User user : users) {
                    usersMap.put(user.getUserID(), user);
                }
                for (Lead lead : leads) {
                    lead.setBroker(usersMap.get(lead.getBrokerID()));
                    lead.setAssignedToUser(usersMap.get(lead.getAssignedToUserID()));
                    lead.setCreatedUser(usersMap.get(lead.getCreatedUserID()));
                }
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leads);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " saveLead() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getLeads(Integer leadID, Integer userID, String type, String status, String item, String brokerIDString, Date startDate, Date endDate) {
        try {
            StringBuilder queryString = new StringBuilder(QueryConstants.GET_ALL_LEADS);
            Map<String, Object> queryParams = new HashMap<String, Object>();

            if (leadID != null) {
                queryString.append(" AND l.leadID= :leadID");
                queryParams.put("leadID", leadID);
            } else {
                if (userID != null) {
                    queryString.append(" AND (l.createdUserID= :createdUserID"
                            + " OR l.brokerID= :createdUserID) ");
                    queryParams.put("createdUserID", userID);
                }
                if (type != null) {
                    queryString.append(" AND l.type= :type");
                    queryParams.put("type", type);
                }
                if (status != null && type != null) {
                    String statusColumnName = type.equals("B") ? "buyerStatus" : "sellerStatus";
                    queryString.append(" AND l.").append(statusColumnName).append("= :currentStatus");
                    queryParams.put("currentStatus", status);
                }
                if (item != null) {
                    queryString.append(" AND l.itemName=:itemName");
                    queryParams.put("itemName", item);
                }
                if (brokerIDString != null) {
                    Integer brokerID = Integer.parseInt(brokerIDString);
                    queryString.append(" AND l.brokerID=:brokerID");
                    queryParams.put("brokerID", brokerID);
                }
                if (startDate != null && endDate != null) {
                    queryString.append(" AND l.createdDttm BETWEEN :startDate AND :endDate");
                    queryParams.put("startDate", startDate);
                    queryParams.put("endDate", endDate);
                }
            }
            Query query = em.createQuery(queryString.toString());
            for (String param : queryParams.keySet()) {
                query.setParameter(param, queryParams.get(param));
            }
            List<Lead> leads = query.getResultList();
            if (leads != null && !leads.isEmpty()) {
                List<Integer> userIDs = new ArrayList<Integer>();
                for (Lead lead : leads) {
                    userIDs.add(lead.getCreatedUserID());
                    userIDs.add(lead.getBrokerID());
                    if (lead.getAssignedToUserID() != null) {
                        userIDs.add(lead.getAssignedToUserID());
                    }
                }
                Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                        .setParameter("userIDs", userIDs);
                List<User> users = userQuery.getResultList();
                Map<Integer, User> usersMap = new HashMap();
                for (User user : users) {
                    usersMap.put(user.getUserID(), user);
                }
                for (Lead lead : leads) {
                    lead.setBroker(usersMap.get(lead.getBrokerID()));
                    if (lead.getAssignedToUserID() != null) {
                        lead.setAssignedToUser(usersMap.get(lead.getAssignedToUserID()));
                    }
                    lead.setCreatedUser(usersMap.get(lead.getCreatedUserID()));
                }
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leads);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " saveLead() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getLeadsByBroker(Integer brokerID, String type, String status, Date startDate, Date endDate) {
        try {
            StringBuilder queryString = new StringBuilder(QueryConstants.GET_ALL_LEADS);
            Map<String, Object> queryParams = new HashMap<String, Object>();

            if (brokerID != null) {
                queryString.append(" AND l.brokerID= :brokerID");
                queryParams.put("brokerID", brokerID);
            }
            if (type != null) {
                queryString.append(" AND l.type= :type");
                queryParams.put("type", type);
            }
//            if (status != null) {
//                queryString.append(" AND l.currentStatus= :currentStatus");
//                queryParams.put("currentStatus", status);ghjgh
//            }
            String includeStatus = "" + LeadCurrentStatus.Accepted.getStatus() + "," + LeadCurrentStatus.Waiting.getStatus() + "," + LeadCurrentStatus.Reverted.getStatus();
            List<String> includeStatusList = GarnetStringUtils.getListOfComaValues(includeStatus);
            queryString.append(" AND l.brokerStatus IN (:includeStatus) ");
//                    + " AND l.assignedToUserID Is Null");
            queryParams.put("includeStatus", includeStatusList);

            if (startDate != null && endDate != null) {
                queryString.append(" AND l.createdDttm BETWEEN :startDate AND :endDate");
                queryParams.put("startDate", startDate);
                queryParams.put("endDate", endDate);
            }
            queryString.append(" ORDER BY l.lastUpdDateTime DESC");
            Query query = em.createQuery(queryString.toString());
            for (String param : queryParams.keySet()) {
                query.setParameter(param, queryParams.get(param));
            }
            List<Lead> leads = query.getResultList();
            if (leads != null && !leads.isEmpty()) {
                for (Lead lead : leads) {
                    User createdUser = em.find(User.class, lead.getCreatedUserID());
                    lead.setCreatedUser(createdUser);
                    if (lead.getAssignedToUserID() != null) {
                        User assignedTouser = em.find(User.class, lead.getAssignedToUserID());
                        lead.setAssignedToUser(assignedTouser);
                    }
                }
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leads);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " saveLead() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getLeadHistory(Integer leadID) {
        try {
            List<LeadHistory> leadHistories = em.createNamedQuery("LeadHistory.getByLeadID").setParameter("leadID", leadID).getResultList();

            List<Lead> leads = mapLeadHistoryToLead(leadHistories);
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            if (!leads.isEmpty()) {
                List<Lead> historyLeads = new ArrayList<Lead>();
                Lead parrentLead = em.find(Lead.class, leadID);
                User createdUser = em.find(User.class, parrentLead.getCreatedUserID());
                User assignedTouser = null;
                if (parrentLead.getAssignedToUserID() != null) {
                    assignedTouser = em.find(User.class, parrentLead.getAssignedToUserID());
                }
                User broker = em.find(User.class, parrentLead.getBrokerID());
                for (Lead lead : leads) {
                    lead.setCreatedUser(createdUser);
                    lead.setAssignedToUser(assignedTouser);
                    lead.setBroker(broker);
                    lead.setType(parrentLead.getType());
                    lead.setBrokerID(parrentLead.getBrokerID());
                    lead.setItemName(parrentLead.getItemName());
                    lead.setItemID(parrentLead.getItemID());
                    lead.setBasicPriceUnit(parrentLead.getBasicPriceUnit());
                    lead.setExciseUnit(parrentLead.getExciseUnit());
                    lead.setAsPerAvailablity(parrentLead.getAsPerAvailablity());
                    historyLeads.add(lead);
                }
                messageDTO.setData(historyLeads);
            }
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " getLeadHistory() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    private List<Lead> mapLeadHistoryToLead(List<LeadHistory> leadHistories) {
        List<Lead> leads = new ArrayList<Lead>();
        if (!leadHistories.isEmpty()) {
            String historyJSON = JsonConverter.createJson(leadHistories);
            leads = (List<Lead>) JsonConverter.getListFromJson(historyJSON, new TypeToken<List<Lead>>() {
            }.getType());
        }

        return leads;
    }

    private LeadHistory mapLeadToLeadHistory(String fieldsAltered, Lead lead) {
        LeadHistory leadHistory = new LeadHistory();
        leadHistory.setLeadID(lead.getLeadID());
        leadHistory.setAgainstForm(lead.getAgainstForm());
        leadHistory.setAssignedToUserID(lead.getAssignedToUserID());
        leadHistory.setBasicPrice(lead.getBasicPrice());
        leadHistory.setBrokerageAmt(lead.getBrokerageAmt());
        leadHistory.setComments(lead.getComments());
        leadHistory.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
        leadHistory.setCreatedUserID(lead.getLastUpdUserID());
        String createdUserType;
        if (lead.getCreatedUserID() == lead.getLastUpdUserID()) {
            if (lead.getType().equals("B")) {
                createdUserType = "Buyer";
            } else {
                createdUserType = "Seller";
            }
        } else if (Objects.equals(lead.getCreatedUserID(), lead.getAssignedToUserID())) {
            if (lead.getType().equals("B")) {
                createdUserType = "Seller";
            } else {
                createdUserType = "Buyer";
            }
        } else {
            createdUserType = "Broker";
        }
        leadHistory.setCreatedUserType(createdUserType);
        leadHistory.setCreditPeriod(lead.getCreditPeriod());
        leadHistory.setBuyerStatus(lead.getBuyerStatus());
        leadHistory.setSellerStatus(lead.getSellerStatus());
        leadHistory.setBrokerStatus(lead.getBrokerStatus());
        leadHistory.setExciseDuty(lead.getExciseDuty());
        if (fieldsAltered != null) {
//            String fieldsAltered = getFieldsAltered(prevLead, lead);
            leadHistory.setFieldsAltered(fieldsAltered);
        } else {
            leadHistory.setFieldsAltered("Created The Lead");
        }
        leadHistory.setFreeStoragePeriod(lead.getFreeStoragePeriod());
        leadHistory.setLocation(lead.getLocation());
        leadHistory.setMake(lead.getMake());
        leadHistory.setMiscCharges(lead.getMiscCharges());
        leadHistory.setPacking(lead.getPacking());
        leadHistory.setPreferredSellerName(lead.getPreferredSellerName());
        leadHistory.setQty(lead.getQty());
        leadHistory.setQtyUnit(lead.getQtyUnit());
        leadHistory.setTransportCharges(lead.getTransportCharges());
        return leadHistory;
    }

    private String getFieldsAltered(Lead prevLead, Lead lead) {
        StringBuilder alteredFileds = new StringBuilder();
        ObjectMapper m = new ObjectMapper();
        Map<String, Object> prevLeadMap = m.convertValue(prevLead, Map.class);
        Map<String, Object> nweLeadMap = m.convertValue(lead, Map.class);
        boolean isStatusChanged = false;
        for (String key : prevLeadMap.keySet()) {
            if (key.contains("Status")) {
                isStatusChanged = true;
                continue;
            }
            if (key.contains("isMoveToPending")) {
                alteredFileds.append("Moved To Pending Deals");
                break;
            }
            if (key.contains("lastUpd") || key.contains("createdUser")
                    || key.equals("lastUpdDateTime")
                    || key.equals("createdUser")
                    || key.equals("lastUpdUserID")
                    || key.equals("asPerAvailablity")
                    || key.toLowerCase().equals("broker")
                    || key.toLowerCase().equals("seller")
                    || key.toLowerCase().equals("buyer")
                    ) {
                continue;
            }
            if (!Objects.equals(prevLeadMap.get(key), nweLeadMap.get(key))) {
                alteredFileds.append(key).append(", ");
            }
        }
        String fields = alteredFileds.toString();
        if (fields.contains(",")) {
            fields = fields.substring(0, fields.lastIndexOf(",")).trim();
        }
        if (fields.trim().length() == 0 && isStatusChanged) {
            fields = "Changed The Status";
        }
        return fields;
    }

    @Override
    public MessageDTO getLeadDocuments(Integer leadID) {
        try {
            Query query = em.createQuery(QueryConstants.GET_LEAD_DOCUMENTS_BY_LEADID)
                    .setParameter("leadID", leadID);
            List<LeadDocument> leadDocuments = query.getResultList();
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leadDocuments);
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " getLeadDocuments() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO uploadDocument(LeadDocument leadDocument, Map<String, FileItem> fileItemsMap) {
        try {
            String fileNames = "";
            for (String fileName : fileItemsMap.keySet()) {
                String uploadPath[] = FileUploadHelper.getPathToUploadFile(fileName, FileUploadHelper.FILTE_TYPE_LEAD_DOCUMENTS);
                if (uploadPath != null) {
                    boolean isFileUploaded = FileUploadHelper.UploadImage(fileItemsMap.get(fileName), uploadPath[0]);
                    if (isFileUploaded) {
                        String regex;
                        if (FileUploadHelper.fileSeparator.equals("\\")) {
                            regex = "\\\\";
                        } else {
                            regex = "/";
                        }
                        String path[] = uploadPath[0].split(regex);
                        String uploadedFileName = path[path.length - 1];
                        if (uploadedFileName.toLowerCase().endsWith(".jpg") || uploadedFileName.toLowerCase().endsWith(".jpeg")
                                || uploadedFileName.toLowerCase().endsWith(".png")) {
                            String thumbNailImagePath = uploadPath[0].substring(0, uploadPath[0].lastIndexOf(FileUploadHelper.fileSeparator));
                            FileUploadHelper.createThumbNailForUploadedImage(uploadPath[0], uploadedFileName, thumbNailImagePath, true, null, null);
                        }
                    }
                    fileNames += uploadPath[1];
                }

            }
            if (leadDocument == null) {
                return MessageDTO.getFailureDTO();
            }
            if (StringUtils.isNotBlank(fileNames)) {
                leadDocument.setDocumentURL(fileNames);
            }
            leadDocument.setUploadedDttm(ApptDateUtils.getCurrentDateAndTime());
            em.persist(leadDocument);
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leadDocument);
            sendLeadStatusHisoryNotification(leadDocument.getLeadID(), true);
            return messageDTO;
        } catch (Exception e) {
            logger.error(LeadServiceBean.class + " uploadDocument() : ERROR " + e.toString());
            return MessageDTO.getFailureDTO();
        }
    }

    private void sendPushNotification(String gcmKey, String userName, Notification notification) {
        String type = GCMUtils.TYPE_NEW_NOTIFICATION;
        if (StringUtils.isNotBlank(gcmKey)) {
            String payload = JsonConverter.createJson(notification);
            GCMUtils.sendNotification(gcmKey, userName, type, payload);
        }
    }

    private void sendDealDoneNotification(Lead lead) {
        List<Integer> userIDs = new ArrayList<Integer>();
        userIDs.add(lead.getCreatedUserID());
        userIDs.add(lead.getBrokerID());
        userIDs.add(lead.getAssignedToUserID());
        Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                .setParameter("userIDs", userIDs);
        List<User> users = userQuery.getResultList();
        Map<Integer, User> usersMap = new HashMap();
        for (User user : users) {
            usersMap.put(user.getUserID(), user);
        }
        Notification createdUserNotification = new Notification();
        createdUserNotification.setFromUserID(lead.getBrokerID());
        String createdUsername = usersMap.get(lead.getBrokerID()).getFullName();
        createdUserNotification.setToUserID(lead.getCreatedUserID());
        createdUserNotification.setLeadID(lead.getLeadID());
        createdUserNotification.setItemName(lead.getItemName());
        createdUserNotification.setMessage("Deal Done");
        createdUserNotification.setType(NotificationType.DEAL_DONE.getNotificationType());
        createdUserNotification.setIsRead(false);
        createdUserNotification.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
        em.persist(createdUserNotification);

        // For Assigned User
        Notification assignedUserNotification = new Notification();
        assignedUserNotification.setFromUserID(lead.getBrokerID());
        assignedUserNotification.setToUserID(lead.getAssignedToUserID());
        assignedUserNotification.setLeadID(lead.getLeadID());
        assignedUserNotification.setItemName(lead.getItemName());
        assignedUserNotification.setMessage("Deal Done");
        assignedUserNotification.setType(NotificationType.DEAL_DONE.getNotificationType());
        assignedUserNotification.setIsRead(false);
        assignedUserNotification.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
        em.persist(assignedUserNotification);

        sendPushNotification(usersMap.get(lead.getCreatedUserID()).getGcmKey(), createdUsername, createdUserNotification);
        sendPushNotification(usersMap.get(lead.getAssignedToUserID()).getGcmKey(), createdUsername, assignedUserNotification);
    }
}
