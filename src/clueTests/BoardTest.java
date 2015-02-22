package clueTests;

import static org.junit.Assert.*;

import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.RoomCell;

public class BoardTest {
    private static Board the_board;
    public static final int total_rooms = 13;
    public static final int total_rows= 26;
    public static final int total_col = 26;
    
    @BeforeClass
    public static void start() throws BadConfigFormatException{
    	ClueGame testGame = new ClueGame("ClueBoard.csv", "Legend.txt");
    	the_board = new Board();
    	the_board.loadBoardConfig("ClueBoard.csv", "Legend.txt");
    	testGame.loadConfigFiles();
    	
    }
    
    @Test
	public void testRooms() {
		Map<Character,String> room = the_board.getRooms();
		assertEquals(total_rooms,room.size());
		assertEquals("Chapel",room.get('C'));
		assertEquals("Kitchen",room.get('K'));
		assertEquals("Solar",room.get('S'));
		assertEquals("Lord and Ladies' Room",room.get('L'));
		assertEquals("Wardrobe (Impassable)",room.get('X'));
		
	}
    
    @Test
	public void testDim(){
		assertEquals(total_rows,the_board.getNumRows());
		assertEquals(total_col,the_board.getNumColumns());
	}
    
    @Test
	public void testDoorDirection(){
		RoomCell a_room = the_board.getRoomCellAt(7,4);
		assertTrue(a_room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN,a_room.getDoorDirection());
		a_room = the_board.getRoomCellAt(1, 13);
		assertTrue(a_room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP,a_room.getDoorDirection());
		a_room = the_board.getRoomCellAt(16, 3);
		assertTrue(a_room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT,a_room.getDoorDirection());
		a_room = the_board.getRoomCellAt(12, 10);
		assertTrue(a_room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT,a_room.getDoorDirection());
		
		a_room = the_board.getRoomCellAt(0, 0);
		assertFalse(a_room.isDoorway());
		
		a_room = the_board.getRoomCellAt(10, 0);
		assertFalse(a_room.isDoorway());		
		
	}
    
    @Test
	public void testIntials(){
		assertEquals('T',the_board.getRoomCellAt(11, 12).getInitial());
		assertEquals('G',the_board.getRoomCellAt(1, 5).getInitial());
		assertEquals('L',the_board.getRoomCellAt(22, 8).getInitial());
		assertEquals('D',the_board.getRoomCellAt(25, 17).getInitial());
		
	}
	
	
	
	
	
	@Test
	public void testTotalDoors(){
		int total_doors = 0;
		for(int i = 0; i < the_board.getNumRows(); i++){
			for(int j=0; j<the_board.getNumColumns(); j++){
				BoardCell b = the_board.getCellAt(i, j);
				if(b.isDoorway() == true){
					total_doors++;
				}
			}
		}
		assertEquals(11,total_doors);
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void badRow() throws BadConfigFormatException{
		ClueGame game = new ClueGame("ClueBoardBadRow.csv","Legend.txt");
		game.loadRoomConfig();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void badLegend() throws BadConfigFormatException{
		ClueGame game = new ClueGame("ClueBoard.csv", "BadLegend.txt");
		game.loadRoomConfig();
	}
	
	

}
