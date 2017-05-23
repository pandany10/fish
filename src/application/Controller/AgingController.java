package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import application.Utill.Menu;
import application.Utill.PdfCustomer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class AgingController extends Menu implements Initializable {

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
	private TableColumn<OrderModel, String> twi_total;
	@FXML
	private TableColumn<OrderModel, String> twi_paid;
	@FXML
	private TableColumn<OrderModel, String> twi_unpaid;
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private OrderDao orderDao = new OrderDao();
	@FXML
	public Label lblcus;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		inits();
		// begin
		twCus.setPlaceholder(new Label("Please wait… Searching Database."));
		twInvoice.setPlaceholder(new Label("Please wait… Searching Database."));
		twCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	  if (event.getCode() == KeyCode.ENTER) {		
		    			TablePosition pos = twCus.getFocusModel().getFocusedCell();
		    			if (pos.getRow() == -1) {
		    				twCus.getSelectionModel().select(0);
		    			}
		    			if (pos.getRow() < twCus.getItems().size()) {
		    				OrderModel c = twCus.getSelectionModel().getSelectedItem();
		    				if(c != null){
		    				if(c.getCustomerId() != null){
			    				getOrderByCus(c.getCustomerId());		    					
		    				}
		    				}
		    			}
					}
		      }
		});   
		tws_cus.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		tws_cus_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
		tws_term.setCellValueFactory(new PropertyValueFactory<>("customerTerms")); //
		tws_slsmn.setCellValueFactory(new PropertyValueFactory<>("customerSalesperson"));//
		tws_phone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
		tws_contact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
		
		twi_invoice.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		twi_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		twi_total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		twi_paid.setCellValueFactory(new PropertyValueFactory<>("amoutPaid"));
		twi_unpaid.setCellValueFactory(new PropertyValueFactory<>("amoutUnPaid"));
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
	    });;
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
					Date date = new Date();
					String tddate = dateFormat.format(date);							
					List<OrderModel> lstOrderCus;
					try {
						lstOrderCus = orderDao.getOrderCustomerSale();
							Platform.runLater(new Runnable() {
								  @Override
								  public void run() {
									  	twInvoice.refresh();
										if(lstOrderCus.size() == 0){
											twCus.setPlaceholder(new Label("No found."));
											twInvoice.setPlaceholder(new Label("No found."));
										}else{
											twCus.getItems().clear();
											twCus.getItems().addAll(lstOrderCus);
											twCus.getSelectionModel().select(0);
											getOrderByCus(lstOrderCus.get(0).getCustomerId());
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
	public void explanCustomer(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			OrderModel c = twCus.getSelectionModel().getSelectedItem();
			if(c != null){
			if(c.getCustomerId() != null){
				getOrderByCus(c.getCustomerId());		    					
			}
			}
		}
	}	 
	public void getOrderByCus(String cusID){
		twInvoice.getItems().clear();
		lblcus.setText("Invoice for CustomerID: "+cusID);
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