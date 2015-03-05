package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Assert;

public class Board {
  private BoardCell layout [][];
  private int numRows,numColumns;
  Map<Character,String> rooms; 
  private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
  private Set<BoardCell> targetList;
  
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
	adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>();
	targetList = new HashSet<BoardCell>();
	
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
	return null; //Return a bad RoomCell if the given location is not a Room Cell, We might want to throw an exception here but I am not sure
  }

  public void calcAdjacencies(){
	  for(int i = 0; i < numRows; i++ ) { 
			for(int j = 0; j < numColumns;j++) {
				LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();
				if( i - 1 >= 0 && (!(getCellAt(i-1,j).isRoom() ) || (getCellAt(i-1,j).isDoorWay() && getRoomCellAt(i-1,j).getDoorDirection() == RoomCell.DoorDirection.DOWN  ) )){
					if( !(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.UP)||getCellAt(i-1,j).isDoorWay()){
						adjList.add( getCellAt(i-1,j));
					}
				}
				if( j - 1 >= 0 && (!(getCellAt(i,j-1).isRoom() ) || (getCellAt(i,j-1).isDoorWay() && getRoomCellAt(i,j-1).getDoorDirection() == RoomCell.DoorDirection.RIGHT  )) ){
					if( !(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.LEFT)||getCellAt(i,j-1).isDoorWay()){
						adjList.add( getCellAt(i,j-1));
					}
				}
				if( i + 1 < numRows && (!(getCellAt(i+1,j).isRoom() ) || (getCellAt(i+1,j).isDoorWay()) && getRoomCellAt(i+1,j).getDoorDirection() == RoomCell.DoorDirection.UP  ) ){
					if( !(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.DOWN)||getCellAt(i+1,j).isDoorWay()){
						adjList.add( getCellAt(i+1,j));
					}
				}
				if( j + 1 < numColumns && (!(getCellAt(i,j+1).isRoom() ) || (getCellAt(i,j+1).isDoorWay()) && getRoomCellAt(i,j+1).getDoorDirection() == RoomCell.DoorDirection.LEFT  )   ){
					if(!(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.RIGHT)|| getCellAt(i,j+1).isDoorWay()){
						adjList.add( getCellAt(i,j+1));
					}
				}
				adjacencies.put( layout[i][j], adjList);	
			}
			
		}
		
  }
  
  public LinkedList<BoardCell> getAdjList(int x, int y){
	return adjacencies.get( getCellAt(x,y));
	  
  }
  
  public LinkedList<BoardCell> getAdjList(BoardCell bc){
		return adjacencies.get(bc);
		  
  }	  
		
  public void calcTargets(int x,int y, int distance){
	  targetList.clear();
	  ArrayList<BoardCell> visited = new ArrayList<BoardCell>();
	  calcAllTargets( x, y, distance, visited);
	  //targetList.clear();
  }
  
  public void calcAllTargets(int x,int y, int distance, ArrayList<BoardCell> visited){
	  ArrayList<BoardCell> adjacentCells = new ArrayList<BoardCell>();
	  visited.add(getCellAt(x,y));
	  adjacentCells.addAll(getAdjList( x,y) );
	  for(BoardCell adjCell: adjacentCells ){

		  if( !(visited.contains(adjCell))){
			  visited.add(adjCell);
			  if( adjCell.isDoorWay()){
				  targetList.add(adjCell);
			  }
			  if(distance == 1){

				  targetList.add(adjCell);
				  visited.remove(adjCell);

			  } else {
				  calcAllTargets(adjCell.getRow(), adjCell.getColumn(), distance - 1, visited);
				  visited.remove(adjCell);

			  }

		  }
	  }
	  visited.remove(getCellAt(x,y));
  }
  public Set<BoardCell> getTargets(){
		return targetList;
		  
	  }
  
  
}
