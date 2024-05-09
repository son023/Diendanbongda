package controllers.product;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Voucher;
import model.user.User;

import com.google.gson.Gson;
import static common.product.Constant.URL_VOUCHER_POST_AND_GET;

import dal.productdao.VoucherDAO;
import dal.userdao.UserDAO;
import helper.JSONHelper;
import jakarta.servlet.http.Cookie;
import java.util.*;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */

@WebServlet(name="VoucherServlet", urlPatterns={URL_VOUCHER_POST_AND_GET})
public class VoucherServlet extends HttpServlet {

    private Gson gson=new Gson();
    private VoucherDAO voucherDAO=new VoucherDAO();
    private UserDAO userDAO=new UserDAO();
    // tạo voucher mới
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       Map<String, Object> res = new HashMap<> ();
        try{
            JSONObject jsonObject = new JSONObject(JSONHelper.readJSON(request));
            // Cookie[] cookies = request.getCookies();
            // for (Cookie cookie : cookies) {
            //         if (cookie.getName().equals("user_id")) {
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
            //         }
            // }
            int discount_amount=Integer.parseInt(jsonObject.get("discount_amount").toString());
            int expire_time=Integer.parseInt(jsonObject.get("expire_time").toString());
            Voucher voucher= new Voucher(0, discount_amount, expire_time);
            boolean isSuccess = voucherDAO.addObject(voucher);
            if (isSuccess) {
                    res.put("message", "Success");
                    JSONHelper.sendJsonAsResponse(response, 200,res);
            }
            else {
                    res.put("message", "Server's error");	
                    JSONHelper.sendJsonAsResponse(response, 500, res);
            }
        }
        catch(NumberFormatException e) {
            res.put("message", "bad request, check url params again");	
            JSONHelper.sendJsonAsResponse(response, 400, res);
        }
    }
    
    
    // lấy voucher theo id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> res = new HashMap<> ();
        try {
            int id=Integer.parseInt(req.getParameter("voucher_id"));
            Object check =  voucherDAO.getById(id);
            if(check==null){
                res.put("message", "Voucher không có trong dữ liệu");
                JSONHelper.sendJsonAsResponse(resp, 404, res);
                
            }
            else {
                Voucher voucher=(Voucher) check;
                JSONHelper.sendJsonAsResponse(resp, 200, voucher);
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