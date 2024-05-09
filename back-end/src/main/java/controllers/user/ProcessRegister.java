/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dal.userdao.EmailDAO;
import dal.userdao.UserDAO;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.Email;
import model.user.User;
import jakarta.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;
/*
// các biến frontend gửi
            String full_name 
            String date_of_birth 
            boolean gender 
            String user_name 
            String pass_word 
            String email  // dung de quen mat khau

// các lỗi xảy ra
{
    "Size of Fullname is between 5 and 15": 0,
    "Email is INVALID": 0,
    "Size of Username is between 5 and 15": 0,
    "Username mustn't have special character": 0,
    "The account name already exists": 1,
    "Size of password is between 5 and 15": 0,
    "The password must contain at least 1 alphabetic character": 0,
    "The password must contain at least 1 digit character": 0,
    "The password must contain at least 1 special character": 0,
    "You are not enough age": 0,
    "VALID": 0, //
    "You are troll !": 0, // để ngày sinh là 1 thời điểm trong tương lai chưa diễn ra
    "The format of time is error , please type again": 0 // định dạng ngày giờ sai
}
// nếu valid = 1 thì trong json sẽ không có  1 loạt các mesage kia
*/

@WebServlet(name = "ProcessRegister", urlPatterns = {"/ProcessRegister"})
public class ProcessRegister extends HttpServlet {

	private String updateEmail(String email){
            try {
                if(!email.substring(email.length() - 10).equals("@gmail.com"))return "Email is INVALID";
                for(int i = 0 ; i< email.length() - 10 ; i++){
                    if(!checkCharacter(email.charAt(i)))return "Email is INVALID";
                }
            } catch (Exception e) {
                return "Email is INVALID";
            }
            return "VALID";
    }
    private boolean checkCharacter(Character c){
        int x = c + 0;
        if(x >= 48 && x <= 57)return true;
        else if( x >= 65 && x <= 90)return true ;
        else if(x >= 97 && x <= 122)return true;
        else return false;
    }
    private HashSet<String> check(String full_name ,String date_of_birth , String Username ,String passWord , boolean gender){
         HashSet<String> list = new HashSet<>();
        //check fullname
        if(full_name.length() > 100 || full_name.length() < 5 )list.add("Size of Fullname is between 5 and 100") ;
        //check username
        if(Username.length() > 15 || Username.length() < 5)list.add("Size of Username is between 5 and 15") ;
        for(Character x : Username.toCharArray()){
            if(!Character.isAlphabetic(x) && !Character.isDigit(x))list.add("Username mustn\'t have special character");
            else if(!checkCharacter(x))list.add("Username mustn\'t have special character");
        }
        List<Object> LIST = (new UserDAO()).getAllObjects();
        for(Object  x : LIST){
            User y = (User)x ;
            if(y.getUser_name().equals(Username)) list.add("The account name already exists") ;
        }
        // check password
        if(passWord.length() >15 || passWord.length() < 5)list.add("Size of password is between 5 and 15") ;
        boolean isdigit = false  , isalpha = false , isspe = false ;
        for(Character x : passWord.toCharArray()){
            if(!Character.isAlphabetic(x) && !Character.isDigit(x))isspe = true;
            if(Character.isAlphabetic(x))isalpha = true ;
            if(Character.isDigit(x)) isdigit = true ;
        }
        if(!isalpha) list.add("The password must contain at least 1 alphabetic character");
        if(!isdigit)list.add("The password must contain at least 1 digit character");
        if(!isspe)list.add("The password must contain at least 1 special character");
        //check date_of_birth
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            Date date = formatter.parse(date_of_birth);
            if((new Date()).getTime() - date.getTime() < 0 )list.add("You are troll !");
            else if(((new Date()).getTime() - date.getTime())/1000/3600/24/365 < 15 )list.add("You are not enough age");
        } catch (Exception e) {
            list.add("The format of time is error , please type again") ;
        }
        
