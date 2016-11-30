/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.ctrl.CtrlCollection;
import com.firstidea.garnet.web.brokerx.ctrl.UserCtrl;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.FormParam;
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
@Path("user")
public class UserResource extends AppResource {

    @Context
    private UriInfo context;

    UserCtrl userCtrl = CtrlCollection.USER_CTRL;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    @POST
    @Produces("application/json")
    @Path("/registerUser") 
    public String registerUser() {
        try {
            
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

            // Set factory constraints
            factory.setSizeThreshold(10000);

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> multiparts = upload.parseRequest(request);
//            FileItem filePart = null;
//            String fileType = null, title = null;
            Map<String, FileItem> fileItemsMap = new HashMap<String, FileItem>();
            Map<String, String> uploadForm = new HashMap<String, String>();

            for (FileItem item : multiparts) {
                if (!item.isFormField()) {
                    //your operations on file
//                    String fieldName = item.getFieldName();
                    String fileName = item.getName();
                    if (fileName != null && fileName.length() > 0) {
//                        String contentType = item.getContentType();
//                        boolean isInMemory = item.isInMemory();
//                        long sizeInBytes = item.getSize();
                        fileItemsMap.put(fileName, item);
                    }
                } else {
                    String name = item.getFieldName();
                    String value = item.getString();
                    uploadForm.put(name, value);
                }
            }
            String userJSON = uploadForm.get("DataJSON");
            userCtrl.setUserRequest(request);
            String responseString = userCtrl.registerUser(userJSON, fileItemsMap);
            return responseString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonConverter.createJson(MessageDTO.getFailureDTO());
    }

    @POST
    @Produces("application/json")
    @Path("/appLogin")
    public String appLogin(@FormParam("userName") String userName,
            @FormParam("password") String password) {
        return userCtrl.appLogin(userName, password);
    }

    @POST
    @Produces("application/json")
    @Path("/updateGCMKey")
    public String updateGCMKey(@FormParam("userID") Integer userID,
            @FormParam("gcmKey") String gcmKey) {
        return userCtrl.updateGCMKey(userID, gcmKey);
    }

    @POST
    @Produces("application/json")
    @Path("/updateBrokerDealsInItems")
    public String updateBrokerDealsInItems(@FormParam("userID") Integer userID,
            @FormParam("brokerDealsInItems") String brokerDealsInItems) {
        return userCtrl.updateBrokerDealsInItems(userID, brokerDealsInItems);
    }

    @POST
    @Produces("application/json")
    @Path("/getUserByMobile")
    public String getUserByMobile(@FormParam("mobile") String mobile) {
        return userCtrl.getUserByMobile(mobile);
    }

    @POST
    @Produces("application/json")
    @Path("/sendConnectionRequest")
    public String sendConnectionRequest(@FormParam("fromUserID") Integer fromUserID,
            @FormParam("toUserID") Integer toUserID) {
        return userCtrl.sendConnectionRequest(fromUserID, toUserID);
    }

    @POST
    @Produces("application/json")
    @Path("/changeConnectionStatus")
    public String changeConnectionStatus(@FormParam("connectionID") Integer connectionID,
            @FormParam("status") String status) {
        return userCtrl.changeConnectionStatus(connectionID, status);
    }

    @POST
    @Produces("application/json")
    @Path("/getUserConnections")
    public String getUserConnections(@FormParam("userID") Integer userID) {
        return userCtrl.getUserConnections(userID);
    }
}
