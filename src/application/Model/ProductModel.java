package application.Model;

public class ProductModel {
	private Integer id;
	private Integer ido;
	private Integer productId;
	private String sku;
	private String qty;//
	private String size;//
	private String name;
	private String lot;
	private String addon;//
	private String price;
	private String disc;//
	private String total;
	private Float All_Total;
	private Float All_Total_Memo;
	private Float surcharge;
	private String scientific;
	private String readyPayment ="0";
	private String checknumber ="";
	private String issued ="0";
	private String  ExpressCommisson;
	private String chlSL;
	private String creditmemo;
	private String ClientCustomerID;
	
	public Float getAll_Total_Memo() {
		return All_Total_Memo;
	}
	public void setAll_Total_Memo(Float all_Total_Memo) {
		All_Total_Memo = all_Total_Memo;
	}
	public String getClientCustomerID() {
		return ClientCustomerID;
	}
	public void setClientCustomerID(String clientCustomerID) {
		ClientCustomerID = clientCustomerID;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	private String grxp="";
	
	public String getGrxp() {
		return grxp;
	}
	public void setGrxp(String grxp) {
		this.grxp = grxp;
	}
	public String getChlSL() {
		return chlSL;
	}
	public void setChlSL(String chlSL) {
		this.chlSL = chlSL;
	}
	public String getExpressCommisson() {
		return ExpressCommisson;
	}
	public void setExpressCommisson(String expressCommisson) {
		ExpressCommisson = expressCommisson;
	}
	public String getIssued() {
		return issued;
	}
	public void setIssued(String issued) {
		this.issued = issued;
	}
	public String getCreditMemo() {
		return creditmemo;
	}
	public void setCreditMemo(String value) {
		this.creditmemo = value;
	}
	public String getChecknumber() {
		return checknumber;
	}
	public void setChecknumber(String checknumber) {
		this.checknumber = checknumber;
	}
	private Float amoutPaid;
	private String paymentMethod;
	
	public Float getAmoutPaid() {
		return amoutPaid;
	}
	public void setAmoutPaid(Float amoutPaid) {
		this.amoutPaid = amoutPaid;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getReadyPayment() {
		return readyPayment;
	}
	public void setReadyPayment(String readyPayment) {
		this.readyPayment = readyPayment;
	}
	public String getScientific() {
		return scientific;
	}
	public void setScientific(String scientific) {
		this.scientific = scientific;
	}
	public Integer getIdo() {
		return ido;
	}
	public void setIdo(Integer ido) {
		this.ido = ido;
	}
	private Boolean commission = true;
	private Boolean fishdie = true;
	
	public Boolean getFishdie() {
		return fishdie;
	}
	public void setFishdie(Boolean fishdie) {
		this.fishdie = fishdie;
	}
	public Boolean getCommission() {
		return commission;
	}
	public void setCommission(Boolean commission) {
		this.commission = commission;
	}
	public Float getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(Float surcharge) {
		this.surcharge = surcharge;
	}
	public Float getAll_Total() {
		return All_Total;
	}
	public void setAll_Total(Float all_Total) {
		All_Total = all_Total;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLot() {
		return lot;
	}
	public void setLot(String lot) {
		this.lot = lot;
	}
	public String getAddon() {
		return addon;
	}
	public void setAddon(String addon) {
		this.addon = addon;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}

	
	
}
