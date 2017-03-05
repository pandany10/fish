package application.Model;

public class CardModel {
	private Integer id;
	private String name;
	private String card;
	private String type;
	private String expired;
	private String cvc;
	private Boolean cdefault = false;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public Boolean getCdefault() {
		return cdefault;
	}
	public void setCdefault(Boolean cdefault) {
		this.cdefault = cdefault;
	}

	
}
