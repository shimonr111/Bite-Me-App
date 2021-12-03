package prototypedontuse;
//
//package prototype;
//
//import bitemeclient.BiteMeClientCommunication;
//import bitemeclient.BiteMeClientUI;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import orders.Order;
//
//public class EntryOrderNumberFormController {
//	private EntryDisplayAndUpdateInfoForOrderFormController displayAndUpdateFormController;
//	private static FXMLLoader loaderOfDisplayAndUpdateForm;
//
//	@FXML
//	private Button btnExit = null;
//
//	@FXML
//	private Button btnSend = null;
//
//	@FXML
//	private TextField txtInitalOrderNumber;
//
//	@FXML
//	private Label txtSearchOrderGUI;
//
//	private String getInitalOrderNumber() {
//		return txtInitalOrderNumber.getText();
//	}
//
//	/* button for sending the Order number to the server */
//	public void Send(ActionEvent event) throws Exception {
//		String orderNum;
//		loaderOfDisplayAndUpdateForm = new FXMLLoader();
//
//		orderNum = getInitalOrderNumber();
//		if (orderNum.trim().isEmpty()) {
//			displayOrderStatusIsExistInOrderNumberForm("You must enter Order number");
//		} else {
//			/*
//			 * create a query for the DB to check if the order id exist -> sent to the
//			 * server
//			 */
//			orderNum = "find" + " " + orderNum;
//			BiteMeClientUI.biteMeClientController.accept(orderNum); // send to the server
//
//			if (BiteMeClientCommunication.displayedOrder.getOrderNumber() == null) {
//				displayOrderStatusIsExistInOrderNumberForm("Order ID Not Found");
//			} else {
//				System.out.println("Order Number Found"); /* if ID is found switch to next form */
//				((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
//				Stage primaryStage = new Stage();
//				Pane root = loaderOfDisplayAndUpdateForm
//						.load(getClass().getResource("/order/EntryDisplayAndUpdateInfoForOrderForm.fxml").openStream());
//				EntryDisplayAndUpdateInfoForOrderFormController OrderMainFrameController = loaderOfDisplayAndUpdateForm
//						.getController();
//				OrderMainFrameController.loadOrder(BiteMeClientCommunication.displayedOrder);
//
//				Scene scene = new Scene(root);
//				scene.getStylesheets().add(
//						getClass().getResource("/order/EntryDisplayAndUpdateInfoForOrderForm.css").toExternalForm());
//				primaryStage.setTitle("Order");
//
//				primaryStage.setScene(scene);
//				primaryStage.show(); /* load new form */
//			}
//		}
//	}
//
//	public void start(Stage primaryStage) throws Exception {
//		Parent root = FXMLLoader.load(getClass().getResource("/order/EntryOrderNumberForm.fxml"));
//
//		Scene scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("/order/EntryOrderNumberForm.css").toExternalForm());
//		primaryStage.setTitle("Order");
//		primaryStage.setScene(scene);
//		primaryStage.show();
//	}
//
//	public void getExitBtn(ActionEvent event) throws Exception {
//		System.exit(0);
//	}
//
//	public void loadOrder(Order orderFromServer) {
//		this.displayAndUpdateFormController.loadOrder(orderFromServer);
//	}
//
//	public void displayOrderStatusIsExistInOrderNumberForm(String message) {
//		Platform.runLater(() -> {
//			txtSearchOrderGUI.setText(message);
//		});
//
//	}
//
//	public static FXMLLoader getClientMainFrameLoader() {
//		return loaderOfDisplayAndUpdateForm;
//	}
//}
