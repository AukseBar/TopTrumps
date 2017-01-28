import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JFrame;

/* Indicates if player is human or not, selects category for players (human or computer).
 * Returns int for roundsWon by each player. 
 * 
 * 
 * Maybe create additional class to manage array of players? As with Card/Deck classes.
 */

/*If player array position == 0, then player is human. Human is always first player in array layout 
/* of the class Game.
 * 
 *  isHuman == true if human is first instance in array.
 *  all players afterwards are computer players.*/



public class Player {
	
	// Hplayer is always first position (0) in player array.
		protected static final int HUMAN_PLAYER = 0;
		
		private int numOfCompPlayers;
		private String hCategory;
		private String cCategory;
		boolean human = false;
		private int roundsWon;
		private Deck playerHand = new Deck();
		private Player[] players;
		
		
	public boolean isHuman(int numOfCompPlayers){
		
	int[] players = {numOfCompPlayers + 1};
	
		if (players[HUMAN_PLAYER]{
		
		boolean human = true;
		return human;
		}
	}

	
	
	public void chooseHcategory(){ // Separate category for human player.
		
		GUI frame = new GUI();
			
			if (GUI.radioButton_0.getAction() != null){  //Selects radio button value if not null.
				String hCategory = GUI.radioButton_0.getText();	
			}
			else if (GUI.getRadioButton_1().getAction() != null){
				String hCategory = GUI.radioButton_1.getText();
			}
			else if (GUI.getRadioButton_2().getAction() != null){
				String hCategory = GUI.getRadioButton_2().getText();
			}
			else if (GUI.getRadioButton_3().getAction() != null){
				String hCategory = GUI.getRadioButton_3().getText();
			}
			else if (GUI.getRadioButton_4().getAction() != null){
				String hCategory = GUI.getRadioButton_4().getText();
			}
	}

	
	public int chooseCcategory(){  //selects category for computer player
		
		Card cards = new Card(cCategory);
		
		//Card returnThisOne; Select top card instead? 
		
		int compCategory  = Math.max(cards.getCategoryValue(Card.attrib1), cards.getCategoryValue(Card.attrib2), cards.getCategoryValue(Card.attrib3), cards.getCategoryValue(Card.attrib4), cards.getCategoryValue(Card.attrib5));
		// Math.max selects highest value. 
		
		return compCategory;
	}

	public void setHuman(boolean b) {
		// TODO Auto-generated method stub
		// game class refers to 'setHuman'
	}
	
	public void getDeck(int numOfCompPlayers){   // Makes Player & Deck completely dependent.
					
		for (players[numOfCompPlayers + 1]){
			
			Deck hand = new Deck();  
			// then split deck.
		}
		
	}
	
	private void transferCard(){   // Method transfers cards between players/communal deck upon rounds being completed. 
		
		// get top card from non-round winners, transfer to round winner.
		
			
	}
	

	
	public int getRoundsWon() {
		
		
		if (players[HUMAN_PLAYER] = Game.getRoundWinner){  //Relies on external class/method 
		
		this.roundsWon++;  //increments roundsWon 
		}
		return roundsWon;
	}
}

/*}
*/
