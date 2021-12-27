package controllers_gui;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import bitemeclient.PopUpMessages;
import communication.Answer;
import communication.Message;
import communication.Task;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import orders.Item;
import orders.ItemCategory;
import orders.ItemSize;
import orders.ItemWithPicture;
import orders.Order;
import orders.OrderStatus;
import users.SupplierWorker;
import util.DataLists;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * 
 * @author Lior, Guzovsky
 * @author Shimon, Rubin
 * 
 * Class description: This is a class for managing the menu for the
 * supplier. In this screen the supplier worker edit the menu.
 * 
 * @version 23/12/2021
 */
public class SupplierWorkerManageMenuController extends AbstractBiteMeController implements Initializable {
	/**
	 * Class members description:
	 */

	private static FXMLLoader loader;
	private static SupplierWorkerManageMenuController supplierWorkerManageMenuController;
	public static ArrayList<Item> itemListOfMenuFromDB = new ArrayList<>();
	public static ArrayList<Item> updateItems = new ArrayList<>();
	public static ArrayList<ItemWithPicture> updateItemsWithPicture = new ArrayList<>();
	@FXML
	private Button btnExit;

	@FXML
	private Button btnHelp;

	@FXML
	private Button btnBack;

	@FXML
	private TableView<ItemWithPicture> manageMenuTable;

	@FXML
	private TableColumn<ItemWithPicture, ItemCategory> categoryColumn;

	@FXML
	private TableColumn<ItemWithPicture, String> itemNameColumn;

	@FXML
	private TableColumn<ItemWithPicture, ImageView> pictureColumn;

	@FXML
	private TableColumn<ItemWithPicture, ItemSize> sizeColumn;

	@FXML
	private TableColumn<ItemWithPicture, Double> priceColumn;

    
	@FXML
	private Button saveBtn;

	@FXML
	private Text errorText;
	
	@FXML
	private Button addItemBtn;

