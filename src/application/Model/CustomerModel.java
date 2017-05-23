package application.Model;

public class CustomerModel {
	private String 	stripe_user_id ="";

	private String 	CustomerID ="";
	private String 	CompanyName="";
	private String 	Address="";
	private String 	Address2="";
	private String 	City="";
	private String 	States="";
	private String 	Zip="";
	private String 	Phone1="";
	private String 	Terms="";
	private String 	carrier="";
	private String 	Salesperson="";
	private String 	Email="";
	private String 	saleEmail="";
	private String 	SalesDisc ="0";
	private Float ShippingCost = (float) 0;
	
	private String 	Phone2="";
	private String 	Cellphone="";
	private String 	Fax="";
	private String 	Country="";
	private String 	Destination="";
	private String 	AlrportCode="";
	private String 	Time="";
	private String 	Contact="";
	private String 	Contact2="";
	private String 	EMail="";
	private String 	EMail2="";
	private String 	Title="";
	private String 	Title2="";
	private String 	Website="";
	private String 	Comments="";
	private Float 	CurrBal = (float) 0;
	private Float 	OpnCred = (float) 0;
	private Float 	YTDSales = (float) 0;
	private Float 	LstSales1 = (float) 0;
	private Float 	LstPmt1 = (float) 0;
	private String 	Entered="";
	private String 	LstSales2="";
	private String 	LstPmt2="";
	//private String 	Terms;
	private String 	NetDue="";
	private String 	Price="";
	private Float 	CreditLimit  = (float) 0;
	//private String 	SalesDisc;
	
	private Integer Tax =0;
	
	public String getStripe_user_id() {
		return stripe_user_id;
	}
	public void setStripe_user_id(String stripe_user_id) {
		this.stripe_user_id = stripe_user_id;
	}
	public Float getYTDSales() {
		return YTDSales;
	}
	public void setYTDSales(Float yTDSales) {
		YTDSales = yTDSales;
	}
	public Float getLstSales1() {
		return LstSales1;
	}
	public void setLstSales1(Float lstSales1) {
		LstSales1 = lstSales1;
	}
	public Float getLstPmt1() {
		return LstPmt1;
	}
	public void setLstPmt1(Float lstPmt1) {
		LstPmt1 = lstPmt1;
	}
	public Float getCurrBal() {
		return CurrBal;
	}
	public Float getOpnCred() {
		return OpnCred;
	}
	public Float getCreditLimit() {
		return CreditLimit;
	}
	public void setCurrBal(Float currBal) {
		CurrBal = currBal;
	}
	public void setOpnCred(Float opnCred) {
		OpnCred = opnCred;
	}
	public void setCreditLimit(Float creditLimit) {
		CreditLimit = creditLimit;
	}
	public String getPhone2() {
		return Phone2;
	}
	public void setPhone2(String phone2) {
		Phone2 = phone2;
	}
	public String getCellphone() {
		return Cellphone;
	}
	public void setCellphone(String cellphone) {
		Cellphone = cellphone;
	}
	public String getFax() {
		return Fax;
	}
	public void setFax(String fax) {
		Fax = fax;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public String getAlrportCode() {
		return AlrportCode;
	}
	public void setAlrportCode(String alrportCode) {
		AlrportCode = alrportCode;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getContact2() {
		return Contact2;
	}
	public void setContact2(String contact2) {
		Contact2 = contact2;
	}
	public String getEMail() {
		return EMail;
	}
	public void setEMail(String eMail) {
		EMail = eMail;
	}
	public String getEMail2() {
		return EMail2;
	}
	public void setEMail2(String eMail2) {
		EMail2 = eMail2;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getTitle2() {
		return Title2;
	}
	public void setTitle2(String title2) {
		Title2 = title2;
	}
	public String getWebsite() {
		return Website;
	}
	public void setWebsite(String website) {
		Website = website;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	public String getEntered() {
		return Entered;
	}
	public void setEntered(String entered) {
		Entered = entered;
	}
	public String getLstSales2() {
		return LstSales2;
	}
	public void setLstSales2(String lstSales2) {
		LstSales2 = lstSales2;
	}
	public String getLstPmt2() {
		return LstPmt2;
	}
	public void setLstPmt2(String lstPmt2) {
		LstPmt2 = lstPmt2;
	}
	public String getNetDue() {
		return NetDue;
	}
	public void setNetDue(String netDue) {
		NetDue = netDue;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}

	public Integer getTax() {
		return Tax;
	}
	public void setTax(Integer tax) {
		Tax = tax;
	}
	public Float getShippingCost() {
		return ShippingCost;
	}
	public void setShippingCost(Float shippingCost) {
		ShippingCost = shippingCost;
	}
	public String getSalesDisc() {
		return SalesDisc;
	}
	public  void setSalesDisc(String salesDisc) {
		SalesDisc = salesDisc;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getSaleEmail() {
		return saleEmail;
	}
	public void setSaleEmail(String saleEmail) {
		this.saleEmail = saleEmail;
	}
	public String getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getAddress2() {
		return Address2;
	}
	public void setAddress2(String address2) {
		Address2 = address2;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getStates() {
		return States;
	}
	public void setStates(String states) {
		States = states;
	}
	public String getZip() {
		return Zip;
	}
	public void setZip(String zip) {
		Zip = zip;
	}
	public String getPhone1() {
		return Phone1;
	}
	public void setPhone1(String phone1) {
		Phone1 = phone1;
	}
	public String getTerms() {
		return Terms;
	}
	public void setTerms(String terms) {
		Terms = terms;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getSalesperson() {
		return Salesperson;
	}
	public void setSalesperson(String salesperson) {
		Salesperson = salesperson;
	}	
	
}
