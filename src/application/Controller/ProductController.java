package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.ProductDao;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class ProductController implements Initializable {
	public boolean stateEdit = false; 
	Stage prevStage;
	@FXML
	private TableView<ProductModel> twResultSearch;
	@FXML
	private ChoiceBox filter;
	
	@FXML
	private TableColumn<ProductModel, String> tws_sku1;
	@FXML
	private TableColumn<ProductModel, String> tws_name;
	@FXML
	private TableColumn<ProductModel, String> tws_size;
	@FXML
	private TableColumn<ProductModel, String> tws_lot;
	@FXML
	private TableColumn<ProductModel, String> tws_price;
	@FXML
	private TextField keySearch;
	
	@FXML
	private TextField asku;
	@FXML
	private TextField aname;
	@FXML
	private TextField asize;
	@FXML
	private TextField alot;
	@FXML
	private TextField aprice;
	
	@FXML
	private Label statusAdd;
	
	final String[] filterPro = new String[] { "Sku", "Name", "Size", "Lot" ,"Price" };
	public String str_filters = filterPro[0];;
	
	public void setPrevStage(Stage stage) {
		this.prevStage = stage;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.asku.textProperty().addListener(new TextFieldListener(this.asku));
		this.aname.textProperty().addListener(new TextFieldListener(this.aname));
		this.asize.textProperty().addListener(new TextFieldListener(this.asize));
		this.alot.textProperty().addListener(new TextFieldListener(this.alot));
		this.aprice.textProperty().addListener(new TextFieldListener(this.aprice));
		twResultSearch.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.DELETE) {
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
				                    	System.out.println(twResultSearch.getSelectionModel().getSelectedItem().getIdo());
				    				 	ProductDao productDao = new ProductDao();
				    				 	try {
				    						productDao.delete(twResultSearch.getSelectionModel().getSelectedItem().getIdo());
				    						twResultSearch.getItems().removeAll(twResultSearch.getSelectionModel().getSelectedItems());
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
				if (event.getCode() == KeyCode.PAGE_UP) {
					keySearch.requestFocus();
				}
			}
		});
		keySearch.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.PAGE_DOWN) {
					twResultSearch.requestFocus();
					twResultSearch.getSelectionModel().clearSelection();
					twResultSearch.getSelectionModel().select(0, tws_sku1);
				}
			}
		});
		filter.setItems(FXCollections.observableArrayList("Sku", "Product Name", "Size", "Lot" ,"Price"));
		filter.setValue("Sku");
		filter.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				str_filters = filterPro[new_value.intValue()];
				System.out.println("--"+str_filters);
				try {
					actionSearch();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	/*	TableColumn<ProductModel, Integer> indexCol = new TableColumn<ProductModel, Integer>("#");
		indexCol.setCellFactory(new Callback<TableColumn<ProductModel, Integer>, TableCell<ProductModel, Integer>>() {
			@Override
			public TableCell<ProductModel, Integer> call(TableColumn<ProductModel, Integer> param) {
				return new TableCell<ProductModel, Integer>() {
					@Override
					protected void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);

						if (this.getTableRow() != null) {

							int index = this.getTableRow().getIndex();

							if (index < twResultSearch.getItems().size() || index <30) {
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
		twResultSearch.getColumns().add(0, indexCol); // number column is at
*/		// index 0
		twResultSearch.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// single cell selection mode
		twResultSearch.getSelectionModel().setCellSelectionEnabled(true);
		twResultSearch.setEditable(true);
		tws_sku1.setCellValueFactory(new PropertyValueFactory<>("sku"));
		tws_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		tws_size.setCellValueFactory(new PropertyValueFactory<>("size"));
		tws_lot.setCellValueFactory(new PropertyValueFactory<>("lot"));
		tws_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		tws_name.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("name");
            }
        });
		tws_size.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("size");
            }
        });
		tws_lot.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("lot");
            }
        });
		tws_price.setCellFactory(new Callback<TableColumn<ProductModel,String>, TableCell<ProductModel,String>>() {
            public TableCell call(TableColumn p) {
                return new EditingCell("price");
            }
        });
		tws_name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "name");
			}
		});
		tws_size.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "size");
			}
		});
		tws_lot.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "lot");
			}
		});
		tws_price.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductModel, String>>() {

			@Override
			public void handle(TableColumn.CellEditEvent<ProductModel, String> event) {
				changeTextfield(event, "price");
			}
		});
		// init list product
		Thread thLoadData = new Thread() {
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

		thLoadData.start();
	}
	private class TextFieldListener implements ChangeListener<String> {
		  private final TextField textField ;
		  TextFieldListener(TextField textField) {
		    this.textField = textField ;
			  textField.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			       
				    @Override
				    public void handle(KeyEvent event) {
				    	 
				    	if(event.getCode() == KeyCode.PAGE_DOWN){
				    		twResultSearch.requestFocus();
							twResultSearch.getSelectionModel().clearSelection();
							twResultSearch.getSelectionModel().select(0, tws_sku1);
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
	public void changeTextfield(TableColumn.CellEditEvent<ProductModel, String> event, String type) {
		ProductModel item = event.getRowValue();
		String news = event.getNewValue();
		if (type.equals("name")) {
			item.setName(news);
		}
		if (type.equals("size")) {
			item.setSize(news);
		}
		if (type.equals("lot")) {
			item.setLot(news);
		}
		if (type.equals("price")) {
			item.setPrice(news);
		}
		
		System.out.println(item.getName());
		ProductDao productDao = new ProductDao();
		try {
			productDao.update(item);
			twResultSearch.requestFocus();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actionSearch(ActionEvent event) throws IOException {
		actionSearch();
	}
	public void addProduct(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		addProduct();
	}
	public void addProducts(KeyEvent event) throws IOException, ClassNotFoundException, SQLException {
		if(event.getCode() == KeyCode.ENTER){
			addProduct();
		}
		
	}
	public void addProduct() throws IOException, ClassNotFoundException, SQLException {   
		if(validateProduct()){
			// action add product 
			ProductDao productDao = new ProductDao();
			ProductModel productModel = new ProductModel();
			productModel.setSku(asku.getText());
			productModel.setName(aname.getText());
			productModel.setSize(asize.getText());
			productModel.setLot(alot.getText());
			productModel.setPrice(aprice.getText());
			boolean chkSku = productDao.checkProductBySku(productModel);
			if(chkSku){
				String text = "sku "+asku.getText()+" exist";
				statusAdd.setText(text);
			}else{
				Integer status = productDao.addProduct(productModel);
				System.out.println("add product:"+status);	
				String text = "Add success product sku: "+asku.getText()+" .";
				statusAdd.setText(text);
			}
		}
	}
	public boolean validateProduct(){
		
		boolean validate = true;
		String text = "";
		if(asku.getText().equals("")){
			validate = false;
			text = "Product sku is required.";
		}
		else if(aname.getText().equals("")){
			validate = false;
			text = "Product name is required.";
		}
		else if(asize.getText().equals("")){
			//validate = false;
			//text = "Product Size is required.";
		}
		else if(alot.getText().equals("")){
			validate = false;
			text = "Product lot is required.";
		}
		else if(aprice.getText().equals("")){
			validate = false;
			text = "Product price is required.";
		}
		statusAdd.setText(text);
		return validate;
	}
	public void actionSearch() throws IOException {          
		System.out.println("key ="+keySearch.getText());
    	ProductDao productDao = new ProductDao();
    	try {
    		long start = System.nanoTime();    
    		
    		List<ProductModel> lstProduct = productDao.getListProductSearchs(keySearch.getText(),str_filters);
    		long elapsedTime = System.nanoTime() - start;
    		double seconds = (double)elapsedTime / 1000000000.0;
    		System.out.println("selconds: "+seconds);
    		// show result
    		twResultSearch.getItems().clear();
    		twResultSearch.getItems().addAll(lstProduct);
    		twResultSearch.getSelectionModel().select(0);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	public void gotoHome(ActionEvent event) throws IOException {
		gotoHome();
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
					    		//focusCusId();
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
						        	  //showPopup(false);
						           //   txtKeySearch.requestFocus();
						              textField.setText(oldValue);
					        	}else{
					        		textField.setText(oldValue);
					        	}
					        }
			        	}else{
			        		/*if (newValue.matches("\\d*")) {
					    		System.out.println(newValue);
					        } else {
				        		System.out.println(newValue);
					        	if(newValue.indexOf(".") != -1 && id.equals("price")){
					        		
					        	}else{
					        		textField.setText(oldValue);
					        	}
					        }*/
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