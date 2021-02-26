/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.ProductDAO;
import com.longpc.dto.CartDTO;
import com.longpc.dto.MessageDTO;
import com.longpc.dto.ProductDTO;
import com.longpc.util.FieldConstant;
import com.longpc.util.LinkConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class OrderServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

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
        String url = "error.jsp";
        try {
            String id = request.getParameter(FieldConstant.FORM_PRODUCT_ID);
            String toDate = request.getParameter(FieldConstant.FORM_TO_DATE);
            String fromDate = request.getParameter(FieldConstant.FORM_FROM_DATE);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dFromDate = sdf.parse(fromDate);
            Date dToDate=sdf.parse(toDate);
            fromDate = output.format(dFromDate);
            toDate=output.format(dToDate);
            
            MessageDTO messageDTO = new MessageDTO();
            HttpSession session = request.getSession();
            ProductDTO productDTO = productDAO.findById(id);
            productDTO.setFromDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fromDate));
            productDTO.setToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(toDate));
            productDTO.setId(id);
            boolean check = false;
            if (null == session.getAttribute(FieldConstant.CART_SESSION)) {
                CartDTO cartDTO = new CartDTO();
                if (productDTO != null) {
                    check = cartDTO.addToCart(productDTO);
                    session.setAttribute(FieldConstant.CART_SESSION, cartDTO);
                }
            } else {
                CartDTO cartDTO = (CartDTO) session.getAttribute(FieldConstant.CART_SESSION);
                check = cartDTO.addToCart(productDTO);
                session.setAttribute(FieldConstant.CART_SESSION, cartDTO);
            }
            if (check) {
                messageDTO.setStatus(true);
                messageDTO.setContent("Đã thêm vào giỏ hàng");
                session.setAttribute(FieldConstant.MESSAGE, messageDTO);
            } else {
                messageDTO.setStatus(false);
                messageDTO.setContent("Không được đặt xe xin vui lòng kiểm tra lại ngày");
                session.setAttribute(FieldConstant.MESSAGE, messageDTO);
            }
            url = LinkConstant.HOME;
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at OrderServlet "+e.getMessage());
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
