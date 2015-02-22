package clueGame;

import java.util.HashMap;
import java.util.Map;

import clueGame.Board;

public class ClueGame {
	private String boardName;
	private String boardLegend;
	private Board gameBoard;
	Map<Character,String> rooms;
   
   public ClueGame(String boardName, String legendName) {
	   rooms = new HashMap<Character,String>();
	   gameBoard = new Board();
	   this.boardName = boardName;
	   boardLegend = legendName;
   }
   public void loadConfigFiles() throws BadConfigFormatException{
	   gameBoard.loadBoardConfig( boardName, boardLegend);
   }
}
