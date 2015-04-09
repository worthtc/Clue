package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clueGame.Card.CardType;

@SuppressWarnings("serial")
public class SuggestionFrame extends JFrame{
	
	private Player player;
	private Board board;
	private ClueGame game;
	private GameInterface gameInt;
	private JComboBox<String> people;
	private JComboBox<String> weapons;
	private JButton submit;
	private JButton cancel;
	
	
	public SuggestionFrame(Player p, Board b, ClueGame g, GameInterface gi){
		player = p;
		board = b;
		game = g;
		gameInt = gi;
		setLayout(new GridLayout(4,2));
		people = peopleSetup();
		weapons = weaponsSetup();
		JTextField yourRoom = new JTextField("Your Room:");
		yourRoom.setEditable(false);
		add(yourRoom);
		JTextField room = new JTextField(board.getRooms().get(((RoomCell)board.getCellAt(p.getCurrentRow(),  p.getCurrentCol())).getInitial()));
		room.setEditable(false);
		add(room);
		JTextField person = new JTextField("Person:");
		person.setEditable(false);
		add(person);
		add(people);
		JTextField weapon = new JTextField("Weapon:");
		weapon.setEditable(false);
		add(weapon);
		add(weapons);
		submit = submitSetup();
		cancel = cancelSetup();
		add(submit);
		add(cancel);
		setTitle("Make a suggestion!");
		setSize(250,250);
	}
	
	private JComboBox<String> peopleSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c :  game.getCards()){
			if(c.getType() == CardType.PERSON) temp.addItem(c.getName());
		}
		return temp;
	}
	
	private JComboBox<String> weaponsSetup(){
		JComboBox<String> temp = new JComboBox<String>();
		for(Card c : game.getCards()){
			if(c.getType() == CardType.WEAPON) temp.addItem(c.getName());
		}
		return temp;
	}
	
	private JButton submitSetup(){
		JButton temp = new JButton("Submit");
		class SubmitListener implements ActionListener{
			SuggestionFrame sp;
			
			public SubmitListener(SuggestionFrame sp){
				this.sp = sp;
			}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(Player p : game.getPlayers()){
					if(p.getName().equals((String)people.getSelectedItem())){
						board.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setIsOccupied(false);
						p.setCurrentRow(game.getPlayers().get(board.getCurrentIndex()).getCurrentRow());
						p.setCurrentCol(game.getPlayers().get(board.getCurrentIndex()).getCurrentCol());
						board.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setPlayerColor(p.getColor());
						board.getCellAt(p.getCurrentRow(), p.getCurrentCol()).setIsOccupied(true);
					}
				}
				board.repaint();
				Card a = game.handleSuggestion((String)people.getSelectedItem(),board.getRooms().get(((RoomCell)board.getCellAt(player.getCurrentRow(),  player.getCurrentCol())).getInitial()),(String)weapons.getSelectedItem(), player);
				gameInt.getGuessField().setText((String)people.getSelectedItem() + "," + board.getRooms().get(((RoomCell)board.getCellAt(player.getCurrentRow(),  player.getCurrentCol())).getInitial()) + "," + (String)weapons.getSelectedItem());
				gameInt.repaint();
				if(a == null){
					int reply = JOptionPane.showConfirmDialog(null, "Would you like to make this suggestion an accusation?", "", JOptionPane.YES_NO_OPTION);
					if(reply != 0){
						boolean check = game.checkAccusation(new Solution((String)people.getSelectedItem(),board.getRooms().get(((RoomCell)board.getCellAt(player.getCurrentRow(),  player.getCurrentCol())).getInitial()),(String)weapons.getSelectedItem()));
						if(check){
							JOptionPane.showMessageDialog(null, "You have correctly guessed the solution!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
						}
						else{
							JOptionPane.showMessageDialog(null, "You have incorrectly guessed the solution. Better luck next time!", "", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				else{
					gameInt.getSuggestionResponse().setText(a.getName());
					gameInt.repaint();
				}
				sp.dispose();
			}
			
		};
		temp.addActionListener(new SubmitListener(this));
		return temp;
	}
	
	private JButton cancelSetup(){
		JButton temp = new JButton("Cancel");
		class CancelListener implements ActionListener{
			SuggestionFrame sp;//To allow closing the frame
			
			public CancelListener(SuggestionFrame sp){
				this.sp = sp;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				sp.dispose();
			}
			
		};
		temp.addActionListener(new CancelListener(this));
		return temp;
	}
}
