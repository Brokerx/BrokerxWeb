/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service.bean;

import com.firstidea.garnet.web.brokerx.constants.QueryConstants;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.entity.User;
import com.firstidea.garnet.web.brokerx.service.LeadService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.GCMUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Govind
 */
@Stateless
public class LeadServiceBean implements LeadService {
    static final Logger logger = LoggerFactory.getLogger(BrokerxUserServiceBean.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public MessageDTO saveLead(Lead lead) {
        try {
            if (lead.getLeadID() != null) {
                em.merge(lead);
            } else {
                lead.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
                em.persist(lead);
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(lead);
            User broker = em.find(User.class, lead.getBrokerID());
            if(StringUtils.isNotBlank(broker.getGcmKey())) {
                User createdUser = em.find(User.class,lead.getCreatedUserID());
                GCMUtils.sendNotification(broker.getGcmKey(), createdUser.getFullName(), GCMUtils.TYPE_NEW_LEAD_ADDED);
            }
            return messageDTO;
        } catch (Exception e) {
            logger.info(LeadServiceBean.class + " saveLead() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getLeads(Integer userID, String type, String status, Date startDate, Date endDate) {
        try {
            StringBuilder queryString = new StringBuilder(QueryConstants.GET_ALL_LEADS);
            Map<String, Object> queryParams = new HashMap<String, Object>();

            if (userID != null) {
                queryString.append(" AND l.createdUserID= :createdUserID");
                queryParams.put("createdUserID", userID);
            }
            if (type != null) {
                queryString.append(" AND l.type= :type");
                queryParams.put("type", type);
            }
            if (status != null) {
                queryString.append(" AND l.currentStatus= :currentStatus");
                queryParams.put("currentStatus", status);
            }
            if (startDate != null && endDate != null) {
                queryString.append(" AND l.createdDttm BETWEEN :startDate AND :endDate");
                queryParams.put("startDate", startDate);
                queryParams.put("endDate", endDate);
            }
            Query query = em.createQuery(queryString.toString());
            for (String param : queryParams.keySet()) {
                query.setParameter(param, queryParams.get(param));
            }
            List<Lead> leads = query.getResultList();
            if(leads != null && !leads.isEmpty()) {
                for(Lead lead : leads) {
                    User broker = em.find(User.class, lead.getCreatedUserID());
                    lead.setBroker(broker);
                    if(lead.getAssignedToUserID() != null) {
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
            if (status != null) {
                queryString.append(" AND l.currentStatus= :currentStatus");
                queryParams.put("currentStatus", status);
            }
            if (startDate != null && endDate != null) {
                queryString.append(" AND l.createdDttm BETWEEN :startDate AND :endDate");
                queryParams.put("startDate", startDate);
                queryParams.put("endDate", endDate);
            }
            Query query = em.createQuery(queryString.toString());
            for (String param : queryParams.keySet()) {
                query.setParameter(param, queryParams.get(param));
            }
            List<Lead> leads = query.getResultList();
            if(leads != null && !leads.isEmpty()) {
                for(Lead lead : leads) {
                    User createdUser = em.find(User.class, lead.getCreatedUserID());
                    lead.setCreatedUser(createdUser);
                    if(lead.getAssignedToUserID() != null) {
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

}
