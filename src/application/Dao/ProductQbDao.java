package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.DBConnection;
import application.Model.ProductModel;

public class ProductQbDao {
	public List<ProductModel> getListProductSearchs1(int offset,String key,String type) throws ClassNotFoundException, SQLException{
		int limit = 50000;
		List<ProductModel> lstProduct = new ArrayList<>();
		String sql = "SELECT ListID,Name AS sku,Description AS name,SalesPrice AS Price FROM item limit "+offset+","+limit+";";
		final String[] filter = new String[]{ "Sku", "Name", "Size", "Price" };
		
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 ProductModel product = new ProductModel();
             String sku = rs.getString("sku");
             String listid = rs.getString("ListID");
             String name = rs.getString("name");
             Float price = rs.getFloat("price");
             product.setSku(sku);
             product.setName(name);
             product.setPrice(String.format("%.2f", price));
             lstProduct.add(product); 
        	 System.out.println(sku);
         }
	
		return lstProduct;
		
	}
	public int updateQb(ProductModel productModel) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE exoticre_order.masterProduct SET  "
				+ "sku ='"+productModel.getSku()+"',"
				+ "name = '"+productModel.getName()+"',"
				+ "size = '"+productModel.getSize()+"',"
				+ "lot = '"+productModel.getLot()+"',"
				+ "price = '"+productModel.getPrice()+"'"
				+ "  WHERE id = '"+productModel.getIdo()+"'";
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql);
		System.out.println("update product: "+status);
		return status;
	}

}