        //need't check gender 
        return list ;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
          response.setContentType("text/html;charset=UTF-8");
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
            String full_name = yourdata.getString("full_name").trim();
            String date_of_birth = yourdata.getString("date_of_birth").trim();
            boolean gender = yourdata.getBoolean("gender");
            String Username = yourdata.getString("user_name").trim();
            String passWord = yourdata.getString("pass_word").trim();
            String email = yourdata.getString("email").trim();
            // check database va cu phap hop le
            if(check( full_name , date_of_birth ,  Username , passWord ,  gender).size() > 0 || !updateEmail(email).equals("VALID") ){
                HashSet<String> res = check(full_name, date_of_birth, Username, passWord, gender) ;
                String emailmessage = updateEmail(email);
                // nem message
                String s1 = "{" +"\"Size of Fullname is between 5 and 15\" : %d "  +" ," ;
                String s1plus = "\"Email is INVALID\" : 0" +" ," ;
                String s2 = "\"Size of Username is between 5 and 15\" : %d" +" ," ;
                String s3 = "\"Username mustn\'t have special character\" : %d" +" ," ;
                String s4 = "\"The account name already exists\" : %d" +" ," ;
                String s5 = "\"Size of password is between 5 and 15\" : %d" +" ," ;
                String s6 = "\"The password must contain at least 1 alphabetic character\" : %d" +" ," ;
                String s7 = "\"The password must contain at least 1 digit character\" : %d" +" ,";
                String s8 = "\"The password must contain at least 1 special character\" : %d" +" ," ;
                String s9 = "\"You are not enough age\" :%d"  +" ," ;
                String s9plusplus = "\"You are troll !\" :%d"  +" ," ;
                String s9plus = "\"VALID\" : 0"  +" ," ;
                String s10 = "\"The format of time is error , please type again\" : %d" + "}" ;
                s1 = String.format(s1, 0);
                s2 = String.format(s2, 0);
                s3 = String.format(s3, 0);
                s4 = String.format(s4, 0);
                s5 = String.format(s5, 0);
                s6 = String.format(s6, 0);
                s7 = String.format(s7, 0);
                s8 = String.format(s8, 0);
                s9 = String.format(s9, 0);
                s9plusplus = String.format(s9plusplus, 0);
                s10 = String.format(s10, 0);
                
                for(String error : check( full_name, date_of_birth ,  Username , passWord ,  gender)){
                    if(error.equals("Size of Fullname is between 5 and 15"))s1 = "{" +"\"Size of Fullname is between 5 and 15\" : 1 "  +" ," ;
                    if(error.equals("Size of Username is between 5 and 15"))s2 = "\"Size of Username is between 5 and 15\" : 1" +" ," ;
                    if(error.equals("Username mustn\'t have special character"))s3 = "\"Username mustn\'t have special character\" : 1" +" ," ;
                    if(error.equals("The account name already exists"))s4 = "\"The account name already exists\" : 1" +" ," ;
                    if(error.equals("Size of password is between 5 and 15"))s5 = "\"Size of password is between 5 and 15\" : 1" +" ," ;
                    if(error.equals("The password must contain at least 1 alphabetic character"))s6 = "\"The password must contain at least 1 alphabetic character\" : 1" +" ," ;
                    if(error.equals("The password must contain at least 1 digit character"))s7 = "\"The password must contain at least 1 digit character\" : 1" +" ,";
                    if(error.equals("The password must contain at least 1 special character"))s8 = "\"The password must contain at least 1 special character\" : 1" +" ," ;
                    if(error.equals("You are not enough age"))s9 = "\"You are not enough age\" : 1"  +" ," ;
                    if(error.equals("You are troll !"))s9plusplus = "\"You are troll !\" :1"  +" ," ;
                    if(error.equals("The format of time is error , please type again"))s10 = "\"The format of time is error , please type again\" : 1" + "}" ;
                }
                if(!emailmessage.equals("VALID"))s1plus = "\"Email is INVALID\" : 1" +" ," ;
                response.getWriter().write(s1  + s1plus + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 +s9plus + s9plusplus  +  s10);
                return ;
            }
            // bat dau dang ki
            User x = new User(1,Username,passWord.hashCode(),full_name, date_of_birth,gender,"","", "","","avatar_image_path", "link_app","link_social","favor_fc","description_text",Username,0,0,true, 0, 0, 0,passWord.hashCode(),(new Date()).getTime(), 0 , 0);
            UserDAO config = new UserDAO();
            config.addObject(x);
            // set email
            // lay ra user_id sau khi dang ki
            int user_id = 0 ;
            for(Object j : config.getAllObjects()){
                User k = (User)j ;
                if(k.getUser_name().equals(Username))user_id = k.getUser_id() ;
            }
            EmailDAO config2 = new EmailDAO();
            try {// da ton tai email
                    List<Object> list = config2.getAllObjects(user_id);
                    Email client = (Email)list.get(0);
                    client.setEmail(yourdata.getString("email"));
                    boolean bool = config2.updateObject(client);
                } catch (Exception e) {//chua ton tai email
                    Email guest = new Email(1, user_id, yourdata.getString("email")) ;
                    config2.addObject(guest) ;
                }
            response.getWriter().write("{\"VALID\" : 1}");
        }
        catch (Exception e) {
            response.getWriter().write(e.toString());
            return ;
        }
    } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}

