package util;

import java.io.Serializable;

public class OrderForView implements Serializable{
	
	private String resturantName;
	private String orderDate;
	private String orderTime;
	private String orderDetails;
	private String orderStatus;
	
	public OrderForView(String resturantName, String orderDate, String orderTime, String orderDetails,
			String orderStatus) {
		super();
		this.resturantName = resturantName;
		this.orderDate = orderDate;
		this.orderTime = orderTime;
		this.orderDetails = orderDetails;
		this.orderStatus = orderStatus;
	}
	
	public String getResturantName() {
		return resturantName;
	}
	public void setResturantName(String resturantName) {
		this.resturantName = resturantName;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String toString() {
		return "Resturant Name : " + resturantName +"\nOrder Date: " + orderDate +"\nOrder Time: " + orderTime + "\nOrder Details : " +orderDetails + "\nOrder Status: " + orderStatus;
	}

	
}
