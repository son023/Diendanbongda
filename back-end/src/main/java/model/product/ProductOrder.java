/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

/**
 *
 * @author pc
 */
public class ProductOrder {
    private int order_id;
    private int quantity;
    private int product_order_id;
    private String nametag;
    private String color;
    private int size;
    private int squadNumber;
    private Product product; 

    public ProductOrder(int product_order_id, Product prod, int order_id, String nametag, String color, int size, int squadNumber, int quantity) {
        //0 - S, 1 - M, 2 - L, 3 - XL, 4 - XXL
        this.product_order_id = product_order_id;
        this.product = prod;
        this.order_id = order_id;
        this.nametag = nametag;
        this.color = color;
        this.size = size;
        this.squadNumber = squadNumber;
        this.quantity = quantity;
    }

	public int getOrder_id() {
		return order_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getProduct_order_id() {
		return product_order_id;
	}

	public String getNametag() {
		return nametag;
	}

	public String getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}

	public int getSquadNumber() {
		return squadNumber;
	}

	public Product getProduct() {
		return product;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setNametag(String nametag) {
		this.nametag = nametag;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setSquadNumber(int squadNumber) {
		this.squadNumber = squadNumber;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

    
    
}
