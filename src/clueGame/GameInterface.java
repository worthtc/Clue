package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
//????
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

@SuppressWarnings("serial")
public class GameInterface extends JPanel {
	private JPanel buttonLayout;
	private JPanel messageLayout;
	private JButton suggest;
	private JButton nextPlayer;
	private JPanel currentPlayerTurn;
	private JPanel lowerLeftText;
	private JTextField player;
	private JPanel dieRollPanel;
	private JTextField dieRoll;
	private JPanel suggestionResponsePanel;
	private JTextField suggestionResponse;
	private ArrayList<Player> players;
	private int currentIndex;
	private ClueGame game;
	private Board currentBoard;
	private Set<BoardCell> targetSet;
	private JTextField guessField;
	
	
	
	public GameInterface( ArrayList<Player> playerList, Board board, ClueGame game){
		players = playerList;
		currentIndex = 0;
		currentBoard = board;
		this.game = game;
		setLayout(new BorderLayout());
		buttonLayout = buttonLayoutSetup();
		add(buttonLayout, BorderLayout.EAST);
		messageLayout = messageLayoutSetup();
		add(messageLayout, BorderLayout.CENTER);
		currentPlayerTurn = createCurrentPlayerPanel();
		messageLayout.add(currentPlayerTurn);
		lowerLeftText = lowerLeftTextSetup();
		messageLayout.add(lowerLeftText);
		dieRollPanel = dieRollSetup();
		lowerLeftText.add(dieRollPanel);
		suggestionResponsePanel = suggestionResponseSetup();
		lowerLeftText.add(suggestionResponsePanel);
		JPanel guessBox = new JPanel();
		//guessBox.setLayout(new GridLayout(1,2));
		guessBox.setLayout(new BorderLayout());
		JLabel guessLabel = new JLabel("Guess");
		guessField = new JTextField(10);
		guessBox.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		guessField.setEditable(false);
		guessBox.add(guessLabel, BorderLayout.WEST);
		guessBox.add(guessField, BorderLayout.CENTER);
		lowerLeftText.add(guessBox);
		
	}
	private JPanel buttonLayoutSetup(){
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		suggest = new JButton("Make an Accusation");
		class AccusationListener implements ActionListener {
			private ClueGame game;
			public AccusationListener(ClueGame g){
				game = g;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentBoard.getCurrentIndex() == 0 && !((HumanPlayer)game.getPlayers().get(currentBoard.getCurrentIndex())).isFinished()){
					AccusationFrame gui = new AccusationFrame(game);
					gui.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "You can't make an accusation now!", "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		}
		suggest.addActionListener(new AccusationListener(game));
		temp.add(suggest);
		nextPlayer = new JButton("Next Player");
		//Create the listener for the Next player button
		class NextPlayerListener implements ActionListener {
			private GameInterface game;
			private ClueGame currentClueGame;
			public void actionPerformed(ActionEvent e)
			{
				
				currentBoard.setPlayers( players );
				currentBoard.setCurrentIndex( currentIndex );
				player.setText(players.get(currentIndex).toString());
				if( players.get(currentIndex).isComputer() && ((ComputerPlayer)players.get(currentIndex)).getAccusationFlag()){
					((ComputerPlayer)players.get(currentIndex)).makeAccusation();
				}
				//If we are currently on a human player and that player is not finished we stop doing the action
				if( players.get(currentIndex).isHuman() && !(((HumanPlayer)players.get(currentIndex)).isFinished())){
					JOptionPane.showMessageDialog(game, "Please Choose a cell to move to!", "", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if( players.get(currentIndex).isHuman() && currentBoard.isHumanFinished()){
					currentBoard.repaint();
					currentIndex = (currentIndex + 1)%players.size();
					currentBoard.setHumanFinished(false);
					for( BoardCell b: targetSet ){
						b.setHighlighted(false);
					}
					
					return;
				}
				//Create a random integer for the dice roll
				int roll = (int)(Math.random()*6 + 1);
				dieRoll.setText((new Integer(roll).toString()) );
				//Get the target list for the given dice roll
				currentBoard.calcAdjacencies();
				currentBoard.calcTargets(players.get(currentIndex).getCurrentRow(), players.get(currentIndex).getCurrentCol(), roll);
				targetSet = currentBoard.getTargets();
				//Set the last location the player was at to be unoccupied
				currentBoard.getCellAt(players.get(currentIndex).getCurrentRow(), players.get(currentIndex).getCurrentCol()).setIsOccupied(false);
				players.get(currentIndex).makeAMove(targetSet);
				currentBoard.repaint();
				//Before we increment we make sure that the current player is finished
				if( players.get(currentIndex).isHuman() && !(((HumanPlayer)players.get(currentIndex)).isFinished())){
					return;
				}
				
				suggestionResponse.setText("");
				guessField.setText("");
				if( players.get(currentIndex).isComputer() && currentBoard.getCellAt(players.get(currentIndex).getCurrentRow(), players.get(currentIndex).getCurrentCol()).isRoom() ){
					Set<Card> suggestion = ( (ComputerPlayer) players.get(currentIndex)).createSuggestion();
					int i = currentIndex + 1;
					String weapon = "";
					String room = "";
					String person = "";
					for( Card c: suggestion){
						if( c.getType() == Card.CardType.PERSON){
							person = c.getName();
						}
						else if( c.getType() == Card.CardType.WEAPON){
							weapon = c.getName();
						}
						else{
							room = currentBoard.rooms.get( ((ComputerPlayer) players.get(currentIndex)).getLastRoomVisitied());
						}
					}
					for( Player p: players){
						if( p.getName().equals( person )){
							currentBoard.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setIsOccupied(false);
							p.setCurrentCol(players.get(currentIndex).getCurrentCol());
							p.setCurrentRow(players.get(currentIndex).getCurrentRow());
						}
					}
					Card returnedCard = currentClueGame.handleSuggestion(person, room, weapon, players.get(currentIndex));
					guessField.setText(person + "," + weapon +"," + room);
					if( returnedCard == null){
						suggestionResponse.setText("No New Clue");
						((ComputerPlayer)players.get(i)).setAccusationFlag(true);
						((ComputerPlayer)players.get(i)).setWinningPerson( person );
						((ComputerPlayer)players.get(i)).setWinningWeapon( weapon );
						((ComputerPlayer)players.get(i)).setWinningRoom( room );
					}
					else{
						for( int j = 0; j < players.size(); j++ ){
							if( players.get(j).isComputer()){
								((ComputerPlayer)players.get(j)).updateSeen(returnedCard);
							}
						}
						suggestionResponse.setText(returnedCard.getName());
					}
				}
				currentIndex = (currentIndex + 1)%players.size();
				currentBoard.repaint();
			}
			
			NextPlayerListener(GameInterface g, ClueGame clueGame){
				game = g;
				currentClueGame = clueGame;
			}
		}
		nextPlayer.addActionListener(new NextPlayerListener(this, game));
		temp.add(nextPlayer);
		return temp;
	}
	
	private JPanel messageLayoutSetup(){
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		return temp;	
	}
	
	private JPanel createCurrentPlayerPanel(){
		JPanel temp = new JPanel();
		JLabel currentTurn = new JLabel("Current Player's Turn:");
		temp.setLayout(new GridLayout(1,2));
		temp.add(currentTurn);
		player = new JTextField(1);
		player.setEditable(false);
		temp.add(player);
		temp.setBorder(new TitledBorder(new EtchedBorder(), "Whose Turn?"));
		return temp;
	}
	
	private JPanel lowerLeftTextSetup() {
		JPanel temp = new JPanel();
		//GridBagLayout c = new GridBagLayout();LOOK INTO THIS
		temp.setLayout(new GridLayout(1,2));
		return temp;
	}
	
	private JPanel dieRollSetup(){
		JPanel temp = new JPanel();
		JLabel rollName = new JLabel("Die Roll: ");
		temp.setLayout(new GridLayout(1,2));
		temp.add(rollName);
		dieRoll = new JTextField(10);
		dieRoll.setEditable(false);
		temp.add(dieRoll);
		temp.setBorder(new TitledBorder(new EtchedBorder(), "What did you roll?"));
		return temp;
	}
	
	private JPanel suggestionResponseSetup(){
		
		JPanel temp = new JPanel();
		JLabel suggestionName = new JLabel("Response: ");
		temp.setLayout(new GridLayout(1,2));
		temp.add(suggestionName);
		suggestionResponse = new JTextField(10);
		suggestionResponse.setEditable(false);
		temp.add(suggestionResponse);
		temp.setBorder(new TitledBorder(new EtchedBorder(), "What was the response?"));
		return temp;
	}
	public JTextField getSuggestionResponse() {
		return suggestionResponse;
	}
	public JTextField getGuessField() {
		return guessField;
	}	
	
	
}
