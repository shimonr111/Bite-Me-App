package query;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import bitemeserver.BiteMeServerUI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import orders.DeliveryType;
import orders.ItemCategory;
import orders.ItemSize;
import orders.OrderStatus;
import orders.OrderTimeType;
import orders.SupplyType;

import users.Branch;
import users.BranchManager;
import users.BusinessCustomer;
import users.CeoBiteMe;
import users.Company;
import users.CreditCard;
import users.Customer;
import users.HrManager;
import users.Supplier;
import users.SupplierWorker;
import users.User;
import util.SupplierByReport;
import util.DateTimeHandler;

/**
 * 
 * @author Mousa, Srour
 * @author Alexander, Martinov
 * @author Lior, Guzovsky
 * Class description:
 * This class will analyze the login feature from the side of the server
 * @version 26/12/2021
 */
public class Query {

	private static Connection con;
	private static Connection externalCon;
	public static void setConnectionFromServerToDB(Connection connection) {
		con = connection;
	}
	public static void setConnectionFromServerToExternalDB(Connection connection) {
		externalCon=connection;
	}
	
	
	
	/**
	 * This method will be called once to import the data of users management from the external DB.
	 * @return
	 */
	public static ResultSet getExternalDB() {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query = "SELECT * FROM externaldb.usersmanagement";
		try {
		pstmt = externalCon.prepareStatement(query);
		rs= pstmt.executeQuery();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;	
	}
	
	/**
	 * This method  will get the external data and insert it into our registration table .
	 * @param userType
	 * @param userID
	 * @param statusInSystem
	 * @param firstName
	 * @param lastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param email
	 * @param phoneNumber
	 * @param creditCardNumber
	 * @param creditCardCvvCode
	 * @param creditCardDateOfExpiration
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean insertRowIntoRegistrationTable(String userType, String userID, String statusInSystem, String firstName,String lastName,
			String homeBranch,int isLoggedIn,String email, String phoneNumber, String creditCardNumber,String creditCardCvvCode,String creditCardDateOfExpiration,
			String username,String password,String companyName, int companyCode, double revenueFee) {
		String query = "INSERT INTO semesterialproject.registration ( userType, userID, statusInSystem, firstName,lastName,homeBranch,isLoggedIn,email, phoneNumber, creditCardNumber,"
				+ " creditCardCvvCode,creditCardDateOfExpiration,username,  password,companyName ,companyCode, revenueFee  ) VALUES( '"+ userType +"' , '" +userID  +"' , '"+ statusInSystem  +"' , '" + firstName  +"' , '"  + lastName
				+"' , '"  + homeBranch +"' , '"  + isLoggedIn +"' , '"  + email +"' , '"  + phoneNumber +"' , '"  + creditCardNumber +"' , '"  + creditCardCvvCode +"' , '"  + creditCardDateOfExpiration 
				+"' , '"  + username +"' , '"  +password +"' , '"  + companyName +"' , '"  + companyCode +"' , '"  +  revenueFee +"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			return true;
		}
		return false;
	}
	/**
	 * General methods for getting Data from DB
	 * 
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public static ResultSet getRowsFromTableInDB(String tableName, String condition) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
		String query = "SELECT * FROM semesterialproject."+tableName+"  WHERE "+condition;
		pstmt = con.prepareStatement(query);
		rs= pstmt.executeQuery();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * general method for getting specific data from db
	 * @param resultName
	 * @param condition
	 * @return
	 */
	public static ResultSet getBasicQuery(String source, String fields) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
		String query = "SELECT "+fields+"FROM "+source;
		pstmt = con.prepareStatement(query);
		rs= pstmt.executeQuery();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * method for inserting reports into db
	 * @param supplierReport
	 * @return
	 */
	public static void saveReportToDb(SupplierByReport supplier) {
		PreparedStatement pstmt=null;
		PreparedStatement check=null;
		PreparedStatement delete=null;
		ResultSet rs=null;
		try {
		String search = "SELECT * FROM semesterialproject.reports WHERE supplier=? AND reportType=? AND issueDate=?;";
		check = con.prepareStatement(search);
		check.setString(1,supplier.getSupplierId());
		check.setString(2,supplier.getReportType());
		check.setString(3,supplier.getIssueDate());
		rs = check.executeQuery();
		if(rs.next()) {
			String deleteString = "DELETE FROM `semesterialproject`.`reports` WHERE supplier=? AND reportType=? AND issueDate=?;";
			delete = con.prepareStatement(deleteString);
			delete.setString(1,supplier.getSupplierId());
			delete.setString(2,supplier.getReportType());
			delete.setString(3,supplier.getIssueDate());
			delete.executeUpdate();
		}
		String query = "INSERT INTO `semesterialproject`.`reports` (`supplier`, `reportType`,`homeBranch`, `issueDate`,report) VALUES (?,?,?,?,?);";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1,supplier.getSupplierId());
		pstmt.setString(2,supplier.getReportType());
		pstmt.setString(3,supplier.getSupplierBranch());
		pstmt.setString(4,supplier.getIssueDate());
		pstmt.setObject(5,supplier);
		pstmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method for inserting reports into db
	 * @param supplierReport
	 * @return
	 */
	public static void savePdfToDb(Object[] report) {
		//reports[0] is the branch name
		//reports[1] is the report pdf file
		//reports[2] is the report file name
		PreparedStatement pstmt=null;
		try {
		String query = "INSERT INTO `semesterialproject`.`quarterlypdf` (`filename`,`homeBranch`,pdffile) VALUES (?,?,?);";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1,(String)report[2]);
		pstmt.setString(2,(String)report[0]);
		pstmt.setObject(3,report[1]);
		
		pstmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static ResultSet getColumnFromTableInDB(String tableName, String columnName) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String query = "SELECT "+columnName +" FROM semesterialproject."+tableName;
			pstmt = con.prepareStatement(query);
			rs= pstmt.executeQuery();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rs;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param columnName
	 * @param condition
	 * @return
	 */
	public static ResultSet getColumnWithConditionFromTableInDB(String tableName,String columnName,String condition) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String query = "SELECT "+columnName +" FROM semesterialproject."+tableName+" WHERE "+condition;
			pstmt = con.prepareStatement(query);
			rs= pstmt.executeQuery();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rs;
	}
	
	/**
	 * this method gets a table name , and returns all the table.
	 * @param tableName
	 * @return
	 */
	public static ResultSet getTableFromDB(String tableName) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			String query = "SELECT * FROM semesterialproject."+tableName;
			pstmt = con.prepareStatement(query);
			rs= pstmt.executeQuery();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rs;
	}
	/**
	 * 
	 * @param tableName
	 * @param columnSet : the name of the column and what we want to set it for example : (isLoggedIn=1)
	 * @param condition : what is the condition behind the WHERE operand , for example : (userID = 12)
	 */
	public static void updateOneColumnForTableInDbByPrimaryKey(String tableName, String columnSet, String condition) {
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE semesterialproject."+ tableName+" SET "+columnSet+" WHERE " +"("+condition+")";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * @param userName
	 * @param Password
	 * @param userId
	 * @param userType
	 */
	
	public static void insertOneRowIntoLoginTable(String userName,String Password,String userId, String userType) {
		String query = "INSERT INTO semesterialproject.login ( username, password, userID, userType) VALUES( '" + userName +"' , '"+Password +"' , '" + userId+"' , '" + userType+"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(java.sql.SQLIntegrityConstraintViolationException e) {
			if(!BiteMeServerUI.console.contains("One or more User Names already Imported.\n"))
				BiteMeServerUI.console.add("One or more User Names already Imported.\n");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a query for entering a full row into the Order table in the DB
	 * 
	 * @param orderNumber
	 * @param supplierId
	 * @param customerUserId
	 * @param customerUserType
	 * @param branch
	 * @param timeType
	 * @param status
	 * @param issueDateTime
	 * @param estimatedSupplyDateTime
	 * @param actualSupplyDateTime
	 * @param supplyType
	 * @param totalPrice
	 * @param receiverFirstName
	 * @param receiverLastName
	 * @param receiverAddress
	 * @param receiverPhoneNumber
	 * @param deliveryFee
	 * @param itemList
	 * @param comments
	 */
	public static void insertOneRowIntoOrderTable(int orderNumber, String supplierId, String customerUserId, String customerUserType,
			Branch branch, OrderTimeType timeType, OrderStatus status, Date issueDateTime, Date estimatedSupplyDateTime,
			Date actualSupplyDateTime, SupplyType supplyType, double totalPrice, String receiverFirstName,
			String receiverLastName, String receiverAddress, String receiverPhoneNumber, double deliveryFee, String itemList, String comments, DeliveryType deliveryType) {
		
		String estimatedSupplyDateAndTime = DateTimeHandler.convertMySqlDateTimeFormatToString(estimatedSupplyDateTime);

		String query = "INSERT INTO semesterialproject.order ( orderNumber, supplierId, customerUserId, customerUserType, branch,"
				+ " timeType, status, estimatedSupplyDateTime, supplyType,"
				+ " totalPrice, receiverFirstName, receiverLastName, receiverAddress, receiverPhoneNumber, deliveryFee,"
				+ " itemsList, comments, deliveryType) VALUES( '" + orderNumber +"' , '"+supplierId +"' , '" + customerUserId+"' , '" + customerUserType+"' , '" + branch+"' ,"
						+ " '" + timeType.name()+"' , '" + status.name()+"' ,  '" + estimatedSupplyDateAndTime  +"' , '"+ supplyType.name()+"' ,"
								+ " '" + totalPrice+"' , '" + receiverFirstName+"' , '" + receiverLastName+"' , '" + receiverAddress+"' , '" + receiverPhoneNumber+"' ,"
										+ " '" + deliveryFee+"' , '" + itemList+"' , '" + comments+"' , '" +deliveryType.name() +"' )";

		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a query for entering a full row into the Order table in the DB
	 * 
	 * @param name
	 * @param supplierId
	 * @param category
	 * @param size
	 * @param picture
	 * @param price
	 * @param itemList
	 */
	public static void insertOneRowIntoMenuTable(String name, String supplierId, ItemCategory category, ItemSize size, 
			String picture, double price) {
			
		String query = "INSERT INTO semesterialproject.item_in_menu ( itemName, supplierId, itemCategory, itemSize, picturePath,"
				+ " itemPrice) VALUES( '" + name +"' , '"+supplierId +"' , '" + category+"' , '" + size+"' , '" + picture+"' ,"
						+ " '" + price + "' )";

		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is a query for delete rows before we update the db
	 * in order to prevent duplicates
	 * @param supplierId
	 */
	public static void deleteDuplicateRowBeforeUpdateDb(String tableName, String condition) {
		String query = "DELETE FROM semesterialproject."+tableName+" WHERE ("+condition+")";
		
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param creditCardNumber
	 * @param expDate
	 * @param cvvNumber
	 */
	public static void insertOneRowIntoCreditCardTable(CreditCard creditCard) {
		String query = "INSERT INTO semesterialproject.creditcard ( creditCardNumber, creditCardDateOfExpiration,creditCardCvvCode) VALUES ( '" 
				+ creditCard.getCreditCardNumber()+"' , '" +creditCard.getCreditCardDateOfExpiration() + "' , '"+creditCard.getCreditCardCvvCode()+"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
		}catch(SQLException e) {
			
		}
	}
	
	/**
	 * 
	 * @param userID
	 * @param status
	 * @param firstName
	 * @param lastName
	 * @param homeBranch
	 * @param isLoggedIn
	 * @param w4c
	 * @param email
	 * @param phoneNumber
	 * @param creditCard
	 */
	public static void insertOneRowIntoCustomerTable(Customer customer) {
		String query = "INSERT INTO semesterialproject.customer ( userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, privateW4cCodeNumber, email, phoneNumber, "
				+ "privateCreditCard ) VALUES( '" + customer.getUserId() + "', '" + customer.getStatusInSystem().toString() +  "', '" +customer.getUserFirstName() +  "', '" +
				customer.getUserLastName() +  "', '" + customer.getHomeBranch().toString() + "', '"  + 0 +  "', '" + customer.getPrivateW4cCodeNumber() 
				+  "', '" + customer.getUserEmail() +  "', '" + customer.getPhoneNumber() +  "', '" + customer.getPrivateCreditCard()  +"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	/**
	 *  at the beginning we get the w4cCode of the company then we add it to the query.
	 * @param businessCustomer
	 * @param type
	 */
	public static void insertOneRowIntoBusinessCustomerOrHrManagerTable(BusinessCustomer businessCustomer,String type) {
		int employerID=0;
		ResultSet rs = null;
		PreparedStatement pstmt1=null;
		try {
			String query1 = "SELECT companyCode FROM semesterialproject.company WHERE companyName='"+businessCustomer.getCompanyOfBusinessCustomerString()+"'";
			pstmt1 = con.prepareStatement(query1);
			rs= pstmt1.executeQuery();
			if(rs.next())
			 employerID = rs.getInt(1);
			rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		String query2 = "INSERT INTO semesterialproject." +type+" ( userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, companyCode, email, phoneNumber, "
				+ "privateCreditCard, companyName, budgetType, budgetMaxAmount ,privateW4cCodeNumber ) VALUES ( '" + businessCustomer.getUserId() +  "', '" + businessCustomer.getStatusInSystem().toString()+  "', '" +
				businessCustomer.getUserFirstName() +  "', '" + businessCustomer.getUserLastName() +  "', '" + businessCustomer.getHomeBranch().toString() +  "', '" + 0 +  "', '" + employerID
				+  "', '" + businessCustomer.getUserEmail() +  "', '" + businessCustomer.getPhoneNumber() +  "', '" + businessCustomer.getPrivateCreditCard() +  "', '"  + businessCustomer.getCompanyOfBusinessCustomerString() 
				+ "', '" +  businessCustomer.getBudgetOfBusinessCustomer().toString() + "', '"    + businessCustomer.getBudgetMaxAmount() +  "', '" 
				+ businessCustomer.getPrivateW4cCodeNumber()+"' )";
		PreparedStatement pstmt2=null;
		try {
			pstmt2 = con.prepareStatement(query2);
			pstmt2.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param customer
	 */
	public static boolean insertOneRowIntoSupplierWorkerTable(SupplierWorker supplierWorker) {
		String query = "INSERT INTO semesterialproject.supplierworker ( userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, email, phoneNumber, "
				+ "supplierId, workerPosition ) VALUES( '" + supplierWorker.getUserId() + "', '" + supplierWorker.getStatusInSystem() + "', '" + supplierWorker.getUserFirstName() + "', '" 
				+ supplierWorker.getUserLastName() + "', '" +  supplierWorker.getHomeBranch() + "', '" +  0+ "', '" +  supplierWorker.getUserEmail() + "', '" +  supplierWorker.getPhoneNumber()
				+ "', '" +  supplierWorker.getSupplier().getSupplierId() + "', '" + supplierWorker.getWorkerPosition() +"' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			BiteMeServerUI.console.add("Supplier workers already Imported.\n");
			return true;
		}
		
		return false;
				
	}
	
	/**
	 * This method gets a company object and insert it into the company table in DB.
	 * @param company
	 */
	public static boolean insertOneRowIntoCompanyTable(Company company) {
		String query = "INSERT INTO semesterialproject.company (companyName, companyStatusInSystem, address, email, companyCode) VALUES ('" +company.getCompanyName()
		+"', '" + company.getStatusCompanyInSystem() +"', '" + company.getAddress() +"', '" + company.getEmail() +"', '" + company.getcompanyCode() + "' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			BiteMeServerUI.console.add("Companies already Imported.\n");
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method gets a ceo user and insert him into ceoBiteMe table.
	 * @param ceo
	 */
	public static boolean inserOneRowIntoCeoBiteMeTable(CeoBiteMe ceo) {
		String query = "INSERT INTO semesterialproject.ceobiteme (userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, email, phoneNumber) VALUES ('" + ceo.getUserId()
		+ "' , '" + ceo.getStatusInSystem().toString() 	+ "' , '" + ceo.getUserFirstName() + "' , '" + ceo.getUserLastName() + "' , '" + ceo.getHomeBranch().toString() + "' , '" + 0 
		+ "' , '" + ceo.getUserEmail() + "' , '" + ceo.getPhoneNumber() +"' )";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			BiteMeServerUI.console.add("Ceo Bite Me Already Imported.\n");
			return true;
		}
		return false;
	}
	
	/**
	 * this method gets a branch manager and insert it into branch manager table.
	 * @param bm
	 */
	public static boolean insertOneRowIntoBranchManagerTable(BranchManager bm) {
		String query = "INSERT INTO semesterialproject.branchmanager (userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, email, phoneNumber) VALUES ('" + bm.getUserId()
		+ "' , '" + bm.getStatusInSystem().toString() 	+ "' , '" + bm.getUserFirstName() + "' , '" + bm.getUserLastName() + "' , '" + bm.getHomeBranch().toString() + "' , '" + 0 
		+ "' , '" + bm.getUserEmail() + "' , '" + bm.getPhoneNumber() +"' )";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			BiteMeServerUI.console.add("Branch Managers Already Imported.\n");
			return true;
		}
		return false;
	}
	
	/**
	 * This method gets a supplier , and inserts the supplier object into the supplier table in DB.
	 * @param supplier
	 */
	public static boolean insertOneRowIntoSupplierTable(Supplier supplier) {
		String query = "INSERT INTO semesterialproject.supplier (supplierId, supplierName, homeBranch, email, phoneNumber, revenueFee, statusInSystem ) VALUES ('" + supplier.getSupplierId() 
		+ "' , '" + supplier.getSupplierName() + "' , '" +  supplier.getHomeBranch() + "' , '" +  supplier.getEmail() + "' , '" +  supplier.getPhoneNumber() + "' , '" +  supplier.getRevenueFee()
		+ "' , '" +  supplier.getStatusInSystem() +"' )";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			BiteMeServerUI.console.add("Suppliers already Imported.\n");
			return true;
		}
		return false;
	}
	
	/**
	 * this method gets an object of Hr Manager , and parse the data 
	 * to insert a new row into Hr Manager table in DB.
	 * @param hr
	 * @return
	 */
	public static boolean insertOneRowIntoHrManagerTable(HrManager hr) {
		String query = "INSERT INTO semesterialproject.hrmanager (userID, statusInSystem, firstName, lastName, homeBranch, isLoggedIn, email, "
				+ "phoneNumber, companyName) VALUES ('" + hr.getUserId() 
				+ "' , '" + hr.getStatusInSystem() + "' , '" + hr.getUserFirstName() + "' , '" +  hr.getUserLastName() + "' , '" + hr.getHomeBranch() + "' , '" + 0
				+ "' , '" + hr.getUserEmail() + "' , '" + hr.getPhoneNumber() + "' , '" +hr.getCompany().getCompanyName() +"')";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			BiteMeServerUI.console.add("Hr Manager already Imported.\n");
			return true;
		}
		return false;
	}
	
	/**
	 * this method gets a table name and condition , and deletes the row according to the condition from DB.
	 * condition must be according to table's primary key !
	 * @param tableName
	 * @param condition
	 */
	public static void deleteRowFromTableInDbByPrimaryKey(String tableName,String condition) {
		String query = "DELETE FROM semesterialproject."+tableName+" WHERE ("+condition+")";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update all budgetUsed according to the condition
	 * (which can be weekly,daily or monthly) to 0
	 * 
	 * @param condition
	 */
	public static void updateBusinessCustomersBudgetUsed(String condition) {
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE semesterialproject.businesscustomer" +" SET "+"budgetUsed= 0"+" WHERE " +"("+condition+")";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * This method gets all the balance table parameters 
	 * and inserts a new row into this table in DB.
	 * @param userId
	 * @param supplierId
	 * @param balance
	 */
	public static void insertOneRowIntoBalanceTable(String userId,String supplierId,double balance) {
		String query = "INSERT INTO semesterialproject.balance (supplierId, customerUserId, balance) VALUES ('" + supplierId + "' , '" + userId + "' , '" +  balance +"')";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
