package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

public class PaymentControllerNew extends Menu implements Initializable {
	public boolean stateEdit = false; 
	public int orderId = 0;
	@FXML
	private TextField txtCus;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtAddress;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtStates;
	@FXML
	private TextField txtZip;
	@FXML
	private TextField txtCountry;
	@FXML
	private TextField txtPhone;
	@FXML
	private TextField txtSales;
	@FXML
	private TextField txtShipVia;
	@FXML
	private TextField txtCheckN;
	
	@FXML
	private TextField txtEmail;	

	@FXML
	private TextField txtRemaining;	
	@FXML
	private TextField txtCreditMemo;	
	
	@FXML
	private TextField txtPaid;	
	@FXML
	private TextField txtpaids;	
	@FXML
	private CheckBox chkPartial;
	
	
	@FXML
	private RadioButton optCheck;
	@FXML
	private RadioButton optCredit;
	@FXML
	private RadioButton optPayPal;
	@FXML
	private RadioButton optCash;
	private OrderDao orderDao;
	String paymentMethod ="";

	@FXML
	private ChoiceBox cbFilter;
	final String[] filter = new String[] { "ID","Name", "Contact", "Phone", "Email" ,"City" };
	private String str_filters = filter[0];
	private Integer  tkey=0;
	
	@FXML
	private Label lblCheckRef;
	@FXML
	private Label lblChekAmount;
	@FXML
	private Label lblApplyDate;
	@FXML
	private Label lblEmailPaypal;
	@FXML
	private TextField txtCheckRef;
	@FXML
	private TextField txtChekAmount;
	@FXML
	private TextField txtEmailPaypal;
	
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
	private TableView<OrderModel> twOrder;
	@FXML
	private TableColumn<OrderModel, Integer> two_orderid;
	@FXML
	private TableColumn<OrderModel, String> two_date;
	@FXML
	private TableColumn<OrderModel, String> two_duedate;
	@FXML
	private TableColumn<OrderModel, String> two_dayduedate;
	@FXML
	private TableColumn<OrderModel, String> two_total;
	@FXML
	private TableColumn<OrderModel, String> two_payment;
	@FXML
	private TableColumn<OrderModel, String> two_prepay;
	
	@FXML
	private TextField keySearch;
	
