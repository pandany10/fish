package application.Dao;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import application.DBConnection;
import application.Model.AirlinesModel;
import application.Model.CustomerModel;
import application.Model.InvoiceModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class OrderDao {
	private List<OrderModel> currentOrder;
	private OrderDetailModel orderDetail;
	public List<OrderModel> getOrderCustomerSale() throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_order.customerfishpro t1 inner join exoticre_order.orders t2 on t1.CustomerID = t2.ClientCustomerID WHERE t1.CompanyName != '' and t2.amoutPaid != t2.All_Total  and t2.status != 'COMPLETED' GROUP BY t2.order_id order by t1.CompanyName asc limit 20000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd"); 
		while (rs.next()) {
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Date");
			Float All_Total = rs.getFloat("All_Total");
			String All_Totals = String.format ("%.2f", All_Total);
			
			Float amoutPaid = rs.getFloat("amoutPaid");
			String amoutPaids = String.format ("%.2f", amoutPaid);
			
			Float amoutUnPaid = All_Total-amoutPaid;
			String amoutUnPaids = String.format ("%.2f", amoutUnPaid);

			Date d1 = null;
			Date d2 = null;

			try {
			Date date = new Date();
			String dateStop = format.format(date);
			String dateStart = Customer_date;

			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			long diff = d2.getTime() - d1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			System.out.println(diffDays + " days, ");


			OrderModel order = new OrderModel();
			order.setCustomerId(customerId);
			order.setCustomerName(customerName);
			order.setCustomerContact(customerContact);
			order.setCustomerPhone(customerPhone);
			order.setCustomerTerms(customerTerms);
			order.setCustomerSalesperson(customerSalesperson);
			order.setOrder_id(order_id);
			order.setCustomer_date(Customer_date);
			order.setAll_Total(All_Totals);
			order.setAmoutPaid(amoutPaids); 
			order.setAmoutUnPaid(amoutUnPaids);
/*			order.setBlance30("");
			order.setBlance60("");
			order.setBlance90("");
			order.setBlance120("");*/

			if(diffDays>=0 && diffDays <=30){
				order.setBlance30(All_Totals);
			}else if(diffDays>30 && diffDays<=60){
				order.setBlance60(All_Totals);
			}else if(diffDays>60 && diffDays<=90){
				order.setBlance90(All_Totals);
			}else if(diffDays>90 ){
				order.setBlance120(All_Totals);
			}
			lstOrder.add(order);
			} catch (Exception e) {
			e.printStackTrace();
			}
			
		}
		return lstOrder;
	}
	public List<OrderModel> getOrderCustomerSale(String Customerid) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_order.customerfishpro t1 inner join exoticre_order.orders t2 on t1.CustomerID = t2.ClientCustomerID WHERE t1.CompanyName != '' and t2.amoutPaid != t2.All_Total  and t2.status != 'COMPLETED' and t2.amoutPaid = '0.00' and t2.ClientCustomerID = '"+Customerid+"' GROUP BY t2.order_id order by t2.Date desc limit 20000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd"); 

		while (rs.next()) {			
			String email = rs.getString("Email");
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Date");
			Float All_Total = rs.getFloat("All_Total");
			String All_Totals = String.format ("%.2f", All_Total);
			
			Float amoutPaid = rs.getFloat("amoutPaid");
			String amoutPaids = String.format ("%.2f", amoutPaid);
			
			Float amoutUnPaid = All_Total-amoutPaid;
			String amoutUnPaids = String.format ("%.2f", amoutUnPaid);
			Date d1 = null;
			Date d2 = null;

			try {
			Date date = new Date();
			String dateStop = format.format(date);
			String dateStart = Customer_date;

			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);

			long diff = d2.getTime() - d1.getTime();
			long diffDays = diff / (24 * 60 * 60 * 1000);
			System.out.println(diffDays + " days, ");
			OrderModel order = new OrderModel();
			order.setCustomerId(customerId);
			order.setCustomerName(customerName);
			order.setCustomerContact(customerContact);
			order.setCustomerPhone(customerPhone);
			order.setCustomerTerms(customerTerms);
			order.setCustomerSalesperson(customerSalesperson);
			
			order.setOrder_id(order_id);
			order.setCustomer_date(Customer_date);
			order.setAll_Total(All_Totals);
			order.setAmoutPaid(amoutPaids); 
			order.setAmoutUnPaid(amoutUnPaids);
			order.setCustomer_email(email);
			if(diffDays>=0 && diffDays <=30){
				order.setBlance30(All_Totals);
			}else if(diffDays>30 && diffDays<=60){
				order.setBlance60(All_Totals);
			}else if(diffDays>60 && diffDays<=90){
				order.setBlance90(All_Totals);
			}else if(diffDays>90 ){
				order.setBlance120(All_Totals);
			}
			lstOrder.add(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lstOrder;
	}
	public List<OrderModel> getAgingCustomer() throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_order.customerfishpro t1 inner join exoticre_order.orders t2 on t1.CustomerID = t2.ClientCustomerID WHERE t1.CompanyName != '' and t2.amoutPaid != t2.All_Total  and t2.status != 'COMPLETED'    GROUP BY t2.ClientCustomerID order by t1.CompanyName asc limit 20000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Date");
			Float All_Total = rs.getFloat("All_Total");
			String All_Totals = String.format ("%.2f", All_Total);
			
			Float amoutPaid = rs.getFloat("amoutPaid");
			String amoutPaids = String.format ("%.2f", amoutPaid);
			
			Float amoutUnPaid = All_Total-amoutPaid;
			String amoutUnPaids = String.format ("%.2f", amoutUnPaid);
			
			OrderModel order = new OrderModel();
			order.setCustomerId(customerId);
			order.setCustomerName(customerName);
			order.setCustomerContact(customerContact);
			order.setCustomerPhone(customerPhone);
			order.setCustomerTerms(customerTerms);
			order.setCustomerSalesperson(customerSalesperson);
			
			order.setOrder_id(order_id);
			order.setCustomer_date(Customer_date);
			order.setAll_Total(All_Totals);
			order.setAmoutPaid(amoutPaids); 
			order.setAmoutUnPaid(amoutUnPaids);
			lstOrder.add(order);
		}
		return lstOrder;
	}
	public List<OrderModel> getOrderAging(String Customerid) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_order.customerfishpro t1 inner join exoticre_order.orders t2 on t1.CustomerID = t2.ClientCustomerID WHERE t1.CompanyName != '' and t2.amoutPaid != t2.All_Total  and t2.status != 'COMPLETED'    and t2.ClientCustomerID = '"+Customerid+"' GROUP BY t2.order_id order by t1.CompanyName asc limit 20000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Date");
			Float All_Total = rs.getFloat("All_Total");
			String All_Totals = String.format ("%.2f", All_Total);
			
			Float amoutPaid = rs.getFloat("amoutPaid");
			String amoutPaids = String.format ("%.2f", amoutPaid);
			
			Float amoutUnPaid = All_Total-amoutPaid;
			String amoutUnPaids = String.format ("%.2f", amoutUnPaid);
			
			OrderModel order = new OrderModel();
			order.setCustomerId(customerId);
			order.setCustomerName(customerName);
			order.setCustomerContact(customerContact);
			order.setCustomerPhone(customerPhone);
			order.setCustomerTerms(customerTerms);
			order.setCustomerSalesperson(customerSalesperson);
			
			order.setOrder_id(order_id);
			order.setCustomer_date(Customer_date);
			order.setAll_Total(All_Totals);
			order.setAmoutPaid(amoutPaids); 
			order.setAmoutUnPaid(amoutUnPaids);
			lstOrder.add(order);
		}
		return lstOrder;
	}
	public List<OrderModel> getOrderCustomerSale1(String Customerid) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_order.customerfishpro t1 inner join exoticre_order.orders t2 on t1.CustomerID = t2.ClientCustomerID WHERE t1.CompanyName != '' and t2.ClientCustomerID = '"+Customerid+"'  GROUP BY t2.order_id order by t2.Date desc limit 20000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		Float All_balance = 0.00f;
		while (rs.next()) {
			String email = rs.getString("Email");
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Date");
			Float All_Total = rs.getFloat("All_Total");
			All_balance = All_balance + All_Total;
			String All_Totals = String.format ("%.2f", All_Total);
			String All_balances = String.format ("%,.2f", All_balance);
            String paymentMethod = rs.getString("paymentMethod");

			Float amoutPaid = rs.getFloat("amoutPaid");
			String amoutPaids = String.format ("%.2f", amoutPaid);
			
			Float amoutUnPaid = All_Total-amoutPaid;
			String amoutUnPaids = String.format ("%.2f", amoutUnPaid);
			
			OrderModel order = new OrderModel();
			order.setCustomerId(customerId);
			order.setCustomerName(customerName);
			order.setCustomerContact(customerContact);
			order.setCustomerPhone(customerPhone);
			order.setCustomerTerms(customerTerms);
			order.setCustomerSalesperson(customerSalesperson);
			String sqlsSub = "SELECT Total,disc FROM exoticre_order.orders WHERE order_id = '"+order_id+"'  limit 500;";
			ResultSet rs1 = DBConnection.getConnection().createStatement().executeQuery(sqlsSub);
			Float totalDisc = 0.00f;
			while (rs1.next()) {
				Float Total = rs.getFloat("Total");
				Integer disc = rs.getInt("disc");
				Float subdisc = (Total*disc)/100;
				totalDisc = totalDisc + subdisc;
			}
			String totalDiscs = String.format ("%.2f", totalDisc);
			order.setDisc(totalDiscs);
			order.setOrder_id(order_id);
			order.setCustomer_date(Customer_date);
			order.setAll_Total(All_Totals);
			order.setBalance(All_balances);
			order.setAmoutPaid(amoutPaids); 
			order.setAmoutUnPaid(amoutUnPaids);
			order.setCustomer_email(email);
/*            String payments1 = "NONE";
            if((!paymentMethod.equals("") && amoutPaid != 0.00)){
            	payments1 = "YES";
            }*/
            order.setPaymentMethod(paymentMethod);          
			lstOrder.add(order);
		}
		return lstOrder;
	}
	public List<OrderModel> getOrderByOrderId(int orderId) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.order_id) = LOWER('"+orderId+"') GROUP BY t1.order_id order by t1.order_id desc limit 100";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			 String Customer_date = rs.getString("Customer_date");
             String status = rs.getString("status");
             String Customer_ship = rs.getString("Customer_ship");
             Integer order_id = rs.getInt("order_id");
             String ClientCustomerID = rs.getString("ClientCustomerID");
             String Customer_email =  rs.getString("Customer_email");
             String CompanyName =  rs.getString("CompanyName");
             Float All_Total = rs.getFloat("All_Total");
             String All_Totals = String.format ("%.2f", All_Total);
             Float surcharge = rs.getFloat("surcharge");
             String payment = rs.getString("payment");
             String issued = rs.getString("issued");
             String paymentMethod = rs.getString("paymentMethod");
             String notes1= rs.getString("notes");
             Float amoutPaid = rs.getFloat("amoutPaid");
             Boolean payments = false;
             Boolean issueds = false;
             if(payment.equals("1")){
            	 payments = true;
             }else{
            	 payments = false;
             }
             if(paymentMethod.equals("")){
            	 
             }else{
            	 if(amoutPaid != 0.00){
            		 payments = true;
            	 }
             }
             if(issued.equals("1")){
            	 issueds = true;
             }else{
            	 issueds = false;
             }
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
             order.setIssued(issueds);
            	 lstOrder.add(order);
		}
		return lstOrder;
	}
	public List<OrderModel> getOrderByOrderId(String customerId) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.ClientCustomerID) = LOWER('"+customerId+"') GROUP BY t1.order_id order by t1.order_id desc limit 100";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			 String Customer_date = rs.getString("Customer_date");
             String status = rs.getString("status");
             String Customer_ship = rs.getString("Customer_ship");
             Integer order_id = rs.getInt("order_id");
             String ClientCustomerID = rs.getString("ClientCustomerID");
             String Customer_email =  rs.getString("Customer_email");
             String CompanyName =  rs.getString("CompanyName");
             Float All_Total = rs.getFloat("All_Total");
             String All_Totals = String.format ("%.2f", All_Total);
             Float surcharge = rs.getFloat("surcharge");
             String payment = rs.getString("payment");
             String issued = rs.getString("issued");
             String paymentMethod = rs.getString("paymentMethod");
             String notes1= rs.getString("notes");
             Float amoutPaid = rs.getFloat("amoutPaid");
             Boolean payments = false;
             Boolean issueds = false;
             if(payment.equals("1")){
            	 payments = true;
             }else{
            	 payments = false;
             }
             if(paymentMethod.equals("")){
            	 
             }else{
            	 if(amoutPaid != 0.00){
            		 payments = true;
            	 }
             }
             if(issued.equals("1")){
            	 issueds = true;
             }else{
            	 issueds = false;
             }
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
             order.setIssued(issueds);
            	 lstOrder.add(order);
		}
		return lstOrder;
	}
	public List<OrderModel> getOrderPrint(int orderid) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE t1.order_id = '"+orderid+"' GROUP BY t1.order_id order by t1.order_id desc limit 1000";

		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
             String Customer_date = rs.getString("Customer_date");
             String status = rs.getString("status");
             String Customer_ship = rs.getString("Customer_ship");
             Integer order_id = rs.getInt("order_id");
             String ClientCustomerID = rs.getString("ClientCustomerID");
             String Customer_email =  rs.getString("Customer_email");
             String CompanyName =  rs.getString("CompanyName");
             Float All_Total = rs.getFloat("All_Total");
             String All_Totals = String.format ("%.2f", All_Total);
             Float surcharge = rs.getFloat("surcharge");
             String payment = rs.getString("payment");
             String issued = rs.getString("issued");
             String paymentMethod = rs.getString("paymentMethod");
             String notes1= rs.getString("notes");
             Float amoutPaid = rs.getFloat("amoutPaid");
             Boolean payments = false;
             Boolean issueds = false;
             if(payment.equals("1")){
            	 payments = true;
             }else{
            	 payments = false;
             }
             if(paymentMethod.equals("")){
            	 
             }else{
            	 if(amoutPaid != 0.00){
            		 payments = true;
            	 }
             }
             if(issued.equals("1")){
            	 issueds = true;
             }else{
            	 issueds = false;
             }
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
             order.setIssued(issueds);
             lstOrder.add(order);
           /*  if((!notes.equals("App Java")) && notes1.equals("App Java") && issueds == false && payments == false){
            	 
             }else if(!notes.equals("App Java")){
                 lstOrder.add(order);
             }else if(notes.equals("App Java") && issueds == false && payments == false){
            	 lstOrder.add(order);
             }*/
         }
		return lstOrder;
	}
	public List<OrderModel> getOrderMemo() throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.fishdie) = LOWER('1')  GROUP BY t1.order_id order by t1.order_id desc limit 1000;";

		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
             String Customer_date = rs.getString("Customer_date");
             String status = rs.getString("status");
             String Customer_ship = rs.getString("Customer_ship");
             Integer order_id = rs.getInt("order_id");
             String order_idc = order_id+"-c";

             String ClientCustomerID = rs.getString("ClientCustomerID");
             String Customer_email =  rs.getString("Customer_email");
             String CompanyName =  rs.getString("CompanyName");
             Float All_Total = rs.getFloat("All_Total");
             String All_Totals = String.format ("%.2f", All_Total);
             Float surcharge = rs.getFloat("surcharge");
             String payment = rs.getString("payment");
             String issued = rs.getString("issued");
             String paymentMethod = rs.getString("paymentMethod");
             String notes1= rs.getString("notes");
             Float amoutPaid = rs.getFloat("amoutPaid");
             Boolean payments = false;
             Boolean issueds = false;
             if(payment.equals("1")){
            	 payments = true;
             }else{
            	 payments = false;
             }
             if(paymentMethod.equals("")){
            	 
             }else{
            	 if(amoutPaid != 0.00){
            		 payments = true;
            	 }
             }
             if(issued.equals("1")){
            	 issueds = true;
             }else{
            	 issueds = false;
             }
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
             order.setIssued(issueds);
             order.setOrder_idc(order_idc);
             lstOrder.add(order);
           /*  if((!notes.equals("App Java")) && notes1.equals("App Java") && issueds == false && payments == false){
            	 
             }else if(!notes.equals("App Java")){
                 lstOrder.add(order);
             }else if(notes.equals("App Java") && issueds == false && payments == false){
            	 lstOrder.add(order);
             }*/
         }
		return lstOrder;
	}
	public static void openWebpage(String url) {
	    try {
	        new ProcessBuilder("x-www-browser", url).start();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public List<AirlinesModel> getAirlines() throws ClassNotFoundException, SQLException{
		List<AirlinesModel> lstAirline = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_wrdp1.airlines WHERE selected=1 ORDER BY name";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 AirlinesModel item = new AirlinesModel();
			 String text = rs.getString("name");
			 String value = rs.getString("prefix");
			 item.setText(text);
			 item.setValue(value);
			 lstAirline.add(item);
		 }
		return lstAirline;
	}
	public List<OrderModel> getOrder(String in_status,String notes) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String status1 = "";
		if(in_status.endsWith("Express")){
			status1 = "1";
		}
		if(in_status.endsWith("Exotic")){
			status1 = "0";
		}
		System.out.println(in_status);
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes,t1.tracking ,t1.tracking_link FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.issued) = LOWER('1') AND t1.isExpress ='"+status1+"' GROUP BY t1.order_id order by t1.order_id desc limit 1000";
		if(in_status.endsWith("Both")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes,t1.tracking  ,t1.tracking_link FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.issued) = LOWER('1') GROUP BY t1.order_id order by t1.order_id desc limit 1000";
		}
		//String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.status) = LOWER('"+in_status+"') GROUP BY t1.order_id order by t1.order_id desc limit 200";

		if(notes.equals("App Java")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes,t1.tracking   ,t1.tracking_link  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.issued) = LOWER('0')   and t1.payment = '0' GROUP BY  t1.order_id order by t1.order_id desc limit 1000";
		  //sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes     FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.status) = LOWER('"+in_status+"') and t1.notes = '"+notes+"' GROUP BY  t1.order_id order by t1.order_id desc limit 200";
		}
		if(notes.equals("Express")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes,t1.tracking  ,t1.tracking_link   FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.isExpress) = LOWER('1')  GROUP BY  t1.order_id order by t1.order_id desc limit 1000";
		}
		if(notes.equals("App Java1")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes,t3.order_idc,t1.tracking  ,t1.tracking_link   FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID  left join ordersCreditMemo t3 On t1.order_id = t3.order_id WHERE LOWER(t1.status) = LOWER('COMPLETED')  GROUP BY  t1.order_id order by t1.order_id desc limit 1000";
		}
		if(notes.equals("ExpressStore")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes,t1.tracking  ,t1.tracking_link   FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.isExpress) = LOWER('1') and t1.ClientCustomerID = '"+in_status+"' GROUP BY  t1.order_id order by t1.order_id desc limit 1000";
		}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
             String Customer_date = rs.getString("Customer_date");
             String status = rs.getString("status");
             String Customer_ship = rs.getString("Customer_ship");
             Integer order_id = rs.getInt("order_id");

             String ClientCustomerID = rs.getString("ClientCustomerID");
             String Customer_email =  rs.getString("Customer_email");
             String CompanyName =  rs.getString("CompanyName");
             Float All_Total = rs.getFloat("All_Total");
             String All_Totals = String.format ("%.2f", All_Total);
             Float surcharge = rs.getFloat("surcharge");
             String payment = rs.getString("payment");
             String issued = rs.getString("issued");
             String paymentMethod = rs.getString("paymentMethod");
             String notes1= rs.getString("notes");
             Float amoutPaid = rs.getFloat("amoutPaid");
             
             String tracking = rs.getString("tracking");
             String tracking_link = rs.getString("tracking_link");

             Boolean payments = false;
             Boolean issueds = false;
             if(payment.equals("1")){
            	 payments = true;
             }else{
            	 payments = false;
             }
             if(paymentMethod.equals("")){
            	 
             }else{
            	 if(amoutPaid != 0.00){
            		 payments = true;
            	 }
             }
             if(issued.equals("1")){
            	 issueds = true;
             }else{
            	 issueds = false;
             }

             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
             order.setIssued(issueds);
             order.setTracking(tracking);
             	Hyperlink link = new Hyperlink();
	     		link.setText(tracking_link);
	     		link.setOnAction(new EventHandler<ActionEvent>() {
	     		    @Override
	     		    public void handle(ActionEvent e) {
	     		        System.out.println("This link is clicked");
	     		      //  openWebpage(tracking_link);
	     		       URI myUri;
					try {
						myUri = new URI(tracking_link);
						Desktop.getDesktop().browse(myUri);
					} catch (URISyntaxException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	     		    } 
	     		});
             order.setTrackinglink(link);
             if(notes.equals("App Java1")){
                 String order_idc = rs.getString("order_idc");
	             if(order_idc != null && !order_idc.isEmpty()) { 
	          		 order.setFishDie("Yes");
	          	 }
             }
             lstOrder.add(order);
           /*  if((!notes.equals("App Java")) && notes1.equals("App Java") && issueds == false && payments == false){
            	 
             }else if(!notes.equals("App Java")){
                 lstOrder.add(order);
             }else if(notes.equals("App Java") && issueds == false && payments == false){
            	 lstOrder.add(order);
             }*/
         }
		return lstOrder;
	}
	public List<OrderModel> getLstPayment() throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment,t1.paymentMethod ,t1.amoutPaid    FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE readyPayment = '1' GROUP BY  t1.order_id order by t1.order_id desc limit 500";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
            String Customer_date = rs.getString("Customer_date");
            String status = rs.getString("status");
            String Customer_ship = rs.getString("Customer_ship");
            Integer order_id = rs.getInt("order_id");
            String ClientCustomerID = rs.getString("ClientCustomerID");
            String Customer_email =  rs.getString("Customer_email");
            String CompanyName =  rs.getString("CompanyName");
            Float All_Total = rs.getFloat("All_Total");
            String All_Totals = String.format ("%.2f", All_Total);
            All_Total = Float.parseFloat(All_Totals);
            Float surcharge = rs.getFloat("surcharge");
            String payment = rs.getString("payment");
            String paymentMethod = rs.getString("paymentMethod");
            String notes1= rs.getString("notes");
            Float amoutPaid = rs.getFloat("amoutPaid");
            
            Boolean payments = false;
            if(payment.equals("1")){
           	 payments = true;
            }else{
           	 payments = false;
            }
            OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
            String payments1 = "NONE";
            if((!paymentMethod.equals("") && amoutPaid != 0.00)){
            	payments1 = "YES";
            }
            order.setPayments(payments1);
            lstOrder.add(order);
        }
		return lstOrder;
	}
	public List<OrderModel> getLstPayment(String CustomerId) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		List<String> lstPre = new ArrayList<>();

		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment,t1.paymentMethod ,t1.amoutPaid,t1.notes ,t1.applyDate,t1.checknumber,t1.emailPaypal   FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE readyPayment = '1' and  LOWER(t1.ClientCustomerID) = LOWER('"+CustomerId+"')  GROUP BY  t1.order_id order by t1.order_id desc limit 500";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		String previousPayment = "";
		
		 while (rs.next()) {
            String Customer_date = rs.getString("Customer_date"); //
            String status = rs.getString("status");
            String Customer_ship = rs.getString("Customer_ship");
            Integer order_id = rs.getInt("order_id"); //
            String ClientCustomerID = rs.getString("ClientCustomerID");
            String Customer_email =  rs.getString("Customer_email");
            String CompanyName =  rs.getString("CompanyName");
            Float All_Total = rs.getFloat("All_Total"); //
            String All_Totals = String.format ("%.2f", All_Total);
            All_Total = Float.parseFloat(All_Totals);
            Float surcharge = rs.getFloat("surcharge");
            String payment = rs.getString("payment");
            String paymentMethod = rs.getString("paymentMethod");//paymentMethod
            String notes1= rs.getString("notes");
            Float amoutPaid = rs.getFloat("amoutPaid"); //
            String amoutPaids = String.format ("%.2f", amoutPaid);
            String applyDate = rs.getString("applyDate");
            String checknumber = rs.getString("checknumber");
            String emailPaypal = rs.getString("emailPaypal");
            
            if(checknumber == null){
            	checknumber = "";
            }
            Boolean payments = false;
            if(payment.equals("1")){
           	 payments = true;
            }else{
           	 payments = false;
            }
            OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
            String payments1 = "NONE";
            if((!paymentMethod.equals("") && amoutPaid != 0.00)){
            	payments1 = "YES";
            }else{
            	
            }
            if((!paymentMethod.equals("") && amoutPaid != 0.00)){
            	
            }else{
            	amoutPaids="0.00";
            }
            
            order.setPayments(payments1);
            order.setPaymentMethod(paymentMethod);
            order.setAmoutPaid(amoutPaids);
            order.setEmailPaypal(emailPaypal);
            
            //if(i<lstPre.size()-1){
            order.setPreviousPayment(amoutPaids);
           // }else{
            	//order.setPreviousPayment("");
            //}
            
            order.setPaymentDueDate(Customer_date);
            order.setApplyDate(applyDate);
    		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd"); 
    		Date d1 = null;
			Date d2 = null;

			Date date = new Date();
			String dateStop = format.format(date);
			String dateStart = Customer_date;
			//if(applyDate.equals("")){
			//	order.setDaysDueDate(0);
			//}else{
				try {
					
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);
					long diff = d2.getTime() - d1.getTime();
					int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
					System.out.println(diffDays + " days, ");
					order.setDaysDueDate(diffDays);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//}
			
            //if((!paymentMethod.equals("") && amoutPaid != 0.00)){
            //}else{
            lstOrder.add(order);
            previousPayment = amoutPaids;
            //}
        }
		 int i =0;
		 for (OrderModel element : lstOrder) {
			   if(i<lstOrder.size()-1){
				   lstOrder.get(i).setPreviousPayment(lstOrder.get(i+1).getPreviousPayment());
			   }else{
				   lstOrder.get(i).setPreviousPayment("");
			   }
			   i++;
		 }
		return lstOrder;
	}
	public List<OrderModel> getLstPaymentExpress() throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment,t1.paymentMethod ,t1.amoutPaid    FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE readyPayment = '1' and isExpress = '1' GROUP BY  t1.order_id order by t1.order_id desc limit 500";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
            String Customer_date = rs.getString("Customer_date");
            String status = rs.getString("status");
            String Customer_ship = rs.getString("Customer_ship");
            Integer order_id = rs.getInt("order_id");
            String ClientCustomerID = rs.getString("ClientCustomerID");
            String Customer_email =  rs.getString("Customer_email");
            String CompanyName =  rs.getString("CompanyName");
            Float All_Total = rs.getFloat("All_Total");
            String All_Totals = String.format ("%.2f", All_Total);
            All_Total = Float.parseFloat(All_Totals);
            Float surcharge = rs.getFloat("surcharge");
            String payment = rs.getString("payment");
            String paymentMethod = rs.getString("paymentMethod");
            String notes1= rs.getString("notes");
            Float amoutPaid = rs.getFloat("amoutPaid");
            
            Boolean payments = false;
            if(payment.equals("1")){
           	 payments = true;
            }else{
           	 payments = false;
            }
            OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments,notes1);
            String payments1 = "NONE";
            if((!paymentMethod.equals("") && amoutPaid != 0.00)){
            	payments1 = "YES";
            }else{
            	
            }
            order.setPayments(payments1);
            if((!paymentMethod.equals("") && amoutPaid != 0.00)){
            }else{
            	 lstOrder.add(order);
            }
        }
		return lstOrder;
	}
	public OrderDetailModel getOrderDetail(Integer order_id) throws ClassNotFoundException, SQLException {
		OrderDetailModel orderDetail = new OrderDetailModel();
		
		CustomerDao customerDao = new CustomerDao();
		OrderInfoDao orderInfoDao = new OrderInfoDao();

		ProductDao productDao = new ProductDao();
		
	//	OrderModel order = new OrderModel();
	    //currentorder = currentOrder.getOrderDetail
		
		List<ProductModel> lstProduct = new ArrayList<>(); 
		lstProduct = productDao.getListProductByOrderId(order_id);
		
		CustomerModel customer = customerDao.getCustomerByOrderId(order_id);
		OrderInfoModel  orderInfo = new OrderInfoModel(); // 
		orderInfo = orderInfoDao.getOrderInfoByOrderId(order_id);
		
		
		orderDetail.setCustomer(customer);
		orderDetail.setShipTo(customer);
		orderDetail.setOrderInfo(orderInfo);
		orderDetail.setLstProduct(lstProduct);
		return orderDetail;
	}
	public OrderDetailModel getOrderDetailExpress(Integer order_id) throws ClassNotFoundException, SQLException {
		OrderDetailModel orderDetail = new OrderDetailModel();
		
		CustomerDao customerDao = new CustomerDao();
		OrderInfoDao orderInfoDao = new OrderInfoDao();

		ProductDao productDao = new ProductDao();
		
		List<ProductModel> lstProduct = new ArrayList<>(); 
		lstProduct = productDao.getListProductByOrderIdExpress(order_id);
		
		CustomerModel customer = customerDao.getCustomerByOrderId(order_id);
		OrderInfoModel  orderInfo = new OrderInfoModel(); // 
		orderInfo = orderInfoDao.getOrderInfoByOrderId(order_id);
		
		
		orderDetail.setCustomer(customer);
		orderDetail.setShipTo(customer);
		orderDetail.setOrderInfo(orderInfo);
		orderDetail.setLstProduct(lstProduct);
		return orderDetail;
	}
	
	public Boolean updateOrderProduct(ProductModel product) throws ClassNotFoundException, SQLException {
		if(product.getId() != null){
			int commission = 1;
			if(product.getCommission() == false){
				commission = 0;
			}
			System.out.println(product.getSku());
			String sql = "UPDATE exoticre_order.orders SET  "
					+ "Product_Sku ='"+product.getSku()+"',"
					+ "Item = '"+product.getQty()+"',"
					+ "Size = '"+product.getSize()+"',"
					+ "Product_name = '"+product.getName()+"',"
					+ "Lot = '"+product.getLot()+"',"
					+ "Addon = '"+product.getAddon()+"',"
					+ "Price = '"+product.getPrice()+"',"
					+ "disc = '"+product.getDisc()+"',"
					+ "commission = "+commission+","
					+ "Total = '"+product.getTotal()+"'"
					+ "  WHERE id = "+product.getId();
			int status = DBConnection.getConnection().createStatement().executeUpdate(sql);
			System.out.println("update product order: "+status);
		}
		return true;
	}
	public Boolean updateOrderTotal(Integer order_id,Float discount) throws ClassNotFoundException, SQLException {
		String sql = "SELECT Total   FROM exoticre_order.orders WHERE 	order_id =  "+order_id;
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		Float allTotal = (float) 0;
		 while (rs.next()) {
			 allTotal += rs.getFloat("Total");
        }
		 Float dis =  discount/100;
		 allTotal = (1- dis)*allTotal;
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "All_Total ='"+allTotal+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update product order Total : "+status);
		return true;
	}
	public float getTotalMemo(String CusId) throws ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM exoticre_order.ordersCreditMemo where ClientCustomerID = "+CusId+" group by order_idc;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		Float allTotal = (float) 0;
		 while (rs.next()) {
			 allTotal += rs.getFloat("All_Total_Memo");
        }
		String sql1 = "SELECT amoutMemo FROM exoticre_order.orders where ClientCustomerID = "+CusId+" group by order_id;";
		ResultSet rs1 = DBConnection.getConnection().createStatement().executeQuery(sql1);
		while (rs1.next()) {
			allTotal -= rs1.getFloat("amoutMemo");
	    }
		return allTotal;
	}
	public float getAmountMemo(Integer order_id) throws ClassNotFoundException, SQLException {
		String sql = "SELECT amoutMemo FROM exoticre_order.orders where order_id = "+order_id+" group by order_id;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		Float allTotal = (float) 0;
		 while (rs.next()) {
			 allTotal += rs.getFloat("amoutMemo");
        }
		return allTotal;
	}
	public Boolean updatePaymentMethod(Integer order_id,String paymentMethod) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "paymentMethod ='"+paymentMethod+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update payment Method: "+status);
		// for new payment
		return true;
	}
	public Boolean updateAmoutPaid(Integer order_id,Float amountPaid) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "amoutPaid ='"+amountPaid+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update amount Paid: "+status);
		// for new payment
		return true;
	}
	public Boolean updateAmoutMemo(Integer order_id,Float amountMemo) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "amoutMemo ='"+amountMemo+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update amount amoutMemo: "+status);
		// for new payment
		return true;
	}
	public Boolean updateTracking(Integer order_id,String tracking) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "tracking ='"+tracking+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  tracking: "+status);
		// for new payment
		return true;
	}
	public Boolean updateTrackingLink(Integer order_id,String trackingLink) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "tracking_link ='"+trackingLink+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  trackingLink: "+status);
		// for new payment
		return true;
	}
	public Boolean updateCheckN(Integer order_id,String  txtCheckN) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "checknumber ='"+txtCheckN+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update txtCheckN : "+status);
		return true;
	}
	
	public Boolean updateOrderTotals(Integer order_id,Float allTotal) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "All_Total ='"+allTotal+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update product order Total : "+status);
		return true;
	}
	public Boolean delete(Integer id) throws ClassNotFoundException, SQLException {
		String sql1 = "DELETE FROM  exoticre_order.orders  "
				+ "  WHERE id = "+id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("delete product order Total : "+status);
		return true;
	}
	public Boolean deleteOneOrder(Integer orderId) throws ClassNotFoundException, SQLException {
		String sql1 = "DELETE FROM  exoticre_order.orders  "
				+ "  WHERE order_id = "+orderId;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("delete One Order : "+status);
		return true;
	}
	public Boolean updateOrderStatus(Integer order_id,String status) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "status ='"+status+"'"
				+ "  WHERE order_id = "+order_id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order Status : "+status1);
		return true;
	}
	public Boolean updatePayment(Integer order_id,Boolean payment) throws ClassNotFoundException, SQLException {
		String payments = "0";
		if(payment == true){
			payments = "1";
		}
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "payment ='"+payments+"'"
				+ "  WHERE order_id = "+order_id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order payment : "+status1);
		return true;
	}
	public Boolean updateFishDie(Integer id,Boolean fishdie) throws ClassNotFoundException, SQLException {
		String fishdies = "0";
		if(fishdie == true){
			fishdies = "1";
		}
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "fishdie ='"+fishdies+"'"
				+ "  WHERE id = "+id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order fishdie : "+status1);
		return true;
	}
	public Boolean deleteOnceOrderCreditMemo(Integer order_id) throws ClassNotFoundException, SQLException {
		String sql1 = "DELETE FROM  exoticre_order.ordersCreditMemo  "
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("delete  order credit memo : "+order_id+"-"+status);
		return true;
	}
	public Integer addceOrderCreditMemo(List<ProductModel> lstProduct,Integer order_id) throws ClassNotFoundException, SQLException {
		String orderc= order_id+"-c";
		String ClientCustomerID= order_id+"-c";
		float allTotal = 0.00f;
		String allTotals = "0.00";
		for (ProductModel product : lstProduct) {
		    System.out.println(product.getTotal());
		    if(product.getFishdie() == true){
		    	allTotal = allTotal + Float.parseFloat(product.getTotal()); 
		    }
		}
		allTotals = String.format ("%.2f", allTotal);
		for (ProductModel product : lstProduct) {
		    System.out.println(product.getTotal());
		    if(product.getFishdie() == true){
		    	product.setAll_Total(Float.parseFloat(allTotals));
		    	product.setAll_Total_Memo(Float.parseFloat(allTotals));
		    String sql = "INSERT INTO exoticre_order.ordersCreditMemo("
					+ "order_id,"
					+ "order_idc,"
					+ "Product_id,"
					+ "Product_Sku,"
					+ "Size,"
					+ "Lot,"
					+ "Item,"
					+ "Price,"
					+ "Total,"
					+ "All_Total,"
					+ "surcharge,"
					+ "All_Total_Memo,"
					+ "ClientCustomerID)"
					+ "VALUES ('"
					+order_id+"','"
					+orderc+"','"
					+product.getProductId()+"','"
					+product.getSku()+"','"
					+product.getSize()+"','"
					+product.getLot()+"','"
					+product.getQty()+"','"
					+product.getPrice()+"','"
					+product.getTotal()+"','"
					+product.getAll_Total()+"','"
					+product.getSurcharge()+"','"
					+product.getAll_Total_Memo()+"','"
					+product.getClientCustomerID()+"')";     
			Statement stmt = (Statement) DBConnection.getConnection().createStatement();
			int status = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		    }
		}
		return 1;
	}
	public Boolean updateReadyPayment(Integer order_id,Boolean payment) throws ClassNotFoundException, SQLException {
		String payments = "0";
		if(payment == true){
			payments = "1";
		}
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "readyPayment ='"+payments+"'"
				+ "  WHERE order_id = "+order_id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order payment : "+status1);
		return true;
	}
	public Boolean updateIssued(Integer order_id,Boolean issued) throws ClassNotFoundException, SQLException, MalformedURLException, IOException {
		String issueds = "0";
		if(issued == true){
			issueds = "1";
			Integer isPetco  = getPetcoValidation(order_id);
			System.out.println("IS Petco : " +isPetco);
		/*	if(isPetco==1) {
				currentOrder = getOrderPrint(order_id);
				OrderModel currentOrders =  currentOrder.get(0);
				orderDetail = getOrderDetail(order_id);
		     PetcoInvoicing petcoUp= new PetcoInvoicing();
			petcoUp.UploadInvoice(orderDetail,currentOrders);
			}*/
		}
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "issued ='"+issueds+"'"
				+ "  WHERE order_id = "+order_id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order issued : "+status1);
		return true;
	}
	public Boolean updateEcommission(Integer id,String commission) throws ClassNotFoundException, SQLException {
		int commissions =Integer.parseInt(commission);
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "ExpressCommisson ='"+commissions+"'"
				+ "  WHERE id = "+id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order commissions : "+status1);
		return true;
	}
	
	public Integer updateOrderInfo(OrderInfoModel orderInfoModel,Integer order_id) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "Customer_ship ='"+orderInfoModel.getCustomer_ship()+"',"
				+ "awb =' "+orderInfoModel.getAwb()+"',"
				+ "fcb =' "+orderInfoModel.getFcb()+"',"
				+ "fcbw =' "+orderInfoModel.getFcbw()+"',"
				+ "tpacks =' "+orderInfoModel.getTpacks()+"',"
				+ "fob =' "+orderInfoModel.getFob()+"',"
				+ "rockb =' "+orderInfoModel.getRockb()+"',"
				+ "rockw =' "+orderInfoModel.getRockw()+"',"
				+ "totalp =' "+orderInfoModel.getTotalp()+"',"
				+ "ponumber =' "+orderInfoModel.getPonumber()+"',"
				+ "dfb =' "+orderInfoModel.getDfb()+"',"
				+ "dfbw =' "+orderInfoModel.getDfbw()+"',"
				+ "totalb =' "+orderInfoModel.getTotalb()+"'"
				+ "  WHERE order_id = "+order_id;       
		
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update product order Total : "+status);
		return status;
	}
	public Integer addOrder(InvoiceModel invoice,Integer order_id) throws ClassNotFoundException, SQLException {
		
		OrderInfoModel orderInfoModel = invoice.getOrderInfo();
		ProductModel productModel = invoice.getProduct();
		Date myDate = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(myDate);
		int commission = 1;
		if(productModel.getCommission() == false){
			commission = 0;
		}
		String sql = "INSERT INTO exoticre_order.orders("
				+ "notes,"
				+ "Airport,"
				+ "Customer_comments,"
				+ "Product_id,"
				+ "Date,"
				+ "Customer_date,"
				+ "Customer_email,"
				+ "saleperson_email,"
				+ "order_id,"
				+ "ClientCustomerID,"
				+ "Customer_ship,"
				+ "awb,"
				+ "fob,"
				+ "ponumber,"
				+ "fcb,"
				+ "fcbw,"
				+ "tpacks,"
				+ "rockb,"
				+ "rockw,"
				+ "totalp,"
				+ "dfb,"
				+ "dfbw,"
				+ "totalb,"
				+ "Product_Sku,"
				+ "Item,"
				+ "Size,"
				+ "Product_name,"
				+ "Lot,"
				+ "Addon,"
				+ "Price,"
				+ "disc,"
				+ "Total,"
				+ "commission,"
				+ "readyPayment,"
				+ "issued,"
				+ "All_Total)"
				+ "VALUES ('App Java','"
				+ "','"
				+  "','"
				+ "0','"
				+date+"','"
				+date+"','"
				+orderInfoModel.getCustomer_email()+"','"
				+orderInfoModel.getSaleEmail()+"','"
				+order_id+"','"
				+orderInfoModel.getClientCustomerID()+"','"
				+orderInfoModel.getCustomer_ship()+"','"
				+orderInfoModel.getAwb()+"','"
				+orderInfoModel.getFob()+"','"
				+orderInfoModel.getPonumber()+"','"
				+orderInfoModel.getFcb()+"','"
				+orderInfoModel.getFcbw()+"','"
				+orderInfoModel.getTpacks()+"','"
				+orderInfoModel.getRockb()+"','"
				+orderInfoModel.getRockw()+"','"
				+orderInfoModel.getTotalp()+"','"
				+orderInfoModel.getDfb()+"','"
				+orderInfoModel.getDfbw()+"','"
				+orderInfoModel.getTotalb()+"','"
				+productModel.getSku()+"','"
				+productModel.getQty()+"','"
				+productModel.getSize()+"','"
				+productModel.getName()+"','"
				+productModel.getLot()+"','"
				+productModel.getAddon()+"','"
				+productModel.getPrice()+"','"
				+productModel.getDisc()+"','"
				+productModel.getTotal()+"',"
				+commission+",'"
				+productModel.getReadyPayment()+"','"
				+productModel.getIssued()+"','"
				+productModel.getAll_Total()+"')";
		Statement stmt = (Statement) DBConnection.getConnection().createStatement();
		int status = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
        	status=rs.getInt(1);
        }
        rs.close();

        stmt.close();
		return status;

	}
	public Integer getLastNewOrderId() throws ClassNotFoundException, SQLException {
		Integer order_id = null;
		String sql = "SELECT  order_id  FROM exoticre_order.orders  order by order_id desc limit 1";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 order_id = rs.getInt("order_id") +1;
        }
		return order_id;
	}
	/************************************************************
	 * FROM PEDRO **************** PETCO ORDERS  ***************	
	 */
	
	public Integer addPetcoOrder(String order) throws ClassNotFoundException, SQLException {
		
	//	OrderInfoModel orderInfoModel = invoice.getOrderInfo();
	//	ProductModel productModel = invoice.getProduct();
		Date myDate = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(myDate);
	//	int commission = 1;
	//	if(productModel.getCommission() == false){
	//		commission = 0;
	//	}
	/**	String sql = "INSERT INTO exoticre_order.orders("
				+ "notes,"
				+ "Airport,"
				+ "Customer_comments,"
				+ "Product_id,"
				+ "Date,"
				+ "Customer_date,"
				+ "Customer_email,"
				+ "saleperson_email,"
				+ "order_id,"
				+ "ClientCustomerID,"
				+ "Customer_ship,"
				+ "awb,"
				+ "fob,"
				+ "ponumber,"
				+ "fcb,"
				+ "fcbw,"
				+ "tpacks,"
				+ "rockb,"
				+ "rockw,"
				+ "totalp,"
				+ "dfb,"
				+ "dfbw,"
				+ "totalb,"
				+ "Product_Sku,"
				+ "Item,"
				+ "Size,"
				+ "Product_name,"
				+ "Lot,"
				+ "Addon,"
				+ "Price,"
				+ "disc,"
				+ "Total,"
				+ "commission,"
				+ "readyPayment,"
				+ "issued,"
				+ "All_Total)"
				+ "VALUES ('App Java','"
				+ "','"
				+  "','"
				+ "0','"
				+date+"','"
				+date+"','"
				+orderInfoModel.getCustomer_email()+"','"
				+orderInfoModel.getSaleEmail()+"','"
				+order_id+"','"
				+orderInfoModel.getClientCustomerID()+"','"
				+orderInfoModel.getCustomer_ship()+"','"
				+orderInfoModel.getAwb()+"','"
				+orderInfoModel.getFob()+"','"
				+orderInfoModel.getPonumber()+"','"
				+orderInfoModel.getFcb()+"','"
				+orderInfoModel.getFcbw()+"','"
				+orderInfoModel.getTpacks()+"','"
				+orderInfoModel.getRockb()+"','"
				+orderInfoModel.getRockw()+"','"
				+orderInfoModel.getTotalp()+"','"
				+orderInfoModel.getDfb()+"','"
				+orderInfoModel.getDfbw()+"','"
				+orderInfoModel.getTotalb()+"','"
				+productModel.getSku()+"','"
				+productModel.getQty()+"','"
				+productModel.getSize()+"','"
				+productModel.getName()+"','"
				+productModel.getLot()+"','"
				+productModel.getAddon()+"','"
				+productModel.getPrice()+"','"
				+productModel.getDisc()+"','"
				+productModel.getTotal()+"',"
				+commission+",'"
				+productModel.getReadyPayment()+"','"
				+productModel.getIssued()+"','"
				+productModel.getAll_Total()+"')"; **/
		Statement stmt = (Statement) DBConnection.getConnection().createStatement();
		int status = stmt.executeUpdate(order,Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
        	status=rs.getInt(1);
        }
        rs.close();

        stmt.close();
		return status;

	}
	public Integer getPetcoValidation(Integer order_id) throws ClassNotFoundException, SQLException {
		String notes = null;
		Integer res = 0;
		String sql = "SELECT  notes  FROM exoticre_order.orders WHERE order_id='" + order_id + "'";
		 System.out.println(sql);
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 notes = rs.getString("notes");
        }
		 if(notes.equals("PETCO")) { res=1;}
		return res;
	}
	
}
