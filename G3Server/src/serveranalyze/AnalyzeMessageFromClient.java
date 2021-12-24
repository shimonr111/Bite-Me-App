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
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 *  Class description:
 *  This is a class which is a Wrapper for handling
 *  all messages from the client.
 * @version 21/12/2021
 */
public class AnalyzeMessageFromClient {
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
				ClientDoc diconnectedClient = new ClientDoc(client.getInetAddress().getHostAddress(),client.getInetAddress().getHostName(),"Disconnected");
				for(int i=0;i<BiteMeServerUI.clients.size();i++) {
					if(BiteMeServerUI.clients.get(i).equals(diconnectedClient)) {
						BiteMeServerUI.clients.remove(i);
						BiteMeServerUI.clients.add(diconnectedClient);
					}
				}
				break;
			 case LOGIN:
				recivedMessageFromClient = LoginQueries.createLoginMessageForServer(recivedMessageFromClient);
				break;
			 case LOGOUT:
				 LoginQueries.logOutUser(recivedMessageFromClient);
				 break;
			 case REGISTER_PRIVATE_CUSTOMER:
				recivedMessageFromClient= RegistrationQueries.getPrivateCustomerRegistration((Message)message);
				break;
			 case GET_COMPANIES_FROM_DB:
				 recivedMessageFromClient = RegistrationQueries.getCompaniesFromDB((Message)message);
				 break;
			 case REGISTER_BUSINESS_CUSTOMER:
				 recivedMessageFromClient = RegistrationQueries.getBusinessCustomerRegistration((Message)message);
				 break;
			 case REGISTER_SUPPLIER:
				 recivedMessageFromClient = RegistrationQueries.getSupplierRegistration((Message) message);
				 break;
			 case REGISTER_COMPANY:
				 recivedMessageFromClient = RegistrationQueries.getCompanyRegistration((Message) message);
				 break;
			 case GET_CUSTOMERS_FROM_DB:
				  recivedMessageFromClient = EditUsersQueries.getCustomersListFromDb((Message) message);
				  break;
			 case UPDATE_CUSTOMER_STATUS:
				 EditUsersQueries.setCustomerNewStatus((Message)message);
				 break;
			 case GET_RESTAURANTS_FOR_ORDER:
				 recivedMessageFromClient = OrderQueries.createRestaurantsForCustomer((Message)message);
				 break;
			 case GET_RESTAURANTS_FOR_SUPPLIER_REGISTRATION:
				 recivedMessageFromClient = RegistrationQueries.createRestaurantsForSupplierRegistration((Message)message);
				 break;
			 case GET_SUPPLIER_FOR_SUPPLIER_WORKER_REGISTRATION:
				 recivedMessageFromClient = RegistrationQueries.getSupplierFromDb((Message)message);
				 break;
			 case REMOVE_USER_FROM_DB:
				 EditUsersQueries.removeUserFromDB((Message)message);
				 break;
			 case GET_ITEMS_FOR_ORDER_MENU:
				 recivedMessageFromClient = OrderQueries.createMenuForSpecificSupplier((Message)message);
				 break;
			 case GET_ITEMS_FOR_MANAGE_MENU:
				 recivedMessageFromClient = OrderQueries.createMenuForSpecificSupplier((Message)message);
				 break;
			 case GET_BUSINESS_CUSTOMERS_FOR_CONFIRMATION:
				  recivedMessageFromClient = EditUsersQueries.getBusinessCustomersListFromDb((Message) message);
				 break;
			 case GET_COMPANIES_FOR_CONFIRMATION:
				 recivedMessageFromClient = EditUsersQueries.getPendingApprovalCompaniesFromDb((Message)message);
				 break;
			 case CONFIRM_COMPANY:
				 EditUsersQueries.confirmCompany((Message)message);
				 break;
			 case DENY_COMPANY:
				 EditUsersQueries.denyCompany((Message) message);
				 break;
			 case GET_SYSTEM_REPORTS:
				 recivedMessageFromClient = ReportsController.getSuppliersByBranch((Message) message);
				 break;
			 case GET_USERS_FOR_REGISTRATION:
				 recivedMessageFromClient = RegistrationQueries.getUsersFromRegistrationTable((Message) message);
				 break;
			 case GET_SUPPLIERS_FOR_REGISTRATION:
				 recivedMessageFromClient = RegistrationQueries.getSuppliersWaitingForRegistration((Message) message);
				 break;
			 case CONFIRM_SUPPLIER:
				 RegistrationQueries.getSupplierConfirmation((Message)message);
				 break;
			 case ORDER_FINISHED:
				 recivedMessageFromClient = OrderQueries.addOrderToDbAndUpdateSupplier((Message)message);
				 break;
			 case SUPPLIER_WORKER_GET_ALL_RELEVANT_ORDERS:
				 recivedMessageFromClient = OrderQueries.getOrdersFromOrderTableForSpecificRestaurant((Message) message);
				 break;
			 case MANAGE_MENU_FINISHED:
				 recivedMessageFromClient = OrderQueries.updateMenuOnDb((Message)message);
				 break;
			 case MANAGE_ORDER_FINISHED:
				 recivedMessageFromClient = OrderQueries.updateOrdersStatusOnDb((Message)message);
				 break;
			default:
				break;
			}
			return recivedMessageFromClient;
		}
	}

	/**
	 * add server listener to the array
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addServerListener(AnalyzeServerListener listener) {
		serverListeners.add(listener);
	}
	

}
