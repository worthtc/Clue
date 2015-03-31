package clueGame;
import java.util.*;

public class ComputerPlayer extends Player{
	private char lastRoomVisited;
	private Set<Card> masterListCards;
	
	public ComputerPlayer(String name, String color, int startRow, int startCol){
		super(name, color, startRow, startCol);
		masterListCards = new HashSet<Card>();
	}
	public ComputerPlayer(Player player){
		super(player);
	}
	public BoardCell pickLocation(Set<BoardCell> targets){
		// Check to see if one of the targets is a room that we were not just in
		for( BoardCell roomTest: targets){
			if( roomTest.isRoom() && ((RoomCell)roomTest).getInitial()  != lastRoomVisited){
				return roomTest;
			}
		}
		//Otherwise we just pick a board Cell at random
		Object[] targetArray = targets.toArray();
		return ((BoardCell)targetArray[(int) (Math.random()*targets.size())]);
	}
	
	public Set<Card> createSuggestion(){
		ArrayList<Card> personOptions = new ArrayList<Card>();
		ArrayList<Card> roomOptions = new ArrayList<Card>();
		ArrayList<Card> weaponOptions = new ArrayList<Card>();
		for(Card c : masterListCards){
			if(c.getType() == Card.CardType.PERSON) personOptions.add(c);
			else if(c.getType() == Card.CardType.ROOM) roomOptions.add(c);
			else weaponOptions.add(c);
		}
		Set<Card> suggestion = new HashSet<Card>();
		int choice = (int)(Math.random()*personOptions.size());
		suggestion.add(personOptions.get(choice));
		choice = (int)(Math.random()*roomOptions.size());
		suggestion.add(roomOptions.get(choice));
		choice = (int)(Math.random()*weaponOptions.size());
		suggestion.add(weaponOptions.get(choice));
		return suggestion;
	}
	
	public void updateSeen(){
		
	}
	
	@Override
	public boolean isComputer(){
		return true;
	}
	
	public char getLastRoomVisitied() {
		return lastRoomVisited;
	}
	public void setLastRoomVisitied(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}
	public Set<Card> getMasterListCards() {
		return masterListCards;
	}
	public void setMasterListCards(Set<Card> seenCards) {
		this.masterListCards = seenCards;
	}
}
