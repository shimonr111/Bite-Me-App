package controllers_gui;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import orders.Order;
import orders.PaymentWay;
import users.BusinessCustomer;
import users.Customer;
import util.OrderBusinessBudgetCalculation;

/**
 * Class description:
 * This is a class for 
 * controlling the UI of the Order 
 * payment configuration .
 * @author Lior, Guzovsky.
 * @author Shimon, Rubin.
 * @author Mousa, Srour.
 * 
 * @version 31/12/2021
 */
public class OrderPaymentConfigurationScreenController  extends AbstractBiteMeController implements Initializable{
	
		/**
		 * Class members description:
		 */
	
		/**
		 * The FXMLLoader of the current screen.
		 */
		private static FXMLLoader loader;
		
		/**
		 * A static object of the current class.
		 */
	    private static OrderPaymentConfigurationScreenController orderPaymentConfigurationScreenController;
	    
	    /**
	     * Static Order object for the current order flow.
	     */
	    private static Order order;
	    
	    /**
	     * The amount of money that the customer has to pay.
	     */
	    private double amountLeftToPay;

	    @FXML
	    /**
	     * The finish order button.
	     */
	    private Button finishOrderBtn;

	    @FXML
	    /**
	     * The exit Button.
	     */
	    private Button btnExit;

	    @FXML
	    /**
	     * The Back Button.
	     */
	    private Button btnBack;

	    @FXML
	    /**
	     * The Help Button.
	     */
	    private Button btnHelp;

	    @FXML
	    /**
	     * Account Balance Text Field.
	     */
	    private TextField availableAccountBalanceTextField;

	    @FXML
	    /**
	     * Label of budget amount.
	     */
	    private Label availableBudgetBalanceLabel;

	    @FXML
	    /**
	     * Budget Balance Text Field.
	     */
	    private TextField availableBudgetBalanceTextField;

	    @FXML
	    /**
	     * The Text Field that the user has to enter the amount of money he wants to pay.
	     */
	    private TextField enterAmountTextField;

	    @FXML
	    /**
	     * The total pay text field.
	     */
	    private TextField totalToPayTextField;

	    @FXML
	    /**
	     * The empty text that we use to display messages to the user.
	     */
	    private Text errorText;

	    @FXML
	    /**
	     * The Payment Methods comboBox.
	     */
	    private ComboBox<PaymentWay> paymentMethodCombo;

	    @FXML
	    /**
	     * Add Amount Button.
	     */
	    private Button addAmountBtn;

	    @FXML
	    /**
	     * Remove Amount Button.
	     */
	    private Button removeAmountBtn;

	    @FXML
	    /**
	     * Label of employee budget.
	     */
	    private Label employeeBudgetLabel;

	    @FXML
	    /**
	     * Already Cash Text Field.
	     */
	    private TextField alreadyCashTextField;

	    @FXML
	    /**
	     * Already Credit Card Text Field.
	     */
	    private TextField alreadyCreditCardTextField;

	    @FXML
	    /**
	     * Already Account Balance Text Field.
	     */
	    private TextField alreadyAccountBalanceTextField;

	    @FXML
	    /**
	     * Already Employee Budget Text Field.
	     */
	    private TextField alreadyEmployeeBudgetTextField;

