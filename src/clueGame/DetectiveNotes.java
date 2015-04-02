package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

@SuppressWarnings("serial")
public class DetectiveNotes extends JDialog {
	
	JPanel people, rooms, weapons, personGuess, roomGuess, weaponGuess;
	ArrayList<Card> cards;
	ArrayList<Card> playerCards;
	ArrayList<Card> roomCards;
	ArrayList<Card> weaponCards;
	
	public DetectiveNotes(ArrayList<Card> cards){
		setLayout(new GridLayout(3,2));
		this.cards = cards;
		playerCards = new ArrayList<Card>();
		roomCards = new ArrayList<Card>();
		weaponCards = new ArrayList<Card>();
		setTitle("Detective Notes!");
		setSize(450,450);
		for(Card c : cards){
			if(c.getType() == CardType.PERSON){
				playerCards.add(c);
			}
			else if(c.getType() == CardType.ROOM){
				roomCards.add(c);
			}
			else{
				weaponCards.add(c);
			}
		}
		people = peopleSetup();
		add(people);
		personGuess = peopleGuessSetup();
		add(personGuess);
		rooms = roomSetup();
		add(rooms);
		roomGuess = roomGuessSetup();
		add(roomGuess);
		weapons = weaponSetup();
		add(weapons);
		weaponGuess = weaponGuessSetup();
		add(weaponGuess);
	}
	
	public JPanel peopleSetup(){
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(3,2));
		temp.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		for(Card c: playerCards){
			temp.add(new JCheckBox(c.getName()));
		}
		return temp;
	}
	
	public JPanel roomSetup(){
		JPanel temp = new JPanel();
		int height = (roomCards.size()+1)/2;
		temp.setLayout(new GridLayout(height, 2));
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		for(Card c: roomCards){
			temp.add(new JCheckBox(c.getName()));
		}
		return temp;
	}

	public JPanel weaponSetup(){
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(3,2));
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		for(Card c: weaponCards){
			temp.add(new JCheckBox(c.getName()));
		}
		return temp;
	}

	public JPanel peopleGuessSetup(){
		JPanel temp = new JPanel();
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("I'm not sure");
		for(Card c : playerCards){
			combo.addItem(c.getName());
		}
		temp.add(combo, BorderLayout.CENTER);
		return temp;
	}

	public JPanel roomGuessSetup(){
		JPanel temp = new JPanel();
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("I'm not sure");
		for(Card c : roomCards){
			combo.addItem(c.getName());
		}
		temp.add(combo, BorderLayout.CENTER);
		return temp;
	}

	public JPanel weaponGuessSetup(){
		JPanel temp = new JPanel();
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("I'm not sure");
		for(Card c : weaponCards){
			combo.addItem(c.getName());
		}
		temp.add(combo, BorderLayout.CENTER);
		return temp;
	}

}
