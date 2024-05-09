/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.articledao;

import dal.dao.DAO;
import model.article.ReactionComment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hanh
 */
public class ReactionCommentDAO extends DAO{

    @Override
    public Object getById(int Record_id) {
        CommentDAO cd = new CommentDAO();
        try {
            String sql = "select * from reaction_comments where reaction_comment_id = " + Record_id;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new ReactionComment(rs.getInt("reaction_comment_id"), rs.getBoolean("reaction_type"), 
                rs.getInt("user_id"), rs.getInt("comment_id"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public boolean addObject(Object object) {
        try {
            ReactionComment react = (ReactionComment)object;
            String sql = "insert into reaction_comments(user_id, comment_id, reaction_type) "
                    + "value(?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, react.getUserId());
            st.setInt(2, react.getCommentId());
            st.setBoolean(3, react.isReationType());
            st.executeUpdate();
            System.out.println("scs");
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean updateObject(Object object) {
        try {
            ReactionComment react = (ReactionComment)object;
            String sql = "update reaction_comments set reaction_type = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setBoolean(1, react.isReationType());
            st.executeUpdate();
//            System.out.println("scs");
            return true;
        } catch (SQLException e) {
//            System.out.println(e);
            return false;
        }   
    }

    @Override
    public boolean deleteObject(int objectId) {
        try {
            String sql = "delete from reaction_comments where reaction_comment_id = " + objectId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    public boolean deleteListRC (String criteria) {
        try {
            String sql = "delete from reaction_comments where " + criteria;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Object> getAllObjects() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<ReactionComment> getListRC (String criteria) { // lấy theo tiêu chí
        try {
            String sql = "select * from reaction_comments where " + criteria;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ArrayList<ReactionComment> list = new ArrayList<>();
            while (rs.next()) {
                list.add( new ReactionComment(rs.getInt("reaction_comment_id"), 
                        rs.getBoolean("reaction_type"), rs.getInt("user_id"), rs.getInt("comment_id")));
            }
            return list;
        } catch (SQLException ex) {
        }
        return null;
    }
    
    public ReactionComment getByCommentAndUser(int comment_id, int user_id) {
        try {
            String sql = "select * from reaction_comments where user_id = ? and comment_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, comment_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new ReactionComment(rs.getInt("reaction_comment_id"), 
                        rs.getBoolean("reaction_type"), rs.getInt("user_id"), rs.getInt("comment_id"));
            }
        } catch (SQLException ex) {
        }
        return null;
    }
}