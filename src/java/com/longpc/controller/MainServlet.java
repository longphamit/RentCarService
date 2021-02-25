/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.controller;

import com.longpc.dao.CategoryDAO;
import com.longpc.dao.OrderDetailDAO;
import com.longpc.dao.ProductDAO;
import com.longpc.dto.CategoryDTO;
import com.longpc.dto.ProductDTO;
import com.longpc.dto.SearchProductDTO;
import com.longpc.util.PagingUtil;
import com.longpc.util.SearchUtil;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
public class MainServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();
    private PagingUtil pagingUtil = new PagingUtil();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ServletContext context = getServletContext();
            context.setAttribute("listCategories", categoryDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "home.jsp";
        try {
            SearchProductDTO searchProductDTO = SearchUtil.setUpSearchDTO(request);
            int pageNum = 1;
            int offset = 0;
            if (request.getParameter("page") != null) {
                pageNum = Integer.parseInt(request.getParameter("page"));
                offset = PagingUtil.SIZE * (pageNum - 1);
            }
            LinkedHashMap<String, ProductDTO> listProducts = productDAO.findAll(searchProductDTO, offset);
            int count = productDAO.countAll(searchProductDTO);
            if (count <= PagingUtil.SIZE) {
                count = listProducts.size();
            }
            request.setAttribute("listProducts", listProducts);
            HttpSession session = request.getSession();
            pagingUtil.calculatorMinMax(count);
            session.setAttribute("minPage", pagingUtil.getMinPage());
            session.setAttribute("maxPage", pagingUtil.getMaxPage());
        } catch (Exception e) {
            e.printStackTrace();
            log("Error at MainServlet "+e.getMessage());
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
