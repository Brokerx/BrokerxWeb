/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.rest;

import com.firstidea.garnet.web.brokerx.ctrl.CtrlCollection;
import com.firstidea.garnet.web.brokerx.ctrl.LeadCtrl;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
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
@Path("lead")
public class LeadResource extends AppResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LeadResource
     */
    public LeadResource() {
    }

    @POST
    @Path("/saveLead")
    @Produces("application/json")
    public String saveLead(String leadJSON) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        String response = leadCtrl.saveLead(leadJSON);
        return response;
    }

    @POST
    @Path("/saveLeadStatusHistory")
    @Produces("application/json")
    public String saveLeadStatusHistory(String leadStatusHistoryJSON) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        String response = leadCtrl.saveLeadStatusHistory(leadStatusHistoryJSON);
        return response;
    }

    @POST
    @Path("/getLeads")
    @Produces("application/json")
    public String getLeads(@FormParam("leadID") Integer leadID,
            @FormParam("userID") Integer userID,
            @FormParam("otherUserID")  Integer otherUserID,
            @FormParam("type") String type,
            @FormParam("status") String status,
            @FormParam("item") String item,
            @FormParam("brokerID") String brokerID,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        String responseString = leadCtrl.getLeads(leadID, userID, otherUserID, type, status, item, brokerID, startDate, endDate);
        return responseString;
    }
    

    @POST
    @Path("/getAnalysisLeads")
    @Produces("application/json")
    public String getAnalysisLeads(@FormParam("leadID") Integer leadID,
            @FormParam("userID") Integer userID,
            @FormParam("otherUserID")  Integer otherUserID,
            @FormParam("type") String type,
            @FormParam("status") String status,
            @FormParam("item") String item,
            @FormParam("brokerID") String brokerID,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        String responseString = leadCtrl.getAnalysisLeads(leadID, userID, otherUserID, type, status, item, brokerID, startDate, endDate);
        return responseString;
    }
    

    @POST
    @Path("/getLeadDocuments")
    @Produces("application/json")
    public String getLeadDocuments(@FormParam("leadID") Integer leadID) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);

        String responseString = leadCtrl.getLeadDocuments(leadID);
        return responseString;
    }
    
    @POST
    @Path("/getLeadStatusHistory")
    @Produces("application/json")
    public String getLeadStatusHistory(@FormParam("leadID") Integer leadID) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);

        String responseString = leadCtrl.getLeadStatusHistory(leadID);
        return responseString;
    }

    @POST
    @Path("/getBrokerLeads")
    @Produces("application/json")
    public String getBrokerLeads(@FormParam("brokerID") Integer brokerID,
            @FormParam("type") String type,
            @FormParam("status") String status,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);

        String responseString = leadCtrl.getLeadsByBroker(brokerID, type, status, startDate, endDate);
        return responseString;
    }

    @POST
    @Path("/getLeadHistory")
    @Produces("application/json")
    public String getLeadHistory(@FormParam("leadID") Integer leadID) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.getLeadHistory(leadID);
    }

    @POST
    @Path("/getActiveLeads")
    @Produces("application/json")
    public String getActiveLeads(@FormParam("userID") Integer userID,
            @FormParam("type") String type) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.getActiveLeads(userID, type);
    }

    @POST
    @Path("/getHistory")
    @Produces("application/json")
    public String getHistory(@FormParam("userID") Integer userID,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.getHistory(userID, startDate, endDate);
    }
    
    @POST
    @Path("/dealDone")
    @Produces("application/json")
    public String dealDone(@FormParam("leadID")  Integer leadID) {
        LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
        leadCtrl.setUserRequest(request);
        return leadCtrl.dealDone(leadID);
    }
    
    
    @POST
    @Produces("application/json")
    @Path("/uploadDocument") 
    public String uploadDocument() {
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
            LeadCtrl leadCtrl = CtrlCollection.LEAD_CTRL;
            leadCtrl.setUserRequest(request);
            String responseString = leadCtrl.uploadDocument(userJSON, fileItemsMap);
            return responseString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonConverter.createJson(MessageDTO.getFailureDTO());
    }
}
