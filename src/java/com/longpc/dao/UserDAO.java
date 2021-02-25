/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longpc.dao;

import com.longpc.dto.UserDTO;
import com.longpc.util.DBUtil;
import java.sql.Timestamp;

/**
 *
 * @author ASUS
 */
public class UserDAO extends BaseDAO {

    public UserDTO checkLogin(String userId, String password) throws Exception {
        String sql = "select user_id, password,name,status from tblUsers where user_id = ? and password = ?";
        UserDTO udto= new UserDTO();
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                ps.setObject(1, userId);
                ps.setObject(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String userIdCheck = rs.getString("user_id");
                    String userPasswordCheck = rs.getString("password");
                    if(userIdCheck.equals(userId)&&userPasswordCheck.equals(password)){
                        udto.setUserId(userId);
                        udto.setPassword(password);
                        udto.setName(rs.getString("name"));
                        udto.setStatus(rs.getString("status"));
                        return udto;
                    }
                }
            }
        } finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return null;
    }

    public boolean addNew(UserDTO userDTO) throws Exception {
        String sql = "insert into tblUsers(user_id,password,name,phone,address,create_date,status) values(?,?,?,?,?,?,?)";
        try {
            cn = DBUtil.makeConnection();
            if (cn != null) {
                ps = cn.prepareStatement(sql);
                ps.setObject(1, userDTO.getUserId());
                ps.setObject(2, userDTO.getPassword());
                ps.setObject(3, userDTO.getName());
                ps.setObject(4, userDTO.getPhone());
                ps.setObject(5, userDTO.getAddress());
                ps.setObject(6, new Timestamp(userDTO.getCreateDate().getTime()));
                ps.setObject(7,userDTO.getStatus());
                int check = ps.executeUpdate();
                if (check > 0) {
                    return true;
                }
            }
        } finally {
            DBUtil.closeConnection(cn, ps, rs);
        }
        return false;
    }
    public boolean activeUser(String id) throws Exception{
        String sql="update tblUsers set status = 'ACTIVE' where user_id = ? ";
        try{
            cn=DBUtil.makeConnection();
            if(cn!=null){
                ps=cn.prepareStatement(sql);
                ps.setObject(1,id);
                int check=ps.executeUpdate();
                if (check > 0) {
                    return true;
                }
            }
            
        }finally{
            DBUtil.closeConnection(cn, ps, rs);
        }
        return false;
    }

}
