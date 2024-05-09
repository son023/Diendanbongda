/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.article;

import dal.articledao.CommentDAO;
import dal.articledao.ReactionCommentDAO;

import com.google.gson.Gson;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.article.Comment;
import model.article.ReactionComment;

import java.io.BufferedReader;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
@WebServlet(name="ReactionCommentServlet", urlPatterns={"/reaction_comment"})
public class ReactionCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String reactionCommentId = request.getParameter("reactionCommentId");
        String commentId = request.getParameter("commentId");
        String articleId = request.getParameter("articleId");
        String userId = request.getParameter("userId");
        String json = "";
        Gson gson = new Gson();
        
        ReactionCommentDAO rcd = new ReactionCommentDAO();
        CommentDAO cd = new CommentDAO();
        if (reactionCommentId != null) { // lấy rc theo id
            ReactionComment rc = (ReactionComment) rcd.getById(Integer.parseInt(reactionCommentId));
            json = gson.toJson(rc);
        } 
        
        else if (commentId != null && userId != null) { // lấy rc theo cmt và user
            ReactionComment rc = rcd.getByCommentAndUser(Integer.parseInt(commentId), Integer.parseInt(userId));
            json = gson.toJson(rc);
        } 
        
        else if (commentId != null) { // lấy list rc theo cmt
            String criteria = "comment_id = " + commentId;
            ArrayList<ReactionComment> list = rcd.getListRC(criteria);
            json = gson.toJson(list);
        } 
        
        else if (userId != null) { // lấy list rc theo user
            String criteria = "user_id = " + userId;
            ArrayList<ReactionComment> list = rcd.getListRC(criteria);
            json = gson.toJson(list);
        } 
        
        else if (articleId != null) { // lấy list rc theo article
            String criteria = "article_id = " + articleId;
            ArrayList<Comment> listCmt = cd.getListComment(criteria);
            ArrayList<ReactionComment> listRC = new ArrayList<>();
            for (Comment c : listCmt) {
                criteria = "comment_id = " + c.getCommentId();
                listRC.addAll(rcd.getListRC(criteria));
            }
            json = gson.toJson(listRC);
        }
        
        response.setContentType("application/json"); 
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    } 
    
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
            int commentId = jsonObject.getInt("commentId");
            boolean reactionType = jsonObject.getBoolean("reactionType");
            ReactionCommentDAO rad = new ReactionCommentDAO();
            ReactionComment ra = new ReactionComment(0, reactionType, userId, commentId);
            rad.addObject(ra);
            response.setContentType("application/json"); 
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Post reactionComment successfully!\"}");
        } catch (JSONException ex) {
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json"); 
        resp.setCharacterEncoding("UTF-8");
        ReactionCommentDAO rcd = new ReactionCommentDAO();
        
        BufferedReader reader = req.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        boolean ok = false;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        
        if (!ok) {
            try { // xóa 1 reactionComment theo id
                JSONObject jsonObject = new JSONObject(json.toString());
                int reactionId = Integer.parseInt(req.getParameter("reactionCommentId"));
                rcd.deleteObject(reactionId);
                ok = true;
                resp.getWriter().write("{\"message\": \"1\"}");
            } catch (JSONException ex) {
            }
        }
        
        if (!ok) {
            try { // xóa 1 reactionComment userId và commentId
                JSONObject jsonObject = new JSONObject(json.toString());
                int commentId = Integer.parseInt(req.getParameter("commentId"));
                int userId = Integer.parseInt(req.getParameter("userId"));
                String criteria = "comment_id = " + commentId + " and user_id = " + userId;
                rcd.deleteListRC(criteria);
                ok = true;
                resp.getWriter().write("{\"message\": \"2\"}");
            } catch (JSONException ex) {
            }
        }
        
        if (!ok) {
            try { // xóa 1 list rc theo comment
                JSONObject jsonObject = new JSONObject(json.toString());
                int commentId = Integer.parseInt("commentId");
                String criteria = "comment_id = " + commentId;
                rcd.deleteListRC(criteria);
                ok = true;
                resp.getWriter().write("{\"message\": \"3\"}");
            } catch (JSONException ex) {
            }
        }
        
        if (!ok) {
            try { // xóa 1 list rc theo user
                JSONObject jsonObject = new JSONObject(json.toString());
                int userId = Integer.parseInt(req.getParameter("userId"));
                String criteria = "user_id = " + userId;
                rcd.deleteListRC(criteria);
                ok = true;
                resp.getWriter().write("{\"message\": \"4\"}");
            } catch (JSONException ex) {
            }
        }
        
        if (!ok) {
            try { // xóa 1 list rc theo article
                JSONObject jsonObject = new JSONObject(json.toString());
                int articleId = Integer.parseInt(req.getParameter("articleId"));
                CommentDAO cd = new CommentDAO();
                String criteria = "article_id = " + articleId;
                ArrayList<Comment> listCmt = cd.getListComment(criteria);
                for (Comment c : listCmt) {
                    criteria = "comment_id = " + c.getCommentId();
                    rcd.deleteListRC(criteria);
                }
                ok = true;
                resp.getWriter().write("{\"message\": \"5\"}");
            } catch (JSONException ex) {
            }
        }
    }
}