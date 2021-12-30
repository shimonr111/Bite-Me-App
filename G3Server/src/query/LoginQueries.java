package query;

import java.sql.ResultSet;
import java.sql.SQLException;

import communication.Answer;
import communication.Message;
import communication.Task;
import users.Branch;
import users.BranchManager;
import users.BudgetType;
import users.BusinessCustomer;
import users.CeoBiteMe;
import users.Company;
import users.Customer;
import users.ConfirmationStatus;
import users.HrManager;
import users.Login;
import users.Supplier;
import users.SupplierWorker;
import users.User;
import users.WorkerPosition;

/**
 * 
 * @author Mousa, Srour
 * @author Lior, Guzovsky
 * @author Alexander, Martinov
 * Class description:
 * This class will contain all the queries to DB
 * that relate to Login phase.
 * @version 15/12/2021
 */
public class LoginQueries {
	
	/**
	 * main function for parsing the message and getting the relevant data for login check
	 * 
	 * @param messageFromClient
	 * @return
	 * @throws SQLException
	 */
	public static Message createLoginMessageForServer(Message messageFromClient) throws SQLException {
		Message recivedMessageFromClient = messageFromClient;
		String userName = ((Login)messageFromClient.getObject()).getUserName();
		String password = ((Login)messageFromClient.getObject()).getPassword();
		String userId = null;
		String userType = null;
		
		/**
		 * get the row from the DB if 
		 * possible from the login table.
		 */
		ResultSet rs = Query.getRowsFromTableInDB("login","username='"+userName+"' AND password='"+password+"'");
		//If the row doesn't exist in login Table
		if(!rs.isBeforeFirst()) {
			recivedMessageFromClient.setObject(null);
			recivedMessageFromClient = setMessageAccordingly(null,recivedMessageFromClient,null);
			return recivedMessageFromClient;
		}
		//Get the user Type and ID from the statement
		if(rs.next()) {
		userId = rs.getString(3);
		userType =  rs.getString(4);
		}
		rs.close();
		/**
		 * get a row from the 
		 * DB which is according to the userType table
		 * for example: "Customer Table.
		 */
		rs = Query.getRowsFromTableInDB(userType,"userID='"+userId+"'");
		switch(userType) {
		case "customer":
			Customer customerResult = null;
			//parse data and put in instance.
			customerResult = LoginQueries.getCustomer(rs);
			//set the Message to return to the Client side
			recivedMessageFromClient=setMessageAccordingly(customerResult,recivedMessageFromClient,"customer");
			break;
		case "ceobiteme":
			CeoBiteMe ceoResult = null;
			//parse data and put in instance.
			ceoResult = LoginQueries.getCeo(rs);
			//set the Message to return to the Client side
			recivedMessageFromClient=setMessageAccordingly(ceoResult,recivedMessageFromClient,"ceobiteme");
			break;
		case "hrmanager":
			HrManager hrManagerResult=null;
			//parse data and put in instance.
			hrManagerResult=LoginQueries.getHrManager(rs);
			//set the Message to return to the Client side
			recivedMessageFromClient=setMessageAccordingly(hrManagerResult,recivedMessageFromClient,"hrmanager");
			break;
		case "businesscustomer":
			BusinessCustomer businessCustomerResult = null;
			//parse data and put in instance.
			businessCustomerResult = LoginQueries.getBusinessCustomer(rs);
			//set the Message to return to the Client side
			recivedMessageFromClient=setMessageAccordingly(businessCustomerResult,recivedMessageFromClient,"businesscustomer");
			break;
		case "supplierworker":
			SupplierWorker supplierResult = null;
			//parse data and put in instance.
			supplierResult = LoginQueries.getSupplierWorker(rs);
			//set the Message to return to the Client side
			recivedMessageFromClient=setMessageAccordingly(supplierResult,recivedMessageFromClient,"supplierworker");
			break;
		case "branchmanager":
			BranchManager branchManagerResult = null;
			//parse data and put in instance.
			branchManagerResult = LoginQueries.getBranchManager(rs);
			//set the Message to return to the Client side
			recivedMessageFromClient = setMessageAccordingly(branchManagerResult,recivedMessageFromClient,"branchmanager");
		default:
			break;
		}
		return recivedMessageFromClient;
	}
	
