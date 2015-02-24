package clueGame;

public abstract class BoardCell {
	private int row, column;
    
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
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
}
