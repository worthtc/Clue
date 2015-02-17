package Game;

public class BoardCell {
	private int row, column;

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public boolean equals(Object obj) {
		if( obj instanceof BoardCell && this.row == ((BoardCell) obj).row && this.column == ((BoardCell) obj).column ){
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}

	
	

	
}
