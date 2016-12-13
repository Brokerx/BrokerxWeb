/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.util;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Govind
 */
public class GCMUtils {
    static final Logger logger = LoggerFactory.getLogger(GCMUtils.class);
    
    public static String TYPE_CONNECTION_REQUEST = "ConnectionRequest";
    public static String TYPE_CONNECTION_REQUEST_ACCEPTED = "ConnectionRequestAccepted";
    public static String TYPE_CONNECTION_REQUEST_REJECTED = "ConnectionRequestRejected";
    public static String TYPE_NEW_LEAD_ADDED = "NewLeadAdded";
    public static String TYPE_LEAD_REVERTED = "LeadReverted";
    
    public static void sendNotification(String regID, String data, String type){
        try {
            logger.info(GCMUtils.class
                    + " : sendNotification() : Sending New notification : TYPE = " + type
                    + "  : FOR Reg Key : " + regID);
            String serverKey = "AIzaSyCVXSzPy3_forGg89qf_fendr9cSHM8AI4"; // created from mobilehealthcaresystem@gmail.com firebase
            Sender sender = new Sender(serverKey); 
            Message message = new Message.Builder().addData("type", type)
                    .addData("data", data).addData("SentDttm", ApptDateUtils.getFormattedDateString(new Date())).build();
            Result result = sender.send(message, regID, 5);
            if (result != null && result.getMessageId() != null && !result.getMessageId().isEmpty()) {
                //TODO Handle success result
                logger.info(GCMUtils.class
                        + " : sendNotification() : Sending New notification : TYPE = " + type
                        + "  : REG Key: " + regID + " SUCCESS MSGID = " + result.getMessageId());
                System.out.println(" : sendNotification() : Sending New notification : TYPE = " + type
                        + "  : REG Key: " + regID + " SUCCESS MSGID = " + result.getMessageId());
            } else {
                logger.info(GCMUtils.class
                        + " : sendNotification() : Got Empty Rsult/MessageID From sender.send()"
                        + "  : FOR Reg Key : " + regID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(GCMUtils.class + " ERROR IN sendNotification() : " + e.getMessage() + e.toString());
            //throw new DeviceNotificationException(e.toString());
        }

    }
}
