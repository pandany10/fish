package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

import application.DBConnection;
import application.Model.CustomerModel;
import application.Model.InvoiceModel;
import application.Model.OrderDetailModel;
import application.Model.OrderInfoModel;
import application.Model.OrderModel;
import application.Model.ProductModel;

public class OrderDao {

	public List<OrderModel> getOrder(String in_status,String notes) throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment   FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.status) = LOWER('"+in_status+"') GROUP BY t1.order_id order by t1.order_id desc limit 100";
		if(notes.equals("App Java")){
			sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment   FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE LOWER(t1.status) = LOWER('"+in_status+"') and t1.notes = '"+notes+"' GROUP BY  t1.order_id order by t1.order_id desc limit 100";
		}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
             String Customer_date = rs.getString("Customer_date");
             String status = rs.getString("status");
             String Customer_ship = rs.getString("Customer_ship");
             Integer order_id = rs.getInt("order_id");
             String ClientCustomerID = rs.getString("ClientCustomerID");
             String Customer_email =  rs.getString("Customer_email");
             String CompanyName =  rs.getString("CompanyName");
             Float All_Total = rs.getFloat("All_Total");
             Float surcharge = rs.getFloat("surcharge");
             String payment = rs.getString("payment");
             Boolean payments = false;
             if(payment.equals("1")){
            	 payments = true;
             }else{
            	 payments = false;
             }
             OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Total,surcharge,payments);
             lstOrder.add(order);
         }
		return lstOrder;
	}
	public List<OrderModel> getLstPayment() throws ClassNotFoundException, SQLException{
		List<OrderModel> lstOrder = new ArrayList<>();
		String sql = "SELECT t1.Customer_date,t1.status,t1.Customer_ship,t1.order_id,t1.ClientCustomerID,t1.Customer_email,t2.CompanyName,t1.All_Total,t1.surcharge,t1.payment   FROM exoticre_order.orders t1 LEFT JOIN customerfishpro t2 ON t1.ClientCustomerID = t2.CustomerID WHERE readyPayment = '1' GROUP BY  t1.order_id order by t1.order_id desc limit 500";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
            String Customer_date = rs.getString("Customer_date");
            String status = rs.getString("status");
            String Customer_ship = rs.getString("Customer_ship");
            Integer order_id = rs.getInt("order_id");
            String ClientCustomerID = rs.getString("ClientCustomerID");
            String Customer_email =  rs.getString("Customer_email");
            String CompanyName =  rs.getString("CompanyName");
            Float All_Total = rs.getFloat("All_Total");
            Float surcharge = rs.getFloat("surcharge");
            String payment = rs.getString("payment");
            Boolean payments = false;
            if(payment.equals("1")){
           	 payments = true;
            }else{
           	 payments = false;
            }
            OrderModel order = new OrderModel(0,Customer_date,status,Customer_ship,order_id,ClientCustomerID,Customer_email,CompanyName,All_Total,surcharge,payments);
            lstOrder.add(order);
        }
		return lstOrder;
	}
	public OrderDetailModel getOrderDetail(Integer order_id) throws ClassNotFoundException, SQLException {
		OrderDetailModel orderDetail = new OrderDetailModel();
		
		CustomerDao customerDao = new CustomerDao();
		OrderInfoDao orderInfoDao = new OrderInfoDao();

		ProductDao productDao = new ProductDao();
		
		List<ProductModel> lstProduct = new ArrayList<>(); 
		lstProduct = productDao.getListProductByOrderId(order_id);
		
		CustomerModel customer = customerDao.getCustomerByOrderId(order_id);
		OrderInfoModel  orderInfo = new OrderInfoModel(); // 
		orderInfo = orderInfoDao.getOrderInfoByOrderId(order_id);
		
		
		orderDetail.setCustomer(customer);
		orderDetail.setShipTo(customer);
		orderDetail.setOrderInfo(orderInfo);
		orderDetail.setLstProduct(lstProduct);
		return orderDetail;
	}
	public Boolean updateOrderProduct(ProductModel product) throws ClassNotFoundException, SQLException {
		if(product.getId() != null){
			int commission = 1;
			if(product.getCommission() == false){
				commission = 0;
			}
			System.out.println(product.getSku());
			String sql = "UPDATE exoticre_order.orders SET  "
					+ "Product_Sku ='"+product.getSku()+"',"
					+ "Item = '"+product.getQty()+"',"
					+ "Size = '"+product.getSize()+"',"
					+ "Product_name = '"+product.getName()+"',"
					+ "Lot = '"+product.getLot()+"',"
					+ "Addon = '"+product.getAddon()+"',"
					+ "Price = '"+product.getPrice()+"',"
					+ "disc = '"+product.getDisc()+"',"
					+ "commission = "+commission+","
					+ "Total = '"+product.getTotal()+"'"
					+ "  WHERE id = "+product.getId();
			int status = DBConnection.getConnection().createStatement().executeUpdate(sql);
			System.out.println("update product order: "+status);
		}
		return true;
	}
	public Boolean updateOrderTotal(Integer order_id,Float discount) throws ClassNotFoundException, SQLException {
		String sql = "SELECT Total   FROM exoticre_order.orders WHERE 	order_id =  "+order_id;
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		Float allTotal = (float) 0;
		 while (rs.next()) {
			 allTotal += rs.getFloat("Total");
        }
		 Float dis =  discount/100;
		 allTotal = (1- dis)*allTotal;
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "All_Total ='"+allTotal+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update product order Total : "+status);
		return true;
	}
	public Boolean updatePaymentMethod(Integer order_id,String paymentMethod) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "paymentMethod ='"+paymentMethod+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update payment Method: "+status);
		return true;
	}
	public Boolean updateAmoutPaid(Integer order_id,Float amountPaid) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "amoutPaid ='"+amountPaid+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update amount Paid: "+status);
		return true;
	}
	public Boolean updateOrderTotals(Integer order_id,Float allTotal) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "All_Total ='"+allTotal+"'"
				+ "  WHERE order_id = "+order_id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update product order Total : "+status);
		return true;
	}
	public Boolean delete(Integer id) throws ClassNotFoundException, SQLException {
		String sql1 = "DELETE FROM  exoticre_order.orders  "
				+ "  WHERE id = "+id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("delete product order Total : "+status);
		return true;
	}
	public Boolean deleteOneOrder(Integer orderId) throws ClassNotFoundException, SQLException {
		String sql1 = "DELETE FROM  exoticre_order.orders  "
				+ "  WHERE order_id = "+orderId;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("delete One Order : "+status);
		return true;
	}
	public Boolean updateOrderStatus(Integer order_id,String status) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "status ='"+status+"'"
				+ "  WHERE order_id = "+order_id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order Status : "+status1);
		return true;
	}
	public Boolean updatePayment(Integer order_id,Boolean payment) throws ClassNotFoundException, SQLException {
		String payments = "0";
		if(payment == true){
			payments = "1";
		}
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "payment ='"+payments+"'"
				+ "  WHERE order_id = "+order_id;       
		int status1 = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update  order payment : "+status1);
		return true;
	}
	public Integer updateOrderInfo(OrderInfoModel orderInfoModel,Integer order_id) throws ClassNotFoundException, SQLException {
		String sql1 = "UPDATE exoticre_order.orders SET  "
				+ "Customer_ship ='"+orderInfoModel.getCustomer_ship()+"',"
				+ "awb =' "+orderInfoModel.getAwb()+"',"
				+ "fcb =' "+orderInfoModel.getFcb()+"',"
				+ "fcbw =' "+orderInfoModel.getFcbw()+"',"
				+ "tpacks =' "+orderInfoModel.getTpacks()+"',"
				+ "fob =' "+orderInfoModel.getFob()+"',"
				+ "rockb =' "+orderInfoModel.getRockb()+"',"
				+ "rockw =' "+orderInfoModel.getRockw()+"',"
				+ "totalp =' "+orderInfoModel.getTotalp()+"',"
				+ "ponumber =' "+orderInfoModel.getPonumber()+"',"
				+ "dfb =' "+orderInfoModel.getDfb()+"',"
				+ "dfbw =' "+orderInfoModel.getDfbw()+"',"
				+ "totalb =' "+orderInfoModel.getTotalb()+"'"
				+ "  WHERE order_id = "+order_id;       
		
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("update product order Total : "+status);
		return status;
	}
	public Integer addOrder(InvoiceModel invoice,Integer order_id) throws ClassNotFoundException, SQLException {
		
		OrderInfoModel orderInfoModel = invoice.getOrderInfo();
		ProductModel productModel = invoice.getProduct();
		Date myDate = new Date();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(myDate);
		int commission = 1;
		if(productModel.getCommission() == false){
			commission = 0;
		}
		String sql = "INSERT INTO exoticre_order.orders("
				+ "notes,"
				+ "Airport,"
				+ "Customer_comments,"
				+ "Product_id,"
				+ "Date,"
				+ "Customer_date,"
				+ "Customer_email,"
				+ "saleperson_email,"
				+ "order_id,"
				+ "ClientCustomerID,"
				+ "Customer_ship,"
				+ "awb,"
				+ "fob,"
				+ "ponumber,"
				+ "fcb,"
				+ "fcbw,"
				+ "tpacks,"
				+ "rockb,"
				+ "rockw,"
				+ "totalp,"
				+ "dfb,"
				+ "dfbw,"
				+ "totalb,"
				+ "Product_Sku,"
				+ "Item,"
				+ "Size,"
				+ "Product_name,"
				+ "Lot,"
				+ "Addon,"
				+ "Price,"
				+ "disc,"
				+ "Total,"
				+ "commission,"
				+ "readyPayment,"
				+ "All_Total)"
				+ "VALUES ('App Java','"
				+ "','"
				+  "','"
				+ "0','"
				+date+"','"
				+date+"','"
				+orderInfoModel.getCustomer_email()+"','"
				+orderInfoModel.getSaleEmail()+"','"
				+order_id+"','"
				+orderInfoModel.getClientCustomerID()+"','"
				+orderInfoModel.getCustomer_ship()+"','"
				+orderInfoModel.getAwb()+"','"
				+orderInfoModel.getFob()+"','"
				+orderInfoModel.getPonumber()+"','"
				+orderInfoModel.getFcb()+"','"
				+orderInfoModel.getFcbw()+"','"
				+orderInfoModel.getTpacks()+"','"
				+orderInfoModel.getRockb()+"','"
				+orderInfoModel.getRockw()+"','"
				+orderInfoModel.getTotalp()+"','"
				+orderInfoModel.getDfb()+"','"
				+orderInfoModel.getDfbw()+"','"
				+orderInfoModel.getTotalb()+"','"
				+productModel.getSku()+"','"
				+productModel.getQty()+"','"
				+productModel.getSize()+"','"
				+productModel.getName()+"','"
				+productModel.getLot()+"','"
				+productModel.getAddon()+"','"
				+productModel.getPrice()+"','"
				+productModel.getDisc()+"','"
				+productModel.getTotal()+"',"
				+commission+",'"
				+productModel.getReadyPayment()+"','"
				+productModel.getAll_Total()+"')";
		Statement stmt = (Statement) DBConnection.getConnection().createStatement();
		int status = stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
        	status=rs.getInt(1);
        }
        rs.close();

        stmt.close();
		return status;

	}
	public Integer getLastNewOrderId() throws ClassNotFoundException, SQLException {
		Integer order_id = null;
		String sql = "SELECT  order_id  FROM exoticre_order.orders  order by order_id desc limit 1";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 order_id = rs.getInt("order_id") +1;
        }
		return order_id;
	}
}
