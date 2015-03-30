package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP,DOWN,LEFT,RIGHT,NONE};
	private DoorDirection doorDirection;
	private char room_initial;
	private boolean isNamed;
	public RoomCell(int row, int column, String initial) {
		super(row, column);
		room_initial = initial.charAt(0);
		isNamed = false;
		if( initial.length() == 2 ){
			switch(initial.charAt(1)){
				case 'U':
					doorDirection = DoorDirection.UP;
					break;
				case 'D':
					doorDirection = DoorDirection.DOWN;
					break;
				case 'L':
					doorDirection = DoorDirection.LEFT;
					break;
				case 'R':
					doorDirection = DoorDirection.RIGHT;
					break;
				default:
					doorDirection = DoorDirection.NONE;
					isNamed = true;
					break;
			}
		}
		else{
			doorDirection = DoorDirection.NONE;
		}
	}
	
	@Override
	public boolean isRoom(){
		return true;
	}
	
	@Override
	public boolean isDoorWay(){
		if( doorDirection != DoorDirection.NONE && doorDirection != null ){
			return true;
		}
		return false;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	@Override
	public boolean getIsOccupied(){
		if( doorDirection != DoorDirection.NONE) return false;
		return super.getIsOccupied();
	}
	
	public char getInitial() {
		return room_initial;
	}

	@Override
	public void Draw(Graphics g, Board b, int currentRow, int currentColumn) {
		if( isNamed ){
			g.setColor(Color.BLACK);
			g.setFont(new Font("Times New Roman", Font.BOLD, (int)(b.getCellSize().getHeight()*2/3)));
			g.drawString(b.getRooms().get(getInitial()), (int)(currentColumn*b.getCellSize().getHeight()), (int)(currentRow*b.getCellSize().getWidth()));
			//b.getRoomPrintNames().remove(b.getRooms().get(getInitial()));
		}
		if(isDoorWay()){
			g.setColor(Color.BLUE);
			if(getDoorDirection() == DoorDirection.LEFT){
				g.fillRect((int)(currentColumn*b.getCellSize().getWidth()), (int)(currentRow*b.getCellSize().getHeight()), (int)(b.getCellSize().getWidth()/8), (int)(b.getCellSize().getHeight()));
			}
			else if(getDoorDirection() == DoorDirection.RIGHT){
				g.fillRect((int)(currentColumn*b.getCellSize().getWidth() + b.getCellSize().getWidth()), (int)(currentRow*b.getCellSize().getHeight()), (int)(-1*b.getCellSize().getWidth()/8), (int)(b.getCellSize().getHeight()));
			}
			else if(getDoorDirection() == DoorDirection.UP){
				g.fillRect((int)(currentColumn*b.getCellSize().getWidth()), (int)(currentRow*b.getCellSize().getHeight()), (int)(b.getCellSize().getWidth()), (int)(b.getCellSize().getHeight()/8));
			}
			else{
				g.fillRect((int)(currentColumn*b.getCellSize().getWidth()), (int)(currentRow*b.getCellSize().getHeight() + b.getCellSize().getHeight()), (int)(b.getCellSize().getWidth()), (int)(-1*b.getCellSize().getHeight()/8));
			}
		}
	}

}
