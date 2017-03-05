package application.Model;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailModel {
	private Integer order_id;
	
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public CustomerModel getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerModel customer) {
		this.customer = customer;
	}
	public CustomerModel getShipTo() {
		return shipTo;
	}
	public void setShipTo(CustomerModel shipTo) {
		this.shipTo = shipTo;
	}
	public List<ProductModel> getLstProduct() {
		return lstProduct;
	}
	public void setLstProduct(List<ProductModel> lstProduct) {
		this.lstProduct = lstProduct;
	}
	public OrderInfoModel getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfoModel orderInfo) {
		this.orderInfo = orderInfo;
	}
	
	private CustomerModel customer = new CustomerModel(); // bill to
	private CustomerModel  shipTo = new CustomerModel(); // ship to
	private OrderInfoModel  orderInfo = new OrderInfoModel(); // 
	private List<ProductModel> lstProduct = new ArrayList<>(); // list product
}
