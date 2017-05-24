package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import application.DBConnection;
import application.Model.CustomerModel;
import application.Model.InvoiceModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.OrderModel;
import application.Model.ProductModel;

public class OrderDao {
	public List<OrderModel> getOrderCustomerSale() throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_order.customerfishpro t1 inner join exoticre_order.orders t2 on t1.CustomerID = t2.ClientCustomerID WHERE t1.CompanyName != '' and t2.amoutPaid != t2.All_Total  and t2.status != 'COMPLETED' GROUP BY t2.order_id order by t1.CompanyName asc limit 20000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Customer_date");
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
	public List<OrderModel> getOrderCustomerSale(String Customerid) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT * FROM exoticre_order.customerfishpro t1 inner join exoticre_order.orders t2 on t1.CustomerID = t2.ClientCustomerID WHERE t1.CompanyName != '' and t2.amoutPaid != t2.All_Total  and t2.status != 'COMPLETED' and t2.amoutPaid = '0.00' and t2.ClientCustomerID = '"+Customerid+"' GROUP BY t2.order_id order by t2.Date desc limit 20000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			String email = rs.getString("Email");
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Customer_date");
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
			order.setCustomer_email(email);
			lstOrder.add(order);
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
			String Customer_date =  rs.getString("Customer_date");
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
			String Customer_date =  rs.getString("Customer_date");
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
		while (rs.next()) {
			String email = rs.getString("Email");
			String customerId = rs.getString("CustomerID");
			String customerName = rs.getString("CompanyName");
			String customerContact = rs.getString("Contact");
			String customerPhone = rs.getString("Phone1");
			String customerTerms = rs.getString("Terms");
			String customerSalesperson = rs.getString("Salesperson");
			Integer order_id = rs.getInt("order_id");
			String Customer_date =  rs.getString("Customer_date");
			Float All_Total = rs.getFloat("All_Total");
			String All_Totals = String.format ("%.2f", All_Total);
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
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments);
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
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments);
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
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments);
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
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.issued) = LOWER('1') AND t1.isExpress ='"+status1+"' GROUP BY t1.order_id order by t1.order_id desc limit 1000";
		if(in_status.endsWith("Both")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.issued) = LOWER('1') GROUP BY t1.order_id order by t1.order_id desc limit 1000";
		}
		//String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes  FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.status) = LOWER('"+in_status+"') GROUP BY t1.order_id order by t1.order_id desc limit 200";

		if(notes.equals("App Java")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes     FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.issued) = LOWER('0')   and t1.payment = '0' GROUP BY  t1.order_id order by t1.order_id desc limit 1000";
		  //sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes     FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.status) = LOWER('"+in_status+"') and t1.notes = '"+notes+"' GROUP BY  t1.order_id order by t1.order_id desc limit 200";
		}
		if(notes.equals("Express")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes     FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.isExpress) = LOWER('1')  GROUP BY  t1.order_id order by t1.order_id desc limit 1000";
		}
		if(notes.equals("ExpressStore")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment ,t1.issued,t1.paymentMethod ,t1.amoutPaid,t1.notes     FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.isExpress) = LOWER('1') and t1.ClientCustomerID = '"+in_status+"' GROUP BY  t1.order_id order by t1.order_id desc limit 1000";
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
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments);
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
            Float amoutPaid = rs.getFloat("amoutPaid");
            
            Boolean payments = false;
            if(payment.equals("1")){
           	 payments = true;
            }else{
           	 payments = false;
            }
            OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments);
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
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment,t1.paymentMethod ,t1.amoutPaid    FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE readyPayment = '1' and  LOWER(t1.ClientCustomerID) = LOWER('"+CustomerId+"')  GROUP BY  t1.order_id order by t1.order_id desc limit 500";
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
            Float amoutPaid = rs.getFloat("amoutPaid");
            
            Boolean payments = false;
            if(payment.equals("1")){
           	 payments = true;
            }else{
           	 payments = false;
            }
            OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments);
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
            Float amoutPaid = rs.getFloat("amoutPaid");
            
            Boolean payments = false;
            if(payment.equals("1")){
           	 payments = true;
            }else{
           	 payments = false;
            }
            OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Totals,surcharge,payments);
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
	public Boolean updateIssued(Integer order_id,Boolean issued) throws ClassNotFoundException, SQLException {
		String issueds = "0";
		if(issued == true){
			issueds = "1";
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
}
