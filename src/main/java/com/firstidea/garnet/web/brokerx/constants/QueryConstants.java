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

}