	@FXML
	private Button removeItemBtn;
	
	
	/**
	 * This is a function for going back to the previous screen.
	 * 
	 * @param event
	 */
	@FXML
	void getBackBtn(ActionEvent event) {
		updateItemsWithPicture.clear();// clear this array for the next time we come back for this screen
		updateItems.clear();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				FXMLLoader loader = new FXMLLoader();
				Pane root;
				try {
					Stage Stage = new Stage();
					Stage.setResizable(false);
					root = loader.load(getClass().getResource("/fxmls/UserPortalOfSupplier.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.initStyle(StageStyle.UNDECORATED);
					scene.setOnMousePressed(pressEvent -> {
					    scene.setOnMouseDragged(dragEvent -> {
					    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
					    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
					    });
					});
					scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
					Stage.setTitle("Main Screen");
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
	 * Exit and logout button
	 * 
	 * @param event
	 */
	@FXML
	void getExitBtn(ActionEvent event) {
		Message message = new Message(Task.LOGOUT, Answer.WAIT_RESPONSE, connectedUser);
		sendToClient(message);
		connectedUser = null;
		Message disconnectMessage = new Message(Task.CLIENT_DICONNECT, Answer.WAIT_RESPONSE, null);
		sendToClient(disconnectMessage);
		System.exit(0);
	}

	/**
	 * This is a function for popping help message if pressed by the user.
	 * 
	 * @param event
	 */
	@FXML
	void getHelpBtn(ActionEvent event) {
		PopUpMessages.helpMessage("On this screen you can manage you'r resturant menu (add, remove or edit items).");
	}

	
	/**
	 * This is a function for saving the changes in the menu.
	 * 
	 * @param event
	 */
	@FXML
	void getSaveBtn(ActionEvent event) {
       // if the menu is empty or the user added new items but did not edit them, it will not let him to save the changes.
       if(updateItemsWithPicture.isEmpty()) {
    	   errorText.setVisible(true);
    	   errorText.setText("The menu is empty! insert values or add new items.");
    	   errorText.setFill(Color.RED);
       }
       else {
    	   updateItems.clear();
    	   for(ItemWithPicture i: updateItemsWithPicture) {
    		   updateItems.add(i.getItem());
    	   }
    	   Message message = new Message(Task.MANAGE_MENU_FINISHED,Answer.WAIT_RESPONSE,updateItems);
       	   sendToClient(message);//send message to the server telling the manage menu is finished and than push into DB
       	   
       	   /*Give notice for the user that the changes have been saved*/
    	   PopUpMessages.updateMessage("Menu changes saved successfully!");
       }
       
	   
	}
	
	/**
	 * This is a function for adding new item.
	 * 
	 * @param event
	 */
	@FXML
	void getAddItemBtn(ActionEvent event) {
		String supplierId=((SupplierWorker) connectedUser).getSupplier().getSupplierId();
		ItemWithPicture addNewRow = new ItemWithPicture(new Item(supplierId, "Item name", ItemCategory.FIRST, ItemSize.REGULAR, DataLists.getDefaultFirstPicturePath(), 10.0));
		manageMenuTable.getItems().add(addNewRow); // we add it to the table, but it wont be saved locally until we click or change some field of this row
	}
	
	
	/**
	 * This is a function for removing existing item.
	 * 
	 * @param event
	 */
	@FXML
    void getRemoveItemBtn(ActionEvent event) {
		if(manageMenuTable.getSelectionModel().getSelectedItem() != null) { // when row selected
    		if(manageMenuTable.getSelectionModel().getSelectedItem() instanceof ItemWithPicture) {
    			ItemWithPicture itemRemoveFromMenu = (ItemWithPicture)manageMenuTable.getSelectionModel().getSelectedItem(); // insert this selected row 
    			updateItemsWithPicture.remove(itemRemoveFromMenu); // remove it from our array
    			manageMenuTable.getItems().remove(itemRemoveFromMenu); // remove it from the table
    		}
    	}
    }
	

	/**
	 * This is the initialization function for this screen.
	 * 
	 * @param primaryStage
	 * @param fxmlPath
	 */
	public void initSupplierWorkerManageMenuScreen() {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					loader = new FXMLLoader();
					Pane root;
					try {
						Stage Stage = new Stage();
						Stage.setResizable(false);
						root = loader.load(getClass().getResource("/fxmls/SUP1ManageMenuScreen.fxml").openStream());
						supplierWorkerManageMenuController = loader.getController();
						Scene scene = new Scene(root);
						Stage.initStyle(StageStyle.UNDECORATED);
						scene.setOnMousePressed(pressEvent -> {
						    scene.setOnMouseDragged(dragEvent -> {
						    	Stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
						    	Stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
						    });
						});
						scene.getStylesheets().add(getClass().getResource("/css/G3_BiteMe_Main_Style_Sheet.css").toExternalForm());
						Stage.setTitle("Manage Menu");
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
     * the menu of the restaurant
     * to the screen in the Table View 
     * for showing it to the user.
     * and it will save the changes locally
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String supplierId=((SupplierWorker) connectedUser).getSupplier().getSupplierId();
		ObservableList<ItemWithPicture> items = FXCollections.observableArrayList();
		Message message = new Message (Task.GET_ITEMS_FOR_MANAGE_MENU,Answer.WAIT_RESPONSE,supplierId);
		sendToClient(message);
		if(itemListOfMenuFromDB == null) {
			errorText.setVisible(true);
			errorText.setText("There are no items in this restaurant!");
    		errorText.setFill(Color.RED);
		}
		else {
			for(Item i : itemListOfMenuFromDB) {
				items.add(new ItemWithPicture(i));
			}
			//add all the wrapper items to the table view
		}
		// Set the id of the supplier for each item
					for(int i=0; i<itemListOfMenuFromDB.size(); i++) {
						itemListOfMenuFromDB.get(i).setSupplierUserId(supplierId);
					}
	
		
		updateItemsWithPicture = new ArrayList<ItemWithPicture>(); //copy the menu we got from DB to our updateItems array
		for(Item i : itemListOfMenuFromDB) {
			updateItemsWithPicture.add(new ItemWithPicture(i));
		}
		
        categoryColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture, ItemCategory>("category"));
    	//set combo box in category column
		categoryColumn.setCellFactory((param) -> new ComboBoxTableCell<>(new StringConverter<ItemCategory>() {

			@Override
			public String toString(ItemCategory object) {
				return object.toString();
			}

			@Override
			public ItemCategory fromString(String string) {
				return ItemCategory.valueOf(string);
			}
			
		}, ItemCategory.values()));
        
		itemNameColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture,String>("ItemName"));
		itemNameColumn.setCellFactory(TextFieldTableCell.forTableColumn()); // set text field in item name column
			
		
		pictureColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture, ImageView>("picture"));
	  
		sizeColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture, ItemSize>("size"));
		//set combo box in size column
		sizeColumn.setCellFactory((param) -> new ComboBoxTableCell<>(new StringConverter<ItemSize>() {

			@Override
			public String toString(ItemSize object) {
				return object.toString();
			}

			@Override
			public ItemSize fromString(String string) {
				return ItemSize.valueOf(string);
			}
			
		}, ItemSize.values()));
				
		
		priceColumn.setCellValueFactory(new PropertyValueFactory<ItemWithPicture, Double>("price"));
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter())); //set text field in price column
		
