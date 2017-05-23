package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.CustomerDao;
import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderModel;
import application.Utill.Menu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ExpressPaymentController extends Menu implements Initializable {
	public boolean stateEdit = false; 
	
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
	private TableColumn<OrderModel, String> tp_total;
	@FXML
	private TableColumn<OrderModel, String> tp_payment;
	
	@FXML
	private TableView<CustomerModel> twSearchCus;
	@FXML
	private TableColumn<CustomerModel, String> tcs_cus;
	@FXML
	private TableColumn<CustomerModel, String> tcs_name;
	@FXML
	private TableColumn<CustomerModel, String> tcs_contact;
	@FXML
	private TableColumn<CustomerModel, String> tcs_phone;
	@FXML
	private TableColumn<CustomerModel, String> tcs_email;
	@FXML
	private TableColumn<CustomerModel, String> tcs_city;
	
	@FXML
	private TextField keySearch;
	@FXML
	private ChoiceBox cbFilter;
	final String[] filter = new String[] { "ID","Name", "Contact", "Phone", "Email" ,"City" };
	private String str_filters = filter[0];
	private Integer  tkey=0;
	
	public Thread thLoadDatas1;
	private OrderDao orderDao;	
	List<OrderModel> list;
	


	@SuppressWarnings("deprecation")
	public void getListOrderByCus(String customerId){
    	tp.setPlaceholder(new Label("Please wait… Searching Database."));
    	if(thLoadDatas1 != null){
    		thLoadDatas1.stop();
    		//System.out.println(thLoadDatas1.getState());
    	}
    	thLoadDatas1 = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					List<OrderModel> lstOrder = orderDao.getLstPayment(customerId);
					tp.getItems().clear();
					tp.getItems().addAll(lstOrder);
					tp.getSelectionModel().select(0);
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
							//	btnPrint.setDisable(false);
								int count = tp.getItems().size();
								tp.refresh();
								if(count == 0){
									tp.setPlaceholder(new Label("No matching results were found."));
								}
						  }
						});
					this.suspend();

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					tp.setPlaceholder(new Label("No matching results were found."));
				}							
			}
		};

		thLoadDatas1.start();

    
    }
	public void getListOrderExpress(){
    	tp.setPlaceholder(new Label("Please wait… Searching Database."));
    	if(thLoadDatas1 != null){
    		thLoadDatas1.stop();
    		//System.out.println(thLoadDatas1.getState());
    	}
    	thLoadDatas1 = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					List<OrderModel> lstOrder = orderDao.getLstPaymentExpress();
					tp.getItems().clear();
					tp.getItems().addAll(lstOrder);
					tp.getSelectionModel().select(0);
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
							//	btnPrint.setDisable(false);
								int count = tp.getItems().size();
								tp.refresh();
								if(count == 0){
									tp.setPlaceholder(new Label("No matching results were found."));
								}
						  }
						});
					this.suspend();

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					tp.setPlaceholder(new Label("No matching results were found."));
				}							
			}
		};

		thLoadDatas1.start();

    
    }
	public void actionSearchCus() throws IOException {
		  twSearchCus.setVisible(true);
		  twSearchCus.setPlaceholder(new Label("Please wait… Searching Database."));
		  System.out.println("key ="+keySearch.getText());
		  System.out.println("key ="+str_filters);
	  	  CustomerDao customerDao = new CustomerDao();
	  	  try {
	  		long start = System.nanoTime();    
	  		List<CustomerModel> lstCustomer = customerDao.getLstCustomerSearchc(keySearch.getText(),str_filters);
	  		long elapsedTime = System.nanoTime() - start;
	  		double seconds = (double)elapsedTime / 1000000000.0;
	  		System.out.println("selconds: "+seconds);
	  		// show result
	  		twSearchCus.getItems().clear();
	  		twSearchCus.getItems().addAll(lstCustomer);
	  		twSearchCus.getSelectionModel().select(0);
	  		int count = tp.getItems().size();
	  		twSearchCus.refresh();
			if(count == 0){
				twSearchCus.setPlaceholder(new Label("No matching results were found."));
			}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
    public void actionSearchCus(EventHandler event) throws IOException {          
    	actionSearchCus();
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inits();
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	  if(event.getCode() == KeyCode.ENTER){
		    		  CustomerModel c = twSearchCus.getSelectionModel().getSelectedItem();
		    		   keySearch.setText(c.getCustomerID());
						System.out.println(c.getCustomerID());
						//System.out.println(c.getAddress());
						if (c.getCustomerID() != null) {
							// tp.setVisible(true);
							 twSearchCus.setVisible(false);
							 keySearch.requestFocus();
					         getListOrderByCus(c.getCustomerID());
						}
		    	  }
		   }
		});
		twSearchCus.setVisible(false);
		tcs_cus.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
		tcs_name.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		tcs_contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
		tcs_phone.setCellValueFactory(new PropertyValueFactory<>("Phone1"));
		tcs_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tcs_city.setCellValueFactory(new PropertyValueFactory<>("City"));
		twSearchCus.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	//isShowSearchCus(true);
		        }
		        else
		        {
		        	//isShowSearchCus(false);
		        }
		    }
		});
		cbFilter.setItems(FXCollections.observableArrayList("ID", "Customer Name", "Contact", "Phone", "Email" ,"City"));
		cbFilter.setValue("ID");
		cbFilter.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				str_filters = filter[new_value.intValue()];
				tkey = new_value.intValue();
				System.out.println(str_filters); //////////////////////////////////
				//gotoSearchCus();
				try {
					actionSearchCus();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
/*		keySearch.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){        	
		        	 String  value = newValue;
			         System.out.println(value);
			         twSearchCus.setVisible(true);
			         
		        }

		    }
		});*/
/*		keySearch.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){        	
			    	if (newValue.matches("\\d*")) {
			            String  value = newValue;
			            System.out.println(value);
			            tp.setVisible(true);
			            getListOrderByCus(value);
			        } else {
			        	keySearch.setText(oldValue);
			        }
		        }

		    }
		});*/
		tp_orderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		tp_sumitDate.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		tp_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		tp_shipvia.setCellValueFactory(new PropertyValueFactory<>("Customer_ship"));
		tp_customerId.setCellValueFactory(new PropertyValueFactory<>("ClientCustomerID"));
		tp_email.setCellValueFactory(new PropertyValueFactory<>("Customer_email"));
		tp_storeName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		tp_total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		tp_payment.setCellValueFactory(new PropertyValueFactory<>("payments"));
		orderDao = new OrderDao();
		tp.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					int  orderId = tp.getSelectionModel().getSelectedItems().get(0).getOrder_id();
					//keySearch.setText(String.valueOf(orderId));
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
		//tp.setVisible(false);
		//thLoadData.start();
		
		getListOrderExpress();

	}
	public void doubleClick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			int  orderId = tp.getSelectionModel().getSelectedItems().get(0).getOrder_id();
			// keySearch.setText(String.valueOf(orderId));
			try {
				System.out.println(orderId);
				gotoPaymentDetail(orderId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void doubleClicks(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			CustomerModel c = twSearchCus.getSelectionModel().getSelectedItem();
			System.out.println(c.getCustomerID());
			keySearch.setText(c.getCustomerID());
			//System.out.println(c.getAddress());
			if (c.getCustomerID() != null) {
				 tp.setVisible(true);
				 twSearchCus.setVisible(false);
				 keySearch.requestFocus();
		         getListOrderByCus(c.getCustomerID());
			}
		}
	}
	   public void gotoPaymentDetail(int orderId) throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Express Payment Details");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ExpressPaymentDetail.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        ExpressPaymentDetailController controller = (ExpressPaymentDetailController) myLoader.getController();
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
}