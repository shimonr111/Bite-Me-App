package query;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import users.ConfirmationStatus;
import users.Customer;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for 
 * order queries which is 
 * used for getting information 
 * related to the DB in the order 
 * process.
 * 
 * @version 15/12/2021
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
				supplierList.put(rs.getString(1),rs.getString(2));//add to the hashMap all the data we need
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set the message back to the customer
		returnMessageToClient.setObject(supplierList);
		returnMessageToClient.setAnswer(Answer.RESTAURANTS_EXIST_FOR_THIS_BRANCH);
		return returnMessageToClient;
	}
	
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
	public static Message addOrderToDbAndUpdateCustomer(Message messageFromClient) throws SQLException{
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
		Message messageToClient = new Message(Task.ORDER_FINISHED, Answer.ORDER_SUCCEEDED_WRITING_INTO_DB, null);
		return messageToClient;
		
		
	}
	
	/**
	 * This is a query for adding a new order to the DB.
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message updateMenuOnDb(Message messageFromClient) throws SQLException{
		ArrayList<Item> items = new ArrayList<>();
		items = new ArrayList<Item>((Collection<? extends Item>)messageFromClient.getObject());
		
		for(int i=0; i<items.size(); i++) {
			ItemCategory category = items.get(i).getCategory();
			String name = items.get(i).getItemName();
			String picture = items.get(i).getPicturePath();
			ItemSize size = items.get(i).getSize();
			double price = items.get(i).getPrice();
			
			//System.out.println(category +" "+ name +" "+ picture+" "+  size +" "+ price+" "+ items.get(i).getSupplierUserId());
			
			
			/*Send to to query for setting value in the whole row of order Table*/
			Query.insertOneRowIntoMenuTable(name, items.get(i).getSupplierUserId(), category, size, picture, price);
		}
		Message messageToClient = new Message(Task.MANAGE_MENU_FINISHED, Answer.MANAGE_MENU_SUCCEEDED_WRITING_INTO_DB, null);
        return messageToClient;
	}
	
	
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
				Order orderFromDb = convertToOrder(rs);
				if(orderFromDb != null) {
					orderList.add(orderFromDb);
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
	 * This is a function used for converting
	 * Sql Object into Order Object
	 * 
	 * @param rs
	 * @return
	 */
	private static Order convertToOrder(ResultSet rs) {
		try {
			int orderNumber = Integer.parseInt(rs.getString(1));
			String supplierId = rs.getString(2);
			String customerUserId = rs.getString(3);
			String customerUserType = rs.getString(4); //Currently not in use
			Branch branch = Branch.valueOf(rs.getString(5));
			OrderTimeType timeType = OrderTimeType.valueOf(rs.getString(6));
			OrderStatus status = OrderStatus.valueOf(rs.getString(7));
			Date issueDateTime = rs.getDate(8);
			Date estimatedSupplyDateTime =  rs.getDate(9);
			Date actualSupplyDateTime =  rs.getDate(10);
			SupplyType supplyType = SupplyType.valueOf(rs.getString(11));
			double totalPrice = rs.getDouble(12);
			String receiverFirstName = rs.getString(13);
			String receiverLastName = rs.getString(14);
			String receiverAddress = rs.getString(15);
			String receiverPhoneNumber = rs.getString(16);
			double deliveryFee =  rs.getDouble(17); //Currently not in use
			String itemList = rs.getString(18); //Currently not in use
			String comments = rs.getString(19); //Currently not in use
			DeliveryType deliveryType = DeliveryType.valueOf(rs.getString(20));
			
			/**
			 * public Order(
			*,  
			 int supplyId, double totalPrice, ArrayList<Item> itemList) {
			 * 
			 */
			Order returnedOrder =  new Order(orderNumber,supplierId,customerUserId,branch,timeType,status,issueDateTime,estimatedSupplyDateTime,actualSupplyDateTime,
					supplyType,0,totalPrice,null);
			if(supplyType == SupplyType.DELIVERY) {
				returnedOrder.setSupplyMethodInformation(new DeliverySupplyMethod(0, orderNumber, receiverFirstName, receiverLastName, receiverPhoneNumber, deliveryType, receiverAddress));
			}
			else {
				returnedOrder.setSupplyMethodInformation(new TakeAwaySupplyMethod(0, orderNumber, receiverFirstName, receiverLastName, receiverPhoneNumber));
			}
			return returnedOrder;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
