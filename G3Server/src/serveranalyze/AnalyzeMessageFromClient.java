package serveranalyze;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bitemeserver.BiteMeServerUI;
import communication.Answer;
import communication.Message;
import communication.Task;
import guiserver.ClientDoc;
import ocsf.server.ConnectionToClient;
import query.EditUsersQueries;
import query.LoginQueries;
import query.OrderQueries;
import query.RegistrationQueries;
import reports.ReportsController;

/**
 *  Class description:
 *  This is a class which is a Wrapper for handling
 *  all messages from the client.
 * 
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 * @version 21/12/2021
 */
public class AnalyzeMessageFromClient{
	
	/**
	 * Class members description:
	 */
	
	/**
	 * listener arrays for notifying relevant server gui classes of changes 
	 */
	private static final List<AnalyzeServerListener> serverListeners = new ArrayList<AnalyzeServerListener>();
	
	/**
	 * This is a function which analyzes all the messages from the client and than
	 * does logic accordingly.
	 * 
	 * @param message
	 * @param client
	 * @throws SQLException 
	 * @throws IOException
	 * @return message answer to the client side
	 */
	public static Message analyzeMessageFromClient(Object message, ConnectionToClient client) throws SQLException{
		if (!(message instanceof Message)) {
			return null;
		} else {
			Message recivedMessageFromClient = (Message) message;
			Task recivedTaskFromClient = recivedMessageFromClient.getTask();
			Answer recievedAnswerFromClient = recivedMessageFromClient.getAnswer();
			/**
			 * Switch case for getting Message from client side.
			 */
			switch (recivedTaskFromClient) {
			case CONFIRM_IP:
				/**
				 * The ip is confirmed
				 */
				boolean isExist=false;
				ClientDoc connectedClient = new ClientDoc(client.getInetAddress().getHostAddress(),client.getInetAddress().getHostName(),"Connected");
				for(int i=0;i<BiteMeServerUI.clients.size();i++) {
					if(BiteMeServerUI.clients.get(i).equals(connectedClient)) {
						BiteMeServerUI.clients.remove(i);
						BiteMeServerUI.clients.add(connectedClient);
						isExist=true;
					}
				}
				if(!isExist)
					BiteMeServerUI.clients.add(connectedClient);
				recivedMessageFromClient.setAnswer(Answer.SUCCEED);
				break;
			case CLIENT_DICONNECT:
				/**
				 * The client disconnect
				 */
				ClientDoc diconnectedClient = new ClientDoc(client.getInetAddress().getHostAddress(),client.getInetAddress().getHostName(),"Disconnected");
				for(int i=0;i<BiteMeServerUI.clients.size();i++) {
					if(BiteMeServerUI.clients.get(i).equals(diconnectedClient)) {
						BiteMeServerUI.clients.remove(i);
						BiteMeServerUI.clients.add(diconnectedClient);
					}
				}
				break;
			 case LOGIN:
				 /**
				 * The user login
			     */
				recivedMessageFromClient = LoginQueries.createLoginMessageForServer(recivedMessageFromClient);
				break;
			 case LOGOUT:
                 /**
                  * The user log out
                  */
				 LoginQueries.logOutUser(recivedMessageFromClient);
				 break;
			 case REGISTER_PRIVATE_CUSTOMER:
				 /**
				  * We register the user as private customer
				  */
				recivedMessageFromClient= RegistrationQueries.getPrivateCustomerRegistration((Message)message);
				break;
			 case GET_COMPANIES_FROM_DB:
				 /**
				  * We get companies from the db
				  */
				 recivedMessageFromClient = RegistrationQueries.getCompaniesFromDB((Message)message);
				 break;
			 case REGISTER_BUSINESS_CUSTOMER:
				 /**
				  * We register user as business customer
				  */
				 recivedMessageFromClient = RegistrationQueries.getBusinessCustomerRegistration((Message)message);
				 break;
			 case REGISTER_SUPPLIER:
				 /**
				  * We register user as supplier
				  */
				 recivedMessageFromClient = RegistrationQueries.getSupplierRegistration((Message) message);
				 break;
			 case REGISTER_COMPANY:
                 /**
                  * We register company
                  */
				 recivedMessageFromClient = RegistrationQueries.getCompanyRegistration((Message) message);
				 break;
			 case GET_CUSTOMERS_FROM_DB:
				 /**
				  * We get customer from DB
				  */
				  recivedMessageFromClient = EditUsersQueries.getCustomersListFromDb((Message) message);
				  break;
			 case UPDATE_CUSTOMER_STATUS:
				 /**
				  * We update the customer status
				  */
				 EditUsersQueries.setCustomerNewStatus((Message)message);
				 break;
			 case GET_RESTAURANTS_FOR_ORDER:
				 /**
				  * We get the specific restaurant for the order
				  */
				 recivedMessageFromClient = OrderQueries.createRestaurantsForCustomer((Message)message);
				 break;
			 case GET_RESTAURANTS_FOR_SUPPLIER_REGISTRATION:
				 /**
				  * We get the restaurants for supplier registration
				  */
				 recivedMessageFromClient = RegistrationQueries.createRestaurantsForSupplierRegistration((Message)message);
				 break;
			 case GET_SUPPLIER_FOR_SUPPLIER_WORKER_REGISTRATION:
				 /**
				  * We get the supplier for supplier worker registration
				  */
				 recivedMessageFromClient = RegistrationQueries.getSupplierFromDb((Message)message);
				 break;
			 case REMOVE_USER_FROM_DB:
				 /**
				  * We remove user from DB
				  */
				 EditUsersQueries.removeUserFromDB((Message)message);
				 break;
			 case GET_ITEMS_FOR_ORDER_MENU:
				 /**
				  * We get items for the order from the DB
				  */
				 recivedMessageFromClient = OrderQueries.createMenuForSpecificSupplier((Message)message);
				 break;
			 case GET_ITEMS_FOR_MANAGE_MENU:
				 /**
				  * We get items for the manage menu from the DB
				  */
				 recivedMessageFromClient = OrderQueries.createMenuForSpecificSupplier((Message)message);
				 break;
			 case GET_BUSINESS_CUSTOMERS_FOR_CONFIRMATION:
				 /**
				  * We get all the business customer that we can confirm
				  */
				  recivedMessageFromClient = EditUsersQueries.getBusinessCustomersListFromDb((Message) message);
				 break;
			 case GET_COMPANIES_FOR_CONFIRMATION:
				 /**
				  * We get all the companies that we can confirm
				  */
				 recivedMessageFromClient = EditUsersQueries.getPendingApprovalCompaniesFromDb((Message)message);
				 break;
			 case CONFIRM_COMPANY:
				 /**
				  * We confirm the company
				  */
				 EditUsersQueries.confirmCompany((Message)message);
				 break;
			 case DENY_COMPANY:
				 /**
				  * We deny the company
				  */
				 EditUsersQueries.denyCompany((Message) message);
				 break;
			 case GET_SYSTEM_REPORTS:
				 /**
				  * We get the system reports from the DB
				  */
				 recivedMessageFromClient = ReportsController.getSuppliersByBranch((Message) message);
				 break;
			 case GET_USERS_FOR_REGISTRATION:
				 /**
				  * We get users that we can register
				  */
				 recivedMessageFromClient = RegistrationQueries.getUsersFromRegistrationTable((Message) message);
				 break;
			 case GET_SUPPLIERS_FOR_REGISTRATION:
				 /**
				  * We get supplier that we can register
				  */
				 recivedMessageFromClient = RegistrationQueries.getSuppliersWaitingForRegistration((Message) message);
				 break;
			 case CONFIRM_SUPPLIER:
				 /**
				  * We confirm the supplier
				  */
				 RegistrationQueries.getSupplierConfirmation((Message)message);
				 break;
			 case ORDER_FINISHED:
				 /**
				  * The user finish the order
				  */
				 recivedMessageFromClient = OrderQueries.addOrderToDbAndUpdateSupplier((Message)message);
				 break;
			 case SUPPLIER_WORKER_GET_ALL_RELEVANT_ORDERS:
				 /**
				  * The supplier get all the orders that have been made
				  */
				 recivedMessageFromClient = OrderQueries.getOrdersFromOrderTableForSpecificRestaurant((Message) message);
				 break;
			 case MANAGE_MENU_FINISHED:
				 /**
				  * The supplier finished to update his menu
				  */
				 recivedMessageFromClient = OrderQueries.updateMenuOnDb((Message)message);
				 break;
			 case MANAGE_ORDER_FINISHED:
				 /**
				  * The supplier finished to manage the orders
				  */
				 recivedMessageFromClient = OrderQueries.updateOrdersStatusOnDb((Message)message);
				 break;
			 case GET_ORDERS_FOR_USER:
				 /**
				  * We present the orders history for the user
				  */
				 recivedMessageFromClient = OrderQueries.getOrdersForUser((Message)message);
				 break;
			 case CUSTOMER_UPDATE_DB_AFTER_PAYMENT:
				 /**
				  * We update the DB after user paid for his order
				  */
				 recivedMessageFromClient = OrderQueries.updatePaymentBalanceAndBudgetBalance((Message)message);
				 break;
			 case UPLOAD_PDF:
				 /**
				  * We upload the pdf file
				  */
				 recivedMessageFromClient = ReportsController.uploadQuarterlyReport((Message)message);
				 break;
			 case GET_PDF_LIST:
				 /**
				  * We get the pdf files list
				  */
				 recivedMessageFromClient = ReportsController.getUploadedList((Message)message);
				 break;
			 case GET_PDF_FILE:
				 /**
				  * We get specific pdf file
				  */
				 recivedMessageFromClient = ReportsController.getPdfFile((Message)message);
				 break;
			 case GET_USER_BALANCE:
				 /**
				  * We get user balance to present it in the Payment screen
				  */
				 recivedMessageFromClient = OrderQueries.getBalanceForUser((Message) message);
				 break;
			 case GET_USER_EMAIL:
				 /**
				  * We get the mail of the user 
				  */
				 recivedMessageFromClient = OrderQueries.getCustomerEmailForSimulation((Message) message);
				 break;
			 case SET_ACTUAL_DATE_AND_BALANCE:
				 /**
				  * We set the updated date and balance in the DB
				  */
				recivedMessageFromClient = OrderQueries.updateActualTimeAndCheckBalance((Message)message);
				 break;
			 case CONFIRM_BUSINESS_CUSTOMER:
				 /**
				  * We confirm the business customer
				  */
				 recivedMessageFromClient = RegistrationQueries.businessCustomerCompleteRegistration((Message) message);
				 break;
			 case SUPPLIER_RESTORE_BALANCE_AND_BUDGET_BALANCE:
				 /**
				  * We restore the balance and budget balance of the user in the DB
				  */
				 OrderQueries.reverseBudgetBalanceAndBalanceForUserInUnApprovedOrder((Message) message);
				 break;
			 case GET_BUSINESS_CUSTOMERS_WITH_MULTI_ORDER:
				 /**
				  * We get the business customers that participants in multi order
				  */
				 recivedMessageFromClient = OrderQueries.getAvailableMultiOrders((Message) message);
				 break;
			 case JOIN_MULTI_GET_NUMBER_OF_PARTICIPANTS:
				 /**
				  * We get the number of participants in multi order
				  */
				 recivedMessageFromClient = OrderQueries.joinMultiGetNumberOfParticipants((Message)message);
				 break;
			 case JOIN_MULTI_FINISH_ORDER:
				 /**
				  * The multi order is finished
				  */
				 OrderQueries.joinMultiFinishedOrder((Message)message);
				 break;
			default:
				break;
			}
			return recivedMessageFromClient;
		}
	}

	/**
	 * Add server listener to the array
	 * 
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addServerListener(AnalyzeServerListener listener) {
		serverListeners.add(listener);
	}
	

}
