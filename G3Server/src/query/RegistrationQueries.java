package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import communication.Answer;
import communication.Message;
import communication.Task;
import users.Branch;
import users.BranchManager;
import users.BudgetType;
import users.BusinessCustomer;
import users.Company;
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.Login;
import users.Supplier;
import users.SupplierWorker;
import users.User;
import users.UserForRegistration;

/**
 * Class description:
 * This class will contain all the queries to DB and logi
 * that relate to Registration phase.
 */

/**
 * 
 * @author Mousa, Srour.
 * @author Alexander, Martinov.
 * 
 * @version 15/12/2021
 */
public class RegistrationQueries{

	/**
	 * In this method we check if all the details are not exist before in db ( userID , username , creditcard)
	 * in case userID or username already exist we return a relevant message and we do not insert new rows into tables
	 * in case the credit card number already exist , we do create rows and inesrt them but we dont create a new row for the credit card.
	 * 
	 * @param message
	 * @return answer to the clients side
	 */
	public static Message getPrivateCustomerRegistration(Message message) {
		Message messageBackToClient;
		  @SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) message.getObject();
		Customer customer = (Customer) list.get(0);
		Login login = (Login) list.get(1);
		CreditCard creditCard = (CreditCard) list.get(2);
		if(checkIfUserIdExist(customer)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_ID_ALREADY_EXIST,null);
			return messageBackToClient;
		}
		if(checkIfLoginUserNameExist(login)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_NAME_ALREADY_EXIST,null);
			return messageBackToClient;
		}
		if(!checkIfCreditCardNumberExist(creditCard))
			Query.insertOneRowIntoCreditCardTable(creditCard);
		Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), customer.getUserId(), "customer");
		Query.insertOneRowIntoCustomerTable(customer);
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userID='"+customer.getUserId()+"'");
		messageBackToClient= new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.CUSTOMER_REGISTRATION_SUCCEED,null);
		return messageBackToClient;
		
	}
	
	/**
	 * In this method we check if all the details are not exist before in db ( userID , username , creditcard)
	 * in case userID or username already exist we return a relevant message and we do not insert new rows into tables
	 * in case the credit card number already exist , we do create rows and inesrt them but we dont create a new row for the credit card.
	 * 
	 * @param message
	 * @return answer to the clients side
	 */
	
	public static Message getBusinessCustomerRegistration(Message message) {
		Message messageBackToClient;
		  @SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) message.getObject();
		String businessCustomerType=null;
		businessCustomerType = "businesscustomer";
		Login login = (Login) list.get(0);
		CreditCard creditCard = (CreditCard) list.get(1);
		if(checkIfUserIdExist((User)list.get(2))) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_ID_ALREADY_EXIST_BUSINESS,null);
			return messageBackToClient;
		}
		if(checkIfLoginUserNameExist(login)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_NAME_ALREADY_EXIST_BUSINESS,null);
			return messageBackToClient;
		}
		if(!checkIfCreditCardNumberExist(creditCard))
			Query.insertOneRowIntoCreditCardTable(creditCard);
		Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), ((User)list.get(2)).getUserId(), businessCustomerType);
		Query.insertOneRowIntoBusinessCustomerOrHrManagerTable((BusinessCustomer)list.get(2), "businesscustomer");
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userID='"+((BusinessCustomer)list.get(2)).getUserId()+"'");
		
		
		messageBackToClient= new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.BUSINESS_CUSTOMER_REGISTRATION_SUCCEED,null);
		return messageBackToClient;
	}
	
	/**
	 * In this method we check if all the details are not exist before in db ( userID , username )
	 * in case userID or username already exist we return a relevant message and we do not insert new rows into tables
	 * we also check here if the supplier name already exist.
	 * 
	 * @param message
	 * @return answer to the clients side
	 */
	public static Message getSupplierRegistration(Message message) {
		Message messageBackToClient;
		  @SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) message.getObject();
		SupplierWorker supplierWorker = (SupplierWorker) list.get(0);
		Login login = (Login) list.get(1);
		if(checkIfUserIdExist(supplierWorker)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_ID_ALREADY_EXIST_SUPPLIER,null);
			return messageBackToClient;
		}
		if(checkIfLoginUserNameExist(login)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_NAME_ALREADY_EXIST_SUPPLIER,null);
			return messageBackToClient;
		}
		Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), supplierWorker.getUserId(), "supplierworker");
		Query.insertOneRowIntoSupplierWorkerTable(supplierWorker);
		Query.deleteRowFromTableInDbByPrimaryKey("registration", "userID='"+supplierWorker.getUserId()+"'");
		messageBackToClient= new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.SUPPLIER_REGISTRATION_SUCCEED,null);
		return messageBackToClient;
		
	}
	
	/**
	 * In this method we receive a message that contains a company object that we want to register
	 * and the HrManager that asked for the registration 
	 * In case the registration succeed we return a relevant answer and update the companyName 
	 * of the HrManager to the new company that he asked to register.
	 * 
	 * @param message
	 * @return answer to the clients side
	 */
	public static Message getCompanyRegistration(Message message) {
		Company company = (Company) message.getObject();
		Query.updateOneColumnForTableInDbByPrimaryKey("company", "companyStatusInSystem='PENDING_APPROVAL'","companyName ='" + company.getCompanyName()+"'");
		return new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.COMPANY_REGISTRATION_SUCCEED,null);
	}
	
	/**
	 * In this method we check if the company code already exist in DB.
	 * 
	 * @param companyCode
	 * @return true if company code exist
	 */
	public static boolean checkIfCompanyCodeExist(int companyCode) {
		ResultSet rs = Query.getColumnFromTableInDB("company", "companyCode");
		try {
			while(rs.next()) {
				if(Integer.parseInt(rs.getString(1))==companyCode) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method gets a string of company name and checks if the name already exist on DB .
	 * 
	 * @param companyName
	 * @return true if company name exist
	 */
	public static boolean checkIfCompanyNameExist(String companyName) {
		ResultSet rs = Query.getColumnFromTableInDB("company", "companyName");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(companyName)) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method returns a list of suppliers (by branch) for the supplier registration process
	 * 
	 * @param messageFromClient
	 * @return answer to the clients side
	 */
	public static Message createRestaurantsForSupplierRegistration(Message messageFromClient) throws SQLException{
		Message returnMessageToClient = messageFromClient;
		BranchManager manager = (BranchManager) messageFromClient.getObject();
		Branch branchNameForRelevantRestaurants = manager.getHomeBranch();
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
	
	/**
	 * This method checks if the customerID exist in the customer table.
	 * 
	 * @param customer
	 * @return true if user id exist
	 */
	private static boolean checkIfUserIdExist(User user) {	
		ResultSet rs = Query.getColumnFromTableInDB("login", "userID");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(user.getUserId())) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the username already exists on the login table.
	 * 
	 * @param login
	 * @return true if login user name exist
	 */
	private static boolean checkIfLoginUserNameExist(Login login) {
		ResultSet rs = Query.getColumnFromTableInDB("login", "username");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(login.getUserName())) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the credit card already exist in db.
	 * 
	 * @param creditCard
	 * @return true if credit card number exist
	 */
	private static boolean checkIfCreditCardNumberExist(CreditCard creditCard) {
		ResultSet rs = Query.getColumnFromTableInDB("creditcard", "creditCardNumber");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(creditCard.getCreditCardNumber())) {
					return true;
					
				}
			}
			rs.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the supplier name already exist in db .
	 * 
	 * @param supplierWorker
	 * @return true if supplier name exist
	 */
	private static boolean checkIfSupplierNameExsist(SupplierWorker supplierWorker) {
		ResultSet rs = Query.getColumnFromTableInDB("supplier", "supplierName");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(supplierWorker.getSupplier().getSupplierName())) {
					return true;
					
				}
			}
			rs.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method gets from the db all the confirmed companies and returns them as arraylist.
	 * 
	 * @param message
	 * @return message answer to the client side
	 */
	public static Message getCompaniesFromDB(Message message) {
		ArrayList<String> companies = new ArrayList<>();
		ResultSet rs = Query.getColumnWithConditionFromTableInDB("company", "companyName","companyStatusInSystem='CONFIRMED'" );
		try {
			while(rs.next()) {
				companies.add(rs.getString(1));
			}
			rs.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.setTask(Task.DISPLAY_COMPANIES_INTO_COMBOBOX);
		message.setAnswer(Answer.SUCCEED);
		message.setObject(companies);
		return message;
	}
	/**
	 * This method returns a supplier object on request
	 * used for new supplier worker registration (needs a supplier object for creation)
	 * 
	 * @param messageFromClient
	 * @return message answer to the client side
	 */
	public static Message getSupplierFromDb(Message messageFromClient) {	
		Message returnMessageToClient = messageFromClient;
		String supplierId = (String) messageFromClient.getObject();
		Supplier supplier = new Supplier(null, null, null, null, null, 0,null);
		ResultSet rs = Query.getRowsFromTableInDB("supplier","supplierId='"+supplierId+"'");
		try {
			while(rs.next()) {
				supplier.setSupplierId(rs.getString(1));
				supplier.setSupplierName(rs.getString(2));
				supplier.setHomeBranch(Branch.valueOf(rs.getString(3)));
				supplier.setEmail(rs.getString(4));
				supplier.setPhoneNumber(rs.getString(5));
				supplier.setRevenueFee(rs.getDouble(6));
				supplier.setStatusInSystem(ConfirmationStatus.valueOf(rs.getString(7)));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//set the message back to the customer
		returnMessageToClient.setObject(supplier);
		returnMessageToClient.setAnswer(Answer.SUPPLIER_FOUND_IN_DB);
		return returnMessageToClient;
	}
	
	/**
	 * This method looks for all the users in the registration table and returns them into array list to the client.
	 * 
	 * @param message
	 * @return message answer to the client side
	 */
	public static Message getUsersFromRegistrationTable(Message message) {
		Message returnMessageToClient = message;
		User bm = (User)message.getObject();
		Branch homeBranch = bm.getHomeBranch();
		ArrayList<UserForRegistration> usersList = new ArrayList<>();
		ResultSet rs =Query.getRowsFromTableInDB("registration", "userType= 'user' AND (homeBranch='" + bm.getHomeBranch().toString() +"')");
		try {
			while(rs.next()) {
				usersList.add(new UserForRegistration(rs.getString(2),ConfirmationStatus.valueOf(rs.getString(3)), rs.getString(4),rs.getString(5),
						Branch.valueOf(rs.getString(6)),false,rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),
						rs.getString(13),rs.getString(14)));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMessageToClient.setTask(Task.DISPLAY_USERS_ON_REGISTRATION_LIST);
		returnMessageToClient.setAnswer(Answer.SUCCEED);
		returnMessageToClient.setObject(usersList);
		return returnMessageToClient;
		
	}
	
	/**
	 * This method gets all the suppliers from specific branch that are not registered yet
	 * and returns them into arrayList to the branch manager .
	 * 
	 * @param message
	 * @return message answer to the client side
	 */
	public static Message getSuppliersWaitingForRegistration(Message message) {
		Message returnMessageToClient = message;
		Branch homeBranch = (Branch)message.getObject();
		ArrayList<Supplier> suppliersWaitingForRegistration = new ArrayList<>();
		ResultSet rs = Query.getRowsFromTableInDB("supplier", "statusInSystem='PENDING_REGISTRATION' AND (homeBranch='"+homeBranch.toString()+"')");
		try {
			while(rs.next()) {
				suppliersWaitingForRegistration.add(new Supplier(rs.getString(1),rs.getString(2),Branch.valueOf(rs.getString(3)), rs.getString(4), rs.getString(5),
						rs.getDouble(6), ConfirmationStatus.valueOf(rs.getString(7))));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnMessageToClient.setTask(Task.DISPLAY_SUPPLIERS_INTO_TABLE);
		returnMessageToClient.setAnswer(Answer.SUCCEED);
		returnMessageToClient.setObject(suppliersWaitingForRegistration);
		return returnMessageToClient;
	}
	
	public static void getSupplierConfirmation(Message message) {
		Supplier supplier = (Supplier)message.getObject();
		Query.updateOneColumnForTableInDbByPrimaryKey("supplier", "statusInSystem='CONFIRMED'", "supplierId='"+supplier.getSupplierId()+"'");
	}
	
	
	/**
	 * In this method we get all the messing information about the business customer
	 * from the HR manager, we insert the information and we change the status to 
	 * CONFIRMED so he is approved in system .
	 * 
	 * @param message
	 * @return message answer to the client side
	 */
	public static Message businessCustomerCompleteRegistration(Message message) {
		ArrayList<Object> businessCustomerBudgetDetails = (ArrayList<Object>)message.getObject();
		BudgetType budgetType = BudgetType.valueOf((String)businessCustomerBudgetDetails.get(0));
		System.out.println(budgetType);
		int budgetAmount = Integer.parseInt((String)businessCustomerBudgetDetails.get(1));
		String businessCustomerId = (String)businessCustomerBudgetDetails.get(2);
		Query.updateOneColumnForTableInDbByPrimaryKey("businesscustomer", "statusInSystem='CONFIRMED', budgetType='"+budgetType+"', "
				+ "budgetMaxAmount='"+budgetAmount+"'", "userID='"+businessCustomerId+"'");
		return new Message(Task.CONFIRM_BUSINESS_CUSTOMER,Answer.SUCCEED,null);
	}


}
