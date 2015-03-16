package clueGame;

public class Card {
	private String name;
	public enum CardType{ROOM,WEAPON,PERSON};
	private CardType type;
	
	public Card(String name){
		this.name = name;
	}
	
	public Card(String name, CardType type){
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}
	
	
}