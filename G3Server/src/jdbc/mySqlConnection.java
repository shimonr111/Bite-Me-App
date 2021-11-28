package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mySqlConnection {
	private Connection con;

	/*create the connection to the DB */
	public boolean connectToDB(String DBName, String DBUserName, String DBPassword) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
			return false;

		}

		try {
			con = DriverManager.getConnection(DBName, DBUserName, DBPassword);
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return true;
	}

	public Connection getConnection() {
		return con;
	}
}
