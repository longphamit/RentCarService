/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.dao;

import com.longpc.dto.CartDTO;
import com.longpc.dto.OrderDTO;
import com.longpc.dto.ProductDTO;
import com.longpc.dto.SearchOrderDTO;
import com.longpc.util.DBUtil;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class OrderDAO extends BaseDAO {

    public boolean makeOrder(OrderDTO orderDTO, CartDTO cartDTO) throws Exception {
        String sql = "insert into tblOrders(id,create_date,id_user,total,sum,id_discount,status) values (?,?,?,?,?,?,?)";
        String sqlOrderDetail = "insert into tblOrderDetail(id_order,id_product,price,from_date,to_date) values (?,?,?,?,?)";
        try {
            cn = DBUtil.makeConnection();
            cn.setAutoCommit(false);
            if (null != cn) {
                ps = cn.prepareStatement(sql);
                ps.setObject(1, orderDTO.getId());
                ps.setObject(2, new Timestamp(orderDTO.getCreateDate().getTime()));
                ps.setObject(3, orderDTO.getUserId());
                ps.setObject(4,orderDTO.getTotal());
                ps.setObject(5, orderDTO.getSum());
                ps.setObject(6, orderDTO.getDiscountId());
                ps.setObject(7,orderDTO.getStatus());
                ps.executeUpdate();

                ps = cn.prepareStatement(sqlOrderDetail);
                for (Map.Entry<String, List<ProductDTO>> entry : cartDTO.getCart().entrySet()) {
                    for (ProductDTO productDTO : entry.getValue()) {
                        ps.setObject(1, orderDTO.getId());
                        ps.setObject(2, entry.getKey());
                        ps.setObject(3, productDTO.getPrice());
                        ps.setObject(4, new Timestamp(productDTO.getFromDate().getTime()));
                        ps.setObject(5, new Timestamp(productDTO.getToDate().getTime()));
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
                cn.commit();
                return true;
            }
        } finally {
            DBUtil.closeConnection(cn, ps, rs);
        }
        return false;
    }
    public List<OrderDTO> getAllOrder(SearchOrderDTO searchOrderDTO) throws Exception{
        List<OrderDTO> orderDTOs= new ArrayList<>();
        String sql="select o.id,o.total,o.sum,o.create_date,o.id_discount,o.status from tblOrders o, tblOrderDetail od,tblProducts p where od.id_order=o.id and p.id=od.id_product and o.status=1 and o.id_user="+"'"+searchOrderDTO.getIdUser()+"' ";
        if(null!=searchOrderDTO.getName()){
            if(!searchOrderDTO.getName().isEmpty()){
                sql+=" and p.name like "+"'%"+searchOrderDTO.getName()+"%' ";
            }
        }
        if(null!=searchOrderDTO.getDate()){
            if(!searchOrderDTO.getDate().isEmpty()){
                sql+=" and o.create_date = "+"'"+searchOrderDTO.getDate()+"'";
            }
        }
        sql+=" order by o.id DESC";
        try{
            
            cn=DBUtil.makeConnection();
            if(cn!=null){
                ps=cn.prepareStatement(sql);
                rs=ps.executeQuery();
                while(rs.next()){
                    OrderDTO orderDTO= new OrderDTO();
                    orderDTO.setId(rs.getString("id"));
                    orderDTO.setTotal(rs.getDouble("total"));
                    orderDTO.setSum(rs.getDouble("sum"));
                    orderDTO.setCreateDate(rs.getDate("create_date"));
                    orderDTO.setDiscountId(rs.getString("id_discount"));
                    orderDTO.setStatus(rs.getBoolean("status"));
                    orderDTOs.add(orderDTO);
                }
                return orderDTOs;
            }
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return null;
    }
    public List<OrderDTO> getAllOrderRemoved(SearchOrderDTO searchOrderDTO) throws Exception{
        List<OrderDTO> orderDTOs= new ArrayList<>();
        String sql="select o.id,o.total,o.sum,o.create_date,o.id_discount,o.status from tblOrders o, tblOrderDetail od,tblProducts p where od.id_order=o.id and p.id=od.id_product and o.status=0 and o.id_user="+"'"+searchOrderDTO.getIdUser()+"' ";
        sql+=" order by o.id DESC";
        try{
            
            cn=DBUtil.makeConnection();
            if(cn!=null){
                ps=cn.prepareStatement(sql);
                rs=ps.executeQuery();
                while(rs.next()){
                    OrderDTO orderDTO= new OrderDTO();
                    orderDTO.setId(rs.getString("id"));
                    orderDTO.setTotal(rs.getDouble("total"));
                    orderDTO.setSum(rs.getDouble("sum"));
                    orderDTO.setCreateDate(rs.getDate("create_date"));
                    orderDTO.setDiscountId(rs.getString("id_discount"));
                    orderDTO.setStatus(rs.getBoolean("status"));
                    orderDTOs.add(orderDTO);
                }
                return orderDTOs;
            }
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return null;
    }
    
    
    
    public boolean setStatusFalse(String []arrayId) throws Exception{
        String sql="update tblOrders set status = 0 where id =?";
        try{
            cn=DBUtil.makeConnection();
            if(cn!=null){
                cn.setAutoCommit(false);
                ps=cn.prepareStatement(sql);
                for(int i=0;i<arrayId.length;i++){
                    ps.setObject(1,arrayId[i]);
                    ps.addBatch();
                }
                ps.executeBatch();
                cn.commit();
                return true;
            }
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return false;
    }
    
}
