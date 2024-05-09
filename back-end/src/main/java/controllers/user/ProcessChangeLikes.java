/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dal.userdao.UserDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.User;
import jakarta.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author pc
 */
@WebServlet(name = "ProcessChangeLikes", urlPatterns = {"/ProcessChangeLikes"})
public class ProcessChangeLikes extends HttpServlet {

	/** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            BufferedReader reader = request.getReader();
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
           reader.close();
           response.setContentType("application/json");
           response.setCharacterEncoding("UTF-8");
           JSONObject yourdata = new JSONObject(json.toString());
           // lay ra du lieu
           int user_id =  yourdata.getInt("user_id");
           int numlike = yourdata.getInt("numberlikes");
           //lay ra user tu db
           User yen = (User)(new UserDAO()).getById(user_id);
           yen.setLikes(yen.getLikes() + numlike);
           yen.setScore_to_award(yen.getScore_to_award() + numlike);
           if(yen.getLikes() - yen.getDislikes() > 100)yen.setUser_role(1);
           (new UserDAO()).updateObject(yen);
        //    response.getWriter().write(yen.toString() );
           response.getWriter().write("{" + "\"VALID\" : 1" + "}" );
           //check format
           
        } catch (Exception e) {
            response.getWriter().write(e.toString());
        }
    }
    @Override
   protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            this.doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }
    protected void doPatch(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            processRequest(request, response);
        }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
