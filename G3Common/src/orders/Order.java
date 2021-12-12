package orders;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import users.Branch;

/**
 * 
 * @author Ori, Malka.
 *  Class description:
 *  This calss describes the Order Entity.
 * @version 11/12/2021
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
	public Date supplyDateTime;
	
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
	 * Constructors:
	 */
	
	/**
	 * This constructor used only when ~~passing~~ object.
	 * @param orderNumber
	 * @param supplierUserId
	 * @param customerUserId
	 * @param branch
	 * @param timeType
	 * @param status
	 * @param issueDateTime
	 * @param supplyDateTime
	 * @param supplyType
	 * @param supplyId
	 * @param totalPrice
	 */
	public Order(int orderNumber, String supplierUserId, String customerUserId, Branch branch, OrderTimeType timeType,
			OrderStatus status, Date issueDateTime, Date supplyDateTime, SupplyType supplyType, int supplyId,
			double totalPrice) {
		super();
		this.orderNumber = orderNumber;
		this.supplierUserId = supplierUserId;
		this.customerUserId = customerUserId;
		this.branch = branch;
		this.timeType = timeType;
		this.status = status;
		this.issueDateTime = issueDateTime;
		this.supplyDateTime = supplyDateTime;
		this.supplyType = supplyType;
		this.supplyId = supplyId;
		this.totalPrice = totalPrice;
		
		//TBD, need to check what to do with items.
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
		
		//TBD, need to update when getting.
		this.supplyDateTime = null;
		this.supplyType = null;
		this.supplyId = 0;
		this.totalPrice = 0;
		itemList = new ArrayList<Item>();
	} 
	
	/**
	 * Methods:
	 */
	
	/**
	 * TBD, need to add item methods (add, remove...)
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


	public Date getSupplyDateTime() {
		return supplyDateTime;
	}


	public void setSupplyDateTime(Date supplyDateTime) {
		this.supplyDateTime = supplyDateTime;
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
	
}
