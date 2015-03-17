package clueGame;
import java.util.ArrayList;

public class Player {
	private String name;
	private String color;
	private int startRow;
	private int startCol;
	private ArrayList<Card> myCards;
	
	public Player(String name, String color, int startRow, int startCol){
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startCol = startCol;
		myCards = new ArrayList<Card>();
	}

	public Card disproveSuggestion(String person, String room, String weapon){
		return new Card("");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void giveCard(Card c){
		myCards.add(c);
	}
	
	public ArrayList<Card> getCards(){
		return myCards;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isHuman(){
		return false;
	}
	
	public boolean isComputer(){
		return false;
	}
}
