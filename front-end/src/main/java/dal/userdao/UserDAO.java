package dal.userdao;
import dal.dao.DAO;
import model.user.User;

import com.google.gson.Gson;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.Date;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
// các hàm khả dụng
//getByID(int Record_id) : tra ve ban ghi cua id
// addObject(Object object) : 1 người dùng đăng kí tài khooản
// updateObject(Object object) : người dùng sửa thông tin cá nhân của họ
// deleteObject(int objectId) : người dùng xóa tài khoản của họ đồng thời xóa hết các bản ghi tại các  bảng tham chiếu tới (làm trong servlet)
// getAllObjects() : trả về object user , trả về null nếu không có user tương ứng
// de y permission va search viet sai chinh ta trong database
public class UserDAO extends DAO {
    public UserDAO (){
        super();
    }
    public  Object getById(int Record_id) {
       String sql = "select * from users where user_id  = ?" ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, Record_id);
            ResultSet res = stm.executeQuery();
            if(!res.next())return null ;
            int user_id_ = Integer.parseInt(res.getString("user_id")) ;
            String user_name_ = res.getString("user_name")  ;
            int pass_word_ = res.getInt("pass_word") ;
            String full_name_ = res.getString("full_name") ;
            String date_of_birth_  = res.getString("date_of_birth") ;
            boolean gender_ = false ;
            if(res.getString("gender").equals("1"))gender_ = true ;
            String country_ = res.getString("country") ;
            String city_ = res.getString("city") ;
            String district_ = res.getString("district");
            String detail_position_ = res.getString("detail_position");
            String avatar_image_path_ = res.getString("avatar_image_path");
            String link_app_ = res.getString("link_app");
            String link_social_ = res.getString("link_social");
            String Favor_fc_ = res.getString("Favor_fc") ;
            String description_text_ = res.getString("description_text");
            String uid_ = res.getString("uid") ;
            int user_role_ = Integer.parseInt(res.getString("user_role")) ;
            long namechange_cooldown_ = res.getLong("namechange_cooldown") ;
            boolean search_permission_ = false ;
            if(res.getString("search_permisson").equals("1"))search_permission_ = true ;
            int likes_ = Integer.parseInt(res.getString("likes")) ;
            int dislikes_ = Integer.parseInt(res.getString("dislikes")) ;
            int score_to_award_ = Integer.parseInt(res.getString("score_to_award"));
            int pass_word_latest_ = Integer.parseInt(res.getString("password_latest")) ;
            long pass_word_latest_time_ = res.getLong("password_latest_time") ;
            int login_fail_ = Integer.parseInt(res.getString("login_failed")) ;
            long login_cooldown_ = res.getLong("login_cooldown");
            return (Object)new User( user_id_,  user_name_,  pass_word_,  full_name_,  date_of_birth_,  gender_,  country_,  city_,  district_,  detail_position_,  avatar_image_path_,  link_app_,  link_social_,  Favor_fc_,  description_text_,  uid_,  user_role_,  namechange_cooldown_,  search_permission_,  likes_,  dislikes_,  score_to_award_,  pass_word_latest_,  pass_word_latest_time_,  login_fail_,  login_cooldown_);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public boolean addObject(Object x) {
        try {
            User a = (User)x ;
            String sql = "insert into users values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setNull(1, Types.INTEGER);
            stm.setString(2, a.getUser_name());
            stm.setInt(3,  a.getPass_word());
            stm.setString(4, a.getFull_name());
            stm.setString(5, a.getDate_of_birth());
            if(a.getGender())stm.setBoolean(6,true);
            else stm.setBoolean(6,false);
            stm.setString(7, a.getCountry());
            stm.setString(8, a.getCity());
            stm.setString(9, a.getDistrict());
            stm.setString(10, a.getDetail_position());
            stm.setString(11, a.getAvatar_image_path());
            stm.setString(12, a.getLink_app());
            stm.setString(13, a.getLink_social());
            stm.setString(14, a.getFavor_fc());
            stm.setString(15, a.getDescription_text());
            stm.setString(16, a.getUid());
            stm.setInt(17,   a.getUser_role());
            if(a.isSearch_permission()) stm.setBoolean(19, true);
            else stm.setBoolean(19,false);
            stm.setInt(20, a.getLikes());
            stm.setInt(21,   a.getDislikes());
            stm.setInt(22,   a.getScore_to_award());
            stm.setInt(23,a.getPass_word_latest());
            stm.setLong(24, a.getPass_word_latest_time());
            stm.setInt(25,  a.getLogin_fail());
            stm.setLong(18, a.getNamechange_cooldown());
            stm.setLong(26,  a.getLogin_cooldown());
            return stm.executeUpdate() > 0 ;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean updateObject( Object  x ) {
        try {
            User a = (User)x ;
            String sql = "update users set user_id = ?, user_name =?, pass_word =?, full_name =?,  date_of_birth =?,  gender =?,  country =?,  city =?,  district =?,  detail_position =?,  avatar_image_path =?,  link_app =?,   link_social =?,  favor_fc =?,  description_text =?,  uid =?,   user_role =?,  namechange_cooldown =?,   search_permisson =?,   likes =?,  dislikes =?,   score_to_award =?,   password_latest =?,   password_latest_time =?,  login_failed =?,   login_cooldown =?  where user_id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, a.getUser_id());
            stm.setString(2, a.getUser_name());
            stm.setInt(3, a.getPass_word());
            stm.setString(4, a.getFull_name());
            stm.setString(5,a.getDate_of_birth()) ;
            if(a.getGender())stm.setBoolean(6,true);
            else stm.setBoolean(6, false);
            stm.setString(7, a.getCountry());
            stm.setString(8, a.getCity());
            stm.setString(9, a.getDistrict());
            stm.setString(10, a.getDetail_position());
            stm.setString(11, a.getAvatar_image_path());
            stm.setString(12, a.getLink_app());
            stm.setString(13, a.getLink_social());
            stm.setString(14, a.getFavor_fc());
            stm.setString(15, a.getDescription_text());
            stm.setString(16, a.getUid());
            stm.setInt(17,  a.getUser_role());
            stm.setLong(18,   a.getNamechange_cooldown());
            if(a.isSearch_permission()) stm.setBoolean(19, true);
            else stm.setBoolean(19,false);
            stm.setInt(20,  a.getLikes());
            stm.setInt(21,   a.getDislikes());
            stm.setInt(22,  a.getScore_to_award());
            stm.setInt(23, a.getPass_word_latest());
            stm.setLong(24, a.getPass_word_latest_time());
            stm.setInt(25,  a.getLogin_fail());
            stm.setLong(26,  a.getLogin_cooldown());
            stm.setInt(27, a.getUser_id());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteObject(int objectId) {
        String sql = "delete from users where user_id  = ?" ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1,objectId);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public List<Object> getAllObjects() {
        String sql = "select * from users";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet res = stm.executeQuery();
            List<Object> list = new ArrayList<>();
            while(res.next()){
                int user_id_ = Integer.parseInt(res.getString("user_id")) ;
                String user_name_ = res.getString("user_name")  ;
                int pass_word_ = Integer.parseInt(res.getString("pass_word")) ;
                String full_name_ = res.getString("full_name") ;
                String date_of_birth_ = res.getString("date_of_birth") ;
                boolean gender_ = false ;
                if(res.getString("gender").equals("1"))gender_ = true ;
                String country_ = res.getString("country") ;
                String city_ = res.getString("city") ;
                String district_ = res.getString("district");
                String detail_position_ = res.getString("detail_position");
                String avatar_image_path_ = res.getString("avatar_image_path");
                String link_app_ = res.getString("link_app");
                String link_social_ = res.getString("link_social");
                String Favor_fc_ = res.getString("Favor_fc") ;
                String description_text_ = res.getString("description_text");
                String uid_ = res.getString("uid") ;
                int user_role_ = Integer.parseInt(res.getString("user_role")) ;
                long namechange_cooldown_ = res.getLong("namechange_cooldown") ;
                boolean search_permission_ = false ;
                if(res.getString("search_permisson").equals("1"))search_permission_ = true ;
                int likes_ = Integer.parseInt(res.getString("likes")) ;
                int dislikes_ = Integer.parseInt(res.getString("dislikes")) ;
                int score_to_award_ = Integer.parseInt(res.getString("score_to_award"));
                int pass_word_latest_ = Integer.parseInt(res.getString("password_latest")) ;
                long pass_word_latest_time_ = res.getLong("password_latest_time") ;
                int login_fail_ = Integer.parseInt(res.getString("login_failed")) ;
                long login_cooldown_ = Long.parseLong(res.getString("login_cooldown")) ;
                list.add((Object)new User( user_id_,  user_name_,  pass_word_,  full_name_,  date_of_birth_,  gender_,  country_,  city_,  district_,  detail_position_,  avatar_image_path_,  link_app_,  link_social_,  Favor_fc_,  description_text_,  uid_,  user_role_,  namechange_cooldown_,  search_permission_,  likes_,  dislikes_,  score_to_award_,  pass_word_latest_,  pass_word_latest_time_,  login_fail_,  login_cooldown_));
            }
            if(list.size() == 0)return new ArrayList<Object> ();
            return list;
        } catch (Exception e) {
            return new ArrayList<Object> ();
        }
    }
}
