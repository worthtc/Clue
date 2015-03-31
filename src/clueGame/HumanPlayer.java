package clueGame;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name, String color, int startRow, int startCol){
		super(name, color, startRow, startCol);
	}
	public HumanPlayer(Player player){
		super(player); 
	}
	@Override
	public boolean isHuman(){
		return true;
	}
	public Card disproveSuggestion(String person, String room, String weapon){
		return getCards().get(1); //TODO: gui prompt to return a disproving card
	}
}
