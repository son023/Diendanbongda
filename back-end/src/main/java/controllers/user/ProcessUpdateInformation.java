/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers.user;

import dal.userdao.EmailDAO;
import dal.userdao.PhoneNumberDAO;
import dal.userdao.UserDAO;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.user.Email;
import model.user.PhoneNumber;
import model.user.User;
import jakarta.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.json.JSONObject;
//update gender
// update full_name
//update Date_of_birth
// update favor_fcemail
// update email
//update phone_number
// update address
// update description
// 

// co 2 loai json
/* 1 la {
    "VALID": 1
}
*/

// 2 la
/*
{
    "Size of Fullname is between 5 and 15": 0,
    "VALID": 0,
    "The format of time is error , please type again": 1,
    "Email is INVALID": 0,
    "PhoneNumber is INVALID": 0,
}
*/

// VALID = 0 ->> khong hop le
// VALID = 1 ->> hop le
// message = 1 ->> xảy ra lỗi này
// ví dụ  "The format of time is error , please type again": 1

/* cac thong tin frontend can gui
// gender : boolean
//  full_name : string
// date_of_birth : string
//  favor_fc : String
//  email :string
// phone_number :String
//  country :String
// city :String
//district :String
// detail_position :String
// description_text :String
//  đặt tên biến chuẩn như thế kia để khớp với backend
*/
@WebServlet(name = "ProcessUpdateInformation", urlPatterns = { "/ProcessUpdateInformation" })
public class ProcessUpdateInformation extends HttpServlet {
    private boolean checkCharacter(Character c) { // check ki tu dac biet
        int x = c + 0;
        if (x >= 48 && x <= 57)
            return true;
        else if (x >= 65 && x <= 90)
            return true;
        else if (x >= 97 && x <= 122)
            return true;
        else
            return false;
    }

    private String check(String Username) { // check ki tu dac biet va sai cu phap
        // check username
        if (Username.length() > 15 || Username.length() < 5)
            return ("Size of Fullname is between 5 and 15");
        return "VALID";
    }

    private String updateFullName(String full_name) {
        try {
            if (full_name.length() > 100 || full_name.length() < 5)
                return ("Size of Fullname is between 5 and 15");
            return "VALID";
        } catch (Exception e) {
            return "INVALID";
        }
    }

    private String updateDateOfBirth(String date_of_birth) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            formatter.setLenient(false);
            Date date = formatter.parse(date_of_birth);
            if ((new Date()).getTime() - date.getTime() < 0)
                return "You are troll !";
        } catch (Exception e) {
            return ("The format of time is error , please type again");
        }
        return "VALID";
    }

    private String updateEmail(String email) {
        try {
            if (!email.substring(email.length() - 10).equals("@gmail.com"))
                return "Email is INVALID";
            for (int i = 0; i < email.length() - 10; i++) {
                if (!checkCharacter(email.charAt(i)))
                    return "Email is INVALID";
            }
        } catch (Exception e) {
            return "Email is INVALID";
        }
        return "VALID";
    }

    private String updatePhoneNumber(String PhoneNumber) {
        try {
            for (Character x : PhoneNumber.toCharArray()) {
                if (!Character.isDigit(x))
                    return "PhoneNumber is INVALID";
            }
        } catch (Exception e) {
            return "PhoneNumber is INVALID";
        }
        return "VALID";
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            BufferedReader reader = request.getReader();
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            reader.close();
            // Sử dụng Gson để chuyển đổi JSON thành đối tượng Java
            // lay ra cac du lieu
            JSONObject yourdata = new JSONObject(json.toString());
            // lay ra user_id
            int user_id = yourdata.getInt("user_id");
            // lay ra User tu database
            User yen = (User) (new UserDAO()).getById(user_id);
            // process
            String s1 = updateFullName(yourdata.getString("full_name").trim());
            String s2 = updateDateOfBirth(yourdata.getString("date_of_birth").trim());
            String s4 = updateEmail(yourdata.getString("email").trim());
            String s5 = updatePhoneNumber(yourdata.getString("phone_number").trim());
            if (s1.equals("VALID") && s2.equals("VALID") && s4.equals("VALID") && s5.equals("VALID")) {
                // update database
                UserDAO config1 = new UserDAO();
                EmailDAO config2 = new EmailDAO();
                PhoneNumberDAO config3 = new PhoneNumberDAO();
                yen.setFull_name(yourdata.getString("full_name").trim());
                yen.setDate_of_birth(yourdata.getString("date_of_birth").trim());
                yen.setAvatar_image_path(yourdata.getString("avatar_image_path").trim());
                yen.setFavor_fc(yourdata.getString("favor_fc").trim());
                // set address
                yen.setUser_role(yourdata.getInt("user_role"));
                yen.setScore_to_award(yourdata.getInt("score_to_award"));
                yen.setCountry(yourdata.getString("country").trim());
                yen.setCity(yourdata.getString("city").trim());
                yen.setDistrict(yourdata.getString("district").trim());
                yen.setDetail_position(yourdata.getString("detail_position").trim());
                // set description
                yen.setDescription_text(yourdata.getString("description_text").trim());
                yen.setGender(yourdata.getBoolean("gender"));
                // set Phone
                try {
                    List<Object> list = config3.getAllObjects(user_id);
                    PhoneNumber client = (PhoneNumber) list.get(0);
                    client.setPhone_number(yourdata.getString("phone_number"));
                    boolean bool = config3.updateObject(client);
                } catch (Exception e) {
                    PhoneNumber guest = new PhoneNumber(1, user_id, yourdata.getString("phone_number"));
                    config3.addObject(guest);
                }
                // set Email
                try {
                    List<Object> list = config2.getAllObjects(user_id);
                    Email client = (Email) list.get(0);
                    client.setEmail(yourdata.getString("email"));
                    boolean bool = config2.updateObject(client);
                } catch (Exception e) {
                    Email guest = new Email(1, user_id, yourdata.getString("email"));
                    config2.addObject(guest);
                }
                // update database
                config1.updateObject((Object) yen);
                // nem message
                response.getWriter().write("{\"VALID\" : 1}");
            } else {
                String a1 = "\"Size of Fullname is between 5 and 15\" : 0,";
                String a2 = "\"VALID\" : 0,";
                String a3 = "\"The format of time is error , please type again\" : 0,";
                String a4 = "\"You are troll !\" : 0,";
                String a5 = "\"Email is INVALID\" : 0,";
                String a6 = "\"PhoneNumber is INVALID\" : 0,";
                if (!s1.equals("VALID"))
                    a1 = "\"Size of Fullname is between 5 and 15\" : 1,";
                if (!s2.equals("VALID"))
                    a3 = "\"The format of time is error , please type again\" : 1,";
                if (!s4.equals("VALID"))
                    a5 = "\"Email is INVALID\" : 1,";
                if (!s5.equals("VALID"))
                    a6 = "\"PhoneNumber is INVALID\" : 1";
                response.getWriter().write("{" + a1 + a2 + a3 + a5 + a6 + "}");
            }
        } catch (Exception e) {
            response.getWriter().write(e.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}