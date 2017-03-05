package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HomeController implements Initializable {

    Stage prevStage;
    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }
    @FXML
	public Button btnOrder;
    @FXML
    public Button btnInvoice;
    @FXML
    public Button btnCustomer;
    @FXML
    public Button btnTemp;
    @FXML
    public Button btnProduct;
    @FXML
    public Button btnSales;
    @FXML
    public Button btnPayment;
    
	@FXML
	private MenuItem menuItemOrders;
	@FXML
	private MenuItem menuItemOrdersTemp;
	@FXML
	private MenuItem menuItemInvoicer;
	@FXML
	private MenuItem menuItemCustomers;
	public void tranfer(){
		System.out.println("12341");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
		//a.confirmTranactionPosting(prevStage, "2");
	/*	 Stage dialogStage = new Stage();
		    dialogStage.initModality(Modality.WINDOW_MODAL);

		    VBox vbox = new VBox(new Text("Do you really want to delete ?"), new Button("Ok."), new Button("Cancel."));
		    vbox.setAlignment(Pos.CENTER);
		    vbox.setPadding(new Insets(15));
		    dialogStage.setTitle("Confirmation");
		    dialogStage.setScene(new Scene(vbox));
		    dialogStage.setWidth(300);
		    dialogStage.setResizable(false);
		    dialogStage.show();
		    dialogStage.setAlwaysOnTop(true);*/
		   //prevStage.close();
	/*	prevStage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	    @Override
    	    public void handle(KeyEvent evt) {
    	        if (evt.getCode().equals(KeyCode.DIGIT1)) {
    	        	btnOrder.requestFocus();
    	        }
    	    }
    	});*/
		btnOrder.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
						gotoOrders();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnInvoice.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		gotoInvoice();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnCustomer.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		gotoCustomer();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnTemp.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		gotoOrdersTemp();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnProduct.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		gotoProduct();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnSales.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		gotoSales();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnPayment.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		gotoPayment();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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
		menuItemCustomers.setAccelerator(KeyCombination.keyCombination("Ctrl+U"));

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
    public void gotoLogin(ActionEvent event) throws IOException {          
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
    public void gotoOrders(ActionEvent event) throws IOException {          
    	gotoOrders();
     }
    public void gotoInvoice(ActionEvent event) throws IOException {          
    	gotoInvoice();
     }
    public void gotoCustomer(ActionEvent event) throws IOException {          
    	gotoCustomer();
     }
    public void gotoOrdersTemp(ActionEvent event) throws IOException {          
    	gotoOrdersTemp();
     }
    public void gotoPayment(ActionEvent event) throws IOException {          
    	gotoPayment();
    }
    public void gotoProduct(ActionEvent event) throws IOException {          
    	gotoProduct();
     }
    public void gotoSales(ActionEvent event) throws IOException {          
    	gotoSales();
     }
    public void gotoPayment() throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Payment");
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
     }
    public void gotoOrdersTemp() throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Orders Temporary");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Orders.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        OrdersController controller = (OrdersController) myLoader.getController();
 	    controller.setPrevStage(stage);
 	    controller.setScreen("App Java");
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
								gotoOrdersTemp();
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
     }
    public void gotoInvoice() throws IOException {          
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
    	        		prevStage.show();
    	        		stage.close();
    	        	}
    	        }
    	        if (evt.getCode().equals(KeyCode.F4)) {
    	        	controller.txtKeySearchCus.requestFocus();
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
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
    	    @Override
    	    public void handle(KeyEvent evt) {
    	        if (evt.getCode().equals(KeyCode.ESCAPE)) {
    	        		prevStage.show();
    	        		stage.close();
    	        }
    	    }
    	});
     }

}