/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.dao;

/**
 *
 * @author pc
 */
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
/**
 *
 * @author admin
 */
public abstract class DAO {
    protected Connection con ;
    public DAO (){
        if(con == null){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3307/btl-java?autoReconnect=true&useSSL=false";
                String username = "root";
                String password = "admin123";
                con = DriverManager.getConnection(url,username,password);
//                System.out.println("sucessfully");
            }
            catch(Exception e){
//                System.out.println("FAIL");
            }
        }
    }
    
//    public static void main(String[] args) {
//        DAO d = new DAO();
//    }
    public abstract Object getById(int Record_id);
    public abstract boolean addObject(Object object);
    public abstract boolean updateObject(Object object);
    public abstract boolean deleteObject(int objectId);
    public abstract List<Object> getAllObjects();
}