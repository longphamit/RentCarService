/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.OrderDAO;
import com.longpc.dto.MessageDTO;
import com.longpc.util.LinkConstant;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class RemoveOrderServlet extends HttpServlet {

    private OrderDAO orderDAO= new OrderDAO();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LinkConstant.ERROR;
        HttpSession session=request.getSession();
        MessageDTO messageDTO= new MessageDTO();
        try {
            String id[] = request.getParameterValues("checkRemove");
            if (id != null) {
                orderDAO.setStatusFalse(id);
                messageDTO.setStatus(true);
                messageDTO.setContent("Xóa thành công");
                session.setAttribute("message",messageDTO);
            }else{
                messageDTO.setStatus(false);
                messageDTO.setContent("Xin hãy chọn 1 đơn hàng để xóa");
                session.setAttribute("message", messageDTO);
            }
            
            url="History";
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at RemoveOrderServlet "+e.getMessage());
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
