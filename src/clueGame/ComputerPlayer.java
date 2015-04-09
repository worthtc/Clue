package clueGame;
import java.util.*;

public class ComputerPlayer extends Player{
	private char lastRoomVisited;
	private Set<Card> masterListCards;
	private boolean AccusationFlag;
	private String winningPerson;
	private String winningWeapon;
	private String winningRoom;
	
	public ComputerPlayer(String name, String color, int startRow, int startCol, ArrayList<Card> gameCards){
		super(name, color, startRow, startCol);
		Set<Card> cards = new HashSet<Card>();
		for(Card c : gameCards){
			cards.add(c);
		}
		masterListCards = cards;
		AccusationFlag = false;
	}
	public ComputerPlayer(Player player, ArrayList<Card> gameCards){
		super(player);
		Set<Card> cards = new HashSet<Card>();
		for(Card c : gameCards){
			cards.add(c);
		}
		masterListCards = cards;
		AccusationFlag = false;
	}
	public BoardCell pickLocation(Set<BoardCell> targets){
		// Check to see if one of the targets is a room that we were not just in
		for( BoardCell roomTest: targets){
			if( roomTest.isRoom() && ((RoomCell)roomTest).getInitial() != lastRoomVisited){
				return roomTest;
			}
		}
		//Otherwise we just pick a board Cell at random
		Object[] targetArray = targets.toArray();
		return ((BoardCell)targetArray[(int) (Math.random()*(targets.size()))]);
	}
	
	 // Generates a random suggestion based on a list of cards not yet seen by the computer
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
		suggestion.add(roomOptions.get(choice ));
		choice = (int)(Math.random()*weaponOptions.size());
		suggestion.add(weaponOptions.get(choice));
		return suggestion;
	}
	//Removes cards as they are revealed from the master list of cards, if it has not previously been seen
	public void updateSeen(Card c){
		if(masterListCards.contains(c)){
			masterListCards.remove(c);
		}
	}
	//Checks each of the cards in  myCards to see if they match any of the cards asserted by this method, then chooses a random one if any are found to match. If none match, returns null
	public Card disproveSuggestion(String person, String room, String weapon){
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
	
	@Override
	public void giveCard(Card c){
		super.giveCard(c);
		updateSeen(c);
	}

	@Override
	public void makeAMove(Set<BoardCell> targetSet) {
		
		BoardCell chosenCell = pickLocation(targetSet);
		setCurrentRow(chosenCell.getRow());
		setCurrentCol(chosenCell.getColumn());
		chosenCell.setPlayerColor(getColor());
		chosenCell.setIsOccupied(true);
		if(chosenCell.isRoom()){
			lastRoomVisited = ((RoomCell)chosenCell).getInitial();
		}
	}
	public boolean getAccusationFlag() {
		return AccusationFlag;
	}
	public void setAccusationFlag(boolean accusationFlag) {
		AccusationFlag = accusationFlag;
	}
	
	public void setWinningPerson(String winningPerson) {
		this.winningPerson = winningPerson;
	}
	public void setWinningWeapon(String winningWeapon) {
		this.winningWeapon = winningWeapon;
	}
	public void setWinningRoom(String winningRoom) {
		this.winningRoom = winningRoom;
	}
	public Solution makeAccusation() {
		return new Solution( winningPerson, winningWeapon, winningRoom);
		
	}
	public String getWinningPerson() {
		return winningPerson;
	}
	public String getWinningWeapon() {
		return winningWeapon;
	}
	public String getWinningRoom() {
		return winningRoom;
	}
	
	
	
	
}
