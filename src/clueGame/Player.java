package clueGame;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private ArrayList<Card> myCards;
	
	public Player(String name){
		this.name = name;
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
	
	public boolean isHuman(){
		return false;
	}
	
	public boolean isComputer(){
		return false;
	}
}
