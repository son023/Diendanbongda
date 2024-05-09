/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.articledao;

import dal.dao.DAO;
import model.article.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hanh
 */
public class ReportDAO extends DAO{

    @Override
    public Object getById(int Record_id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addObject(Object object) {
        try {
            Report report = (Report)object;
            String sql = "insert into report_article(user_id, comment_id, content) value(?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, report.getUserId());
            st.setInt(2, report.getArticleId());
            st.setString(3, report.getContent());
            st.executeUpdate();
//            System.out.println("scs");
            return true;
        } catch (SQLException e) {
//            System.out.println(e);
            return false;
        }        
    }

    @Override
    public boolean updateObject(Object object) {
        try {
            Report report = (Report)object;
            String sql = "update report_article set content = ? where report_article_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, report.getContent());
            st.setInt(2, report.getReportId());
            
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
            String sql = "delete from report_article where report_aritcle_id = " + objectId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
//            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<Object> getAllObjects() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public ArrayList<Report> getListByArticle(int article_id) {
        try {
            ArrayList<Report> arr = new ArrayList<>();
            String sql = "select * from report_article where aritcle_id = " + article_id;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                arr.add(new Report(rs.getInt("report_article_id"), rs.getInt("user_id"),
                    article_id, rs.getString("content")));
            }
            return arr;
        } catch (SQLException e) {
            
        }
        return null;
    }
    
    public Report getByUserAndArticle(int user_id, int article_id) {
        try {
            ArrayList<Report> arr = new ArrayList<>();
            String sql = "select * from report_article where user_id = ? and aritcle_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, article_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Report(rs.getInt("report_article_id"), user_id,
                    article_id, rs.getString("content"));
            }
        } catch (SQLException e) {
            
        }
        return null;
    }
}