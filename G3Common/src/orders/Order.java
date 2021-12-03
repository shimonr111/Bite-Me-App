package orders;

import java.io.Serializable;

/**
 * 
 * @author Lior, Guzovsky.
 * Class description: 
 * 
 * This is a class that describes the 
 * entity of Order Object.
 * 
 * @version 03/12/2021
 */
public class Order implements Serializable{
	/**
	 * Class members description:
	 */
	
	private String resturantName;
	private String orderNumber;
	private String orderTime;
	private String phoneNumber;
	private String orderAddress;
	private String orderType;
	
	/**
	 * @param id
	 * @param private String resturantName;
	 * @param orderNumber;
	 * @param orderTime
	 * @param phoneNumber;
	 * @param orderAddress;
	 * @param OrderType;
	 */
	public Order(String resturantName, String orderNumber, String orderTime, String phoneNumber, String orderAddress, String orderType){
		super();
		this.resturantName = resturantName;
		this.orderNumber = orderNumber;
		this.orderTime = orderTime;
		this.phoneNumber = phoneNumber;
		this.orderAddress = orderAddress;
		this.orderType = orderType;

	}

	/**
	 * This section is for the 
	 * Setters and Getters of the 
	 * Class Customer
	 */
	public String getResturantName() {
		return resturantName;
	}


	public void setResturantName(String resturantName) {
		this.resturantName = resturantName;
	}


	public String getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getOrderAddress() {
		return orderAddress;
	}


	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	
	@Override
	public String toString() {
		return resturantName + " " + orderNumber + " " + orderTime  + " " + phoneNumber + " " + orderAddress  + " " + orderType;
	}
	
}
