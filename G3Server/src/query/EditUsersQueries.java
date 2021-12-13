package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
import users.PositionType;
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
	
	
	public static Message getCustomersListFromDb() {
		Message returnMessageToClient;
		ArrayList<User> customersList = new ArrayList<>();
		Company company;
		ResultSet rs;
		
		rs = Query.getTableFromDB("customer");
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
		
		rs= Query.getTableFromDB("businesscustomer");
		try {
			while(rs.next()) {
				company = LoginQueries.getCompany(rs.getString(12));
				customersList.add( new BusinessCustomer(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),company,(BudgetType.valueOf(rs.getString(13))),
						(PositionType.valueOf(rs.getString(14))),rs.getInt(15)));
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rs = Query.getTableFromDB("hrmanager");
		try {
			while(rs.next()) {
			company = LoginQueries.getCompany(rs.getString(12));
			customersList.add(new HrManager(rs.getString(1),(ConfirmationStatus.valueOf(rs.getString(2))),rs.getString(3),rs.getString(4),(Branch.valueOf(rs.getString(5))),
						rs.getBoolean(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getDouble(11),company, 
						(BudgetType.valueOf(rs.getString(13))),(PositionType.valueOf(rs.getString(14))),rs.getInt(15)));
			}
			rs.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		returnMessageToClient = new Message (Task.DISPLAY_CUSTOMERS_INTO_TABLE,Answer.SUCCEED,customersList);
		return returnMessageToClient;
		
	}

}
