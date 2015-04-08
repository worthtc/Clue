package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import clueGame.Card.CardType;

@SuppressWarnings("serial")
public class SuggestionPanel extends JFrame{
	
	private Player player;
	private Board board;
	private ClueGame game;
	private JComboBox<String> people;
	private JComboBox<String> weapons;
	private JButton submit;
	private JButton cancel;
	
	
	public SuggestionPanel(Player p, Board b, ClueGame g){
		player = p;
		board = b;
		game = g;
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
		for(Card c :  player.getCards()){
			if(c.getType() == CardType.PERSON) temp.addItem(c.getName());
		}
		return temp;
	}
	
	private JComboBox<String> weaponsSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c : player.getCards()){
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
				game.handleSuggestion((String)people.getSelectedItem(),board.getRooms().get(((RoomCell)board.getCellAt(player.getCurrentRow(),  player.getCurrentCol())).getInitial()),(String)weapons.getSelectedItem(), player);
				//close the window!
			}
			
		};
		temp.addActionListener(new SubmitListener(this));
		return temp;
	}
	
	private JButton cancelSetup(){
		JButton temp = new JButton();
		class CancelListener implements ActionListener{
			SuggestionPanel sp;//To allow closing the frame
			
			public CancelListener(SuggestionPanel sp){
				this.sp = sp;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				//close the window!
			}
			
		};
		temp.addActionListener(new CancelListener(this));
		return temp;
	}
}
