/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl.impl;

import com.firstidea.garnet.web.brokerx.auth.Authentication;
import com.firstidea.garnet.web.brokerx.ctrl.AnalysisCtrl;
import com.firstidea.garnet.web.brokerx.service.AnalysisService;
import com.firstidea.garnet.web.brokerx.service.JndiService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Govind
 */
public class AnalysisCtrlImpl extends Authentication implements AnalysisCtrl {

    AnalysisService analysisService = JndiService.getAnalysisService();
    
    @Override
    public String getBrokerTopHighestPayingLeads(Integer brokerID, String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(analysisService.getBrokerTopHighestPayingLeads(brokerID, start, end));
    }

    @Override
    public String getBrokerTopHighestPayingUsers(Integer brokerID, String type, String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(analysisService.getBrokerTopHighestPayingUsers(brokerID, type, start, end));
    }

    @Override
    public String getBrokerDesparity(Integer brokerID, String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(analysisService.getBrokerDesparity(brokerID, start, end));
    }

    @Override
    public String getTopBrokers(Integer userID, String type, String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(analysisService.getTopBrokers(userID, type, start, end));
    }
    
}
