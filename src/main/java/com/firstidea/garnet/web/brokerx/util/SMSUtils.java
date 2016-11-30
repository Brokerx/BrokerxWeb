/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import static javax.servlet.SessionTrackingMode.URL;

/**
 *
 * @author Govind
 */
public class SMSUtils {

    public static void sendOTPSMS(String mobile, String OTP) {
        //Your authentication key
        String authkey = "101964AS3ImdhGZwWX568e5ea9";
//Multiple mobiles numbers separated by comma
        String mobiles = mobile;//"9730656051";
//Sender ID,While using route4 sender id should be 6 characters long.
        String senderId = "Reachx";
//Your message to send, Add URL encoding here.
        String message = "Your Reachx verification code is "+OTP;
//define route 1: promotional, 4:Transactional
        String route = "4";

//Prepare Url
        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;

//encoding message 
        String encoded_message = URLEncoder.encode(message);

//Send SMS API
        String mainUrl = "http://api.msg91.com/api/sendhttp.php?";

//Prepare parameter string 
        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append("authkey=" + authkey);
        sbPostData.append("&mobiles=" + mobiles);
        sbPostData.append("&message=" + encoded_message);
        sbPostData.append("&route=" + route);
        sbPostData.append("&sender=" + senderId);

//final string
        mainUrl = sbPostData.toString();
        try {
            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            //reading response 
            String response;
            while ((response = reader.readLine()) != null) //print response 
            {
                System.out.println(response);
            }

            //finally close connection
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
