package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.CustomerDao;
import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import application.Utill.Menu;
import application.Utill.PdfCustomer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class CustomerController extends Menu implements Initializable {
	@FXML
	private Label statusCus;
	@FXML
	private TextField bill_cus_id;
	@FXML
	private TextField bill_name;
	@FXML
	private TextField bill_address1;
	@FXML
	private TextField bill_address2;
	@FXML
	private TextField bill_city;
	@FXML
	private TextField bill_state;
	@FXML
	private TextField bill_zip;
	@FXML
	private TextField bill_phone;
	@FXML
	private TextField courier;
	@FXML
	private TextField terms;
	@FXML
	private TextField salsperson;
	@FXML
	private TextField txtShippingCost;
	@FXML
	private TextField saleEmail;
	
	@FXML
	private TextField Phone2;
	@FXML
	private TextField Cellphone;
	@FXML
	private TextField Fax;
	@FXML
	private TextField Country;
	@FXML
	private TextField Destination;
	@FXML
	private TextField AlrportCode;
	@FXML
	private TextField Time;
	@FXML
	private TextField Contact;
	@FXML
	private TextField Contact2;
	@FXML
	private TextField EMail;
	@FXML
	private TextField EMail2;
	@FXML
	private TextField Title;
	@FXML
	private TextField Title2;
	@FXML
	private TextField Website;
	@FXML
	private TextArea Comments;
	@FXML
	private TextField CurrBal;
	@FXML
	private TextField OpnCred;
	@FXML
	private TextField YTDSales;
	@FXML
	private TextField LstSales1;
	@FXML
	private TextField LstPmt1;
	@FXML
	private TextField Entered;
	@FXML
	private TextField LstSales2;
	@FXML
	private TextField LstPmt2;
	@FXML
	private TextField NetDue;
	@FXML
	private TextField Price;
	@FXML
	private TextField CreditLimit;
	@FXML
	private TextField Tax;
	@FXML
	private TextField SalesDisc;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnReport;
	@FXML
	private Button btnReport1;
	@FXML
	private Label lblstatuspdf;
	
	@FXML
	private TextField Customer_email;
	final String[] filter = new String[] { "ID","Name", "Contact", "Phone", "Email" ,"City" };
	private String str_filters = filter[0];
	final String[] scouriers = new String[] { "OnTrac", "UPS", "Aeromexico", "Aerounion", "Air Canada", "Alaska Airlines", "American Airlines Cargo","Cathay Pacific Airways", "COPA Airlines", "Delta Air Lines","Southwest Airlines","Southwest Airlines","United Airlines Cargo"};
	@FXML
	private ChoiceBox cbFilter;
	@FXML
	private ChoiceBox<String> scourier;
	@FXML
	private TextField keySearch;
	private Integer  tkey=0;
	@FXML
	private Pane paneSearchCus;	
	@FXML
	private TableView<CustomerModel> twSearchCus;
	@FXML
	private TableView<OrderModel> tw;
	@FXML
	private TableColumn<OrderModel, String> tw_orderId;
	@FXML
	private TableColumn<OrderModel, String> tw_date;
	@FXML
	private TableColumn<OrderModel, String> tw_status;
	@FXML
	private TableColumn<OrderModel, Boolean> tw_payment;
	@FXML
	private TableColumn<OrderModel, Boolean> tw_issued;
	@FXML
	private TableColumn<OrderModel, String> tw_total;
	
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
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private CustomerModel customer;
	private OrderDao orderDao;
	public boolean isFixKey = false;
	public boolean isFixKeys = false;
	public int isFixKeyn = 0;
	public void isShowSearchCus(boolean isShow){
		//if(isShow == true){
			paneSearchCus.setVisible(isShow);
			twSearchCus.setVisible(isShow);
			statusCus.setVisible(!isShow);
		//}
	}
	public void gotoOrdersDetail(List<OrderModel> currentOrder) throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Orders Detail");
		stage.getIcons().add(new Image("file:resources/images/icon.png"));
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Orders.fxml"));
		Pane myPane = (Pane) myLoader.load();

		OrdersController controller = (OrdersController) myLoader.getController();
		controller.setPrevStage(stage);
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		controller.setCscreen("orderDetails");
			try {
				controller.showDetail(currentOrder);
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//orderDetail
		prevStage.close();
		stage.setResizable(false);
		stage.show();
	    scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            int count = 0;
    	    @Override
    	    public void handle(KeyEvent evt) {
    	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
    	        	String temp = controller.getCscreen();
    	        	System.out.println(temp);
    	        	if(temp.equals("lstOrder")){
    	        		prevStage.show();
    	        		stage.close();
    	        	}else if(temp.equals("orderDetail")){
    	        		count++;
    	        		if(count ==1 ){
	    	        		try {
	    	        			stage.close();
								gotoOrders();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    	        		}
    	        	}else if(temp.equals("orderDetails")){
    	        		prevStage.show();
    	        		stage.close();
    	        	}
    	        }
    	    }
    	});
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 inits();
		 Callback<TableColumn<OrderModel, Boolean>, TableCell<OrderModel, Boolean>> booleanCellFactoryPayment = 
		         new Callback<TableColumn<OrderModel, Boolean>, TableCell<OrderModel, Boolean>>() {
		         @Override
		             public TableCell<OrderModel, Boolean> call(TableColumn<OrderModel, Boolean> p) {
		                 return new BooleanCells();
		         }
		     };
		     
		     Callback<TableColumn<OrderModel, Boolean>, TableCell<OrderModel, Boolean>> booleanCellFactoryIssued = 
		             new Callback<TableColumn<OrderModel, Boolean>, TableCell<OrderModel, Boolean>>() {
		             @Override
		                 public TableCell<OrderModel, Boolean> call(TableColumn<OrderModel, Boolean> p) {
		                     return new BooleanCellsIssued();
		             }
		         };
		orderDao = new OrderDao();
		isShowSearchCus(false);
		btnAdd.setVisible(false);
		this.bill_name.textProperty().addListener(new TextFieldListener(this.bill_name));
		this.bill_address1.textProperty().addListener(new TextFieldListener(this.bill_address1));
		this.bill_address2.textProperty().addListener(new TextFieldListener(this.bill_address2));
		this.bill_city.textProperty().addListener(new TextFieldListener(this.bill_city));
		this.bill_state.textProperty().addListener(new TextFieldListener(this.bill_state));
		this.bill_zip.textProperty().addListener(new TextFieldListener(this.bill_zip));
		this.bill_phone.textProperty().addListener(new TextFieldListener(this.bill_phone));
		this.terms.textProperty().addListener(new TextFieldListener(this.terms));
		this.salsperson.textProperty().addListener(new TextFieldListener(this.salsperson));
		this.txtShippingCost.textProperty().addListener(new TextFieldListener(this.txtShippingCost));
		
		this.Phone2.textProperty().addListener(new TextFieldListener(this.Phone2));
		this.Cellphone.textProperty().addListener(new TextFieldListener(this.Cellphone));
		this.Fax.textProperty().addListener(new TextFieldListener(this.Fax));
		this.Country.textProperty().addListener(new TextFieldListener(this.Country));
		this.Destination.textProperty().addListener(new TextFieldListener(this.Destination));
		this.AlrportCode.textProperty().addListener(new TextFieldListener(this.AlrportCode));
		this.Time.textProperty().addListener(new TextFieldListener(this.Time));
		this.Contact.textProperty().addListener(new TextFieldListener(this.Contact));
		this.Contact2.textProperty().addListener(new TextFieldListener(this.Contact2));
		this.EMail.textProperty().addListener(new TextFieldListener(this.EMail));
		this.EMail2.textProperty().addListener(new TextFieldListener(this.EMail2));
		this.Title.textProperty().addListener(new TextFieldListener(this.Title));
		this.Title2.textProperty().addListener(new TextFieldListener(this.Title2));
		this.Website.textProperty().addListener(new TextFieldListener(this.Website));
		this.Comments.textProperty().addListener(new TextAreListener(this.Comments));
		this.CurrBal.textProperty().addListener(new TextFieldListener(this.CurrBal));
		this.OpnCred.textProperty().addListener(new TextFieldListener(this.OpnCred));
		this.YTDSales.textProperty().addListener(new TextFieldListener(this.YTDSales));
		this.LstSales1.textProperty().addListener(new TextFieldListener(this.LstSales1));
		this.LstPmt1.textProperty().addListener(new TextFieldListener(this.LstPmt1));
		this.Entered.textProperty().addListener(new TextFieldListener(this.Entered));
		this.LstSales2.textProperty().addListener(new TextFieldListener(this.LstSales2));
		this.LstPmt2.textProperty().addListener(new TextFieldListener(this.LstPmt2));
		this.NetDue.textProperty().addListener(new TextFieldListener(this.NetDue));
		this.Price.textProperty().addListener(new TextFieldListener(this.Price));
		this.CreditLimit.textProperty().addListener(new TextFieldListener(this.CreditLimit));
		this.Tax.textProperty().addListener(new TextFieldListener(this.Tax));		
		tw_payment.setCellFactory(booleanCellFactoryPayment);
		tw_issued.setCellFactory(booleanCellFactoryIssued);
		
		tw_orderId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		tw_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		tw_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		tw_payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
		tw_issued.setCellValueFactory(new PropertyValueFactory<>("issued"));
		tw_total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		//tw.setPlaceholder(new Label("Please wait… Searching Database."));
		TableColumn<OrderModel, Integer> indexCols = new TableColumn<OrderModel, Integer>("#");
		indexCols.setCellFactory(new Callback<TableColumn<OrderModel, Integer>, TableCell<OrderModel, Integer>>() {
			@Override
			public TableCell<OrderModel, Integer> call(TableColumn<OrderModel, Integer> param) {
				return new TableCell<OrderModel, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null) {

							int index = this.getTableRow().getIndex();

							if (index < tw.getItems().size()) {
								int rowNum = index + 1;
								setText(String.valueOf(rowNum));
							} else {
								setText("");
							}

						} else {
							setText("");
						}

					}
				};
			}
		});
		indexCols.setPrefWidth(45.0);
		indexCols.getStyleClass().add("clNo");
		tw.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					int  orderId = tw.getSelectionModel().getSelectedItems().get(0).getOrder_id();
					List<OrderModel> currentOrder  = tw.getSelectionModel().getSelectedItems();
					System.out.println(orderId);
					try {
						gotoOrdersDetail(currentOrder);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (event.getCode() == KeyCode.PAGE_DOWN) {
					keySearch.requestFocus();
				}
				if (event.getCode() == KeyCode.PAGE_UP) {
					bill_cus_id.requestFocus();
				}
			}
		});
		tw.getColumns().add(0, indexCols); // number column is at
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
		        	isShowSearchCus(true);
		        }
		        else
		        {
		        	isShowSearchCus(false);
		        }
		    }
		});
		bill_cus_id.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){        	
			    	if (newValue.matches("\\d*")) {
			            int value = Integer.parseInt(newValue);
			        } else {
			        	bill_cus_id.setText(oldValue);
			        }
		        }

		    }
		});
		bill_cus_id.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_DOWN){
		    	  tw.requestFocus();
		      }else if(event.getCode() == KeyCode.PAGE_UP){   	
		    	  keySearch.requestFocus();
		      }else{
		    	  gotoSearchCus();
		      }
		    }
		});
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_UP){
		    	  keySearch.requestFocus();
		      }else{ 
		    	  if(isFixKeys = true){
		    		  isFixKeyn = 2;
		    		  isFixKeys = false;
		    		  if(isFixKey == true){
		    			 // isFixKeyn = 1;
		    		  }
		    	  }
		    	  if(event.getCode() == KeyCode.ENTER && isFixKey == true){
		    		  isFixKeyn++;
		    	  }
		    	  System.out.println("--"+isFixKeys);
		    	  System.out.println("--"+isFixKeyn);


		    	  if (event.getCode() == KeyCode.ENTER && isFixKeyn == 2) {
		    		  isFixKeyn = 0;
		    		  isFixKey = false;
						// move focus & selection
						// we need to clear the current selection first or else the
						// selection would be added to the current selection since

						// we are in multi selection mode
						TablePosition pos = twSearchCus.getFocusModel().getFocusedCell();

						if (pos.getRow() == -1) {
							twSearchCus.getSelectionModel().select(0);
						}
						// add new row when we are at the last row
						/*else if (pos.getRow() == twResultSearch.getItems().size() - 1) {
						}
						// select next row, but same column as the current selection
						else*/
						if (pos.getRow() < twSearchCus.getItems().size()) {
							CustomerModel c = twSearchCus.getSelectionModel().getSelectedItem();
							System.out.println(c.getCustomerID());
							//System.out.println(c.getAddress());
							if (c.getCustomerID() != null) {
								bill_cus_id.setText(c.getCustomerID());
								CustomerModel bill = c;
						    	CustomerModel ship = bill;
						    	customer = bill;
						    	statusCus.setText("You can edit user.");
						    	btnAdd.setVisible(false);
						    	//bill_cus_id.setText(bill.getCustomerID());
						    	bill_name.setText(bill.getCompanyName());
						    	bill_address1.setText(bill.getAddress());
						    	bill_address2.setText(bill.getAddress2());
						    	bill_city.setText(bill.getCity());
						    	bill_state.setText(bill.getStates());
						    	bill_zip.setText(bill.getZip());
						    	bill_phone.setText(bill.getPhone1());
						    	courier.setText(bill.getCarrier());
						    	//scourier.setValue(bill.getCarrier());
						    	terms.setText(bill.getTerms());
						    	salsperson.setText(bill.getSalesperson());
						 
						    	txtShippingCost.setText(String.valueOf(bill.getShippingCost()));
						    	
						    	Phone2.setText(bill.getPhone2());
						    	Cellphone.setText(bill.getCellphone());
						    	Fax.setText(bill.getFax());
						    	Country.setText(bill.getCountry());
						    	Destination.setText(bill.getDestination());
						    	AlrportCode.setText(bill.getAlrportCode());
						    	Time.setText(bill.getTime());
						    	Contact.setText(bill.getContact());
						    	Contact2.setText(bill.getContact2());
						    	EMail.setText(bill.getEMail());
						    	EMail2.setText(bill.getEMail2());
						    	Title.setText(bill.getTitle());
						    	Title2.setText(bill.getTitle2());
						    	Website.setText(bill.getWebsite());
						    	Comments.setText (bill.getComments());
						    	CurrBal.setText(String.valueOf(bill.getCurrBal()));
						    	OpnCred.setText(String.valueOf(bill.getOpnCred()));
						    	YTDSales.setText(String.valueOf(bill.getYTDSales()));
						    	LstSales1.setText(String.valueOf(bill.getLstSales1()));
						    	LstPmt1.setText(String.valueOf(bill.getLstPmt1()));
						    	Entered.setText(bill.getEntered());
						    	LstSales2.setText(bill.getLstSales2());
						    	LstPmt2.setText(bill.getLstPmt2());
						    	NetDue.setText(bill.getNetDue());
						    	Price.setText(bill.getPrice());
						    	CreditLimit.setText(String.valueOf(bill.getCreditLimit()));
						    	Tax.setText(String.valueOf(bill.getTax()));
						    	SalesDisc.setText(String.valueOf(bill.getSalesDisc()));
						    	if(bill.getSaleEmail() == null){
							    	saleEmail.setText(bill.getEmail());
						    	}else{
							    	saleEmail.setText(bill.getSaleEmail());
						    	}
						    	Customer_email.setText(bill.getEmail());
						    	bill_cus_id.requestFocus();
						    	// process customer id show order
						    	tw.setPlaceholder(new Label("Please wait… Searching Database."));
						    	Thread thLoadData = new Thread() {
									@SuppressWarnings("deprecation")
									public void run() {
										try {
											List<OrderModel> lstOrder = orderDao.getOrderByOrderId(c.getCustomerID());
											tw.getItems().clear();
											tw.getItems().addAll(lstOrder);
											if(lstOrder.size() == 0){
												Platform.runLater(new Runnable() {
													  @Override
													  public void run() {
															tw.setPlaceholder(new Label("Currently customer not available order."));

													  }
													});
											}
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
							} else {
								customer = new CustomerModel();
							}
						}
						

					}
		    	  if (event.getCode() == KeyCode.DELETE ) {
						ConfirmationWindow a = new ConfirmationWindow(prevStage, "Confirmation");
						a.show();
						a.setAlwaysOnTop(true);
						isFixKey = true;
						isFixKeys = true;
						a.stage.setOnHiding(new EventHandler<WindowEvent>() {

				            @Override
				            public void handle(WindowEvent event) {
				                Platform.runLater(new Runnable() {

				                    @Override
				                    public void run() {
				                        System.out.println("Application Closed by click to Close Button(X)");
				                        System.out.println(a.postStatus);
				                        if(a.postStatus == true){
				                        	CustomerDao customerDao = new CustomerDao();
				                        	String cusID = twSearchCus.getSelectionModel().getSelectedItem().getCustomerID();
				                        	try {
												customerDao.delete(cusID);
					                        	twSearchCus.getItems().removeAll(twSearchCus.getSelectionModel().getSelectedItems());
											} catch (ClassNotFoundException | SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
				                        }
				                        isShowSearchCus(true);
				                        twSearchCus.requestFocus();
				                    }
				                });
				            }
				        }); 
					}
		      }
		   }
		}); 
		keySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	try {
					actionSearchCus();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
		}); 
		keySearch.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	isShowSearchCus(true);
		        }
		        else
		        {
		        	isShowSearchCus(false);
		        }
		    }
		});
		cbFilter.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	isShowSearchCus(true);
		        }
		        else
		        {
		        	isShowSearchCus(false);
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
		scourier.setItems(FXCollections.observableArrayList("OnTrac", "UPS", "Aeromexico", "Aerounion", "Air Canada", "Alaska Airlines", "American Airlines Cargo","Cathay Pacific Airways", "COPA Airlines", "Delta Air Lines","Southwest Airlines","Southwest Airlines","United Airlines Cargo"));
		scourier.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
			//	String str_filters = filter[new_value.intValue()];
				//tkey = new_value.intValue();
			//	System.out.println(str_filters); //////////////////////////////////
				 tranferCus();
			}
		});
		
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					actionSearchCus();
					this.suspend();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		thLoadData.start();
