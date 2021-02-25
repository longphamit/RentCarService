/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.dao;

import com.longpc.util.DBUtil;

/**
 *
 * @author ASUS
 */
public class DiscountDAO extends BaseDAO {

    public double checkDiscount(String id, String userId) throws Exception {
        String sql = "select id_discount from tblOrders where id_user=? and id_discount=?";
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                ps.setObject(1, userId);
                ps.setObject(2, id);
                rs=ps.executeQuery();
                if (rs.next()) {
                    return -2;
                } else {
                    DBUtil.closeConnection(cn, ps, rs);
                    try {
                        sql = "select discount,id from tblDiscounts where id =? and expired_date >= GETDATE() ";
                        try {
                            cn = DBUtil.makeConnection();
                            if (cn != null) {
                                ps = cn.prepareStatement(sql);
                                ps.setObject(1, id);
                                rs = ps.executeQuery();
                                if (rs.next()) {
                                    if (rs.getString("id").equals(id)) {
                                        return rs.getDouble("discount");
                                    }
                                }
                            }
                        } finally {
                            DBUtil.closeConnection(cn, ps, rs);
                        }
                        return -1;
                    } finally {
                        DBUtil.closeConnection(cn, ps, rs);
                    }
                }
            }
        } finally {
            DBUtil.closeConnection(cn, ps, rs);
        }
        return -1;
    }
}
