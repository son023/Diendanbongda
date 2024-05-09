/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.productdao;
import controllers.product.ProcessOrder;
import dal.dao.DAO;
import model.product.Order;
import model.product.Product;
import model.product.ProductOrder;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author DELL
 */
public class OrderDAO extends DAO{


    public OrderDAO() {
        super();
    }

    @Override
    public Object getById(int order_id) {
        List<ProductOrder> entries = new ProductOrderDAO().getByOrderId(order_id);
        try {
            String sql = "select * from orders where order_id = " + order_id;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order newOrder = new Order(rs.getInt("order_id"), rs.getInt("user_id"), entries, rs.getInt("total"), rs.getString("address"), rs.getString("contact"));
                newOrder.setDate(rs.getTimestamp("date"));
                newOrder.setStatus(rs.getInt("status"));
                return newOrder;
            }
        }
        catch (SQLException e) {
            return null;
        }
        return null;
    }

    public List<Order> getByUserId(int user_id) {
        
        List<Order> res = new ArrayList<> ();
        try {
            List<Integer> orders = new ArrayList<> ();
            String sql = "select * from orders where user_id = " + user_id;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                orders.add(rs.getInt("order_id"));
            }
            for (int orderId: orders) {
                res.add((Order) this.getById(orderId));
            }
            return res;
        }
        catch (SQLException e) {
            return null;
        }
    }

    public List<Order> getOrderAdmin() {
        List<Order> res = new ArrayList<> ();
        try {
            List<Integer> orders = new ArrayList<> ();
            String sql = "select * from orders";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                orders.add(rs.getInt("order_id"));
            }
            for (int orderId: orders) {
                res.add((Order) this.getById(orderId));
            }
            return res;
        }
        catch (SQLException e) {
            return null;
        }
    }

    
    @Override
    public boolean addObject(Object object) {
        return false;
    }

    public int addOrder(Object object) {

        try {
            Order order = (Order) object;
            String sql = "insert into orders(user_id, status, total, address, contact) value (?, 0, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, order.getUser_id());
            st.setInt(2, order.getTotal());
            st.setString(3, order.getAddress());
            st.setString(4, order.getContact());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            else {
                return -1;
            }
        }
        catch (SQLException e) {
            return -1;
        }
    }
    

    @Override
    public boolean updateObject(Object object) {
        // Chi cho phep update trang thai cua order
        try {
            Order newOrder = (Order) object;
            String sql = "update orders set status = ? where order_id = " + newOrder.getOrder_id();
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, newOrder.getStatus());
            st.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }


    @Override
    public boolean deleteObject(int objectId) {
        try {
            String sql = "delete from orders where order_id = " + objectId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            ProductOrderDAO prodOrderDAO = new ProductOrderDAO();
            prodOrderDAO.deleteByOrderId(objectId);
            return true;
        } catch (SQLException e) {
//            System.out.println(e);
            return false;
        }
    }
    @Override
    public List<Object> getAllObjects() {
        //todo
        return null;
    }
    
}
