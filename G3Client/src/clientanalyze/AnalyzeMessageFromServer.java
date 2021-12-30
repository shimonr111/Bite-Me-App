package clientanalyze;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import communication.Answer;
import communication.Message;
import communication.Task;
import controllers_gui.AbstractBiteMeController;
import controllers_gui.BusinessCustomerConfirmationScreenController;
import controllers_gui.BusinessCustomerRegistartionController;
import controllers_gui.CompanyRegistartionManagementScreenController;
import controllers_gui.EditCustomerInformationScreenController;
import controllers_gui.OrderChooseItemsScreenController;
import controllers_gui.OrderChooseResturantInOrderScreenController;
import controllers_gui.SupplierRegistrationScreenController;
import controllers_gui.SupplierWorkerManageMenuController;
import controllers_gui.SupplierWorkerManageOrders;
import controllers_gui.SupplierWorkerRegistrationScreenController;
import controllers_gui.ViewSystemReportsScreenController;
import controllers_gui.WatchOrderHistoryScreenController;
import controllers_gui.UsersRegistrationScreenController;
import controllers_gui.ViewBMQuarterlyReportsScreenController;
import controllers_gui.ViewQuarterlyReportsScreenController;
import controllers_gui.ViewReceiptsSummaryController;
import controllers_gui.ViewSystemReportsScreenCEOController;
import orders.Item;
import orders.Order;
import users.BusinessCustomer;
import users.Company;
import users.Customer;
import users.Supplier;
import users.User;
import util.OrderForView;
import util.SupplierByReport;
import users.UserForRegistration;


/**
 * @author Lior, Guzovsky.
 * @author Mousa, Srour.
 * @author Alexander, Martinov
 *
 * Class description:
 * This is a class which is a Wrapper for handeling
 * all messages from the server.
 * @version 21/12/2021
 */
public class AnalyzeMessageFromServer {
	/**
	 * Class members description:
	 */
	
