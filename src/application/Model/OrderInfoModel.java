package application.Model;

public class OrderInfoModel {
	private String ClientCustomerID;
	private String Customer_ship; //order
	private String awb; //order
	
	private String fob; //order
	private String Terms; //cus fish pro
	
	private Integer ponumber; //order
	private String Salesperson; //cus fish pro
	

	private Integer 	fcb; //order
	private Integer 	fcbw; //order
	private Integer 	tpacks; //order
	
	private Integer 	rockb; //order//order
	private Integer 	rockw; //order
	private Integer 	totalp; //order
	
	
	private Integer 	dfb; //order
	private Integer 	dfbw; //order
	private Integer 	totalb; //order
	private String saleEmail; //order
	private String Customer_email; //order
	private Float ShippingCost; //cus fish pro
	
	public Float getShippingCost() {
		return ShippingCost;
	}
	public void setShippingCost(Float shippingCost) {
		ShippingCost = shippingCost;
	}
	public String getCustomer_email() {
		return Customer_email;
	}
	public void setCustomer_email(String customer_email) {
		Customer_email = customer_email;
	}
	public String getSaleEmail() {
		return saleEmail;
	}
	public void setSaleEmail(String saleEmail) {
		this.saleEmail = saleEmail;
	}
	public String getCustomer_ship() {
		return Customer_ship;
	}
	public void setCustomer_ship(String customer_ship) {
		Customer_ship = customer_ship;
	}
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
	public String getFob() {
		return fob;
	}
	
	public String getClientCustomerID() {
		return ClientCustomerID;
	}
	public void setClientCustomerID(String clientCustomerID) {
		ClientCustomerID = clientCustomerID;
	}
	public void setFob(String fob) {
		this.fob = fob;
	}
	public String getTerms() {
		return Terms;
	}
	public void setTerms(String terms) {
		Terms = terms;
	}
	public Integer getPonumber() {
		return ponumber;
	}
	public Integer getFcb() {
		return fcb;
	}
	public Integer getFcbw() {
		return fcbw;
	}
	public Integer getTpacks() {
		return tpacks;
	}
	public Integer getRockb() {
		return rockb;
	}
	public Integer getRockw() {
		return rockw;
	}
	public Integer getTotalp() {
		return totalp;
	}
	public Integer getDfb() {
		return dfb;
	}
	public Integer getDfbw() {
		return dfbw;
	}
	public Integer getTotalb() {
		return totalb;
	}
	public String getSalesperson() {
		return Salesperson;
	}
	public void setSalesperson(String salesperson) {
		Salesperson = salesperson;
	}
	public void setPonumber(Integer ponumber) {
		this.ponumber = ponumber;
	}
	public void setFcb(Integer fcb) {
		this.fcb = fcb;
	}
	public void setFcbw(Integer fcbw) {
		this.fcbw = fcbw;
	}
	public void setTpacks(Integer tpacks) {
		this.tpacks = tpacks;
	}
	public void setRockb(Integer rockb) {
		this.rockb = rockb;
	}
	public void setRockw(Integer rockw) {
		this.rockw = rockw;
	}
	public void setTotalp(Integer totalp) {
		this.totalp = totalp;
	}
	public void setDfb(Integer dfb) {
		this.dfb = dfb;
	}
	public void setDfbw(Integer dfbw) {
		this.dfbw = dfbw;
	}
	public void setTotalb(Integer totalb) {
		this.totalb = totalb;
	}
	
}
