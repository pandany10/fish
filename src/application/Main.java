package application;

import java.io.IOException;
import java.sql.SQLException;

import application.Controller.LoginController;
import application.Utill.Pdf;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException, SQLException {
		
		   primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		   primaryStage.setTitle("FishPro v2");
		   DBConnection.connect(); // connect db
		 //  DBConnection.connectLc(); // connect db
		   FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Login.fxml"));
		  // Pdf pdf = new Pdf();
			//pdf.PrintInvoice(orderDetail);
		   Pane myPane = (Pane)myLoader.load();

		   LoginController controller = (LoginController) myLoader.getController();

		   controller.setPrevStage(primaryStage);

		   Scene myScene = new Scene(myPane);        
		   primaryStage.setScene(myScene);
		   primaryStage.setResizable(false);
		   primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
