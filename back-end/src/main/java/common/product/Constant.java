/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common.product;

/**
 *
 * @author DELL
 */
public class Constant {
    //VOUCHER
    public static final String URL_VOUCHER_POST_AND_GET= "/product/voucher";
    public static final String URL_VOUCHER_DELETE_VOUCHER_BY_ID= "/product/voucher/delete/*";
    public static final String URL_VOUCHER_EDIT= "/product/voucher/edit";
    public static final String URL_VOUCHER_GET_ALL_VOUCHER="/product/voucher/getallvoucher";
    
    //HAS VOUCHER
    public static final String URL_HAS_VOUCHER_POST_AND_GET= "/product/hasvoucher";
    public static final String URL_HAS_VOUCHER_DELETE_BY_ID= "/product/hasvoucher/deletebyid/*";
    public static final String URL_HAS_VOUCHER_DELETE_WHEN_EXPIRED= "/product/hasvoucher/deletewhenexpire";
    public static final String URL_HAS_VOUCHER_EDIT="/product/hasvoucher/edit";
    public static final String URL_HAS_VOUCHER_GET_BY_USER="/product/hasvoucher/getbyuser/*";
    
    //REVIEW
    public static final String ULR_REVIEW_POST_AND_GET="/product/review";
    public static final String URL_LIST_REVIEW_GET_BY_PRODUCT_ID="/product/review/getlistbyproduct/*"; // lấy ra <List>những review mà Product đang có
    public static final String URL_REVIEW_DELETE_BY_ID="/product/review/deletebyid/*";
    
}