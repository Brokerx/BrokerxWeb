/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl.impl;


import com.firstidea.garnet.web.brokerx.auth.Authentication;
import com.firstidea.garnet.web.brokerx.constants.MsgConstants;
import com.firstidea.garnet.web.brokerx.ctrl.FileCtrl;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.service.FileService;
import com.firstidea.garnet.web.brokerx.service.JndiService;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 */
public class FileCtrlImpl extends Authentication implements FileCtrl{

    @Override
    public String uploadFile(String type, String userID, Map<String, FileItem> fileItemsMap) {
        MessageDTO messageDTO = authenticationCheck(getUserRequest());
        if(messageDTO.getMessageID().equals(MsgConstants.VALID_URL_SUCCESS_ID)) {
            FileService fileService = JndiService.getFileService();
            messageDTO = fileService.uploadFile(type, userID, fileItemsMap);
        }
        
        return JsonConverter.createJson(messageDTO);
    }
    
}
