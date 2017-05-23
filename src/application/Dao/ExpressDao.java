package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.DBConnection;
import application.Model.ExpressModel;
import application.Model.SaleModel;
import application.Model.SalesModel;

public class ExpressDao {

	public ExpressModel getSite(String cusid) throws ClassNotFoundException, SQLException{
		ExpressModel expressModel = new ExpressModel();
		String sql = "SELECT * FROM exoticre_order.sites t1 where t1.storeId = '"+cusid+"'";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		while (rs.next()) {
			Integer id = rs.getInt("id");
			String site = rs.getString("site");
			String email = rs.getString("email");
			String Logo = rs.getString("Logo");
			String StoreName = rs.getString("StoreName");
			String Address1 = rs.getString("Address1");
			String Address2 = rs.getString("Address2");
			String City = rs.getString("City");
			String State = rs.getString("State");
			String ZipCode = rs.getString("ZipCode");
			String PhoneNumber = rs.getString("PhoneNumber");
			String aboutUs = rs.getString("aboutUs");
			String storeId = rs.getString("storeId");
			expressModel.setId(id);
			expressModel.setSite(site);
			expressModel.setLogo(Logo);
			expressModel.setStoreName(StoreName);
			expressModel.setAddress1(Address1);
			expressModel.setAddress2(Address2);
			expressModel.setCity(City);
			expressModel.setState(State);
			expressModel.setZipCode(ZipCode);
			expressModel.setPhoneNumber(PhoneNumber);
			expressModel.setAboutUs(aboutUs);
			expressModel.setStoreId(storeId);
		}
		return expressModel;
	}

}
