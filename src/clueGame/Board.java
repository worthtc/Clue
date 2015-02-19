	package clueGame;

import java.util.HashMap;
import java.util.Map;

public class Board {
  private BoardCell layout [][];
  private int numRows,numColumns;
  Map<Character,String> rooms; 
  
  public void loadBordConfig(){
	  
  }
  
  public Board() {
	numRows = 0;
	numColumns = 0;
	rooms = new HashMap<Character,String>();
	
}

public BoardCell getCell(int x, int y){
	  return new WalkWayCell(x,y); // We have to return a board cell but we can't instantiate the BoardCell class so we just return a WalkWay
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
