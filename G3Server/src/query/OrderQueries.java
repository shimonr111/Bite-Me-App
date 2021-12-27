package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import communication.Answer;
import communication.Message;
import communication.Task;
import orders.DeliverySupplyMethod;
import orders.DeliveryType;
import orders.Item;
import orders.ItemCategory;
import orders.ItemSize;
import orders.Order;
import orders.OrderStatus;
import orders.OrderTimeType;
import orders.SupplyType;
import orders.TakeAwaySupplyMethod;
import users.Branch;
import users.BusinessCustomer;
import users.ConfirmationStatus;
import users.Customer;
import util.DateTimeHandler;
import util.OrderForView;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * @author Alexander, Martinov
 * @author Mousa, Srour.
 * Class description: 
 * This is a class for 
 * order queries which is 
 * used for getting information 
 * related to the DB in the order 
 * process.
 * 
 * @version 22/12/2021
 */
public class OrderQueries {
	
	/**
	 * This is a function for getting all 
	 * the required data about the restaurants 
	 * we need for the customer in the order 
	 * process.
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message createRestaurantsForCustomer(Message messageFromClient) throws SQLException{
		Message returnMessageToClient = messageFromClient;
		Customer customer = (Customer) messageFromClient.getObject();
		Branch branchNameForRelevantRestaurants = customer.getHomeBranch();
		ConfirmationStatus status = ConfirmationStatus.CONFIRMED;
		Map<String,String> supplierList = new HashMap<>();
		
		ResultSet rs = Query.getRowsFromTableInDB("supplier","homeBranch='"+branchNameForRelevantRestaurants.toString()+"'");
		try {
			//If the row doesn't exist in login Table
			if(!rs.isBeforeFirst()) {
				returnMessageToClient.setAnswer(Answer.RESTAURANTS_NOT_EXIST_IN_THIS_BRANCH);
				returnMessageToClient.setObject(null);
				return returnMessageToClient;
			}
			while(rs.next()) {
				/*Check if the supplier is confirmed if not than don't add it*/
				if(ConfirmationStatus.valueOf(rs.getString(7)) == ConfirmationStatus.CONFIRMED) {
				supplierList.put(rs.getString(1),rs.getString(2));//add to the hashMap all the data we need
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*In case the list is empty needed to be null for the message update to the UI*/
		if(supplierList.size()==0) {
			supplierList=null;
		}
		//set the message back to the customer
		returnMessageToClient.setObject(supplierList);
		returnMessageToClient.setAnswer(Answer.RESTAURANTS_EXIST_FOR_THIS_BRANCH);
		return returnMessageToClient;
	}
	
