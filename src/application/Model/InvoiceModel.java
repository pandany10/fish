package application.Model;

public class InvoiceModel {
	private OrderInfoModel orderInfo;
	private ProductModel product;
	public OrderInfoModel getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfoModel orderInfo) {
		this.orderInfo = orderInfo;
	}
	public ProductModel getProduct() {
		return product;
	}
	public void setProduct(ProductModel product) {
		this.product = product;
	}
	
}
