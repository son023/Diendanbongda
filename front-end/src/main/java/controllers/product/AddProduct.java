/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import dal.productdao.ProductDAO;
import dal.userdao.UserDAO;
import helper.JSONHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Product;
import model.user.User;

/**
 *
 * @author pc
 */
@WebServlet(name = "addProduct", urlPatterns = { "/product/addProduct" })
public class AddProduct extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> jsonMap = new JSONObject(JSONHelper.readJSON(request)).toMap();
		ProductDAO prodDao = new ProductDAO();
		String productName = "";
		boolean category = false;
		String imagePath = "";
		String team = "";
		int price = 0;
		float rating = 0.0f;
		int totalRatingTime = 0;
		int sold = 0;
		int discounted = 0;
		Map<String, Object> res = new HashMap<>();
		try {
			// Cookie[] cookies = request.getCookies();
			// if (cookies == null) {
			// 	res.put("message", "thieu cookie");
			// 	JSONHelper.sendJsonAsResponse(response, 401, res);
			// 	return;
			// }
			// for (Cookie cookie : cookies) {
			// 	if (cookie.getName().equals("user_id")) {
			// 		int user_id = Integer.parseInt(cookie.getValue().toString());
			// 		UserDAO userDAO = new UserDAO();
			// 		User currentUser = (User) userDAO.getById(user_id);
			// 		if (currentUser == null) {
			// 			res.put("message", "wrong user id");
			// 			JSONHelper.sendJsonAsResponse(response, 400, res);
			// 			return;
			// 		}
			// 		if (currentUser.getUser_role() != 2) {
			// 			res.put("message", "ko phai admin");
			// 			JSONHelper.sendJsonAsResponse(response, 401, res);
			// 			return;
			// 		}
			// 		break;
			// 	}
			// }
			productName = jsonMap.get("product_name").toString();
			if (jsonMap.get("category").toString().equals("1")) {
				category = true;
			}
			if (jsonMap.get("category").toString().equals("0")) {
				category = false;
			}
			imagePath = jsonMap.get("imagePath").toString();
			team = jsonMap.get("team").toString();
			price = Integer.parseInt(jsonMap.get("price").toString());
			rating = 0.0f;
			totalRatingTime = 0;
			sold = 0;
			discounted = Integer.parseInt(jsonMap.get("discounted").toString());
		} catch (Exception e) {
			res.put("message",e.getMessage());
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		}
		Product newProd = new Product(0, productName, category, imagePath, team, price, rating, sold, discounted,
				totalRatingTime);
		int newId = prodDao.addProduct(newProd);
		if (newId != -1) {
			Product createdProd = (Product) prodDao.getById(newId);
			JSONHelper.sendJsonAsResponse(response, 200, createdProd);
			return;
		} else {
			res.put("message", "bad request, trung ten");
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		}
	}

	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
