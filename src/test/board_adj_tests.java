package test;

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
		ClueGame game = new ClueGame();
		game.loadConfigFiles();
		board = game.getBoardLayout();
		board.calcAdj();
	}
	@Test
	public void testAdj() {
		//test only walk way adj
		LinkedList<BoardCell> test = board.getAdjList(10, 4);
		assertTrue(test.contains(board.getCell(10, 5)));
		assertTrue(test.contains(board.getCell(10, 3)));
		assertTrue(test.contains(board.getCell(9, 4)));
		assertTrue(test.contains(board.getCell(11, 4)));
		Assert.assertEquals(4, test.size());
		
	    //locations that are edge of board
		test = board.getAdjList(10,0);
		assertTrue(test.contains(board.getCell(10, 1)));
		assertTrue(test.contains(board.getCell(11, 0)));
		Assert.assertEquals(2, test.size());
		
		test = board.getAdjList(25,19);
		assertTrue(test.contains(board.getCell(25, 18)));
		Assert.assertEquals(1, test.size());
		
		test = board.getAdjList(19,23);
		assertTrue(test.contains(board.getCell(19, 22)));
		assertTrue(test.contains(board.getCell(20, 23)));
		Assert.assertEquals(2, test.size());
		
		test = board.getAdjList(0,8);
		assertTrue(test.contains(board.getCell(0, 9)));
		assertTrue(test.contains(board.getCell(1, 8)));
		Assert.assertEquals(2, test.size());
		
		//location that is beside room cell
		test = board.getAdjList(3, 14);
		assertTrue(test.contains(board.getCell(2, 14)));
		assertTrue(test.contains(board.getCell(4, 14)));
		Assert.assertEquals(2, test.size());
		
		test = board.getAdjList(19, 3);
		assertTrue(test.contains(board.getCell(19, 2)));
		assertTrue(test.contains(board.getCell(20, 3)));
		assertTrue(test.contains(board.getCell(18, 3)));
		Assert.assertEquals(2, test.size());
		
		//location adj to door way
		test = board.getAdjList(21, 2);
		assertTrue(test.contains(board.getCell(21, 1)));
		assertTrue(test.contains(board.getCell(21, 3)));
		Assert.assertEquals(2, test.size());
		
		test = board.getAdjList(19, 7);
		assertTrue(test.contains(board.getCell(20, 7)));
		assertTrue(test.contains(board.getCell(19, 6)));
		assertTrue(test.contains(board.getCell(18, 7)));
		Assert.assertEquals(3, test.size());
		
		test = board.getAdjList(8, 4);
		assertTrue(test.contains(board.getCell(7, 4)));
		assertTrue(test.contains(board.getCell(9, 4)));
		assertTrue(test.contains(board.getCell(8, 5)));
		Assert.assertEquals(3, test.size());
		
		test = board.getAdjList(12, 9);
		assertTrue(test.contains(board.getCell(12, 10)));
		assertTrue(test.contains(board.getCell(12, 8)));
		assertTrue(test.contains(board.getCell(13, 10)));
		assertTrue(test.contains(board.getCell(13, 8)));
		Assert.assertEquals(4, test.size());
		
		//locations that are doors
		test = board.getAdjList(17, 12);
		assertTrue(test.contains(board.getCell(16, 12)));
		Assert.assertEquals(1, test.size());
		
		test = board.getAdjList(25, 18);
		assertTrue(test.contains(board.getCell(25, 17)));
		Assert.assertEquals(1, test.size());
		
	}
	
	@Test
	public void testTargs(){
		//test of target at various distances
		board.calcTarget(16, 7, 3);
		Set<BoardCell>testTarg = board.getTargs();
		Assert.assertEquals(4, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(14, 7)));
		Assert.assertTrue(testTarg.contains(board.getCell(16, 10)));
		Assert.assertTrue(testTarg.contains(board.getCell(19, 7)));
		Assert.assertTrue(testTarg.contains(board.getCell(16, 4)));
		
		board.calcTarget(19, 10, 2);
		testTarg = board.getTargs();
		Assert.assertEquals(2, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(19, 5)));
		Assert.assertTrue(testTarg.contains(board.getCell(17, 10)));
		
		board.calcTarget(15, 10, 1);
		testTarg = board.getTargs();
		Assert.assertEquals(3, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(16, 10)));
		Assert.assertTrue(testTarg.contains(board.getCell(15, 9)));
		Assert.assertTrue(testTarg.contains(board.getCell(15, 11)));
		
		board.calcTarget(11, 7, 5);
		testTarg = board.getTargs();
		Assert.assertEquals(4, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(16, 7)));
		Assert.assertTrue(testTarg.contains(board.getCell(6, 7)));
		Assert.assertTrue(testTarg.contains(board.getCell(11, 12)));
		Assert.assertTrue(testTarg.contains(board.getCell(11, 2)));
		
		//test can enter room
		
		//exactly 3 steps
		board.calcTarget(12, 10, 3);
		testTarg = board.getTargs();
		Assert.assertEquals(3, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(10, 9)));
		Assert.assertTrue(testTarg.contains(board.getCell(12, 13)));
		Assert.assertTrue(testTarg.contains(board.getCell(14, 9)));
		
		
		//exactly 2
		board.calcTarget(20, 7, 2);
		testTarg = board.getTargs();
		Assert.assertEquals(2, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(18, 7)));
		Assert.assertTrue(testTarg.contains(board.getCell(19, 6)));
		//test leaving room
		
		board.calcTarget(19, 23, 1);
		testTarg=board.getTargs();
		Assert.assertEquals(1, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(20, 23)));
		
		board.calcTarget(19, 23, 3);
		testTarg=board.getTargs();
		Assert.assertEquals(2, testTarg.size());
		Assert.assertTrue(testTarg.contains(board.getCell(22, 23)));
		Assert.assertTrue(testTarg.contains(board.getCell(19, 20)));
		
	}

}
