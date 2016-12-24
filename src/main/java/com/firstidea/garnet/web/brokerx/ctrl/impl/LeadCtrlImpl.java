/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl.impl;

import com.firstidea.garnet.web.brokerx.auth.Authentication;
import com.firstidea.garnet.web.brokerx.ctrl.LeadCtrl;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.service.JndiService;
import com.firstidea.garnet.web.brokerx.service.LeadService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Govind
 */
public class LeadCtrlImpl extends Authentication implements LeadCtrl {

    LeadService leadService = JndiService.getLeadService();

    @Override
    public String saveLead(String leadJSON) {
        Lead l = (Lead) JsonConverter.fromJson(leadJSON, Lead.class);
        return JsonConverter.createJson(leadService.saveLead(l));
    }

    @Override
    public String getLeads(Integer userID, String type, String status, String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(leadService.getLeads(userID, type, status, start, end));
    }

    @Override
    public String getLeadsByBroker(Integer userID, String type, String status, String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(leadService.getLeadsByBroker(userID, type, status, start, end));
    }

    @Override
    public String getLeadHistory(Integer leadID) {
        return JsonConverter.createJson(leadService.getLeadHistory(leadID));
    }

    @Override
    public String getActiveLeads(Integer userID, String type) {
        return JsonConverter.createJson(leadService.getActiveLeads(userID, type));
    }

    @Override
    public String getHistory(Integer userID, String startDateString, String endDateString) {
        Date startDate = null, endDate = null;
        if (startDateString != null && endDateString != null) {
            startDate = ApptDateUtils.getFormatedDate(startDateString);
            endDate = ApptDateUtils.getFormatedDate(endDateString);
        }
        return JsonConverter.createJson(leadService.getHistory(userID, startDate, endDate));
    }
    
    @Override
    public String dealDone(Integer leadID) {
        return JsonConverter.createJson(leadService.dealDone(leadID));
    }
}