	    /**
	     * This is a function for 
	     * adding amount of money payed 
	     * by the user.
	     * 
	     * @param event - when pressed on the button
	     */
	   @FXML
	   public void getAddAmountBtn(ActionEvent event) {
		   if(paymentMethodCombo.getValue() == null) {
	    		errorText.setText("Choose payment method!");
	    		paymentMethodCombo.setStyle("-fx-border-color: red");

	    	}
		   else if(enterAmountTextField.getText() == "") {
			   errorText.setText("Please enter amount to pay!");
			   enterAmountTextField.setStyle("-fx-border-color: red");
	    	   
		   }
		   else if(amountLeftToPay == 0) {
			   errorText.setText("You have finished paying! , press finish!");
		   }
		   else if(Double.parseDouble(enterAmountTextField.getText()) <= 0.0) {
			   errorText.setText("Amount must be un-negative!");
			   enterAmountTextField.setStyle("-fx-border-color: red");
		   }
		   else if(BigDecimal.valueOf(Double.parseDouble(enterAmountTextField.getText())).scale() > 2){
			   errorText.setText("Amount must not exceed 2 decimal points!");
			   enterAmountTextField.setStyle("-fx-border-color: red");
		   }
		   else {
			   errorText.setText(""); //disable previous error warning
			   PaymentWay paymentWay = paymentMethodCombo.getValue();
			   try {
			   double moneyToPay = Double.parseDouble(enterAmountTextField.getText());
			   
			   if(isPaymentAmountValidForAddAmount(paymentWay)) {
				   amountLeftToPay -= moneyToPay; //update the amount left to pay
				   amountLeftToPay = (double) Math.round(amountLeftToPay*100)/100;
				   totalToPayTextField.setText(OrderBusinessBudgetCalculation.df.format(amountLeftToPay));//set the total price, set format as 2 decimal figures after the dot
			   /*Update all the text fields in the screen according to the users pick in the combo box*/
			   switch(paymentWay) {
			   case CASH:
				   double cash;
				   if(alreadyCashTextField.getText().equals("")){
					   cash = 0.0;
				   }else {
				   cash = Double.parseDouble(alreadyCashTextField.getText());
				   }
				   cash += moneyToPay;
				   alreadyCashTextField.setText(OrderBusinessBudgetCalculation.df.format(cash)); //set format as 2 decimal figures after the dot
				  break;
			   case CREDIT_CARD:
				   double creditCard;
				   if(alreadyCreditCardTextField.getText().equals("")) {
					   creditCard =0.0;
				   }else {
				   creditCard = Double.parseDouble(alreadyCreditCardTextField.getText());
				   }
				   creditCard += moneyToPay;
				   alreadyCreditCardTextField.setText(OrderBusinessBudgetCalculation.df.format(creditCard));//set format as 2 decimal figures after the dot
				  break;
			   case ACCOUNT_BALANCE:
				   double accountBalance;
				   if(alreadyAccountBalanceTextField.getText().equals("")) {
					   accountBalance = 0.0;
				   }else {
				   accountBalance = Double.parseDouble(alreadyAccountBalanceTextField.getText());
				   }
				   accountBalance += moneyToPay;
				   availableAccountBalanceTextField.setText(OrderBusinessBudgetCalculation.df.format(Double.parseDouble
						   (availableAccountBalanceTextField.getText())-moneyToPay));//set format as 2 decimal figures after the dot
				   alreadyAccountBalanceTextField.setText(OrderBusinessBudgetCalculation.df.format(accountBalance));//set format as 2 decimal figures after the dot
				   break;
			   case EMPLOYEE_BUDGET:
				    boolean isLoggedInAsBusinessAccount = false;
				    if(connectedUser instanceof BusinessCustomer) {
						isLoggedInAsBusinessAccount = ((BusinessCustomer) connectedUser).getLoggedInAsBusinessAccount();
					}
					if(isLoggedInAsBusinessAccount) {
						double employeeBudget;
						if(alreadyEmployeeBudgetTextField.getText().equals("")) {
							employeeBudget = 0.0;
						}else {
							employeeBudget = Double.parseDouble(alreadyEmployeeBudgetTextField.getText());
						}
						availableBudgetBalanceTextField.setText(OrderBusinessBudgetCalculation.df.format
								(Double.parseDouble(availableBudgetBalanceTextField.getText())-moneyToPay)); //set format as 2 decimal figures after the dot
						employeeBudget += moneyToPay;
						alreadyEmployeeBudgetTextField.setText(OrderBusinessBudgetCalculation.df.format(employeeBudget));//set format as 2 decimal figures after the dot
					}
				   break;
			   default:
				   break;
			   }
			  }
			   }catch(NumberFormatException e) {
				   errorText.setText("Invalid amount to pay, change it!");
		    	   
			   }
			   
		   }
	    }

