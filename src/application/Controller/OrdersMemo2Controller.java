package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Controller.OrdersMeno1Controller.BooleanCell;
import application.Dao.OrderDao;
import application.Model.CustomerModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import application.Utill.Menu;
import application.Utill.Pdf;
import application.Utill.PdfCreditMemo;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class OrdersMemo2Controller extends Menu implements Initializable {

/*
	 * @FXML private TableView<Order> twOrder;
	 */
	public Integer orderId = 0;
	public String customerID = "";
	private OrderDetailModel orderDetail ;
	private OrderDao orderDao = new OrderDao();
	List<ProductModel> lstProduct;
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					System.out.println(orderId);
					orderDetail = orderDao.getOrderDetail(orderId);
					showDetail(orderDetail);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		thLoadData.start();

		
	}
	public void showDetail(OrderDetailModel orderDetail) {
		CustomerModel bill = orderDetail.getCustomer();
		CustomerModel ship = orderDetail.getShipTo();
		OrderInfoModel orderInfo = orderDetail.getOrderInfo();
		lstProduct = orderDetail.getLstProduct();
		bill_cus_id.setText(bill.getCustomerID());
		customerID = bill.getCustomerID();
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
		if(lstProduct.size()>0){
			String readyPayment = lstProduct.get(0).getReadyPayment();
			String issued = lstProduct.get(0).getIssued();
			if(readyPayment.equals("1")){
				//chkpm.setSelected(true);
			}
			if(issued.equals("1")){
				//chkIs.setSelected(true);
			}
		}
		twOrderDetail.getItems().addAll(lstProduct);
		twOrderDetail.getSelectionModel().selectFirst();
		txtTotalOrder.setText(String.format ("%.2f", calTotal()));
		twOrderDetail.refresh();
		
		Platform.runLater(new Runnable() {
			  @Override
			  public void run() {
					twOrderDetail.requestFocus();
					calTotalFishDie();
			  }
			});
	}
	@FXML
	private TableView<ProductModel> twOrderDetail;

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
	private TableColumn<ProductModel, Boolean> twd_fishdie;
	
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
	private TextField txtTotalOrderFishDie;
	@FXML
	private TextField txtDiscount1;
	@FXML
	private TextField txtShippingCost;
	@FXML
	private TextField Customer_email;
	@FXML
	private TextField txtDiscount;
	@FXML
	private Button btnsave;
	@FXML
	private Button btnprint;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnprint.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	printCreditMemo();
	            }
	        }
	    });
		btnsave.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	saveCredit();
	            }
	        }
	    });
		twOrderDetail.setEditable(true);
		twd_sku.setCellValueFactory(new PropertyValueFactory<>("sku"));
		//twd_sku.setCellFactory(createNumberCellFactoryd());
	/*	twd_sku.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("sku");
            }
        });*/
		twd_qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
		//twd_qty.setCellFactory(createNumberCellFactoryd());

		twd_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		// twd_size.setCellFactory(createNumberCellFactoryd());

		twd_name.setCellValueFactory(new PropertyValueFactory<>("name"));

		twd_lot.setCellValueFactory(new PropertyValueFactory<>("lot"));

		twd_addon.setCellValueFactory(new PropertyValueFactory<>("addon"));
		//twd_addon.setCellFactory(createNumberCellFactoryd());

		twd_price.setCellValueFactory(new PropertyValueFactory<>("price"));

		twd_disk.setCellValueFactory(new PropertyValueFactory<>("disc"));
		//twd_disk.setCellFactory(createNumberCellFactoryd());
	       Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>> booleanCellFactory = 
	                new Callback<TableColumn<ProductModel, Boolean>, TableCell<ProductModel, Boolean>>() {
	                @Override
	                    public TableCell<ProductModel, Boolean> call(TableColumn<ProductModel, Boolean> p) {
	                        return new BooleanCell();
	                }
	            };

		twd_fishdie.setCellValueFactory(new PropertyValueFactory<>("fishdie"));
		twd_fishdie.setCellFactory(booleanCellFactory);
		
		twd_total.setCellValueFactory(new PropertyValueFactory<>("total"));
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
	}
	class BooleanCell extends TableCell<ProductModel, Boolean> {
        private CheckBox checkBox;
        public BooleanCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
           // checkBox.setOpacity(0);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean> () {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                	 if(isEditing()){
                        TablePosition pos = twOrderDetail.getFocusModel().getFocusedCell();
                        System.out.println(newValue+"-"+pos.getRow()+"-"+lstProduct.get(pos.getRow()).getId());
                        
                        lstProduct.get(pos.getRow()).setFishdie(newValue);
                        calTotalFishDie();
                        /*try {
     						orderDao.updateFishDie(lstProduct.get(pos.getRow()).getId(),newValue);
     					} catch (ClassNotFoundException e) {
     						// TODO Auto-generated catch block
     						e.printStackTrace();
     					} catch (SQLException e) {
     						// TODO Auto-generated catch block
     						e.printStackTrace();
     					}*/
                      }else{
                      	
                      }
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
           // checkBox.setVisible(false);
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
            		item = true;
            	}else{
                    checkBox.setOpacity(1);
            	}
                checkBox.setSelected(item);
            }
        }
    }
	public void focusSkuTwD(KeyEvent event) throws IOException {
		 if(event.getCode() == KeyCode.PAGE_DOWN){
 		  	
	      }
	}
	public void saveCredit(){
		btnsave.setDisable(true);
		
		Thread thLoadData = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				// check database exits order credit
				try {
					orderDao.deleteOnceOrderCreditMemo(orderId);
					orderDao.addceOrderCreditMemo(lstProduct,orderId);

				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//insert database
				
				//
				int count = twOrderDetail.getItems().size();
				for(int i =0;i<count;i++){
					String sub = lstProduct.get(i).getTotal();
					Boolean fishdie = lstProduct.get(i).getFishdie();
					if(sub != null){
							try {
								orderDao.updateFishDie(lstProduct.get(i).getId(),fishdie);
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
				//
				Platform.runLater(new Runnable() {
					  @Override
					  public void run() {
							btnsave.setDisable(false);
							btnsave.requestFocus();
					  }
					});

			}
		};

		thLoadData.start();
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
	public Float calTotalFishDie(){
		Float total = (float) 0;
		int count = twOrderDetail.getItems().size();
		for(int i =0;i<count;i++){
			String sub = lstProduct.get(i).getTotal();
			Boolean fishdie = lstProduct.get(i).getFishdie();
		//	System.out.println(lstProduct.get(i).getId());
			if(sub != null){
				if(fishdie != false){
					total = total + Float.parseFloat(sub);
				}
			}
		}
		txtTotalOrderFishDie.setText(String.format ("%.2f", total));
		return total;
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
    public void gotoPrint(ActionEvent event) throws IOException {          
    	printCreditMemo();
     }
    public void printCreditMemo(){
    	System.out.println("111");
    	PdfCreditMemo pdf = new PdfCreditMemo();
			try {
				String orderidc = orderId+"-c";
				pdf.print(customerID,orderidc);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
    }
    public void gotoSave(ActionEvent event) throws IOException {          
    	saveCredit();
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

	public void gotoHome(ActionEvent event) throws IOException {
		gotoHome();
	}
}


