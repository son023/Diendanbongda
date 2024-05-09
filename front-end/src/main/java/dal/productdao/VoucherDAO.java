package dal.productdao;
import dal.dao.DAO;
import model.product.Voucher;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class VoucherDAO extends DAO{

    @Override
    public Object getById(int Voucher_id) {
        String sql = "select * from vouchers where voucher_id = " + Voucher_id;	
            try {
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
                if (rs.next()){
		    return new Voucher(rs.getInt("voucher_id"), 
                        rs.getInt("discount_amount"),
                        rs.getInt("expire_time"));
                       
                    }
		}
		catch (SQLException e) {

		}
		return null;   
    }

    @Override
    public boolean addObject(Object newVoucher) {
        try {
                Voucher v = (Voucher) newVoucher;
                String sql = "insert into vouchers(discount_amount,expire_time)"
                        + " value(?, ?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1,v.getDiscount_amount());
                st.setInt(2,v.getExpire_time());
                st.executeUpdate();
                return true;
		}
	catch (SQLException e) {
            return false;
	}
    }

    @Override
    public boolean updateObject(Object newVoucher) {
        try {
                Voucher v = (Voucher) newVoucher;
                String sql = "update vouchers set discount_amount=?, expire_time=? where voucher_id= "+ v.getVoucher_id();
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1,v.getDiscount_amount());
                st.setInt(2,v.getExpire_time());
                st.executeUpdate();
                return true;
		}
	catch (SQLException e) {
            return false;
	}
    }

    @Override
    public boolean deleteObject(int Voucher_id) {
        try {
                String sql = "delete from vouchers where voucher_id = " + Voucher_id;
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
    public List<Voucher> queryObjects() {
        try {
            String sql = "select * from vouchers";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Voucher> o = new ArrayList<> ();
            while (rs.next()) {
                    o.add(new Voucher(rs.getInt("voucher_id"), 
                        rs.getInt("discount_amount"),
                        rs.getInt("expire_time")));
                       
            }
            return o;
        }
        catch (SQLException e) {
                return null;
        }
	}
}
        
    
    
   
