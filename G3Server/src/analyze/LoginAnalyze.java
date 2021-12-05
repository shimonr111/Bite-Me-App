package analyze;

import java.sql.Connection;


import communication.*;
import query.LoginQueries;
import users.BusinessCustomer;
import users.CeoBiteMe;
import users.Customer;
import users.HrManager;
import users.Supplier;
import users.User;

public class LoginAnalyze {

	/**
	 * 
	 * @author Mousa, Srour
	 * Class description:
	 * This class will analyze the login feature from the side of the server
	 * @version 05/12/2021
	 */
	
	/**
	 * this method checks the type of the user, gets the data
	 * from the DB  and calls the setMessageAccordingly 
	 * method according to the type .
	 * @param userType
	 * @param userId
	 * @param con
	 * @param message
	 * @return message that contains the correct Object,Task,Answer.
	 */
	public static Message getUserByType(String userType, String userId, Connection con, Message message) {
		switch (userType) {
		case "customer":
			Customer customerResult = null;
			customerResult = LoginQueries.getCustomer(userId, con);
			message=setMessageAccordingly(customerResult,message);
			break;
		case "ceo":
			CeoBiteMe ceoResult = null;
			ceoResult = LoginQueries.getCeo(userId,con);
			message=setMessageAccordingly(ceoResult,message);
			break;
		case "hrmanager":
			HrManager hrManagerResult=null;
			hrManagerResult=LoginQueries.getHrManager(userId, con);
			message=setMessageAccordingly(hrManagerResult,message);
			break;
		case "businesscustomer":
			BusinessCustomer businessCustomerResult = null;
			businessCustomerResult = LoginQueries.getBusinessCustomer(userId, con);
			message=setMessageAccordingly(businessCustomerResult,message);
			break;
		case "supplier":
			Supplier supplierResult = null;
			supplierResult = LoginQueries.getSupplier(userId, con);
			message=setMessageAccordingly(supplierResult,message);
			break;
		default:
			break;

		}
		return message;
	}
	
	/**
	 * This method gets an user object and sets the correct
	 * Task and answer according to the info.
	 * @param user
	 * @param message
	 * @return message with the correct Task and Answer.
	 */
	public static Message setMessageAccordingly(User user, Message message) {
		if (user == null) {
			message.setAnswer(Answer.ERROR_USER_NOT_FOUND);
			message.setTask(Task.PRINT_ERROR_TO_SCREEN);
		} else if (user.isLoggedIn()) {
			message.setAnswer(Answer.ERROR_ALREADY_LOGGED_IN);
			message.setTask(Task.PRINT_ERROR_TO_SCREEN);
		} else if (!user.isConfirmedInSystem()) {
			message.setAnswer(Answer.ERROR_USER_NOT_CONFIRMED);
			message.setTask(Task.PRINT_ERROR_TO_SCREEN);
		} else {
			message.setObject(user);
			message.setTask(Task.CREATE_USER_PORTAL);
			if (user instanceof HrManager)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_HR_MANAGER);
			else if (user instanceof BusinessCustomer)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER);
			else if (user instanceof Customer)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_CUSTOMER);
			else if (user instanceof CeoBiteMe)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_CEO_BITE_ME);

			else if (user instanceof Supplier)
				message.setAnswer(Answer.CREATE_USER_PORTAL_FOR_SUPPLIER);
		}
		return message;
	}
}