	   /**
	    * This is a function for removing 
	    * amount of money from the paying methods
	    * by the user
	    * 
	    * @param event - when pressed on the button
	    */
	   @FXML
	   public void getRemoveAmountBtn(ActionEvent event) {
		   if(paymentMethodCombo.getValue() == null) {
	    		errorText.setText("Choose payment method!");
	    		paymentMethodCombo.setStyle("-fx-border-color: red");
	    	}
		   else if(enterAmountTextField.getText() == "") {
			   errorText.setText("Enter amount!");
			   enterAmountTextField.setStyle("-fx-border-color: red");
		   }
		   else if(amountLeftToPay == order.getTotalPrice()) {
			   errorText.setText("Nothing to remove, enter payment method!");
	    	   
		   }
		   else if(BigDecimal.valueOf(Double.parseDouble(enterAmountTextField.getText())).scale() > 2){
			   errorText.setText("Amount must not exceed 2 decimal points!");
			   enterAmountTextField.setStyle("-fx-border-color: red");
		   }
		   else if(Double.parseDouble(enterAmountTextField.getText()) <= 0.0) {
			   errorText.setText("Amount must be un-negative!");
			   enterAmountTextField.setStyle("-fx-border-color: red");
		   }
		   else {
			   errorText.setText(""); //disable previous error warning
			   PaymentWay paymentWay = paymentMethodCombo.getValue();
			   try {
			   double moneyToReturn = Double.parseDouble(enterAmountTextField.getText());
			   if(isPaymentAmountValidForRemoveAmount(paymentWay)) {
				   amountLeftToPay += moneyToReturn; //update the amount left to pay
				   amountLeftToPay = (double) Math.round(amountLeftToPay*100)/100;
				   totalToPayTextField.setText(OrderBusinessBudgetCalculation.df.format(amountLeftToPay));//set the total price, set format as 2 decimal figures after the dot
				   /*Update all the text fields in the screen according to the users pick in the combo box*/
			   switch(paymentWay) {
			   case CASH:
				   double cash;
				   if(alreadyCashTextField.getText() == "") {
					   cash = 0.0;
				   }else {
				   cash = Double.parseDouble(alreadyCashTextField.getText());
				   }
				   cash -= moneyToReturn;
				   alreadyCashTextField.setText(OrderBusinessBudgetCalculation.df.format(cash));//set format as 2 decimal figures after the dot
				  break;
			   case CREDIT_CARD:
				   double creditCard;
				   if(alreadyCreditCardTextField.getText() == "") {
					   creditCard =0.0;
				   }else {
				   creditCard = Double.parseDouble(alreadyCreditCardTextField.getText());
				   }
				   creditCard -= moneyToReturn;
				   alreadyCreditCardTextField.setText(OrderBusinessBudgetCalculation.df.format(creditCard));//set format as 2 decimal figures after the dot
				  break;
			   case ACCOUNT_BALANCE:
				   double accountBalance;
				   if(alreadyAccountBalanceTextField.getText() == "") {
					   accountBalance = 0.0;
				   }else {
				   accountBalance = Double.parseDouble(alreadyAccountBalanceTextField.getText());
				   }
				   accountBalance -= moneyToReturn;
				   availableAccountBalanceTextField.setText(OrderBusinessBudgetCalculation.df.format
						   (Double.parseDouble(availableAccountBalanceTextField.getText())+moneyToReturn));//set format as 2 decimal figures after the dot
				   alreadyAccountBalanceTextField.setText(OrderBusinessBudgetCalculation.df.format(accountBalance));//set format as 2 decimal figures after the dot
				   break;
			   case EMPLOYEE_BUDGET:
				   boolean isLoggedInAsBusinessAccount = false;
				   if(connectedUser instanceof BusinessCustomer) {
						isLoggedInAsBusinessAccount = ((BusinessCustomer) connectedUser).getLoggedInAsBusinessAccount();
					}
						if(isLoggedInAsBusinessAccount) {
							double employeeBudget;
							if(alreadyEmployeeBudgetTextField.getText() == "") {
								employeeBudget = 0.0;
							}else {
								employeeBudget = Double.parseDouble(alreadyEmployeeBudgetTextField.getText());
							}
							availableBudgetBalanceTextField.setText(OrderBusinessBudgetCalculation.df.format
									(Double.parseDouble(availableBudgetBalanceTextField.getText())+moneyToReturn));//set format as 2 decimal figures after the dot
							employeeBudget -= moneyToReturn;
							alreadyEmployeeBudgetTextField.setText(OrderBusinessBudgetCalculation.df.format(employeeBudget));//set format as 2 decimal figures after the dot
						}	
				   break;
			   default:
				   break;
			   }
			  }
			   }catch(NumberFormatException e) {
				   errorText.setText("Invalid amount to pay, change it!");
		    	   
			   }
		   }
	    }
	   
