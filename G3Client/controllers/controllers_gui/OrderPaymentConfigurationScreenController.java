package controllers_gui;

import java.io.IOException;
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
import javafx.scene.paint.Color;
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
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * Class description: 
 * This is a class for the user 
 * to add all his payment to the system (cash / credit card, etc.)
 * 
 * @version 20/12/2021
 */
public class OrderPaymentConfigurationScreenController  extends AbstractBiteMeController implements Initializable{
		/**
		 * Class members description:
		 */

		private static FXMLLoader loader;
	    private static OrderPaymentConfigurationScreenController orderPaymentConfigurationScreenController;
	    private static Order order;
	    private double amountLeftToPay;

	    @FXML
	    private Button finishOrderBtn;

	    @FXML
	    private Button btnExit;

	    @FXML
	    private Button btnBack;

	    @FXML
	    private Button btnHelp;

	    @FXML
	    private TextField availableAccountBalanceTextField;

	    @FXML
	    private Label availableBudgetBalanceLabel;

	    @FXML
	    private TextField availableBudgetBalanceTextField;

	    @FXML
	    private TextField enterAmountTextField;

	    @FXML
	    private TextField totalToPayTextField;

	    @FXML
	    private Text errorText;

	    @FXML
	    private ComboBox<PaymentWay> paymentMethodCombo;

	    @FXML
	    private Button addAmountBtn;

	    @FXML
	    private Button removeAmountBtn;

	    @FXML
	    private Label employeeBudgetLabel;

	    @FXML
	    private TextField alreadyCashTextField;

	    @FXML
	    private TextField alreadyCreditCardTextField;

	    @FXML
	    private TextField alreadyAccountBalanceTextField;

	    @FXML
	    private TextField alreadyEmployeeBudgetTextField;

