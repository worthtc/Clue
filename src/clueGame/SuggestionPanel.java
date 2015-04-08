package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import clueGame.Card.CardType;

public class SuggestionPanel extends JFrame{
	
	private Player player;
	private JComboBox<String> people;
	private JComboBox<String> weapons;
	private JButton submit;
	private JButton cancel;
	
	
	public SuggestionPanel(Player p){
		player = p;
		setLayout(new GridLayout(4,2));
		people = peopleSetup();
		weapons = weaponsSetup();
		JTextField yourRoom = new JTextField("Your Room:");
		yourRoom.setEditable(false);
		add(yourRoom);
		JTextField room = new JTextField()
	}
	
	public JComboBox<String> peopleSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c :  player.getCards()){
			if(c.getType() == CardType.PERSON) temp.addItem(c.getName());
		}
		return temp;
	}
	
	public JComboBox<String> weaponsSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c : player.getCards()){
			if(c.getType() == CardType.WEAPON) temp.addItem(c.getName());
		}
		return temp;
	}
}
