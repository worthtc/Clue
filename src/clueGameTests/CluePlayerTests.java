package clueGameTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import clueGame.*;

public class CluePlayerTests {
	private static ClueGame game;
	private final int NUM_CARDS = 23; //Number of cards, determined by legend
	private final int NUM_WEAPONS = 6; //determined by legend
	private final int NUM_PEOPLE = 6;//determined by legend
	private final int NUM_PLAYERS = 5; //determined by legend
	private final int NUM_HUMAN_PLAYERS = 2;
	private final int NUM_COMPUTER_PLAYERS = 3;
	
	private Solution sol;
	
	@BeforeClass
	public static void init(){
		game = new ClueGame("map/Clue Map.txt","map/legend.txt","map/weaponLegend.txt","map/peopleLegend.txt");
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
		ArrayList<Player> players = game.getPlayers();
		assertTrue(players.get(1).getCards().size() != 0);
		assertTrue(players.get(3).getCards().size() != 0);
		assertEquals(players.get(1).getCards().size(), players.get(2).getCards().size(), 1);
		assertEquals(players.get(2).getCards().size(), players.get(3).getCards().size(), 1);
		assertEquals(players.get(1).getCards().size(), players.get(3).getCards().size(), 1);
	}
	//Testing a solution is created, that it is size 3, and that is has one of each card type
	@Test
	public void testSolution(){
		Solution sol = game.getSolution();
		assertFalse(sol.getPerson().equals(null));
		assertFalse(sol.getWeapon().equals(null));
		assertFalse(sol.getRoom().equals(null));
		assertTrue(game.getCharacters().contains(sol.getPerson()));
		assertTrue(game.getWeapons().contains(sol.getWeapon()));	
	}
	@Before
	public void setSolution(){
		sol = new Solution("Jace the Mind Sculptor", "Batterskull", "Dominaria");
		game.setSolution(sol);
	}
	@Test
	public void testAccusation(){
		assertFalse(game.checkAccusation(new Solution("Nicol Bolas", "Batterskull","Dominaria")));
		assertFalse(game.checkAccusation(new Solution("Jace the Mind Sculptor", "Umezawa's Jitte", "Dominaria")));
		assertFalse(game.checkAccusation(new Solution("Jace the Mind Sculptor", "Batterskull", "Kamigawa")));
		assertTrue(game.checkAccusation(new Solution("Jace the Mind Sculptor", "Batterskull", "Dominaria")));
	}
}
