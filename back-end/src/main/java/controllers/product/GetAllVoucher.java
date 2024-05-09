/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.product;

import dal.productdao.VoucherDAO;

import static common.product.Constant.URL_VOUCHER_GET_ALL_VOUCHER;

import helper.JSONHelper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Voucher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
@WebServlet(name="getAllVoucher", urlPatterns={URL_VOUCHER_GET_ALL_VOUCHER})
public class GetAllVoucher extends HttpServlet {
    
    // lấy ra tất cả voucher hiện có của shop
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> res = new HashMap<> ();
        VoucherDAO voucherDAO=new VoucherDAO();
        List <Voucher> vouchers=voucherDAO.queryObjects();
        if(vouchers==null){
            res.put("Messge", "Voucher không có trong dữ liệu");
            JSONHelper.sendJsonAsResponse(resp, 404, res);
        }
        else{
            JSONHelper.sendJsonAsResponse(resp, 200, vouchers);
        }
        
        
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
