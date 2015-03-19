package clueGame;
import java.util.Set;

public class ComputerPlayer extends Player{
	private char lastRoomVisitied;
	
	public ComputerPlayer(String name, String color, int startRow, int startCol){
		super(name, color, startRow, startCol);
	}
	public ComputerPlayer(Player player){
		super(player);
	}
	public BoardCell pickLocation(Set<BoardCell> targets){
		int choice = (int)(Math.random()*targets.size());
		BoardCell[] tar = targets.toArray(new BoardCell[1]);
		return tar[choice];
	}
	
	public void createSuggestion(){
		
	}
	
	public void updateSeen(){
		
	}
	
	@Override
	public boolean isComputer(){
		return true;
	}
}