	    /**
	     * This is a function for 
	     * adding amount of money payed 
	     * by the user.
	     * 
	     * @param event
	     */
	   @FXML
	    void getAddAmountBtn(ActionEvent event) {
		   if(paymentMethodCombo.getValue() == null) {
	    		errorText.setText("Please choose the payment method first!");
	    		errorText.setFill(Color.RED);
	    	}
		   else if(enterAmountTextField.getText() == "") {
			   errorText.setText("Please enter the payment ammount!");
	    	   errorText.setFill(Color.RED);
		   }
		   else if(amountLeftToPay == 0) {
			   errorText.setText("Nothing to pay anymore, press finish order!");
	    	   errorText.setFill(Color.RED);
		   }
		   else {
			   errorText.setText(""); //disable previous error warning
			   PaymentWay paymentWay = paymentMethodCombo.getValue();
			   try {
			   double moneyToPay = Double.parseDouble(enterAmountTextField.getText());
			   if(isPaymentAmountValidForAddAmount(paymentWay)) {
				   amountLeftToPay -= moneyToPay; //update the amount left to pay
				   totalToPayTextField.setText(String.valueOf(amountLeftToPay));//set the total price
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
				   alreadyCashTextField.setText(String.valueOf(cash));
				  break;
			   case CREDIT_CARD:
				   double creditCard;
				   if(alreadyCreditCardTextField.getText().equals("")) {
					   creditCard =0.0;
				   }else {
				   creditCard = Double.parseDouble(alreadyCreditCardTextField.getText());
				   }
				   creditCard += moneyToPay;
				   alreadyCreditCardTextField.setText(String.valueOf(creditCard));
				  break;
			   case ACCOUNT_BALANCE:
				   double accountBalance;
				   if(alreadyAccountBalanceTextField.getText().equals("")) {
					   accountBalance = 0.0;
				   }else {
				   accountBalance = Double.parseDouble(alreadyAccountBalanceTextField.getText());
				   }
				   accountBalance += moneyToPay;
				   availableAccountBalanceTextField.setText(String.valueOf(Double.parseDouble(availableAccountBalanceTextField.getText())-moneyToPay));
				   alreadyAccountBalanceTextField.setText(String.valueOf(accountBalance));
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
						availableBudgetBalanceTextField.setText(String.valueOf(Double.parseDouble(availableBudgetBalanceTextField.getText())-moneyToPay)); //bug?
						employeeBudget += moneyToPay;
						alreadyEmployeeBudgetTextField.setText(String.valueOf(employeeBudget));
					}
				   break;
			   default:
				   break;
			   }
			  }
			   }catch(NumberFormatException e) {
				   errorText.setText("You have entered invalid amount to pay, Please change it!");
		    	   errorText.setFill(Color.RED);
			   }
			   
		   }
	    }

	   /**
	    * This is a function for removing 
	    * amount of money from the paying methods
	    * by the user
	    * 
	    * @param event
	    */
	   @FXML
	    void getRemoveAmountBtn(ActionEvent event) {
		   if(paymentMethodCombo.getValue() == null) {
	    		errorText.setText("Please choose the payment method first!");
	    		errorText.setFill(Color.RED);
	    	}
		   else if(enterAmountTextField.getText() == "") {
			   errorText.setText("Please enter the payment ammount!");
	    	   errorText.setFill(Color.RED);
		   }
		   else if(amountLeftToPay == order.getTotalPrice()) {
			   errorText.setText("Nothing to remove anymore, enter payment method!");
	    	   errorText.setFill(Color.RED);
		   }
		   else {
			   errorText.setText(""); //disable previous error warning
			   PaymentWay paymentWay = paymentMethodCombo.getValue();
			   try {
			   double moneyToReturn = Double.parseDouble(enterAmountTextField.getText());
			   if(isPaymentAmountValidForRemoveAmount(paymentWay)) {
				   amountLeftToPay += moneyToReturn; //update the amount left to pay
				   totalToPayTextField.setText(String.valueOf(amountLeftToPay));//set the total price
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
				   alreadyCashTextField.setText(String.valueOf(cash));
				  break;
			   case CREDIT_CARD:
				   double creditCard;
				   if(alreadyCreditCardTextField.getText() == "") {
					   creditCard =0.0;
				   }else {
				   creditCard = Double.parseDouble(alreadyCreditCardTextField.getText());
				   }
				   creditCard -= moneyToReturn;
				   alreadyCreditCardTextField.setText(String.valueOf(creditCard));
				  break;
			   case ACCOUNT_BALANCE:
				   double accountBalance;
				   if(alreadyAccountBalanceTextField.getText() == "") {
					   accountBalance = 0.0;
				   }else {
				   accountBalance = Double.parseDouble(alreadyAccountBalanceTextField.getText());
				   }
				   accountBalance -= moneyToReturn;
				   availableAccountBalanceTextField.setText(String.valueOf(Double.parseDouble(availableAccountBalanceTextField.getText())+moneyToReturn));
				   alreadyAccountBalanceTextField.setText(String.valueOf(accountBalance));
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
							availableBudgetBalanceTextField.setText(String.valueOf(Double.parseDouble(availableBudgetBalanceTextField.getText())+moneyToReturn));
							employeeBudget -= moneyToReturn;
							alreadyEmployeeBudgetTextField.setText(String.valueOf(employeeBudget));
						}	
				   break;
			   default:
				   break;
			   }
			  }
			   }catch(NumberFormatException e) {
				   errorText.setText("You have entered invalid amount to pay, Please change it!");
		    	   errorText.setFill(Color.RED);
			   }
		   }
	    }
	   
	   
	     /**
	     * Back button for the 
	     * 
	     * 
	     * @param event
	     */
	    @FXML
	    void getBackBtn(ActionEvent event) {
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
	     * @param event
	     */
	    @FXML
	    void getExitBtn(ActionEvent event) {
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
	     * @param event
	     */
	    @FXML
	    void getFinishOrderBtn(ActionEvent event) {
	    	if(amountLeftToPay!=0) {
	    		errorText.setText("Finish paying first!!");
	    		errorText.setFill(Color.RED);
	    	}
	    	else {
	    	errorText.setText("");
	    	Message message = new Message(Task.ORDER_FINISHED,Answer.WAIT_RESPONSE,order);
	    	sendToClient(message);//send message to the server telling the order is finished and than push into DB
	    	
	    	/*send to the server the customers data for updating the balance and update the users budget balance in case of businessCustomer*/
	    	/*decrement the amount balance used by the user (customer \ business customer*/
	    	if(connectedUser instanceof BusinessCustomer){
	    		/*In case the connected user is business customer update his budget balance accordingly*/
	    		if(!alreadyEmployeeBudgetTextField.getText().equals("")) {
		    	((BusinessCustomer)connectedUser).setBudgetUsed(((BusinessCustomer)connectedUser).getBudgetUsed()+Double.valueOf(alreadyEmployeeBudgetTextField.getText())); //set new budget used after use
	    		}
	    		if(!alreadyAccountBalanceTextField.getText().equals("")) {
			    	((BusinessCustomer)connectedUser).setBalance(((BusinessCustomer)connectedUser).getBalance()-Double.valueOf(alreadyAccountBalanceTextField.getText())); //set new balance after reduce
		    		}
	    	}else {
	    		if(!alreadyAccountBalanceTextField.getText().equals("")) {
		    	((Customer)connectedUser).setBalance(((Customer)connectedUser).getBalance()-Double.valueOf(alreadyAccountBalanceTextField.getText())); //set new balance after reduce
	    		}
	    	}
	    	
	    	/*Send to server for updating customers balance and company budget*/
	    	ArrayList<Object> list = new ArrayList<>();
	    	list.add(connectedUser);
	    	list.add(order.getSupplierUserId());
	    	Message messageForUpdateBalance = new Message(Task.CUSTOMER_UPDATE_DB_AFTER_PAYMENT,Answer.WAIT_RESPONSE,list);
	    	sendToClient(messageForUpdateBalance);//send message to the server telling the balance and budget update and than push into DB
	    	
	    	/*Give notice for the user that the order is ok*/
    		PopUpMessages.updateMessage("You have finished the order, The food will be shortly at your door steps!!");
    		OrderChooseItemsScreenController.order= null; 
	    	/*After saving the order in the DB send the customer back to his home screen*/ 
    		if(connectedUser instanceof BusinessCustomer)
    			setBusinessCustomerPortal(event);
    		else
    			setPrivateCustomerPortal(event);

	    	}
	    }

	       /**
	     * This is a method for getting 
	     * information for the user
	     * 
	     * @param event
	     */
	    @FXML
	    void getHelpBtn(ActionEvent event) {
	    	PopUpMessages.helpMessage("On this screen you can choose the order payment methods and amount to pay.");
	    }

	    /**
	     * This is the init for the current 
	     * screen, in which we load the fxml.
	     * This function is called from the previous 
	     * screen controller.
	     * 
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
		 * this method loads the customer portal
		 * @param event
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
		 * this method loads the customer portal
		 * @param event
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
	* initializing the screen.
	* 
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
			order.setTotalPrice(Math.round(order.getTotalPrice())); //done for treating a behavioral bug that started in numbers with a few figures after the dot.
			amountLeftToPay = order.getTotalPrice();
			totalToPayTextField.setText(String.valueOf(amountLeftToPay));//set the total price
		}

	/**
	 * This is a function that is used for checking 
	 * the validity of the added amount 
	 * for the payment methods such as credit card and etc.
	 * 
	 * @param paymentWay
	 * @return boolean, true if valid 
	 */
	private boolean isPaymentAmountValidForAddAmount(PaymentWay paymentWay) {
		double enteredAmount = Double.parseDouble(enterAmountTextField.getText());
		double accountBalance =  Double.parseDouble(availableAccountBalanceTextField.getText());
		double budgetBalance=0;
		if(enteredAmount<0 || (amountLeftToPay - enteredAmount < 0)) {
			errorText.setText("You have entered a wrong input value, change it!!");
    		errorText.setFill(Color.RED);
			return false;
		}

		if(paymentWay == PaymentWay.ACCOUNT_BALANCE) {
			if((accountBalance < enteredAmount)) {
				errorText.setText("You have entered a wrong input value, change it!!");
				errorText.setFill(Color.RED);
				return false;
			}
		}
		
		if(paymentWay == PaymentWay.EMPLOYEE_BUDGET) {
			budgetBalance = Double.parseDouble(availableBudgetBalanceTextField.getText()); 
			if(budgetBalance < enteredAmount) {
				errorText.setText("You have entered a wrong input value, change it!!");
				errorText.setFill(Color.RED);
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
	 * @param paymentWay
	 * @return boolean, true if valid 
	 */
	private boolean isPaymentAmountValidForRemoveAmount(PaymentWay paymentWay) {
		double enteredAmount = Double.parseDouble(enterAmountTextField.getText());		
		if(enteredAmount<0) {
			errorText.setText("You have entered a wrong input value, change it!!");
    		errorText.setFill(Color.RED);
			return false;
		}
		//double employerBudgetBalance = Double.parseDouble(availableBudgetBalanceTextField.getText());
		if(paymentWay == PaymentWay.ACCOUNT_BALANCE) {
			if(alreadyAccountBalanceTextField.getText() == "") {
				errorText.setText("You have entered a wrong input value, change it!!");
	    		errorText.setFill(Color.RED);
				return false;
			}
			else {
				double accountBalanceAlreadyPayed =  Double.parseDouble(alreadyAccountBalanceTextField.getText());
				if(accountBalanceAlreadyPayed < enteredAmount) {
					errorText.setText("You have entered a wrong input value, change it!!");
					errorText.setFill(Color.RED);
					return false;
				}
			}
		}
		if(paymentWay == PaymentWay.EMPLOYEE_BUDGET) {
			if(alreadyEmployeeBudgetTextField.getText() == "") {
				errorText.setText("You have entered a wrong input value, change it!!");
	    		errorText.setFill(Color.RED);
				return false;
			}
			else {
				double alreadyBudgetBalance =  Double.parseDouble(alreadyEmployeeBudgetTextField.getText());
				if(alreadyBudgetBalance < enteredAmount) {
					errorText.setText("You have entered a wrong input value, change it!!");
					errorText.setFill(Color.RED);
					return false;
				}
			}
		}
		if(paymentWay == PaymentWay.CASH) {
			if(alreadyCashTextField.getText() == "") {
				errorText.setText("You have entered a wrong input value, change it!!");
	    		errorText.setFill(Color.RED);
				return false;
			}
			else {
				double cashAlreadyPayed =  Double.parseDouble(alreadyCashTextField.getText());
				if(cashAlreadyPayed < enteredAmount) {
					errorText.setText("You have entered a wrong input value, change it!!");
		    		errorText.setFill(Color.RED);
					return false;
				}
			}
		}
		if(paymentWay == PaymentWay.CREDIT_CARD) {
			if(alreadyCreditCardTextField.getText() == "") {
				errorText.setText("You have entered a wrong input value, change it!!");
	    		errorText.setFill(Color.RED);
				return false;
			}
			else {
				double creaditCardAlreadyPayed = Double.parseDouble(alreadyCreditCardTextField.getText());
				if(creaditCardAlreadyPayed < enteredAmount) {
					errorText.setText("You have entered a wrong input value, change it!!");
		    		errorText.setFill(Color.RED);
					return false;
				}
			}
		}
			return true;
	}
}

