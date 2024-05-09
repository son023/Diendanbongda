package dal.productdao;

import dal.dao.DAO;
import model.product.Product;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDAO extends DAO {
	
	public ProductDAO() {
		super();
	}

	@Override
	public Object getById(int id) {
		String sql = "select * from products where product_id = " + id;	
		try {
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			if (rs.next()){
				return new Product(rs.getInt("product_id"), 
									rs.getString("product_name"), 
									rs.getBoolean("category"), 
									rs.getString("imagePath"),
									rs.getString("team"),
									rs.getInt("price"),
									rs.getFloat("rating"),
									rs.getInt("sold"),
									rs.getInt("discounted"),
									rs.getInt("total_rating_time"));
			}
		}
		catch (SQLException e) {

		}
		return null;
	}
	
	@Override
	public boolean addObject(Object object) {
		return false;
	}

	public int addProduct(Object newProd) {
		try {
			Product p = (Product) newProd;
			String sql = "insert into products(product_name, category, imagePath, team, price, rating, sold, discounted, total_rating_time)"
						+ " value(?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, p.getProduct_name());
			st.setBoolean(2, p.getCategory());
			st.setString(3, p.getImagePath());
			st.setString(4, p.getTeam());
			st.setInt(5, p.getPrice());
			st.setFloat(6, p.getRating());
			st.setInt(7, p.getSold());
			st.setInt(8, p.getDiscounted());
			st.setInt(9, p.getTotalRatingTime());
			int affected = st.executeUpdate();
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
	public boolean updateObject(Object newProd) {
		try {
			Product p = (Product) newProd;
			String sql = "update products set product_name = ?, category = ?, imagePath = ?, team = ?, price = ?, rating = ?, sold = ?, discounted = ?, total_rating_time = ? where product_id = " + p.getProduct_id();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, p.getProduct_name());
			st.setBoolean(2, p.getCategory());
			st.setString(3, p.getImagePath());
			st.setString(4, p.getTeam());
			st.setInt(5, p.getPrice());
			st.setFloat(6, p.getRating());
			st.setInt(7, p.getSold());
			st.setInt(8, p.getDiscounted());
			st.setInt(9, p.getTotalRatingTime());
			st.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			return false;
		}
	}

	public boolean sellProduct(int productId) {
		try {
			String sql = "update products set sold = sold + 1 where product_id = " + productId;
			PreparedStatement st = con.prepareStatement(sql);
			st.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean deleteObject(int id) {
		try {
			String sql = "delete from products where product_id = " + id;
			PreparedStatement st = con.prepareStatement(sql);
			st.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			return false;
		}
	}

	@Override 
	public List<Object> getAllObjects() {
		// ko cast List<Product> ve List<Object> dc @@@	
		
		return null;
	}

	public List<Product> queryObjects(Map<String, Object> filter) {
		//filtered query

		// filter: name, category, team, price, sold, discounted, rating, total_rating_time

		//category: 0 - ao, 1 - giay
		try {
			ArrayList<String> filteredFields = new ArrayList<> ();
			
			if (filter.get("product_name") != null) {
				filteredFields.add("product_name like '%" + filter.get("product_name") + "%'");
			}

			if (filter.get("category") != null) {
				filteredFields.add("category = " + filter.get("category"));
			}

			if (filter.get("team") != null) {
				filteredFields.add("team like %" + filter.get("team") + "%");
			}
			
			if (filter.get("priceLow") != null) {
				filteredFields.add("price >= " + filter.get("priceLow"));
			}
			
			if (filter.get("priceHigh") != null) {
				filteredFields.add("price <= " + filter.get("priceHigh"));
			}
			
			if (filter.get("soldLow") != null) {
				filteredFields.add("sold >= " + filter.get("soldLow"));
			}
			
			if (filter.get("soldHigh") != null) {
				filteredFields.add("sold <= " + filter.get("soldHigh"));
			}
			
			if (filter.get("discounted") != null) {
				filteredFields.add("discounted >= " + filter.get("discounted"));
			}
			
			if (filter.get("ratingLow") != null) {
				filteredFields.add("rating >= " + filter.get("ratingLow"));
			}
			
			if (filter.get("ratingHigh") != null) {
				filteredFields.add("rating <= " + filter.get("ratingHigh"));
			}
			
			if (filter.get("total_rating_time_low") != null) {
				filteredFields.add("total_rating_time >= " + filter.get("total_rating_time_low"));
			}
			
			if (filter.get("total_rating_time_high") != null) {
				filteredFields.add("total_rating_time <= " + filter.get("total_rating_time_high"));
			}
			
			String sql = "select * from products";
			if (filteredFields.size() != 0) {
				sql += " where " + filteredFields.get(0);
				for (int i = 1; i < filteredFields.size(); i++) {
					sql += " and " + filteredFields.get(i);
				}	
			}
			
			PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Product> o = new ArrayList<Product> ();
			while (rs.next()) {
				o.add(new Product(rs.getInt("product_id"), 
									rs.getString("product_name"), 
									rs.getBoolean("category"), 
									rs.getString("imagePath"),
									rs.getString("team"),
									rs.getInt("price"),
									rs.getFloat("rating"),
									rs.getInt("sold"),
									rs.getInt("discounted"),
									rs.getInt("total_rating_time")));
			}
			return o;
		}
		catch (SQLException e) {
			return null;
		}
	}
}
