package clueGame;

import java.util.ArrayList;
import java.util.Set;

public class HumanPlayer extends Player{
	private boolean isFinished;
	
	public boolean isFinished() {
		return isFinished;
	}
	
	public HumanPlayer(String name, String color, int startRow, int startCol){
		super(name, color, startRow, startCol);
		isFinished = true;
	}
	public HumanPlayer(Player player){
		super(player); 
		isFinished = true;
	}
	@Override
	public boolean isHuman(){
		return true;
	}
	public Card disproveSuggestion(String person, String room, String weapon){
		// We do not need a gui to pick a card here, we just return one of the cards of the human player
		//return getCards().get(1); //TODO: gui prompt to return a disproving card
		ArrayList<Card> matches = new ArrayList<Card>();
		for (Card c : getCards()){
			if(c.getName().equals(person) || c.getName().equals(room) || c.getName().equals(weapon)) matches.add(c);
		}
		if(matches.size() != 0){
			int choice = (int)(Math.random()*matches.size());
			return matches.get(choice);
		}
		else return null;
	}
	@Override
	public void makeAMove(Set<BoardCell> targetSet) {
		isFinished = false;
		for( BoardCell b: targetSet ){
			b.setHighlighted(true);
		}
		
	}
	public void finishMove(BoardCell chosenCell, Set<BoardCell> targetSet){
		
		setCurrentRow(chosenCell.getRow());
		setCurrentCol(chosenCell.getColumn());
		chosenCell.setPlayerColor(getColor());
		chosenCell.setIsOccupied(true);
		isFinished = true;
	}
}
