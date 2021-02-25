/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.OrderDAO;
import com.longpc.dto.CartDTO;
import com.longpc.dto.MessageDTO;
import com.longpc.dto.OrderDTO;
import com.longpc.dto.UserDTO;
import com.longpc.util.FieldConstant;
import com.longpc.util.LinkConstant;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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
public class CheckOutServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

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
        double discount = 0;
        String discountId = null;
        try {
            if (null != request.getParameter("txtDiscount")) {
                if (!request.getParameter("txtDiscount").toString().isEmpty()) {
                    discount = Double.parseDouble((String) request.getParameter("txtDiscount"));
                    discountId = (String) request.getParameter("txtDiscountId");
                }
            }
            HttpSession session = request.getSession();
            CartDTO cartDTO = (CartDTO) session.getAttribute(FieldConstant.CART_SESSION);
            UserDTO userDTO = (UserDTO) session.getAttribute("USER");
            if (userDTO == null) {
                response.sendRedirect(LinkConstant.LOGIN);
                return;
            } else if (userDTO.getStatus().equals("NEW")) {
                MessageDTO messageDTO = new MessageDTO();
                messageDTO.setContent("Vui lòng verify email của bạn");
                messageDTO.setStatus(false);
                session.setAttribute("message", messageDTO);
                response.sendRedirect(LinkConstant.CART);
                return;
            }
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(UUID.randomUUID().toString());
            orderDTO.setSum(cartDTO.total());
            orderDTO.setUserId(userDTO.getUserId());
            orderDTO.setCreateDate(Calendar.getInstance().getTime());
            orderDTO.setStatus(true);
            if (discount != 0) {
                orderDTO.setTotal(cartDTO.total() - cartDTO.total() * discount);
            } else {
                orderDTO.setTotal(cartDTO.total());
            }
            orderDTO.setDiscountId(discountId);
            orderDAO.makeOrder(orderDTO, cartDTO);
            url = LinkConstant.HOME;
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setContent("Đặt xe thành công");
            messageDTO.setStatus(true);
            session.setAttribute("message", messageDTO);
            session.setAttribute(FieldConstant.CART_SESSION, new CartDTO());
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at CheckOutServlet "+e.getMessage());
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
