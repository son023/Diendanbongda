/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import dal.userdao.UserDAO;
import helper.JSONHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.User;

/**
 *
 * @author pc
 */
@WebServlet(name = "getUserById", urlPatterns = {"/getUserById/*"})
public class GetUserById extends HttpServlet {

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getPathInfo().substring(1);
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			int _id = Integer.parseInt(id);
			UserDAO prodDao = new UserDAO();
			User prod = (User) prodDao.getById(_id);
			JSONHelper.sendJsonAsResponse(response, 200, prod);
			return;
		} catch (NumberFormatException e) {
			res.put("message", "bad request, check url params again");
			response.setStatus(400);
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		}
	}
}
