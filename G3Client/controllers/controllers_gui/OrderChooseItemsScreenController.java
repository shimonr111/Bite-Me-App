package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import orders.Item;
import orders.ItemCategory;
import orders.ItemSize;
import orders.ItemWithPicture;
import orders.Order;
import users.BusinessCustomer;

/**
 *  Class description: 
 * This is a class for 
 * controlling the UI of the item 
 * choosing for the private customer.
 * 
 * @author Lior, Guzovsky.
 * @author Shimon, Rubin.
 * @author Mousa, Srour.
 * 
 * @version 14/12/2021
 */
public class OrderChooseItemsScreenController extends AbstractBiteMeController implements Initializable{
	
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
    private static OrderChooseItemsScreenController orderChooseItemsScreenController;
    
    /**
     * A static String of restaurant ID.
     */
	private static String restaurantID;
	
	/**
	 * A Static String of restaurant Name.
	 */
	private static String restaurantName;
	
	/**
	 * The ArrayList of Items that we get from DB to display into the menu.
	 */
	public static ArrayList<Item> itemListOfMenuFromDB = new ArrayList<>();
	
	/**
	 * Static empty order to update it during the process.
	 */
	static Order order = new Order(null,null,null);
	
	/**
	 *  Observable List of Items to display into the menu.
	 */
	private static ObservableList<Item> itemsForCart = FXCollections.observableArrayList();
	
    @FXML
    /**
     * The Back Button.
     */
    private Button btnBack;

    @FXML
    /**
     * The Exit Button.
     */
    private Button btnExit;

    @FXML
    /**
     * The Help Button.
     */
    private Button btnHelp;

    @FXML
    /**
     * The Back Button.
     */
    private Button addToCartBtn;

    @FXML
    /**
     * The TableView of the Cart.
     */
    private TableView<Item> cartTable;

    @FXML
    /**
     * Item Name Column for cart.
     */
    private TableColumn<Item, String> itemCartColumn;

    @FXML
    /**
     * Item Size Column for cart.
     */
    private TableColumn<Item, ItemSize> sizeCartColumn;

    @FXML
    /**
     * Item price column for cart.
     */
    private TableColumn<Item, Double> priceCartColumn;

    @FXML
    /**
     * Item comment column for cart.
     */
    private TableColumn<Item, String> commentCartColumn;

    @FXML
    /**
     * The Remove Button.
     */
    private Button removeItemBtn;

    @FXML
    /**
     * The Menu TableView.
     */
    private TableView<ItemWithPicture> menuTable;
    
    @FXML
    /**
     * The Picture Column.
     */
    private TableColumn<ItemWithPicture, ImageView> pictureMenuColumn;
    
    @FXML
    /**
     * The Category of item column.
     */
    private TableColumn<ItemWithPicture, ItemCategory> menuCategoryColumn;

    @FXML
    /**
     * The Price of item column.
     */
    private TableColumn<ItemWithPicture, String> itemMenuColumn;

    @FXML
    /**
     * The item size column for menu.
     */
    private TableColumn<ItemWithPicture, ItemSize> sizeMenuColumn;
    
    @FXML
    /**
     * The price column for menu.
     */
    private TableColumn<ItemWithPicture, Double> priceMenuColumn;

    @FXML
    /**
     * The total price text field.
     */
    private TextField totalPriceTxtField;

    @FXML
    /**
     * The Next Button.
     */
    private Button nextBtn;

    @FXML
    /**
     * the text that we use to display message for the customer.
     */
    private Text errorText;

    @FXML
    private Text errorText1;

