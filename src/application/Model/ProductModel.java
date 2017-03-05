package application.Model;

public class ProductModel {
	private Integer id;
	private Integer ido;
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
	private Float surcharge;
	private String scientific;
	private String readyPayment ="0";
	
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
