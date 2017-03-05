package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {  
	   private static Connection conn;  
	   private static String url = "jdbc:mysql://69.28.84.78:3306/exoticre_order";  
	   private static String user = "exoticre_wrdp1";//Username of database  
	   private static String pass = "sZhFzgaLl-5_";//Password of database  
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
	     return conn;  
	   }  
	   public static Connection getConnection() throws SQLException, ClassNotFoundException{  
	     if(conn !=null && !conn.isClosed())  
	       return conn;  
	     connect();  
	     return conn;  
	   }  
	 }  