package controllers.product;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.HasVoucher;
import model.product.Voucher;
import model.user.User;

import com.google.gson.Gson;
import static common.product.Constant.URL_HAS_VOUCHER_POST_AND_GET;

import dal.productdao.HasVoucherDAO;
import dal.productdao.VoucherDAO;
import dal.userdao.UserDAO;
import helper.JSONHelper;
import jakarta.servlet.http.Cookie;
import java.util.*;
import java.sql.Date;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */

@WebServlet(name = "HasVoucherServlet", urlPatterns = { URL_HAS_VOUCHER_POST_AND_GET })
public class HasVoucherServlet extends HttpServlet {

    private Gson gson = new Gson();
    private HasVoucherDAO hasVoucherDAO = new HasVoucherDAO();
    private VoucherDAO voucherDAO = new VoucherDAO();
    private UserDAO userDAO = new UserDAO();

    // add by id
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> res = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(JSONHelper.readJSON(request));
            // Cookie[] cookies = request.getCookies();
            // for (Cookie cookie : cookies) {
            // if (cookie.getName().equals("user_id")) {
            // int user_id = Integer.parseInt(cookie.getValue());
            // User currentUser = (User) userDAO.getById(user_id);
            // if (currentUser == null) {
            // res.put("message", "wrong user id");
            // JSONHelper.sendJsonAsResponse(response, 400, res);
            // return;
            // }
            // if (currentUser.getUser_role() != 2) {
            // res.put("message", "ko phai admin");
            // JSONHelper.sendJsonAsResponse(response, 401, res);
            // return;
            // }
            // break;
            // }
            // }
            int user_id = Integer.parseInt(jsonObject.get("user_id").toString());
            int voucher_id = Integer.parseInt(jsonObject.get("voucher_id").toString());
            Voucher voucher = (Voucher) voucherDAO.getById(voucher_id);
            if (voucher == null) {
                res.put("Message", "Voucher không có trong dữ liệu");
                JSONHelper.sendJsonAsResponse(response, 404, res);
            } else {
                Date expiration_date = new Date(
                        (long) voucher.getExpire_time() * 24 * 60 * 60 * 1000 + System.currentTimeMillis());
                Voucher vouch = (Voucher) new VoucherDAO().getById(voucher_id);
                HasVoucher hasVoucher = new HasVoucher(0, user_id, vouch, expiration_date);
                boolean isSuccess = hasVoucherDAO.addObject(hasVoucher);
                if (isSuccess) {
                    res.put("message", "Success");
                    JSONHelper.sendJsonAsResponse(response, 200, res);
                } else {
                    res.put("message", "Server's error");
                    JSONHelper.sendJsonAsResponse(response, 500, res);
                }
            }

        } catch (NumberFormatException e) {
            res.put("message", "bad request, check url params again");
            JSONHelper.sendJsonAsResponse(response, 400, res);
        }
    }

    // get by id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> res = new HashMap<>();
        try {
            int id = Integer.parseInt(req.getParameter("has_voucher_id"));
            Object check = hasVoucherDAO.getById(id);
            if (check == null) {
                res.put("message", "HasVoucher không có trong dữ liệu");
                JSONHelper.sendJsonAsResponse(resp, 404, res);

            } else {
                HasVoucher hasVoucher = (HasVoucher) check;
                JSONHelper.sendJsonAsResponse(resp, 200, hasVoucher);
            }

        } catch (NumberFormatException e) {
            res.put("message", "bad request, check url params again");
            JSONHelper.sendJsonAsResponse(resp, 400, res);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}