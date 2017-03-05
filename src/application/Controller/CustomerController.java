package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.CustomerDao;
import application.Model.CustomerModel;
import application.Model.ProductModel;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CustomerController implements Initializable {
	@FXML
	private Label statusCus;
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
	private TextField courier;
	@FXML
	private TextField terms;
	@FXML
	private TextField salsperson;
	@FXML
	private TextField txtShippingCost;
	@FXML
	private TextField saleEmail;
	
	@FXML
	private TextField Phone2;
	@FXML
	private TextField Cellphone;
	@FXML
	private TextField Fax;
	@FXML
	private TextField Country;
	@FXML
	private TextField Destination;
	@FXML
	private TextField AlrportCode;
	@FXML
	private TextField Time;
	@FXML
	private TextField Contact;
	@FXML
	private TextField Contact2;
	@FXML
	private TextField EMail;
	@FXML
	private TextField EMail2;
	@FXML
	private TextField Title;
	@FXML
	private TextField Title2;
	@FXML
	private TextField Website;
	@FXML
	private TextArea Comments;
	@FXML
	private TextField CurrBal;
	@FXML
	private TextField OpnCred;
	@FXML
	private TextField YTDSales;
	@FXML
	private TextField LstSales1;
	@FXML
	private TextField LstPmt1;
	@FXML
	private TextField Entered;
	@FXML
	private TextField LstSales2;
	@FXML
	private TextField LstPmt2;
	@FXML
	private TextField NetDue;
	@FXML
	private TextField Price;
	@FXML
	private TextField CreditLimit;
	@FXML
	private TextField Tax;
	@FXML
	private TextField SalesDisc;
	@FXML
	private Button btnAdd;
	@FXML
	private TextField Customer_email;
	final String[] filter = new String[] { "ID","Name", "Contact", "Phone", "Email" ,"City" };
	private String str_filters = filter[0];
	final String[] scouriers = new String[] { "OnTrac", "UPS", "Aeromexico", "Aerounion", "Air Canada", "Alaska Airlines", "American Airlines Cargo","Cathay Pacific Airways", "COPA Airlines", "Delta Air Lines","Southwest Airlines","Southwest Airlines","United Airlines Cargo"};
	@FXML
	private MenuItem menuItemOrders;
	@FXML
	private ChoiceBox cbFilter;
	@FXML
	private ChoiceBox<String> scourier;
	@FXML
	private MenuItem menuItemInvoicer;
	@FXML
	private MenuItem menuItemHome;
	@FXML
	private MenuItem menuItemCustomers;
	@FXML
	private TextField keySearch;
	private Integer  tkey=0;
	@FXML
	private Pane paneSearchCus;	
	@FXML
	private TableView<CustomerModel> twSearchCus;
	
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
	Stage prevStage;
	private CustomerModel customer;
	public boolean isFixKey = false;
	public boolean isFixKeys = false;
	public int isFixKeyn = 0;
	public void setPrevStage(Stage stage) {
		this.prevStage = stage;
	}
	public void isShowSearchCus(boolean isShow){
		//if(isShow == true){
			paneSearchCus.setVisible(isShow);
			twSearchCus.setVisible(isShow);
			statusCus.setVisible(!isShow);
		//}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		isShowSearchCus(false);
		btnAdd.setVisible(false);
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
		
		this.Phone2.textProperty().addListener(new TextFieldListener(this.Phone2));
		this.Cellphone.textProperty().addListener(new TextFieldListener(this.Cellphone));
		this.Fax.textProperty().addListener(new TextFieldListener(this.Fax));
		this.Country.textProperty().addListener(new TextFieldListener(this.Country));
		this.Destination.textProperty().addListener(new TextFieldListener(this.Destination));
		this.AlrportCode.textProperty().addListener(new TextFieldListener(this.AlrportCode));
		this.Time.textProperty().addListener(new TextFieldListener(this.Time));
		this.Contact.textProperty().addListener(new TextFieldListener(this.Contact));
		this.Contact2.textProperty().addListener(new TextFieldListener(this.Contact2));
		this.EMail.textProperty().addListener(new TextFieldListener(this.EMail));
		this.EMail2.textProperty().addListener(new TextFieldListener(this.EMail2));
		this.Title.textProperty().addListener(new TextFieldListener(this.Title));
		this.Title2.textProperty().addListener(new TextFieldListener(this.Title2));
		this.Website.textProperty().addListener(new TextFieldListener(this.Website));
		this.Comments.textProperty().addListener(new TextAreListener(this.Comments));
		this.CurrBal.textProperty().addListener(new TextFieldListener(this.CurrBal));
		this.OpnCred.textProperty().addListener(new TextFieldListener(this.OpnCred));
		this.YTDSales.textProperty().addListener(new TextFieldListener(this.YTDSales));
		this.LstSales1.textProperty().addListener(new TextFieldListener(this.LstSales1));
		this.LstPmt1.textProperty().addListener(new TextFieldListener(this.LstPmt1));
		this.Entered.textProperty().addListener(new TextFieldListener(this.Entered));
		this.LstSales2.textProperty().addListener(new TextFieldListener(this.LstSales2));
		this.LstPmt2.textProperty().addListener(new TextFieldListener(this.LstPmt2));
		this.NetDue.textProperty().addListener(new TextFieldListener(this.NetDue));
		this.Price.textProperty().addListener(new TextFieldListener(this.Price));
		this.CreditLimit.textProperty().addListener(new TextFieldListener(this.CreditLimit));
		this.Tax.textProperty().addListener(new TextFieldListener(this.Tax));
		menuItemInvoicer.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
		tcs_cus.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
		tcs_name.setCellValueFactory(new PropertyValueFactory<>("CompanyName"));
		tcs_contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
		tcs_phone.setCellValueFactory(new PropertyValueFactory<>("Phone1"));
		tcs_email.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tcs_city.setCellValueFactory(new PropertyValueFactory<>("City"));
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
		bill_cus_id.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_DOWN){
		    	  
		      }else{   	
		    	  gotoSearchCus();
		      }
		    }
		});
		twSearchCus.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.PAGE_UP){
		    	  keySearch.requestFocus();
		      }else{ 
		    	  if(isFixKeys = true){
		    		  isFixKeyn = 2;
		    		  isFixKeys = false;
		    		  if(isFixKey == true){
		    			 // isFixKeyn = 1;
		    		  }
		    	  }
		    	  if(event.getCode() == KeyCode.ENTER && isFixKey == true){
		    		  isFixKeyn++;
		    	  }
		    	  System.out.println("--"+isFixKeys);
		    	  System.out.println("--"+isFixKeyn);


		    	  if (event.getCode() == KeyCode.ENTER && isFixKeyn == 2) {
		    		  isFixKeyn = 0;
		    		  isFixKey = false;
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
						    	statusCus.setText("You can edit user.");
						    	btnAdd.setVisible(false);
						    	//bill_cus_id.setText(bill.getCustomerID());
						    	bill_name.setText(bill.getCompanyName());
						    	bill_address1.setText(bill.getAddress());
						    	bill_address2.setText(bill.getAddress2());
						    	bill_city.setText(bill.getCity());
						    	bill_state.setText(bill.getStates());
						    	bill_zip.setText(bill.getZip());
						    	bill_phone.setText(bill.getPhone1());
						    	courier.setText(bill.getCarrier());
						    	//scourier.setValue(bill.getCarrier());
						    	terms.setText(bill.getTerms());
						    	salsperson.setText(bill.getSalesperson());
						 
						    	txtShippingCost.setText(String.valueOf(bill.getShippingCost()));
						    	
						    	Phone2.setText(bill.getPhone2());
						    	Cellphone.setText(bill.getCellphone());
						    	Fax.setText(bill.getFax());
						    	Country.setText(bill.getCountry());
						    	Destination.setText(bill.getDestination());
						    	AlrportCode.setText(bill.getAlrportCode());
						    	Time.setText(bill.getTime());
						    	Contact.setText(bill.getContact());
						    	Contact2.setText(bill.getContact2());
						    	EMail.setText(bill.getEMail());
						    	EMail2.setText(bill.getEMail2());
						    	Title.setText(bill.getTitle());
						    	Title2.setText(bill.getTitle2());
						    	Website.setText(bill.getWebsite());
						    	Comments.setText (bill.getComments());
						    	CurrBal.setText(String.valueOf(bill.getCurrBal()));
						    	OpnCred.setText(String.valueOf(bill.getOpnCred()));
						    	YTDSales.setText(String.valueOf(bill.getYTDSales()));
						    	LstSales1.setText(String.valueOf(bill.getLstSales1()));
						    	LstPmt1.setText(String.valueOf(bill.getLstPmt1()));
						    	Entered.setText(bill.getEntered());
						    	LstSales2.setText(bill.getLstSales2());
						    	LstPmt2.setText(bill.getLstPmt2());
						    	NetDue.setText(bill.getNetDue());
						    	Price.setText(bill.getPrice());
						    	CreditLimit.setText(String.valueOf(bill.getCreditLimit()));
						    	Tax.setText(String.valueOf(bill.getTax()));
						    	SalesDisc.setText(String.valueOf(bill.getSalesDisc()));
						    	if(bill.getSaleEmail() == null){
							    	saleEmail.setText(bill.getEmail());
						    	}else{
							    	saleEmail.setText(bill.getSaleEmail());
						    	}
						    	Customer_email.setText(bill.getEmail());
						    	bill_cus_id.requestFocus();
							} else {
								customer = new CustomerModel();
							}
						}
						

					}
		    	  if (event.getCode() == KeyCode.DELETE ) {
						ConfirmationWindow a = new ConfirmationWindow(prevStage, "Confirmation");
						a.show();
						a.setAlwaysOnTop(true);
						isFixKey = true;
						isFixKeys = true;
						a.stage.setOnHiding(new EventHandler<WindowEvent>() {

				            @Override
				            public void handle(WindowEvent event) {
				                Platform.runLater(new Runnable() {

				                    @Override
				                    public void run() {
				                        System.out.println("Application Closed by click to Close Button(X)");
				                        System.out.println(a.postStatus);
				                        if(a.postStatus == true){
				                        	CustomerDao customerDao = new CustomerDao();
				                        	String cusID = twSearchCus.getSelectionModel().getSelectedItem().getCustomerID();
				                        	try {
												customerDao.delete(cusID);
					                        	twSearchCus.getItems().removeAll(twSearchCus.getSelectionModel().getSelectedItems());
											} catch (ClassNotFoundException | SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
				                        }
				                        isShowSearchCus(true);
				                        twSearchCus.requestFocus();
				                    }
				                });
				            }
				        }); 
					}
		      }
		   }
		}); 
		keySearch.focusedProperty().addListener(new ChangeListener<Boolean>()
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
		cbFilter.setItems(FXCollections.observableArrayList("ID", "Customer Name", "Contact", "Phone", "Email" ,"City"));
		cbFilter.setValue("ID");
		cbFilter.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				str_filters = filter[new_value.intValue()];
				tkey = new_value.intValue();
				System.out.println(str_filters); //////////////////////////////////
				//gotoSearchCus();
				try {
					actionSearchCus();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		scourier.setItems(FXCollections.observableArrayList("OnTrac", "UPS", "Aeromexico", "Aerounion", "Air Canada", "Alaska Airlines", "American Airlines Cargo","Cathay Pacific Airways", "COPA Airlines", "Delta Air Lines","Southwest Airlines","Southwest Airlines","United Airlines Cargo"));
		scourier.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
			//	String str_filters = filter[new_value.intValue()];
				//tkey = new_value.intValue();
			//	System.out.println(str_filters); //////////////////////////////////
				 tranferCus();
			}
		});
		try {
			actionSearchCus();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		keySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		    	 // System.out.println("handler : " + keySearch.getText());
			      if(keySearch.getText().length()>0){
		    		System.out.println(keySearch.getText());////////////////////////////////////////
		    		gotoSearchCus();
			      }
		    	}
		    });*/
		keySearch.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
		       
		    @Override
		    public void handle(KeyEvent event) {
		      if(event.getCode() == KeyCode.DOWN){
		    	  twSearchCus.requestFocus();
		      }else{ 
		    	  if(event.getCode() == KeyCode.PAGE_DOWN){
		    		  bill_cus_id.requestFocus();
			      }
		      }
		   }
		});  
		//keySearch.requestFocus();
		

	}
	public void actionSearchCus() throws IOException {
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
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
    public void actionSearchCus(EventHandler event) throws IOException {          
    	actionSearchCus();
    }
	public void tranferCus(){
		if(customer != null){
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
			//customer.setCarrier(scourier.getValue());
			//System.out.println(scourier.getValue());
			//System.out.println(txtShippingCost.getText());
			customer.setPhone2(Phone2.getText());
			customer.setCellphone(Cellphone.getText());
			customer.setFax(Fax.getText());
			customer.setCountry(Country.getText());
			customer.setDestination(Destination.getText());
			customer.setAlrportCode(AlrportCode.getText());
			customer.setTime(Time.getText());
			customer.setContact(Contact.getText());
			customer.setContact2(Contact2.getText());
			customer.setEMail(EMail.getText());
			customer.setEMail2(EMail2.getText());
			customer.setTitle(Title.getText());
			customer.setTitle2(Title2.getText());
			customer.setWebsite(Website.getText());
			customer.setComments(Comments.getText());
			customer.setCurrBal(Float.parseFloat(CurrBal.getText()));
			customer.setOpnCred(Float.parseFloat(OpnCred.getText()));
			customer.setYTDSales(Float.parseFloat(YTDSales.getText()));
			customer.setLstSales1(Float.parseFloat(LstSales1.getText()));
			customer.setLstPmt1(Float.parseFloat(LstPmt1.getText()));
			customer.setEntered(Entered.getText());
			customer.setLstSales2(LstSales2.getText());
			customer.setLstPmt2(LstPmt2.getText());
			customer.setNetDue(NetDue.getText());
			customer.setPrice(Price.getText());
			customer.setCreditLimit(Float.parseFloat(CreditLimit.getText()));
			customer.setTax(Integer.parseInt(Tax.getText()));
			customer.setSalesDisc(SalesDisc.getText());
			  CustomerDao customerDao = new CustomerDao();
			  try {
				int status = customerDao.updateCustomer(customer);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	private class TextAreListener implements ChangeListener<String> {
		  private final TextArea textArea ;
		  TextAreListener(TextArea textArea) {
		    this.textArea = textArea ;
		    textArea.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
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
	public void gotoHome(ActionEvent event) throws IOException {
		gotoHome();
	}
	public void addCustomer(ActionEvent event) throws IOException {
		addCustomer();
	}
	public void addCustomers(KeyEvent event) throws IOException {
		if(event.getCode() == KeyCode.ENTER){
			addCustomer();
		}
	}

	public void addCustomer() throws IOException { 
		// validate field
		boolean vali = true;//validateAddCus();
		// check exits customer id
		if(vali == true){
			String customerId = bill_cus_id.getText();
			boolean chkCus = chkCustomer(customerId);
			if(chkCus == false){
				// add to db
				int status  = addDoCustomer();
				if(status == 1){
					String msg = "Add new customer success.";
					bill_cus_id.requestFocus();
					statusCus.setText(msg);
				}else{
					String msg = "Add new customer fail.";
					bill_cus_id.requestFocus();
					statusCus.setText(msg);
				}
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
			statusCus.setText(msg);
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
		statusCus.setText(msg);
		return vali;
	}
	public void gotoSearchCus(){
		String key = bill_cus_id.getText();
		String type = filter[tkey];
		System.out.println(key);
		if(key.length()>0){
			  CustomerDao customerDao = new CustomerDao();
			 try {
					CustomerModel bill = customerDao.getCustomerSearch(key,type);
			    	customer = bill;
			    	if(bill.getCustomerID().equals("") || bill.getCustomerID() == null){
			    		statusCus.setText("Customer not exits.");
			    		btnAdd.setVisible(true);
			    	}else{
				    	statusCus.setText("You can edit user.");
				    	btnAdd.setVisible(false);
			    	}
			    	//bill_cus_id.setText(bill.getCustomerID());
			    	bill_name.setText(bill.getCompanyName());
			    	bill_address1.setText(bill.getAddress());
			    	bill_address2.setText(bill.getAddress2());
			    	bill_city.setText(bill.getCity());
			    	bill_state.setText(bill.getStates());
			    	bill_zip.setText(bill.getZip());
			    	bill_phone.setText(bill.getPhone1());
			    	courier.setText(bill.getCarrier());
			    	//scourier.setValue(bill.getCarrier());
			    	terms.setText(bill.getTerms());
			    	salsperson.setText(bill.getSalesperson());
			 
			    	txtShippingCost.setText(String.valueOf(bill.getShippingCost()));
			    	
			    	Phone2.setText(bill.getPhone2());
			    	Cellphone.setText(bill.getCellphone());
			    	Fax.setText(bill.getFax());
			    	Country.setText(bill.getCountry());
			    	Destination.setText(bill.getDestination());
			    	AlrportCode.setText(bill.getAlrportCode());
			    	Time.setText(bill.getTime());
			    	Contact.setText(bill.getContact());
			    	Contact2.setText(bill.getContact2());
			    	EMail.setText(bill.getEMail());
			    	EMail2.setText(bill.getEMail2());
			    	Title.setText(bill.getTitle());
			    	Title2.setText(bill.getTitle2());
			    	Website.setText(bill.getWebsite());
			    	Comments.setText (bill.getComments());
			    	CurrBal.setText(String.valueOf(bill.getCurrBal()));
			    	OpnCred.setText(String.valueOf(bill.getOpnCred()));
			    	YTDSales.setText(String.valueOf(bill.getYTDSales()));
			    	LstSales1.setText(String.valueOf(bill.getLstSales1()));
			    	LstPmt1.setText(String.valueOf(bill.getLstPmt1()));
			    	Entered.setText(bill.getEntered());
			    	LstSales2.setText(bill.getLstSales2());
			    	LstPmt2.setText(bill.getLstPmt2());
			    	NetDue.setText(bill.getNetDue());
			    	Price.setText(bill.getPrice());
			    	CreditLimit.setText(String.valueOf(bill.getCreditLimit()));
			    	Tax.setText(String.valueOf(bill.getTax()));
			    	SalesDisc.setText(String.valueOf(bill.getSalesDisc()));
			    	if(bill.getSaleEmail() == null){
				    	saleEmail.setText(bill.getEmail());
			    	}else{
				    	saleEmail.setText(bill.getSaleEmail());
			    	}
			    	Customer_email.setText(bill.getEmail());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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


	

	public void gotoOrders() throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Orders");
		stage.getIcons().add(new Image("file:resources/images/icon.png"));
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Orders.fxml"));
		Pane myPane = (Pane) myLoader.load();

		OrdersController controller = (OrdersController) myLoader.getController();
		controller.setPrevStage(stage);
		Scene scene = new Scene(myPane);
		stage.setScene(scene);
		prevStage.close();
		stage.setResizable(false);
		stage.show();
	}

	public void gotoInvoice() throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Create Invoice");
		stage.getIcons().add(new Image("file:resources/images/icon.png"));
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Invoice.fxml"));
		Pane myPane = (Pane) myLoader.load();

		CustomerController controller = (CustomerController) myLoader.getController();
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
	            } else if (t.getCode() == KeyCode.ESCAPE) {
	                cancelEdit();
	            }
	        });
	        textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
			    @Override
			    public void handle(KeyEvent event) {
			    	if(id.equals("sku")){
				    	if(event.getCode() != KeyCode.BACK_SPACE){
				    		if(textField.getText().length()==5 && textField.getText().indexOf("-") == -1)
				    			textField.fireEvent(new KeyEvent(KeyEvent.KEY_TYPED, "-", "-", KeyCode.RIGHT, false, false, false, false));
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