package clueGame;

import java.awt.BorderLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;

import clueGame.Board;

public class ClueGame extends JFrame {
	private int numPlayers;
	private String boardName;
	private String boardLegend;
	private String weaponLegend;
	private String characterLegend;
	private Board gameBoard;
	private Map<Character,String> rooms;
	
	private ArrayList<String> weapons;
	private ArrayList<String> characters;
	private ArrayList<Card> cards;
	private ArrayList<Player> referencePlayers;
	private ArrayList<Player> players;
	private Solution solution;
   
   public ClueGame(){
	   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Board");
		setSize(500,500);
   }
   
   public static void main(String[] args){
	   ClueGame gui = new ClueGame("map/Clue Map.txt","map/legend.txt","map/weaponLegend.txt","map/peopleLegend.txt", 6);
	   gui.setVisible(true);
	   DetectiveNotes gui2 = new DetectiveNotes(gui.getCards());
	   gui2.setVisible(true);
	   GameInterface gui3 = new GameInterface();
	   gui3.setVisible(true);
   }
   
   public ClueGame(String boardName, String legendName, String weaponLegend, String characterLegend, int numPlayers) {
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set basic load functions for the JFrame
	   setTitle("Clue Board");
	   setSize(500,500);
	   rooms = new HashMap<Character,String>(); //Initialize all generic global variables
	   gameBoard = new Board();
	   this.boardName = boardName;
	   boardLegend = legendName;
	   this.weaponLegend = weaponLegend;
	   this.characterLegend = characterLegend;
	   weapons = new ArrayList<String>();
	   characters = new ArrayList<String>();
	   cards = new ArrayList<Card>();
	   referencePlayers = new ArrayList<Player>();
	   players = new ArrayList<Player>();
	   this.numPlayers = numPlayers;
	   loadConfigFiles(); //Fill global variables with actual information, necessary to build subsequent JPanels
	   add(gameBoard, BorderLayout.CENTER);
	   generateDeck();
	   makePlayers();
	   for(Player p : players){//Initialize board with current players
		   gameBoard.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setIsOccupied(true);
		   gameBoard.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setPlayerColor(p.getColor());
	   }
	   
   }

   public void generateDeck(){
	   for(String s : weapons){//For every string encountered as a weapon, make a WEAPON card
		   cards.add(new Card(s, Card.CardType.WEAPON));
	   }
	   for(String s : characters){//For every string encountered as a player name, make a PERSON card
		   cards.add(new Card(s, Card.CardType.PERSON));
	   }
	   HashSet<Character> keys = new HashSet<Character>(rooms.keySet());
	   for(Character c : keys){//For every element in the rooms map (generated in Board), make a ROOM card
		   if (c != 'W'){
			   cards.add(new Card(rooms.get(c), Card.CardType.ROOM));
		   }
	   }
   }
   /* We create three temporary ArrayLists to pick a new random Solution from the lists
    * We add each card to a master list of cards left after removing the cards chosen for the solution
    * For each card in the list of cards left, we choose a random one, deal it out to the player in turn order, then remove it from the list and continue
    * until there are no elements left in cardsLeft.
    */
   public void deal(){
	   ArrayList<Card> weaponsLeft = new ArrayList<Card>();
	   ArrayList<Card> charactersLeft = new ArrayList<Card>();
	   ArrayList<Card> roomsLeft = new ArrayList<Card>();
	   for(String s : weapons){
		   weaponsLeft.add(new Card(s, Card.CardType.WEAPON));
	   }
	   for(String s : characters){
		   charactersLeft.add(new Card(s, Card.CardType.PERSON));
	   }
	   Set<Character> keys = rooms.keySet();
	   for(Character c : keys){
		   if (c != 'W'){
			   roomsLeft.add(new Card(rooms.get(c), Card.CardType.ROOM));
		   }
	   }
	   int weapon = (int) (Math.random()*weaponsLeft.size());
	   int character = (int) (Math.random()*charactersLeft.size());
	   int room = (int) (Math.random()*roomsLeft.size());
	   solution = new Solution(weaponsLeft.get(weapon).getName(), charactersLeft.get(character).getName(), roomsLeft.get(room).getName());
	   weaponsLeft.remove(weapon);
	   charactersLeft.remove(character);
	   roomsLeft.remove(room);
	   int currentPlayer = 0;
	   ArrayList<Card> cardsLeft = new ArrayList<Card>();
	   for(Card c :  weaponsLeft){
		   cardsLeft.add(c);
	   }
	   for(Card c : charactersLeft){
		   cardsLeft.add(c);
	   }
	   for(Card c : roomsLeft){
		   cardsLeft.add(c);
	   }
	   while(cardsLeft.size() != 0){
		   int choice = (int)(Math.random()*cardsLeft.size());
		   players.get(currentPlayer).giveCard(cardsLeft.get(choice));
		   cardsLeft.remove(choice);
		   currentPlayer++;
		   if(currentPlayer >= players.size()){
			   currentPlayer = 0;
		   }
	   }
   }
   
   public void selectAnswer(){
	   
   }
   /*
    * Called when a player creates a suggestion. Goes through the list of players in turn order after the initiating player
    * calls to the players to disprove the suggestion; if they cannot, they return null and we continue through turn order, if they do they return any card, 
    * and this method returns that card to be revealed.
    * If we make it back to the suggesting player's turn, we return null and follow-up methods are called to prompt the player to make an accusation
    */
   public Card handleSuggestion(String person, String room, String weapon, Player accusingPlayer){
	   int currentPlayer = players.indexOf(accusingPlayer);
	   currentPlayer++;
	   if(currentPlayer >= players.size()) currentPlayer = 0;
	   
	   while(currentPlayer != players.indexOf(accusingPlayer)){
		   Card a = players.get(currentPlayer).disproveSuggestion(person, room, weapon);
		   if(a!= null) return a;
		   currentPlayer++;
		   if(currentPlayer >= players.size()) currentPlayer = 0;
	   }
	   return null;
    }
   //Various getters/setters
   public Board getBoard() {
		return gameBoard;
   }
   
