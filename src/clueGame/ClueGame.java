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
	   ClueGame gui = new ClueGame("map/Clue Map.txt","map/legend.txt","map/weaponLegend.txt","map/peopleLegend.txt", 3);
	   gui.setVisible(true);
	   gui.loadConfigFiles();
   }
   
   public ClueGame(String boardName, String legendName, String weaponLegend, String characterLegend, int numPlayers) {
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setTitle("Clue Board");
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
	   this.numPlayers = numPlayers;
   }

   public void generateDeck(){
	   for(String s : weapons){
		   cards.add(new Card(s, Card.CardType.WEAPON));
	   }
	   for(String s : characters){
		   cards.add(new Card(s, Card.CardType.PERSON));
	   }
	   HashSet<Character> keys = new HashSet<Character>(rooms.keySet());
	   for(Character c : keys){
		   if (c != 'W'){
			   cards.add(new Card(rooms.get(c), Card.CardType.ROOM));
		   }
	   }
   }
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

	public boolean checkAccusation(Solution solution){
		   if(solution.equals(this.solution)){
			   return true;
		   }
		   else{
			   return false;
		   }
	}
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
	public void loadConfigFiles() {
		try{
			playerConfigFiles();
			rooms = gameBoard.loadBoardConfig( boardName, boardLegend);
			add(gameBoard, BorderLayout.CENTER);
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
