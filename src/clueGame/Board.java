	package clueGame;

import java.util.HashMap;
import java.util.Map;

public class Board {
  private BoardCell layout [][];
  private int numRows,numColumns;
  Map<Character,String> rooms = new HashMap<Character,String>();
  
  public void loadBordConfig(){
	  
  }
  public BoardCell getCell(int x, int y){
	  return new BoardCell(x,y);
  }
  public BoardCell[][] getLayout() {
	return layout;
  }

  public int getNumRows() {
	return numRows;
  }

  public int getNumColumns() {
	return numColumns;
  }

  public Map<Character, String> getRooms() {
	return rooms;
  }
  public RoomCell getRoom(int x,int y){
	return new RoomCell(x,y);
	  
  }
}
