package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import bitemeserver.BiteMeServerUI;

/**
 * @author Lior, Guzovsky.
 * Class description: 
 * 
 * This is a class for the connection to the 
 * DB.
 * 
 * @version 03/12/2021
 *
 */
public class mySqlConnection {
	/**
	 * Class members description:
	 */	
	
	private Connection con;

	/**
	 * create the connection to the DB
	 * 
	 * @param DBName
	 * @param DBUserName
	 * @param DBPassword
	 * @return
	 */
	public boolean connectToDB(String DBName, String DBUserName, String DBPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			BiteMeServerUI.console.add("Driver definition succeed\n");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			return false;

		}
		try {
			con = DriverManager.getConnection(DBName, DBUserName, DBPassword);
			BiteMeServerUI.console.add("SQL connection succeed\n");
		} catch (SQLException ex) {/* handle any errors */
			BiteMeServerUI.console.add("SQLException: " + ex.getMessage()+"\n");
			BiteMeServerUI.console.add("SQLState: " + ex.getSQLState()+"\n");
			BiteMeServerUI.console.add("VendorError: " + ex.getErrorCode()+"\n");
			return false;
		}
		return true;
	}

	public Connection getConnection() {
		return con;
	}
}
