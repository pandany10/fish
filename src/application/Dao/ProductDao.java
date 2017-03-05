package application.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.DBConnection;
import application.Model.CustomerModel;
import application.Model.ProductModel;

public class ProductDao {
	public List<ProductModel> getListProductSearchs(String key,String type) throws ClassNotFoundException, SQLException{
		List<ProductModel> lstProduct = new ArrayList<>();
		String sql = "SELECT t1.id, t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku  left join exoticre_order.products t3 on t1.sku = t3.sku where LOWER(t1.sku) like LOWER('%"+key+"%')  limit 0,300;";
		final String[] filter = new String[]{ "Sku", "Name", "Size", "Lot" ,"Price" };
		if(type.equals(filter[1])){
			sql = "SELECT t1.id, t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku  left join exoticre_order.products t3 on t1.sku = t3.sku where  LOWER(t1.name) like LOWER('%"+key+"%')  limit 500;";
		}
		if(type.equals(filter[2])){
			sql = "SELECT t1.id, t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku  left join exoticre_order.products t3 on t1.sku = t3.sku where  LOWER(t1.size) = LOWER('"+key+"')  limit 500;";
		}
		if(type.equals(filter[3])){
			sql = "SELECT t1.id, t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku  left join exoticre_order.products t3 on t1.sku = t3.sku where  LOWER(t1.lot) = LOWER('"+key+"')  limit 500;";
		}
		if(type.equals(filter[4])){
			sql = "SELECT t1.id, t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku  left join exoticre_order.products t3 on t1.sku = t3.sku where  LOWER(t1.price) = LOWER('"+key+"')  limit 500;";
		}
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 ProductModel product = new ProductModel();
             String sku = rs.getString("sku");
             Integer qty = 1;
             String size = rs.getString("size");
             String name = rs.getString("name");
             String lots = rs.getString("lot");
             lots = lots.replace("/Lot", "");
             Integer lot = 1;
             String scientific = rs.getString("scientific");
             if(lots.equals("")){
            	 
             }else{
            	lots = lots.replace("STICK ", "");
            	lot = Integer.parseInt(lots.replace("/Lot", ""));
             }
             Integer addon = 0;
             Float price = rs.getFloat("price");
             Float prices = rs.getFloat("prices");
             if(rs.getString("skus") != null){
            	 price = prices; 
             }
             Integer disc =  0;
             Float total = price*lot*qty ;
             //System.out.println(sku+" "+ name+" "+size+" "+price);
             Integer ido = rs.getInt("id");
             product.setIdo(ido);
             product.setScientific(scientific);
             product.setSku(sku);
             product.setQty(String.valueOf(qty));
             product.setSize(size);
             product.setName(name);
             product.setLot(String.valueOf(lot));
             product.setAddon(String.valueOf(addon));
             product.setPrice(String.format("%.2f", price));
             product.setDisc(String.valueOf(disc));
             product.setTotal(String.format("%.2f", total));
             lstProduct.add(product); 
         
         }
		return lstProduct;
		
	}
	public List<ProductModel> getListProductSearch(String key,int limit) throws ClassNotFoundException, SQLException{
		System.out.println("limit: "+limit);
		List<ProductModel> lstProduct = new ArrayList<>();
		int count = 0;
		 String sql1 = "SELECT * FROM exoticre_order.products t1 where grxp = 'NOC' and (t1.sku like '%"+key+"%' or LOWER(t1.description) like LOWER('%"+key+"%'))  limit "+limit+";";
		 ResultSet rs1 = DBConnection.getConnection().createStatement().executeQuery(sql1);
		 while (rs1.next()) {
			 ProductModel product = new ProductModel();
             String sku = rs1.getString("sku");
             Integer qty = 1;
             String size = rs1.getString("size");
             String name = rs1.getString("description");
             String lots = rs1.getString("lot");
             lots = lots.replace("/Lot", "");
             String scientific = rs1.getString("scientific");
             Integer lot = 1;
             if(lots.equals("")){
            	 
             }else{
            	// lot = Integer.parseInt(lots.replace("/Lot", ""));
             }
             Integer addon = 0;
             Float price = rs1.getFloat("price");
             Integer disc =  0;
             Float total = price*lot*qty ;
             //System.out.println(sku+" "+ name+" "+size+" "+price);
             
             product.setSku(sku);
             product.setScientific(scientific);
             product.setQty(String.valueOf(qty));
             product.setSize(size);
             product.setName(name);
             product.setLot(String.valueOf(lot));
             product.setAddon(String.valueOf(addon));
             product.setPrice(String.format("%.2f", price));
             product.setDisc(String.valueOf(disc));
             product.setTotal(String.format("%.2f", total));
             lstProduct.add(product); 
             count++;
         }
		 limit = limit - count;
		String sql = "SELECT t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku left join exoticre_order.products t3 on t1.sku = t3.sku where t1.sku like '%"+key+"%' or LOWER(t1.name) like LOWER('%"+key+"%')  limit "+limit+";";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 ProductModel product = new ProductModel();
             String sku = rs.getString("sku");
             Integer qty = 1;
             String size = rs.getString("size");
             String name = rs.getString("name");
             String lots = rs.getString("lot");
             lots = lots.replace("/Lot", "");
             String scientific = rs.getString("scientific");
             Integer lot = 1;
             if(lots.equals("")){
            	 
             }else{
            	// lot = Integer.parseInt(lots.replace("/Lot", ""));
             }
             Integer addon = 0;
             Float price = rs.getFloat("price");
             Float prices = rs.getFloat("prices");
             if(rs.getString("skus") != null){
            	 price = prices; 
             }
             Integer disc =  0;
             Float total = price*lot*qty ;
             //System.out.println(sku+" "+ name+" "+size+" "+price);
             
             product.setSku(sku);
             product.setScientific(scientific);
             product.setQty(String.valueOf(qty));
             product.setSize(size);
             product.setName(name);
             product.setLot(String.valueOf(lot));
             product.setAddon(String.valueOf(addon));
             product.setPrice(String.format("%.2f", price));
             product.setDisc(String.valueOf(disc));
             product.setTotal(String.format("%.2f", total));
             lstProduct.add(product); 
         
         }
		
		return lstProduct;
		
	}
	public List<ProductModel> getListProductSearch(String key) throws ClassNotFoundException, SQLException{
		List<ProductModel> lstProduct = new ArrayList<>();
		String sql = "SELECT t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku left join exoticre_order.products t3 on t1.sku = t3.sku where t1.sku like '%"+key+"%' or LOWER(t1.name) like LOWER('%"+key+"%')  limit 1000;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 ProductModel product = new ProductModel();
             String sku = rs.getString("sku");
             Integer qty = 1;
             String size = rs.getString("size");
             String name = rs.getString("name");
             String lots = rs.getString("lot");
             lots = lots.replace("/Lot", "");
             String scientific = rs.getString("scientific");
             Integer lot = 1;
             if(lots.equals("")){
            	 
             }else{
            	// lot = Integer.parseInt(lots.replace("/Lot", ""));
             }
             Integer addon = 0;
             Float price = rs.getFloat("price");
             Float prices = rs.getFloat("prices");
             if(rs.getString("skus") != null){
            	 price = prices; 
             }
             Integer disc =  0;
             Float total = price*lot*qty ;
             //System.out.println(sku+" "+ name+" "+size+" "+price);
             
             product.setSku(sku);
             product.setScientific(scientific);
             product.setQty(String.valueOf(qty));
             product.setSize(size);
             product.setName(name);
             product.setLot(String.valueOf(lot));
             product.setAddon(String.valueOf(addon));
             product.setPrice(String.format("%.2f", price));
             product.setDisc(String.valueOf(disc));
             product.setTotal(String.format("%.2f", total));
             lstProduct.add(product); 
         
         }
		return lstProduct;
		
	}
	public List<ProductModel> getListProductByOrderId(Integer order_id) throws ClassNotFoundException, SQLException{
		List<ProductModel> lstProduct = new ArrayList<>();
		String sql = "SELECT t1.*,t3.scientific FROM exoticre_order.orders t1  left join exoticre_order.products t3 on t1.Product_Sku = t3.sku  where order_id = "+order_id+" and t3.scientific != 'null'";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 ProductModel product = new ProductModel();
             String sku = rs.getString("Product_Sku");
             Integer qty = rs.getInt("Item");
             String size = rs.getString("Size");
             String name = rs.getString("Product_name");
             Integer lot = rs.getInt("Lot");
             Integer addon = rs.getInt("Addon");
             Float price = rs.getFloat("Price");
             Integer disc =  rs.getInt("disc");
             Float total = rs.getFloat("Total");
             Float alltotal = rs.getFloat("All_Total");
             Float surcharge = rs.getFloat("surcharge");
             Boolean commission = rs.getBoolean("commission");
             String scientific = rs.getString("scientific");
             String paymentMethod = rs.getString("paymentMethod");
             Float amoutPaid = rs.getFloat("amoutPaid");
             if(amoutPaid == 0.00){
            	 amoutPaid = alltotal;
             }
             if(disc == 0){
            	// Float subTotal = price*(lot*qty + addon);
            	// disc =  (int)Math.round(((total-subTotal)/subTotal)*100);
             }
             Integer id = rs.getInt("id");
             product.setId(id);
             product.setScientific(scientific);
             product.setSku(sku);
             product.setQty(String.valueOf(qty));
             
             product.setSize(size);
             product.setName(name);
             product.setLot(String.valueOf(lot));
             product.setAddon(String.valueOf(addon));
             product.setPrice(String.format("%.2f", price));
             product.setDisc(String.valueOf(disc));
             product.setTotal(String.format("%.2f", total));
             product.setAll_Total(alltotal);
             product.setSurcharge(surcharge);
             product.setCommission(commission);
             product.setAmoutPaid(amoutPaid);
             product.setPaymentMethod(paymentMethod);
             lstProduct.add(product); 
         }
		return lstProduct;
	}
	public ProductModel getProductBySku(String skus) throws ClassNotFoundException, SQLException{
		ProductModel product = new ProductModel();
		if(skus.equals("")){
			skus = "nothing";
		}
		int count = 0;
		String sql = "SELECT t1.*,  t2.sku  as skus,t2.name  as names ,t2.size  as sizes ,t2.lot  as lots,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku left join exoticre_order.products t3 on t1.sku = t3.sku  where t1.sku = '"+skus+"' limit 1";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
             String sku = rs.getString("sku");
             Integer qty = 1;
             String size = rs.getString("size");
             String name = rs.getString("name");
             String lots = rs.getString("lot");
             String scientific = rs.getString("scientific");
             lots = lots.replace("/Lot", "");
             Integer lot = 1;
             if(lots.equals("")){
            	 
             }else{
            	// lot = Integer.parseInt(lots.replace("/Lot", ""));
             }
             Integer addon = 0;
             Float price = rs.getFloat("price");
             Float prices = rs.getFloat("prices");
             if(rs.getString("skus") != null){
            	 price = prices; 
             }
             Integer disc =  0;
             Float total = price*lot*qty ;
             
             product.setSku(sku);
             product.setScientific(scientific);
             product.setQty(String.valueOf(qty));
             product.setSize(size);
             product.setName(name);
             product.setLot(String.valueOf(lot));
             product.setAddon(String.valueOf(addon));
             product.setPrice(String.format("%.2f", price));
             product.setDisc(String.valueOf(disc));
             product.setTotal(String.format("%.2f", total));
             count++;
         }
		 if(product.getSku() == null){
			 String temp ="";
			 product.setSku(temp);
             product.setQty(temp);
             product.setScientific(temp);
             product.setSize(temp);
             product.setName(temp);
             product.setLot(String.valueOf(temp));
             product.setAddon(String.valueOf(temp));
             product.setPrice(temp);
             product.setDisc(temp);
             product.setTotal(temp);
		 }
		 if(count == 0){
			 String sql1 = "SELECT * FROM exoticre_order.products t1 where grxp = 'NOC' and t1.sku = '"+skus+"'   limit 1;";
			 ResultSet rs1 = DBConnection.getConnection().createStatement().executeQuery(sql1);
			 while (rs1.next()) {
	             String sku = rs1.getString("sku");
	             Integer qty = 1;
	             String size = rs1.getString("size");
	             String name = rs1.getString("description");
	             String lots = rs1.getString("lot");
	             lots = lots.replace("/Lot", "");
	             String scientific = rs1.getString("scientific");
	             Integer lot = 1;
	             if(lots.equals("")){
	            	 
	             }else{
	            	// lot = Integer.parseInt(lots.replace("/Lot", ""));
	             }
	             Integer addon = 0;
	             Float price = rs1.getFloat("price");
	             Integer disc =  0;
	             Float total = price*lot*qty ;
	             //System.out.println(sku+" "+ name+" "+size+" "+price);
	             
	             product.setSku(sku);
	             product.setScientific(scientific);
	             product.setQty(String.valueOf(qty));
	             product.setSize(size);
	             product.setName(name);
	             product.setLot(String.valueOf(lot));
	             product.setAddon(String.valueOf(addon));
	             product.setPrice(String.format("%.2f", price));
	             product.setDisc(String.valueOf(disc));
	             product.setTotal(String.format("%.2f", total));
	         }
		 }
		return product;
	}
	public ProductModel getProductBySkuInvoice(String skus) throws ClassNotFoundException, SQLException{
		ProductModel product = new ProductModel();
		if(skus.equals("")){
			skus = "nothing";
		}
		int count = 0;
		String sql = "SELECT t1.*,  t2.sku  as skus,t2.name  as names ,t2.size  as sizes ,t2.lot  as lots,t2.price  as prices,t3.scientific  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku left join exoticre_order.products t3 on t1.sku = t3.sku  where t1.sku = '"+skus+"' limit 1";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
             String sku = rs.getString("sku");
             Integer qty = 1;
             String size = rs.getString("size");
             String name = rs.getString("name");
             String lots = rs.getString("lot");
             lots = lots.replace("/Lot", "");
             String scientific = rs.getString("scientific");
             Integer lot = 1;
             if(lots.equals("")){
            	 
             }else{
            	// lot = Integer.parseInt(lots.replace("/Lot", ""));
             }
             Integer addon = 0;
             Float price = rs.getFloat("price");
             Float prices = rs.getFloat("prices");
             if(rs.getString("skus") != null){
            	 price = prices; 
             }
             Integer disc =  0;
             Float total = price*lot*qty ;
             
             product.setSku(sku);
             product.setScientific(scientific);
             product.setQty(String.valueOf(qty));
             product.setSize(size);
             product.setName(name);
             product.setLot(String.valueOf(lot));
             product.setAddon(String.valueOf(addon));
             product.setPrice(String.format("%.2f", price));
             product.setDisc(String.valueOf(disc));
             product.setTotal(String.format("%.2f", total));
             count++;
         }
		 if(product.getSku() == null){
			 String temp ="";
			 product.setSku(temp);
			 product.setScientific(temp);
             product.setQty(temp);
             product.setSize(temp);
             product.setName(temp);
             product.setLot(String.valueOf(temp));
             product.setAddon(String.valueOf(temp));
             product.setPrice(temp);
             product.setDisc(temp);
             product.setTotal(temp);
		 }
		 if(count == 0){
			 String sql1 = "SELECT * FROM exoticre_order.products t1 where grxp = 'NOC' and t1.sku = '"+skus+"'   limit 1;";
			 ResultSet rs1 = DBConnection.getConnection().createStatement().executeQuery(sql1);
			 while (rs1.next()) {
	             String sku = rs1.getString("sku");
	             Integer qty = 1;
	             String size = rs1.getString("size");
	             String name = rs1.getString("description");
	             String lots = rs1.getString("lot");
	             lots = lots.replace("/Lot", "");
	             String scientific = rs1.getString("scientific");
	             Integer lot = 1;
	             if(lots.equals("")){
	            	 
	             }else{
	            	// lot = Integer.parseInt(lots.replace("/Lot", ""));
	             }
	             Integer addon = 0;
	             Float price = rs1.getFloat("price");
	             Integer disc =  0;
	             Float total = price*lot*qty ;
	             //System.out.println(sku+" "+ name+" "+size+" "+price);
	             
	             product.setSku(sku);
	             product.setScientific(scientific);
	             product.setQty(String.valueOf(qty));
	             product.setSize(size);
	             product.setName(name);
	             product.setLot(String.valueOf(lot));
	             product.setAddon(String.valueOf(addon));
	             product.setPrice(String.format("%.2f", price));
	             product.setDisc(String.valueOf(disc));
	             product.setTotal(String.format("%.2f", total));
	         }
		 }
		return product;
	}
	
	public boolean checkProductBySku(ProductModel productModel) throws ClassNotFoundException, SQLException{
		boolean status = false;
		String sql ="SELECT t1.sku,t1.size,t1.name,t1.lot,t1.price, t2.sku  as skus,t2.price  as prices  FROM exoticre_order.masterProduct t1 left join exoticre_order.test t2 on t2.sku = t1.sku where t1.sku = '"+productModel.getSku()+"' or LOWER(t1.sku) = LOWER('%"+productModel.getSku()+"%')  limit 1;";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
			 status = true;
		 }
		return status;
	}
	public Integer addProduct(ProductModel productModel) throws ClassNotFoundException, SQLException{
		String sql = "INSERT INTO exoticre_order.masterProduct("
				+ "sku,"
				+ "name,"
				+ "size,"
				+ "lot,"
				+ "price)"
				+ "VALUES ('"
				+productModel.getSku()+"','"
				+productModel.getName()+"','"
				+productModel.getSize()+"','"
				+productModel.getLot()+"','"
				+productModel.getPrice()+"')";
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql);
		return status;
	}
	public Boolean delete(Integer id) throws ClassNotFoundException, SQLException {
		String sql1 = "DELETE FROM  exoticre_order.masterProduct  "
				+ "  WHERE id = "+id;       
		int status = DBConnection.getConnection().createStatement().executeUpdate(sql1);
		System.out.println("delete product   : "+status);
		return true;
	}
	public int update(ProductModel productModel) throws ClassNotFoundException, SQLException {
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
