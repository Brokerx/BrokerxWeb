package com.firstidea.garnet.web.brokerx.ctrl;

import com.firstidea.garnet.web.brokerx.ctrl.impl.AnalysisCtrlImpl;
import com.firstidea.garnet.web.brokerx.ctrl.impl.ChatCtrlImpl;
import com.firstidea.garnet.web.brokerx.ctrl.impl.FileCtrlImpl;
import com.firstidea.garnet.web.brokerx.ctrl.impl.LeadCtrlImpl;
import com.firstidea.garnet.web.brokerx.ctrl.impl.UserCtrlImpl;

/**
 *
 * @author Govind
 */
public interface CtrlCollection {
    
    UserCtrl USER_CTRL = new UserCtrlImpl();
    
    LeadCtrl LEAD_CTRL = new LeadCtrlImpl();
    
    AnalysisCtrl ANALYSIS_CTRL = new AnalysisCtrlImpl();
    
    FileCtrl FILE_CTRL = new FileCtrlImpl();
    
    ChatCtrl CHAT_CTRL = new ChatCtrlImpl();
}