	     /**
	     * Back button for enable the user
	     * go back to summary screen.
	     * 
	     * @param event - when pressed on the button
	     */
	    @FXML
	    public void getBackBtn(ActionEvent event) {
	      Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/ORD6OrderSummary.fxml").openStream());
						Scene scene = new Scene(root);
						Stage.initStyle(StageStyle.UNDECORATED);
						scene.setOnMousePressed(pressEvent -> {
						    scene.setOnMouseDragged(dragEvent -> {
						    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
						    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
						    });
						});
						scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
						Stage.setTitle("Payment Method");
						Stage.setScene(scene);
						Stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
					((Node) event.getSource()).getScene().getWindow().hide();
				}
			});

	    }

	      /**
	     * Exit from screen and update
	     * DB.
	     * 
	     * @param event - when pressed on the button
	     */
	    @FXML
	    public void getExitBtn(ActionEvent event) {
	    	Message message = new Message(Task.LOGOUT,Answer.WAIT_RESPONSE,connectedUser);
			sendToClient(message);
			connectedUser = null;
			Message disconnectMessage= new Message(Task.CLIENT_DICONNECT,Answer.WAIT_RESPONSE,null);
			sendToClient(disconnectMessage);
			System.exit(0);
	    }

	    /**
	     * This is a function for updating 
	     * the DB and for letting the customer know he finished his order,
	     * after the finish we move him to his main user portal 
	     * UI view.
	     * 
	     * @param event - when pressed on the button
	     */
	    @FXML
	    public void getFinishOrderBtn(ActionEvent event) {
	    	if(amountLeftToPay!=0) {
	    		errorText.setText("Finish with the payment first!");
	    	}
	    	else {
	    	errorText.setText("");

	    	/*Used for updating the balance used and the budget balance used in order to restore if needed from the supplier side if he doesn't approve the order*/
	    	String balanceUsed = "0";
	    	String budgetBalanceUsed = "0";
	    	
	    	/*send to the server the customers data for updating the balance and update the users budget balance in case of businessCustomer*/
	    	/*decrement the amount balance used by the user (customer \ business customer*/
	    	if(connectedUser instanceof BusinessCustomer){
	    		/*In case the connected user is business customer update his budget balance accordingly*/
	    		if(!alreadyEmployeeBudgetTextField.getText().equals("")) {
		    	((BusinessCustomer)connectedUser).setBudgetUsed(((BusinessCustomer)connectedUser).getBudgetUsed()+Double.valueOf(alreadyEmployeeBudgetTextField.getText())); //set new budget used after use
		    	budgetBalanceUsed = alreadyEmployeeBudgetTextField.getText();
	    		}
	    		if(!alreadyAccountBalanceTextField.getText().equals("")) {
			    	((BusinessCustomer)connectedUser).setBalance(((BusinessCustomer)connectedUser).getBalance()-Double.valueOf(alreadyAccountBalanceTextField.getText())); //set new balance after reduce
			    	balanceUsed = alreadyAccountBalanceTextField.getText();	
	    		}
	    	}else {
	    		if(!alreadyAccountBalanceTextField.getText().equals("")) {
		    	((Customer)connectedUser).setBalance(((Customer)connectedUser).getBalance()-Double.valueOf(alreadyAccountBalanceTextField.getText())); //set new balance after reduce
		    	balanceUsed = alreadyAccountBalanceTextField.getText();
	    		}
	    	}
	    	/*Send to Server and update order table in relevant columns*/
	    	List<Object> orderDetailsToDb = new ArrayList<Object>();
	    	orderDetailsToDb.add(balanceUsed);
	    	orderDetailsToDb.add(budgetBalanceUsed);
	    	orderDetailsToDb.add(order);
	    	Message message = new Message(Task.ORDER_FINISHED,Answer.WAIT_RESPONSE,orderDetailsToDb);
	    	sendToClient(message);//send message to the server telling the order is finished and than push into DB
	    	
	    	/*Send to server for updating customers balance and company budget*/
	    	ArrayList<Object> list = new ArrayList<>();
	    	list.add(connectedUser);
	    	list.add(order.getSupplierUserId());
	    	Message messageForUpdateBalance = new Message(Task.CUSTOMER_UPDATE_DB_AFTER_PAYMENT,Answer.WAIT_RESPONSE,list);
	    	sendToClient(messageForUpdateBalance);//send message to the server telling the balance and budget update and than push into DB
	    	
	    	/*Give notice for the user that the order is ok*/
    		PopUpMessages.updateMessage("Order finished successfully!");
    		OrderChooseItemsScreenController.order= null; 
	    	/*After saving the order in the DB send the customer back to his home screen*/ 
    		if(connectedUser instanceof BusinessCustomer)
    			setBusinessCustomerPortal(event);
    		else
    			setPrivateCustomerPortal(event);
	    	}
	    }

	     /**
	     * 
	     * This is a method for getting 
	     * information for the user
	     * 
	     * @param event - when pressed on the button
	     */
	    @FXML
	    public void getHelpBtn(ActionEvent event) {
	    	PopUpMessages.helpMessage("On this screen you can choose the order payment methods and amount to pay.");
	    }

	    /**
	     * This is the init for the current 
	     * screen, in which we load the fxml.
	     * This function is called from the previous 
	     * screen controller.
	     * 
	     * @param order The current order.
	     */
	  public void initPaymentConfigurationScreen(Order order) {
		  OrderPaymentConfigurationScreenController.order = order;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/ORD7PaymentConfiguration.fxml").openStream());
						orderPaymentConfigurationScreenController = loader.getController();
						Scene scene = new Scene(root);
						Stage.initStyle(StageStyle.UNDECORATED);
						scene.setOnMousePressed(pressEvent -> {
						    scene.setOnMouseDragged(dragEvent -> {
						    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
						    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
						    });
						});
						scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
						Stage.setTitle("Payment");
						Stage.setScene(scene);
						Stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
	  
		/**
		 * This method loads the customer portal
		 * 
		 * @param event ActionEvent of javaFX.
		 */
		public void setPrivateCustomerPortal(ActionEvent event) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						FXMLLoader loader = new FXMLLoader();
						Pane root;
						try {
							Stage Stage = new Stage();
							Stage.setResizable(false);
							root = loader.load(getClass().getResource("/fxmls/UserPortalOfCustomer.fxml").openStream());
							UserPortalOfCustomerController UPOCC = new UserPortalOfCustomerController();
							UPOCC.initPortalAgain();
							Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
								@Override
								public void handle(WindowEvent event) { 
									event.consume();
									Stage.close();
								}
							});
						} catch (IOException e) {
							e.printStackTrace();
						}
						((Node) event.getSource()).getScene().getWindow().hide();
					}
				});
			
		}
		
		/**
		 * This method loads the business customer portal
		 * 
		 * @param event ActionEvent of javaFX.
		 */
		public void setBusinessCustomerPortal(ActionEvent event) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						FXMLLoader loader = new FXMLLoader();
						Pane root;
						try {
							Stage Stage = new Stage();
							Stage.setResizable(false);
							root = loader.load(getClass().getResource("/fxmls/UserPortalOfBusinessCustomer.fxml").openStream());
							UserPortalOfBusinessCustomerController UPOBCC = new UserPortalOfBusinessCustomerController();
							UPOBCC.initPortalAgain();
							Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
								@Override
								public void handle(WindowEvent event) { 
									event.consume();
									Stage.close();
								}
							});
						} catch (IOException e) {
							e.printStackTrace();
						}
						((Node) event.getSource()).getScene().getWindow().hide();
					}
				});
			
		}

	/**
	* This is a function for 
	* initializing the screen, we set here all the data from
	* the DB related to the balance and the budget received for 
	* the user (depends on the customer type - business or regular).
	* In addition, we add here all the data for the combo box 
	* picking payment options.
	* 
	* @param arg0 - not in use
	* @param arg1 - not in use
	*/
	@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			List<PaymentWay> paymentMethods = new ArrayList<>();
			/*For business customer and hr manager*/
			boolean isLoggedInAsBusinessAccount = false;
			if(connectedUser instanceof BusinessCustomer) {
				isLoggedInAsBusinessAccount = ((BusinessCustomer) connectedUser).getLoggedInAsBusinessAccount();
			}
			if(connectedUser instanceof BusinessCustomer && (isLoggedInAsBusinessAccount)) {
				for(PaymentWay pay : PaymentWay.values()) {
					paymentMethods.add(pay);
				}
				if(connectedUser instanceof BusinessCustomer) {
					availableBudgetBalanceTextField.setText(String.valueOf(OrderBusinessBudgetCalculation.calculateBusinessBudgetLeftForUser(((BusinessCustomer) connectedUser))));
				}
			}
			else { // if connectedUser is instance of Customer
				for(PaymentWay pay : PaymentWay.values()) {
					/*Add only enums that related to regular customer!*/
					if((pay != PaymentWay.EMPLOYEE_BUDGET)) {
						paymentMethods.add(pay);
					}	
				}
				employeeBudgetLabel.setVisible(false); //set the employee budget label invisible for regular customer, TBD: add fx:id for the text field next to him!!!!!!
				alreadyEmployeeBudgetTextField.setVisible(false);//set the employee text field invisible for regular customer
				availableBudgetBalanceLabel.setVisible(false);//set the employee text field invisible for regular customer
				availableBudgetBalanceTextField.setVisible(false);//set the employee text field invisible for regular customer
			}
			paymentMethodCombo.getItems().addAll(paymentMethods); //set all the relevant enums into the combo box
			availableAccountBalanceTextField.setText((String.valueOf(((Customer) connectedUser).getBalance())));//set the amount of the balance of any customer according to how much the bite me system owe him 
			/*set all text fields to be not editable*/
			alreadyEmployeeBudgetTextField.setDisable(true);
			availableBudgetBalanceTextField.setDisable(true);
			totalToPayTextField.setDisable(true);
			alreadyCashTextField.setDisable(true);
			alreadyCreditCardTextField.setDisable(true);
			alreadyAccountBalanceTextField.setDisable(true);
			availableAccountBalanceTextField.setDisable(true);
			order.setTotalPrice(order.getTotalPrice()); //done for treating a behavioral bug that started in numbers with a few figures after the dot.
			amountLeftToPay = order.getTotalPrice();
			totalToPayTextField.setText(String.valueOf(amountLeftToPay));//set the total price
		}

	/**
	 * This is a function that is used for checking 
	 * the validity of the added amount 
	 * for the payment methods such as credit card and etc.
	 * 
	 * @param paymentWay According to the payment way we check the fields.
	 * @return boolean, true if valid 
	 */
	private boolean isPaymentAmountValidForAddAmount(PaymentWay paymentWay) {
		double enteredAmount = Double.parseDouble(enterAmountTextField.getText());
		double accountBalance =  Double.parseDouble(availableAccountBalanceTextField.getText());
		double budgetBalance=0;
			
		if((amountLeftToPay - enteredAmount < 0)) {
			errorText.setText("Wrong input value, change it!");
			return false;
		}

		if(paymentWay == PaymentWay.ACCOUNT_BALANCE) {
			if((accountBalance < enteredAmount)) {
				errorText.setText("Wrong input value, change it!");
				
				return false;
			}
		}
		
		if(paymentWay == PaymentWay.EMPLOYEE_BUDGET) {
			budgetBalance = Double.parseDouble(availableBudgetBalanceTextField.getText()); 
			if(budgetBalance < enteredAmount) {
				errorText.setText("Wrong input value, change it!");
				
				return false;
			}
		}
			return true;
	}
	
	/**
	 * This is a function that is used for checking 
	 * the validity of the removed amount 
	 * from the payment methods such as credit card and etc.
	 * 
	 * @param paymentWay According to the payment way we check the fields.
	 * @return boolean, true if valid .
	 */
	private boolean isPaymentAmountValidForRemoveAmount(PaymentWay paymentWay) {
		double enteredAmount = Double.parseDouble(enterAmountTextField.getText());		
		if(enteredAmount<0) {
			errorText.setText("Wrong input value, change it!");
			return false;
		}
		//double employerBudgetBalance = Double.parseDouble(availableBudgetBalanceTextField.getText());
		if(paymentWay == PaymentWay.ACCOUNT_BALANCE) {
			if(alreadyAccountBalanceTextField.getText() == "") {
					errorText.setText("Wrong input value, change it!");
				return false;
			}
			else {
				double accountBalanceAlreadyPayed =  Double.parseDouble(alreadyAccountBalanceTextField.getText());
				if(accountBalanceAlreadyPayed < enteredAmount) {
						errorText.setText("Wrong input value, change it!");
					return false;
				}
			}
		}
		if(paymentWay == PaymentWay.EMPLOYEE_BUDGET) {
			if(alreadyEmployeeBudgetTextField.getText() == "") {
					errorText.setText("Wrong input value, change it!");
				return false;
			}
			else {
				double alreadyBudgetBalance =  Double.parseDouble(alreadyEmployeeBudgetTextField.getText());
				if(alreadyBudgetBalance < enteredAmount) {
						errorText.setText("Wrong input value, change it!");
					return false;
				}
			}
		}
		if(paymentWay == PaymentWay.CASH) {
			if(alreadyCashTextField.getText() == "") {
					errorText.setText("Wrong input value, change it!");
				return false;
			}
			else {
				double cashAlreadyPayed =  Double.parseDouble(alreadyCashTextField.getText());
				if(cashAlreadyPayed < enteredAmount) {
						errorText.setText("Wrong input value, change it!");
					return false;
				}
			}
		}
		if(paymentWay == PaymentWay.CREDIT_CARD) {
			if(alreadyCreditCardTextField.getText() == "") {
					errorText.setText("Wrong input value, change it!");
				return false;
			}
			else {
				double creaditCardAlreadyPayed = Double.parseDouble(alreadyCreditCardTextField.getText());
				if(creaditCardAlreadyPayed < enteredAmount) {
						errorText.setText("Wrong input value, change it!");
					return false;
				}
			}
		}
			return true;
	}
}

