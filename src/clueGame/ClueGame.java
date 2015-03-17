package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import clueGame.Board;

public class ClueGame {
	private String boardName;
	private String boardLegend;
	private String weaponLegend;
	private String characterLegend;
	private Board gameBoard;
	Map<Character,String> rooms;
	
	private ArrayList<String> weapons;
	private ArrayList<String> characters;
	private ArrayList<Card> cards;
	private ArrayList<Player> referencePlayers;
	private ArrayList<Player> players;
	private Solution solution;
   
   public Board getBoard() {
		return gameBoard;
   }
   
   public ClueGame(){
	   
   }
   
   public ClueGame(String boardName, String legendName, String weaponLegend, String characterLegend) {
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
   }
//TODO   
   public void generateDeck(){
	   for(String s : weapons){
		   cards.add(new Card(s, Card.CardType.WEAPON));
	   }
	   for(String s : characters){
		   cards.add(new Card(s, Card.CardType.PERSON));
	   }
	   Set<Character> keys = rooms.keySet();
	   for(Character c : keys){
		   cards.add(new Card(rooms.get(c), Card.CardType.ROOM));
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
		   roomsLeft.add(new Card(rooms.get(c), Card.CardType.ROOM));
	   }
	   int weapon = (int) Math.random()*weaponsLeft.size();
	   int character = (int) Math.random()*charactersLeft.size();
	   int room = (int) Math.random()*roomsLeft.size();
	   solution = new Solution(weaponsLeft.get(weapon).getName(), charactersLeft.get(character).getName(), roomsLeft.get(room).getName());
	   weaponsLeft.remove(weapon);
	   charactersLeft.remove(character);
	   roomsLeft.remove(room);
	   int currentPlayer = 0;
	   for(Card c :  weaponsLeft){
		   players.get(currentPlayer).giveCard(c);
		   currentPlayer++;
		   if(currentPlayer >= players.size()){
			   currentPlayer = 0;
		   }
	   }
	   for(Card c : charactersLeft){
		   players.get(currentPlayer).giveCard(c);
		   currentPlayer++;
		   if(currentPlayer >= players.size()){
			   currentPlayer = 0;
		   }
	   }
	   for(Card c : roomsLeft){
		   players.get(currentPlayer).giveCard(c);
		   currentPlayer++;
		   if(currentPlayer >= players.size()){
			   currentPlayer = 0;
		   }
	   }
   }
   
   public void selectAnswer(){
	   
   }
   
   public void handleSuggestion(String person, String room, String weapon, Player accusingPlayer){
	   
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
			weapons.add(temp.substring(0,temp.length()-2));
		}
		currentLine = 0;
		inf.close();
		try{
			legends.close();
		}catch(IOException e){
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
			for(int i = 0; i<temp.length(); i++){
				int numSlashes = 0;
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
			gameBoard.loadBoardConfig( boardName, boardLegend);
		   }catch (BadConfigFormatException e ){
			   System.out.println( e.getMessage() );
		   }catch (FileNotFoundException e){
			   System.out.println(e.getMessage());
		   }
	   }
	   public void loadRoomConfig() throws BadConfigFormatException {
		  gameBoard.loadBoardConfig( boardName, boardLegend);
	   }
	   public Board getBoardLayout(){
		return gameBoard;
		   
	   }
}
