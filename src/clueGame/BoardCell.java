package clueGame;

public abstract class BoardCell {
	private int row, column;
	private boolean isOccupied;
    
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		isOccupied = false;
	}

	public boolean isDoorWay(){
		return false;
	}
	
	public boolean isWalkWay(){
		return false;
	}

	public boolean isRoom(){
		return false;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public void setIsoCcupied(boolean status){
		isOccupied = status;
	}
	
	public boolean getIsOccupied(){
		return isOccupied;
	}
}
