package com.firstidea.garnet.web.brokerx.ctrl;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Govind
 */
public interface AuthenticationCtrl {
    public void setUserRequest(HttpServletRequest request);
    public HttpServletRequest getUserRequest();
}
