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
	
	public Solution getSolution() {
		return solution;
	}
	
	public void checkAccusation(Solution solution){
		   if(solution.equals(this.solution)){
			   
		   }
		   else{
			   
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
			currentLine++;
			for(int i = 0; i<temp.length(); i++){
				if (i != temp.length()-1 && temp.charAt(i) == ';'){
					inf.close();
					throw new BadConfigFormatException("There was a semi-colon at a place other than the end of line " + currentLine + "in the " + characterLegend + "file.");
				}
				else if (i == temp.length()-1 && temp.charAt(i) != ';'){
					inf.close();
					throw new BadConfigFormatException("There was not a semi-colon at the end of line " + currentLine + "in the " + characterLegend + "file.");
				}
			}
			characters.add(temp.substring(0,temp.length()-2));
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
