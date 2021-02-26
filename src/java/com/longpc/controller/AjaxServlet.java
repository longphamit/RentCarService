/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.google.gson.Gson;
import com.longpc.dao.OrderDetailDAO;
import com.longpc.dao.ProductDAO;
import com.longpc.dto.CartDTO;
import com.longpc.dto.MessageDTO;
import com.longpc.dto.ProductDTO;
import com.longpc.util.FieldConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class AjaxServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String action = request.getParameter("action");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date dFromDate = sdf.parse(fromDate);
            Date dToDate=sdf.parse(toDate);
            fromDate = output.format(dFromDate);
            toDate=output.format(dToDate);
            String id = request.getParameter("id");
            switch (action) {
                case "checkOrder": {
                    Gson gson = new Gson();
                    StringBuilder stringBuilder = new StringBuilder();
                    MessageDTO messageDTO = new MessageDTO();
                    HttpSession session = request.getSession();
                    CartDTO cartDTO = (CartDTO) session.getAttribute(FieldConstant.CART_SESSION);
                    if (cartDTO != null) {
                        if (cartDTO.getCart().containsKey(id)) {
                            for (ProductDTO productDTO : cartDTO.getCart().get(id)) {
                                if (productDTO.getFromDate().getTime() <= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fromDate).getTime()
                                        && productDTO.getToDate().getTime() >= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(fromDate).getTime()
                                        || productDTO.getFromDate().getTime() <= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(toDate).getTime()
                                        && productDTO.getToDate().getTime() >= new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(toDate).getTime()) {
                                    messageDTO.setStatus(false);
                                    stringBuilder.append("Xe đã được đặt trong khoảng thời gian này");
                                    messageDTO.setContent(stringBuilder.toString());
                                    String json = gson.toJson(messageDTO);
                                    PrintWriter w = response.getWriter();
                                    w.write(json);
                                    w.flush();
                                    return;
                                }
                            }
                        }
                        //List<ProductDTO> listProductDTOs=productDAO.checkQuantity(cartDTO.getItems());
                    }
                    if (!orderDetailDAO.checkOrderDetail(id, fromDate, toDate)) {
                        messageDTO.setStatus(false);
                        stringBuilder.append("Xe đã được đặt trong khoảng thời gian này");
                        messageDTO.setContent(stringBuilder.toString());
                        String json = gson.toJson(messageDTO);
                        PrintWriter w = response.getWriter();
                        w.write(json);
                        w.flush();
                        return;
                    }
                    messageDTO.setStatus(true);
                    messageDTO.setContent(stringBuilder.toString());
                    String json = gson.toJson(messageDTO);
                    PrintWriter w = response.getWriter();
                    w.write(json);
                    w.flush();
                    break;
                }
                case "checkout": {
                    HttpSession session = request.getSession();

                    session.setAttribute(FieldConstant.CART_SESSION, new CartDTO());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at Ajax servlet" + e.getMessage());
        }
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
        response.setContentType("application/json;charset=UTF-8");
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
