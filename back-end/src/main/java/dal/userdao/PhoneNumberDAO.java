package dal.userdao;


import dal.dao.DAO;
import model.user.Email;
import model.user.PhoneNumber;
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
import java.util.ArrayList;

// các hàm khả dụng
// addObject(Object object) : 1 người dùng đăng kí 1 sđt
// updateObject(Object object) : người dùng sửa sđt của họ
// deleteObject(int objectId) : người dùng xóa sđt của họ
// getAllObjects(int Record_id) : trả về 1 list các sđt của người dùng , trả về null nếu không có sđt nào
//getAllObjects() : trả về tất cả sđt trong database
public class PhoneNumberDAO extends DAO {
    public PhoneNumberDAO (){
        super();
    }
    public Object getById(int Record_id){
        return null ;
    }
    public  boolean addObject(Object object){
        try {
            PhoneNumber a = (PhoneNumber)object ;
            String sql = "insert into phone_numbers values(?,?,?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setNull(1, Types.INTEGER);
            stm.setInt(2, a.getUser_id());
            stm.setString(3, a.getPhone_number());
            return stm.executeUpdate() > 0 ;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public  boolean updateObject(Object object){
        try {
            PhoneNumber a = (PhoneNumber)object ;
            String sql = "update phone_numbers set phone_number_id = ? , user_id = ? , phone_number = ?  where phone_number_id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, a.getPhone_number_id());
            stm.setInt(2, a.getUser_id());
            stm.setString(3, a.getPhone_number());
            stm.setInt(4, a.getPhone_number_id());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean deleteObject(int objectId){
        String sql = "delete from phone_numbers where user_id = ?" ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1,objectId);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public List<Object> getAllObjects(){
        String sql = "select * from phone_numbers  " ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet res = stm.executeQuery();
            List<Object> list = new ArrayList<>();
            while(res.next()){
                list.add((Object) new PhoneNumber(res.getInt(1) , res.getInt(2) , res.getString(3)));
            }
            if(list.size() > 0)return list ;
            return null ;
        } catch (Exception e) {
            return null;
        }
    }
    public List<Object>getAllObjects(int Record_id){
        String sql = "select * from phone_numbers where user_id  = ? " ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, Record_id);
            ResultSet res = stm.executeQuery();
            List<Object> list = new ArrayList<>();
            while(res.next()){
                list.add((Object) new PhoneNumber(res.getInt(1) , res.getInt(2) , res.getString(3)));
            }
            if(list.size() > 0)return list ;
            return null ;
        } catch (Exception e) {
            return null;
        }
    }
}
