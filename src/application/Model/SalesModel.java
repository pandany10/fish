package application.Model;

public class SalesModel {

	private String Customer_date ="";
	private String Day="";
	private String total_pending="";
	private String total_sales="";
	private String total_complete="";
	private String total_discount="";
	private String total_tax="";
	private String total_net_sales="";
	
	private String status="";
	private String paymentMethod="";
	private String issued="";
	
	//
	private String inv="";
	private String intDate="";
	private String invCusName="";
	private String invTotal="";
	private String invNon="";
	
	public String getTotal_discount() {
		return total_discount;
	}

	public void setTotal_discount(String total_discount) {
		this.total_discount = total_discount;
	}

	public String getTotal_tax() {
		return total_tax;
	}

	public void setTotal_tax(String total_tax) {
		this.total_tax = total_tax;
	}

	public String getTotal_net_sales() {
		return total_net_sales;
	}

	public void setTotal_net_sales(String total_net_sales) {
		this.total_net_sales = total_net_sales;
	}

	public String getIssued() {
		return issued;
	}

	public void setIssued(String issued) {
		this.issued = issued;
	}

	public String getTotal_sales() {
		return total_sales;
	}

	public void setTotal_sales(String total_sales) {
		this.total_sales = total_sales;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getInv() {
		return inv;
	}

	public void setInv(String inv) {
		this.inv = inv;
	}

	public String getIntDate() {
		return intDate;
	}

	public void setIntDate(String intDate) {
		this.intDate = intDate;
	}

	public String getInvCusName() {
		return invCusName;
	}

	public void setInvCusName(String invCusName) {
		this.invCusName = invCusName;
	}

	public String getInvTotal() {
		return invTotal;
	}

	public void setInvTotal(String invTotal) {
		this.invTotal = invTotal;
	}

	public String getInvNon() {
		return invNon;
	}

	public void setInvNon(String invNon) {
		this.invNon = invNon;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomer_date() {
		return Customer_date;
	}

	public void setCustomer_date(String customer_date) {
		Customer_date = customer_date;
	}

	public String getDay() {
		return Day;
	}

	public void setDay(String day) {
		Day = day;
	}

	public String getTotal_pending() {
		return total_pending;
	}

	public void setTotal_pending(String total_pending) {
		this.total_pending = total_pending;
	}

	public String getTotal_complete() {
		return total_complete;
	}

	public void setTotal_complete(String total_complete) {
		this.total_complete = total_complete;
	}

	public SalesModel() {}

   
}
