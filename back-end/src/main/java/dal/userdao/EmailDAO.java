package dal.userdao;
import dal.dao.DAO;
import model.user.Email;
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
import java.util.HashSet;
import java.util.Set;
// các hàm khả dụng
// addObject(Object object) : 1 người dùng đăng kí 1 email
// updateObject(Object object) : người dùng sửa email của họ
// deleteObject(int objectId) : người dùng xóa email của họ
// getAllObjects(int Record_id) : trả về 1 list các email của người dùng , trả về null nếu không có email nào
public class EmailDAO extends DAO {
    public EmailDAO (){
        super();
    }
    public Object getById(int Record_id){
        return null ;
    }
    public  boolean addObject(Object object){
        try {
            Email a = (Email)object ;
            String sql = "insert into emails values(?,?,?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setNull(1, Types.INTEGER);
            stm.setInt(2, a.getUser_id());
            stm.setString(3, a.getEmail());
            return stm.executeUpdate() > 0 ;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public  boolean updateObject(Object object){
        try {
            Email a = (Email)object ;
            String sql = "update emails set email_id = ? , user_id = ? , email = ?  where email_id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, a.getEmail_id());
            stm.setInt(2, a.getUser_id());
            stm.setString(3, a.getEmail());
            stm.setInt(4, a.getEmail_id());
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public boolean deleteObject(int objectId){
        String sql = "delete from emails where email_id  = ?" ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1,objectId);
            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public List<Object> getAllObjects(){
        String sql = "select * from emails " ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet res = stm.executeQuery();
            List<Object> list = new ArrayList<>();
            while(res.next()){
                list.add((Object) new Email(res.getInt(1) , res.getInt(2) , res.getString(3)));
            }
            if(list.size() > 0)return list ;
            return null ;
        } catch (Exception e) {
            return null;
        }
    }
    public List<Object>getAllObjects(int Record_id){
        String sql = "select * from emails where user_id  = ? " ;
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, Record_id);
            ResultSet res = stm.executeQuery();
            List<Object> list = new ArrayList<>();
            while(res.next()){
                list.add((Object) new Email(res.getInt(1) , res.getInt(2) , res.getString(3)));
            }
            if(list.size() > 0)return list ;
            return null ;
        } catch (Exception e) {
            return null;
        }
    }
}
