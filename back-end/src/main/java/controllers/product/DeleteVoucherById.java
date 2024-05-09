/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.product;

import static common.product.Constant.URL_VOUCHER_DELETE_VOUCHER_BY_ID;

import dal.productdao.VoucherDAO;
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
@WebServlet(name="deleteVoucherById", urlPatterns={URL_VOUCHER_DELETE_VOUCHER_BY_ID})
public class DeleteVoucherById extends HttpServlet {
    // xoá voucher theo id
    private VoucherDAO voucherDAO=new VoucherDAO();
    UserDAO userDAO=new UserDAO();
        
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> res = new HashMap<> ();
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
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            boolean isSuccess = voucherDAO.deleteObject(id);
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
