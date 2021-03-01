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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class OrderDetailDAO extends BaseDAO {

    public void setStatus() throws Exception {
        try {
            String sql = "update tblOrderDetail where ";
            cn = DBUtil.makeConnection();
            if (cn != null) {

            }
        } finally {
            DBUtil.closeConnection(cn, ps, rs);
        }
    }

    public LinkedHashMap<String, ProductDTO> listProductInOrderDetailBySearch(SearchProductDTO searchProductDTO) throws Exception {
        StringBuilder sql = new StringBuilder();
        LinkedHashMap<Object, Integer> sqlParameterAppend = new LinkedHashMap<>();
        LinkedHashMap<String, ProductDTO> result= new LinkedHashMap<>();
        sql.append("select p.id, p.name,p.image_address,p.price,p.carNumber,p.yearProduct,p.id_categories, ca.name as category_name "
                + "from tblProducts p, tblCategories ca ,tblOrderDetail od, tblOrders o "
                + "where p.id_categories = ca.id and o.id=od.id_order and od.id_product=p.id and od.from_date <= GETDATE() and od.to_date >= GETDATE() "
        );
        if (null != searchProductDTO.getFromDate() || null != searchProductDTO.getToDate()) {
            int count = 1;
            if (null != searchProductDTO.getFromDate()) {
                sql.append("and od.from_date <= convert(datetime,?) ");
                sqlParameterAppend.put(searchProductDTO.getFromDate(), count++);
            }
            if (null != searchProductDTO.getToDate()) {
                sql.append("and od.to_date >= convert(datetime,?) ");
                sqlParameterAppend.put(searchProductDTO.getToDate(), count++);
            }
        }
        try {
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
                while (rs.next()) {
                    ProductDTO productDTO= ProductFactory.convertToDTO(rs);
                    result.put(productDTO.getId(), productDTO);
                }
                return result;
            }
        } finally {
            DBUtil.closeConnection(cn, ps, rs);
        }

        return null;
    }
    public List<ProductDTO> findByIdOrder(String id) throws Exception {
        StringBuilder sql = new StringBuilder();
        LinkedHashMap<Object, Integer> sqlParameterAppend = new LinkedHashMap<>();
        List<ProductDTO> result= new ArrayList<>();
        sql.append("select p.id, p.name,p.image_address,p.price,p.carNumber,p.yearProduct,p.id_categories, ca.name as category_name, od.from_date,od.to_date "
                + "from tblProducts p, tblCategories ca ,tblOrderDetail od, tblOrders o "
                + "where p.id_categories = ca.id and o.id=od.id_order and od.id_product=p.id and od.id_order = ? ");
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql.toString());
                ps.setObject(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ProductDTO productDTO= ProductFactory.convertToDTO(rs);
                    productDTO.setFromDate(rs.getTimestamp("from_date"));
                    productDTO.setToDate(rs.getTimestamp("to_date"));
                    result.add(productDTO);
                }
                return result;
            }
        } finally {
            DBUtil.closeConnection(cn, ps, rs);
        }

        return null;
    }
    public boolean checkOrderDetail(String id,String fromDate,String toDate) throws Exception{
        String sql="select od.id_product from tblOrderDetail od,tblOrders o "
                + "where o.id=od.id_order and od.id_product=? and ((od.from_date >= ? and od.from_date <= ?) or (od.to_date >= ? and od.to_date <= ?) or (od.from_date <= ? and od.to_date >= ?) or (od.from_date>= ? and od.to_date<=?))";
        try{
            cn=DBUtil.makeConnection();
            ps=cn.prepareStatement(sql);
            ps.setObject(1, id);
            ps.setObject(2, fromDate);
            ps.setObject(3, toDate);
            
            
            ps.setObject(4, fromDate);
            ps.setObject(5, toDate);
            
            ps.setObject(6, fromDate);
            ps.setObject(7, toDate);
            
            ps.setObject(8, fromDate);
            ps.setObject(9, toDate);
            rs=ps.executeQuery();
            if(rs.next()){
                return false;
            }
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return true;
    }
    public boolean setRating(int rating, String feedback,String id,String idOrder)throws Exception{
        String sql="update tblOrderDetail set rating = ?, feed_back= ? where id_product=? and id_order= ?";
        try{
            cn=DBUtil.makeConnection();
            if(cn!=null){
                ps=cn.prepareStatement(sql);
                ps.setObject(1,rating);
                ps.setObject(2,feedback);
                ps.setObject(3, id);
                ps.setObject(4,idOrder);
                int check=ps.executeUpdate();
                if(check>0){
                    return true;
                }
            }
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return false;
    }
    public int getRatingStarByIdProduct(String id) throws Exception{
        String sql="select AVG(rating) as avg from tblOrderDetail where id_product=?";
        try{
            cn=DBUtil.makeConnection();
            if(null!=cn){
                ps=cn.prepareStatement(sql);
                ps.setObject(1,id);
                rs=ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("avg");
                }
            }
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return 0;
    }
    public HashMap<String, String> getFeedBack(String id)throws Exception{
        String sql="select od.feed_back, u.user_id from tblOrderDetail od,tblOrders o,tblUsers u where o.id=od.id_order and o.id_user=u.user_id and od.id_product=?";
        try{
            cn=DBUtil.makeConnection();
            if(cn!=null){
                ps=cn.prepareStatement(sql);
                ps.setObject(1, id);
                rs=ps.executeQuery();
                HashMap<String, String> result= new HashMap<>();
                while(rs.next()){
                    result.put(rs.getString("user_id"),rs.getString("feed_back"));
                }
                return result;
            }
            
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return null;
    }
    
    
}
