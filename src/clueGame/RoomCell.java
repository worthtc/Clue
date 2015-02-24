package clueGame;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP,DOWN,LEFT,RIGHT,NONE};
	private DoorDirection doorDirection;
	private char room_initial;
	
	@Override
	public boolean isRoom(){
		return true;
	}
	
	@Override
	public boolean isDoorway(){
		if( doorDirection != DoorDirection.NONE ){
			return true;
		}
		return false;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return room_initial;
	}
	public RoomCell(int row, int column, String initial) {
		super(row, column);
		room_initial = initial.charAt(0);
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
			}
		}
		else{
			doorDirection = DoorDirection.NONE;
		}
	}

}
