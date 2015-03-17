package clueGame;
import java.util.Set;

public class ComputerPlayer extends Player{
	private char lastRoomVisitied;
	
	public ComputerPlayer(String name, String color, int startRow, int startCol){
		super(name, color, startRow, startCol);
	}
	
	public void pickLocation(Set<BoardCell> targets){
		
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
