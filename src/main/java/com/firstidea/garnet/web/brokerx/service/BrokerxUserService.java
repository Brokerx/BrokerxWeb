/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import com.firstidea.garnet.web.brokerx.admin.ApplicationUser;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.User;
import java.util.Date;
import java.util.Map;
import javax.ejb.Local;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 */
@Local
public interface BrokerxUserService {
    public ApplicationUser getCurrentUser();
    public MessageDTO registerUser(User userDTO, Map<String, FileItem> fileItemsMap);
    public MessageDTO appLogin(String userName, String Password);
    public MessageDTO updateGCMKey(Integer userID, String gcmKey);
    public MessageDTO getUserByMobile(String mobile);
    public MessageDTO sendConnectionRequest(Integer fromUserID, Integer toUserID);
    public MessageDTO changeConnectionStatus(Integer connectionID, String status);
    public MessageDTO getUserConnections(Integer userID);
    public MessageDTO updateBrokerDealsInItems(Integer brokerID, String items);
    
    //for admin panel
    public MessageDTO getUsers(String userType, Date startDate, Date endDate);
    public MessageDTO getAnalysisDropDownValues(Integer userID);
}
