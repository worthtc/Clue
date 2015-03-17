package clueGameTests;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import clueGame.*;

public class CluePlayerTests {
	private static ClueGame game;
	private final int NUM_CARDS = 20; //Number of cards, determined by legend
	private final int NUM_WEAPONS = 12; //determined by legend
	private final int NUM_PEOPLE = 8;//determined by legend
	private final int NUM_PLAYERS = 5; //determined by legend
	private final int NUM_HUMAN_PLAYERS = 2;
	private final int NUM_COMPUTER_PLAYERS = 3;
	
	@BeforeClass
	public static void init(){
		game = new ClueGame("Clue/map/Clue Map.txt","Clue/map/legend.txt");
		game.loadConfigFiles();
	}
	//Total cards generated must match the number of cards specified in the legend
	@Test
	public void testNumberOfCards(){
		assertEquals(game.getCards().size(),NUM_CARDS);
	}
	//Create the correct number of PEOPLE cards, and contains a random selection of them
	@Test
	public void testNumberOfPeople(){
		ArrayList<Card> cards = game.getCards();
		int test = 0;
		for(Card c: cards){
			if(c.getType() == Card.CardType.PERSON){
				test++;
			}
		}
		assertEquals(test, NUM_PEOPLE);
	}
	//Create the correct number of WEAPON cards, and contains a random selection of them
	@Test
	public void testNumberOfWeapons(){
		ArrayList<Card> cards = game.getCards();
		int test = 0;
		for(Card c: cards){
			if(c.getType() == Card.CardType.WEAPON){
				test++;
			}
		}
		assertEquals(test, NUM_WEAPONS);
	}
	//Test correct players/computers created
	@Test
	public void testNumberOfPlayers(){
		ArrayList<Player> players = game.getPlayers();
		int numComps = 0;
		int numHumans = 0;
		assertEquals(players.size(), NUM_PLAYERS);
		for(Player p: players){
			if(p.isComputer()){
				numComps++;
			}
			else if(p.isHuman()){
				numHumans++;
			}
		}
		assertEquals(numComps, NUM_COMPUTER_PLAYERS);
		assertEquals(numHumans, NUM_HUMAN_PLAYERS);
	}
	//Each player has correct number and type of cards
	@Test
	public void testDeal(){
		
	}
}
