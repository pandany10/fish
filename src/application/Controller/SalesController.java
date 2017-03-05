package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.Dao.SalesDao;
import application.Model.SalesModel;
import application.Utill.Pdf;
import application.Utill.PdfSales;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SalesController implements Initializable {
	public boolean stateEdit = false; 
	Stage prevStage;
	@FXML
	private TableView<SalesModel> twSummary;
	@FXML
	private TableView<SalesModel> twSales;
	@FXML
	private TableColumn<SalesModel, String> tws_date; 
	@FXML
	private TableColumn<SalesModel, String> tws_day; 
	@FXML
	private TableColumn<SalesModel, String> tws_total_pending; 
	@FXML
	private TableColumn<SalesModel, String> tws_total_complete; 
	
	@FXML
	private TableColumn<SalesModel, String> tcInv; 
	@FXML
	private TableColumn<SalesModel, String> tcDate; 
	@FXML
	private TableColumn<SalesModel, String> tcCustomer; 
	@FXML
	private TableColumn<SalesModel, String> tcInvTotal; 
	@FXML
	private TableColumn<SalesModel, String> tcNonTotal; 
	
	@FXML
	private DatePicker dpFromDate;
	@FXML
	private DatePicker dpToDate;
	@FXML
	private DatePicker dpToDate1;
	
	@FXML
	private Label lf;
	@FXML
	private Label lt;
	@FXML
	private Label ltday;
	
	@FXML
	private RadioButton rdoSumary;
	@FXML
	private RadioButton rdoSale;
	
	@FXML
	private Pane paneSale;
	
	@FXML
	private Label lblTpending;
	
	@FXML
	private Label lblTcomplete;
	
	@FXML
	private Button btnPrint;
	
	@FXML
	private ChoiceBox filterDate;
	final String[] filterDates = new String[] { "Day", "Week", "Month", "Year" };
	final String[] lstDayofWeek = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY","THURSDAY","FRIDAY","SATURDAY" };
	private String str_filters = filterDates[1];
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	DateFormat dateFormatSql = new SimpleDateFormat("yyyy-MM-dd");
	SalesDao saleDao = new SalesDao();
	public void setPrevStage(Stage stage) {
		this.prevStage = stage;
	}
	public static final LocalDate LOCAL_DATE (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//init param
		///dpFromDate.showingProperty();
		//dpFromDate.setShowWeekNumbers(true);
		showDate(true);
		filterDate.setItems(FXCollections.observableArrayList("Day", "Week", "Month", "Year"));
		filterDate.setValue("Week");
		filterDate.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue ov, Number value, Number new_value) {
				str_filters = filterDates[new_value.intValue()];
				System.out.println("--"+str_filters);
				calculatorDate(str_filters);
			}
		});
		/*Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -7);
		Date d = c.getTime();
		
		Date date = new Date();
		String todate = dateFormat.format(date);
		String fromdate = dateFormat.format(d);*/
		calculatorDate(str_filters);
		/*dpToDate.setValue(LOCAL_DATE(todate));
		dpFromDate.setValue(LOCAL_DATE(fromdate));*/
		rdoSumary.setSelected(true);	
		tws_date.setCellValueFactory(new PropertyValueFactory<>("Customer_date"));
		tws_day.setCellValueFactory(new PropertyValueFactory<>("Day"));
		tws_total_pending.setCellValueFactory(new PropertyValueFactory<>("total_pending"));
		tws_total_complete.setCellValueFactory(new PropertyValueFactory<>("total_complete"));
		
		
		tcInv.setCellValueFactory(new PropertyValueFactory<>("inv"));
		tcDate.setCellValueFactory(new PropertyValueFactory<>("intDate"));
		tcCustomer.setCellValueFactory(new PropertyValueFactory<>("invCusName"));
		tcInvTotal.setCellValueFactory(new PropertyValueFactory<>("invTotal"));
		tcNonTotal.setCellValueFactory(new PropertyValueFactory<>("invNon"));
		twSummary.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
	            @Override
	            public void handle(ScrollEvent scrollEvent) {
	               System.out.println("Scrolled1.");
	            }
	     });
		twSales.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent scrollEvent) {
               System.out.println("Scrolled2.");
            }
       });

		twSummary.setEditable(true);
		twSales.setEditable(true);
		
		Platform.setImplicitExit(false);
		// init process data
		processData();
		showTab(false);
		
		
	}
	public void processData(ActionEvent event){
		processData();
	}
	public void processData1(ActionEvent event){
		processData1();
	}
	public void processData1(){
		LocalDate cudate = dpToDate1.getValue();
		dpToDate.setValue(cudate);
		dpFromDate.setValue(cudate);
		System.out.println("vo");
	//	processData();
	}
	public void processData(){
		twSummary.setPlaceholder(new Label("Please wait… Searching Database."));
		twSales.setPlaceholder(new Label("Please wait… Searching Database."));
		btnPrint.setDisable(true);
		LocalDate fDate  = dpFromDate.getValue();
		LocalDate tDate  = dpToDate.getValue();
		String fromDate = getDate(fDate);
		String toDate = getDate(tDate);
		String type = getType();
		System.out.println(fromDate);
		System.out.println(toDate);
		System.out.println(type);
		if(type.equals("sumary")){	
				twSummary.getItems().clear();
				twSummary.requestFocus();
				lblTpending.setText("");
				lblTcomplete.setText("");
				Thread thLoadDatas = new Thread() {
					@SuppressWarnings("deprecation")
					public void run() {
						try {
							List<SalesModel> lstSale = saleDao.getSale(fromDate, toDate, type);
							twSummary.getItems().addAll(lstSale);
							twSummary.getSelectionModel().select(0);
							calTotal();
							this.suspend();
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};

				thLoadDatas.start();
	  }
	if(type.equals("sale")){		
		twSales.getItems().clear();
		twSales.requestFocus();
		Thread thLoadDatas1 = new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				try {
					List<SalesModel> lstSale = saleDao.getSales(fromDate, toDate, type);	
					twSales.getItems().addAll(lstSale);
					twSales.getSelectionModel().select(0);
					Platform.runLater(new Runnable() {
						  @Override
						  public void run() {
								btnPrint.setDisable(false);
								int count = twSales.getItems().size();
								twSales.refresh();
								if(count == 0){
									twSales.setPlaceholder(new Label("No matching results were found."));
								}
						  }
						});
					this.suspend();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		thLoadDatas1.start();
	}

	}
	
	public void showDate(boolean ishow){
		dpFromDate.setVisible(ishow);
		dpToDate.setVisible(ishow);
		lf.setVisible(ishow);
		lt.setVisible(ishow);
		dpToDate1.setVisible(!ishow);
		ltday.setVisible(!ishow);
	}
	
	public void dclick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
		}
		System.out.println("test");
		//dpFromDate.show();
	}
	public void eclick(MouseEvent event) throws IOException {
		if(event.getClickCount() == 2){
		}
		System.out.println("test2");
	}
	public void calculatorDate(String type){
		/*LocalDate fDate  = dpFromDate.getValue();
		LocalDate tDate  = dpToDate.getValue();
		String fromDate = getDate(fDate);
		String toDate = getDate(tDate);*/
		
		if(type.equals("Day")){ // custom day
			showDate(false);
			Date date = new Date();
			String cudate = dateFormat.format(date);
			//LocalDate curentDate = LOCAL_DATE(cudate);
			dpToDate1.setValue(LOCAL_DATE(cudate));
			dpToDate.setValue(LOCAL_DATE(cudate));
			dpFromDate.setValue(LOCAL_DATE(cudate));
			dpToDate1.show();
		}
		if(type.equals("Week")){  // sunday to current day
			showDate(true);
			Date date = new Date();
			String cudate = dateFormat.format(date);
			LocalDate curentDate = LOCAL_DATE(cudate);
			String Day = curentDate.getDayOfWeek().toString();
			int count = 0;
			int discount = 0;
			for (String stringday : lstDayofWeek) {
				if(Day.equals(stringday)){
					discount = count;
				}else{
					
				}
				count++;
			}
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, - discount);
			Date d = c.getTime();
			
			String fromdate = dateFormat.format(d);
			dpToDate.setValue(LOCAL_DATE(cudate));
			dpFromDate.setValue(LOCAL_DATE(fromdate));
		}
		if(type.equals("Month")){ // 01 to current day
			showDate(true);
			Date date = new Date();
			String cudate = dateFormat.format(date);
			LocalDate curentDate = LOCAL_DATE(cudate);
			int Day = curentDate.getDayOfMonth()-1;
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, - Day);
			Date d = c.getTime();
			String fromdate = dateFormat.format(d);
			dpToDate.setValue(LOCAL_DATE(cudate));
			dpFromDate.setValue(LOCAL_DATE(fromdate));
		}
		if(type.equals("Year")){ // 01/01 to current day 
			showDate(true);
			Date date = new Date();
			String cudate = dateFormat.format(date);
			LocalDate curentDate = LOCAL_DATE(cudate);
			int Day = curentDate.getDayOfYear()-1;
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, - Day);
			Date d = c.getTime();
			String fromdate = dateFormat.format(d);
			dpToDate.setValue(LOCAL_DATE(cudate));
			dpFromDate.setValue(LOCAL_DATE(fromdate));
		}
		
	}
	
	public void calTotal(){
		Float totalP = (float) 0;
		Float totalC = (float) 0;
		int count = twSummary.getItems().size();
		for(int i =0;i<count;i++){
			String sub = twSummary.getItems().get(i).getTotal_pending();
			String subs = twSummary.getItems().get(i).getTotal_complete();
			if(sub != null && !sub.isEmpty()){
				totalP = totalP + Float.parseFloat(sub.replace("$", "").replace(",", ""));
			}
			if(subs != null && !subs.isEmpty()){
				totalC = totalC + Float.parseFloat(subs.replace("$", "").replace(",", ""));
			}
			twSummary.getItems().get(i).setTotal_pending("$"+String.format ("%,.2f",Float.parseFloat(sub.replace("$", "").replace(",", ""))));
			twSummary.getItems().get(i).setTotal_complete("$"+String.format ("%,.2f",Float.parseFloat(subs.replace("$", "").replace(",", ""))));
			twSummary.refresh();
		}
		String txtTpending = "$"+String.format ("%,.2f",totalP);
		String txtTcom = "$"+String.format ("%,.2f",totalC);
		System.out.println(txtTpending);
		System.out.println(txtTcom);

		Platform.runLater(new Runnable() {
			  @Override
			  public void run() {
					lblTpending.setText(txtTpending);
					lblTcomplete.setText(txtTcom);  
					btnPrint.setDisable(false);
					int count = twSummary.getItems().size();
					if(count == 0){
						twSummary.setPlaceholder(new Label("No matching results were found."));
					}
			  }
			});

	}
	public String getType(){
		String type = "";
		if(rdoSumary.isSelected() == true){
			type = "sumary";
		}
		if(rdoSale.isSelected() == true){
			type = "sale";
		}
		return type;
	}
	public String getDate(LocalDate fDate){
		int year = fDate.getYear();
		int month = fDate.getMonthValue();
		int day = fDate.getDayOfMonth();
		String days = "";
		String months = "";
		if(day<10){
			days = "0";
		}
		if(month<10){
			months = "0";
		}
		String strDate = year+"-"+months+month+"-"+days+day;

		return strDate;
	}
	//
	public void Summary(ActionEvent event) throws IOException {
		//gotoHome();
		showTab(false);
	//	if(rdoSumary.isSelected() == false){
			rdoSumary.setSelected(true);
			if(rdoSumary.isSelected() == true){
				rdoSale.setSelected(false);
			}
			processData();
	//	}
	}
	public void SalesPerson(ActionEvent event) throws IOException {
		showTab(true);
	//if(rdoSale.isSelected() == false){
			rdoSale.setSelected(true);
			if(rdoSale.isSelected() == true){
				rdoSumary.setSelected(false);
			}
			processData();
	//	}
	}
	public void showTab(boolean isShow){
		paneSale.setVisible(isShow);
		twSales.setVisible(isShow);
		twSummary.setVisible(!isShow);
		lblTpending.setVisible(!isShow);
		lblTcomplete.setVisible(!isShow);
	}
	public void gotoHome(ActionEvent event) throws IOException {
		gotoHome();
	}
	public void PrintPdf(ActionEvent event) throws IOException {
		PrintPdf();
	}
	public void PrintPdf(){
		PdfSales pdf = new PdfSales();
		int count = twSummary.getItems().size();
		int counts = twSales.getItems().size();

		List<SalesModel> lstSale = new ArrayList<>();
		LocalDate fDate  = dpFromDate.getValue();
		LocalDate tDate  = dpToDate.getValue();
		String fromDate = getDate(fDate);
		String toDate = getDate(tDate);
		String type = getType();
		System.out.println(fromDate);
		System.out.println(toDate);
		System.out.println(type);
		if(type.equals("sale")){
			for(int i =0;i<counts;i++){
				 SalesModel salesModel = new SalesModel();
				 salesModel = twSales.getItems().get(i);
				 lstSale.add(salesModel);
				 System.out.println(salesModel.getCustomer_date());
			}
		}else {
			for(int i =0;i<count;i++){
				 SalesModel salesModel = new SalesModel();
				 salesModel = twSummary.getItems().get(i);
				 lstSale.add(salesModel);
				 System.out.println(salesModel.getCustomer_date());
			}
		}
		try {
			pdf.Print(lstSale,fromDate,toDate,type);
		} catch (IOException e) {
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
}