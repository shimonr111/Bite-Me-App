package query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import communication.Answer;
import communication.Message;
import communication.Task;
import users.BusinessCustomer;
import users.CeoBiteMe;
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
 * @version 05/12/2021
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
	 * @param columnSet : the name of the column and what we want to set it for example : (isLoggedIn=1)
	 * @param condition : what is the condition behind the WHERE operand , for example : (userID = 12)
	 */
	public static void updateOneColumnForTableInDbByPrimaryKey(String tableName,String columnSet,String condition) {
		PreparedStatement pstmt=null;
		//ResultSet rs=null;
		try {
			String query = "UPDATE semesterialproject."+ tableName+" SET "+columnSet+" WHERE " +"("+condition+")";
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();	
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
