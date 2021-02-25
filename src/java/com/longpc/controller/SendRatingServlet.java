/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.OrderDetailDAO;
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
public class SendRatingServlet extends HttpServlet {

    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String url = LinkConstant.ERROR;
        try {
            url = "OrderDetail" + "?id=" + request.getParameter("idOrder");
            if (null == request.getParameter("rating")) {
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setStatus(false);
                messageDTO.setContent("Xin đánh giá trên sao");
                HttpSession session = request.getSession();
                session.setAttribute("message", messageDTO);
                response.sendRedirect(url);
                return;
            }
            if (null == request.getParameter("txtFeedback") || request.getParameter("txtFeedback").isEmpty()) {
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setStatus(false);
                messageDTO.setContent("Xin hãy nhập feedback");
                HttpSession session = request.getSession();
                session.setAttribute("message", messageDTO);
                response.sendRedirect(url);
                return;
            }
            orderDetailDAO.setRating(Integer.parseInt(request.getParameter("rating")), request.getParameter("txtFeedback"), request.getParameter("id"),request.getParameter("idOrder"));
           
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setStatus(true);
            messageDTO.setContent("Gửi feedback thành công");
            HttpSession session = request.getSession();
            session.setAttribute("message", messageDTO);
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at SendRatingServlet "+e.getMessage());
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
