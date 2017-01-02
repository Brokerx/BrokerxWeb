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
import com.firstidea.garnet.web.brokerx.service.AnalysisService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
public class AnalysisServiceBean implements AnalysisService {

    static final Logger logger = LoggerFactory.getLogger(AnalysisServiceBean.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public MessageDTO getBrokerTopHighestPayingLeads(Integer brokerID, Date startDate, Date endDate) {
        try {
            String queryString = QueryConstants.GET_BROKER_TOP_HIGHEST_PAYING_LEADS;
            if (startDate != null && endDate != null) {
                queryString = String.format(queryString, " and LastUpdDateTime between :startDate and :endDate ");
            } else {
                queryString = String.format(queryString, "");
            }
            Query query = em.createNativeQuery(queryString);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            query.setParameter("brokerID", brokerID);
            List<Object[]> leadAmounts = query.getResultList();
            List<Integer> leadIDs = new ArrayList<Integer>();
            for (Object leadAmount[] : leadAmounts) {
                leadIDs.add(Integer.valueOf(leadAmount[0].toString()));
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            if (!leadIDs.isEmpty()) {
                List<Lead> leads = getLeadsByIDs(leadIDs);
                leads = populateUsersInLead(leads);
                messageDTO.setData(leads);
            }
            return messageDTO;
        } catch (Exception e) {
            logger.info(AnalysisServiceBean.class + " getBrokerTopHighestPayingLeads() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getBrokerTopHighestPayingUsers(Integer brokerID, String type, Date startDate, Date endDate) {
        try {
            String queryString = QueryConstants.GET_TOP_HIGHEST_PAYING_BUYERS_FOR_BROKER;
            if (type.equals("S")) {
                queryString = QueryConstants.GET_TOP_HIGHEST_PAYING_SELLER_FOR_BROKER;
            }
            if (startDate != null && endDate != null) {
                queryString = String.format(queryString, " and LastUpdDateTime between :startDate and :endDate ");
            } else {
                queryString = String.format(queryString, "");
            }
            Query query = em.createNativeQuery(queryString, Lead.class);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            query.setParameter("brokerID", brokerID);
            List<Lead> leads = query.getResultList();
            leads = populateUsersInLead(leads);
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(leads);
            return messageDTO;
        } catch (Exception e) {
            logger.info(AnalysisServiceBean.class + " getBrokerTopHighestPayingUsers() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getBrokerDesparity(Integer brokerID, Date startDate, Date endDate) {
        try {
            String buyerQueryString = QueryConstants.GET_TOTAL_BUYER_BROKERAGE_BY_BROKERID;

            if (startDate != null && endDate != null) {
                buyerQueryString = String.format(buyerQueryString, " and LastUpdDateTime between :startDate and :endDate ");
            } else {
                buyerQueryString = String.format(buyerQueryString, "");
            }
            Query query = em.createNativeQuery(buyerQueryString);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            query.setParameter("brokerID", brokerID);
            BigDecimal buyerAmount = (BigDecimal) query.getSingleResult();

            String sellerQueryString = QueryConstants.GET_TOTAL_SELLER_BROKERAGE_BY_BROKERID;

            if (startDate != null && endDate != null) {
                sellerQueryString = String.format(sellerQueryString, " and LastUpdDateTime between :startDate and :endDate ");
            } else {
                sellerQueryString = String.format(sellerQueryString, "");
            }
            Query sellerQuery = em.createNativeQuery(sellerQueryString);
            if (startDate != null && endDate != null) {
                sellerQuery.setParameter("startDate", startDate);
                sellerQuery.setParameter("endDate", endDate);
            }
            sellerQuery.setParameter("brokerID", brokerID);
            BigDecimal sellerAmount = (BigDecimal) sellerQuery.getSingleResult();

            String amount = buyerAmount.toString() + "|" + sellerAmount.toString();
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(amount);
            return messageDTO;
        } catch (Exception e) {
            logger.info(AnalysisServiceBean.class + " getBrokerTopHighestPayingUsers() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getTopBrokers(Integer userID, String type, Date startDate, Date endDate) {
        try {
            String queryString = QueryConstants.GET_TOP_BROKERID_USER_ID;
            String type1 = "S";
            if (type.equals("S")) {
                type1 = "B";
            }
            if (startDate != null && endDate != null) {
                queryString = String.format(queryString, " and LastUpdDateTime between :startDate and :endDate ", " and LastUpdDateTime between :startDate and :endDate ");
            } else {
                queryString = String.format(queryString, "", "");
            }
            Query query = em.createNativeQuery(queryString);
            if (startDate != null && endDate != null) {
                query.setParameter("startDate", startDate);
                query.setParameter("endDate", endDate);
            }
            query.setParameter("userID", userID);
            query.setParameter("leadType", type);
            query.setParameter("leadType1", type1);
            List<Object[]> brokersCount = query.getResultList();
            Collections.sort(brokersCount, new Comparator<Object[]>() {

                @Override
                public int compare(Object[] o1, Object[] o2) {
                    BigDecimal amount1 = new BigDecimal(o1[2].toString());
                    BigDecimal amount2 = new BigDecimal(o2[2].toString());
                    return amount2.compareTo(amount1);
                }
            });
            List<Integer> brokerIDs = new ArrayList<Integer>();
            Map<Integer, Integer> brokerCountMap = new HashMap<Integer, Integer>();
            Map<Integer, BigDecimal> brokerAmountMap = new HashMap<Integer, BigDecimal>();
            for (int i = 0; (i < brokersCount.size() && i < 11); i++) {
                Integer brokerID = Integer.valueOf(brokersCount.get(i)[0].toString());
                Integer count = Integer.valueOf(brokersCount.get(i)[1].toString());
                BigDecimal amount = new BigDecimal(brokersCount.get(i)[2].toString());
                brokerIDs.add(brokerID);
                brokerCountMap.put(brokerID, count);
                brokerAmountMap.put(brokerID, amount);
            }
            Query userQuery = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                    .setParameter("userIDs", brokerIDs);
            List<User> brokers = userQuery.getResultList();
            List<User> finalBrokers = new ArrayList<User>();
            for (User broker : brokers) {
                broker.setLeadCount(brokerCountMap.get(broker.getUserID()));
                broker.setLeadAmount(brokerAmountMap.get(broker.getUserID()));
                finalBrokers.add(broker);
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(finalBrokers);
            return messageDTO;
        } catch (Exception e) {
            logger.info(AnalysisServiceBean.class + " getBrokerTopHighestPayingUsers() : ERROR: " + e.toString());
        }

        return MessageDTO.getFailureDTO();
    }

    private List<Lead> getLeadsByIDs(List<Integer> leadIDs) {
        String leadsQueryString = QueryConstants.GET_ALL_LEADS;
        leadsQueryString += " AND l.leadID IN (:leadIDs)";
        Query leadQuery = em.createQuery(leadsQueryString)
                .setParameter("leadIDs", leadIDs);
        List<Lead> leads = leadQuery.getResultList();
        return leads;
    }

    private List<Lead> populateUsersInLead(List<Lead> leads) {
        List<Lead> finalLeads = new ArrayList<Lead>();
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
                finalLeads.add(lead);
            }
        }
        return finalLeads;
    }
}