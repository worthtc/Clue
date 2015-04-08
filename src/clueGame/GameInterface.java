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
		suggest = new JButton("Make a Suggestion");
		class SuggestionListener implements ActionListener {
			private ClueGame game;
			private GameInterface gameInt;
			public SuggestionListener(ClueGame g, GameInterface gi){
				game = g;
				gameInt = gi;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentBoard.getCellAt(game.getPlayers().get(currentBoard.getCurrentIndex()).getCurrentRow(),game.getPlayers().get(currentBoard.getCurrentIndex()).getCurrentCol()).isRoom() && ((HumanPlayer)game.getPlayers().get(currentBoard.getCurrentIndex())).isFinished()){
					SuggestionPanel gui = new SuggestionPanel(game.getPlayers().get(currentBoard.getCurrentIndex()), currentBoard, game, gameInt);
					gui.setVisible(true);
				}
				
			}
			
		}
		suggest.addActionListener(new SuggestionListener(game, this));
		temp.add(suggest);
		nextPlayer = new JButton("Next Player");
		//Create the listener for the Next player button
		class NextPlayerListener implements ActionListener {
			private GameInterface game;
			public void actionPerformed(ActionEvent e)
			{
				
				currentBoard.setPlayers( players );
				currentBoard.setCurrentIndex( currentIndex );
				player.setText(players.get(currentIndex).toString());
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
				if( players.get(currentIndex).isComputer() && currentBoard.getCellAt(players.get(currentIndex).getCurrentCol(), players.get(currentIndex).getCurrentRow()).isRoom() ){
					Set<Card> suggestion = ( (ComputerPlayer) players.get(currentIndex)).createSuggestion();
					int i = currentIndex + 1;
					while( i != currentIndex ){
						i = i%players.size();
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
						guessField.setText(person + "," + weapon +"," + room);
						Card returnedCard = players.get(i).disproveSuggestion(person, room, weapon );
						if ( returnedCard != null ){
							suggestionResponse.setText(returnedCard.getName());
							break;
						}
						else{
							suggestionResponse.setText("None Found");
						}
						i++;
					}
				}
				currentIndex = (currentIndex + 1)%players.size();
				currentBoard.repaint();
			}
			
			NextPlayerListener(GameInterface g){
				game = g;
			}
		}
		nextPlayer.addActionListener(new NextPlayerListener(this));
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
	public void setSuggestionResponse(JTextField suggestionResponse) {
		this.suggestionResponse = suggestionResponse;
	}
	public void setGuessField(JTextField guessField) {
		this.guessField = guessField;
	}	
	
}
