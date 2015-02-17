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
	private ArrayList<BoardCell> visited;
	private static final int BOARDSIZE = 4;
	
	public IntBoard() {
		this.adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>(); 
		targetList = new HashSet<BoardCell>();
		visited = new ArrayList<BoardCell>();
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
		ArrayList<BoardCell> adjacentCells = new ArrayList<BoardCell>();
		visited.add(currentCell);
		adjacentCells.addAll(getAdjList(currentCell));
		for(BoardCell adjCell: adjacentCells ){
			
			if( !(visited.contains(adjCell))){
				visited.add(adjCell);
				if(moveNum == 1){
					
					targetList.add(adjCell);
					visited.remove(adjCell);
					
				} else {
					calcTargets(adjCell,moveNum - 1);
					visited.remove(adjCell);
					
				}
				
			}
		}
		visited.remove(currentCell);
		
	}
	
	public Set<BoardCell> getTargets(){
		return targetList;
	}
	
	public LinkedList<BoardCell> getAdjList( BoardCell currentCell){
		return adjacencies.get( currentCell );
		
	}
	
	public void clearTargets(){
		targetList.clear();
		visited.clear();
	}
	public BoardCell getCell( int row, int column ){
		return new BoardCell(row, column );

	}
}
