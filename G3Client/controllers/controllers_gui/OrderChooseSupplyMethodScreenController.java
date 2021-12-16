package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import orders.Order;
import orders.SupplyType;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * 
 * Class description: 
 * This is a class for 
 * choosing the supply method 
 * for the order shipment.
 * 
 * 
 * @version 15/12/2021
 */
public class OrderChooseSupplyMethodScreenController extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */

	private static FXMLLoader loader;
    private static OrderChooseSupplyMethodScreenController orderChooseSupplyMethodScreenController;
    private static Order order;

    @FXML
    private DatePicker supplyDatePicker;

    @FXML
    private ComboBox<String> supplyTimeCombo;

    @FXML
    private ComboBox<String> supplyMethodCombo;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnBack;

    @FXML
    private Button nextBtn;

    @FXML
    private Button btnHelp;

    @FXML
    private Text errorText;


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
					root = loader.load(getClass().getResource("/fxmls/ORD3ChooseItemsScreen.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Choose items");
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
     * This function is used for
     * switching to the next screen and 
     * 
     * @param event
     */
    @FXML
    void getBtnNext(ActionEvent event) {
    	//order.supplyType = supplyMethodCombo.getValue();
    	//order.estimatedSupplyDateTime = Date.from(supplyDatePicker.getValue().atStartOfDay().toInstant(null));
    	//System.out.println(order.estimatedSupplyDateTime);
    	System.out.println(supplyDatePicker.getValue());
    	System.out.println(LocalDate.now());
    	System.out.println(supplyDatePicker.getValue().equals(LocalDate.now()));
    	


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
     * This is a method for getting 
     * information for the user
     * 
     * @param event
     */
    @FXML
    void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("Please choose the supply method (Delivery / TA / Robotic delivery) and time");
    }


   /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     */
  public void initChooseSupplyMethodScreen(Order order) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD4ChooseSupplyMethod.fxml").openStream());
					orderChooseSupplyMethodScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Choose Supply method");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		OrderChooseSupplyMethodScreenController.order=order; // save the order from the previous screen
		
	}


  	 /**
     * This is a function for 
     * initializing the screen.
     * 
     */
  	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
  		
  		supplyDatePicker.setValue(LocalDate.now()); // set the Current date in the date picker
  		supplyMethodCombo.getItems().addAll(SupplyType.DELIVERY.toString(), SupplyType.TAKE_AWAY.toString()); //set supply methods in the combo box of supply method
  		List<String> hourArray = new ArrayList<String>(); // create hour array
  		Date date = new Date();
  		int hour = date.getHours();
  		
  		//disable the previous dates in the date picker
  	    Callback<DatePicker, DateCell> disablePreviousDates = new Callback<DatePicker, DateCell>() {
          @Override
          public DateCell call(final DatePicker param) {
              return new DateCell() {
                  @Override
                  public void updateItem(LocalDate item, boolean empty) {
                      super.updateItem(item, empty); 
                      LocalDate today = LocalDate.now();
                      setDisable(empty || item.compareTo(today) < 0);
                  }

              };
          }

      };
      supplyDatePicker.setDayCellFactory(disablePreviousDates);
      
      /**Compare between the current date to the chosen date in the DatePicker**/

      
      
    
      supplyDatePicker.setOnAction( (event) -> {
    	  
    	  boolean checkIfCustomerPickedCurrentDayForTheSupply = supplyDatePicker.getValue().equals(LocalDate.now());
    	  System.out.println(hour);
    	  System.out.println(checkIfCustomerPickedCurrentDayForTheSupply);
    	  
      	  /**adding elements to the hour array depending on the current time**/
    	  if (checkIfCustomerPickedCurrentDayForTheSupply) { // if the customer picked the current day
    			
    			if (hour < 8) { // // the current time is earlier than 8am (but in the current day)
    				for(int i=8; i<23; i++) {
    					hourArray.add(String.valueOf(i)+":00");
    					}
    				supplyTimeCombo.getItems().removeAll();
    		    	supplyTimeCombo.getItems().addAll(hourArray);
    			}
    				
    			else {  // the current time is later than 8am (but in the current day)
    				for(int i=hour+1; i<23; i++){ 
    					hourArray.add(String.valueOf(i)+":00");
    					}
    				supplyTimeCombo.getItems().removeAll();
    		    	supplyTimeCombo.getItems().addAll(hourArray);
    			}
    			
    		}
    			
    	    else {  // the customer want the delivery for the next days
    			for(int i=8; i<23; i++){ 
    				hourArray.add(String.valueOf(i)+":00");
    				}
    			supplyTimeCombo.getItems().removeAll();
    	    	supplyTimeCombo.getItems().addAll(hourArray);
           }
    	  
      });
	 
    }
  	
}
