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
	public RoomCell(int row, int column) {
		super(row, column);
		// TODO Auto-generated constructor stub
	}

}
