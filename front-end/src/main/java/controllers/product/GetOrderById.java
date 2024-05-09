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
@WebServlet(name = "getOrderById", urlPatterns = { "/product/getOrderById/*" })
public class GetOrderById extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo().substring(1);
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			OrderDAO orderDao = new OrderDAO();
			// Cookie[] cookies = request.getCookies();
			// if (cookies == null) {
			// 	res.put("message", "thieu cookie");
			// 	JSONHelper.sendJsonAsResponse(response, 400, res);
			// }
			// for (Cookie cookie : cookies) {
			// 	if (cookie.getName().equals("user_id")) {
			// 		int user_id = Integer.parseInt(cookie.getValue());
			// 		List<Order> orders = orderDao.getByUserId(user_id);
			// 		if (orders == null || orders.size() == 0) {
			// 			res.put("message", "ko phai order cua ban");
			// 			JSONHelper.sendJsonAsResponse(response, 401, res);
			// 			return;
			// 		}
			// 		break;
			// 	}
			// }
			int _id = Integer.parseInt(id);
			Order order = (Order) orderDao.getById(_id);
			JSONHelper.sendJsonAsResponse(response, 200, order);
			return;
		} catch (NumberFormatException e) {
			res.put("message", "bad request, check url params again");
			response.setStatus(400);
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
