package application.Utill;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

 
public class MoveFiles{
	public String getReasonForFileDeletionFailureInPlainEnglish(File file) {
	    try {
	        if (!file.exists())
	            return "It doesn't exist in the first place.";
	        else if (file.isDirectory() && file.list().length > 0)
	            return "It's a directory and it's not empty.";
	        else
	            return "Somebody else has it open, we don't have write permissions, or somebody stole my disk.";
	    } catch (SecurityException e) {
	        return "We're sandboxed and don't have filesystem access.";
	    }
	}  
	
public String Moveit(String f){	
 
        InputStream  inStream   = null;
     	OutputStream outStream1 = null;
        String LOC1 = null;
        String LOC2 = null;
        String a = "";
 
    	try{
            LOC1 = "petco/download"; //rb.getString("LOC1");
            LOC2 = "petco/backup/download"; //rb.getString("LOC2");
           // String ff = f.getAbsolutePath();
    	    File afile =new File(LOC1 + "/" + f);
    	    File bfile =new File(LOC2 + "/" + f);
    	    File cfile =new File(LOC1 + "/" + f + ".bak");
             // File cfile =new File(f);
    	    try {
    	    inStream   = new FileInputStream(afile);
    	    outStream1 = new FileOutputStream(bfile);
           // outStream2 = new FileOutputStream(cfile);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    //copy the file content in bytes 
    	      while ((length = inStream.read(buffer)) > 0){
 
       	      outStream1.write(buffer, 0, length);
             // outStream2.write(buffer, 0, length);
 
         	    }
    	  /*  outStream1.flush();
    	    inStream.close();
    	    outStream1.close();
    	   // outStream1 = null;
            System.gc();*/

    	    
    
    	    
             	    //delete the original file
            a = afile.getAbsolutePath(); 
    	 String b = cfile.getAbsolutePath(); 
    	 System.out.println("ORDER:     " +a);
    	 System.out.println("ORDER ARC: " +b);
          File c = new File(a);  
          File d = new File(b);  
          IOUtils.copy(new FileInputStream(c), new FileOutputStream(d)); 

          //FileUtils.deleteDirectory(new File("C:/Users/PADTECH/eclipse-workspace/FishProv2/petco/download/ffd01681.txt"));
         // afile.c
    	  } finally {
    		  outStream1.flush();
      	    inStream.close();
      	    outStream1.close();
      	   // outStream1 = null;
              System.gc();
              
              File c = new File(a);
              c.setWritable(true);
              String path = c.getCanonicalPath();
              File filePath = new File(path);
              if(filePath.delete()){
      			System.out.println(c.getName() + " is deleted!");
      		}else{
      			System.out.println("Delete operation is failed.");
      		  throw new IOException(
      		        "Failed to delete the file because: " +
      		        getReasonForFileDeletionFailureInPlainEnglish(filePath));
      		}
              System.out.println("File rename success");;
    	  }
    	 
          /*     if(c.renameTo(d)){
            System.out.println("File rename success");;
        }else{
            System.out.println("File rename failed");
        }*/
    	  //  System.out.println("File is copied successful!");
 
    	    }catch(IOException e){
    	    e.printStackTrace();
    	    }
     return a;
     }
}