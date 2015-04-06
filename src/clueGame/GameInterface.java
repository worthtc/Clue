package clueGame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;





//????
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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
	
	public GameInterface(){
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
	}
	private JPanel buttonLayoutSetup(){
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		suggest = new JButton("Make a Suggestion");
		temp.add(suggest);
		nextPlayer = new JButton("Next Player");
		class NextPlayerListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				int roll = (int)Math.floor(Math.random()*6 + 1);
				dieRoll.setText((new Integer(roll).toString()) );
			}
		}
		nextPlayer.addActionListener(new NextPlayerListener());
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
	
}
