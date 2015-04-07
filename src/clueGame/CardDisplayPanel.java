package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;

@SuppressWarnings("serial")
public class CardDisplayPanel extends JPanel{
	
	private ArrayList<Card> cards;
	
	private JPanel people;
	private JPanel rooms;
	private JPanel weapons;
	
	public CardDisplayPanel(ArrayList<Card> cards){
		this.cards = cards;
		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder(new EtchedBorder(), "My Cards:"));
		people = buildPeople();
		rooms = buildRooms();
		weapons = buildWeapons();
		add(people);
		add(rooms);
		add(weapons);
	}
	
	private JPanel buildPeople(){
		JPanel temp = new JPanel();
		ArrayList<Card> list = new ArrayList<Card>();
		for(Card c : cards){
			if(c.getType() == CardType.PERSON){
				list.add(c);
			}
		}
		temp.setLayout(new GridLayout(list.size(), 1));
		for(Card c : list){
			JTextField card = new JTextField(c.getName());
			card.setEditable(false);
			temp.add(card);
		}
		temp.setBorder(new TitledBorder(new EtchedBorder(), "People:"));
		return temp;
	}
	
	private JPanel buildRooms(){
		JPanel temp = new JPanel();
		ArrayList<Card> list = new ArrayList<Card>();
		for(Card c : cards){
			if(c.getType() == CardType.ROOM){
				list.add(c);
			}
		}
		temp.setLayout(new GridLayout(list.size(), 1));
		for(Card c : list){
			JTextField card = new JTextField(c.getName());
			card.setEditable(false);
			temp.add(card);
		}
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Rooms:"));
		return temp;
	}
	
	private JPanel buildWeapons(){
		JPanel temp = new JPanel();
		ArrayList<Card> list = new ArrayList<Card>();
		for(Card c : cards){
			if(c.getType() == CardType.WEAPON){
				list.add(c);
			}
		}
		temp.setLayout(new GridLayout(list.size(), 1));
		for(Card c : list){
			JTextField card = new JTextField(c.getName());
			card.setEditable(false);
			temp.add(card);
		}
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Weapons:"));
		return temp;
	}
}
