/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.dao;

import com.longpc.dto.CategoryDTO;
import com.longpc.factory.CategoryFactory;
import com.longpc.util.DBUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class CategoryDAO extends BaseDAO{
    public List<CategoryDTO> findAll() throws Exception{
        List<CategoryDTO> result= new ArrayList<>();
        String sql="select id,name from tblCategories";
        try{
            cn=DBUtil.makeConnection();
            if(cn!=null){
                ps=cn.prepareStatement(sql);
                rs=ps.executeQuery();
                while(rs.next()){
                    CategoryDTO categoryDTO=CategoryFactory.convertToDTO(rs);
                    result.add(categoryDTO);
                }
            }
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return result;
    }
}
