/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dto.CartDTO;
import com.longpc.dto.MessageDTO;
import com.longpc.dto.ProductDTO;
import com.longpc.util.FieldConstant;
import com.longpc.util.LinkConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class RemoveFromCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=LinkConstant.ERROR;
        HttpSession session= request.getSession();
        MessageDTO messageDTO= new MessageDTO();
        try{
           String [] paramRemoves= request.getParameterValues("checkRemove");
           if(paramRemoves!=null){
               CartDTO cartDTO=(CartDTO) session.getAttribute(FieldConstant.CART_SESSION);
               for(String s:paramRemoves){
                   String[] sSplit=s.split("@");
                   ProductDTO productDTO= new ProductDTO();
                   productDTO.setId(sSplit[0]);
                   productDTO.setFromDate(new Date(sSplit[1]));
                   productDTO.setToDate(new Date(sSplit[2]));
                   cartDTO.remove(productDTO);
               }
               messageDTO.setStatus(true);
               
               messageDTO.setContent("Đã xóa thành công");
               session.setAttribute(FieldConstant.CART_SESSION,cartDTO);
               url=LinkConstant.CART;
           }else{
               messageDTO.setStatus(false);
               messageDTO.setContent("Xin hãy chọn 1 sản phẩm để xóa");
           }
           url=LinkConstant.CART;
           session.setAttribute("message", messageDTO);
        }catch(Exception e){
            e.printStackTrace();
            log("Error at RemoveFromCartServlet "+e.getMessage());
        }
        response.sendRedirect(url);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
