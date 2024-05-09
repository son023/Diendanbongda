/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.product;

import static common.product.Constant.URL_HAS_VOUCHER_EDIT;

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

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
@WebServlet(name="editHasVoucher", urlPatterns={URL_HAS_VOUCHER_EDIT})
public class EditHasVoucher extends HttpServlet {
   
    
    private final HasVoucherDAO hasVoucherDAO=new HasVoucherDAO();
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
        //         if (cookies == null) {
        //                 res.put("message", "thieu cookie");
        //                 JSONHelper.sendJsonAsResponse(response, 400, res);
        //         }
        //         for (Cookie cookie : cookies) {
        //             if (cookie.getName().equals("user_id")) {
        //                 int user_id = Integer.parseInt(cookie.getValue());
        //                 User currentUser = (User) userDAO.getById(user_id);
        //                 if (currentUser == null) {
        //                         res.put("message", "wrong user id");
        //                         JSONHelper.sendJsonAsResponse(response, 400, res);
        //                         return;
        //                 }
        //                 if (currentUser.getUser_role() != 2) {
        //                         res.put("message", "ko phai admin");
        //                         JSONHelper.sendJsonAsResponse(response, 401, res);
        //                         return;
        //                 }
        //                 break;
        //             }
        //         }
                try {
                    if (jSONObject.get("has_voucher_id") == null && jSONObject.get("has_voucher_id").toString().trim().equals("")) {
			res.put("message", "bad request, json khong co id");
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		     }
                    HasVoucher oldH = (HasVoucher) hasVoucherDAO.getById(Integer.parseInt(jSONObject.get("has_voucher_id").toString()));
                    if (jSONObject.get("expiration_date") != null && !jSONObject.get("expiration_date").toString().equals("")) {
                        String dateString = jSONObject.get("expiration_date").toString();
                        LocalDate localDate = LocalDate.parse(dateString);
                        Date sqlDate = Date.valueOf(localDate);
                        oldH.setExpiration_date(sqlDate);           

                    }

                    boolean status = hasVoucherDAO.updateObject(oldH);
                    if (!status) {
                            res.put("message", "Server's error");
                            JSONHelper.sendJsonAsResponse(response, 500, res);

                    }
                    else {
                            JSONHelper.sendJsonAsResponse(response, 200, oldH);
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
