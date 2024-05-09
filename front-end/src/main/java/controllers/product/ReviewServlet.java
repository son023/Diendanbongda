/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.product;

import static common.product.Constant.ULR_REVIEW_POST_AND_GET;

import dal.productdao.OrderDAO;
import dal.productdao.ProductDAO;
import dal.productdao.ReviewDAO;
import helper.JSONHelper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Order;
import model.product.Product;
import model.product.ProductOrder;
import model.product.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ReviewServlet", urlPatterns = { ULR_REVIEW_POST_AND_GET })
public class ReviewServlet extends HttpServlet {

    ReviewDAO reviewDAO = new ReviewDAO();
    OrderDAO orderDAO = new OrderDAO();

    // lấy voucher theo id
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> res = new HashMap<>();
        try {
            int id = Integer.parseInt(req.getParameter("review_id"));
            Object check = reviewDAO.getById(id);
            if (check == null) {
                res.put("message", "Review không có trong dữ liệu");
                JSONHelper.sendJsonAsResponse(resp, 404, res);
            } else {
                Review review = (Review) check;
                JSONHelper.sendJsonAsResponse(resp, 200, review);
            }
        }

        catch (NumberFormatException e) {
            res.put("message", "bad request, check url params again");
            JSONHelper.sendJsonAsResponse(resp, 400, res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> res = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(JSONHelper.readJSON(request));
            int user_id = Integer.parseInt(jsonObject.get("user_id").toString());
            int product_id = Integer.parseInt(jsonObject.get("product_id").toString());
            int star = Integer.parseInt(jsonObject.get("star").toString());
            String review_content = jsonObject.get("review_content").toString();
            List<Order> order = orderDAO.getByUserId(user_id);
            if (order == null || order.isEmpty()) {
                res.put("Message", "Vui lòng mua hàng để thực hiện tác vụ này");
                JSONHelper.sendJsonAsResponse(response, 400, res);
                return;
            } else {
                boolean check_order = false;
                for (Order o : order) {
                    if (o.getStatus() == 2 || o.getStatus() == 3) {
                        for (ProductOrder productOrder : o.getEntries()) {
                            if (productOrder.getProduct().getProduct_id() == product_id) {
                                check_order = true;
                            }
                        }
                    }
                }
                if (check_order) {
                    if (star <= 5 && star >= 0) {
                        Review review = new Review(0, user_id, product_id, star, review_content);
                        boolean isSuccess = reviewDAO.addObject(review);
                        if (isSuccess) {
                            ProductDAO prodDAO = new ProductDAO();
                            Product reviewedProduct = (Product) prodDAO.getById(product_id);
                            int currentTotalReview = reviewedProduct.getTotalRatingTime();
                            float currentRating = reviewedProduct.getRating();
                            int newTotalReview = currentTotalReview + 1;
                            float newRating = (currentRating * currentTotalReview + star) / newTotalReview;
                            reviewedProduct.setRating(newRating);
                            reviewedProduct.setTotal_rating_time(newTotalReview);
                            prodDAO.updateObject(reviewedProduct);
                            res.put("Message", "Success");
                            JSONHelper.sendJsonAsResponse(response, 200, res);
                        } else {
                            res.put("Message", "Server's error");
                            JSONHelper.sendJsonAsResponse(response, 500, res);
                        }

                    } else {
                        res.put("Message", "Star không hợp lệ");
                        JSONHelper.sendJsonAsResponse(response, 501, res);
                    }
                } else {
                    res.put("Message", "Vui lòng sản phẩm này để thực hiện tác vụ");
                    JSONHelper.sendJsonAsResponse(response, 400, res);

                }
            }

        } catch (NumberFormatException e) {
            res.put("Message", "Bad request, check url params again");
            JSONHelper.sendJsonAsResponse(response, 400, res);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
