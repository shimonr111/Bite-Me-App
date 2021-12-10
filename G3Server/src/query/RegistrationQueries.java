package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import communication.Answer;
import communication.Message;
import communication.Task;
import users.CreditCard;
import users.Customer;
import users.Login;

/**
 * 
 * @author Mousa, Srour
 * Class description:
 * This class will contain all the queries to DB and logi
 * that relate to Registration phase.
 * @version 11/12/2021
 */
public class RegistrationQueries {

	/**
	 * In this method we check if all the details are not exist before in db ( userID , username , creditcard)
	 * in case userID or username already exist we return a relevant message and we do not insert new rows into tables
	 * in case the credit card number already exist , we do create rows and inesrt them but we dont create a new row for the credit card.
	 * @param message
	 * @return
	 */
	public static Message getPrivateCustomerRegistration(Message message) {
		Message messageBackToClient;
		  @SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) message.getObject();
		Customer customer = (Customer) list.get(0);
		Login login = (Login) list.get(1);
		CreditCard creditCard = (CreditCard) list.get(2);
		if(checkIfCustomerIdExist(customer)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_ID_ALREADY_EXIST,null);
			return messageBackToClient;
		}
		if(checkIfLoginUserNameExist(login)) {
			messageBackToClient = new Message(Task.PRINT_ERROR_TO_SCREEN,Answer.USER_NAME_ALREADY_EXIST,null);
			return messageBackToClient;
		}
		if(!checkIfCreditCardNumberExist(creditCard))
			Query.insertOneRowIntoCreditCardTable(creditCard);
		Query.insertOneRowIntoLoginTable(login.getUserName(), login.getPassword(), customer.getUserId(), "customer");
		Query.insertOneRowIntoCustomerTable(customer);
		messageBackToClient= new Message(Task.DISPLAY_MESSAGE_TO_CLIENT,Answer.REGISTRATION_SUCCEED,null);
		return messageBackToClient;
		
	}
	
	/**
	 * This method checks if the customerID exist in the customer table.
	 * @param customer
	 * @return
	 */
	private static boolean checkIfCustomerIdExist(Customer customer) {	
		ResultSet rs = Query.getColumnFromTableInDB("customer", "userID");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(customer.getUserId())) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the username already exists on the login table.
	 * @param login
	 * @return
	 */
	private static boolean checkIfLoginUserNameExist(Login login) {
		ResultSet rs = Query.getColumnFromTableInDB("login", "username");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(login.getUserName())) {
					return true;
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This method checks if the credit card already exist in db.
	 * @param creditCard
	 * @return
	 */
	private static boolean checkIfCreditCardNumberExist(CreditCard creditCard) {
		ResultSet rs = Query.getColumnFromTableInDB("creditcard", "creditCardNumber");
		try {
			while(rs.next()) {
				if(rs.getString(1).equals(creditCard.getCreditCardNumber())) {
					return true;
					
				}
			}
			rs.close();
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
