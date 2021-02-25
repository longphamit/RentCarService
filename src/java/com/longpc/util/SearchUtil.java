/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.util;

import com.longpc.dto.SearchProductDTO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.webresources.Cache;

/**
 *
 * @author ASUS
 */
public class SearchUtil {
    public static SearchProductDTO setUpSearchDTO (HttpServletRequest request) throws Exception {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy"); 
        String searchName= request.getParameter(FieldConstant.SEARCH_NAME);
        String fromDate=request.getParameter(FieldConstant.SEARCH_FROM_DATE);
        String toDate=request.getParameter(FieldConstant.SEARCH_TO_DATE);
        String categoryId=request.getParameter(FieldConstant.SEARCH_CATEGORY);
        SearchProductDTO searchProductDTO= new SearchProductDTO();
        searchProductDTO.setName(searchName);
        if(null!=fromDate&&!fromDate.isEmpty()){
            searchProductDTO.setFromDate(fromDate);
        }
        if(null!=toDate&&!toDate.isEmpty()){
            searchProductDTO.setToDate(toDate);
        }
        if(null!=categoryId&&!categoryId.isEmpty()){
            searchProductDTO.setCategoryId(categoryId);
        }
        return searchProductDTO;
    }
}
