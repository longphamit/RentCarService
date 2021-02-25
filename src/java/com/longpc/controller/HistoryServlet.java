/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.OrderDAO;
import com.longpc.dto.SearchOrderDTO;
import com.longpc.dto.UserDTO;
import com.longpc.util.FieldConstant;
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
public class HistoryServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LinkConstant.HISTORY;
        try {
            HttpSession session = request.getSession();
            UserDTO udto = (UserDTO) session.getAttribute("USER");
            SearchOrderDTO searchOrderDTO= new SearchOrderDTO();
            searchOrderDTO.setIdUser(udto.getUserId());
            String createDate=request.getParameter("searchCreateDate");
            String name=request.getParameter(FieldConstant.SEARCH_NAME);
            searchOrderDTO.setDate(createDate);
            searchOrderDTO.setName(name);
            request.setAttribute("listOrders", orderDAO.getAllOrder(searchOrderDTO));
            request.setAttribute("getAll", true);
        }catch(Exception e){
            e.printStackTrace();
            log("Error at HistoryServlet "+e.getMessage());
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
