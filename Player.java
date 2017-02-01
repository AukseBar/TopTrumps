import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JFrame;

/* Indicates if player is human or not, selects category for players (human or computer).
 * Returns int for roundsWon by each player. 
 * 
 */


public class Player {

	private Deck playerHand;
	boolean human = false;
	private int roundsWon;


	public Player() {
		playerHand = new Deck();
	}

	public int chooseCategory(int categoryIn){  //selects category for computer player
		
		Card c = new Card(null);
		int [] hValue = {c.getCategoryValue(1), c.getCategoryValue(2), c.getCategoryValue(3), c.getCategoryValue(4), c.getCategoryValue(5)};
		
		int max = hValue[0];
		
		for (int i = 1; i < hValue.length; i++)
		{
		     if (hValue[i] > max)
		     {
		      max = hValue[i];
		     }
		}
	//	int highValue = Math.max(c.getCategoryValue(1), Math.max( c.getCategoryValue(2), Math.max(c.getCategoryValue(3), c.getCategoryValue(4))));

		
		
		
		//needs to get attribute number, not highest value
		
		return compCategory;		//****** This would return the highest value, what is actually wanted to be returned though is the attribute number (1-5) that this value belongs to
	}

	public void transferCardTo(Deck deck) {   // Method transfers cards from this deck to players' decks/communal deck upon rounds being completed. 

		playerHand.getTopCard();
		playerHand.addCardToBottom(null);
	}

	public void wonRound() {
		// increments rounds won.
		
		roundsWon++;
	}
	
	public int getRoundsWon() {
		return roundsWon;
	}

	public Deck getDeck(){   // Makes Player & Deck completely dependent.
		return playerHand;
	}

	public boolean isHuman(){
		return human;
	}

	public void setHuman(boolean b) {
		human = b;
	}
}
