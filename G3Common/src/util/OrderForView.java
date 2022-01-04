package util;

import java.io.Serializable;

/**
*  Class description:
*  This is class manages all the order flow values.
*/
/**
* 
* @author Lior, Guzovsky.
* @author Mousa, Srour.
*  
*  @version 9/12/2021
*/
public class OrderForView implements Serializable{
	
	/**
	 * Class members:
	 */
	
	/**
	 * Order Number.
	 */
	private int orderNum;
	
	/**
	 * Restaurant Name.
	 */
	private String resturantName;
	
	/**
	 * Order Date.
	 */
	private String orderDate;
	
	/**
	 * Order Time.
	 */
	private String orderTime;
	
	/**
	 * Order Items+comments.
	 */
	private String orderDetails;
	
	/**
	 * Order Status.
	 */
	private String orderStatus;
	
	/**
	 * 
	 * @param resturantName
	 * @param orderDate
	 * @param orderTime
	 * @param orderDetails
	 * @param orderStatus
	 * @param orderNum
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
	 * 
	 * @return order number.
	 */
	public int getOrderNum() {
		return orderNum;
	}
	
	/**
	 * 
	 * @param orderNum
	 */
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
	
	/**
	 * 
	 * @return
	 */
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
