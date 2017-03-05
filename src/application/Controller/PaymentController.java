package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.OrderDao;
import application.Model.OrderModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaymentController implements Initializable {
	public boolean stateEdit = false; 
	Stage prevStage;
	
	@FXML
	private TableView<OrderModel> tp;
	@FXML
	private TableColumn<OrderModel, Integer> tp_orderId;
	@FXML
	private TableColumn<OrderModel, String> tp_sumitDate;
	@FXML
	private TableColumn<OrderModel, String> tp_status;
	@FXML
	private TableColumn<OrderModel, String> tp_shipvia;
	@FXML
	private TableColumn<OrderModel, String> tp_customerId;
	@FXML
	private TableColumn<OrderModel, String> tp_email;
	@FXML
	private TableColumn<OrderModel, String> tp_storeName;
	@FXML
	private TableColumn<OrderModel, Float> tp_total;
	
	private OrderDao orderDao;	
	List<OrderModel> list;
	
	public void setPrevStage(Stage stage) {
		this.prevStage = stage;
	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tp_orderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		tp_sumitDate.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		tp_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		tp_shipvia.setCellValueFactory(new PropertyValueFactory<>("Customer_ship"));
		tp_customerId.setCellValueFactory(new PropertyValueFactory<>("ClientCustomerID"));
		tp_email.setCellValueFactory(new PropertyValueFactory<>("Customer_email"));
		tp_storeName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		tp_total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		orderDao = new OrderDao();
		tp.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					int  orderId = tp.getSelectionModel().getSelectedItems().get(0).getOrder_id();
					try {
						System.out.println(orderId);
						gotoPaymentDetail(orderId);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					list = orderDao.getLstPayment();
					tp.getItems().addAll(list);
					tp.getSelectionModel().selectFirst();
					this.suspend();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		thLoadData.start();

	}
	public void doubleClick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			int  orderId = tp.getSelectionModel().getSelectedItems().get(0).getOrder_id();
			try {
				System.out.println(orderId);
				gotoPaymentDetail(orderId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	   public void gotoPaymentDetail(int orderId) throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Payment Details");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/PaymentDetail.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        PaymentDetailController controller = (PaymentDetailController) myLoader.getController();
	 	    controller.setPrevStage(stage);
	 	    controller.orderId = orderId;
	        Scene scene = new Scene(myPane);
	        stage.setScene(scene);
	        prevStage.close();
	        stage.setResizable(false);
	        stage.show();
	        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	    	    @Override
	    	    public void handle(KeyEvent evt) {
	    	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
	    	        	if(!controller.stateEdit){
	    	        		prevStage.show();
	    	        		stage.close();
	    	        	}
	    	        }
	    	    }
	    	});
	     }
	public void gotoHome() throws IOException {          
			Stage stage = new Stage();
			stage.setTitle("Home");
			stage.getIcons().add(new Image("file:resources/images/icon.png"));
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Home.fxml"));
			Pane myPane = (Pane) myLoader.load();

			HomeController controller = (HomeController) myLoader.getController();
			controller.setPrevStage(stage);
			Scene scene = new Scene(myPane);
			stage.setScene(scene);

			prevStage.close();
			stage.setResizable(false);
			stage.show();
	}
}