    /**
     * Check if the user clicked
     * on any row in the menu table view.
     * if he did, add to Cart table view
     * else, print error message using errorText
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getAddToCartBtn(ActionEvent event) {
    	if(menuTable.getSelectionModel().getSelectedItem() != null) {
    		if(menuTable.getSelectionModel().getSelectedItem() instanceof ItemWithPicture) {
    			errorText1.setVisible(false); // if the user added an item to the cart set invisible
    			Item itemAddToCart = new Item((Item)menuTable.getSelectionModel().getSelectedItem().getItem());
    			itemAddToCart.setSupplierUserId(restaurantID);
    			order.itemList.add(itemAddToCart);//add item to cart
    			order.totalPrice += itemAddToCart.getPrice();
    			//print the new sum to the user
    			totalPriceTxtField.setText(String.valueOf(order.totalPrice));
    		
		    	TextInputDialog dialog = new TextInputDialog("~Comment Here~");
		    	dialog.setHeaderText("Add Item Comment");
		    	dialog.getDialogPane().getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
		    	PopUpMessages.centerButtons(dialog.getDialogPane());
		    	Optional<String> result = dialog.showAndWait();
		    	  if(result.isPresent()) {
		    		  if (!result.get().equals("~Comment Here~")) //check that it contains only numbers
		    			  itemAddToCart.setComment(result.get());
		    	  }
    			itemsForCart.add(itemAddToCart);
    			cartTable.setItems(itemsForCart);
    			commentCartColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item,String>>() {
    				@Override
    				public void handle(CellEditEvent<Item, String> event) {
    					//get the whole object from the row
    					Item item = event.getRowValue(); 
    					//remove the item from the order list, because we need to update the comment as well if changed
    					order.itemList.remove(item);
    					//get the item from the arraylist
    					item.setComment(event.getNewValue());
    					//add the updated item back to the order
    					order.itemList.add(item);
    				}
    			});
    		}
    	}
    }

    /**
     * Back button for the restaurant
     * picking screen.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBackBtn(ActionEvent event) {
    	totalPriceTxtField.setText(String.valueOf(0));//set text to 0
    	order.totalPrice = 0;//set total price in order to 0
    	for(Item item : order.itemList) {
    		cartTable.getItems().remove(item);
    	}
    	order.itemList.removeAll(order.itemList);//empty the array list in order
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD2ChooseResturantInOrderScreen.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Choose Items");
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
     * This method move to next screen
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getBtnNext(ActionEvent event) {
    	//first check if the order item array list is not empty, if it is set text to the user and deny going to the next screen
    	if(order.itemList.size()!=0) {
    		addCustomerUserTypeToOrderObj(); //set the customer type
    		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
        	OrderChooseSupplyMethodScreenController orderChooseSupplyMethodScreenController = new OrderChooseSupplyMethodScreenController();
        	orderChooseSupplyMethodScreenController.initChooseSupplyMethodScreen(order); // call the init of the next screen	
    	}
    	else {
    		//set error message to the user
    		errorText1.setVisible(true); 
    		errorText1.setText("No items in the cart!");
    		errorText1.setFill(Color.RED);
    	}
    }

    /**
     * Exit from screen and update
     * DB.
     * 
     * @param event
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
     * This is a method for getting 
     * information for the user
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getHelpBtn(ActionEvent event) {
    	PopUpMessages.helpMessage("On this screen you can see the resturant menu.\nTo add items to the cart, click at item on the menu and the add button.\n"
    			+ "To remove items from the cart, click at the item on the cart and the remove button.\n"
    			+ "On each item you added to the cart you can add a comment (for example: no lettuce, Medium-Well..");
    }

    /**
     * Set the customer type into the order 
     * object for the DB - Table order.
     */
    private void addCustomerUserTypeToOrderObj() {
    	String customerType = null;
    	if(connectedUser instanceof BusinessCustomer) {
    		customerType = "businesscustomer";
    	}else {
    		customerType = "customer";
    	}
    	order.setCustomerUserType(customerType);
    }
    
