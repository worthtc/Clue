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
		Map<Integer, Integer> adj_matrix = new HashMap<Integer, Integer>();
		Set<BoardCell> keySet = adjacencies.keySet();
		
		
		for(int i = 0; i < adjacencies.size(); i++ ) { //this makes a map of the matrix (i believe) as long as adjacencies is as large as our board
			for(int j = i; j < adjacencies.size();j++) {
				
				if(i == 0 && j == 0){
					
					adj_matrix.put(i, j+1);
					adj_matrix.put(i+1, j);
					
				} else if(i >= 1 && j == 0) {
					
					adj_matrix.put(i,j+1);
					adj_matrix.put(i+1,j);
					adj_matrix.put(i-1,j);
					
				} else if(i == 0 && j >= 1) {
					
					adj_matrix.put(i, j+1);
					adj_matrix.put(i,j-1);
					adj_matrix.put(i+1,j);
					
				} else if(i >= 1 && j >= 1 && i < adjacencies.size() && j < adjacencies.size() ) {
					
					adj_matrix.put(i-1,j);
					adj_matrix.put(i+1,j);
					adj_matrix.put(i,j+1);
					adj_matrix.put(i,j-1);
					
				} else if(i == adjacencies.size() && j == 0) {
					
					adj_matrix.put(i-1, j);
					adj_matrix.put(i, j+1);
					
				} else if(i == adjacencies.size() && j == adjacencies.size()) {
					
					adj_matrix.put(i-1, j);
					adj_matrix.put(i, j-1);
					
				} else if(i == 0 && j == adjacencies.size()) {
					
					adj_matrix.put(i+1, j);
					adj_matrix.put(i, j-1);
					
				}else if(i >= 1 && j == adjacencies.size()) {
					
					adj_matrix.put(i-1, j);
					adj_matrix.put(i,j-1);
					adj_matrix.put(i+1,j);
					
				}else if(i == adjacencies.size() && j >= 1) {
					
					adj_matrix.put(i, j-1);
					adj_matrix.put(i-1,j);
					adj_matrix.put(i+1,j);
					
				}
			}
		}
		
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
