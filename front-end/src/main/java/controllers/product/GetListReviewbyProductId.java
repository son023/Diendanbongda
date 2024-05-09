/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers.product;

import dal.productdao.ReviewDAO;

import static common.product.Constant.URL_LIST_REVIEW_GET_BY_PRODUCT_ID;

import helper.JSONHelper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DELL
 */
@WebServlet(name="getListReviewbyProductId", urlPatterns={URL_LIST_REVIEW_GET_BY_PRODUCT_ID})
public class GetListReviewbyProductId extends HttpServlet {
   
   
   
    // lấy ra <List>những review mà Product đang có
    private ReviewDAO reviewDAO=new ReviewDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { 
        Map<String, Object> res = new HashMap<> ();
        try {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            List <Review> check =  reviewDAO.getListReviewByProduct(id);
            if(check==null || check.isEmpty()){
                res.put("message", "Product này chưa có đánh giá");
                JSONHelper.sendJsonAsResponse(resp, 404, res);
            }
            else {
                JSONHelper.sendJsonAsResponse(resp, 200,check);
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