		manageMenuTable.setItems(items);
		manageMenuTable.setEditable(true); // set the table editable in order to edit items
		
		
		/*Save changes in the table view in the local memory*/
		categoryColumn.setOnEditCommit(
	            event ->
	    {
	    	errorText.setVisible(false);
	    	// if we added new item and did not change the default name, it wont save this item if we try to change other fields
	    	if(event.getRowValue().getItemName() == "Item name") { 
	    		errorText.setVisible(true);
    			errorText.setText("Edit the item name first!");
        		errorText.setFill(Color.RED);
        		manageMenuTable.refresh();
	    	}
	     else { 
	    	updateItemsWithPicture.remove(event.getRowValue());
	    	event.getRowValue().setCategory(event.getNewValue());
	    	switch(event.getNewValue()) {
	    	case SALAD:
	    		event.getRowValue().setPicturePath(DataLists.getDefaultSaladPicturePath());
	    		break;
	    	case FIRST:
	    		event.getRowValue().setPicturePath(DataLists.getDefaultFirstPicturePath());
	    		break;
	    	case MAIN:
	    		event.getRowValue().setPicturePath(DataLists.getDefaultMainPicturePath());
	    		break;
	    	case DESSERT:
	    		event.getRowValue().setPicturePath(DataLists.getDefaultDessertPicturePath());
	    		break;
	    	case DRINK:
	    		event.getRowValue().setPicturePath(DataLists.getDefaultDrinkPicturePath());
	    		break;
			default:
				break;
	    	
	    	}
	    	event.getRowValue().setPicture(new ImageView(new Image(event.getRowValue().getPicturePath(),128,128,false,true)));
	    	updateItemsWithPicture.add(event.getRowValue());
	    }
	   });
		
		itemNameColumn.setOnEditCommit(
	            event ->
	    {
	    	errorText.setVisible(false);
	    	boolean itemAlreadyExist = false;
	    	for(ItemWithPicture i : updateItemsWithPicture) { // check if there is duplicate of item names
	    		if(i.getItemName().toUpperCase().equals(event.getNewValue().toUpperCase())) {
	    			errorText.setVisible(true);
	    			errorText.setText("This item name is already exist!");
	        		errorText.setFill(Color.RED);
	        		itemAlreadyExist = true;
	        		manageMenuTable.refresh();
	    	      }
	    	}
	   
	    	if(!itemAlreadyExist) { // there is not same item name so we can add it
	    		updateItemsWithPicture.remove(event.getRowValue());
		    	event.getRowValue().setItemName(event.getNewValue());
		    	updateItemsWithPicture.add(event.getRowValue());
	    	}
	    });
		
		sizeColumn.setOnEditCommit(
		       event ->
	    {	
	    	errorText.setVisible(false);
	    	// if we added new item and did not change the default name, it wont save this item if we try to change other fields
	    	if(event.getRowValue().getItemName() == "Item name") {
	    		errorText.setVisible(true);
    			errorText.setText("Edit the item name first!");
        		errorText.setFill(Color.RED);
        		manageMenuTable.refresh();
	    	}
	    	else {
	    	updateItemsWithPicture.remove(event.getRowValue());
	    	event.getRowValue().setSize(event.getNewValue());
	    	updateItemsWithPicture.add(event.getRowValue());
	    	}
		});
		
		priceColumn.setOnEditCommit(
	            event ->
	    {
	    	errorText.setVisible(false);
	    	// if we added new item and did not change the default name, it wont save this item if we try to change other fields
	    	if(event.getRowValue().getItemName() == "Item name") {
	    		errorText.setVisible(true);
    			errorText.setText("Edit the item name first!");
        		errorText.setFill(Color.RED);
        		manageMenuTable.refresh();
	    	}
	    	
	    	else { 
	    	  if(event.getNewValue() <= 0) { //check if the user did not enter negative price
	    		errorText.setText("Price cannot be un-positive!");
	    		errorText.setFill(Color.RED);
	    		manageMenuTable.refresh();
	    	}
	    		    	
	    	  else { // the user enter positive price
	    		updateItemsWithPicture.remove(event.getRowValue());
	    		event.getRowValue().setPrice(event.getNewValue());
	    		updateItemsWithPicture.add(event.getRowValue());
	    	}
	    }
	    	
	  });
		
		
	}

}