/*		keySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	 // System.out.println("handler : " + keySearch.getText());
			      if(keySearch.getText().length()>0){
		    		System.out.println(keySearch.getText());////////////////////////////////////////
		    		gotoSearchCus();
			      }
		    	}
		    });*/
		keySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.DOWN){
		    	  twSearchCus.requestFocus();
		      }else{ 
		    	  if(event.getCode() == KeyCode.PAGE_DOWN){
		    		  bill_cus_id.requestFocus();
			      }
		    	  if(event.getCode() == KeyCode.PAGE_UP){
		    		  tw.requestFocus();
			      }
		      }
		   }
		});  
		//keySearch.requestFocus();
		

	}
	public void actionSearchCus() throws IOException {
		 twSearchCus.getItems().clear();
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
	  		Platform.runLater(new Runnable() {
				  @Override
				  public void run() {
						int count = twSearchCus.getItems().size();
						twSearchCus.refresh();
						if(count == 0){
							twSearchCus.setPlaceholder(new Label("No matching results were found."));
						}
				  }
				});
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
    public void actionSearchCus(EventHandler event) throws IOException {          
    	actionSearchCus();
    }
	public void tranferCus(){
		if(customer != null){
			customer.setCompanyName(bill_name.getText());
			customer.setAddress(bill_address1.getText());
			customer.setAddress2(bill_address2.getText());
			customer.setCity(bill_city.getText());
			customer.setStates(bill_state.getText());
			customer.setZip(bill_zip.getText());
			customer.setPhone1(bill_phone.getText());
			customer.setTerms(terms.getText());
			customer.setSalesperson(salsperson.getText());
			customer.setShippingCost(Float.parseFloat(txtShippingCost.getText()));
			//customer.setCarrier(scourier.getValue());
			//System.out.println(scourier.getValue());
			//System.out.println(txtShippingCost.getText());
			customer.setPhone2(Phone2.getText());
			customer.setCellphone(Cellphone.getText());
			customer.setFax(Fax.getText());
			customer.setCountry(Country.getText());
			customer.setDestination(Destination.getText());
			customer.setAlrportCode(AlrportCode.getText());
			customer.setTime(Time.getText());
			customer.setContact(Contact.getText());
			customer.setContact2(Contact2.getText());
			customer.setEMail(EMail.getText());
			customer.setEMail2(EMail2.getText());
			customer.setTitle(Title.getText());
			customer.setTitle2(Title2.getText());
			customer.setWebsite(Website.getText());
			customer.setComments(Comments.getText());
			customer.setCurrBal(Float.parseFloat(CurrBal.getText()));
			customer.setOpnCred(Float.parseFloat(OpnCred.getText()));
			customer.setYTDSales(Float.parseFloat(YTDSales.getText()));
			customer.setLstSales1(Float.parseFloat(LstSales1.getText()));
			customer.setLstPmt1(Float.parseFloat(LstPmt1.getText()));
			customer.setEntered(Entered.getText());
			customer.setLstSales2(LstSales2.getText());
			customer.setLstPmt2(LstPmt2.getText());
			customer.setNetDue(NetDue.getText());
			customer.setPrice(Price.getText());
			customer.setCreditLimit(Float.parseFloat(CreditLimit.getText()));
			customer.setTax(Integer.parseInt(Tax.getText()));
			customer.setSalesDisc(SalesDisc.getText());
			  CustomerDao customerDao = new CustomerDao();
			  try {
				int status = customerDao.updateCustomer(customer);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class TextFieldListener implements ChangeListener<String> {
		  private final TextField textField ;
		  TextFieldListener(TextField textField) {
		    this.textField = textField ;
			  textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
				    @Override
				    public void handle(KeyEvent event) {
				    	 tranferCus();
				    	 System.out.println("change ...");
				    }
				  });
		  }

		  @Override
		  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    // do validation on textField
			 // tranferCus();
			  //System.out.println("change ...");

		  }
	}
	private class TextAreListener implements ChangeListener<String> {
		  private final TextArea textArea ;
		  TextAreListener(TextArea textArea) {
		    this.textArea = textArea ;
		    textArea.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
				    @Override
				    public void handle(KeyEvent event) {
				    	 tranferCus();
				    	 System.out.println("change ...");
				    }
				  });
		  }

		  @Override
		  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    // do validation on textField
			 // tranferCus();
			  //System.out.println("change ...");

		  }
	}
	public void gotoHome(ActionEvent event) throws IOException {
		gotoHome();
	}
	public void addCustomer(ActionEvent event) throws IOException {
		addCustomer();
	}
	public void Report(ActionEvent event) throws IOException {
		Report();
	}
	public void Report1(ActionEvent event) throws IOException {
		Report1();
	}
	public void addCustomers(KeyEvent event) throws IOException {
		if(event.getCode() == KeyCode.ENTER){
			addCustomer();
		}
	}
	public void Report() throws IOException { 
		btnReport.setDisable(true);
		btnReport.setText("Generate Aging Report");
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
					PdfCustomer pdf = new PdfCustomer();
					Date date = new Date();
					String tddate = dateFormat.format(date);
					//LocalDate curentDate = LOCAL_DATE(cudate);
					
					List<OrderModel> lstOrder;
					try {
						lstOrder = orderDao.getOrderCustomerSale();
							pdf.Print(lstOrder,tddate);
						    Platform.runLater(new Runnable() {
								  @Override
								  public void run() {
									    btnReport.setDisable(false);
									    btnReport.setText("Aging Report");
								  }
							});
					} catch (ClassNotFoundException | SQLException  | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						btnReport.setDisable(false);
					    btnReport.setText("Aging Report");
					}
;

			}
		};

		thLoadData.start();		
	}
	public void Report1() throws IOException { 
		Boolean check = true;
		if(customer == null){
			check = false;
		}else{
			if(customer.getCustomerID().equals("")){
				check = false;
			}
		}
		if(check == false){
			lblstatuspdf.setText("Please select a customer");
		}else{
			lblstatuspdf.setText("");
			btnReport1.setDisable(true);
			btnReport1.setText("Generate Open Receivables Report");
			System.out.println("tttt:"+customer.getCustomerID());
			Thread thLoadData = new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
						PdfCustomer pdf = new PdfCustomer();
						Date date = new Date();
						String tddate = dateFormat.format(date);
						//LocalDate curentDate = LOCAL_DATE(cudate);
						
						List<OrderModel> lstOrder;
						try {
							lstOrder = orderDao.getOrderCustomerSale(customer.getCustomerID());
								pdf.Prints(lstOrder,tddate);
								Platform.runLater(new Runnable() {
									  @Override
									  public void run() {
										    btnReport1.setDisable(false);
										    btnReport1.setText("Open Receivables Report");
									  }
								});

						} catch (ClassNotFoundException | SQLException  | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							btnReport.setDisable(false);
						    btnReport.setText("Aging Report");
						}
	;
	
				}
			};
	
			thLoadData.start();		
		}
	}
	public void addCustomer() throws IOException { 
		// validate field
		boolean vali = true;//validateAddCus();
		// check exits customer id
		if(vali == true){
			String customerId = bill_cus_id.getText();
			boolean chkCus = chkCustomer(customerId);
			if(chkCus == false){
				// add to db
				int status  = addDoCustomer();
				if(status == 1){
					String msg = "Add new customer success.";
					bill_cus_id.requestFocus();
					statusCus.setText(msg);
				}else{
					String msg = "Add new customer fail.";
					bill_cus_id.requestFocus();
					statusCus.setText(msg);
				}
			}
		}
	}  
	public int addDoCustomer(){
		int status = 0;
		CustomerDao customerDao = new CustomerDao();
		CustomerModel customerModel = new CustomerModel();
		customerModel.setCustomerID(bill_cus_id.getText());
		customerModel.setCompanyName(bill_name.getText());
		customerModel.setAddress(bill_address1.getText());
		customerModel.setAddress2(bill_address2.getText());
		customerModel.setCity(bill_city.getText());
		customerModel.setStates(bill_state.getText());
		customerModel.setZip(bill_zip.getText());
		customerModel.setPhone1(bill_phone.getText());
		try {
			status = customerDao.addCustomer(customerModel);
			System.out.println("status Add customer : "+status);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	public boolean chkCustomer(String customerId){
		CustomerDao customerDao = new CustomerDao();
		boolean exits = false;
		try {
			exits = customerDao.chkCustome(customerId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(exits == true){
			String msg = "";
			msg = "Customer Id already exists.";
			bill_cus_id.requestFocus();
			statusCus.setText(msg);
		}
		return exits;
	}
	public boolean validateAddCus(){
		boolean vali = true;
		String msg = "";
		if(bill_cus_id.getText().equals("")){
			vali = false;
			msg = "Customer Id required.";
			bill_cus_id.requestFocus();
		}
		else if(bill_name.getText().equals("")){
			msg = "Name Id required.";
			vali = false;
			bill_name.requestFocus();
		}
		else if(bill_address1.getText().equals("")){
			msg = "Address 1 Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_city.getText().equals("")){
			msg = "Address 1 Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_state.getText().equals("")){
			msg = "State Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_zip.getText().equals("")){
			msg = "Zip Code Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_phone.getText().equals("")){
			msg = "Telephone Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		statusCus.setText(msg);
		return vali;
	}
	public void gotoSearchCus(){
		String key = bill_cus_id.getText();
		String type = filter[tkey];
		System.out.println(key);
		if(key.length()>0){
			  CustomerDao customerDao = new CustomerDao();
			 try {
					CustomerModel bill = customerDao.getCustomerSearch(key,type);
			    	customer = bill;
			    	if(bill.getCustomerID().equals("") || bill.getCustomerID() == null){
			    		statusCus.setText("Customer not exits.");
			    		btnAdd.setVisible(true);
			    	}else{
				    	statusCus.setText("You can edit user.");
				    	btnAdd.setVisible(false);
			    	}
			    	//bill_cus_id.setText(bill.getCustomerID());
			    	bill_name.setText(bill.getCompanyName());
			    	bill_address1.setText(bill.getAddress());
			    	bill_address2.setText(bill.getAddress2());
			    	bill_city.setText(bill.getCity());
			    	bill_state.setText(bill.getStates());
			    	bill_zip.setText(bill.getZip());
			    	bill_phone.setText(bill.getPhone1());
			    	courier.setText(bill.getCarrier());
			    	//scourier.setValue(bill.getCarrier());
			    	terms.setText(bill.getTerms());
			    	salsperson.setText(bill.getSalesperson());
			 
			    	txtShippingCost.setText(String.valueOf(bill.getShippingCost()));
			    	
			    	Phone2.setText(bill.getPhone2());
			    	Cellphone.setText(bill.getCellphone());
			    	Fax.setText(bill.getFax());
			    	Country.setText(bill.getCountry());
			    	Destination.setText(bill.getDestination());
			    	AlrportCode.setText(bill.getAlrportCode());
			    	Time.setText(bill.getTime());
			    	Contact.setText(bill.getContact());
			    	Contact2.setText(bill.getContact2());
			    	EMail.setText(bill.getEMail());
			    	EMail2.setText(bill.getEMail2());
			    	Title.setText(bill.getTitle());
			    	Title2.setText(bill.getTitle2());
			    	Website.setText(bill.getWebsite());
			    	Comments.setText (bill.getComments());
			    	CurrBal.setText(String.valueOf(bill.getCurrBal()));
			    	OpnCred.setText(String.valueOf(bill.getOpnCred()));
			    	YTDSales.setText(String.valueOf(bill.getYTDSales()));
			    	LstSales1.setText(String.valueOf(bill.getLstSales1()));
			    	LstPmt1.setText(String.valueOf(bill.getLstPmt1()));
			    	Entered.setText(bill.getEntered());
			    	LstSales2.setText(bill.getLstSales2());
			    	LstPmt2.setText(bill.getLstPmt2());
			    	NetDue.setText(bill.getNetDue());
			    	Price.setText(bill.getPrice());
			    	CreditLimit.setText(String.valueOf(bill.getCreditLimit()));
			    	Tax.setText(String.valueOf(bill.getTax()));
			    	SalesDisc.setText(String.valueOf(bill.getSalesDisc()));
			    	if(bill.getSaleEmail() == null){
				    	saleEmail.setText(bill.getEmail());
			    	}else{
				    	saleEmail.setText(bill.getSaleEmail());
			    	}
			    	Customer_email.setText(bill.getEmail());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	class BooleanCells extends TableCell<OrderModel, Boolean> {
        private CheckBox checkBox;
        private Boolean lock = true;
        public BooleanCells() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean> () {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                	Boolean checked = isEditing();
                    if(isEditing()){
                     /*   String status = twOrder.getSelectionModel().getSelectedItems().get(0).getStatus();
                        if(status.equals(statusS[3])){
                            twOrder.getSelectionModel().getSelectedItems().get(0).setPayment(newValue);
                            Integer orderId = twOrder.getSelectionModel().getSelectedItems().get(0).getOrder_id();
                            commitEdit(newValue == null ? false : newValue);
                            try {
         						orderDao.updatePayment(orderId,newValue);
         					} catch (ClassNotFoundException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					} catch (SQLException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
                        }*/
                    }else{
                    	
                    }
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
        /*    String status = twOrder.getSelectionModel().getSelectedItems().get(0).getStatus();
            if(status.equals(statusS[3])){
            	checkBox.setDisable(false);
                checkBox.requestFocus();
            	System.out.println("1");
            }else{
                checkBox.setDisable(true);
            }*/
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty())
            {
            	if(item == null){
            		item = true;
            	}
            	checkBox.setSelected(item);

            }
        }
    }
	class BooleanCellsIssued  extends TableCell<OrderModel, Boolean> {
        private CheckBox checkBox;
        private Boolean lock = false;
        public BooleanCellsIssued () {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    	if(lock){
                			//checkBox.setDisable(true);
                			//lock = false;
                		}
                    	else if(!lock){
                    		//lock = true;
                    		//checkBox.setDisable(false);
                    	}
                    }
                }
            });
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean> () {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                	Boolean checked = isEditing();
                	System.out.println(isEditing());
                   // if(isEditing()){
                    /*    String status = twOrder.getSelectionModel().getSelectedItems().get(0).getStatus();
                       // if(status.equals(statusS[3])){
                            twOrder.getSelectionModel().getSelectedItems().get(0).setIssued(newValue);
                            Integer orderId = twOrder.getSelectionModel().getSelectedItems().get(0).getOrder_id();
                            commitEdit(newValue == null ? false : newValue);
                            try {
         						orderDao.updateIssued(orderId,newValue);
         					} catch (ClassNotFoundException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					} catch (SQLException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
                            lock = false;
                            checkBox.setDisable(true);*/
                       // }
                  //  }else{
                    	
                  // }
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
         /*   checkBox.requestFocus();
            if(lock){
    			checkBox.setDisable(true);
    			lock = false;
    		}
        	else if(!lock){
        		lock = true;
        		checkBox.setDisable(false);
        	}*/
           /* String status = twOrder.getSelectionModel().getSelectedItems().get(0).getStatus();
            if(status.equals(statusS[3])){
            	checkBox.setDisable(false);
                checkBox.requestFocus();
            	System.out.println("1");
            }else{
                checkBox.setDisable(true);
            }*/
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
           // checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
           // checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty())
            {
            	if(item == null){
            		item = true;
            	}
            	checkBox.setSelected(item);

            }
        }
    }
	class EditingCell extends TableCell<ProductModel, String> {
	    private TextField textField;
	    private String id = "";
	    public EditingCell(String ids) {
	    	this.id = ids;
	    }
	    public EditingCell() {
	    	
	    }
	    @Override
	    public void startEdit() {
	        super.startEdit();

	        if (textField == null) {
	            createTextField();
	        }
	        setGraphic(textField);
	        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	        textField.selectAll();
	        textField.requestFocus();
	    }

	    @Override
	    public void cancelEdit() {
	        super.cancelEdit();
	        String string = getItem() == null ?"":getItem();
	        setText(string);
	        setContentDisplay(ContentDisplay.TEXT_ONLY);
	    }

	    @Override
	    public void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty);

	        if (empty) {
	            setText("");
	            setGraphic(textField);
	        } else {
	            if (isEditing()) {
	                if (textField != null) {
	                    textField.setText(getString());
	                }
	                setGraphic(textField);
	                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            } else {
	                setText(getString());
	                setContentDisplay(ContentDisplay.TEXT_ONLY);
	            }
	        }
	    }

	    private void createTextField() {
	        textField = new TextField(getString());
	        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
	        textField.setOnKeyPressed(t -> {
	            if (t.getCode() == KeyCode.ENTER) {
	                commitEdit(textField.getText());
	            } else if (t.getCode() == KeyCode.ESCAPE) {
	                cancelEdit();
	            }
	        });
	        textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
			    @Override
			    public void handle(KeyEvent event) {
			    	if(id.equals("sku")){
				    	if(event.getCode() != KeyCode.BACK_SPACE){
				    		if(textField.getText().length()==5 && textField.getText().indexOf("-") == -1)
				    			textField.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "-", "-", KeyCode.RIGHT, false, false, false, false));
				    	}
			    	}
			    }
	    	});
	    }

	    private String getString() {
	        return getItem() == null ? "" : getItem();
	    }
	}
}