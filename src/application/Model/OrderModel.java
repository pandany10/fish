package application.Model;

public class OrderModel {

	private Integer no;
	private String Customer_date;
	private String status;
	private String Customer_ship;
	private Integer order_id;
	private String ClientCustomerID;
	private String Customer_email;
	private String CompanyName;
	private Float All_Total;
	private Float surcharge;
	private Boolean payment;
	

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

	public Float getAll_Total() {
		return All_Total;
	}

	public void setAll_Total(Float all_Total) {
		All_Total = all_Total;
	}

	public OrderModel() {}

    public  OrderModel(Integer No, String Customer_date,String status,String Customer_ship, Integer order_id, String ClientCustomerID,String Customer_email, String CompanyName,Float All_Total,Float surcharge ,Boolean payment) {
    	setNo(No);
    	setCustomer_date(Customer_date);
    	setStatus(status);
    	setCustomer_ship(Customer_ship);
    	setOrder_id(order_id);
    	setClientCustomerID(ClientCustomerID);
    	setCustomer_email(Customer_email);
    	setCompanyName(CompanyName);
    	setAll_Total(All_Total);
    	setSurcharge(surcharge);
    	setPayment(payment);
    }
}
