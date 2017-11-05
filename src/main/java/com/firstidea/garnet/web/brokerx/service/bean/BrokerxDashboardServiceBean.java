/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service.bean;

import com.firstidea.garnet.web.brokerx.constants.QueryConstants;
import com.firstidea.garnet.web.brokerx.dto.DashboardDTO;
import com.firstidea.garnet.web.brokerx.dto.DashboardItem;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.service.BrokerxDashboardService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
public class BrokerxDashboardServiceBean implements BrokerxDashboardService {

    static final Logger logger = LoggerFactory.getLogger(BrokerxDashboardServiceBean.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public MessageDTO getDashboard() {
        MessageDTO messageDTO = null;
        try {
            Date monthStartDate = ApptDateUtils.getCurrentMonthStartDate();
            BigInteger totalUsers = getTotalUsers();
            BigInteger totalBrokers = getTotalBrokers();
            BigInteger totalDeals = getTotalDeals();
//            BigDecimal totalRevenue = getTotalRevenue();
            BigInteger currentMonthtotalUsers = getCurrentMonthUsers(monthStartDate);
            BigInteger currentMonthBrokers = getCurrentMonthBrokers(monthStartDate);
            BigInteger currentMonthtotalDeals = getCurrentMonthTotalDeals(monthStartDate);
//            BigDecimal currentMonthtotalRevenue = getCurrentMonthRevenue(monthStartDate);
            List<DashboardItem> userSummary = getUserSummary();
            List<DashboardItem> dealsSummary = getDealsSummary();
            List<DashboardItem> transactionSummary = getTransactionSummary();
            DashboardDTO dashboardDTO = new DashboardDTO();
            dashboardDTO.setTotalUsers(totalUsers.toString());
            dashboardDTO.setTotalBrokers(totalBrokers.toString());
            dashboardDTO.setTotalNoOfdeals(totalDeals.toString()); 
//            dashboardDTO.setTotalAmount(totalRevenue.toString());
            dashboardDTO.setCurrentMonthUser(currentMonthtotalUsers.toString());
            dashboardDTO.setCurrentMonthBrokers(currentMonthBrokers.toString());
            dashboardDTO.setCurrentMonthDeals(currentMonthtotalDeals.toString());
//            dashboardDTO.setCurrentMonthRevenue(currentMonthtotalRevenue.toString());
            dashboardDTO.setUserSummary(userSummary);
            dashboardDTO.setDealsSummary(dealsSummary);
            dashboardDTO.setTransactionSummary(transactionSummary);
            
            messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(dashboardDTO);

        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getDashboard() : ERROR : " + e.toString());
            messageDTO = MessageDTO.getFailureDTO();
        }

        return messageDTO;
    }

    private BigInteger getTotalUsers() {
        try {
            Query query = em.createNativeQuery(QueryConstants.GET_TOTAL_USER_COUNT);
            BigInteger count = (BigInteger) query.getSingleResult();
            return count != null ? count : BigInteger.ZERO;
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
        }

        return null;
    }

    private BigInteger getCurrentMonthUsers(Date monthStartDate) {
        try {
            Query query = em.createNativeQuery(QueryConstants.GET_CURRENT_MONTH_USER_COUNT)
                    .setParameter("monthStartDate", monthStartDate);
            BigInteger count = (BigInteger) query.getSingleResult();
            return count != null ? count : BigInteger.ZERO;
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
        }

        return null;
    }

    private BigInteger getTotalBrokers() {
        try {
            Query query = em.createNativeQuery(QueryConstants.GET_TOTAL_BROKER_COUNT);
            BigInteger count = (BigInteger) query.getSingleResult();
            return count != null ? count : BigInteger.ZERO;
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
        }

        return null;
    }

    private BigInteger getCurrentMonthBrokers(Date monthStartDate) {
        try {
            Query query = em.createNativeQuery(QueryConstants.GET_CURRENT_MONTH_BROKER_COUNT)
                    .setParameter("monthStartDate", monthStartDate);
            BigInteger count = (BigInteger) query.getSingleResult();
            return count != null ? count : BigInteger.ZERO;
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
        }

        return null;
    }

    private BigInteger getTotalDeals() {
        try {
            Query query = em.createNativeQuery(QueryConstants.GET_TOTAL_DEALS_COUNT);
            BigInteger count = (BigInteger) query.getSingleResult();
            return count != null ? count : BigInteger.ZERO;
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
        }

        return null;
    }

    private BigInteger getCurrentMonthTotalDeals(Date monthStartDate) {
        try {
            Query query = em.createNativeQuery(QueryConstants.GET_CURRENT_MONTH_TOTAL_DEALS_COUNT)
                    .setParameter("monthStartDate", monthStartDate);
            BigInteger count = (BigInteger) query.getSingleResult();
            return count != null ? count : BigInteger.ZERO;
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
        }
        return null;
    }

//    private BigDecimal getTotalRevenue() {
//        try {
//            Query query = em.createNativeQuery(QueryConstants.GET_TOTAL_REVENUE);
//            BigDecimal count = (BigDecimal) query.getSingleResult();
//            return count != null ? count : BigDecimal.ZERO;
//        } catch (Exception e) {
//            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
//        }
//
//        return null;
//    }
//
//    private BigDecimal getCurrentMonthRevenue(Date monthStartDate) {
//        try {
//            Query query = em.createNativeQuery(QueryConstants.GET_CURRENT_MONTH_TOTAL_REVENUE)
//                    .setParameter("monthStartDate", monthStartDate);
//            BigDecimal count = (BigDecimal) query.getSingleResult();
//            return count != null ? count : BigDecimal.ZERO;
//        } catch (Exception e) {
//            logger.error(BrokerxDashboardServiceBean.class + " getTotalUsers() : ERROR : " + e.toString());
//        }
//        return null;
//    }

    private List<DashboardItem> getUserSummary() {
        List<DashboardItem> userSummary = new ArrayList<DashboardItem>();
        try {
            Query newUserQuery = em.createNativeQuery(QueryConstants.GET_NEW_USER_SUMMARY);
            List<Object[]> newUsers = newUserQuery.getResultList();
            Map<Date, DashboardItem> dateItemMap = new HashMap<Date, DashboardItem>();
            for (Object[] summary : newUsers) {
                Date date = ApptDateUtils.getDateFromYearmonth(summary[1].toString());
                DashboardItem dashboardItem = new DashboardItem();
                dashboardItem.setData1(summary[0].toString());
                dateItemMap.put(date, dashboardItem);
            }
            Query brokerQuery = em.createNativeQuery(QueryConstants.GET_NEW_BROKER_SUMMARY);
            List<Object[]> brokerUsers = brokerQuery.getResultList();
            for (Object[] summary : brokerUsers) {
                Date date = ApptDateUtils.getDateFromYearmonth(summary[1].toString());
                DashboardItem dashboardItem = new DashboardItem();
                if (dateItemMap.containsKey(date)) {
                    dashboardItem = dateItemMap.get(date);
                }
                dashboardItem.setData2(summary[0].toString());
                dateItemMap.put(date, dashboardItem);
            }
            TreeMap<Date, DashboardItem> descendingMap = new TreeMap<Date, DashboardItem>(dateItemMap);
            for (Date summeryDate : descendingMap.descendingKeySet()) {
                if (userSummary.size() >= 6) {
                    break;
                }
                DashboardItem dashboardItem = descendingMap.get(summeryDate);
                String month = ApptDateUtils.getMonthFromDate(summeryDate);
                dashboardItem.setMonth(month);
                userSummary.add(dashboardItem);
            }
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getUserSummary() : ERROR : " + e.toString());
        }
        return userSummary;
    }
    
    private List<DashboardItem> getDealsSummary() {
        List<DashboardItem> adsSummary = new ArrayList<DashboardItem>();
        try {
            Query data1Query = em.createNativeQuery(QueryConstants.GET_ACTIVE_DEALS_SUMMARY);
            List<Object[]> data1Result = data1Query.getResultList();
            Map<Date, DashboardItem> dateItemMap = new HashMap<Date, DashboardItem>();
            for (Object[] summary : data1Result) {
                Date date = ApptDateUtils.getDateFromYearmonth(summary[1].toString());
                DashboardItem dashboardItem = new DashboardItem();
                dashboardItem.setData1(summary[0].toString());
                dateItemMap.put(date, dashboardItem);
            }
            Query data2Query = em.createNativeQuery(QueryConstants.GET_DEALS_DONE_SUMMARY);
            List<Object[]> data2Result = data2Query.getResultList();
            for (Object[] summary : data2Result) {
                Date date = ApptDateUtils.getDateFromYearmonth(summary[1].toString());
                DashboardItem dashboardItem = new DashboardItem();
                if (dateItemMap.containsKey(date)) {
                    dashboardItem = dateItemMap.get(date);
                }
                dashboardItem.setData2(summary[0].toString());
                dateItemMap.put(date, dashboardItem);
            }
            TreeMap<Date, DashboardItem> descendingMap = new TreeMap<Date, DashboardItem>(dateItemMap);
            for (Date summeryDate : descendingMap.descendingKeySet()) {
                if (adsSummary.size() >= 6) {
                    break;
                }
                DashboardItem dashboardItem = descendingMap.get(summeryDate);
                String month = ApptDateUtils.getMonthFromDate(summeryDate);
                dashboardItem.setMonth(month);
                adsSummary.add(dashboardItem);
            }
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getAdsSummary() : ERROR : " + e.toString());
        }
        return adsSummary;
    }
    
    private List<DashboardItem> getTransactionSummary() {
        List<DashboardItem> walletSummary = new ArrayList<DashboardItem>();
        try {
            Query data1Query = em.createNativeQuery(QueryConstants.GET_BUYER_AMOUNT_SUMMARY);
            List<Object[]> data1Result = data1Query.getResultList();
            Map<Date, DashboardItem> dateItemMap = new HashMap<Date, DashboardItem>();
            for (Object[] summary : data1Result) {
                Date date = ApptDateUtils.getDateFromYearmonth(summary[1].toString());
                DashboardItem dashboardItem = new DashboardItem();
                dashboardItem.setData1(summary[0].toString());
                dateItemMap.put(date, dashboardItem);
            }
            Query data2Query = em.createNativeQuery(QueryConstants.GET_SELLER_AMOUNT_SUMMARY);
            List<Object[]> data2Result = data2Query.getResultList();
            for (Object[] summary : data2Result) {
                Date date = ApptDateUtils.getDateFromYearmonth(summary[1].toString());
                DashboardItem dashboardItem = new DashboardItem();
                if (dateItemMap.containsKey(date)) {
                    dashboardItem = dateItemMap.get(date);
                }
                dashboardItem.setData2(summary[0].toString());
                dateItemMap.put(date, dashboardItem);
            }
            Query data3Query = em.createNativeQuery(QueryConstants.GET_BROKER_AMOUNT_SUMMARY);
            List<Object[]> data3Result = data3Query.getResultList();
            for (Object[] summary : data3Result) {
                Date date = ApptDateUtils.getDateFromYearmonth(summary[1].toString());
                DashboardItem dashboardItem = new DashboardItem();
                if (dateItemMap.containsKey(date)) {
                    dashboardItem = dateItemMap.get(date);
                }
                dashboardItem.setData3(summary[0].toString());
                dateItemMap.put(date, dashboardItem);
            }
            TreeMap<Date, DashboardItem> descendingMap = new TreeMap<Date, DashboardItem>(dateItemMap);
            for (Date summeryDate : descendingMap.descendingKeySet()) {
                if (walletSummary.size() >= 6) {
                    break;
                }
                DashboardItem dashboardItem = descendingMap.get(summeryDate);
                String month = ApptDateUtils.getMonthFromDate(summeryDate);
                dashboardItem.setMonth(month);
                walletSummary.add(dashboardItem);
            }
        } catch (Exception e) {
            logger.error(BrokerxDashboardServiceBean.class + " getAdsSummary() : ERROR : " + e.toString());
        }
        return walletSummary;
    }
}
