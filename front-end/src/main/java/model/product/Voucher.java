/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

/**
 *
 * @author DELL
 */
public class Voucher {
    private int voucher_id;
    private int discount_amount;
    private int expire_time;

    public Voucher(int voucher_id, int discount_amount, int expire_time) {
        this.voucher_id = voucher_id;
        this.discount_amount = discount_amount;
        this.expire_time = expire_time;
    }

    public int getVoucher_id() {
        return voucher_id;
    }
    public int getDiscount_amount() {
        return discount_amount;
    }
    public int getExpire_time() {
        return expire_time;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }
    
    
    @Override
    public String toString() {
        return "Voucher{" + "voucher_id=" + voucher_id + ", discount_amount=" + discount_amount + ", expire_time=" + expire_time + '}';
    }

    public void getExpire_time(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
}
