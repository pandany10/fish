package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.DBConnection;
import application.Model.CustomerModel;

public class CustomerDao {
	public CustomerModel getCustomerByOrderId(Integer order_id) throws ClassNotFoundException, SQLException {
		CustomerModel Customer = new CustomerModel();
		String sql ="SELECT * FROM exoticre_order.orders t1 inner join exoticre_order.customerfishpro t2 on t1.ClientCustomerID = t2.CustomerID where t1.order_id =  "+order_id+" group by t1.order_id";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 Customer.setCustomerID(rs.getString("CustomerID"));
			 Customer.setCompanyName(rs.getString("CompanyName"));
			 Customer.setAddress(rs.getString("Address"));
			 Customer.setAddress2(rs.getString("Address2"));
			 Customer.setCity(rs.getString("City"));
			 Customer.setStates(rs.getString("States"));
			 Customer.setZip(rs.getString("Zip"));
			 Customer.setPhone1(rs.getString("Phone1"));
			 Customer.setCountry(rs.getString("Country"));
			 Customer.setSalesperson(rs.getString("Salesperson"));
			 Customer.setCarrier(rs.getString("carrier"));
			 Customer.setEmail(rs.getString("Customer_email"));
			 Customer.setStripe_user_id(rs.getString("stripe_user_id"));
			 Customer.setSaleEmail(rs.getString("saleperson_email"));
			 //stripe_user_id
			 String sl =rs.getString("SalesDisc");
			 Customer.setShippingCost(rs.getFloat("ShippingCost"));
			 if(sl.equals("") || sl == null){
				 sl = "0"; 
			 }
			 Customer.setSalesDisc(sl);
         }
		return Customer;
	}
	public CustomerModel getCustomerByCusId(String cusId) throws ClassNotFoundException, SQLException {
		CustomerModel Customer = new CustomerModel();
		String sql ="SELECT t1.* , t2.email as emailsale FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	CustomerID =  '"+cusId+"' ";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 Customer.setCustomerID(rs.getString("CustomerID"));
			 Customer.setCompanyName(rs.getString("CompanyName"));
			 Customer.setAddress(rs.getString("Address"));
			 Customer.setAddress2(rs.getString("Address2"));
			 Customer.setCity(rs.getString("City"));
			 Customer.setStates(rs.getString("States"));
			 Customer.setZip(rs.getString("Zip"));
			 Customer.setPhone1(rs.getString("Phone1"));
			 Customer.setTerms(rs.getString("Terms"));
			 Customer.setCarrier(rs.getString("carrier"));
			 Customer.setSalesperson(rs.getString("Salesperson"));
			 Customer.setEmail(rs.getString("Email"));
			 Customer.setSaleEmail(rs.getString("emailsale"));
			 Customer.setShippingCost(rs.getFloat("ShippingCost"));
			 System.out.println(rs.getString("emailsale"));
			 System.out.println(Customer.getSaleEmail());
			 String sl =rs.getString("SalesDisc");
			 if(sl.equals("") || sl == null){
				 sl = "0"; 
			 }
			 Customer.setSalesDisc(sl);
         }
		return Customer;
	}
	public boolean chkCustome(String key) throws ClassNotFoundException, SQLException {
		boolean vali = false;
		//String key = cusId.toString();
		String sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	CustomerID =  '"+key+"' ";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		
		 while (rs.next()) {
			 System.out.println("----"+rs.getString("CustomerID"));
			 vali = true;
         }
		return vali;
	}
	public CustomerModel getCustomerSearch(String key, String type) throws ClassNotFoundException, SQLException {
		CustomerModel Customer = new CustomerModel();
		final String[] filter = new String[] { "ID", "Email", "Name", "Contact", "Phone", "City" };
		String sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	CustomerID =  '"+key+"' ";
		if(type.equals(filter[1])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Email) like  LOWER('%"+key+"%')  limit 1";
		}
		if(type.equals(filter[2])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.CompanyName) like  LOWER('%"+key+"%') limit 1";
		}
		if(type.equals(filter[3])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Contact) like  LOWER('%"+key+"%') limit 1";
		}
		if(type.equals(filter[4])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Phone1) like  LOWER('%"+key+"%') limit 1";
		}
		if(type.equals(filter[5])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.City) like  LOWER('%"+key+"%') limit 1";
		}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 Customer.setCustomerID(rs.getString("CustomerID"));
			 Customer.setCompanyName(rs.getString("CompanyName"));
			 Customer.setAddress(rs.getString("Address"));
			 Customer.setAddress2(rs.getString("Address2"));
			 Customer.setCity(rs.getString("City"));
			 Customer.setStates(rs.getString("States"));
			 Customer.setZip(rs.getString("Zip"));
			 Customer.setPhone1(rs.getString("Phone1"));
			 String Terms =rs.getString("Terms");
			 if(Terms.equals("null")){
				 Terms = ""; 
			 }
			 Customer.setTerms(Terms);
			 Customer.setCarrier(rs.getString("carrier"));
			 Customer.setSalesperson(rs.getString("Salesperson"));
			 Customer.setEmail(rs.getString("Email"));
			 Customer.setSaleEmail(rs.getString("email"));
			 Customer.setShippingCost(rs.getFloat("ShippingCost"));
			 String sl =rs.getString("SalesDisc");
			 if(sl.equals("") || sl == null){
				 sl = "0"; 
			 }
			 Customer.setSalesDisc(sl);
			 
			 Customer.setCountry(rs.getString("Country"));
			 //Customer.setCountry(rs.getString("Phone1"));
			 Customer.setPhone2(rs.getString("Phone2"));
			 Customer.setCellphone(rs.getString("Cellphone"));
			 Customer.setFax(rs.getString("FaxNo"));
			 Customer.setCountry(rs.getString("Country"));
			// Customer.setCountry(rs.getString("Country"));
			 Customer.setDestination(rs.getString("Destin"));
			 Customer.setAlrportCode(rs.getString("alrportcode"));
			 Customer.setTime(rs.getString("times"));
			 Customer.setContact(rs.getString("Contact"));
			 Customer.setContact2(rs.getString("contact2"));
			 Customer.setEMail(rs.getString("Email"));
			 Customer.setEMail2(rs.getString("email2"));
			 Customer.setTitle(rs.getString("Title"));
			 Customer.setTitle2(rs.getString("title2"));
			 Customer.setWebsite(rs.getString("Website"));
			 Customer.setComments(rs.getString("Comments"));
			 Float Balance =rs.getFloat("Balance");
			 if(Balance == null){
				 Balance = (float) 0; 
			 }
			 Float Credit =rs.getFloat("Credit");
			 if(Credit == null){
				 Credit = (float) 0; 
			 }
			 Customer.setCurrBal(Balance);
			 Customer.setOpnCred(Credit);
			 Customer.setYTDSales(rs.getFloat("YTDSales"));
			 Customer.setLstSales1(rs.getFloat("LstSale"));
			 Customer.setLstPmt1(rs.getFloat("LstPmt"));
			 Customer.setEntered(rs.getString("Entered"));
			 Customer.setLstSales2(rs.getString("LstSaleDate"));
			 Customer.setLstPmt2(rs.getString("LstPmtDate"));
			 //Customer.setCountry(rs.getString("Terms"));
			 Customer.setNetDue(rs.getString("NetDue"));
			 Customer.setPrice(rs.getString("Price"));
			 Customer.setCreditLimit(rs.getFloat("CreditLimit"));
			 Customer.setSalesDisc(rs.getString("SalesDisc"));
			 Customer.setTax(rs.getInt("Tax"));
         }
		return Customer;
	}
	public List<CustomerModel> getLstCustomerSearchc(String key, String type) throws ClassNotFoundException, SQLException {
		List<CustomerModel> LstCustomer = new ArrayList<>();
		final String[] filter = new String[] { "ID", "Email", "Name", "Contact", "Phone", "City" };
		String sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.CustomerID) like  LOWER('%"+key+"%')  limit 1000 ";
		if(type.equals(filter[1])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Email) like  LOWER('%"+key+"%')  limit 1000";
		}
		if(type.equals(filter[2])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.CompanyName) like  LOWER('%"+key+"%') limit 1000";
		}
		if(type.equals(filter[3])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Contact) like  LOWER('%"+key+"%') limit 1000";
		}
		if(type.equals(filter[4])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Phone1) like  LOWER('%"+key+"%') limit 1000";
		}
		if(type.equals(filter[5])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.City) like  LOWER('%"+key+"%') limit 1000";
		}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 CustomerModel Customer = new CustomerModel();
			 Customer.setCustomerID(rs.getString("CustomerID"));
			 Customer.setCompanyName(rs.getString("CompanyName"));
			 Customer.setAddress(rs.getString("Address"));
			 Customer.setAddress2(rs.getString("Address2"));
			 Customer.setCity(rs.getString("City"));
			 Customer.setStates(rs.getString("States"));
			 Customer.setZip(rs.getString("Zip"));
			 Customer.setPhone1(rs.getString("Phone1"));
			 String Terms =rs.getString("Terms");
			 if(Terms != null){
				 if(Terms.equals("null")){
					 Terms = ""; 
				 }
			 }
			 Customer.setTerms(Terms);
			 Customer.setCarrier(rs.getString("carrier"));
			 Customer.setSalesperson(rs.getString("Salesperson"));
			 Customer.setEmail(rs.getString("Email"));
			 Customer.setSaleEmail(rs.getString("email"));
			 Customer.setShippingCost(rs.getFloat("ShippingCost"));
			 String sl =rs.getString("SalesDisc");
			 if(sl != null){
				 if(sl.equals("") || sl == null){
					 sl = "0"; 
				 }
			 }
			 Customer.setSalesDisc(sl);
			 
			 Customer.setCountry(rs.getString("Country"));
			 //Customer.setCountry(rs.getString("Phone1"));
			 Customer.setPhone2(rs.getString("Phone2"));
			 Customer.setCellphone(rs.getString("Cellphone"));
			 Customer.setFax(rs.getString("FaxNo"));
			 Customer.setCountry(rs.getString("Country"));
			// Customer.setCountry(rs.getString("Country"));
			 Customer.setDestination(rs.getString("Destin"));
			 Customer.setAlrportCode(rs.getString("alrportcode"));
			 Customer.setTime(rs.getString("times"));
			 Customer.setContact(rs.getString("Contact"));
			 Customer.setContact2(rs.getString("contact2"));
			 Customer.setEMail(rs.getString("Email"));
			 Customer.setEMail2(rs.getString("email2"));
			 Customer.setTitle(rs.getString("Title"));
			 Customer.setTitle2(rs.getString("title2"));
			 Customer.setWebsite(rs.getString("Website"));
			 Customer.setComments(rs.getString("Comments"));
			 Float Balance =rs.getFloat("Balance");
			 if(Balance == null){
				 Balance = (float) 0; 
			 }
			 Float Credit =rs.getFloat("Credit");
			 if(Credit == null){
				 Credit = (float) 0; 
			 }
			 Customer.setCurrBal(Balance);
			 Customer.setOpnCred(Credit);
			 Customer.setYTDSales(rs.getFloat("YTDSales"));
			 Customer.setLstSales1(rs.getFloat("LstSale"));
			 Customer.setLstPmt1(rs.getFloat("LstPmt"));
			 Customer.setEntered(rs.getString("Entered"));
			 Customer.setLstSales2(rs.getString("LstSaleDate"));
			 Customer.setLstPmt2(rs.getString("LstPmtDate"));
			 //Customer.setCountry(rs.getString("Terms"));
			 Customer.setNetDue(rs.getString("NetDue"));
			 Customer.setPrice(rs.getString("Price"));
			 Customer.setCreditLimit(rs.getFloat("CreditLimit"));
			 Customer.setSalesDisc(rs.getString("SalesDisc"));
			 Customer.setTax(rs.getInt("Tax"));
             LstCustomer.add(Customer);
         }
		return LstCustomer;
	}
	public List<CustomerModel> getLstCustomerForExpress() throws ClassNotFoundException, SQLException {
		List<CustomerModel> LstCustomer = new ArrayList<>();
		String sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode left join exoticre_order.orders t3 on t1.CustomerID = t3.ClientCustomerID  where  t3.isExpress = '1' group by t1.CustomerID";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 CustomerModel Customer = new CustomerModel();
			 Customer.setCustomerID(rs.getString("CustomerID"));
			 Customer.setCompanyName(rs.getString("CompanyName"));
			 Customer.setAddress(rs.getString("Address"));
			 Customer.setAddress2(rs.getString("Address2"));
			 Customer.setCity(rs.getString("City"));
			 Customer.setStates(rs.getString("States"));
			 Customer.setZip(rs.getString("Zip"));
			 Customer.setPhone1(rs.getString("Phone1"));
			 String Terms =rs.getString("Terms");
			 if(Terms.equals("null")){
				 Terms = ""; 
			 }
			 Customer.setTerms(Terms);
			 Customer.setCarrier(rs.getString("carrier"));
			 Customer.setSalesperson(rs.getString("Salesperson"));
			 Customer.setEmail(rs.getString("Email"));
			 Customer.setSaleEmail(rs.getString("email"));
			 Customer.setShippingCost(rs.getFloat("ShippingCost"));
			 String sl =rs.getString("SalesDisc");
			 if(sl.equals("") || sl == null){
				 sl = "0"; 
			 }
			 Customer.setSalesDisc(sl);
			 
			 Customer.setCountry(rs.getString("Country"));
			 //Customer.setCountry(rs.getString("Phone1"));
			 Customer.setPhone2(rs.getString("Phone2"));
			 Customer.setCellphone(rs.getString("Cellphone"));
			 Customer.setFax(rs.getString("FaxNo"));
			 Customer.setCountry(rs.getString("Country"));
			// Customer.setCountry(rs.getString("Country"));
			 Customer.setDestination(rs.getString("Destin"));
			 Customer.setAlrportCode(rs.getString("alrportcode"));
			 Customer.setTime(rs.getString("times"));
			 Customer.setContact(rs.getString("Contact"));
			 Customer.setContact2(rs.getString("contact2"));
			 Customer.setEMail(rs.getString("Email"));
			 Customer.setEMail2(rs.getString("email2"));
			 Customer.setTitle(rs.getString("Title"));
			 Customer.setTitle2(rs.getString("title2"));
			 Customer.setWebsite(rs.getString("Website"));
			 Customer.setComments(rs.getString("Comments"));
			 Float Balance =rs.getFloat("Balance");
			 if(Balance == null){
				 Balance = (float) 0; 
			 }
			 Float Credit =rs.getFloat("Credit");
			 if(Credit == null){
				 Credit = (float) 0; 
			 }
			 Customer.setCurrBal(Balance);
			 Customer.setOpnCred(Credit);
			 Customer.setYTDSales(rs.getFloat("YTDSales"));
			 Customer.setLstSales1(rs.getFloat("LstSale"));
			 Customer.setLstPmt1(rs.getFloat("LstPmt"));
			 Customer.setEntered(rs.getString("Entered"));
			 Customer.setLstSales2(rs.getString("LstSaleDate"));
			 Customer.setLstPmt2(rs.getString("LstPmtDate"));
			 //Customer.setCountry(rs.getString("Terms"));
			 Customer.setNetDue(rs.getString("NetDue"));
			 Customer.setPrice(rs.getString("Price"));
			 Customer.setCreditLimit(rs.getFloat("CreditLimit"));
			 Customer.setSalesDisc(rs.getString("SalesDisc"));
			 Customer.setTax(rs.getInt("Tax"));
            LstCustomer.add(Customer);
        }
		return LstCustomer;
	}
	public List<CustomerModel> getLstCustomerSearch(String key, String type) throws ClassNotFoundException, SQLException {
		List<CustomerModel> LstCustomer = new ArrayList<>();
		final String[] filter = new String[] { "ID", "Email", "Name", "Contact", "Phone", "City" };
		String sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	CustomerID =  '"+key+"' ";
		if(type.equals(filter[1])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Email) like  LOWER('%"+key+"%')  limit 50";
		}
		if(type.equals(filter[2])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.CompanyName) like  LOWER('%"+key+"%') limit 50";
		}
		if(type.equals(filter[3])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Contact) like  LOWER('%"+key+"%') limit 50";
		}
		if(type.equals(filter[4])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.Phone1) like  LOWER('%"+key+"%') limit 50";
		}
		if(type.equals(filter[5])){
			sql ="SELECT t1.* , t2.* FROM  exoticre_order.customerfishpro t1 left join exoticre_order.salesperson t2 on t1.Salesperson = t2.scode  where 	LOWER(t1.City) like  LOWER('%"+key+"%') limit 100";
		}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 CustomerModel Customer = new CustomerModel();
			 Customer.setCustomerID(rs.getString("CustomerID"));
			 Customer.setCompanyName(rs.getString("CompanyName"));
			 Customer.setAddress(rs.getString("Address"));
			 Customer.setAddress2(rs.getString("Address2"));
			 Customer.setCity(rs.getString("City"));
			 Customer.setStates(rs.getString("States"));
			 Customer.setZip(rs.getString("Zip"));
			 Customer.setPhone1(rs.getString("Phone1"));
			 String Terms =rs.getString("Terms");
			// if(Terms.equals("null")){
			//	 Terms = ""; 
			// }
			 Customer.setTerms(Terms);
			 Customer.setCarrier(rs.getString("carrier"));
			 Customer.setSalesperson(rs.getString("Salesperson"));
			 Customer.setEmail(rs.getString("Email"));
			 Customer.setSaleEmail(rs.getString("email"));
			 Customer.setShippingCost(rs.getFloat("ShippingCost"));
			 String sl =rs.getString("SalesDisc");
			 if(sl.equals("") || sl == null){
				 sl = "0"; 
			 }
			 Customer.setSalesDisc(sl);
			 
			 Customer.setCountry(rs.getString("Country"));
			 //Customer.setCountry(rs.getString("Phone1"));
			 Customer.setPhone2(rs.getString("Phone2"));
			 Customer.setCellphone(rs.getString("Cellphone"));
			 Customer.setFax(rs.getString("FaxNo"));
			 Customer.setCountry(rs.getString("Country"));
			// Customer.setCountry(rs.getString("Country"));
			 Customer.setDestination(rs.getString("Destin"));
			 Customer.setAlrportCode(rs.getString("alrportcode"));
			 Customer.setTime(rs.getString("times"));
			 Customer.setContact(rs.getString("Contact"));
			 Customer.setContact2(rs.getString("contact2"));
			 Customer.setEMail(rs.getString("Email"));
			 Customer.setEMail2(rs.getString("email2"));
			 Customer.setTitle(rs.getString("Title"));
			 Customer.setTitle2(rs.getString("title2"));
			 Customer.setWebsite(rs.getString("Website"));
			 Customer.setComments(rs.getString("Comments"));
			 Float Balance =rs.getFloat("Balance");
			 if(Balance == null){
				 Balance = (float) 0; 
			 }
			 Float Credit =rs.getFloat("Credit");
			 if(Credit == null){
				 Credit = (float) 0; 
			 }
			 Customer.setCurrBal(Balance);
			 Customer.setOpnCred(Credit);
			 Customer.setYTDSales(rs.getFloat("YTDSales"));
			 Customer.setLstSales1(rs.getFloat("LstSale"));
			 Customer.setLstPmt1(rs.getFloat("LstPmt"));
			 Customer.setEntered(rs.getString("Entered"));
			 Customer.setLstSales2(rs.getString("LstSaleDate"));
			 Customer.setLstPmt2(rs.getString("LstPmtDate"));
			 //Customer.setCountry(rs.getString("Terms"));
			 Customer.setNetDue(rs.getString("NetDue"));
			 Customer.setPrice(rs.getString("Price"));
			 Customer.setCreditLimit(rs.getFloat("CreditLimit"));
			 Customer.setSalesDisc(rs.getString("SalesDisc"));
			 Customer.setTax(rs.getInt("Tax"));
             LstCustomer.add(Customer);
         }
		return LstCustomer;
	}
	public int updateCustomer(CustomerModel customerModel) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE exoticre_order.customerfishpro SET  "
				+ "CompanyName ='"+customerModel.getCompanyName()+"',"
				+ "Address = '"+customerModel.getAddress()+"',"
				+ "Address2 = '"+customerModel.getAddress2()+"',"
				+ "City = '"+customerModel.getCity()+"',"
				+ "States = '"+customerModel.getStates()+"',"
				+ "Zip = '"+customerModel.getZip()+"',"
				+ "Phone1 = '"+customerModel.getPhone1()+"',"
				+ "Terms = '"+customerModel.getTerms()+"',"
				+ "Salesperson = '"+customerModel.getSalesperson()+"',"
				+ "ShippingCost = '"+customerModel.getShippingCost()+"',"
				+ "Phone2 = '"+customerModel.getPhone2()+"',"
				+ "Cellphone = '"+customerModel.getCellphone()+"',"
				+ "FaxNo = '"+customerModel.getFax()+"',"
				+ "Country = '"+customerModel.getCountry()+"',"
				+ "Destin = '"+customerModel.getDestination()+"',"
				+ "alrportcode = '"+customerModel.getAlrportCode()+"',"
				+ "times = '"+customerModel.getTime()+"',"
				+ "Contact = '"+customerModel.getContact()+"',"
				+ "contact2 = '"+customerModel.getContact2()+"',"
				+ "Email = '"+customerModel.getEmail()+"',"
				+ "Email2 = '"+customerModel.getEMail2()+"',"
				+ "Title = '"+customerModel.getTitle()+"',"
				+ "Title2 = '"+customerModel.getTitle2()+"',"
				+ "Website = '"+customerModel.getWebsite()+"',"
				+ "Comments = '"+customerModel.getComments()+"',"
				+ "Balance = '"+customerModel.getCurrBal()+"',"
				+ "Credit = '"+customerModel.getOpnCred()+"',"
				+ "YTDSales = '"+customerModel.getYTDSales()+"',"
				+ "LstSale = '"+customerModel.getLstSales1()+"',"
				+ "LstPmt = '"+customerModel.getLstPmt1()+"',"
				+ "Entered = '"+customerModel.getEntered()+"',"
				+ "LstSaleDate = '"+customerModel.getLstSales2()+"',"
				+ "LstPmtDate = '"+customerModel.getLstPmt2()+"',"
				+ "NetDue = '"+customerModel.getNetDue()+"',"
				+ "CreditLimit = '"+customerModel.getCreditLimit()+"',"
				+ "SalesDisc = '"+customerModel.getSalesDisc()+"',"
				+ "Tax = '"+customerModel.getTax ()+"'"
				+ "  WHERE CustomerID = '"+customerModel.getCustomerID()+"'";
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql);
		System.out.println("update customerfishpro: "+status);
		return status;
	}
	public int addCustomer(CustomerModel customerModel) throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO exoticre_order.customerfishpro("
				+ "CustomerID,"
				+ "CompanyName,"
				+ "Address,"
				+ "Address2,"
				+ "City,"
				+ "States,"
				+ "Zip,"
				+ "Phone1)"
				+ "VALUES ('"
				+customerModel.getCustomerID()+"','"
				+customerModel.getCompanyName()+"','"
				+customerModel.getAddress()+"','"
				+customerModel.getAddress2()+"','"
				+customerModel.getCity()+"','"
				+customerModel.getStates()+"','"
				+customerModel.getZip()+"','"
				+customerModel.getPhone1()+"')";
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql);
		return status;
	}
	public Boolean delete(String  cusID) throws ClassNotFoundException, SQLException {
		String sql1 = "DELETE FROM  exoticre_order.customerfishpro  "
				+ "  WHERE CustomerID = '"+cusID+"'";       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("delete Customer   : "+status);
		return true;
	}
}
