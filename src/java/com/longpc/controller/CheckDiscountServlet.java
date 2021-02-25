/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.DiscountDAO;
import com.longpc.dto.UserDTO;
import com.longpc.util.FieldConstant;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class CheckDiscountServlet extends HttpServlet {

    private DiscountDAO discountDAO = new DiscountDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jSONObject = new JSONObject();
        PrintWriter printWriter = response.getWriter();
        String id = request.getParameter("code");
        try {
            HttpSession session=request.getSession();
            UserDTO udto=(UserDTO) session.getAttribute("USER");
            
            double discount = discountDAO.checkDiscount(id,udto.getUserId());
            if (discount >=0) {
                jSONObject.put("status", 1);
                jSONObject.put("discount", discount);
                printWriter.write(jSONObject.toString());
                printWriter.flush();
            } else {
                jSONObject.put("status",discount);
                printWriter.write(jSONObject.toString());
                printWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at CheckDiscountServlet"+e.getMessage());
        }
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
