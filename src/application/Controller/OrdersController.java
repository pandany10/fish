package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.CustomerDao;
import application.Dao.OrderDao;
import application.Dao.ProductDao;
import application.Model.CustomerModel;
import application.Model.InvoiceModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import application.Utill.Pdf;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class OrdersController implements Initializable {

	Stage prevStage;
	final String[] statusS = new String[] { "Pending", "Packing", "Shipped", "Completed","Pulling" };
	final String[] filter = new String[] { "Pending", "Packing", "Shipped", "Completed" ,"Pulling" };
	String screen = "final";
	
	public boolean stateEdit = false;
	
	public String getScreen() {
		return screen;
	}
	private String cscreen = "lstOrder";
	
	public String getCscreen() {
		return cscreen;
	}

	public void setCscreen(String cscreen) {
		this.cscreen = cscreen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public void setPrevStage(Stage stage) {
		this.prevStage = stage;
	}

	/*
	 * @FXML private TableView<Order> twOrder;
	 */
	@FXML
	private StackPane root;
	
	@FXML
	private TableView<ProductModel> twResultSearch;
	
	@FXML
	private Pane editOrder;

	@FXML
	private Pane orders;

	@FXML
	private TableView<OrderModel> twOrder;

	@FXML
	private TableView<ProductModel> twOrderDetail;
	@FXML
	private TextField txtKeySearch;	
	@FXML
	private TextField bill_cus_id;
	@FXML
	private TextField bill_name;
	@FXML
	private TextField bill_address1;
	@FXML
	private TextField bill_address2;
	@FXML
	private TextField bill_city;
	@FXML
	private TextField bill_state;
	@FXML
	private TextField bill_zip;
	@FXML
	private TextField bill_phone;
	@FXML
	private TextField saleEmail;
	@FXML
	private TextField ship_name;
	@FXML
	private AnchorPane r;
	@FXML
	private TextField ship_address1;
	@FXML
	private TextField ship_address2;
	@FXML
	private TextField ship_city;
	@FXML
	private TextField ship_state;
	@FXML
	private TextField ship_zip;
	@FXML
	private TextField ship_phone;
	@FXML
	private ChoiceBox fSearchP;
	
	@FXML
	private TableColumn<ProductModel, Boolean> tws_sku1;
	@FXML
	private TableColumn<ProductModel, Boolean> tws_name;
	@FXML
	private TableColumn<ProductModel, Boolean> tws_size;
	@FXML
	private TableColumn<ProductModel, Boolean> tws_price;
	
	@FXML
	private TableColumn<ProductModel, String> twd_scientific;
	@FXML
	private TableColumn<ProductModel, String> tws_scientific;
	
	@FXML
	private TextField courier;
	@FXML
	private TextField tracking;
	@FXML
	private TextField fob;
	@FXML
	private TextField terms;
	@FXML
	private TextField ponumber;
	@FXML
	private TextField salsperson;

	@FXML
	private TextField fish_boxes;
	@FXML
	private TextField fish_weight;
	@FXML
	private TextField fish_t_packs;
	@FXML
	private TextField rock_boxes;
	@FXML
	private TextField rock_weight;
	@FXML
	private TextField rock_t_packs;
	@FXML
	private TextField dry_boxes;
	@FXML
	private TextField dry_weight;
	@FXML
	private TextField dry_t_boxes;
	@FXML
	private TextField txtTotalOrder;
	@FXML
	private TextField txtDiscount1;
	@FXML
	private TextField txtShippingCost;
	@FXML
	private TextField Customer_email;
	@FXML
	private TextField txtDiscount;
	
	@FXML
	private TableColumn<ProductModel, String> twd_sku;
	@FXML
	private TableColumn<ProductModel, String> twd_qty;
	@FXML
	private TableColumn<ProductModel, String> twd_size;
	@FXML
	private TableColumn<ProductModel, String> twd_name;
	@FXML
	private TableColumn<ProductModel, String> twd_lot;
	@FXML
	private TableColumn<ProductModel, String> twd_addon;
	@FXML
	private TableColumn<ProductModel, Float> twd_price;
	@FXML
	private TableColumn<ProductModel, String> twd_disk;
	@FXML
	private TableColumn<ProductModel, Float> twd_total;
	@FXML
	private TableColumn<ProductModel, Boolean> twd_commission;
	@FXML
	private MenuItem menuItemOrders;
	@FXML
	private ChoiceBox cbFilter;
	@FXML
	private MenuItem menuItemInvoicer;
	@FXML
	private MenuItem menuItemHome;
	@FXML
	private MenuItem menuItemCustomers;
	@FXML
	private MenuItem menuItemPrintInvoice;
	private CustomerModel customer;
	private Boolean modeEdit = false;
	private Integer editOrderId = 0;
	private Boolean init = true;
	private List<OrderModel> currentOrder;
	private OrderDao orderDao;
	private OrderDetailModel orderDetail;
	private Boolean isShowOrder = true;
	List<OrderModel> list;
	
	final int[] fSearchPs = new int[] { 100, 200, 300, 500 ,1000};
	
	private int str_fSearchPs = fSearchPs[0];
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		isShowOrdes(true);
		orderDao = new OrderDao();
		initViewOrders();
		initViewOrderDetail();
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					list = orderDao.getOrder(filter[0],screen);
					twOrder.getItems().addAll(list);
					twOrder.getSelectionModel().selectFirst();
					this.suspend();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		thLoadData.start();
	}

	public void initViewOrderDetail() {
		fSearchP.setItems(FXCollections.observableArrayList("100", "200", "300", "500" ,"1000"));
		fSearchP.setValue("100");
		fSearchP.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				str_fSearchPs = fSearchPs[new_value.intValue()];
				System.out.println("--"+str_fSearchPs);
				try {
					actionSearch();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		txtKeySearch.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_UP){
		    	  focusSkuTwD();
		      }else{ 
		    	  if(event.getCode() == KeyCode.PAGE_DOWN){
		    		  twResultSearch.requestFocus();
			      }
		      }
		   }
		});  
		
		twd_scientific.setCellValueFactory(new PropertyValueFactory<>("scientific"));
		tws_scientific.setCellValueFactory(new PropertyValueFactory<>("scientific"));
		twResultSearch.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					System.out.println("enter selected.");
					}
				
				if (event.getCode() == KeyCode.PAGE_UP) {
					txtKeySearch.requestFocus();
				}
				if (event.getCode() == KeyCode.PAGE_DOWN) {
					bill_cus_id.requestFocus();
				}
			}
		});
		twResultSearch.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.ENTER) {
					addProduct();
				}

			}
		});
		twd_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		tws_sku1.setCellValueFactory(new PropertyValueFactory<>("sku"));
		tws_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		tws_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		tws_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		TableColumn<ProductModel, Integer> indexCols = new TableColumn<ProductModel, Integer>("#");
		indexCols.setCellFactory(new Callback<TableColumn<ProductModel, Integer>, TableCell<ProductModel, Integer>>() {
			@Override
			public TableCell<ProductModel, Integer> call(TableColumn<ProductModel, Integer> param) {
				return new TableCell<ProductModel, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null) {

							int index = this.getTableRow().getIndex();

							if (index < twResultSearch.getItems().size()) {
								int rowNum = index + 1;
								setText(String.valueOf(rowNum));
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
		indexCols.setPrefWidth(45.0);
		indexCols.getStyleClass().add("clNo");
		twResultSearch.getColumns().add(0, indexCols); // number column is at
		this.bill_name.textProperty().addListener(new TextFieldListener(this.bill_name));
		this.bill_address1.textProperty().addListener(new TextFieldListener(this.bill_address1));
		this.bill_address2.textProperty().addListener(new TextFieldListener(this.bill_address2));
		this.bill_city.textProperty().addListener(new TextFieldListener(this.bill_city));
		this.bill_state.textProperty().addListener(new TextFieldListener(this.bill_state));
		this.bill_zip.textProperty().addListener(new TextFieldListener(this.bill_zip));
		this.bill_phone.textProperty().addListener(new TextFieldListener(this.bill_phone));
		this.terms.textProperty().addListener(new TextFieldListener(this.terms));
		this.salsperson.textProperty().addListener(new TextFieldListener(this.salsperson));
		this.txtShippingCost.textProperty().addListener(new TextFieldListener(this.txtShippingCost));
		orders.setPrefHeight(913);
		this.courier.textProperty().addListener(new TextFieldListenerInfo(this.courier));
		this.tracking.textProperty().addListener(new TextFieldListenerInfo(this.tracking));
		this.fish_boxes.textProperty().addListener(new TextFieldListenerInfo(this.fish_boxes));
		this.fish_weight.textProperty().addListener(new TextFieldListenerInfo(this.fish_weight));
		this.fish_t_packs.textProperty().addListener(new TextFieldListenerInfo(this.fish_t_packs));
		this.fob.textProperty().addListener(new TextFieldListenerInfo(this.fob));
		this.rock_boxes.textProperty().addListener(new TextFieldListenerInfo(this.rock_boxes));
		this.rock_weight.textProperty().addListener(new TextFieldListenerInfo(this.rock_weight));
		this.rock_t_packs.textProperty().addListener(new TextFieldListenerInfo(this.rock_t_packs));
		this.ponumber.textProperty().addListener(new TextFieldListenerInfo(this.ponumber));
		this.dry_boxes.textProperty().addListener(new TextFieldListenerInfo(this.dry_boxes));
		this.dry_weight.textProperty().addListener(new TextFieldListenerInfo(this.dry_weight));
		this.dry_t_boxes.textProperty().addListener(new TextFieldListenerInfo(this.dry_t_boxes));
		this.txtDiscount.textProperty().addListener(new TextFieldListener(this.txtDiscount));
		System.out.println(r.isResizable());
        Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>> booleanCellFactory = 
                new Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>>() {
                @Override
                    public TableCell<ProductModel, Boolean> call(TableColumn<ProductModel, Boolean> p) {
                        return new BooleanCell();
                }
            };
		twd_qty.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	   System.out.println("handler : " + twd_qty.getText());
		    }
		});
		bill_cus_id.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){        	
			    	if (newValue.matches("\\d*")) {
			            int value = Integer.parseInt(newValue);
			        } else {
			        	bill_cus_id.setText(oldValue);
			        }
		        }

		    }
		});

		twd_commission.setCellValueFactory(new PropertyValueFactory<>("commission"));
		twd_commission.setCellFactory(booleanCellFactory);
		bill_cus_id.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			  @Override
			    public void handle(KeyEvent event) {
				  if(event.getCode() == KeyCode.PAGE_UP){
					  twResultSearch.requestFocus();
			      }
			      if(event.getCode() == KeyCode.PAGE_DOWN){
			    	  focusSkuTwD();
			      }
			  }
		});
		bill_cus_id.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      System.out.println("handler : " + bill_cus_id.getText());
		/*      if(event.getCode() == KeyCode.PAGE_DOWN){
		    	  focusSkuTwD();
		      }else{   	*/
		      if(bill_cus_id.getText().length()>0){
		    	  
		  
		      String cusId = bill_cus_id.getText();
		      CustomerDao customerDao = new CustomerDao();
		      try {
				CustomerModel bill = customerDao.getCustomerByCusId(cusId);
		    	CustomerModel ship = bill;
		    	customer = bill;
		    	//bill_cus_id.setText(bill.getCustomerID());
		    	bill_name.setText(bill.getCompanyName());
		    	bill_address1.setText(bill.getAddress());
		    	bill_address2.setText(bill.getAddress2());
		    	bill_city.setText(bill.getCity());
		    	bill_state.setText(bill.getStates());
		    	bill_zip.setText(bill.getZip());
		    	bill_phone.setText(bill.getPhone1());
		    	courier.setText(bill.getCarrier());
		    	terms.setText(bill.getTerms());
		    	salsperson.setText(bill.getSalesperson());
		    	
		    	ship_name.setText(ship.getCompanyName());
		    	ship_address1.setText(ship.getAddress());
		    	ship_address2.setText(ship.getAddress2());
		    	ship_city.setText(ship.getCity());
		    	ship_state.setText(ship.getStates());
		    	ship_zip.setText(ship.getZip());
		    	ship_phone.setText(ship.getPhone1());
		    	txtShippingCost.setText(String.valueOf(bill.getShippingCost()));
		    	txtDiscount.setText(bill.getSalesDisc());
		    	if(bill.getSaleEmail() == null){
			    	saleEmail.setText(bill.getEmail());
		    	}else{
			    	saleEmail.setText(bill.getSaleEmail());
		    	}
		    	Customer_email.setText(bill.getEmail());
		    	txtTotalOrder.setText(String.format ("%.2f", calTotal()));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      }
		    //  }
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
				//isShowOrdes(true);
				try {
					gotoOrders();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		menuItemHome.setAccelerator(KeyCombination.keyCombination("Ctrl+H"));

		menuItemHome.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gotoHome();
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
		menuItemPrintInvoice.setAccelerator(KeyCombination.keyCombination("Ctrl+P"));

		menuItemPrintInvoice.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if(!isShowOrder){
						gotoPrintInvoice();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		twOrderDetail.setEditable(true);
		twd_sku.setCellValueFactory(new PropertyValueFactory<>("sku"));
		//twd_sku.setCellFactory(createNumberCellFactoryd());
		twd_sku.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("sku");
            }
        });
		twd_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		twd_qty.setCellFactory(createNumberCellFactoryd());

		twd_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		// twd_size.setCellFactory(createNumberCellFactoryd());

		twd_name.setCellValueFactory(new PropertyValueFactory<>("name"));

		twd_lot.setCellValueFactory(new PropertyValueFactory<>("lot"));

		twd_addon.setCellValueFactory(new PropertyValueFactory<>("addon"));
		twd_addon.setCellFactory(createNumberCellFactoryd());

		twd_price.setCellValueFactory(new PropertyValueFactory<>("price"));

		twd_disk.setCellValueFactory(new PropertyValueFactory<>("disc"));
		twd_disk.setCellFactory(createNumberCellFactoryd());

		twd_total.setCellValueFactory(new PropertyValueFactory<>("total"));

		twd_sku.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				ProductModel item = event.getRowValue();
				ProductModel items = cloneRowTable(item);
				String news = event.getNewValue();
				item.setSku(news);
				System.out.println(item.getId());
				try {
					ProductDao productDao = new ProductDao();
					ProductModel product = productDao.getProductBySku(news);
					if (product.getSku() != null) {
						product.setId(item.getId());
						twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), product);
						twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
						//orderDao.updateOrderProduct(product);
						//Float discount = Float.parseFloat((txtDiscount.getText()));
						//orderDao.updateOrderTotal(editOrderId,discount);
						
						OrderInfoModel orderInfoModel = getOrderInfoModel();
						InvoiceModel invoice = new InvoiceModel();
						product.setAll_Total(calTotal());
						invoice.setOrderInfo(orderInfoModel);
						invoice.setProduct(product);
						if(product.getTotal() != null){
							Integer status;
							Boolean status1;
							try {
								status = orderDao.addOrder(invoice, editOrderId);
								status1 = orderDao.updateOrderTotals(editOrderId,calTotal());//(editOrderId,calTotal());
								System.out.println(status);
								//twOrderDetail.getSelectionModel().clearSelection();
								//twOrderDetail.getSelectionModel().select(twOrderDetail.getItems().size() - 1, twd_sku);
								twOrderDetail.getSelectionModel().getSelectedItem().setId(status);
								System.out.println(status1);
							} catch (ClassNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), items);
						twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
					}
					// orderDao.updateOrderProduct(editOrderId, item);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		twd_qty.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "qty");
			}
		});
		twd_addon.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "addon");
			}
		});
		twd_disk.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "disc");
			}
		});

		TableColumn<ProductModel, Integer> indexCol = new TableColumn<ProductModel, Integer>("#");

		indexCol.setCellFactory(new Callback<TableColumn<ProductModel, Integer>, TableCell<ProductModel, Integer>>() {
			@Override
			public TableCell<ProductModel, Integer> call(TableColumn<ProductModel, Integer> param) {
				return new TableCell<ProductModel, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null) {

							int index = this.getTableRow().getIndex();

							if (index < twOrderDetail.getItems().size()) {
								int rowNum = index + 1;
								setText(String.valueOf(rowNum));
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
		twOrderDetail.getColumns().add(0, indexCol); // number column is at
														// index 0
		twOrderDetail.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// single cell selection mode
		twOrderDetail.getSelectionModel().setCellSelectionEnabled(true);
		twOrderDetail.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					// event.consume(); // don't consume the event or else the
					// values won't be updated;

					return;
				}

				// switch to edit mode on keypress, but only if we aren't
				// already in edit mode
				if (twOrderDetail.getEditingCell() == null) {
					if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
						TablePosition focusedCellPosition = twOrderDetail.getFocusModel().getFocusedCell();
						twOrderDetail.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

					}
				}
				
				
				if (event.getCode() == KeyCode.TAB) {
					TablePosition focusedCellPosition = twOrderDetail.getFocusModel().getFocusedCell();
					twOrderDetail.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
					
				}
				if (event.getCode() == KeyCode.PAGE_UP) {
					focusCusId();
					
				}
				if (event.getCode() == KeyCode.DOWN) {
					TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();
					if(pos.getColumn()==2){
						if(pos.getRow() == twOrderDetail.getItems().size() - 1){
							addRowd();
						}else{
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_sku);
						}
					}
				}
				if (event.getCode() == KeyCode.PAGE_DOWN) {
					txtKeySearch.requestFocus();
				}
			}
		});

		twOrderDetail.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER ) {
					// move focus & selection
					// we need to clear the current selection first or else the
					// selection would be added to the current selection since
					// we are in multi selection mode
					TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();

					if (pos.getRow() == -1) {
						twOrderDetail.getSelectionModel().select(0);
					}
					// add new row when we are at the last row
					else if (pos.getRow() <= twOrderDetail.getItems().size() - 1) {
						//addRow();
						if(pos.getColumn()==1){
							twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_qty);
							//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
						}
						if(pos.getColumn()==2){
							twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_disk);
							twOrderDetail.getFocusModel().focus(pos.getRow(), twd_disk);
							//addRow();
						}
						if(pos.getColumn()==9){
							twOrderDetail.getSelectionModel().clearSelection();
							if(pos.getRow() == twOrderDetail.getItems().size() - 1){
								addRowd();
							}else{
								twOrderDetail.getSelectionModel().select(pos.getRow()+1, twd_sku);
							}
						}
						if(pos.getColumn()==6){
							twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_disk);
							//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_disk);
						}
					/*	if(pos.getColumn()==8){
							twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_sku);
						}*/
					}
					// select next row, but same column as the current selection
				/*	else if (pos.getRow() < twOrderDetail.getItems().size() - 1) {
						//twOrderDetail.getSelectionModel().clearAndSelect(pos.getRow() + 1, pos.getTableColumn());
						
					}*/

				}
			/*	if ( event.getCode() == KeyCode.TAB ) {
					TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();
					if (pos.getRow() == -1) {
						twOrderDetail.getSelectionModel().select(0);
					}else if(pos.getRow() <= twOrderDetail.getItems().size() - 1){
						
						if(pos.getColumn()==10){
							if(pos.getRow() == twOrderDetail.getItems().size() - 1){
								addRowd();
							}else{
								twOrderDetail.getSelectionModel().select(pos.getRow()+1, twd_sku);
							}
						}else{
							if(pos.getColumn()==1){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_qty);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==2){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_size);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==3){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_name);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==4){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_lot);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==5){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_addon);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==6){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_price);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==7){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_disk);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==8){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_commission);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==9){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_total);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
						}
					}
					// add new row when we are at the last row
					else if (pos.getRow() <= twOrderDetail.getItems().size() - 1) {
					twOrderDetail.getSelectionModel().clearSelection();
					twOrderDetail.getSelectionModel().select(pos.getRow(), twd_qty);
				}
				}*/
				if (event.getCode() == KeyCode.DELETE ) {
					ConfirmationWindow a = new ConfirmationWindow(prevStage, "Confirmation");
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
			        					String s = twOrderDetail.getSelectionModel().getSelectedItem().getSku();
			        					//twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
			        					System.out.println(twOrderDetail.getSelectionModel().getSelectedItem().getId());
			        					try {
			        						orderDao.delete(twOrderDetail.getSelectionModel().getSelectedItem().getId());
			        						twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
			        						orderDao.updateOrderTotals(editOrderId,calTotal());
			        					} catch (ClassNotFoundException | SQLException e) {
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
		});
		twOrderDetail.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				//System.out.println("test"+event.getCode());
				if (event.getCode() == KeyCode.ENTER) {
					// event.consume(); // don't consume the event or else the
					// values won't be updated;

					return;
				}

				// switch to edit mode on keypress, but only if we aren't
				// already in edit mode
				if (twOrderDetail.getEditingCell() == null) {
					if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
						TablePosition focusedCellPosition = twOrderDetail.getFocusModel().getFocusedCell();
						twOrderDetail.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

					}
				}
				if (event.getCode() == KeyCode.TAB) {
					TablePosition focusedCellPosition = twOrderDetail.getFocusModel().getFocusedCell();
					twOrderDetail.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
					
				}
				if (event.getCode() == KeyCode.PAGE_UP) {
					focusCusId();
					
				}
				if (event.getCode() == KeyCode.DOWN) {
					TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();
					if(pos.getColumn()==2){
						if(pos.getRow() == twOrderDetail.getItems().size() - 1){
							addRow();
						}else{
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_sku);
						}
					}
				}

			}
		});
	}
	public void actionSearch(ActionEvent event) throws IOException {
		actionSearch();
	}
	public void actionSearch() throws IOException {
		System.out.println("key ="+txtKeySearch.getText());
    	ProductDao productDao = new ProductDao();
		twResultSearch.getItems().clear();

    	try {
    		long start = System.nanoTime();    
    		
    		List<ProductModel> lstProduct = productDao.getListProductSearch(txtKeySearch.getText(),str_fSearchPs);
    		long elapsedTime = System.nanoTime() - start;
    		double seconds = (double)elapsedTime / 1000000000.0;
    		System.out.println("selconds: "+seconds);
    		// show result
    		twResultSearch.getItems().addAll(lstProduct);
    		twResultSearch.getSelectionModel().select(0);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void focusSkuTwD(KeyEvent event) throws IOException {
		 if(event.getCode() == KeyCode.PAGE_DOWN){
  		  	focusSkuTwD();
	      }
	}
	public void focusSkuTwD() {
		twOrderDetail.requestFocus();
		twOrderDetail.getSelectionModel().clearSelection();
		twOrderDetail.getSelectionModel().select(0, twd_sku);
	}
	public void focusCusId() {
		bill_cus_id.requestFocus();
	}
	public void initViewOrders() {
		
		 Callback<TableColumn<OrderModel, Boolean>, TableCell<OrderModel, Boolean>> booleanCellFactoryPayment = 
         new Callback<TableColumn<OrderModel, Boolean>, TableCell<OrderModel, Boolean>>() {
         @Override
             public TableCell<OrderModel, Boolean> call(TableColumn<OrderModel, Boolean> p) {
                 return new BooleanCells();
         }
     };
     TableColumn<OrderModel, Boolean> tw_payment =  new  TableColumn<OrderModel, Boolean>("Payment");
 		tw_payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
		tw_payment.setCellFactory(booleanCellFactoryPayment);
		tw_payment.getStyleClass().add("clcenter");
		tw_payment.setPrefWidth(70.0);
		twOrder.setEditable(true);
		cbFilter.setItems(FXCollections.observableArrayList("Pending", "Packing", "Shipped", "Completed","Pulling"));
		cbFilter.setValue("Pending");
		cbFilter.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				String str_filters = filter[new_value.intValue()];
				Thread thLoadData = new Thread() {
					@SuppressWarnings("deprecation")
					public void run() {
						//twOrder.getItems().removeAll(list);
						twOrder.getItems().clear();
						try {
							list = orderDao.getOrder(str_filters,screen);
							for (OrderModel element : list) {
							  //System.out.println(element.getStatus());COMPLETED
							}
							twOrder.getItems().addAll(list);
							twOrder.getSelectionModel().selectFirst();
							for(int i =0;i< twOrder.getItems().size();i++){
								//System.out.println(twOrder.getItems().get(i).getStatus());
							}
							this.suspend();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};

				thLoadData.start();
			}
		});
	     Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>> booleanCellFactory = 
                 new Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>>() {
                 @Override
                     public TableCell<ProductModel, Boolean> call(TableColumn<ProductModel, Boolean> p) {
                         return new BooleanCell();
                 }
             };
		twd_commission.setCellValueFactory(new PropertyValueFactory<>("commission"));
		twd_commission.setCellFactory(booleanCellFactory);
		
		TableColumn<OrderModel, Integer> No = new TableColumn<>("No");
		No.setCellValueFactory(new PropertyValueFactory<>("No"));
		No.setPrefWidth(45.0);
		No.getStyleClass().add("clNo");
		TableColumn<OrderModel, String> Customer_date = new TableColumn<>("Submit Date");
		Customer_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		Customer_date.setPrefWidth(115.0);
		Customer_date.getStyleClass().add("clCusDate");
		Customer_date.setOnEditCommit((CellEditEvent<OrderModel, String> t) -> {
			((OrderModel) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCustomer_date(t.getNewValue());
		});
		Customer_date.setCellFactory(createNumberCellFactory());
		TableColumn<OrderModel, Integer> order_id = new TableColumn<>("Order id");
		order_id.setCellValueFactory(new PropertyValueFactory<>("order_id"));
		order_id.setPrefWidth(89.0);
		order_id.getStyleClass().add("clOrderId");
		TableColumn<OrderModel, String> ClientCustomerID = new TableColumn<>("Customer ID");
		ClientCustomerID.setCellValueFactory(new PropertyValueFactory<>("ClientCustomerID"));
		ClientCustomerID.setPrefWidth(90.0);
		ClientCustomerID.getStyleClass().add("clCusId");
		ClientCustomerID.setCellFactory(createNumberCellFactory());
		TableColumn<OrderModel, String> CompanyName = new TableColumn<>("Store");
		CompanyName.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		CompanyName.setPrefWidth(280.0);
		CompanyName.setCellFactory(createNumberCellFactory());
		TableColumn<OrderModel, Float> All_Total = new TableColumn<>("Total($)");
		All_Total.setCellValueFactory(new PropertyValueFactory<>("All_Total"));
		All_Total.setPrefWidth(90.0);
		All_Total.getStyleClass().add("clTotal");
		TableColumn<OrderModel, String> status = new TableColumn<>("Status");
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		status.setPrefWidth(100.0);
		status.getStyleClass().add("clStatus");
		// SETTING THE CELL FACTORY FOR THE RATINGS COLUMN
		status.setCellFactory(new Callback<TableColumn<OrderModel, String>, TableCell<OrderModel, String>>() {
			@Override
			public TableCell<OrderModel, String> call(TableColumn<OrderModel, String> param) {
				TableCell<OrderModel, String> cell = new TableCell<OrderModel, String>() {
					public void updateItem(String item, boolean empty) {
							ChoiceBox choice = new ChoiceBox(
									FXCollections.observableArrayList("Pending", "Packing", "Shipped", "Completed","Pulling"));
							choice.setVisible(false);
							choice.getSelectionModel().select(FXCollections
									.observableArrayList("Pending", "Packing", "Shipped", "Completed","Pulling").indexOf(item));
							choice.setTooltip(new Tooltip("Select the status order"));
							choice.getSelectionModel().selectedIndexProperty()
									.addListener(new ChangeListener<Number>() {
										public void changed(ObservableValue ov, Number value, Number new_value) {
											//System.out.println(statusS[new_value.intValue()]);
										}
									}); 
							// SETTING ALL THE GRAPHICS COMPONENT FOR CELL
							if (item != null && empty != true) {
								choice.setVisible(true);
							}else{
								choice.setVisible(false);

							}
							setGraphic(choice);
					}
				};
				return cell;
			}
		});
		TableColumn<OrderModel, String> Customer_ship = new TableColumn<>("Ship Via");
		Customer_ship.setCellValueFactory(new PropertyValueFactory<>("Customer_ship"));
		Customer_ship.setPrefWidth(143.0);
		Customer_ship.getStyleClass().add("clStatus");
		Customer_ship.setCellFactory(createNumberCellFactory());
		TableColumn<OrderModel, String> Customer_email = new TableColumn<>("Email");
		Customer_email.setCellValueFactory(new PropertyValueFactory<>("Customer_email"));
		Customer_email.setPrefWidth(220.0);
		Customer_email.getStyleClass().add("clEmail");
		Customer_email.setCellFactory(createNumberCellFactory());
		boolean addAll = twOrder.getColumns().addAll(order_id, Customer_date, status,tw_payment, Customer_ship, ClientCustomerID,
				Customer_email, CompanyName, All_Total);
		twOrder.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					 String status = twOrder.getSelectionModel().getSelectedItems().get(0).getStatus();
			         if(!status.equals(statusS[3])){
					// event.consume(); // don't consume the event or else the
					// values won't be updated;
					System.out.println("modeEdit: " + modeEdit);
					if (modeEdit == true) {
						modeEdit = false;
					} else {
						try {
							EditOrder();
							//r.resize(1267, 913);
							cscreen = "orderDetail";
							r.setPrefHeight(913);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return;
			      }
				}

				// switch to edit mode on keypress, but only if we aren't
				// already in edit mode
				if (twOrder.getEditingCell() == null) {
					if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
						modeEdit = true;
						TablePosition focusedCellPosition = twOrder.getFocusModel().getFocusedCell();
						twOrder.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());

					}
				}

			}
		});
		twOrder.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				if (event.getCode() == KeyCode.ENTER) {

					// move focus & selection
					// we need to clear the current selection first or else the
					// selection would be added to the current selection since
					// we are in multi selection mode
					TablePosition pos = twOrder.getFocusModel().getFocusedCell();

					if (pos.getRow() == -1) {
						twOrder.getSelectionModel().select(0);
					}
					// add new row when we are at the last row
					else if (pos.getRow() == twOrder.getItems().size() - 1) {
						// addRow();
					}
					// select next row, but same column as the current selection
					else if (pos.getRow() < twOrder.getItems().size() - 1) {
						twOrder.getSelectionModel().clearAndSelect(pos.getRow() + 1, pos.getTableColumn());
					}

				}
				if (event.getCode() == KeyCode.DELETE ) {
					ConfirmationWindow a = new ConfirmationWindow(prevStage, "Confirmation");
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
			        					Integer orderID = twOrder.getSelectionModel().getSelectedItem().getOrder_id();
			        					//twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
			        					System.out.println(orderID);
			        					try {
			        						orderDao.deleteOneOrder(orderID);
			        						twOrder.getItems().removeAll(twOrder.getSelectionModel().getSelectedItems());
			        					} catch (ClassNotFoundException | SQLException e) {
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
		});

		// single cell selection mode
		twOrder.getSelectionModel().setCellSelectionEnabled(true);
		// add row index column as 1st column
		// -------------------------------------
		TableColumn<OrderModel, Integer> indexCol = new TableColumn<OrderModel, Integer>("#");

		indexCol.setCellFactory(new Callback<TableColumn<OrderModel, Integer>, TableCell<OrderModel, Integer>>() {
			@Override
			public TableCell<OrderModel, Integer> call(TableColumn<OrderModel, Integer> param) {
				return new TableCell<OrderModel, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null) {

							int index = this.getTableRow().getIndex();

							if (index < twOrder.getItems().size()) {
								int rowNum = index + 1;
								setText(String.valueOf(rowNum));
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
		twOrder.getColumns().add(0, indexCol); // number column is at index 0
		// allow multi selection
		twOrder.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		FlowPane buttonBar = new FlowPane();

		// add new row button
		Button addButton = new Button("Add");
		addButton.setOnAction(e -> {
			addRow();
		});
		addButton.setFocusTraversable(false);// don't let it get the focus or
												// else the table would lose it
												// when we click the button and
												// we's have to request the
												// focus on the table in the
												// event handler

		// remove selected rows button
		Button removeButton = new Button("Remove");
		removeButton.setOnAction(e -> {
			removeSelectedRows();
		});
		removeButton.setFocusTraversable(false);// don't let it get the focus or
												// else the table would lose it
												// when we click the button and
												// we's have to request the
												// focus on the table in the
												// event handler

		buttonBar.getChildren().addAll(addButton, removeButton);
		// root.setCenter(twOrder);
		// root.setTop(buttonBar);
	}

	/**
	 * Insert a new default row to the table, select a cell of it and scroll to
	 * it.
	 */
	public void addRow() {

		// get current position
		TablePosition pos = twOrder.getFocusModel().getFocusedCell();

		// clear current selection
		twOrder.getSelectionModel().clearSelection();

		// create new record and add it to the model
		OrderModel order = new OrderModel();
		twOrder.getItems().add(order);

		// get last row
		int row = twOrder.getItems().size() - 1;
		twOrder.getSelectionModel().select(row, pos.getTableColumn());

		// scroll to new row
		twOrder.scrollTo(order);

	}
	public void addRowd() {

		// get current position
		TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();

		// clear current selection
		twOrderDetail.getSelectionModel().clearSelection();

		// create new record and add it to the model
		ProductModel productModel = new ProductModel();
		twOrderDetail.getItems().add(productModel);

		// get last row
		int row = twOrderDetail.getItems().size() - 1;
		twOrderDetail.getSelectionModel().select(row, twd_sku);

		// scroll to new row
		twOrderDetail.scrollTo(productModel);

	}

	/**
	 * Remove all selected rows.
	 */
	public void removeSelectedRows() {

		twOrder.getItems().removeAll(twOrder.getSelectionModel().getSelectedItems());

		// table selects by index, so we have to clear the selection or else
		// items with that index would be selected
		twOrder.getSelectionModel().clearSelection();

	}

	/**
	 * Edit Order
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void EditOrder() throws ClassNotFoundException, SQLException {
		currentOrder = twOrder.getSelectionModel().getSelectedItems();
		Integer in = currentOrder.get(0).getOrder_id();
		System.out.println(in);
		editOrderId = in;
		isShowOrdes(false);
		orderDetail = orderDao.getOrderDetail(editOrderId);
		showDetail(orderDetail);
	}

	public void showDetail(OrderDetailModel orderDetail) {
		CustomerModel bill = orderDetail.getCustomer();
		CustomerModel ship = orderDetail.getShipTo();
		OrderInfoModel orderInfo = orderDetail.getOrderInfo();
		List<ProductModel> lstProduct = orderDetail.getLstProduct();

		bill_cus_id.setText(bill.getCustomerID());
		bill_name.setText(bill.getCompanyName());
		bill_address1.setText(bill.getAddress());
		bill_address2.setText(bill.getAddress2());
		bill_city.setText(bill.getCity());
		bill_state.setText(bill.getStates());
		bill_zip.setText(bill.getZip());
		bill_phone.setText(bill.getPhone1());

		ship_name.setText(ship.getCompanyName());
		ship_address1.setText(ship.getAddress());
		ship_address2.setText(ship.getAddress2());
		ship_city.setText(ship.getCity());
		ship_state.setText(ship.getStates());
		ship_zip.setText(ship.getZip());
		ship_phone.setText(ship.getPhone1());

		courier.setText(orderInfo.getCustomer_ship());
		tracking.setText(orderInfo.getAwb());
		fob.setText(orderInfo.getFob());
		terms.setText(orderInfo.getTerms());
		ponumber.setText(String.valueOf(orderInfo.getPonumber()));
		salsperson.setText(orderInfo.getSalesperson());

		fish_boxes.setText(String.valueOf(orderInfo.getFcb()));
		fish_weight.setText(String.valueOf(orderInfo.getFcb()));
		fish_t_packs.setText(String.valueOf(orderInfo.getTpacks()));

		rock_boxes.setText(String.valueOf(orderInfo.getRockb()));
		rock_weight.setText(String.valueOf(orderInfo.getRockw()));
		rock_t_packs.setText(String.valueOf(orderInfo.getDfb()));

		dry_boxes.setText(String.valueOf(orderInfo.getDfbw()));
		dry_weight.setText(String.valueOf(orderInfo.getDfbw()));
		dry_t_boxes.setText(String.valueOf(orderInfo.getTotalb()));
		txtShippingCost.setText(String.valueOf(orderInfo.getShippingCost()));
		System.out.println(twOrderDetail.getItems().size());
		for (int i = 0; i < twOrderDetail.getItems().size(); i++) {
			twOrderDetail.getItems().remove(i);
		}
		twOrderDetail.getItems().addAll(lstProduct);
		twOrderDetail.getSelectionModel().selectFirst();
		txtTotalOrder.setText(String.format ("%.2f", calTotal()));
		twOrderDetail.refresh();
	}

	/**
	 * Number cell factory which converts strings to numbers and vice versa.
	 * 
	 * @return
	 */
	private Callback<TableColumn<OrderModel, String>, TableCell<OrderModel, String>> createNumberCellFactory() {

		Callback<TableColumn<OrderModel, String>, TableCell<OrderModel, String>> factory = TextFieldTableCell
				.forTableColumn(new StringConverter<String>() {

					@Override
					public String fromString(String string) {
						return string.toString();
					}

					@Override
					public String toString(String object) {
						// TODO Auto-generated method stub
						if (object == null) {
							object = "";
						}
						return object.toString();
					}

				});

		return factory;
	}

	private Callback<TableColumn<ProductModel, String>, TableCell<ProductModel, String>> createNumberCellFactoryd() {

		Callback<TableColumn<ProductModel, String>, TableCell<ProductModel, String>> factory = TextFieldTableCell
				.forTableColumn(new StringConverter<String>() {

					@Override
					public String fromString(String string) {
						return string.toString();
					}

					@Override
					public String toString(String object) {
						// TODO Auto-generated method stub
						if (object == null) {
							object = "";
						}
						return object.toString();
					}

				});

		return factory;
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
	public void gotoHome(ActionEvent event) throws IOException {
		gotoHome();
	}
	public void PrintInvoice(ActionEvent event) throws IOException {
		gotoPrintInvoice();
	}
	public void doubleClick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			addProduct();
		}
	}
	public void addProduct() {
		// move focus & selection
		// we need to clear the current selection first or else the
		// selection would be added to the current selection since

		// we are in multi selection mode
		TablePosition pos = twResultSearch.getFocusModel().getFocusedCell();

		if (pos.getRow() == -1) {
			twResultSearch.getSelectionModel().select(0);
		}
		// add new row when we are at the last row
		/*else if (pos.getRow() == twResultSearch.getItems().size() - 1) {
		}
		// select next row, but same column as the current selection
		else*/
		if (pos.getRow() < twResultSearch.getItems().size()) {
			ProductModel p = twResultSearch.getSelectionModel().getSelectedItem();
			System.out.println(p.getSku());
			if (p.getSku() != null) {
				p.setId(p.getId());
				int count = twOrderDetail.getItems().size();
				for(int i =0;i<count;i++){
					System.out.println(twOrderDetail.getItems().get(i).getSku());
					if (twOrderDetail.getItems().get(i).getSku() != null) {
						
					}else{
						twOrderDetail.getItems().remove(i);
					}
				}
				twOrderDetail.getItems().add(p);
				//twOrderDetail.getItems().addAll(p);
				//twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), p);
				//twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
				txtTotalOrder.setText(String.format ("%.2f", calTotal()));
				// orderDao.updateOrderProduct(product);
				// orderDao.updateOrderTotal(editOrderId);
				OrderInfoModel orderInfoModel = getOrderInfoModel();
				InvoiceModel invoice = new InvoiceModel();
				ProductModel product = new ProductModel();
				product.setAll_Total(calTotal());
				product.setAddon(p.getAddon());
				product.setDisc(p.getDisc());
				product.setLot(p.getLot());
				product.setName(p.getName());
				product.setPrice(p.getPrice());
				product.setQty(p.getQty());
				product.setSize(p.getSize());
				product.setSku(p.getSku());
				product.setTotal(p.getTotal());
				product.setCommission(p.getCommission());
				invoice.setOrderInfo(orderInfoModel);
				invoice.setProduct(product);
				if(product.getTotal() != null){
					Integer status;
					Boolean status1;
					try {
						status = orderDao.addOrder(invoice, editOrderId);
						status1 = orderDao.updateOrderTotals(editOrderId,calTotal());//(editOrderId,calTotal());
						System.out.println(status);
						twOrderDetail.getSelectionModel().clearSelection();
						twOrderDetail.getSelectionModel().select(twOrderDetail.getItems().size() - 1, twd_sku);
						twOrderDetail.getSelectionModel().getSelectedItem().setId(status);
						System.out.println(status1);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				//twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), items);
			//	twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
			}
		}
	}
	public void gotoPrintInvoice() throws IOException {
		Pdf pdf = new Pdf();
		OrderModel currentOrders =  currentOrder.get(0);
		pdf.PrintInvoice(orderDetail ,currentOrders);
		try {
			orderDao.updateOrderStatus(currentOrders.getOrder_id(),"Completed");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void isShowOrdes(Boolean isShowOrdes) {
		isShowOrder = isShowOrdes;
		orders.setVisible(isShowOrdes);
		editOrder.setVisible(!isShowOrdes);
		menuItemPrintInvoice.setVisible(!isShowOrdes);
		if (!init) {
			if (isShowOrdes) {
				prevStage.setTitle("Orders");
			} else {
				prevStage.setTitle("Edit Order id " + editOrderId);
			}
		} else {
			init = false;
		}
	}

	public ProductModel cloneRowTable(ProductModel item) {
		ProductModel items = new ProductModel();
		items.setId(item.getId());
		items.setSku(item.getSku());
		items.setQty(item.getQty());
		items.setSize(item.getSize());
		items.setName(item.getName());
		items.setLot(item.getLot());
		items.setAddon(item.getAddon());
		items.setPrice(item.getPrice());
		items.setDisc(item.getDisc());
		items.setTotal(item.getTotal());
		return items;
	}

	public void gotoInvoice() throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Create Invoice");
		stage.getIcons().add(new Image("file:resources/images/icon.png"));
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Invoice.fxml"));
		Pane myPane = (Pane) myLoader.load();

		InvoiceController controller = (InvoiceController) myLoader.getController();
		controller.setPrevStage(stage);
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		prevStage.close();
		stage.setResizable(false);
		stage.show();
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
     }
	public Float calTotal(){
		Float total = (float) 0;
		int count = twOrderDetail.getItems().size();
		for(int i =0;i<count;i++){
			String sub = twOrderDetail.getItems().get(i).getTotal();
			if(sub != null){
				total = total + Float.parseFloat(sub);
			}
		}
		Float discount = (float) 0;
		Float discount1 = (float) 0;
		discount = Float.parseFloat((txtDiscount.getText()));
		Float dis =  discount/100;
		discount1 = (dis)*total;
		total = (1- dis)*total;
		txtDiscount1.setText(String.format ("%.2f", discount1));
		txtTotalOrder.setText(String.format ("%.2f", total));
		return total;
	}
	public void changeTextfield(TableColumn.CellEditEvent<ProductModel, String> event, String type) {
		ProductModel item = event.getRowValue();
		String news = event.getNewValue();
		if (type.equals("qty")) {
			item.setQty(news);
		}
		if (type.equals("addon")) {
			item.setAddon(news);
		}
		if (type.equals("disc")) {
			item.setDisc(news);
		}
		ProductModel items = new ProductModel();
		Integer qtys = Integer.parseInt(item.getQty());
		Integer lot = Integer.parseInt(item.getLot());
		Integer disc = Integer.parseInt(item.getDisc());
		Integer addon = Integer.parseInt(item.getAddon());

		Float price = Float.parseFloat(item.getPrice());
		Float total = price * (lot * qtys + addon);
		total = total * (100 - disc) / 100;
		item.setTotal(String.format("%.2f", total));
		items = cloneRowTable(item);
		twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), items);
		twOrderDetail.getItems().remove(twOrderDetail.getSelectionModel().getSelectedIndex());
		txtTotalOrder.setText(String.format ("%.2f", calTotal()));
		try {
			orderDao.updateOrderProduct(items);
			Float discount = Float.parseFloat((txtDiscount.getText()));
			orderDao.updateOrderTotal(editOrderId,discount);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Float totaln = (float) 0.00; for(int
		 * i=0;i<twOrderDetail.getItems().size() -1;i++){
		 * totaln+=Float.parseFloat(twOrderDetail.getItems().get(i).getTotal());
		 * } System.out.println(totaln);
		 */

	}
	class BooleanCell extends TableCell<ProductModel, Boolean> {
        private CheckBox checkBox;
        public BooleanCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean> () {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(isEditing())
                        commitEdit(newValue == null ? false : newValue);
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
            checkBox.setDisable(false);
            checkBox.requestFocus();
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                checkBox.setSelected(item);
            }
        }
    }
	class BooleanCells extends TableCell<OrderModel, Boolean> {
        private CheckBox checkBox;
        private Boolean lock = true;
        public BooleanCells() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean> () {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                	Boolean checked = isEditing();
                    if(isEditing()){
                        String status = twOrder.getSelectionModel().getSelectedItems().get(0).getStatus();
                        if(status.equals(statusS[3])){
                            twOrder.getSelectionModel().getSelectedItems().get(0).setPayment(newValue);
                            Integer orderId = twOrder.getSelectionModel().getSelectedItems().get(0).getOrder_id();
                            commitEdit(newValue == null ? false : newValue);
                            try {
         						orderDao.updatePayment(orderId,newValue);
         					} catch (ClassNotFoundException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					} catch (SQLException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
                        }
                    }else{
                    	
                    }
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
            String status = twOrder.getSelectionModel().getSelectedItems().get(0).getStatus();
            if(status.equals(statusS[3])){
            	checkBox.setDisable(false);
                checkBox.requestFocus();
            	System.out.println("1");
            }else{
                checkBox.setDisable(true);
            }
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty())
            {
            	if(item == null){
            		item = true;
            	}
            	checkBox.setSelected(item);

            }
        }
    }
	public void tranferCus(){
		ship_name.setText(bill_name.getText());
		ship_address1.setText(bill_address1.getText());
		ship_address2.setText(bill_address2.getText());
		ship_city.setText(bill_city.getText());
		ship_state.setText(bill_state.getText());
		ship_zip.setText(bill_zip.getText());
		ship_phone.setText(bill_phone.getText());
		customer.setCompanyName(bill_name.getText());
		customer.setAddress(bill_address1.getText());
		customer.setAddress2(bill_address2.getText());
		customer.setCity(bill_city.getText());
		customer.setStates(bill_state.getText());
		customer.setZip(bill_zip.getText());
		customer.setPhone1(bill_phone.getText());
		customer.setTerms(terms.getText());
		customer.setSalesperson(salsperson.getText());
		customer.setShippingCost(Float.parseFloat(txtShippingCost.getText()));
		customer.setSalesDisc(txtDiscount.getText());
		  CustomerDao customerDao = new CustomerDao();
		  try {
			int status = customerDao.updateCustomer(customer);
			txtTotalOrder.setText(String.format ("%.2f", calTotal()));
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class TextFieldListener implements ChangeListener<String> {
		  private final TextField textField ;
		  TextFieldListener(TextField textField) {
		    this.textField = textField ;
			  textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
				    @Override
				    public void handle(KeyEvent event) {
				    	 tranferCus();
				    	 System.out.println("change ...");
				    	 if(textField == txtDiscount){
				    		 try {
					    		Float discount = Float.parseFloat((txtDiscount.getText()));
								orderDao.updateOrderTotal(editOrderId,discount);
							} catch (ClassNotFoundException | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				    	 }
				    }
				  });
		  }

		  @Override
		  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    // do validation on textField
			 // tranferCus();
			  //System.out.println("change ...");

		  }
	}
	public OrderInfoModel getOrderInfoModel() {
		OrderInfoModel orderInfoModel = new OrderInfoModel();
		orderInfoModel.setClientCustomerID(bill_cus_id.getText());
		orderInfoModel.setCustomer_ship(courier.getText());
		orderInfoModel.setAwb(tracking.getText());
		orderInfoModel.setFob(fob.getText());
		orderInfoModel.setTerms(terms.getText());
		orderInfoModel.setPonumber(Integer.parseInt(ponumber.getText()));
		orderInfoModel.setSalesperson(salsperson.getText());
		orderInfoModel.setFcb(Integer.parseInt(fish_boxes.getText()));
		orderInfoModel.setFcbw(Integer.parseInt(fish_weight.getText()));
		orderInfoModel.setTpacks(Integer.parseInt(fish_t_packs.getText()));
		orderInfoModel.setRockb(Integer.parseInt(rock_boxes.getText()));
		orderInfoModel.setRockw(Integer.parseInt(rock_weight.getText()));
		orderInfoModel.setTotalp(Integer.parseInt(rock_t_packs.getText()));
		orderInfoModel.setDfb(Integer.parseInt(dry_boxes.getText()));
		orderInfoModel.setDfbw(Integer.parseInt(dry_weight.getText()));
		orderInfoModel.setTotalb(Integer.parseInt(dry_t_boxes.getText()));
		orderInfoModel.setSaleEmail(saleEmail.getText());
		orderInfoModel.setCustomer_email(Customer_email.getText());
		
		return orderInfoModel;
	}
	public void updateInfo(){
		OrderInfoModel orderInfoModel = getOrderInfoModel();
		try {
			orderDao.updateOrderInfo(orderInfoModel, editOrderId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class TextFieldListenerInfo implements ChangeListener<String> {
		  private final TextField textField ;
		  TextFieldListenerInfo(TextField textField) {
		    this.textField = textField ;
			  textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
				    @Override
				    public void handle(KeyEvent event) {
				    	 updateInfo();
				    	 System.out.println("change info");
				    }
				  });
		  }

		  @Override
		  public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    // do validation on textField
			 // tranferCus();
			  //System.out.println("change ...");

		  }
	}
	
	class EditingCell extends TableCell<ProductModel, String> {

	    private TextField textField;
	    private String id = "";
	    public EditingCell(String ids) {
	    	this.id = ids;
	    }
	    public EditingCell() {
	    	
	    }
	    @Override
	    public void startEdit() {
	        super.startEdit();

	        if (textField == null) {
	            createTextField();
	        }
	        setGraphic(textField);
	        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	        textField.selectAll();
	        textField.requestFocus();
	    }

	    @Override
	    public void cancelEdit() {
	        super.cancelEdit();
	        String string = getItem() == null ?"":getItem();
	        setText(string);
	        setContentDisplay(ContentDisplay.TEXT_ONLY);
	    }

	    @Override
	    public void updateItem(String item, boolean empty) {
	        super.updateItem(item, empty);
	        
	        if (empty) {
	            setText("");
	            setGraphic(textField);
	        } else {
	            if (isEditing()) {
	                if (textField != null) {
	                    textField.setText(getString());
	                }
	                setGraphic(textField);
	                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	            } else {
	                setText(getString());
	                //System.out.println("aa "+getString());
	                setContentDisplay(ContentDisplay.TEXT_ONLY);
	            }
	        }
	    }

	    private void createTextField() {
	        textField = new TextField(getString());
	        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
	        textField.setOnKeyPressed(t -> {
	            if (t.getCode() == KeyCode.ENTER) {
	                commitEdit(textField.getText());
	                stateEdit = isEditing();
	            } else if (t.getCode() == KeyCode.ESCAPE) {
	                cancelEdit();
	                stateEdit = isEditing();
	            }
	        });
	        textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
			    @Override
			    public void handle(KeyEvent event) {
			    	stateEdit = isEditing();
			    	if(id.equals("sku")){
				    	if(event.getCode() != KeyCode.BACK_SPACE){
				    		if(textField.getText().length()==5 && textField.getText().indexOf("-") == -1)
				    			textField.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "-", "-", KeyCode.RIGHT, false, false, false, false));
				    	}
			    	}
			    }
	    	});
	        textField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			       
			    @Override
			    public void handle(KeyEvent event) {
				    	if(event.getCode() == KeyCode.TAB){
				    		if(!textField.getText().equals("")){
					    		commitEdit(textField.getText());
				    		}
				    	}
				    	if(id.equals("sku")){
					    	if(event.getCode() == KeyCode.PAGE_UP){
					    		focusCusId();
					    	}
				    	}
			    }
	    	});

	        textField.textProperty().addListener(new ChangeListener<String>() {
			    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			        if(newValue.length()>0){  
			        	if(id.equals("sku")){
					    	if (newValue.matches("\\d*")) {
					    		//System.out.println(newValue);
					        } else {
				        		//System.out.println(newValue);
					        	if(newValue.indexOf("-") != -1){
					        		
					        	}else if(newValue.indexOf("f") != -1 || newValue.indexOf("F") != -1){
						        	//  showPopup(false);
						              txtKeySearch.requestFocus();
						              textField.setText(oldValue);
					        	}else{
					        		textField.setText(oldValue);
					        	}
					        }
			        	}else{
			        		if (newValue.matches("\\d*")) {
					    		System.out.println(newValue);
					        } else {
				        		System.out.println(newValue);
					        	if(newValue.indexOf(".") != -1 && id.equals("price")){
					        		
					        	}else{
					        		textField.setText(oldValue);
					        	}
					        }
			        	}
			        }

			    }
			});
	    }

	    private String getString() {
	        return getItem() == null ? "" : getItem();
	    }
	}
}


