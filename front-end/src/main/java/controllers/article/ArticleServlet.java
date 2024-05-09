/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.article;

import com.google.gson.Gson;

import dal.articledao.ArticleDAO;
import dal.userdao.UserDAO;

import java.io.BufferedReader;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.article.Article;
import model.user.User;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Hanh
 */
@WebServlet(name = "ArticleServlet", urlPatterns = { "/article" })
public class ArticleServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            this.doPatch(req, resp);
        }

        else {
            super.service(req, resp);
        }
    }

    // cập nhật số like, dislike của bài viết hoặc duyệt bài
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // StringBuilder json = (StringBuilder) req.getAttribute("json");

        BufferedReader reader = req.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        ArticleDAO ad = new ArticleDAO();
        Gson gson = new Gson();
        String jsonString = "";
        boolean ok = false;

        if (!ok) {
            try { // cập nhật số like, dislike của bài viết
                JSONObject jsonObject = new JSONObject(json.toString());
                int articleId = jsonObject.getInt("articleId");
                int likes = jsonObject.getInt("likes");
                int dislikes = jsonObject.getInt("dislikes");
                Article a = (Article) ad.getById(articleId);
                a.setLikes(likes);
                a.setDislikes(dislikes);
                ad.updateObject(a);
                jsonString = gson.toJson(a);
                ok = true;
            } catch (JSONException e) {
            }
        }

        if (!ok) {
            try { // duyệt bài viết
                JSONObject jsonObject = new JSONObject(json.toString());
                int articleId = jsonObject.getInt("articleId");
                boolean stt = jsonObject.getBoolean("stt");
                Article a = (Article) ad.getById(articleId);
                Timestamp now = new Timestamp(System.currentTimeMillis());
                a.setTimeAccept(now);
                a.setStt(stt);
                ad.updateObject(a);
                jsonString = gson.toJson(a);
                ok = true;
            } catch (JSONException e) {
            }
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonString);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id"); // article_id
        String category = request.getParameter("category");
        String uncensored = request.getParameter("uncensored"); // bài chưa duyệt
        String sortBy = request.getParameter("sortBy"); // sắp xếp theo tiêu chí nào?
        String userId = request.getParameter("userId"); // lấy những bài viết của user theo thứ tự mới đến cũ
        String featured = request.getParameter("featured"); // lấy những bài viết nổi bật(>= 100 reactions, mới viết
                                                            // trong vòng 7 ngày)
        String accepted = request.getParameter("accepted"); // lấy những bài viết đã duyệt
        // không có parameter là lấy hết

        ArticleDAO ad = new ArticleDAO();
        Gson gson = new Gson();
        String json = "";

        if (id != null) { // lấy bởi id
            Article art = (Article) ad.getById(Integer.parseInt(id));
            json = gson.toJson(art);
        }

        else if (featured != null) { // lấy nhưng bài viết nổi bật(>= 100 reactions, mới viết trong vòng 7 ngày)
            LocalDate today = LocalDate.now();
            LocalDate sevenDaysBefore = today.minusDays(7);
            String d = sevenDaysBefore.toString();
            String criteria = "DATE(time_accept) > '" + d + "' and (likes + dislikes) >= 100";
            ArrayList<Article> arr = ad.getListArticle(criteria);
            Collections.sort(arr, new Comparator<Article>() { // ưu tiên: số react > like > thời gian
                @Override
                public int compare(Article o1, Article o2) {
                    if ((o2.getLikes() + o2.getDislikes()) != (o1.getLikes() + o1.getDislikes()))
                        return (o2.getLikes() + o2.getDislikes()) - (o1.getLikes() + o1.getDislikes());
                    else if (o1.getLikes() != o2.getLikes())
                        return o2.getLikes() - o1.getLikes();
                    return o2.getTimeAccept().compareTo(o1.getTimeAccept());
                }
            });
            json = gson.toJson(arr);
        }

        else if (category != null) { // lấy bởi danh mục
            String criteria = "article_category = '" + category + "' and stt = 1";
            ArrayList<Article> arr = ad.getListArticle(criteria);
            Collections.sort(arr, new Comparator<Article>() { // sắp xếp theo thứ tự thời gian gần nhất
                @Override
                public int compare(Article o1, Article o2) {
                    return o2.getTimeAccept().compareTo(o1.getTimeAccept());
                }
            });
            json = gson.toJson(arr);
        }

        else if (uncensored != null) { // lấy những article đang chờ duyệt
            String criteria = "stt = 0";
            ArrayList<Article> arr = ad.getListArticle(criteria);
            json = gson.toJson(arr);
        }

        else if (sortBy != null) { // lấy những article theo thứ tự sắp xếp
            String criteria = "stt = 1";
            ArrayList<Article> arr = ad.getListArticle(criteria);

            if (sortBy.equals("likes")) { // sort theo like
                Collections.sort(arr, new Comparator<Article>() {
                    @Override
                    public int compare(Article o1, Article o2) {
                        return o2.getLikes() - o1.getLikes();
                    }
                });
            }

            else if (sortBy.equals("scores")) { // sort theo điểm (likes - dislikes)
                Collections.sort(arr, new Comparator<Article>() {
                    @Override
                    public int compare(Article o1, Article o2) {
                        return (o2.getLikes() - o2.getDislikes()) - (o1.getLikes() - o1.getDislikes());
                    }
                });
            }

            else if (sortBy.equals("newest")) { // sort theo bài mới nhất
                Collections.sort(arr, new Comparator<Article>() {
                    @Override
                    public int compare(Article o1, Article o2) {
                        return o2.getTimeAccept().compareTo(o1.getTimeAccept());
                    }
                });
            }

            json = gson.toJson(arr);
        }

        else if (userId != null) { // lấy những bài viết đã được duyệt của userId theo thứ tự mới nhất
            String criteria = "user_id = " + userId + " and stt = 1";
            ArrayList<Article> arr = ad.getListArticle(criteria);
            Collections.sort(arr, new Comparator<Article>() {
                @Override
                public int compare(Article o1, Article o2) {
                    return o2.getTimeAccept().compareTo(o1.getTimeAccept());
                }
            });
            json = gson.toJson(arr);
        }

        else if (accepted != null) { // lấy toàn bộ bài viết đã được duyệt
            String criteria = "stt = 1";
            ArrayList<Article> arr = ad.getListArticle(criteria);
            json = gson.toJson(arr);
        }

        else {
            String criteria = "1";
            ArrayList<Article> arr = ad.getListArticle(criteria);
            json = gson.toJson(arr);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // tạo bài viết
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // StringBuilder json = (StringBuilder) request.getAttribute("json");
            BufferedReader reader = request.getReader();
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            JSONObject jsonObject = new JSONObject(json.toString());
            int userId = jsonObject.getInt("userId");
            String articleName = jsonObject.getString("articleName");
            String image = jsonObject.getString("image");
            String content = jsonObject.getString("content");
            String articleCategory = jsonObject.getString("articleCategory");
            String articleDescription = jsonObject.getString("articleDescription");

            UserDAO ud = new UserDAO();
            User user = (User) ud.getById(userId);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            boolean stt = false;
            if (user.getUser_role() == 2) {
                stt = true;
            }
            Article art = new Article(0, 0, 0, 0, articleName, articleCategory,
                    articleDescription, content, image, now, now, stt, userId);
            ArticleDAO ad = new ArticleDAO();
            ad.addObject(art);
            String jsonString;
            if (stt) {
                jsonString = "{\"message\": \"Your article has been posted.\"}";
            } else {
                jsonString = "{\"message\": \"Your post has been sent, admin will review and approve your post. Thank you for your contribution.\"}";
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonString);
        } catch (JSONException ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"Json sai.\"}");
        }
    }

    // xóa bài viết theo id
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        boolean ok = false;
        String articleId = request.getParameter("articleId");
        String byUser = request.getParameter("byUser");
        String userId = request.getParameter("userId");
        if (articleId != null) {
            try { // xóa khi có người ấn xóa bài viết
                  // StringBuilder json = (StringBuilder) request.getAttribute("json");

                // JSONObject jsonObject = new JSONObject(json.toString());
                ArticleDAO ad = new ArticleDAO();
                ad.deleteObject(Integer.parseInt(articleId));
                String jsonString = "{\"message\": \"Delete successfully.\"}";
                ok = true;
                response.getWriter().write(jsonString);
            } catch (JSONException ex) {
            }
        }

        if (byUser != null && userId != null) {
            try { // xóa theo userId
                  // StringBuilder json = (StringBuilder) request.getAttribute("json");

                // JSONObject jsonObject = new JSONObject(json.toString());
                ArticleDAO ad = new ArticleDAO();
                ad.deleteByUserId(Integer.parseInt(userId));
                String jsonString = "{\"message\": \"Delete successfully.\"}";
                ok = true;
                response.getWriter().write(jsonString);
            } catch (JSONException ex) {
            }
        }
    }
}