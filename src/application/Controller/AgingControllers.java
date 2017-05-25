package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.CustomerDao;
import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderDetailModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import application.Utill.Menu;
import application.Utill.Pdf;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AgingControllers extends Menu implements Initializable {

	public  String 	CustomerID ="";

	@FXML
	public TableView<OrderModel> twInvoice;	
	@FXML
	private TableColumn<OrderModel, String> twi_date;
	@FXML
	private TableColumn<OrderModel, String> twi_cusid;
	@FXML
	private TableColumn<OrderModel, String> twi_name;
	@FXML
	private TableColumn<OrderModel, String> twi_phone;
	@FXML
	private TableColumn<OrderModel, String> twi_terms;
	@FXML
	private TableColumn<OrderModel, String> twi_sale;
	@FXML
	private TableColumn<OrderModel, Integer> twi_invoice;
	@FXML
	private TableColumn<OrderModel, Integer> twi_amount;
	@FXML
	private TableColumn<OrderModel, String> twi_30;
	@FXML
	private TableColumn<OrderModel, String> twi_60;
	@FXML
	private TableColumn<OrderModel, String> twi_90;
	@FXML
	private TableColumn<OrderModel, String> twi_120;
	@FXML
	private TableColumn<OrderModel, String> twi_blance;
	@FXML
	private TableColumn<OrderModel, String> twi_remark;
	
	@FXML
	private TableColumn<CustomerModel, Boolean> tcs_cus;
	@FXML
	private TableColumn<CustomerModel, Boolean> tcs_name;
	@FXML
	private TableColumn<CustomerModel, Boolean> tcs_contact;
	@FXML
	private TableColumn<CustomerModel, Boolean> tcs_phone;
	@FXML
	private TableColumn<CustomerModel, Boolean> tcs_email;
	@FXML
	private TableColumn<CustomerModel, Boolean> tcs_city;
	@FXML
	public Button btnPrint;	

	// begin
		@FXML
		public TextField txtKeySearchCus;
		final String[] filterCus = new String[] { "ID","Name", "Contact", "Phone", "Email" ,"City" };
		private String str_filters = filterCus[0];
		@FXML
		private ChoiceBox cbFilterCus;
		@FXML
		public TableView<CustomerModel> twSearchCus;
		@FXML
		private Pane paneSearch;
	boolean isShowPopup = false;
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private OrderDao orderDao = new OrderDao();
    public void printAging() throws IOException{
    	btnPrint.setDisable(true);
    	//btnAging.setText("1 - PRINT AGING REPORT");
       // lblAging.setText("Printing...");
    		Thread thLoadData = new Thread() {
    			@SuppressWarnings("deprecation")
    			public void run() {
    					PdfCustomer pdf = new PdfCustomer();
    					Date date = new Date();
    					String tddate = dateFormat.format(date);
    					//LocalDate curentDate = LOCAL_DATE(cudate);
    					
    					List<OrderModel> lstOrder;
    					try {
    						if(CustomerID.equals("")){
        						lstOrder = orderDao.getOrderCustomerSale();	
    						}else{
        						lstOrder = orderDao.getOrderCustomerSale(CustomerID);
    						}
    							pdf.Print(lstOrder,tddate);
    						    Platform.runLater(new Runnable() {
    								  @Override
    								  public void run() {
    									  btnPrint.setDisable(false);
    									//  btnAging.setText("1 - PRINT AGING REPORT");
    								  }
    							});
    					} catch (ClassNotFoundException | SQLException  | IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    						btnPrint.setDisable(false);
    					//	btnAging.setText("1 - PRINT AGING REPORT");
    					}

    			}
    		};

    		thLoadData.start();
    }
    public void gotoPrint(ActionEvent event) throws IOException {          
    	printAging();
     }
	public void isShowSearchCus(boolean isShow){
		twSearchCus.setVisible(isShow);
		if(isShow == true){
			twInvoice.setLayoutY(210);
			twInvoice.setPrefHeight(457);
			twInvoice.refresh();
		}else{
			twInvoice.setLayoutY(68);
			twInvoice.setPrefHeight(600);
			twInvoice.refresh();
		}
	}
	public void actionSearchCus() throws IOException {
		  System.out.println("key ="+txtKeySearchCus.getText());
		  if(!txtKeySearchCus.getText().equals("")){
	  	  CustomerDao customerDao = new CustomerDao();
	  	  try {
	  		long start = System.nanoTime();    
	  		List<CustomerModel> lstCustomer = customerDao.getLstCustomerSearch(txtKeySearchCus.getText(),str_filters);
	  		long elapsedTime = System.nanoTime() - start;
	  		double seconds = (double)elapsedTime / 1000000000.0;
	  		System.out.println("selconds: "+seconds);
	  		// show result
	  		twSearchCus.getItems().clear();
	  		twSearchCus.getItems().addAll(lstCustomer);
	  		twInvoice.getItems().clear();
	  		if(lstCustomer.size()>0){
		  		twSearchCus.getSelectionModel().select(0);
	  			explanCustomer();
	  		}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		  }
		}
	
    public void actionSearchCus(EventHandler event) throws IOException {          
    	actionSearchCus();
    }
	public void explanCustomer(MouseEvent event) throws IOException {
	//	if(event.getClickCount() == 2){
			explanCustomer();
	//	}
	}	
	public void explanCustomer1(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			explanCustomer1();
		}
	}	
	   public void gotoHistory (String cusID) throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Customer Ledger Report");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/History.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        HistoryController controller = (HistoryController) myLoader.getController();
	 	    controller.setPrevStage(stage);
	 	    controller.CustomerID = cusID;
	        Scene scene = new Scene(myPane);
	        stage.setScene(scene);
	        prevStage.close();
	        stage.setResizable(false);
	        stage.show();
	        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent t) {
	                Platform.exit();
	                System.exit(0);
	            }
	        });
	        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	    	    @Override
	    	    public void handle(KeyEvent evt) {
	    	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
	    	        		prevStage.show();
	    	        		stage.close();
	    	        }
	    	    }
	    	});
	        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	     	    @Override
	     	    public void handle(KeyEvent evt) {
	     	    	if(controller.isControl == true){
	 	     	        String code = evt.getText();
	 	     	        if(code != null){
	 	 	    	        if(code.length()>0){
	 	 	    	        	 if (evt.getEventType() == KeyEvent.KEY_PRESSED && (!evt.isControlDown())) {
	 	 	    	    	        System.out.println("Home " +evt.getCode());
	 	 	    	    	        evt.fireEvent(scene,new KeyEvent(KeyEvent.KEY_PRESSED, null, null, evt.getCode(), false, true, false, false));
	 	 	    	    	     }
	 	 	    	        }
	 	     	        }
	     	    	}
	     	    }
	     	}); 

	     }
    public void gotoReceivables(String cusID) throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Account Statements");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Receivables.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        ReceivablesController controller = (ReceivablesController) myLoader.getController();
 	    controller.setPrevStage(stage);
 	    controller.CustomerID = cusID;
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        prevStage.close();
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	    @Override
    	    public void handle(KeyEvent evt) {
    	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
    	        		prevStage.show();
    	        		stage.close();
    	        }
    	    }
    	});
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
     	    @Override
     	    public void handle(KeyEvent evt) {
     	    	if(controller.isControl == true){
 	     	        String code = evt.getText();
 	     	        if(code != null){
 	 	    	        if(code.length()>0){
 	 	    	        	 if (evt.getEventType() == KeyEvent.KEY_PRESSED && (!evt.isControlDown())) {
 	 	    	    	        System.out.println("Home " +evt.getCode());
 	 	    	    	        evt.fireEvent(scene,new KeyEvent(KeyEvent.KEY_PRESSED, null, null, evt.getCode(), false, true, false, false));
 	 	    	    	     }
 	 	    	        }
 	     	        }
     	    	}
     	    }
     	}); 
    }
	   public void explanCustomer1() throws IOException {
			TablePosition pos = twInvoice.getFocusModel().getFocusedCell();

	    	  if (pos.getRow() == -1) {
	    		  twInvoice.getSelectionModel().select(0);
				}
				if (pos.getRow() >=0 && pos.getRow() < twInvoice.getItems().size()) {
					OrderModel o = new OrderModel();
					o = twInvoice.getSelectionModel().getSelectedItem();
					if(isNullOrEmptyAfterTrim(o.getCustomerId())){
						if(o.getOrder_id() == null){
							
						}else{
							System.out.println(o.getOrder_id());
							gotoPrintInvoice(o.getOrder_id());
						}
					}else{
						System.out.println(o.getCustomerId());
						String cus = o.getCustomerId();
						isShowPopup = true;
						ConfirmationCustomer a = new ConfirmationCustomer(prevStage, "Confirmation");
						a.show();
						a.setAlwaysOnTop(true);
						a.stage.setOnHiding(new EventHandler<WindowEvent>() {

				            @Override
				            public void handle(WindowEvent event) {
				                Platform.runLater(new Runnable() {

				                    @Override
				                    public void run() {
				                        System.out.println("Application Closed by click to Close Button(X)");
				                        System.out.println(a.postStatus);
				                        isShowPopup = false;
				                        if(a.postStatus == true){
				                        	try {
												gotoReceivables(cus);
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
				                        }else{
				                        		try {
													gotoHistory(cus);
												} catch (IOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
				                        }
				                        
				                    }
				                });
				            }
				        }); 
					}
				}
	   }
	   public void explanCustomer() throws IOException {   
			TablePosition pos = twSearchCus.getFocusModel().getFocusedCell();
			if (pos.getRow() == -1) {
				twSearchCus.getSelectionModel().select(0);
			}
			if (pos.getRow() < twSearchCus.getItems().size()) {
				CustomerModel c = new CustomerModel();
				c = twSearchCus.getSelectionModel().getSelectedItem();
				System.out.println(c.getCustomerID());
				CustomerID = c.getCustomerID();
			//	isShowSearchCus(false);
				twInvoice.getItems().clear();
				Thread thLoadData = new Thread() {
					@SuppressWarnings("deprecation")
					public void run() {
							Date date = new Date();
							String tddate = dateFormat.format(date);							
							List<OrderModel> lstOrder;
							try {
								lstOrder = orderDao.getOrderCustomerSale(CustomerID);
									Platform.runLater(new Runnable() {
										  @Override
										  public void run() {
											  	twInvoice.refresh();
												if(lstOrder.size() == 0){
													twInvoice.setPlaceholder(new Label("No found."));
												}else{
													//getOrderByCus(lstOrderCus.get(0).getCustomerId());
													System.out.println(lstOrder.size());
													List<OrderModel> lstOrderConvert = new ArrayList<>();
													if(lstOrder.size() > 0){
														String currentCusId = "";
														Float total = 0.f;
														Float totalP = 0.f;
														Float totalUP = 0.f;
														OrderModel or = new OrderModel();
														OrderModel orderConvert = new OrderModel();
														for (OrderModel order : lstOrder) {

															if(total>0){
															if(( !currentCusId.equals(order.getCustomerId())) ){
																or.setAll_Total("$"+String.format ("%,.2f",total));
																or.setAmoutPaid("$"+String.format ("%,.2f",totalP));
																or.setAmoutUnPaid("$"+String.format ("%,.2f",totalUP));
																or.setCustomer_date("Total:");
																total = 0.f;
																totalP = 0.f;
																totalUP = 0.f;
																lstOrderConvert.add(or);
																or = new OrderModel();
															}
															}
															if(( !currentCusId.equals(order.getCustomerId())) ){
																orderConvert.setCustomerId(order.getCustomerId());
																orderConvert.setCustomerName(order.getCustomerName());
																orderConvert.setCustomerPhone(order.getCustomerPhone());
																orderConvert.setCustomerTerms(order.getCustomerTerms());
																orderConvert.setCustomerSalesperson(order.getCustomerSalesperson());
																lstOrderConvert.add(orderConvert);
																orderConvert = new OrderModel();
																currentCusId = order.getCustomerId();


															}
															total = total + Float.parseFloat(order.getAll_Total().replace(",", ""));
															totalP = totalP + Float.parseFloat(order.getAmoutPaid().replace(",", ""));
															totalUP = totalUP + Float.parseFloat(order.getAmoutUnPaid().replace(",", ""));
															order.setAll_Total("$"+order.getAll_Total());
															order.setAmoutPaid("$"+order.getAmoutPaid());
															order.setAmoutUnPaid("$"+order.getAmoutUnPaid());
															
															orderConvert.setCustomer_date(order.getCustomer_date());
															orderConvert.setOrder_id(order.getOrder_id());
															orderConvert.setAll_Total(order.getAll_Total());
															lstOrderConvert.add(orderConvert);
															orderConvert = new OrderModel();
														}
														or.setAll_Total("$"+String.format ("%,.2f",total));
														or.setAmoutPaid("$"+String.format ("%,.2f",totalP));
														or.setAmoutUnPaid("$"+String.format ("%,.2f",totalUP));
														or.setCustomer_date("Total:");
														lstOrderConvert.add(or);
													}
														//pdf.Prints(lstOrder,tddate);
													twInvoice.getItems().clear();
													twInvoice.getItems().addAll(lstOrderConvert);
														Platform.runLater(new Runnable() {
															  @Override
															  public void run() {
																  	twInvoice.refresh();
																	if(lstOrder.size() == 0){
																		twInvoice.setPlaceholder(new Label("No found."));
																	}else{
																		
																	}
															  }
														});
												}
										  }
									});

							} catch (ClassNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					}
				};

				thLoadData.start();	

			}
	   }
	public static boolean isNullOrEmptyAfterTrim(String techmasterString) {
	        return (techmasterString == null || techmasterString.trim().length() == 0);
	}   
	public void gotoPrintInvoice(int orderID) throws IOException {
		try {
			Pdf pdf = new Pdf();
			OrderDetailModel orderDetail = orderDao.getOrderDetail(orderID);
			List<OrderModel>  lstOrder = orderDao.getOrderByOrderId(orderID);//orderDao.getOrderByOrderId(orderDetail.getCustomer().getCustomerID());
			OrderModel currentOrders =  lstOrder.get(0);
			pdf.PrintInvoice(orderDetail ,currentOrders);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inits();
		// begin
		btnPrint.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
						printAging();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		txtKeySearchCus.focusedProperty().addListener(new ChangeListener<Boolean>()
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
		        	if(!twSearchCus.isFocused()){
			        	isShowSearchCus(false);
		        	}
		        }
		    }
		});
		cbFilterCus.focusedProperty().addListener(new ChangeListener<Boolean>()
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
		        	if(!twSearchCus.isFocused()){
			        	isShowSearchCus(false);
		        	}
		        }
		    }
		});
		tcs_cus.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
		tcs_name.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		tcs_contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
		tcs_phone.setCellValueFactory(new PropertyValueFactory<>("Phone1"));
		tcs_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tcs_city.setCellValueFactory(new PropertyValueFactory<>("City"));
		isShowSearchCus(false);
		cbFilterCus.setItems(FXCollections.observableArrayList("Customer ID","Customer Name", "Contact", "Phone", "Email" ,"City"));
		cbFilterCus.setValue("Customer ID");
		cbFilterCus.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				str_filters = filterCus[new_value.intValue()];
				isShowSearchCus(true);
				System.out.println("--"+str_filters);
				try {
					actionSearchCus();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		txtKeySearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
				isShowSearchCus(true);
		      if(event.getCode() == KeyCode.DOWN){
		    	  twSearchCus.requestFocus();
		      }else if(event.getCode() == KeyCode.PAGE_DOWN){
		    		
		      }else{
		    	  	try {
						actionSearchCus();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		      }
		   }
		});  
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_UP){
		    	  txtKeySearchCus.requestFocus();
		    	  
		      }else{ 
		    	  if (event.getCode() == KeyCode.ENTER) {		
		    		  try {
						explanCustomer();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
		      }
		      if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP  ){
		    	  try {
						explanCustomer();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		      }
		   }
		}); 
		twInvoice.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER && isShowPopup == false){
		    	  try {
					explanCustomer1();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      }
		   }
		}); 
		twInvoice.setPlaceholder(new Label("Please wait… Searching Database."));
		twi_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		twi_cusid.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		twi_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		twi_phone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
		twi_terms.setCellValueFactory(new PropertyValueFactory<>("customerTerms"));
		twi_sale.setCellValueFactory(new PropertyValueFactory<>("customerSalesperson"));
		twi_invoice.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		twi_amount.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		twi_30.setCellValueFactory(new PropertyValueFactory<>("blance30"));
		twi_60.setCellValueFactory(new PropertyValueFactory<>("blance60"));
		twi_90.setCellValueFactory(new PropertyValueFactory<>("blance90"));
		twi_120.setCellValueFactory(new PropertyValueFactory<>("blance120"));
		twi_blance.setCellValueFactory(new PropertyValueFactory<>("blance"));
		twi_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));
		
		twi_date.setCellFactory(column -> { 
	        return new TableCell<OrderModel, String>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                if(item != null){
	                	setText(empty? "" : getItem().toString());
	                }else{
	                	setText("");
	                }
	                setGraphic(null);

	                TableRow<OrderModel> currentRow = getTableRow();

	                if (!isEmpty()) {
	                	//System.out.println(item);
	                    if(item == null) {
	                     	currentRow.setStyle("");
		                    /*	for(int i=0; i<getChildren().size();i++){
			                    	currentRow.getChildrenUnmodifiable().get(i).setStyle("");
			                    }*/
		                    	for(int i=0; i<currentRow.getChildrenUnmodifiable().size();i++){
		                    		((Labeled)currentRow.getChildrenUnmodifiable().get(i)).setStyle("");
		                    	}
		                    //Color.ALICEBLUE
	                    }else{

			                	if(item.equals("Total:")) {
			                		currentRow.setStyle("-fx-background-color:lightgreen");
			                    	//currentRow.setStyle("-fx-text-fill: red ! important;");
			                    	for(int i=0; i<currentRow.getChildrenUnmodifiable().size();i++){
			                    		//((Labeled)currentRow.getChildrenUnmodifiable().get(i)).setStyle("-fx-text-fill: red ! important;");
			                    	}
			                	}else{
			                    	currentRow.setStyle("");
			                    /*	for(int i=0; i<getChildren().size();i++){
				                    	currentRow.getChildrenUnmodifiable().get(i).setStyle("");
				                    }*/
			                    	for(int i=0; i<currentRow.getChildrenUnmodifiable().size();i++){
			                    		((Labeled)currentRow.getChildrenUnmodifiable().get(i)).setStyle("");
			                    	}
			                    
			                    //Color.ALICEBLUE
			                	}
	                }
	            }
	            }
	        };
	    });;
		 Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		        	txtKeySearchCus.requestFocus();
		        }
		    });
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
					Date date = new Date();
					String tddate = dateFormat.format(date);							
					List<OrderModel> lstOrder;
					try {
						lstOrder = orderDao.getOrderCustomerSale();
							Platform.runLater(new Runnable() {
								  @Override
								  public void run() {
									  	twInvoice.refresh();
										if(lstOrder.size() == 0){
											twInvoice.setPlaceholder(new Label("No found."));
										}else{
											//getOrderByCus(lstOrderCus.get(0).getCustomerId());
											System.out.println(lstOrder.size());
											List<OrderModel> lstOrderConvert = new ArrayList<>();
											if(lstOrder.size() > 0){
												String currentCusId = "";
												Float total = 0.f;
												Float totalP = 0.f;
												Float totalUP = 0.f;
												OrderModel or = new OrderModel();
												OrderModel orderConvert = new OrderModel();
												for (OrderModel order : lstOrder) {

													if(total>0){
													if(( !currentCusId.equals(order.getCustomerId())) ){
														or.setAll_Total("$"+String.format ("%,.2f",total));
														or.setAmoutPaid("$"+String.format ("%,.2f",totalP));
														or.setAmoutUnPaid("$"+String.format ("%,.2f",totalUP));
														or.setCustomer_date("Total:");
														total = 0.f;
														totalP = 0.f;
														totalUP = 0.f;
														lstOrderConvert.add(or);
														or = new OrderModel();
													}
													}
													if(( !currentCusId.equals(order.getCustomerId())) ){
														orderConvert.setCustomerId(order.getCustomerId());
														orderConvert.setCustomerName(order.getCustomerName());
														orderConvert.setCustomerPhone(order.getCustomerPhone());
														orderConvert.setCustomerTerms(order.getCustomerTerms());
														orderConvert.setCustomerSalesperson(order.getCustomerSalesperson());
														lstOrderConvert.add(orderConvert);
														orderConvert = new OrderModel();
														currentCusId = order.getCustomerId();


													}
													total = total + Float.parseFloat(order.getAll_Total().replace(",", ""));
													totalP = totalP + Float.parseFloat(order.getAmoutPaid().replace(",", ""));
													totalUP = totalUP + Float.parseFloat(order.getAmoutUnPaid().replace(",", ""));
													order.setAll_Total("$"+order.getAll_Total());
													order.setAmoutPaid("$"+order.getAmoutPaid());
													order.setAmoutUnPaid("$"+order.getAmoutUnPaid());
													orderConvert.setCustomer_date(order.getCustomer_date());
													orderConvert.setOrder_id(order.getOrder_id());
													orderConvert.setAll_Total(order.getAll_Total());
													lstOrderConvert.add(orderConvert);
													orderConvert = new OrderModel();
												}
												or.setAll_Total("$"+String.format ("%,.2f",total));
												or.setAmoutPaid("$"+String.format ("%,.2f",totalP));
												or.setAmoutUnPaid("$"+String.format ("%,.2f",totalUP));
												or.setCustomer_date("Total:");
												lstOrderConvert.add(or);
											}
												//pdf.Prints(lstOrder,tddate);
											twInvoice.getItems().clear();
											twInvoice.getItems().addAll(lstOrderConvert);
												Platform.runLater(new Runnable() {
													  @Override
													  public void run() {
														  	twInvoice.refresh();
															if(lstOrder.size() == 0){
																twInvoice.setPlaceholder(new Label("No found."));
															}else{
																
															}
													  }
												});
										}
								  }
							});

					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		};

		thLoadData.start();			
	}
	 
	public void getOrderByCus(String cusID){
		twInvoice.getItems().clear();
		twInvoice.setPlaceholder(new Label("Please wait… Searching Database."));
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
					Date date = new Date();
					String tddate = dateFormat.format(date);							
					List<OrderModel> lstOrder;
					try {
						lstOrder = orderDao.getOrderAging(cusID);
						System.out.println(lstOrder.size());
						if(lstOrder.size() > 0){
							Float total = 0.f;
							Float totalP = 0.f;
							Float totalUP = 0.f;
							OrderModel or = new OrderModel();
							for (OrderModel order : lstOrder) {
								total = total + Float.parseFloat(order.getAll_Total().replace(",", ""));
								totalP = totalP + Float.parseFloat(order.getAmoutPaid().replace(",", ""));
								totalUP = totalUP + Float.parseFloat(order.getAmoutUnPaid().replace(",", ""));
								order.setAll_Total("$"+order.getAll_Total());
								order.setAmoutPaid("$"+order.getAmoutPaid());
								order.setAmoutUnPaid("$"+order.getAmoutUnPaid());
							}
							or.setAll_Total("$"+String.format ("%,.2f",total));
							or.setAmoutPaid("$"+String.format ("%,.2f",totalP));
							or.setAmoutUnPaid("$"+String.format ("%,.2f",totalUP));
							or.setCustomer_date("");
							lstOrder.add(or);
						}
							//pdf.Prints(lstOrder,tddate);
						twInvoice.getItems().clear();
						twInvoice.getItems().addAll(lstOrder);
							Platform.runLater(new Runnable() {
								  @Override
								  public void run() {
									  	twInvoice.refresh();
										if(lstOrder.size() == 0){
											twInvoice.setPlaceholder(new Label("No found."));
										}else{
											
										}
								  }
							});

					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
		};

		thLoadData.start();			
	 }

}