/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.articledao;

import dal.dao.DAO;
import model.article.Comment;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hanh
 */
public class CommentDAO extends DAO{
    ArticleDAO ad = new ArticleDAO();

    @Override
    public Object getById(int Record_id) {
        try {
            String sql = "select * from comments where comment_id = " + Record_id;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Comment(rs.getInt("comment_id"), rs.getInt("likes"), 
                        rs.getInt("dislikes"), rs.getString("comment_content"), 
                        rs.getTimestamp("comment_time"), rs.getInt("article_id"),
                        rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public boolean addObject(Object object) {
        try {
            Comment comment = (Comment)object;
            String sql = "insert into comments(article_id, user_id, comment_content,"
                    + " comment_time, likes, dislikes) value(?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, comment.getArticleId());
            st.setInt(2, comment.getUserId());
            st.setString(3, comment.getCommentContent());
            st.setTimestamp(4, comment.getCommentTime());
            st.setInt(5, comment.getLikes());
            st.setInt(6, comment.getDislikes());
            st.executeUpdate();
//            System.out.println("scs");
            return true;
        } catch (SQLException e) {
//            System.out.println(e);
            return false;
        }
    }

    public int addComment(Object object) {

        try {
            Comment comment = (Comment)object;
            String sql = "insert into comments(article_id, user_id, comment_content,"
                    + " comment_time, likes, dislikes) value(?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, comment.getArticleId());
            st.setInt(2, comment.getUserId());
            st.setString(3, comment.getCommentContent());
            st.setTimestamp(4, comment.getCommentTime());
            st.setInt(5, comment.getLikes());
            st.setInt(6, comment.getDislikes());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            else {
                return -1;
            }
//            System.out.println("scs");
        } catch (SQLException e) {
//            System.out.println(e);
            return -1;
        }
    }

    @Override
    public boolean updateObject(Object object) {
        try {
            Comment cmt = (Comment)object;
            String sql = "update comments set comment_content = ?, comment_time = ?,"
                    + " likes = ?, dislikes = ? where comment_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, cmt.getCommentContent());
            st.setTimestamp(2, cmt.getCommentTime());
            st.setInt(3, cmt.getLikes());
            st.setInt(4, cmt.getDislikes());
            st.setInt(5, cmt.getCommentId());
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
            String sql = "delete from comments where comment_id = " + objectId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
//            System.out.println(e);
            return false;
        }
    }
    
    public boolean deleteByArticleId(int articleId) { // xóa toàn bộ comment trong 1 bài viết
        try {
            String sql = "delete from comments where article_id = " + articleId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean deleteByUserId(int userId) { // xóa toàn bộ comment của 1 user
        try {
            String sql = "delete from comments where user_id = " + userId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Object> getAllObjects() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Comment> getListComment (String criteria) {
        try {
            String sql = "select * from comments where " + criteria;
            PreparedStatement st = con.prepareStatement(sql);
            ArrayList<Comment> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(new Comment(rs.getInt("comment_id"), rs.getInt("likes"), 
                        rs.getInt("dislikes"), rs.getString("comment_content"), 
                        rs.getTimestamp("comment_time"), rs.getInt("article_id"), 
                        rs.getInt("user_id")));
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}