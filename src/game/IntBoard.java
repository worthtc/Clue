package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private static final LinkedList<BoardCell> LinkedList = null;
	private Map<BoardCell, LinkedList<BoardCell>> adjacencies;
	private Set<BoardCell> targetList;
	
	public IntBoard() {
		this.adjacencies = new HashMap<BoardCell, LinkedList<BoardCell>>(); //Not sure that we want a hash map instead of a tree map but I will leave it like this for now
	}
	
	public void calcAdjacencies(){
		
		for(int i = 0; i < 4; i++ ) { 
			
			for(int j = i; j < 4;j++) {
				BoardCell b = new BoardCell(i, j);
				
				if(i == 0 && j == 0){
					
					 b = new BoardCell(i, j+1);
					 LinkedList.add(b);
					 adjacencies.put(b, LinkedList);
				     b = new BoardCell(i+1, j);
					 LinkedList.add(b);
					 adjacencies.put(b, LinkedList);
					
				} else if(i >= 1 && j == 0) {
					
					 
					b = new BoardCell(i,j+1);
					 LinkedList.add(b);
					 adjacencies.put(b, LinkedList);
					b = new BoardCell(i+1,j);
					 LinkedList.add(b);
					 adjacencies.put(b, LinkedList);
					b = new BoardCell(i-1,j);
					 LinkedList.add(b);
					 adjacencies.put(b, LinkedList);
					
					
				} else if(i == 0 && j >= 1) {
					
					b = new BoardCell(i, j+1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i,j-1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i+1,j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					
				} else if(i >= 1 && j >= 1 && i < adjacencies.size() && j < adjacencies.size() ) {
					
				    b = new BoardCell(i-1,j);
				    LinkedList.add(b);
				    adjacencies.put(b, LinkedList);
					b = new BoardCell(i+1,j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i,j+1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i,j-1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					
				} else if(i == adjacencies.size() && j == 0) {
					
					b = new BoardCell(i-1, j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i, j+1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					
				} else if(i == adjacencies.size() && j == adjacencies.size()) {
					
					b = new BoardCell(i-1, j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i, j-1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					
				} else if(i == 0 && j == adjacencies.size()) {
					
					b = new BoardCell(i+1, j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i, j-1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					
				}else if(i >= 1 && j == adjacencies.size()) {
					
					b = new BoardCell(i-1, j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i,j-1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i+1,j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					
				}else if(i == adjacencies.size() && j >= 1) {
					b.setColumn(i);
					b.setRow(j-1);
					b = new BoardCell(i, j-1);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i-1,j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					b = new BoardCell(i+1,j);
					LinkedList.add(b);
					adjacencies.put(b, LinkedList);
					
				}
					
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
		return new LinkedList<BoardCell>();
	}
	
	public BoardCell getCell( int row, int column ){
		return new BoardCell(row, column);
	}

}
