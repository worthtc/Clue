package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;

public class board_adj_tests {
    private static Board board;
	@BeforeClass
	public static void setUp(){
		ClueGame game = new ClueGame("ClueBoard.csv", "Legend.txt");
		game.loadConfigFiles();
		board = game.getBoardLayout();
		board.calcAdjacencies();
	}
	@Test
	public void testAdjWalkWays() {
		//test only walk way adj
		LinkedList<BoardCell> test = board.getAdjList(10, 4);
		assertTrue(test.contains(board.getCellAt(10, 5)));
		assertTrue(test.contains(board.getCellAt(10, 3)));
		assertTrue(test.contains(board.getCellAt(9, 4)));
		assertTrue(test.contains(board.getCellAt(11, 4)));
		Assert.assertEquals(4, test.size());
	}
	@Test
	public void testAdjEdge() {
	    //locations that are edge of board
		LinkedList<BoardCell> test = board.getAdjList(10,0);
		assertTrue(test.contains(board.getCellAt(10, 1)));
		assertTrue(test.contains(board.getCellAt(11, 0)));
		Assert.assertEquals(2, test.size());
		
		test = board.getAdjList(25,21);
		assertTrue(test.contains(board.getCellAt(24, 21)));
		Assert.assertEquals(1, test.size());
		
		test = board.getAdjList(21,25);
		assertTrue(test.contains(board.getCellAt(20, 25)));
		assertTrue(test.contains(board.getCellAt(21, 24)));
		Assert.assertEquals(2, test.size());
		
		test = board.getAdjList(0,8);
		assertTrue(test.contains(board.getCellAt(0, 9)));
		assertTrue(test.contains(board.getCellAt(1, 8)));
		Assert.assertEquals(2, test.size());
	}
	@Test
	public void testAdjByRoom() {
		//location that is beside room cell
		LinkedList<BoardCell> test = board.getAdjList(8, 17);
		assertTrue(test.contains(board.getCellAt(9, 17)));
		assertTrue(test.contains(board.getCellAt(7, 17)));
		assertTrue(test.contains(board.getCellAt(8, 16)));
		Assert.assertEquals(3, test.size());
		
		test = board.getAdjList(19, 3);
		assertTrue(test.contains(board.getCellAt(19, 2)));
		assertTrue(test.contains(board.getCellAt(20, 3)));
		assertTrue(test.contains(board.getCellAt(18, 3)));
		Assert.assertEquals(3, test.size());
	}
	@Test
	public void testAdjByDoor(){
		//location adj to door way
		LinkedList<BoardCell> test = board.getAdjList(21, 2);
		assertTrue(test.contains(board.getCellAt(21, 1)));
		assertTrue(test.contains(board.getCellAt(21, 3)));
		Assert.assertEquals(2, test.size());
		
		test = board.getAdjList(19, 7);
		assertTrue(test.contains(board.getCellAt(20, 7)));
		assertTrue(test.contains(board.getCellAt(19, 6)));
		assertTrue(test.contains(board.getCellAt(18, 7)));
		Assert.assertEquals(3, test.size());
		
		test = board.getAdjList(8, 4);
		assertTrue(test.contains(board.getCellAt(7, 4)));
		assertTrue(test.contains(board.getCellAt(9, 4)));
		assertTrue(test.contains(board.getCellAt(8, 5)));
		Assert.assertEquals(3, test.size());
		
		test = board.getAdjList(12, 9);
		assertTrue(test.contains(board.getCellAt(12, 10)));
		assertTrue(test.contains(board.getCellAt(12, 8)));
		assertTrue(test.contains(board.getCellAt(11, 9)));
		assertTrue(test.contains(board.getCellAt(13, 9)));
		Assert.assertEquals(4, test.size());
	}
	@Test
	public void testAdjonDoor(){
		//locations that are doors
		LinkedList<BoardCell> test = board.getAdjList(17, 12);
		assertTrue(test.contains(board.getCellAt(16, 12)));
		Assert.assertEquals(1, test.size());
		
		test = board.getAdjList(25, 16);
		assertTrue(test.contains(board.getCellAt(25, 15)));
		Assert.assertEquals(1, test.size());
		
	}
	
	@Test
	public void testTargs(){
		//test of target at various distances
		board.calcTargets(16, 7, 3);
		Set<BoardCell>testTarg = board.getTargets();
		Assert.assertEquals(15, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCellAt(14, 6)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(14, 8)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(13, 7)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(15, 5)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(17, 9)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(16, 10)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(19, 7)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(16, 4)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(18, 6)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(17, 5)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(17, 9)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(16, 8)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(16, 6)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(17, 7)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(17, 5)));
		
		
		board.calcTargets(19, 10, 2);
		testTarg = board.getTargets();
		Assert.assertEquals(2, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCellAt(17, 10)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(21, 10)));
		
		board.calcTargets(15, 10, 1);
		testTarg = board.getTargets();
		Assert.assertEquals(3, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCellAt(16, 10)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(15, 9)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(15, 11)));
		
		board.calcTargets(1, 17, 5);
		testTarg = board.getTargets();
		Assert.assertEquals(2, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCellAt(0, 13)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(6, 17)));
	}
	@Test
	public void testDoorTargs(){
		//test can enter room
		
		//exactly 3 steps
		board.calcTargets(19, 23, 3);
		Set<BoardCell>testTarg = board.getTargets();
		
		Assert.assertTrue(testTarg.contains(board.getCellAt(21, 24)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(20, 25)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(18, 25)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(20, 21)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(18, 21)));
		Assert.assertEquals(5, testTarg.size());
		
		
		//exactly 2
		board.calcTargets(19, 6, 2);
		testTarg = board.getTargets();
		Assert.assertEquals(3, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCellAt(20, 7)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(17, 6)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(18, 7)));
		//test leaving room
	}
	@Test
	public void testLeaveTargs(){
		board.calcTargets(18, 25, 1);
		Set<BoardCell>testTarg=board.getTargets();
		Assert.assertEquals(1, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCellAt(19, 25)));
		
		board.calcTargets(1, 13, 3);
		testTarg=board.getTargets();
		Assert.assertEquals(2, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCellAt(0, 11)));
		Assert.assertTrue(testTarg.contains(board.getCellAt(0, 15)));
		
	}

}
