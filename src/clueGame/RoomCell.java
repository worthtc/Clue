package clueGame;

public class RoomCell extends BoardCell {
	public enum DoorDirection{UP,DOWN,LEFT,RIGHT};
	private DoorDirection doorDirection;
	private char room_initial;
	
	@Override
	public boolean isRoom(){
		return true;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getRoom_initial() {
		return room_initial;
	}
	public RoomCell(int row, int column, String initial) {
		super(row, column);
		room_initial = initial.charAt(0);
		if( initial.length() == 2 ){
			switch(initial.charAt(1)){
				case 'U':
					doorDirection = DoorDirection.UP;
				case 'D':
					doorDirection = DoorDirection.DOWN;
				case 'L':
					doorDirection = DoorDirection.LEFT;
				case 'R':
					doorDirection = DoorDirection.RIGHT;
			}
		}
	}

}
