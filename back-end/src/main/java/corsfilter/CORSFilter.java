/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corsfilter;

import dal.articledao.ArticleDAO;
import dal.userdao.UserDAO;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.article.Article;
import model.user.User;

import java.io.BufferedReader;
import org.json.JSONException;
import org.json.JSONObject;

public class CORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (((HttpServletResponse)response).getHeader("Access-Control-Allow-Origin") == null || !((HttpServletResponse)response).getHeader("Access-Control-Allow-Origin").equals("*")) {
            ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
        }
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        ((HttpServletResponse)response).setHeader("Access-Control-Allow-Headers", "*");
		HttpServletResponse resp = (HttpServletResponse) response;
		if (req.getMethod().equals("OPTIONS")){
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}
        chain.doFilter(request, response);
    }
}