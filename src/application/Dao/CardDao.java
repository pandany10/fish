package application.Dao;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import application.DBConnection;
import application.Model.CardModel;
import application.Utill.lorenz;
public class CardDao {
	public List<CardModel> getLstCard(String CustomerId) throws ClassNotFoundException, SQLException{
		lorenz lorenz = new lorenz();
		List<CardModel> lstCard = new ArrayList<>();
		String sql = "SELECT * FROM payment t1 LEFT JOIN customerfishpro t2 ON t1.user_email = t2.Email WHERE t2.CustomerID='"+CustomerId+"'";
		ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
		 while (rs.next()) {
           CardModel cardModel = new CardModel();
           Integer id = rs.getInt("id");
           String Name = rs.getString("card_holder");
           String type = rs.getString("card_type");
           String expired = rs.getString("card_expired");
           Integer cdefault = rs.getInt("card_default");
           
          // String card = rs.getString("card_number");
          // String cvc = rs.getString("card_cvv");
           //System.out.println(id);

           try {
			String c = lorenz.lorenz_decrypt(id);
			JSONObject jsonObject = new JSONObject(c);
			//System.out.println(jsonObject.getString("card_number"));
			//System.out.println(jsonObject.getString("card_cvv"));
			cardModel.setCard(jsonObject.getString("card_number"));
			cardModel.setCvc(jsonObject.getString("card_cvv"));

		} catch (KeyManagementException | NoSuchAlgorithmException | IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           cardModel.setId(id);
           cardModel.setName(Name);
           cardModel.setType(type);
           cardModel.setExpired(expired);
           cardModel.setCdefault(cdefault==1?true:false);
           
           
           
           lstCard.add(cardModel);
        }
		return lstCard;
	}

}