	/**
	 * This is a function for creating menu for 
	 * specific supplier, used in the order process.
	 * 
	 * @param messageFromClient
	 * @return Message containing answer for the creation of the menu for the Order process.
	 * @throws SQLException
	 */
	public static Message createMenuForSpecificSupplier(Message messageFromClient) throws SQLException{
		Message returnMessageToClient = messageFromClient;
		String supplierId = (String) messageFromClient.getObject();	
		List<Item> itemList = new ArrayList<Item>();
		//check first if the supplier is confirmed in the system
		
		
		//create query for getting all the items of the specific supplier for the users menu		
		ResultSet rs = Query.getRowsFromTableInDB("item_in_menu","supplierId='"+supplierId+"'");
		try {
			//If the row doesn't exist in login Table
			if(!rs.isBeforeFirst()) {
				returnMessageToClient.setAnswer(Answer.SUPPLIER_HAS_NO_MENU);
				returnMessageToClient.setObject(null);
				return returnMessageToClient;
			}
			while(rs.next()) {
				Item itemFromDB = convertToItem(rs);
				if(itemFromDB != null) {
					itemList.add(itemFromDB);
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMessageToClient.setObject(itemList);
		returnMessageToClient.setAnswer(Answer.SUPPLIER_MENU_FOUND);
		return returnMessageToClient;
	}
	
	/**
	 * This is a function
	 * for creating the Item from the DB 
	 * that helps us to create arrayList as
	 * we want to return to the customer.
	 * 
	 * @param rs
	 * @return
	 */
	private static Item convertToItem(ResultSet rs) {
		try {
			String itemName = rs.getString(1);
			String supplierId = null;
			ItemCategory itemCategory = ItemCategory.valueOf(rs.getString(3));
			ItemSize itemSize = ItemSize.valueOf(rs.getString(4));
			String picturePath = rs.getString(5);
			double price =  Double.parseDouble(rs.getString(6));
			return new Item(supplierId,itemName,itemCategory,itemSize,picturePath,price);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This is a query for adding a new order to the DB.
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message addOrderToDbAndUpdateSupplier(Message messageFromClient) throws SQLException{
		Order orderIntoDb = (Order) messageFromClient.getObject();
		
		int orderNumber = orderIntoDb.getOrderNumber();
		String supplierId = orderIntoDb.getSupplierUserId();
		String customerUserId = orderIntoDb.getCustomerUserId();
		String customerUserType = orderIntoDb.getCustomerUserType();
		Branch branch = orderIntoDb.getBranch();
		OrderTimeType timeType = orderIntoDb.getTimeType();
		OrderStatus status = orderIntoDb.getStatus();
		Date issueDateTime = orderIntoDb.getIssueDateTime();
		Date estimatedSupplyDateTime = orderIntoDb.getEstimatedSupplyDateTime();
		Date actualSupplyDateTime = orderIntoDb.getActualSupplyDateTime();
		SupplyType supplyType = orderIntoDb.getSupplyType();
		double totalPrice = orderIntoDb.getTotalPrice();
		String receiverFirstName = orderIntoDb.getSupplyMethodInformation().getReceiverFirstName();
		String receiverLastName = orderIntoDb.getSupplyMethodInformation().getReceiverLastName();
		String receiverAddress = "";
		String receiverPhoneNumber = orderIntoDb.getSupplyMethodInformation().getReceiverPhoneNumber();
		double deliveryFee = 0;
		String itemList = orderIntoDb.turnItemListIntoStringForDB();
		String comments = orderIntoDb.turnCommentsIntoStringForDB();
		DeliveryType deliveryType = DeliveryType.NA;//only for TA
		if(supplyType == SupplyType.DELIVERY){
			receiverAddress = ((DeliverySupplyMethod)orderIntoDb.getSupplyMethodInformation()).getReciverAddress();
			deliveryFee = ((DeliverySupplyMethod)orderIntoDb.getSupplyMethodInformation()).getDeliveryFee();
			deliveryType = ((DeliverySupplyMethod)orderIntoDb.getSupplyMethodInformation()).getDeliveryType();
		}
		/*Send to to query for setting value in the whole row of order Table*/
		Query.insertOneRowIntoOrderTable(orderNumber, supplierId, customerUserId, customerUserType, branch,
				timeType, status, issueDateTime, estimatedSupplyDateTime, actualSupplyDateTime, supplyType,
				totalPrice, receiverFirstName, receiverLastName, receiverAddress, receiverPhoneNumber,
				deliveryFee, itemList, comments,deliveryType);
		
		/*get the order number as written in the DB*/
		ResultSet result = Query.getRowsFromTableInDB("order","supplierId= '"+supplierId+"' AND (customerUserId= '"+customerUserId+"')"
				+ "AND (branch= '"+branch+"') AND (issueDateTime= '"+DateTimeHandler.convertMySqlDateTimeFormatToString(estimatedSupplyDateTime)+"') " ); 
		while(result.next()) {
			orderIntoDb.setOrderNumber(Integer.valueOf(result.getString(1)));
			}
		Message messageToClient = new Message(Task.ORDER_FINISHED, Answer.ORDER_SUCCEEDED_WRITING_INTO_DB, orderIntoDb);
		return messageToClient;
	}
	
	/**
	 * This is a query for update the menu in DB.
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message updateMenuOnDb(Message messageFromClient) throws SQLException{
		ArrayList<Item> items = new ArrayList<>();
		items = new ArrayList<Item>((Collection<? extends Item>)messageFromClient.getObject());//copy the array that we got from the controller
		String supplierId = items.get(0).getSupplierUserId();//get the supplier id
		
		//first delete rows with same supplier id , in order to prevent duplicates after we updated the DB
		Query.deleteDuplicateRowBeforeUpdateDb("item_in_menu", "supplierId='"+supplierId+"'");
		
		
		//add in loop all the rows that we got from the updateItems array in the controller
		for(int i=0; i<items.size(); i++) {
			ItemCategory category = items.get(i).getCategory();
			String name = items.get(i).getItemName();
			String picture = items.get(i).getPicturePath();
			ItemSize size = items.get(i).getSize();
			double price = items.get(i).getPrice();
			
			/*Send to to query for setting value in the whole row of order Table*/
			Query.insertOneRowIntoMenuTable(name, items.get(i).getSupplierUserId(), category, size, picture, price);
		}
		Message messageToClient = new Message(Task.MANAGE_MENU_FINISHED, Answer.MANAGE_MENU_SUCCEEDED_WRITING_INTO_DB, null);
        return messageToClient;
	}
	
	/**
	 * This is a query for update the orders status in DB.
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message updateOrdersStatusOnDb(Message messageFromClient) throws SQLException{
		ArrayList<Order> orders = new ArrayList<>();
		orders = new ArrayList<Order>((Collection<? extends Order>)messageFromClient.getObject());//copy the array that we got from the controller
		String orderStatus=null;
		
		for (Order order : orders) {
			switch(order.getStatus()) {
			case PENDING_APPROVAL:
				orderStatus="PENDING_APPROVAL";
				break;
				
			case APPROVED:
				orderStatus="APPROVED";
				break;
				
			case UN_APPROVED:
				orderStatus="UN_APPROVED";
				break;
				
				default:
					break;
			}
			
			Query.updateOneColumnForTableInDbByPrimaryKey("order", "status='"+orderStatus+"'" , "supplierId='"+order.getSupplierUserId()+"' AND ( orderNumber='"+order.getOrderNumber()+"')"); // update the status column in DB according to supplyId
			Query.updateOneColumnForTableInDbByPrimaryKey("order", "issueDateTime='"+DateTimeHandler.convertMySqlDateTimeFormatToString(order.getIssueDateTime())+"'" , "supplierId='"+order.getSupplierUserId()+"' AND ( orderNumber='"+order.getOrderNumber()+"')"); // 
		}
		
		
				
		Message messageToClient = new Message(Task.MANAGE_ORDER_FINISHED, Answer.MANAGE_ORDER_SUCCEEDED_WRITING_INTO_DB, null);
        return messageToClient;
	}
	
	/**
	 * This function updates in the DB the 
	 * budget balance of the user
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message updatePaymentBalanceAndBudgetBalance(Message messageFromClient) throws SQLException{
		ArrayList<Object> list = (ArrayList<Object>)messageFromClient.getObject();
		Customer customer = (Customer)list.get(0);
		String supplierId = (String)list.get(1);
		if(customer instanceof BusinessCustomer) 
			Query.updateOneColumnForTableInDbByPrimaryKey("businesscustomer","budgetUsed='"+((BusinessCustomer)customer).getBudgetUsed()+"'",
					"userID='"+((BusinessCustomer)customer).getUserId()+"'");
		if(customer.getBalance()==0) {
			//check if exist  -> delete
			//else do nothing
			ResultSet rs =Query.getRowsFromTableInDB("balance", "customerUserId='"+customer.getUserId()+"' AND ( supplierId ='" + supplierId +"')");
			try {
				if(rs.next()) {
					Query.deleteRowFromTableInDbByPrimaryKey("balance", "customerUserId='"+customer.getUserId()+"' AND ( supplierId ='" + supplierId +"')");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			Query.updateOneColumnForTableInDbByPrimaryKey("balance", "balance='"+customer.getBalance()+"'"
					, "customerUserId='"+customer.getUserId()+"' AND ( supplierId ='" + supplierId +"')");
		}
		Message messageToClient = new Message(Task.CUSTOMER_UPDATE_DB_AFTER_PAYMENT, Answer.SUCCEED, null);
        return messageToClient;
        
	}
	
	
	/**
	 * This is a function for getting 
	 * all the orders from the DB and 
	 * sending it to the supplier worker screen.
	 * 
	 * @param messageFromClient
	 * @return Message contains answer and object for the supplier Worker.
	 * @throws SQLException
	 */
	public static Message getOrdersFromOrderTableForSpecificRestaurant(Message messageFromClient) throws SQLException{
		Message returnMessageToClient = messageFromClient;
		String supplierId = (String) messageFromClient.getObject();	
		List<Order> orderList = new ArrayList<Order>();
		
		//create query for getting all the Orders of the specific supplier for the manage orders	
		ResultSet rs = Query.getRowsFromTableInDB("order","supplierId='"+supplierId+"'");
		try {
			//If there are no rows exist in Order Table for the specific supplier Id
			if(!rs.isBeforeFirst()) {
				returnMessageToClient.setAnswer(Answer.SUPPLIER_WORKER_NO_ORDERS_FOUND);
				returnMessageToClient.setObject(null);
				return returnMessageToClient;
			}
			while(rs.next()) {
				if(OrderStatus.valueOf(rs.getString(7)) == OrderStatus.PENDING_APPROVAL) { //if the order is approved go for the next one
				Order orderFromDb= null;
				orderFromDb = new Order(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3),
						Branch.valueOf(rs.getString(5)),OrderTimeType.valueOf(rs.getString(6)), OrderStatus.valueOf(rs.getString(7)),
						DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(8)), DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(9)),
						DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(10)),SupplyType.valueOf(rs.getString(11)),0,rs.getDouble(12),
						null);
				SupplyType supplyType = SupplyType.valueOf(rs.getString(11));
				if(supplyType == SupplyType.DELIVERY) {
					orderFromDb.setSupplyMethodInformation(new DeliverySupplyMethod(0, Integer.parseInt(rs.getString(1)), rs.getString(13), rs.getString(14), rs.getString(16), DeliveryType.valueOf(rs.getString(20)), rs.getString(15)));
				}
				else {
					orderFromDb.setSupplyMethodInformation(new TakeAwaySupplyMethod(0, Integer.parseInt(rs.getString(1)), rs.getString(13), rs.getString(14), rs.getString(16)));
				}

				if(orderFromDb != null) {
					orderList.add(orderFromDb);
				}
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMessageToClient.setObject(orderList);
		returnMessageToClient.setAnswer(Answer.SUPPLIER_WORKER_ORDERS_FOUND);
		return returnMessageToClient;
	}
	
	/**
	 * Get the orders of a specific user to set them into the table 
	 * according to the wanted format.
	 * @param message
	 * @return
	 */
	public static Message getOrdersForUser(Message message) {
		String customerId = (String)message.getObject();
		ArrayList<OrderForView> orderList = new ArrayList<>();
		String resturantName="", orderDate="", orderTime="",orderDetails="",orderStatus="";
		ResultSet rs = Query.getRowsFromTableInDB("order", "customerUserId='"+customerId+"' AND ( status= 'PENDING_APPROVAL' OR (status = 'APPROVED'))");
		try {
			while(rs.next()) {
				// get the supplier string .
				String supplierId= rs.getString(2);
				String supplierStatus = "";
				ResultSet rs2 = Query.getRowsFromTableInDB("supplier","supplierId='" + supplierId +"'");
				if(rs2.next()) {
					resturantName = rs2.getString(2);
					supplierStatus = rs2.getString(3);
				}
				rs2.close();
				switch(supplierStatus) {
				case "NORTH":
					resturantName= resturantName + ", North Branch";
					break;
				case "CENTER":
					resturantName= resturantName + ", Center Branch";
					break;
				case "SOUTH":
					resturantName= resturantName + ", South Branch";
					break;
				default:
					break;
				}
				// get the date with the wanted format without the time
				Date date = DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(9));
				ZoneId defaultZoneId = ZoneId.systemDefault();
				Instant instant = date.toInstant();
				LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
				orderDate = localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
				//get the time with the wanted format without date
				LocalTime localTime = instant.atZone(defaultZoneId).toLocalTime();
				orderTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
				// get the order items with comments
				orderDetails="";
				String items = rs.getString(18);
				String comments = rs.getString(19);
				String [] itemsParts = items.split(",");
				String [] commentsParts = comments.split(",");
				for(int i=0;i<itemsParts.length;i++) {
					orderDetails= orderDetails + (i+1) +") ";
					orderDetails = orderDetails + itemsParts[i];	
					if(!commentsParts[i].equals("null")) {
						orderDetails = orderDetails +" (" + commentsParts[i]+")\n";
					}
					else
						orderDetails+="\n";
					
				}
				// get the status
				String status = rs.getString(7);
				if(status.equals("APPROVED"))
					orderStatus = "Approved, In progress";
				else
					orderStatus = "Pending for resturant approval";
				// add to the list
				orderList.add(new OrderForView(resturantName, orderDate, orderTime, orderDetails, orderStatus));	
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Message(Task.DISPLAY_ORDERS_INTO_TABLE,Answer.SUCCEED,orderList);
	}
	
	/**
	 * In this method we get the userId and the supplierId , 
	 * we check if the user has already a balance in this restaurant :
	 * if he does, we return the amount of balance he has.
	 * else , we return 0.
	 * @param message
	 * @return
	 */
	public static Message getBalanceForUser(Message message) {
		Message returnMessageToClient= message;
		Order order = (Order)message.getObject();
		String userId = order.getCustomerUserId();
		String supplierId = order.getSupplierUserId();
		ResultSet rs =Query.getRowsFromTableInDB("balance", "customerUserId='"+userId+"' AND ( supplierId ='" + supplierId +"')");
		try {
			if(!rs.isBeforeFirst()) {
				returnMessageToClient.setObject(Double.toString(0));
			}
			else {
				if(rs.next())
					returnMessageToClient.setObject(Double.toString(rs.getDouble(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnMessageToClient;
	}
	
}
