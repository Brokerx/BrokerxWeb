/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl.impl;

import com.firstidea.garnet.web.brokerx.auth.Authentication;
import com.firstidea.garnet.web.brokerx.constants.MsgConstants;
import com.firstidea.garnet.web.brokerx.ctrl.DashBoardCtrl;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.service.BrokerxDashboardService;
import com.firstidea.garnet.web.brokerx.service.JndiService;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;

/**
 *
 * @author Govind
 */
public class DashBoardCtrlImpl extends Authentication implements DashBoardCtrl {

    @Override
    public String getDashboard() {
        MessageDTO messageDTO = authenticationCheck(getUserRequest());
        if (messageDTO.getMessageID().equals(MsgConstants.VALID_URL_SUCCESS_ID)) {
            BrokerxDashboardService dashboardService = JndiService.getDashboardService();
            messageDTO = dashboardService.getDashboard();
        }
        
        return JsonConverter.createJson(messageDTO);
    }
    
}
