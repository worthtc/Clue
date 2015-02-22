package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
  private BoardCell layout [][];
  private int numRows,numColumns;
  Map<Character,String> rooms; 
  
  public Map<Character,String> loadBoardConfig( String boardName, String legendName ) throws BadConfigFormatException {
	  FileReader legendReader;
	  int rowNum = 0;
	try {
		legendReader = new FileReader( legendName );
		Scanner legendInput = new Scanner( legendReader );
		//Read the values from the legend file and map them to the correct string
		while( legendInput.hasNextLine() ){
			String strParse = legendInput.nextLine();
			String[] parseArray = strParse.split( "," );
			if( parseArray.length != 2 || parseArray[0].length() != 1 ){
				legendInput.close();
				throw new BadConfigFormatException( "Legend is not correctly formated");
			}
			Character mapChar = parseArray[0].charAt(0);
			String mapString = parseArray[1];
			mapString = mapString.trim(); //Remove any white space at the start of the string
			rooms.put(mapChar, mapString);
			
		}
		legendInput.close();
		
		//Check to see how many rows of values are in the file
		FileReader roomReader = new FileReader( boardName );
		Scanner roomInput = new Scanner( roomReader );
		while( roomInput.hasNext()){
			roomInput.nextLine();
			rowNum++;
		}
		roomInput.close();
		//Set the number of Rows
		numRows = rowNum;
		
		//Reopen the Scanner so we are back at the start of the file
		roomReader = new FileReader( boardName );
		roomInput = new Scanner( roomReader );
		int currentRow = 0;
		int currentColumn = 0;
		if( roomInput.hasNextLine() ){ //Set the initial numColumns and read in the first line of values
			String roomsString = roomInput.nextLine();
			String[] roomParse = roomsString.split( "," );
			numColumns = roomParse.length; 
			layout = new BoardCell[numRows][numColumns];
			for( String s:roomParse){
				//Check to make sure the value is in the map
				if( !rooms.containsKey(s.charAt(0) )){
					roomInput.close();
					throw new BadConfigFormatException("Value in config file is not found in legend.");
				}
				if( s.length() != 1 && s.length() != 2 ){
					roomInput.close();
					throw new BadConfigFormatException();
				}
				//Check to see if the boardCell is a Walkway or RoomCell
				if( s.charAt(0) == 'W'){
					layout[currentRow][currentColumn] = new WalkWayCell(currentRow, currentColumn);
				}
				else{
					layout[currentRow][currentColumn] = new RoomCell(currentRow, currentColumn, s); 
				}
				currentColumn++;
			}
			currentColumn = 0;
			currentRow++;
		}
		else{
			roomInput.close();
			throw new BadConfigFormatException("Config file is empty.");
		}
		//Read in the rest of the values
		while( roomInput.hasNextLine() ){
			String roomsString = roomInput.nextLine();
			String[] roomParse = roomsString.split( "," );
			if( roomParse.length != numColumns ){
				roomInput.close();
				throw new BadConfigFormatException("Config file is incorrectly formatted, not all columns are of the same size");
			}
			for( String s:roomParse){
				//Check to make sure the value is in the map
				if( !rooms.containsKey(s.charAt(0) )){
					roomInput.close();
					throw new BadConfigFormatException("Value in config file is not found in legend.");
				}
				if( s.length() != 1 && s.length() != 2 ){
					roomInput.close();
					throw new BadConfigFormatException();
				}
				//Check to see if the boardCell is a Walkway or RoomCell
				if( s.charAt(0) == 'W'){
					layout[currentRow][currentColumn] = new WalkWayCell(currentRow, currentColumn);
				}
				else{
					layout[currentRow][currentColumn] = new RoomCell(currentRow, currentColumn, s); 
				}
				currentColumn++;
			}
			currentColumn = 0;
			currentRow++;
		}
		roomInput.close();
	    
	} catch (FileNotFoundException e) {
		System.out.println(e.getMessage());
	} 
	return rooms;
  }
  
  public Board() {
	rooms = new HashMap<Character,String>();
	
}

public BoardCell getCellAt(int x, int y){
	  return layout[x][y];
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
  public RoomCell getRoomCellAt(int x,int y){
	if(layout[x][y].isRoom()){
		return (RoomCell) layout[x][y];
	}
	return new RoomCell( -1, -1, " "); //Return a bad RoomCell if the given location is not a Room Cell, We might want to throw an exception here but I am not sure
  }

  public void calcAdj(){
	  
  }
  public LinkedList<BoardCell> getAdjList(int x, int y){
	return new LinkedList<BoardCell>();
	  
  }
  public void calcTarget(int x,int y, int distance){
	  
  }
  public Set<BoardCell> getTargs(){
		return new HashSet<BoardCell>();
		  
	  }

}
