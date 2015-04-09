package clueGame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener {
	private BoardCell layout [][];
	private int numRows,numColumns;
	Map<Character,String> rooms; 
	private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> targetList;
	private ClueGame game;

	private Dimension cellSize;
	private boolean targetSelected;
	
	private ArrayList<Player> players;
	private int currentIndex;
	
	private boolean humanFinished;
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		fixSize();
		//Draw a background for the board and give it a black border
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, (int)(getCellSize().getWidth()*numColumns),(int)( getCellSize().getHeight()*numRows));
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, (int)(getCellSize().getWidth()*numColumns),(int)( getCellSize().getHeight()*numRows));
		//Set all the cells with players in them to be Occupied
		if( players != null){
			for( Player p: players){
				layout[p.getCurrentRow()][p.getCurrentCol()].setIsOccupied(true);
				layout[p.getCurrentRow()][p.getCurrentCol()].setPlayerColor(p.getColor());
			}
		}
		//Then we have every room cell draw itself
		for(int i = 0; i<layout.length; i++){
			for(int j = 0; j<layout[i].length; j++){
				layout[i][j].Draw(g, this, i, j);
			}
		}
	}
	
	
	public boolean isHumanFinished() {
		return humanFinished;
	}


	public void setHumanFinished(boolean humanFinished) {
		this.humanFinished = humanFinished;
	}


	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}


	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public boolean isTargetSelected() {
		return targetSelected;
	}


	public void setTargetSelected(boolean targetSelected) {
		this.targetSelected = targetSelected;
	}


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

	public Board(ClueGame g) {
		rooms = new HashMap<Character,String>();
		adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>();
		targetList = new HashSet<BoardCell>();
		targetSelected = false;
		currentIndex = 0;
		addMouseListener(this);
		humanFinished = false;
		game = g;
	}
	public void fixSize(){
		cellSize = new Dimension(1,1);
		cellSize.setSize((int) (getSize().getWidth()/numColumns), (int) (getSize().getHeight()/numRows));
	}

	public BoardCell getCellAt(int x, int y){
		return layout[x][y];
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Dimension getCellSize() {
		return cellSize;
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}

	public RoomCell getRoomCellAt(int x,int y){
		if(layout[x][y].isRoom()){
			return (RoomCell) layout[x][y];
		}
		return null; //Return a null value if the given location is not a Room Cell
	}

	public void calcAdjacencies(){
		for(int i = 0; i < numRows; i++ ) { 
			for(int j = 0; j < numColumns;j++) {
				LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();
				//This if statement check to see if the cell at i-1 is either not a room or is a door way with the door facing in the correct direction
				if( i - 1 >= 0 && (!(getCellAt(i-1,j).isRoom() ) || (getCellAt(i-1,j).isDoorWay() && getRoomCellAt(i-1,j).getDoorDirection() == RoomCell.DoorDirection.DOWN  ) )){
					//this if statement makes sure that we are either not currently in a room or that we are currently in a door way and the door is facing in the correct direction
					if( !(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.UP)||getCellAt(i-1,j).isDoorWay()){
						//Finally we make sure that the room we are moving to is not occupied, if it is not, we add it to the adjacency list
						adjList.add(getCellAt(i-1,j));
					}
				}
				//This if statement check to see if the cell at j-1 is either not a room or is a door way with the door facing in the correct direction
				if( j - 1 >= 0 && (!(getCellAt(i,j-1).isRoom() ) || (getCellAt(i,j-1).isDoorWay() && getRoomCellAt(i,j-1).getDoorDirection() == RoomCell.DoorDirection.RIGHT  )) ){
					//this if statement makes sure that we are either not currently in a room or that we are currently in a door way and the door is facing in the correct direction
					if( !(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.LEFT)||getCellAt(i,j-1).isDoorWay()){
						//Finally we make sure that the room we are moving to is not occupied, if it is not, we add it to the adjacency list
						adjList.add( getCellAt(i,j-1));
					}
				}
				//This if statement check to see if the cell at i+1 is either not a room or is a door way with the door facing in the correct direction
				if( i + 1 < numRows && (!(getCellAt(i+1,j).isRoom() ) || (getCellAt(i+1,j).isDoorWay()) && getRoomCellAt(i+1,j).getDoorDirection() == RoomCell.DoorDirection.UP  ) ){
					//this if statement makes sure that we are either not currently in a room or that we are currently in a door way and the door is facing in the correct direction
					if( !(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.DOWN)||getCellAt(i+1,j).isDoorWay()){
						//Finally we make sure that the room we are moving to is not occupied, if it is not, we add it to the adjacency list
						adjList.add( getCellAt(i+1,j));
					}
				}
				//This if statement check to see if the cell at j+1 is either not a room or is a door way with the door facing in the correct direction
				if( j + 1 < numColumns && (!(getCellAt(i,j+1).isRoom() ) || (getCellAt(i,j+1).isDoorWay()) && getRoomCellAt(i,j+1).getDoorDirection() == RoomCell.DoorDirection.LEFT  )   ){
					//this if statement makes sure that we are either not currently in a room or that we are currently in a door way and the door is facing in the correct direction
					if(!(getCellAt(i,j).isRoom()) || ((getCellAt(i,j).isDoorWay()) && getRoomCellAt(i,j).getDoorDirection() == RoomCell.DoorDirection.RIGHT)|| getCellAt(i,j+1).isDoorWay()){
						//Finally we make sure that the room we are moving to is not occupied, if it is not, we add it to the adjacency list
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

	public void calcTargets(int x,int y, int distance){
		targetList.clear();
		ArrayList<BoardCell> visited = new ArrayList<BoardCell>();
		calcAllTargets( x, y, distance, visited);
	}

	public void calcAllTargets(int x,int y, int distance, ArrayList<BoardCell> visited){
		ArrayList<BoardCell> adjacentCells = new ArrayList<BoardCell>();
		visited.add(getCellAt(x,y));
		adjacentCells.addAll(getAdjList(x,y));
		for(BoardCell adjCell: adjacentCells){
			// Check to see if we have not already visited the adjacent cell
			if( !(visited.contains(adjCell))){
				//Add this cell to the visited list
				visited.add(adjCell);
				// If the cell is a door way or we are at a distance of 1, we just add the cell to the adjacency list
				if( adjCell.isDoorWay()){
					targetList.add(adjCell);
				}
				if(distance == 1){
					targetList.add(adjCell);
					visited.remove(adjCell);
				} else { //Otherwise we run calcAllTargets again with the adjacent cell
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


	@Override
	public void mouseClicked(MouseEvent e) {
		//see if the list of players is not null and we are currently looking at a human player 
		if( players != null ){
			if( players.get(currentIndex).isHuman() && !(((HumanPlayer)players.get(currentIndex)).isFinished())){
				//Make a list of rectangles
				
				repaint();
				for( BoardCell b: targetList ){
					if (new Rectangle((int)(b.getColumn()*getCellSize().getWidth()),(int)(b.getRow()*getCellSize().getHeight()), (int)(getCellSize().getWidth()),(int)(getCellSize().getHeight())).contains(e.getX(), e.getY())) {
						getCellAt(players.get(currentIndex).getCurrentRow(), players.get(currentIndex).getCurrentCol()).setIsOccupied(false);
						humanFinished = true;
				    	((HumanPlayer)players.get(currentIndex)).finishMove( b, targetList);
				    	if(b.isRoom()){
				    		JOptionPane.showMessageDialog(this, "Please make a suggestion!", "", JOptionPane.INFORMATION_MESSAGE);
				    		SuggestionFrame gui = new SuggestionFrame(players.get(currentIndex),this, game, game.getGameInterface());
				    		gui.setVisible(true);
				    	}
				    	repaint();
				    	return;
				    }
				}
				JOptionPane.showMessageDialog(this, "Please choose one of the highlighted cells!", "", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}


}
