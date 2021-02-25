/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;


/**
 *
 * @author ASUS
 */
public class ReCaptchaUtil {
    public static final String SITE_KEY="6Ld52EMaAAAAAP-rR8NykL93kX2-XgNVNA0cEmFP";
    public static final String SECRET_KEY="6Ld52EMaAAAAAMOn9rZu4_JcsVM5fryhVcxTM97W";
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    
    public static boolean verify(String reCapchaRespone) throws Exception{
        if(reCapchaRespone==null||reCapchaRespone.isEmpty()){
            return false;
        }
        URL urlVerify= new URL(SITE_VERIFY_URL);
        HttpsURLConnection uRLConnection= (HttpsURLConnection) urlVerify.openConnection();
        uRLConnection.setRequestMethod("POST");
        uRLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        
        return false;
        
    }
}
