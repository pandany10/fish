package application.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController implements Initializable {

    Stage prevStage;
    Connection c ; 
    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }
    @FXML
	private Button btnOrder;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnOrder.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	            	try {
						gotoOrders();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	            }
	        }
	    });
	}

    public void gotoLogin(ActionEvent event) throws IOException {          
       Stage stage = new Stage();
       stage.setTitle("Login");
       stage.getIcons().add(new Image("file:resources/images/icon.png"));
       FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Login.fxml"));
       Pane myPane = (Pane)myLoader.load();
       
       LoginController controller = (LoginController) myLoader.getController();
	   controller.setPrevStage(stage);
	   
       Scene scene = new Scene(myPane);
       stage.setScene(scene);
       prevStage.close();
       stage.setResizable(false);
       stage.show();
    }
    public void gotoOrders(ActionEvent event) throws IOException {          
    	gotoOrders();
     }
    public void gotoOrders() throws IOException {          
        Stage stage = new Stage();
        stage.setTitle("Orders");
        stage.getIcons().add(new Image("file:resources/images/icon.png"));
        FXMLLoader myLoader  = new  FXMLLoader(getClass().getResource("/application/View/Orders.fxml"));
        Pane myPane = (Pane)myLoader.load();
        
        OrdersController controller = (OrdersController) myLoader.getController();
 	    controller.setPrevStage(stage);
        Scene scene = new Scene(myPane);
        stage.setScene(scene);
        prevStage.close();
        stage.setResizable(false);
        stage.show();
     }

}