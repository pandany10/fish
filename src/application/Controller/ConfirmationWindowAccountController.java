package application.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
 
 public class ConfirmationWindowAccountController  implements Initializable {
            Stage owner;
            Stage stage;
            public static boolean postStatus;
            @FXML
        	public Button btnkok;
            @FXML
        	public Button btncancle;
            @FXML
        	public TextField tx;
            
            @Override
            public void initialize(URL location, ResourceBundle resources) {
            	setContents();
            }
            public ConfirmationWindowAccountController( Stage owner, String title){
            this.owner = owner;
            setContents();
            }
            public void ConfirmationWindowAccounts( Stage owner, Stage stages){
                this.owner = owner;
                this.stage = stages;
              //  setContents();
                }
            public static void addTextLimiter(final TextField tf, final int maxLength) {
                tf.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                        if (tf.getText().length() > maxLength) {
                            String s = tf.getText().substring(0, maxLength);
                            tf.setText(s);
                        }
                    }
                });
            }
     public void setContents(){
            tx.textProperty().addListener((ov, oldValue, newValue) -> {
            	tx.setText(newValue.toUpperCase());
           });
            addTextLimiter(tx, 1);

            btnkok.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
        			if(tx.getText().equals("E")||tx.getText().equals("P")||tx.getText().equals("V")){
        				postStatus =true;
        				stage.close(); // Close the pop up. Transfer control to   PostTransaction.java and execute the PostTransaction() method.
        			}
                }
            });
            btnkok.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            	@Override
    			public void handle(KeyEvent event) {
            		if(event.getCode() == KeyCode.ENTER){
            			if(tx.getText().equals("E")||tx.getText().equals("P")||tx.getText().equals("V")){
               			 postStatus =true;
                         stage.close(); 
            			}
            		}
            	}
        	});
 
 
            btncancle.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    postStatus =false;
                    stage.close(); // Close the pop up only
                }
                 
            });
            btncancle.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            	@Override
    			public void handle(KeyEvent event) {
            		if(event.getCode() == KeyCode.ENTER){
            			 postStatus =false;
                         stage.close(); 
            		}
            	}
        	});
 
            tx.requestFocus();
        //   stage.show();
            }
 

 
}