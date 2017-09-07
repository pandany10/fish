package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.DBConnection;
import application.Model.OrderInfoModel;

public class OrderInfoDao {
	public OrderInfoModel getOrderInfoByOrderId(Integer order_id) throws ClassNotFoundException, SQLException {
		OrderInfoModel orderInfo = new OrderInfoModel();
		String sql ="SELECT * FROM exoticre_order.orders t1 inner join exoticre_order.customerfishpro t2 on t1.ClientCustomerID = t2.CustomerID where t1.order_id =  "+order_id+" group by t1.order_id";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 orderInfo.setCustomer_ship(rs.getString("Customer_ship"));
			 orderInfo.setAwb(rs.getString("awb"));
			 orderInfo.setFob(rs.getString("fob"));
			 orderInfo.setTerms(rs.getString("Terms"));
			 orderInfo.setPonumber(rs.getString("purchase_order"));
			 orderInfo.setSalesperson(rs.getString("Salesperson"));
			 orderInfo.setFcb(rs.getInt("fcb"));
			 orderInfo.setFcbw(rs.getInt("fcbw"));
			 orderInfo.setTpacks(rs.getInt("tpacks"));
			 orderInfo.setRockb(rs.getInt("rockb"));
			 orderInfo.setRockw(rs.getInt("rockw"));
			 orderInfo.setTotalp(rs.getInt("totalp"));
			 orderInfo.setDfb(rs.getInt("dfb"));
			 orderInfo.setDfbw(rs.getInt("dfbw"));
			 orderInfo.setTotalb(rs.getInt("totalb"));
			 orderInfo.setShippingCost(rs.getFloat("ShippingCost"));
			 orderInfo.setCustomerDate(rs.getString("Date"));
         }
		return orderInfo;
	}
}