	/**
	 * This method gets an user object and sets the correct
	 * Task and answer according to the info.
	 * DO NOT CHANGE THE ORDER IN THE LOWER ELSE CASE WHERE WE USE INSTANCEOF!!!!!
	 * @param user
	 * @param message
	 * @param tableName : it is the table name in DB according to the user type .
	 * @return message with the correct Task and Answer.
	 */
	public static Message setMessageAccordingly(User user, Message message,String tableName) {
		if (user == null) {
			message.setAnswer(Answer.ERROR_USER_NOT_FOUND);
			message.setTask(Task.PRINT_ERROR_TO_SCREEN);
		} else if (user.isLoggedIn()) {
			message.setAnswer(Answer.ERROR_ALREADY_LOGGED_IN);
			message.setTask(Task.PRINT_ERROR_TO_SCREEN);
		} else if (user.getStatusInSystem()==ConfirmationStatus.PENDING_APPROVAL) {
			message.setAnswer(Answer.ERROR_USER_NOT_CONFIRMED);
			message.setTask(Task.PRINT_ERROR_TO_SCREEN);
		} else {
			//set the user status to isLoggedIn=1, we do it here after checking all the conditions up, so we know the user can log in .
			Query.updateOneColumnForTableInDbByPrimaryKey(tableName,"isLoggedIn"+"="+"1" , "userID="+user.getUserId());

			message.setObject(user);
			message.setTask(Task.CREATE_USER_PORTAL);
			if (user instanceof HrManager)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER);
			else if (user instanceof BusinessCustomer)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER);
			else if (user instanceof Customer)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_CUSTOMER);
			else if (user instanceof CeoBiteMe)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME);
			else if (user instanceof SupplierWorker)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_SUPPLIER);
			else if (user instanceof BranchManager)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_BRANCH_MANAGER);
		}
		return message;
	}
	
	/**
	 *  This method gets an message that contains the User as object in the message 
	 *  we check what is the user type and we send query to update the isLoggedIn status accordingly.
	 * @param message
	 */
	public static void logOutUser(Message message) {
		if (message.getObject() instanceof HrManager)
			Query.updateOneColumnForTableInDbByPrimaryKey("hrmanager","isLoggedIn"+"="+"0", "userID="+((User) (message.getObject())).getUserId());
		else if (message.getObject() instanceof BusinessCustomer)
			Query.updateOneColumnForTableInDbByPrimaryKey("businesscustomer","isLoggedIn"+"="+"0", "userID="+((User) (message.getObject())).getUserId());
		else if (message.getObject() instanceof Customer)
			Query.updateOneColumnForTableInDbByPrimaryKey("customer","isLoggedIn"+"="+"0", "userID="+((User) (message.getObject())).getUserId());
		else if (message.getObject() instanceof CeoBiteMe)
			Query.updateOneColumnForTableInDbByPrimaryKey("ceobiteme","isLoggedIn"+"="+"0", "userID="+((User) (message.getObject())).getUserId());
		else if (message.getObject() instanceof SupplierWorker)
			Query.updateOneColumnForTableInDbByPrimaryKey("supplierworker","isLoggedIn"+"="+"0", "userID="+((User) (message.getObject())).getUserId());
		else if (message.getObject() instanceof BranchManager) 
			Query.updateOneColumnForTableInDbByPrimaryKey("branchmanager","isLoggedIn"+"="+"0", "userID="+((User) (message.getObject())).getUserId());
	}
	
	/**
	 * This method searches for a customer object by his userId
	 * and gets all the parameter and creates a new object from these parameters.
	 * 
	 * @param rs
	 * @return
	 */
	public static Customer getCustomer(ResultSet rs) {
		Customer customerResult = null;
		try {
			if(rs.next()) {
				customerResult = new Customer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),0);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerResult;
	}
	
	/**
	 * This method searches for a ceobiteme object by his userId
	 * and gets all the parameter and creates a new object from these parameters.
	 * 
	 * @param rs
	 * @return
	 */
	public static CeoBiteMe getCeo(ResultSet rs) {
		CeoBiteMe ceoResult = null;
		try {
			if(rs.next()) {
				ceoResult = new CeoBiteMe(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getString(7),rs.getString(8));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ceoResult;
	}
	
	/**
	 * This method searches for a HrManager object by his userId
	 * and gets all the parameter and creates a new object from these parameters
	 * 
	 * @param rs
	 * @return
	 */
	public static HrManager getHrManager(ResultSet rs) {
		HrManager hrManagerResult = null;
		Company company = null;
		try {	
			if(rs.next()) {
				company = getCompany(rs.getString(9));
				hrManagerResult = new HrManager(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getString(7),rs.getString(8),company);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hrManagerResult;
	}
	
	/**
	 * This method searches for a BusinessCustomer object by his userId
	 * and gets all the parameter and creates a new object from these parameters.
	 * 
	 * @param rs
	 * @return
	 */
	public static BusinessCustomer getBusinessCustomer(ResultSet rs) {
		BusinessCustomer businessCustomerResult = null;
		Company company = null;
		try {
			if(rs.next()) {
				company = getCompany(rs.getString(12));
				businessCustomerResult = new BusinessCustomer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(16),rs.getString(8),rs.getString(9),rs.getString(10),0,company,(BudgetType.valueOf(rs.getString(13))),rs.getInt(15));
				businessCustomerResult.setBudgetUsed(rs.getDouble(17));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return businessCustomerResult;
	}
	
	/**
	 * This method searches for a SupplierWorker object by his userId
	 * and gets all the parameter and creates a new object from these parameters.
	 * 
	 * @param rs
	 * @return
	 */
	public static SupplierWorker getSupplierWorker(ResultSet rs) {
		SupplierWorker supplierWorkerResult = null;
		Supplier supplier = null;
		try {
			if(rs.next()) {
				supplier = getSupplierForWorker(rs.getString(9));
				supplierWorkerResult = new SupplierWorker(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getString(7),rs.getString(8),supplier,(WorkerPosition.valueOf(rs.getString(10))));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return supplierWorkerResult;
	}
	/**
	 * This method searches for a Supplier object by his SupplierId
	 * and gets all the parameter and creates a new object from these parameters.
	 * 
	 * @param supplierId
	 */
	private static Supplier getSupplierForWorker(String supplierId) {
		Supplier supplierResult = null;
		ResultSet rs = Query.getRowsFromTableInDB("supplier","supplierId='"+supplierId+"'");
		try {
			if(rs.next()) {
				supplierResult = new Supplier(rs.getString(1),rs.getString(2),(Branch.valueOf(rs.getString(3))),rs.getString(4),rs.getString(5),rs.getDouble(6),
						ConfirmationStatus.valueOf(rs.getString(7)));
			}
		}catch(SQLException e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		return supplierResult;
	}

	/**
	 * This method searches for a Company object by his companyName
	 * and gets all the parameter and creates a new object from these parameters.
	 * 
	 * @param companyName
	 * @return
	 */
	public static Company getCompany(String companyName) {
		Company companyResult = null;
		ResultSet rs = Query.getRowsFromTableInDB("company","companyName='"+companyName+"'");
		try {
			if(rs.next()) {
				companyResult = new Company(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),rs.getInt(5));
			}
		}catch(SQLException e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		return companyResult;
	}
	
	/**
	 * This is a function for getting data from DB
	 * for the Branch Manager
	 * 
	 * @param rs
	 * @return
	 */
	public static BranchManager getBranchManager(ResultSet rs) {
		BranchManager branchManagerResult = null;
		try {
			if(rs.next()) {;
			branchManagerResult = new BranchManager(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getString(7),rs.getString(8));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return branchManagerResult;
	}
}
