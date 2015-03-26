package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkWayCell extends BoardCell {
    @Override
    public boolean isWalkWay(){
    	return true;
    }
    
	public WalkWayCell(int row, int column) {
		super(row, column);
	}
	
	@Override
	public void Draw(Graphics g, Board b, int currentRow, int currentColumn) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)(currentRow*b.getCellSize().getHeight()),(int)(currentColumn*b.getCellSize().getWidth()), (int)(b.getCellSize().getHeight()), (int)(b.getCellSize().getWidth()));
		g.setColor(Color.BLACK);
		g.drawRect((int)(currentRow*b.getCellSize().getHeight()),(int)(currentColumn*b.getCellSize().getWidth()), (int)(b.getCellSize().getHeight()), (int)(b.getCellSize().getWidth()));
	}
}
