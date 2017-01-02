/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.constants;

/**
 *
 * @author Govind
 */
public interface QueryConstants {

    String GET_ALL_LEADS = "SELECT l from Lead l"
            + " WHERE 1=1";

    String GET_LOGIN_USER = "Select u FROM User u"
            + " WHERE u.email=:userID"
            + " AND u.password=:password";

    String GET_USERS_BY_USER_IDS = "Select u FROM User u"
            + " WHERE u.userID IN (:userIDs)";

    String GET_USERS_BY_MOBILE = "SELECT u FROM User u"
            + " WHERE u.mobile = :mobile";

    String GET_USER_CONNECTION = "SELECT uc FROM UserConnection uc"
            + " WHERE uc.fromUserID = :fromUserID"
            + " AND uc.toUserID=:toUserID";

    String GET_USERS_ALL_CONNECTIONS = "SELECT uc FROM UserConnection uc"
            + " WHERE uc.fromUserID = :userID"
            + " OR uc.toUserID=:userID";

    String GET_LEAD_DOCUMENTS_BY_LEADID = "SELECT ld FROM LeadDocument ld"
            + " WHERE ld.leadID=:leadID";

    String GET_BROKER_TOP_HIGHEST_PAYING_LEADS = "Select LeadID,(BuyerBrokerage+SellerBrokerage) as Brokerage from Lead "
            + " WHERE BrokerStatus='D' AND BrokerID= :brokerID %s"
            + " order by Brokerage DESC limit 11";

    String GET_TOP_HIGHEST_PAYING_BUYERS_FOR_BROKER = "Select * from Lead "
            + " WHERE BrokerStatus='D' AND BrokerID=:brokerID "
            + " %s "
            + " order by BuyerBrokerage DESC limit 11";

    String GET_TOP_HIGHEST_PAYING_SELLER_FOR_BROKER = "Select * from Lead "
            + " WHERE BrokerStatus='D' AND BrokerID=:brokerID "
            + " %s "
            + " order by SellerBrokerage DESC limit 11";

    String GET_TOTAL_BUYER_BROKERAGE_BY_BROKERID = "Select sum(BuyerBrokerage) from Lead "
            + " WHERE BrokerID=:brokerID and BrokerStatus='D' "
            + " %s ";

    String GET_TOTAL_SELLER_BROKERAGE_BY_BROKERID = "Select sum(SellerBrokerage) from Lead "
            + " WHERE BrokerID=:brokerID and BrokerStatus='D' "
            + " %s ";

    String GET_TOP_BROKERID_USER_ID = "(Select BrokerID,count(BrokerID) as NoOfLeads ,sum(BasicPrice) as TotalAmount from Lead "
            + " WHERE BrokerStatus='D' AND CreatedUserID=:userID "
            + " %s "
            + " and Type=:leadType group by BrokerID  order by TotalAmount desc,NoOfLeads desc  limit 11)"
            + " UNION ALL "
            + " (Select BrokerID,count(BrokerID) as NoOfLeads,sum(BasicPrice) as TotalAmount from Lead "
            + " WHERE BrokerStatus='D' AND AssignedToUserID=:userID "
            + " %s "
            + " and Type=:leadType1 group by BrokerID order by TotalAmount desc,NoOfLeads desc  limit 11)";

}
