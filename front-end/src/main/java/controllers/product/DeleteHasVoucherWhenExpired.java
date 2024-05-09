/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.product;

import static common.product.Constant.URL_HAS_VOUCHER_DELETE_WHEN_EXPIRED;

import dal.productdao.HasVoucherDAO;
import dal.userdao.UserDAO;
import helper.JSONHelper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.HasVoucher;
import model.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
@WebServlet(name="deleteHasVoucherWhenExpired", urlPatterns={URL_HAS_VOUCHER_DELETE_WHEN_EXPIRED})
public class DeleteHasVoucherWhenExpired extends HttpServlet {
   
    // xoá hasvoucher khi hết hạn
    private HasVoucherDAO hasVoucherDAO=new HasVoucherDAO();
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> res = new HashMap<> ();
        // UserDAO userDAO=new UserDAO();
        // Cookie[] cookies = req.getCookies();
        // if (cookies == null) {
        //         res.put("message", "thieu cookie");
        //         JSONHelper.sendJsonAsResponse(resp, 400, res);
        // }
        // for (Cookie cookie : cookies) {
        //         if (cookie.getName().equals("user_id")) {
        //                 int user_id = Integer.parseInt(cookie.getValue());
        //                 User currentUser = (User) userDAO.getById(user_id);
        //                 if (currentUser == null) {
        //                         res.put("message", "wrong user id");
        //                         JSONHelper.sendJsonAsResponse(resp, 400, res);
        //                         return;
        //                 }
        //                 if (currentUser.getUser_role() != 2) {
        //                         res.put("message", "ko phai admin");
        //                         JSONHelper.sendJsonAsResponse(resp, 401, res);
        //                         return;
        //                 }
        //                 break;
        //         }
        // }

        List <HasVoucher> a = hasVoucherDAO.queryObjects();
        if(a==null){
            res.put("message", "HasVoucher trống");
            JSONHelper.sendJsonAsResponse(resp, 404, res);
        }
        else{
            for(HasVoucher hasVoucher:a){
                if(hasVoucher.getExpiration_date().getTime()<System.currentTimeMillis()){
                    hasVoucherDAO.deleteObject(hasVoucher.getHas_voucher_id());
                }
            }
            res.put("message", "Sussces");
            JSONHelper.sendJsonAsResponse(resp, 200,res);
        }     
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
