package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {  
	   protected static Connection conn;  
	   protected static Connection connLc;  
	   private static String url = "jdbc:mysql://69.28.84.78:3306/exoticre_order";  
	   private static String urlLc = "jdbc:mysql://127.0.0.1:3306/exoticre_order"; 
	   private static String user = "exoticre_wrdp1";//Username of database  
	   private static String userLc = "userLc";//Username of database  
	   private static String pass = "sZhFzgaLl-5_";//Password of database  
	   private static String passLc = "passLc";//Password of database 

	   public static Connection connect() throws SQLException{  
	     try{  
	       Class.forName("com.mysql.jdbc.Driver").newInstance();  
	     }catch(ClassNotFoundException cnfe){  
	       System.err.println("Error: "+cnfe.getMessage());  
	     }catch(InstantiationException ie){  
	       System.err.println("Error: "+ie.getMessage());  
	     }catch(IllegalAccessException iae){  
	       System.err.println("Error: "+iae.getMessage());  
	     }  
	     conn = DriverManager.getConnection(url,user,pass);  
		System.out.println("connect db online done.");
	     return conn;  
	   }  
	   public static Connection connectLc() throws SQLException{  
		     try{  
		       Class.forName("com.mysql.jdbc.Driver").newInstance();  
		     }catch(ClassNotFoundException cnfe){  
		       System.err.println("Error: "+cnfe.getMessage());  
		     }catch(InstantiationException ie){  
		       System.err.println("Error: "+ie.getMessage());  
		     }catch(IllegalAccessException iae){  
		       System.err.println("Error: "+iae.getMessage());  
		     }  
		     connLc = DriverManager.getConnection(urlLc,userLc,passLc);  
			System.out.println("connect db local done.");
		     return conn;  
		   }  
	   public static Connection getConnection() throws SQLException, ClassNotFoundException{  
	     if(conn !=null && !conn.isClosed())  
	       return conn;  
	     connect();  
	     return conn;  
	   }  
	   public static Connection getConnectionLc() throws SQLException, ClassNotFoundException{  
		     if(connLc !=null && !connLc.isClosed())  
		       return connLc;  
		     connectLc();  
		     return connLc;  
		}  
	 }  