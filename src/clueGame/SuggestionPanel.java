package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clueGame.Card.CardType;

@SuppressWarnings("serial")
public class SuggestionPanel extends JFrame{
	
	private Player player;
	private Board board;
	private ClueGame game;
	private GameInterface gameInt;
	private JComboBox<String> people;
	private JComboBox<String> weapons;
	private JButton submit;
	private JButton cancel;
	
	
	public SuggestionPanel(Player p, Board b, ClueGame g, GameInterface gi){
		player = p;
		board = b;
		game = g;
		gameInt = gi;
		setLayout(new GridLayout(4,2));
		people = peopleSetup();
		weapons = weaponsSetup();
		JTextField yourRoom = new JTextField("Your Room:");
		yourRoom.setEditable(false);
		add(yourRoom);
		JTextField room = new JTextField(board.getRooms().get(((RoomCell)board.getCellAt(p.getCurrentRow(),  p.getCurrentCol())).getInitial()));
		room.setEditable(false);
		add(room);
		JTextField person = new JTextField("Person:");
		person.setEditable(false);
		add(person);
		add(people);
		JTextField weapon = new JTextField("Weapon:");
		weapon.setEditable(false);
		add(weapon);
		add(weapons);
		submit = submitSetup();
		cancel = cancelSetup();
		add(submit);
		add(cancel);
		setTitle("Make a suggestion!");
		setSize(250,250);
	}
	
	private JComboBox<String> peopleSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c :  game.getCards()){
			if(c.getType() == CardType.PERSON) temp.addItem(c.getName());
		}
		return temp;
	}
	
	private JComboBox<String> weaponsSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c : game.getCards()){
			if(c.getType() == CardType.WEAPON) temp.addItem(c.getName());
		}
		return temp;
	}
	
	private JButton submitSetup(){
		JButton temp = new JButton("Submit");
		class SubmitListener implements ActionListener{
			SuggestionPanel sp;
			
			public SubmitListener(SuggestionPanel sp){
				this.sp = sp;
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Card a = game.handleSuggestion((String)people.getSelectedItem(),board.getRooms().get(((RoomCell)board.getCellAt(player.getCurrentRow(),  player.getCurrentCol())).getInitial()),(String)weapons.getSelectedItem(), player);
				JTextField temp = new JTextField((String)people.getSelectedItem() + " " + board.getRooms().get(((RoomCell)board.getCellAt(player.getCurrentRow(),  player.getCurrentCol())).getInitial()) + " " + (String)weapons.getSelectedItem());
				temp.setEditable(false);
				gameInt.setGuessField(temp);
				gameInt.repaint();
				if(a == null){
					int reply = JOptionPane.showConfirmDialog(null, "Would you like to make this suggestion an accusation?", "", JOptionPane.YES_NO_OPTION);
					if(reply != 0){
						boolean check = game.checkAccusation(new Solution((String)people.getSelectedItem(),board.getRooms().get(((RoomCell)board.getCellAt(player.getCurrentRow(),  player.getCurrentCol())).getInitial()),(String)weapons.getSelectedItem()));
						if(check){
							JOptionPane.showMessageDialog(null, "You have correctly guessed the solution!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
						else{
							JOptionPane.showMessageDialog(null, "You have incorrectly guessed the solution!", "", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				else{
					temp = new JTextField(a.getName());
					temp.setEditable(false);
					gameInt.setSuggestionResponse(temp);
					gameInt.repaint();
				}
				sp.dispose();
			}
			
		};
		temp.addActionListener(new SubmitListener(this));
		return temp;
	}
	
	private JButton cancelSetup(){
		JButton temp = new JButton("Cancel");
		class CancelListener implements ActionListener{
			SuggestionPanel sp;//To allow closing the frame
			
			public CancelListener(SuggestionPanel sp){
				this.sp = sp;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				sp.dispose();
			}
			
		};
		temp.addActionListener(new CancelListener(this));
		return temp;
	}
}