   public ArrayList<Card> getCards() {
		return cards;
   }
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<String> getWeapons() {
		return weapons;
	}

	public ArrayList<String> getCharacters() {
		return characters;
	}
	
	public Solution getSolution() {
		return solution;
	}
	
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	//Checks the current solution to see if it matches the accusation
	public boolean checkAccusation(Solution solution){
		   if(solution.equals(this.solution)){
			   return true;
		   }
		   else{
			   return false;
		   }
	}
	/*
	 * A pair method to LoadConfigFiles(), which is called in board. This loads the legend files associated with the players and the weapons, and loads them into related ArrayLists.
	 * First opens the legend file associated with weapons, reads each line, adds it to 'weapons' list.
	 * Then we iterate through the player list, check for various bad formatting exceptions related to delimiting characters, adding the name, color and starting position
	 * into the player list and Player objects related to each. 
	 */
	public void playerConfigFiles() throws BadConfigFormatException, FileNotFoundException{
		FileReader legends = new FileReader(weaponLegend);
		Scanner inf = new Scanner(legends);
		int currentLine = 0;
		while(inf.hasNextLine()){
			String temp  = inf.nextLine();
			currentLine++;
			for(int i = 0; i<temp.length(); i++){
				if (i != temp.length()-1 && temp.charAt(i) == ';'){
					inf.close();
					throw new BadConfigFormatException("There was a semi-colon at a place other than the end of line " + currentLine + "in the" + weaponLegend + "file.");
				}
				else if (i == temp.length()-1 && temp.charAt(i) != ';'){
					inf.close();
					throw new BadConfigFormatException("There was not a semi-colon at the end of line " + currentLine + "in the " + weaponLegend + "file.");
				}
				
			}
			weapons.add(temp.substring(0,temp.length()-1));
		}
		currentLine = 0;
		inf.close();
		try{
			legends.close();
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
		legends = new FileReader(characterLegend);
		inf = new Scanner(legends);
		while(inf.hasNextLine()){
			String temp  = inf.nextLine();
			String name = "";
			int startRow = 0;
			int startCol = 0;
			String color = "";
			int reference = 0;
			currentLine++;
			int numSlashes = 0;
			for(int i = 0; i<temp.length(); i++){
				if (i != temp.length()-1 && temp.charAt(i) == ';'){
					inf.close();
					throw new BadConfigFormatException("There was a semi-colon at a place other than the end of line " + currentLine + "in the " + characterLegend + "file.");
				}
				else if (i == temp.length()-1 && temp.charAt(i) != ';'){
					inf.close();
					throw new BadConfigFormatException("There was not a semi-colon at the end of line " + currentLine + "in the " + characterLegend + "file.");
				}
				if(temp.charAt(i) == '/'){
					numSlashes++;
					if(numSlashes > 3){
						inf.close();
						throw new BadConfigFormatException("There were too many /'s on line " + currentLine + "of the " + characterLegend + "file.");
					}
					if(numSlashes == 1){
						characters.add(temp.substring(0,i));
						name = temp.substring(0,i);
						reference = i;
					}
					else if(numSlashes == 2){
						startRow = Integer.parseInt(temp.substring(reference+1, i));
						reference = i;
					}
					else if(numSlashes == 3){
						startCol = Integer.parseInt(temp.substring(reference+1, i));
						color = temp.substring(i+1, temp.length()-1);
					}
				}
			}
			referencePlayers.add(new Player(name, color, startRow, startCol));
		}
		inf.close();
		try{
			legends.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	//Loads the information from each legend file into associated structures. See specific methods for more information.
	public void loadConfigFiles() {
		try{
			playerConfigFiles();
			rooms = gameBoard.loadBoardConfig( boardName, boardLegend);
		   }catch (BadConfigFormatException e ){
			   System.out.println(e);
		   }catch (FileNotFoundException e){
			   System.out.println(e);
		   }
	}
	public void makePlayers(){//Temporary, simple used to test that players can be made and hold cards
		for (int i = 0; i < numPlayers; i++){
			if (i == 0){
				players.add(new HumanPlayer(referencePlayers.get(findSpecificCharacter("Jace the Mind Sculptor"))));
			}
			else if (i == 1){
				players.add(new ComputerPlayer(referencePlayers.get(findSpecificCharacter("Urza"))));
			}
			else if (i == 2){
				players.add(new ComputerPlayer(referencePlayers.get(findSpecificCharacter("Nicol Bolas"))));
			}
			else if (i == 3){
				players.add(new ComputerPlayer(referencePlayers.get(findSpecificCharacter("Ugin, the Spirit Dragon"))));
			}
			else if (i == 4){
				players.add(new ComputerPlayer(referencePlayers.get(findSpecificCharacter("Karn"))));
			}
			else if (i == 5){
				players.add(new ComputerPlayer(referencePlayers.get(findSpecificCharacter("Sorin Markov"))));
			}
		}
	}
	public int findSpecificCharacter(String character){
		int index = 0;
		for (int i = 0; i < referencePlayers.size(); i++){
			if (referencePlayers.get(i).getName().equals(character)){
				index = i;
			}
		}
		return index;
	}
   public void loadRoomConfig() throws BadConfigFormatException {
	  gameBoard.loadBoardConfig( boardName, boardLegend);
   }
   public Board getBoardLayout(){
	return gameBoard;
	   
   }
}
