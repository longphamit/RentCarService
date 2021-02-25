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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class UpdateFromCartServlet extends HttpServlet {

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
        String url = LinkConstant.ERROR;
        
        try {
            String id = request.getParameter(FieldConstant.FORM_PRODUCT_ID);
            String toDate = request.getParameter(FieldConstant.FORM_TO_DATE);
            String fromDate = request.getParameter(FieldConstant.FORM_FROM_DATE);
            String oldToDate = request.getParameter("oldToDate");
            String oldFromDate = request.getParameter("oldFromDate");
            MessageDTO messageDTO = new MessageDTO();
            HttpSession session = request.getSession();
            CartDTO cartDTO = (CartDTO) session.getAttribute(FieldConstant.CART_SESSION);
            int i=0;
            List<ProductDTO> result= new ArrayList<>();
            for (ProductDTO productDTO : cartDTO.getCart().get(id)) {
                
                if (productDTO.getToDate().getTime() == new SimpleDateFormat("yyyy-MM-dd").parse(oldToDate).getTime()
                        && productDTO.getFromDate().getTime()==new SimpleDateFormat("yyyy-MM-dd").parse(oldFromDate).getTime() ) {
                    productDTO.setFromDate(new SimpleDateFormat("yyyy-MM-dd").parse(fromDate));
                    productDTO.setToDate(new SimpleDateFormat("yyyy-MM-dd").parse(toDate));
                    result.add(productDTO);
                }
                else{
                    result.add(productDTO);
                }
            }
            if(!result.isEmpty()){
                cartDTO.getCart().put(id, result);
                session.setAttribute(FieldConstant.CART_SESSION, cartDTO);
                messageDTO.setStatus(true);
                messageDTO.setContent("Đã update giỏ hàng");
                session.setAttribute(FieldConstant.MESSAGE, messageDTO);
            }
            url = LinkConstant.CART;
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at UpdateFromCartServlet "+e.getMessage());
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
