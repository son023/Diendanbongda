/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

/**
 *
 * @author admin
 */
public class Email {
    private int email_id , user_id ;
    private String email ;

    public Email(int email_id, int user_id, String email) {
        this.email_id = email_id;
        this.user_id = user_id;
        this.email = email;
    }

    public int getEmail_id() {
        return email_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Email{" + "email_id=" + email_id + ", user_id=" + user_id + ", email=" + email + '}';
    }

    public void setEmail_id(int email_id) {
        this.email_id = email_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
