/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl.impl;

import com.firstidea.garnet.web.brokerx.auth.Authentication;
import com.firstidea.garnet.web.brokerx.ctrl.LeadCtrl;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.entity.LeadDocument;
import com.firstidea.garnet.web.brokerx.entity.LeadStatusHistory;
import com.firstidea.garnet.web.brokerx.service.JndiService;
import com.firstidea.garnet.web.brokerx.service.LeadService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.Date;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;
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
    public String getLeads(Integer leadID, Integer userID, Integer otherUserID, String type, String status, String item, String brokerID,String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(leadService.getLeads(leadID, userID, otherUserID, type, status, item, brokerID, start, end));
    }

    @Override
    public String getAnalysisLeads(Integer leadID, Integer userID, Integer otherUserID, String type, String status, String item, String brokerID,String startDate, String endDate) {
        Date start = null, end = null;
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            start = ApptDateUtils.getFormatedDate(startDate);
            end = ApptDateUtils.getFormatedDate(endDate);
        }
        return JsonConverter.createJson(leadService.getAnalysisLeads(leadID, userID, otherUserID, type, status, item, brokerID, start, end));
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
    
    @Override
    public String getLeadStatusHistory(Integer leadID) {
        return JsonConverter.createJson(leadService.getLeadStatusHistory(leadID));
    }
    
    @Override
    public String saveLeadStatusHistory(String leadStatusHistoryJSON){
        LeadStatusHistory leadStatusHistory = (LeadStatusHistory) JsonConverter.parseJsonWithDateFormat(leadStatusHistoryJSON, LeadStatusHistory.class, "MMM dd, yyyy hh:mm:ss a");
        return JsonConverter.createJson(leadService.saveLeadStatusHistory(leadStatusHistory));
    }
    
    @Override
    public String getLeadDocuments(Integer leadID){
        return JsonConverter.createJson(leadService.getLeadDocuments(leadID));
    }

    @Override
   public String uploadDocument(String fileJSON, Map<String, FileItem> fileItemsMap){
        LeadDocument leadDocument = (LeadDocument) JsonConverter.fromJson(fileJSON, LeadDocument.class);
        return JsonConverter.createJson(leadService.uploadDocument(leadDocument, fileItemsMap));
        
    }
   
    @Override
   public String getDashboardLeads(Integer userID, String status, boolean isBroker){
        return JsonConverter.createJson(leadService.getDashboardLeads(userID, status, isBroker));
        
    }
}
