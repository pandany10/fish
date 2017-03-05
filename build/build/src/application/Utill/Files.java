package application.Utill;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Files {
	public void Write(String content,String name) {
		 try {
			  
		    File file = new File(name);
		
		    // if file doesnt exists, then create it
		    if (!file.exists()) {
		        file.createNewFile();
		    }
		    FileWriter fw = new FileWriter(file.getAbsoluteFile());
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(content);
		    bw.close();
			
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	public String read(String name) throws IOException 
    {
		String text = "";
        FileInputStream f = new FileInputStream(name); 
        Scanner input = new Scanner(f,"UTF-8"); 
 
        while(input.hasNextLine()){
        	text = text + input.nextLine();
        }
        input.close();
        return text;
    }
}
