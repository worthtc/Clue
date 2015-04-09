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
public class AccusationFrame extends JFrame{
	
	private ClueGame game;
	private JComboBox<String> people;
	private JComboBox<String> weapons;
	private JComboBox<String> room;
	private JButton submit;
	private JButton cancel;
	
	
	public AccusationFrame(ClueGame g){
		game = g;
		setLayout(new GridLayout(4,2));
		people = peopleSetup();
		weapons = weaponsSetup();
		JTextField yourRoom = new JTextField("Room:");
		yourRoom.setEditable(false);
		add(yourRoom);
		room = roomSetup();
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
		setTitle("Make an accusation!");
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
	
	private JComboBox<String> roomSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c : game.getCards()){
			if(c.getType() == CardType.ROOM) temp.addItem(c.getName());
		}
		return temp;
	}
	
	private JButton submitSetup(){
		JButton temp = new JButton("Submit");
		class SubmitListener implements ActionListener{
			AccusationFrame ap;
			
			public SubmitListener(AccusationFrame ap){
				this.ap = ap;
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getBoard().setIsSuggesting(false);
				boolean check = game.checkAccusation(new Solution((String)people.getSelectedItem(),(String)weapons.getSelectedItem(),(String)room.getSelectedItem()));
				if(check){
					JOptionPane.showMessageDialog(null, "You have correctly guessed the solution!", "", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
				else{
					((HumanPlayer)game.getPlayers().get(0)).setFinished(true);
					game.getBoard().setHumanFinished(true);
					JOptionPane.showMessageDialog(null, "You have incorrectly guessed the solution. Better luck next time!", "", JOptionPane.INFORMATION_MESSAGE);
				}
				ap.dispose();
			}
			
		};
		temp.addActionListener(new SubmitListener(this));
		return temp;
	}
	
	private JButton cancelSetup(){
		JButton temp = new JButton("Cancel");
		class CancelListener implements ActionListener{
			AccusationFrame ap;//To allow closing the frame
			
			public CancelListener(AccusationFrame ap){
				this.ap = ap;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				ap.dispose();
			}
			
		};
		temp.addActionListener(new CancelListener(this));
		return temp;
	}
	
}
