package clueGameTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import clueGame.*;

public class CluePlayerTests {
	private static ClueGame game;
	private final int NUM_CARDS = 22; //Number of cards, determined by legend
	private final int NUM_WEAPONS = 6; //determined by legend
	private final int NUM_PEOPLE = 6;//determined by legend
	private final int NUM_PLAYERS = 3; //determined by legend
	private final int NUM_HUMAN_PLAYERS = 1;
	private final int NUM_COMPUTER_PLAYERS = 2;
	
	private Solution sol;
	
	@BeforeClass
	public static void init(){
		game = new ClueGame("map/Clue Map.txt","map/legend.txt","map/weaponLegend.txt","map/peopleLegend.txt", 3);
		game.loadConfigFiles();
		game.generateDeck();
		game.makePlayers();
		game.deal();
	}
	//Total cards generated must match the number of cards specified in the legend
	@Test
	public void testNumberOfCards(){
		assertEquals(NUM_CARDS, game.getCards().size());
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
		assertEquals(NUM_PEOPLE, test);
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
		assertEquals(NUM_WEAPONS, test);
	}
	//Test correct players/computers created
	@Test
	public void testNumberOfPlayers(){
		ArrayList<Player> players = game.getPlayers();
		int numComps = 0;
		int numHumans = 0;
		assertEquals(NUM_PLAYERS, players.size());
		for(Player p: players){
			if(p.isComputer()){
				numComps++;
			}
			else if(p.isHuman()){
				numHumans++;
			}
		}
		assertEquals(NUM_COMPUTER_PLAYERS, numComps);
		assertEquals(NUM_HUMAN_PLAYERS, numHumans);
	}
	//Each player has correct number and not 2 players have the same card.
	@Test
	public void testDeal(){
		ArrayList<Player> players = game.getPlayers();
		assertEquals(7, players.get(0).getCards().size());
		assertEquals(6, players.get(1).getCards().size(), players.get(2).getCards().size());
		assertEquals(19, players.get(0).getCards().size() + players.get(1).getCards().size() + players.get(2).getCards().size());
		for (int i = 0; i < 6; i++){
			assertFalse(players.get(0).getCards().get(i).getName().equals(players.get(1).getCards().get(i).getName()));
			assertFalse(players.get(1).getCards().get(i).getName().equals(players.get(2).getCards().get(i).getName()));
			assertFalse(players.get(0).getCards().get(i).getName().equals(players.get(2).getCards().get(i).getName()));
		}
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
