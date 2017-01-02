/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl;

/**
 *
 * @author Govind
 */
public interface AnalysisCtrl extends AuthenticationCtrl{
    
    public String getBrokerTopHighestPayingLeads(Integer brokerID, String startDate, String endDate);

    public String getBrokerTopHighestPayingUsers(Integer brokerID, String type, String startDate, String endDate);

    public String getBrokerDesparity(Integer brokerID, String startDate, String endDate);

    public String getTopBrokers(Integer userID, String type, String startDate, String endDate);

}
