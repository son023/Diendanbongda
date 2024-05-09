/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.product;

import dal.productdao.ProductDAO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import org.json.JSONObject;

import helper.JSONHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.product.Product;

/**
 *
 * @author pc
 */
@WebServlet(name = "index", urlPatterns = {"/product/index"})
public class Index extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//filtered query

		// filter: name, category, team, price, sold, discounted, rating, total_rating_time

		//category: 0 - ao, 1 - giay

		ProductDAO prodDao = new ProductDAO();
		String jsonFromRequest = JSONHelper.readJSON(request);
		Map<String, Object> filter = new JSONObject(jsonFromRequest).toMap();	
		List<Product> prods = prodDao.queryObjects(filter);	
		JSONHelper.sendJsonAsResponse(response, 200, prods);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */

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
