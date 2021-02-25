/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.factory;

import com.longpc.dto.CategoryDTO;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class CategoryFactory {
    public static CategoryDTO convertToDTO(ResultSet rs) throws Exception{
        CategoryDTO categoryDTO= new CategoryDTO();
        categoryDTO.setId(rs.getString("id"));
        categoryDTO.setName(rs.getString("name"));
        return categoryDTO;
    }
}
