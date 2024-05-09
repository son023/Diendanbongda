/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.product;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dal.productdao.ProductDAO;
import dal.userdao.UserDAO;
import helper.JSONHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.User;

/**
 *
 * @author pc
 */
@WebServlet(name = "deleteById", urlPatterns = { "/product/deleteById/*" })
public class DeleteById extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo().substring(1);
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			// Cookie[] cookies = request.getCookies();
			// if (cookies == null) {
			// 	res.put("message", "thieu cookie");
			// 	JSONHelper.sendJsonAsResponse(response, 400, res);
			// }
			// for (Cookie cookie : cookies) {
			// 	if (cookie.getName().equals("user_id")) {
			// 		int user_id = Integer.parseInt(cookie.getValue());
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
			int _id = Integer.parseInt(id);
			ProductDAO prodDao = new ProductDAO();
			boolean success = prodDao.deleteObject(_id);
			if (success) {
				res.put("message", "success");
				JSONHelper.sendJsonAsResponse(response, 200, res);
				return;
			} else {
				res.put("message", "server's error");
				JSONHelper.sendJsonAsResponse(response, 500, res);
				return;
			}
		} catch (NumberFormatException e) {
			res.put("message", "bad request, check url params again");
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