	@FXML
	private Label lblCurrent;
	@FXML
	private Label lbl30;
	@FXML
	private Label lbl60;
	@FXML
	private Label lbl90;
	@FXML
	private Label lbl91;
	@FXML
	private Label lblOver;
	@FXML
	private CheckBox chkApplyCreditMemo;
	private TextField txtStatusCreditMemo;
	@FXML
	private DatePicker dpApplyDate;
	private float 	All_Total_Memo = 0.00f;
	private float 	All_Total_Memo_Sub = 0.00f;
	private float 	All_Total = 0.00f;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderDao = new OrderDao();
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
		showUIByChoice();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
            		keySearch.requestFocus();
                }
            });
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	  if(event.getCode() == KeyCode.ENTER){
		    		  CustomerModel c = twSearchCus.getSelectionModel().getSelectedItem();
		    		  txtCus.setText(c.getCustomerID());
						System.out.println(c.getCustomerID());
						//System.out.println(c.getAddress());
						if (c.getCustomerID() != null) {
							// tp.setVisible(true);
							 twSearchCus.setVisible(false);
							 keySearch.requestFocus();
							 ShowCus(c);
					       //  getListOrderByCus(c.getCustomerID());
						}
		    	  }
		   }
		});
		twOrder.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
		    		  OrderModel o = twOrder.getSelectionModel().getSelectedItem();
		    		  ShowOr(o);
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
		
		two_orderid.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		two_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		two_duedate.setCellValueFactory(new PropertyValueFactory<>("paymentDueDate"));
		two_dayduedate.setCellValueFactory(new PropertyValueFactory<>("daysDueDate"));
		two_total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		two_payment.setCellValueFactory(new PropertyValueFactory<>("amoutPaid"));
		two_prepay.setCellValueFactory(new PropertyValueFactory<>("previousPayment"));

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
		keySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.DOWN){
		    	  twSearchCus.requestFocus();
		      }
		   }
		});  
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_UP){
		    	  keySearch.requestFocus();
		      }
		   }
		});
		dpApplyDate.setValue(LOCAL_DATE(getCurrentTimeStamp()));
	}
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	public static final LocalDate LOCAL_DATE (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
	public void ShowOr(OrderModel o){
		 System.out.println(o.getPaymentMethod());
		  //System.out.println(o.get);
		  orderId = o.getOrder_id();
		  All_Total = Float.parseFloat(o.getAll_Total());
		  txtPaid.setText(o.getAmoutPaid());
		  if(!o.getApplyDate().equals("")){
			  dpApplyDate.setValue(LOCAL_DATE(o.getApplyDate()));
		  }
		  txtCheckRef.setText(o.getChecknumber());
		  txtChekAmount.setText(o.getAmoutPaid());
		  txtEmailPaypal.setText(o.getEmailPaypal());
		  optCash.setSelected(false);
		  optCheck.setSelected(false);
		  optCredit.setSelected(false);
		  optPayPal.setSelected(false);
		  
		  paymentMethod = o.getPaymentMethod();
		  showUIByChoice();
			if(paymentMethod.equals("Check")){
				optCheck.setSelected(true);
			}
			else if(paymentMethod.equals("Credit/Debit Card")){
				optCredit.setSelected(true);
			}
			else if(paymentMethod.equals("PayPal")){
				optPayPal.setSelected(true);
			}
			else if(paymentMethod.equals("Cash")){
				optCash.setSelected(true);
			}else{
				
			}
			
			try {
				float amoutMemo = orderDao.getAmountMemo(orderId);
				if(amoutMemo>0){
					chkApplyCreditMemo.setSelected(true);
					try {
						checkMemo();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}
	public void ShowCus(CustomerModel customer){
		txtCus.setText(customer.getCustomerID());
		txtName.setText(customer.getCompanyName());
		txtAddress.setText(customer.getAddress());
		txtCity.setText(customer.getStates());
		txtStates.setText(customer.getStates());
		txtZip.setText(customer.getZip());
		txtCountry.setText(customer.getCountry());
		txtPhone.setText(customer.getPhone1());
		txtSales.setText(customer.getSalesperson());
		txtShipVia.setText(customer.getCarrier());
		txtEmail.setText(customer.getEmail());
		twOrder.getItems().clear();
		twOrder.setPlaceholder(new Label("Please wait… Searching Database."));
		Thread thLoadDatas1;
		thLoadDatas1 = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					List<OrderModel> lstOrder = orderDao.getLstPayment(customer.getCustomerID());
					twOrder.getItems().clear();
					twOrder.getItems().addAll(lstOrder);
					twOrder.getSelectionModel().select(0);
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
							//	btnPrint.setDisable(false);
								int count = twOrder.getItems().size();
								twOrder.refresh();
								if(count == 0){
									twOrder.setPlaceholder(new Label("No matching results were found."));
								}else{
									twOrder.requestFocus();
									 OrderModel o = twOrder.getSelectionModel().getSelectedItem();
						    		 ShowOr(o);
						    		 caculatorOverall();
								}
						  }
						});
					this.suspend();

				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					twOrder.setPlaceholder(new Label("No matching results were found."));
				}							
			}
		};

		thLoadDatas1.start();

	}
	public void doubleClicks(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			CustomerModel c = twSearchCus.getSelectionModel().getSelectedItem();
			System.out.println(c.getCustomerID());
			txtCus.setText(c.getCustomerID());
			//System.out.println(c.getAddress());
			if (c.getCustomerID() != null) {
				// tp.setVisible(true);
				 twSearchCus.setVisible(false);
				 keySearch.requestFocus();
				 ShowCus(c);
		      //   getListOrderByCus(c.getCustomerID());
			}
		}
	}
	public void doubleClick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			  OrderModel o = twOrder.getSelectionModel().getSelectedItem();
    		  ShowOr(o);
		}
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
	  		int count = twSearchCus.getItems().size();
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
	public void checkMemo(ActionEvent event) throws IOException {
		checkMemo();
	}
	public void submitPayment(ActionEvent event) throws IOException {
		//processSubmit();
	}
	public void submitPayments(KeyEvent event) throws IOException {
		if(event.getCode() == KeyCode.ENTER){
		//processSubmit();
		}
	}
	public void checkMemo() throws IOException {
		System.out.println(chkApplyCreditMemo.isSelected());
		if(chkApplyCreditMemo.isSelected()){
			if(All_Total<All_Total_Memo){
				All_Total_Memo_Sub = All_Total;
			}else {
				All_Total_Memo_Sub = All_Total_Memo;
			}
			//txtStatusCreditMemo.setText("-$"+ String.format ("%.2f", All_Total_Memo_Sub)+" subtract from $"+All_Total);
			System.out.println("-$"+ String.format ("%.2f", All_Total_Memo_Sub)+" subtract from $"+All_Total);
			txtCreditMemo.setText("-"+ String.format ("%.2f", All_Total_Memo_Sub));
			float totaltemp = All_Total - All_Total_Memo_Sub;
			txtRemaining.setText(String.format ("%.2f", totaltemp));
		}else{
			//txtStatusCreditMemo.setText("");
			All_Total_Memo_Sub = 0.00f;
			txtRemaining.setText(String.format ("%.2f", All_Total));
			txtCreditMemo.setText("-"+ String.format ("%.2f", All_Total_Memo_Sub));
		}
	}
	
	public void payMethod(ActionEvent event) throws IOException {
		//RadioButton  temp = (RadioButton) event.clone();
		//System.out.println(temp.isSelected());
		if(optCheck.isSelected()){
			optCredit.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			paymentMethod = "Check";
			
		}
		else if(optCredit.isSelected()){
			optCheck.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			paymentMethod = "Credit/Debit Card";
			
		}
		else if(optPayPal.isSelected()){
			optCredit.setSelected(false);
			optCheck.setSelected(false);
			optCash.setSelected(false);
			paymentMethod = "PayPal";
			
		}
		else if(optCash.isSelected()){
			optCredit.setSelected(false);
			optPayPal.setSelected(false);
			optCheck.setSelected(false);
			paymentMethod = "Cash";
			
		}else{
		}
		showUIByChoice();
/*		try {
			orderDao.updatePaymentMethod(orderId, paymentMethod);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	public void caculatorOverall() {
		Float total = (float) 0;
		Float totalCurrent = (float) 0;
		Float total30 = (float) 0;
		Float total60 = (float) 0;
		Float total90 = (float) 0;
		Float total91 = (float) 0;

		int count = twOrder.getItems().size();
		for(int i =0;i<count;i++){
			Integer days = twOrder.getItems().get(i).getDaysDueDate();
			String sub = twOrder.getItems().get(i).getAmoutPaid();
			if(sub != null && !sub.isEmpty()){
				total = total + Float.parseFloat(sub);
			}
			if(days == 0){
				totalCurrent = totalCurrent + Float.parseFloat(sub);
			}
			else if(days>0 && days <31){
				total30 = total30 + Float.parseFloat(sub);
			}
			else if(days>30 && days <61){
				total60 = total60 + Float.parseFloat(sub);
			}
			else if(days>60 && days <91){
				total90 = total90 + Float.parseFloat(sub);
			}
			else if(days>90){
				total91 = total91 + Float.parseFloat(sub);
			}
		}
		lblOver.setText("$"+String.format ("%.2f",total));
		lblCurrent.setText("$"+String.format ("%.2f",totalCurrent));
		lbl30.setText("$"+String.format ("%.2f",total30));
		lbl60.setText("$"+String.format ("%.2f",total60));
		lbl90.setText("$"+String.format ("%.2f",total90));
		lbl91.setText("$"+String.format ("%.2f",total91));

	}
	public void showUIByChoice() {
		lblEmailPaypal.setVisible(false);
		txtEmailPaypal.setVisible(false);
		lblApplyDate.setVisible(false);
		dpApplyDate.setVisible(false);
		lblCheckRef.setVisible(false);
		txtCheckRef.setVisible(false);
		txtChekAmount.setVisible(false);
		lblChekAmount.setVisible(false);
		if(paymentMethod.equals("Check")){
			lblApplyDate.setVisible(true);
			dpApplyDate.setVisible(true);
			lblCheckRef.setVisible(true);
			txtCheckRef.setVisible(true);
			txtChekAmount.setVisible(true);
			lblChekAmount.setVisible(true);
			//optCheck.setSelected(true);
		}
		else if(paymentMethod.equals("Credit/Debit Card")){
			lblApplyDate.setVisible(true);
			dpApplyDate.setVisible(true);
			//optCredit.setSelected(true);
		}
		else if(paymentMethod.equals("PayPal")){
			lblApplyDate.setVisible(true);
			dpApplyDate.setVisible(true);
			lblEmailPaypal.setVisible(true);
			txtEmailPaypal.setVisible(true);
			//optPayPal.setSelected(true);
		}
		else if(paymentMethod.equals("Cash")){
			lblApplyDate.setVisible(true);
			dpApplyDate.setVisible(true);
			//optCash.setSelected(true);
		}else{
			
		}
	}
}