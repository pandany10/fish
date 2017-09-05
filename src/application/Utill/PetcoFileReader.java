package application.Utill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import application.Utill.MoveFiles;
import application.Dao.OrderDao;
import application.Dao.ProductQbDao;
import application.Model.ProductModel;
//import application.Utill.qbComm; 

public class PetcoFileReader {
 private static final Object[] String = null;
private BufferedReader b;
private ProductQbDao keySearch;

public int DownloadOrders()throws IOException, ClassNotFoundException, SQLException {

   String po         = "";
   String odate      = "";
   String StoreNumber= "";
   String StoreName  = "";
   String StoreAdd   = "";
   String StoreCity  = "";
   String StoreState = "";
   String StoreZip   = "";
   String quantity   = "";
String unit          = "";
String price         = "";
String sku           = "";
String productName   = "";
 File f = null;
//File[] paths;
   OrderDao porder = new OrderDao();

  try {  
	 // qbComm Petcosend = new qbComm();
	  //send to QB Web Order Products 
	//  ProductQbDao websend = new ProductQbDao();
	    keySearch = null;
		Object str_filters = null;
	//	List<ProductModel> lstProduct = websend.getListProduct();
//		int prods = lstProduct.size();
//		for(int i=0;i<prods;i++) {
	//	sku = lstProduct.get(i).getSku();
	//	productName = lstProduct.get(i).getName();
	//	Double unitp = Double.parseDouble(lstProduct.get(i).getPrice());
	//	System.out.println("SENDING product to QB: sku= "+sku+",Product Name= "+productName+ ",Price= "+unitp);
		if(sku.length()>0 && productName.length()>0) {
     //  Petcosend.itemInventoryAdd(sku, productName, unitp);
		}
		 //System.out.println("RESULT:"+lstProduct.get(i).getSku());
	//	}
  
	  int order_id=0;
	  File[] files = new File("petco/download/").listFiles(new FilenameFilter() { @Override public boolean accept(File dir, String name) { return name.endsWith(".txt"); } });  
     for(int z = 0;z<files.length;z++){
    	 
   
    	 order_id = porder.getLastNewOrderId(); 
   	  System.out.println("New Order will be :" + Integer.toString(order_id));
    	
    	String order[]        = new String[100000];
        String petcoorder[][] = new String[10000][13];
        int i = 0;
        int k = 0;
        int l = 0;

         //  f =   new File("petco/download/ffd01680.txt"); 
        // File f =   new File(args[0]); 
          f =   files[z]; 
            b = new BufferedReader(new FileReader(f));

            String readLine = "";
           
            System.out.println("Reading file using Buffered Reader");

            while ((readLine = b.readLine()) != null) {
    
              order[i] = readLine;
              i++;
                  }
 //System.out.println(order[0]);
for(int j=0;j<100000;j++){
       if(order[j] == null){j=100000;}else{
         //  System.out.println(order[j]);
         //get order info into an array
             order[j]  = order[j] .replace("\"","");
             String[] line= order[j] .split(","); 
               if(line[0].equals("ORDER")){ 
 // System.out.println("Array Size: " + line.length); 
                  po    = line[1]; //PO NUMBER
                  odate = line[2]; //Order date
                  j=j+5;
            continue;
                 } 
//System.out.println(Arrays.toString(line)); 
           if(line[0].equals("ORDER_ADDRESS")){
              if(line[1].equals("ST")){ 
//System.out.println("Array Size: " + line.length);
            	  if(line[3].length()==3) {
               StoreNumber = " 12"+line[3]; //Store Number
            	  }else {

            		  StoreNumber = "12"+line[3]; //Store Number     
            	  }
               StoreName   = line[6]; //Store Name
               StoreAdd    = line[9]; //Store address
               StoreCity   = line[11]; //Store City
               StoreState  = line[12]; //Store State
               StoreZip    = line[13]; //Store ZipCode
               continue;
                }
              
               }  
  if(line[0].equals("ORDER_NOTE") || line[0].equals("ORDER_REF_NUM")){  continue; }
   
            if(line[0].equals("ORDER_END")){ j=100000;}else{
               if(line[0].equals("ITEM")){
                  String m =Integer.toString(k);
//System.out.println(line[1]);
                 if(line[1].equals(m)){
//System.out.println(m);
                  quantity = line[6];
                  unit     = line[7];
                  price    = line[8];
                  j++; 
                   continue;
                     }
                   }         
               if(line[0].equals("ITEM_ID") && line[2].equals("VN")  ){
                  sku = line[1];
                 continue;     
                   } 
                 // System.out.println(Arrays.toString(line));          
                 if(line[0].equals("ITEM_ID") && line[2].equals("PD") ){
                  productName = line[1];
                  Date myDate = new Date();
          		String date = new SimpleDateFormat("yyyy-MM-dd").format(myDate);
                    Double iqty = new Double(quantity);
               //   int qty = iqty.intValue();
                 double iprice = Double.parseDouble(price);
                 double total = iqty*iprice;
              String ordertoinsert = "INSERT INTO orders(Customer_ship,Size,awb,fob,fcb,fcbw,tpacks,rockb,rockw,totalp,dfb,dfbw,totalb,Item,Lot,Addon,disc,commission,readyPayment,issued,order_id,purchase_order,notes,Airport,Customer_comments,Product_id,Date,Customer_email,saleperson_email,Product_name,Customer_date,Product_Sku,Price,Total,All_Total,ClientCustomerID,ordertype) "
              		+ "VALUES(\"UPS\",\"STD\",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,\""+order_id+"\",\""+po+"\",\"PETCO\",\"ZZZ\",\"NA\",\"0\",\""+date+"\",\"orders@petco.com\",\"ERIfish@aol.com'\",\""+productName+"\",\""+odate+"\",\""+sku+"\",\""+iprice+"\",\""+total+"\",\""+total+"\",\""+StoreNumber+"\",\"PETCO\")";    
//System.out.println(ordertoinsert);
porder.addPetcoOrder(ordertoinsert,order_id);
//Petcosend.itemInventoryAdd(sku,productName,Double.parseDouble(price));

 petcoorder[l][0] = po;
 petcoorder[l][1] = odate;
 petcoorder[l][2] = StoreNumber;
 petcoorder[l][3] = StoreName;
 petcoorder[l][4] = StoreAdd;
 petcoorder[l][5] = StoreCity;
 petcoorder[l][6] = StoreState;
 petcoorder[l][7] = StoreZip;
 petcoorder[l][8] = quantity;
 petcoorder[l][9] = unit;
 petcoorder[l][10] = price;
 petcoorder[l][11] = sku;
 petcoorder[l][12] = productName;
                   l++;
                   k++;
                   j++; 
                continue;     
                     }    
                
                  
        
             }  
           
          }
    
  }
    int n = 0;
   for(n=0; n<100000;n++){
    if(petcoorder[n][1] ==null){ break;}
}
  String[][] tempStringArray = Arrays.copyOf(petcoorder, 6);    
   petcoorder = tempStringArray;
 
   System.out.println(Arrays.deepToString(petcoorder)); 
     
b.close();
// files[z].delete();
 File newFile = new File(files[z]+".bak");
if(files[z].renameTo(newFile)){
    System.out.println("File rename success");;
}else{
    System.out.println("File rename failed");
}
    System.out.println("File is copied successful!");

   
 
//MoveFiles m = new MoveFiles();


//File a = m.Moveit(files[z].getName());

  // files[z].delete();
  // FileUtils.deleteDirectory(new File("C:/Users/PADTECH/eclipse-workspace/FishProv2/petco/download/ffd01681.txt"));

// System.out.println("file reading " + files[z].getName());
 }
     
     
     } catch (IOException e) {
            e.printStackTrace();
        }

 //}
return 1;
}

}

