package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import application.Dao.CustomerDao;
import application.Model.CustomerModel;
import application.Model.OrderModel;
import application.Utill.Menu;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ConfirmationEmailFieldController extends Menu implements Initializable {
    Stage owner;
    Stage stage;
    public static boolean postStatus = true;
    boolean chkClose = false;
    @FXML
	public Button btnkok;
    @FXML
	public TextField txtEmail;
    @FXML
	public TextField keySearch;
	@FXML
	private TableView<CustomerModel> twSearchCus;
	public String email = "";
	final String[] filter = new String[] { "ID","Name", "Contact", "Phone", "Email" ,"City","combind" };
	private String str_filters = filter[6];
	@FXML
	private TableColumn<CustomerModel, String> tcs_cus;
	@FXML
	private TableColumn<CustomerModel, String> tcs_name;
	@FXML
	private TableColumn<CustomerModel, String> tcs_email;
	// end
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		setContents();
	}

    public void ConfirmationWindowAccounts( Stage owner, Stage stages){
        this.owner = owner;
        this.stage = stages;
      //  setContents();
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
	public void doubleClick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			Entertb();
		}
	}
	public void Entertb(){
		TablePosition pos = twSearchCus.getFocusModel().getFocusedCell();

		if (pos.getRow() == -1) {
			twSearchCus.getSelectionModel().select(0);
		}
		if (pos.getRow() < twSearchCus.getItems().size()) {
			CustomerModel c = twSearchCus.getSelectionModel().getSelectedItem();
			System.out.println(c.getCustomerID());
			if (c.getCustomerID() != null) {
				txtEmail.setText(c.getEMail());
				txtEmail.requestFocus();
			} else {
				
			}
		}
	}
    public static boolean isValidEmailAddress(String email) {
 	   boolean result = true;
 	   try {
 	      InternetAddress emailAddr = new InternetAddress(email);
 	      emailAddr.validate();
 	   } catch (AddressException ex) {
 	      result = false;
 	   }
 	   return result;
   }
   public void actionSearchCus(EventHandler event) throws IOException {          
   	actionSearchCus();
   }
	 public void setContents(){
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			    @Override
			    public void handle(KeyEvent event) {
			      if(event.getCode() == KeyCode.PAGE_UP){
			    	  keySearch.requestFocus();
			      }else{ 
			    	  if (event.getCode() == KeyCode.ENTER ) {
			    		  Entertb();
					  }
			    	  if (event.getCode() == KeyCode.PAGE_DOWN ) {
			    		  txtEmail.requestFocus();
			    	  }
			      }
			   }
			});  
		keySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.DOWN){
		    	  twSearchCus.requestFocus();
		      }else{ 
		    	  if(event.getCode() == KeyCode.TAB){
		    		  
		    	  }else{
		    		  try {
						actionSearchCus();
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
		tcs_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			    @Override
			    public void handle(KeyEvent event) {
			    	
			    }
		});
        btnkok.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent e) {
            	email = txtEmail.getText();
             	if(isValidEmailAddress(email) == true){
                     postStatus =false;
                     stage.close();
             	}else{
             		
             	}
             }
         });
         btnkok.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
         	@Override
 			public void handle(KeyEvent event) {
         		if(event.getCode() == KeyCode.ENTER){
         			email = txtEmail.getText();
                 	if(isValidEmailAddress(email) == true){
                         postStatus =false;
                         stage.close();
                 	}else{
                 		
                 	}
         		}
         	}
     	});
         Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		           // tx.requestFocus();
		        }
		    });
         try {
			actionSearchCus();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}         
         
         }

        public static boolean confirmTranactionPosting(Stage owner, String title) {

           new ConfirmationWindowAccountController(owner, title);
           System.out.println(postStatus);
           return postStatus;
         }

}