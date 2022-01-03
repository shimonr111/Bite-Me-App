package orders;

import java.io.Serializable;


/**
* 
* @author Lior, Guzovsky.
* 
*  Class description:
*  This is class manages all the order flow values.
*  
*  @version 9/12/2021
*/
public class OrderForView implements Serializable{
	
	/**
	 * Class members:
	 */
	private int orderNum;
	private String resturantName;
	private String orderDate;
	private String orderTime;
	private String orderDetails;
	private String orderStatus;
	
	/**
	 * Constructor:
	 */
	public OrderForView(String resturantName, String orderDate, String orderTime, String orderDetails,
			String orderStatus,int orderNum) {
		super();
		this.resturantName = resturantName;
		this.orderDate = orderDate;
		this.orderTime = orderTime;
		this.orderDetails = orderDetails;
		this.orderStatus = orderStatus;
		this.orderNum = orderNum;
	}
	
	/**
	 * Getters and Setters:
	 */
	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
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
	
	/**
	 * The toString of this class:
	 */
	public String toString() {
		return "Resturant Name : " + resturantName +"\nOrder Date: " + orderDate +"\nOrder Time: " + orderTime + "\nOrder Details : " +orderDetails + "\nOrder Status: " + orderStatus;
	}

	
}
