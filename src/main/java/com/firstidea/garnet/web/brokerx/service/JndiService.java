/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Govind
 */
public abstract class JndiService {

    private static Context getInitialContext() {
        Context ctx = null;
        try {
            ctx = new InitialContext();//(propertis);
        } catch (NamingException ex) {
            Logger.getLogger(JndiService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ctx;
    }

    public final static BrokerxUserService getUserService() {
        BrokerxUserService loginService = null;
        try {
            Context context = getInitialContext();
            loginService = (BrokerxUserService) context.lookup("java:global/Brokerx/BrokerxUserServiceBean");
        } catch (NamingException ex) {
            Logger.getLogger(BrokerxUserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loginService;
    }


    public final static LeadService getLeadService() {
        LeadService service = null;
        try {
            Context context = getInitialContext();
            service = (LeadService) context.lookup("java:global/Brokerx/LeadServiceBean");
        } catch (NamingException ex) {
            Logger.getLogger(BrokerxUserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return service;
    }

    public final static AnalysisService getAnalysisService() {
        AnalysisService service = null;
        try {
            Context context = getInitialContext();
            service = (AnalysisService) context.lookup("java:global/Brokerx/AnalysisServiceBean");
        } catch (NamingException ex) {
            Logger.getLogger(AnalysisService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return service;
    }

     public final static BrokerxFileService getFileService() {
         BrokerxFileService service = null;
        try {
            Context context = getInitialContext();
            service = (BrokerxFileService) context.lookup("java:global/Brokerx/BrokerxFileServiceBean");
        } catch (NamingException ex) {
            Logger.getLogger(BrokerxFileService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return service;
     }

     public final static ChatService getChatService() {
         ChatService service = null;
        try {
            Context context = getInitialContext();
            service = (ChatService) context.lookup("java:global/Brokerx/ChatServiceBean");
        } catch (NamingException ex) {
            Logger.getLogger(ChatService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return service;
     }
}
