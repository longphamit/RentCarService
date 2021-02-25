/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.OrderDetailDAO;
import com.longpc.dto.ProductDTO;
import com.longpc.dto.SearchProductDTO;
import com.longpc.util.LinkConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
public class OrderDetailServlet extends HttpServlet {

    private OrderDetailDAO orderDetailDAO= new OrderDetailDAO();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=LinkConstant.ERROR;
        String id=request.getParameter("id");
        SearchProductDTO searchProductDTO= new SearchProductDTO();
        try{
            LinkedHashMap<String,ProductDTO> listProduct=orderDetailDAO.findByIdOrder(id);
            request.setAttribute("listProducts", listProduct);
            url=LinkConstant.ORDER_DETAIL;
        }catch(Exception e){
            e.printStackTrace();
            log("Error at OrderDetailServlet "+e.getMessage());
        }
        request.getRequestDispatcher(url).forward(request, response);
        
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
