package com.firstidea.garnet.web.brokerx.auth;

import com.firstidea.garnet.web.brokerx.admin.ApplicationUser;
import com.firstidea.garnet.web.brokerx.constants.AppConstants;
import com.firstidea.garnet.web.brokerx.constants.MsgConstants;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gurjeet singh
 */
public class Authentication {

    private HttpServletRequest pmbsrequest = null;
    static final Logger logger = LoggerFactory.getLogger(Authentication.class);
    //private ApplicationUser currentUser;

    public final MessageDTO authenticationCheck(HttpServletRequest request) {
        MessageDTO messageDTO = new MessageDTO();
        String key = getAuthenticatedKey(request);
        logger.info("ApplicationUser.............  " + key);
        boolean isValidUser = true;
//        boolean isValidUser = validateUser(key);
        if (isValidUser) {
            messageDTO.setMessageID(MsgConstants.VALID_URL_SUCCESS_ID);
            messageDTO.setMessageText(MsgConstants.VALID_URL_SUCCESS_TEXT);
        } else {
            messageDTO.setMessageID(MsgConstants.VALID_URL_FAILURE_ID);
            messageDTO.setMessageText(MsgConstants.VALID_URL_FAILURE_TEXT);
        }
        return messageDTO;
    }

    public final MessageDTO isAdmin(HttpServletRequest request) {
        MessageDTO messageDTO = new MessageDTO();
        ApplicationUser user = getRequestedUser(request);
        if (user != null && user.getEmail() != null && user.getEmail().equalsIgnoreCase("kulkarnigovind9@gmail.com")) { // admin UserID i.e kulkarnigovind9@gmail.com
            logger.info("ApplicationUser.............  " + user.getName());
            messageDTO.setMessageID(MsgConstants.VALID_URL_SUCCESS_ID);
            messageDTO.setMessageText(MsgConstants.VALID_URL_SUCCESS_TEXT);
        } else {
            messageDTO.setMessageID(MsgConstants.VALID_URL_FAILURE_ID);
            messageDTO.setMessageText(MsgConstants.VALID_URL_FAILURE_TEXT);
        }
        return messageDTO;
    }

    private ApplicationUser getRequestedUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = null;
        if (session.getAttribute(AppConstants.CURRENT_USER) != null) {
            ApplicationUser user = (ApplicationUser) session.getAttribute(AppConstants.CURRENT_USER);
            if (user != null) {
//                setCurrentUser(user);
                if (user.getName() != null) {
                    userName = user.getName();
                    logger.info("userName......00.....  " + userName);
                    return user;
                }
            }
        }
        return null;
    }

    private String getAuthenticatedKey(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = null;
        if (session.getAttribute(AppConstants.CURRENT_USER) != null) {
            ApplicationUser user = (ApplicationUser) session.getAttribute(AppConstants.CURRENT_USER);
            if (user != null) {
//                setCurrentUser(user);
                if (user.getName() != null) {
                    userName = user.getName();
                }
            }
        }
        logger.info("userName......00.....  " + userName);
        return userName;
    }

    public boolean validateUser(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return true;
    }

    private String encode(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
        final Charset charSet = Charset.forName("US-ASCII");
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(charSet.encode("key").array(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
    }

    public void setUserRequest(HttpServletRequest request) {
        pmbsrequest = request;
    }

    public HttpServletRequest getUserRequest() {
        return pmbsrequest;
    }

//    public ApplicationUser getCurrentUser() {
//        return currentUser;
//    }
//
//    private void setCurrentUser(ApplicationUser currentUser) {
//        this.currentUser = currentUser;
////        AdminService adminService = JndiService.getAdminService();
////        adminService.setCurrentUser(currentUser);
//    }
    public String getIPFromRequest(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ipAddress) && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0];
        }
        if (StringUtils.isNotBlank(ipAddress) && ipAddress.length() > 15) {
            ipAddress = ipAddress.substring(0, 14);
        }
        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
