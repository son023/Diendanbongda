/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.product;

import static common.product.Constant.URL_VOUCHER_EDIT;

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
import model.product.Voucher;
import model.user.User;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
@WebServlet(name="editVoucher", urlPatterns={URL_VOUCHER_EDIT})
public class EditVoucher extends HttpServlet {
    private final VoucherDAO voucherDAO=new VoucherDAO();
    private final UserDAO userDAO=new UserDAO();
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            this.doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String jsonFromRequest = JSONHelper.readJSON(request);
		JSONObject jSONObject= new JSONObject(jsonFromRequest);
		Map<String, String> res = new HashMap<> ();
                // Cookie[] cookies = request.getCookies();
                // if (cookies == null) {
                //         res.put("message", "thieu cookie");
                //         JSONHelper.sendJsonAsResponse(response, 400, res);
                // }
                // for (Cookie cookie : cookies) {
                //     if (cookie.getName().equals("user_id")) {
                //         int user_id = Integer.parseInt(cookie.getValue());
                //         User currentUser = (User) userDAO.getById(user_id);
                //         if (currentUser == null) {
                //                 res.put("message", "wrong user id");
                //                 JSONHelper.sendJsonAsResponse(response, 400, res);
                //                 return;
                //         }
                //         if (currentUser.getUser_role() != 2) {
                //                 res.put("message", "ko phai admin");
                //                 JSONHelper.sendJsonAsResponse(response, 401, res);
                //                 return;
                //         }
                //         break;
                //     }
                // }
		
		try {
                        if (jSONObject.get("voucher_id") == null && jSONObject.get("voucher_id").toString().trim().equals("")) {
                            res.put("message", "bad request, json khong co id");
                            JSONHelper.sendJsonAsResponse(response, 400, res);
                            return;
                        }
                        Voucher oldV = (Voucher) voucherDAO.getById(Integer.parseInt(jSONObject.get("voucher_id").toString()));
                        if (jSONObject.get("discount_amount") != null && !jSONObject.get("discount_amount").toString().equals("")) {
                                int discount= Integer.parseInt(jSONObject.get("discount_amount").toString());
                                if(discount<=100 && discount>=0) oldV.setDiscount_amount(discount);

                        }
                        if (jSONObject.get("expire_time") != null && !jSONObject.get("expire_time").toString().equals("")) {
                                oldV.setExpire_time(Integer.parseInt(jSONObject.get("expire_time").toString()));
                        }

                        boolean status = voucherDAO.updateObject(oldV);
                        if (!status) {
                                res.put("message", "Server's error");
                                JSONHelper.sendJsonAsResponse(response, 500, res);

                        }
                        else {
                                JSONHelper.sendJsonAsResponse(response, 200, oldV);
                        }
                       
		}
		catch (IOException | NumberFormatException | JSONException e) {
			res.put("message", "bad request, sai kieu du lieu");
			JSONHelper.sendJsonAsResponse(response, 400, res);
			
		}
	}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
