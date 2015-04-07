package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public abstract class BoardCell {
	private int row, column;
	private boolean isOccupied;
	private Color playerColor;
	private boolean isHighlighted;
    
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		isOccupied = false;
		isHighlighted = false;
	}

	public boolean isHighlighted() {
		return isHighlighted;
	}

	public void setHighlighted(boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
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
	
	public void setIsOccupied(boolean status){
		isOccupied = status;
	}
	
	public boolean getIsOccupied(){
		return isOccupied;
	}
	
	public Color getPlayerColor(){
		return playerColor;
	}
	
	public void setPlayerColor(Color c){
		playerColor = c;
	}
	/*
	 * Each board cell draws itself in board. All cells are a simple rectangle determined by the dimensions of the window, as can be seen in clueGame.Board#fixSize()
	 * roomCells draw themselves slightly differently, in that specific cells are tagged in the legend files to print the name of the room to which they belong. 
	 */
	public abstract void Draw(Graphics g, Board b, int currentRow, int currentColumn);
	
		
}
