package query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import communication.Answer;
import communication.Message;
import communication.Task;
import users.Branch;
import users.BusinessCustomer;
import users.CeoBiteMe;
import users.ConfirmationStatus;
import users.CreditCard;
import users.Customer;
import users.HrManager;
import users.Login;
import users.Supplier;
import users.User;
/**
 * 
 * @author Mousa, Srour
 * Class description:
 * This class will analyze the login feature from the side of the server
 * @version 10/12/2021
 */
public class Query {

	private static Connection con;
	public static void setConnectionFromServerToDB(Connection connection) {
		con = connection;
	}
	
	
	/**
	 * General methods for getting Data from DB
	 * 
	 * @param tableName
	 * @param condition
	 * @return
	 */
	public static ResultSet getRowFromTableInDB(String tableName, String condition) {
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
	 * @param columnSet : the name of the column and what we want to set it for example : (isLoggedIn=1)
	 * @param condition : what is the condition behind the WHERE operand , for example : (userID = 12)
	 */
	public static void updateOneColumnForTableInDbByPrimaryKey(String tableName, String columnSet, String condition) {
		PreparedStatement pstmt = null;
		//ResultSet rs=null;
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
		String query = "INSERT INTO semesterialproject.creditcard ( creditCardNumber, creditCardCvvCode, creditCardDateOfExpiration) VALUES ( '" 
				+ creditCard.getCreditCardNumber()+"' , '" +creditCard.getCreditCardDateOfExpiration() + "' , '"+creditCard.getCreditCardCvvCode()+"' )";
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
				+  "', '" + customer.getUserEmail() +  "', '" + customer.getPhoneNumber() +  "', '" + customer.getPrivateCreditCard() +  "' )";
		PreparedStatement pstmt=null;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
				
	}
	
}
