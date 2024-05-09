/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.articledao;

import dal.dao.DAO;
import model.article.ReactionArticle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hanh
 */
public class ReactionArticleDAO extends DAO{

    @Override
    public Object getById(int Record_id) {
        ArticleDAO ad = new ArticleDAO();
        try {
            String sql = "select * from reaction_articles where reaction_article_id = " + Record_id;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new ReactionArticle(rs.getInt("reaction_article_id"), rs.getBoolean("reaction_type"), 
                rs.getInt("user_id"), rs.getInt("article_id"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public ReactionArticle getByArticleAndUser(int article_id, int user_id) {
        try {
            String sql = "select * from reaction_articles where user_id = ? and article_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, article_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new ReactionArticle(rs.getInt("reaction_article_id"), 
                        rs.getBoolean("reaction_type"), rs.getInt("user_id"), rs.getInt("article_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReactionArticleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean addObject(Object object) {
        try {
            ReactionArticle react = (ReactionArticle)object;
            String sql = "insert into reaction_articles(user_id, article_id, reaction_type) "
                    + "value(?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, react.getUserId());
            st.setInt(2, react.getArticleId());
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
            ReactionArticle react = (ReactionArticle)object;
            String sql = "update reaction_articles set reaction_type = ?";
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
            String sql = "delete from reaction_articles where reaction_article_id = " + objectId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteRAByCriteria(String criteria) { // xóa toàn bộ rA khi bài viết bị xóa
        try {
            String sql = "delete from reaction_articles where " + criteria;
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
    
    public ArrayList<ReactionArticle> getListRA(String criteria) { // lấy list theo tiêu chí
        try {
            String sql = "select * from reaction_articles where " + criteria;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ArrayList<ReactionArticle> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new ReactionArticle(rs.getInt("reaction_article_id"), rs.getBoolean("reaction_type"), 
                            rs.getInt("user_id"), rs.getInt("article_id")));
            }
            return list;
        } catch (SQLException e) {
        }
        return null;
    }
    
}