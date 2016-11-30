package com.firstidea.garnet.web.brokerx.ctrl;

import com.firstidea.garnet.web.brokerx.ctrl.impl.LeadCtrlImpl;
import com.firstidea.garnet.web.brokerx.ctrl.impl.UserCtrlImpl;

/**
 *
 * @author Govind
 */
public interface CtrlCollection {
    
    
    UserCtrl USER_CTRL = new UserCtrlImpl();
    
    LeadCtrl LEAD_CTRL = new LeadCtrlImpl();
}
