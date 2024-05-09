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
@WebServlet(name = "editProduct", urlPatterns = { "/product/editProduct" })
public class EditProduct extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getMethod();
		if (method.equals("PATCH")) {
			this.doPatch(req, resp);
		} else {
			super.service(req, resp);
		}
	}
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
	protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String jsonFromRequest = JSONHelper.readJSON(request);
		Map<String, Object> fields = new JSONObject(jsonFromRequest).toMap();
		Map<String, String> res = new HashMap<>();
		if (fields.get("product_id") == null && fields.get("product_id").toString().trim().equals("")) {
			res.put("message", "bad request, json ko chua id");
			JSONHelper.sendJsonAsResponse(response, 400, res);
			return;
		}
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
		ProductDAO prodDao = new ProductDAO();
		try {
			Product oldProd = (Product) prodDao.getById(Integer.parseInt(fields.get("product_id").toString()));
			if (oldProd == null) {
				res.put("message", "wrong id");
				JSONHelper.sendJsonAsResponse(response, 400, res);
			}
			if (fields.get("product_name") != null && !fields.get("product_name").toString().equals("")) {
				oldProd.setProduct_name(fields.get("product_name").toString());
			}
			if (fields.get("category") != null && !fields.get("category").toString().equals("")) {
				if (fields.get("category").toString().equals("1")) {
					oldProd.setCategory(true);
				}
				if (fields.get("category").toString().equals("0")) {
					oldProd.setCategory(false);
				}
			}
			if (fields.get("imagePath") != null && !fields.get("imagePath").toString().equals("")) {
				oldProd.setImagePath(fields.get("imagePath").toString());
			}
			if (fields.get("team") != null && !fields.get("team").toString().equals("")) {
				oldProd.setTeam(fields.get("team").toString());
			}
			if (fields.get("price") != null && !fields.get("price").toString().equals("")) {
				int newPrice = Integer.parseInt(fields.get("price").toString());
				if (newPrice >= 0) {
					oldProd.setPrice(newPrice);
				}
			}
			if (fields.get("discounted") != null && !fields.get("discounted").toString().equals("")) {
				int newDiscounted = Integer.parseInt(fields.get("discounted").toString());
				if (newDiscounted > 0 && newDiscounted <= 100) {
					oldProd.setDiscounted(newDiscounted);
				}
			}
			boolean status = prodDao.updateObject(oldProd);
			if (!status) {
				res.put("message", "bad request");
				JSONHelper.sendJsonAsResponse(response, 400, res);
				return;
			} else {
				JSONHelper.sendJsonAsResponse(response, 200, oldProd);
				return;
			}
		} catch (Exception e) {
			res.put("message", "bad request, sai kieu du lieu");
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
