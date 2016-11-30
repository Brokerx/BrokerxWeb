package com.firstidea.garnet.web.brokerx.dto;

import com.firstidea.garnet.web.brokerx.constants.MsgConstants;

/**
 *
 * @author Govind
 */
public class MessageDTO {
    private String messageID;
    private String messageText;
    private Object data;

    
    public static MessageDTO getSuccessDTO() {
        MessageDTO garnetResponseDTO = new MessageDTO();
        garnetResponseDTO.setMessageID(MsgConstants.SUCCESS_ID);
        garnetResponseDTO.setMessageText(MsgConstants.SUCCESS_MESSAGE);
        
        return garnetResponseDTO;
    }
    
    public static MessageDTO getExistingUserDTO() {
        MessageDTO garnetResponseDTO = new MessageDTO();
        garnetResponseDTO.setMessageID(MsgConstants.EXISTING_USER_ID);
        garnetResponseDTO.setMessageText(MsgConstants.EXISTING_USER_TEXT);
        
        return garnetResponseDTO;
    }
    
    public static MessageDTO getFailureDTO() {
        MessageDTO garnetResponseDTO = new MessageDTO();
        garnetResponseDTO.setMessageID(MsgConstants.FAILURE_ID);
        garnetResponseDTO.setMessageText(MsgConstants.FAILURE_MESSAGE);
        
        return garnetResponseDTO;
    }
    
    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    
    
}
