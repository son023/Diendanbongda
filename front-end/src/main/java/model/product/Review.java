/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

/**
 *
 * @author DELL
 */
public class Review {
    private int review_id;
    private int user_id;
    private int product_id;
    private int star;
    private String review_content;

    public Review(int review_id, int user_id, int product_id, int star, String review_content) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.star = star;
        this.review_content = review_content;
    }

    public int getReview_id() {
        return review_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getStar() {
        return star;
    }

    public String getReview_content() {
        return review_content;
    }
    

    @Override
    public String toString() {
        return "Review{" + "review_id=" + review_id + ", user_id=" + user_id + ", product_id=" + product_id + ", star=" + star + ", review_content=" + review_content + '}';
    }
    
    
    
    
}