/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import javax.ejb.Local;

/**
 *
 * @author Govind
 */
@Local
public interface BrokerxDashboardService {
    public MessageDTO getDashboard();
}
