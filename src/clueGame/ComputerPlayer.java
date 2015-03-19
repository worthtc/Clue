package clueGame;
import java.util.*;

public class ComputerPlayer extends Player{
	private char lastRoomVisitied;
	private Set<Card> masterListCards;
	
	public ComputerPlayer(String name, String color, int startRow, int startCol){
		super(name, color, startRow, startCol);
		masterListCards = new HashSet<Card>();
	}
	public ComputerPlayer(Player player){
		super(player);
	}
	public BoardCell pickLocation(Set<BoardCell> targets){
		int choice = (int)(Math.random()*targets.size());
		BoardCell[] tar = targets.toArray(new BoardCell[1]);
		return tar[choice];
	}
	
	public HashSet<Card> createSuggestion(){
		return (HashSet<Card>) masterListCards;
	}
	
	public void updateSeen(){
		
	}
	
	@Override
	public boolean isComputer(){
		return true;
	}
	
	public char getLastRoomVisitied() {
		return lastRoomVisitied;
	}
	public void setLastRoomVisitied(char lastRoomVisitied) {
		this.lastRoomVisitied = lastRoomVisitied;
	}
	public Set<Card> getMasterListCards() {
		return masterListCards;
	}
	public void setMasterListCards(Set<Card> seenCards) {
		this.masterListCards = seenCards;
	}
}
