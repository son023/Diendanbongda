/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dal.userdao.UserDAO;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;
import model.user.User;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;
/*
// các kiểu dữ liệu frontend cung cấp 
String : user_name
String pass_word
Note: viết đúng tên biến

// các kiểu json backend trả về : 

 */
@WebServlet(name = "ProcessLogin", urlPatterns = {"/ProcessLogin"})
public class ProcessLogin extends HttpServlet {

protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    } 
    // @Override
    // protected void doPost(HttpServletRequest request, HttpServletResponse response)
    // throws ServletException, IOException {
    //     CORS.disableCORS(response, "post");
    //     processRequest(request, response);
    // }
    private boolean checkCharacter(Character c){
        int x = c + 0;
        if(x >= 48 && x <= 57)return true;
        else if( x >= 65 && x <= 90)return true ;
        else if(x >= 97 && x <= 122)return true;
        else return false;
    }
    private HashSet<String> check( String Username ,String passWord ){
         HashSet<String> list = new HashSet<>();
        //check username
        if(Username.length() > 15 || Username.length() < 5)list.add("Size of Username is between 5 and 15") ;
        for(Character x : Username.toCharArray()){
            if(!Character.isAlphabetic(x) && !Character.isDigit(x))list.add("Username mustn\'t have special character");
            else if(!checkCharacter(x))list.add("Username mustn\'t have special character");
        }
        // check password
        if(passWord.length() >15 || passWord.length() < 5)list.add("Size of password is between 5 and 15") ;
        return list ;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // CORS.disableCORS(response, "post");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }
        // Đọc dữ liệu JSON từ request
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
            // Sử dụng Gson để chuyển đổi JSON thành đối tượng Java
            JSONObject yourdata = new JSONObject(json.toString());
    //         Lấy thông tin từ đối tượng yourData
            String Username = yourdata.getString("user_name");
            String PASSWORD = yourdata.getString("pass_word");
            int passWord = PASSWORD.hashCode() ;
            // check true format
            if(check(Username, PASSWORD).size() > 0){
                response.getWriter().write( "{" + "\"The username or password is wrong format\" , 1" + "}");
                return ;
            }
            // check database
            List<Object> list = (new UserDAO()).getAllObjects();
            User res = null , yen  = null ;
            for(Object  x : list){
                User y = (User)x ;
                if(y.getUser_name().equals(Username) &&  y.getPass_word() == passWord)res = y ;
                else if(y.getUser_name().equals(Username))yen = y ;
            }
            String message = "" ;
            long cur_time = (new Date()).getTime() ;
           if(res == null && yen == null)message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","INVALID",-1);// nem message fail login
           else {
                long wait = 0 ;
                   try {
                        wait = cur_time - yen.getLogin_cooldown() ;
                  } catch (Exception e) {}
                   try {
                        wait = cur_time - res.getLogin_cooldown() ;
                  } catch (Exception e) {}
                   if(wait  < 10 * 60 * 1000){
                       if(wait/1000 < 3600)message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","Please login again after: " + (wait/1000/60) + " minutes",-1);
                       else if(wait/1000/3600 < 24)message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","Please login again after: " + (wait/1000/3600) + " hours",-1);
                       else message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","Please login again after: " + (wait/1000/3600/24) + " days",-1);
                   }
                   else {
                      if(res != null){
                          // nem message 
                          message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", res.getUser_role(),res.getUser_name(),res.getFull_name(),"VALID",res.getUser_id());
                          // sua password_late va login failed in db
                          UserDAO config = new UserDAO();
                          res.setPass_word_latest(passWord);
                          res.setPass_word_latest_time(cur_time);
                          res.setLogin_fail(0);
                          res.setLogin_cooldown(0);
                          config.updateObject(res);
                      }
                      else if(yen.getPass_word_latest() == passWord){
                          //nem message mat khau cu cach day
//                            message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","It's your password " +((cur_time - yen.getPass_word_latest_time())/1000/3600/24)+" days before",-1);
                            if((cur_time - yen.getPass_word_latest_time())/1000 < 3600)message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","It's your password " +((cur_time - yen.getPass_word_latest_time())/1000/60)+" minutes before",-1);
                            else if((cur_time - yen.getPass_word_latest_time())/1000/3600 < 24)message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","It's your password " +((cur_time - yen.getPass_word_latest_time())/1000/3600)+" hours before",-1);
                            else message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","It's your password " +((cur_time - yen.getPass_word_latest_time())/1000/3600/24)+" days before",-1);
                          //sua login fail in db
                          UserDAO config = new UserDAO();
                          yen.setLogin_fail(yen.getLogin_fail() + 1);
                          if(yen.getLogin_fail() > 20){
                              yen.setLogin_cooldown(cur_time);
                              yen.setLogin_fail(0);
                          }
                          config.updateObject(yen);
                      }
                       else{
                            // nem message fail login  va sua login fail in db
                             message = String.format("{\"role\" : %d , \"username\" : \"%s\" , \"fullname\" : \"%s\" , \"token\" : \"%s\" , \"id\" : %d}", -1,"-1","-1","INVALID",-1);
                             //sua login fail in db
                            UserDAO config = new UserDAO();
                            yen.setLogin_fail(yen.getLogin_fail() + 1);
                          if(yen.getLogin_fail() > 20){
                              yen.setLogin_cooldown(cur_time);
                              yen.setLogin_fail(0);
                          }
                          config.updateObject(yen);
                        }
                   }//end else
                   }
        //    response.getWriter().write(message);
            
           response.getWriter().write(message);
        }
        catch (Exception e) {
            response.getWriter().write(e.toString());
            return ;
        }
}

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>	

}
