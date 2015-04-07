package clueGame;

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
		return getCards().get(1); //TODO: gui prompt to return a disproving card
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
