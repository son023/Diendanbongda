package dal.productdao;
import dal.dao.DAO;
import model.product.HasVoucher;
import model.product.Review;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ReviewDAO extends DAO{

    @Override
    public Object getById(int Review_id) {
        String sql = "select * from reviews where review_id = " + Review_id;	
            try {
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
                if (rs.next()){
		    return new Review (rs.getInt("review_id"), 
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("star"),
                        rs.getString("review_content"));
                       
                    }
		}
		catch (SQLException e) {

		}
		return null;   
    }

    @Override
    public boolean addObject(Object newReview) {
        try {
                Review review = (Review) newReview;
                String sql = "insert into reviews(user_id,product_id,star,review_content)"
                        + " values(?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1,review.getUser_id() );
                st.setInt(2,review.getProduct_id());
                st.setInt(3,review.getStar());
                st.setString(4,review.getReview_content());
                st.executeUpdate();
                return true;
		}
	catch (SQLException e) {
            return false;
	}
    }

    @Override
    public boolean updateObject(Object newObject) {
        return true;
    }

    @Override
    public boolean deleteObject(int review_id) {
        try {
                String sql = "delete from reviews where review_id = " + review_id;
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
            // ko cast List<Voucher> ve List<Object> dc @@@	

            return null;
    }
    public List<Review> queryObjects() {
        try {
            String sql = "select * from reviews";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Review> o = new ArrayList<> ();
            while (rs.next()) {
                    o.add(new Review(rs.getInt("review_id"), 
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("star"),
                        rs.getString("review_content")));
            }
            return o;
        }
        catch (SQLException e) {
                return null;
        }
    }
     public List<Review> getListReviewByProduct(int product_id) {
        try {
            String sql = "select * from reviews where product_id ="+ product_id;
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Review> o = new ArrayList<> ();
            while (rs.next()) {
                    o.add(new Review(rs.getInt("review_id"), 
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("star"),
                        rs.getString("review_content")));
                       
            }
            return o;
        }
        catch (SQLException e) {
                return null;
        }
    }  
}
        
    
    