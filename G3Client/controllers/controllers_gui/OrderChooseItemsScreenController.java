package controllers_gui;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    @FXML
    private Button btnBack;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHelp;

    @FXML
    private Button addToCartBtn;

    @FXML
    private TableView<?> cartTable;

    @FXML
    private TableColumn<?, ?> itemCartColumn;

    @FXML
    private TableColumn<?, ?> sizeCartColumn;

    @FXML
    private TableColumn<?, ?> priceCartColumn;

    @FXML
    private TableColumn<?, ?> commentCartColumn;

    @FXML
    private Button removeItemBtn;

    @FXML
    private TableView<?> menuTable;

    @FXML
    private TableColumn<?, ?> menuCategoryColumn;

    @FXML
    private TableColumn<?, ?> itemMenuColumn;

    @FXML
    private TableColumn<?, ?> pictureMenuColumn;

    @FXML
    private TableColumn<?, ?> sizeMenuColumn;

    @FXML
    private TableColumn<?, ?> priceMenuColumn;

    @FXML
    private TextField totalPriceTxtField;

    @FXML
    private Button nextBtn;

    @FXML
    private Text errorText;

    @FXML
    private Text errorText1;

    @FXML
    void getAddToCartBtn(ActionEvent event) {

    }

    /**
     * Back button for the restaurant
     * picking screen.
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
					root = loader.load(getClass().getResource("/fxmls/ORD2ChooseResturantInOrderScreen.fxml").openStream());
					Scene scene = new Scene(root);
					Stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
						@Override
						public void handle(WindowEvent event) { 
							event.consume();
							Stage.close();
						}
					});
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
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

    @FXML
    void getRemoveItemBtn(ActionEvent event) {
    	
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
					//scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
					Stage.setTitle("Choose Items");
					Stage.setScene(scene);
					Stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}
	
	

}
