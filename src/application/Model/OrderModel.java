package application.Model;

public class OrderModel {
	private String customerId = "";
	private String customerName;
	private String customerContact;
	private String customerPhone;
	private String customerTerms;
	private String customerSalesperson;
	
	
	
	private Integer no;
	private String Customer_date;
	private String status;
	private String Customer_ship;
	private Integer order_id;
	private String order_idc;
	private String ClientCustomerID;
	private String Customer_email;
	private String CompanyName;
	private String All_Total;
	private Float surcharge;
	private Boolean payment;
	private Boolean issued;
	private String payments;
	private String paymentMethod;
	private String amoutPaid;
	private String amoutUnPaid;
	private String disc;
	private String balance;
	private String blance30 = "";
	private String blance60  = "";
	private String blance90  = "";
	private String blance120  = "";
	
	private String fishDie  = "";


	public String getFishDie() {
		return fishDie;
	}
	public void setFishDie(String fishDie) {
		this.fishDie = fishDie;
	}
	public String getBlance30() {
		return blance30;
	}
	public void setBlance30(String blance30) {
		this.blance30 = blance30;
	}
	public String getBlance60() {
		return blance60;
	}
	public void setBlance60(String blance60) {
		this.blance60 = blance60;
	}
	public String getBlance90() {
		return blance90;
	}
	public void setBlance90(String blance90) {
		this.blance90 = blance90;
	}
	public String getBlance120() {
		return blance120;
	}
	public void setBlance120(String blance120) {
		this.blance120 = blance120;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getAmoutPaid() {
		return amoutPaid;
	}
	public void setAmoutPaid(String amoutPaid) {
		this.amoutPaid = amoutPaid;
	}
	public String getAmoutUnPaid() {
		return amoutUnPaid;
	}
	public void setAmoutUnPaid(String amoutUnPaid) {
		this.amoutUnPaid = amoutUnPaid;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerContact() {
		return customerContact;
	}
	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerTerms() {
		return customerTerms;
	}
	public void setCustomerTerms(String customerTerms) {
		this.customerTerms = customerTerms;
	}
	public String getCustomerSalesperson() {
		return customerSalesperson;
	}
	public void setCustomerSalesperson(String customerSalesperson) {
		this.customerSalesperson = customerSalesperson;
	}
	public Boolean getIssued() {
		return issued;
	}
	public void setIssued(Boolean issued) {
		this.issued = issued;
	}
	public String getAll_Total() {
		return All_Total;
	}
	public void setAll_Total(String all_Total) {
		All_Total = all_Total;
	}
	public String getPayments() {
		return payments;
	}
	public void setPayments(String payments) {
		this.payments = payments;
	}
	public Boolean getPayment() {
		return payment;
	}
	public void setPayment(Boolean payment) {
		this.payment = payment;
	}
	public Float getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(Float surcharge) {
		this.surcharge = surcharge;
	}
	public String getCustomer_email() {
		return Customer_email;
	}
	
	public void setCustomer_email(String customer_email) {
		Customer_email = customer_email;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		if(status.equals("COMPLETED")){
			status = "Completed";
		}
		this.status = status;
	}
	
	public String getCustomer_ship() {
		return Customer_ship;
	}
	
	public void setCustomer_ship(String customer_ship) {
		Customer_ship = customer_ship;
	}
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

    public String getCustomer_date() {
		return Customer_date;
	}

	public void setCustomer_date(String customer_date) {
		Customer_date = customer_date;
	}

	public Integer getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}

	public String getClientCustomerID() {
		return ClientCustomerID;
	}

	public void setClientCustomerID(String clientCustomerID) {
		ClientCustomerID = clientCustomerID;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}


	public String getOrder_idc() {
		return order_idc;
	}
	public void setOrder_idc(String order_idc) {
		this.order_idc = order_idc;
	}
	public OrderModel() {}

    public  OrderModel(Integer No, String Customer_date,String status,String Customer_ship, Integer order_id, String ClientCustomerID,String Customer_email, String CompanyName,String All_Total,Float surcharge ,Boolean payment) {
    	setNo(No);
    	setCustomer_date(Customer_date);
    	setStatus(status);
    	setCustomer_ship(Customer_ship);
    	setOrder_id(order_id);
    	setClientCustomerID(ClientCustomerID);
    	setCustomer_email(Customer_email);
    	setCompanyName(CompanyName);
    	this.setAll_Total(All_Total);
    	setSurcharge(surcharge);
    	setPayment(payment);
    }
}
