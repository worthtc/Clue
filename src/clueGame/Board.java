package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {
  private BoardCell layout [][];
  private int numRows,numColumns;
  Map<Character,String> rooms; 
  
  public Map<Character,String> loadBoardConfig( String boardName, String legendName ) throws BadConfigFormatException {
	  FileReader legendReader;
	  int line_num =0;
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
		/*LineNumberReader line_read = new LineNumberReader(legendReader);
		try{
		  while(line_read.readLine() != null){ //set up initial Rows
			line_num++;
		   }
		  numRows = line_num;
		  line_read.close();
		}catch(IOException e){
			e.printStackTrace();
		}*/
		
		FileReader roomReader = new FileReader( boardName );
		Scanner roomInput = new Scanner( roomReader );
		while( roomInput.hasNext()){
			roomInput.nextLine();
			line_num++;
		}
		numRows = line_num;
		roomReader = new FileReader( boardName );
		roomInput = new Scanner( roomReader );
		int currentRow = 0;
		int currentColumn = 0;
		if( roomInput.hasNextLine() ){ //Set the initial numColumns
			String roomsString = roomInput.nextLine();
			String[] roomParse = roomsString.split( "," );
			numColumns = roomParse.length; // Here I would like to put the cells into the layout, but we need to figure out some way to get numRows to initialize the layout variable.
			layout = new BoardCell[numRows][numColumns];
			for( String s:roomParse){
				if( !rooms.containsKey(s.charAt(0) )){
					throw new BadConfigFormatException();
				}
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
			throw new BadConfigFormatException();
		}
		while( roomInput.hasNextLine() ){
			String roomsString = roomInput.nextLine();
			String[] roomParse = roomsString.split( "," );
			if( roomParse.length != numColumns ){
				throw new BadConfigFormatException();
			}
			for( String s:roomParse){
				if( !rooms.containsKey(s.charAt(0) )){
					throw new BadConfigFormatException();
				}
				if( s.length() != 1 && s.length() != 2 ){
					throw new BadConfigFormatException();
				}
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
	if(layout[x][y].isRoom()){
		return (RoomCell) layout[x][y];
	}
	return new RoomCell(0,0, "a");
  }
  
}
