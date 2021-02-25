/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.UserDAO;
import com.longpc.dto.MessageDTO;
import com.longpc.dto.UserDTO;
import com.longpc.util.FieldConstant;
import com.longpc.util.LinkConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class RegisterServlet extends HttpServlet {

    private UserDAO udao = new UserDAO();

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
        MessageDTO messageDTO = new MessageDTO();
        boolean status = false;
        HttpSession session = request.getSession();
        String url = LinkConstant.ERROR;
        UserDTO udto = new UserDTO();
        try {
            String userName = request.getParameter(FieldConstant.FORM_USER_ID);
            String password = request.getParameter(FieldConstant.FORM_USER_PASSWORD);
            String phone = request.getParameter(FieldConstant.FORM_USER_PHONE);
            String fullName=request.getParameter(FieldConstant.FORM_USER_NAME);
            String address=request.getParameter(FieldConstant.FORM_USER_ADDRESS);
            udto.setCreateDate(Calendar.getInstance().getTime());
            udto.setUserId(userName);
            udto.setPassword(password);
            udto.setName(fullName);
            udto.setAddress(address);
            udto.setStatus("NEW");
            udao.addNew(udto);
            status = true;

        } catch (Exception e) {
            
            if (e.getMessage().contains("duplicate")) {
                messageDTO.setStatus(status);
                messageDTO.setContent("Email đã tồn tại");
            } else {
                e.printStackTrace();
                log("Error at RegisterServlet "+e.getMessage());
            }
        }
        if (status) {
            session.setAttribute("USER", udto);
            messageDTO.setContent("Welcome " + udto.getName());
            messageDTO.setStatus(true);
            url = LinkConstant.HOME;
        } else {
            url = LinkConstant.LOGIN;
        }
        session.setAttribute(FieldConstant.MESSAGE, messageDTO);
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
