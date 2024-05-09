/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.productdao.OrderDAO;
import helper.JSONHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Order;

/**
 *
 * @author pc
 */
@WebServlet(name = "getOrderByUserId", urlPatterns = { "/product/getOrderByUserId" })
public class GetOrderByUserId extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo().substring(1);
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			int _id = Integer.parseInt(id);
			OrderDAO orderDao = new OrderDAO();
			// Cookie[] cookies = request.getCookies();
			// if (cookies == null) {
			// 	res.put("message", "thieu cookie");
			// 	JSONHelper.sendJsonAsResponse(response, 400, res);
			// }
			// for (Cookie cookie : cookies) {
			// 	if (cookie.getName().equals("user_id")) {
			// 		int user_id = Integer.parseInt(cookie.getValue());
			// 		if (user_id != _id) {
			// 			res.put("message", "khong phai ban");
			// 			JSONHelper.sendJsonAsResponse(response, 401, res);
			// 			return;
			// 		}
			// 		break;
			// 	}
			// }
			List<Order> orders = orderDao.getByUserId(_id);
			JSONHelper.sendJsonAsResponse(response, 200, orders);
			return;
		} catch (NumberFormatException e) {
			res.put("message", "bad request, co loi xay ra");
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		}
	}

}
