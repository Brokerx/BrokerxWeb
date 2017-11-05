/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.entity.LeadDocument;
import com.firstidea.garnet.web.brokerx.entity.LeadStatusHistory;
import java.util.Date;
import java.util.Map;
import javax.ejb.Local;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 */
@Local
public interface LeadService {
    public MessageDTO saveLead(Lead lead);
    public MessageDTO getLeads(Integer leadID, Integer userID, Integer otherUserID, String type, String status, String item, String brokerID,Date startDate, Date endDate);
    public MessageDTO getAnalysisLeads(Integer leadID, Integer userID, Integer otherUserID, String type, String status, String item, String brokerIDString, Date startDate, Date endDate);
    public MessageDTO getLeadsByBroker(Integer userID, String type, String status,Date startDate, Date endDate);
    public MessageDTO getLeadHistory(Integer leadID);
    public MessageDTO getActiveLeads(Integer userID, String type);
    public MessageDTO getHistory(Integer userID, Date startDate, Date endDate);
    public MessageDTO dealDone(Integer leadID);
    public MessageDTO getLeadStatusHistory(Integer leadID);
    public MessageDTO saveLeadStatusHistory(LeadStatusHistory leadStatusHistory);
    public MessageDTO getLeadDocuments(Integer leadID);
    public MessageDTO uploadDocument(LeadDocument leadDocument, Map<String, FileItem> fileItemsMap);
    public MessageDTO getDashboardLeads(Integer userID, String status, boolean isBroker);
}
