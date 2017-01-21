/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service.bean;

import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.filehandling.FileUploadHelper;
import com.firstidea.garnet.web.brokerx.service.BrokerxFileService;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Govind
 */
@Stateless
public class BrokerxFileServiceBean implements BrokerxFileService{
    
    static final Logger logger = LoggerFactory.getLogger(BrokerxFileServiceBean.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public MessageDTO uploadFile(String type, String userID, Map<String, FileItem> fileItemsMap) {
        MessageDTO messageDTO;
        try {
            String fileURL = "";
            for (String fileName : fileItemsMap.keySet()) {
                String uploadPath[] = FileUploadHelper.getPathToUploadFile(fileName, type, userID);
                if (uploadPath != null) {
                    boolean isFileUploaded = FileUploadHelper.UploadImage(fileItemsMap.get(fileName), uploadPath[0]);
                    if (isFileUploaded) {
                        String regex;
                        if (FileUploadHelper.fileSeparator.equals("\\")) {
                            regex = "\\\\";
                        } else {
                            regex = "/";
                        }
                        String path[] = uploadPath[0].split(regex);
                        String uploadedFileName = path[path.length - 1];
                        if (uploadedFileName.toLowerCase().endsWith(".jpg") || uploadedFileName.toLowerCase().endsWith(".jpeg")
                                || uploadedFileName.toLowerCase().endsWith(".png")) {
                            String thumbNailImagePath = uploadPath[0].substring(0, uploadPath[0].lastIndexOf(FileUploadHelper.fileSeparator));
                            FileUploadHelper.createThumbNailForUploadedImage(uploadPath[0], uploadedFileName, thumbNailImagePath, true, null, null);
                        }
                    }
                    fileURL += type+"/"+userID+"/"+uploadPath[1] ;
                }
            }
            messageDTO = MessageDTO.getSuccessDTO();
            
            messageDTO.setData(fileURL);
        } catch (Exception e) {
            logger.error(" ERROR : uploadFile() : TYPE = " + type + " USERID = " + userID + " : ERROR : " + e.toString());
            messageDTO = MessageDTO.getFailureDTO();
        }

        return messageDTO;
    }

}
