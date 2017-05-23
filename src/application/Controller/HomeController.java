package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Dao.CustomerDao;
import application.Model.CustomerModel;
import application.Utill.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HomeController extends Menu implements Initializable {

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
    public Button btnExpress;
    @FXML
    public Button btnAccounting;
    @FXML
    public Button btnPaymentExpress;
    
	@FXML
	public TextField txtEnterSelect;
	public void tranfer(){
		System.out.println("12341");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inits();
		//btnOrder.setUnderline(true);
		//btnOrder.setMnemonicParsing(false);
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
						gotoOrders();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        }
				   if (number == 2) {
						try {
							gotoCustomer();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		   	        }
				   if (number == 3) {
					   try {
						gotoProduct();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        }
				   if (number == 4) {
					   try {
						gotoSales();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        }
				   if (number == 5) {
					   try {
						gotoInvoice();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		   	        }
		   	    /*    if (evt.getCode().equals(KeyCode.DIGIT6) || evt.getCode().equals(KeyCode.NUMPAD6)) {
			        	controller.btnTemp.requestFocus();
			        }*/
				   if (number == 6) {
					   try {
						gotoPayment();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        }
				   if (number == 7) {
					   try {
						gotoOrdersExpress();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        }
				   if (number == 8) {
					   try {
						gotoAccounting();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        }
				   if (number == 9) {
					   try {
						   gotoExpressPayment();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        }
			   }
		    }
		});	   
		btnOrder.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		txtEnterSelect.setText("1");
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
	            		txtEnterSelect.setText("5");
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
	            		txtEnterSelect.setText("2");
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
	            		txtEnterSelect.setText("3");
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
	            		txtEnterSelect.setText("4");
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
	            		txtEnterSelect.setText("6");
	            		gotoPayment();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		
		btnExpress.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		txtEnterSelect.setText("7");
	            		gotoOrdersExpress();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnAccounting.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		txtEnterSelect.setText("8");
	            		gotoAccountingHome();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
		btnPaymentExpress.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
	            		txtEnterSelect.setText("9");
	            		gotoExpressPayment();
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

    public void gotoOrders(ActionEvent event) throws IOException {     
    	txtEnterSelect.setText("1");
    	gotoOrders();
     }
    public void gotoInvoice(ActionEvent event) throws IOException {       
    	txtEnterSelect.setText("5");
    	gotoInvoice();
     }
    public void gotoCustomer(ActionEvent event) throws IOException {  
    	txtEnterSelect.setText("2");
    	gotoCustomer();
     }
    public void gotoOrdersTemp(ActionEvent event) throws IOException {          
    	gotoOrdersTemp();
     }
    public void gotoPayment(ActionEvent event) throws IOException {  
    	txtEnterSelect.setText("6");
    	gotoPayment();
    }
    public void gotoExpress(ActionEvent event) throws IOException {     
    	txtEnterSelect.setText("7");
    	gotoOrdersExpress();
    }
    public void gotoProduct(ActionEvent event) throws IOException {       
    	txtEnterSelect.setText("3");
    	gotoProduct();
     }
    public void gotoSales(ActionEvent event) throws IOException {   
    	txtEnterSelect.setText("4");
    	gotoSales();
     }
    public void gotoAccounting(ActionEvent event) throws IOException {    
    	txtEnterSelect.setText("8");
    //	gotoAccounting();
    	gotoAccountingHome();
     }
    public void gotoExpressPayment(ActionEvent event) throws IOException {      
    	txtEnterSelect.setText("9");
    	gotoExpressPayment();
     }
    

}