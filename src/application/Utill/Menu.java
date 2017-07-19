package application.Utill;

import java.io.IOException;
import java.sql.SQLException;

import application.Controller.AccoutingController;
import application.Controller.AccoutingHomeController;
import application.Controller.ConfirmationWindowSave;
import application.Controller.CustomerController;
import application.Controller.EmailController;
import application.Controller.ExpressPaymentController;
import application.Controller.HomeController;
import application.Controller.InvoiceController;
import application.Controller.LoginController;
import application.Controller.OrdersController;
import application.Controller.OrdersExpressController;
import application.Controller.OrdersExpressStoreController;
import application.Controller.OrdersMemoController;
import application.Controller.PaymentController;
import application.Controller.ProductController;
import application.Controller.SalesController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Menu {
    public Stage prevStage;
	@FXML
	private MenuItem menuItemOrders;
	@FXML
	private MenuItem menuItemOrderss;
	@FXML
	private MenuItem menuItemOrdersTemp;
	@FXML
	private MenuItem menuItemCustomers;
	@FXML
	private MenuItem menuItemCustomerss;
	@FXML
	private MenuItem menuItemInvoicer;
	@FXML
	private MenuItem menuItemInvoicers;
	@FXML
	private MenuItem menuItemProducts;
	@FXML
	private MenuItem menuItemProductss;
	@FXML
	private MenuItem menuItemReports;
	@FXML
	private MenuItem menuItemReportss;
	@FXML
	private MenuItem menuItemPayment;
	@FXML
	private MenuItem menuItemPayments;
	@FXML
	private MenuItem menuItemExpress;
	@FXML
	private MenuItem menuItemExpresss;
	@FXML
	private MenuItem menuItemLogout;
	@FXML
	private MenuItem menuItemLogouts;
	@FXML
	private MenuItem menuItemAccounting;
	@FXML
	private MenuItem menuItemAccountings;
	@FXML
	private MenuItem menuItemPaymentExpress;
	@FXML
	private MenuItem menuItemPaymentExpresss;
	@FXML
	private MenuItem menuItemQuit;
	@FXML
	private MenuItem menuItemQuits;
	@FXML
	private Pane footer;

	
    public Boolean isControl = false;
    
	public void mouseEnter(MouseEvent event) throws IOException {
		if(event.getEventType() == MouseEvent.MOUSE_ENTERED){
			isControl = true;
			footer.requestFocus();
		}
		if(event.getEventType() == MouseEvent.MOUSE_EXITED){
			isControl = false;
			//Ctrl.setDisable(true);
			//Ctrl.setDisable(false);
			//Ctrl.setFocusTraversable(false);
			//footer.requestFocus();
		}
	}
    public void setPrevStage(Stage stage){
         this.prevStage = stage;
         
         prevStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
             @Override
             public void handle(WindowEvent t) {
                 Platform.exit();
                 System.exit(0);
             }
         });         
    }
    public void gotoLogin(ActionEvent event) throws IOException {          
    	gotoLogin();
    }

    public void inits(){

		menuItemOrders.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

		menuItemOrders.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoOrders();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemOrderss.setAccelerator(KeyCombination.keyCombination("F2"));

		menuItemOrderss.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoOrders();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemCustomers.setAccelerator(KeyCombination.keyCombination("Ctrl+C"));

		menuItemCustomers.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoCustomer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemCustomerss.setAccelerator(KeyCombination.keyCombination("F4"));

		menuItemCustomerss.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoCustomer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemProducts.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));

		menuItemProducts.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoProduct();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemProductss.setAccelerator(KeyCombination.keyCombination("F5"));

		menuItemProductss.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoProduct();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemReports.setAccelerator(KeyCombination.keyCombination("Ctrl+R"));

		menuItemReports.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoSales();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemReportss.setAccelerator(KeyCombination.keyCombination("F6"));

		menuItemReportss.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoSales();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemInvoicer.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));

		menuItemInvoicer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoInvoice();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemInvoicers.setAccelerator(KeyCombination.keyCombination("F7"));

		menuItemInvoicers.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoInvoice();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemPayment.setAccelerator(KeyCombination.keyCombination("Ctrl+B"));

		menuItemPayment.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoPayment();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemPayments.setAccelerator(KeyCombination.keyCombination("F8"));

		menuItemPayments.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoPayment();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemOrdersTemp.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));

		menuItemOrdersTemp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoOrdersTemp();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemExpress.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));

		menuItemExpress.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoOrdersExpress();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemExpresss.setAccelerator(KeyCombination.keyCombination("F3"));

		menuItemExpresss.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoOrdersExpress();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemLogout.setAccelerator(KeyCombination.keyCombination("Ctrl+L"));

		menuItemLogout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoLogin();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemLogouts.setAccelerator(KeyCombination.keyCombination("F11"));

		menuItemLogouts.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoLogin();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemAccounting.setAccelerator(KeyCombination.keyCombination("Ctrl+A"));

		menuItemAccounting.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoAccountingHome();
					//gotoSendEmail(148466);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemAccountings.setAccelerator(KeyCombination.keyCombination("F9"));

		menuItemAccountings.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoAccountingHome();
					//gotoSendEmail(148466);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemPaymentExpress.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));

		menuItemPaymentExpress.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoExpressPayment();
					//gotoSendEmail(148466);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemPaymentExpresss.setAccelerator(KeyCombination.keyCombination("F10"));

		menuItemPaymentExpresss.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoExpressPayment();
					//gotoSendEmail(148466);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		menuItemQuit.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

		menuItemQuit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
                System.exit(0);
			}
		});
		menuItemQuits.setAccelerator(KeyCombination.keyCombination("F12"));

		menuItemQuits.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
                System.exit(0);
			}
		});
		
		
    }
    public void gotoLogin() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Login.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        LoginController controller = (LoginController) myLoader.getController();
 	   controller.setPrevStage(stage);
 	   
        Scene scene = new Scene(myPane);

        stage.setScene(scene);
        prevStage.close();
        stage.setResizable(false);
        stage.show();
    }
    public void gotoHome() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Home");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
 	   FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Home.fxml"));
 	   Pane myPane = (Pane)myLoader.load();

        HomeController controller = (HomeController) myLoader.getController();
 	   controller.setPrevStage(stage);
        Scene scene = new Scene(myPane);
        stage.setScene(scene);

        prevStage.close();
        stage.setResizable(false);
        stage.show();
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
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	    @Override
    	    public void handle(KeyEvent evt) {
    	        if (evt.getCode().equals(KeyCode.DIGIT1) || evt.getCode().equals(KeyCode.NUMPAD1)) {
    	        	controller.btnOrder.requestFocus();
    	        }
    	        if (evt.getCode().equals(KeyCode.DIGIT2) || evt.getCode().equals(KeyCode.NUMPAD2)) {
    	        	controller.btnCustomer.requestFocus();
    	        }
    	        if (evt.getCode().equals(KeyCode.DIGIT3) || evt.getCode().equals(KeyCode.NUMPAD3)) {
    	        	controller.btnProduct.requestFocus();
    	        }
    	        if (evt.getCode().equals(KeyCode.DIGIT4) || evt.getCode().equals(KeyCode.NUMPAD4)) {
    	        	controller.btnSales.requestFocus();
    	        }
    	        if (evt.getCode().equals(KeyCode.DIGIT5) || evt.getCode().equals(KeyCode.NUMPAD5)) {
    	        	controller.btnInvoice.requestFocus();
    	        }
    	    /*    if (evt.getCode().equals(KeyCode.DIGIT6) || evt.getCode().equals(KeyCode.NUMPAD6)) {
 	        	controller.btnTemp.requestFocus();
 	        }*/
    	        if (evt.getCode().equals(KeyCode.DIGIT6) || evt.getCode().equals(KeyCode.DIGIT6)) {
 	        	controller.btnPayment.requestFocus();
 	        }
    	        
	  /*      if (evt.getCode().equals(KeyCode.ESCAPE)) {
	        		prevStage.show();
	        		stage.close();
	        }  */  
	        
    	    }
    	});
     }
	public void gotoPayment() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Billing");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Payment.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        PaymentController controller = (PaymentController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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
	    	    @SuppressWarnings("deprecation")
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
	    public void gotoSales() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Sales Reports");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Sales.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        SalesController controller = (SalesController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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
	    	        	if(!controller.stateEdit){
	    	        		prevStage.show();
	    	        		stage.close();
	    	        	}
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
	    public void gotoProduct() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Products");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Product.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        ProductController controller = (ProductController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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
	    	        	if(!controller.stateEdit){
	    	        		controller.chk = true;
	    	        		prevStage.show();
	    	        		stage.close();
	    	        	}
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
	 	 	    	    	        if(evt.getCode().equals("O") || evt.getCode().equals("U") || evt.getCode().equals("P") || evt.getCode().equals("R") || evt.getCode().equals("E") || evt.getCode().equals("E")){
	 	 	    	    	        	controller.chk = true;
	 	 	    	    	        }
	 	 	    	    	        evt.fireEvent(scene,new KeyEvent(KeyEvent.KEY_PRESSED, null, null, evt.getCode(), false, true, false, false));
	 	 	    	    	     }
	 	 	    	        }
	 	     	        }
	     	    	}
	     	    }
	     	}); 
	     }

	    public void gotoOrders() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Orders");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Orders.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        OrdersController controller = (OrdersController) myLoader.getController();
	 	    controller.setPrevStage(stage);
	        Scene scene = new Scene(myPane);
	        stage.setScene(scene);
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
	    	        	}else{
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
	    	        	}
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
	    public void gotoOrdersTemp() throws IOException {     
	    	 Stage stage = new Stage();
	         stage.setTitle("Temporary Orders");
	         stage.getIcons().add(new Image("file:resources/images/icon.png"));
	         FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Orders.fxml"));
	         Pane myPane = (Pane)myLoader.load();
	         
	        OrdersController controller = (OrdersController) myLoader.getController();
	  	    controller.setPrevStage(stage);
	  	    controller.setScreen("App Java");
	 	    controller.setCbFilter(false);
	 	    controller.setTw_payment(false);
	 	    controller.setTw_issued(false);
	 	    controller.setStatus(false);
	        Scene scene = new Scene(myPane);
	         stage.setScene(scene);
	         prevStage.close();
	         stage.setResizable(false);
	         stage.show();

	         scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	             int count = 0;
	     	    @Override
	     	    public void handle(KeyEvent evt) {
	     	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
		     	       	if(!controller.stateEdit){
		     	        	String temp = controller.getCscreen();
		     	        	System.out.println(temp);
		     	        	if(temp.equals("lstOrder")){
		     	        		prevStage.show();
		     	        		stage.close();
		     	        	}else{
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
		     	        	}
		     	        }
	     	       }
	     	    }
	     	});
		    scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		     	    @Override
		     	    public void handle(KeyEvent evt) {
		     	    	//if(isControl == true){
			     	        String code = evt.getText();
			     	        if(code != null){
			 	    	        if(code.length()>0){
			 	    	        	 if (evt.getEventType() == KeyEvent.KEY_PRESSED && (!evt.isControlDown())) {
			 	    	    	        System.out.println("Home " +evt.getCode());
			 	    	    	        evt.fireEvent(scene,new KeyEvent(KeyEvent.KEY_PRESSED, null, null, evt.getCode(), false, true, false, false));
			 	    	    	     }
			 	    	        }
			     	        }
		     	    	//}
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
	    public void gotoOrdersExpress() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Orders Express");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/OrdersExpress.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        OrdersExpressController controller = (OrdersExpressController) myLoader.getController();
	 	    controller.setPrevStage(stage);
	        Scene scene = new Scene(myPane);
	  	    controller.setScreen("Express");
	        stage.setScene(scene);
	        prevStage.close();
	        stage.setResizable(false);
	        stage.show();

	        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	            int count = 0;
	    	    @Override
	    	    public void handle(KeyEvent evt) {
	    	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
	    	        	String temp = controller.getCscreen();
	    	        	String screen = controller.getScreen();
	    	        	System.out.println(temp);
	    	        	if(temp.equals("lstOrder")){
	    	        		prevStage.show();
	    	        		stage.close();
	    	        	}else{
	    	        		count++;
	    	        		if(count ==1 ){
		    	        		try {
		    	        			stage.close();
		    	        			if(screen.equals("Express")){
		    	        				gotoOrdersExpress();
		    	        			}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	    	        		}
	    	        	}
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
	    public void gotoOrdersExpressStore(String CusId) throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Orders Express");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/OrdersExpressStore.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        OrdersExpressStoreController controller = (OrdersExpressStoreController) myLoader.getController();
	 	    controller.setPrevStage(stage);
	        Scene scene = new Scene(myPane);
	        controller.cusID = CusId;
	  	    controller.setScreen("Express");
	        stage.setScene(scene);
	        prevStage.close();
	        stage.setResizable(false);
	        stage.show();

	        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	            int count = 0;
	    	    @Override
	    	    public void handle(KeyEvent evt) {
	    	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
	    	        	String temp = controller.getCscreen();
	    	        	String screen = controller.getScreen();
	    	        	System.out.println(temp);
	    	        	if(temp.equals("lstOrder")){
	    	        		prevStage.show();
	    	        		stage.close();
	    	        	}else{
	    	        		count++;
	    	        		if(count ==1 ){
		    	        		try {
		    	        			stage.close();
		    	        			if(screen.equals("Express")){
		    	        				gotoOrdersExpressStore(controller.cusID);
		    	        			}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	    	        		}
	    	        	}
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
	    public void gotoInvoice() throws IOException {          
	  /*      Stage stage = new Stage();
	        stage.setTitle("Create Invoice");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Invoice.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        InvoiceController controller = (InvoiceController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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
	    	        if (evt.getCode().equals(KeyCode.F4)) {
	    	        	controller.txtKeySearchCus.requestFocus();
	    	        }
	    	    }
	    	});*/
	    	if(prevStage.getTitle().equals("Temporary Orders") || prevStage.getTitle().equals("Create Invoice")){ //
	    		gotoInvoices();
	    	}else{
		    	gotoOrdersTemp();	    		
	    	}
	     }
	    public void gotoInvoices() throws IOException {          
	  	        Stage stage = new Stage();
	  	        stage.setTitle("Create Invoice");
	  	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	  	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Invoice.fxml"));
	  	        Pane myPane = (Pane)myLoader.load();
	  	        
	  	        InvoiceController controller = (InvoiceController) myLoader.getController();
	  	 	    controller.setPrevStage(stage);
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
	  	    	        	//	prevStage.show();
	  	    	        //		stage.close();
	  	    	        		int count = controller.twOrderDetail.getItems().size();
	  	    	        		int counts =0;
	  	    	        		boolean isExitCus = false;
	  	    	        		for(int i =0;i<count;i++){
		  	    	      			String sub = controller.twOrderDetail.getItems().get(i).getTotal();
		  	    	      			if(sub != null && !sub.isEmpty()){
		  	    	      				counts++;
		  	    	      			}
	  	    	        		}
	  	    	        	if(controller.customer != null){
	  	    	        			if((!controller.customer.getCustomerID().equals("")) && controller.bill_cus_id.getLength()>0){
	  	    	        				isExitCus = true;
	  	    	        			}
	  	    	        	}
	  	    	        	if(counts>0 && isExitCus == true){
	  	    	        		System.out.println(counts+"--"+isExitCus);
	  	    	        		ConfirmationWindowSave a = new ConfirmationWindowSave(prevStage, "Confirmation");
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
		  	  			                        if(a.postStatus == true){
		  	  			        					//
					  	  			                    System.out.println("Save...");
					  	  			                    controller.twOrderDetail.requestFocus();
					  	  			                    controller.twOrderDetail.getSelectionModel().clearSelection();
					  	  			                    controller.twSearchCus.getSelectionModel().select(0);
					  	  			                    controller.twOrderDetail.getSelectionModel().select(0, controller.twd_sku1);
					  	  			                   try {
					  	  			                	controller.SaveOrder();
					  	  			      			} catch (ClassNotFoundException | SQLException e) {
					  	  			      				// TODO Auto-generated catch block
					  	  			      				e.printStackTrace();
					  	  			      			}
		  	  			                        }else{
		  	  			                        	prevStage.show();
		  	  	  		  	    	        		stage.close();
		  	  			                        }
		  	  			                    }
		  	  			                });
		  	  			            }
		  	  			        }); 
	  	    	        	}else{
		  	    	        	prevStage.show();
	  		  	    	        stage.close();
	  	    	        	}
	  	    	        		
	  	    	        	}
	  	    	        }
	  	    	        if (evt.getCode().equals(KeyCode.F1)) {
	  	    	        	controller.txtKeySearchCus.requestFocus();
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
	  	 	 	    		    	 if(evt.getCode().equals("F")){
	  	 	 	    		  	        controller.txtKeySearch.setText("");
	  	 	 	    		    	 }
	  	 	 	    	    	        evt.fireEvent(scene,new KeyEvent(KeyEvent.KEY_PRESSED, null, null, evt.getCode(), false, true, false, false));
	  	 	 	    	    	     }
	  	 	 	    	        }
	  	 	     	        }
	  	     	    	}
	  	     	    }
	  	     	}); 
	  	 }
	    public void gotoCustomer() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Customer");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Customer.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        CustomerController controller = (CustomerController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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
	    public void gotoAccounting() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Accounting");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Accounting.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        AccoutingController controller = (AccoutingController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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
	    public void gotoAccountingHome() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Accounting Home");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/AccountingHome.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        AccoutingHomeController controller = (AccoutingHomeController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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

	    	        		if(controller.txtEmail.isFocused()){
	    	        			controller.statusPrint = "";
	    	        			controller.isShowEmail(false);
	    	        		}else{
		    	        		prevStage.show();
		    	        		stage.close();
	    	        		}
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
	        controller.txtEnterSelect.setText("1");
	        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	       	    @Override
	       	    public void handle(KeyEvent evt) {
	       	    	if(!controller.txtEnterSelect.isFocused() && !controller.txtKeySearchCus.isFocused() && !controller.txtEmail.isFocused() && !controller.tx.isFocused()){
	    	   	        if (evt.getCode().equals(KeyCode.DIGIT1) || evt.getCode().equals(KeyCode.NUMPAD1)) {
	    	   	        	controller.btnAging.requestFocus();
	    	   	        	controller.txtEnterSelect.setText("1");
	    	   	        }
	    	   	        if (evt.getCode().equals(KeyCode.DIGIT2) || evt.getCode().equals(KeyCode.NUMPAD2)) {
	    	   	        	controller.btnReceivablesP.requestFocus();
	    	   	        	controller.txtEnterSelect.setText("2");
	    	   	        }
	    	   	        if (evt.getCode().equals(KeyCode.DIGIT3) || evt.getCode().equals(KeyCode.NUMPAD3)) {
	    	   	        	controller.btnHistoryP.requestFocus();
	    	   	        	controller.txtEnterSelect.setText("3");
	    	   	        }
	    	        
	       	    	}
	       	    }
	       	});
	     }
	    public void gotoCreditMemo() throws IOException { 
	        Stage stage = new Stage();
	        stage.setTitle("Orders Credit Memo");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/OrdersMemo.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        OrdersMemoController controller = (OrdersMemoController) myLoader.getController();
	 	    controller.setPrevStage(stage);
	        Scene scene = new Scene(myPane);
	        stage.setScene(scene);
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
	    	        	//prevStage.show();
    	        		//stage.close();
    	        		if(temp.equals("lstOrder")){
	     	        		prevStage.show();
	     	        		stage.close();
	     	        	}else{
	     	        		count++;
	     	        		if(count ==1 ){
	 	    	        		try {
	 	    	        			stage.close();
	 	    	        			gotoCreditMemo();
	 							} catch (IOException e) {
	 								// TODO Auto-generated catch block
	 								e.printStackTrace();
	 							}
	     	        		}
	     	        	}
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
	    public void gotoExpressPayment() throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Express Payment");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ExpressPayment.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        ExpressPaymentController controller = (ExpressPaymentController) myLoader.getController();
	 	    controller.setPrevStage(stage);
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
	    
	    public void gotoSendEmail(int orderid) throws IOException {          
	        Stage stage = new Stage();
	        stage.setTitle("Send Email");
	        stage.getIcons().add(new Image("file:resources/images/icon.png"));
	        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/SendEmail.fxml"));
	        Pane myPane = (Pane)myLoader.load();
	        
	        EmailController controller = (EmailController) myLoader.getController();
	 	    controller.setPrevStage(stage);
	 	   controller.setOrderid(orderid);
	        Scene scene = new Scene(myPane);
	        stage.setScene(scene);
	        prevStage.close();
	        stage.setResizable(false);
	        stage.show();

	        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	            int count = 0;
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
