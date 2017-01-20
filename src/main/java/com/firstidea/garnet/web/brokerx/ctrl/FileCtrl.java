/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.ctrl;

import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Govind
 */
public interface FileCtrl extends AuthenticationCtrl {
    public String uploadFile(String type,String userID, Map<String, FileItem> fileItemsMap);
}
