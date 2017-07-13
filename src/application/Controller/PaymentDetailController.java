package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.CardDao;
import application.Dao.OrderDao;
import application.Model.CardModel;
import application.Model.CustomerModel;
import application.Model.OrderDetailModel;
import application.Model.ProductModel;
import application.Utill.Menu;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PaymentDetailController extends Menu implements Initializable {
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
	private TextField txtTotal;	
	
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
	private RadioButton optCreditMeno;
	@FXML
	private RadioButton optPayPal;
	@FXML
	private RadioButton optCash;
	@FXML
	private RadioButton optStripe;
	
	@FXML
	private TableView<CardModel> tp;
	@FXML
	private TableColumn<CardModel, String> tp_Name;
	@FXML
	private TableColumn<CardModel, String> tp_Card;
	@FXML
	private TableColumn<CardModel, String> tp_Type;
	@FXML
	private TableColumn<CardModel, String> tp_Expired;
	@FXML
	private TableColumn<CardModel, String> tp_Cvc;
	@FXML
	private TableColumn<CardModel, Boolean> tp_Default;
	
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
	private Label statusSPayment;
	
	@FXML
	private Button btnSubmit;
	@FXML
	private Button btnSave;
	@FXML
	private CheckBox chkApplyCreditMemo;
	@FXML
	private Label lblmsgMemo;
	
	private OrderDao orderDao;
	private CardDao cardDao;
	private float 	All_Total_Memo = 0.00f;
	private float 	All_Total_Memo_Sub = 0.00f;
	private float 	All_Total = 0.00f;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inits();
		//tp.setEditable(true);
		//tp.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//tp.getSelectionModel().setCellSelectionEnabled(true);
		txtPaid.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){  
		        	 if(!txtTotal.getText().equals(txtPaid.getText())){
						  chkPartial.setSelected(true);
					 }else{
						 chkPartial.setSelected(false);
					 }
		        	Float amountPaid = Float.parseFloat(txtPaid.getText());
		        	try {
						orderDao.updateAmoutPaid(orderId, amountPaid);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }

		    }
		});
		txtCheckN.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){  
		        	String txtCheckNs = txtCheckN.getText();
		        	try {
						orderDao.updateCheckN(orderId, txtCheckNs);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }

		    }
		});
		chkPartial.requestFocus();
        Callback<TableColumn<CardModel, Boolean>, TableCell<CardModel, Boolean>> booleanCellFactory = 
                new Callback<TableColumn<CardModel, Boolean>, TableCell<CardModel, Boolean>>() {
                @Override
                    public TableCell<CardModel, Boolean> call(TableColumn<CardModel, Boolean> p) {
                        return new BooleanCell();
                }
            };
		tp.setPlaceholder(new Label("Please wait… Loading."));
		orderDao = new OrderDao();
		cardDao = new CardDao();
		tp_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
		tp_Card.setCellValueFactory(new PropertyValueFactory<>("card"));
		tp_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
		tp_Expired.setCellValueFactory(new PropertyValueFactory<>("expired"));
		tp_Cvc.setCellValueFactory(new PropertyValueFactory<>("cvc"));
		tp_Default.setCellValueFactory(new PropertyValueFactory<>("cdefault"));
		tp_Default.setCellFactory(booleanCellFactory);
		td_sku.setCellValueFactory(new PropertyValueFactory<>("sku"));
		td_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		td_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		td_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		td_scien.setCellValueFactory(new PropertyValueFactory<>("scientific"));
		td_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		td_total.setCellValueFactory(new PropertyValueFactory<>("total"));
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					OrderDetailModel orderDetail = orderDao.getOrderDetail(orderId);
					CustomerModel customer = orderDetail.getCustomer();
					List<ProductModel> lstProduct = orderDetail.getLstProduct();
					td.getItems().addAll(lstProduct);
					
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
							  txtTotal.setText(String.format("%.2f", lstProduct.get(0).getAll_Total()));
							  txtPaid.setText(String.format("%.2f", lstProduct.get(0).getAmoutPaid()));
							  txtpaids.setText(String.format("%.2f", lstProduct.get(0).getAmoutPaid()));
							  txtCheckN.setText(lstProduct.get(0).getChecknumber());
							  showPaymentMethod(lstProduct.get(0).getPaymentMethod());
							  if(!txtTotal.getText().equals(txtPaid.getText())){
								  chkPartial.setSelected(true);
							  }
						  }
						});
					ShowCus( customer);
					All_Total_Memo = orderDao.getTotalMemo(customer.getCustomerID());
					All_Total = lstProduct.get(0).getAll_Total();
					
					System.out.println(All_Total_Memo);
				
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
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
	public void showPaymentMethod(String input){
		if(input.equals("")){
			
		}else if(input.equals("Check")){
			optCheck.setSelected(true);
		}else if(input.equals("Credit/Debit Card")){
			optCredit.setSelected(true);
		}else if(input.equals("Credit Memo")){
			optCreditMeno.setSelected(true);
		}else if(input.equals("PayPal")){
			optPayPal.setSelected(true);
		}else if(input.equals("Cash")){
			optCash.setSelected(true);
		}else if(input.equals("Stripe")){
			optStripe.setSelected(true);
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
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					List<CardModel> lstCard = cardDao.getLstCard(customer.getCustomerID());
					tp.getItems().addAll(lstCard);
					//
					tp.getSelectionModel().select(0);
					
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
							  chkPartial.requestFocus();
						  }
						});
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
	class BooleanCell extends TableCell<CardModel, Boolean> {
        private RadioButton radioButton;
        public Boolean st = true;
        public BooleanCell() {
        	radioButton = new RadioButton();
        	radioButton.setDisable(true);
        	radioButton.selectedProperty().addListener(new ChangeListener<Boolean> () {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(isEditing() == false && oldValue == false && newValue == true){
                    	// f- t t
                    	// t - f f
                    	st = false;
                    }
                    if(st == false){
                    	st = true;
                    }else{
                    	System.out.println("tt "+isEditing());
                    	newValue = true;
                    	tp.getSelectionModel().getSelectedItem().setCdefault(newValue);
                    	int index = tp.getSelectionModel().getSelectedIndex();
                    	System.out.println(index);
        	            	int count = tp.getItems().size();
        	        		for(int i =0;i<count;i++){
        	        			if(index == i){
        	        				
        	        			}else{
        	        				tp.getSelectionModel().getSelectedItem().setCdefault(false);
        	        			}
        	        			//Boolean sub = tp.getItems().get(i).getCdefault();
        	        			//System.out.println("-->"+sub);
        	        		}
                    }
                    if(isEditing()){
                        commitEdit(newValue == null ? false : newValue);
                    //    System.out.println(oldValue +"<->"+newValue);
                    }
                //    System.out.println(oldValue +"-"+newValue);
                }
            });
            this.setGraphic(radioButton);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
           // radioButton.setDisable(false);
            radioButton.requestFocus();
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            radioButton.setDisable(true);
        }
       
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            //System.out.println(value);
            radioButton.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
            	radioButton.setSelected(item);
            	//System.out.println(radioButton.isSelected());
            	//System.out.println(tp.getSelectionModel().getSelectedItem().getCdefault());

            	
            }
        }
    }
	public void checkPartial(ActionEvent event) throws IOException {
		System.out.println(chkPartial.isSelected());
		if(chkPartial.isSelected()){
			txtPaid.setEditable(true);
			txtPaid.requestFocus();
		}else{
			txtPaid.setEditable(false);
		}
	}
	public void checkMemo(ActionEvent event) throws IOException {
		checkMemo();
	}
	public void checkMemo() throws IOException {
		System.out.println(chkApplyCreditMemo.isSelected());
		if(chkApplyCreditMemo.isSelected()){
			if(All_Total<All_Total_Memo){
				All_Total_Memo_Sub = All_Total;
			}else {
				All_Total_Memo_Sub = All_Total_Memo;
			}
			lblmsgMemo.setText("-$"+All_Total_Memo_Sub+" subtract from $"+All_Total);
		}else{
			lblmsgMemo.setText("");
			All_Total_Memo_Sub = 0.00f;
		}
	}
	public void payMethod(ActionEvent event) throws IOException {
		String paymentMethod ="";
		if(optCheck.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Check";
		}
		if(optCredit.isSelected()){
			optCheck.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Credit/Debit Card";
		}
		if(optCreditMeno.isSelected()){
			optCredit.setSelected(false);
			optCheck.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Credit Memo";
		}
		if(optPayPal.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optCheck.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "PayPal";
		}
		if(optCash.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCheck.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Cash";
		}
		if(optStripe.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCheck.setSelected(false);
			optCash.setSelected(false);
			paymentMethod = "Stripe";
		}
		try {
			orderDao.updatePaymentMethod(orderId, paymentMethod);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String paymentMethod ="";
		if(optCheck.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Check";
		}
		if(optCredit.isSelected()){
			optCheck.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Credit/Debit Card";
		}
		if(optCreditMeno.isSelected()){
			optCredit.setSelected(false);
			optCheck.setSelected(false);
			optPayPal.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Credit Memo";
		}
		if(optPayPal.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optCheck.setSelected(false);
			optCash.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "PayPal";
		}
		if(optCash.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCheck.setSelected(false);
			optStripe.setSelected(false);
			paymentMethod = "Cash";
		}
		if(optStripe.isSelected()){
			optCredit.setSelected(false);
			optCreditMeno.setSelected(false);
			optPayPal.setSelected(false);
			optCheck.setSelected(false);
			optCash.setSelected(false);
			paymentMethod = "Stripe";
		}
		if(paymentMethod.equals("")){
			statusSPayment.setText("Please select a payment method.");
		}else{
			statusSPayment.setText("Please wait ... the payment is processing.");
		//	if(chkApplyCreditMemo.isSelected()){
				try {
					orderDao.updateAmoutMemo(orderId, All_Total_Memo_Sub);
				/*	float amoutMemo =  orderDao.getAmountMemo(orderId);
					if(amoutMemo>0){
						//processs
					}*/
					statusSPayment.setText("Payment process success.");
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//	}
			
		}
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