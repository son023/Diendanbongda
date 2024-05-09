/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.articledao;

import dal.dao.DAO;
import model.article.Article;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hanh
 */
public class ArticleDAO extends DAO{

    @Override
    public Object getById(int Article_id) {
        String sql = "select * from articles where article_id = " + Article_id;
        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Article(rs.getInt("article_id"), rs.getInt("likes"),
                rs.getInt("dislikes"), rs.getInt("reports"), rs.getString("article_name"),
                rs.getString("article_category"), rs.getString("article_description"),
                rs.getString("content"), rs.getString("image"), rs.getTimestamp("time_submit"),
                rs.getTimestamp("time_accept"), rs.getBoolean("stt"), rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public boolean addObject(Object object) {
        try {
            Article art = (Article)object;
            String sql = "insert into articles(article_name, article_category, article_description,"
                    + "content, image, time_submit, time_accept, likes, dislikes, reports,"
                    + "user_id, stt) value(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, art.getArticleName());
            st.setString(2, art.getArticleCategory());
            st.setString(3, art.getArticleDescription());
            st.setString(4, art.getContent());
            st.setString(5, art.getImage());
            st.setTimestamp(6, art.getTimeSubmit());
            st.setTimestamp(7, art.getTimeAccept());
            st.setInt(8, art.getLikes());
            st.setInt(9, art.getDislikes());
            st.setInt(10, art.getReports());
            st.setInt(11, art.getUserId());
            st.setBoolean(12, art.isStt());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {      
            return false;
        }
    }

    @Override
    public boolean updateObject(Object object) {
        try {
            Article art = (Article)object;
            String sql = "update articles set article_name = ?, article_category = ?,"
                    + " article_description = ?, content = ?, image = ?,"
                    + " likes = ?, dislikes = ?, reports = ?, stt = ?, time_accept = ? where article_id = " 
                    + art.getArticleId();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, art.getArticleName());
            st.setString(2, art.getArticleCategory());
            st.setString(3, art.getArticleDescription());
            st.setString(4, art.getContent());
            st.setString(5, art.getImage());
            st.setInt(6, art.getLikes());
            st.setInt(7, art.getDislikes());
            st.setInt(8, art.getReports());
            st.setBoolean(9, art.isStt());
            st.setTimestamp(10, art.getTimeAccept());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteObject(int objectId) {
        try {
            String sql = "delete from articles where article_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, objectId);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean deleteByUserId(int userId) { // xóa toàn bộ bài viết của 1 user
        try {
            String sql = "delete from articles where user_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, userId);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Object> getAllObjects() {
        return null;
    }
    
    public ArrayList<Article> getListArticle(String criteria) { // lấy list article theo tiêu chí
        try {
            String sql = "select * from articles where " + criteria;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            ArrayList<Article> arr = new ArrayList<>();
            while (rs.next()) {
                arr.add(new Article(rs.getInt("article_id"), rs.getInt("likes"),
                rs.getInt("dislikes"), rs.getInt("reports"), rs.getString("article_name"),
                rs.getString("article_category"), rs.getString("article_description"),
                rs.getString("content"), rs.getString("image"), rs.getTimestamp("time_submit"),
                rs.getTimestamp("time_accept"), rs.getBoolean("stt"), rs.getInt("user_id")));
            }
            return arr;
        } catch (SQLException e) {
        }
        return null; 
    }
}