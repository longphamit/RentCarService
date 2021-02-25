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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=LinkConstant.ERROR;
        try {
            String id = request.getParameter(FieldConstant.FORM_USER_ID);
            String password = request.getParameter(FieldConstant.FORM_USER_PASSWORD);
            HttpSession session = request.getSession();
            UserDTO userDTO =userDAO.checkLogin(id, password);
            
            if(null!=userDTO){
                session.setAttribute("USER",userDTO);
                url=LinkConstant.HOME;
                MessageDTO messageDTO= new MessageDTO();
                messageDTO.setContent("Welcome "+userDTO.getName());
                messageDTO.setStatus(true);
                session.setAttribute(FieldConstant.MESSAGE, messageDTO);
            }else{
                url=LinkConstant.LOGIN;
                MessageDTO messageDTO= new MessageDTO();
                messageDTO.setContent("Bạn nhập sai email hoặc mật khẩu");
                messageDTO.setStatus(false);
                session.setAttribute(FieldConstant.MESSAGE, messageDTO);
            }
        }catch(Exception e){
            e.printStackTrace();
            log("Error at LoginServlet "+e.getMessage());
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
