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

import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import application.Utill.Menu;
import application.Utill.PdfCustomer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class HistoryController extends Menu implements Initializable {
	@FXML
	private MenuItem menuItemEmailToCustomer;
	public  String 	CustomerID ="";
	@FXML
	public TableView<OrderModel> twCus;
	@FXML
	public TableView<OrderModel> twInvoice;
	@FXML
	private TableColumn<OrderModel, String> tws_cus;
	@FXML
	private TableColumn<OrderModel, String> tws_cus_name;
	@FXML
	private TableColumn<OrderModel, String> tws_term;
	@FXML
	private TableColumn<OrderModel, String> tws_slsmn;
	@FXML
	private TableColumn<OrderModel, String> tws_phone;
	@FXML
	private TableColumn<OrderModel, String> tws_contact;
	
	@FXML
	private TableColumn<OrderModel, Integer> twi_invoice;
	@FXML
	private TableColumn<OrderModel, String> twi_date;
	@FXML
	private TableColumn<OrderModel, String> twi_chk;
	@FXML
	private TableColumn<OrderModel, String> twi_invoices;
	@FXML
	private TableColumn<OrderModel, String> twi_payment;
	@FXML
	private TableColumn<OrderModel, String> twi_discount;
	@FXML
	private TableColumn<OrderModel, String> twi_balance;
	@FXML
	public Button btnEmail;	
	String email = "";
	String emailTo = "";
	boolean isShowPopup = false;
	boolean isCustomEmail = false;
	private boolean emailAgingWait = false;
	private boolean emailReceivablesWait = false;
	private boolean emailHistoryWait = false;
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private OrderDao orderDao = new OrderDao();
	@FXML
	public Button btnPrint;	
    public void gotoEmail(ActionEvent event) throws IOException {   
    	ConfirmationEmail a = new ConfirmationEmail(prevStage, "Confirmation");
        a.setOnCloseRequest(new EventHandler<WindowEvent>() {
             @Override
             public void handle(WindowEvent t) {
                a.chkClose = true;
             }
         }); 
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
                        System.out.println(event.getEventType());
                        if( a.chkClose == false){
	                        if(a.postStatus == true){
	                        	try {
									Report11E();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	                        }else{
	                        	ConfirmationEmailField b = new ConfirmationEmailField(prevStage, "Confirmation");
	                            b.setOnCloseRequest(new EventHandler<WindowEvent>() {
	                                 @Override
	                                 public void handle(WindowEvent t) {
	                                    a.chkClose = true;
	                                 }
	                             }); 
	                    		b.show();
	                    		b.setAlwaysOnTop(true);
	                    		b.stage.setOnHiding(new EventHandler<WindowEvent>() {

	                                @Override
	                                public void handle(WindowEvent event) {
	                                    Platform.runLater(new Runnable() {

	                                        @Override
	                                        public void run() {
	                                            System.out.println("Application Closed by click to Close Button(X)");
	                                            System.out.println(a.postStatus);
	                                            System.out.println(event.getEventType());
	                                            if( b.chkClose == false){
	                    	                        if(b.postStatus == true){
	                    	                        	
	                    	                        }else{
	                    	                        	//
	                    	                        	isCustomEmail = true;
	                    	                        	emailTo = b.email;
	                    	                        	try {
															Report11E();
														} catch (IOException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
	                    	                        }
	                                            }
	                                        }
	                                    });
	                                }
	                            });
	                        }
                        }
                    }
                });
            }
        });
    	Report11E();
     }
	public void Report11E() throws IOException { 
		btnEmail.setDisable(true);
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
					PdfCustomer pdf = new PdfCustomer();
					Date date = new Date();
					String tddate = dateFormat.format(date);							
					List<OrderModel> lstOrder;
					try {
						lstOrder = orderDao.getOrderCustomerSale1(CustomerID);
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
						btnEmail.setDisable(false);
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
		if(isCustomEmail == true){
			emailto = emailTo;
		}
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
		  final String password="Z@eqQ^hnA~9R";//change accordingly  
		    
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
		     //popup success show
				isShowPopup = true;
				ConfirmationEmailMessage a = new ConfirmationEmailMessage(prevStage, "Confirmation");
		        a.setOnCloseRequest(new EventHandler<WindowEvent>() {
		             @Override
		             public void handle(WindowEvent t) {
		                a.chkClose = true;
		             }
		         }); 
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
		                        System.out.println(event.getEventType());
		                        if( a.chkClose == false){
			                        if(a.postStatus == true){
			                        	
			                        }else{
			                        	
			                        }
		                        }
		                    }
		                });
		            }
		        }); 
				
			     if(emailAgingWait == true){
					  emailAgingWait = false;
			     }
			     if(emailReceivablesWait == true){
			    	 emailReceivablesWait = false;
			     }
			     if(emailHistoryWait== true){
			    	 btnEmail.setDisable(false);
			    	 emailHistoryWait = false;
			     }
		     } catch (MessagingException e) {
		    	 e.printStackTrace();
		    	 if(emailAgingWait == true){
					  emailAgingWait = false;
			     }
			     if(emailReceivablesWait == true){
			    	 btnEmail.setDisable(false);
			    	 emailReceivablesWait = false;
			     }
			     if(emailHistoryWait== true){
			    	 emailHistoryWait = false;
			     }
		     }  
		
	}
	public void Report11() throws IOException { 
		btnPrint.setDisable(true);
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
					PdfCustomer pdf = new PdfCustomer();
					Date date = new Date();
					String tddate = dateFormat.format(date);							
					List<OrderModel> lstOrder;
					try {
						lstOrder = orderDao.getOrderCustomerSale1(CustomerID);
							pdf.Prints1(lstOrder,tddate);
							Platform.runLater(new Runnable() {
								  @Override
								  public void run() {
									  btnPrint.setDisable(false);
								  }
							});

					} catch (ClassNotFoundException | SQLException  | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						btnPrint.setDisable(false);
					}

			}
		};

		thLoadData.start();		
}

    public void gotoPrint(ActionEvent event) throws IOException {          
    	Report11();
     }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inits();
		// begin
		menuItemEmailToCustomer.setAccelerator(KeyCombination.keyCombination("Ctrl+M"));

		menuItemEmailToCustomer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					Report11E();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnPrint.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
						Report11();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		btnEmail.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
						Report11E();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	        }
	    });
		twCus.setPlaceholder(new Label("Please wait… Searching Database."));
		twInvoice.setPlaceholder(new Label("Please wait… Searching Database."));

		tws_cus.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		tws_cus_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		tws_term.setCellValueFactory(new PropertyValueFactory<>("customerTerms")); //
		tws_slsmn.setCellValueFactory(new PropertyValueFactory<>("customerSalesperson"));//
		tws_phone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
		tws_contact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
		
		twi_invoice.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		twi_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		twi_chk.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
		//twi_total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		twi_payment.setCellValueFactory(new PropertyValueFactory<>("amoutPaid"));
		twi_invoices.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		
		twi_discount.setCellValueFactory(new PropertyValueFactory<>("disc"));
		//twi_unpaid.setCellValueFactory(new PropertyValueFactory<>("amoutUnPaid"));
		twi_invoice.setCellFactory(column -> { 
		        return new TableCell<OrderModel, Integer>() {
		            @Override
		            protected void updateItem(Integer item, boolean empty) {
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
		                        currentRow.setStyle("-fx-background-color:lightgreen");
		                    	//currentRow.setStyle("-fx-text-fill: red ! important;");
		                    	for(int i=0; i<currentRow.getChildrenUnmodifiable().size();i++){
		                    		((Labeled)currentRow.getChildrenUnmodifiable().get(i)).setStyle("-fx-text-fill: red ! important;");
		                    	}
		                    }else{
		                    	currentRow.setStyle("");
		                    /*	for(int i=0; i<getChildren().size();i++){
			                    	currentRow.getChildrenUnmodifiable().get(i).setStyle("");
			                    }*/
		                    	for(int i=0; i<currentRow.getChildrenUnmodifiable().size();i++){
		                    		((Labeled)currentRow.getChildrenUnmodifiable().get(i)).setStyle("");
		                    	}
		                    }
		                    //Color.ALICEBLUE
		                }
		            }
		        };
		    });
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
					Date date = new Date();
					String tddate = dateFormat.format(date);							
					List<OrderModel> lstOrder;
					try {
						lstOrder = orderDao.getOrderCustomerSale1(CustomerID);
						if(lstOrder.size() > 0){
							Float total = 0.f;
							Float totalP = 0.f;
							Float totalUP = 0.f;
							Float totalDS = 0.f;

							OrderModel or = new OrderModel();
							for (OrderModel order : lstOrder) {
								total = total + Float.parseFloat(order.getAll_Total().replace(",", ""));
								totalP = totalP + Float.parseFloat(order.getAmoutPaid().replace(",", ""));
								totalUP = totalUP + Float.parseFloat(order.getAmoutUnPaid().replace(",", ""));
								totalDS = totalDS + Float.parseFloat(order.getDisc().replace(",", ""));

								order.setAll_Total("$"+order.getAll_Total());
								order.setAmoutPaid("$"+order.getAmoutPaid());
								order.setAmoutUnPaid("$"+order.getAmoutUnPaid());
								order.setDisc("$"+order.getDisc());

							}
							or.setAll_Total("$"+String.format ("%,.2f",total));
							or.setAmoutPaid("$"+String.format ("%,.2f",totalP));
							or.setAmoutUnPaid("$"+String.format ("%,.2f",totalUP));
							or.setDisc("$"+String.format ("%,.2f",totalDS));
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
											twCus.setPlaceholder(new Label("No found."));
											twInvoice.setPlaceholder(new Label("No found."));
										}else{
											twCus.getItems().clear();
											twCus.getItems().addAll(lstOrder.get(0));
											email = lstOrder.get(0).getCustomer_email();
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