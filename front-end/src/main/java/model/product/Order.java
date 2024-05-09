/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.product;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.user.User;

/**
 *
 * @author DELL
 */
public class Order {
    private int order_id;
    private int user_id;
    private Timestamp date;
    private List<ProductOrder> entries = new ArrayList<> ();
    private int status;
    private int total;
	private String address;
	private String contact;

    public Order(int order_id, int user_id, List<ProductOrder> entries, int total, String address, String contact) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.entries = entries;
        this.status = 0; // 0 - pending, 1 - delivering, 2 - delivered, 3 - failed
        this.total = total;
		this.address = address;
		this.contact = contact;
    }

	public int getOrder_id() {
		return order_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public Timestamp getDate() {
		return date;
	}

	public List<ProductOrder> getEntries() {
		return entries;
	}

    public int getTotal() {
        return total;
    }

	public int getStatus() {
		return status;
	}

	public String getAddress() {
		return address;
	}

	public String getContact() {
		return contact;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

    public void setTotal(int total) {
        this.total = total;
    }

    public void setAddress(String add) {
        this.address = add;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
