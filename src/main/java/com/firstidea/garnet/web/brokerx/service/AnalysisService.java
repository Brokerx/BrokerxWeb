/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author Govind
 */
@Local
public interface AnalysisService {

    public MessageDTO getBrokerTopHighestPayingLeads(Integer brokerID, Date startDate, Date endDate);

    public MessageDTO getBrokerTopHighestPayingUsers(Integer brokerID, String type, Date startDate, Date endDate);

    public MessageDTO getBrokerDesparity(Integer brokerID, Date startDate, Date endDate);

    public MessageDTO getTopBrokers(Integer userID, String type, Date startDate, Date endDate);

}
