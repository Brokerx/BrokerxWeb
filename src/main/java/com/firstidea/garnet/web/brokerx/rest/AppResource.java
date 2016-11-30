package com.firstidea.garnet.web.brokerx.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
//import org.jboss.resteasy.spi.HttpResponse;

/**
 *
 * @author GovindK
 */
public class AppResource {
    
    @Context 
    HttpServletResponse response;
    
    @Context 
    HttpServletRequest request;
            
    protected void setHeaders(){
        response.getHeaderNames().add("Access-Control-Allow-Origin:*");        
    }    
        
}
