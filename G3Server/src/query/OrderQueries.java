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
import util.Constans;
import util.DateTimeHandler;
import util.OrderForView;

/**
 * Class description: 
 * This is a class for 
 * order queries which is 
 * used for getting information 
 * related to the DB in the order 
 * process.
 */

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * @author Alexander, Martinov
 * @author Mousa, Srour.
 * 
 * @version 2/1/2022
 */
public class OrderQueries{
	
	/**
	 * This is a function for getting all 
	 * the required data about the restaurants 
	 * we need for the customer in the order 
	 * process.
	 * 
	 * @param messageFromClient
	 * @return answer message to the client side
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
	 * @return Item object
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
	 * @return answer message to the client side
	 * @throws SQLException
	 */
	public static Message addOrderToDbAndUpdateSupplier(Message messageFromClient) throws SQLException{
		ArrayList<Object> list = (ArrayList<Object>) messageFromClient.getObject();
		Order orderIntoDb = (Order)list.get(2);
		double balanceUsed = Double.parseDouble((String) list.get(0));
		double budgetBalanceUsed = Double.parseDouble((String) list.get(1));
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
				+ "AND (branch= '"+branch+"') AND (estimatedSupplyDateTime= '"+DateTimeHandler.convertMySqlDateTimeFormatToString(estimatedSupplyDateTime)+"') " ); 
		while(result.next()) {
			orderIntoDb.setOrderNumber(Integer.valueOf(result.getString(1)));
			}
		
		/*Update DB balance and budget balance*/
		Query.updateOneColumnForTableInDbByPrimaryKey("order", "balanceUsed='"+balanceUsed+"'" , "orderNumber='"+orderIntoDb.getOrderNumber()+"'"); 
		Query.updateOneColumnForTableInDbByPrimaryKey("order", "budgetBalanceUsed='"+budgetBalanceUsed+"'" , "orderNumber='"+orderIntoDb.getOrderNumber()+"'"); 
		
