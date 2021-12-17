package orders;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import users.Branch;

/**
 * 
 * @author Ori, Malka.
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * 
 * Class description:
 * This class describes the Order Entity.
 *  
 * @version 17/12/2021
 */
public class Order implements Serializable {
	/**
	 * Class members:
	 */
	
	/**
	 * The orderNumber, primary key, need to be unique.
	 */
	public int orderNumber;
	
	/**
	 * The Supplier user id, who we order from - FK.
	 */
	public String supplierUserId;
	
	/**
	 * The Customer user id, the one who orders - FK.
	 */
	public String customerUserId;
	
	/**
	 * The branch we order from - saved to optimize the reports query. 
	 */
	public Branch branch;
	
	/**
	 * The order time type.
	 */
	public OrderTimeType timeType;
	
	/**
	 * The order status.
	 */
	public OrderStatus status;
	
	/**
	 * The order issue date and time - MySql DateTime format.
	 */
	public Date issueDateTime;
	
	/**
	 * The order desired supply date and time - MySql DateTime format.
	 */
	public Date estimatedSupplyDateTime;
	
	/**
	 * The order actual recive time - MySql DateTime format.
	 */
	public Date actualSupplyDateTime;
	/**
	 * The order desired supply type.
	 */
	public SupplyType supplyType;
	
	/**
	 * The order supply id - FK, unique to each supply object.
	 */
	public int supplyId;
	
	/**
	 * The orders total price;
	 */
	public double totalPrice;
	
	/**
	 * The order items
	 */
	
	public ArrayList<Item> itemList;
	
	
	/**
	 * This section is for the attributes
	 * of the supply process, we need to 
	 * save here all the information about the receiver
	 * of the supply.
	 * 
	 */
	
	
	/**
	 * Constructors:
	 */
	
	/**
	 * This constructor used only when ~~passing~~ object.
	 * 
	 * @param orderNumber
	 * @param supplierUserId
	 * @param customerUserId
	 * @param branch
	 * @param timeType
	 * @param status
	 * @param issueDateTime
	 * @param estimatedSupplyDateTime
	 * @param actualSupplyDateTime
	 * @param supplyType
	 * @param supplyId
	 * @param totalPrice
	 * @param itemList
	 */
	public Order(int orderNumber, String supplierUserId, String customerUserId, Branch branch, OrderTimeType timeType,
			OrderStatus status, Date issueDateTime, Date estimatedSupplyDateTime, Date actualSupplyDateTime,
			SupplyType supplyType, int supplyId, double totalPrice, ArrayList<Item> itemList) {
		super();
		this.orderNumber = orderNumber;
		this.supplierUserId = supplierUserId;
		this.customerUserId = customerUserId;
		this.branch = branch;
		this.timeType = timeType;
		this.status = status;
		this.issueDateTime = issueDateTime;
		this.estimatedSupplyDateTime = estimatedSupplyDateTime;
		this.actualSupplyDateTime = actualSupplyDateTime;
		this.supplyType = supplyType;
		this.supplyId = supplyId;
		this.totalPrice = totalPrice;
		this.itemList = itemList;
	}



	/**
	 * @param supplierUserId
	 * @param customerUserId
	 * @param branch
	 */
	public Order(String supplierUserId, String customerUserId, Branch branch) {
		super();
		this.orderNumber = 0; //TBD, Need to create Unique order number
		this.supplierUserId = supplierUserId;
		this.customerUserId = customerUserId;
		this.branch = branch;
		this.timeType = OrderTimeType.REGULAR; //default, need to update when getting the supply time.
		this.status = OrderStatus.PENDING_APPROVAL; //default when creating new order.
		this.issueDateTime = new Date();
		
		this.estimatedSupplyDateTime = null;
		this.actualSupplyDateTime = null;
		this.supplyType = null;
		this.supplyId = 0;
		this.totalPrice = 0;
		itemList = new ArrayList<Item>();
		
	} 
	
	/**
	 * Methods:
	 */
	
	
	/**
	 * Getters and Setters:
	 */

	public int getOrderNumber() {
		return orderNumber;
	}


	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}


	public String getSupplierUserId() {
		return supplierUserId;
	}


	public void setSupplierUserId(String supplierUserId) {
		this.supplierUserId = supplierUserId;
	}


	public String getCustomerUserId() {
		return customerUserId;
	}


	public void setCustomerUserId(String customerUserId) {
		this.customerUserId = customerUserId;
	}


	public Branch getBranch() {
		return branch;
	}


	public void setBranch(Branch branch) {
		this.branch = branch;
	}


	public OrderTimeType getTimeType() {
		return timeType;
	}


	public void setTimeType(OrderTimeType timeType) {
		this.timeType = timeType;
	}


	public OrderStatus getStatus() {
		return status;
	}


	public void setStatus(OrderStatus status) {
		this.status = status;
	}


	public Date getIssueDateTime() {
		return issueDateTime;
	}


	public void setIssueDateTime(Date issueDateTime) {
		this.issueDateTime = issueDateTime;
	}

	public SupplyType getSupplyType() {
		return supplyType;
	}


	public void setSupplyType(SupplyType supplyType) {
		this.supplyType = supplyType;
	}


	public int getSupplyId() {
		return supplyId;
	}


	public void setSupplyId(int supplyId) {
		this.supplyId = supplyId;
	}


	public double getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Date getEstimatedSupplyDateTime() {
		return estimatedSupplyDateTime;
	}


	public void setEstimatedSupplyDateTime(Date estimatedSupplyDateTime) {
		this.estimatedSupplyDateTime = estimatedSupplyDateTime;
	}

	

	public Date getActualSupplyDateTime() {
		return actualSupplyDateTime;
	}



	public void setActualSupplyDateTime(Date actualSupplyDateTime) {
		this.actualSupplyDateTime = actualSupplyDateTime;
	}

	@Override
	public String toString() {
		return "Order [orderNumber=" + orderNumber + ", supplierUserId=" + supplierUserId + ", customerUserId="
				+ customerUserId + ", branch=" + branch + ", timeType=" + timeType + ", status=" + status
				+ ", issueDateTime=" + issueDateTime + ", estimatedSupplyDateTime=" + estimatedSupplyDateTime
				+ ", actualSupplyDateTime=" + actualSupplyDateTime + ", supplyType=" + supplyType + ", supplyId="
				+ supplyId + ", totalPrice=" + totalPrice + ", itemList=" + itemList + "]";
	}
	
}
