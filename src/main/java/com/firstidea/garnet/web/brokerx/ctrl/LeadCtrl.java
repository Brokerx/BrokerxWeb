/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl;

import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 * 
 */
public interface LeadCtrl extends AuthenticationCtrl{
    public String saveLead(String leadJSON);
    public String getLeads(Integer userID, String type, String status, String item, String brokerID,String startDate, String endDate);
    public String getLeadsByBroker(Integer userID, String type, String status,String startDate, String endDate);
    public String getLeadHistory(Integer leadID);
    public String getActiveLeads(Integer userID, String type);
    public String getHistory(Integer userID, String startDate, String endDate);
    public String dealDone(Integer leadID);
    public String getLeadStatusHistory(Integer leadID);
    public String saveLeadStatusHistory(String leadStatusHistoryJSON);
    public String getLeadDocuments(Integer leadID);
    public String uploadDocument(String fileJSON, Map<String, FileItem> fileItemsMap);
}
