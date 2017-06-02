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

    String GET_USER_COUNT_BY_MOBILE = "SELECT count(*) From User "
            + " WHERE Mobile=:mobile";

    String GET_USER_COUNT_BY_EMAIL = "SELECT count(*) FROM User  "
            + " WHERE Email=:email";

    String GET_USER_CHATS = "SELECT uc FROM Chat uc "
            + " WHERE uc.fromUserID IN (:fromUserID) "
            + " AND uc.toUserID IN (:toUserID) "
            + " AND uc.leadID = :leadID"
            + " ORDER BY uc.createdDttm DESC";

    String GET_NOTIFICATIONS_BY_USERID = "SELECT n from Notification n WHERE n.toUserID=:userID"
            + " ORDER BY n.createdDttm DESC";

    String UPDATE_NOTIFICATION_READ_FLAG_BY_USERID = "UPDATE Notification SET IsRead=true WHERE ToUserID=:userID"
            + " AND IsRead=false";

    String UPDATE_LEAD_ID_IN_CHAT_ON_DEAL_DONE = "Update Chat SET LeadID=:newLeadID WHERE LeadID=:oldLeadID";

    String UPDATE_LEAD_ID_IN_CHAT_SUMMARY_ON_DEAL_DONE = "Update ChatSummary SET LeadID=:newLeadID WHERE LeadID=:oldLeadID";

    String UPDATE_CHAT_SUMMARY_BY_USERS_N_LEAD = "Update ChatSummary SET LastMsg=:msg, LastMsgType=:msgType, LastMsgDateTime=now()"
            + " WHERE LeadID=:leadID AND (FromUserID=:fromUserID and ToUserID=:toUserID) OR (FromUserID=:toUserID  and ToUserID=:fromUserID)";

    String GET_UNREAD_NOTIFICATION_COUNT_BY_USERID = "SELECT count(*) from Notification n WHERE ToUserID=:userID"
            + " AND IsRead=false";

    String GET_CHAT_SUMMARY_BY_USERS_ID = "SELECT cs FROM ChatSummary cs "
            + " WHERE cs.fromUserID=:userID OR cs.toUserID=:userID"
            + " ORDER BY cs.lastMsgDateTime DESC";

    String GET_DISTINCT_ITEMS_USER_DEALS_WITH = "SELECT distinct(ItemName) FROM Lead where (BrokerID=:userID OR CreatedUserID=:userID OR AssignedToUserID=:userID) AND BuyerStatus='D';";

    String GET_SELLERS_BY_USERID = "Select distinct(CreatedUserID) from Lead where `Type`='S' and AssignedToUserID=:userID AND BuyerStatus='D'\n"
            + " UNION ALL "
            + "Select distinct(AssignedToUserID) from Lead where `Type`='B' and CreatedUserID=:userID AND BuyerStatus='D'";

    String GET_BUYER_BY_USERID = "Select distinct(AssignedToUserID) from Lead where `Type`='S' and CreatedUserID=:userID AND BuyerStatus='D' "
            + " UNION ALL "
            + " Select distinct(CreatedUserID) from Lead where `Type`='B' and AssignedToUserID=:userID AND BuyerStatus='D'";

    String GET_SELLERS_BY_BROKERID = "Select distinct(CreatedUserID) from Lead where `Type`='S' and BrokerID=:userID AND BuyerStatus='D' "
            + " UNION ALL "
            + " SELECT distinct(AssignedToUserID) from Lead where `Type`='B' and BrokerID=:userID AND BuyerStatus='D'";

    String GET_BUYER_BY_BROKERID = "Select distinct(CreatedUserID) from Lead where `Type`='B' and BrokerID=:userID AND BuyerStatus='D'\n"
            + " UNION ALL "
            + " Select distinct(AssignedToUserID) from Lead where `Type`='S' and BrokerID=:userID AND BuyerStatus='D'";

    String GET_USERS_BY_TYPE = "SELECT u FROM User u WHERE u.isBroker=:isBroker";

    String GET_USERS_BUYER_DEAL_STATUS_COUNT = "SELECT CreatedUserID,BuyerStatus,count(BuyerStatus) from Lead "
            + " WHERE Type='B' and CreatedUserID in (:userIDs) AND BuyerStatus <> 'L'"
            + " GROUP BY CreatedUserID,BuyerStatus "
            + " UNION All "
            + " SELECT AssignedToUserID,BuyerStatus,count(BuyerStatus) from Lead"
            + " WHERE Type='S' and AssignedToUserID in (:userIDs) AND BuyerStatus <> 'L'"
            + " GROUP by AssignedToUserID,BuyerStatus ";

    String GET_USERS_SELLER_DEAL_STATUS_COUNT = "SELECT CreatedUserID,SellerStatus,count(SellerStatus) from Lead "
            + " WHERE Type='S' and CreatedUserID in (:userIDs) AND SellerStatus <> 'L' "
            + " GROUP BY CreatedUserID,SellerStatus "
            + " UNION ALL "
            + " SELECT AssignedToUserID,SellerStatus,count(SellerStatus) from Lead "
            + " WHERE Type='B' and AssignedToUserID in(:userIDs) AND SellerStatus <> 'L' "
            + " GROUP BY AssignedToUserID,SellerStatus";
    
    String GET_BROKERS_BUYER_DEAL_STATUS_COUNT = "SELECT BrokerID,BrokerStatus,count(BrokerStatus) from Lead "
            + " WHERE Type='B' and BrokerID in (:userIDs) AND BuyerStatus <> 'L'"
            + " GROUP BY BrokerID,BrokerStatus ";

    String GET_BROKERS_SELLER_DEAL_STATUS_COUNT = "SELECT BrokerID,BrokerStatus,count(BrokerStatus) from Lead "
            + " WHERE Type='S' and BrokerID in (:userIDs) AND BuyerStatus <> 'L'"
            + " GROUP BY BrokerID,BrokerStatus ";

}
