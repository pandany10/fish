package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import application.Dao.CustomerDao;
import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderModel;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class AccoutingHomeController extends Menu implements Initializable {

    @FXML
	public Button btnAging;
    @FXML
    public Button btnReceivablesP;
    @FXML
    public Button btnHistoryP;
    
	@FXML
	private Pane paneSearch;
	@FXML
	private Pane paneEmail;
	@FXML
	private Pane paneChoice;
	@FXML
	public Button btnSendEmail;
	@FXML
	public TextField txtEmail;
	@FXML
	public TextField tx;
	String email = "";
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private OrderDao orderDao = new OrderDao();
	@FXML
	public TextField txtEnterSelect;
	public void tranfer(){
		System.out.println("12341");
	}

	public String statusPrint = "";
	private int  statusbuton = 0;
	private boolean emailAgingWait = false;
	private boolean emailReceivablesWait = false;
	private boolean emailHistoryWait = false;

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
	CustomerModel c = new CustomerModel();
	// end
    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inits();
		// begin
		btnSendEmail.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	if (event.getCode() == KeyCode.ENTER) {
						try {
							SendEmail();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
		   }
		}); 
        tx.textProperty().addListener((ov, oldValue, newValue) -> {
         	tx.setText(newValue.toUpperCase());
        });
         addTextLimiter(tx, 1);
		isShowSearchCus(false);
		isShowEmail(false);
		isShowChoice(false);
		cbFilterCus.setItems(FXCollections.observableArrayList("Customer ID","Customer Name", "Contact", "Phone", "Email" ,"City"));
		cbFilterCus.setValue("Customer ID");
		cbFilterCus.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				str_filters = filterCus[new_value.intValue()];
				System.out.println("--"+str_filters);
				try {
					actionSearchCus();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		try {
			actionSearchCusInit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
		txtKeySearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
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
		   }
		});   
		tcs_cus.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
		tcs_name.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		tcs_contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
		tcs_phone.setCellValueFactory(new PropertyValueFactory<>("Phone1"));
		tcs_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tcs_city.setCellValueFactory(new PropertyValueFactory<>("City"));
