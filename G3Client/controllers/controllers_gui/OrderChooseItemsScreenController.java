package controllers_gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orders.Item;
import orders.ItemCategory;
import orders.ItemSize;
import orders.Order;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * Class description: 
 * This is a class for 
 * controlling the UI of the item 
 * choosing for the private customer.
 * 
 * @version 14/12/2021
 */
public class OrderChooseItemsScreenController extends AbstractBiteMeController implements Initializable{
	/**
	 * Class members description:
	 */
	
	private static FXMLLoader loader;
    private static OrderChooseItemsScreenController orderChooseItemsScreenController;
	private static String restaurantID;
	private static String restaurantName;
	public static ArrayList<Item> itemListOfMenuFromDB = new ArrayList<>();
	private static Order order = new Order(null,null,null); //create empty order and update it during the process
	private static ObservableList<Item> itemsForCart = FXCollections.observableArrayList();
	
    @FXML
    private Button btnBack;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHelp;

    @FXML
    private Button addToCartBtn;

    @FXML
    private TableView<Item> cartTable;

    @FXML
    private TableColumn<Item, String> itemCartColumn;

    @FXML
    private TableColumn<Item, ItemSize> sizeCartColumn;

    @FXML
    private TableColumn<Item, Double> priceCartColumn;

    @FXML
    private TableColumn<Item, String> commentCartColumn;

    @FXML
    private Button removeItemBtn;

    @FXML
    private TableView<Item> menuTable;

    @FXML
    private TableColumn<Item, ItemCategory> menuCategoryColumn;

    @FXML
    private TableColumn<Item, String> itemMenuColumn;

    @FXML
    private TableColumn<Item, String> pictureMenuColumn;

    @FXML
    private TableColumn<Item, ItemSize> sizeMenuColumn;

    @FXML
    private TableColumn<Item, Double> priceMenuColumn;

    @FXML
    private TextField totalPriceTxtField;

    @FXML
    private Button nextBtn;

    @FXML
    private Text errorText;

    @FXML
    private Text errorText1;

    /**
     * Check if the user clicked
     * on any row in the menu table view.
     * if he did, add to Cart table view
     * else, print error message using errorText
     * 
     * @param event
     */
    @FXML
    void getAddToCartBtn(ActionEvent event) {
    	if(menuTable.getSelectionModel().getSelectedItem() != null) {
    		if(menuTable.getSelectionModel().getSelectedItem() instanceof Item) {
    			errorText1.setVisible(false); // if the user added an item to the cart set invisible
    			Item itemAddToCart = new Item((Item)menuTable.getSelectionModel().getSelectedItem());
    			itemAddToCart.setSupplierUserId(restaurantID);
    			order.itemList.add(itemAddToCart);//add item to cart
    			order.totalPrice += itemAddToCart.getPrice();
    			//print the new sum to the user
    			totalPriceTxtField.setText(String.valueOf(order.totalPrice));
    			itemsForCart.add(itemAddToCart);
    			cartTable.setItems(itemsForCart);
    			commentCartColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item,String>>() {
    				@Override
    				public void handle(CellEditEvent<Item, String> event) {
    					//get the whole object from the row
    					Item item = event.getRowValue(); 
    					//get the item from the arraylist
    					item.setComment(event.getNewValue());
    				}
    			});
    		}
    	}
    }

    /**
     * Back button for the restaurant
     * picking screen.
     * 
     * @param event
     */
    @FXML
    void getBackBtn(ActionEvent event) {
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
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Choose restaurant");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				((Node) event.getSource()).getScene().getWindow().hide();
			}
		});
    }

    @FXML
    void getBtnNext(ActionEvent event) {
    	//first check if the order item array list is not empty, if it is set text to the user and deny going to the next screen
    	if(order.itemList.size()!=0) {
    		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
        	OrderChooseSupplyMethodScreenController orderChooseSupplyMethodScreenController = new OrderChooseSupplyMethodScreenController();
        	orderChooseSupplyMethodScreenController.initChooseSupplyMethodScreen(order); // call the init of the next screen	
    	}
    	else {
    		//set error message to the user
    		errorText1.setVisible(true); 
    		errorText1.setText("You didn't choose any items, please choose them first!");
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
    	PopUpMessages.helpMessage("Please choose the items you want and add them to the chart, than continue!");
    }

    /**
     * Remove item from cart
     * and update price and order 
     * Object.
     * 
     * @param event
     */
    @FXML
    void getRemoveItemBtn(ActionEvent event) {
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
     */
    public void initChooseItemsScreen(String pickedRestaurantId, String pickedRestaurntName ) {
    	restaurantID = pickedRestaurantId;
    	restaurantName = pickedRestaurntName;
    	//set data for the order here because the user can change to different screen if he wants!
    	order.setBranch(connectedUser.getHomeBranch()); //set to the branch the user chose in W4C identification stage
    	order.setSupplierUserId(restaurantID);
    	order.setCustomerUserId(connectedUser.getUserId());
    	
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
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
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
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		totalPriceTxtField.setDisable(true); //set total price disable so that the user cant edit the price of the order
		ObservableList<Item> items = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_ITEMS_FOR_ORDER_MENU,Answer.WAIT_RESPONSE,restaurantID);
		sendToClient(message);
		if(itemListOfMenuFromDB == null) {
			errorText1.setVisible(true);
			errorText1.setText("There are no Items in this restaurant!");
    		errorText1.setFill(Color.RED);
		}
		else {
			//add all the wrapper items to the table view
			items.addAll(itemListOfMenuFromDB);
		}
		cartTable.setEditable(true); //enables to add text fields to the comment column
		
		menuCategoryColumn.setCellValueFactory(new PropertyValueFactory<Item,ItemCategory>("category"));
		
		itemMenuColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("itemName"));
		itemCartColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("itemName"));
		
		pictureMenuColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("picturePath"));
		
		sizeMenuColumn.setCellValueFactory(new PropertyValueFactory<Item,ItemSize>("size"));
		sizeCartColumn.setCellValueFactory(new PropertyValueFactory<Item,ItemSize>("size"));
		
		priceMenuColumn.setCellValueFactory(new PropertyValueFactory<Item,Double>("price"));
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
