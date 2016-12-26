/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.entity.LeadStatusHistory;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author Govind
 */
@Local
public interface LeadService {
    public MessageDTO saveLead(Lead lead);
    public MessageDTO getLeads(Integer userID, String type, String status,Date startDate, Date endDate);
    public MessageDTO getLeadsByBroker(Integer userID, String type, String status,Date startDate, Date endDate);
    public MessageDTO getLeadHistory(Integer leadID);
    public MessageDTO getActiveLeads(Integer userID, String type);
    public MessageDTO getHistory(Integer userID, Date startDate, Date endDate);
    public MessageDTO dealDone(Integer leadID);
    public MessageDTO getLeadStatusHistory(Integer leadID);
    public MessageDTO saveLeadStatusHistory(LeadStatusHistory leadStatusHistory);
    public MessageDTO getLeadDocuments(Integer leadID);
}
