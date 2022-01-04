package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import bitemeserver.BiteMeServerUI;

/**
 * Class description: 
 * This is a class for the connection to the 
 * DB.
 */

/**
 * 
 * @author Lior, Guzovsky.
 * 
 * @version 03/12/2021
 */
public class mySqlConnection{
	
	/**
	 * Class members description:
	 */	
	
	/**
	 * This is the var of the connection to the DB
	 */
	private Connection con;

	/**
	 * Create the connection to the DB
	 * 
	 * @param DBName
	 * @param DBUserName
	 * @param DBPassword
	 * @return
	 */
	public boolean connectToDB(String DBName, String DBUserName, String DBPassword,boolean isFirstTime) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			if(isFirstTime)
				BiteMeServerUI.console.add("Driver definition succeed\n");
			else{
				System.out.println("Driver definition succeed\n");
			}
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			return false;

		}
		try {
			con = DriverManager.getConnection(DBName, DBUserName, DBPassword);
			if(isFirstTime)
				BiteMeServerUI.console.add("SQL connection succeed\n");
			else{
				System.out.println("SQL connection succeed\n");
			}
		} catch (SQLException ex) {/* handle any errors */
			if(isFirstTime) {
				BiteMeServerUI.console.add("SQLException: " + ex.getMessage()+"\n");
				BiteMeServerUI.console.add("SQLState: " + ex.getSQLState()+"\n");
				BiteMeServerUI.console.add("VendorError: " + ex.getErrorCode()+"\n");
				}
			else {
				System.out.println("SQLException: " + ex.getMessage()+"\n");
				System.out.println("SQLState: " + ex.getSQLState()+"\n");
				System.out.println("VendorError: " + ex.getErrorCode()+"\n");
			}
			return false;
		}
		return true;
	}

	/**
	 * This is the getter of the connection
	 * 
	 * @return connection
	 */
	public Connection getConnection() {
		return con;
	}
}
