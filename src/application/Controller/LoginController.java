package application.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.json.JSONException;
import org.json.JSONObject;

import application.Utill.EnDeCryption;
import application.Utill.Files;
import application.Utill.InvalidCertificateHostVerifier;
import application.Utill.InvalidCertificateTrustManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    Stage prevStage;
    EnDeCryption objEnDe = new EnDeCryption("sakglj-sklagiwqk@9238A");
    public void setPrevStage(Stage stage){
         this.prevStage = stage;
    }

    @FXML
	private Button btnLogin;

	@FXML
	private TextField txtUser;

	@FXML
	private PasswordField txtPW;

	@FXML
	private CheckBox chkLg;

	@FXML
	private Label lbl;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Files files = new Files();
		String remember_login = "";
		String user = "";
		String pw = "";
		try {
			remember_login = files.read("remember_login.txt");
			user = files.read("user.txt");
			pw = objEnDe.decoding(files.read("pw.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(remember_login.indexOf("true") != -1){
			chkLg.setSelected(true);
			txtUser.setText(user);
			txtPW.setText(pw);	
		}
	}

	 
	/**
	 * @param event
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws JSONException
	 */
	public void Login(ActionEvent event) throws KeyManagementException, NoSuchAlgorithmException, IOException, JSONException{
		Login();
	}
	public void Login() throws KeyManagementException, NoSuchAlgorithmException, IOException, JSONException{
		/*System.out.println("User: " + txtUser.getText());
		System.out.println("PW: " + txtPW.getText());
		System.out.println("remember login:" + String.valueOf(chkLg.selectedProperty().get()));*/
		String user = txtUser.getText();
		String pw = txtPW.getText();
		String remember_login = String.valueOf(chkLg.selectedProperty().get());
		Files files = new Files();
		files.Write(remember_login, "remember_login.txt");
		files.Write(user, "user.txt");
		String pwEncryption  = objEnDe.encoding(pw);
		files.Write(pwEncryption, "pw.txt");
		Boolean validate = validateLogin(user, pw);
		//System.out.println(validate);
		if (validateLogin(user, pw) == true) {
			URL url = new URL("https://www.exoticreefimports.com/api/user/generate_auth_cookie/?username=" + user
					+ "&password=" + pw);
			HttpsURLConnection connection = getConnection(true, url);
			InputStream content = (InputStream) connection.getInputStream();
			String result = getStringFromInputStream(content);
			JSONObject jsonObject = new JSONObject(result);
			String status = jsonObject.getString("status");
			if (status.equals("ok")) {
				lbl.setText("Login success.");
				gotoHome();
			} else {
				lbl.setText("Username or password is incorrect.");
			}
			System.out.println("login:"+status);
		} else {
			lbl.setText("User name or password may not be empty.");
		}
	}
	public Boolean validateLogin(String user, String pw) {
		Boolean rt = false;
		if (user.length() != 0 && pw.length() != 0) {
			rt = true;
		}
		return rt;
	}

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	public static HttpsURLConnection getConnection(boolean ignoreInvalidCertificate, URL url)
			throws KeyManagementException, NoSuchAlgorithmException, IOException {
		SSLContext ctx = SSLContext.getInstance("TLS");
		if (ignoreInvalidCertificate) {
			ctx.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
		}
		SSLContext.setDefault(ctx);

		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		connection.setRequestMethod("GET");
		connection.setDoOutput(true);

		if (ignoreInvalidCertificate) {
			connection.setHostnameVerifier(new InvalidCertificateHostVerifier());
		}

		return connection;
	}
    public void gotoHome() throws IOException {
       Stage stage = new Stage();
       stage.setTitle("Home");
       stage.getIcons().add(new Image("file:resources/images/icon.png"));
	   FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/application/View/Home.fxml"));
	   Pane myPane = (Pane)myLoader.load();

       HomeController controller = (HomeController) myLoader.getController();
	   controller.setPrevStage(stage);
       Scene scene = new Scene(myPane);
       stage.setScene(scene);

       prevStage.close();
       stage.setResizable(false);
       stage.show();
       scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
   	    @Override
   	    public void handle(KeyEvent evt) {
   	        if (evt.getCode().equals(KeyCode.DIGIT1) || evt.getCode().equals(KeyCode.NUMPAD1)) {
   	        	controller.btnOrder.requestFocus();
   	        }
   	        if (evt.getCode().equals(KeyCode.DIGIT2) || evt.getCode().equals(KeyCode.NUMPAD2)) {
   	        	controller.btnCustomer.requestFocus();
   	        }
   	        if (evt.getCode().equals(KeyCode.DIGIT3) || evt.getCode().equals(KeyCode.NUMPAD3)) {
   	        	controller.btnProduct.requestFocus();
   	        }
   	        if (evt.getCode().equals(KeyCode.DIGIT4) || evt.getCode().equals(KeyCode.NUMPAD4)) {
   	        	controller.btnSales.requestFocus();
   	        }
   	        if (evt.getCode().equals(KeyCode.DIGIT5) || evt.getCode().equals(KeyCode.NUMPAD5)) {
   	        	controller.btnInvoice.requestFocus();
   	        }
   	        if (evt.getCode().equals(KeyCode.DIGIT6) || evt.getCode().equals(KeyCode.NUMPAD6)) {
	        	controller.btnTemp.requestFocus();
	        }
   	     if (evt.getCode().equals(KeyCode.DIGIT7) || evt.getCode().equals(KeyCode.NUMPAD7)) {
	        	controller.btnPayment.requestFocus();
	        }
   	    }
   	});
    }

}