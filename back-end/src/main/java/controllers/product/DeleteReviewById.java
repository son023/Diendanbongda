

package controllers.product;

import static common.product.Constant.URL_REVIEW_DELETE_BY_ID;

import dal.productdao.ReviewDAO;
import dal.userdao.UserDAO;
import helper.JSONHelper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.User;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DELL
 */
@WebServlet(name="deleteReviewById", urlPatterns={URL_REVIEW_DELETE_BY_ID})
public class DeleteReviewById extends HttpServlet {
   
    // xoá Review theo id
    private ReviewDAO reviewDAO=new ReviewDAO();
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> res = new HashMap<> ();
        // UserDAO userDAO=new UserDAO();
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        try {
        //     Cookie[] cookies = req.getCookies();
        //     if (cookies == null) {
        //             res.put("message", "thieu cookie");
        //             JSONHelper.sendJsonAsResponse(resp, 400, res);
        //     }
        //     for (Cookie cookie : cookies) {
        //             if (cookie.getName().equals("user_id")) {
        //                     int user_id = Integer.parseInt(cookie.getValue());
        //                     User currentUser = (User) userDAO.getById(user_id);
        //                     if (currentUser == null) {
        //                             res.put("message", "wrong user id");
        //                             JSONHelper.sendJsonAsResponse(resp, 400, res);
        //                             return;
        //                     }
        //                     if (currentUser.getUser_role() != 2) {
        //                             res.put("message", "ko phai admin");
        //                             JSONHelper.sendJsonAsResponse(resp, 401, res);
        //                             return;
        //                     }
        //                     break;
        //             }
        //     }
            boolean isSuccess = reviewDAO.deleteObject(id);
            if (isSuccess) {
                    res.put("message", "Success");	
                    JSONHelper.sendJsonAsResponse(resp, 200, res);
            }
            else {
                    res.put("message", "server's error");	
                    JSONHelper.sendJsonAsResponse(resp, 500, res);
            }
        } 
        catch (NumberFormatException e) {
                res.put("message", "bad request, check url params again");	
                JSONHelper.sendJsonAsResponse(resp, 400, res);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}