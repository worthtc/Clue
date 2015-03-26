package clueGame;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Player {
	private String name;
	private Color color;
	private ArrayList<Card> myCards;
	private int currentCol, currentRow;
	
	public Player(String name, String color, int startRow, int startCol){
		this.name = name;
		this.color = convertColor(color);
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
	
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}
		return color;
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
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isHuman(){
		return false;
	}
	
	public boolean isComputer(){
		return false;
	}
}
