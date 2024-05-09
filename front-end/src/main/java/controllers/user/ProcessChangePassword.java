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
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;
import model.user.User;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
/*
// các biến frontend gửi
            String old_password 
            String new_password 
            String confirm_password 
// các lỗi xảy ra
{
    "Size of new password is between 5 and 15": 0,
    "The new password must contain at least 1 alphabetic character": 0,
    "The new password must contain at least 1 digit character": 0,
    "The new password must contain at least 1 special character": 0,
    "INVALID OLD PASSWORD": 1,
    "INVALID": 1,
    "NEW PASSWORD AND CONFIRM PASSWORD IS DIFFERENT": 0
}
// nếu valid = 1 thì trong json sẽ không có  1 loạt các mesage kia
*/
@WebServlet(name = "ProcessChangePassword", urlPatterns = {"/ProcessChangePassword"})
public class ProcessChangePassword extends HttpServlet {

	private String checkPassword(String passWord){
        if(passWord.length() >15 || passWord.length() < 5)return ("Size of new password is between 5 and 15") ;
        boolean isdigit = false  , isalpha = false , isspe = false ;
        for(Character x : passWord.toCharArray()){
            if(!Character.isAlphabetic(x) && !Character.isDigit(x))isspe = true;
            if(Character.isAlphabetic(x))isalpha = true ;
            if(Character.isDigit(x)) isdigit = true ;
        }
        if(!isalpha) return ("The new password must contain at least 1 alphabetic character");
        if(!isdigit)return ("The new password must contain at least 1 digit character");
        if(!isspe)return ("The new password must contain at least 1 special character");
        return "VALID NEW PASSWORD";
    }
    private String checkdb(String passWord,int user_id){
        User yen = (User)(new UserDAO()).getById(user_id);
           if(passWord.hashCode() == yen.getPass_word())return "VALID OLD PASSWORD";
           return "INVALID OLD PASSWORD" ;
    }
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
           String old_password = yourdata.getString("old_password");
           String new_password = yourdata.getString("new_password");
           String confirm_password = yourdata.getString("confirm_password");
           //check format
           List<String> errors = new ArrayList<>();
           if( checkdb(old_password, user_id).equals("INVALID OLD PASSWORD"))errors.add("INVALID OLD PASSWORD");
           if(!checkPassword(new_password).equals("VALID NEW PASSWORD"))errors.add(checkPassword(new_password));
           if(!new_password.equals(confirm_password))errors.add("NEW PASSWORD AND CONFIRM PASSWORD IS DIFFERENT");
           String s1 = "\"Size of new password is between 5 and 15\" :0," ;
           String s2= "\"The new password must contain at least 1 alphabetic character\" : 0," ;
           String s3 = "\"The new password must contain at least 1 digit character\" : 0," ; 
           String s4 = "\"The new password must contain at least 1 special character\" : 0,";
           String s5 = "\"INVALID OLD PASSWORD\" :0," ;
           String s6 = "\"NEW PASSWORD AND CONFIRM PASSWORD IS DIFFERENT\": 0" ;
           if(!errors.isEmpty() ){
               for(String string : errors){
                   if(string.equals("Size of new password is between 5 and 15"))s1 = "\"Size of new password is between 5 and 15\" :1," ;
                   if(string.equals("The new password must contain at least 1 alphabetic character"))s2= "\"The new password must contain at least 1 alphabetic character\" : 1," ;
                   if(string.equals("The new password must contain at least 1 digit character"))s3 = "\"The new password must contain at least 1 digit character\" : 1," ; 
                   if(string.equals("The new password must contain at least 1 special character"))s4 = "\"The new password must contain at least 1 special character\" : 1,";
                   if(string.equals("INVALID OLD PASSWORD"))s5 = "\"INVALID OLD PASSWORD\" :1," ;
                   if(string.equals("NEW PASSWORD AND CONFIRM PASSWORD IS DIFFERENT"))s6 = "\"NEW PASSWORD AND CONFIRM PASSWORD IS DIFFERENT\": 1" ;
               }
               response.getWriter().write( "{" + s1 + s2 + s3 + s4 + s5 + "\"INVALID\" : 1,"  + s6 + "}");
               return ;
           }
           //lay ra user tu db
           User yen = (User)(new UserDAO()).getById(user_id);
           yen.setPass_word(new_password.hashCode());
           (new UserDAO()).updateObject(yen);
           response.getWriter().write("{" + "\"VALID\" : 1" + "}" );
        } catch (Exception e) {
            response.getWriter().write(e.toString());
        }
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
