package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.jdbc.SuspendableXAConnection;

import communication.Answer;
import communication.Message;
import communication.Task;
import users.Branch;
import users.BudgetType;
import users.BusinessCustomer;
import users.Company;
import users.ConfirmationStatus;
import users.Customer;
import users.HrManager;
import users.User;

/**
 * 
 * @author Mousa, Srour

 * Class description:
 * This class will contain all the queries to DB
 * that relate to Editing users phase.
 * @version 13/12/2021
 */
public class EditUsersQueries {
	
	
	/**
	 * This method gets a branch manager object , and gets all the customers,businesscustomers and hrmanagers that belong to same branch.
	 * @param message
	 * @return list of customers .
	 */
	public static Message getCustomersListFromDb(Message message) {
		Message returnMessageToClient;
		ArrayList<User> customersList = new ArrayList<>();
		Company company;
		ResultSet rs;
		Branch homeBranch = ((User)message.getObject()).getHomeBranch();
		rs = Query.getRowsFromTableInDB("customer","homeBranch = '"+homeBranch.toString()+"'");
		try {
			while(rs.next()) {
				customersList.add(new Customer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10), rs.getDouble(11)));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rs= Query.getRowsFromTableInDB("businesscustomer","homeBranch = '"+homeBranch.toString()+"'");
		try {
			while(rs.next()) {
				company = LoginQueries.getCompany(rs.getString(12));
				customersList.add( new BusinessCustomer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),company,(BudgetType.valueOf(rs.getString(13))),
						rs.getInt(15)));
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		returnMessageToClient = new Message (Task.DISPLAY_CUSTOMERS_INTO_TABLE,Answer.SUCCEED,customersList);
		return returnMessageToClient;
		
	}

	/**
	 * This method is general for every user type , in the message we sent the userid,the new status as a string and the table name
	 * this method calls the update query method and changes the status.
	 * @param message
	 */
	public static void setCustomerNewStatus(Message message) {
		ArrayList<String> list = (ArrayList<String>) message.getObject();
		String userId= list.get(0);
		String newStatus = list.get(1);
		String tableName = list.get(2);
		Query.updateOneColumnForTableInDbByPrimaryKey(tableName, "statusInSystem = '"+newStatus+"'", "userID='"+userId+"'");
	}
	
	/**
	 * this method gets a list that contains userId and user type , deletes the user from his table and from login table.
	 * @param message
	 */
	public static void removeUserFromDB(Message message) {
		ArrayList<String> list = (ArrayList<String>) message.getObject();
		String userId = list.get(0);
		String tableName = list.get(1);
		Query.deleteRowFromTableInDbByPrimaryKey(tableName, "userID='"+userId+"'");
		Query.deleteRowFromTableInDbByPrimaryKey("login", "userID='"+userId+"'");
	}
	
	/**
	 * this method will return an arraylist of business customer which not approved yet,
	 * this method will be called after pressing on business customers confirmation from the HR manager portal.
	 * @param message
	 * @return
	 */
	public static Message getBusinessCustomersListFromDb(Message message) {
		Message returnMessageToClient;
		ArrayList<BusinessCustomer> businessCustomersList = new ArrayList<>();
		String companyName = ((HrManager)message.getObject()).getCompany().getCompanyName();
		Company company;
		ResultSet rs;
		rs= Query.getRowsFromTableInDB("businesscustomer", "companyName= '"+companyName+"' AND (statusInSystem= 'PENDING_APPROVAL')");
		try {
			while(rs.next()) {
				company = LoginQueries.getCompany(rs.getString(12));
				businessCustomersList.add( new BusinessCustomer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),company,(BudgetType.valueOf(rs.getString(13))),
						rs.getInt(15)));
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		returnMessageToClient = new Message (Task.DISPLAY_BUSINESS_CUSTOMERS_INTO_TABLE,Answer.SUCCEED,businessCustomersList);
		return returnMessageToClient;
		
	}
	
	/**
	 * This method returns a list of companies that are not approved yet.
	 * @param message
	 * @return
	 */
	public static Message getPendingApprovalCompaniesFromDb(Message message) {
		Message returnMessageToClient;
		ArrayList<Company> unConfirmedCompanies = new ArrayList<>();
		ResultSet rs;
		rs=Query.getRowsFromTableInDB("company", "companyStatusInSystem= 'PENDING_APPROVAL'");
		try {
			while(rs.next()) {
				unConfirmedCompanies.add( new Company(rs.getString(1),ConfirmationStatus.valueOf(rs.getString(2)),rs.getString(3),rs.getString(4),rs.getInt(5)));
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Message(Task.DISPLAY_UNCONFIRMED_COMPANIES,Answer.SUCCEED,unConfirmedCompanies);
	}
	
	/**
	 * This method is to confirm company , we change the status of the company to CONFIRMED.
	 * @param message
	 */
	public static void confirmCompany(Message message) {
		Company company = (Company) message.getObject();
		String companyName = company.getCompanyName();
		int companyCode = company.getcompanyCode();
		Query.updateOneColumnForTableInDbByPrimaryKey("company", "companyStatusInSystem= 'CONFIRMED'","companyName='"+companyName+"'");
		
	}
	
	/**
	 * Before removing a company , we set the change the hrManager of the company to the same status
	 * here were before registering the company, so the hrManager won't be deleted from DB
	 * after denying the registration , he can try and register later.
	 * @param message
	 */
	public static void denyCompany(Message message) {
		Company company = (Company) message.getObject();
		String hrManagerId="";
		ResultSet rs=Query.getColumnWithConditionFromTableInDB("hrmanager", "userID", "companyName='"+company.getCompanyName()+"'");
		try {
		if(rs.next())
			hrManagerId=rs.getString(1);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query.updateOneColumnForTableInDbByPrimaryKey("hrmanager", "companyName='Waiting_Registration'", "userID='"+hrManagerId+"'");
		Query.updateOneColumnForTableInDbByPrimaryKey("hrmanager", "businessW4cCodeNumber='0'", "userID='"+hrManagerId+"'" );
		Query.deleteRowFromTableInDbByPrimaryKey("company", "companyName='"+company.getCompanyName()+"'");
	}
	

}
