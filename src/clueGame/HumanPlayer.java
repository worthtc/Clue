package clueGame;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name, String color, int startRow, int startCol){
		super(name, color, startRow, startCol);
	}
	@Override
	public boolean isHuman(){
		return true;
	}
}
