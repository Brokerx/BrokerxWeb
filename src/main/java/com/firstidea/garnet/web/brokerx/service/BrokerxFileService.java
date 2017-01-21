/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import java.util.Map;
import javax.ejb.Local;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 */
@Local
public interface BrokerxFileService {
    public MessageDTO uploadFile(String type, String userID, Map<String, FileItem> fileItemsMap);
}
