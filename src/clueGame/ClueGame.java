package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import clueGame.Board;

@SuppressWarnings("serial")
public class ClueGame extends JFrame {
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

	@SuppressWarnings("unused")
	public static void main(String[] args){
		ClueGame gui = new ClueGame("map/Clue Map2.txt","map/legend.txt","map/weaponLegend.txt","map/peopleLegend.txt");
		
	}

	public ClueGame(String boardName, String legendName, String weaponLegend, String characterLegend) {
		//Initialize the JFrame and all of the variables
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Magical Clue!"); // Note: title is a work in progress
		setSize(500,500);
		rooms = new HashMap<Character,String>();
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
		loadConfigFiles();//Fill global variables with actual information, necessary to build subsequent JPanels
		add(gameBoard, BorderLayout.CENTER);
		try{ //Tries to make a number of players. If that number exceeds the total number of possible players, an exception is thrown.
			makePlayers(1,2);
		}catch(Exception e){
			System.out.println(e);
		}
		for(Player p : players){//Initialize board with current players
			gameBoard.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setIsOccupied(true);
			gameBoard.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setPlayerColor(p.getColor());
		}
		
		//Create the menu for exit and the Detective Notes
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		setJMenuBar(menuBar);
		JMenuItem notes = new JMenuItem("Toggle Detective Notes");
		JMenuItem exit = new JMenuItem("Exit");
		class DetectivePadListener implements ActionListener {
			private DetectiveNotes currentFrame;
			ArrayList<Card> cards;

			DetectivePadListener( ArrayList<Card> c ){
				super();
				cards = c;
			}
			public void actionPerformed(ActionEvent e)
			{
				if( currentFrame != null){
					if( currentFrame.isVisible() ){
						currentFrame.setVisible(false);
					}
					else{
						currentFrame.setVisible(true);
					}
				}
				else{
					currentFrame = new DetectiveNotes(cards);
					currentFrame.setVisible(true);
				}

			}
		}
		class ExitListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		notes.addActionListener( new DetectivePadListener(this.getCards()));
		menu.add(notes);
		exit.addActionListener(new ExitListener());
		menu.add(exit);
		menuBar.add(menu);
		
		GameInterface gameControl = new GameInterface(players);
		add(gameControl, BorderLayout.SOUTH);
		
		JOptionPane.showMessageDialog(this, "You are Nicol Bolas, press the Next Player button to start!", "Clue Intro", JOptionPane.INFORMATION_MESSAGE);
		//Set the frame to be visible
		setVisible(true);
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
	   ArrayList<Card> cardsLeft = new ArrayList<Card>();
	   String weaponChoice = ""; //Initialized to blank strings to satisfy compiler message regarding initialization in following loops.
	   String personChoice = "";
	   String roomChoice = "";
	   int random =(int) Math.random()*weapons.size();
	   for(String s : weapons){
		   if(weapons.indexOf(s) == random){ //guaranteed to enter this statement
			   weaponChoice = s;
		   }
		   else{
			   cardsLeft.add(new Card(s, Card.CardType.WEAPON));
		   }
	   }
	   random =(int) Math.random()*characters.size();
	   for(String s : characters){
		   if(characters.indexOf(s) == random){
			   personChoice = s;
		   }
		   else{
			   cardsLeft.add(new Card(s, Card.CardType.PERSON));
		   }
	   }
	   Set<Character> keys = rooms.keySet();
	   random = (int) Math.random()*keys.size();
	   for(Character c : keys){
		   if (c != 'W'){
			   if(characters.indexOf(rooms.get(c)) == random){
				   roomChoice = rooms.get(c);
			   }
			   else{
				   cardsLeft.add(new Card(rooms.get(c), Card.CardType.ROOM));
			   }
		   }
	   }
	   solution = new Solution(weaponChoice, personChoice, roomChoice);
	   int currentPlayer = 0;
	   while(cardsLeft.size() != 0){
		   int choice = (int)(Math.random()*cardsLeft.size());
		   players.get(currentPlayer).giveCard(cardsLeft.get(choice));
		   cardsLeft.remove(choice);
		   currentPlayer++;
		   currentPlayer = currentPlayer%players.size();
	   }
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
	   currentPlayer = currentPlayer%players.size();
	   
	   while(currentPlayer != players.indexOf(accusingPlayer)){
		   Card a = players.get(currentPlayer).disproveSuggestion(person, room, weapon);
		   if(a!= null) return a;
		   currentPlayer++;
		   currentPlayer = currentPlayer%players.size();
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
		while(inf.hasNextLine()){
			String temp  = inf.nextLine();
			String[] splitTemp = temp.split(";");
			if( splitTemp.length != 1 ){
				inf.close();
				throw new BadConfigFormatException(weaponLegend + "was not correctly formatted, more than 1 semicolon was found");
			}
			weapons.add(splitTemp[0]);
		}
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
			String[] splitTemp = temp.split(";");
			if( splitTemp.length != 1 ){
				inf.close();
				throw new BadConfigFormatException(characterLegend + "was not correctly formatted, more than 1 semicolon was found");
			}
			String[] stringParse = splitTemp[0].split("/");
			if( stringParse.length != 4 ){
				inf.close();
				throw new BadConfigFormatException(characterLegend + "was not correctly formatted, not all of the player data fields were found");
			}
			String name = stringParse[0];
			int startRow = Integer.parseInt(stringParse[1]);
			int startCol = Integer.parseInt(stringParse[2]);
			String color = stringParse[3];
			characters.add(stringParse[0]);
			referencePlayers.add(new HumanPlayer(name, color, startRow, startCol));
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
	public void makePlayers(int humans, int computers) throws Exception{//Temporary, simple used to test that players can be made and hold cards
		if(humans + computers > referencePlayers.size()){
			throw new Exception("Too many players! Total players can only add up to " + referencePlayers.size() + ".");
		}
		int currentPlayer = 0;
		for(int i = 0; i<humans; i++){
			players.add(new HumanPlayer(referencePlayers.get(currentPlayer)));
			currentPlayer++;
		}
		for(int i = 0; i<computers; i++){
			players.add(new ComputerPlayer(referencePlayers.get(currentPlayer)));
			currentPlayer++;
		}
	}
	
   public void loadRoomConfig() throws BadConfigFormatException {
	  gameBoard.loadBoardConfig( boardName, boardLegend);
   }
}
