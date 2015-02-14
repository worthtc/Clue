package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> targetList;
	
	public IntBoard() {
		this.adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>(); //Not sure that we want a hash map instead of a tree map but I will leave it like this for now
	}
	
	public void calcAdjacencies(){
		
	}
	
	public void calcTargets(BoardCell currentCell, int moveNum ){
		
	}
	
	public Set<BoardCell> getTargets(){
		return new HashSet<BoardCell>();
	}
	
	public LinkedList<BoardCell> getAdjList( BoardCell currentCell){
		return new LinkedList<BoardCell>();
	}
	
	public BoardCell getCell( int row, int column ){
		return new BoardCell(0, 0);
	}

}
