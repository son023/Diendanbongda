/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.article;

import dal.articledao.ReactionArticleDAO;

import com.google.gson.Gson;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.article.ReactionArticle;

import java.io.BufferedReader;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ReactionArticleServlet", urlPatterns = { "/reaction_article" })
public class ReactionArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ReactionArticleDAO rad = new ReactionArticleDAO();
        String json = "";
        Gson gson = new Gson();
        String reactionArticleId = request.getParameter("reactionArticleId");
        String userId = request.getParameter("userId");
        String articleId = request.getParameter("articleId");

        if (reactionArticleId != null) { // Lấy ra reactionAritcle theo id
            ReactionArticle ra = (ReactionArticle) rad.getById(Integer.parseInt(reactionArticleId));
            json = gson.toJson(ra);
        }

        else if (articleId != null && userId != null) { // Lấy ra reactionAritcle theo user và article
            ReactionArticle ra = rad.getByArticleAndUser(Integer.parseInt(articleId), Integer.parseInt(userId));
            json = gson.toJson(ra);
        }

        else if (articleId != null) { // Lấy ra list reactionAritcle article
            String criteria = "article_id = " + articleId;
            ArrayList<ReactionArticle> list = rad.getListRA(criteria);
            json = gson.toJson(list);
        }

        else if (userId != null) { // Lấy ra list reactionAritcle user
            String criteria = "user_id = " + userId;
            ArrayList<ReactionArticle> list = rad.getListRA(criteria);
            json = gson.toJson(list);
        }
        response.getWriter().write(json);
    }

    // tạo 1 reactionArticle mới
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        try {
            JSONObject jsonObject = new JSONObject(json.toString());
            int userId = jsonObject.getInt("userId");
            int articleId = jsonObject.getInt("articleId");
            boolean reactionType = jsonObject.getBoolean("reactionType");
            ReactionArticleDAO rad = new ReactionArticleDAO();
            ReactionArticle ra = new ReactionArticle(0, reactionType, userId, articleId);
            rad.addObject(ra);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Post reactionArticle successfully!\"}");
        } catch (JSONException ex) {
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ReactionArticleDAO rad = new ReactionArticleDAO();

        BufferedReader reader = req.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        boolean ok = false;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        String reactionId = req.getParameter("reactionArticleId");
        String articleId = req.getParameter("articleId");
        String userId = req.getParameter("userId");
        if (!ok && reactionId != null) {
            try { // xóa 1 reactionArticle theo id
                  // JSONObject jsonObject = new JSONObject(json.toString());
                  // int reactionId = jsonObject.getInt("reactionArticleId");
                rad.deleteObject(Integer.parseInt(reactionId));
                ok = true;
                resp.getWriter().write("{\"message\": \"1\"}");
            } catch (JSONException ex) {
            }
        }

        if (!ok && userId != null && articleId != null) {
            try { // xóa 1 reactionArticle theo userId và articleId
                  // JSONObject jsonObject = new JSONObject(json.toString());
                rad.deleteRAByCriteria("article_id = " + Integer.parseInt(articleId) + " and user_id = " + Integer.parseInt(userId));
                ok = true;
                resp.getWriter().write("{\"message\": \"2\"}");
            } catch (JSONException ex) {
            }
        }

        if (!ok && articleId != null) {
            try { // xóa list reactionArticle theo articleId
                  // JSONObject jsonObject = new JSONObject(json.toString());
                rad.deleteRAByCriteria("article_id = " + Integer.parseInt(articleId));
                ok = true;
                resp.getWriter().write("{\"message\": \"3\"}");
            } catch (JSONException ex) {
            }
        }

        if (!ok && userId != null) {
            try { // xóa list reactionArticle theo userId
                  // JSONObject jsonObject = new JSONObject(json.toString());
                rad.deleteRAByCriteria("user_id = " + Integer.parseInt(userId));
                ok = true;
                resp.getWriter().write("{\"message\": \"4\"}");
            } catch (JSONException ex) {
            }
        }
    }
}