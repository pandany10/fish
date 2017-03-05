package application;

import java.io.IOException;

import application.Controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		   primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		   primaryStage.setTitle("FishPro v2");

		   FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Login.fxml"));

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
