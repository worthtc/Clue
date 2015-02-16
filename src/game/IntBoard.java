package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> targetList;
	private static final int BOARDSIZE = 4;
	
	public IntBoard() {
		this.adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>(); 
	}
	
	public void calcAdjacencies(){
		for(int i = 0; i < BOARDSIZE; i++ ) { 
			for(int j = 0; j < BOARDSIZE;j++) {
				LinkedList<BoardCell> adjList = new LinkedList<BoardCell>();
				if( i - 1 >= 0 ){
					adjList.add( new BoardCell(i-1, j));
				}
				if( j - 1 >= 0 ){
					adjList.add( new BoardCell(i, j-1));
				}
				if( i + 1 < BOARDSIZE ){
					adjList.add( new BoardCell(i+1, j));
				}
				if( j + 1 < BOARDSIZE ){
					adjList.add( new BoardCell(i, j+1));
				}
				adjacencies.put( new BoardCell(i, j), adjList);	
			}
			
		}
		
	}
		
	
	
	public void calcTargets(BoardCell currentCell, int moveNum ){
		ArrayList<BoardCell> visited = new ArrayList<BoardCell>();
		ArrayList<BoardCell> adjacentCells = new ArrayList<BoardCell>();
		BoardCell start = new BoardCell(0,0);
		targetList.add(start);
		adjacentCells.addAll(adjacencies.get(currentCell));
		for(BoardCell adjCell: adjacentCells ){
		  visited.add(adjCell);
		  if(moveNum == 1){
			  targetList.add(adjCell);
		  } else {
			  calcTargets(adjCell,moveNum);
			  visited.remove(adjCell);
		  }
		}
	}
	
	public Set<BoardCell> getTargets(){
		return new HashSet<BoardCell>();
	}
	
	public LinkedList<BoardCell> getAdjList( BoardCell currentCell){
		return adjacencies.get( currentCell );
	}
	
	public BoardCell getCell( int row, int column ){
		BoardCell b = new BoardCell(row, column );
		for( BoardCell currentCell:adjacencies.keySet()){
			if( currentCell.equals( b )){
				return currentCell;
			}
		}
		return new BoardCell( -1, -1);

	}
}
