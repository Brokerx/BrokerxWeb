package com.firstidea.garnet.web.brokerx.ctrl;

import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 */
public interface UserCtrl extends AuthenticationCtrl {
    public String registerUser(String userJSON, Map<String, FileItem> fileItemsMap);
    public String appLogin(String userName, String Password);
    public String updateGCMKey(Integer userID, String gcmKey);
    public String getUserByMobile(String mobile);
    public String sendConnectionRequest(Integer fromUserID, Integer toUserID);
    public String changeConnectionStatus(Integer connectionID, String status);
    public String getUserConnections(Integer userID);
    public String updateBrokerDealsInItems(Integer brokerID, String items);
    public String getAnalysisDropDownValues(Integer userID);
    public String getUsers(String userType, String startDate, String endDate);
}
