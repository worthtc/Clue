package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
  private BoardCell layout [][];
  private int numRows,numColumns;
  Map<Character,String> rooms; 
  
  public Map<Character,String> loadBoardConfig( String boardName, String legendName ) throws BadConfigFormatException {
	  FileReader legendReader;
	try {
		legendReader = new FileReader( legendName );
		Scanner legendInput = new Scanner( legendReader );
		while( legendInput.hasNextLine() ){
			String strParse = legendInput.nextLine();
			String[] parseArray = strParse.split( "," );
			if( parseArray.length != 2 || parseArray[0].length() != 1 ){
				throw new BadConfigFormatException();
			}
			Character mapChar = parseArray[0].charAt(0);
			String mapString = parseArray[1];
			rooms.put(mapChar, mapString);
		}
		
	} catch (FileNotFoundException e) {
		e.printStackTrace(); // Added better error reporting
	}
	return rooms;
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