		Message messageToClient = new Message(Task.ORDER_FINISHED, Answer.ORDER_SUCCEEDED_WRITING_INTO_DB, orderIntoDb);
		return messageToClient;
	}
	
	/**
	 * This is a query for update the menu in DB.
	 * 
	 * @param messageFromClient
	 * @return answer message to the client side
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
	 * @return answer message to the client side
	 * @throws SQLException
	 */
	public static Message updateOrdersStatusOnDb(Message messageFromClient) throws SQLException{
		ArrayList<Order> orders = new ArrayList<>();
		orders = new ArrayList<Order>((Collection<? extends Order>)messageFromClient.getObject());//copy the array that we got from the controller
		String orderStatus = null;
		
		for (Order order : orders) {
			switch(order.getStatus()) {
			case PENDING_APPROVAL:
				/**
				 * Update the order status to Pending Approval
				 */
				orderStatus="PENDING_APPROVAL";
				break;
				
			case APPROVED:
				/**
				 * Update the order status to Approved
				 */
				orderStatus="APPROVED";
				break;
				
			case UN_APPROVED:
				/**
				 * Update the order status to Un Approved
				 */
				orderStatus="UN_APPROVED";
				break;	
			default:
				break;
			}
			Query.updateOneColumnForTableInDbByPrimaryKey("order", "status='"+orderStatus+"'" , "supplierId='"+order.getSupplierUserId()+"' AND ( orderNumber='"+order.getOrderNumber()+"')"); // update the status column in DB according to supplyId
			if(orderStatus.equals("APPROVED")) {
				Query.updateOneColumnForTableInDbByPrimaryKey("order", "issueDateTime='"+DateTimeHandler.convertMySqlDateTimeFormatToString(order.getIssueDateTime())+"'" , "supplierId='"+order.getSupplierUserId()+"' AND ( orderNumber='"+order.getOrderNumber()+"')"); 
			}
		}
		
		Message messageToClient = new Message(Task.MANAGE_ORDER_FINISHED, Answer.MANAGE_ORDER_SUCCEEDED_WRITING_INTO_DB, null);
        return messageToClient;
	}
	
	/**
	 * This function updates in the DB the 
	 * budget balance of the user
	 * 
	 * @param messageFromClient
	 * @return answer message to the client side
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
				orderFromDb.setCustomerUserType(rs.getString(4));
				//
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
	 * 
	 * @param message
	 * @return answer message to the client side
	 */
	public static Message getOrdersForUser(Message message) {
		String customerId = (String)message.getObject();
		ArrayList<OrderForView> orderList = new ArrayList<>();
		String resturantName="", orderDate="", orderTime="",orderDetails="",orderStatus="";
		int orderNum;
		ResultSet rs = Query.getRowsFromTableInDB("order", "customerUserId='"+customerId+"' AND ( status= 'PENDING_APPROVAL' OR (status = 'APPROVED'))");
		try {
			while(rs.next()) {
				// get the order number.
				orderNum = rs.getInt(1);
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
					/**
					 * The supplier status is north
					 */
					resturantName= resturantName + "-North";
					break;
				case "CENTER":
					/**
					 * The supplier status is center
					 */
					resturantName= resturantName + "-Center";
					break;
				case "SOUTH":
					/**
					 * The supplier status is south
					 */
					resturantName= resturantName + "-South";
					break;
				default:
					break;
				}
				// get the date with the wanted format without the time
				Date date = DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(9));
				ZoneId defaultZoneId = ZoneId.systemDefault();
				Instant instant = date.toInstant();
				LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
				orderDate = localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
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
					orderStatus = "Approved";
				else
					orderStatus = "Pending Approval";
				// add to the list
				orderList.add(new OrderForView(resturantName, orderDate, orderTime, orderDetails, orderStatus,orderNum));	
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
	 * 
	 * @param message
	 * @return answer message to the client side
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
	
	/**
	 * In this method we look for the customer email
	 * according to the userId and customer type.
	 * 
	 * @param message
	 * @return answer message to the client side
	 */
	public static Message getCustomerEmailForSimulation(Message message) {
		ArrayList<String> customerDetails = (ArrayList<String>)message.getObject();
		String userID = customerDetails.get(0);
		String customerType = customerDetails.get(1);
		String supplierId = customerDetails.get(2);
		ArrayList<String> customerMailDetails = new ArrayList<>();
		ResultSet rs = Query.getRowsFromTableInDB(customerType,"userID='"+userID+"'");
		try {
			if(rs.next()) {
				customerMailDetails.add(rs.getString(8)) ; //email 0
				customerMailDetails.add(rs.getString(3)); // first name 1
				customerMailDetails.add(rs.getString(4)); // last name 2;
			}
			rs.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs2 = Query.getRowsFromTableInDB("supplier", "supplierId='"+supplierId +"'");
		try {
			if(rs2.next()) {
				String restaurantWithBranch =  rs2.getString(2) ;
				switch(rs2.getString(3)) {
				case"NORTH":
					restaurantWithBranch = restaurantWithBranch + " (North Branch)";
					break;
				case "CENTER":
					restaurantWithBranch = restaurantWithBranch + " (Center Branch)";
					break;
				case "SOUTH":
					restaurantWithBranch = restaurantWithBranch + " (South Branch)";
					break;
				default:
					break;
				}
				customerMailDetails.add(restaurantWithBranch);  // 3 restaurant info.
			}
			rs2.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!customerMailDetails.isEmpty())
			return new Message(Task.GET_USER_EMAIL,Answer.SUCCEED,customerMailDetails);
		return null;
	}
	
	/**
	 * Set the actual supply date and the order status.
	 * Check if the user will get a balance and update it
	 * into balance table accordingly.
	 * 
	 * @param message
	 * @return answer message to the client side
	 */
	public static Message updateActualTimeAndCheckBalance(Message message) {
		ArrayList<Object> orderDetails = (ArrayList<Object>)message.getObject();
		Date actualDate = (Date)orderDetails.get(0);
		int orderNum = Integer.parseInt((String)orderDetails.get(1));
		SupplyType supplyType=null;
		OrderTimeType timeType=null;
		Date issuedDate=null,estimatedDate=null;
		String userId=null,supplierId=null;
		double totalPrice=0;
		double balance=0;
		//update the actual time and the order status to completed
		Query.updateOneColumnForTableInDbByPrimaryKey("order", "actualSupplyDateTime='"+DateTimeHandler.convertMySqlDateTimeFormatToString(actualDate)+"'", "orderNumber='"+orderNum+"'");
		Query.updateOneColumnForTableInDbByPrimaryKey("order", "status='COMPLETED'", "orderNumber='"+orderNum+"'");
		//get the order to check balance
		ResultSet rs = Query.getRowsFromTableInDB("order","orderNumber='"+orderNum+"'");
		try {
			if(rs.next()) {
				supplierId=rs.getString(2);
				userId = rs.getString(3);
				timeType = OrderTimeType.valueOf(rs.getString(6)); 
				issuedDate = DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(8));
				estimatedDate = DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(9));
				supplyType = SupplyType.valueOf(rs.getString(11));
				totalPrice = rs.getDouble(12);
				
			}
			rs.close();
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(supplyType == SupplyType.TAKE_AWAY) {
			return new Message(Task.SET_ACTUAL_DATE_AND_BALANCE,Answer.SUCCEED,null);
		}
		else {
			long diff = actualDate.getTime() - issuedDate.getTime();
			if(timeType == OrderTimeType.REGULAR) {
				if(diff> Constans.ONE_HOUR_IN_MILLESECONDS) {
					balance = totalPrice*Constans.FIFTY_PERCENT;
					ResultSet rs2=Query.getRowsFromTableInDB("balance", "supplierId='"+supplierId+"' AND ( customerUserId='" + userId+"')");
					try {
						if(!rs2.isBeforeFirst()) {
							//insert a new row with the userId,supplierId and the calculated balance.
							Query.insertOneRowIntoBalanceTable(userId, supplierId, balance);
						}
						else {
							//Calculate the new balance and update in the balance table.
							if(rs2.next())
							balance = balance +rs2.getDouble(3);
							Query.updateOneColumnForTableInDbByPrimaryKey("balance", "balance='"+balance+"'",  "supplierId='"+supplierId+"' AND ( customerUserId='" + userId+"')");
						}
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
			/// it is PRE order
			else {
				if(diff> Constans.TWENTY_MINUTES_IN_MILLESECONDS) {
					balance = totalPrice*Constans.FIFTY_PERCENT;
					ResultSet rs2 =Query.getRowsFromTableInDB("balance", "supplierId='"+supplierId+"' AND ( customerUserId='" + userId+"')");
					try {
						if(!rs2.isBeforeFirst()) {
							//insert a new row with the userId,supplierId and the calculated balance.
							Query.insertOneRowIntoBalanceTable(userId, supplierId, balance);
						}
						else {
							//Calculate the new balance and update in the balance table.
							if(rs2.next())
							balance+=rs2.getDouble(3);
							Query.updateOneColumnForTableInDbByPrimaryKey("balance", "balance='"+balance+"'",  "supplierId='"+supplierId+"' AND ( customerUserId='" + userId+"')");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return new Message(Task.SET_ACTUAL_DATE_AND_BALANCE,Answer.SUCCEED,null);
		
	}
	
	/**
	 * This function is for going back to the money that the user had before
	 * the order was done
	 * 
	 * @param message
	 * @throws SQLException
	 */
	public static void reverseBudgetBalanceAndBalanceForUserInUnApprovedOrder(Message message) throws SQLException{
		ArrayList<String> list = (ArrayList<String>) message.getObject();
		int orderNumber = Integer.valueOf(list.get(1));
		String customerId = list.get(0);
		String supplierId = list.get(2);
		double balanceUsed = 0.0;
		double budgetBalanceUsed = 0.0;
		
		/*get the order row from order table*/
		ResultSet rs = Query.getRowsFromTableInDB("order", "orderNumber='"+orderNumber+"'");
		if(rs.next()) {
			balanceUsed = rs.getDouble(21);
			budgetBalanceUsed = rs.getDouble(22);
		}
		rs.close();
		/*Update business customer backwards and balance table as well*/
		double companyBudgetUsedBeforeCancelingOrder = 0.0;
		ResultSet rs2 = Query.getRowsFromTableInDB("businesscustomer", "userID='"+customerId+"'");
		if(!rs2.isBeforeFirst()) {
			//not business customer
		}else {
			if(rs2.next()) {
				//if there is a businesscustomer
				companyBudgetUsedBeforeCancelingOrder = rs2.getDouble(17);
				companyBudgetUsedBeforeCancelingOrder = companyBudgetUsedBeforeCancelingOrder - budgetBalanceUsed; //reverse to previous stage
				//update businesscustomer table
				Query.updateOneColumnForTableInDbByPrimaryKey("businesscustomer", "budgetUsed='"+companyBudgetUsedBeforeCancelingOrder+"'",  "userID='"+customerId+"'");
			}
			rs2.close();
		}
		
		//update balance table
		double balanceBudgetUsedBeforeCancelingOrder = 0.0;
		ResultSet rs3 = Query.getRowsFromTableInDB("balance", "supplierId='"+supplierId+"' AND ( customerUserId='" + customerId+"')");
		if(!rs3.isBeforeFirst()) {
			//create row in DB
			Query.insertOneRowIntoBalanceTable(customerId, supplierId, balanceUsed);
		}else {
			if(rs3.next()) {
				//if there is a row in the balance table
				balanceBudgetUsedBeforeCancelingOrder = rs3.getDouble(3);
				balanceBudgetUsedBeforeCancelingOrder = balanceBudgetUsedBeforeCancelingOrder + balanceUsed; //reverse to previous stage
				//update businesscustomer table
				Query.updateOneColumnForTableInDbByPrimaryKey("balance", "balance='"+balanceBudgetUsedBeforeCancelingOrder+"'",  "supplierId='"+supplierId+"' AND ( customerUserId='" + customerId+"')");
			}
			rs3.close();
		}
	}
	
	/**
	 * In this method we get the MULTI
	 * orders and with pending approval status and with
	 * the same supplier id that the customer is
	 * looking for.
	 *  so the other business customers can join 
	 *  them into the delivery.
	 *  
	 * @param message
	 * @return a message contains the list of orders
	 */
	public static Message getAvailableMultiOrders(Message message) {
		ArrayList<String> listFromClient = (ArrayList<String>)message.getObject();
		
		String supplierId = listFromClient.get(0);
		String customerId = listFromClient.get(1);
		ArrayList<Order> ordersWithMulti = new ArrayList<>();
		String customerName ="";
		ResultSet rs = Query.getRowsFromTableInDB("order", "supplierId='"+supplierId+"' AND (status='PENDING_APPROVAL' AND ( deliveryType='MULTI' AND ("
				+ "customerUserId != '"+customerId+"')))");
		try {
			while(rs.next()) {
				ResultSet rs2 = Query.getRowsFromTableInDB("businesscustomer", "userID='"+rs.getString(3)+"'");
				if(rs2.next()) {
					customerName = rs2.getString(3);
				}
				rs2.close();
				ordersWithMulti.add(new Order (rs.getInt(1),rs.getString(3),customerName,rs.getString(15)));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Message (Task.GET_BUSINESS_CUSTOMERS_WITH_MULTI_ORDER,Answer.SUCCEED,ordersWithMulti);
	}
	
	/**
	 * This method gets a finished order by a customer
	 * that has been ordered by the Join multiple participants option,
	 * we get all the delivery details from the customer who he wants to join
	 * for the multiple delivery option , we insert a new row for the new participant
	 * with his items but without any amount of cost, we add the cost of his order
	 * to the owner of the multiple-order.
	 * 
	 * @param message
	 */
	public static void joinMultiFinishedOrder(Message message) {
		ArrayList<Object> listFromClient = (ArrayList<Object>)message.getObject();
		Order order = (Order)listFromClient.get(0);
		int ownerOrderNumber = Integer.parseInt((String)listFromClient.get(1));
		Order multiOrder=null;
		ResultSet rs = Query.getRowsFromTableInDB("order", "orderNumber='"+ownerOrderNumber+"'");
		try {
			if(rs.next()) {
				// insert into a order table a new row wihtout payment ( all 0 )
				double totalPrice=0.0;
				String itemList = order.turnItemListIntoStringForDB();
				String commentsList = order.turnCommentsIntoStringForDB();
				Query.insertOneRowIntoOrderTable(order.getOrderNumber(),order.getSupplierUserId(),order.getCustomerUserId(),order.getCustomerUserType()
						,order.getBranch(),OrderTimeType.valueOf(rs.getString(6)),
						OrderStatus.COMPLETED,DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(9)),
						DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(9)),
						DateTimeHandler.buildMySqlDateTimeFormatFromDateTimeString(rs.getString(9)),SupplyType.DELIVERY,totalPrice,
						rs.getString(13),rs.getString(14),rs.getString(15),rs.getString(16),0.0,itemList,commentsList,DeliveryType.JOIN_MULTI);
				// update the row of the multiple participants owner
				int participants = rs.getInt(23);
				totalPrice=order.getTotalPrice()+25; // delivery fee(25) by default
				double ownerTotalPrice = rs.getDouble(12);
				String ownerItems = rs.getString(18);
				String ownerComments = rs.getString(19);
				switch(participants) {
				case 1:
					ownerTotalPrice = ownerTotalPrice+totalPrice-10; //2 participants 
					participants+=1;
					ownerItems+=itemList;
					ownerComments+=commentsList;
					
					break;
				case 2:
					ownerTotalPrice = ownerTotalPrice+totalPrice-20; // 3 participants
					participants+=1;
					ownerItems+=itemList;
					ownerComments+=commentsList;
					break;
				default:
					ownerTotalPrice = ownerTotalPrice+totalPrice-10;
					ownerItems+=itemList;
					ownerComments+=commentsList;
					participants+=1;
					break;
					
				}
				Query.updateOneColumnForTableInDbByPrimaryKey("order", "totalPrice='"+ownerTotalPrice+"', itemsList='"+ownerItems+"', comments='"+
						ownerComments+"', participantsAmount='"+participants+"'", "orderNumber='"+ownerOrderNumber+"'");
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method gets the multi particiapnts order, number of participants.
	 * 
	 * @param message
	 * @return a message contains an Integer which is the number of particiapnts.
	 */
	public static Message joinMultiGetNumberOfParticipants(Message message) {
		ArrayList<Object> listFromClient = (ArrayList<Object>)message.getObject();
		int ownerOrderNumber = Integer.parseInt((String)listFromClient.get(0));
		Integer multiPartiNumber = 1;
		ResultSet rs = Query.getRowsFromTableInDB("order", "orderNumber='"+ownerOrderNumber+"'");
		try {
			if(rs.next()) {
				// get the row of the multiple participants owner
				multiPartiNumber = rs.getInt(23);

			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Message (Task.JOIN_MULTI_GET_NUMBER_OF_PARTICIPANTS, Answer.SUCCEED, multiPartiNumber);
	}
}
