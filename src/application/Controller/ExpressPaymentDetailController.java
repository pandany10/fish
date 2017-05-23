package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;

import application.Dao.CardDao;
import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderDetailModel;
import application.Model.ProductModel;
import application.Utill.Menu;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ExpressPaymentDetailController extends Menu implements Initializable {
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
	private TextField txtCardNumber;
	@FXML
	private TextField txtExpired;
	@FXML
	private TextField txtExpired1;
	@FXML
	private TextField txtCVC;
	@FXML
	private Button btnSendPayment;
	@FXML
	private TextField txtTotalStore;
	@FXML
	private TextField txtTotalExo;
	
	@FXML
	private Label lblcart;
	@FXML
	private Label lblExpired;
	@FXML
	private Label lblcvc;
	
	
	@FXML
	private TextField txtEmail;	
	@FXML
	private TextField txtTotal;	
	
	
	@FXML
	private TableView<ProductModel> td;
	@FXML
	private TableColumn<ProductModel, String> td_sku;
	@FXML
	private TableColumn<ProductModel, String> td_qty;
	@FXML
	private TableColumn<ProductModel, String> td_size;
	@FXML
	private TableColumn<ProductModel, String> td_name;
	@FXML
	private TableColumn<ProductModel, String> td_scien;
	@FXML
	private TableColumn<ProductModel, String> td_price;
	@FXML
	private TableColumn<ProductModel, String> td_total;
	@FXML
	private TableColumn<ProductModel, String> td_commission;
	
	
	@FXML
	private Label statusSPayment;
	
	@FXML
	private Button btnSubmit;
	
	private OrderDao orderDao;
	private CardDao cardDao;
	private CustomerModel customer = new CustomerModel();
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inits();
		//tp.setEditable(true);
		//tp.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//tp.getSelectionModel().setCellSelectionEnabled(true);
		td.setEditable(true);
		orderDao = new OrderDao();
		cardDao = new CardDao();
		td_sku.setCellValueFactory(new PropertyValueFactory<>("sku"));
		td_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		td_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		td_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		td_scien.setCellValueFactory(new PropertyValueFactory<>("scientific"));
		td_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		td_total.setCellValueFactory(new PropertyValueFactory<>("total"));
		td_commission.setCellValueFactory(new PropertyValueFactory<>("ExpressCommisson"));
		td_commission.setCellFactory(createNumberCellFactoryd());
		td_commission.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {
			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "commission");
			}
		});
		td.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		td.getSelectionModel().setCellSelectionEnabled(true);

		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					OrderDetailModel orderDetail = orderDao.getOrderDetail(orderId);
					customer = orderDetail.getCustomer();
					List<ProductModel> lstProduct = orderDetail.getLstProduct();
					td.getItems().addAll(lstProduct);
					
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
							  txtTotal.setText(String.format("%.2f", lstProduct.get(0).getAll_Total()));
							  callTotal();
							ShowCus( customer);
						  }
						});
					//
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
	public void callTotal(){
		Float totalS = (float) 0;
		Float totalE = (float) 0;
		int count = td.getItems().size();
		for(int i =0;i<count;i++){
			String price = td.getItems().get(i).getPrice();
			Integer commission = Integer.parseInt(td.getItems().get(i).getExpressCommisson());
			if(price != null && !price.isEmpty()){
				totalS = totalS + (Float.parseFloat(price)*commission)/100;
			}
		}
		totalE =  Float.parseFloat(txtTotal.getText()) - totalS;
		txtTotalStore.setText(String.format("%.2f", totalS));
		txtTotalExo.setText(String.format("%.2f", totalE));
		System.out.println(totalE);
		System.out.println(totalS);
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
		if(customer.getStripe_user_id() == ""){
			statusSPayment.setText("Store is yet connect to Stripe, Please connect can submit payment.");
			btnSendPayment.setDisable(true);
		}
	}
	
	public void submitPayment(ActionEvent event) throws IOException {
		processSubmit();
	}
	public void submitPayments(KeyEvent event) throws IOException {
		if(event.getCode() == KeyCode.ENTER){
		processSubmit();
		}
	}
	public void processSubmit(){
		statusSPayment.setText("Please wait ... the payment is processing.");
		
	}
    public void actionSendPayment(ActionEvent event) throws IOException {          
    	if(chkSendPayment()){
    		btnSendPayment.setDisable(true);
        	System.out.println("actionSendPayment");
        	String carnumber = txtCardNumber.getText();
        	String expiredDay = txtExpired.getText();
        	String expiredYear = txtExpired1.getText();
        	String CVC = txtCVC.getText();
        	
        	Stripe.apiKey = "sk_test_4jiVdrjyyvvjvrXIfLTlocNZ";

    		Map<String, Object> tokenParams = new HashMap<String, Object>();
    		Map<String, Object> cardParams = new HashMap<String, Object>();
    		cardParams.put("number", carnumber);
    		cardParams.put("exp_month", expiredDay);
    		cardParams.put("exp_year", expiredYear);
    		cardParams.put("cvc", CVC);
    		tokenParams.put("card", cardParams);

    		try {
    			Token token = Token.create(tokenParams);
    			String tokenid = token.getId();
    			Token token2 = Token.create(tokenParams);
    			String tokenid2 = token2.getId();
    			System.out.println(tokenid);
    			System.out.println(tokenid2);

    			// payment for main site
				int totalMain =(int)(Float.parseFloat(txtTotalExo.getText())*100);
				int totalStore = (int)(Float.parseFloat(txtTotalStore.getText())*100);
				Float amountPaid = Float.parseFloat(txtTotal.getText());
				Map<String, Object> chargeParams = new HashMap<String, Object>();
				String chargeFor = "Charge for :" + txtName.getText() + " " + txtEmail.getText() + " OrderID:"+orderId;
				chargeParams.put("amount", totalMain);
				chargeParams.put("currency", "usd");
				chargeParams.put("description", chargeFor);
				chargeParams.put("source", tokenid);
				// ^ obtained with Stripe.js
				Charge chargemain = Charge.create(chargeParams);
				//System.out.println(chargemain);
				
				// payment for main Store
				// Token is created using Stripe.js or Checkout!
				// Get the payment token submitted by the form:
			//	String token = request.getParameter("stripeToken");

				// Create a Charge:
		/*		Map<String, Object> params = new HashMap<String, Object>();
				params.put("amount", totalStore);
				params.put("currency", "usd");
				params.put("description", chargeFor);
				params.put("source", tokenid2);

				RequestOptions requestOptions = RequestOptions.builder().setStripeAccount(customer.getStripe_user_id()).build();
				Charge chargeStore = Charge.create(params, requestOptions);
				System.out.println(chargeStore);*/
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("amount", totalStore);
				params.put("currency", "usd");
				params.put("description", chargeFor);
				params.put("source", tokenid2);
				Map<String, Object> destinationParams = new HashMap<String, Object>();
				destinationParams.put("account", customer.getStripe_user_id());
				params.put("destination", destinationParams);
				Charge chargeStore = Charge.create(params);
				//System.out.println(chargeStore);
				if(chargemain.getId().length()>0 && chargeStore.getId().length()>0){
					//
					try {
						orderDao.updateAmoutPaid(orderId, amountPaid);
						orderDao.updatePaymentMethod(orderId, "Stripe");
						statusSPayment.setText("Submit Payment Success.");
						btnSendPayment.setDisable(true);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					statusSPayment.setText("Have err submit payment.");
					btnSendPayment.setDisable(false);
				}
    		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException
    				| APIException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			btnSendPayment.setDisable(false);
    		}
    	}else{
    		
    	}
    }
    public boolean chkSendPayment(){
    	boolean chk = true;
    	lblcart.setText("");
    	lblcvc.setText("");
    	lblExpired.setText("");
    	String carnumber = txtCardNumber.getText();
    	String expired = "";
    	String cvc = txtCVC.getText();
    	if(cvc.length()==0){
    		chk = false;
    		txtCVC.requestFocus();
    		lblcart.setText("CVC is required");
    	}
    	if(txtExpired.getText().equals("") || txtExpired1.getText().equals("")){
    		chk = false;
    		if(txtExpired.getText().equals("")){
    			txtExpired.requestFocus();
    		}
    		if(txtExpired1.getText().equals("")){
    			txtExpired1.requestFocus();
    		}
    		lblcart.setText("Expired is required");
    	}else{
    		expired = txtExpired+"/"+txtExpired1;
    	}
    	if(carnumber.length()!=16){
    		chk = false;
    		txtCardNumber.requestFocus();
    		lblcart.setText("Card number  is required and 16 number.");
    	}
    	return chk;
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
	private Callback<TableColumn<ProductModel, String>, TableCell<ProductModel, String>> createNumberCellFactoryd() {

		Callback<TableColumn<ProductModel, String>, TableCell<ProductModel, String>> factory = TextFieldTableCell
				.forTableColumn(new StringConverter<String>() {

					@Override
					public String fromString(String string) {
						return string.toString();
					}

					@Override
					public String toString(String object) {
						// TODO Auto-generated method stub
						if (object == null) {
							object = "";
						}
						return object.toString();
					}

				});

		return factory;
	}
	public void changeTextfield(TableColumn.CellEditEvent<ProductModel, String> event, String type) {
		ProductModel item = event.getRowValue();
		String news = event.getNewValue();
		if (type.equals("commission")) {
			item.setExpressCommisson(news);
		}
		callTotal();
		td.refresh();
		td.requestFocus();
		int in = item.getId();
		//System.out.println("id:"+in+"----"+news);
		try {
			orderDao.updateEcommission(in, news);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}