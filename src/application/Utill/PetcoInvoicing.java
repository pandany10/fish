package application.Utill;


import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;



import application.Model.CustomerModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.OrderModel;
import application.Model.ProductModel;
import application.Dao.OrderDao;
import application.Utill.Files;


public class PetcoInvoicing {
	private OrderDetailModel orderDetail;
	private OrderModel currentOrder;
	private OrderInfoModel orderInfo;
	private List<ProductModel> lstProduct;
	private String filecontent;

	public void UploadInvoice(OrderDetailModel orderDetail, OrderModel currentOrders)
	//public void UploadInvoice(Integer order_id)
			throws MalformedURLException, IOException, ClassNotFoundException, SQLException {
		
		OrderDao getit = new OrderDao();
		
	
		//currentOrder = getit.getOrderPrint(order_id);
	//	orderDetail = getit.getOrderDetail(order_id);
	//	currentOrder = getit.getOrderByOrderId(order_id);
		currentOrder = currentOrders;
		//bill = orderDetail.getCustomer();
	//	ship = orderDetails.getShipTo();
		orderInfo = orderDetail.getOrderInfo();
System.out.println("PO NUMBER : " +orderInfo.getPonumber());
		lstProduct = orderDetail.getLstProduct();

		Files p = new Files();
	//	try {
	
    	
		filecontent = "\"INVOICE\",\"10029,810\",\"" + currentOrder.getOrder_id() + "\"," + currentOrder.getCustomer_date() + "," + orderInfo.getPonumber() + "," +currentOrder.getCustomer_date() + ",\"" + orderInfo.getPonumber() + "\",\"\",0.00,\"LB\",0.00,\"\",\"\",\"\",\"\",\"\",\"\",0.00,\"\"\r\n";
		//p.Write("\"INVOICE,10029,810," + currentOrder.getOrder_id() + "," + currentOrder.getCustomer_date() + "," + orderInfo.getPonumber() + "," +currentOrder.getCustomer_date() + "," + orderInfo.getPonumber() + ",,0.00,LB,0.00,,,,,,,0.00","petco/upload/"+currentOrder.getOrder_id() +".txt");
    	
    	for (ProductModel product : lstProduct) {
    		filecontent += "\"ITEM\",\"\",\"\",\"\"," +product.getQty() + ",\"EA\"," + product.getPrice() + ",\"\",\'" + product.getName() + "\"\r\n";
    	    filecontent += "\"ITEM_ID\",\""+ product.getSku() + "\",\"VN\",\"Vendor Item Number\"\r\n";
       	    filecontent += "\"ITEM_ID\",\""+ product.getName() +"\",\"PD\",\"Part Number Description\"\r\n";
       	    filecontent += "\"ITEM_ID\",\""+ product.getSku() +"\",\"SK\",\"Stock Keeping Unit (SKU)\"\r\n";
       	    filecontent += "ITEM_END";

		}			
    	
    	 filecontent += "\"INVOICE_ALLOW_CHG\",0,\"C\",\"F155\",\"06\",0,\"\",5.50,0,0,0,\"\",\"\",\"\",\"\",\"\",\"\"\r\n";
      	 filecontent += "INVOICE_END";
      	p.Write(filecontent,"petco/upload/"+currentOrder.getOrder_id() +".txt");
    	
	//	} catch (DocumentException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}  
	}
	

	
}
