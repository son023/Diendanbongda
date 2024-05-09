/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter.article;

import dal.articledao.ArticleDAO;
import dal.articledao.CommentDAO;
import dal.userdao.UserDAO;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.article.Article;
import model.article.Comment;
import model.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
public class CommentFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String method = req.getMethod();
        response.setContentType("application/json"); 
        response.setCharacterEncoding("UTF-8");
        UserDAO ud = new UserDAO();
        ArticleDAO ad = new ArticleDAO();
        CommentDAO cd = new CommentDAO();
        BufferedReader reader = req.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        request.setAttribute("json", json);
        
        if (method.equals("DELETE")) {
            boolean ok = false;
            try { // có người ấn nút xóa comment
                JSONObject jsonObject = new JSONObject(json.toString());
                int commentId = jsonObject.getInt("commentId");
                int userId = jsonObject.getInt("userId");
                User u = (User) ud.getById(userId);
                Comment cmt = (Comment) cd.getById(commentId);
                Article art = (Article) ad.getById(cmt.getArticleId());
                ok = true;
                
                // admin, chu bai viet, chu cmt duoc xoa cmt
                if (u.getUser_role() == 2 || art.getUserId() == userId || cmt.getUserId() == userId) {
                    chain.doFilter(request, response);
                } else {
                    String message = "{\"message: \" \"Not enough authority!\"}";
                    response.getWriter().write(message);
                }
            } catch (JSONException ex) {
            }
            
            if (!ok) {
                try { // xóa comment theo bài viết
                    JSONObject jsonObject = new JSONObject(json.toString());
                    boolean byArticle = jsonObject.getBoolean("byArticle");
                    ok = true;
                    chain.doFilter(request, response);
                } catch (JSONException ex) {
                }
            }
            
            if (!ok) {
                try { // xóa comment theo user
                    JSONObject jsonObject = new JSONObject(json.toString());
                    boolean byUser = jsonObject.getBoolean("byUser");
                    chain.doFilter(request, response);
                } catch (JSONException ex) {
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}