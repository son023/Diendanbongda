package model.product;

import java.sql.Date;


public class HasVoucher {
    private int has_voucher_id;
    private int user_id;
    private Voucher voucher;
    private Date expiration_date;

    public HasVoucher(int has_voucher_id, int user_id, Voucher voucher, Date expiration_date) {
        this.has_voucher_id = has_voucher_id;
        this.user_id = user_id;
        this.voucher = voucher;
        this.expiration_date = expiration_date;
    }

    public int getHas_voucher_id() {
        return has_voucher_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }
    // @Override
    // public String toString() {
    //     return "HasVoucher{" + "has_voucher_id=" + has_voucher_id + ", user_id=" + user_id + ", voucher_id=" + voucher_id + ", expiration_date=" + expiration_date + '}';
    // }
    
    
    
}




