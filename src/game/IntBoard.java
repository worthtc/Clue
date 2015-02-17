package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;

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
		//currentCell = this.getCell( currentCell.getRow(), currentCell.getColumn());
		ArrayList<BoardCell> adjacentCells = new ArrayList<BoardCell>();
		visited.add(currentCell);
		LinkedList<BoardCell> x = getAdjList(currentCell);
		//System.out.println( x.toString() );
		for( BoardCell y: x){
			adjacentCells.add(y);
		}
		//System.out.println( adjacentCells.toString() );
		//adjacentCells.addAll(x);
		for(BoardCell adjCell: adjacentCells ){
			if( !(visited.contains(adjCell)) && adjCell != null){
				visited.add(adjCell);
				if(moveNum == 1){
					targetList.add(adjCell);
				} else {
					calcTargets(adjCell,moveNum - 1);
					visited.remove(adjCell);
				}
			}
		}
	}
	
	public Set<BoardCell> getTargets(){
		System.out.println(targetList.toString());
		return targetList;
	}
	
	public LinkedList<BoardCell> getAdjList( BoardCell currentCell){
		return adjacencies.get( currentCell );
	}
	
	public BoardCell getCell( int row, int column ){
		BoardCell b = new BoardCell(row, column );
		return b;
		/*for( BoardCell currentCell:adjacencies.keySet()){
			if( currentCell.equals( b )){
				return currentCell;
			}
		}
		return new BoardCell( -1, -1);*/

	}
	
	public static void main( String[] args ){
		IntBoard testBoard = new IntBoard();
		testBoard.calcAdjacencies();
		BoardCell testCell = testBoard.getCell(0, 1);
		testBoard.calcTargets( testCell, 2 );
		Set<BoardCell> targets = testBoard.getTargets();
		System.out.println(targets.toString());
		System.out.println( targets.contains( testBoard.getCell(2, 1)));
		System.out.println( targets.contains( testBoard.getCell(1, 0)));
		System.out.println( targets.contains( testBoard.getCell(1, 2)));
		System.out.println( targets.contains( testBoard.getCell(0, 3)));
		
	}
}
