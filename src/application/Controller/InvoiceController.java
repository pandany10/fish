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
import application.Model.OrderInfoModel;
import application.Model.ProductModel;
import application.Utill.Menu;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class InvoiceController  extends Menu implements Initializable {
	@FXML
	public TableView<ProductModel> twOrderDetail;
	@FXML
	private TableView<ProductModel> twResultSearch;
	@FXML
	public TableView<CustomerModel> twSearchCus;

	@FXML
	private MenuItem menuItemsave;

	@FXML
	private MenuItem newLine;
	
	@FXML
	public TextField bill_cus_id;
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
	private TextField ship_name;
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
	public TextField txtKeySearch;
	
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
	private TextField saleEmail;
	@FXML
	private TextField Customer_email;
	@FXML
	private TextField txtTotalOrder;
	@FXML
	private TextField txtDiscount1;
	@FXML
	private TextField txtShippingCost;
	@FXML
	private TextField txtDiscount;
	@FXML
	public TextField txtKeySearchCus;
	@FXML
	public CheckBox creditmemo;
	
	@FXML
	public TableColumn<ProductModel, String> twd_sku1;
	
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
	private TableColumn<ProductModel, String> twd_price;
	@FXML
	private TableColumn<ProductModel, String> twd_disk;
	@FXML
	private TableColumn<ProductModel, Float> twd_total;
	@FXML
	private TableColumn<ProductModel, Boolean> twd_commission;
	
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
	private TableColumn<ProductModel, Boolean> tcs_cus;
	@FXML
	private TableColumn<ProductModel, Boolean> tcs_name;
	@FXML
	private TableColumn<ProductModel, Boolean> tcs_contact;
	@FXML
	private TableColumn<ProductModel, Boolean> tcs_phone;
	@FXML
	private TableColumn<ProductModel, Boolean> tcs_email;
	@FXML
	private TableColumn<ProductModel, Boolean> tcs_city;
	
	private OrderDao orderDao;
	private Boolean isSave = false;
	private Integer order_id;

	@FXML
	private MenuItem menuItemHome;
	@FXML
	private MenuItem menuItemSearch;
	@FXML
	private MenuItem menuItemSearchCus;
	@FXML
	private ChoiceBox cbFilterCus;
	@FXML
	private ChoiceBox fSearchP;
	@FXML
	private CheckBox chkpm;
	@FXML
	private CheckBox chkIs;
	
	@FXML
	private Pane searchProduct;
	@FXML
	private Pane editOrder;	
	@FXML
	private Pane paneSearchCus;	
	@FXML
	private Label statusAddnewCus;	
	
	public boolean stateEdit = false;
	
	public CustomerModel customer;
	
	public boolean isEditSku = false;
	public void setTxtKeySearch() {
		txtKeySearch.setText("");
	}
	final String[] filterCus = new String[] { "ID","Name", "Contact", "Phone", "Email" ,"City" };
	final int[] fSearchPs = new int[] { 100, 200, 300, 500 ,1000};
	private String str_filters = filterCus[0];
	private int str_fSearchPs = fSearchPs[0];

	public void isShowSearchCus(boolean isShow){
		//if(isShow == true){
			paneSearchCus.setVisible(isShow);
			twSearchCus.setVisible(isShow);
		//}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inits();
		isShowSearchCus(false);
		bill_cus_id.requestFocus();
		cbFilterCus.setItems(FXCollections.observableArrayList("Customer ID","Customer Name", "Contact", "Phone", "Email" ,"City"));
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
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_UP){
		    	  txtKeySearchCus.requestFocus();
		      }else{ 
		    	  if (event.getCode() == KeyCode.ENTER) {
						
						explanCustomer();

					}
		      }
		   }
		});    
		txtKeySearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.DOWN){
		    	  twSearchCus.requestFocus();
		      }else if(event.getCode() == KeyCode.PAGE_DOWN){
		    		  focusSkuTwD();
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
		txtKeySearchCus.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	isShowSearchCus(true);
		        }
		        else
		        {
		        	isShowSearchCus(false);
		        }
		    }
		});
		twSearchCus.focusedProperty().addListener(new ChangeListener<Boolean>()
		{
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
		    {
		        if (newPropertyValue)
		        {
		        	isShowSearchCus(true);
		        }
		        else
		        {
		        	isShowSearchCus(false);
		        }
		    }
		});
		menuItemSearch.setVisible(false);
		menuItemSearchCus.setVisible(false);
		showPopup(true);
		// TODO Auto-generated method stub
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
		this.txtDiscount.textProperty().addListener(new TextFieldListener(this.txtDiscount));
		orderDao = new OrderDao();
		newLine.setVisible(false);
		initViewOrderDetail();
		newLine.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		 
		newLine.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
        	   addRow();
           }
       });
		menuItemsave.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
		 
		menuItemsave.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
              System.out.println("Save...");
              twOrderDetail.requestFocus();
              twOrderDetail.getSelectionModel().clearSelection();
  	  		  twSearchCus.getSelectionModel().select(0);
  	  		twOrderDetail.getSelectionModel().select(0, twd_sku1);
              // function save order //get order_id
              // function update list product
             try {
				SaveOrder();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
       });
		menuItemSearch.setAccelerator(KeyCombination.keyCombination("Ctrl+T"));
		 
		menuItemSearch.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
              System.out.println("search...");
              showPopup(false);
              txtKeySearch.requestFocus();
              txtKeySearch.setText("");
             
           }
       });
		menuItemSearchCus.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
		 
		menuItemSearchCus.setOnAction(new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
        	   txtKeySearchCus.requestFocus();
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

		txtKeySearch.textProperty().addListener(new ChangeListener<String>() {
		    @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if(newValue.length()>0){        	
			    	
		        }

		    }
		});
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
		    /*  if(event.getCode() == KeyCode.PAGE_DOWN){
		    	  focusSkuTwD();
		      }else{   */	
		      System.out.println("handler : " + bill_cus_id.getText());
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
		    	bill_phone.setText(bill.getPhone1()); // 1
		    	
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
			    	System.out.println("null  ..");
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
		   //}
			   if(event.getCode() == KeyCode.ENTER){
				   //txtKeySearchCus.requestFocus();
			   }
		    }
		});
		txtKeySearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	//  bill_name.requestFocus();
		      }
		    }
		});
		bill_name.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  bill_address1.requestFocus();
		      }
		    }
		});
		bill_address1.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  bill_address2.requestFocus();
		      }
		    }
		});
		bill_address2.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  bill_city.requestFocus();
		      }
		    }
		});
		bill_city.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  bill_state.requestFocus();
		      }
		    }
		});
		bill_state.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  bill_zip.requestFocus();
		      }
		    }
		});
		bill_zip.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  bill_phone.requestFocus();
		      }
		    }
		});
		bill_phone.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  ship_name.requestFocus();
		      }
		    }
		});
		ship_name.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  ship_address1.requestFocus();
		      }
		    }
		});
		ship_address1.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  ship_address2.requestFocus();
		      }
		    }
		});
		ship_address2.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  ship_city.requestFocus();
		      }
		    }
		});
		ship_city.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  ship_state.requestFocus();
		      }
		    }
		});
		ship_state.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  ship_zip.requestFocus();
		      }
		    }
		});
		ship_zip.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  ship_phone.requestFocus();
		      }
		    }
		});
		ship_phone.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.ENTER){
		    	  focusSkuTwD();
		      }
		    }
		});
		 Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		    		txtKeySearchCus.requestFocus();
		        }
		    });
	}
	public void showPopup(Boolean isShow){
		editOrder.setVisible(true);
		searchProduct.setVisible(true);
	}
	public Float calTotal(){
		Float total = (float) 0;
		int count = twOrderDetail.getItems().size();
		for(int i =0;i<count;i++){
			String sub = twOrderDetail.getItems().get(i).getTotal();
			if(sub != null && !sub.isEmpty()){
				total = total + Float.parseFloat(sub);
			}
		}
		Float discount = (float) 0;
		Float tdiscount = (float) 0;
		discount = Float.parseFloat((txtDiscount.getText()));
		Float dis =  discount/100;
		tdiscount = (dis)*total;
		total = (1- dis)*total;
		txtDiscount1.setText(String.format ("%.2f",tdiscount));
		return total;
	}
	public void initViewOrderDetail() {
	    Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>> cellFactory =
	            new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
	                public TableCell call(TableColumn p) {
	                    return new EditingCell();
	                }
	            };
	    
	            Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>> booleanCellFactory = 
	                    new Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>>() {
	                    @Override
	                        public TableCell<ProductModel, Boolean> call(TableColumn<ProductModel, Boolean> p) {
	                            return new BooleanCell();
	                    }
	                };
	    
		tcs_cus.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
		tcs_name.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		tcs_contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
		tcs_phone.setCellValueFactory(new PropertyValueFactory<>("Phone1"));
		tcs_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tcs_city.setCellValueFactory(new PropertyValueFactory<>("City"));
		
		twOrderDetail.setEditable(true);
		List<ProductModel> lst = new ArrayList<>();
		ProductModel pro = new ProductModel();
		for(int i =0;i<10;i++){
			lst.add(pro);
		}
		twOrderDetail.getItems().addAll(lst);
		twd_commission.setCellValueFactory(new PropertyValueFactory<>("commission"));
		twd_commission.setCellFactory(booleanCellFactory);
		twd_sku1.setCellValueFactory(new PropertyValueFactory<>("sku"));
		//twd_sku.setCellFactory(createNumberCellFactoryd());
		twd_sku1.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("sku");
            }
        });
	
		twd_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
	//	twd_qty.setCellFactory(createNumberCellFactoryd());
		twd_qty.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("qty");
            }
        });
		twd_qty.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		    @Override
		    public void handle(KeyEvent e)
		    {
		       System.out.println("1233");
		    }
		}); 
		
		twd_scientific.setCellValueFactory(new PropertyValueFactory<>("scientific"));
		tws_scientific.setCellValueFactory(new PropertyValueFactory<>("scientific"));
		
		twd_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		tws_sku1.setCellValueFactory(new PropertyValueFactory<>("sku"));
		tws_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		tws_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		tws_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		// twd_size.setCellFactory(createNumberCellFactoryd());		
		twd_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		twd_lot.setCellValueFactory(new PropertyValueFactory<>("lot"));

		twd_addon.setCellValueFactory(new PropertyValueFactory<>("addon"));
		twd_addon.setCellFactory(createNumberCellFactoryd());

		twd_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		//twd_price.setCellFactory(createNumberCellFactoryd());
		twd_price.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("price");
            }
        });
		
		twd_disk.setCellValueFactory(new PropertyValueFactory<>("disc"));
		twd_disk.setCellFactory(createNumberCellFactoryd());

		twd_total.setCellValueFactory(new PropertyValueFactory<>("total"));

		twd_sku1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				/*try {
             	Stage stage = new Stage();
                stage.setTitle("Confirmation");
                stage.getIcons().add(new Image("file:resources/images/icon.png"));
                FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ConfirmationSearchSku.fxml"));
                Pane myPane;
				myPane = (Pane)myLoader.load();
				ConfirmationSearchSkuController controller = (ConfirmationSearchSkuController) myLoader.getController();
         	 //   controller.setPrevStage(stage);
                Scene scene = new Scene(myPane);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality( Modality.APPLICATION_MODAL );
                stage.initOwner( prevStage );
                stage.initStyle( StageStyle.UTILITY );
                controller.ConfirmationWindowAccounts(prevStage, stage);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                     @Override
                     public void handle(WindowEvent t) {
                    	 controller.chkClose = true;
                     }
                 }); 
                stage.setOnHiding(new EventHandler<WindowEvent>() {

                    @Override
                    public void handle(WindowEvent event) {
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                System.out.println("Application Closed by click to Close Button(X)");
                                System.out.println(controller.postStatus);
                                System.out.println(event.getEventType());
                                if( controller.chkClose == false){
        	                        if(controller.postStatus == true){
        	                        	
        	                        }else{
        	                        	
        	                        }
                                }
                            }
                        });
                    }
                });
                stage.show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
				//System.out.println("double:"+event.getNewValue());
				//ProductModel item = event.getRowValue();
			//	System.out.println(item.getSku());

				ProductModel item = event.getRowValue();
				ProductModel items = cloneRowTable(item);
				String news = event.getNewValue();
				item.setSku(news);
				if(!news.equals("")){
				try {
					ProductDao productDao = new ProductDao();
					ProductModel product = productDao.getProductBySkuInvoice(news,creditmemo.isSelected());
					if (product.getSku() != null) {
					System.out.println("iscreditmemo " +creditmemo.isSelected());	
						product.setId(item.getId());
						twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), product);
						twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
						txtTotalOrder.setText(String.format ("%.2f", calTotal()));
						// orderDao.updateOrderProduct(product);
						// orderDao.updateOrderTotal(editOrderId);
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
		twd_price.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "price");
			}
		});

		TableColumn<ProductModel, Integer> indexCol = new TableColumn<ProductModel, Integer>("#");
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

		indexCol.setCellFactory(new Callback<TableColumn<ProductModel, Integer>, TableCell<ProductModel, Integer>>() {
			@Override
			public TableCell<ProductModel, Integer> call(TableColumn<ProductModel, Integer> param) {
				return new TableCell<ProductModel, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null) {

							int index = this.getTableRow().getIndex();

							if (index < twOrderDetail.getItems().size() || index <30) {
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
				if (event.getCode() == KeyCode.PAGE_DOWN) {
					txtKeySearch.requestFocus();
					
				}
				if (event.getCode() == KeyCode.DOWN) {
					TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();
					if(pos.getColumn()==2){
						if(pos.getRow() == twOrderDetail.getItems().size() - 1){
							addRow();
						}else{
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_sku1);
						}
					}
				}

			}
		});
		twOrderDetail.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
	
				if ( event.getCode() == KeyCode.P ) {
					try {
		             	Stage stage = new Stage();
		                stage.setTitle("Search Products");
		                stage.getIcons().add(new Image("file:resources/images/icon.png"));
		                FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ConfirmationSearchSku.fxml"));
		                Pane myPane;
						myPane = (Pane)myLoader.load();
						ConfirmationSearchSkuController controller = (ConfirmationSearchSkuController) myLoader.getController();
		         	 //   controller.setPrevStage(stage);
		                Scene scene = new Scene(myPane);
		                stage.setScene(scene);
		                stage.setResizable(false);
		                stage.initModality( Modality.APPLICATION_MODAL );
		                stage.initOwner( prevStage );
		                stage.initStyle( StageStyle.UTILITY );
		                controller.ConfirmationWindowAccounts(prevStage, stage);
		                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		                     @Override
		                     public void handle(WindowEvent t) {
		                    	 controller.chkClose = true;
		                     }
		                 }); 
		                controller.twResultSearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		        		    @Override
		        		    public void handle(KeyEvent event) {
		        		      if(event.getCode() == KeyCode.PAGE_UP){
		        		    	  txtKeySearch.requestFocus();
		        		      }else{ 
		        		    	  if (event.getCode() == KeyCode.ENTER ) {
		        		    		  Entertb(controller);
		        				  }
		        		    	  if (event.getCode() == KeyCode.PAGE_DOWN ) {
		        		    		 // txtEmail.requestFocus();
		        		    	  }
		        		      }
		        		   }
		        		}); 
		                stage.setOnHiding(new EventHandler<WindowEvent>() {

		                    @Override
		                    public void handle(WindowEvent event) {
		                        Platform.runLater(new Runnable() {

		                            @Override
		                            public void run() {
		                                System.out.println("Application Closed by click to Close Button(X)");
		                                System.out.println(controller.postStatus);
		                                System.out.println(event.getEventType());
		                                if( controller.chkClose == false){
		        	                        if(controller.postStatus == true){
		        	                        	
		        	                        }else{
		        	                        	
		        	                        }
		                                }
		                            }
		                        });
		                    }
		                });
		                stage.show();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if ( event.getCode() == KeyCode.TAB ||  event.getCode() == KeyCode.ENTER ) {
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
							/*twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_qty);*/
							//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							if(pos.getRow() == twOrderDetail.getItems().size() - 1){
								addRow();
							}else{
								System.out.println("123");
								twOrderDetail.getSelectionModel().select(pos.getRow()+1, twd_sku1);
							}
			
						}
						if(pos.getColumn()==2){
							twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_disk);
							twOrderDetail.getFocusModel().focus(pos.getRow(), twd_disk);
							
							//addRow();
						}
						if(pos.getColumn()==7){
							twOrderDetail.getSelectionModel().clearSelection();
							if(pos.getRow() == twOrderDetail.getItems().size() - 1){
								addRow();
							}else{
								System.out.println("123");
								twOrderDetail.getSelectionModel().select(pos.getRow()+1, twd_sku1);
							}
						}
					/*	if(pos.getColumn()==6){
							twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_disk);
							//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_disk);
						}*/
						if(pos.getColumn()==8){
							twOrderDetail.getSelectionModel().clearSelection();
							twOrderDetail.getSelectionModel().select(pos.getRow(), twd_sku1);
						}
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
						
						if(pos.getColumn()==8){
							if(pos.getRow() == twOrderDetail.getItems().size() - 1){
								addRow();
							}else{
								twOrderDetail.getSelectionModel().select(pos.getRow()+1, twd_sku1);
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
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_price);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==5){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_disk);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==6){
								twOrderDetail.getSelectionModel().clearSelection();
								twOrderDetail.getSelectionModel().select(pos.getRow(), twd_commission);
								//twOrderDetail.getFocusModel().focus(pos.getRow(), twd_qty);
							}
							if(pos.getColumn()==7){
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
			        					twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
			        					calTotal();
			                        }
			                    }
			                });
			            }
			        }); 
				}

			}
		});

	}
	public void Entertb(ConfirmationSearchSkuController controller){
		TablePosition pos = controller.twResultSearch.getFocusModel().getFocusedCell();
		TablePosition pos1 = twOrderDetail.getFocusModel().getFocusedCell();
		System.out.println(pos1.getRow());
		if (pos.getRow() == -1) {
			controller.twResultSearch.getSelectionModel().select(0);
		}
		if (pos.getRow() < controller.twResultSearch.getItems().size()) {
			ProductModel p = controller.twResultSearch.getSelectionModel().getSelectedItem();
			System.out.println(p.getSku());
			if (p.getSku() != null) {
				p.setId(p.getId());
				showPopup(true );
				int count = twOrderDetail.getItems().size();
				List<ProductModel> lst = new ArrayList<>();
				lst.add(p);
				ProductModel pro = new ProductModel();
				for(int i =0;i<count;i++){
					String sub = twOrderDetail.getItems().get(i).getTotal();
					if (sub != null && !sub.isEmpty() ) {
						ProductModel pro1 = twOrderDetail.getItems().get(i);
						lst.add(pro1);
					}else{
						lst.add(pro);
					}
				}
				//twOrderDetail.getItems().clear();
				//twOrderDetail.getItems().addAll(lst);
				twOrderDetail.getItems().remove(pos1.getRow());
				twOrderDetail.getItems().add(pos1.getRow(), p);
				txtTotalOrder.setText(String.format ("%.2f", calTotal()));
				twOrderDetail.getSelectionModel().clearSelection();
				twOrderDetail.getSelectionModel().select((lst.size()-1), twd_sku1);
				controller.postStatus =false;
				controller.stage.close();
				twOrderDetail.getSelectionModel().clearSelection();
				if(pos1.getRow() == twOrderDetail.getItems().size() - 1){
					addRow();
				}else{
					System.out.println("123");
					twOrderDetail.getSelectionModel().select(pos1.getRow()+1, twd_sku1);
				}
			} else {
				
			}
		}
		isEditSku = false;
		
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
	
	private Callback<TableColumn<ProductModel, String>, TableCell<ProductModel, String>> createNumberCellFactorydfix() {

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
				showPopup(true );
				int count = twOrderDetail.getItems().size();
				List<ProductModel> lst = new ArrayList<>();
				lst.add(p);
				ProductModel pro = new ProductModel();
				for(int i =0;i<count;i++){
					//System.out.println(twOrderDetail.getItems().get(i).getSku());
					String sub = twOrderDetail.getItems().get(i).getTotal();
					if (sub != null && !sub.isEmpty() ) {
						ProductModel pro1 = twOrderDetail.getItems().get(i);
						lst.add(pro1);
					}else{
						lst.add(pro);
						//twOrderDetail.getItems().remove(i);
						//count = twOrderDetail.getItems().size();
					//	System.out.println(count);
					}
				}
				twOrderDetail.getItems().clear();
				twOrderDetail.getItems().addAll(lst);
				//twOrderDetail.getItems().add(p);
				//twOrderDetail.getItems().addAll(p);
				//twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), p);
				//twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
				txtTotalOrder.setText(String.format ("%.2f", calTotal()));
				// orderDao.updateOrderProduct(product);
				// orderDao.updateOrderTotal(editOrderId);
			} else {
				//twOrderDetail.getItems().add(twOrderDetail.getSelectionModel().getSelectedIndex(), items);
			//	twOrderDetail.getItems().removeAll(twOrderDetail.getSelectionModel().getSelectedItems());
			}
		}
		
	}
	public void focusSkuTwD() {
		twOrderDetail.requestFocus();
		twOrderDetail.getSelectionModel().clearSelection();
		twOrderDetail.getSelectionModel().select(0, twd_sku1);
	}
	public void focusCusId() {
		bill_cus_id.requestFocus();
	}
	public void addCustomer(ActionEvent event) throws IOException {
		addCustomer();
	}
	public void addCustomers(KeyEvent event) throws IOException {
		if(event.getCode() == KeyCode.ENTER){
			addCustomer();
		}
	}
	public void doubleClick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			addProduct();
		}
	}
	public void gotoHome(ActionEvent event) throws IOException {
		gotoHome();
	}
	public void explanCustomer(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
			explanCustomer();
		}
	}
	public void addCustomer() throws IOException { 
		// validate field
		boolean vali = validateAddCus();
		// check exits customer id
		if(vali == true){
			String customerId = bill_cus_id.getText();
			boolean chkCus = chkCustomer(customerId);
			if(chkCus == false){
				// add to db
				int status  = addDoCustomer();
				if(status == 1){
					String msg = "add new customer success.";
					bill_cus_id.requestFocus();
					statusAddnewCus.setText(msg);
				}else{
					String msg = "add new customer fail.";
					bill_cus_id.requestFocus();
					statusAddnewCus.setText(msg);
				}
			}
		}
	}
	public void explanCustomer(){
		// move focus & selection
		// we need to clear the current selection first or else the
		// selection would be added to the current selection since

		// we are in multi selection mode
		TablePosition pos = twSearchCus.getFocusModel().getFocusedCell();

		if (pos.getRow() == -1) {
			twSearchCus.getSelectionModel().select(0);
		}
		// add new row when we are at the last row
		/*else if (pos.getRow() == twResultSearch.getItems().size() - 1) {
		}
		// select next row, but same column as the current selection
		else*/
		if (pos.getRow() < twSearchCus.getItems().size()) {
			CustomerModel c = twSearchCus.getSelectionModel().getSelectedItem();
			System.out.println(c.getCustomerID());
			//System.out.println(c.getAddress());
			if (c.getCustomerID() != null) {
				bill_cus_id.setText(c.getCustomerID());
				CustomerModel bill = c;
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
			    	System.out.println("null  ..");
		    	}else{
			    	saleEmail.setText(bill.getSaleEmail());
		    	}
		    	Customer_email.setText(bill.getEmail());
		    	txtTotalOrder.setText(String.format ("%.2f", calTotal()));
		    	bill_cus_id.requestFocus();
			} else {
				customer = new CustomerModel();
			}
		}
		
	}
	public int addDoCustomer(){
		int status = 0;
		CustomerDao customerDao = new CustomerDao();
		CustomerModel customerModel = new CustomerModel();
		customerModel.setCustomerID(bill_cus_id.getText());
		customerModel.setCompanyName(bill_name.getText());
		customerModel.setAddress(bill_address1.getText());
		customerModel.setAddress2(bill_address2.getText());
		customerModel.setCity(bill_city.getText());
		customerModel.setStates(bill_state.getText());
		customerModel.setZip(bill_zip.getText());
		customerModel.setPhone1(bill_phone.getText());
		try {
			status = customerDao.addCustomer(customerModel);
			System.out.println("status Add customer : "+status);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	public boolean chkCustomer(String customerId){
		CustomerDao customerDao = new CustomerDao();
		boolean exits = false;
		try {
			exits = customerDao.chkCustome(customerId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(exits == true){
			String msg = "";
			msg = "Customer Id already exists.";
			bill_cus_id.requestFocus();
			statusAddnewCus.setText(msg);
		}
		return exits;
	}
	public boolean validateAddCus(){
		boolean vali = true;
		String msg = "";
		if(bill_cus_id.getText().equals("")){
			vali = false;
			msg = "Customer Id required.";
			bill_cus_id.requestFocus();
		}
		else if(bill_name.getText().equals("")){
			msg = "Name Id required.";
			vali = false;
			bill_name.requestFocus();
		}
		else if(bill_address1.getText().equals("")){
			msg = "Address 1 Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_city.getText().equals("")){
			msg = "Address 1 Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_state.getText().equals("")){
			msg = "State Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_zip.getText().equals("")){
			msg = "Zip Code Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		else if(bill_phone.getText().equals("")){
			msg = "Telephone Id required.";
			vali = false;
			bill_address1.requestFocus();
		}
		statusAddnewCus.setText(msg);
		return vali;
	}

	/**
	 * Insert a new default row to the table, select a cell of it and scroll to
	 * it.
	 */
	public void addRow() {

		// get current position
		TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();

		// clear current selection
		twOrderDetail.getSelectionModel().clearSelection();

		// create new record and add it to the model
		ProductModel productModel = new ProductModel();
		twOrderDetail.getItems().add(productModel);

		// get last row
		int row = twOrderDetail.getItems().size() - 1;
		twOrderDetail.getSelectionModel().select(row, twd_sku1);

		// scroll to new row
		twOrderDetail.scrollTo(productModel);

	}

	public void updateListProduct() {
		// List<ProductModel> lstProduct = new ArrayList<>();
		// lstProduct = productDao.getListProductByOrderId(order_id);
	}

	public void SaveOrder() throws ClassNotFoundException, SQLException {
		if (isSave == false) {
			List<InvoiceModel> listOrder = new ArrayList<>();
			Float All_Total = (float) 0.00;
			OrderInfoModel orderInfoModel = getOrderInfoModel();
			for (int i = 0; i <= twOrderDetail.getItems().size() - 1; i++) {
				if (twOrderDetail.getItems().get(i).getTotal() == null) {

				} else {
					All_Total += Float.parseFloat(twOrderDetail.getItems().get(i).getTotal());
				}
			}
			All_Total = Float.parseFloat(txtTotalOrder.getText());
			order_id = orderDao.getLastNewOrderId();
			for (int i = 0; i <= twOrderDetail.getItems().size() - 1; i++) {
				InvoiceModel invoice = new InvoiceModel();
				ProductModel product = new ProductModel();
				product.setAll_Total(All_Total);
				product.setAddon(twOrderDetail.getItems().get(i).getAddon());
				product.setDisc(twOrderDetail.getItems().get(i).getDisc());
				product.setLot(twOrderDetail.getItems().get(i).getLot());
				product.setName(twOrderDetail.getItems().get(i).getName());
				product.setPrice(twOrderDetail.getItems().get(i).getPrice());
				product.setQty(twOrderDetail.getItems().get(i).getQty());
				product.setSize(twOrderDetail.getItems().get(i).getSize()== null?"":twOrderDetail.getItems().get(i).getSize());
				product.setSku(twOrderDetail.getItems().get(i).getSku());
				product.setTotal(twOrderDetail.getItems().get(i).getTotal());
				product.setCommission(twOrderDetail.getItems().get(i).getCommission());
				product.setReadyPayment(chkpm.isSelected()==true?"1":"0");
				product.setIssued(chkIs.isSelected()==true?"1":"0");
				product.setCreditMemo(creditmemo.isSelected()==true?"1":"0");
				if(creditmemo.isSelected()==true) {
				product.setFishdie(true);
				}else {
					product.setFishdie(false);
						
				}
				invoice.setOrderInfo(orderInfoModel);
				invoice.setProduct(product);
				if(product.getTotal() != null){
					Integer status = orderDao.addOrder(invoice, order_id);
					System.out.println(status);
					twOrderDetail.getSelectionModel().getSelectedItem().setId(status);
				}
				// listOrder.add(invoice);
			}
			System.out.println(order_id);
			try {
				gotoSendEmail(order_id);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isSave = true;
/*			try {
				if(chkIs.isSelected()){
					gotoOrders();
				}else{
					gotoOrdersTemp();	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		} else {

		}
	}

	public OrderInfoModel getOrderInfoModel() {
		OrderInfoModel orderInfoModel = new OrderInfoModel();
		orderInfoModel.setClientCustomerID(bill_cus_id.getText());
		orderInfoModel.setCustomer_ship(courier.getText());
		orderInfoModel.setAwb(tracking.getText());
		orderInfoModel.setFob(fob.getText());
		orderInfoModel.setTerms(terms.getText());
		orderInfoModel.setPonumber(ponumber.getText());
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
		if (type.equals("price")) {
			item.setPrice(news);
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
		/*
		 * try { orderDao.updateOrderProduct(items);
		 * orderDao.updateOrderTotal(editOrderId); } catch
		 * (ClassNotFoundException | SQLException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */

		/*
		 * Float totaln = (float) 0.00; for(int
		 * i=0;i<twOrderDetail.getItems().size() -1;i++){
		 * totaln+=Float.parseFloat(twOrderDetail.getItems().get(i).getTotal());
		 * } System.out.println(totaln);
		 */

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
	        isEditSku = true;
	        if (textField == null) {
	            createTextField();
	        }
	        setGraphic(textField);
	        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	        textField.selectAll();
	        textField.requestFocus();
/*	    	try {
             	Stage stage = new Stage();
                stage.setTitle("Search Products");
                stage.getIcons().add(new Image("file:resources/images/icon.png"));
                FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/ConfirmationSearchSku.fxml"));
                Pane myPane;
				myPane = (Pane)myLoader.load();
				ConfirmationSearchSkuController controller = (ConfirmationSearchSkuController) myLoader.getController();
         	 //   controller.setPrevStage(stage);
                Scene scene = new Scene(myPane);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.initModality( Modality.APPLICATION_MODAL );
                stage.initOwner( prevStage );
                stage.initStyle( StageStyle.UTILITY );
                controller.ConfirmationWindowAccounts(prevStage, stage);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                     @Override
                     public void handle(WindowEvent t) {
                    	 controller.chkClose = true;
                     }
                 }); 
                controller.twResultSearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
        		    @Override
        		    public void handle(KeyEvent event) {
        		      if(event.getCode() == KeyCode.PAGE_UP){
        		    	  txtKeySearch.requestFocus();
        		      }else{ 
        		    	  if (event.getCode() == KeyCode.ENTER ) {
        		    		  Entertb(controller);
        				  }
        		    	  if (event.getCode() == KeyCode.PAGE_DOWN ) {
        		    		 // txtEmail.requestFocus();
        		    	  }
        		      }
        		   }
        		}); 
                stage.setOnHiding(new EventHandler<WindowEvent>() {

                    @Override
                    public void handle(WindowEvent event) {
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                System.out.println("Application Closed by click to Close Button(X)");
                                System.out.println(controller.postStatus);
                                System.out.println(event.getEventType());
                                if( controller.chkClose == false){
        	                        if(controller.postStatus == true){
        	                        	
        	                        }else{
        	                        	
        	                        }
                                }
                            }
                        });
                    }
                });
                stage.show();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    }

	    @Override
	    public void cancelEdit() {
	        super.cancelEdit();
	        String string = getItem() == null ?"":getItem();
	        setText(string);
	        setContentDisplay(ContentDisplay.TEXT_ONLY);
/*	        if(isEditing()){
	        	isEditSku = false;
	        	System.out.println("cancle editsku = "+isEditSku);
	        }*/
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
	              //  isEditSku = true;
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
						        	  showPopup(false);
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
	//
	class BooleanCell extends TableCell<ProductModel, Boolean> {
        private CheckBox checkBox;
        
        public BooleanCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
           // checkBox.setOpacity(0);
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
            	if(item == null){
            		//item = true;
            	}else{
            		// checkBox.setOpacity(1);
            	}
                checkBox.setSelected(item);
/*                String sku = twOrderDetail.getSelectionModel().getSelectedItem().getSku();
                if(sku !=  null){
        			if(sku.indexOf("2") != -1){
        				checkBox.setDisable(true);    		
        				checkBox.setVisible(false);
        			}else{
        				checkBox.setDisable(false);  
        				checkBox.setVisible(true);
        			}            	
                }*/
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
		if(customer != null){
		if((!customer.getCustomerID().equals("")) && bill_cus_id.getLength()>0){
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
/*	public void BackToInvoice(ActionEvent event) throws IOException {
		showPopup(true);
	}*/
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

	
}