	/**
	 * listener arrays for notifying relevant client gui classes of changes 
	 */
	private static final List<AnalyzeClientListener> clientListeners = new ArrayList<AnalyzeClientListener>();
	
	
	/**
	 * This is a function which analyzes all the messages from the server and than
	 * does logic accordingly.
	 * 
	 * @param message
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void analyzeMessageFromServer(Object message) throws IOException {
		if (!(message instanceof Message)) {
			return;
		} else {
			Message recivedMessageFromServer = (Message) message;
			Task recivedTaskFromServer = recivedMessageFromServer.getTask();
			Answer recievedAnswerFromServer = recivedMessageFromServer.getAnswer();
			switch (recivedTaskFromServer) {
			//changed controller method calls into listener notifications
			case CONFIRM_IP:
				switch (recievedAnswerFromServer) {
				case SUCCEED:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) { 
						listener.clientIpConfirmed();
					}
					break;
				case ERROR_FAILED:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientIpNotConfirmed();
					}
					break;
				default:
					break;
				}
				break;
			case CREATE_USER_PORTAL:
				AbstractBiteMeController.connectedUser = ((User)recivedMessageFromServer.getObject()); // add the received user into the connected users list.
				switch (recievedAnswerFromServer) {
				case CREATE_USER_PORTAL_FOR_CUSTOMER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotCustomerLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_HR_MANAGER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotHRManagerLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_BUSINESS_CUSTOMER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotBusinessCustomerLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_CEO_BITE_ME:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotCEOLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_SUPPLIER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotSupplierLogin();
					}
					break;
				case CREATE_USER_PORTAL_FOR_BRANCH_MANAGER:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientGotBranchManagerLogin();
					}
				default:
					break;
				}
				break;
			case PRINT_ERROR_TO_SCREEN:
				switch (recievedAnswerFromServer) {
				case ERROR_USER_NOT_FOUND:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientNotFoundInSystem();
					}
					break;
				case ERROR_ALREADY_LOGGED_IN:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientIsLogedIn();
					}
					break;
				case ERROR_USER_NOT_CONFIRMED:
					/*notify all the listeners that 
					needs to know about this action*/
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientIsNotConfirmed();
					}
					break;
				case USER_ID_ALREADY_EXIST:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientUserIdExist();
					}
					break;
				case USER_NAME_ALREADY_EXIST:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientUserNameExist();;
					}
					break;
				case USER_ID_ALREADY_EXIST_BUSINESS:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientBusinessUserIdExist();;
					}
					break;
				case USER_NAME_ALREADY_EXIST_BUSINESS:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientBusinessUserNameExist();;
					}
					break;
				case USER_ID_ALREADY_EXIST_SUPPLIER:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientSupplierIdExist();
					}
					break;
				case USER_NAME_ALREADY_EXIST_SUPPLIER:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientSupplierUserNameExist();
					}
					break;
				case COMPANY_NAME_ALREADY_EXIST:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientCompanyRegistrationFailed("Company NAME already exist in DB");
					}
					break;
				case COMPANY_CODE_ALREADY_EXIST:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientCompanyRegistrationFailed("Company CODE already exist in DB");
					}
					break;
				default:
					break;
				}
			case DISPLAY_MESSAGE_TO_CLIENT:
				switch (recievedAnswerFromServer) {
				case CUSTOMER_REGISTRATION_SUCCEED:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientPrivateCustomerRegistrationSucceed();
					}
					break;
				case BUSINESS_CUSTOMER_REGISTRATION_SUCCEED:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientBusinessCustomerRegistrationSucceed();
					}
					break;
				case SUPPLIER_REGISTRATION_SUCCEED:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientSupplierRegistrationSucceed();
					}
					break;
				case COMPANY_REGISTRATION_SUCCEED:
					for (AnalyzeClientListener listener : clientListeners) {
						listener.clientCompanyRegistrationSucceed();
					}
					break;
				default:
					break;
				}
				break;
			case DISPLAY_COMPANIES_INTO_COMBOBOX:
				BusinessCustomerRegistartionController.companies=(ArrayList<String>)recivedMessageFromServer.getObject();
				break;
			case DISPLAY_CUSTOMERS_INTO_TABLE:
				EditCustomerInformationScreenController.customersList=(ArrayList<User>)recivedMessageFromServer.getObject();
				break;
			case DISPLAY_BUSINESS_CUSTOMERS_INTO_TABLE:
				BusinessCustomerConfirmationScreenController.businessCustomersWaitingForConfirmation=(ArrayList<BusinessCustomer>)recivedMessageFromServer.getObject();
				break;
			case DISPLAY_UNCONFIRMED_COMPANIES:
				CompanyRegistartionManagementScreenController.companiesWaitingForConfirmation = (ArrayList<Company>)recivedMessageFromServer.getObject();
				break;
			case DISPLAY_USERS_ON_REGISTRATION_LIST:
				UsersRegistrationScreenController.usersList=(ArrayList<UserForRegistration>)recivedMessageFromServer.getObject();
				break;
			case DISPLAY_SUPPLIERS_INTO_TABLE:
				SupplierRegistrationScreenController.suppliersWaitingForConfirmation = (ArrayList<Supplier>)recivedMessageFromServer.getObject();
				break;
			case DISPLAY_ORDERS_INTO_TABLE:
				WatchOrderHistoryScreenController.ordersForCustomer = (ArrayList<OrderForView>)recivedMessageFromServer.getObject();
				break;
			case GET_RESTAURANTS_FOR_ORDER:
				switch(recievedAnswerFromServer) {
				case RESTAURANTS_NOT_EXIST_IN_THIS_BRANCH:
					OrderChooseResturantInOrderScreenController.suppliersList = null;
					break;
				case RESTAURANTS_EXIST_FOR_THIS_BRANCH:
					OrderChooseResturantInOrderScreenController.suppliersList=(HashMap<String,String>)recivedMessageFromServer.getObject();
					break;
				default:
					break;	
				}
				break;
			case GET_RESTAURANTS_FOR_SUPPLIER_REGISTRATION:
				switch(recievedAnswerFromServer) {
				case RESTAURANTS_NOT_EXIST_IN_THIS_BRANCH:
					SupplierWorkerRegistrationScreenController.suppliersList = null;
					break;
				case RESTAURANTS_EXIST_FOR_THIS_BRANCH:
					SupplierWorkerRegistrationScreenController.suppliersList=(HashMap<String,String>)recivedMessageFromServer.getObject();
					break;
				default:
					break;	
				}
			case GET_SUPPLIER_FOR_SUPPLIER_WORKER_REGISTRATION:
				switch(recievedAnswerFromServer) {
				case SUPPLIER_FOUND_IN_DB:
					SupplierWorkerRegistrationScreenController.supplier=(Supplier)recivedMessageFromServer.getObject();
					break;
				default:
				break;
				}
				
				break;
			case GET_ITEMS_FOR_ORDER_MENU:
				switch(recievedAnswerFromServer) {
				case SUPPLIER_HAS_NO_MENU:
					OrderChooseItemsScreenController.itemListOfMenuFromDB = null;
					break;
				case SUPPLIER_MENU_FOUND:
					OrderChooseItemsScreenController.itemListOfMenuFromDB = (ArrayList<Item>)recivedMessageFromServer.getObject();
					break;
				default:
					break;
				}
				break;
				
			case GET_ITEMS_FOR_MANAGE_MENU:
				switch(recievedAnswerFromServer) {
				case SUPPLIER_HAS_NO_MENU:
					SupplierWorkerManageMenuController.itemListOfMenuFromDB = null;
					break;
				case SUPPLIER_MENU_FOUND:
					SupplierWorkerManageMenuController.itemListOfMenuFromDB = (ArrayList<Item>)recivedMessageFromServer.getObject();
					break;
				default:
					break;
				}
				break;
			
			case GET_SYSTEM_REPORTS:
				switch(recievedAnswerFromServer) {
				case SENT_REPORT_SUPPLIERS_LIST:
					ViewSystemReportsScreenController.suppliers=(SupplierByReport[])recivedMessageFromServer.getObject();
					ViewSystemReportsScreenCEOController.suppliers=(SupplierByReport[])recivedMessageFromServer.getObject();
					ViewReceiptsSummaryController.suppliers=(SupplierByReport[])recivedMessageFromServer.getObject();
					break;
				case SENT_REPORT_QUARTERLY_LIST:
					ViewQuarterlyReportsScreenController.suppliers=(SupplierByReport[][])recivedMessageFromServer.getObject();
					ViewSystemReportsScreenController.suppliers=((SupplierByReport[][])recivedMessageFromServer.getObject())[3];
					break;
					default:
						break;
				}
				break;
			case UPLOAD_PDF:
				switch(recievedAnswerFromServer) {
				case REPORT_UPLOADED:
					break;
					default:
						break;
				}
				break;
			case GET_PDF_LIST:
				switch(recievedAnswerFromServer) {
				case PDF_LIST_SENT:
					ViewBMQuarterlyReportsScreenController.pdfList=(String[][])recivedMessageFromServer.getObject();
					break;
					default:
						break;
				}
				break;
			case GET_PDF_FILE:
				switch(recievedAnswerFromServer) {
				case PDF_FILE_SENT:
					ViewBMQuarterlyReportsScreenController.pdfFile=(byte[])recivedMessageFromServer.getObject();
					break;
					default:
						break;
				}
				break;
			case SUPPLIER_WORKER_GET_ALL_RELEVANT_ORDERS:
				switch(recievedAnswerFromServer) {
				case SUPPLIER_WORKER_NO_ORDERS_FOUND:
					SupplierWorkerManageOrders.orderListFromDB = new ArrayList<Order>();
					break;
				case SUPPLIER_WORKER_ORDERS_FOUND:
					SupplierWorkerManageOrders.orderListFromDB = (ArrayList<Order>)recivedMessageFromServer.getObject();
					break;
				default:
					break;
				}
				break;
			case ORDER_FINISHED:
				switch(recievedAnswerFromServer) {
				case ORDER_SUCCEEDED_WRITING_INTO_DB:
				Order order = ((Order)(recivedMessageFromServer.getObject()));
					for (AnalyzeClientListener listener : clientListeners) {
						listener.addOrderToSupplierTable(order);
					}
					break;
				default:
					break;
				}
				break;
			case GET_USER_BALANCE:
				((Customer)AbstractBiteMeController.connectedUser).setBalance((Double.parseDouble((String)recivedMessageFromServer.getObject())));
				break;
			case GET_USER_EMAIL:
				SupplierWorkerManageOrders.approvedCustomerEmail = (String)recivedMessageFromServer.getObject();
				break;
				
			default:
				break;
			}
		}

	}
	
	/**
	 * add client listener to the array.
	 * 
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void addClientListener(AnalyzeClientListener listener) {
		clientListeners.add(listener);
	}
	/**
	 * remove client listener to the array.
	 * 
	 * @param listener every listener is implemented in the class that's "listening"
	 */
	public static void removeClientListener(AnalyzeClientListener listener) {
		clientListeners.remove(listener);
	}
}
