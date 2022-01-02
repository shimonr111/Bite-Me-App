package orders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import users.Branch;
import util.DateTimeHandler;

/**
 * 
 * @author Ori, Malka.
 * @author Lior, Guzovsky.
 * @author Shimon, Rubin.
 * 
 * Class description:
 * This class describes the Order Entity.
 *  
 * @version 20/12/2021
 */
public class Order implements Serializable{
	
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
	 * The order actual receive time - MySql DateTime format.
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
	 * This is the information given for the supply method,
	 * such as the name of the , his phone and in case of 
	 * delivery an address. 
	 */
	public AbatractSupplyMethod supplyMethodInformation;
	
	/**
	 * This is an attribute that
	 * tells us the users type:
	 * customer, businesscustomer or hrmanager
	 */
	public String customerUserType;
	
	/**
	 * used for multiple participants delivery.
	 */
	public String customerName;
	

	/**
	 * used for multiple participants delivery.
	 */
	public String deliveryAddress;
	
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
		this.supplyMethodInformation = null;
		this.customerUserType =null;
	}

	/**
	 * @param supplierUserId
	 * @param customerUserId
	 * @param branch
	 */
	public Order(String supplierUserId, String customerUserId, Branch branch) {
		super();
		this.orderNumber = 0; //order unique number is created in the DB 
		this.supplierUserId = supplierUserId;
		this.customerUserId = customerUserId;
		this.branch = branch;
		this.timeType = OrderTimeType.REGULAR; //default, need to update when getting the supply time.
		this.status = OrderStatus.PENDING_APPROVAL; //default when creating new order.
		this.issueDateTime = DateTimeHandler.buildMySqlDateTimeFormatFromTextFields("1970/01/01", "00:00"); //set default - update after the restaurant approved the order
		
		this.estimatedSupplyDateTime = null;
		this.actualSupplyDateTime = DateTimeHandler.buildMySqlDateTimeFormatFromTextFields("1970/01/01", "00:00"); //set default
		this.supplyType = null;
		this.supplyId = 0;
		this.totalPrice = 0;
		itemList = new ArrayList<Item>();
		this.supplyMethodInformation = null;
		this.customerUserType = null;
		
	} 
	
	public Order(int orderNumber, String supplierUserId, String customerName, String deliveryAddress) {
		this.orderNumber = orderNumber;
		this.supplierUserId = supplierUserId;
		this.customerName = customerName;
		this.deliveryAddress = deliveryAddress;
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
	
	public AbatractSupplyMethod getSupplyMethodInformation() {
		return supplyMethodInformation;
	}
	
	public void setSupplyMethodInformation(AbatractSupplyMethod supplyMethodInformation) {
		this.supplyMethodInformation = supplyMethodInformation;
	}
	
	public String getCustomerUserType() {
		return customerUserType;
	}

	public void setCustomerUserType(String customerUserType) {
		this.customerUserType = customerUserType;
	}

	public String getReceiverPhoneNumber() {
		return supplyMethodInformation.getReceiverPhoneNumber();
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	/**
	 * Help method for returning the items as Sting rather than
	 * ArrayList in Specific way (thats why we didn't use toString();
	 * 
	 * @return String of items
	 */
	public String turnItemListIntoStringForDB() {
		String res = null;
		int itemLen = itemList.size();
		for(Item item : itemList) {
			if(itemLen == itemList.size())//first item in list
			{
				res = item.getItemName()+ ",";
				itemLen--;
			}
			else if(itemLen != 0) {
			res = res + item.getItemName() + ",";
			itemLen--;
			}
			else {
				res = res + item.getItemName();
			}
		}
		return res;
	}
	
	/**
	 * Help method for returning comments 
	 * as a string into the DB.
	 * 
	 * @return String of comments
	 */
	public String turnCommentsIntoStringForDB() {
		String res = null;
		int itemLen = itemList.size();
		for(Item item : itemList) {
			if(itemLen == itemList.size())//first item in list
			{
				res = item.getComment() + ",";
				itemLen--;
			}
			else if(itemLen != 0) {
			res = res + item.getComment() + ",";
			itemLen--;
			}
			else {
				res = res + item.getComment();
				itemLen--;
			}
		}
		return res;
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
