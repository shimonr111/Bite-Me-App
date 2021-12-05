package query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import communication.Message;
import users.Branch;
import users.BudgetType;
import users.BusinessCustomer;
import users.CeoBiteMe;
import users.Company;
import users.Customer;
import users.HrManager;
import users.Login;
import users.Supplier;

public class LoginQueries {
	/**
	 * 
	 * @author Mousa, Srour
	 * Class description:
	 * This class will contain all the queries to DB
	 * All queries to DB will run throw this class.
	 * @version 05/12/2021
	 */
	
	/**
	 * This method searches for a login object by his userName
	 * and gets all the parameters and creates a new object from these parameters.
	 * @param userName
	 * @param con
	 * @return loginRsult which contains the specific row from the DB.
	 */
	public static Login getLogin(String userName,Connection con) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Login loginResult = null;
		String query ="SELECT * FROM semesterialproject.login WHERE username=?"; // setting the query
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, userName);
			rs= pstmt.executeQuery();	
			if(rs.next()) {
				loginResult = new Login(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginResult;
	}
	
	/**
	 * This method searches for a customer object by his userId
	 * and gets all the parameter and creates a new object from these parameters.
	 * @param userId
	 * @param con
	 * @return customrResult which contains the specific row from the DB.
	 */
	public static Customer getCustomer(String userId,Connection con) {
		Customer customerResult=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query ="SELECT * FROM semesterialproject.customer WHERE userID=?";
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, userId);
			rs= pstmt.executeQuery();	
			if(rs.next()) {
				customerResult = new Customer(rs.getString(1),rs.getBoolean(2),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10));
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
	 * @param userId
	 * @param con
	 * @return ceoResult which contains the specific row from the DB.
	 */
	public static CeoBiteMe getCeo(String userId,Connection con) {
		CeoBiteMe ceoResult=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query ="SELECT * FROM semesterialproject.ceobiteme WHERE userID=?";
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, userId);
			rs= pstmt.executeQuery();	
			if(rs.next()) {
				ceoResult = new CeoBiteMe(rs.getString(1),rs.getBoolean(2),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9));
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
	 * and gets all the parameter and creates a new object from these parameters.
	 * @param userId
	 * @param con
	 * @return hrManagerResult which contains the specific row from the DB.
	 */
	public static HrManager getHrManager(String userId,Connection con) {
		HrManager hrManagerResult=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query ="SELECT * FROM semesterialproject.hrmanager WHERE userID=?";
		Company company = null;
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, userId);
			rs= pstmt.executeQuery();	
			if(rs.next()) {
				company = getCompany(rs.getString(11),con);
				hrManagerResult = new HrManager(rs.getString(1),rs.getBoolean(2),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),company,(BudgetType.valueOf(rs.getString(12))),rs.getString(13),rs.getInt(14),
								rs.getString(15));
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
	 * @param userId
	 * @param con
	 * @return businessCustomerResult which contains the specific row from the DB.
	 */
	public static BusinessCustomer getBusinessCustomer(String userId,Connection con) {
		BusinessCustomer businessCustomerResult=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query ="SELECT * FROM semesterialproject.businesscustomer WHERE userID=?";
		Company company = null;
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, userId);
			rs= pstmt.executeQuery();	
			if(rs.next()) {
				company = getCompany(rs.getString(11),con);
				businessCustomerResult = new BusinessCustomer(rs.getString(1),rs.getBoolean(2),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),company,(BudgetType.valueOf(rs.getString(12))),rs.getString(13),rs.getInt(14),
								rs.getString(15));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return businessCustomerResult;
	}
	
	/**
	 * This method searches for a Supplier object by his userId
	 * and gets all the parameter and creates a new object from these parameters.
	 * @param userId
	 * @param con
	 * @return supplierResult which contains the specific row from the DB.
	 */
	public static Supplier getSupplier(String userId,Connection con) {
		Supplier supplierResult=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query ="SELECT * FROM semesterialproject.supplier WHERE userID=?";
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, userId);
			rs= pstmt.executeQuery();	
			if(rs.next()) {;
				supplierResult = new Supplier(rs.getString(1),rs.getBoolean(2),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return supplierResult;
	}
	
	/**
	 * This method searches for a Company object by his userId
	 * and gets all the parameter and creates a new object from these parameters.
	 * @param userId
	 * @param con
	 * @return companyResult which contains the specific row from the DB.
	 */
	public static Company getCompany(String companyName,Connection con) {
		Company companyResult=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query="SELECT * FROM semesterialproject.company WHERE companyNAme=?";
		try {
			pstmt=con.prepareStatement(query);
			pstmt.setString(1, companyName);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				companyResult = new Company(rs.getString(1),rs.getBoolean(2));
			}
		}catch(SQLException e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}
		return companyResult;
	}
}
