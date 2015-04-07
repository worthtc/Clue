package clueGame;

import java.awt.Rectangle;
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
		return getCards().get(1); //TODO: gui prompt to return a disproving card
	}
	@Override
	public void makeAMove(Set<BoardCell> targetSet, Board board) {
		isFinished = false;
		ArrayList<Rectangle> targets = new ArrayList<Rectangle>();
		for( BoardCell b: targetSet ){
			b.setHighlighted(true);
			targets.add(new Rectangle((int)(b.getColumn()*board.getCellSize().getWidth()),(int)(b.getRow()*board.getCellSize().getHeight()), (int)(board.getCellSize().getWidth()),(int)(board.getCellSize().getHeight())));
		}
		/*while( isFinished == false ){
			//We wait for the target to be selected
			isFinished = board.isTargetSelected();
		}
		board.setTargetSelected(false);
		for( BoardCell b: targetSet ){
			b.setHighlighted(false);
		}*/
		
	}
}
