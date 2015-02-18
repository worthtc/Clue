package test;

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
    public static final int total_rooms = 12;
    public static final int total_rows=26;
    public static final int total_col = 24;
    
    @BeforeClass
    public static void start(){
    	
    	
    }
    @Test
	public void testDim(){
		assertEquals(total_rows,the_board.getNumRows());
		assertEquals(total_col,the_board.getNumColumns());
	}
    @Test
	public void Intials(){
		assertEquals('T',the_board.getRoom(11, 12));
		assertEquals('G',the_board.getRoom(1, 5));
		assertEquals('L',the_board.getRoom(22, 8));
		assertEquals('D',the_board.getRoom(26, 17));
		
	}
	@Test
	public void testRooms() {
		Map<Character,String> room = the_board.getRooms();
		assertEquals(total_rooms,room.size());
		assertEquals("Chapel",room.get('C'));
		assertEquals("Kitchen",room.get('K'));
		assertEquals("Solar",room.get('S'));
		assertEquals("Lord and Ladies' Room",room.get('L'));
		assertEquals("Wardrobe(impassable)",room.get('X'));
		
	}
	
	
	@Test
	public void testDoorDirection(){
		RoomCell a_room = the_board.getRoom(8,4);
		assertTrue(a_room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.DOWN,a_room.getDoorDirection());
		a_room = the_board.getRoom(2, 14);
		assertTrue(a_room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.UP,a_room.getDoorDirection());
		a_room = the_board.getRoom(17, 4);
		assertTrue(a_room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.RIGHT,a_room.getDoorDirection());
		a_room = the_board.getRoom(2, 11);
		assertTrue(a_room.isDoorWay());
		assertEquals(RoomCell.DoorDirection.LEFT,a_room.getDoorDirection());
		
		
		
		
	}
	
	@Test
	public void testTotalDoors(){
		int total_doors = 0;
		for(int i = 0; i < the_board.getNumRows();i++){
			for(int j=i;j<the_board.getNumColumns();j++){
				BoardCell b = the_board.getCell(i, j);
				if(b.isDoorWay() == true){
					total_doors++;
				}
			}
		}
		assertEquals(11,total_doors);
	}
	@Test 
	public void badrow() throws BadConfigFormatException{
		ClueGame game = new ClueGame();
		game.loadConfigFiles();
	}
	
	@Test
	public void badDoors(){
		ClueGame game = new ClueGame();
		game.loadConfigFiles();
	}
	
	

}