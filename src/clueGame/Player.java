package clueGame;
import java.util.ArrayList;

public class Player {
	private String name;
	private String color;
	private ArrayList<Card> myCards;
	private int currentCol, currentRow;
	
	public Player(String name, String color, int startRow, int startCol){
		this.name = name;
		this.color = color;
		currentRow = startRow;
		currentCol = startCol;
		myCards = new ArrayList<Card>();
	}
	public Player(Player player){
		name = player.getName();
		color = player.getColor();
		currentRow = player.getCurrentRow();
		currentCol = player.getCurrentCol();
		myCards = new ArrayList<Card>();
	}

	public int getCurrentRow() {
		return currentRow;
	}
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}
	public int getCurrentCol() {
		return currentCol;
	}
	public void setCurrentCol(int currentCol) {
		this.currentCol = currentCol;
	}
	//This method should be specific to ComputerPlayer, and when the GUI is developed it will be moved there
	public Card disproveSuggestion(String person, String room, String weapon){
		ArrayList<Card> matches = new ArrayList<Card>();
		for (Card c : myCards){
			if(c.getName().equals(person) || c.getName().equals(room) || c.getName().equals(weapon)) matches.add(c);
		}
		if(matches.size() != 0){
			int choice = (int)(Math.random()*matches.size());
			return matches.get(choice);
		}
		else return null;
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