    /**
     * Remove item from cart
     * and update price and order 
     * Object.
     * 
     * @param event ActionEvent of javaFX.
     */
    @FXML
    public void getRemoveItemBtn(ActionEvent event) {
    	if(cartTable.getSelectionModel().getSelectedItem() != null) {
    		if(cartTable.getSelectionModel().getSelectedItem() instanceof Item) {
    			Item itemRemoveFromCart = (Item)cartTable.getSelectionModel().getSelectedItem();
    			order.itemList.remove(itemRemoveFromCart);//remove item from cart
    			order.totalPrice -= itemRemoveFromCart.getPrice(); //update the total bill of the order
    			//print the new total bil to the user
    			totalPriceTxtField.setText(String.valueOf(order.totalPrice));
    			cartTable.getItems().remove(itemRemoveFromCart);
    		}
    	}
    }
    
    /**
     * This is the init for the current 
     * screen, in which we load the fxml.
     * This function is called from the previous 
     * screen controller.
     * 
     * @param pickRestaurantId The restaurant ID.
     * @param pickRestaurantName the restaurant name.
     */
    public void initChooseItemsScreen(String pickedRestaurantId, String pickedRestaurntName ) {
    	restaurantID = pickedRestaurantId;
    	restaurantName = pickedRestaurntName;
    	if(order == null) { //in case the user entered another round of order process
			order =new Order(null,null,null); //create empty order and update it during the process
			itemsForCart.removeAll(itemsForCart);
		}
    	//set data for the order here because the user can change to different screen if he wants!
    	order.setBranch(connectedUser.getHomeBranch()); //set to the branch the user chose in W4C identification stage
    	order.setSupplierUserId(restaurantID);
    	order.setCustomerUserId(connectedUser.getUserId());
    	// get the balance for specific user in this resturant.
    	sendToClient(new Message(Task.GET_USER_BALANCE,Answer.WAIT_RESPONSE,order));
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/ORD3ChooseItemsScreen.fxml").openStream());
					orderChooseItemsScreenController = loader.getController();
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Choose Items");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
    
    /**
     * This function will upload all
     * the menu of the chosen restaurant
     * to the screen in the Table View 
     * for showing it to the user.
     * 
     * @param arg0
     * @param arg1
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		totalPriceTxtField.setDisable(true); //set total price disable so that the user cant edit the price of the order
		ObservableList<ItemWithPicture> items = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_ITEMS_FOR_ORDER_MENU,Answer.WAIT_RESPONSE,restaurantID);
		sendToClient(message);
		if(itemListOfMenuFromDB == null) {
			errorText1.setVisible(true);
			errorText1.setText("There are no Items in this restaurant!");
		}
		else {
			//add all the wrapper items to the table view
			for(Item i : itemListOfMenuFromDB) {
				items.add(new ItemWithPicture(i));
			}
		}
		cartTable.setEditable(true); //enables to add text fields to the comment column

		pictureMenuColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture,ImageView>("picture"));
		
		menuCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture,ItemCategory>("category"));
		
		itemMenuColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture,String>("itemName"));
		itemCartColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("itemName"));
		
		
		sizeMenuColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture,ItemSize>("size"));
		sizeCartColumn.setCellValueFactory(new PropertyValueFactory<Item,ItemSize>("size"));
		
		priceMenuColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture,Double>("price"));
		priceCartColumn.setCellValueFactory(new PropertyValueFactory<Item,Double>("price"));
		
		commentCartColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("comment"));
		commentCartColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		
		menuTable.setItems(items);
		
		/*Add event handlers for distinguishing between cart table view and menu table view upon row selection*/
		cartTable.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				menuTable.getSelectionModel().clearSelection(); 
			}
			
		});
		
		menuTable.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				cartTable.getSelectionModel().clearSelection(); 
			}
			
		});
		
		/*If the item list is not empty (we got to this screen from the next one, show the old view table*/
		if(!order.itemList.isEmpty()) {
			cartTable.setItems(itemsForCart);
			totalPriceTxtField.setText(String.valueOf(order.totalPrice));
		}
	}
}
