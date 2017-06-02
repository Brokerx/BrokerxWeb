package com.firstidea.garnet.web.brokerx.ctrl.impl;

import com.firstidea.garnet.web.brokerx.admin.ApplicationUser;
import com.firstidea.garnet.web.brokerx.auth.Authentication;
import com.firstidea.garnet.web.brokerx.constants.AppConstants;
import com.firstidea.garnet.web.brokerx.constants.MsgConstants;
import com.firstidea.garnet.web.brokerx.ctrl.UserCtrl;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.entity.User;
import com.firstidea.garnet.web.brokerx.service.JndiService;
import com.firstidea.garnet.web.brokerx.service.BrokerxUserService;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 */
public class UserCtrlImpl extends Authentication implements UserCtrl {

    BrokerxUserService userService = JndiService.getUserService();

    @Override
    public String registerUser(String userJSON, Map<String, FileItem> fileItemsMap) {
        User userDTO = (User) JsonConverter.fromJson(userJSON, User.class);
        MessageDTO registerResponseDTO = userService.registerUser(userDTO, fileItemsMap);
        if (registerResponseDTO.getMessageID().equals(MsgConstants.SUCCESS_ID)) {
            HttpSession session = getUserRequest().getSession();
            ApplicationUser user = userService.getCurrentUser();
            session.setAttribute(AppConstants.CURRENT_USER, user);
            session.setMaxInactiveInterval(4 * 60 * 60);
        }
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String appLogin(String userName, String Password) {
        MessageDTO registerResponseDTO = userService.appLogin(userName, Password);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String updateGCMKey(Integer userID, String gcmKey) {
        MessageDTO registerResponseDTO = userService.updateGCMKey(userID, gcmKey);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String getUserByMobile(String mobile) {
        MessageDTO registerResponseDTO = userService.getUserByMobile(mobile);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String sendConnectionRequest(Integer fromUserID, Integer toUserID) {
        MessageDTO registerResponseDTO = userService.sendConnectionRequest(fromUserID, toUserID);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String changeConnectionStatus(Integer connectionID, String status) {
        MessageDTO registerResponseDTO = userService.changeConnectionStatus(connectionID, status);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String getUserConnections(Integer userID) {
        MessageDTO registerResponseDTO = userService.getUserConnections(userID);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String updateBrokerDealsInItems(Integer brokerID, String items) {
        MessageDTO registerResponseDTO = userService.updateBrokerDealsInItems(brokerID, items);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String getAnalysisDropDownValues(Integer userID) {
        MessageDTO registerResponseDTO = userService.getAnalysisDropDownValues(userID);
        return JsonConverter.createJson(registerResponseDTO);
    }

    @Override
    public String getUsers(String userType, String startDate, String endDate) {
        MessageDTO userResponseDTO = userService.getUsers(userType, null, null);
        return JsonConverter.createJson(userResponseDTO);
    }

}
