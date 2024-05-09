/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter.article;

import dal.articledao.ArticleDAO;
import dal.userdao.UserDAO;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.article.Article;
import model.user.User;

import java.io.BufferedReader;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String method = req.getMethod();
        response.setContentType("application/json"); 
        response.setCharacterEncoding("UTF-8");
        UserDAO ud = new UserDAO();
        ArticleDAO ad = new ArticleDAO();
        BufferedReader reader = req.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        request.setAttribute("json", json);

        if (method.equals("POST")) { // đủ quyền mới cho vào post bài
            try {
                JSONObject jsonObject = new JSONObject(json.toString());
                int userId = jsonObject.getInt("userId");
                User u = (User) ud.getById(userId);
                if (u.getUser_role() > 0) {
                    chain.doFilter(request, response);
                } else {
                    String message = "{\"message: \" \"Not enough authority!\"}";
                    response.getWriter().write(message);
                }
            } catch (JSONException ex) {
                response.getWriter().write("{\"message\": \"Json sai.\"}");
            }
        } else if (method.equals("DELETE")) { 
            boolean ok = false;
            try {  // phải là chủ hoặc admin mới xóa được bài, xóa khi có người ấn xóa
                JSONObject jsonObject = new JSONObject(json.toString());
                int userId = jsonObject.getInt("userId");
                int articleId = jsonObject.getInt("articleId");
                User u = (User) ud.getById(userId);
                Article a = (Article) ad.getById(articleId);
                ok = true;
                if (u.getUser_role() == 2) {
                    chain.doFilter(request, response);
                } else if (u.getUser_role() == 1 && a.getUserId() == userId) {
                    chain.doFilter(request, response);
                } else {
                    String message = "{\"message: \" \"Not enough authority!\"}";
                    response.getWriter().write(message);
                }
            }  catch (JSONException ex) {
            }
            
            if (!ok) {
                try { // xóa khi chủ bài viết xóa tài khoản
                    JSONObject jsonObject = new JSONObject(json.toString());
                    boolean byUser = jsonObject.getBoolean("byUser");
                    int userId = jsonObject.getInt("userId");
                    chain.doFilter(request, response);
                } catch (JSONException ex) {
                }
            }
            
        } else if (method.equals("GET")) { // phải là admin mới được vào trang duyệt bài
            String uncensored = request.getParameter("uncensored");
            if (uncensored != null) {
                String userId = request.getParameter("userId");
                User u = (User) ud.getById(Integer.parseInt(userId));
                if (u.getUser_role() != 2) {
                    String message = "{\"message: \" \"Not enough authority!\"}";
                    response.getWriter().write(message);
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } else if (method.equals("PATCH")) {
            chain.doFilter(request, response);
        }
    }
}