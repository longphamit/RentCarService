/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebFilter(urlPatterns = "/*")
public class FilterDispatcher implements Filter{
    private final String mainPage="MainServlet";
    private FilterConfig filterConfig=null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       this.filterConfig=filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse resp=  (HttpServletResponse) response ;
        String uri=req.getRequestURI();
        try{
            
            int lastIndex=uri.lastIndexOf("/");
            String resource=uri.substring(lastIndex+1);
            String url=mainPage;
            if(resource.length()>0){
                url=resource.substring(0,1).toUpperCase()
                        +resource.substring(1)
                        +"Servlet";
                if(resource.lastIndexOf(".jsp")>0){
                    url=resource;
                }
                
            }
            if(url!=null){
                if(url.contains("Servlet")){
                    filterConfig.getServletContext().log(req.getRemoteAddr()+"-"+req.getProtocol()+"-"+url);
                }
                RequestDispatcher rd=req.getRequestDispatcher(url);
                rd.forward(request, response);
            }else{
                
                chain.doFilter(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        
    }
    
}
