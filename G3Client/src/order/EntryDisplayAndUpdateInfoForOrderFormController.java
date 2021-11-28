package order;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import biteMeClient.BiteMeClientUI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Order;

public class EntryDisplayAndUpdateInfoForOrderFormController implements Initializable {
	private Order orderToDisplay;
	@FXML
	private Label lblResturantName;

	@FXML
	private TextField txtResturantName;

	@FXML
	private Label lblOrderNumber;

	@FXML
	private TextField txtOrderNumber;

	@FXML
	private Label lblPhoneNumber;

	@FXML
	private TextField txtPhoneNumber;

	@FXML
	private Label lblOrderAddress;

	@FXML
	private TextField txtOrderAddress;

	@FXML
	private DatePicker dateOrderTime;

	@FXML
	private TextField txtTime;

	@FXML
	private Label lblTime;

	@FXML
	private ComboBox<String> cmbOrderType;

	@FXML
	private Button btnclose = null;

	@FXML
	private Button btnsave = null;

	@FXML
	private Label txtMainOrderGUI;

	ObservableList<String> list;

	/* get order information from server , parse the data */
	public void loadOrder(Order o1) {
		this.orderToDisplay = o1;
		txtResturantName.setText(orderToDisplay.getResturantName());
		txtResturantName.setEditable(false);
		txtOrderNumber.setText(orderToDisplay.getOrderNumber());
		txtOrderNumber.setEditable(false);
		String[] orderSplitDate = orderToDisplay.getOrderTime().split("_");
		String Date = orderSplitDate[0];
		String Time = orderSplitDate[1];

		txtTime.setText(Time);
		txtTime.setEditable(false);

		dateOrderTime.setValue(LOCAL_DATE(Date));
		dateOrderTime.setDisable(true);
		dateOrderTime.setStyle("-fx-opacity: 1");
		dateOrderTime.getEditor().setStyle("-fx-opacity: 1");

		txtPhoneNumber.setText(orderToDisplay.getPhoneNumber());
		txtPhoneNumber.setEditable(false);
		txtOrderAddress.setText(orderToDisplay.getOrderAddress());
		cmbOrderType.setValue(orderToDisplay.getOrderType());
	}

	/* Parse the date */
	public static final LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	// creating list of Order delivery type
	private void setDeliveryComboBox() {
		ArrayList<String> deliveryComboBoxList = new ArrayList<String>();
		deliveryComboBoxList.add("Order-In");
		deliveryComboBoxList.add("Take-Away");
		deliveryComboBoxList.add("Delivery-Robotic");
		deliveryComboBoxList.add("Delivery-Regular");

		list = FXCollections.observableArrayList(deliveryComboBoxList);
		cmbOrderType.setItems(list);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setDeliveryComboBox();
	}

	/* using close button hide window and go backwards */
	public void getCloseBtn(ActionEvent event) throws Exception {
		EntryOrderNumberFormController prevWindowController = new EntryOrderNumberFormController();
		Stage primaryStage = new Stage();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		prevWindowController.start(primaryStage);

	}

	/* After doing Save update DB using the server */
	public void getSaveBtn(ActionEvent event) throws Exception {
		this.orderToDisplay.setOrderAddress(txtOrderAddress.getText());
		this.orderToDisplay.setOrderType(cmbOrderType.getValue());
		String msg = "update" + " " + orderToDisplay.toString();
		BiteMeClientUI.biteMeClientController.accept(msg);
	}

	public void displayUpdateMessage(String message) {
		Platform.runLater(() -> {
			txtMainOrderGUI.setText(message);
		});

	}

}
