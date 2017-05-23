package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.DBConnection;
import application.Model.SaleModel;
import application.Model.SalesModel;

public class SalesDao {

	public List<SalesModel> getSale(String fromDate,String toDate,String type) throws ClassNotFoundException, SQLException{
		List<SalesModel> lstSale = new ArrayList<>();
		String status1 = "";
		if(type.endsWith("Express")){
			status1 = "1";
		}
		if(type.endsWith("Exotic")){
			status1 = "0";
		}
		String sql = "SELECT t1.*,t2.Tax FROM orders t1 left join customerfishpro t2 on t1.ClientCustomerID = t2.CustomerID WHERE Customer_date>='"+fromDate+"' AND Customer_date<='"+toDate+"'  AND isExpress ='"+status1+"' GROUP BY order_id ORDER BY Customer_date";
		if(type.endsWith("Both")){
			 sql = "SELECT t1.*,t2.Tax FROM orders t1 left join customerfishpro t2 on t1.ClientCustomerID = t2.CustomerID WHERE Customer_date>='"+fromDate+"' AND Customer_date<='"+toDate+"'   GROUP BY order_id ORDER BY Customer_date";
		}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		List<SalesModel> lst = new ArrayList<>();
		 while (rs.next()) {
			 SalesModel salesModel = new SalesModel();
             String Customer_date = rs.getString("Customer_date");
            // String Day = "";
             String total = rs.getString("All_Total");
             String status = rs.getString("status");
             String paymentMethod = rs.getString("paymentMethod");
             Float amoutPaid = rs.getFloat("amoutPaid");
             String amountPaids = String.format ("%,.2f",amoutPaid);
             String order_id = rs.getString("order_id");
             String issued = rs.getString("issued");
             int  Tax = rs.getInt("Tax");
			 int  Disc = rs.getInt("disc");
			 String Taxs = String.format ("%,.2f",(Tax*Float.parseFloat(total)/100));
			 String Discs = String.format ("%,.2f",(Disc*Float.parseFloat(total)/100));
			 String NetSales = String.format ("%,.2f",Float.parseFloat(total) -((Disc+Tax)*Float.parseFloat(total)/100));
			// System.out.println(Taxs+" --  "+Discs);
             salesModel.setCustomer_date(Customer_date);
             int leng  = Customer_date.length();
             int index1= Customer_date.indexOf(".");
             String Day = "";
             if(index1 == -1){
	             if(leng ==10){
	            	 Day = LOCAL_DATE(Customer_date).getDayOfWeek().toString();
	             }else{
	            	 Day = LOCAL_DATES(Customer_date).getDayOfWeek().toString();
	             }
             }else{
            	 Day = LOCAL_DATEDOT(Customer_date).getDayOfWeek().toString();
             }
             salesModel.setTotal_discount(Discs);
             salesModel.setTotal_tax(Taxs);
             salesModel.setTotal_net_sales(NetSales);
             salesModel.setIssued(issued);
             salesModel.setPaymentMethod(paymentMethod);
             salesModel.setStatus(status);
             salesModel.setDay(Day);
             salesModel.setTotal_pending("0.00");
             salesModel.setTotal_complete("0.00");
             salesModel.setTotal_sales("0.00");
             if(issued.toLowerCase().equals("1")){
            	 salesModel.setTotal_sales(total);
             }
             if(!paymentMethod.toLowerCase().equals("")){
            	 salesModel.setTotal_complete(amountPaids);
             }
             if(paymentMethod.toLowerCase().equals("")){
            	 salesModel.setTotal_pending(total);
             }
           //  System.out.println(order_id);

             lst.add(salesModel);
         }
		 for (SalesModel salesModel : lst) {
				if(!checkDate(lstSale,salesModel.getCustomer_date())){
					lstSale.add(getTotalSale(lst,salesModel.getCustomer_date()));
				}
		 }
		return lstSale;
	}
	public List<SalesModel> getSales(String fromDate,String toDate,String type) throws ClassNotFoundException, SQLException{
		List<SalesModel> lstSale = new ArrayList<>();
		List<SaleModel> lstS = new ArrayList<>();
		List<SaleModel> lstS1 = new ArrayList<>();

		String sqlSale = "SELECT * FROM salesperson ;";
		ResultSet rsSale = DBConnection.getConnection().createStatement().executeQuery(sqlSale);
		 while (rsSale.next()) {
			 SaleModel saleModel = new SaleModel();
			 String scode = rsSale.getString("scode");
			 String email = rsSale.getString("email");
			 saleModel.setScode(scode);
			 saleModel.setEmail(email);
			 lstS.add(saleModel);
		 }
			String status1 = "";
			if(type.endsWith("Express")){
				status1 = "1";
			}
			if(type.endsWith("Exotic")){
				status1 = "0";
			}
			String sql = "SELECT * FROM orders WHERE Customer_date>='"+fromDate+"' AND Customer_date<='"+toDate+"'   AND isExpress ='"+status1+"' GROUP BY saleperson_email ORDER BY saleperson_email";
			if(type.endsWith("Both")){
				 sql = "SELECT * FROM orders WHERE Customer_date>='"+fromDate+"' AND Customer_date<='"+toDate+"'   GROUP BY saleperson_email ORDER BY saleperson_email";
			}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		List<String> lst = new ArrayList<>();
		 while (rs.next()) {
			 String saleperson_email = rs.getString("saleperson_email");
			 if(saleperson_email.equals("")){
				 saleperson_email = "franciscos@exoticreefimports.com";
			 }else{
				 SaleModel saleModel = new SaleModel();
				 saleModel.setScode("");
				 saleModel.setEmail(saleperson_email);
				 for (SaleModel saleModele : lstS) {
					 if(saleModele.getEmail().equals(saleperson_email)){
						 saleModel.setScode(saleModele.getScode());
					 }
				 }
				 if(saleModel.getScode().equals("")){
					 
				 }else {
					 lstS1.add(saleModel);
				 }
			 }

         }
		 for (SaleModel saleModele : lstS1) {
			 SalesModel salesModel = new SalesModel();
			 salesModel.setInvCusName("SALESPERSON COMMISSION REPORT FOR CODE - "+saleModele.getScode());
			 lstSale.add(salesModel);
			 	String sql1 = "SELECT t1.* , t2.CompanyName,t2.Tax FROM orders t1 left join customerfishpro t2 on t1.ClientCustomerID = t2.CustomerID WHERE  saleperson_email ='"+saleModele.getEmail()+"' AND Customer_date>='"+fromDate+"' AND Customer_date<='"+toDate+"'   AND isExpress ='"+status1+"' GROUP BY order_id ORDER BY Customer_date";
				if(type.endsWith("Both")){
				 	sql1 = "SELECT t1.* , t2.CompanyName,t2.Tax FROM orders t1 left join customerfishpro t2 on t1.ClientCustomerID = t2.CustomerID WHERE  saleperson_email ='"+saleModele.getEmail()+"' AND Customer_date>='"+fromDate+"' AND Customer_date<='"+toDate+"'   GROUP BY order_id ORDER BY Customer_date";
				}
			 ResultSet rs1 = DBConnection.getConnection().createStatement().executeQuery(sql1);
			 Float total =(float) 0.00;
			 Float totalNon =(float) 0.00;
			 while (rs1.next()) {
				 SalesModel salesModels = new SalesModel();
				 String order_id = rs1.getString("order_id");
				 String ClientCustomerID = rs1.getString("ClientCustomerID");
				 String Customer_date = rs1.getString("Customer_date");
				 String CustomerName = rs1.getString("CompanyName");
				 String All_Total = rs1.getString("All_Total");
				 int  Tax = rs1.getInt("Tax");
				 int  Disc = rs1.getInt("disc");
				 System.out.println(Tax+" --  "+Disc);
				 Float totalNonNode =(float) 0.00;
				 totalNonNode = Float.parseFloat(All_Total);
				 salesModels.setInv(order_id);
				 salesModels.setIntDate(Customer_date);
				 salesModels.setInvCusName(ClientCustomerID+"-"+CustomerName);
				 salesModels.setInvTotal("$"+String.format ("%,.2f",Float.parseFloat(All_Total)));
				 total = total + Float.parseFloat(All_Total);
				 totalNon = totalNon + Float.parseFloat(All_Total);
				 //getAllNonCommisionables
				 
				 lstSale.add(salesModels);
				 String sqlnon = "SELECT t1.* FROM orders t1 left join products t2 on t1.Product_Sku = t2.sku  where  t2.grxp ='NOC' and t1.order_id = "+order_id+";";
				 ResultSet rsNon = DBConnection.getConnection().createStatement().executeQuery(sqlnon);
				 while (rsNon.next()) {
					 SalesModel salesModelNon = new SalesModel();
					 String productSku = rsNon.getString("Product_Sku");
					 String Product_name = rsNon.getString("Product_name");
					 String Total = rsNon.getString("Total");
					 salesModelNon.setInvCusName(productSku+"-"+Product_name);
					 salesModelNon.setInvNon("-$"+String.format ("%,.2f",Float.parseFloat(Total)));
					 totalNon = totalNon - Float.parseFloat(Total);
					 totalNonNode = totalNonNode - Float.parseFloat(Total);
					 lstSale.add(salesModelNon);
				 }
				 SalesModel salesModelNon = new SalesModel();
				 salesModelNon.setInvNon("$"+String.format ("%,.2f",totalNonNode));
				 lstSale.add(salesModelNon);
			 }
			 SalesModel salesModelt = new SalesModel();
			 String ttotal = String.format ("%,.2f",total);
			 String ttotalNon = String.format ("%,.2f",totalNon);
			 salesModelt.setInvTotal("$"+ttotal);
			 salesModelt.setInvNon("$"+ttotalNon);

			 lstSale.add(salesModelt);
		 }
		return lstSale;
	}
	public boolean checkDate(List<SalesModel> lstSale,String date){
		boolean chk = false;
		for (SalesModel salesModel : lstSale) {
			if(salesModel.getCustomer_date().equals(date)){
				chk = true;
			}
		}
		return chk;
	}
	public SalesModel getTotalSale(List<SalesModel> lst,String date){
		SalesModel salesModels = new SalesModel();
		salesModels.setTotal_pending("0.00");
		salesModels.setTotal_complete("0.00");
		salesModels.setTotal_sales("0.00");
		salesModels.setTotal_discount("0.00");
		salesModels.setTotal_tax("0.00");
		salesModels.setTotal_net_sales("0.00");
		for (SalesModel salesModel : lst) {
			if(salesModel.getCustomer_date().equals(date)){
				salesModels.setCustomer_date(salesModel.getCustomer_date());
				salesModels.setDay(salesModel.getDay());
				String paymentMethod = salesModel.getPaymentMethod();
				String issued = salesModel.getIssued();
				if(issued.toLowerCase().equals("1")){
					 String sub = salesModels.getTotal_sales();
					 Float total = Float.parseFloat(sub);
					 Float totals = Float.parseFloat(salesModel.getTotal_sales());
					 Float totals1 = total + totals;
					 salesModels.setTotal_sales( String.format ("%.2f",totals1));
					 
					 String subd = salesModels.getTotal_discount();
					 Float totald = Float.parseFloat(subd);
					 Float totalsd = Float.parseFloat(salesModel.getTotal_discount());
					 Float totals1d = totald + totalsd;
					 salesModels.setTotal_discount( String.format ("%.2f",totals1d));
					 
					 String subt = salesModels.getTotal_tax();
					 Float totalt = Float.parseFloat(subd);
					 Float totalst = Float.parseFloat(salesModel.getTotal_tax());
					 Float totals1t = totalt + totalst;
					 salesModels.setTotal_discount( String.format ("%.2f",totals1t));
					 
					 String subn = salesModels.getTotal_net_sales();
					 Float totaln = Float.parseFloat(subd);
					 String totalsns = salesModel.getTotal_net_sales().replace(",", "");
					 Float totalsn = Float.parseFloat(totalsns);
					 Float totals1n = totaln + totalsn;
					 salesModels.setTotal_net_sales( String.format ("%.2f",totals1n));
					 
	             }
				 if(!paymentMethod.toLowerCase().equals("")){
					 String sub = salesModels.getTotal_complete();
					 Float total = Float.parseFloat(sub);
					 Float totals = Float.parseFloat(salesModel.getTotal_complete());
					 Float totals1 = total + totals;
					 salesModels.setTotal_complete( String.format ("%.2f",totals1));
	             }
	             if(paymentMethod.toLowerCase().equals("")){
	            	 String sub = salesModels.getTotal_pending();
					 Float total = Float.parseFloat(sub);
					 Float totals = Float.parseFloat(salesModel.getTotal_pending());
					 Float totals1 = total + totals;
					 salesModels.setTotal_pending( String.format ("%.2f",totals1));
	             }	
			 }	
		}
		return salesModels;
	}
	public static final LocalDate LOCAL_DATE (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
	public static final LocalDate LOCAL_DATEDOT (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
	public static final LocalDate LOCAL_DATES (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
}
