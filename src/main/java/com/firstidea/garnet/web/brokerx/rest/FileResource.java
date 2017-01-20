/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.ctrl.CtrlCollection;
import com.firstidea.garnet.web.brokerx.ctrl.FileCtrl;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * REST Web Service
 *
 * @author Govind
 */
@Path("FileUpload")
public class FileResource extends AppResource{

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of FileResource
     */
    public FileResource() {
    }

    @POST
    @Path("/uploadFile")
    @Produces("application/json")
    public String uploadFile() {
//        System.out.println("In file upload");
        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constraints
            factory.setSizeThreshold(10000);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> multiparts = upload.parseRequest(request);
            FileItem filePart = null;
            String fileType = null, title = null;
            Map<String, FileItem> fileItemsMap = new HashMap<String, FileItem>();
            Map<String, String> uploadForm = new HashMap<String, String>();

            for (FileItem item : multiparts) {
                if (!item.isFormField()) {
                    //your operations on file
                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    if (fileName != null && fileName.length() > 0) {
                        String contentType = item.getContentType();
                        boolean isInMemory = item.isInMemory();
                        long sizeInBytes = item.getSize();
                        fileItemsMap.put(fileName, item);
                    }
                } else {
                    String name = item.getFieldName();
                    String value = item.getString();
                    uploadForm.put(name, value);
                }
            }
            FileCtrl FILE_CTRL = CtrlCollection.FILE_CTRL;
            setHeaders();
            FILE_CTRL.setUserRequest(request);
           return FILE_CTRL.uploadFile(uploadForm.get("type"),uploadForm.get("userID"), fileItemsMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonConverter.createJson(MessageDTO.getFailureDTO());
    }
}
