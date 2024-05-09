/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

/**
 *
 * @author pc
 */
public class Product {
	private int product_id;
	private String product_name;
	private boolean category;
	private String imagePath;
	private String team;
	private int price;
	private float rating;
	private int total_rating_time;
	private int sold;
	private int discounted;

	public Product(int product_id, String product_name, boolean category, String imagePath, String team, int price, float rating, int sold, int discounted, int total_rating_time) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.category = category;
		this.imagePath = imagePath;
		this.team = team;
		this.price = price;
		this.rating = rating;
		this.total_rating_time = total_rating_time;
		this.sold = sold;
		this.discounted = discounted;
	}
	
	public int getProduct_id() {
		return product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public boolean getCategory() {
		return category;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getTeam() {
		return team;
	}

	public int getPrice() {
		return price;
	}

	public float getRating() {
		return rating;
	}

	public int getTotalRatingTime() {
		return total_rating_time;
	}

	public int getSold() {
		return sold;
	}

	public int getDiscounted() {
		return discounted;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public void setCategory(boolean category) {
		this.category = category;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public void setTotal_rating_time(int total_rating_time) {
		this.total_rating_time = total_rating_time;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public void setDiscounted(int discounted) {
		this.discounted = discounted;
	}


}
