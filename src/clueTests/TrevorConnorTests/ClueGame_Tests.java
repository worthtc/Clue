package clueTests.TrevorConnorTests;

import static org.junit.Assert.*;
import clueGame.*;

import java.io.FileNotFoundException;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ClueGame_Tests {
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 25;
	
	@BeforeClass
	public static void setUp(){
		ClueGame game = new ClueGame("map/Clue Map.txt", "map/legend.txt", "map/weaponLegend.txt","map/peopleLegend.txt", 6);
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}
	@Test
	public void testRooms(){
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
		assertEquals("Innistrad", rooms.get('i'));
		assertEquals("Ravnica", rooms.get('r'));
		assertEquals("Alara", rooms.get('a'));
		assertEquals("Kamigawa", rooms.get('k'));
		assertEquals("Shandalar", rooms.get('s'));
		assertEquals("Blind Eternities", rooms.get('W'));
	}
	@Test
	public void testBoardDimentions(){
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());	
	}
	@Test
	public void FourDoorDirections(){
		RoomCell room = board.getRoomCellAt(1,4);
		assertTrue(room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(14,12);
		assertTrue(room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = board.getRoomCellAt(4,6);
		assertTrue(room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(15,23);
		assertTrue(room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		room = board.getRoomCellAt(13,8);
		assertTrue(room == null);
	}
	
	@Test (expected = BadConfigFormatException.class) 
	public void testBadColums() throws BadConfigFormatException, FileNotFoundException {
		ClueGame game = new ClueGame("map/ClueBadMap.txt", "map/legend.txt","map/weaponLegend.txt","map/peopleLegend.txt", 6);
		game.loadRoomConfig();
	}
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomsFormat() throws BadConfigFormatException, FileNotFoundException{
		ClueGame game = new ClueGame("map/Clue Map.txt", "map/Badlegend.txt","map/weaponLegend.txt","map/peopleLegend.txt", 6);
		game.loadRoomConfig();
	}
	
	@Test
	public void testNumberOfDoorways(){
		int numDoors = 0;
		int numCells = board.getNumRows() * board.getNumColumns();
		assertEquals(525,numCells);
		for(int i = 0; i<board.getNumRows(); i++){
			for(int j = 0; j<board.getNumColumns(); j++){
				BoardCell bc = board.getRoomCellAt(i, j);
				if(bc != null && ((RoomCell)bc).isDoorWay()){
					numDoors++;
				}
			}
		}
		assertEquals(11,numDoors);
	}
	@Test
	public void WalkwayAdjTest7_5(){
		LinkedList<BoardCell> adj = board.getAdjList(7,5);
		assertEquals(4, adj.size());
		assertTrue(board.getCellAt(7, 5).isWalkWay());
		assertTrue(adj.contains(board.getCellAt(6,5)));//above
		assertTrue(adj.contains(board.getCellAt(8,5)));//below
		assertTrue(adj.contains(board.getCellAt(7,4)));//left
		assertTrue(adj.contains(board.getCellAt(7,6)));//right
	}
	@Test
	public void AdjTestEdge0_0(){
		LinkedList<BoardCell> adj = board.getAdjList(0,0);
		assertTrue(board.getCellAt(0,0).isRoom());
		assertEquals(0,adj.size());
	}
	@Test
	public void AdjTestEdge8_0(){
		LinkedList<BoardCell> adj = board.getAdjList(8,0);
		assertTrue(board.getCellAt(8,0).isDoorWay());
		assertEquals(1,adj.size());
		assertTrue(adj.contains(board.getCellAt(7,0)));
	}
	@Test
	public void AdjTestEdge20_9(){
		LinkedList<BoardCell> adj = board.getAdjList(20,9);
		assertTrue(board.getCellAt(20, 9).isWalkWay());
		assertEquals(1,adj.size());
		assertTrue(adj.contains(board.getCellAt(19,9)));
	}
	@Test
	public void AdjTestEdge0_12(){
		LinkedList<BoardCell> adj = board.getAdjList(0,12);
		assertTrue(board.getCellAt(0,12).isWalkWay());
		assertEquals(2,adj.size());
		assertTrue(adj.contains(board.getCellAt(0,11)));
		assertTrue(adj.contains(board.getCellAt(1,12)));
	}
	@Test
	public void AdjTestEdge12_24(){
		LinkedList<BoardCell> adj = board.getAdjList(12,24);
		assertTrue(board.getCellAt(12,24).isWalkWay());
		assertEquals(3,adj.size());
		assertTrue(adj.contains(board.getCellAt(11,24)));
		assertTrue(adj.contains(board.getCellAt(12,23)));
		assertTrue(adj.contains(board.getCellAt(13,24)));
	}
	@Test
	public void AdjTestRoomNotDoorway16_9(){
		LinkedList<BoardCell> adj = board.getAdjList(16,9);
		assertTrue(board.getCellAt(16, 9).isWalkWay());
		assertEquals(2, adj.size());
		assertFalse(adj.contains(board.getCellAt(16,8)));
		assertFalse(adj.contains(board.getCellAt(16,10)));
	}
	@Test
	public void AdjTestRoomNotDoorway6_15(){
		LinkedList<BoardCell> adj = board.getAdjList(6,15);
		assertEquals(3,adj.size());
		assertFalse(adj.contains(board.getCellAt(5,15)));
	}
	@Test
	public void AdjTestContainsDoorway14_11(){
		LinkedList<BoardCell> adj = board.getAdjList(14,11);
		assertTrue(board.getCellAt(14, 11).isWalkWay());
		assertTrue(adj.contains(board.getCellAt(14,12)));
		assertTrue(adj.contains(board.getCellAt(14,10)));
		assertTrue(adj.contains(board.getCellAt(13,11)));
		assertTrue(adj.contains(board.getCellAt(15,11)));
	}
	@Test
	public void AdjTestContainsDoorway6_17(){
		LinkedList<BoardCell> adj = board.getAdjList(6,17);
		assertEquals(4,adj.size());
		assertTrue(adj.contains(board.getCellAt(5,17)));
		assertTrue(adj.contains(board.getCellAt(7,17)));
		assertTrue(adj.contains(board.getCellAt(6,16)));
		assertTrue(adj.contains(board.getCellAt(6,18)));
	}
	@Test
	public void AdjTestContainsDoorway7_22(){
		LinkedList<BoardCell> adj = board.getAdjList(7,22);
		assertEquals(3,adj.size());
		assertTrue(adj.contains(board.getCellAt(7,21)));
		assertTrue(adj.contains(board.getCellAt(7,23)));
		assertTrue(adj.contains(board.getCellAt(6,22)));
	}
	@Test
	public void AdjTestContainsDoorway14_23(){
		LinkedList<BoardCell> adj = board.getAdjList(14,23);
		assertEquals(4,adj.size());
		assertTrue(adj.contains(board.getCellAt(14,22)));
		assertTrue(adj.contains(board.getCellAt(14,24)));
		assertTrue(adj.contains(board.getCellAt(13,23)));
		assertTrue(adj.contains(board.getCellAt(15,23)));
	}
	@Test
	public void AdjTestIsDoorway14_12(){
		LinkedList<BoardCell> adj = board.getAdjList(14,12);
		assertEquals(1,adj.size());
		assertTrue(adj.contains(board.getCellAt(14,11)));
	}
	@Test
	public void AdjTestIsDoorway4_6(){
		LinkedList<BoardCell> adj = board.getAdjList(4,6);
		assertEquals(1,adj.size());
		assertTrue(adj.contains(board.getCellAt(5,6)));
	}
	@Test
	public void TargetsAlongWalkway10_7_2(){
		board.calcTargets(10,7,2);
		Set<BoardCell> tar = board.getTargets();
		assertEquals(6,tar.size());
		assertTrue(tar.contains(board.getCellAt(8,7)));
		assertTrue(tar.contains(board.getCellAt(9,6)));
		assertTrue(tar.contains(board.getCellAt(11,6)));
		assertTrue(tar.contains(board.getCellAt(12,7)));
		assertTrue(tar.contains(board.getCellAt(11,8)));
		assertTrue(tar.contains(board.getCellAt(9,8)));
	}
	@Test
	public void TargestAlongWalkway10_7_6(){
		board.calcTargets(10,7,6);
		Set<BoardCell> tar = board.getTargets();	
		assertTrue(board.getCellAt(10, 7).isWalkWay());
		assertEquals(25,tar.size());
		assertTrue(tar.contains(board.getCellAt(7,4)));
		assertTrue(tar.contains(board.getCellAt(6,5)));
		assertTrue(tar.contains(board.getCellAt(8,5)));
		assertTrue(tar.contains(board.getCellAt(7,6)));
		assertTrue(tar.contains(board.getCellAt(5,6)));
		assertTrue(tar.contains(board.getCellAt(6,7)));
		assertTrue(tar.contains(board.getCellAt(8,7)));
		assertTrue(tar.contains(board.getCellAt(7,8)));
		assertTrue(tar.contains(board.getCellAt(5,8)));
		assertTrue(tar.contains(board.getCellAt(6,9)));
		assertTrue(tar.contains(board.getCellAt(7,10)));
		assertTrue(tar.contains(board.getCellAt(9,8)));
		assertTrue(tar.contains(board.getCellAt(9,6)));
		assertTrue(tar.contains(board.getCellAt(11,6)));
		assertTrue(tar.contains(board.getCellAt(12,5)));
		assertTrue(tar.contains(board.getCellAt(11,4)));
		assertTrue(tar.contains(board.getCellAt(11,8)));
		assertTrue(tar.contains(board.getCellAt(12,7)));
		assertTrue(tar.contains(board.getCellAt(13,6)));
		assertTrue(tar.contains(board.getCellAt(15,6)));
		assertTrue(tar.contains(board.getCellAt(14,7)));
		assertTrue(tar.contains(board.getCellAt(13,8)));
		assertTrue(tar.contains(board.getCellAt(15,8)));
		assertTrue(tar.contains(board.getCellAt(14,9)));
		assertTrue(tar.contains(board.getCellAt(13,10)));
	}
	@Test
	public void TargetsAlongWalkway7_0_3(){//also entering a doorway
		board.calcTargets(7,0,3);
		Set<BoardCell> tar = board.getTargets();
		assertEquals(5,tar.size());
		assertTrue(tar.contains(board.getCellAt(6,0)));
		assertTrue(tar.contains(board.getCellAt(8,0)));
		assertTrue(tar.contains(board.getCellAt(7,1)));
		assertTrue(tar.contains(board.getCellAt(6,2)));
		assertTrue(tar.contains(board.getCellAt(7,3)));
	}
	@Test
	public void TargetsAlongWalkway7_0_4(){//also entering a doorway
		board.calcTargets(7,0,4);
		Set<BoardCell> tar = board.getTargets();
		assertEquals(5,tar.size());
		assertTrue(tar.contains(board.getCellAt(6,1)));
		assertTrue(tar.contains(board.getCellAt(7,2)));
		assertTrue(tar.contains(board.getCellAt(8,0)));
		assertTrue(tar.contains(board.getCellAt(6,3)));
		assertTrue(tar.contains(board.getCellAt(7,4)));
	}
	@Test
	public void TargetsAlongWalkway14_12_2(){//also entering a doorway
		board.calcTargets(14,12,2);
		Set<BoardCell> tar = board.getTargets();	
		assertTrue(board.getCellAt(14, 12).isDoorWay());
		assertEquals(3,tar.size());
		assertTrue(tar.contains(board.getCellAt(13,11)));
		assertTrue(tar.contains(board.getCellAt(15,11)));
		assertTrue(tar.contains(board.getCellAt(14,10)));
	}
	@Test
	public void TargetsAlongWalkway14_12_4(){//also entering a doorway
		board.calcTargets(14,12,4);
		Set<BoardCell> tar = board.getTargets();
		assertTrue(board.getCellAt(14, 12).isDoorWay());
		assertEquals(7,tar.size());
		assertTrue(tar.contains(board.getCellAt(13,13)));
		assertTrue(tar.contains(board.getCellAt(13,11)));
		assertTrue(tar.contains(board.getCellAt(15,11)));
		assertTrue(tar.contains(board.getCellAt(14,10)));
		assertTrue(tar.contains(board.getCellAt(13,9)));
		assertTrue(tar.contains(board.getCellAt(15,9)));
		assertTrue(tar.contains(board.getCellAt(14,8)));
	}
}
