package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.ProductDao;
import application.Model.ProductModel;
import application.Utill.Menu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfirmationSearchSkuController extends Menu implements Initializable {
    Stage owner;
    Stage stage;
    public static boolean postStatus = true;
    boolean chkClose = false;
    @FXML
	public Button btnkok;
    @FXML
	public TextField txtEmail;
    @FXML
	public TextField txtKeySearch;
    
	@FXML
	public TableView<ProductModel> twResultSearch;
	@FXML
	private TableColumn<ProductModel, Boolean> tws_sku1;
	@FXML
	private TableColumn<ProductModel, Boolean> tws_name;
	@FXML
	private TableColumn<ProductModel, Boolean> tws_size;
	@FXML
	private TableColumn<ProductModel, Boolean> tws_price;
	@FXML
	private TableColumn<ProductModel, String> tws_scientific;
	
	final int[] fSearchPs = new int[] { 100, 200, 300, 500 ,1000};
	private int str_fSearchPs = fSearchPs[0];
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

	public void doubleClick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			//Entertb();
		}
	}
	public void actionSearch(ActionEvent event) throws IOException {
		actionSearch();
	}
	public void actionSearch() throws IOException {
		System.out.println("key ="+txtKeySearch.getText());
		twResultSearch.getItems().clear();
		twResultSearch.refresh();
    	ProductDao productDao = new ProductDao();
    	try {
    		long start = System.nanoTime();    
    		
    		List<ProductModel> lstProduct = productDao.getListProductSearch(txtKeySearch.getText(),str_fSearchPs);
    		long elapsedTime = System.nanoTime() - start;
    		double seconds = (double)elapsedTime / 1000000000.0;
    		System.out.println("selconds: "+seconds);
    		// show result
    		twResultSearch.getItems().addAll(lstProduct);
    		twResultSearch.getSelectionModel().select(0);
    		int count = twResultSearch.getItems().size();
    		twResultSearch.refresh();
			if(count == 0){
				twResultSearch.setPlaceholder(new Label("No matching results were found."));
			}
    		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Entertb(){
	
	}
	 public void setContents(){
		twResultSearch.setPlaceholder(new Label("Please wait… Searching Database.")); 
	 	tws_sku1.setCellValueFactory(new PropertyValueFactory<>("sku"));
		tws_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		tws_scientific.setCellValueFactory(new PropertyValueFactory<>("scientific"));
		tws_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		tws_size.setCellValueFactory(new PropertyValueFactory<>("size"));
/*		twResultSearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_UP){
		    	  txtKeySearch.requestFocus();
		      }else{ 
		    	  if (event.getCode() == KeyCode.ENTER ) {
		    		  Entertb();
				  }
		    	  if (event.getCode() == KeyCode.PAGE_DOWN ) {
		    		 // txtEmail.requestFocus();
		    	  }
		      }
		   }
		}); */
		txtKeySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.DOWN){
		    	  twResultSearch.requestFocus();
		      }else{ 
		    	  if(event.getCode() == KeyCode.TAB){
		    		  
		    	  }else{
		    		  Thread thLoadDatas1 = new Thread() {
		  				@SuppressWarnings("deprecation")
		  				public void run() {
		  					try {
				    			  actionSearch();
		  					} catch (IOException e) {
		  						// TODO Auto-generated catch block
		  						e.printStackTrace();
		  					}
		  				}
		  			};
		  			thLoadDatas1.start();
		    	  }
		      }
		   }
		});
			Thread thLoadDatas = new Thread() {
				@SuppressWarnings("deprecation")
				public void run() {
					try {
						actionSearch();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			thLoadDatas.start();

     }

        public static boolean confirmTranactionPosting(Stage owner, String title) {

           new ConfirmationWindowAccountController(owner, title);
           System.out.println(postStatus);
           return postStatus;
         }

}