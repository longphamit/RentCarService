/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.factory;

import com.longpc.dto.CategoryDTO;
import com.longpc.dto.ProductDTO;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class ProductFactory {

    public static ProductDTO convertToDTO(ResultSet rs) throws Exception {
        ProductDTO productDTO = new ProductDTO();
        try {
            if (null != rs.getString("id")) {
                productDTO.setId(rs.getString("id"));
            }
        } catch (Exception e) {
            System.out.println("khong set id");
        }
        try {
            if (null != rs.getString("name")) {
                productDTO.setName(rs.getString("name"));
            }
        } catch (Exception e) {
            System.out.println("khong set name");
        }
        try {
            productDTO.setQuantity(rs.getInt("quantity"));
        } catch (Exception e) {
            System.out.println("khong set quantity");
        }
        try {
            if (null != rs.getString("price")) {
                productDTO.setPrice(rs.getDouble("price"));
            }
        } catch (Exception e) {
            System.out.println("khong set price");
        }
        try{
            if(0!=rs.getInt("yearProduct")){
                productDTO.setYearProduct(rs.getInt("yearProduct"));
            }
        }catch(Exception e){
            System.out.println("khong set year product");
        }
         try{
            if(null!=rs.getString("carNumber")){
                productDTO.setCarNumber(rs.getString("carNumber"));
            }
        }catch(Exception e){
            System.out.println("khong set carNumber");
        }
        try {
            if (null != rs.getString("image_address")) {
                productDTO.setImageAddress(rs.getString("image_address"));
            }
        } catch (Exception e) {
            System.out.println("khong set image");
        }
        try {
            if (null != rs.getString("id_categories")) {
                String id = rs.getString("id_categories");
                if (null != rs.getString("category_name")) {
                    String name = rs.getString("category_name");
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setId(id);
                    categoryDTO.setName(name);
                    productDTO.setCategoryDTO(categoryDTO);
                }
            }
        }catch(Exception e){
            System.out.println("khong set category");
        }
       

        return productDTO;
    }
}