/*		tcs_email.setCellFactory(column -> {
		    return new TableCell<CustomerModel, String>() {
		        @Override
		        protected void updateItem(String item, boolean empty) {
		            super.updateItem(item, empty); //This is mandatory

		            if (item == null || empty) { //If the cell is empty
		                setText(null);
		                setStyle("");
		            } else { //If the cell is not empty

		                setText(item); //Put the String data in the cell

		                //We get here all the info of the Person of this row
		                Person auxPerson = getTableView().getItems().get(getIndex());

		                // Style all persons wich name is "Edgard"
		                if (auxPerson.getName().equals("Edgard")) {
		                    setTextFill(Color.RED); //The text in red
		                    setStyle("-fx-background-color: yellow"); //The background of the cell in yellow
		                } else {
		                    //Here I see if the row of this cell is selected or not
		                    if(getTableView().getSelectionModel().getSelectedItems().contains(auxPerson))
		                        setTextFill(Color.WHITE);
		                    else
		                        setTextFill(Color.BLACK);
		                }
		            }
		        }
		    };
		});*/
		// end
		txtEnterSelect.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){        	
			    	if (newValue.matches("\\d*")) {
			            int value = Integer.parseInt(newValue);
			        } else {
			        	txtEnterSelect.setText(oldValue);
			        }
		        }
		    }
		});
		txtEnterSelect.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
			   if(event.getCode() == KeyCode.ENTER){
				   int number = Integer.parseInt(txtEnterSelect.getText());
				   if (number == 1) {
					   try {
						   gotoAging();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        }
				   if (number == 2) {
						try {
							gotoReceivables1();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		   	        }
				   if (number == 3) {
					   try {
						   gotoHistory1();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        }
			   }
		    }
		});	   
		btnAging.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		 gotoAging();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnReceivablesP.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		gotoReceivables1();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnHistoryP.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		 gotoHistory1();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });


		 txtEnterSelect.requestFocus();
		 txtEnterSelect.selectEnd();
	}
	public void isShowSearchCus(boolean isShow){
		//if(isShow == true){
			paneSearch.setVisible(isShow);
		//}
			if(isShow == true){
				txtKeySearchCus.requestFocus();
			}
	}
	public void isShowEmail(boolean isShow){
		//if(isShow == true){
		paneEmail.setVisible(isShow);
		//}
		if(isShow == true){
			txtEmail.requestFocus();
		}
	}
	public void isShowChoice(boolean isShow){
		paneChoice.setVisible(isShow);
		if(isShow == true){
			tx.requestFocus();
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
	  		twSearchCus.getSelectionModel().select(0);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		  }
		}
		public void actionSearchCusInit() throws IOException {
			  System.out.println("key ="+txtKeySearchCus.getText());
		  	  CustomerDao customerDao = new CustomerDao();
		  	  try {
		  		long start = System.nanoTime();    
		  		List<CustomerModel> lstCustomer = customerDao.getLstCustomerSearch(txtKeySearchCus.getText(),"Name");
		  		long elapsedTime = System.nanoTime() - start;
		  		double seconds = (double)elapsedTime / 1000000000.0;
		  		System.out.println("selconds: "+seconds);
		  		// show result
		  		twSearchCus.getItems().clear();
		  		twSearchCus.getItems().addAll(lstCustomer);
		  		twSearchCus.getSelectionModel().select(0);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
	    public void actionSearchCus(EventHandler event) throws IOException {          
	    	actionSearchCus();
	    }
		public void explanCustomer(MouseEvent event) throws IOException {
			if(event.getClickCount() == 2){
				explanCustomer();
			}
		}	    
	   public void explanCustomer() throws IOException {   
			TablePosition pos = twSearchCus.getFocusModel().getFocusedCell();
			if (pos.getRow() == -1) {
				twSearchCus.getSelectionModel().select(0);
			}
			if (pos.getRow() < twSearchCus.getItems().size()) {
				c = twSearchCus.getSelectionModel().getSelectedItem();
				isShowSearchCus(false);
				isShowChoice(false);
				if(statusPrint.equals("2")){
					//btnReceivablesP.setDisable(true);
					Report1();
				}
				if(statusPrint.equals("3")){
				//	btnHistoryP.setDisable(true);
					Report11();
				}
				if(statusPrint.equals("4")){
					gotoReceivables();
				}
				if(statusPrint.equals("5")){
					gotoHistory();
				}
				if(statusPrint.equals("6")){
					//email aging
					printAgingE();
				}
				if(statusPrint.equals("7")){
				//	btnReceivablesP.setVisible(true);
				//	Report1E();
					isShowEmail(true);
				}
				if(statusPrint.equals("8")){
				//	btnHistoryP.setDisable(true);
				//	Report11E();
					isShowEmail(true);
				}
				if(statusPrint.equals("9")){
					System.out.println("1221");
					gotoAging1();
				}
			}
	   }
		public void Report11() throws IOException { 
			 	btnHistoryP.setDisable(true);
				System.out.println("tttt:"+c.getCustomerID());
				Thread thLoadData = new Thread() {
					@SuppressWarnings("deprecation")
					public void run() {
							PdfCustomer pdf = new PdfCustomer();
							Date date = new Date();
							String tddate = dateFormat.format(date);							
							List<OrderModel> lstOrder;
							try {
								lstOrder = orderDao.getOrderCustomerSale1(c.getCustomerID());
									pdf.Prints1(lstOrder,tddate);
									Platform.runLater(new Runnable() {
										  @Override
										  public void run() {
											  btnHistoryP.setDisable(false);
										  }
									});

							} catch (ClassNotFoundException | SQLException  | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								btnHistoryP.setDisable(false);
							}
		
					}
				};
		
				thLoadData.start();		
		}
		
		public void Report1() throws IOException { 
			 btnReceivablesP.setDisable(true);
				System.out.println("tttt:"+c.getCustomerID());
				Thread thLoadData = new Thread() {
					@SuppressWarnings("deprecation")
					public void run() {
							PdfCustomer pdf = new PdfCustomer();
							Date date = new Date();
							String tddate = dateFormat.format(date);							
							List<OrderModel> lstOrder;
							try {
								lstOrder = orderDao.getOrderCustomerSale(c.getCustomerID());
									pdf.Prints(lstOrder,tddate);
									Platform.runLater(new Runnable() {
										  @Override
										  public void run() {
											  btnReceivablesP.setDisable(false);
										  }	
									});

							} catch (ClassNotFoundException | SQLException  | IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								btnReceivablesP.setDisable(false);
							}
		
					}
				};
		
				thLoadData.start();		

		}   
		public void Report11E() throws IOException { 
			btnHistoryP.setDisable(true);
			System.out.println("tttt:"+c.getCustomerID());
			Thread thLoadData = new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
						PdfCustomer pdf = new PdfCustomer();
						Date date = new Date();
						String tddate = dateFormat.format(date);							
						List<OrderModel> lstOrder;
						try {
							lstOrder = orderDao.getOrderCustomerSale1(c.getCustomerID());
								pdf.Prints1E(lstOrder,tddate);
								Platform.runLater(new Runnable() {
									  @Override
									  public void run() {
											String replacedString = tddate.replace("/", "");
											replacedString = replacedString.replace("/", "");
											String cusid = "";
											if(lstOrder.size()>0){
												cusid = lstOrder.get(0).getCustomerId();
											}
											String attach ="Customer Ledger #"+cusid+".pdf";	
    										String subject ="Customer Ledger #"+cusid;
    										emailHistoryWait = true;
    										sendEmail(attach, subject);
    										
											}
								});

						} catch (ClassNotFoundException | SQLException  | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							btnHistoryP.setDisable(false);
						}
	
				}
			};
	
			thLoadData.start();		
	}
		public void Report1E() throws IOException { 
			btnReceivablesP.setDisable(true);
			System.out.println("tttt:"+c.getCustomerID());
			Thread thLoadData = new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
						PdfCustomer pdf = new PdfCustomer();
						Date date = new Date();
						String tddate = dateFormat.format(date);							
						List<OrderModel> lstOrder;
						try {
							lstOrder = orderDao.getOrderCustomerSale(c.getCustomerID());
								pdf.PrintsE(lstOrder,tddate);
								Platform.runLater(new Runnable() {
									  @Override
									  public void run() {
											String replacedString = tddate.replace("/", "");
											replacedString = replacedString.replace("/", "");
											String cusid = "";
											if(lstOrder.size()>0){
												cusid = lstOrder.get(0).getCustomerId();
											}
											String attach ="Account Statement #"+cusid+".pdf";
    										String subject ="Account Statement "+"#"+cusid;
    										emailReceivablesWait = true;
    										sendEmail(attach, subject);
										  }
								});

						} catch (ClassNotFoundException | SQLException  | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							btnReceivablesP.setDisable(false);
						}
	
				}
			};
	
			thLoadData.start();		

	} 
		// EnterChoice
	public void EnterChoice(ActionEvent event) throws IOException {          
    	String txt = tx.getText();
    	if(txt.equals("E")){   		
			isShowChoice(false);
			if(statusbuton == 1){
				statusPrint = "6";
				try {
					gotoAgingE();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(statusbuton == 2){
				isShowSearchCus(true);
				statusPrint = "7";
			}
			
			if(statusbuton == 3){
				isShowSearchCus(true);
				statusPrint = "8";
			}

    	}
    	if(txt.equals("P")){
			isShowChoice(false);
    		if(statusbuton == 1){
    			try {
					printAging();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(statusbuton == 2){
				isShowSearchCus(true);
				statusPrint = "2";
			}
			
			if(statusbuton == 3){
				isShowSearchCus(true);
				statusPrint = "3";
			}
    	}
    	if(txt.equals("V")){
			isShowChoice(false);
    		if(statusbuton == 1){
    			statusPrint = "9";
				//isShowSearchCus(true);
				gotoAging1();
			}
			
			if(statusbuton == 2){
				statusPrint = "4";
				isShowSearchCus(true);
			}
			
			if(statusbuton == 3){
				statusPrint = "5";
				isShowSearchCus(true);
			}
    	}
    }	
    public void gotoAging(ActionEvent event) throws IOException {          
    	gotoAging();
     }
    public void gotoReceivables1(ActionEvent event) throws IOException {          
    	gotoReceivables1();
     }
    public void gotoHistory1(ActionEvent event) throws IOException {          
    	gotoHistory1();
     }
    public void SendEmail(ActionEvent event) throws IOException {          
    	SendEmail();
     }
    public void SendEmail() throws IOException{
    	String emails = txtEmail.getText();
    	if(emails.indexOf('@') != -1){
    		email = emails;
    		//isShowSearchCus(true);
    		isShowEmail(false);
   		
    		if(statusPrint.equals("2")){
				btnReceivablesP.setDisable(true);
				Report1();
			}
			if(statusPrint.equals("3")){
				btnHistoryP.setDisable(true);
				Report11();
			}
			if(statusPrint.equals("4")){
				gotoReceivables();
			}
			if(statusPrint.equals("5")){
				gotoHistory();
			}
			if(statusPrint.equals("6")){
				//email aging
				printAgingE();
			}
			if(statusPrint.equals("7")){
				//btnReceivablesP.setVisible(true);
				Report1E();
			}
			if(statusPrint.equals("8")){
				//btnHistoryP.setVisible(true);
				Report11E();
			}
    	}
    }
    public void printAging() throws IOException{
    	btnAging.setDisable(true);
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
    						lstOrder = orderDao.getOrderCustomerSale();
    							pdf.Print(lstOrder,tddate);
    						    Platform.runLater(new Runnable() {
    								  @Override
    								  public void run() {
    									  btnAging.setDisable(false);
    									//  btnAging.setText("1 - PRINT AGING REPORT");
    								  }
    							});
    					} catch (ClassNotFoundException | SQLException  | IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    						btnAging.setDisable(false);
    					//	btnAging.setText("1 - PRINT AGING REPORT");
    					}

    			}
    		};

    		thLoadData.start();
    }
    public void printAgingE() throws IOException{
    	btnAging.setDisable(true);
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
    						lstOrder = orderDao.getOrderCustomerSale();
    							pdf.PrintE(lstOrder,tddate);
    						    Platform.runLater(new Runnable() {
    								  @Override
    								  public void run() {
    										String replacedString = tddate.replace("/", "");
    										replacedString = replacedString.replace("/", "");
    										String attach ="Aging-Report-"+replacedString+".pdf";
    										String subject ="Aging Report Date "+tddate;
    										emailAgingWait = true;
    										sendEmail(attach, subject);

    									//  btnAging.setText("1 - PRINT AGING REPORT");
    								  }
    							});
    					} catch (ClassNotFoundException | SQLException  | IOException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    						btnAging.setDisable(false);
    					//	btnAging.setText("1 - PRINT AGING REPORT");
    					}

    			}
    		};

    		thLoadData.start();
    }
	public void sendEmail(String attach,String subject){
		System.out.println("send..");
		//email
		//body
		//attach
		String emailto = email;
		String body ="<div><br><br><br></div><div>";
		String signature = "<span style='font-size:11.0pt;'>Francisco Sanchez (frankie)</span><br><br><br>";
		signature = signature +"<a href='tel:(310)%20648-7258' value='+13106487258' target='_blank'>310-648-7258 ext. 11</a><br>";
		signature = signature +"<a href='tel:(310)%20648-7611' value='+13106487611' target='_blank'>310-648-7611</a> fax<br>";
		signature = signature +"<a href='tel:(310)%20869-1070' value='+13108691070' target='_blank'>310-869-1070</a> cell<br>";
		signature = signature +"<span style='font-size:12.0pt;'>Exotic Reef Imports, Inc.</span><br>";
		signature = signature +"<span lang='ES-MX' style='font-size:12.0pt;'>1924-A Maple Ave.</span><br>";
		signature = signature +"<span lang='ES-MX' style='font-size:12.0pt;'>El Segundo, CA 90245</span><br>";
		signature = signature +"<a href='http://www.exoticreefimports.com/' target='_blank' ><font size='2' color='blue'><span lang='ES-MX' style='font-size:10.0pt;color:blue'>www.exoticreefimports.com</span></font></a><br><br><br>";
		signature = signature +"<a href='https://www.facebook.com/pages/Exotic-Reef-Imports-Inc/136406809722089' target='_blank'><img border='0' width='40' height='40' src='https://gm1.ggpht.com/_Vv0H3sNlwAWkC52QH6zsvgfLLrJ9q3RIxftAnjMPC0XF2dWrmpO2h1XNtu-8nAcMbjoAUul1h3K4VAfuvxB02REoLjpyl_YcaEFmkwtVwydwbiz1S9CW4o4uBsL8h8Sg4MUJtJ_finhQkTqJ1gGN-eW6YHPIWvth1fJIfqAB_lynn9f_OA64DWGjJqTIcKuroPseWC09G5er0uzvgxyKL6Z9_hPMhw6a1JEb9fzAvJpcVERRN1Og9fYnOBpi3ICLBWeBg3t0ktqF83jfXUa8q4iESxzTXnaCAnSOd6QBz4nVt7qAPK19PQErQFatzPOptsGsyf6djZymrOoRKkLjfQlR_r1irTheSnds8WDhZaBD-hP_A-PPZ__NBjaWO1CRO2abEPPzALFka0wp28z3mUnAhy27b66ND4mm5S8_t-lTWi62q9tMrVJuHYa__w8Ryp4oobC65kVGwonT_-vF75YAxy97KZeqnoWEBjficKxxC1Wpczpz8frSlhqYRrUx9S48JHrkdIciNTMHb_DCFBzCYlTKUJDmGEeGTB0iy8Xv5TQMNpWrsQ0SmyyD_elgl7hFfoUXDwx3-A3n_XubgakoLopz6W_Ejyii1vFRJfwkI_TsMuR-K9fIces0ie3DpP5KbQzaML3GzZ9S5rEYxHtNZh5dUgy9HxyybQNxefS6vGcWK3XtTc85FlqWI_xRJh6HEOQY0lqXagswtAm7RGUZZzu=w80-h80-l75-ft' alt='unnamed'></a>";
		signature = signature +"<a href='https://instagram.com/exoticreefimports/' target='_blank' ><img border='0' width='98' height='33'  src='https://gm1.ggpht.com/mVnoiLnXkbYCFKQS8WgPeXYpl1rRwFshwVYQtSwKY4w7l7Xx3vn3wnw1wyBFNMB4NajYhqTll6bR2XJM6BRXeb4t3qLwpW_THB9trR1B3Z_Rf3X1V3AyJpWgMl2zdvQ1OiZ3oajcSvUR0BVLyA-KsHyNbqYsxIjgJf0bKiJiR_Km__5bxAZI_xZA1u7IyylW_rVV0nbEGI-lsYmtTIK-ktddQ-W1YEqpIvLebPgPtzQLg3MIgtW9MnQfogW3yvxyWI23fU2O33fGHZBqAxUlG9h5wlbV8Ch-eZ8GKwxVnKQ32L47J9LqLbqkLfRqpA8P7aretNKkhYPXvzmxmFw8aXkZayJp8dn3MBoao8KVGhVin9gFrwlo8fB0QAMN4KUTx6XkRBY3x2o7QhPYDyehhrqBMAqQShS-cpmiDfmyl-Aw6tx9ok_TiwJpyygw2Uu6wAPDAAIKHKj21lE_yzBFk8GaqnvPlVtf0NZT2xpbrf2E4GeD48dBwVzSe5pPbFCSezLMpX3gHBpfQIosGV1ORmw8erIFMDx_ZPy9vBFMODj9z1nYhWCULa7CJWvfSPhPKcQwO5nI-xZQxjQ5yXusBRD2wQcYD_zHG2LDLcoQeXirNmMFUCxhKjNZl2iPiFwvro6ThBYi4KxtKTFzbJXYxFRqcBQHMnFLzACL9XyULx_Lweo-tZ-mdvF-i0iuhgXNXtNPuesMNdx_q6FG5LpvWzFsYnUx=w196-h66-l75-ft' alt='instagram' class='CToWUd'></a>";
		signature = signature +"<a href='https://twitter.com/ExoticReefImp' target='_blank' ><img border='0' width='46' height='46'  src='https://gm1.ggpht.com/uBlK_m3z-4sf1nPlyKpyhQooFFGlNCuu2MjkxtlFq55MdH4FcRtODh6yeICh12wtoZhnhtu37Mdho1GMPTiY1NkJlvp9sR_0h_Ldd5LEyl91mpSOqxPvx82BP6jsroP4uTq7kZpPwI-csy0T6i0RMWhW9MOiDD74fnQ8vEj13ADLkXJoKO03VMQrFD1DOfBPLMw7grJnZ485yU0SAcqP1Jj0Q7BrXzBSrgRX7wd1DbcIfn0y6Ts4d2EoXjQfihfahHLmjQIpC4Cemv_k6bTtBNjehFGxNgrdjSFyOCSx4eO2Yub1AHXDqxFpIWeMaUCr-sH72IJJIZNQe2ik3iYk-_McVyiD-POpqfj2kfo6d4l8lMI7KLFzEedAZVfOC5cJU30BtPGKhht_OU-ivT-R_HbgWDl50KFu8Z-_bZgPselCgjmFQOrsKd-VkIOKBrUS6ZMv4yB7-zFHMHXAuQytXasvVuBNRKLi2J1dkceBBn0Uu90bRyOo_Caa1NBxmk9zsvpNnwo-mrrksYhkvkZ46Dzy0pG84OvN5cJ4E65ZcQ3jEOtVfNHViAhdPKHFAdO3gErt6hi-DnRTys114AnwEBRCt_QUcNfdNU_qmCnCWRFuD3_7M137qdPWQTiFjhaI8qlHXqrIPeREUJJnAgXF30kQLoo6B7jlJ2OP9SveX5UbxaYbbyGVSOGUsqODDB39482HGLccYV9n3ZoR6oQ0uhTqXS5l=w92-h92-l75-ft' alt='logo-email-twitter' class='CToWUd'></a></div>";
		body = body + signature;
		String[] emailtos = emailto.split(";");
		System.out.println(emailtos[0]);

		 String host="secure.emailsrvr.com";  
		  final String user="orders@exoticreefimports.com";//change accordingly  
		  final String password="trigger1924*";//change accordingly  
		    
		  String to="avictim404@gmail.com";//change accordingly  
		  String to1="remymedranda@gmail.com";//change accordingly  
		  
		   //Get the session object  
		   Properties props = new Properties();  
		   props.put("mail.smtp.host",host);  
		   props.put("mail.transport.protocol.", "smtp");
		   props.put("mail.smtp.auth", "true");  
		   props.put("mail.smtp.port", "587");
		     
		   Session session = Session.getDefaultInstance(props,  
		    new javax.mail.Authenticator() {  
		      protected PasswordAuthentication getPasswordAuthentication() {  
		    return new PasswordAuthentication(user,password);  
		      }  
		    });  
		  
		   //Compose the message  
		    try {  
		     MimeMessage message = new MimeMessage(session);  
		     message.setFrom(new InternetAddress(user));  
		     for (String email : emailtos){
			     message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));  
		     }

		     message.addRecipient(Message.RecipientType.CC,new InternetAddress(to));  
		     message.setSubject(subject);  
		     
		     BodyPart messageBodyPart1 = new MimeBodyPart();     
	        // messageBodyPart1.setText(body);
	         messageBodyPart1.setContent(body, "text/html; charset=utf-8");
		     //4) create new MimeBodyPart object and set DataHandler object to this object        
	         MimeBodyPart messageBodyPart2 = new MimeBodyPart();      
	         String filename = attach;//change accordingly     
	         DataSource source = new FileDataSource(filename);    
	         messageBodyPart2.setDataHandler(new DataHandler(source));    
	         messageBodyPart2.setFileName(filename);             

	         //5) create Multipart object and add MimeBodyPart objects to this object        
	         Multipart multipart = new MimeMultipart();    
	         multipart.addBodyPart(messageBodyPart1);     
	         multipart.addBodyPart(messageBodyPart2);
	         message.setContent(multipart );
		    //send the message  
		     Transport.send(message);  
		  
		     System.out.println("message sent successfully..."); 
			     if(emailAgingWait == true){
					  btnAging.setDisable(false);
					  emailAgingWait = false;
			     }
			     if(emailReceivablesWait == true){
			    	 btnReceivablesP.setDisable(false);
			    	 emailReceivablesWait = false;
			     }
			     if(emailHistoryWait== true){
			    	 btnHistoryP.setDisable(false);
			    	 emailHistoryWait = false;
			     }
		     } catch (MessagingException e) {
		    	 e.printStackTrace();
		    	 if(emailAgingWait == true){
					  btnAging.setDisable(false);
					  emailAgingWait = false;
			     }
			     if(emailReceivablesWait == true){
			    	 btnReceivablesP.setDisable(false);
			    	 emailReceivablesWait = false;
			     }
			     if(emailHistoryWait== true){
			    	 btnHistoryP.setDisable(false);
			    	 emailHistoryWait = false;
			     }
		     }  
		
	}
    public void gotoAging() throws IOException {
    //	statusPrint = "1";
    	statusbuton = 1;
    	isShowEmail(false);
    	isShowSearchCus(false);
    	isShowChoice(true);
    	txtEnterSelect.setText("1");
    	/*Stage stage = new Stage();
        stage.setTitle("Confirmation");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ConfirmationWindowAccount.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        AccoutingHomeController2 controller = (AccoutingHomeController2) myLoader.getController();
        Scene scene = new Scene(myPane);       
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.initModality( Modality.WINDOW_MODAL );
        stage.initOwner( prevStage );
        stage.initStyle( StageStyle.UTILITY ); 
        stage.setTitle( "Confirmation");
       
        stage.show();
        controller.ConfirmationWindowAccounts(prevStage,stage);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("Application Closed by click to Close Button(X)");
                        System.out.println(controller.postStatus);
                        if(controller.postStatus == true){
        					if(controller.tx.getText().equals("E")){
        						try {
									gotoAgingE();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
        					if(controller.tx.getText().equals("P")){
        						try {
									printAging();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
        					if(controller.tx.getText().equals("V")){
        						
        					}
                        }else{
                        	
                        }
                    }
                });
          
	            }
	        });	*/
    }
    public void gotoReceivablesP() throws IOException {
		isShowSearchCus(true);
		txtKeySearchCus.requestFocus();
		statusPrint = "2";
    }
    public void gotoHistoryP() throws IOException {
		isShowSearchCus(true);
		txtKeySearchCus.requestFocus();
		statusPrint = "3";
    }
    public void gotoReceivablesV() throws IOException {
    	isShowSearchCus(true);
		txtKeySearchCus.requestFocus();
		statusPrint = "4";
    }
    public void gotoHistoryV() throws IOException {
    	isShowSearchCus(true);
		txtKeySearchCus.requestFocus();
		statusPrint = "5";
    }
    public void gotoAgingE() throws IOException {
    	//isShowSearchCus(true);
    	isShowEmail(true);
    	txtEmail.requestFocus();
		statusPrint = "6";
    }
    public void gotoReceivablesE() throws IOException {
    	//isShowSearchCus(true);
    	isShowEmail(true);
    	txtEmail.requestFocus();
		statusPrint = "7";
    }
    public void gotoHistoryE() throws IOException {
    	//isShowSearchCus(true);
    	isShowEmail(true);
    	txtEmail.requestFocus();
		statusPrint = "8";
    }
    
    public void gotoHistory1 () throws IOException { 
       	statusbuton = 3;
       	isShowEmail(false);
    	isShowSearchCus(false);
    	isShowChoice(true);
    	txtEnterSelect.setText("3");
/*    	isShowEmail(false);
    	Stage stage = new Stage();
        stage.setTitle("Confirmation");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ConfirmationWindowAccount.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        AccoutingHomeController2 controller = (AccoutingHomeController2) myLoader.getController();
        Scene scene = new Scene(myPane);       
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.initModality( Modality.WINDOW_MODAL );
        stage.initOwner( prevStage );
        stage.initStyle( StageStyle.UTILITY ); 
        stage.setTitle( "Confirmation");
       
        stage.show();
        controller.ConfirmationWindowAccounts(prevStage,stage);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("Application Closed by click to Close Button(X)");
                        System.out.println(controller.postStatus);
                        if(controller.postStatus == true){
        					if(controller.tx.getText().equals("E")){
        						try {
									gotoHistoryE();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
        					if(controller.tx.getText().equals("P")){
        						try {
        							gotoHistoryP();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
        					if(controller.tx.getText().equals("V")){
        						try {
									gotoHistoryV();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
                        }else{
                        	
                        }
                    }
                });
          
	            }
	        });	*/
    }
    public void gotoHistory () throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Customer Ledger Report");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/History.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        HistoryController controller = (HistoryController) myLoader.getController();
 	    controller.setPrevStage(stage);
 	    controller.CustomerID = c.getCustomerID();
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
    public void gotoReceivables1() throws IOException {   
       	statusbuton = 2;
       	isShowEmail(false);
    	isShowSearchCus(false);
    	isShowChoice(true);
    	txtEnterSelect.setText("2");
/*    	isShowEmail(false);
    	Stage stage = new Stage();
        stage.setTitle("Confirmation");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ConfirmationWindowAccount.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        AccoutingHomeController2 controller = (AccoutingHomeController2) myLoader.getController();
        Scene scene = new Scene(myPane);       
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.initModality( Modality.WINDOW_MODAL );
        stage.initOwner( prevStage );
        stage.initStyle( StageStyle.UTILITY ); 
        stage.setTitle( "Confirmation");
       
        stage.show();
        controller.ConfirmationWindowAccounts(prevStage,stage);

        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("Application Closed by click to Close Button(X)");
                        System.out.println(controller.postStatus);
                        if(controller.postStatus == true){
        					if(controller.tx.getText().equals("E")){
        						try {
									gotoReceivablesE();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
        					if(controller.tx.getText().equals("P")){
        						try {
        							gotoReceivablesP();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
        					if(controller.tx.getText().equals("V")){
        						try {
									gotoReceivablesV();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
        					}
                        }else{
                        	
                        }
                    }
                });
          
	            }
	        });	*/
    }
    public void gotoReceivables() throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Account Statements");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Receivables.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        ReceivablesController controller = (ReceivablesController) myLoader.getController();
 	    controller.setPrevStage(stage);
 	    controller.CustomerID = c.getCustomerID();
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
    public void gotoAging1() throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Open Aging Report");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Agings.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        AgingControllers controller = (AgingControllers) myLoader.getController();
 	    controller.setPrevStage(stage);
 	    //controller.CustomerID = c.getCustomerID();
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
    public void gotoAging1BackUp() throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Open Aging Report");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Aging.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        AgingController controller = (AgingController) myLoader.getController();
 	    controller.setPrevStage(stage);
 	    //controller.CustomerID = c.getCustomerID();
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
}