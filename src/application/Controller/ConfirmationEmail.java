package application.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
 public class ConfirmationEmail extends Stage {
            Stage owner;
            Stage stage;
            BorderPane root;
            static boolean postStatus;
            boolean chkClose = false;
            public ConfirmationEmail( Stage owner, String title){
            root = new BorderPane();
            stage = this;
            this.owner = owner;
            initModality( Modality.APPLICATION_MODAL );
            initOwner( owner );
            initStyle( StageStyle.UTILITY ); 
            setTitle( title );
            setContents();
            }
 
            public void setContents(){
 
            Scene scene = new Scene(root,350,120);
            setScene(scene);
 
            Group groupInDialog = new Group();
            groupInDialog.getChildren().add( new Label("Send Email to?") );
            root.setCenter( groupInDialog );
 
 
            Button yes = new Button( "Send to Customer" );
            yes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    postStatus =true;
                    stage.close(); // Close the pop up. Transfer control to   PostTransaction.java and execute the PostTransaction() method. 
                }
            });
            yes.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            	@Override
    			public void handle(KeyEvent event) {
            		if(event.getCode() == KeyCode.ENTER){
            			 postStatus =true;
                         stage.close(); 
            		}
            	}
        	});
 
 
            Button no  = new Button( "Send To" );
            no.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    postStatus =false;
                    stage.close(); // Close the pop up only
                }
                 
            });
            no.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            	@Override
    			public void handle(KeyEvent event) {
            		if(event.getCode() == KeyCode.ENTER){
            			 postStatus =false;
                         stage.close(); 
            		}
            	}
        	});
 
 
            HBox buttonPane = new HBox();
            buttonPane.setSpacing(10);
            buttonPane.getChildren().addAll(yes,no);
            buttonPane.setAlignment(Pos.CENTER);
            Insets u = new Insets(0.0f,0.0f,4.0f,0.0f);
            buttonPane.setPadding(u);
            root.setBottom(buttonPane);
 
        //   stage.show();
            }
 
              public static boolean confirmTranactionPosting(Stage owner, String title) {
 
              new ConfirmationEmail(owner, title);
              System.out.println(postStatus);
              return postStatus;
            }
 
}