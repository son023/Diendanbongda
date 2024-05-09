/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

/**
 *
 * @author admin
 */
public class PhoneNumber {
    private int phone_number_id , user_id ;
    private String phone_number ;

    public PhoneNumber(int phone_number_id, int user_id, String phone_number) {
        this.phone_number_id = phone_number_id;
        this.user_id = user_id;
        this.phone_number = phone_number;
    }

    public int getPhone_number_id() {
        return phone_number_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" + "phone_number_id=" + phone_number_id + ", user_id=" + user_id + ", phone_number=" + phone_number + '}';
    }

    public void setPhone_number_id(int phone_number_id) {
        this.phone_number_id = phone_number_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    
    
}
