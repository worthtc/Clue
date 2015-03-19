package clueGameTests;

import static org.junit.Assert.*;

import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.*;

public class CluePlayerActionTests {
	private ClueGame game;
	
	@Before
	public void init(){
		game = new ClueGame("map/Clue Map.txt","map/legend.txt","map/weaponLegend.txt","map/peopleLegend.txt", 3);
		game.loadConfigFiles();
		game.generateDeck();
		game.makePlayers();
	}
	//Testing disporveSuggestion itself, for one player with various cards that player has or does not have
	@Test
	public void disproveSuggestionTest() {
		game.getPlayers().get(1).giveCard(new Card("Mindslaver", Card.CardType.WEAPON));
		game.getPlayers().get(1).giveCard(new Card("Karn", Card.CardType.PERSON));
		game.getPlayers().get(1).giveCard(new Card("Alara", Card.CardType.ROOM));
		//Testing a suggestion where the player has exactly one of the cards
		Card a = game.getPlayers().get(1).disproveSuggestion("Karn","Kamigawa","Batterskull");
		assertTrue(a.equals(new Card("Karn", Card.CardType.PERSON)));
		//Testing a suggestion where the player has two of the cards
		a = game.getPlayers().get(1).disproveSuggestion("Karn","Alara","Batterskull");
		assertTrue(a.equals(new Card("Karn", Card.CardType.PERSON)) || a.equals(new Card("Alara", Card.CardType.ROOM)));
		a = game.getPlayers().get(1).disproveSuggestion("Urza","Kamigawa","Batterskull");
		assertEquals(a, null);
	}
	//Giving each player a specific set of cards, then testing an accusation that each player can refute
	@Test
	public void handleSuggestionInOrderTest(){
		game.getPlayers().get(0).giveCard(new Card("Door to Nothingness", Card.CardType.WEAPON));
		game.getPlayers().get(0).giveCard(new Card("Nicol Bolas", Card.CardType.PERSON));
		game.getPlayers().get(0).giveCard(new Card("Zendikar", Card.CardType.ROOM));
		game.getPlayers().get(1).giveCard(new Card("Karn", Card.CardType.PERSON));
		game.getPlayers().get(1).giveCard(new Card("Mindslaver", Card.CardType.WEAPON));
		game.getPlayers().get(1).giveCard(new Card("Alara", Card.CardType.ROOM));
		game.getPlayers().get(2).giveCard(new Card("Loxodon Warhammer", Card.CardType.WEAPON));
		game.getPlayers().get(2).giveCard(new Card("Ugin, the Spirit Dragon", Card.CardType.PERSON));
		game.getPlayers().get(2).giveCard(new Card("Innistrad", Card.CardType.ROOM));
		//testing that when player 1 makes the suggestion, it is player 2 that refutes by revealing the specified card
		Card a = game.handleSuggestion("Karn","Loxodon Warhammer","Zendikar",game.getPlayers().get(1));
		assertTrue(a.equals(new Card("Loxodon Warhammer", Card.CardType.WEAPON)));
		//Same as above, but player 2 makes the suggestion and player 0 (the human player) refutes
		a = game.handleSuggestion("Karn","Loxodon Warhammer","Zendikar",game.getPlayers().get(2));
		assertTrue(a.equals(new Card("Zendikar", Card.CardType.ROOM)));
		//etc
		a = game.handleSuggestion("Karn","Loxodon Warhammer","Zendikar",game.getPlayers().get(0));
		assertTrue(a.equals(new Card("Karn", Card.CardType.PERSON)));
		//Checking to make sure that none of the players refute a suggestion they cannot
		a = game.handleSuggestion("Sorin Markov","Batterskull","Dominaria",game.getPlayers().get(2));
		assertEquals(a, null);
	}
	//Testing the valid targets for player 1 (Urza, who begins at position (15,8))
	@Test
	public void testPlayerTargeting(){
		
	}
}
