package Test;
import java.util.LinkedList;
import java.util.Set;
import org.junit.Test;

import Game.BoardCell;
import Game.IntBoard;

import org.junit.*;

public class IntBoardTests {
	private IntBoard testBoard;
	
	@Before
	public void setUp() {
		testBoard = new IntBoard();
		testBoard.calcAdjacencies();
	}

	@Test
	public void testAdjacency() {
		BoardCell testCell = testBoard.getCell(0, 1);
		LinkedList<BoardCell> adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(0, 2)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 1)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(0, 0)));
		Assert.assertEquals(3, adjList.size());
		
		testCell = testBoard.getCell(0, 0);
		adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 0)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(0, 1)));
		Assert.assertEquals(2, adjList.size());
		
		testCell = testBoard.getCell(3, 3);
		adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 3)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(3, 2)));
		Assert.assertEquals(2, adjList.size());
		
		testCell = testBoard.getCell(2, 3);
		adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(3, 3)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 3)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 2)));
		Assert.assertEquals(3, adjList.size());
		
		testCell = testBoard.getCell(1, 0);
		adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(0, 0)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 1)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 0)));
		Assert.assertEquals(3, adjList.size());
		
		testCell = testBoard.getCell(1, 2);
		adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 2)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 1)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 3)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(0, 2)));
		Assert.assertEquals(4, adjList.size());
		
		testCell = testBoard.getCell(2, 1);
		adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 2)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 1)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(3, 1)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 0)));
		Assert.assertEquals(4, adjList.size());
		
		testCell = testBoard.getCell(2, 2);
		adjList = testBoard.getAdjList( testCell );
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 3)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(3, 2)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(1, 2)));
		Assert.assertTrue( adjList.contains( testBoard.getCell(2, 1)));
		Assert.assertEquals(4, adjList.size());
	}
	
	@Test
	public void testTargets(){
		BoardCell testCell = testBoard.getCell(0, 1);
		testBoard.calcTargets( testCell, 2 );
		Set<BoardCell> targets = testBoard.getTargets();
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 0)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 2)));
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 3))); 
		Assert.assertEquals(4, targets.size());
		testBoard.clearTargets();
		
		testCell = testBoard.getCell(0, 0);
		testBoard.calcTargets( testCell, 3 );
		targets = testBoard.getTargets();
		Assert.assertTrue( targets.contains( testBoard.getCell(3, 0)));
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 2)));
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 3)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 0)));
		Assert.assertEquals(6, targets.size());
		testBoard.clearTargets();
		
		testCell = testBoard.getCell(3, 3);
		testBoard.calcTargets( testCell, 1 );
		targets = testBoard.getTargets();
		Assert.assertTrue( targets.contains( testBoard.getCell(3, 2)));
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 3)));
		Assert.assertEquals(2, targets.size());
		testBoard.clearTargets();
		
		
		testCell = testBoard.getCell(2, 2);
		testBoard.calcTargets( testCell, 6 );
		targets = testBoard.getTargets();
		Assert.assertTrue( targets.contains( testBoard.getCell(3, 3)));
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 2)));
		Assert.assertTrue( targets.contains( testBoard.getCell(3, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 3)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 0)));
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 0)));
		Assert.assertEquals(7, targets.size());
		testBoard.clearTargets();
		
		testCell = testBoard.getCell(0, 3);
		testBoard.calcTargets( testCell, 4 );
		targets = testBoard.getTargets();
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 0)));
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 2)));
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 3)));
		Assert.assertTrue( targets.contains( testBoard.getCell(3, 2)));
		Assert.assertEquals(6, targets.size());
		testBoard.clearTargets();
		
		testCell = testBoard.getCell(1, 1);
		testBoard.calcTargets( testCell, 3 );
		targets = testBoard.getTargets();
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 2)));
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 3)));
		Assert.assertTrue( targets.contains( testBoard.getCell(1, 0)));
		Assert.assertTrue( targets.contains( testBoard.getCell(0, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 1)));
		Assert.assertTrue( targets.contains( testBoard.getCell(3, 0)));
		Assert.assertTrue( targets.contains( testBoard.getCell(3, 2)));
		Assert.assertTrue( targets.contains( testBoard.getCell(2, 3)));
		Assert.assertEquals(8, targets.size());

	}

}