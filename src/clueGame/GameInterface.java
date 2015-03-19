package clueGame;

import javax.swing.JFrame;

public class GameInterface extends JFrame {

	public GameInterface(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue!");
		setSize(500,300);
	}
	
	public static void main(String[] args) {
		GameInterface gui = new GameInterface();
		gui.setVisible(true);
	}

}
