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
}
