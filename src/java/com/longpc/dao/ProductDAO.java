/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.dao;

import com.longpc.dto.ProductDTO;
import com.longpc.dto.SearchProductDTO;
import com.longpc.factory.ProductFactory;
import com.longpc.util.DBUtil;
import com.longpc.util.PagingUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author ASUS
 */
public class ProductDAO extends BaseDAO {


    public Set<String> findName() throws Exception {
        StringBuilder sql = new StringBuilder();
        Set<String> result= new TreeSet<>();
        LinkedHashMap<Object, Integer> sqlParameterAppend = new LinkedHashMap<>();
        sql.append("select p.name "
                + "from tblProducts p "
                + "where p.id not in (select od.id_product from tblOrders o, tblOrderDetail od where o.id=od.id_order and od.from_date <= GETDATE() and od.to_date >= GETDATE()) ");
        int count = 1;
        try {
            sql.append(" order by p.yearProduct DESC ");
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql.toString());
                rs = ps.executeQuery();
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }
                return result;
            }
        } finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return null;

    }
    public LinkedHashMap<String, ProductDTO> findAll(SearchProductDTO searchProductDTO,int offset) throws Exception {
        StringBuilder sql = new StringBuilder();
        LinkedHashMap<String, ProductDTO> result = new LinkedHashMap<String, ProductDTO>();
        LinkedHashMap<Object, Integer> sqlParameterAppend = new LinkedHashMap<>();
        sql.append("select p.id,p.name,p.image_address,p.price,p.carNumber,p.yearProduct,p.id_categories, ca.name as category_name "
                + "from tblProducts p, tblCategories ca "
                + "where p.id_categories = ca.id ");
        int count = 1;
        try {
            if (null != searchProductDTO.getName()) {
                sql.append(" and p.name like ? ");
                sqlParameterAppend.put(searchProductDTO.getName(), count++);
            }
            if(null!=searchProductDTO.getCategoryId()){
                sql.append(" and p.id_categories = ? ");
                sqlParameterAppend.put(searchProductDTO.getCategoryId(), count++);
            }
            if(null==searchProductDTO.getFromDate()&&null==searchProductDTO.getToDate()){
                sql.append(" and p.id not in (select od.id_product from tblOrders o, tblOrderDetail od where o.id=od.id_order and od.from_date <= GETDATE() and od.to_date >= GETDATE()) ");
            }else{
                if(null!=searchProductDTO.getFromDate()){
                    sql.append(" and p.id not in (select od.id_product from tblOrders o, tblOrderDetail od where o.id=od.id_order and od.from_date <= ?) ");
                    sqlParameterAppend.put(searchProductDTO.getFromDate(), count++);
                }
                if(null!=searchProductDTO.getToDate()){
                    sql.append(" and p.id not in (select od.id_product from tblOrders o, tblOrderDetail od where o.id=od.id_order and od.to_date >= ?) ");
                    sqlParameterAppend.put(searchProductDTO.getToDate(), count++);
                }
            }
           
            sql.append(" order by p.yearProduct DESC ");
            sql.append(" OFFSET ? ROW FETCH NEXT ? ROWS ONLY ");
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql.toString());
                for (Map.Entry<Object, Integer> entry : sqlParameterAppend.entrySet()) {
                    if (!entry.getKey().toString().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")&&!entry.getKey().toString().matches("[0-9]+")) {
                        ps.setObject(entry.getValue(), "%" + entry.getKey() + "%");
                    } 
                    else {
                        ps.setObject(entry.getValue(), entry.getKey());
                    }
                }
                ps.setObject(count++,offset);
                ps.setObject(count++,PagingUtil.SIZE);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ProductDTO productDTO = ProductFactory.convertToDTO(rs);
                    result.put(productDTO.getId(), productDTO);
                }
                return result;
            }
        } finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return null;

    }
    public int countAll(SearchProductDTO searchProductDTO) throws Exception{
        StringBuilder sql = new StringBuilder();
        LinkedHashMap<Object, Integer> sqlParameterAppend = new LinkedHashMap<>();
        sql.append("select count(p.id) as count "
                + "from tblProducts p, tblCategories ca "
                + "where p.id_categories = ca.id ");
        int count = 1;
        try {
            if (null != searchProductDTO.getName()) {
                sql.append(" and p.name like ? ");
                sqlParameterAppend.put(searchProductDTO.getName(), count++);
            }
            if(null!=searchProductDTO.getCategoryId()){
                sql.append(" and p.id_categories = ? ");
                sqlParameterAppend.put(searchProductDTO.getCategoryId(), count++);
            }
            sql.append(" and p.id not in (select od.id_product from tblOrders o, tblOrderDetail od where o.id=od.id_order and od.from_date <= GETDATE() and od.to_date >= GETDATE()) ");
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql.toString());
                for (Map.Entry<Object, Integer> entry : sqlParameterAppend.entrySet()) {
                    if (!entry.getKey().toString().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")&&!entry.getKey().toString().matches("[0-9]+")) {
                        ps.setObject(entry.getValue(), "%" + entry.getKey() + "%");
                    } else {
                        ps.setObject(entry.getValue(), entry.getKey());
                    }
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return 0;
    }
   


    public ProductDTO findById(String id) throws Exception {
        String sql = "select p.id,p.name,p.image_address,p.yearProduct,p.price,p.id_categories,p.carNumber, p.id_categories, ca.name as category_name  "
                + "from tblProducts p, tblCategories ca "
                + "where p.id= ? and p.id_categories = ca.id";
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                ps.setObject(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ProductFactory productFactory = new ProductFactory();
                    return productFactory.convertToDTO(rs);
                }
            }
        } finally {
            DBUtil.closeConnection(cn, ps, rs);
        }
        return null;
    }
}
