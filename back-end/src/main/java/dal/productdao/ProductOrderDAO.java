/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.productdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.dao.DAO;
import model.product.Product;
import model.product.ProductOrder;

/**
 *
 * @author pc
 */
public class ProductOrderDAO extends DAO {

    public ProductOrderDAO() {
        super();
    }

    @Override
    public Object getById(int objectId) {
        try {
            String sql = "select * from products_orders where products_orders_id = " + objectId;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int prod_order_id = rs.getInt("product_order_id");
                int product_id = rs.getInt("product_id");
                Product prod = (Product) new ProductDAO().getById(product_id);
                int order_id = rs.getInt("order_id");
                String nametag = rs.getString("nametag");
                String color = rs.getString("color");
                int size = rs.getInt("size");
                int squadNumber = rs.getInt("squad_number");
                int quantity = rs.getInt("quantity");
                return new ProductOrder(prod_order_id, prod, order_id, nametag, color, size, squadNumber, quantity);
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    public List<ProductOrder> getByOrderId(int orderId) {
        try {
            String sql = "select * from products_orders where order_id = " + orderId;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<ProductOrder> res = new ArrayList<>();
            while (rs.next()) {
                int prod_order_id = rs.getInt("product_order_id");
                int product_id = rs.getInt("product_id");
                Product prod = (Product) new ProductDAO().getById(product_id);
                int order_id = rs.getInt("order_id");
                String nametag = rs.getString("nametag");
                String color = rs.getString("color");
                int size = rs.getInt("size");
                int squadNumber = rs.getInt("squad_number");
                int quantity = rs.getInt("quantity");
                res.add(new ProductOrder(prod_order_id, prod, order_id, nametag, color, size, squadNumber, quantity));
            }
            return res;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean addObject(Object object) {
        try {
            ProductOrder prodOrder = (ProductOrder) object;
            String sql = "insert into products_orders (product_id, order_id, nametag, color, size, squad_number, quantity) value (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, prodOrder.getProduct().getProduct_id());
            st.setInt(2, prodOrder.getOrder_id());
            st.setString(3, prodOrder.getNametag());
            st.setString(4, prodOrder.getColor());
            st.setInt(5, prodOrder.getSize());
            st.setInt(6, prodOrder.getSquadNumber());
            st.setInt(7, prodOrder.getQuantity());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateObject(Object object) {
        // ca Order va moi entry trong Order deu khong de chinh sua
        return false;
    }

    @Override
    public boolean deleteObject(int objectId) {
        try {
            String sql = "delete from products_orders where product_order_id = " + objectId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteByOrderId(int orderId) {
        try {
            String sql = "delete from products_orders where order_id = " + orderId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Object> getAllObjects() {
        // ko cast duoc
        return null;
    }
}
