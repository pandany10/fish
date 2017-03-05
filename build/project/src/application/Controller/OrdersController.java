package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.DBConnection;
import application.Model.Order;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.TablePosition;

public class OrdersController implements Initializable {

    Stage prevStage;
    Connection c ; 
    final String[] statusS = new String[]{"Pending", "Packing", "Shipped", "Complete"};
    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }
    
    
/*    @FXML
 	private TableView<Order> twOrder;*/
    @FXML
   	private BorderPane root;
    
    private TableView<Order> twOrder;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			c = DBConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		twOrder = new TableView<Order>();
		twOrder.setEditable(true);
		TableColumn<Order, Integer> No = new TableColumn<>("No");
		No.setCellValueFactory(new PropertyValueFactory<>("No"));
		No.setPrefWidth(45.0);
		No.getStyleClass().add("clNo");
		TableColumn<Order, String> Customer_date = new TableColumn<>("Submit Date");
		Customer_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		Customer_date.setPrefWidth(115.0);
		Customer_date.getStyleClass().add("clCusDate");
		Customer_date.setOnEditCommit(
		            (CellEditEvent<Order, String> t) -> {
		                ((Order) t.getTableView().getItems().get(
		                        t.getTablePosition().getRow())
		                        ).setCustomer_date(t.getNewValue());
		        });
		Customer_date.setCellFactory(createNumberCellFactory());
		TableColumn<Order, Integer> order_id = new TableColumn<>("Order id");
		order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		order_id.setPrefWidth(89.0);
		order_id.getStyleClass().add("clOrderId");
		TableColumn<Order, String> ClientCustomerID = new TableColumn<>("Customer ID");
		ClientCustomerID.setCellValueFactory(new PropertyValueFactory<>("ClientCustomerID"));
		ClientCustomerID.setPrefWidth(90.0);
		ClientCustomerID.getStyleClass().add("clCusId");
		ClientCustomerID.setCellFactory(createNumberCellFactory());
		TableColumn<Order, String> CompanyName = new TableColumn<>("Store");
		CompanyName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		CompanyName.setPrefWidth(350.0);
		CompanyName.setCellFactory(createNumberCellFactory());
		TableColumn<Order, Float> All_Total = new TableColumn<>("Total($)");
		All_Total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		All_Total.setPrefWidth(90.0);
		All_Total.getStyleClass().add("clTotal");
		TableColumn<Order, String> status = new TableColumn<>("Status");
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		status.setPrefWidth(100.0);
		status.getStyleClass().add("clStatus");
		// SETTING THE CELL FACTORY FOR THE RATINGS COLUMN         
		status.setCellFactory(new Callback<TableColumn<Order,String>,TableCell<Order,String>>(){        
			@Override
			public TableCell<Order, String> call(TableColumn<Order, String> param) {                
				TableCell<Order, String> cell = new TableCell<Order, String>(){
					public void updateItem(String item, boolean empty) {
						if(item!=null){

						   ChoiceBox choice = new ChoiceBox(FXCollections.observableArrayList("Pending", "Packing", "Shipped", "Complete"));                                                      
						   choice.getSelectionModel().select(FXCollections.observableArrayList("Pending", "Packing", "Shipped", "Complete").indexOf(item));
						   choice.setTooltip(new Tooltip("Select the status order"));
						   choice.getSelectionModel().selectedIndexProperty().addListener(new
						            ChangeListener<Number>() {
						                public void changed(ObservableValue ov,
						                    Number value, Number new_value) {
						                        System.out.println(statusS[new_value.intValue()]);
						            }
						        });
						   //SETTING ALL THE GRAPHICS COMPONENT FOR CELL
						   setGraphic(choice);
						} 
					}
				};                           
				return cell;
			}	
		});
		TableColumn<Order, String> Customer_ship = new TableColumn<>("Ship Via");
		Customer_ship.setCellValueFactory(new PropertyValueFactory<>("Customer_ship"));
		Customer_ship.setPrefWidth(143.0);
		Customer_ship.getStyleClass().add("clStatus");
		Customer_ship.setCellFactory(createNumberCellFactory());
		TableColumn<Order, String> Customer_email = new TableColumn<>("Email");
		Customer_email.setCellValueFactory(new PropertyValueFactory<>("Customer_email"));
		Customer_email.setPrefWidth(220.0);
		Customer_email.getStyleClass().add("clEmail");
		Customer_email.setCellFactory(createNumberCellFactory());
		boolean addAll = twOrder.getColumns().addAll( order_id, Customer_date, status, Customer_ship, ClientCustomerID,Customer_email,CompanyName,All_Total);
		twOrder.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {

	                if( event.getCode() == KeyCode.ENTER) {
//	                  event.consume(); // don't consume the event or else the values won't be updated;
	                	System.out.println("1");
	                    return;
	                }

	                // switch to edit mode on keypress, but only if we aren't already in edit mode
	                if( twOrder.getEditingCell() == null) {
	                    if( event.getCode().isLetterKey() || event.getCode().isDigitKey()) {  
	                    	System.out.println("2");

	                        TablePosition focusedCellPosition = twOrder.getFocusModel().getFocusedCell();
	                        twOrder.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

	                    }
	                }

	            }
	    });
		twOrder.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {

	                if( event.getCode() == KeyCode.ENTER) {


	                    // move focus & selection
	                    // we need to clear the current selection first or else the selection would be added to the current selection since we are in multi selection mode 
	                    TablePosition pos = twOrder.getFocusModel().getFocusedCell();

	                    if (pos.getRow() == -1) {
	                    	twOrder.getSelectionModel().select(0);
	                    } 
	                    // add new row when we are at the last row
	                    else if (pos.getRow() == twOrder.getItems().size() -1) {
	                        addRow();
	                    } 
	                    // select next row, but same column as the current selection
	                    else if (pos.getRow() < twOrder.getItems().size() -1) {
	                    	twOrder.getSelectionModel().clearAndSelect( pos.getRow() + 1, pos.getTableColumn());
	                    }


	                }

	            }
	        }); 
	
	       // single cell selection mode
		twOrder.getSelectionModel().setCellSelectionEnabled(true);
        // add row index column as 1st column
        // -------------------------------------
	     TableColumn<Order, Integer> indexCol = new TableColumn<Order, Integer>("#");

	        indexCol.setCellFactory(new Callback<TableColumn<Order, Integer>, TableCell<Order, Integer>>() {
	            @Override public TableCell<Order, Integer> call(TableColumn<Order, Integer> param) {
	                return new TableCell<Order, Integer>() {
	                	 @Override protected void updateItem(Integer item, boolean empty) {
	                        super.updateItem(item, empty);

	                        if (this.getTableRow() != null) {

	                            int index = this.getTableRow().getIndex();

	                            if( index < twOrder.getItems().size()) {
	                                int rowNum = index + 1;
	                                setText( String.valueOf(rowNum));
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
	        indexCol.setPrefWidth(45.0);
	        indexCol.getStyleClass().add("clNo");
	      twOrder.getColumns().add( 0, indexCol); // number column is at index 0
        // allow multi selection
		twOrder.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		 FlowPane buttonBar = new FlowPane();

	        // add new row button
	        Button addButton = new Button( "Add");
	        addButton.setOnAction(e -> {
	            addRow();
	        });
	        addButton.setFocusTraversable(false);// don't let it get the focus or else the table would lose it when we click the button and we's have to request the focus on the table in the event handler

	        // remove selected rows button
	        Button removeButton = new Button( "Remove");
	        removeButton.setOnAction(e -> {
	            removeSelectedRows();
	        });
	        removeButton.setFocusTraversable(false);// don't let it get the focus or else the table would lose it when we click the button and we's have to request the focus on the table in the event handler

	        buttonBar.getChildren().addAll( addButton, removeButton);
		root.setCenter(twOrder);
		//root.setTop(buttonBar);
		Thread thLoadData = new Thread(){
				@SuppressWarnings("deprecation")
				public void run(){
					List<Order> list;
					try {
						list = getPersonList();
						twOrder.getItems().addAll(list);
						twOrder.getSelectionModel().selectFirst();
						this.suspend();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		      }; 
		
		 thLoadData.start(); 
	}
	  /**
     * Insert a new default row to the table, select a cell of it and scroll to it. 
     */
    public void addRow() {

        // get current position
        TablePosition pos = twOrder.getFocusModel().getFocusedCell();

        // clear current selection
        twOrder.getSelectionModel().clearSelection();

        // create new record and add it to the model
        Order order = new Order();
        twOrder.getItems().add( order);

        // get last row
        int row = twOrder.getItems().size() - 1;
        twOrder.getSelectionModel().select( row, pos.getTableColumn());

        // scroll to new row
        twOrder.scrollTo( order);

    }
    /**
     * Remove all selected rows.
     */
    public void removeSelectedRows() {

    	twOrder.getItems().removeAll(twOrder.getSelectionModel().getSelectedItems());

        // table selects by index, so we have to clear the selection or else items with that index would be selected 
    	twOrder.getSelectionModel().clearSelection();


    }  
    /**
     * Number cell factory which converts strings to numbers and vice versa.
     * @return
     */
    private Callback<TableColumn<Order, String>, TableCell<Order, String>>  createNumberCellFactory() {

        Callback<TableColumn<Order, String>, TableCell<Order, String>> factory = TextFieldTableCell.forTableColumn( new StringConverter<String>() {

            @Override
            public String fromString(String string) {
            	return string.toString();
            }

			@Override
			public String toString(String object) {
				// TODO Auto-generated method stub
				if(object == null){
					object = "";
				}
				  return object.toString();
			}

        });

        return factory;
    }
    
    
	 public List<Order> getPersonList() throws SQLException {
	        try (
	        	ResultSet rs = c.createStatement().executeQuery("SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID GROUP BY t1.order_id order by t1.order_id desc limit 100"); 
	        ){
	            List<Order> orderList = new ArrayList<>();
	            Integer i = 1;
	            while (rs.next()) {
	                String Customer_date = rs.getString("Customer_date");
	                String status = rs.getString("status");
	                String Customer_ship = rs.getString("Customer_ship");
	                Integer order_id = rs.getInt("order_id");
	                String ClientCustomerID = rs.getString("ClientCustomerID");
	                String Customer_email =  rs.getString("Customer_email");
	                String CompanyName =  rs.getString("CompanyName");
	                Float All_Total = rs.getFloat("All_Total");
	                Order order = new Order(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Total);
	                orderList.add(order);
	                i++;
	            }
	            return orderList;
	        } 
	}
    public void gotoHome(ActionEvent event) throws IOException {
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
     }
}